package com.jeequan.jeepay.mgr.task;

import com.alibaba.fastjson.JSONObject;
import com.jeequan.jeepay.core.cache.RedisUtil;
import com.jeequan.jeepay.core.constants.CS;
import com.jeequan.jeepay.core.entity.*;
import com.jeequan.jeepay.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class BalanceTask {

    @Autowired
    private MchInfoService mchInfoService;

    @Autowired
    private AgentAccountInfoService agentAccountInfoService;

    @Autowired
    private PayPassageService payPassageService;

    @Autowired
    private MchHistoryService mchHistoryService;

    @Autowired
    private AgentAccountHistoryService agentAccountHistoryService;

    @Autowired
    private PassageTransactionHistoryService passageTransactionHistoryService;

    /**
     * 每5秒监控redis中是否有新的需要处理的余额操作
     */
    @Scheduled(fixedRate = 6000) // 每6秒执行一次 6000
    public void start() {
        List<PayOrder> list = PopDataListFromCache();
        if (list.size() != 0) {
            Map<String, Long> mchBalanceChange = new ConcurrentHashMap<>();
            Map<String, Long> agentBalanceChange = new ConcurrentHashMap<>();
            Map<Long, Long> payPassageBalanceChange = new ConcurrentHashMap<>();
            Map<Long, Long> payPassageQuotaChange = new ConcurrentHashMap<>();

            List<MchHistory> mchHistoryList = new ArrayList<>();
            List<AgentAccountHistory> agentAccountHistoryList = new ArrayList<>();
            List<PassageTransactionHistory> passageTransactionHistoryList = new ArrayList<>();

            //通道余额更新-检查授信
            for (int i = 0; i < list.size(); i++) {
                PayOrder payOrder = list.get(i);
                String mchNo = payOrder.getMchNo();
                MchInfo mchInfo = mchInfoService.queryMchInfo(mchNo);

                if (payOrder.getState() == PayOrder.STATE_SUCCESS) {
                    //正常回调订单
                    //商户资金流水、余额
                    Long mchChangAmount = payOrder.getAmount() - payOrder.getMchFeeAmount();
                    Long mchBeforeBalance = mchInfo.getBalance();

                    //计算并存储商户余额变动 是否有余额变动缓存
                    if (mchBalanceChange.containsKey(mchNo)) {
                        //加上之前变动的金额
                        mchBeforeBalance += mchBalanceChange.get(mchNo);

                        Long tempMchAmount = mchBalanceChange.get(mchNo) + mchChangAmount;
                        mchBalanceChange.replace(mchNo, tempMchAmount);
                    } else {
                        mchBalanceChange.put(mchNo, mchChangAmount);
                    }
                    Long mchAfterBalance = mchBeforeBalance + mchChangAmount;
                    mchHistoryList.add(AddMchOrderSuccessHistory(payOrder, mchInfo, mchBeforeBalance, mchAfterBalance, mchChangAmount));
                    //代理资金流水、余额
                    //区分商户代理、通道代理

                    String agentNoPassage = payOrder.getAgentNoPassage();
                    String agentNo = payOrder.getAgentNo();
                    //相同,合并
                    if (StringUtils.isNotEmpty(agentNoPassage) && StringUtils.isNotEmpty(agentNo) && agentNoPassage.equals(agentNo)) {
                        AgentAccountInfo agentMchInfo = agentAccountInfoService.queryAgentInfo(agentNo);

                        Long changeAgentAmount = payOrder.getAgentPassageFee() + payOrder.getAgentFeeAmount();
                        Long agentBeforeBalance = agentMchInfo.getBalance();

                        //计算并存储代理余额变动
                        if (agentBalanceChange.containsKey(agentNo)) {
                            agentBeforeBalance += agentBalanceChange.get(agentNo);

                            Long tempAgentAmount = agentBalanceChange.get(agentNo) + changeAgentAmount;
                            agentBalanceChange.replace(agentNo, tempAgentAmount);
                        } else {
                            agentBalanceChange.put(agentNo, changeAgentAmount);
                        }
                        Long agentAfterBalance = agentBeforeBalance + changeAgentAmount;
                        agentAccountHistoryList.add(AddAgentOrderSuccessHistory(payOrder, agentMchInfo, agentBeforeBalance, agentAfterBalance, changeAgentAmount));
                    } else {
                        //商户代理
                        if (StringUtils.isNotEmpty(agentNo)) {
                            AgentAccountInfo agentMchInfo = agentAccountInfoService.queryAgentInfo(agentNo);

                            Long agentBeforeBalance = agentMchInfo.getBalance();
                            Long changeAgentAmount = payOrder.getAgentFeeAmount();

                            //计算并存储代理余额变动
                            if (agentBalanceChange.containsKey(agentNo)) {
                                agentBeforeBalance += agentBalanceChange.get(agentNo);

                                Long tempAgentAmount = agentBalanceChange.get(agentNo) + changeAgentAmount;
                                agentBalanceChange.replace(agentNo, tempAgentAmount);
                            } else {
                                agentBalanceChange.put(agentNo, changeAgentAmount);
                            }
                            Long agentAfterBalance = agentBeforeBalance + changeAgentAmount;
                            agentAccountHistoryList.add(AddAgentOrderSuccessHistory(payOrder, agentMchInfo, agentBeforeBalance, agentAfterBalance, changeAgentAmount));
                        }

                        //通道代理
                        if (StringUtils.isNotEmpty(agentNoPassage)) {
                            AgentAccountInfo agentPassageInfo = agentAccountInfoService.queryAgentInfo(agentNoPassage);
                            Long changeAgentAmount = payOrder.getAgentPassageFee();

                            Long agentBeforeBalance = agentPassageInfo.getBalance();

                            //计算并存储代理余额变动
                            if (agentBalanceChange.containsKey(agentNoPassage)) {
                                agentBeforeBalance += agentBalanceChange.get(agentNoPassage);
                                Long tempAgentAmount = agentBalanceChange.get(agentNoPassage) + changeAgentAmount;
                                agentBalanceChange.replace(agentNoPassage, tempAgentAmount);
                            } else {
                                agentBalanceChange.put(agentNoPassage, changeAgentAmount);
                            }
                            Long agentAfterBalance = agentBeforeBalance + changeAgentAmount;
                            agentAccountHistoryList.add(AddAgentOrderSuccessHistory(payOrder, agentPassageInfo, agentBeforeBalance, agentAfterBalance, changeAgentAmount));
                        }
                    }

                    //通道余额、授信
                    //通道入账余额= 订单金额-通道成本-通道代理成本
                    Long passageChangeAmount = payOrder.getAmount() - payOrder.getPassageFeeAmount() - payOrder.getAgentPassageFee();
                    PayPassage payPassage = payPassageService.queryPassageInfo(payOrder.getPassageId());
                    //updatePassageInfo 这个方法中设置了余额会变为null,提前取出
                    Long passageBeforeBalance = payPassage.getBalance();

                    //额度限制打开的情况
                    if (payPassage.getQuotaLimitState() == CS.YES) {
                        if (payPassageQuotaChange.containsKey(payOrder.getPassageId())) {
                            Long tempPassageAmount = payPassageQuotaChange.get(payOrder.getPassageId()) - passageChangeAmount;
                            payPassageQuotaChange.replace(payOrder.getPassageId(), tempPassageAmount);
                        } else {
                            payPassageQuotaChange.put(payOrder.getPassageId(), -passageChangeAmount);
                        }
                        //授信是否小于change
                        Long afterQuota = payPassage.getQuota() + payPassageQuotaChange.get(payOrder.getPassageId());
                        if (afterQuota.longValue() <= 0) {
                            payPassage.setState(CS.NO);
                            payPassageService.updatePassageInfo(payPassage);
                        }
                    }

                    //通道余额处理
                    //为何空？

                    if (payPassageBalanceChange.containsKey(payOrder.getPassageId())) {
                        passageBeforeBalance += payPassageBalanceChange.get(payOrder.getPassageId());

                        Long tempPassageAmount = payPassageBalanceChange.get(payOrder.getPassageId()) + passageChangeAmount;
                        payPassageBalanceChange.replace(payOrder.getPassageId(), tempPassageAmount);
                    } else {
                        payPassageBalanceChange.put(payOrder.getPassageId(), passageChangeAmount);
                    }
                    Long passageAfterBalance = passageBeforeBalance + passageChangeAmount;
                    //保存流水入通道流水记录
                    //插入通道流水记录
                    passageTransactionHistoryList.add(AddPassageOrderSuccessHistory(payOrder, payPassage, passageBeforeBalance, passageAfterBalance, passageChangeAmount));

                } else {
                    log.error("BalanceTask 余额统计订单状态错误 订单号[{}] , {}", payOrder.getPayOrderId(), JSONObject.toJSONString(payOrder));
                }
            }
            //统一更新余额
            //商户
            mchBalanceChange.forEach((mchNo, newValue) -> {
                mchInfoService.updateBalance(mchNo, newValue);
            });
            //代理
            agentBalanceChange.forEach((agentNo, newValue) -> {
                agentAccountInfoService.updateBalance(agentNo, newValue);
            });
            //通道余额
            payPassageBalanceChange.forEach((payPassageId, newValue) -> {
                payPassageService.updateBalance(payPassageId, newValue);
            });
            //通道授信
            payPassageQuotaChange.forEach((payPassageId, newValue) -> {
                payPassageService.updateQuota(payPassageId, newValue);
            });

            //统一更新流水
            mchHistoryService.saveBatch(mchHistoryList);
            agentAccountHistoryService.saveBatch(agentAccountHistoryList);
            passageTransactionHistoryService.saveBatch(passageTransactionHistoryList);
            log.info("流水、入账处理完毕,处理数量{}", list.size());
        }

    }

    /**
     * 订单成功商户流水记录
     *
     * @param payOrder
     * @param mchInfo
     * @param mchBeforeBalance
     * @param mchAfterBalance
     * @param mchChangAmount
     */
    private MchHistory AddMchOrderSuccessHistory(PayOrder payOrder, MchInfo mchInfo, Long mchBeforeBalance, Long mchAfterBalance, Long mchChangAmount) {
        //是否有代理
        String agentNo = payOrder.getAgentNo();
        AgentAccountInfo agentMchInfo = new AgentAccountInfo();
        agentMchInfo.setAgentName("");
        agentMchInfo.setAgentNo(agentNo);
        if (StringUtils.isNotEmpty(agentNo)) {
            agentMchInfo = agentAccountInfoService.queryAgentInfo(agentNo);
        }

        MchHistory mchHistory = new MchHistory();
        mchHistory.setMchNo(mchInfo.getMchNo());
        mchHistory.setMchName(mchInfo.getMchName());

        mchHistory.setAmount(mchChangAmount);
        mchHistory.setPayOrderAmount(payOrder.getAmount());
        mchHistory.setBeforeBalance(mchBeforeBalance);
        mchHistory.setAfterBalance(mchAfterBalance);
        mchHistory.setMchRateAmount(payOrder.getMchFeeAmount());
        mchHistory.setFundDirection(CS.FUND_DIRECTION_INCREASE);
        mchHistory.setBizType(CS.BIZ_TYPE_PAY_OR_INCOME);
        mchHistory.setPayOrderId(payOrder.getPayOrderId());
        mchHistory.setPassageOrderId(payOrder.getPassageOrderNo());
        mchHistory.setPlatIncome(CalPlatProfit(payOrder));
        mchHistory.setAgentIncome(payOrder.getAgentFeeAmount());
        mchHistory.setAgentNo(payOrder.getAgentNo());
        mchHistory.setAgentName(agentMchInfo.getAgentName());
        mchHistory.setAgentIncome(payOrder.getAgentFeeAmount());
        mchHistory.setMchOrderNo(payOrder.getMchOrderNo());

        mchHistory.setCreatedAt(new Date());
        return mchHistory;
    }

    /**
     * 增加一条代理记录
     *
     * @param payOrder
     * @param agentInfo
     * @param beforeBalance
     * @param afterBalance
     * @param changeAgentAmount
     */
    private AgentAccountHistory AddAgentOrderSuccessHistory(PayOrder payOrder, AgentAccountInfo agentInfo, Long beforeBalance, Long afterBalance, Long changeAgentAmount) {
        //代理资金流水、余额
        //区分商户代理、通道代理
        AgentAccountHistory agentAccountHistory = new AgentAccountHistory();
        agentAccountHistory.setAgentNo(agentInfo.getAgentNo());
        agentAccountHistory.setAgentName(agentInfo.getAgentName());
        agentAccountHistory.setAmount(changeAgentAmount);
        agentAccountHistory.setBeforeBalance(beforeBalance);
        agentAccountHistory.setAfterBalance(afterBalance);
        agentAccountHistory.setFundDirection(CS.FUND_DIRECTION_INCREASE);
        agentAccountHistory.setBizType(CS.BIZ_TYPE_PAY_OR_INCOME);
        agentAccountHistory.setPayOrderId(payOrder.getPayOrderId());
        agentAccountHistory.setPayOrderAmount(payOrder.getAmount());

        agentAccountHistory.setCreatedAt(new Date());
        return agentAccountHistory;
    }

    private PassageTransactionHistory AddPassageOrderSuccessHistory(PayOrder payOrder, PayPassage payPassage, Long beforeBalance, Long afterBalance, Long passageChangeAmount) {
        //通道资金流水
        PassageTransactionHistory passageTransactionHistory = new PassageTransactionHistory();
        passageTransactionHistory.setAmount(passageChangeAmount);
        passageTransactionHistory.setPayPassageId(payPassage.getPayPassageId());
        passageTransactionHistory.setPayPassageName(payPassage.getPayPassageName());
        passageTransactionHistory.setPayOrderId(payOrder.getPayOrderId());
        passageTransactionHistory.setCreatedUid(0L);
        passageTransactionHistory.setCreatedLoginName("");
        passageTransactionHistory.setBeforeBalance(beforeBalance);
        passageTransactionHistory.setAfterBalance(afterBalance);
        passageTransactionHistory.setFundDirection(passageChangeAmount >= 0 ? CS.FUND_DIRECTION_INCREASE : CS.FUND_DIRECTION_REDUCE);
        passageTransactionHistory.setBizType(PassageTransactionHistory.BIZ_TYPE_ORDER);
        passageTransactionHistory.setRemark("");

        passageTransactionHistory.setCreatedAt(new Date());
        return passageTransactionHistory;
    }

    /**
     * 获取入库的订单
     *
     * @return
     */
    public List<PayOrder> PopDataListFromCache() {

        List<PayOrder> list = new ArrayList<>();
        Long cacheSize = RedisUtil.getQueueLength(CS.CHANGE_BALANCE_REDIS_SUFFIX);
        if (cacheSize.intValue() == 0) {
            return list;
        }
        log.info("读取入库资金流水操作缓存,当前数量{}", cacheSize);
        int count = cacheSize.intValue();

        for (int index = 0; index < count; index++) {
            list.add(RedisUtil.removeFromQueue(CS.CHANGE_BALANCE_REDIS_SUFFIX, PayOrder.class));
        }
        log.info("获取入库资金流水操作缓存-{},剩余{}", count, RedisUtil.getQueueLength(CS.CHANGE_BALANCE_REDIS_SUFFIX));
        return list;
    }

    private Long CalPlatProfit(PayOrder payOrder) {
        return payOrder.getMchFeeAmount() - payOrder.getPassageFeeAmount() - payOrder.getAgentFeeAmount() - payOrder.getAgentPassageFee();
    }
}
