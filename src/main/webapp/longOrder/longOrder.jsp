<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>超长订单参数设置</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
 <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js?t" charset="UTF-8"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
<script type="text/javascript" src="longOrder/longOrder.js"></script>

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

</style>
</head>
<body>

<table id="grid" toolbar="#toolbar"></table>	
   
     
      <!-- 工具栏  start-->
   <div id="toolbar" style="height:auto">
   <form id="queryTimeLease">
   		城市:<input class="easyui-combobox" name="cityCode" id="s-cityCode"
							data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		会员等级:
		<input class="easyui-combobox" name="levelCode" id="s-levelCode"
					data-options=" url:'sys_optional_memberLevelCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车型:
		<input class="easyui-combobox" name="carTypeId" id="s-carTypeId"
					data-options=" url:'sys_optional_carTypeCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		<r:FunctionRole functionRoleId="longOrder_add">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="btnSave" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="longOrder_edit">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
	  </form>		
   </div>
   <div id="editView" class="easyui-dialog" closed="true" style="width: 650px;height: 600px;">
   		<form id="editForm" method="post" action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
   			<input type="hidden" id='e-id' name="id" >
   			<table cellpadding="5"   style="font-size: 12px;margin-left:50px;margin-top: 30px;width:80%" >
				<tr height="42px;">
	    			<td nowrap="nowrap" width="30%">城市:</td>
	    			<td nowrap="nowrap" width="70%">
	    				<input class="easyui-combobox" name="cityCode" id="e-cityCode"
							data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto', required:true,missingMessage:'城市不能为空'">
	    			</td>
	    		</tr>
				<tr height="42px;">
	    			<td nowrap="nowrap" width="30%">会员等级:</td>
	    			<td nowrap="nowrap" width="70%">
					<input class="easyui-combobox" name="levelCode" id="e-levelCode"
								data-options=" url:'sys_optional_memberLevelCombobox',
				                    method:'get',
				                    valueField:'id',
				                    textField:'text',
			                	    panelHeight:'auto', required:true,missingMessage:'会员等级不能为空'" >
	    			</td>
	    		</tr>
				<tr height="42px;">
	    			<td nowrap="nowrap" width="30%">车型:</td>
	    			<td nowrap="nowrap" width="70%">
	    				<input class="easyui-combobox" name="carTypeId" id="e-carTypeId"
							data-options=" url:'sys_optional_carTypeCombobox',
			                    method:'get',
			                    valueField:'id',
			                    textField:'text',
			                    panelHeight:'auto', required:true,missingMessage:'车型不能为空'">
	    			</td>
	    		</tr>
				<tr height="42px;">
	    			<td nowrap="nowrap" width="30%">订单金额阈值（元）:</td>
	    			<td nowrap="nowrap" width="70%">
	    				<input class="easyui-textbox"　name="budgetAmount" id="e-moneyLimit" data-options="required:true,missingMessage:'订单金额阈值不能为空'">
	    			</td>
	    		</tr>
				<tr height="42px;">
	    			<td nowrap="nowrap" width="30%">租车时限（小时）:</td>
	    			<td nowrap="nowrap" width="70%">
	    				<input class="easyui-textbox"　name="budgetTime" id="e-timeLimit" data-options="required:true,missingMessage:'租车时限不能为空'">
	    			</td>
	    		</tr>
	    	</table>
   		</form>
   </div>
</body>
</html>