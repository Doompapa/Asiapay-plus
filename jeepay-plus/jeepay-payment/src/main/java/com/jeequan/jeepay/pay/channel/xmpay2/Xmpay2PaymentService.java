package com.jeequan.jeepay.pay.channel.xmpay2;

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
public class Xmpay2PaymentService extends AbstractPaymentService {
    private static final String LOG_TAG = "[XM支付2]";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.XMPAY2;
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
            String mchOrderId = payOrder.getPayOrderId();
            String price = AmountUtil.convertCent2DollarShort(payOrder.getAmount());
            String notify = getNotifyUrl(payOrder.getPayOrderId());
            String time = System.currentTimeMillis() / 1000 + "";
            String payType = normalMchParams.getPayType();
            Random random = new Random();
            int rand = 100000 + random.nextInt(900000);

            map.put("mchId", mchId);
            map.put("mchOrderId", mchOrderId);
            map.put("price", price);
            map.put("notify", notify);
            map.put("time", time);
            map.put("payType", payType);
            map.put("rand", rand);

            String sign = SignatureUtils.md5(mchId + mchOrderId + price + payType + notify + rand + time + key).toLowerCase();
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
                JSONObject data = result.getJSONObject("data");

                String payUrl = data.getString("url");
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
        String key = "d0b6a9cb339d44aab0c7ab935a12a255";

        String mchId = "zk1";
        String mchOrderId = RandomStringUtils.random(15, true, true);
        String price = AmountUtil.convertCent2DollarShort(10000L);
        String notify = "http://www.test.com";
        String time = System.currentTimeMillis() / 1000 + "";
        String payType = "1";
        Random random = new Random();
        int rand = 100000 + random.nextInt(900000);

        map.put("mchId", mchId);
        map.put("mchOrderId", mchOrderId);
        map.put("price", price);
        map.put("notify", notify);
        map.put("time", time);
        map.put("payType", payType);
        map.put("rand", rand);

        String sign = SignatureUtils.md5(mchId + mchOrderId + price + payType + notify + rand + time + key).toLowerCase();
        map.put("sign", sign);


        String payGateway = "http://120.24.76.227:8085/api/chargesOrder";

        // 发送POST请求并指定JSON数据
        HttpResponse response = HttpUtil.createPost(payGateway).body(JSONObject.toJSON(map).toString()).contentType("application/json").timeout(10000).execute();
        // 处理响应
        raw = response.body();
        log.info("[{}]请求响应:{}", LOG_TAG, raw);
    }
}