<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta content="" charset="utf-8">
	<title>车辆日租报表页面</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/carRentReport.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
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
		<form action="" id="carReportForm">
			<label class="label">所属城市:&nbsp;</label>
			<input class="easyui-combobox" " name="code" id="code" data-options=" url:'sys_cityCombobox',
	                  method:'get',
	                  valueField:'id',
	                  textField:'text',
	                  panelHeight:'auto'">
	        <label class="label">车牌号码:&nbsp;</label>
			<input class="easyui-textbox"" name="lpn" id="lpn" style="width: 120px;">
			<label class="label">手机号码:&nbsp;</label>
			<input class="easyui-textbox"" name="phone" id="phone" style="width: 120px;">
			<label class="label">会员姓名:&nbsp;</label>
			<input class="easyui-textbox"" name="memberName" id="memberName" style="width: 120px;">
			<label class="label">是否开票:&nbsp;</label>
			<input class="easyui-combobox" name="invoiceStatus" style="width: 120px;" id="invoiceStatus"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '全部',
						value: ''
					},{
						label: '未开票',
						value: '1'
					},{
						label: '开票中',
						value: '2'
					},{
						label: '已开票',
						value: '3'
					}]"
					  >
			时间:<input id="bt"  name="beginTime" class="easyui-datetimebox s" style="width:100px"/>
          	    到:<input id="et"  name="endTime" class="easyui-datetimebox e" style="width:100px"/>
          	<r:FunctionRole functionRoleId="selectCarRentReport">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
          	</r:FunctionRole>
			
			<r:FunctionRole functionRoleId="carReportExecl">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
			</r:FunctionRole>
		</form>    
	</div>
  </body>
</html>
