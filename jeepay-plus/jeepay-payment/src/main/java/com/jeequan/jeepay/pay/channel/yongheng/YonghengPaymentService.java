package com.jeequan.jeepay.pay.channel.yongheng;

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


@Service
@Slf4j
public class YonghengPaymentService extends AbstractPaymentService {

    private static final String LOG_TAG = "[永恒支付]";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.YONGHENG;
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

            String merchcode = normalMchParams.getMchNo();
            String out_trade_no = payOrder.getPayOrderId();
            String amount = AmountUtil.convertCent2Dollar(payOrder.getAmount());

            String transcode = normalMchParams.getPayType();

            String signtype = "MD5";
            String returntype = "1";

            String notifyurl = getNotifyUrl(payOrder.getPayOrderId());

            map.put("merchcode", merchcode);
            map.put("out_trade_no", out_trade_no);
            map.put("amount", amount);

            map.put("transcode", transcode);

            map.put("returntype", returntype);
            map.put("signtype", signtype);
            map.put("notifyurl", notifyurl);


            String signContent = SignatureUtils.getSignContentFilterEmpty(map, null);
            String sign = SignatureUtils.md5(signContent + "&key=" + key).toLowerCase();
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

                String payUrl = result.getString("pay_url");
                String passageOrderId = result.getJSONObject("data").getString("order_no");

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
        String key = "RUV5MWDYXFPDWBZWFQXMIIBWI4EXWXDP";

        String merchcode = "357100001750";
        String out_trade_no = RandomStringUtils.random(15, true, true);
        String amount = AmountUtil.convertCent2Dollar(10000L);

        String transcode = "2";

        String signtype = "MD5";

        String notifyurl = "https://www.test.com";

        String returntype = "1";

        map.put("merchcode", merchcode);
        map.put("out_trade_no", out_trade_no);
        map.put("amount", amount);

        map.put("transcode", transcode);

        map.put("returntype", returntype);
        map.put("signtype", signtype);
        map.put("notifyurl", notifyurl);


        String signContent = SignatureUtils.getSignContentFilterEmpty(map, null);
        String sign = SignatureUtils.md5(signContent + "&key=" + key).toLowerCase();
        map.put("sign", sign);

        String payGateway = "https://s.wgw921.com/gateway/preorder.html";
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