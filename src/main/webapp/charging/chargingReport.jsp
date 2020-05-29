<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>充电桩运营报表</title>
	<link rel="stylesheet" type="text/css"
		href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css"
		href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js"
		charset="gb2312"></script>
	<script type="text/javascript" src="common/reportCommon.js"></script>
	<script type="text/javascript" src="charging/chargingReport.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
	<style type="text/css">
		/*合计单元格样式*/
		.subtotal { font-weight: bold; }
		.fitem label {
			margin-left: 10px;
			margin-right: 5px; 
			display:  inline-block;
		}
		.detailGridStyle {
			font-size: 14px;
			margin: auto;
			margin-top: 0px; 
			left:180px;
		}
		#cityParkTree{
			width:200px;
			height:1000px;
			font-size: 14px;
			background-color: #d7ebf9;
			float:left;
			margin:5px;
			display:  inline-block;
		}
		#dataGrid{
			font-size: 14px;
			float:right;
			margin:5px;
			display:  inline-block;
		}
	</style>

</head>

<body>
	<div title="城市-充电站">
		<div>
			<ul id="cityParkTree" class="easyui-tree"></ul>
			<!-- 城市编码、网点合作类型、层级的隐藏域 -->
			<input type="hidden" name="hidCityCode" id="hidCityCode" />
 	    	<input type="hidden" name="hidType" id="hidType" />
 	    	<input type="hidden" name="hidLevel" id="hidLevel" />
 	    	<input type="hidden" name="hidParkId" id="hidParkId" />
 	    	<input type="hidden" name="hidEquipmentId" id="hidEquipmentId" />
		</div>
		<div>
		<table id="dataGrid" toolbar="#toolbar" ></table>
		<div id="toolbar" class="fitem" style="height:auto">
        	 <form id="chargingReportForm" method="post" action="#" style="height:15px">
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
			
		 	<!-- 查询按钮 -->
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
            </form>
         </div>
		</div>
		</div>

		<!-- 一个桩的明细 -->
		<div id="onePileChargingDetailView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<table id="onePileChargingDetailGrid" class="detailGridStyle" cellpadding="5" ></table>
		</div>

</body>
</html>