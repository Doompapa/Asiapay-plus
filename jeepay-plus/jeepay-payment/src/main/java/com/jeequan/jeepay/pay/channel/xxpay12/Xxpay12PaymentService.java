package com.jeequan.jeepay.pay.channel.xxpay12;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class Xxpay12PaymentService extends AbstractPaymentService {

    private static final String LOG_TAG = "[xxpay12支付]";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.XXPAY12;
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
            long amount = payOrder.getAmount();
            String currency = "cny";
            String clientIp = payOrder.getClientIp();
            String device = "app";
            String notifyUrl = getNotifyUrl(payOrder.getPayOrderId());
            String subject = "subject";
            String body = "body";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String reqTime = dateFormat.format(new Date());
            String version = "1.0";

            map.put("mchId", mchId);
            map.put("productId", productId);
            map.put("mchOrderNo", mchOrderNo);
            map.put("amount", amount);
            map.put("currency", currency);
            map.put("clientIp", clientIp);
            map.put("device", device);
            map.put("notifyUrl", notifyUrl);
            map.put("subject", subject);
            map.put("body", body);
            map.put("reqTime", reqTime);
            map.put("version", version);

            String sign = JeepayKit.getSign(map, key).toLowerCase();
            map.put("sign", sign);

            String payGateway = normalMchParams.getPayGateway();

            raw = HttpUtil.post(payGateway, map, 10000);
            log.info("[{}]请求响应:{}", LOG_TAG, raw);
            channelRetMsg.setChannelOriginResponse(raw);
            JSONObject result = JSON.parseObject(raw, JSONObject.class);
            //拉起订单成功
            if (result.getString("retCode").equals("SUCCESS")) {
                JSONObject data = result.getJSONObject("payParams");
                String payUrl = data.getString("payUrl");

                String passageOrderId = "";

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
        String key = "IO5EPO6D6O9FYNGMQLNNEKUZH7BFU17D3LRDP0Y0H9D4CDIGAOXJITMH152QN3EGEYZNWZKVFDHUROWQIUG2GVGXZXGVNZUEDZFSGIN13223UPS20TIX2WHPEX9NEKHE";

        String mchId = "20000062";
        String productId = "8028";
        String mchOrderNo = RandomStringUtils.random(15, true, true);
        long amount = 10000;
        String currency = "cny";
        String clientIp = "127.0.0.1";
        String device = "app";
        String notifyUrl = "https://www.test.com";
        String subject = "subject";
        String body = "body";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String reqTime = dateFormat.format(new Date());
        String version = "1.0";

        map.put("mchId", mchId);
        map.put("productId", productId);
        map.put("mchOrderNo", mchOrderNo);
        map.put("amount", amount);
        map.put("currency", currency);
        map.put("clientIp", clientIp);
        map.put("device", device);
        map.put("notifyUrl", notifyUrl);
        map.put("subject", subject);
        map.put("body", body);
        map.put("reqTime", reqTime);
        map.put("version", version);

        String sign = JeepayKit.getSign(map, key).toLowerCase();
        map.put("sign", sign);

        String payGateway = "http://154.197.6.114/api/pay/create_order";

        raw = HttpUtil.post(payGateway, map,10000);
        log.info("[{}]请求响应:{}", LOG_TAG, raw);
    }
}