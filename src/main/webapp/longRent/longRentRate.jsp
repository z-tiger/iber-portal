<%@ page pageEncoding="utf-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>计费策略管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
    <script type="text/javascript" src="longRent/longRentRate.js"></script>
</head>
<body>
<table id="dataGrid" toolbar="#toolbar"></table>
<!--列表工具栏 -->
<div id="toolbar" style="height:auto">
    城市:<input class="easyui-combobox" name="city_code" id="city_code" style="width: 80px;"
              data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'"/>
    车辆型号:<input class="easyui-combobox" name="modelId" id="carTypeId"
                data-options=" url:'carTypeCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
                missingMessage="请选择车辆型号">
    时间:<input id="queryDateFrom" name="queryDateFrom" class="easyui-datebox"/>
    到:<input id="queryDateTo" name="queryDateTo" class="easyui-datebox"/>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
    <r:FunctionRole functionRoleId="add_calculate_fee_plot">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
    </r:FunctionRole>
    <r:FunctionRole functionRoleId="update_calculate_fee_plot">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
    </r:FunctionRole>
</div>
<!-- add -->
<div id="addView" class="easyui-dialog" closed="true" style="width: 600px;height: 300px;">
    <form id="addForm" method="post" action="" onsubmit="checkCustomer(this)">
        <table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
            <tr>
                <td>所属区域:</td>
                <td>
                    <input class="easyui-combobox" name="cityCode" id="cityCodeView" style="width:100%;height:22px"
                           data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
                </td>

                <td>车辆型号:</td>
                <td>
                    <input class="easyui-combobox" name="carTypeId" id="carTypeIdView" style="width: 100%;"
                           data-options=" url:'carTypeCombobox',
										method:'get',
										valueField:'id',
										textField:'text',
										panelHeight:'auto'"
                           required="true" missingMessage="请选择车辆型号">
                </td>
            </tr>
            <tr>
                <td>时间:</td>
                <td><input id="dateFrom" name="from" class="easyui-datebox" required/></td>
                <td>到:</td>
                <td><input id="dateTo" name="to" class="easyui-datebox" required/></td>
            </tr>
            <tr>
                <td>日租费(元):</td>
                <td>
                    <input class="easyui-textbox" name="moneyFen" id="money" style="width:100%;height:22px"
                           data-options="required:true,missingMessage:'费用不能为空'">
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- update -->
<div id="updateView" class="easyui-dialog" closed="true" style="width: 300px;height: 200px;">
    <form id="updateForm" method="post" action="" onsubmit="checkCustomer(this)">
        <input type="hidden" name="id" id="updateId"/>
        <div style="padding: 10px;">
            日期: <span id="date"/>
        </div>
        <div style="padding: 10px;">
            日租费(元):<input class="easyui-textbox" name="moneyFen" id="updateMoney" style="width:50%;"
                          data-options="required:true,missingMessage:'费用不能为空'"/>
        </div>
    </form>
</div>
</body>
</html>