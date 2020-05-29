<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员信息报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/memberInfo.js"></script>	
	
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
			<label class="label">会员手机号:&nbsp;</label>
			<input  name="memberPhone" id="memberPhone" style="width:100px;"  class="easyui-textbox">
			<label class="label">所属城市:&nbsp;</label>
			<input class="easyui-combobox"  name="code" id="code" style="width:100px;" data-options=" url:'sys_cityCombobox',
                   method:'get',
                   valueField:'id',
                   textField:'text',
                   panelHeight:'auto'">
			 <label class="label">注册方式：&nbsp;</label>
			  <input class="easyui-combobox" name="registerCategory" id="registerCategory" style="width:140px;" data-options="valueField:'code',textField:'name',url:'sys_dic?dicCode=REGISTERED_TYPE',panelHeight:'200'"> 
			
			<label class="label" style="text-align: center;">注册时间:&nbsp;</label>
			<input id="beginTime" name="beginTime" class="easyui-datebox" style="width:100px;"  data-options="required:true,missingMessage:'开始时间不能为空'"/>
			<label class="label" style="text-align: center;">到：</label>
			<input id="endTime" name="endTime" class="easyui-datebox" style="width:100px;" data-options="required:true,missingMessage:'结束时间不能为空'"/>
			
			<label class="label">会员等级：&nbsp;</label>
			<input class="easyui-combobox" " name="memberLevel" id="memberLevel" style="width:100px;" data-options=" url:'sys_memberLevelCombobox',
                   method:'get',
                   valueField:'id',
                   textField:'text',
                   panelHeight:'auto'">
            <label class="label" style="text-align: center;">审核时间:&nbsp;</label> 
            <input id="examine_start_time" name="examine_start_time" style="width:100px;"  class="easyui-datebox"/>
            <label class="label" style="text-align: center;">到：</label>
            <input id="examine_end_time" name="examine_end_time"  style="width:100px;"  class="easyui-datebox"/>
            <label class="label" style="text-align: center;">审核人:&nbsp;</label> 
            <input class="easyui-combobox" name="examine_id" id="examine_id" style="width:100px;"  data-options=" url:'get_sysUser_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear">清空</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnWeekQuery">本周报表</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnMonthQuery">本月报表</a>
			
			<r:FunctionRole functionRoleId="member_msg_form_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
			</r:FunctionRole>
		     
		</form>
	</div>
</body>
</html>