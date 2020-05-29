<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld"%>
<html>
<head>
<meta charset="UTF-8">
<title>会员管理</title>
<link rel="stylesheet" type="text/css"
	href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js"
	charset="gb2312"></script>
<script type="text/javascript" src="member/memberBlacklistLog.js"></script>
<style type="text/css">
.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.fitem input {
	width: 160px;
}

.fitem a {
	margin-right: 5px;
}
</style>
</head>
<body>
	<table id="grid" toolbar="#toolbar"></table>
	<!-- 工具栏  start-->
	<div id="toolbar" style="height:auto">
	<form action="" id="blackListLogForm">
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
		手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
	<r:FunctionRole functionRoleId="select_blackListLog">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
	</r:FunctionRole>
	<r:FunctionRole functionRoleId="export_blackListLog">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
	</r:FunctionRole>
	</form>
	</div>
</body>
</html>