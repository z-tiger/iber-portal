<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>抽奖记录明细</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="activity/lotteryDetailedPage.js"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>

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
	<div id="toolbar" style="height:auto">
			所属城市:<input class="easyui-combobox" name="cityCode" id="s-cityCode"
						data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
			活动类型:<input name="activityType" id="s-activityType" class="easyui-combobox"
				data-options=" url:'sys_dic?dicCode=ACTIVITY_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'code',
	                    panelHeight:'auto'"/>
	                    中奖项目:<input type="text" name="prizeName" class="easyui-textbox" id="s-prizeName">
			会员姓名:<input type="text" name="memberName" class="easyui-textbox" id="s-memberName">
			手机号码:<input type="text" name="memberPhone" class="easyui-textbox" id="s-memberPhone">
		 &nbsp;时间:
	       		   <input id="s-bt" name="bt" class="easyui-datetimebox s" size="18"/>
		  		  到:
		  		   <input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清除</a>
	  </div>
</body>
</html>
