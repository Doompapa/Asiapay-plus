<template>
  <page-header-wrapper>
    <a-card>
      <div class="table-page-search-wrapper">
        <a-form layout="inline" class="table-head-ground">
          <div class="table-layer">
            <a-form-item label="" class="table-head-layout" style="max-width:350px;min-width:300px">
              <a-range-picker
                  @change="onChange"
                  :show-time="{ format: 'HH:mm:ss' }"
                  format="YYYY-MM-DD HH:mm:ss"
                  :disabled-date="disabledDate"
                  :ranges="ranges"
                  v-model="selectedRange"
              >
                <a-icon slot="suffixIcon" type="sync" />
              </a-range-picker>
            </a-form-item>
<!--            <jeepay-text-up :placeholder="'支付订单号/商户订单号'" :msg="searchData.unionOrderId" v-model="searchData.unionOrderId" />-->
            <jeepay-text-up :placeholder="'支付订单号'" :msg="searchData.payOrderId" v-model="searchData.payOrderId" />
            <jeepay-text-up :placeholder="'商户订单号'" :msg="searchData.mchOrderNo" v-model="searchData.mchOrderNo" />
            <a-form-model-item label="" class="table-head-layout">
              <a-select v-model="searchData.productId" placeholder="对应产品" :allowClear="true">getProductId
                <a-select-option v-for="d in productList" :value="d.productId" :key="d.productId">
                  {{ d.productName + " [ ID: " + d.productId + " ]" }}
                </a-select-option>
              </a-select>
            </a-form-model-item>
            <a-form-item label="" class="table-head-layout">
              <a-select v-model="searchData.state" placeholder="支付状态" default-value="">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="0">订单生成</a-select-option>
                <a-select-option value="1">支付中</a-select-option>
                <a-select-option value="2">支付成功</a-select-option>
                <a-select-option value="3">支付失败</a-select-option>
                <a-select-option value="5">测试冲正</a-select-option>
                <a-select-option value="6">订单关闭</a-select-option>
                <a-select-option value="7">出码失败</a-select-option>
                <a-select-option value="8">调额入账</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="" class="table-head-layout">
              <a-select v-model="searchData.notifyState" placeholder="回调状态" default-value="">
                <a-select-option value="">全部</a-select-option>
                <a-select-option value="0">未发送</a-select-option>
                <a-select-option value="1">已发送</a-select-option>
              </a-select>
            </a-form-item>
            <span class="table-page-search-submitButtons">
              <a-button type="primary" icon="search" @click="queryFunc" :loading="btnLoading">搜索</a-button>
              <a-button style="margin-left: 8px" icon="reload" @click="resetSearch">重置</a-button>
              <a-button type="danger" style="margin-left: 8px" icon="download" @click="exportExcel">导出</a-button>
            </span>
          </div>
        </a-form>
      </div>

      <!-- 列表渲染 -->
      <JeepayTable
          @btnLoadClose="btnLoading=false"
          ref="infoTable"
          :initData="false"
          :reqTableDataFunc="reqTableDataFunc"
          :tableColumns="tableColumns"
          :searchData="searchData"
          rowKey="payOrderId"
          :pageSize="50"
      >
        <template slot="mchSlot" slot-scope="{record}">
          <span style="color: #1A79FF">{{ record.mchNo }}</span>
        </template> <!-- 商户信息插槽 -->
        <template slot="amountSlot" slot-scope="{record}"><b>￥{{ record.amount/100 }}</b></template> <!-- 订单金额插槽 -->
        <template slot="stateSlot" slot-scope="{record}">
          <a-tag :color="getOrderStateColor(record.state)">
            {{ getOrderStateName(record.state) }}
          </a-tag>
        </template>
        <template slot="forceChangeStateSlot" slot-scope="{record}">
          <a-tag :color="record.forceChangeState != undefined && record.forceChangeState === 1?'#007EFF':''">
            {{ record.forceChangeState != undefined && record.forceChangeState === 1?'是':'否'}}
          </a-tag>
        </template>
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
            <a-button type="link" @click="detailFunc(record.payOrderId)">查看详情</a-button>
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
                <a-tag color="#4BD884">
                  {{ detailData.amount/100 }}
                </a-tag>
              </a-descriptions-item>
            </a-descriptions>
          </a-col>

          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="订单状态">
                <a-tag :color="getOrderStateColor(detailData.state)">
                  {{ getOrderStateName(detailData.state) }}
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
            <a-descriptions><a-descriptions-item label="商户费率"><b>{{ (detailData.mchFeeRate*100).toFixed(2) }}%</b></a-descriptions-item></a-descriptions>
          </a-col>
          <a-col :sm="12">
            <a-descriptions><a-descriptions-item label="商户手续费"><a-tag color="pink">{{ detailData.mchFeeAmount/100 }}</a-tag></a-descriptions-item></a-descriptions>
          </a-col>
          <a-divider />
          <a-col :sm="12">
            <a-descriptions>
              <a-descriptions-item label="客户端IP">
                {{ detailData.clientIp }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
          <a-col :sm="24">
            <a-descriptions>
              <a-descriptions-item label="异步通知地址">
                {{ detailData.notifyUrl }}
              </a-descriptions-item>
            </a-descriptions>
          </a-col>
        </a-row>
      </a-drawer>
    </template>
  </page-header-wrapper>
</template>
<script>
import JeepayTextUp from '@/components/JeepayTextUp/JeepayTextUp' // 文字上移组件
import JeepayTable from '@/components/JeepayTable/JeepayTable'
import JeepayTableColumns from '@/components/JeepayTable/JeepayTableColumns'
import {
  API_URL_MCH_APP,
  API_URL_PAY_ORDER_LIST, exportExcel,
  req
} from '@/api/manage'
import moment from 'moment'
import { saveAs } from 'file-saver'
import { getOrderStateColor, getOrderStateName } from '@/utils/util'

// eslint-disable-next-line no-unused-vars
const tableColumns = [
  { key: 'mchNo', title: '商户号', ellipsis: true, width: 150, fixed: 'left', scopedSlots: { customRender: 'mchSlot' } },
  { key: 'mchName', title: '商户名称', ellipsis: true, width: 180, fixed: 'left', dataIndex: 'mchName' },
  { key: 'amount', title: '支付金额', ellipsis: true, width: 100, fixed: 'left', scopedSlots: { customRender: 'amountSlot' } },
  // { key: 'mchFeeAmount', dataIndex: 'mchFeeAmount', title: '手续费', customRender: (text) => '￥' + (text / 100).toFixed(2), width: 100 },
  { key: 'orderNo', title: '订单号', scopedSlots: { customRender: 'orderSlot' }, width: 300, fixed: 'left' },
  { key: 'wayName', title: '产品类型(快照)', width: 200, scopedSlots: { customRender: 'productSlot' }, fixed: 'left' },
  { key: 'state', title: '支付状态', scopedSlots: { customRender: 'stateSlot' }, width: 130, fixed: 'left' },
  { key: 'forceChangeState', title: '手动补单', scopedSlots: { customRender: 'forceChangeStateSlot' }, width: 100, fixed: 'left' },
  { key: 'notifyState', title: '回调状态', scopedSlots: { customRender: 'notifySlot' }, width: 100, fixed: 'left' },
  { key: 'createdAt', dataIndex: 'createdAt', title: '创建日期', width: 200, fixed: 'left' },
  { key: 'op', title: '操作', width: 180, fixed: 'right', align: 'center', scopedSlots: { customRender: 'opSlot' } }
]

export default {
  name: 'PayOrderListPage',
  components: { JeepayTable, JeepayTableColumns, JeepayTextUp },
  data () {
    return {
      btnLoading: false,
      tableColumns: tableColumns,
      searchData: {},
      createdStart: '', // 选择开始时间
      createdEnd: '', // 选择结束时间
      visible: false,
      detailData: {},
      productList: [],
      selectedRange: null,
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
    const that = this
    req.list(API_URL_MCH_APP, { 'pageSize': -1 }).then(res => { // 产品下拉选择列表
      that.productList = res.records
    })
    // 默认今天
    this.selectedRange = [moment().startOf('day'), moment().endOf('day')] // 开始时间
    this.searchData.createdStart = this.selectedRange[0].format('YYYY-MM-DD HH:mm:ss') // 开始时间
    this.searchData.createdEnd = this.selectedRange[1].format('YYYY-MM-DD HH:mm:ss') // 结束时间
    this.$refs.infoTable.refTable(true)
  },
  methods: {
    getOrderStateColor,
    getOrderStateName,
    queryFunc () {
      this.btnLoading = true
      this.$refs.infoTable.refTable(true)
    },
    // 请求table接口数据
    reqTableDataFunc: (params) => {
      return req.list(API_URL_PAY_ORDER_LIST, params)
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
    resetSearch: function () {
      this.searchData = {}
      this.selectedRange = null
    },
    changeStr2ellipsis (orderNo, baseLength) {
      const halfLengh = parseInt(baseLength / 2)
      return orderNo.substring(0, halfLengh - 1) + '...' + orderNo.substring(orderNo.length - halfLengh, orderNo.length)
    },
    exportExcel: function () { // todo 这里返回的结果没有嵌入到通用返回格式中
      this.$message.success('操作成功！请误操作页面，完成后将自动开启下载请耐心等待')
      exportExcel('/api/payOrderExport/exportMchExcel', this.searchData).then(res => {
        const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
        // 使用 FileSaver.js 的 saveAs 方法将 Blob 对象保存为文件
        saveAs(blob, moment().format('YYYY-MM-DD') + '-商户订单流水.xlsx')
      }).catch((resErr) => {
        const blob = new Blob([resErr], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
        // 使用 FileSaver.js 的 saveAs 方法将 Blob 对象保存为文件
        saveAs(blob, moment().format('YYYY-MM-DD') + '-商户订单流水.xlsx')
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
</style>
