<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="r" uri="/tags/platform-taglib.tld" %>
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
<script type="text/javascript" src="dispatcherMonitor/dispatcherMonitor.js"></script>
<link rel="stylesheet" type="text/css" href="ui_lib/css/dispatcherMonitorCenterStyle.css"/>
<style type="text/css">
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
	height:35px;
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
	font-size:12px;
	font-weight:bold;
	padding:13px 3px;
	text-decoration:none;
	text-shadow:0px -1px 0px #5b6178;
	text-align:middle;
	line-height: 8px;
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
</style>
</head>
<body style="width:100%; height:100%; position: absolute;">
	<!-- 调度员地图展示 -->
	<input type="hidden" id="locations" value='${locations}'/>
	<div id="pMap" class="easyui-panel" title="员工地图展示"
	    style="width:100%;height:70%;padding:10px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',
	    collapsible:true,maximizable:true">
	    <div id="dispatcherContent"  style="height: 100%; width:100%;"></div>
	</div>
	<div id="pList" class="easyui-panel" title="员工信息列表"
	    style="width:100%;height:30%;mar;padding-bottom:20px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',maximizable:true">
	    <table id="dataGrid" toolbar="#toolbar"></table> 
		<div id="toolbar" style="height:auto">
	      <div><form name="scarMrgForm" id="scarMrgForm">
			员工名称:<input type="text" name="dispatcherName" class="easyui-textbox" id="_dispatcherName">
			所属网格:
			<input name="griddingName" id="griddingName" class="easyui-combobox"
				data-options=" url:'sys_griddingNameCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"/>
	                员工类型:<input class="easyui-combobox" name="empType" id="empType" data-options="
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto',
		                    data:[{   
    							'id':'1',   
   							    'text':'调度员'  
							},{   
							    'id':'2',   
							    'text':'救援员'  
							},{   
							    'id':'3',   
							    'text':'维保员'  
							},{   
							    'id':'4',   
							    'text':'城市管理员'  
							}] ">
	               工作状态:<input class="easyui-combobox" name="w-status" id="w-status" data-options="
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto',
		                    data:[{   
    							'id':'working',   
   							    'text':'上班'  
							},{   
							    'id':'closed',   
							    'text':'下班'  
							}] ">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<r:FunctionRole functionRoleId="employee_clear_search_car">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClearParam" onclick=";">清空</a>
			</r:FunctionRole>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="btnUpdate" onclick="refresh();">刷新</a>
			</form>
		  </div>
	   </div>
	</div>
	
	<div id="editView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="editViewForm" id="editViewForm">
	         	<input type="hidden" name="id" id="e-id">
			   <div class="fitem">
			   		<tr>
			   			<td>执行人员:</td>
			   			<td><input class="easyui-textbox" name="employeeId" id="employee" data-options="disabled:true">
		                </td>
		            </tr>
		            
		            <br><br>
			   		<tr>
			   			<td>任务类型:</td>
			   			<td><input class="easyui-combobox" name="taskType" id="taskType" data-options=" 
			   			    required:true,
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto',
		                    editable:false,
		                    data:[{   
    							'id':'1',   
   							    'text':'调度'  
							},{   
							    'id':'2',   
							    'text':'维护'  
							},{   
							    'id':'3',   
							    'text':'充电'  
							},{   
							    'id':'4',   
							    'text':'维修'  
							},{   
							    'id':'6',   
							    'text':'其它'  
							}]">
		                </td>
			   			<td>车牌号:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="lpn" id="lpn" data-options="panelHeight:'250px'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>所在网点:</td>
			   			<td><input class="easyui-combobox" name="beginParkId" id="beginParkId" data-options="panelHeight:'250'">
		                </td>
			   			<td>目的网点:</td>
			   			<td><input class="easyui-combobox" name="endParkId" id="endParkId" data-options="panelHeight:'250'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>任务等级:</td>
			   			<td><input class="easyui-combobox" name="taskLevel" id="taskLevel" data-options=" url:'get_task_grade_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
			   			<td>任务期限:</td>
			   			<td>
<%--			   			<input class="easyui-combobox" name="requestPayTime" id="requestPayTime" data-options="--%>
<%--		                    valueField:'id',--%>
<%--		                    textField:'text',--%>
<%--		                    data:[{'id':'0.5小时','text':'0.5小时'},{'id':'1小时','text':'1小时'},--%>
<%--		                          {'id':'1.5小时','text':'1.5小时',},{'id':'2小时','text':'2小时'},--%>
<%--		                          {'id':'2.5小时','text':'2.5小时'}--%>
<%--							    ]">--%>
<%--		                </td>--%>
                            <input class="easyui-datetimebox" name="deadline" id="deadline" >
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
			   <div id="gridContent"  style="height: 100%; width:100%;"></div>
	       </form>
   		</div>
   		<!-- 还车/强制还车 -->
		<div id="returnCarOper" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
			<form id="returnCarForm">
				<input type="hidden" name="employeeId" id="e-employeeId">
	            <div class="fitem">
	                <br/>
	                <label>选网点:</label>
	                <input class="easyui-combobox" id="parkId" name="parkId" 
	            		data-options=" url:'park_list_code',
		                    method:'get',
		                    valueField:'id',
		                    textField:'name',
		                    panelHeight:'auto'" required="true" style="width:250px;height:40px;">
	            </div>
	            <br/>
	            <r:FunctionRole functionRoleId="employee_force_return_car">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"  id="forceReturnCar">强制还车</a> 
				</r:FunctionRole>
	             <br/><br/>
	             <div id="ss" style="display:none">
		           	<label>下线状态:</label>
		           	<input class="easyui-combobox" id="status" name="status"
		           			data-options="
		                    valueField:'value',
		                    textField:'label',
		                    editable:false,
		                    data: [{
								label: '维修',
								value: 'repair'
							},{
								label:'维护',
								value:'maintain'
							},{
								label:'补电',
								value:'charging'
							}],
		                    panelHeight:'auto'"  style="width:250px;height:40px;" >
	             <br/><br/>
		             <label>还车原因:</label>
		             <input class="easyui-textbox" name="reason" id="reason"
		    				data-options="multiline:true" style="height:70px;width:250px">
            	</div>
			</form>
		</div> 
</body>
</html>