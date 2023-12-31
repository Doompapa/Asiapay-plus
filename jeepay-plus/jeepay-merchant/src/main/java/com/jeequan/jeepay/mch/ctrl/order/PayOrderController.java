/*
 * Copyright (c) 2021-2031
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jeequan.jeepay.mch.ctrl.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeequan.jeepay.JeepayClient;
import com.jeequan.jeepay.core.aop.MethodLog;
import com.jeequan.jeepay.core.constants.ApiCodeEnum;
import com.jeequan.jeepay.core.entity.PayOrder;
import com.jeequan.jeepay.core.exception.BizException;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.core.utils.SeqKit;
import com.jeequan.jeepay.exception.JeepayException;
import com.jeequan.jeepay.mch.ctrl.CommonCtrl;
import com.jeequan.jeepay.model.RefundOrderCreateReqModel;
import com.jeequan.jeepay.request.RefundOrderCreateRequest;
import com.jeequan.jeepay.response.RefundOrderCreateResponse;
import com.jeequan.jeepay.service.impl.PayOrderService;
import com.jeequan.jeepay.service.impl.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付订单管理类
 *
 * @author zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@RestController
@RequestMapping("/api/payOrder")
public class PayOrderController extends CommonCtrl {
    //todo PayOrderController
    @Autowired
    private PayOrderService payOrderService;

    /**
     * @Author: ZhuXiao
     * @Description: 订单信息列表
     * @Date: 10:43 2021/5/13
     */
    @GetMapping
    public ApiRes list() {
        try {
            PayOrder payOrder = getObject(PayOrder.class);
            payOrder.setMchNo(getCurrentMchNo());
            JSONObject paramJSON = getReqParamJSON();
            LambdaQueryWrapper<PayOrder> wrapper = PayOrder.gw();

            wrapper.select(PayOrder::getPayOrderId, PayOrder::getMchNo, PayOrder::getMchName, PayOrder::getMchOrderNo, PayOrder::getAmount, PayOrder::getState, PayOrder::getNotifyState, PayOrder::getProductId, PayOrder::getProductName, PayOrder::getCreatedAt, PayOrder::getUpdatedAt, PayOrder::getSuccessTime, PayOrder::getClientIp, PayOrder::getNotifyUrl, PayOrder::getForceChangeState, PayOrder::getForceChangeBeforeState, PayOrder::getForceChangeLoginName, PayOrder::getMchFeeAmount, PayOrder::getMchFeeRate);
            IPage<PayOrder> pages = payOrderService.listByPage(getIPage(), payOrder, paramJSON, wrapper);
            return ApiRes.page(pages);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiRes.fail(ApiCodeEnum.QUERY_ERROR);
        }
    }

    /**
     * @Author: ZhuXiao
     * @Description: 支付订单信息
     * @Date: 10:43 2021/5/13
     */
    @GetMapping("/{payOrderId}")
    public ApiRes detail(@PathVariable("payOrderId") String payOrderId) {
        PayOrder payOrder = payOrderService.getOne(PayOrder.gw().select(PayOrder::getPayOrderId, PayOrder::getMchNo, PayOrder::getMchName, PayOrder::getMchOrderNo, PayOrder::getAmount, PayOrder::getState, PayOrder::getNotifyState, PayOrder::getProductId, PayOrder::getProductName, PayOrder::getCreatedAt, PayOrder::getUpdatedAt, PayOrder::getSuccessTime, PayOrder::getClientIp, PayOrder::getNotifyUrl, PayOrder::getForceChangeState, PayOrder::getForceChangeBeforeState, PayOrder::getForceChangeLoginName, PayOrder::getMchFeeAmount, PayOrder::getMchFeeRate).eq(PayOrder::getPayOrderId, payOrderId));

        if (payOrder == null) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_SELETE);
        }
        if (!payOrder.getMchNo().equals(getCurrentMchNo())) {
            return ApiRes.fail(ApiCodeEnum.SYS_PERMISSION_ERROR);
        }
        return ApiRes.ok(payOrder);
    }
}
