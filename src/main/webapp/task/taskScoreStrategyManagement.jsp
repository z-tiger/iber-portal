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
    <script type="text/javascript" src="task/taskScoreStrategyManagement.js"></script>
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


        所属地区:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;"
                    data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
        类型:<input class="easyui-combobox" name="type" id="type" style="width: 80px;"
                  data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '调度任务',
							value: '1'
						},{
							label: '维护任务',
							value: '2'
						},{
							label:'充电任务',
							value:'3'
						}<%--,{
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
        维修大类:<input class="easyui-combobox" name="maintain" id="maintain" style="width: 80px;"
                    data-options="url:'get_maintain_type',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="clearQuery">清除查询条件</a>
        <r:FunctionRole functionRoleId="task_score_strategy_add">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">新增</a>
        </r:FunctionRole>
        <r:FunctionRole functionRoleId="task_score_strategy_edit">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">编辑</a>
        </r:FunctionRole>

    </form>
</div>



<div id="addView" class="easyui-dialog" closed="true" style="padding:10px 50px">
    <form name="addViewForm" id="addViewForm">
        <input type="hidden" name="id" >
        <div class="fitem">
            <tr>
                <td>城市:</td>
                <td><input class="easyui-combobox" name="cityCode" id="cityCode2" style="width: 80px;" required="true"
                                data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>类型:</td>
                <td><input class="easyui-combobox" name="type" id="e-taskType"  style="width: 80px;" required="true" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '调度',
							value: '1'
						},{
							label: '维护',
							value: '2'
						},{
							label:'充电',
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
            <tr id="maintain_type">
                <td>维护类目:</td>
                <td><input class="easyui-combobox" name="maintainType" id="maintain2" style="width: 100px;" required="true"
                           data-options="url:'get_maintain_type',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
                </td>
            </tr>
            <br><br>
            <tr id="mileage">
                <td>里程(KM):</td>
                <td><input class="easyui-numberbox" name="minMileage" required="true" precision="2" style="width: 80px;" id="min_mileage">-<input class="easyui-numberbox" style="width: 80px;" required="true" precision="2" name="maxMileage" id="max_mileage"></td>
            </tr>
            <tr>
                <td>分值:</td>
                <td><input class="easyui-numberbox" style="width: 80px;" name="score" required="true"  precision="1" missingMessage="请输入分值" id="score"></td>
            </tr>
        </div>
    </form>
</div>
</body>
</html>