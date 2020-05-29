<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>评价标签配置</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="evaluate/common.js"></script>	
	<script type="text/javascript" src="evaluate/orderEvaluateLabel.js"></script>
	
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
   
	<table id="dataGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	 <div id="toolbar" style="height:auto">
		 	<c:choose>
				<c:otherwise>
				 <label class="label">评价模块：&nbsp;</label>
				  <input class="easyui-combobox" name="type" id="type"  data-options="valueField:'code',textField:'name',url:'sys_dic?dicCode=EVALUATE_TYPE',panelHeight:'80'"> 
				</c:otherwise>
			</c:choose>	
			<c:choose>
				<c:otherwise>
				  <label class="label" style="text-align: right;">星级:&nbsp;</label>
				  <input class="easyui-combobox" name="star" id="star"  data-options="valueField:'code',textField:'name',url:'sys_dic?dicCode=EVALUATE_SCORE',panelHeight:'100',panelWidth:'80'" style="width: 80px;" > 
				</c:otherwise>
			</c:choose>	
			<c:choose>
			  <c:otherwise>
					<label class="label">标签:&nbsp;</label>
					<input class="easyui-textbox"" name="label" id="label" style="width: 120px;">
				</c:otherwise>
			</c:choose>	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			
			<r:FunctionRole functionRoleId="add_assess_tag">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="update_assess_tag">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="delete_assess_tag">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete">删除</a>	
			</r:FunctionRole>
      	
	</div>
        
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:600px;height:400px;padding:50px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;width:350px">
					<tr>
		    			<td>评价模块:</td>
		    			<td>
		    			<input class="easyui-combobox" name="modType" id="modType" style="width:100%;height:36px"
							data-options=" url:'sys_dic?dicCode=EVALUATE_TYPE',
			                    method:'get',
			                    valueField:'code',
			                    textField:'name',
			                    panelHeight:'auto',
			                    editable:false,required:true,missingMessage:'评价模块不能为空'" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>星级:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="modStar" id="modStar" style="width:100%;height:36px" 
			    				data-options=" url:'sys_dic?dicCode=EVALUATE_SCORE',
				                    method:'get',
				                    valueField:'code',
				                    textField:'name',
				                    panelHeight:'auto',
				                    editable:false,required:true,missingMessage:'星级不能为空'" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>标签:</td>
		    			<td>
			    			<div style="color:red;">多个标签用#号隔开</div>
			    			<input class="easyui-textbox"  name="modLabel" id="modLabel" style="width:100%;height:66px" data-options="multiline:true,required:true,missingMessage:'标签不能为空'">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
</body>
</html>