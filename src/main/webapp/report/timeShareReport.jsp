<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>时租收入报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js"></script>
	<script type="text/javascript" src="report/common.js"></script>
    <script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
	<script type="text/javascript" src="report/timeShareReport.js"></script>

	
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
	#queryTimeLease{
	  margin:0 auto;
	}
	</style>
</head>

<body>
   
	<table id="timeLeaseGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<form id="queryTimeLease">
		  所属城市:
		<input class="easyui-combobox" name="cityCode" id="cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn">
		会员姓名:<input type="text" name="name" class="easyui-textbox" id="name">
		还车时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox s"/>
                    到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datetimebox e"/>
        支付时间:<input id="payBeginTime"  name="payBeginTime" class="easyui-datetimebox s"/>
        到:<input id="payEndTime"  name="payEndTime" class="easyui-datetimebox e"/>
        是否开票:
			<input class="easyui-combobox" name="invoiceStatus" id="invoiceStatus"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '已开票',
							value: '3'
						},{
							label: '未开票',
							value: '1'
						}]">
            支付状态:
            <input class="easyui-combobox" name="payStatus" id="payStatus"
                   data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '已支付',
							value: 'finish'
						},{
							label: '未支付',
							value: 'noPay'
						}]">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearBtn">清空</a>
			<r:FunctionRole functionRoleId="time_share_rent_income_form_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
			</r:FunctionRole>
		    
		</form>
	</div>
	
	
</body>
</html>