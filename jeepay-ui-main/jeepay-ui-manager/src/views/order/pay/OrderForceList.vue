<template>
  <page-header-wrapper>
    <a-card>
      <div class="table-page-search-wrapper" @keydown.enter="queryFunc">
        <a-form layout="inline" class="table-head-ground">
          <div class="table-layer">
            <a-form-item label="" class="table-head-layout" style="max-width:350px;min-width:300px">
              <a-range-picker
                  @change="onChange"
                  :show-time="{ format: 'HH:mm:ss' }"
                  format="YYYY-MM-DD HH:mm:ss"
                  :disabled-date="disabledDate"
                  v-model="selectedRange"
                  :ranges="ranges"
              >
                <a-icon slot="suffixIcon" type="sync" />
              </a-range-picker>
            </a-form-item>
<!--            <jeepay-text-up :placeholder="'支付订单号/商户订单号/渠道订单号'" :msg="searchData.unionOrderId" v-model="searchData.unionOrderId" />-->
            <jeepay-text-up :placeholder="'支付订单号'" :msg="searchData.payOrderId" v-model="searchData.payOrderId" />
            <jeepay-text-up :placeholder="'商户订单号'" :msg="searchData.mchOrderNo" v-model="searchData.mchOrderNo" />
            <jeepay-text-up :placeholder="'通道订单号'" :msg="searchData.passageOrderNo" v-model="searchData.passageOrderNo" />
            <jeepay-text-up :placeholder="'商户号'" :msg="searchData.mchNo" v-model="searchData.mchNo" />
            <jeepay-text-up :placeholder="'通道ID'" :msg="searchData.passageId" v-model="searchData.passageId" />
            <span class="table-page-search-submitButtons">
              <a-button type="primary" icon="search" @click="queryFunc" :loading="btnLoading">搜索</a-button>
              <a-button style="margin-left: 8px" icon="reload" @click="resetSearch">重置</a-button>
            </span>
          </div>
        </a-form>
      </div>
      <!-- 统计部分 -->
      <div style="background-color: #fafafa;padding-top: 10px;padding-bottom: 10px;border-bottom: 1px solid rgba(179,179,179,0.4)">
        <a-row style="padding-left: 15px;padding-right: 15px;">
          <a-col class="stat-col bg-color-1" :span="4">
            <span class="title">订单金额</span>
            <b style="color: #DB4B4B;">{{ (realTimeStatData.successAmount/100).toFixed(2) }}</b>
            <span class="sub-content">总：{{ (realTimeStatData.totalAmount/100).toFixed(2) }}</span>
            <img src="~@/assets/dashboard/icon_zuorichengg.png">
          </a-col>
          <a-col class="stat-col bg-color-2" :span="4" :offset="1">
            <span class="title">订单数</span>
            <b style="color: #FA9D2A;">{{ realTimeStatData.successCount }}</b>
            <span class="sub-content">总：{{ realTimeStatData.totalCount }}</span>
            <img src="~@/assets/dashboard/orange-icon.png">
          </a-col>
          <a-col class="stat-col bg-color-3" :span="4" :offset="1">
            <span class="title">商户入账</span>
            <b style="color: #2F61DC;">{{ (realTimeStatData.totalMchIncome/100).toFixed(2) }}</b>
            <img src="~@/assets/dashboard/icon_jinrichengg.png">
          </a-col>
          <a-col class="stat-col bg-color-4" :span="4" :offset="1">
            <span class="title">平台利润</span>
            <b style="color: #864FE1;">{{ (realTimeStatData.totalIncome/100).toFixed(2) }}</b>
            <img src="~@/assets/dashboard/icon_dailishuliang.png">
          </a-col>
          <a-col class="stat-col bg-color-5" :span="4" :offset="1">
            <span class="title">成功率</span>
            <b style="color: #4BD884;">{{ (realTimeStatData.successCount===0?0:(realTimeStatData.successCount / realTimeStatData.totalCount * 100)).toFixed(2) }}%</b>
            <img src="~@/assets/dashboard/icon_shanghushuliang.png">
          </a-col>
        </a-row>
      </div>

      <!-- 列表渲染 -->
      <JeepayTable
          @btnLoadClose="btnLoading=false"
          ref="infoTable"
          :initData="false"
          :reqTableDataFunc="reqTableDataFunc"
          :tableColumns="tableColumns"
          :searchData="searchData"
          :pageSize="50"
          rowKey="payOrderId"
      >
        <template slot="mchSlot" slot-scope="{record}">
          <div class="mch-name">
            <p><span style="color: #007EFF;font-size: 14px">{{ record.mchNo }}</span></p>
            <p style="margin-bottom: 0"><span style="font-size: 16px;text-align: left">{{ record.mchName }}</span></p>
          </div>
        </template> <!-- 商户信息插槽 -->
        <template slot="amountSlot" slot-scope="{record}"><b>￥{{ record.amount/100 }}</b></template> <!-- 订单金额插槽 -->
        <template slot="beforeStateSlot" slot-scope="{record}">
          <a-tag
              :key="record.forceChangeBeforeState"
              :color="record.forceChangeBeforeState === 0?'blue':record.forceChangeBeforeState === 1?'orange':record.forceChangeBeforeState === 2?'#4BD884':record.forceChangeBeforeState === 6?'':'#F03B44'"
          >
            {{ record.forceChangeBeforeState === 0?'订单生成':record.forceChangeBeforeState === 1?'支付中':record.forceChangeBeforeState === 2?'支付成功':record.forceChangeBeforeState === 3?'支付失败':record.forceChangeBeforeState === 4?'已撤销':record.forceChangeBeforeState === 5?'测试冲正':record.forceChangeBeforeState === 6?'订单关闭':'未知' }}
          </a-tag>
        </template>
        <template slot="stateSlot" slot-scope="{record}">
          <a-tag
              :key="record.state"
              :color="record.state === 0?'blue':record.state === 1?'orange':record.state === 2?'#4BD884':record.state === 6?'':'#F03B44'"
          >
            {{ record.state === 0?'订单生成':record.state === 1?'支付中':record.state === 2?'支付成功':record.state === 3?'支付失败':record.state === 4?'已撤销':record.state === 5?'测试冲正':record.state === 6?'订单关闭':'未知' }}
          </a-tag>
        </template>
        <template slot="forceChangeStateSlot" slot-scope="{record}">
          <a-tag :color="record.forceChangeState != undefined && record.forceChangeState === 1?'#007EFF':''">
            {{ record.forceChangeState != undefined && record.forceChangeState === 1?'是':'否'}}
          </a-tag>
        </template>
        <template slot="forceChangeNameSlot" slot-scope="{record}">
          <a-tag color="blue">
            {{ record.forceChangeLoginName }}
          </a-tag>
        </template>
        <template slot="passageSlot" slot-scope="{record}">
          <span style="color: #1A79FF;">[{{ record.passageId }}]</span>&nbsp;<span>{{ record.passageName }}</span>
        </template> <!-- 商户信息插槽 -->
        <template slot="notifySlot" slot-scope="{record}">
          <a-badge :status="record.notifyState === 1?'processing':'error'" :text="record.notifyState === 1?'已发送':'未发送'" />
        </template>
        <template slot="productSlot" slot-scope="{record}">
          <span style="color: #1A79FF">[{{ record.productId }}]</span><span>{{ record.productName }}</span>
        </template> <!-- 商户信息插槽 -->
        <template slot="orderSlot" slot-scope="{record}">
          <div class="order-list">
            <p><span style="color:#007EFF;background:#DFEFFF">支付单号</span><b>{{ record.payOrderId }}</b></p>
            <p style="margin-bottom: 0">
              <span style="color:#FA9D2A;background:#FFEDD6">商户单号</span>
              <a-tooltip placement="bottom" style="font-weight: normal;" v-if="record.mchOrderNo.length > record.payOrderId.length">
                <template slot="title">
                  <span>{{ record.mchOrderNo }}</span>
                </template>
                {{ changeStr2ellipsis(record.mchOrderNo, record.payOrderId.length) }}
              </a-tooltip>
              <span style="font-weight: normal;" v-else>{{ record.mchOrderNo }}</span>
            </p>
          </div>
        </template>
        <template slot="opSlot" slot-scope="{record}">  <!-- 操作列插槽 -->
          <JeepayTableColumns>
            <a-button type="link" v-if="$access('ENT_PAY_ORDER_VIEW')" @click="detailFunc(record.payOrderId)">查看详情</a-button>
          </JeepayTableColumns>
        </template>
      </JeepayTable>
    </a-card>
    <!-- 日志详情抽屉 -->
    <template>
      <a-drawer
          width="50%"
          placement="right"
          :closable="true"
          :visible="visible"
          :title="visible === true? '订单详情':''"
          @close="onClose"
      >
        <a-row justify="space-between" type="flex">
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="商户号">
                <b>{{ detailData.mchNo }}</b>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="商户名称">
                {{ detailData.mchName }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="24">
            <a-descriptions>
              <a-descriptions-item label="支付订单号">
                <a-tag color="purple">
                  {{ detailData.payOrderId }}
                </a-tag>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="24">
            <a-descriptions>
              <a-descriptions-item label="商户订单号">
                {{ detailData.mchOrderNo }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="支付金额">
                <a-tag color="green">
                  {{ detailData.amount/100 }}
                </a-tag>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>

          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="订单状态">
                <a-tag :color="detailData.state === 0?'blue':detailData.state === 1?'orange':detailData.state === 2?'#4BD884':detailData.state === 6?'':'#F03B44'">
                  {{ detailData.state === 0?'订单生成':detailData.state === 1?'支付中':detailData.state === 2?'支付成功':detailData.state === 3?'支付失败':detailData.state === 4?'已撤销':detailData.state === 5?'测试冲正':detailData.state === 6?'订单关闭':'未知' }}
                </a-tag>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="回调状态">
                <a-tag :color="detailData.notifyState === 1?'#4BD884':'#F03B44'">
                  {{ detailData.notifyState === 0?'未发送':detailData.notifyState === 1?'已发送':'未知' }}
                </a-tag>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="支付产品">
                [{{ detailData.productId }}] {{ detailData.productName }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="创建时间">
                {{ detailData.createdAt }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="更新时间">
                {{ detailData.updatedAt }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="订单失效时间">
                {{ detailData.expiredTime }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="支付成功时间">
                {{ detailData.successTime }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-divider />
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="商户代理">
                {{ detailData.agentNo == ''? '无':detailData.agentNo }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="支付通道代理">
                {{ detailData.agentNoPassage == ''? '无':detailData.agentNoPassage }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="商户费率"><b>{{ (detailData.mchFeeRate*100).toFixed(2) }}%</b></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="商户手续费"><a-tag color="pink">{{ detailData.mchFeeAmount/100 }}</a-tag></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="商户代理费率"><b>{{ (detailData.agentRate*100).toFixed(2) }}%</b></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="商户代理手续费"><a-tag color="pink">{{ detailData.agentFeeAmount/100 }}</a-tag></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="通道费率"><b>{{ (detailData.passageRate * 100).toFixed(2) }}%</b></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="通道手续费"><a-tag color="pink">{{ detailData.passageFeeAmount/100 }}</a-tag></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="通道代理费率"><b>{{ (detailData.agentPassageRate*100).toFixed(2) }}%</b></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="通道代理手续费"><a-tag color="pink">{{ detailData.agentPassageFee/100 }}</a-tag></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="24">
            <a-descriptions><a-descriptions-item label="平台利润"><a-tag color="blue">{{ ((detailData.mchFeeAmount - detailData.agentFeeAmount - detailData.passageFeeAmount -detailData.agentPassageFee)/100).toFixed(2)}}</a-tag></a-descriptions-item></a-descriptions>
          </a-col>
          <a-divider />
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="补单操作员">
                <b>{{ detailData.forceChangeLoginName }}</b>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="补单前状态">
                <a-tag :color="detailData.forceChangeBeforeState === 0?'blue':detailData.forceChangeBeforeState === 1?'orange':detailData.forceChangeBeforeState === 2?'#4BD884':detailData.forceChangeBeforeState === 6?'':'#F03B44'">
                  {{ detailData.forceChangeBeforeState === 0?'订单生成':detailData.forceChangeBeforeState === 1?'支付中':detailData.forceChangeBeforeState === 2?'支付成功':detailData.forceChangeBeforeState === 3?'支付失败':detailData.forceChangeBeforeState === 4?'已撤销':detailData.forceChangeBeforeState === 5?'测试冲正':detailData.forceChangeBeforeState === 6?'订单关闭':'未知' }}
                </a-tag>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
        </a-row>
          <a-divider />
          <a-col :sm="24">
            <a-descriptions>
              <a-descriptions-item label="渠道订单号">
                <b>{{ detailData.passageOrderNo == ''? '无':detailData.passageOrderNo }}</b>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="支付接口代码">
                {{ detailData.ifCode }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="支付通道ID">
                {{ detailData.passageId }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="客户端IP">
                {{ detailData.clientIp }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="异步通知地址">
                {{ detailData.notifyUrl }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
        <a-divider />
        <a-row justify="start" type="flex">
          <a-col :sm="24">
            <a-form-model-item label="下单返回参数">
              <a-input
                  type="textarea"
                  disabled="disabled"
                  style="height: 100px;color: black"
                  v-model="detailData.passageResp"
              />
            </a-form-model-item>
          </a-col>
        </a-row>
        <a-row justify="start" type="flex">
          <a-col :sm="24">
            <a-form-model-item label="回调通知参数">
              <a-input
                  type="textarea"
                  disabled="disabled"
                  style="height: 100px;color: black"
                  v-model="detailData.notifyParams"
              />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-drawer>
    </template>
  </page-header-wrapper>
</template>
<script>
import RefundModal from './RefundModal' // 退款弹出框
import JeepayTextUp from '@/components/JeepayTextUp/JeepayTextUp' // 文字上移组件
import JeepayTable from '@/components/JeepayTable/JeepayTable'
import JeepayTableColumns from '@/components/JeepayTable/JeepayTableColumns'
import {
  API_URL_PAY_ORDER_FORCE_LIST,
  API_URL_PAY_ORDER_LIST,
  PAY_ORDER_FORCE_SUCCESS,
  req
} from '@/api/manage'
import moment from 'moment'

// eslint-disable-next-line no-unused-vars
const tableColumns = [
  { key: 'mchNo', title: '商户号', ellipsis: true, width: 200, scopedSlots: { customRender: 'mchSlot' } },
  { key: 'amount', title: '支付金额', ellipsis: true, width: 100, scopedSlots: { customRender: 'amountSlot' } },
  { key: 'orderNo', title: '订单号', scopedSlots: { customRender: 'orderSlot' }, width: 300 },
  { key: 'beforeState', title: '补单前状态', scopedSlots: { customRender: 'beforeStateSlot' }, width: 100 },
  { key: 'state', title: '支付状态', scopedSlots: { customRender: 'stateSlot' }, width: 100 },
  { key: 'forceChangeState', title: '手动补单', scopedSlots: { customRender: 'forceChangeStateSlot' }, width: 100 },
  { key: 'forceChangeName', title: '操作员', scopedSlots: { customRender: 'forceChangeNameSlot' }, width: 100 },
  { key: 'notifyState', title: '回调状态', scopedSlots: { customRender: 'notifySlot' }, width: 100 },
  { key: 'passageName', title: '通道', scopedSlots: { customRender: 'passageSlot' }, width: 250 },
  { key: 'updatedAt', dataIndex: 'updatedAt', title: '更新日期', width: 200 },
  { key: 'op', title: '操作', width: 100, align: 'center', scopedSlots: { customRender: 'opSlot' } }
]

export default {
  name: 'OrderForceList',
  components: { JeepayTable, JeepayTableColumns, JeepayTextUp, RefundModal },
  data () {
    return {
      btnLoading: false,
      tableColumns: tableColumns,
      searchData: { forceChangeState: 1, state: 2 },
      createdStart: '', // 选择开始时间
      createdEnd: '', // 选择结束时间
      visible: false,
      detailData: {},
      productList: [],
      selectedRange: [],
      realTimeStatData: {
        'totalAmount': 0,
        'totalIncome': 0,
        'successCount': 0,
        'totalCount': 0,
        'successAmount': 0,
        'totalMchIncome': 0
      },
      ranges: {
        今天: [moment().startOf('day'), moment().endOf('day')],
        昨天: [moment().subtract(1, 'day').startOf('day'), moment().subtract(1, 'day').endOf('day')],
        近一周: [
          moment().subtract(1, 'week').startOf('day'),
          moment().endOf('day')
        ]
      }
    }
  },
  computed: {
  },
  mounted () {
    this.selectedRange = [moment().startOf('day'), moment().endOf('day')] // 开始时间
    this.searchData.createdStart = this.selectedRange[0].format('YYYY-MM-DD HH:mm:ss') // 开始时间
    this.searchData.createdEnd = this.selectedRange[1].format('YYYY-MM-DD HH:mm:ss') // 结束时间
    this.searchData.forceChangeState = 1
    this.searchData.state = 2
    this.queryFunc()
    this.$message.info('此页订单时间查询以订单更新日期为准，请知悉')
  },
  methods: {
    queryFunc () {
      this.searchData.forceChangeState = 1
      this.searchData.state = 2
      this.btnLoading = true
      this.getStatData()
      this.$refs.infoTable.refTable(true)
    },
    // 请求table接口数据
    reqTableDataFunc: (params) => {
      return req.list(API_URL_PAY_ORDER_FORCE_LIST, params)
    },
    searchFunc: function () { // 点击【查询】按钮点击事件
      this.$refs.infoTable.refTable(false)
    },
    detailFunc: function (recordId) {
      const that = this
      req.getById(API_URL_PAY_ORDER_LIST, recordId).then(res => {
        that.detailData = res
      })
      this.visible = true
    },
    forceChangeFunc: function (recordId) {
      console.log(recordId + 'forceChangeFunc')
      const that = this
      req.getNormal(PAY_ORDER_FORCE_SUCCESS, recordId + '/forcePayOrderSuccess').then(res => {
        that.$message.success('补单成功')
        this.$refs.infoTable.refTable(false)
      })
    },
    moment,
    onChange (date, dateString) {
      this.searchData.createdStart = dateString[0] // 开始时间
      this.searchData.createdEnd = dateString[1] // 结束时间
    },
    disabledDate (current) { // 今日之后日期不可选
      return current && current > moment().endOf('day')
    },
    onClose () {
      this.visible = false
    },
    changeStr2ellipsis (orderNo, baseLength) {
      const halfLengh = parseInt(baseLength / 2)
      return orderNo.substring(0, halfLengh - 1) + '...' + orderNo.substring(orderNo.length - halfLengh, orderNo.length)
    },
    resetSearch () {
      this.searchData = { forceChangeState: 1 }
      this.selectedRange = []
    },
    getStatData () {
      const that = this
      that.statLoading = true
      req.postDataNormal('/api/payRealTimeStatOrder', 'countRealForceOrder', this.searchData).then(res => { // 产品下拉列表
        that.realTimeStatData = res
        that.statLoading = false
      })
    }
  }
}
</script>
<style lang="less" scoped>
.order-list {
  -webkit-text-size-adjust:none;
  font-size: 12px;
  display: flex;
  flex-direction: column;

  p {
    white-space:nowrap;
    span {
      display: inline-block;
      height: 24px;
      line-height: 24px;
      width: 60px;
      border-radius: 2px;
      text-align: center;
      margin-right: 4px;
    }
  }
}

.mch-name {
  -webkit-text-size-adjust:none;
  font-size: 12px;
  display: flex;
  flex-direction: column;
  p {
    white-space:nowrap;
    span {
      display: inline-block;
      //font-weight: 800;
      height: 16px;
      line-height: 16px;
      width: 60px;
      text-align: center;
    }
  }
}
.stat-col{
  position: relative;
  //background-color: gray;
  height: 100px;
  border-radius: 13px;
}

.stat-col b{
  position: absolute;
  font-size: 30px;
  top: 10px;
  left: 20px;
}

.stat-col .sub-content{
  //font-weight: bold;
  position: absolute;
  font-size: 15px;
  line-height: 20px;
  height: 20px;
  text-align: right;
  bottom: 20px;
  right: 20px;
  color: #717579;
}

.stat-col img{
  position: absolute;
  top: 20px;
  right: 20px;
}

.stat-col .title{
  position: absolute;
  bottom: 20px;
  left: 20px;
  color: #717579;
}

.stat-col .num{
  position: absolute;
  border-radius: 13px;
}

.bg-color-1{
  background-color: #FFDADA;
}
.bg-color-2{
  background-color: #FFEAD1;
}
.bg-color-3{
  background-color: #DFEFFF;
}
.bg-color-4{
  background-color: #EFE1FF;
}
.bg-color-5{
  background-color: #E4FFEF;
}
</style>
