(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-53ed9eb9"],{"0fea":function(t,e,n){"use strict";n.d(e,"cb",(function(){return a})),n.d(e,"db",(function(){return u})),n.d(e,"d",(function(){return o})),n.d(e,"K",(function(){return i})),n.d(e,"J",(function(){return s})),n.d(e,"N",(function(){return c})),n.d(e,"P",(function(){return d})),n.d(e,"h",(function(){return l})),n.d(e,"f",(function(){return f})),n.d(e,"g",(function(){return p})),n.d(e,"r",(function(){return m})),n.d(e,"o",(function(){return h})),n.d(e,"y",(function(){return y})),n.d(e,"C",(function(){return b})),n.d(e,"c",(function(){return g})),n.d(e,"v",(function(){return S})),n.d(e,"H",(function(){return T})),n.d(e,"u",(function(){return v})),n.d(e,"Q",(function(){return O})),n.d(e,"B",(function(){return C})),n.d(e,"q",(function(){return q})),n.d(e,"i",(function(){return w})),n.d(e,"l",(function(){return D})),n.d(e,"k",(function(){return R})),n.d(e,"j",(function(){return P})),n.d(e,"n",(function(){return F})),n.d(e,"m",(function(){return E})),n.d(e,"F",(function(){return _})),n.d(e,"E",(function(){return k})),n.d(e,"I",(function(){return I})),n.d(e,"s",(function(){return x})),n.d(e,"t",(function(){return L})),n.d(e,"M",(function(){return A})),n.d(e,"L",(function(){return B})),n.d(e,"e",(function(){return G})),n.d(e,"D",(function(){return j})),n.d(e,"p",(function(){return z})),n.d(e,"a",(function(){return J})),n.d(e,"O",(function(){return U})),n.d(e,"G",(function(){return $})),n.d(e,"x",(function(){return K})),n.d(e,"w",(function(){return H})),n.d(e,"z",(function(){return M})),n.d(e,"A",(function(){return X})),n.d(e,"b",(function(){return W})),n.d(e,"hb",(function(){return Q})),n.d(e,"U",(function(){return V})),n.d(e,"bb",(function(){return Y})),n.d(e,"eb",(function(){return Z})),n.d(e,"X",(function(){return tt})),n.d(e,"W",(function(){return et})),n.d(e,"gb",(function(){return nt})),n.d(e,"fb",(function(){return rt})),n.d(e,"Y",(function(){return at})),n.d(e,"V",(function(){return ut})),n.d(e,"S",(function(){return ot})),n.d(e,"T",(function(){return it})),n.d(e,"Z",(function(){return st})),n.d(e,"ab",(function(){return ct})),n.d(e,"R",(function(){return dt}));var r=n("4667"),a={list:function(t,e){return r["a"].request({url:t,method:"GET",params:e},!0,!0,!1)},add:function(t,e){return r["a"].request({url:t,method:"POST",data:e},!0,!0,!1)},getById:function(t,e){return r["a"].request({url:t+"/"+e,method:"GET"},!0,!0,!1)},updateById:function(t,e,n){return r["a"].request({url:t+"/"+e,method:"PUT",data:n},!0,!0,!1)},delById:function(t,e){return r["a"].request({url:t+"/"+e,method:"DELETE"},!0,!0,!1)},postNormal:function(t,e){return r["a"].request({url:t+"/"+e,method:"POST"},!0,!0,!0)},postDataNormal:function(t,e,n){return r["a"].request({url:t+"/"+e,method:"POST",data:n},!0,!0,!0)},getNormal:function(t,e){return r["a"].request({url:t+"/"+e,method:"GET"},!0,!0,!0)}},u={list:function(t,e){return r["a"].request({url:t,method:"GET",params:e},!0,!0,!0)},add:function(t,e){return r["a"].request({url:t,method:"POST",data:e},!0,!0,!0)},getById:function(t,e){return r["a"].request({url:t+"/"+e,method:"GET"},!0,!0,!0)},updateById:function(t,e,n){return r["a"].request({url:t+"/"+e,method:"PUT",data:n},!0,!0,!0)},delById:function(t,e){return r["a"].request({url:t+"/"+e,method:"DELETE"},!0,!0,!0)}},o="/api/sysEnts",i="/api/sysRoles",s="/api/sysRoleEntRelas",c="/api/sysUsers",d="/api/sysUserRoleRelas",l="/api/isvInfo",f="/api/isvBalance",p="/api/agentHistory",m="/api/mchInfo",h="/api/mchBalance",y="/api/mchStatInfo",b="/api/passageStatInfo",g="/api/agentStatInfo",S="/api/mchProductInfo",T="/api/productMchInfo",v="/api/mchPassageInfo",O="/api/payOrder",C="/api/passageMchInfo",q="/api/mchHistory",w="/api/mchApps",D="/api/mchAppsList",R="/api/passageHistory",P="/api/mchAppsBalance",F="/api/mchAppsBalanceReset",E="/api/mchAppsMultipleSet",_="/api/payOrder",k="/api/payOrderForceList",I="/api/refundOrder",x="/api/mchNotify",L="/api/mchNotifyResend/resendAll",A="api/sysLog",B="api/sysConfigs",N="api/mainChart",G="/api/payIfDefines",j="/api/payWays",z="/api/mchDivision",J="/api/agentDivision",U="/api/transferOrders",$="/api/platStat",K="/api/mchStat",H="/api/mchProductStat",M="/api/passageStat",X="/api/productStat",W="/api/agentStat",Q={avatar:r["a"].baseUrl+"/api/ossFiles/avatar",ifBG:r["a"].baseUrl+"/api/ossFiles/ifBG",cert:r["a"].baseUrl+"/api/ossFiles/cert"};function V(t){return r["a"].request({url:"/api/sysEnts/showTree?sysType="+t,method:"GET"})}function Y(t,e,n){return r["a"].request({url:"/api/payOrder/refunds/"+t,method:"POST",data:{refundAmount:e,refundReason:n}})}function Z(t,e){return r["a"].request({url:"api/sysUserRoleRelas/relas/"+t,method:"POST",data:{roleIdListStr:JSON.stringify(e)}})}function tt(){return r["a"].request({url:N+"/twoDayCount",method:"GET"})}function et(){return r["a"].request({url:N+"/realTimeCount",method:"GET"})}function nt(t){return r["a"].request({url:"/api/current/modifyPwd",method:"put",data:t})}function rt(t){return r["a"].request({url:"/api/current/user",method:"put",data:t})}function at(){return r["a"].request({url:"/api/current/user",method:"get"})}function ut(){return r["a"].request({url:"/api/current/getGoogleKey",method:"get"})}function ot(t){return r["a"].request({url:B+"/"+t,method:"GET"})}function it(t,e){return r["a"].request({url:"/api/sysEnts/bySysType",method:"GET",params:{entId:t,sysType:e}})}function st(t){return r["a"].request({url:"/api/mchNotify/resend/"+t,method:"POST"})}function ct(t){return r["a"].request({url:"/api/passageTest/doPay",method:"POST",data:t})}function dt(t,e){return r["a"].request({url:t,method:"POST",data:e,responseType:"arraybuffer"},!0,!1,!0)}},"27fc":function(t,e,n){},"3cbe":function(t,e,n){"use strict";n("a415")},4894:function(t,e,n){},"4f53":function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"jee-text-up table-head-layout"},[n("a-input",{attrs:{required:"required",value:t.msg},on:{input:function(e){return t.$emit("input",e.target.value)}}}),n("label",[t._v(t._s(t.placeholder))])],1)},a=[],u={name:"JeepayTextUp",props:{msg:{type:String,default:""},placeholder:{type:String,default:""}}},o=u,i=(n("8bf8"),n("2877")),s=Object(i["a"])(o,r,a,!1,null,"4708ca2b",null);e["a"]=s.exports},"5d5e":function(t,e,n){"use strict";n("d81d");var r,a,u={name:"JeepayTableColumns",render:function(t,e){var n=arguments[0],r=[];if(this.$slots.default.map((function(t){return t.tag&&r.push(t),!1})),r.length<=4)return t("div",{style:"display:flex; justify-content: space-evenly;"},r);for(var a=[r[0],r[1],r[2]],u=[],o=3;o<r.length;o++)u.push(n("a-menu-item",[r[o]]));return n("div",{style:"display:flex; justify-content: space-evenly;"},[" ",a,n("a-dropdown",[n("a-button",{class:"ant-dropdown-link",attrs:{type:"link"},style:""},["更多",n("a-icon",{attrs:{type:"down"}})]),n("a-menu",{slot:"overlay"},[u])])])}},o=u,i=(n("3cbe"),n("2877")),s=Object(i["a"])(o,r,a,!1,null,"d8995c5c",null);e["a"]=s.exports},"8bf8":function(t,e,n){"use strict";n("eaa4")},a415:function(t,e,n){},c0db:function(t,e,n){"use strict";n("4894")},c63d:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("page-header-wrapper",[n("a-card",[n("div",{staticClass:"table-page-search-wrapper"}),n("JeepayTable",{ref:"infoTable",attrs:{initData:!0,reqTableDataFunc:t.reqTableDataFunc,tableColumns:t.tableColumns,searchData:t.searchData,rowKey:"statisticsDate"},on:{btnLoadClose:function(e){t.btnLoading=!1}},scopedSlots:t._u([{key:"incomeSlot",fn:function(e){var r=e.record;return[n("b",{staticStyle:{color:"#4BD884"}},[t._v(t._s((r.platTotalIncome/100).toFixed(2)))])]}},{key:"dateSlot",fn:function(e){var r=e.record;return[n("b",[t._v(t._s(r.createdAt.substring(0,10)))])]}},{key:"amountSlot",fn:function(e){var r=e.record;return[n("b",[t._v(t._s((r.totalSuccessAmount/100).toFixed(2)))])]}},{key:"successRateSlot",fn:function(e){var r=e.record;return[n("b",[t._v(t._s((r.orderSuccessCount/r.totalOrderCount*100).toFixed(2))+"%")])]}},{key:"payOrderAmountSlot",fn:function(e){var r=e.record;return[n("span",[t._v(t._s(r.totalOrderCount))])]}},{key:"payOrderSuccessCountSlot",fn:function(e){var r=e.record;return[n("span",[t._v(t._s(r.orderSuccessCount))])]}}])})],1)],1)},a=[],u=n("f339"),o=n("4f53"),i=n("5d5e"),s=n("0fea"),c=n("c1df"),d=n.n(c),l=[{key:"date",title:"日期",width:"200px",fixed:"left",scopedSlots:{customRender:"dateSlot"}},{key:"amount",title:"成交额(￥)",scopedSlots:{customRender:"amountSlot"}},{key:"beforeBalance",title:"平台收入(￥)",scopedSlots:{customRender:"incomeSlot"}},{key:"successRate",title:"支付成功率",scopedSlots:{customRender:"successRateSlot"}},{key:"payOrderAmount",title:"订单总笔数",scopedSlots:{customRender:"payOrderAmountSlot"}},{key:"payOrderAmount1",title:"成交笔数",scopedSlots:{customRender:"payOrderSuccessCountSlot"}},{key:"createdAt",dataIndex:"createdAt",title:"创建日期"}],f={name:"PlatStatPage",components:{JeepayTable:u["a"],JeepayTableColumns:i["a"],JeepayTextUp:o["a"]},data:function(){return{btnLoading:!1,tableColumns:l,searchData:{},selectedRange:[],ranges:{"今天":[d()().startOf("day"),d()().endOf("day")],"昨天":[d()().subtract(1,"day").startOf("day"),d()().subtract(1,"day").endOf("day")],"近一周":[d()().subtract(1,"week").startOf("day"),d()().endOf("day")]}}},mounted:function(){},methods:{queryFunc:function(){this.btnLoading=!0,this.$refs.infoTable.refTable(!0)},reqTableDataFunc:function(t){return s["cb"].list(s["G"],t)},disabledDate:function(t){return t&&t>d()().endOf("day")},onChange:function(t,e){this.searchData.createdStart=e[0],this.searchData.createdEnd=e[1],this.$refs.infoTable.refTable(!0)},resetSearch:function(){this.searchData={},this.selectedRange=[]}}},p=f,m=(n("c0db"),n("2877")),h=Object(m["a"])(p,r,a,!1,null,"d5fc7772",null);e["default"]=h.exports},eaa4:function(t,e,n){},f339:function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("a-table",{attrs:{columns:t.tableColumns,"data-source":t.apiResData.records,pagination:t.pagination,loading:t.showLoading,"row-selection":t.rowSelection,rowKey:t.rowKey,scroll:{x:t.scrollX},customRow:function(e,n){return t.tableRowCrossColor?{style:{"background-color":n%2==0?"#FCFCFC":"#FFFFFF"}}:{}}},on:{change:t.handleTableChange},scopedSlots:t._u([t._l(t.columnsCustomSlots,(function(e){return{key:e.customRender,fn:function(n){return[t._t(e.customRender,null,{record:n})]}}}))],null,!0)})],1)},a=[],u=n("5530"),o=(n("a9e3"),n("d81d"),n("4de4"),{name:"JeepayTable",props:{initData:{type:Boolean,default:!0},tableColumns:Array,reqTableDataFunc:{type:Function},currentChange:{type:Function,default:function(t,e){}},searchData:Object,pageSize:{type:Number,default:20},rowSelection:Object,rowKey:{type:[String,Function]},scrollX:{type:Number,default:500},tableRowCrossColor:{type:Boolean,default:!1}},data:function(){return{apiResData:{total:0,records:[]},iPage:{pageNumber:1,pageSize:this.pageSize},pagination:{total:0,current:1,pageSizeOptions:["10","20","50","100"],pageSize:this.pageSize,showSizeChanger:!0,showTotal:function(t){return"共".concat(t,"条")}},showLoading:!1}},computed:{columnsCustomSlots:function(){return this.tableColumns.filter((function(t){return t.scopedSlots})).map((function(t){return t.scopedSlots}))}},mounted:function(){this.initData&&this.refTable(!0)},methods:{handleTableChange:function(t,e,n){this.pagination=t,this.iPage=Object(u["a"])({pageSize:t.pageSize,pageNumber:t.current,sortField:n.columnKey,sortOrder:n.order},e),this.refTable()},refTable:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]&&arguments[0],n=this;e&&(this.iPage.pageNumber=1,this.pagination.current=1),this.showLoading=!0,this.reqTableDataFunc(Object.assign({},this.iPage,this.searchData)).then((function(e){t.pagination.total=e.total,t.apiResData=e,t.showLoading=!1,0===e.records.length&&t.iPage.pageNumber>1&&n.$nextTick((function(){var r=e.total/t.iPage.pageSize+(e.total%t.iPage.pageSize===0?0:1);if(0===r)return!1;var a=t.iPage.pageNumber-1>r?r:t.iPage.pageNumber-1;t.iPage.pageNumber=a,t.pagination.current=a,n.refTable(!1)})),n.$emit("btnLoadClose")})).catch((function(e){t.showLoading=!1,n.$emit("btnLoadClose")}))}}}),i=o,s=(n("f705"),n("2877")),c=Object(s["a"])(i,r,a,!1,null,null,null);e["a"]=c.exports},f705:function(t,e,n){"use strict";n("27fc")}}]);