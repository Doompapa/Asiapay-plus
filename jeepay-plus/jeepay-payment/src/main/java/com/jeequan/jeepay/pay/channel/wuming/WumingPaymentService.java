package com.jeequan.jeepay.pay.channel.wuming;

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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
public class WumingPaymentService extends AbstractPaymentService {

    private static final String LOG_TAG = "[无名支付]";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.WUMING;
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

            String mid = normalMchParams.getMchNo();
            String oid = payOrder.getPayOrderId();
            int pay_type = Integer.parseInt(normalMchParams.getPayType());
            long price = Long.parseLong(AmountUtil.convertCent2DollarShort(payOrder.getAmount()));
            String notify_url = getNotifyUrl(payOrder.getPayOrderId());
            String timestamp = System.currentTimeMillis() + "";
            int rand = RandomInt();

            map.put("mid", mid);
            map.put("oid", oid);
            map.put("pay_type", pay_type);
            map.put("price", price);
            map.put("notify_url", notify_url);
            map.put("timestamp", timestamp);
            map.put("rand", rand);

            String signContent = mid + oid + pay_type + price + notify_url + timestamp + rand + key;
            String sign = SignatureUtils.md5(signContent).toLowerCase();
            map.put("sign", sign);

            String payGateway = normalMchParams.getPayGateway();
            log.info("[{}]请求参数:{}", LOG_TAG, JSONObject.toJSONString(map));

            // 发送POST请求并指定JSON数据
            HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).contentType("application/json").timeout(10000).execute();
            // 处理响应
            raw = response.body();
            log.info("[{}]请求响应:{}", LOG_TAG, raw);
            channelRetMsg.setChannelOriginResponse(raw);
            JSONObject result = JSON.parseObject(raw, JSONObject.class);
            //拉起订单成功
            if (result.getString("code").equals("0")) {

                String payUrl = result.getString("url");
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
        String key = "XcaTkEUvdFUXeFnSoguLJG5Wcegs3UVY";

        String mid = "10015";
        String oid = RandomStringUtils.random(15, true, true);
        int pay_type = 0;
        long price = Long.parseLong(AmountUtil.convertCent2DollarShort(10000L));
        String notify_url = "https://www.test.com";
        String timestamp = System.currentTimeMillis() + "";
        int rand = RandomInt();

        map.put("mid", mid);
        map.put("oid", oid);
        map.put("pay_type", pay_type);
        map.put("price", price);
        map.put("notify_url", notify_url);
        map.put("timestamp", timestamp);
        map.put("rand", rand);

        String signContent = mid + oid + pay_type + price + notify_url + timestamp + rand + key;
        String sign = SignatureUtils.md5(signContent).toLowerCase();
        map.put("sign", sign);

        String payGateway = "http://47.242.249.190:88/pay/create";

        // 发送POST请求并指定JSON数据
        HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).contentType("application/json").timeout(10000).execute();
        // 处理响应
        raw = response.body();
        log.info("[{}]请求响应:{}", LOG_TAG, raw);
    }

    private static int RandomInt() {
        Random random = new Random();
        int lowerBound = 100000;
        int upperBound = 999999;
        return random.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }
}