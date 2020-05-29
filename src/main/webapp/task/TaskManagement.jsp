<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>任务管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="task/taskManagement.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
	<style type="text/css">
		/*.ftitle {
			font-size: 14px;
			font-weight: bold;
			padding: 5px 0;
			margin-bottom: 10px;
			border-bottom: 1px solid #ccc;
		}
		
		.fitem {
			margin-bottom: 5px;
		}*/
		
		.fitem label {
			display: inline-block;
			width: 80px;
		}
		
		/*.fitem input {
			width: 160px;
		}
		
		.fitem a {
			margin-right: 5px;
		}*/

	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" class="fitem" style="height:auto">
 	    <form action="" id="taskForm">
 	   	     姓名:<input id="name"  name="name" class="easyui-textbox" style="width: 150px;"/>
 	        所属地区:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			任务名称:<input id="_taskName"  name="taskName" class="easyui-textbox" style="width:150px"/>
			任务类型:<input class="easyui-combobox" name="taskType" id="_taskType" style="width: 100px;"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '调度任务',
							value: '1'
						},{
							label: '救援任务',
							value: '5'
						},{
							label:'维保任务',
							value:'0'
						}],
	                    panelHeight:'auto'" >
	                   任务状态:<input class="easyui-combobox" name="status" id="_taskStatus" style="width: 100px;"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: '0'
						},{
							label: '创建',
							value: '1'
						},{
							label: '正在执行',
							value: '2'
						},{
							label:'完成',
							value:'3'
						},{
							label:'取消',
							value:'4'
						}],
	                    panelHeight:'auto'" >
			完成时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox s" style="width:160px"/>
			到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datetimebox e" style="width:160px"/>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="update_task">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="export_task">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
			</r:FunctionRole>
            </form>
	    </div>
	   
      	<!-- 调度任务编辑对话框 -->
		 <div id="editView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="editViewForm" id="editViewForm">
	         	<input type="hidden" name="id" id="e-id">
	         	<input type="hidden" name="taskTypeNo" id="e-taskTypeNo">
	         	<input type="hidden" name="oldEmployeeId" id="e-oldEmployeeId"> 
	         	<input type="hidden" name="lpnId" id="e-carLpn">
			    <div class="fitem">
			   		<tr>
			   		<td>当前执行人员:</td><input id="currentName"  name="currentName" disabled="disabled" class="easyui-textbox" style="width:150px"/>
			   			<td>新执行人员:</td>
			   			<td><input class="easyui-combotree" name="employeeId" id="employee" data-options=" url:'get_employee_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    onlyLeafCheck:true,
		                    children:[{   
        						valueField:'id',
		                    	textField:'text'
    						}],
		                    panelHeight:'400'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>任务类型:</td>
			   			<td><input class="easyui-combobox" name="editTaskType" id="e-taskType" disabled="disabled" data-options=" url:'get_task_type_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
			   			<td>车牌号:</td>
			   			<td><input class="easyui-textbox" name="lpn" id="carLpn" disabled="disabled" data-options=" url:'get_lpn_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>所在网点:</td>
			   			<td><input class="easyui-combobox" name="beginParkId" id="beginParkId" data-options=" url:'get_park_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
			   			<td>目的网点:</td>
			   			<td><input class="easyui-combobox" name="endParkId" id="endParkId" data-options=" url:'get_park_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
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
<%--		                    data:[{'id':1, 'text':'0.5小时'},{'id':2,'text':'1小时'},--%>
<%--		                          {'id':3,'text':'1.5小时'},{'id':4,'text':'2小时' },--%>
<%--		                          {'id':5,'text':'2.5小时'}--%>
<%--		                         ]">--%>
<%--                           <input class="easyui-datetimebox"  name="deadline" id="deadline" >--%>
                           <input  name="deadline" id="e-deadline" class="easyui-datetimebox"/>
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
	       </form>
   		</div>
   		<!-- 非调度任务类型编辑对话框 -->
		 <div id="updateView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="updateViewForm" id="updateViewForm">
	         	<input type="hidden" name="id" id="u-taskId">
	         	<input type="hidden" name="taskTypeNo" id="u-taskTypeNo">
	         	<input type="hidden" name="lpnId" id="u-carLpn">
	         	<input type="hidden" name="oldEmployeeId" id="u-oldEmployeeId"> 
			    <div class="fitem">
			   		<tr>
			   			<td>当前执行人员:</td><input id="u-currentName"  name="currentName" disabled="disabled" class="easyui-textbox" style="width:150px"/>
			   			<td>新执行人员:</td>
			   			<td><input class="easyui-combotree" name="employeeId" id="u-employee" data-options=" url:'get_employee_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    onlyLeafCheck:true,
		                    children:[{   
        						valueField:'id',
		                    	textField:'text'
    						}],
		                    panelHeight:'400'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>任务类型:</td>
			   			<td><input class="easyui-combobox" name="editTaskType" id="taskCategory" disabled="disabled" data-options=" url:'get_task_type_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
			   			<td>车牌号:</td>
			   			<td><input class="easyui-textbox" name="lpn" id="licensePlate" disabled="disabled">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>所在网点:</td>
			   			<td><input class="easyui-combobox" name="beginParkId" id="startingParkId" data-options=" url:'get_park_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
			   			<td>目的网点:</td>
			   			<td><input class="easyui-combobox" name="endParkId" id="overParkId" data-options=" url:'get_park_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>任务等级:</td>
			   			<td><input class="easyui-combobox" name="taskLevel" id="taskGrade" data-options=" url:'get_task_grade_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
			   			<td>任务期限:</td>
<%--			   			<td><input class="easyui-combobox" name="requestPayTime" id="requestedTime" data-options="--%>
<%--		                    valueField:'id',--%>
<%--		                    textField:'text',--%>
<%--		                    data:[{'id':1,'text':'0.5小时'},{'id':2,'text':'1小时'},--%>
<%--		                          {'id':3,'text':'1.5小时'},{'id':4,'text':'2小时'},--%>
<%--		                          {'id':5,'text':'2.5小时'}] --%>
<%--		                    ">--%>
<%--		                    <input class="easyui-datetimebox"  name="deadline" id="u-deadline" >--%>
		                    <input  name="deadline" id="u-deadline" class="easyui-datetimebox"/>
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="specialExplain" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
	       </form>
   		</div>

		<div id="photoView" class="easyui-dialog" closed="true" >
			<div id="contentView" style="overflow-y: scroll;height: 300px;width: auto">

			</div>
		</div>
	</body>
</html>