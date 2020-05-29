<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>当前订单列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="timeShare/cancelOrder.js"></script>	
	
	<style type="text/css" >
	#conversionForm {
		margin: 0;
		padding: 10px 30px;
	}
	
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
   
	<table id="memberGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<div>
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
	          姓名:<input type="text" id="memberName" class="easyui-textbox">
	          会员手机号:<input type="text" id="phoneNumber"  class="easyui-textbox" style="width: 100px">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		<r:FunctionRole functionRoleId="reset_member_reserve_order_cancel_time">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-slider_no_pointy_thing" plain="true" id="resetMember">重置会员约车订单取消次数</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="reset_member_reserve_charging_order_cancel_time">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-slider_no_pointy_thing" plain="true" id="resetMemberCharing">重置会员约充电订单取消次数</a>
		</r:FunctionRole>
		
		</div>
	</div>
</body>
</html>