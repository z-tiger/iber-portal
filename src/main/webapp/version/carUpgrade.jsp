<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆类型</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="UTF-8"></script>
<script type="text/javascript" src="version/carUpgrade.js"></script>
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
   <table id="versionGrid" toolbar="#toolbar"></table>
   <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
			所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
	    车牌品牌:<input type="text" name="brandName" class="easyui-textbox" id="s-brand-name">
	   	tboxVersion：<input class="easyui-combobox" name="tboxVersion" id="tboxVersion" style="width: 80px;"
					 data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '全部',
							value: ''
						},{
							label: '二代',
							value: 2
						},{
							label: '三代',
							value: 3
						}],
	                    panelHeight:'auto'" >
	   	车辆运营状态：<input class="easyui-combobox" name="status" id="e-status" style="width: 80px;"
					 data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '全部',
							value: ''
						},{
							label: '空闲',
							value: 'empty'
						},{
							label: '维修',
							value: 'repair'
						},{
							label:'维护',
							value:'maintain'
						},{
							label:'运营',
							value:'run'
						},{
							label:'补电',
							value:'charging'
						}],
	                    panelHeight:'auto'" >
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		<r:FunctionRole functionRoleId="increase_box">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-wrench-orange" plain="true" id="boxUpgrade" onclick=";">宝盒升级</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="mirror_increase">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-wrench" plain="true" id="rearviewUpgrade" onclick=";">后视镜升级</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="all_box_increase">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-hammer" plain="true" id="allBoxUpgrade" onclick=";">全部宝盒升级</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="all_mirror_increase">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-hammer-screwdriver" plain="true" id="allRearviewUpgrade" onclick=";">全部后视镜升级</a>
		</r:FunctionRole>
	
   </div>
   <div id="showVersionView" class="easyui-dialog" closed="true" style="width:800px;height:500px;padding-bottom: 25px;">
		<table id="showVersionGrid"></table>
   </div>
</body>
</html>