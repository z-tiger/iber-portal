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
	<script type="text/javascript" src="timeShare/order.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>	
	
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
	#sp1{margin-right:10px;}
	</style>
</head>

<body>
   
	<table id="orderGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
	<form action="" id="timeShareOrderForm">
		<div>
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
	          姓名:<input type="text" id="memberName"  class="easyui-textbox" style="width: 100px">
	          手机号码:<input type="text" id="phoneNumber"  class="easyui-textbox" style="width: 100px">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width: 100px">
		预约时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox s" style="width:160px"/>
                    到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datetimebox e" style="width:160px"/>	
                是否是超长订单:<input class="easyui-combobox" style="width:100px;" name="isLongOrder" id="s-isLongOrder"
					data-options=" valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '是',
							value: '1'
						},{
							label: '否',
							value: '0'
						}],
	                    panelHeight:'auto'">
            订单类型:<input class="easyui-combobox" style="width:100px;" name="isEnterpriseUseCar" id="isEnterpriseUseCar"
                          data-options=" valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '',
							value: ''
						},{
							label: '企业约车',
							value: 'true'
						},{
							label: '个人约车',
							value: 'false'
						}],
	                    panelHeight:'auto'">
            是否锁车:<input class="easyui-combobox" style="width:100px;" name="isLockCar" id="s-isLockCar"
					data-options=" valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '是',
							value: '1'
						},{
							label: '否',
							value: '0'
						}],
	                    panelHeight:'auto'">
		<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" id="btnCal">计算所选订单的当前金额</a> -->
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		<r:FunctionRole functionRoleId="save_order">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnSave">手动保留订单</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="export_time_share_order">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
		</div>
	</div>
</body>
</html>