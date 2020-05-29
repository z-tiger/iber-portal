<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<%
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>分享内容设置</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="msg/shareContent.js"></script>
</head>

<body>

	<table id="sharContentGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<r:FunctionRole functionRoleId="add_share_content">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_share_content">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_share_content">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove" onclick=";">删除</a>
		</r:FunctionRole>
		
	</div>
	<!-- 添加用户 -->
	<div id="sharcontentView" class="easyui-dialog" closed="true"
		style="width:400px;height:280px;padding:10px 20px">
<!-- 		<div class="ftitle">分享内容设置</div> -->
		<form id="sharcontentForm" method="post" name='sharcontentForm'>
		   <input type="hidden" name="id" id="id">
			<div class="fitem">
			 <label>分享入口:</label>
			<input class="easyui-combobox" name="shareModu"
					data-options=" url:'sys_dic?dicCode=SHARE_MODU',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="shareModu" missingMessage="请选择分享入口" style="width: 70%">
			</div>
			<div class="fitem">
				<label>标题:</label> <input name="shareTitle" class="easyui-textbox"
					required="true" missingMessage="请填写分享标题" id="shareTitle"  style="width: 70%"/>
			</div>
			 <div class="fitem">
                <label>URL:</label>
                <input id="shareUrl" name='shareUrl'  class="easyui-textbox"   required="true"  missingMessage="请填写分享URL" style="width: 70%">
		     </div>
			<div class="fitem">
				<label>内容:</label> 
				<input  name="shareContent" id="shareContent"  class="easyui-textbox"   data-options="multiline:true" style="width: 70%; height: 150px;" 	required="true"  missingMessage="请填写分享内容"/>
			</div>
			
		</form>
	</div>
	
	<style type="text/css">
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