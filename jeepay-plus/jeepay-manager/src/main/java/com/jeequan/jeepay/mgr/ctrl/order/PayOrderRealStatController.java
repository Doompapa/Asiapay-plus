package com.jeequan.jeepay.mgr.ctrl.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jeequan.jeepay.core.entity.*;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 实时统计
 */
@Slf4j
@RestController
@RequestMapping("/api/payRealTimeStatOrder")
public class PayOrderRealStatController extends CommonCtrl {
    @Autowired
    private PayOrderService payOrderService;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ApiRes countReal() {
        JSONObject paramJSON = getReqParamJSON();
        PayOrder payOrder = getObject(PayOrder.class);
        LambdaQueryWrapper<PayOrder> wrapper = PayOrder.gw();
//        long test1 = System.currentTimeMillis();
        List<PayOrder> payOrderList = payOrderService.listByQueryStatTotal(payOrder, paramJSON, wrapper);
//        long test2 = System.currentTimeMillis();
//        log.error("================= " + ((test2 - test1) / 1000f) + " ============");
        return ApiRes.ok(genReturnJson(payOrderList));
    }

    @RequestMapping(value = "/countRealForceOrder", method = RequestMethod.POST)
    public ApiRes countRealForceOrder() {
        //todo 优化此处操作,节约性能
        JSONObject paramJSON = getReqParamJSON();
        PayOrder payOrder = getObject(PayOrder.class);
        LambdaQueryWrapper<PayOrder> wrapper = PayOrder.gw();
//        long test1 = System.currentTimeMillis();
        List<PayOrder> payOrderList = payOrderService.listByQueryUpdatedAt(payOrder, paramJSON, wrapper);
//        long test2 = System.currentTimeMillis();
//        log.error("================= " + ((test2 - test1) / 1000f) + " ============");
        return ApiRes.ok(genReturnJson(payOrderList));
    }

    private Long CalPlatProfit(PayOrder payOrder) {
        return payOrder.getMchFeeAmount() - payOrder.getPassageFeeAmount() - payOrder.getAgentFeeAmount() - payOrder.getAgentPassageFee();
    }

    private Long CalMchIncome(PayOrder payOrder) {
        return payOrder.getAmount() - payOrder.getMchFeeAmount();
    }

    private JSONObject genReturnJson(List<PayOrder> payOrderList) {
        ////统计数据 成交金额,利润,订单总数,成交订单数,订单总金额，平台成本，成功率，代理收入
        int successCount = 0;
        int totalCount = payOrderList.size();//订单总数
        Long totalAmount = 0L;//订单总金额
        Long successAmount = 0L;//成交金额
        Long totalIncome = 0L;//利润
        Long totalMchIncome = 0L;//商户入账
        for (int i = 0; i < payOrderList.size(); i++) {
            PayOrder payOrderItem = payOrderList.get(i);
            if (payOrderItem.getState() == PayOrder.STATE_SUCCESS) {
                successCount++;
                successAmount += payOrderItem.getAmount();
                totalIncome += CalPlatProfit(payOrderItem);
                totalMchIncome += CalMchIncome(payOrderItem);
            }
            totalAmount += payOrderItem.getAmount();
        }
        JSONObject result = new JSONObject();
        result.put("successCount", successCount);
        result.put("totalCount", totalCount);
        result.put("totalAmount", totalAmount);
        result.put("successAmount", successAmount);
        result.put("totalIncome", totalIncome);
        result.put("totalMchIncome", totalMchIncome);
        return result;
    }
}
