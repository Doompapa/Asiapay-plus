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
package com.jeequan.jeepay.pay.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.ctrls.AbstractCtrl;
import com.jeequan.jeepay.core.entity.MchInfo;
import com.jeequan.jeepay.core.exception.BizException;
import com.jeequan.jeepay.core.utils.JeepayKit;
import com.jeequan.jeepay.pay.rqrs.AbstractMchAppRQ;
import com.jeequan.jeepay.pay.rqrs.AbstractRQ;
import com.jeequan.jeepay.pay.service.ConfigContextQueryService;
import com.jeequan.jeepay.pay.service.ValidateService;
import com.jeequan.jeepay.pay.util.XssFilterUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * api 抽象接口， 公共函数
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 17:28
 */
public abstract class ApiController extends AbstractCtrl {

    @Autowired
    private ValidateService validateService;
    @Autowired
    private ConfigContextQueryService configContextQueryService;


    /**
     * 获取请求参数并转换为对象，通用验证
     **/
    protected <T extends AbstractRQ> T getRQ(Class<T> cls) {

        T bizRQ = getObject(cls);

        // [1]. 验证通用字段规则
        validateService.validate(bizRQ);

        return bizRQ;
    }


    /**
     * 获取请求参数并转换为对象，商户通用验证
     **/
    protected <T extends AbstractRQ> T getRQByWithMchSign(Class<T> cls) {

        //获取请求RQ, and 通用验证
        T bizRQ = getRQ(cls);

        AbstractMchAppRQ abstractMchAppRQ = (AbstractMchAppRQ) bizRQ;

        //业务校验， 包括： 验签， 商户状态是否可用， 是否支持该支付方式下单等。
        String mchNo = abstractMchAppRQ.getMchNo();
        String sign = bizRQ.getSign();

        if (StringUtils.isAnyBlank(mchNo, sign)) {
            throw new BizException("参数有误！");
        }

        MchInfo mchInfo = configContextQueryService.queryMchInfo(mchNo);

        if (mchInfo == null || mchInfo.getState() != MchInfo.STATE_OPEN) {
            throw new BizException("商户不存或商户状态异常");
        }

        // 验签
        String appSecret = mchInfo.getSecret();

        // 转换为 JSON
        JSONObject bizReqJSON = (JSONObject) JSONObject.toJSON(bizRQ);
        bizReqJSON.remove("sign");
        if (!sign.equalsIgnoreCase(JeepayKit.getSign(bizReqJSON, appSecret))) {
            throw new BizException("验签失败");
        }


        String params = bizReqJSON.toString();
        if (StringUtils.isNotBlank(params)) {
            if (XssFilterUtil.isHandXss(request.getRequestURI())) {
                String cleanValue = XssFilterUtil.clean(params);
                if (!cleanValue.equals(params)) {
                    logger.error("参数中含有XSS符号!,params={}" + params);
                    throw new BizException("请求参数异常");
                }
            }
        }

        return bizRQ;
    }

}
