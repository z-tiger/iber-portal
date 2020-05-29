<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>版本类型管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="version/common.js"></script>	
	<script type="text/javascript" src="version/versionsCategory.js"></script>
	
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
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="add_version_type">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="update_version_type">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="delete_version_type">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete">删除</a>	
			</r:FunctionRole>  		
	</div>
        
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:300px;height:400px;padding:50px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;">
		    		<tr>
		    			<td>种类名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'种类名称不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>种类代码:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="code" id="code" style="width:100%;height:24px" data-options="required:true,missingMessage:'种类代码不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>描述:</td>
		    			<td>
		    				<input class="easyui-textbox" id="remark" name="remark" data-options="multiline:true" style="width:100%;height:60px"></input>
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
</body>
</html>