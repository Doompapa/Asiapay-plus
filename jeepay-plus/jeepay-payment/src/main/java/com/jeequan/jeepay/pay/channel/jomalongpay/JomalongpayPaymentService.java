package com.jeequan.jeepay.pay.channel.jomalongpay;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.PayOrder;
import com.jeequan.jeepay.core.entity.PayPassage;
import com.jeequan.jeepay.core.model.params.NormalMchParams;
import com.jeequan.jeepay.core.utils.JeepayKit;
import com.jeequan.jeepay.pay.channel.AbstractPaymentService;
import com.jeequan.jeepay.pay.model.PayConfigContext;
import com.jeequan.jeepay.pay.rqrs.AbstractRS;
import com.jeequan.jeepay.pay.rqrs.msg.ChannelRetMsg;
import com.jeequan.jeepay.pay.rqrs.payorder.UnifiedOrderRQ;
import com.jeequan.jeepay.pay.rqrs.payorder.UnifiedOrderRS;
import com.jeequan.jeepay.pay.util.ApiResBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JomalongpayPaymentService extends AbstractPaymentService {

    private static final String LOG_TAG = "祖玛珑支付";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.JOMALONGPAY;
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

            String mchId = normalMchParams.getMchNo();
            String productId = normalMchParams.getPayType();
            String mchOrderNo = payOrder.getPayOrderId();
            long amount = payOrder.getAmount();
            String notifyUrl = getNotifyUrl(payOrder.getPayOrderId());
            String key = normalMchParams.getSecret();

            Map<String, Object> map = new HashMap<>();

            map.put("mchId", mchId);
            map.put("productId", productId);
            map.put("amount", amount);
            map.put("mchOrderNo", mchOrderNo);
            map.put("notifyUrl", notifyUrl);
            String sign = JeepayKit.getSign(map, key).toLowerCase();
            map.put("sign", sign);

            String payGateway = normalMchParams.getPayGateway();
            log.info("[{}]请求参数:{}", LOG_TAG, JSONObject.toJSONString(map));

            raw = HttpUtil.post(payGateway, map, 10000);
            channelRetMsg.setChannelOriginResponse(raw);
            log.info("[{}]请求响应:{}", LOG_TAG, raw);
            JSONObject result = JSON.parseObject(raw, JSONObject.class);
            //拉起订单成功
            if (result.getString("retCode").equals("SUCCESS")) {
                JSONObject data = result.getJSONObject("payParams");
                String payUrl = data.getString("payUrl");

                String passageOrderId = result.getString("payOrderId");

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
        String key = "4CPAX2SWB7DJEURINHW5ASB0BF8RJIYJRK0XIIDAMRKCOL3A8XB4IMBDFZWYEGWRGZI1P7CPZORK2TEITXBANPDD5CNHVYIKWLMTJ1U1BZVFWVQZJHPE8INCBNO3UDBD";

        String mchId = "10035";
        String productId = "2001";
        String mchOrderNo = RandomStringUtils.random(15, true, true);
        long amount = 1000;
        String notifyUrl = "https://www.test.com";

        map.put("mchId", mchId);
        map.put("productId", productId);
        map.put("amount", amount);
        map.put("mchOrderNo", mchOrderNo);
        map.put("notifyUrl", notifyUrl);
        String sign = JeepayKit.getSign(map, key).toLowerCase();
        map.put("sign", sign);

        String payGateway = "http://pay.jomalong.click/api/pay/create_order";

        raw = HttpUtil.post(payGateway, map, 10000);
        // 发送POST请求并指定JSON数据
//        HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).contentType("application/json") // 指定请求体的Content-Type为JSON.execute();
        // 处理响应
//        raw = response.body();
        log.info("[{}]请求响应:{}", LOG_TAG, raw);
    }

}