(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1c39c64e"],{"0fea":function(e,t,a){"use strict";a.d(t,"cb",(function(){return r})),a.d(t,"db",(function(){return o})),a.d(t,"d",(function(){return i})),a.d(t,"K",(function(){return s})),a.d(t,"J",(function(){return u})),a.d(t,"N",(function(){return c})),a.d(t,"P",(function(){return l})),a.d(t,"h",(function(){return d})),a.d(t,"f",(function(){return f})),a.d(t,"g",(function(){return p})),a.d(t,"r",(function(){return m})),a.d(t,"o",(function(){return h})),a.d(t,"y",(function(){return y})),a.d(t,"C",(function(){return b})),a.d(t,"c",(function(){return g})),a.d(t,"v",(function(){return v})),a.d(t,"H",(function(){return T})),a.d(t,"u",(function(){return w})),a.d(t,"Q",(function(){return S})),a.d(t,"B",(function(){return D})),a.d(t,"q",(function(){return x})),a.d(t,"i",(function(){return O})),a.d(t,"l",(function(){return R})),a.d(t,"k",(function(){return k})),a.d(t,"j",(function(){return _})),a.d(t,"n",(function(){return E})),a.d(t,"m",(function(){return C})),a.d(t,"F",(function(){return q})),a.d(t,"E",(function(){return I})),a.d(t,"I",(function(){return L})),a.d(t,"s",(function(){return B})),a.d(t,"t",(function(){return F})),a.d(t,"M",(function(){return N})),a.d(t,"L",(function(){return j})),a.d(t,"e",(function(){return z})),a.d(t,"D",(function(){return A})),a.d(t,"p",(function(){return M})),a.d(t,"a",(function(){return U})),a.d(t,"O",(function(){return Y})),a.d(t,"G",(function(){return H})),a.d(t,"x",(function(){return $})),a.d(t,"w",(function(){return G})),a.d(t,"z",(function(){return J})),a.d(t,"A",(function(){return K})),a.d(t,"b",(function(){return X})),a.d(t,"hb",(function(){return W})),a.d(t,"U",(function(){return Q})),a.d(t,"bb",(function(){return V})),a.d(t,"eb",(function(){return Z})),a.d(t,"X",(function(){return ee})),a.d(t,"W",(function(){return te})),a.d(t,"gb",(function(){return ae})),a.d(t,"fb",(function(){return ne})),a.d(t,"Y",(function(){return re})),a.d(t,"V",(function(){return oe})),a.d(t,"S",(function(){return ie})),a.d(t,"T",(function(){return se})),a.d(t,"Z",(function(){return ue})),a.d(t,"ab",(function(){return ce})),a.d(t,"R",(function(){return le}));var n=a("4667"),r={list:function(e,t){return n["a"].request({url:e,method:"GET",params:t},!0,!0,!1)},add:function(e,t){return n["a"].request({url:e,method:"POST",data:t},!0,!0,!1)},getById:function(e,t){return n["a"].request({url:e+"/"+t,method:"GET"},!0,!0,!1)},updateById:function(e,t,a){return n["a"].request({url:e+"/"+t,method:"PUT",data:a},!0,!0,!1)},delById:function(e,t){return n["a"].request({url:e+"/"+t,method:"DELETE"},!0,!0,!1)},postNormal:function(e,t){return n["a"].request({url:e+"/"+t,method:"POST"},!0,!0,!0)},postDataNormal:function(e,t,a){return n["a"].request({url:e+"/"+t,method:"POST",data:a},!0,!0,!0)},getNormal:function(e,t){return n["a"].request({url:e+"/"+t,method:"GET"},!0,!0,!0)}},o={list:function(e,t){return n["a"].request({url:e,method:"GET",params:t},!0,!0,!0)},add:function(e,t){return n["a"].request({url:e,method:"POST",data:t},!0,!0,!0)},getById:function(e,t){return n["a"].request({url:e+"/"+t,method:"GET"},!0,!0,!0)},updateById:function(e,t,a){return n["a"].request({url:e+"/"+t,method:"PUT",data:a},!0,!0,!0)},delById:function(e,t){return n["a"].request({url:e+"/"+t,method:"DELETE"},!0,!0,!0)}},i="/api/sysEnts",s="/api/sysRoles",u="/api/sysRoleEntRelas",c="/api/sysUsers",l="/api/sysUserRoleRelas",d="/api/isvInfo",f="/api/isvBalance",p="/api/agentHistory",m="/api/mchInfo",h="/api/mchBalance",y="/api/mchStatInfo",b="/api/passageStatInfo",g="/api/agentStatInfo",v="/api/mchProductInfo",T="/api/productMchInfo",w="/api/mchPassageInfo",S="/api/payOrder",D="/api/passageMchInfo",x="/api/mchHistory",O="/api/mchApps",R="/api/mchAppsList",k="/api/passageHistory",_="/api/mchAppsBalance",E="/api/mchAppsBalanceReset",C="/api/mchAppsMultipleSet",q="/api/payOrder",I="/api/payOrderForceList",L="/api/refundOrder",B="/api/mchNotify",F="/api/mchNotifyResend/resendAll",N="api/sysLog",j="api/sysConfigs",P="api/mainChart",z="/api/payIfDefines",A="/api/payWays",M="/api/mchDivision",U="/api/agentDivision",Y="/api/transferOrders",H="/api/platStat",$="/api/mchStat",G="/api/mchProductStat",J="/api/passageStat",K="/api/productStat",X="/api/agentStat",W={avatar:n["a"].baseUrl+"/api/ossFiles/avatar",ifBG:n["a"].baseUrl+"/api/ossFiles/ifBG",cert:n["a"].baseUrl+"/api/ossFiles/cert"};function Q(e){return n["a"].request({url:"/api/sysEnts/showTree?sysType="+e,method:"GET"})}function V(e,t,a){return n["a"].request({url:"/api/payOrder/refunds/"+e,method:"POST",data:{refundAmount:t,refundReason:a}})}function Z(e,t){return n["a"].request({url:"api/sysUserRoleRelas/relas/"+e,method:"POST",data:{roleIdListStr:JSON.stringify(t)}})}function ee(){return n["a"].request({url:P+"/twoDayCount",method:"GET"})}function te(){return n["a"].request({url:P+"/realTimeCount",method:"GET"})}function ae(e){return n["a"].request({url:"/api/current/modifyPwd",method:"put",data:e})}function ne(e){return n["a"].request({url:"/api/current/user",method:"put",data:e})}function re(){return n["a"].request({url:"/api/current/user",method:"get"})}function oe(){return n["a"].request({url:"/api/current/getGoogleKey",method:"get"})}function ie(e){return n["a"].request({url:j+"/"+e,method:"GET"})}function se(e,t){return n["a"].request({url:"/api/sysEnts/bySysType",method:"GET",params:{entId:e,sysType:t}})}function ue(e){return n["a"].request({url:"/api/mchNotify/resend/"+e,method:"POST"})}function ce(e){return n["a"].request({url:"/api/passageTest/doPay",method:"POST",data:e})}function le(e,t){return n["a"].request({url:e,method:"POST",data:t,responseType:"arraybuffer"},!0,!1,!0)}},"21a6":function(e,t,a){(function(a){var n,r,o;(function(a,i){r=[],n=i,o="function"===typeof n?n.apply(t,r):n,void 0===o||(e.exports=o)})(0,(function(){"use strict";function t(e,t){return"undefined"==typeof t?t={autoBom:!1}:"object"!=typeof t&&(console.warn("Deprecated: Expected third argument to be a object"),t={autoBom:!t}),t.autoBom&&/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(e.type)?new Blob(["\ufeff",e],{type:e.type}):e}function n(e,t,a){var n=new XMLHttpRequest;n.open("GET",e),n.responseType="blob",n.onload=function(){u(n.response,t,a)},n.onerror=function(){console.error("could not download file")},n.send()}function r(e){var t=new XMLHttpRequest;t.open("HEAD",e,!1);try{t.send()}catch(e){}return 200<=t.status&&299>=t.status}function o(e){try{e.dispatchEvent(new MouseEvent("click"))}catch(n){var t=document.createEvent("MouseEvents");t.initMouseEvent("click",!0,!0,window,0,0,0,80,20,!1,!1,!1,!1,0,null),e.dispatchEvent(t)}}var i="object"==typeof window&&window.window===window?window:"object"==typeof self&&self.self===self?self:"object"==typeof a&&a.global===a?a:void 0,s=i.navigator&&/Macintosh/.test(navigator.userAgent)&&/AppleWebKit/.test(navigator.userAgent)&&!/Safari/.test(navigator.userAgent),u=i.saveAs||("object"!=typeof window||window!==i?function(){}:"download"in HTMLAnchorElement.prototype&&!s?function(e,t,a){var s=i.URL||i.webkitURL,u=document.createElement("a");t=t||e.name||"download",u.download=t,u.rel="noopener","string"==typeof e?(u.href=e,u.origin===location.origin?o(u):r(u.href)?n(e,t,a):o(u,u.target="_blank")):(u.href=s.createObjectURL(e),setTimeout((function(){s.revokeObjectURL(u.href)}),4e4),setTimeout((function(){o(u)}),0))}:"msSaveOrOpenBlob"in navigator?function(e,a,i){if(a=a||e.name||"download","string"!=typeof e)navigator.msSaveOrOpenBlob(t(e,i),a);else if(r(e))n(e,a,i);else{var s=document.createElement("a");s.href=e,s.target="_blank",setTimeout((function(){o(s)}))}}:function(e,t,a,r){if(r=r||open("","_blank"),r&&(r.document.title=r.document.body.innerText="downloading..."),"string"==typeof e)return n(e,t,a);var o="application/octet-stream"===e.type,u=/constructor/i.test(i.HTMLElement)||i.safari,c=/CriOS\/[\d]+/.test(navigator.userAgent);if((c||o&&u||s)&&"undefined"!=typeof FileReader){var l=new FileReader;l.onloadend=function(){var e=l.result;e=c?e:e.replace(/^data:[^;]*;/,"data:attachment/file;"),r?r.location.href=e:location=e,r=null},l.readAsDataURL(e)}else{var d=i.URL||i.webkitURL,f=d.createObjectURL(e);r?r.location=f:location.href=f,r=null,setTimeout((function(){d.revokeObjectURL(f)}),4e4)}});i.saveAs=u.saveAs=u,e.exports=u}))}).call(this,a("c8ba"))},"27fc":function(e,t,a){},"3cbe":function(e,t,a){"use strict";a("a415")},"4f53":function(e,t,a){"use strict";var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"jee-text-up table-head-layout"},[a("a-input",{attrs:{required:"required",value:e.msg},on:{input:function(t){return e.$emit("input",t.target.value)}}}),a("label",[e._v(e._s(e.placeholder))])],1)},r=[],o={name:"JeepayTextUp",props:{msg:{type:String,default:""},placeholder:{type:String,default:""}}},i=o,s=(a("8bf8"),a("2877")),u=Object(s["a"])(i,n,r,!1,null,"4708ca2b",null);t["a"]=u.exports},"5d5e":function(e,t,a){"use strict";a("d81d");var n,r,o={name:"JeepayTableColumns",render:function(e,t){var a=arguments[0],n=[];if(this.$slots.default.map((function(e){return e.tag&&n.push(e),!1})),n.length<=4)return e("div",{style:"display:flex; justify-content: space-evenly;"},n);for(var r=[n[0],n[1],n[2]],o=[],i=3;i<n.length;i++)o.push(a("a-menu-item",[n[i]]));return a("div",{style:"display:flex; justify-content: space-evenly;"},[" ",r,a("a-dropdown",[a("a-button",{class:"ant-dropdown-link",attrs:{type:"link"},style:""},["更多",a("a-icon",{attrs:{type:"down"}})]),a("a-menu",{slot:"overlay"},[o])])])}},i=o,s=(a("3cbe"),a("2877")),u=Object(s["a"])(i,n,r,!1,null,"d8995c5c",null);t["a"]=u.exports},6722:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("page-header-wrapper",[a("a-card",[a("div",{staticClass:"table-page-search-wrapper"},[a("a-form",{staticClass:"table-head-ground",attrs:{layout:"inline"}},[a("div",{staticClass:"table-layer"},[a("a-form-item",{staticClass:"table-head-layout",staticStyle:{"max-width":"350px","min-width":"300px"},attrs:{label:""}},[a("a-range-picker",{attrs:{"show-time":{format:"HH:mm:ss"},format:"YYYY-MM-DD HH:mm:ss","disabled-date":e.disabledDate,ranges:e.ranges},on:{change:e.onChange},model:{value:e.selectedRange,callback:function(t){e.selectedRange=t},expression:"selectedRange"}},[a("a-icon",{attrs:{slot:"suffixIcon",type:"sync"},slot:"suffixIcon"})],1)],1),a("jeepay-text-up",{attrs:{placeholder:"代理商商户号",msg:e.searchData.agentNo},model:{value:e.searchData.agentNo,callback:function(t){e.$set(e.searchData,"agentNo",t)},expression:"searchData.agentNo"}}),a("jeepay-text-up",{attrs:{placeholder:"代理商名称",msg:e.searchData.agentName},model:{value:e.searchData.agentName,callback:function(t){e.$set(e.searchData,"agentName",t)},expression:"searchData.agentName"}}),a("jeepay-text-up",{attrs:{placeholder:"订单号",msg:e.searchData.payOrderId},model:{value:e.searchData.payOrderId,callback:function(t){e.$set(e.searchData,"payOrderId",t)},expression:"searchData.payOrderId"}}),a("a-form-item",{staticClass:"table-head-layout",attrs:{label:""}},[a("a-select",{attrs:{placeholder:"资金变动方向","default-value":"0"},model:{value:e.searchData.fundDirection,callback:function(t){e.$set(e.searchData,"fundDirection",t)},expression:"searchData.fundDirection"}},[a("a-select-option",{attrs:{value:"0"}},[e._v("全部")]),a("a-select-option",{attrs:{value:"1"}},[e._v("加款")]),a("a-select-option",{attrs:{value:"2"}},[e._v("减款")])],1)],1),a("a-form-item",{staticClass:"table-head-layout",attrs:{label:""}},[a("a-select",{attrs:{placeholder:"业务类型","default-value":"0"},model:{value:e.searchData.bizType,callback:function(t){e.$set(e.searchData,"bizType",t)},expression:"searchData.bizType"}},[a("a-select-option",{attrs:{value:"0"}},[e._v("全部")]),a("a-select-option",{attrs:{value:"1"}},[e._v("分润")]),a("a-select-option",{attrs:{value:"2"}},[e._v("提现")]),a("a-select-option",{attrs:{value:"3"}},[e._v("调账")]),a("a-select-option",{attrs:{value:"4"}},[e._v("提现驳回")]),a("a-select-option",{attrs:{value:"6"}},[e._v("测试冲正")])],1)],1),a("span",{staticClass:"table-page-search-submitButtons"},[a("a-button",{attrs:{type:"primary",icon:"search",loading:e.btnLoading},on:{click:e.queryFunc}},[e._v("搜索")]),a("a-button",{staticStyle:{"margin-left":"8px"},attrs:{icon:"reload"},on:{click:e.resetSearch}},[e._v("重置")]),a("a-button",{staticStyle:{"margin-left":"8px"},attrs:{type:"danger",icon:"download"},on:{click:e.exportExcel}},[e._v("导出")])],1)],1)])],1),a("JeepayTable",{ref:"infoTable",attrs:{initData:!1,reqTableDataFunc:e.reqTableDataFunc,tableColumns:e.tableColumns,searchData:e.searchData,pageSize:50,rowKey:"agentAccountHistoryId"},on:{btnLoadClose:function(t){e.btnLoading=!1}},scopedSlots:e._u([{key:"isvNameSlot",fn:function(t){var n=t.record;return[a("b",[e._v(e._s(n.agentName))])]}},{key:"beforeSlot",fn:function(t){var n=t.record;return[a("b",[e._v(e._s((n.beforeBalance/100).toFixed(2)))])]}},{key:"amountSlot",fn:function(t){var n=t.record;return[a("b",{style:{color:n.amount>0?"#4BD884":"#DB4B4B"}},[e._v(e._s(n.amount>0?"+"+(n.amount/100).toFixed(2):(n.amount/100).toFixed(2)))])]}},{key:"afterSlot",fn:function(t){var n=t.record;return[a("b",[e._v(e._s((n.afterBalance/100).toFixed(2)))])]}},{key:"bizTypeSlot",fn:function(t){var n=t.record;return[0===n.bizType?a("span",[e._v(" - ")]):1===n.bizType?a("a-tag",{attrs:{color:"green"}},[e._v("分润")]):2===n.bizType?a("a-tag",{attrs:{color:"blue"}},[e._v("提现")]):3===n.bizType?a("a-tag",{attrs:{color:"orange"}},[e._v("调账(ID:"+e._s(n.createdUid)+"--"+e._s(n.createdLoginName)+")")]):4===n.bizType?a("a-tag",{attrs:{color:"red"}},[e._v(" 提现驳回 ")]):6===n.bizType?a("a-tag",{attrs:{color:"#F03B44"}},[e._v(" 测试冲正 ")]):a("span",[e._v("未知")])]}}])})],1)],1)},r=[],o=a("f339"),i=a("4f53"),s=a("5d5e"),u=a("0fea"),c=a("c1df"),l=a.n(c),d=a("21a6"),f=[{key:"agentName",width:"150px",title:"代理商名称",fixed:"left",scopedSlots:{customRender:"isvNameSlot"}},{key:"agentNo",title:"代理商户号",dataIndex:"agentNo",width:"150px",fixed:"left"},{key:"beforeBalance",title:"变更前余额(￥)",width:"180px",scopedSlots:{customRender:"beforeSlot"}},{key:"amount",width:"150px",title:"变更金额(￥)",scopedSlots:{customRender:"amountSlot"}},{key:"afterBalance",title:"变更后余额(￥)",width:"180px",scopedSlots:{customRender:"afterSlot"}},{key:"payOrderId",title:"订单号",dataIndex:"payOrderId",width:"220px"},{key:"bizType",title:"业务类型",scopedSlots:{customRender:"bizTypeSlot"}},{key:"createdAt",dataIndex:"createdAt",title:"创建日期"},{key:"remark",dataIndex:"remark",title:"备注"}],p={name:"IsvHistoryList",components:{JeepayTable:o["a"],JeepayTableColumns:s["a"],JeepayTextUp:i["a"]},data:function(){return{btnLoading:!1,tableColumns:f,searchData:{},selectedRange:[],ranges:{"今天":[l()().startOf("day"),l()().endOf("day")],"昨天":[l()().subtract(1,"day").startOf("day"),l()().subtract(1,"day").endOf("day")],"近一周":[l()().subtract(1,"week").startOf("day"),l()().endOf("day")]}}},mounted:function(){this.selectedRange=[l()().startOf("day"),l()().endOf("day")],this.searchData.createdStart=this.selectedRange[0].format("YYYY-MM-DD HH:mm:ss"),this.searchData.createdEnd=this.selectedRange[1].format("YYYY-MM-DD HH:mm:ss"),this.$refs.infoTable.refTable(!0)},methods:{queryFunc:function(){this.btnLoading=!0,this.$refs.infoTable.refTable(!0)},reqTableDataFunc:function(e){return u["cb"].list(u["g"],e)},disabledDate:function(e){return e&&e>l()().endOf("day")},onChange:function(e,t){this.searchData.createdStart=t[0],this.searchData.createdEnd=t[1],this.$refs.infoTable.refTable(!0)},resetSearch:function(){this.searchData={},this.selectedRange=[]},exportExcel:function(){Object(u["R"])("/api/agentHistory/exportExcel",this.searchData).then((function(e){var t=new Blob([e],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});Object(d["saveAs"])(t,l()().format("YYYY-MM-DD")+"-代理资金流水.xlsx")})).catch((function(e){var t=new Blob([e],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});Object(d["saveAs"])(t,l()().format("YYYY-MM-DD")+"-代理资金流水.xlsx")}))}}},m=p,h=a("2877"),y=Object(h["a"])(m,n,r,!1,null,null,null);t["default"]=y.exports},"8bf8":function(e,t,a){"use strict";a("eaa4")},a415:function(e,t,a){},eaa4:function(e,t,a){},f339:function(e,t,a){"use strict";var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("a-table",{attrs:{columns:e.tableColumns,"data-source":e.apiResData.records,pagination:e.pagination,loading:e.showLoading,"row-selection":e.rowSelection,rowKey:e.rowKey,scroll:{x:e.scrollX},customRow:function(t,a){return e.tableRowCrossColor?{style:{"background-color":a%2==0?"#FCFCFC":"#FFFFFF"}}:{}}},on:{change:e.handleTableChange},scopedSlots:e._u([e._l(e.columnsCustomSlots,(function(t){return{key:t.customRender,fn:function(a){return[e._t(t.customRender,null,{record:a})]}}}))],null,!0)})],1)},r=[],o=a("5530"),i=(a("a9e3"),a("d81d"),a("4de4"),{name:"JeepayTable",props:{initData:{type:Boolean,default:!0},tableColumns:Array,reqTableDataFunc:{type:Function},currentChange:{type:Function,default:function(e,t){}},searchData:Object,pageSize:{type:Number,default:20},rowSelection:Object,rowKey:{type:[String,Function]},scrollX:{type:Number,default:500},tableRowCrossColor:{type:Boolean,default:!1}},data:function(){return{apiResData:{total:0,records:[]},iPage:{pageNumber:1,pageSize:this.pageSize},pagination:{total:0,current:1,pageSizeOptions:["10","20","50","100"],pageSize:this.pageSize,showSizeChanger:!0,showTotal:function(e){return"共".concat(e,"条")}},showLoading:!1}},computed:{columnsCustomSlots:function(){return this.tableColumns.filter((function(e){return e.scopedSlots})).map((function(e){return e.scopedSlots}))}},mounted:function(){this.initData&&this.refTable(!0)},methods:{handleTableChange:function(e,t,a){this.pagination=e,this.iPage=Object(o["a"])({pageSize:e.pageSize,pageNumber:e.current,sortField:a.columnKey,sortOrder:a.order},t),this.refTable()},refTable:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]&&arguments[0],a=this;t&&(this.iPage.pageNumber=1,this.pagination.current=1),this.showLoading=!0,this.reqTableDataFunc(Object.assign({},this.iPage,this.searchData)).then((function(t){e.pagination.total=t.total,e.apiResData=t,e.showLoading=!1,0===t.records.length&&e.iPage.pageNumber>1&&a.$nextTick((function(){var n=t.total/e.iPage.pageSize+(t.total%e.iPage.pageSize===0?0:1);if(0===n)return!1;var r=e.iPage.pageNumber-1>n?n:e.iPage.pageNumber-1;e.iPage.pageNumber=r,e.pagination.current=r,a.refTable(!1)})),a.$emit("btnLoadClose")})).catch((function(t){e.showLoading=!1,a.$emit("btnLoadClose")}))}}}),s=i,u=(a("f705"),a("2877")),c=Object(u["a"])(s,n,r,!1,null,null,null);t["a"]=c.exports},f705:function(e,t,a){"use strict";a("27fc")}}]);