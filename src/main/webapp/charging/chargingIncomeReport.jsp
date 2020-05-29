<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电桩收入报表</title>
	<link rel="stylesheet" type="text/css"
		href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css"
		href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js"></script>

	<script type="text/javascript" src="charging/chargingIncomeReport.js"></script>
	<script type="text/javascript" src="common/checkCommon.js"></script>
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
		#incomeDetailGrid{
			font-size: 14px;
			float:right;
			margin:5px;
			display:  inline-block;
		}
	</style>

</head>

<body>
	<div title="城市-充电站">
		<div style="height:820px;position:absolute;width: 15%">
			<ul id="cityParkTree" class="easyui-tree"></ul>
			<!-- 城市编码、网点合作类型、层级的隐藏域 -->
			<input type="hidden" name="hidCityCode" id="hidCityCode" />
 	    	<input type="hidden" name="hidType" id="hidType" />
 	    	<input type="hidden" name="hidLevel" id="hidLevel" />
 	    	<input type="hidden" name="hidParkId" id="hidParkId" />
 	    	<input type="hidden" name="hidEquipmentId" id="hidEquipmentId" />
		</div>
		<div style="height:820px;position: absolute;width: 87%;margin-left: 12%;">
		<table id="incomeDetailGrid" toolbar="#toolbar" ></table>
		<div id="toolbar" class="fitem" style="height:auto">
			<input type="hidden" name="cityCode" id="cityCode" />
 	    	<input type="hidden" name="type" id="type" />
 	    	<input type="hidden" name="level" id="level" />
 	    	<input type="hidden" name="parkId" id="parkId" />
 	    	<input type="hidden" name="equipmentId" id="equipmentId" />
			<!--
			省: <select id="cb_provinceList" class="easyui-combobox" style="width:150px;"
        			data-options="url:'province_list',
        					  method:'get',
        					  valueField:'id',
	    					  textField:'name',
	    					  panelHeight:'auto'"></select>
	    	<span>&nbsp;&nbsp;</span>
			城市: <select id="cb_cityList" class="easyui-combobox" style="width:150px;"
        			data-options="url:'city_list',
        					  method:'get',
        					  valueField:'code',
	    					  textField:'name',
	    					  panelHeight:'auto'"></select>
        	<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
        	 -->
			<!-- 存放年月季度的值 -->
			<input type="hidden" name='hidBegin' id="hidBegin" />
			<input type="hidden" name='hidEnd' id="hidEnd" />
			<input type="hidden" name='hidYear' id="hidYear" />
			<input type="hidden" name='hidMonth' id="hidMonth" />
			<input type="hidden" name='hidQuarter' id="hidQuarter" />

            <%--存放支付的年月季度值--%>
            <input type="hidden" name='hidPayBeginTime' id="hidPayBeginTime" />
            <input type="hidden" name='hidPayEndTime' id="hidPayEndTime" />
            <input type="hidden" name='hidPayYear' id="hidPayYear" />
            <input type="hidden" name='hidPayMonth' id="hidPayMonth" />
            <input type="hidden" name='hidPayQuarter' id="hidPayQuarter" />

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

			充电开始时间
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox s"  name="begin_full" id="begin_full" style="width:150px;height:24px">
			</label>

			<label name="label_year">
				<input class="easyui-numberbox"  name="year" id="begin_year"
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="begin_month" min="1" max="12"
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				月
			</label>

			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="begin_quarter" min="1" max="4"
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				季度
			</label>

			<span>&nbsp;&nbsp;</span>
			到
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox e"  name="end_full" id="end_full" style="width:150px;height:24px">
			</label>

			<label name="label_year" >
				<input class="easyui-numberbox"  name="year" id="end_year"
					<%--data-options="required:true, missingMessage:'数值要比开始的年份大'"--%>
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="end_month" min="1" max="12"
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				月
			</label>
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="end_quarter" min="1" max="4"
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				季度
			</label>


            支付时间:
            <label class="cls_label_datetime">
                <input class="easyui-datetimebox s"  name="pay_begin_full" id="pay_begin_full" style="width:150px;height:24px">
            </label>

            <label name="label_year">
                <input class="easyui-numberbox"  name="pay_year" id="pay_begin_year"
                       <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                年
            </label>
            <label name="label_month">
                <input class="easyui-numberbox"  name="pay_month" id="pay_begin_month" min="1" max="12"
                       <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                月
            </label>

            <label name="label_quarter">第
                <input class="easyui-numberbox" name="pay_quarter" id="pay_begin_quarter" min="1" max="4"
                       <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                季度
            </label>

            <span>&nbsp;&nbsp;</span>
            到:

            <!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
            <label class="cls_label_datetime">
                <input class="easyui-datetimebox e"  name="pay_end_full" id="pay_end_full" style="width:150px;height:24px">
            </label>

            <label name="label_year" >
                <input class="easyui-numberbox"  name="pay_year" id="pay_end_year"
                       <%--data-options="required:true, missingMessage:'数值要比开始的年份大'"--%>
                       style="width:100px;height:24px">
                年
            </label>
            <label name="label_month">
                <input class="easyui-numberbox"  name="pay_month" id="pay_end_month" min="1" max="12"
                       <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                月
            </label>
            <label name="label_quarter">第
                <input class="easyui-numberbox" name="pay_quarter" id="pay_end_quarter" min="1" max="4"
                       <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                季度
            </label>


            是否开票:
			<input class="easyui-combobox" name="invoiceStatus" id="invoiceStatus"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '已开票',
							value: '3'
						},{
							label: '未开票',
							value: '1'
						}]">

            <!-- 查询按钮 -->
		 	<r:FunctionRole functionRoleId="select_chargeInComeReport">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQueryIncome">查询</a>
            </r:FunctionRole>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="l-btn-icon icon-clear" plain="true" id="clearBtn">清空</a>
            <r:FunctionRole functionRoleId="export_chargeInComeForm">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
			</r:FunctionRole>
         </div>
		</div>
		</div>

</body>
</html>