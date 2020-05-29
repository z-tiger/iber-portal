<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>邮寄地址管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="memberMail/memberMail.js"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>

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
   <form action="" id="mailForm">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
		手机:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
		<!-- 订单类型:<input class="easyui-combobox" name="orderType" id="s-orderType"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '充电',
							value: 'charging'
						},{
							label: '时租',
							value: 'TS'
						}]"> -->
		<r:FunctionRole functionRoleId="select_mail">	
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		</r:FunctionRole>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		<r:FunctionRole functionRoleId="export_mail">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>	
   </div>

	<div id="editView" class="easyui-dialog" closed="true" style="width: 600px;height: 300px;">
		<form id="editForm" method="post" action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
		<input type="hidden" id='e-id' name="id" >
			<table cellpadding="5"   style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
					<tr height="32px;">
		    			<td nowrap="nowrap">会员名称:</td>
		    			<td nowrap="nowrap">
		    				 <span name='memberName' id="e-memberName"></span>
		    			</td>
		    		</tr>
		    		<tr height="32px;">
		    			<td nowrap="nowrap">会员电话:</td>
		    			<td nowrap="nowrap">
		    				 <span name='memberPhone' id="e-memberPhone"></span>
		    			</td>
		    		</tr>
		    		<tr height="32px;">
		    			<td nowrap="nowrap">订单类型：</td>
		    			<td nowrap="nowrap" colspan="3">
		    			 	<span name='orderType' id="e-orderType"></span>
		    			</td>
		    		</tr>
					<tr height="32px;">
		    			<td nowrap="nowrap">收件人:</td>
		    			<td nowrap="nowrap" colspan="3">
		    				<input class="easyui-textbox"  id="e-receiver" name="receiver" style="width:280px;height:24px" data-options="required:true,missingMessage:'收件人不能为空'">
		    			</td>
		    		</tr>
					<tr height="32px;">
		    			<td nowrap="nowrap">收件电话:</td>
		    			<td nowrap="nowrap" colspan="3">
		    				 <input class="easyui-textbox"  id="e-receiverPhone" name="receiverPhone" style="width:280px;height:24px" data-options="required:true,missingMessage:'收件人电话不能为空'">
		    			</td>
		    		</tr>
		    		<tr height="32px;">
		    			<td nowrap="nowrap">所在地区：</td>
		    			<td nowrap="nowrap">
		    				<input class="easyui-textbox"  id="e-province" name="province" style="width:80px;height:24px" >
		    			</td>
		    			<td nowrap="nowrap">
		    				<input class="easyui-textbox"  id="e-city" name="city" style="width:80px;height:24px" >
		    			</td>
		    			<td nowrap="nowrap">
		    				<input class="easyui-textbox"  id="e-area" name="area" style="width:80px;height:24px" >
		    			</td>
		    		</tr>
		    		<tr height="32px;">
		    			<td nowrap="nowrap">详细地址:</td>
		    			<td nowrap="nowrap" colspan="3">
		    				<input class="easyui-textbox"  id="e-address" name="address" style="width:280px;height:24px" data-options="required:true,missingMessage:'详细地址不能为空'">
		    			</td>
		    		</tr>
		    	
					<!-- <tr height="32px;">
		    			<td nowrap="nowrap">email:</td>
		    			<td nowrap="nowrap" colspan="3">
		    				<input class="easyui-textbox"  id="e-email" name="email" style="width:280px;height:24px" >
		    			</td>
		    		</tr> -->
			</table>
		</form>
	</div>


</body>
</html>