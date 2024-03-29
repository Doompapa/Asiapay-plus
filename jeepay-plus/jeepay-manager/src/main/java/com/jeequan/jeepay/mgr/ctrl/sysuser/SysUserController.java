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
package com.jeequan.jeepay.mgr.ctrl.sysuser;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeequan.jeepay.core.aop.LimitRequest;
import com.jeequan.jeepay.core.aop.MethodLog;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.SysUser;
import com.jeequan.jeepay.core.exception.BizException;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.mgr.service.AuthService;
import com.jeequan.jeepay.service.impl.SysUserAuthService;
import com.jeequan.jeepay.service.impl.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/*
 * 操作员列表
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 17:13
 */
@RestController
@RequestMapping("api/sysUsers")
public class SysUserController extends CommonCtrl {


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserAuthService sysUserAuthService;
    @Autowired
    private AuthService authService;

    /**
     * list
     */
    @PreAuthorize("hasAuthority( 'ENT_UR_USER_LIST' )")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiRes list() {

        SysUser queryObject = getObject(SysUser.class);

        LambdaQueryWrapper<SysUser> condition = SysUser.gw();
        condition.eq(SysUser::getSysType, CS.SYS_TYPE.MGR);

        if (StringUtils.isNotEmpty(queryObject.getLoginUsername())) {
            condition.like(SysUser::getLoginUsername, queryObject.getLoginUsername().trim());
        }

        if (queryObject.getSysUserId() != null) {
            condition.eq(SysUser::getSysUserId, queryObject.getSysUserId());
        }

        condition.orderByDesc(SysUser::getCreatedAt); //时间： 降序

        IPage<SysUser> pages = sysUserService.page(getIPage(), condition);

        return ApiRes.page(pages);
    }


    /**
     * detail
     */
    @PreAuthorize("hasAuthority( 'ENT_UR_USER_EDIT' )")
    @RequestMapping(value = "/{recordId}", method = RequestMethod.GET)
    public ApiRes detail(@PathVariable("recordId") Integer recordId) {
        return ApiRes.ok(sysUserService.getById(recordId));
    }

    /**
     * add
     */
    @PreAuthorize("hasAuthority( 'ENT_UR_USER_ADD' )")
    @MethodLog(remark = "添加管理员")
    @RequestMapping(value = "", method = RequestMethod.POST)
    @LimitRequest
    public ApiRes add() {
        SysUser sysUser = getObject(SysUser.class);
        sysUser.setBelongInfoId("0");

        sysUserService.addSysUser(sysUser, CS.SYS_TYPE.MGR);
        return ApiRes.ok();
    }


    /**
     * 修改操作员 登录认证信息
     */
//	@RequestMapping(value="/modifyPwd", method = RequestMethod.PUT)
//	@MethodLog(remark = "修改操作员密码")
//    public ApiRes authInfo() {
//
//        Long opSysUserId = getValLongRequired("recordId");   //操作员ID
//
//        //更改密码， 验证当前用户信息
//        String currentUserPwd = getValStringRequired("originalPwd"); //当前用户登录密码
//        //验证当前密码是否正确
//        if (!sysUserAuthService.validateCurrentUserPwd(currentUserPwd)) {
//            throw new BizException("原密码验证失败！");
//        }
//
//        String opUserPwd = getValStringRequired("confirmPwd");
//
//        // 验证原密码与新密码是否相同
//        if (opUserPwd.equals(currentUserPwd)) {
//            throw new BizException("新密码与原密码相同！");
//        }
//
//        sysUserAuthService.resetAuthInfo(opSysUserId, null, null, opUserPwd, CS.SYS_TYPE.MGR);
//        return ApiRes.ok();
//    }


    /**
     * update
     */
    @PreAuthorize("hasAuthority( 'ENT_UR_USER_EDIT' )")
    @RequestMapping(value = "/{recordId}", method = RequestMethod.PUT)
    @MethodLog(remark = "修改操作员信息")
    @LimitRequest
    public ApiRes update(@PathVariable("recordId") Long recordId) {
        SysUser sysUser = getObject(SysUser.class);
        sysUser.setSysUserId(recordId);
        //判断是否自己禁用自己
        if (recordId.equals(getCurrentUser().getSysUser().getSysUserId()) && sysUser.getState() != null && sysUser.getState() == CS.PUB_DISABLE) {
            throw new BizException("系统不允许禁用当前登陆用户！");
        }
        //判断是否重置密码
        Boolean resetPass = getReqParamJSON().getBoolean("resetPass");
        if (resetPass != null && resetPass) {
            String updatePwd = getReqParamJSON().getBoolean("defaultPass") == false ? Base64.decodeStr(getValStringRequired("confirmPwd")) : CS.DEFAULT_PWD;
            sysUserAuthService.resetAuthInfoAndGoogle(recordId, updatePwd, CS.SYS_TYPE.MGR);
        }

        sysUserService.updateSysUser(sysUser);
        // 删除用户redis缓存信息
        authService.delAuthentication(Arrays.asList(recordId));

        //如果用户被禁用，需要更新redis数据
        if (sysUser.getState() != null && sysUser.getState() == CS.PUB_DISABLE) {
            authService.refAuthentication(Arrays.asList(recordId));
        }
        return ApiRes.ok();
    }

    /**
     * delete
     */
    @PreAuthorize("hasAuthority( 'ENT_UR_USER_DELETE' )")
    @RequestMapping(value = "/{recordId}", method = RequestMethod.DELETE)
    @MethodLog(remark = "删除操作员信息")
    @LimitRequest
    public ApiRes delete(@PathVariable("recordId") Long recordId) {
        //查询该操作员信息
        SysUser sysUser = sysUserService.getById(recordId);
        if (sysUser == null) {
            throw new BizException("该操作员不存在！");
        }

        //判断是否自己删除自己
        if (recordId.equals(getCurrentUser().getSysUser().getSysUserId())) {
            throw new BizException("系统不允许删除当前登陆用户！");
        }
        // 删除用户
        sysUserService.removeUser(sysUser, CS.SYS_TYPE.MGR);

        //如果用户被删除，需要更新redis数据
        authService.refAuthentication(Arrays.asList(recordId));

        return ApiRes.ok();
    }

}
