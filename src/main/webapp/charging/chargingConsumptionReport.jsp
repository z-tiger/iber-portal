<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>用电量</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="charging/chargingConsumptionReport.js"></script>
	<script type="text/javascript" src="common/reportCommon.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			所属城市:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;" 
				data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			网点名:<input id="stationName"  name="stationName" class="easyui-textbox"/>
			网点类型:<input class="easyui-combobox" name="cooperationType" id="cooperationType" style="width: 80px;" 
				data-options="
					editable:false, panelHeight:'auto',
					valueField: 'value',
					textField: 'label',
					data: [{
						label: '自有网点',
						value: '0'
					},{
						label: '合作网点',
						value: '1'
					}]">
			桩类型:<input class="easyui-combobox" name="equipmentType" id="equipmentType" style="width: 80px;" 
				data-options="
					editable:false, panelHeight:'auto',
					valueField: 'value',
					textField: 'label',
					data: [{
						label: '直流',
						value: '1'
					},{
						label: '交流',
						value: '2'
					},{
						label: '交直流一体',
						value: '3'
					}]">
			
			<!-- 存放年月季度的值 -->
			<input type="hidden" name='hidBegin' id="hidBegin" /> 
			<input type="hidden" name='hidEnd' id="hidEnd" /> 
			<input type="hidden" name='hidYear' id="hidYear" /> 
			<input type="hidden" name='hidMonth' id="hidMonth" /> 
			<input type="hidden" name='hidQuarter' id="hidQuarter" /> 
			周期:<input class="easyui-combobox" name="period" id="period" style="width:100px"
					data-options=" 
						valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '自选区间',
							value: '0'
						},{
							label: '月',
							value: '1'
						},{
							label: '季度',
							value: '2'
						},{
							label: '年',
							value: '3'
						}]">
			
			<!-- 时间区间 -->
			<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
			
			开始时间 
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox s"  name="begin_full" id="begin_full" style="width:150px;height:24px">
			</label>
			
			<label name="label_year">
				<input class="easyui-numberbox"  name="year" id="begin_year" 
					data-options="required:true"
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="begin_month" min="1" max="12" 
					data-options="required:true"
					style="width:100px;height:24px">
				月
			</label>
			
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="begin_quarter" min="1" max="4" 
					data-options="required:true"
					style="width:100px;height:24px">
				季度
			</label>
			
			<span>&nbsp;&nbsp;</span>
			结束时间 
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox e"  name="end_full" id="end_full" style="width:150px;height:24px">
			</label>
			
			<label name="label_year" >
				<input class="easyui-numberbox"  name="year" id="end_year" 
					data-options="required:true, missingMessage:'数值要比开始的年份大'"
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="end_month" min="1" max="12" 
					data-options="required:true"
					style="width:100px;height:24px">
				月
			</label>
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="end_quarter" min="1" max="4" 
					data-options="required:true"
					style="width:100px;height:24px">
				季度
			</label>
			
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
         </div>
         
	</body>
</html>