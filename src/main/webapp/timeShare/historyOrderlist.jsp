<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>时租订单列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="timeShare/historyOrder.js"></script>	
	
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
   
	<table id="orderGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
	<form action="" id="historyOrderForm">
		<div>
			所属城市:
		<input class="easyui-combobox" name="cityCode" id="cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
	                    width="15px">
	        姓名:<input type="text" id="memberName" name="memberName" class="easyui-textbox" style="width: 100px">
	         会员号码:<input type="text" id="phoneNumber" name="phoneNumber" class="easyui-textbox" style="width: 100px">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width: 100px">
		订单号:<input type="text" name="orderId" class="easyui-textbox" id="orderId">
		租车日期:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px"/>
                    到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px"/>	
                    订单状态:<select class="easyui-combobox" id="status" name="status"  style="width:80px;" data-options="editable:false,panelHeight:'auto'"> 
		             <option value="">全部</option>
		             <option value="finish">已完成</option>
		             <option value="cancel">已取消</option>
		             <option value="noPay">未支付</option>
		          </select>
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
		 <r:FunctionRole functionRoleId="select_History_order">
		 <a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		 </r:FunctionRole>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		<r:FunctionRole functionRoleId="export_history_order">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
		</div>
	</div>
</body>
</html>