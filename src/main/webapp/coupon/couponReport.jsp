<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>优惠券报表</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="coupon/couponReport.js"></script>
	<script type="text/javascript" src="common/reportCommon.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
	
	<style type="text/css">
/* 		.ftitle {
			font-size: 14px;
			font-weight: bold;
			padding: 5px 0;
			margin-bottom: 10px;
			border-bottom: 1px solid #ccc;
		}
		
		.fitem {
			margin-bottom: 5px;
		} */
		
		.fitem label {
			margin-left:10px; 
			margin-right:5px;
			display: inline-block;
		}
		
/* 		.fitem input {
			width: 160px;
		}
		
		.fitem a {
			margin-right: 5px;
		} */
	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" class="fitem" style="height:auto">
 	    	<!-- 存放年月季度的值 -->
 	    	<input type="hidden" name='hidBegin' id="hidBegin" />
 	    	<input type="hidden" name='hidEnd' id="hidEnd" />
 	    	
 	    	<input type="hidden" name='hidYear' id="hidYear" />
 	    	<input type="hidden" name='hidMonth' id="hidMonth" />
 	    	<input type="hidden" name='hidQuarter' id="hidQuarter" />
 	    	
			活动名称:<input id="_title"  name="_title" class="easyui-textbox" style="width:80px"/>
			
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
			
			从 
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
			到 
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
         </div>
   
   
   		<div id="couponDetailView" class="easyui-dialog" closed="true"
			style="padding:10px 20px 30px 20px">
			 <table id="couponDetailDataGrid" 
			 	cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px; left:80px;">
			 	批次号:<input id="_batch_no"  name="_batch_no" class="easyui-textbox" style="width:150px" />
				会员姓名:<input class="easyui-textbox" style="width:150px" id="memberName" name="memberName"/>
				<input type="hidden" id="_title"/>
				<input type="hidden" id="_type"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btn">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清除</a>
			 </table>
	   </div> 
	</body>
</html>