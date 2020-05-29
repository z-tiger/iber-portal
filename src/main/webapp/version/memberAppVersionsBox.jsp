<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>宝盒版本管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="version/common.js"></script>	
	<script type="text/javascript" src="version/memberAppVersionsBox.js"></script>
	
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

<body style="width:100%;height:100%;">
   
	<table id="dataGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<r:FunctionRole functionRoleId="add_app_version">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_app_version">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_app_version">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete">删除</a>
		</r:FunctionRole>
      		
	</div>
        
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:300px;height:500px;padding:10px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;">
					<input type="hidden" name="id" id="id" />
		    		<tr>
		    			<td>版本类型:</td>
		    			<td>
		    				安卓会员端
		    				<input type="hidden" name="appCategory" id="appCategory" value="member"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>app类型:</td>
		    			<td>
			    			<input name="type" id="_appType" class="easyui-combobox"
								data-options=" url:'sys_dic?dicCode=APP_TYPE',
		                   	    method:'get',
		                    	valueField:'code',
		                    	textField:'name',
		                    	panelHeight:'auto',
		                    	required:true,
		                    	missingMessage:'app类型必填'""/>
		    			</td>
		    		</tr>  
		    		<tr>
		    			<td>版本名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="appName" name="appName" style="width:100%;height:24px" data-options="required:true,missingMessage:'版本名称不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>版本号:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="versionNo" name="versionNo" style="width:100%;height:24px" data-options="required:true,missingMessage:'版本号不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>是否强制升级:</td>
		    			<td>
		    				<select class="easyui-combobox"  name="isForceUpdate" id="isForceUpdate"  style="width:100%;height:24px" data-options="required:true,missingMessage:'是否强制升级'">
			                   <option value="T">是</option>
			                   <option value="F">否</option>
			               </select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>版本记录数:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="currentRecord" name="currentRecord" style="width:100%;height:24px" data-options="required:true,missingMessage:'版本记录数不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>版本附件:</td>
		    			<td>
		    			<input type="file"  name="appFile" id="appFile" style="width:100%;height:24px" >
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>描述:</td>
		    			<td>
		    				<input class="easyui-textbox" id="appDesc" name="appDesc" data-options="multiline:true" style="height:60px;width:100%;"></input>
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
</body>
</html>