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
package com.jeequan.jeepay.mgr.ctrl.payconfig;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeequan.jeepay.core.aop.LimitRequest;
import com.jeequan.jeepay.core.aop.MethodLog;
import com.jeequan.jeepay.core.constants.ApiCodeEnum;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.*;
import com.jeequan.jeepay.core.exception.BizException;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.service.impl.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 支付产品管理类
 *
 * @author zhuxiao
 * @site https://www.jeequan.com
 * @date 2021-04-27 15:50
 */
@RestController
@RequestMapping("api/payWays")
public class PayWayController extends CommonCtrl {

    @Autowired
    private ProductService productService;
    @Autowired
    MchPayPassageService mchPayPassageService;
    @Autowired
    PayOrderService payOrderService;
    @Autowired
    PayPassageService passageService;
    @Autowired
    MchProductService mchProductService;

    /**
     * @Author: ZhuXiao
     * @Description: list
     * @Date: 15:52 2021/4/27
     */
    @PreAuthorize("hasAnyAuthority('ENT_PC_WAY_LIST', 'ENT_PAY_ORDER_SEARCH_PAY_WAY')")
    @GetMapping
    public ApiRes list() {
        try {
            Product queryObject = getObject(Product.class);
            LambdaQueryWrapper<Product> condition = Product.gw();
            condition.ne(Product::getState, CS.HIDE);
            if (queryObject.getProductId() != null) {
                condition.like(Product::getProductId, queryObject.getProductId());
            }
            if (StringUtils.isNotEmpty(queryObject.getProductName())) {
                condition.like(Product::getProductName, queryObject.getProductName().trim());
            }
            if (queryObject.getState() != null) {
                condition.like(Product::getState, queryObject.getState());
            }
            if (queryObject.getLimitState() != null) {
                condition.like(Product::getLimitState, queryObject.getLimitState());
            }
            condition.orderByAsc(Product::getProductId);

            IPage<Product> pages = productService.page(getIPage(true), condition);

            return ApiRes.page(pages);
        } catch (Exception e) {
            return ApiRes.fail(ApiCodeEnum.QUERY_ERROR);
        }
    }


    /**
     * @Author: ZhuXiao
     * @Description: detail
     * @Date: 15:52 2021/4/27
     */
    @PreAuthorize("hasAnyAuthority('ENT_PC_WAY_VIEW', 'ENT_PC_WAY_EDIT')")
    @GetMapping("/{wayCode}")
    public ApiRes detail(@PathVariable("wayCode") Long wayCode) {
        return ApiRes.ok(productService.getById(wayCode));
    }

    /**
     * @Author: ZhuXiao
     * @Description: add
     * @Date: 15:52 2021/4/27
     */
    @PreAuthorize("hasAuthority('ENT_PC_WAY_ADD')")
    @PostMapping
    @MethodLog(remark = "新增支付产品")
    @LimitRequest
    public ApiRes add() {
        Product product = getObject(Product.class);

        if (productService.count(Product.gw().eq(Product::getProductId, product.getProductId())) > 0) {
            throw new BizException("支付产品ID已存在");
        }
        product.setProductId(product.getProductId());
        product.setUpdatedAt(new Date());

        boolean result = productService.save(product);
        if (!result) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_CREATE);
        }
        return ApiRes.ok();
    }

    /**
     * @Author: ZhuXiao
     * @Description: update
     * @Date: 15:52 2021/4/27
     */
    @PreAuthorize("hasAuthority('ENT_PC_WAY_EDIT')")
    @PutMapping("/{wayCode}")
    @MethodLog(remark = "更新支付产品")
    @LimitRequest
    public ApiRes update(@PathVariable("wayCode") Long wayCode) {
        Product product = getObject(Product.class);
        product.setProductId(wayCode);
        product.setUpdatedAt(new Date());
        boolean result = productService.saveOrUpdate(product);
        if (!result) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_UPDATE);
        }
        return ApiRes.ok();
    }

    /**
     * @Author: ZhuXiao
     * @Description: delete
     * @Date: 15:52 2021/4/27
     */
    @PreAuthorize("hasAuthority('ENT_PC_WAY_DEL')")
    @DeleteMapping("/{productId}")
    @MethodLog(remark = "删除支付产品")
    @LimitRequest
    public ApiRes delete(@PathVariable("productId") Long productId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setState(CS.HIDE);
        boolean result = productService.updateById(product);

        if (!result) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_DELETE);
        } else {
            //移除绑定产品
            mchProductService.remove(MchProduct.gw().eq(MchProduct::getProductId, productId));
        }
        return ApiRes.ok();
    }


}
