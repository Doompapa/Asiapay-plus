<template>
  <page-header-wrapper>
    <a-card>
      <div class="table-page-search-wrapper">
      </div>

      <!-- 列表渲染 -->
      <JeepayTable
          @btnLoadClose="btnLoading=false"
          ref="infoTable"
          :initData="true"
          :reqTableDataFunc="reqTableDataFunc"
          :tableColumns="tableColumns"
          :searchData="searchData"
          rowKey="statisticsMchId"
      >
        <template slot="beforeSlot" slot-scope="{record}">
          <b>{{ (record.totalMchCost / 100).toFixed(2) }}</b>
        </template> <!-- 自定义插槽 -->
        <template slot="dateSlot" slot-scope="{record}">
          <b>{{ record.createdAt.substring(0, 10)}}</b>
        </template> <!-- 自定义插槽 -->
        <template slot="amountSlot" slot-scope="{record}">
          <b style="color: #4BD884">{{(record.totalSuccessAmount / 100).toFixed(2)}}</b>
        </template> <!-- after插槽 -->
        <template slot="afterSlot" slot-scope="{record}">
          <b>{{ (record.orderSuccessCount / record.totalOrderCount *100).toFixed(2) }}%</b>
        </template> <!-- 订单金额插槽 -->
        <template slot="payOrderAmountSlot" slot-scope="{record}">
          <b>{{ record.totalOrderCount }}</b>
        </template> <!-- 自定义插槽 -->
        <template slot="payOrderSuccessCountSlot" slot-scope="{record}">
          <b>{{ record.orderSuccessCount }}</b>
        </template> <!-- 自定义插槽 -->
      </JeepayTable>
    </a-card>
  </page-header-wrapper>
</template>

<script>
import JeepayTable from '@/components/JeepayTable/JeepayTable'
import JeepayTextUp from '@/components/JeepayTextUp/JeepayTextUp' // 文字上移组件
import JeepayTableColumns from '@/components/JeepayTable/JeepayTableColumns'
import { API_URL_MCH_DAY_STAT_LIST, req } from '@/api/manage'
import moment from 'moment'

// eslint-disable-next-line no-unused-vars
const tableColumns = [
  { key: 'date', title: '日期', width: '150px', fixed: 'left', scopedSlots: { customRender: 'dateSlot' } },
  { key: 'mchNo', title: '商户号', dataIndex: 'mchNo', width: '150px', fixed: 'left' },
  { key: 'amount', width: '150px', title: '成交额(￥)', fixed: 'left', scopedSlots: { customRender: 'amountSlot' } },
  {
    key: 'beforeBalance',
    title: '服务费(￥)',
    width: '180px',
    fixed: 'left',
    scopedSlots: { customRender: 'beforeSlot' }
  },
  {
    key: 'afterBalance',
    title: '支付率',
    width: '180px',
    fixed: 'left',
    scopedSlots: { customRender: 'afterSlot' }
  },
  {
    key: 'payOrderAmount',
    width: '150px',
    title: '订单总笔数',
    fixed: 'left',
    scopedSlots: { customRender: 'payOrderAmountSlot' }
  },
  {
    key: 'payOrderAmount1',
    width: '150px',
    title: '成交笔数',
    fixed: 'left',
    scopedSlots: { customRender: 'payOrderSuccessCountSlot' }
  },
  { key: 'createdAt', dataIndex: 'createdAt', title: '创建日期' }
]

export default {
  name: 'DayStatList',
  components: { JeepayTable, JeepayTableColumns, JeepayTextUp },
  data () {
    return {
      btnLoading: false,
      tableColumns: tableColumns,
      searchData: {},
      selectedRange: [],
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
  mounted () {
  },
  methods: {
    queryFunc () {
      this.btnLoading = true
      this.$refs.infoTable.refTable(true)
    },
    // 请求table接口数据
    reqTableDataFunc: (params) => {
      return req.list(API_URL_MCH_DAY_STAT_LIST, params)
    },
    disabledDate (current) { // 今日之后日期不可选
      return current && current > moment().endOf('day')
    },
    onChange: function (date, dateString) {
      this.searchData.createdStart = dateString[0] // 开始时间
      this.searchData.createdEnd = dateString[1] // 结束时间
      this.$refs.infoTable.refTable(true)
    },
    resetSearch: function () {
      this.searchData = {}
      this.selectedRange = [] // 开始时间
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
      font-weight: 800;
      height: 16px;
      line-height: 16px;
      width: 60px;
      border-radius: 5px;
      text-align: center;
      margin-right: 2px;
    }
  }
}
</style>
