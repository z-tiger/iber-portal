<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>车辆时租明细表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/carApplyDetail.js"></script>	
	
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
	#data{
		margin:0 auto;
	}
	</style>
</head>

<body>
   
	<table id="dataGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
			<form id="data">
				<label class="label">车牌:&nbsp;</label>
				<input class="easyui-textbox"" name="lpn" id="lpn" style="width: 120px;">
				<label class="label">用户名:&nbsp;</label>
				<input class="easyui-textbox"" name="name" id="name" style="width: 120px;">
				<label class="label">手机号:&nbsp;</label>
				<input class="easyui-textbox"" name="phone" id="phone" style="width: 120px;">
				<label class="label">身份证:&nbsp;</label>
				<input class="easyui-textbox"" name="idCard" id="idCard" style="width: 120px;">
				<label class="label" style="text-align: center;">开始时间:&nbsp;</label>
				<input id="beginTime" name="beginTime" class="easyui-datebox" data-options="required:true,missingMessage:'开始时间不能为空'"/>
				<label class="label" style="text-align: center;">结束时间:&nbsp;</label>
				<input id="endTime" name="endTime" class="easyui-datebox" data-options="required:true,missingMessage:'结束时间不能为空'"/>
				<label class="label">车辆型号：&nbsp;</label>
				<input class="easyui-combobox" " name="carType" id="carType" data-options=" url:'sys_carTypeCombobox',
                   method:'get',
                   valueField:'id',
                   textField:'text',
                   panelHeight:'auto'">	
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
				<r:FunctionRole functionRoleId="car_usage_item_export_excel">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
				</r:FunctionRole>
			       
		   </form>
	</div>
</body>
</html>