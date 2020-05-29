<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员分时消费明细</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/memberConsumption.js"></script>	
	<script type="text/javascript">
	$(function(){
		//开始时间默认:00:00:00
	    $("#beginTime").datetimebox({  
			required : true,  
			 	onShowPanel:function(){  
			$(this).datetimebox("spinner").timespinner("setValue","00:00:00");  
			}  
		});
		//结束时间默认为23:59:59  
	   $("#endTime").datetimebox({  
			required : true,  
			 	onShowPanel:function(){  
			$(this).datetimebox("spinner").timespinner("setValue","23:59:59");  
			} 
		});  
	});
	</script>
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
	#data{
	  margin:0 auto;
	}
	</style>
	
</head>

<body>
   
	<table id="dataGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<form id="data">
			<label class="label">会员姓名:&nbsp;</label>
            <input class="easyui-textbox" name="name" id="name" style="width: 120px;">
            <label class="label">手机号码:&nbsp;</label>
            <input class="easyui-textbox" name="phoneNumber" id="phoneNumber" style="width: 120px;">

			<label class="label">所属城市:&nbsp;</label>
			<input class="easyui-combobox"  name="cityName" id="cityName" data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'text',
	                    textField:'text',
	                    panelHeight:'auto'">
            <label class="label">车辆品牌:&nbsp;</label>
            <input class="easyui-combobox"  name="brandName" id="brandName" data-options=" url:'car/selectAllCarBrand',
                        method:'get',
                        valueField:'brance',
                        textField:'brance',
                        panelHeight:'auto'">

			<label class="label" style="text-align: center;">取车时间:&nbsp;</label>
			<input id="beginTime" name="beginTime" class="easyui-datetimebox" data-options="required:true,missingMessage:'开始时间不能为空'"/>
			<label class="label" style="text-align: center;">还车时间:&nbsp;</label>
			<input id="endTime" name="endTime" class="easyui-datetimebox" data-options="required:true,missingMessage:'结束时间不能为空'"/>	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="member_time_share_consume_item_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
			</r:FunctionRole>
		     
		</form>
	</div>
			
</body>
</html>