<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>调度员监控中心</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/style.css"/>
<link rel="stylesheet" type="text/css" href="ui_lib/css/sdmenu.css"/>

<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<!-- 高德地图，key是可以通过高德地图api申请 -->
<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
<script type="text/javascript" src="monitorCenter/chargingMonitorCenter.js"></script>
<link rel="stylesheet" type="text/css" href="ui_lib/css/chargingPileStyle.css"/>
<style type="text/css">
 	.table-cls{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	}
	
	.table-cls td{
	  border-left: 1px solid #E0E0E0;
	  border-top: 1px solid #E0E0E0;
	}
	
	.myButton {
	-moz-box-shadow: 0px 1px 0px 0px #f0f7fa;
	-webkit-box-shadow: 0px 1px 0px 0px #f0f7fa;
	box-shadow: 0px 1px 0px 0px #f0f7fa;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #5ac8ed), color-stop(1, #019ad2));
	background:-moz-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:-webkit-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:-o-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:-ms-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:linear-gradient(to bottom, #5ac8ed 5%, #019ad2 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#5ac8ed', endColorstr='#019ad2',GradientType=0);
	background-color:#5ac8ed;
	-moz-border-radius:6px;
	-webkit-border-radius:6px;
	border-radius:6px;
	border:1px solid #057fd0;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:15px;
	font-weight:bold;
	padding:13px 3px;
	text-decoration:none;
	text-shadow:0px -1px 0px #5b6178;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #019ad2), color-stop(1, #5ac8ed));
	background:-moz-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:-webkit-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:-o-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:-ms-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:linear-gradient(to bottom, #019ad2 5%, #5ac8ed 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#019ad2', endColorstr='#5ac8ed',GradientType=0);
	background-color:#019ad2;
}
.myButton:active {
	position:relative;
	top:1px;
}
</style>
</head>
<body style="width:100%; height:100%; position: absolute;">
	<!-- 调度员地图展示 -->
	<input type="hidden" id="locations" value='${locations}'/>
	<div id="pMap" class="easyui-panel" title="充电桩地图展示"
	    style="width:100%;height:70%;padding:10px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',
	    collapsible:true,maximizable:true">
	    <div id="dispatcherContent"  style="height: 100%; width:100%;"></div>
	</div>
	<div id="pList" class="easyui-panel" title="充电网点列表展示"
		style="width:100%;height:30%;padding-bottom:20px;background:#fafafa;"
		data-options="iconCls:'icon-car-repair',maximizable:true">
		<table id="grid" toolbar="#toolbar"></table>
  
		<div id="toolbar" style="height:auto">
		  <div><form name="scarMrgForm" id="scarMrgForm">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="scityCode"
						data-options=" url:'sys_optional_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
			
			网点:<input type="text" name="parkName" class="easyui-textbox" id="s-parkName">
			充电枪的状态:<input class="easyui-combobox" name="connectorStatus" id="connectorStatus"
						data-options=" 
						valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '离网',
							value: '0'
						},{
							label: '空闲',
							value: '1'
						},{
							label: '充电中',
							value: '3'
						},{
							label: '已被预约',
							value: '4'
						},{
							label: '故障',
							value: '255'
						}]">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="btnReload" onclick="refresh();">刷新</a>
			</form>
		  </div>
		</div>
	</div>
	
	<!-- 预约充电桩 -->
   	<div id="orderedCharingPile" class="easyui-dialog" closed="true" style="width:400px;height:200px;padding-left:20px">
		<form id="orderedCharingPileForm" method="post">
				<input type="hidden" name="equipmentId" id="equipmentId">
				<input type="hidden" name="parkNo" id="parkNo">
				<input type="hidden" name="connectorId" id="connectorId">
				<input type="hidden" name="parkId" id="parkId">
				<input type="hidden" name="parkName" id="parkName">
				<input type="hidden" name="cooperationType" id="cooperationType">
				<input type="hidden" name="operatorId" id="operatorId">
				<br>
			   <div class="fitem">
	               <label>手机号码:</label>
	               <input id="phone" name="phone" class="easyui-textbox" style="width:200px;height:20px;">
	           </div>
	           <br>
	           <div class="fitem">
	               <label>车牌号:&nbsp;&nbsp;</label>
	               <input id="lpn" name="lpn" class="easyui-textbox" style="width:200px;height:20px;">
	           </div>
		</form>
	</div>
	
	<!-- 启动充电 -->
	<div id="startCharging" class="easyui-dialog" closed="true" style="width:400px;height:100px;padding-left:20px">
		<form id="startChargingForm" method="post">
				<input name="connectorCode" id="connectorCode" type="hidden">
				<input name="connectorId" id="connectorId1" type="hidden">
				<br>
			   <div class="fitem">
	               <label>手机号码:</label>
	               <input id="phone1" name="phone" class="easyui-textbox" style="width:200px;height:25px;">
	           </div>
	      
		</form>
	</div>
	
	<!-- 结束充电 -->
	<div id="endCharging" class="easyui-dialog" closed="true" style="width:400px;height:100px;padding-left:20px">
		<form id="endChargingForm" method="post">
				<input name="connectorId" id="my-connectorId" type="hidden">
		</form>
	</div>
	
</body>
</html>