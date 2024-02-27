package com.jeequan.jeepay.pay.channel.septpay;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.PayOrder;
import com.jeequan.jeepay.core.entity.PayPassage;
import com.jeequan.jeepay.core.exception.ResponseException;
import com.jeequan.jeepay.core.model.params.NormalMchParams;
import com.jeequan.jeepay.core.utils.SignatureUtils;
import com.jeequan.jeepay.pay.channel.AbstractChannelNoticeService;
import com.jeequan.jeepay.pay.rqrs.msg.ChannelRetMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SeptpayChannelNoticeService extends AbstractChannelNoticeService {
    private static final String LOG_TAG = "[sept支付]";

    private static final String ON_FAIL = "fail";

    private static final String ON_SUCCESS = "success";

    @Override
    public String getIfCode() {
        return CS.IF_CODE.SEPTPAY;
    }

    @Override
    public MutablePair<String, Object> parseParams(HttpServletRequest request, String urlOrderId, NoticeTypeEnum noticeTypeEnum) {
        try {
            JSONObject params = getReqParamJSON();
            return MutablePair.of(urlOrderId, params);
        } catch (Exception e) {
            log.error("error", e);
            throw ResponseException.buildText("ERROR");
        }
    }

    @Override
    public ChannelRetMsg doNotice(HttpServletRequest request, Object params, PayOrder payOrder, PayPassage payPassage, NoticeTypeEnum noticeTypeEnum) {
        ChannelRetMsg result = ChannelRetMsg.confirmSuccess(null);
        try {
            // 获取请求参数
            JSONObject jsonParams = (JSONObject) params;
            log.info("{} 回调参数, jsonParams：{}", LOG_TAG, jsonParams);

            // 校验支付回调
            boolean verifyResult = verifyParams(jsonParams, payOrder, payPassage);
            // 验证参数失败
            if (!verifyResult) {
                //回调参数有问题得
                throw ResponseException.buildText(ON_FAIL);
            }
            log.info("{}验证支付通知数据及签名通过", LOG_TAG);

            ResponseEntity okResponse = textResp(ON_SUCCESS);
            result.setResponseEntity(okResponse);

            //查单
            Map<String, Object> map = new HashMap<>();
            String merOrderId = jsonParams.getString("merOrderId");
            NormalMchParams resultsParam = JSONObject.parseObject(payPassage.getPayInterfaceConfig(), NormalMchParams.class);
            String key = resultsParam.getSecret();
            map.put("merchantId", resultsParam.getMchNo());
            map.put("merOrderId", merOrderId);
            String signStr = SignatureUtils.getSignContentFilterEmpty(map, null) + key;
            String signature = SignatureUtils.md5(signStr);
            map.put("signature", signature);

            String raw = HttpUtil.post(resultsParam.getQueryUrl(), map, 10000);
            log.info("{} 查单请求响应:{}", LOG_TAG, raw);
            JSONObject queryResult = JSON.parseObject(raw, JSONObject.class);

            //code=0为查询成功，订单状态: transStatus=1交易成功，transStatus=2交易失败，transStatus=3交易中
            if (queryResult.getString("code").equals("0")) {
                String transStatus = queryResult.getString("transStatus");
                if (transStatus.equals("1")) {
                    //验签成功后判断上游订单状态
                    result.setChannelState(ChannelRetMsg.ChannelState.CONFIRM_SUCCESS);
                    return result;
                }
            }
            log.info("{}回调通知订单状态错误:{}", LOG_TAG, raw);
            result.setChannelState(ChannelRetMsg.ChannelState.CONFIRM_FAIL);
            return result;
        } catch (Exception e) {
            log.error("error", e);
            throw ResponseException.buildText(ON_FAIL);
        }
    }

    /**
     * 校验签名及金额
     *
     * @param jsonParams
     * @param payOrder
     * @param payPassage
     * @return
     */
    public boolean verifyParams(JSONObject jsonParams, PayOrder payOrder, PayPassage payPassage) {
        String orderNo = jsonParams.getString("merOrderId");        // 商户订单号
        String txnAmt = jsonParams.getString("txnAmt");        // 支付金额

        if (StringUtils.isEmpty(orderNo)) {
            log.info("订单ID为空 [orderNo]={}", orderNo);
            return false;
        }
        if (StringUtils.isEmpty(txnAmt)) {
            log.info("金额参数为空 [txnAmt] :{}", txnAmt);
            return false;
        }

        BigDecimal channelNotifyAmount = new BigDecimal(txnAmt);
        BigDecimal orderAmount = new BigDecimal(payOrder.getAmount());

        NormalMchParams resultsParam = JSONObject.parseObject(payPassage.getPayInterfaceConfig(), NormalMchParams.class);

        String sign = jsonParams.getString("signature");
        Map map = JSON.parseObject(jsonParams.toJSONString());
        map.remove("signature");
        if (resultsParam != null) {
            String secret = resultsParam.getSecret();
            final String signContentStr = SignatureUtils.getSignContentFilterEmpty(map, new String[]{"attach"}) + secret;
            final String signStr = SignatureUtils.md5(signContentStr);
            if (signStr.equalsIgnoreCase(sign) && orderAmount.compareTo(channelNotifyAmount) == 0) {
                return true;
            } else {
                log.error("{} 验签或校验金额失败！ 回调参数：parameter = {}", LOG_TAG, jsonParams);
                return false;
            }
        } else {
            log.info("{} 获取商户配置失败！ 参数：parameter = {}", LOG_TAG, jsonParams);
            return false;
        }
    }

    public static void main(String[] args){
        String jsonParams = "{\"merchantId\":\"Y2400047811\",\"signature\":\"4b26f45dcc45dd2c904f6980c9e16e56\",\"success\":\"1\",\"respMsg\":\"交易成功\",\"txnAmt\":\"10000\",\"respCode\":\"1001\",\"merOrderId\":\"P1762390783818280962\"}";
        Map map = JSON.parseObject(jsonParams);
        String sign = map.get("signature").toString();
        System.out.println("返回Sign："+ sign);
        String secret = "cacd9a4ae76940b5b6e3f8ae95e260d7";
        map.remove("signature");

        String signContentStr = SignatureUtils.getSignContentFilterEmpty(map, new String[]{"attach"}) + secret;
        String signStr = SignatureUtils.md5(signContentStr);
        System.out.println("MD5后字符串："+ signStr);
    }
}
