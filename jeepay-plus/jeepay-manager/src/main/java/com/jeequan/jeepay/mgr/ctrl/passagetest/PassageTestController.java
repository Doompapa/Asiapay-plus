package com.jeequan.jeepay.mgr.ctrl.passagetest;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.exception.BizException;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.core.model.DBApplicationConfig;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.service.impl.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/passageTest")
public class PassageTestController extends CommonCtrl {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 调起下单接口
     **/
    @PostMapping("/doPay")
    public ApiRes doPay() {
        //获取请求参数
        //金额
        Long amount = getRequiredAmountL("amount");
        //测试订单号
        String testOrderNo = getValStringRequired("testOrderNo");
        //通道号
        Long passageId = getValLongRequired("passageId");
        //产品号
        Long productId = getValLongRequired("productId");
        //订单是否入库并挂在测试商户下
        Byte testOrderIn = getValByte("testOrderIn");

        DBApplicationConfig dbApplicationConfig = sysConfigService.getDBApplicationConfig();


        Map<String, Object> map = new HashMap<>();

        long reqTime = System.currentTimeMillis();

        String notifyUrl = dbApplicationConfig.getMgrSiteUrl() + "/api/anon/passageTestNotify/payOrder";

        map.put("payOrderId", testOrderNo);
        map.put("mchOrderNo", testOrderNo);
        map.put("amount", amount);
        map.put("passageId", passageId);
        map.put("productId", productId);
        map.put("reqTime", reqTime);
        map.put("clientIp", getClientIp());
        map.put("notifyUrl", notifyUrl);

        String payGateWay = "";
        if (testOrderIn != null && testOrderIn == CS.YES) {
            payGateWay = dbApplicationConfig.getPaySiteUrl() + "/api/pay/unifiedOrderInTest";
        } else {
            payGateWay = dbApplicationConfig.getPaySiteUrl() + "/api/pay/unifiedOrderPassageTest";
        }

        String raw = HttpUtil.post(payGateWay, map);
        JSONObject jsonObject = JSONObject.parseObject(raw);
        return ApiRes.ok(jsonObject);
    }
}

