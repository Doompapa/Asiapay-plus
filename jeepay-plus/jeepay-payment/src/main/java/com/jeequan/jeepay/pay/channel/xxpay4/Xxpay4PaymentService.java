package com.jeequan.jeepay.pay.channel.xxpay4;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class Xxpay4PaymentService extends AbstractPaymentService {

    private static final String LOG_TAG = "[xxpay4支付]";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.XXPAY4;
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

            String mchId = normalMchParams.getMchNo();
            String productId = normalMchParams.getPayType();
            String mchOrderNo = payOrder.getPayOrderId();

            String subject = "goods";
            String body = "desc";

            String amount = AmountUtil.convertCent2Dollar(payOrder.getAmount());

            String notifyUrl = getNotifyUrl(payOrder.getPayOrderId());
            String clientIp = payOrder.getClientIp();
            if (!StringUtils.isNotEmpty(clientIp)) {
                clientIp = "127.0.0.1";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String timeStamp = dateFormat.format(new Date());

            map.put("mchId", mchId);
            map.put("productId", productId);
            map.put("amount", amount);
            map.put("mchOrderNo", mchOrderNo);

            map.put("subject", subject);
            map.put("body", body);
            map.put("clientIp", clientIp);
            map.put("notifyUrl", notifyUrl);
            map.put("timeStamp", timeStamp);

            String signContent = SignatureUtils.getSignContent(map, null, new String[]{""});
            String sign = SignatureUtils.md5(signContent + "&key=" + key).toUpperCase();
            map.put("sign", sign);

            String payGateway = normalMchParams.getPayGateway();
            log.info("[{}]请求参数:{}", LOG_TAG, JSONObject.toJSONString(map));

            HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).contentType("application/json").timeout(10000) // 指定请求体的Content-Type为JSON
                    .execute();
            // 处理响应
            raw = response.body();
            log.info("[{}]请求响应:{}", LOG_TAG, raw);
            channelRetMsg.setChannelOriginResponse(raw);
            JSONObject result = JSON.parseObject(raw, JSONObject.class);
            //拉起订单成功
            if (result.getString("code").equals("200")) {

                String payUrl = result.getJSONObject("data").getString("payUrl");
                String passageOrderId = result.getJSONObject("data").getString("orderNo");

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
        String key = "a11dfa08e58f10cfe8bbe4e73f71dfcde72d7c4b";

        String mchId = "458";
        String productId = "200235";
        String mchOrderNo = RandomStringUtils.random(15, true, true);

        String subject = "goods";
        String body = "desc";
        String clientIp = "127.0.0.1";

        String amount = AmountUtil.convertCent2Dollar(10000L);
        String notifyUrl = "https://www.test.com";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = dateFormat.format(new Date());

        map.put("mchId", mchId);
        map.put("productId", productId);
        map.put("amount", amount);
        map.put("mchOrderNo", mchOrderNo);

//        map.put("currency", currency);
        map.put("subject", subject);
        map.put("body", body);
        map.put("clientIp", clientIp);
        map.put("notifyUrl", notifyUrl);
        map.put("timeStamp", timeStamp);

        String signContent = SignatureUtils.getSignContent(map, null, new String[]{""});
        String sign = SignatureUtils.md5(signContent + "&key=" + key).toUpperCase();
        map.put("sign", sign);

        String payGateway = "https://mgr.txzf1.xyz/tx-mgr-api/order/matchingPlaceOrder";
        log.info("[{}]请求:{}", LOG_TAG, map);

//        // 发送POST请求并指定JSON数据
        HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).contentType("application/json").timeout(10000) // 指定请求体的Content-Type为JSON
                .execute();
        // 处理响应
        raw = response.body();

//        raw = HttpUtil.post(normalMchParams.getPayGateway(), map,10000);
        log.info("[{}]请求响应:{}", LOG_TAG, raw);
    }
}