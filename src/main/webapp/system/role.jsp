<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<title>角色列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="system/role.js"></script>	
</head>

<body>
   
	<table id="roleGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
			<r:FunctionRole functionRoleId="add_role">
				<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" id="btnSave">添加角色</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="update_role">
				<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" id="btnEdit">修改角色</a> 
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="assign_right">
				<a href="javascript:void(0);" class="easyui-linkbutton"
				   iconCls="icon-luck-cloud-contact_blue" plain="true" id="btnJurisdiction">分派权限</a>
			</r:FunctionRole>

	</div>
<!-- 添加角色 -->
	<div id="roleView" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
			<form id="roleForm" method="post" action=""  onsubmit="return checkRole(this)">
					<div class="fitem">
					<input type="hidden" id="roleId" name="id">
		                <label>角色名称:</label>
		                <input name="name" class="easyui-textbox" required="true" missingMessage="请填写名称" id="roleName" style="width: 75%;"/>
		            </div>
		            <div class="fitem">
		                <label>备注:</label>
		                    <input name="description" style="width: 75%;" class="easyui-textbox" required="true" missingMessage="请填写角色备注" id="roleDescription"/>
		            </div>
			</form>
		</div>	
		<!-- 授权窗口 -->
			<div id="sysModuView" class="easyui-dialog" closed="true" style="width:500px;height:450px;padding:10px 20px">
				<ul id="tree" ></ul>
				<form id="sysModuForm" method="post" action=""  onsubmit="return checkRole(this)">
				<input type="hidden" name="moduIds" id="moduIds"/>
				<input type="hidden" name="roleIds" id="roleIds"/>
				<input type="hidden" name="is_shows" id="is_show"/>
				<input type="hidden" name="pids" id="pid"/>
				<input type="hidden" name="names" id="text">
			</form>
		    </div>	
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
</body>
</html>