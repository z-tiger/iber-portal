<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>违章事故收入报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js"></script>
	<script type="text/javascript" src="report/orderAttachedReport.js"></script>
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
	#sp1{margin-right:10px;}
	</style>
</head>

<body>
   
	<table id="order_attached_grid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<form id="attachedReportForm">
			车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width: 100px">
			会员姓名:<input type="text" id="memberName"  class="easyui-textbox" style="width: 100px">
			手机号码:<input type="text" id="phoneNumber"  class="easyui-textbox" style="width: 100px">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px"
				   data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
			订单类型: <input class="easyui-combobox" name="type" id="type" style="width: 80px"
						 data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '维修',
							value: '1'
						},{
							label: '救援',
							value: '2'
						}],
	                    panelHeight:'auto'">
            支付状态: <input class="easyui-combobox" name="payStatus" id="payStatus" style="width: 80px"
                         data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '',
							value: ''
						},{
							label: '已支付',
							value: '1'
						},{
							label: '未支付',
							value: '0'
						}],
	                    panelHeight:'auto'">
			周期:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox s" style="width:160px"/>
			到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datetimebox e" style="width:160px"/>

		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="l-btn-icon icon-print" plain="true" id="outPutQuery">导出Excel</a>
		</form>
	</div>

</body>
</html>