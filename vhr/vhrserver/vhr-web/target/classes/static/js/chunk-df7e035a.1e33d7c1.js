(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-df7e035a"],{1937:function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[t._v(" 工资表查询 ")])},n=[],i={name:"SalSearch"},r=i,s=a("2877"),o=Object(s["a"])(r,l,n,!1,null,"88d7ad86",null);e["default"]=o.exports},"21c7":function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[t._v(" 工资表管理 ")])},n=[],i={name:"SalTable"},r=i,s=a("2877"),o=Object(s["a"])(r,l,n,!1,null,"0994380e",null);e["default"]=o.exports},"2bc5":function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",{staticStyle:{display:"flex","justify-content":"space-between"}},[a("el-button",{attrs:{icon:"el-icon-plus",type:"primary"},on:{click:t.showAddSalaryView}},[t._v("添加工资账套")]),a("el-button",{attrs:{icon:"el-icon-refresh",type:"success"},on:{click:t.refresh}})],1),a("div",{staticStyle:{"margin-top":"10px"}},[a("el-table",{attrs:{data:t.salaries,border:"",stripe:""}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),a("el-table-column",{attrs:{width:"150",prop:"name",label:"账套名称"}}),a("el-table-column",{attrs:{width:"80",prop:"basicSalary",label:"基本工资"}}),a("el-table-column",{attrs:{width:"80",prop:"trafficSalary",label:"交通补助"}}),a("el-table-column",{attrs:{width:"80",prop:"lunchSalary",label:"午餐补助"}}),a("el-table-column",{attrs:{width:"80",prop:"bonus",label:"奖金"}}),a("el-table-column",{attrs:{width:"150",prop:"createDate",label:"启用时间"}}),a("el-table-column",{attrs:{label:"养老金",align:"center"}},[a("el-table-column",{attrs:{width:"80",prop:"pensionPer",label:"比率"}}),a("el-table-column",{attrs:{width:"80",prop:"pensionBase",label:"基数"}})],1),a("el-table-column",{attrs:{label:"医疗保险",align:"center"}},[a("el-table-column",{attrs:{width:"80",prop:"medicalPer",label:"比率"}}),a("el-table-column",{attrs:{width:"80",prop:"medicalBase",label:"基数"}})],1),a("el-table-column",{attrs:{label:"公积金",align:"center"}},[a("el-table-column",{attrs:{width:"80",prop:"accumulationFundPer",label:"比率"}}),a("el-table-column",{attrs:{width:"80",prop:"accumulationFundBase",label:"基数"}})],1),a("el-table-column",{attrs:{label:"操作"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-button",{attrs:{size:"mini"},on:{click:function(a){return t.showEditSalaryView(e.row)}}},[t._v("编辑")]),a("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(a){return t.deleteSalary(e.row)}}},[t._v("删除")])]}}])})],1)],1),a("el-dialog",{attrs:{title:t.dialogTitle,visible:t.dialogVisible,width:"50%","before-close":t.handleClose},on:{"update:visible":function(e){t.dialogVisible=e}}},[a("div",{staticStyle:{display:"flex","justify-content":"space-around","align-items":"center"}},[a("el-steps",{attrs:{direction:"vertical",active:t.activeItemIndex}},t._l(t.salaryItemName,(function(t,e){return a("el-step",{key:e,attrs:{title:t}})})),1),t._l(t.salary,(function(e,l,n){return a("el-input",{directives:[{name:"show",rawName:"v-show",value:n==t.activeItemIndex,expression:"index==activeItemIndex"}],key:n,staticStyle:{width:"200px"},attrs:{placeholder:"请输入"+t.salaryItemName[n]+"..."},model:{value:t.salary[l],callback:function(e){t.$set(t.salary,l,e)},expression:"salary[title]"}})}))],2),a("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{on:{click:t.preStep}},[t._v(t._s(10==t.activeItemIndex?"取消":"上一步"))]),a("el-button",{attrs:{type:"primary"},on:{click:t.nextStep}},[t._v(t._s(10==t.activeItemIndex?"完成":"下一步"))])],1)])],1)},n=[],i=(a("b0c0"),{name:"SalSob",data:function(){return{dialogTitle:"添加工资账套",salaries:[],dialogVisible:!1,activeItemIndex:0,salaryItemName:["账套名称","基本工资","交通补助","午餐补助","奖金","养老金比率","养老金基数","医疗保险比率","医疗保险基数","公积金比率","公积金基数"],salary:{name:null,basicSalary:null,trafficSalary:null,lunchSalary:null,bonus:null,pensionPer:null,pensionBase:null,medicalPer:null,medicalBase:null,accumulationFundPer:null,accumulationFundBase:null}}},mounted:function(){this.initSalaries()},methods:{refresh:function(){this.initSalaries(),this.$message({message:"刷新成功！",type:"success"})},deleteSalary:function(t){var e=this;this.$confirm("此操作将永久删除【"+t.name+"】账套，是否继续？","提示",{cancelButtonText:"取消",confirmButtonText:"确定"}).then((function(){e.deleteRequest("/salary/sob/"+t.id).then((function(t){t&&e.initSalaries()}))})).catch((function(){e.$message.info("取消删除！")}))},preStep:function(){0!=this.activeItemIndex&&10!=this.activeItemIndex?this.activeItemIndex--:this.dialogVisible=!1},nextStep:function(){var t=this;10!=this.activeItemIndex?this.activeItemIndex++:this.salary.id?this.putRequest("/salary/sob/",this.salary).then((function(e){e&&(t.initSalaries(),t.dialogVisible=!1)})):this.postRequest("/salary/sob/",this.salary).then((function(e){e&&(t.initSalaries(),t.dialogVisible=!1)}))},showAddSalaryView:function(){this.salary={name:null,basicSalary:null,trafficSalary:null,lunchSalary:null,bonus:null,pensionPer:null,pensionBase:null,medicalPer:null,medicalBase:null,accumulationFundPer:null,accumulationFundBase:null},this.activeItemIndex=0,this.dialogTitle="添加工资账套",this.dialogVisible=!0},showEditSalaryView:function(t){this.dialogTitle="修改工资账套",this.dialogVisible=!0,this.activeItemIndex=0,this.salary.name=t.name,this.salary.basicSalary=t.basicSalary,this.salary.trafficSalary=t.trafficSalary,this.salary.lunchSalary=t.lunchSalary,this.salary.bonus=t.bonus,this.salary.creatDate=t.creatDate,this.salary.pensionPer=t.pensionPer,this.salary.pensionBase=t.pensionBase,this.salary.medicalPer=t.medicalPer,this.salary.medicalBase=t.medicalBase,this.salary.accumulationFundPer=t.accumulationFundPer,this.salary.accumulationFundBase=t.accumulationFundBase,this.salary.id=t.id},initSalaries:function(){var t=this;this.getRequest("/salary/sob/").then((function(e){e&&(t.salaries=e)}))}}}),r=i,s=a("2877"),o=Object(s["a"])(r,l,n,!1,null,"26ebef58",null);e["default"]=o.exports},"6ac98":function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("div",[a("el-table",{attrs:{data:t.emps,border:"",stripe:"",size:"mini"}},[a("el-table-column",{attrs:{type:"selection",align:"left",width:"55"}}),a("el-table-column",{attrs:{prop:"name",label:"姓名",fixed:"",width:"140",align:"left"}}),a("el-table-column",{attrs:{prop:"workID",label:"工号",width:"140",align:"left"}}),a("el-table-column",{attrs:{prop:"email",label:"电子邮件",width:"200",align:"left"}}),a("el-table-column",{attrs:{prop:"phone",label:"电话号码",width:"160",align:"left"}}),a("el-table-column",{attrs:{prop:"department.name",label:"所属部门",width:"160",align:"left"}}),a("el-table-column",{attrs:{label:"工资账套",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.salary?a("el-tooltip",{attrs:{placement:"right"}},[a("div",{attrs:{slot:"content"},slot:"content"},[a("table",[a("tr",[a("td",[t._v("基本工资")]),a("td",[t._v(t._s(e.row.salary.basicSalary))])]),a("tr",[a("td",[t._v("交通补助")]),a("td",[t._v(t._s(e.row.salary.trafficSalary))])]),a("tr",[a("td",[t._v("午餐补助")]),a("td",[t._v(t._s(e.row.salary.lunchSalary))])]),a("tr",[a("td",[t._v("奖金")]),a("td",[t._v(t._s(e.row.salary.bonus))])]),a("tr",[a("td",[t._v("养老金比率")]),a("td",[t._v(t._s(e.row.salary.pensionPer))])]),a("tr",[a("td",[t._v("养老金基数")]),a("td",[t._v(t._s(e.row.salary.pensionBase))])]),a("tr",[a("td",[t._v("医疗保险比率")]),a("td",[t._v(t._s(e.row.salary.medicalPer))])]),a("tr",[a("td",[t._v("医疗保险基数")]),a("td",[t._v(t._s(e.row.salary.medicalBase))])]),a("tr",[a("td",[t._v("公积金比率")]),a("td",[t._v(t._s(e.row.salary.accumulationFundPer))])]),a("tr",[a("td",[t._v("公积金基数")]),a("td",[t._v(t._s(e.row.salary.accumulationFundBase))])]),a("tr",[a("td",[t._v("启用时间")]),a("td",[t._v(t._s(e.row.salary.createDate))])])])]),a("el-tag",[t._v(t._s(e.row.salary.name))])],1):a("el-tag",[t._v("暂未设置")])]}}])}),a("el-table-column",{attrs:{label:"操作",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[a("el-popover",{attrs:{placement:"left",title:"修改工资账套",width:"200",trigger:"click"},on:{show:function(a){return t.showPop(e.row.salary)},hide:function(a){return t.hidePop(e.row)}}},[a("div",[a("el-select",{attrs:{placeholder:"请选择",size:"mini"},model:{value:t.currentSalary,callback:function(e){t.currentSalary=e},expression:"currentSalary"}},t._l(t.salaries,(function(t){return a("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})),1)],1),a("el-button",{attrs:{slot:"reference",type:"danger"},slot:"reference"},[t._v("修改工资账套")])],1)]}}])})],1),a("div",{staticStyle:{display:"flex","justify-content":"flex-end"}},[a("el-pagination",{attrs:{background:"",layout:"sizes, prev, pager, next, jumper, ->, total, slot",total:t.total},on:{"current-change":t.currentChange,"size-change":t.sizeChange}})],1)],1)])},n=[],i={name:"SalSobCfg",data:function(){return{currentSalary:null,salaries:[],emps:[],currentPage:1,currentSize:10,total:0}},mounted:function(){this.initEmps(),this.intiSalaries()},methods:{sizeChange:function(t){this.currentSize=t,this.initEmps()},currentChange:function(t){this.currentPage=t,this.initEmps()},hidePop:function(t){var e=this;this.currentSalary&&this.putRequest("/salary/sobcfg/?eid="+t.id+"&sid="+this.currentSalary).then((function(t){t&&e.initEmps()}))},showPop:function(t){this.currentSalary=t?t.id:null},intiSalaries:function(){var t=this;this.getRequest("/salary/sobcfg/salaries").then((function(e){e&&(t.salaries=e)}))},initEmps:function(){var t=this;this.getRequest("/salary/sobcfg/?page="+this.currentPage+"&size"+this.currentSize).then((function(e){e&&(t.emps=e.data,t.total=e.total)}))}}},r=i,s=a("2877"),o=Object(s["a"])(r,l,n,!1,null,"67ffb740",null);e["default"]=o.exports},d7e6:function(t,e,a){var l={"./SalMonth.vue":"fbac","./SalSearch.vue":"1937","./SalSob.vue":"2bc5","./SalSobCfg.vue":"6ac98","./SalTable.vue":"21c7"};function n(t){var e=i(t);return a(e)}function i(t){if(!a.o(l,t)){var e=new Error("Cannot find module '"+t+"'");throw e.code="MODULE_NOT_FOUND",e}return l[t]}n.keys=function(){return Object.keys(l)},n.resolve=i,t.exports=n,n.id="d7e6"},fbac:function(t,e,a){"use strict";a.r(e);var l=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[t._v(" 月末处理 ")])},n=[],i={name:"SalMonth"},r=i,s=a("2877"),o=Object(s["a"])(r,l,n,!1,null,"669adb18",null);e["default"]=o.exports}}]);
//# sourceMappingURL=chunk-df7e035a.1e33d7c1.js.map