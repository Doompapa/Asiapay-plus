(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5d267514"],{"0fea":function(t,n,e){"use strict";e.d(n,"cb",(function(){return u})),e.d(n,"db",(function(){return a})),e.d(n,"d",(function(){return i})),e.d(n,"K",(function(){return o})),e.d(n,"J",(function(){return c})),e.d(n,"N",(function(){return s})),e.d(n,"P",(function(){return d})),e.d(n,"h",(function(){return l})),e.d(n,"f",(function(){return f})),e.d(n,"g",(function(){return p})),e.d(n,"r",(function(){return h})),e.d(n,"o",(function(){return m})),e.d(n,"y",(function(){return y})),e.d(n,"C",(function(){return g})),e.d(n,"c",(function(){return b})),e.d(n,"v",(function(){return k})),e.d(n,"H",(function(){return S})),e.d(n,"u",(function(){return T})),e.d(n,"Q",(function(){return q})),e.d(n,"B",(function(){return E})),e.d(n,"q",(function(){return I})),e.d(n,"i",(function(){return v})),e.d(n,"l",(function(){return O})),e.d(n,"k",(function(){return w})),e.d(n,"j",(function(){return L})),e.d(n,"n",(function(){return R})),e.d(n,"m",(function(){return P})),e.d(n,"F",(function(){return B})),e.d(n,"E",(function(){return G})),e.d(n,"I",(function(){return V})),e.d(n,"s",(function(){return C})),e.d(n,"t",(function(){return F})),e.d(n,"M",(function(){return A})),e.d(n,"L",(function(){return N})),e.d(n,"e",(function(){return D})),e.d(n,"D",(function(){return x})),e.d(n,"p",(function(){return _})),e.d(n,"a",(function(){return H})),e.d(n,"O",(function(){return J})),e.d(n,"G",(function(){return M})),e.d(n,"x",(function(){return j})),e.d(n,"w",(function(){return z})),e.d(n,"z",(function(){return K})),e.d(n,"A",(function(){return $})),e.d(n,"b",(function(){return W})),e.d(n,"hb",(function(){return Q})),e.d(n,"U",(function(){return X})),e.d(n,"bb",(function(){return Y})),e.d(n,"eb",(function(){return Z})),e.d(n,"X",(function(){return tt})),e.d(n,"W",(function(){return nt})),e.d(n,"gb",(function(){return et})),e.d(n,"fb",(function(){return rt})),e.d(n,"Y",(function(){return ut})),e.d(n,"V",(function(){return at})),e.d(n,"S",(function(){return it})),e.d(n,"T",(function(){return ot})),e.d(n,"Z",(function(){return ct})),e.d(n,"ab",(function(){return st})),e.d(n,"R",(function(){return dt}));var r=e("4667"),u={list:function(t,n){return r["a"].request({url:t,method:"GET",params:n},!0,!0,!1)},add:function(t,n){return r["a"].request({url:t,method:"POST",data:n},!0,!0,!1)},getById:function(t,n){return r["a"].request({url:t+"/"+n,method:"GET"},!0,!0,!1)},updateById:function(t,n,e){return r["a"].request({url:t+"/"+n,method:"PUT",data:e},!0,!0,!1)},delById:function(t,n){return r["a"].request({url:t+"/"+n,method:"DELETE"},!0,!0,!1)},postNormal:function(t,n){return r["a"].request({url:t+"/"+n,method:"POST"},!0,!0,!0)},postDataNormal:function(t,n,e){return r["a"].request({url:t+"/"+n,method:"POST",data:e},!0,!0,!0)},getNormal:function(t,n){return r["a"].request({url:t+"/"+n,method:"GET"},!0,!0,!0)}},a={list:function(t,n){return r["a"].request({url:t,method:"GET",params:n},!0,!0,!0)},add:function(t,n){return r["a"].request({url:t,method:"POST",data:n},!0,!0,!0)},getById:function(t,n){return r["a"].request({url:t+"/"+n,method:"GET"},!0,!0,!0)},updateById:function(t,n,e){return r["a"].request({url:t+"/"+n,method:"PUT",data:e},!0,!0,!0)},delById:function(t,n){return r["a"].request({url:t+"/"+n,method:"DELETE"},!0,!0,!0)}},i="/api/sysEnts",o="/api/sysRoles",c="/api/sysRoleEntRelas",s="/api/sysUsers",d="/api/sysUserRoleRelas",l="/api/isvInfo",f="/api/isvBalance",p="/api/agentHistory",h="/api/mchInfo",m="/api/mchBalance",y="/api/mchStatInfo",g="/api/passageStatInfo",b="/api/agentStatInfo",k="/api/mchProductInfo",S="/api/productMchInfo",T="/api/mchPassageInfo",q="/api/payOrder",E="/api/passageMchInfo",I="/api/mchHistory",v="/api/mchApps",O="/api/mchAppsList",w="/api/passageHistory",L="/api/mchAppsBalance",R="/api/mchAppsBalanceReset",P="/api/mchAppsMultipleSet",B="/api/payOrder",G="/api/payOrderForceList",V="/api/refundOrder",C="/api/mchNotify",F="/api/mchNotifyResend/resendAll",A="api/sysLog",N="api/sysConfigs",U="api/mainChart",D="/api/payIfDefines",x="/api/payWays",_="/api/mchDivision",H="/api/agentDivision",J="/api/transferOrders",M="/api/platStat",j="/api/mchStat",z="/api/mchProductStat",K="/api/passageStat",$="/api/productStat",W="/api/agentStat",Q={avatar:r["a"].baseUrl+"/api/ossFiles/avatar",ifBG:r["a"].baseUrl+"/api/ossFiles/ifBG",cert:r["a"].baseUrl+"/api/ossFiles/cert"};function X(t){return r["a"].request({url:"/api/sysEnts/showTree?sysType="+t,method:"GET"})}function Y(t,n,e){return r["a"].request({url:"/api/payOrder/refunds/"+t,method:"POST",data:{refundAmount:n,refundReason:e}})}function Z(t,n){return r["a"].request({url:"api/sysUserRoleRelas/relas/"+t,method:"POST",data:{roleIdListStr:JSON.stringify(n)}})}function tt(){return r["a"].request({url:U+"/twoDayCount",method:"GET"})}function nt(){return r["a"].request({url:U+"/realTimeCount",method:"GET"})}function et(t){return r["a"].request({url:"/api/current/modifyPwd",method:"put",data:t})}function rt(t){return r["a"].request({url:"/api/current/user",method:"put",data:t})}function ut(){return r["a"].request({url:"/api/current/user",method:"get"})}function at(){return r["a"].request({url:"/api/current/getGoogleKey",method:"get"})}function it(t){return r["a"].request({url:N+"/"+t,method:"GET"})}function ot(t,n){return r["a"].request({url:"/api/sysEnts/bySysType",method:"GET",params:{entId:t,sysType:n}})}function ct(t){return r["a"].request({url:"/api/mchNotify/resend/"+t,method:"POST"})}function st(t){return r["a"].request({url:"/api/passageTest/doPay",method:"POST",data:t})}function dt(t,n){return r["a"].request({url:t,method:"POST",data:n,responseType:"arraybuffer"},!0,!1,!0)}},4779:function(t,n,e){"use strict";e.r(n);var r=function(){var t=this,n=t.$createElement,e=t._self._c||n;return e("a-drawer",{attrs:{visible:t.isShow,title:"分配角色",width:"30%",maskClosable:!0},on:{close:function(n){t.isShow=!1}}},[e("div",[e("div",{style:{borderBottom:"1px solid #E9E9E9"}},[e("a-checkbox",{attrs:{indeterminate:0!=t.checkedVal.length&&t.allRoleList.length!=t.checkedVal.length,checked:0!=t.checkedVal.length&&t.allRoleList.length===t.checkedVal.length},on:{change:t.onCheckAllChange}},[t._v(" 全选 ")])],1),e("br"),e("a-checkbox-group",{attrs:{options:t.allRoleList},model:{value:t.checkedVal,callback:function(n){t.checkedVal=n},expression:"checkedVal"}})],1),e("div",{staticClass:"drawer-btn-center"},[e("a-button",{style:{marginRight:"8px"},attrs:{icon:"close"},on:{click:function(n){t.isShow=!1}}},[t._v("取消")]),e("a-button",{attrs:{type:"primary",icon:"check",loading:t.confirmLoading},on:{click:t.handleOkFunc}},[t._v("保存")])],1)])},u=[],a=(e("d81d"),e("0fea")),i={props:{callbackFunc:{type:Function}},data:function(){return{confirmLoading:!1,isShow:!1,recordId:null,allRoleList:[],checkedVal:[]}},created:function(){},methods:{show:function(t){var n=this,e=this;e.allRoleList=[],e.checkedVal=[],e.confirmLoading=!1,e.recordId=t,a["db"].list(a["K"],{pageSize:-1}).then((function(r){if(r.total<=0)return n.$message.error("当前暂无角色，请先行添加");e.allRoleList=[],r.records.map((function(t){e.allRoleList.push({label:t.roleName,value:t.roleId}),e.isShow=!0})),a["cb"].list(a["P"],{pageSize:-1,userId:t}).then((function(t){t.records.map((function(t){e.checkedVal.push(t.roleId)}))}))}))},handleOkFunc:function(){var t=this,n=this;n.confirmLoading=!0,Object(a["eb"])(this.recordId,this.checkedVal).then((function(e){n.$message.success("更新成功！"),t.isShow=!1,void 0!==n.callbackFunc&&n.callbackFunc()})).catch((function(t){n.confirmLoading=!1}))},onCheckAllChange:function(t){var n=this;this.checkedVal=[],t.target.checked&&this.allRoleList.map((function(t){n.checkedVal.push(t.value)}))}}},o=i,c=e("2877"),s=Object(c["a"])(o,r,u,!1,null,null,null);n["default"]=s.exports}}]);