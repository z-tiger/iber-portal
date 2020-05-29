<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
  <head>
  	<meta charset="utf-8">
    <title>车辆下线申请页面</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="car/carOffline.js"></script>
  </head>
  <body>
    	<table id="dataGrid" toolbar="#toolbar"></table>
    	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:25px">
           <form id="queryCarOfflineApply">
			所属城市:<input class="easyui-combobox" name="cityCode" id="cityCode"
						data-options=" url:'sys_optional_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'" style="width:150px;">
			车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width:150px;">
			变更原因:<input type="text" name="reason" class="easyui-textbox" id="reason" style="width:150px;">
			审核状态:<input class="easyui-combobox" id="auditResult" name="auditResult" 
			           			data-options="
			                    valueField:'value',
			                    textField:'label',
			                    data: [{
									label: '全部',
									value: ''
								},{
									label:'审核通过',
									value:'1'
								},{
									label:'审核不通过',
									value:'2'
								},{
									label:'未审核',
									value:'0',
									selected : true
								}],
			                    panelHeight:'auto'"  style="width:150px;" >
	                    申请状态:<input class="easyui-combobox" id="offLineType" name="offLineType" 
	           			data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label:'维修',
							value:'1'
						},{
							label:'维护',
							value:'2'
						},{
							label:'补电',
							value:'3'
						},{
							label:'空闲',
							value:'4'
						}],
	                    panelHeight:'auto'"  style="width:150px;" >  
			申请人:<input type="text" name="applicant" class="easyui-textbox" id ="applicant">
			<r:FunctionRole functionRoleId="select_car_offline">        	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			</r:FunctionRole> 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清除</a>
			<r:FunctionRole functionRoleId="export_car_offline">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
			</r:FunctionRole>
		</form>
   </div>
    <!-- add -->
         <div id="AuditApplyDialog" class="easyui-dialog" closed="true">
			<form id="offlineApplyForm" method="post" style="margin-top: 20px;">
			    <input name = "id" id="l-id" type="hidden" class="easyui-textbox" >
	                             所属地区: <input name="cname" id="l-cname" class="easyui-textbox" readonly="readonly">
	            <br><br>
	            &nbsp;&nbsp;&nbsp;&nbsp;
	                            申请人: <input name="applicant" id="l-applicant" class="easyui-textbox" readonly="readonly">
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            车牌号码: <input name="lpn" id="l-lpn" class="easyui-textbox" readonly="readonly">
	           <br><br>&nbsp;
	                           当前状态: <input name="cstatus" id="l-cstatus" class="easyui-textbox" readonly="readonly">
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           	剩余电量: <input name="restBattery" id="l-restBattery" class="easyui-textbox" readonly="readonly">
	           <br><br>&nbsp;
	                          电瓶电压: <input name="smallBatteryVoltage" id="l-smallBatteryVoltage" class="easyui-textbox" readonly="readonly">
	          	 设备上报时间: <input name="cupdateTime" id="l-cupdateTime" class="easyui-textbox" readonly="readonly">
	           <br><br>&nbsp;
	                            申请状态: <input name="offLineType" id='l-offLineType' class="easyui-textbox" readonly="readonly">
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                            所在网点: <input name="pname" id='l-pname' class="easyui-textbox" readonly="readonly">
	           <br><br>&nbsp;
	                           申请时间: <input name="createTime" id="l-createTime" class="easyui-textbox" readonly="readonly">
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                          变更原因: <input class="easyui-textbox" name="reason" id="l-reason"
		    				data-options="multiline:true" style="height:50px;" readonly="readonly">
	           <br>&nbsp;
	                           审核结果: <input name="auditResult" id="l-auditResult" class="easyui-textbox">
			</form>
		 </div>
  </body>
</html>
