<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员注册报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/memberRegister.js"></script>	
	
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
 <body style="width:100%;height:100%;">
 
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar">
 	      <form id="data">
       		时间:<input id="beginTime"  name="beginTime" class="easyui-datebox" data-options="required:true,missingMessage:'开始时间不能为空'"/>
          	 到:<input id="endTime"  name="endTime" class="easyui-datebox" data-options="required:true,missingMessage:'结束时间不能为空'"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-large-chart" plain="true" id="btnWeekQuery">本周报表</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-large-chart" plain="true" id="btnMonthQuery">本月报表</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"  id="ckMemberQuery">查看会员信息</a> 
            <r:FunctionRole functionRoleId="member_register_form_export_excel">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
            </r:FunctionRole>
            
       	  </form>
         <div>
         
         <div id="memberTypeView" class="easyui-dialog" closed="true" style="width:800px;height:600px;float:right;padding:0">
			<table id="memberGrid" toolbar="#toolbar"></table>
		</div>
	</body>
</html>