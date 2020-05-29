<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", -10);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
    <title>系统运行参数管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
    <script type="text/javascript" src="system/sysRunParam.js"></script>
</head>

<body>
<div class="fitem">
    <label>选择短信模式:</label> <input class="easyui-combobox" name="type" id="sys_run_param"
                                editable="false" data-options="
                    valueField:'value',
                    textField:'label',
                    data: [{
                        label: 'huyi',
                        value: '3'
                    },{
                        label: '至臻',
                        value: '2'
                    },{
                        label: '阿里云',
                        value: '1'
                    },{
                        label: '诚立业',
                        value: '4'
                    }],
                    panelHeight:'auto'" required="true"  missingMessage="请选择订单类型">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="btnQuery">保存</a>

    <label>测试号码:</label> <input type="text" id="phone"  class="easyui-textbox" style="width: 100px">
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true" id="btnSend">发送测试短信</a>
</div>
</body>
</html>