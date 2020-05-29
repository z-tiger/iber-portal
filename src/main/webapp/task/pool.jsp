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
	<script type="text/javascript" src="task/pool.js"></script>
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
			车牌号码:<input id="lpn2"  name="lpn" class="easyui-textbox" style="width: 100px;"/>
			执行人:<input id="employeeName"  name="employeeName" class="easyui-textbox" style="width: 100px;"/>
			执行人电话:<input id="employeePhone"  name="employeePhone" class="easyui-textbox" style="width: 150px;"/>
 	        所属地区:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			<%--/*1.调度   2.维护  3.充电    4.维修    5.救援  6.其他*/--%>
			派单类型:<input class="easyui-combobox" name="taskType" id="_taskType" style="width: 100px;"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '全部',
							value: ''
						},
	                    {
							label: '调度任务',
							value: '1'
						}<%--,{
							label: '维护任务',
							value: '2'
						}--%>,{
							label:'充电任务',
							value:'3'
						},<%--{
							label:'维修任务',
							value:'4'
						},{
							label:'救援任务',
							value:'5'
						},{
							label:'其他任务',
							value:'6'
						}--%>],
	                    panelHeight:'auto'" >
	                   派单状态:<input class="easyui-combobox" name="status" id="_taskStatus" style="width: 100px;"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: '0'
						},{
							label: '未接单',
							value: '1'
						},{
							label: '已接单',
							value: '2'
						},{
							label:'已指派',
							value:'3'
						},{
							label:'已取消',
							value:'4'
						}],
	                    panelHeight:'auto'" >
						任务状态:<input class="easyui-combobox" name="taskStatus" id="taskStatus" style="width: 100px;"
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
										label: '完成',
										value: '3'
									},{
										label: '取消',
										value: '4'
									}],
									panelHeight:'auto'" >

			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="add_task_allocation">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">新增</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="task_allocation">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnAllocation">指派</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="task_cancel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnCancel">取消</a>
			</r:FunctionRole>
            </form>
	    </div>
		<div id="editView" class="easyui-dialog" closed="true" style="padding:10px 50px">
			<form name="editViewForm" id="editViewForm">
				<input type="hidden" name="requestType" id="requestType">
				<input type="hidden" name="taskType" id="taskType">
				<input type="hidden" name="lpn" id="lpn">
				<input type="hidden" name="taskId" id="taskId">
				<input type="hidden" name="beginParkId" id="beginParkId">
				<div class="fitem">
					<tr>
						<td>执行人员:</td>
						<td><input class="easyui-combotree" name="id" id="employee" required=true missingMessage="请选择执行人员" data-options=" url:'get_employee_list',
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
						<td>任务等级:</td>
						<td><input class="easyui-combobox" name="taskLevel" id="taskLevel" required=true missingMessage="请选择任务等级" data-options=" url:'get_task_grade_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
						</td>
					</tr>
					<br><br>
					<tr>
						<td>任务期限:</td>
						<td>
							<input  name="deadline" id="e-deadline" required=true missingMessage="请选择任务期限" class="easyui-datetimebox"/>
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


		<div id="addView" class="easyui-dialog" closed="true" style="padding:10px 50px">
			<form name="addViewForm" id="addViewForm">
				<div class="fitem">
					<tr>
						<td>任务类型:</td>
						<td><input class="easyui-combobox" name="taskType" id="e-taskType"  data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '调度任务',
							value: '1'
						}<%--,{
							label: '维护任务',
							value: '2'
						}--%>,{
							label:'充电任务',
							value:'3'
						},<%--{
							label:'维修任务',
							value:'4'
						},{
							label:'救援任务',
							value:'5'
						},{
							label:'其他任务',
							value:'6'
						}--%>],
	                    panelHeight:'auto'">
						</td>
					</tr>
					<br><br>
					<tr>
						<td>车&nbsp;牌&nbsp;号:</td>
						<td><input class="easyui-combobox" name="lpn" id="carLpn">
						</td>
					</tr>
					<br><br>
					<tr>
						<td>目的网点:</td>
						<td><input class="easyui-combobox" name="endParkId" id="endParkId" data-options="panelHeight:'250'"></td>
					</tr>
				</div>
			</form>
		</div>
	</body>
</html>