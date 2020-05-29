<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>会员账户管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="member/freezeLog.js"></script>

</head>
<body>

    <table id="grid" toolbar="#toolbar"></table>
     
      <!-- 工具栏  start-->
   	<div id="toolbar" style="height:auto">
		<form id="data">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="scityCode"
						data-options=" url:'sys_optional_cityCombobox',
							method:'get',
							valueField:'id',
							textField:'text',
							panelHeight:'auto'">
			姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
			手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
			原因:<input type="text" name="reason" class="easyui-textbox" id="s-reason">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>

			<r:FunctionRole functionRoleId="export_freeze_log">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjdj" plain="true" id="btnExport" onclick=";">会员冻结日志导出excel</a>
			</r:FunctionRole>
		</form>
   	</div>
</body>
</html>