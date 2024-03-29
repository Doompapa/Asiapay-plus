package com.jeequan.jeepay.pay.channel.baihui;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.PayOrder;
import com.jeequan.jeepay.core.entity.PayPassage;
import com.jeequan.jeepay.core.model.params.NormalMchParams;
import com.jeequan.jeepay.core.utils.AmountUtil;
import com.jeequan.jeepay.core.utils.SignatureUtils;
import com.jeequan.jeepay.pay.channel.AbstractPaymentService;
import com.jeequan.jeepay.pay.model.PayConfigContext;
import com.jeequan.jeepay.pay.rqrs.AbstractRS;
import com.jeequan.jeepay.pay.rqrs.msg.ChannelRetMsg;
import com.jeequan.jeepay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.jeequan.jeepay.pay.rqrs.payorder.UnifiedOrderRS;
import com.jeequan.jeepay.pay.util.ApiResBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BaihuiPaymentService extends AbstractPaymentService {

    private static final String LOG_TAG = "[百汇支付]";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.BAIHUI;
    }

    @Override
    public AbstractRS pay(UnifiedOrderRQ bizRQ, PayOrder payOrder, PayConfigContext payConfigContext) {
        log.info("[{}]开始下单:{}", LOG_TAG, payOrder.getPayOrderId());
        UnifiedOrderRS res = ApiResBuilder.buildSuccess(UnifiedOrderRS.class);
        ChannelRetMsg channelRetMsg = new ChannelRetMsg();
        res.setChannelRetMsg(channelRetMsg);
        String raw = "";
        try {
            PayPassage payPassage = payConfigContext.getPayPassage();
            //支付参数转换
            NormalMchParams normalMchParams = JSONObject.parseObject(payPassage.getPayInterfaceConfig(), NormalMchParams.class);

            Map<String, Object> map = new HashMap<>();
            String key = normalMchParams.getSecret();

            String merchantNo = normalMchParams.getMchNo();
            String merchantOrderNo = payOrder.getPayOrderId();
            String channelCode = normalMchParams.getPayType();
            String amount = AmountUtil.convertCent2Dollar(payOrder.getAmount());
            String version = "1.0";
            String notifyUrl = getNotifyUrl(payOrder.getPayOrderId());

            map.put("merchantNo", merchantNo);
            map.put("merchantOrderNo", merchantOrderNo);
            map.put("channelCode", channelCode);
            map.put("amount", amount);
            map.put("version", version);
            map.put("notifyUrl", notifyUrl);

            String signContent = SignatureUtils.getSignContent(map, null, new String[]{""});
            String sign = SignatureUtils.md5(signContent + "&key=" + key).toUpperCase();
            map.put("sign", sign);

            String payGateway = normalMchParams.getPayGateway();
            log.info("[{}]请求参数:{}", LOG_TAG, JSONObject.toJSONString(map));

            HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).header("welcome", "welcome-pay").contentType("application/json").timeout(10000) // 指定请求体的Content-Type为JSON
                    .execute();
            // 处理响应
            raw = response.body();
            log.info("[{}]请求响应:{}", LOG_TAG, raw);
            channelRetMsg.setChannelOriginResponse(raw);
            JSONObject result = JSON.parseObject(raw, JSONObject.class);
            JSONObject data = result.getJSONObject("data");

            //拉起订单成功
            if (result.getString("code").equals("0") && StringUtils.isNotEmpty(data.getString("payData"))) {

                String payUrl = data.getString("payData");
                String passageOrderId = data.getString("orderNo");

                res.setPayDataType(CS.PAY_DATA_TYPE.PAY_URL);
                res.setPayData(payUrl);

                channelRetMsg.setChannelOrderId(passageOrderId);
                channelRetMsg.setChannelState(ChannelRetMsg.ChannelState.WAITING);
            } else {
                //出码失败
                channelRetMsg.setChannelState(ChannelRetMsg.ChannelState.CONFIRM_FAIL);
            }
        } catch (Exception e) {
            channelRetMsg.setChannelState(ChannelRetMsg.ChannelState.SYS_ERROR);
            log.error("[{}] 异常: {}", LOG_TAG, payOrder.getPayOrderId());
            log.error(e.getMessage(), e);
        }
        return res;
    }


    public static void main(String[] args) {
        String raw = "";

        Map<String, Object> map = new HashMap<>();
        String key = "a130c6e6c5004a74997b6f3a77a7bafa";

        String merchantNo = "11591";
        String merchantOrderNo = RandomStringUtils.random(15, true, true);
        String channelCode = "002";
        String amount = AmountUtil.convertCent2Dollar(10000L);
        String version = "1.0";

        map.put("merchantNo", merchantNo);
        map.put("merchantOrderNo", merchantOrderNo);
        map.put("channelCode", channelCode);
        map.put("amount", amount);
        map.put("version", version);

        String signContent = SignatureUtils.getSignContent(map, null, new String[]{""});
        String sign = SignatureUtils.md5(signContent + "&key=" + key).toUpperCase();
        map.put("sign", sign);

        String payGateway = "https://pay.bbbtre.co/pay-api/order/create";
        log.info("[{}]请求map:{}", LOG_TAG, JSONObject.toJSON(map).toString());

        // 发送POST请求并指定JSON数据
        HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).header("welcome", "welcome-pay").contentType("application/json").timeout(10000).execute();
        // 处理响应
        raw = response.body();
        log.info("[{}]请求响应:{}", LOG_TAG, raw);
    }
}