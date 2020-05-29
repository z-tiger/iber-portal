<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>网点类型管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="network/parkCategory.js"></script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	     <r:FunctionRole functionRoleId="add_parkCategory">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
         </r:FunctionRole>
 	     <r:FunctionRole functionRoleId="update_parkCategory">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
         </r:FunctionRole>
	        <!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a> -->
         </div>
         <!-- add -->
         <div id="view" class="easyui-dialog" closed="true">
			<form id="viewForm" name="viewForm" method="post">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
						<tr>
			    			<td>网点类型名称:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'不能为空'">
			    			</td>
			    		</tr>
		    		
						<tr>
			    			<td>网点类型描述:</td>
			    			<td>
			    				<input class="easyui-textbox" id="remark" name="remark" data-options="multiline:true" style="width:100%;height:200px"></input>
			    			</td>
			    		</tr>
		    		
				</table>
			</form>
		 </div>
	</body>
</html>