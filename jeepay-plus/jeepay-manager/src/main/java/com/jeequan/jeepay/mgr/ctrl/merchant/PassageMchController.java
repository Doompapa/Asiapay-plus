package com.jeequan.jeepay.mgr.ctrl.merchant;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeequan.jeepay.core.aop.LimitRequest;
import com.jeequan.jeepay.core.aop.MethodLog;
import com.jeequan.jeepay.core.constants.ApiCodeEnum;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.*;
import com.jeequan.jeepay.core.model.ApiRes;
import com.jeequan.jeepay.mgr.ctrl.CommonCtrl;
import com.jeequan.jeepay.service.impl.*;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * 通道-商户绑定
 */
@RestController
@RequestMapping("/api/passageMchInfo")
public class PassageMchController extends CommonCtrl {

    @Autowired
    private MchPayPassageService mchPayPassageService;

    @Autowired
    private MchInfoService mchInfoService;

    @Autowired
    private AgentAccountInfoService agentAccountInfoService;

    @Autowired
    private MchProductService mchProductService;

    @Autowired
    private PayPassageService payPassageService;

    @PreAuthorize("hasAuthority('ENT_MCH_APP_EDIT')")
    @GetMapping
    public ApiRes list() {
        try {
            PayPassage queryObj = getObject(PayPassage.class);

            JSONObject paramJSON = getReqParamJSON();
            Long payPassageId = queryObj.getPayPassageId();
            String mchNo = paramJSON.getString("mchNo");
            String mchName = paramJSON.getString("mchName");
            String haveAgent = paramJSON.getString("haveAgent");
            String agentNo = paramJSON.getString("agentNo");


            PayPassage payPassage = payPassageService.queryPassageInfo(payPassageId);
            if (payPassage.getProductId() == null) {
                throw new RuntimeException("请先设置通道所属的产品");
            }

            LambdaQueryWrapper<MchPayPassage> wrapper = MchPayPassage.gw();
            wrapper.eq(MchPayPassage::getPayPassageId, payPassageId);

            IPage<MchPayPassage> pages = mchPayPassageService.page(getIPage(true), wrapper);


            List<MchPayPassage> listBlind = mchPayPassageService.list(wrapper);
            Map<String, MchPayPassage> passageMchMap = new HashMap<>();

            for (int i = 0; i < listBlind.size(); i++) {
                passageMchMap.put(listBlind.get(i).getMchNo(), listBlind.get(i));
            }

            LambdaQueryWrapper<MchInfo> mchInfoWrapper = MchInfo.gw();
            mchInfoWrapper.ne(MchInfo::getState, CS.HIDE);
            //查询参数 商户号
            if (!StringUtils.isNullOrEmpty(mchNo)) {
                mchInfoWrapper.eq(MchInfo::getMchNo, mchNo.trim());
            }
            //查询参数 商户名
            if (!StringUtils.isNullOrEmpty(mchName)) {
                mchInfoWrapper.like(MchInfo::getMchName, mchName.trim());
            }
            //是否有代理 0 无 1有
            if (!StringUtils.isNullOrEmpty(haveAgent)) {
                if (haveAgent.equals("1")) {
                    mchInfoWrapper.ne(MchInfo::getAgentNo, "");
                } else {
                    mchInfoWrapper.eq(MchInfo::getAgentNo, "");
                }
            }
            //代理号
            if (!StringUtils.isNullOrEmpty(agentNo)) {
                mchInfoWrapper.eq(MchInfo::getAgentNo, agentNo.trim());
            }

            List<MchInfo> mchList = mchInfoService.list(mchInfoWrapper);
            Map<String, AgentAccountInfo> agentAccountInfoMap = agentAccountInfoService.getAgentInfoMap();

            //产品-商户费率关系表
            Map<String, MchProduct> mchProductMap = mchProductService.GetFullMchProductMap(payPassage.getProductId());

            List<MchPayPassage> result = new ArrayList<>();
            for (int i = 0; i < mchList.size(); i++) {
                MchInfo mchInfo = mchList.get(i);
                MchPayPassage item = passageMchMap.get(mchInfo.getMchNo());
                if (item == null) {
                    item = new MchPayPassage();
                    item.setPayPassageId(payPassageId);
                    item.setMchNo(mchInfo.getMchNo());
                    item.setState(CS.NO);
                }
                item.addExt("mchName", mchInfo.getMchName());
                item.addExt("mchState", mchInfo.getState());
                String agentNoItem = mchInfo.getAgentNo();
                if (!StringUtils.isNullOrEmpty(agentNoItem)) {
                    item.addExt("agentNo", mchInfo.getAgentNo());
                    item.addExt("agentName", agentAccountInfoMap.get(mchInfo.getAgentNo()).getAgentName());
                } else {
                    item.addExt("agentNo", "");
                    item.addExt("agentName", "");
                }
                BigDecimal productRate = mchProductMap.get(mchInfo.getMchNo()).getMchRate();
                item.addExt("productRate", productRate);
                result.add(item);
            }
            pages.setRecords(result);
            pages.setTotal(result.size());
            //此处page的信息total等没有更新
            return ApiRes.page(pages);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiRes.fail(ApiCodeEnum.QUERY_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ENT_MCH_APP_EDIT')")
    @MethodLog(remark = "更新通道信息")
    @PutMapping
    @LimitRequest
    public ApiRes update() {
        MchPayPassage mchPayPassage = getObject(MchPayPassage.class);
        List<MchPayPassage> listBlind = mchPayPassageService.list(MchPayPassage.gw().eq(MchPayPassage::getMchNo, mchPayPassage.getMchNo()).eq(MchPayPassage::getPayPassageId, mchPayPassage.getPayPassageId()));
        if (listBlind != null) {
            if (listBlind.size() == 0) {
                boolean result = mchPayPassageService.saveOrUpdate(mchPayPassage);
                if (result) {
                    return ApiRes.ok();
                }
            } else if (listBlind.size() == 1) {
                mchPayPassage.setMchPayPassageId(listBlind.get(0).getMchPayPassageId());
                boolean result = mchPayPassageService.saveOrUpdate(mchPayPassage);
                if (result) {
                    return ApiRes.ok();
                }
            }

        }
        return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_UPDATE);
    }

    @PreAuthorize("hasAuthority('ENT_MCH_APP_EDIT')")
    @MethodLog(remark = "通道-商户一键全绑定")
    @RequestMapping(value = "/blindAll/{payPassageId}", method = RequestMethod.POST)
    @LimitRequest
    public ApiRes blindAll(@PathVariable("payPassageId") Long payPassageId) {
        List<MchPayPassage> listBlind = mchPayPassageService.list(MchPayPassage.gw().eq(MchPayPassage::getPayPassageId, payPassageId));
        Map<String, MchPayPassage> payPassageMchMap = new HashMap<>();

        for (int i = 0; i < listBlind.size(); i++) {
            payPassageMchMap.put(listBlind.get(i).getMchNo(), listBlind.get(i));
        }

        List<MchInfo> mchList = mchInfoService.list();
        List<MchPayPassage> result = new ArrayList<>();
        try {
            for (int i = 0; i < mchList.size(); i++) {
                MchPayPassage item = payPassageMchMap.get(mchList.get(i).getMchNo());
                if (item == null) {
                    item = new MchPayPassage();
                    item.setPayPassageId(payPassageId);
                    item.setState(CS.YES);
                    item.setMchNo(mchList.get(i).getMchNo());
                    mchPayPassageService.save(item);
                } else {
                    item.setState(CS.YES);
                    mchPayPassageService.updateById(item);
                }

            }
        } catch (Exception e) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_UPDATE);
        }


        return ApiRes.ok();
    }

    @PreAuthorize("hasAuthority('ENT_MCH_APP_EDIT')")
    @MethodLog(remark = "通道-商户一键全解绑")
    @RequestMapping(value = "/unBlindAll/{payPassageId}", method = RequestMethod.POST)
    public ApiRes unBlindAll(@PathVariable("payPassageId") Long payPassageId) {
        List<MchPayPassage> listBlind = mchPayPassageService.list(MchPayPassage.gw().eq(MchPayPassage::getPayPassageId, payPassageId));
        Map<String, MchPayPassage> payPassageMchMap = new HashMap<>();

        for (int i = 0; i < listBlind.size(); i++) {
            payPassageMchMap.put(listBlind.get(i).getMchNo(), listBlind.get(i));
        }

        List<MchInfo> mchList = mchInfoService.list();
        List<MchPayPassage> result = new ArrayList<>();

        for (int i = 0; i < mchList.size(); i++) {
            MchPayPassage item = payPassageMchMap.get(mchList.get(i).getMchNo());
            if (item == null) {
                item = new MchPayPassage();
                item.setPayPassageId(payPassageId);
                item.setState(CS.NO);
                item.setMchNo(mchList.get(i).getMchNo());
            }
            item.setState(CS.NO);
            result.add(item);
        }
        boolean isSuccess = mchPayPassageService.saveOrUpdateBatch(result);
        if (!isSuccess) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_UPDATE);
        }
        return ApiRes.ok();
    }

    @PreAuthorize("hasAuthority('ENT_MCH_APP_EDIT')")
    @MethodLog(remark = "通道-商户批量操作")
    @RequestMapping(value = "/batchSet/{payPassageId}", method = RequestMethod.POST)
    public ApiRes batchSet(@PathVariable("payPassageId") Long payPassageId) {
        JSONObject reqJson = getReqParamJSON();

        Byte state = reqJson.getByte("state");
        JSONArray selectedIds = reqJson.getJSONArray("selectedIds");
        List<String> mchNoList = new LinkedList<>();
        if (selectedIds == null || selectedIds.size() == 0) {
            return ApiRes.customFail("请先选中需要批量修改的商户");
        }

        for (int i = 0; i < selectedIds.size(); i++) {
            mchNoList.add(selectedIds.getString(i));
        }


        List<MchPayPassage> listBlind = mchPayPassageService.list(MchPayPassage.gw().eq(MchPayPassage::getPayPassageId, payPassageId));
        Map<String, MchPayPassage> payPassageMchMap = new HashMap<>();

        for (int i = 0; i < listBlind.size(); i++) {
            payPassageMchMap.put(listBlind.get(i).getMchNo(), listBlind.get(i));
        }


        List<MchPayPassage> result = new ArrayList<>();

        for (int i = 0; i < mchNoList.size(); i++) {
            MchPayPassage item = payPassageMchMap.get(mchNoList.get(i));
            if (item == null) {
                item = new MchPayPassage();
                item.setPayPassageId(payPassageId);
                item.setState(state);
                item.setMchNo(mchNoList.get(i));
            }
            item.setState(state);
            result.add(item);
        }
        boolean isSuccess = mchPayPassageService.saveOrUpdateBatch(result);
        if (!isSuccess) {
            return ApiRes.fail(ApiCodeEnum.SYS_OPERATION_FAIL_UPDATE);
        }
        return ApiRes.ok();
    }

}