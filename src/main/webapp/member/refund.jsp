<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>会员退款</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="member/refund.js"></script>
<style type="text/css">
.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.fitem input {
	width: 160px;
}

.fitem a {
	margin-right: 5px;
}

.table-cls{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	}
	
.table-cls td{
  border-left: 1px solid #E0E0E0;
  border-top: 1px solid #E0E0E0;
}
</style>
<script type="text/javascript">
$(function(){
	
	$('#grid').datagrid({
		title : '会员退款管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'member_refund_data_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'status':$("#s-status").combobox("getValue")
		},
		pageSize : 50,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'money',
			title : '退款金额(元)',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val, rec) {
				if(null != val && val !='' && val != 'null' && val !='NULL'){
					var tmp = val/100;
					if(tmp == 0){
						return "0.00";
					}else{
						n = tmp.toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
					}
				}else{
					if(val == 0){
						return "0.00";
					}else{
						return val;
					}
				}
			}
		},{
			field : 'bankName',
			title : '银行开户行',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'bankCard',
			title : '退款银行卡号',
			width : $(this).width() * 0.09,
			align : 'center'
		}, {
			field : 'createTime',
			title : '申请时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}/*, {
			field : 'chargeCategory',
			title : '退款类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "D"){
					return "押金";
				}else if(val == "B"){
					return "余额";
				}else{
					return val;
				}
			}
		}*/,{
			field : 'userCarTime',
			title : '最后用车时间',
			width : $(this).width() * 0.1,
			align : 'center' 
			/* formatter : function(val, rec){
				var days = rec.days;
				if(days > 15){
					return "<font color='red'>"+val+"</font>";
				}else{
					return val;
				}
			} */
		}, {
			field : 'status',
			title : '退款状态',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1"){
					if(rec.days >= 7){
						return "<font color='red'>审核中</font>";
					}else{
						return "审核中";
					}
				}else if(val == "2"){
					return "驳回";
				}else if(val == "3"){
					return "完成";
				}else if(val == "4"){
					return "取消";
				}else if(val == "5"){
					return "退款中";
				}else{
					return val;
				}
			}
		}, {
			field : 'reason',
			title : '退款原因',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'lastHandleTime',
			title : '最后处理时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'lastHandleUser',
			title : '最后处理人',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'nextHandleUserRoleId',
			title : '下一步处理人角色',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, row, index) {
				if(null == val || "" == val){
					return "${firstRole.name}";
				}else{
					return row.nextHandleUserRoleIdStr;
				}
			}
		}, {
			field : 'action',
			title : '操作',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value,row,index) {
				var _html = "";
				var _curr_id = "${firstRole.id}";
				if(row.nextHandleUserRoleId != null && "" != row.nextHandleUserRoleId){
					_curr_id = row.nextHandleUserRoleId;
				}
				var _roleDataStr = "${sessionScope.roleDataStr}";
				if(row.status == '1'){
					  if(_roleDataStr != ""){
						  if(_roleDataStr.indexOf(",") != -1){
							  var _tmpArr = _roleDataStr.split(",");
							  for(var i=0; i<_tmpArr.length; i++){
								  if(_tmpArr[i] == _curr_id){
									  _html += "<a href='javascript:void(0);' onclick='openRefundAuditDialog("+row.id+", "+_curr_id+", "+row.memberId+");'>审核</a>  ";
									  break;
								  }
							  }
						  }else{
							  if(_roleDataStr == _curr_id){
								  _html += "<a href='javascript:void(0);' onclick='openRefundAuditDialog("+row.id+", "+_curr_id+", "+row.memberId+");'>审核</a>  ";
							  }
						  }
					  }
				}
				_html += "  <a href='javascript:void(0);' onclick='openRefundLookDialog("+row.id+","+row.status+", "+row.memberId+");'>查看</a>";
				return _html;
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	
	
});
</script>
</head>
<body>
   <table id="grid" toolbar="#toolbar"></table>
     
      <!-- 工具栏  start-->
   <div id="toolbar" style="height:26px">
      <form id="queryMemberRefund">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
		手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
		退款状态:<input class="easyui-combobox" name="status" id="s-status"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
							label: '审核中',
							value: '1'
						},{
							label: '退款驳回',
							value: '2'
						},{
							label: '退款完成',
							value: '3'
						},{
							label: '取消',
							value: '4'
						},{
							label: '退款中',
							value: '5'
						}]">
		是否手动打款:<input class="easyui-combobox" name="isHandleReturn" id="s-isHandleReturn"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '原路返回',
							value: '1'
						},{
							label: '手动打款',
							value: '2'
						}]">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清除</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="passBtnQuery" onclick=";">超时未处理</a>
		<r:FunctionRole functionRoleId="member_refund">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="member_refund">退款</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="member_refund_export_excel">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
		</r:FunctionRole>
		
		</form>
   </div>
     <!-- 工具栏  end-->

     <div id="refundAuditWorkflowLookDialog" class="easyui-dialog" closed="true">
      
      <div class="easyui-panel" title="详情" style="width:100%;height: 25%;">
          <table class="table-cls" width="100%" cellpadding="2">
             <tr>
                <td align="right" nowrap="nowrap">会员姓名:</td>
                <td id='l-name'></td>
                <td align="right" nowrap="nowrap">会员手机:</td>
                <td id='l-phone'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">会员等级:</td>
                <td id='l-levelName'></td>
                <td align="right" nowrap="nowrap">退款金额:</td>
                <td id='l-money'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">申请时间:</td>
                <td id='l-createTime'></td>
                <td align="right" nowrap="nowrap">退款银行账户:</td>
                <td id='l-bankCardUserName'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款银行卡号:</td>
                <td id='l-bankCard'></td>
                <td align="right" nowrap="nowrap">退款银行开户行:</td>
                <td id='l-bankName'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款通知手机:</td>
                <td id='l-refundUserMoblie'></td>
             	<td></td><td></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款原因:</td>
                <td id='l-reason' colspan="3"></td>
             </tr>
          </table>
          
      </div>
      
      <div class="easyui-panel" style="width: 100%;height: 25%">
         <table id="refundLogGrid"></table>
     </div>
         <div class="easyui-panel" style="width: 100%;height: 25%">
             <table id="rejectLogGrid"></table>
         </div>
       <div class="easyui-panel" title="审核记录" style="width:100%;height: 25%;">
          <table class="table-cls" width="100%" cellpadding="2" id='l-audit-table'></table>
      </div>
      
    </div>  
   
    
    <div id="refundAuditWorkflowDialog" class="easyui-dialog" closed="true">
      
      <div class="easyui-panel" title="详情" style="width:100%;height: 165;">
          <table class="table-cls" width="100%" cellpadding="2">
             <tr>
                <td align="right" nowrap="nowrap">会员姓名:</td>
                <td id='a-name'></td>
                <td align="right" nowrap="nowrap">会员手机:</td>
                <td id='a-phone'></td>
             </tr>
             <tr>
             	<td align="right" nowrap="nowrap">会员等级:</td>
                <td id='a-levelName'></td>
                <td align="right" nowrap="nowrap">退款金额:</td>
                <td id='a-money'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">申请时间:</td>
                <td id='a-createTime'></td>
                <td align="right" nowrap="nowrap">退款银行账户:</td>
                <td id='a-bankCardUserName'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款银行卡号:</td>
                <td id='a-bankCard'></td>
                <td align="right" nowrap="nowrap">退款银行开户行:</td>
                <td id='a-bankName'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款通知手机:</td>
                <td id='a-refundUserMoblie'></td>
                <td></td>
                <td></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款原因:</td>
                <td id='a-reason' colspan="3"></td>
            <!--  </tr>
               <tr>
                <td align="right" nowrap="nowrap">用车订单是否有违章事故:</td>
                <td id='a-isTrafficCitation' style="color: red"></td>
                <td align="right" nowrap="nowrap">违章事故赔偿金额:</td>
                <td id='a-trafficCitationCharge' style="color: red"></td>
             </tr> -->
          </table>
      </div>
	  <table id="queryWzRecord"></table>      
      
       <div class="easyui-panel" title="审核记录" style="width:100%;height: 120;">
          <table class="table-cls" width="100%" cellpadding="2" id='a-audit-table'></table>
      </div>
      
      <div class="easyui-panel" title="审核" style="width:100%;height: 120;">
        <form action="" name="refunFlowForm" id="refunFlowForm" method="post" enctype="multipart/form-data">
          <input type="hidden" name="rid" id="rid">
          <input type="hidden" name="currRoleId" id="currRoleId">
          <input type="hidden" name="refundId" id="refundId">
          <table class="table-cls" width="100%" cellpadding="2">
             <tr>
                <td>审核结果:</td>
                <td>
                  <input type="radio" name="auditorResult" value= "1" checked="checked">通过
                  <input type="radio" name="auditorResult" value= "2">不通过
                </td>
             </tr>
             <tr>
                <td>备注:</td>
                <td>
                  <textarea rows="3"  name="auditorRemark" id="auditorRemark" style="width: 98%;"></textarea>
                </td>
             </tr>
             <tr>
                <td>文件</td>
                <td>
                  <input type="file" name="uploadFile" id="uploadFile">
                </td>
             </tr>
          </table>
          </form>
      </div>
      
    </div>  
    
    <!-- 原路返回 -->
    <div id="refundReturnDialog" class="easyui-dialog" closed="true">
      <form action="" name="refundReturnForm" id="refundReturnForm" method="post" enctype="multipart/form-data">
      	<input type="hidden" name="returnId" id="returnId">
      	<div class="easyui-panel" title="详情" style="width:100%;height: 165;">
          <table class="table-cls" width="100%" cellpadding="2">
             <tr>
                <td align="right" nowrap="nowrap">会员姓名:</td>
                <td id='d-name'></td>
                <td align="right" nowrap="nowrap">会员手机:</td>
                <td id='d-phone'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款金额:</td>
                <td id='d-money'></td>
                <td align="right" nowrap="nowrap">申请时间:</td>
                <td id='d-createTime'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款银行账户:</td>
                <td id='d-bankCardUserName'></td>
                <td align="right" nowrap="nowrap">退款银行卡号:</td>
                <td id='d-bankCard'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款银行开户行:</td>
                <td id='d-bankName'></td>
                <td align="right" nowrap="nowrap">退款通知手机:</td>
                <td id='d-refundUserMoblie'></td>
             </tr>
             <tr>
                <td align="right" nowrap="nowrap">退款原因:</td>
                <td id='d-reason' colspan="3"></td>
             </tr>
              <!--  <tr>
                <td align="right" nowrap="nowrap">用车订单是否有违章事故:</td>
                <td id='d-isTrafficCitation' style="color: red"></td>
                <td align="right" nowrap="nowrap">违章事故赔偿金额:</td>
                <td id='d-trafficCitationCharge' style="color: red"></td>
             </tr> -->
          </table>
      </div>
	  
       <div class="easyui-panel" title="审核记录" style="width:100%;height: 120;">
          <table class="table-cls" width="100%" cellpadding="2" id='d-audit-table'></table>
      </div>
      </form>
     </div>
    
      
     
    <!-- 手动打款 -->
    <div id="refundHandleReturnDialog" class="easyui-dialog" closed="true">
      <form action="" name="refundHandleReturnForm" id="refundHandleReturnForm" method="post" enctype="multipart/form-data">
      	  <input type="hidden" name="handleId" id="handleId">
	      <div class="easyui-panel" title="详情" style="width:100%;height: 165;">
	          <table class="table-cls" width="100%" cellpadding="2">
	             <tr>
	                <td align="right" nowrap="nowrap">会员姓名:</td>
	                <td id='h-name'></td>
	                <td align="right" nowrap="nowrap">会员手机:</td>
	                <td id='h-phone'></td>
	             </tr>
	             <tr>
	                <td align="right" nowrap="nowrap">退款金额:</td>
	                <td id='h-money'></td>
	                <td align="right" nowrap="nowrap">申请时间:</td>
	                <td id='h-createTime'></td>
	             </tr>
	             <tr>
	                <td align="right" nowrap="nowrap">退款银行账户:</td>
	                <td id='h-bankCardUserName'></td>
	                <td align="right" nowrap="nowrap">退款银行卡号:</td>
	                <td id='h-bankCard'></td>
	             </tr>
	             <tr>
	                <td align="right" nowrap="nowrap">退款银行开户行:</td>
	                <td id='h-bankName'></td>
	                <td align="right" nowrap="nowrap">退款通知手机:</td>
	                <td id='h-refundUserMoblie'></td>
	             </tr>
	             <tr>
	                <td align="right" nowrap="nowrap">退款原因:</td>
	                <td id='h-reason' colspan="3"></td>
	             </tr>
	               <!-- <tr>
	                <td align="right" nowrap="nowrap">用车订单是否有违章事故:</td>
	                <td id='h-isTrafficCitation' style="color: red"></td>
	                <td align="right" nowrap="nowrap">违章事故赔偿金额:</td>
	                <td id='h-trafficCitationCharge' style="color: red"></td>
	             </tr> -->
	          </table>
	      </div>
		  
	       <div class="easyui-panel" title="审核记录" style="width:100%;height: 120;">
	          <table class="table-cls" width="100%" cellpadding="2" id='h-audit-table'></table>
	      </div>
	      </form>
	     </div>
	     
	   <div id="contradictView" class="easyui-dialog" closed="true" style="padding:10px 20px">
	       <form name="contradictViewForm" id="contradictViewForm" method="post" >
	          <input type="hidden" name="refund-id" id="refund-id">
	          <input type="hidden" name="opreationType" id="p-opreationType">
	          <input type="hidden" name="cityCode" id="p-cityCode">
			   <div class="fitem">
					<label>驳回原因:</label><input  name="contradictReason" id="contradict-reason"  class="easyui-textbox"   data-options="multiline:true" style="width: 75%;height:100px"/>
			   </div>
	       </form>
     </div>
    
</body>
</html>