(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4e838df8"],{"09ef":function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=this,a=t.$createElement,s=t._self._c||a;return s("a-drawer",{attrs:{visible:t.visible,title:"商户-通道绑定","body-style":{paddingBottom:"80px"},width:"60%"},on:{close:t.onClose}},[s("div",{staticClass:"table-page-search-wrapper"},[s("a-form",{staticClass:"table-head-ground",attrs:{layout:"inline"}},[s("a-row",{attrs:{justify:"space-between",type:"flex"}},[s("a-col",{attrs:{sm:12}},[s("a-descriptions",[s("a-descriptions-item",{attrs:{label:"商户号"}},[s("b",{staticStyle:{color:"#1a53ff"}},[t._v(t._s(t.mchNo))])])],1)],1),s("a-col",{attrs:{sm:12}},[s("a-descriptions",[s("a-descriptions-item",{attrs:{label:"商户名称"}},[s("b",[t._v(t._s(t.mchName))])])],1)],1),s("a-col",{attrs:{sm:12}},[s("a-descriptions",[s("a-descriptions-item",{attrs:{label:"商户代理"}},[s("span",{staticStyle:{color:"#1a53ff"}},[t._v(t._s(""==t.mchInfo.agentNo?"无":t.mchInfo.agentNo))])])],1)],1),s("a-col",{attrs:{sm:12}},[s("a-descriptions",[s("a-descriptions-item",{attrs:{label:"代理名称"}},[t._v(" "+t._s(void 0==t.mchInfo.agentName||""==t.mchInfo.agentName?"无代理":t.mchInfo.agentName)+" ")])],1)],1)],1),s("br"),s("div",{staticClass:"table-layer"},[s("jeepay-text-up",{attrs:{placeholder:"通道ID",msg:t.searchData.payPassageId},model:{value:t.searchData.payPassageId,callback:function(e){t.$set(t.searchData,"payPassageId",e)},expression:"searchData.payPassageId"}}),s("jeepay-text-up",{attrs:{placeholder:"通道名",msg:t.searchData.payPassageName},model:{value:t.searchData.payPassageName,callback:function(e){t.$set(t.searchData,"payPassageName",e)},expression:"searchData.payPassageName"}}),s("a-form-item",{staticClass:"table-head-layout",attrs:{label:""}},[s("a-select",{attrs:{placeholder:"通道是否存在代理","default-value":""},model:{value:t.searchData.haveAgent,callback:function(e){t.$set(t.searchData,"haveAgent",e)},expression:"searchData.haveAgent"}},[s("a-select-option",{attrs:{value:""}},[t._v("全部")]),s("a-select-option",{attrs:{value:"0"}},[t._v("无")]),s("a-select-option",{attrs:{value:"1"}},[t._v("有")])],1)],1),s("span",{staticClass:"table-page-search-submitButtons",staticStyle:{"flex-grow":"0","flex-shrink":"0"}},[s("a-button",{attrs:{type:"primary",icon:"search",loading:t.btnLoading},on:{click:t.queryFunc}},[t._v("查询")]),s("a-button",{staticStyle:{"margin-left":"8px"},attrs:{icon:"reload"},on:{click:function(){return e.searchData={mchNo:t.mchNo}}}},[t._v("重置")])],1),s("br"),s("br"),s("span",{staticClass:"table-page-search-submitButtons",staticStyle:{"flex-grow":"0","flex-shrink":"0",width:"100%"}},[[s("a-popconfirm",{attrs:{title:"确认全部绑定么?","ok-text":"确认","cancel-text":"取消"},on:{confirm:t.blindAll}},[s("a-button",{attrs:{type:"primary",icon:"check",loading:t.btnLoading}},[t._v("一键全绑定")])],1)],[s("a-popconfirm",{attrs:{title:"确认全部解绑么?","ok-text":"确认","cancel-text":"取消"},on:{confirm:t.unBlindAll}},[s("a-button",{staticStyle:{"margin-left":"8px"},attrs:{icon:"close",loading:t.btnLoading}},[t._v("一键全解绑")])],1)],[s("a-button",{staticStyle:{"margin-left":"8px"},attrs:{type:"dashed",icon:"retweet",loading:t.btnLoading},on:{click:t.setAllMch}},[t._v("批量配置")])]],2)],1)],1)],1),s("JeepayTable",{ref:"mchPassageTable",attrs:{initData:!0,reqTableDataFunc:t.reqTableDataFunc,tableColumns:t.tableColumns,searchData:t.searchData,rowKey:"payPassageId",rowSelection:t.rowSelection},on:{btnLoadClose:function(e){t.btnLoading=!1}},scopedSlots:t._u([{key:"nameSlot",fn:function(e){var a=e.record;return[s("b",{staticStyle:{"font-weight":"bold",color:"#1a53ff"}},[t._v("["+t._s(a.payPassageId)+"]")]),s("b",[t._v(t._s(a.payPassageName))])]}},{key:"agentSlot",fn:function(e){var a=e.record;return[s("span",{staticStyle:{color:"#1A79FF","font-size":"12px"}},[t._v(t._s(""!=a.passageAgentNo?"["+a.passageAgentNo+"]":""))]),s("span",{staticStyle:{"font-size":"12px"}},[t._v(t._s(a.passageAgentName))])]}},{key:"stateSlot",fn:function(e){var t=e.record;return[s("a-badge",{attrs:{status:0===t.state?"error":"processing",text:0===t.state?"未绑定":"已绑定"}})]}},{key:"mchRateSlot",fn:function(e){var a=e.record;return[s("b",[t._v(t._s((100*a.rate).toFixed(2))+"%")])]}},{key:"opSlot",fn:function(e){var a=e.record;return[s("JeepayTableColumns",[s("a-button",{attrs:{type:"link"},on:{click:function(e){return t.blindSelect(a)}}},[t._v("绑定")]),s("a-button",{staticStyle:{color:"#f5222d"},attrs:{type:"link"},on:{click:function(e){return t.unBlindSelect(a)}}},[t._v("解绑")])],1)]}}])}),[s("a-modal",{attrs:{title:"批量配置通道绑定"},on:{ok:t.confirmSetAll},model:{value:t.isShowAllSetModal,callback:function(e){t.isShowAllSetModal=e},expression:"isShowAllSetModal"}},[s("a-form-model",{attrs:{"label-col":{span:6},"wrapper-col":{span:15}}},[s("a-form-model-item",{attrs:{label:"已选择通道"}},[s("b",[t._v(t._s(t.selectedIds.length))])]),s("a-form-model-item",{attrs:{label:"状态",prop:"state"}},[s("a-radio-group",{model:{value:t.changeAllState,callback:function(e){t.changeAllState=e},expression:"changeAllState"}},[s("a-radio",{attrs:{value:1}},[t._v(" 启用 ")]),s("a-radio",{attrs:{value:0}},[t._v(" 禁用 ")])],1)],1)],1)],1)],s("div",{staticClass:"drawer-btn-center"},[s("a-button",{staticStyle:{"margin-right":"8px"},style:{marginRight:"8px"},attrs:{icon:"close"},on:{click:t.onClose}},[t._v(" 关闭 ")])],1)],2)},o=[],n=(a("380f"),a("f64c")),c=(a("159b"),a("f339")),r=a("4f53"),l=a("5d5e"),i=a("0fea"),d=[{key:"nameSlot",fixed:"left",width:"350px",title:"通道名称",scopedSlots:{customRender:"nameSlot"}},{key:"agentSlot",title:"通道代理",scopedSlots:{customRender:"agentSlot"}},{key:"state",title:"状态",width:"100px",scopedSlots:{customRender:"stateSlot"}},{key:"mchRate",title:"通道费率",scopedSlots:{customRender:"mchRateSlot"}},{key:"op",title:"操作",width:"150px",fixed:"right",align:"center",scopedSlots:{customRender:"opSlot"}}],u={name:"MchPassageEdit",components:{JeepayTable:c["a"],JeepayTableColumns:l["a"],JeepayTextUp:r["a"]},data:function(){return{visible:!1,btnLoading:!1,tableColumns:d,searchData:{},value:"''",selectMchPassage:{},mchNo:"",mchName:"",mchInfo:{},selectedIds:[],isShowAllSetModal:!1,changeAllState:0}},mounted:function(){},computed:{rowSelection:function(){var e=this;return{onChange:function(t,a){e.selectedIds=[],a.forEach((function(t){e.selectedIds.push(t.payPassageId)}))}}}},methods:{show:function(e){this.visible=!0,this.mchNo=e.mchNo,this.mchName=e.mchName,this.searchData.mchNo=e.mchNo,this.mchInfo=e,void 0!==this.$refs.mchPassageTable&&this.$refs.mchPassageTable.refTable(!0),this.selectedIds=[]},queryFunc:function(){this.btnLoading=!0,this.$refs.mchPassageTable.refTable(!0)},reqTableDataFunc:function(e){return i["cb"].list(i["u"],e)},blindSelect:function(e){var t=this,a={};a.payPassageId=e.payPassageId,a.state=1,a.mchNo=this.mchNo,i["cb"].updateById(i["u"],"",a).then((function(e){t.$refs.mchPassageTable.refTable(!0),t.$message.success("修改成功")}))},unBlindSelect:function(e){var t=this,a={};a.payPassageId=e.payPassageId,a.state=0,a.mchNo=this.mchNo,i["cb"].updateById(i["u"],"",a).then((function(e){t.$refs.mchPassageTable.refTable(!0),t.$message.success("修改成功")}))},blindAll:function(){var e=this;this.btnLoading=!0,i["cb"].postNormal(i["u"]+"/blindAll",this.mchNo).then((function(t){setTimeout((function(){e.btnLoading=!1,e.$refs.mchPassageTable.refTable(!0),e.$message.success("修改成功")}),500)}))},unBlindAll:function(){var e=this;this.btnLoading=!0,i["cb"].postNormal(i["u"]+"/unBlindAll",this.mchNo).then((function(t){setTimeout((function(){e.btnLoading=!1,e.$refs.mchPassageTable.refTable(!0),e.$message.success("修改成功")}),500)}))},onClose:function(){this.visible=!1,this.selectedIds=[],this.searchData={}},setAllMch:function(){0!==this.selectedIds.length?this.isShowAllSetModal=!0:n["a"].error("请先选择要配置的通道")},confirmSetAll:function(){this.btnLoading=!0;var e=this,t={};t.selectedIds=this.selectedIds,t.changeAllState=this.changeAllState,i["cb"].postDataNormal(i["u"]+"/setAll",this.mchNo,t).then((function(t){setTimeout((function(){e.isShowAllSetModal=!1,e.btnLoading=!1,e.$refs.mchPassageTable.refTable(!0),e.$message.success("修改成功")}),500)}))}}},f=u,h=a("2877"),m=Object(h["a"])(f,s,o,!1,null,null,null);t["default"]=m.exports},1652:function(e,t,a){},"1a6d":function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-drawer",{attrs:{visible:e.visible,title:"商户详情","body-style":{paddingBottom:"80px"},width:"40%"},on:{close:e.onClose}},[a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{sm:12}},[a("a-descriptions",[a("a-descriptions-item",{attrs:{label:"商户号"}},[e._v(" "+e._s(e.detailData.mchNo)+" ")])],1)],1),a("a-col",{attrs:{sm:12}},[a("a-descriptions",[a("a-descriptions-item",{attrs:{label:"商户名称"}},[e._v(" "+e._s(e.detailData.mchName)+" ")])],1)],1),a("a-col",{attrs:{sm:12}},[a("a-descriptions",[a("a-descriptions-item",{attrs:{label:"登录名"}},[e._v(" "+e._s(e.detailData.loginUserName)+" ")])],1)],1),a("a-divider"),a("a-col",{attrs:{sm:12}},[a("a-descriptions",[a("a-descriptions-item",{attrs:{label:"代理商户号"}},[e._v(" "+e._s(e.detailData.agentNo)+" ")])],1)],1),a("a-divider"),a("a-col",{attrs:{sm:12}},[a("a-descriptions",[a("a-descriptions-item",{attrs:{label:"状态"}},[a("a-tag",{attrs:{color:1===e.detailData.state?"#4BD884":"#F03B44"}},[e._v(" "+e._s(0===e.detailData.state?"禁用":1===e.detailData.state?"启用":"未知")+" ")])],1)],1)],1)],1),a("a-row",{attrs:{justify:"start",type:"flex"}},[a("a-col",{attrs:{sm:24}},[a("a-form-model-item",{attrs:{label:"备注"}},[a("a-input",{staticStyle:{height:"50px"},attrs:{type:"textarea",disabled:"disabled"},model:{value:e.detailData.remark,callback:function(t){e.$set(e.detailData,"remark",t)},expression:"detailData.remark"}})],1)],1)],1)],1)},o=[],n=a("0fea"),c={props:{callbackFunc:{type:Function}},data:function(){return{btnLoading:!1,detailData:{},recordId:null,visible:!1,isvList:null,isvName:""}},created:function(){},methods:{show:function(e){this.detailData={state:1,type:1},void 0!==this.$refs.infoFormModel&&this.$refs.infoFormModel.resetFields();var t=this;t.recordId=e,n["cb"].getById(n["r"],e).then((function(e){t.detailData=e})),n["cb"].list(n["h"],{pageSize:null}).then((function(e){t.isvList=e.records;for(var a=0;a<t.isvList.length;a++)t.detailData.isvNo===t.isvList[a].isvNo&&(t.isvName=t.isvList[a].isvName)})),this.visible=!0},onClose:function(){this.visible=!1}}},r=c,l=a("2877"),i=Object(l["a"])(r,s,o,!1,null,null,null);t["default"]=i.exports},"21a6":function(e,t,a){(function(a){var s,o,n;(function(a,c){o=[],s=c,n="function"===typeof s?s.apply(t,o):s,void 0===n||(e.exports=n)})(0,(function(){"use strict";function t(e,t){return"undefined"==typeof t?t={autoBom:!1}:"object"!=typeof t&&(console.warn("Deprecated: Expected third argument to be a object"),t={autoBom:!t}),t.autoBom&&/^\s*(?:text\/\S*|application\/xml|\S*\/\S*\+xml)\s*;.*charset\s*=\s*utf-8/i.test(e.type)?new Blob(["\ufeff",e],{type:e.type}):e}function s(e,t,a){var s=new XMLHttpRequest;s.open("GET",e),s.responseType="blob",s.onload=function(){l(s.response,t,a)},s.onerror=function(){console.error("could not download file")},s.send()}function o(e){var t=new XMLHttpRequest;t.open("HEAD",e,!1);try{t.send()}catch(e){}return 200<=t.status&&299>=t.status}function n(e){try{e.dispatchEvent(new MouseEvent("click"))}catch(s){var t=document.createEvent("MouseEvents");t.initMouseEvent("click",!0,!0,window,0,0,0,80,20,!1,!1,!1,!1,0,null),e.dispatchEvent(t)}}var c="object"==typeof window&&window.window===window?window:"object"==typeof self&&self.self===self?self:"object"==typeof a&&a.global===a?a:void 0,r=c.navigator&&/Macintosh/.test(navigator.userAgent)&&/AppleWebKit/.test(navigator.userAgent)&&!/Safari/.test(navigator.userAgent),l=c.saveAs||("object"!=typeof window||window!==c?function(){}:"download"in HTMLAnchorElement.prototype&&!r?function(e,t,a){var r=c.URL||c.webkitURL,l=document.createElement("a");t=t||e.name||"download",l.download=t,l.rel="noopener","string"==typeof e?(l.href=e,l.origin===location.origin?n(l):o(l.href)?s(e,t,a):n(l,l.target="_blank")):(l.href=r.createObjectURL(e),setTimeout((function(){r.revokeObjectURL(l.href)}),4e4),setTimeout((function(){n(l)}),0))}:"msSaveOrOpenBlob"in navigator?function(e,a,c){if(a=a||e.name||"download","string"!=typeof e)navigator.msSaveOrOpenBlob(t(e,c),a);else if(o(e))s(e,a,c);else{var r=document.createElement("a");r.href=e,r.target="_blank",setTimeout((function(){n(r)}))}}:function(e,t,a,o){if(o=o||open("","_blank"),o&&(o.document.title=o.document.body.innerText="downloading..."),"string"==typeof e)return s(e,t,a);var n="application/octet-stream"===e.type,l=/constructor/i.test(c.HTMLElement)||c.safari,i=/CriOS\/[\d]+/.test(navigator.userAgent);if((i||n&&l||r)&&"undefined"!=typeof FileReader){var d=new FileReader;d.onloadend=function(){var e=d.result;e=i?e:e.replace(/^data:[^;]*;/,"data:attachment/file;"),o?o.location.href=e:location=e,o=null},d.readAsDataURL(e)}else{var u=c.URL||c.webkitURL,f=u.createObjectURL(e);o?o.location=f:location.href=f,o=null,setTimeout((function(){u.revokeObjectURL(f)}),4e4)}});c.saveAs=l.saveAs=l,e.exports=l}))}).call(this,a("c8ba"))},"2be6":function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-drawer",{staticClass:"drawer-width",attrs:{maskClosable:!1,visible:e.visible,title:e.isAdd?"新增商户":"修改商户","body-style":{paddingBottom:"80px"},width:"40%"},on:{close:e.onClose}},[e.visible?a("a-form-model",{ref:"infoFormModel",attrs:{model:e.saveObject,layout:"vertical",rules:e.rules}},[a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:10}},[a("a-form-model-item",{attrs:{label:"商户名称",prop:"mchName"}},[a("a-input",{attrs:{placeholder:"请输入商户名称",disabled:"测试商户"===e.saveObject.mchName},model:{value:e.saveObject.mchName,callback:function(t){e.$set(e.saveObject,"mchName",t)},expression:"saveObject.mchName"}})],1)],1),a("a-col",{attrs:{span:10}},[a("a-form-model-item",{attrs:{label:"登录名",prop:"loginUserName"}},[a("a-input",{attrs:{placeholder:"请输入商户登录名",disabled:!this.isAdd},model:{value:e.saveObject.loginUserName,callback:function(t){e.$set(e.saveObject,"loginUserName",t)},expression:"saveObject.loginUserName"}})],1)],1)],1),a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:24}},[a("a-form-model-item",{attrs:{label:"代理商号",prop:"isvNo"}},[a("a-select",{attrs:{placeholder:"请选择代理商",allowClear:!0},model:{value:e.saveObject.agentNo,callback:function(t){e.$set(e.saveObject,"agentNo",t)},expression:"saveObject.agentNo"}},e._l(e.isvList,(function(t){return a("a-select-option",{key:t.agentNo,attrs:{value:t.agentNo}},[e._v(" "+e._s(t.agentName+" [ ID: "+t.agentNo+" ]")+" ")])})),1)],1)],1)],1),a("a-row",[a("a-col",{attrs:{span:10}},[a("a-form-model-item",{attrs:{label:"状态",prop:"state"}},[a("a-radio-group",{model:{value:e.saveObject.state,callback:function(t){e.$set(e.saveObject,"state",t)},expression:"saveObject.state"}},[a("a-radio",{attrs:{value:1}},[e._v(" 启用 ")]),a("a-radio",{attrs:{value:0}},[e._v(" 禁用 ")])],1)],1)],1)],1),a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:24}},[a("a-form-model-item",{attrs:{label:"商户私钥",prop:"secret"}},[a("a-input",{attrs:{placeholder:e.appSecret_ph,type:"textarea"},model:{value:e.saveObject.secret,callback:function(t){e.$set(e.saveObject,"secret",t)},expression:"saveObject.secret"}}),a("a-button",{attrs:{type:"primary",ghost:""},on:{click:function(t){return e.randomKey(!1,128,0)}}},[a("a-icon",{attrs:{type:"file-sync"}}),e._v("随机生成私钥")],1)],1)],1)],1),a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:24}},[a("a-form-model-item",{attrs:{label:"备注",prop:"remark"}},[a("a-input",{attrs:{placeholder:"请输入备注",type:"textarea"},model:{value:e.saveObject.remark,callback:function(t){e.$set(e.saveObject,"remark",t)},expression:"saveObject.remark"}})],1)],1)],1),a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:24}},[e.resetIsShow?a("a-divider",{attrs:{orientation:"left"}},[a("a-tag",{attrs:{color:"#FF4B33"}},[e._v(" 账户安全 ")])],1):e._e()],1)],1),a("div",[a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:10}},[e.resetIsShow?a("a-form-model-item",{attrs:{label:""}},[e._v(" 重置密码并解绑谷歌验证："),a("a-checkbox",{model:{value:e.sysPassword.resetPass,callback:function(t){e.$set(e.sysPassword,"resetPass",t)},expression:"sysPassword.resetPass"}})],1):e._e()],1),a("a-col",{attrs:{span:10}},[e.sysPassword.resetPass?a("a-form-model-item",{attrs:{label:""}},[e._v(" 恢复默认密码："),a("a-checkbox",{on:{click:e.isResetPass},model:{value:e.sysPassword.defaultPass,callback:function(t){e.$set(e.sysPassword,"defaultPass",t)},expression:"sysPassword.defaultPass"}})],1):e._e()],1)],1)],1),e.sysPassword.resetPass?a("div",[a("div",{directives:[{name:"show",rawName:"v-show",value:!this.sysPassword.defaultPass,expression:"!this.sysPassword.defaultPass"}]},[a("a-row",{attrs:{justify:"space-between",type:"flex"}},[a("a-col",{attrs:{span:10}},[a("a-form-model-item",{attrs:{label:"新密码：",prop:"newPwd"}},[a("a-input-password",{attrs:{autocomplete:"new-password",disabled:e.sysPassword.defaultPass},model:{value:e.newPwd,callback:function(t){e.newPwd=t},expression:"newPwd"}})],1)],1),a("a-col",{attrs:{span:10}},[a("a-form-model-item",{attrs:{label:"确认新密码：",prop:"confirmPwd"}},[a("a-input-password",{attrs:{autocomplete:"new-password",disabled:e.sysPassword.defaultPass},model:{value:e.sysPassword.confirmPwd,callback:function(t){e.$set(e.sysPassword,"confirmPwd",t)},expression:"sysPassword.confirmPwd"}})],1)],1)],1)],1)]):e._e()],1):e._e(),a("div",{staticClass:"drawer-btn-center"},[a("a-button",{staticStyle:{"margin-right":"8px"},style:{marginRight:"8px"},attrs:{icon:"close"},on:{click:e.onClose}},[e._v(" 取消 ")]),a("a-button",{attrs:{type:"primary",icon:"check",loading:e.btnLoading},on:{click:e.handleOkFunc}},[e._v(" 保存 ")])],1)],1)},o=[],n=a("0fea"),c=a("27ae"),r={props:{callbackFunc:{type:Function}},data:function(){var e=this,t=function(e,t,a){a()};return{newPwd:"",resetIsShow:!1,sysPassword:{resetPass:!1,defaultPass:!0,confirmPwd:""},btnLoading:!1,isAdd:!0,saveObject:{},recordId:null,visible:!1,isvList:null,appSecret_ph:"请输入",rules:{mchName:[{required:!0,message:"请输入商户名称",trigger:"blur"}],loginUserName:[{required:!0,pattern:/^[a-zA-Z][a-zA-Z0-9]{5,17}$/,message:"请输入字母开头，长度为6-18位的登录名",trigger:"blur"}],isvNo:[{validator:t,trigger:"blur"}],secret:[{required:!0,pattern:/^[a-zA-Z0-9]{128}$/,message:"请点击生成私钥",trigger:"blur"}],newPwd:[{required:!1,trigger:"blur"},{validator:function(t,a,s){e.sysPassword.defaultPass||(e.newPwd.length<6||e.newPwd.length>12)&&s("请输入6-12位新密码"),s()}}],confirmPwd:[{required:!1,trigger:"blur"},{validator:function(t,a,s){e.sysPassword.defaultPass||e.newPwd===e.sysPassword.confirmPwd?s():s("新密码与确认密码不一致")}}]}}},created:function(){},methods:{show:function(e){this.isAdd=!e,this.saveObject={state:1,secret:""},void 0!==this.$refs.infoFormModel&&this.$refs.infoFormModel.resetFields();var t=this;n["cb"].list(n["h"],{pageSize:-1,state:1}).then((function(e){t.isvList=e.records})),this.isAdd?t.visible=!0:(console.log(555),t.resetIsShow=!0,t.recordId=e,n["cb"].getById(n["r"],e).then((function(e){t.saveObject=e})),this.visible=!0)},randomKey:function(e,t,a){var s="",o=t,n=["0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];e&&(o=Math.round(Math.random()*(a-t))+t);for(var c=0;c<o;c++){var r=Math.round(Math.random()*(n.length-1));s+=n[r]}this.saveObject.secret=s},handleOkFunc:function(){var e=this,t=this;null!=this.saveObject.agentNo&&void 0!==this.saveObject.agentNo||(this.saveObject.agentNo=""),this.$refs.infoFormModel.validate((function(a){a&&(t.isAdd?(e.btnLoading=!0,n["cb"].add(n["r"],t.saveObject).then((function(e){t.$message.success("新增成功"),t.visible=!1,t.callbackFunc(),t.btnLoading=!1})).catch((function(e){t.btnLoading=!1}))):(t.sysPassword.confirmPwd=c["Base64"].encode(t.sysPassword.confirmPwd),Object.assign(t.saveObject,t.sysPassword),n["cb"].updateById(n["r"],t.recordId,t.saveObject).then((function(e){t.$message.success("修改成功"),t.visible=!1,t.callbackFunc(),t.btnLoading=!1,t.resetIsShow=!0,t.sysPassword.resetPass=!1,t.sysPassword.defaultPass=!0,t.resetPassEmpty(t)})).catch((function(e){t.btnLoading=!1,t.resetIsShow=!0,t.sysPassword.resetPass=!1,t.sysPassword.defaultPass=!0,t.resetPassEmpty(t)}))))}))},onClose:function(){this.visible=!1,this.resetIsShow=!1,this.sysPassword.resetPass=!1,this.resetPassEmpty(this),this.sysPassword.defaultPass=!0},searchFunc:function(){this.$refs.infoTable.refTable(!0)},isResetPass:function(){this.sysPassword.defaultPass||(this.newPwd="",this.sysPassword.confirmPwd="")},resetPassEmpty:function(e){e.newPwd="",e.sysPassword.confirmPwd=""}}},l=r,i=(a("a850"),a("2877")),d=Object(i["a"])(l,s,o,!1,null,null,null);t["default"]=d.exports},a529:function(e,t,a){"use strict";a("1652")},a850:function(e,t,a){"use strict";a("e70b")},b275:function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=this,a=t.$createElement,s=t._self._c||a;return s("page-header-wrapper",[s("a-card",[s("div",{staticClass:"table-page-search-wrapper"},[s("a-form",{staticClass:"table-head-ground",attrs:{layout:"inline"}},[s("div",{staticClass:"table-layer"},[s("jeepay-text-up",{attrs:{placeholder:"商户号",msg:t.searchData.mchNo},model:{value:t.searchData.mchNo,callback:function(e){t.$set(t.searchData,"mchNo",e)},expression:"searchData.mchNo"}}),s("jeepay-text-up",{attrs:{placeholder:"代理商号",msg:t.searchData.agentNo},model:{value:t.searchData.agentNo,callback:function(e){t.$set(t.searchData,"agentNo",e)},expression:"searchData.agentNo"}}),s("jeepay-text-up",{attrs:{placeholder:"商户名称",msg:t.searchData.mchName},model:{value:t.searchData.mchName,callback:function(e){t.$set(t.searchData,"mchName",e)},expression:"searchData.mchName"}}),s("a-form-item",{staticClass:"table-head-layout",attrs:{label:""}},[s("a-select",{attrs:{placeholder:"商户状态","default-value":""},model:{value:t.searchData.state,callback:function(e){t.$set(t.searchData,"state",e)},expression:"searchData.state"}},[s("a-select-option",{attrs:{value:""}},[t._v("全部")]),s("a-select-option",{attrs:{value:"0"}},[t._v("禁用")]),s("a-select-option",{attrs:{value:"1"}},[t._v("启用")])],1)],1),s("span",{staticClass:"table-page-search-submitButtons",staticStyle:{"flex-grow":"0","flex-shrink":"0"}},[s("a-button",{attrs:{type:"primary",icon:"search",loading:t.btnLoading},on:{click:t.queryFunc}},[t._v("查询")]),s("a-button",{staticStyle:{"margin-left":"8px"},attrs:{icon:"reload"},on:{click:function(){return e.searchData={}}}},[t._v("重置")])],1)],1)]),s("div",[t.$access("ENT_MCH_INFO_ADD")?s("a-button",{staticClass:"mg-b-30",attrs:{type:"primary",icon:"plus"},on:{click:t.addFunc}},[t._v("新建")]):t._e(),s("a-button",{staticStyle:{"margin-left":"8px"},attrs:{type:"danger",icon:"download"},on:{click:t.exportExcel}},[t._v("导出")])],1)],1),s("div",{staticStyle:{"background-color":"#fafafa","padding-left":"15px","padding-top":"10px","padding-bottom":"10px","border-bottom":"1px solid #e8e8e8"}},[s("a-row",[s("a-col",{staticClass:"stat-col bg-color-1",attrs:{span:4}},[s("span",{staticClass:"title"},[t._v("商户总数")]),s("b",{staticStyle:{color:"#DB4B4B"}},[t._v(t._s(this.totalMchInfo.mchNum))])]),s("a-col",{staticClass:"stat-col bg-color-2",attrs:{span:4,offset:1}},[s("span",{staticClass:"title"},[t._v("商户总余额")]),s("b",{staticStyle:{color:"#FA9D2A"}},[t._v(t._s((this.totalMchInfo.totalBalance/100).toFixed(2)))])]),s("a-col",{staticClass:"stat-col bg-color-3",attrs:{span:4,offset:1}},[s("span",{staticClass:"title"},[t._v("冻结金额汇总")]),s("b",{staticStyle:{color:"#2F61DC"}},[t._v(t._s((this.totalMchInfo.freezeBalance/100).toFixed(2)))])])],1)],1),s("JeepayTable",{ref:"infoTable",attrs:{initData:!0,reqTableDataFunc:t.reqTableDataFunc,tableColumns:t.tableColumns,searchData:t.searchData,rowKey:"mchNo"},on:{btnLoadClose:function(e){t.btnLoading=!1}},scopedSlots:t._u([{key:"mchNameSlot",fn:function(e){var a=e.record;return[t.$access("ENT_MCH_INFO_VIEW")?s("a",{staticStyle:{"font-weight":"bold"},on:{click:function(e){return t.detailFunc(a.mchNo)}}},[t._v(t._s(a.mchName))]):t._e()]}},{key:"stateSlot",fn:function(e){var t=e.record;return[s("a-badge",{attrs:{status:0===t.state?"error":"processing",text:0===t.state?"禁用":"启用"}})]}},{key:"balanceSlot",fn:function(e){var a=e.record;return[t.$access("ENT_MCH_INFO_EDIT")?s("a-button",{attrs:{size:"small",icon:"edit",type:"primary"},on:{click:function(e){return t.clickChangeBalance(a)}}},[t._v("调额")]):t._e(),t._v("   "),s("b",{style:{color:a.balance>0?"#4BD884":"#DB4B4B"}},[t._v(t._s((a.balance/100).toFixed(2)))])]}},{key:"agentInfoSlot",fn:function(e){var a=e.record;return[""!=a.agentNo?s("a-tag",{attrs:{color:"blue"}},[t._v(t._s(a.agentNo))]):t._e(),t._v(t._s(a.agentName))]}},{key:"opSlot",fn:function(e){var a=e.record;return[s("JeepayTableColumns",[t.$access("ENT_MCH_INFO_EDIT")?s("a-button",{attrs:{type:"link"},on:{click:function(e){return t.editFunc(a.mchNo)}}},[t._v("修改")]):t._e(),t.$access("ENT_MCH_APP_CONFIG")?s("a-button",{attrs:{type:"link"},on:{click:function(e){return t.mchAppConfig(a)}}},[t._v("支付配置")]):t._e(),t.$access("ENT_MCH_APP_CONFIG")?s("a-button",{attrs:{type:"link"},on:{click:function(e){return t.mchPassageConfig(a)}}},[t._v("通道绑定")]):t._e(),t.$access("ENT_MCH_INFO_DEL")&&"M1691231056"!==a.mchNo?s("a-button",{staticStyle:{color:"red"},attrs:{type:"link"},on:{click:function(e){return t.delFunc(a.mchNo)}}},[t._v("删除")]):t._e()],1)]}}])})],1),s("InfoAddOrEdit",{ref:"infoAddOrEdit",attrs:{callbackFunc:t.searchFunc}}),s("InfoDetail",{ref:"infoDetail",attrs:{callbackFunc:t.searchFunc}}),s("MchProductEdit",{ref:"infoProduct",attrs:{callbackFunc:t.searchFunc}}),s("MchPassageEdit",{ref:"infoPassage",attrs:{callbackFunc:t.searchFunc}}),[s("a-modal",{attrs:{title:"调整商户余额"},on:{ok:t.handleOkFunc},model:{value:t.isShowModal,callback:function(e){t.isShowModal=e},expression:"isShowModal"}},[s("a-form-model",{ref:"infoFormModel",attrs:{model:t.changeObject,"label-col":{span:6},"wrapper-col":{span:15},rules:t.changeRules}},[s("a-form-model-item",{attrs:{label:"商户号："}},[s("span",{staticStyle:{color:"black"}},[t._v(t._s(t.selectMch.mchNo))])]),s("a-form-model-item",{attrs:{label:"商户名称："}},[s("span",{staticStyle:{color:"black"}},[t._v(t._s(t.selectMch.mchName))])]),s("a-form-model-item",{attrs:{label:"调整金额：",prop:"changeAmount"}},[s("a-input",{attrs:{prefix:"￥",type:"number"},model:{value:t.changeObject.changeAmount,callback:function(e){t.$set(t.changeObject,"changeAmount",e)},expression:"changeObject.changeAmount"}}),s("b",{staticStyle:{color:"rgb(128,128,128)"}},[t._v("如需扣余额，则输入负数，例如:-10.50")])],1),s("a-form-model-item",{attrs:{label:"备注：",prop:"changeRemark"}},[s("a-input",{model:{value:t.changeObject.changeRemark,callback:function(e){t.$set(t.changeObject,"changeRemark",e)},expression:"changeObject.changeRemark"}})],1)],1)],1)]],2)},o=[],n=a("f339"),c=a("4f53"),r=a("5d5e"),l=a("0fea"),i=a("2be6"),d=a("1a6d"),u=a("9825"),f=a("09ef"),h=a("c1df"),m=a.n(h),p=a("21a6"),b=[{key:"mchName",fixed:"left",width:"150px",title:"商户名称",scopedSlots:{customRender:"mchNameSlot"}},{key:"mchNo",title:"商户号",dataIndex:"mchNo",width:"130px"},{key:"agentNo",title:"代理商",width:"260px",scopedSlots:{customRender:"agentInfoSlot"}},{key:"balance",title:"商户余额(￥)",width:"300px",scopedSlots:{customRender:"balanceSlot"}},{key:"state",title:"状态",width:"80px",scopedSlots:{customRender:"stateSlot"}},{key:"createdAt",dataIndex:"createdAt",title:"创建日期",width:"250px"},{key:"remark",dataIndex:"remark",title:"备注",width:"120px"},{key:"op",title:"操作",fixed:"right",align:"center",scopedSlots:{customRender:"opSlot"}}],g={name:"MchListPage",components:{JeepayTable:n["a"],JeepayTableColumns:r["a"],InfoAddOrEdit:i["default"],InfoDetail:d["default"],JeepayTextUp:c["a"],MchProductEdit:u["default"],MchPassageEdit:f["default"]},data:function(){return{btnLoading:!1,tableColumns:b,searchData:{},value:"''",selectMch:{},changeObject:{},isShowModal:!1,totalMchInfo:{mchNum:0,totalBalance:0,freezeBalance:0},changeRules:{changeRemark:[{required:!0,message:"请输入调额备注",trigger:"blur"}],changeAmount:[{required:!0,message:"请输入调整金额",trigger:"blur"}]}}},mounted:function(){this.getMchStatInfo()},methods:{queryFunc:function(){this.btnLoading=!0,this.$refs.infoTable.refTable(!0)},reqTableDataFunc:function(e){return l["cb"].list(l["r"],e)},searchFunc:function(){this.$refs.infoTable.refTable(!0)},addFunc:function(){this.$refs.infoAddOrEdit.show()},editFunc:function(e){this.$refs.infoAddOrEdit.show(e)},detailFunc:function(e){this.$refs.infoDetail.show(e)},delFunc:function(e){var t=this,a=this;this.$infoBox.confirmDanger("确认删除？","该操作将删除商户下所有配置及用户信息",(function(){l["db"].delById(l["r"],e).then((function(e){a.$refs.infoTable.refTable(!0),t.$message.success("删除成功")}))}))},mchAppConfig:function(e){this.$refs.infoProduct.show(e)},mchPassageConfig:function(e){this.$refs.infoPassage.show(e)},clickChangeBalance:function(e){this.isShowModal=!0,this.selectMch=e,void 0!==this.$refs.infoFormModel&&this.$refs.infoFormModel.resetFields(),this.changeObject={}},handleOkFunc:function(){var e=this;""!==this.changeObject.changeAmount&&void 0!==this.changeObject.changeAmount?""!==this.changeObject.changeRemark&&void 0!==this.changeObject.changeRemark?(this.isShowModal=!1,l["cb"].updateById(l["o"],this.selectMch.mchNo,this.changeObject).then((function(t){e.$refs.infoTable.refTable(),e.$message.success("修改成功")})).catch((function(t){console.log(t),e.isShowModal=!1}))):this.$message.error("备注不能为空"):this.$message.error("金额不能为空")},getMchStatInfo:function(){var e=this;l["cb"].getNormal(l["y"],"statMchInfo").then((function(t){e.totalMchInfo=t}))},exportExcel:function(){Object(l["R"])("/api/mchRealTimeInfo/exportExcel",this.searchData).then((function(e){var t=new Blob([e],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});Object(p["saveAs"])(t,m()().format("YYYY-MM-DD HH:mm:ss")+"-商户实时余额.xlsx")})).catch((function(e){var t=new Blob([e],{type:"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});Object(p["saveAs"])(t,m()().format("YYYY-MM-DD HH:mm:ss")+"-商户实时余额.xlsx")}))}}},v=g,y=(a("a529"),a("2877")),w=Object(y["a"])(v,s,o,!1,null,"458e7daa",null);t["default"]=w.exports},e70b:function(e,t,a){}}]);