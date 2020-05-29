<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员资金报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/memberCapital.js?t=11"></script>	
	
	<style type="text/css" >
	#conversionForm {
		margin: 0;
		padding: 10px 30px;
	}
	
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
   
	<table id="dataGrid" toolbar="#toolbar"></table>
	<!--列表工具栏 -->
	<div id="toolbar" style="height:26px">
	<form action="" id="data">
					<label class="label">所属城市:&nbsp;</label>
					<input class="easyui-combobox"  name="code" id="code" data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
					<label class="label">手机号码:&nbsp;</label>
					<input class="easyui-textbox" name="phone" id="phone" style="width: 120px;">
					<label class="label">会员姓名:&nbsp;</label>
					<input class="easyui-textbox" name="name" id="name" style="width: 120px;">
		
			<label class="label" style="text-align: center;">注册日期:&nbsp;</label>
			<input id="beginTime" name="beginTime" class="easyui-datebox" data-options="required:true,missingMessage:'开始时间不能为空'"/>
			<label class="label" style="text-align: center;">到：</label>
			<input id="endTime" name="endTime" class="easyui-datebox" data-options="required:true,missingMessage:'结束时间不能为空'"/>	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			
			<r:FunctionRole functionRoleId="member_amount_form_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
			</r:FunctionRole>
		</form>    
	</div>
	<div id="showRechargeDetailView" class="easyui-dialog" closed="true" style="width:1000px;height:500px;padding-bottom: 25px;">
	<table id="showMemberRechargeGrid">
	</div>
	<form action="export_member_capital_excel" name="exportForm" id="exportForm" method="post">
	    <input type="hidden" name="code1" id="code1">
	    <input type="hidden" name="phone1" id="phone1">
	    <input type="hidden" name="name1" id="name1">
	    <input type="hidden" name="beginTime1" id="beginTime1">
	    <input type="hidden" name="endTime1" id="endTime1">
	</form>
	
</body>
</html>