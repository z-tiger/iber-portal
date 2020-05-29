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
	<title>系统通知设置</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="msg/systemMsg.js"></script>
</head>

<body>

	<table id="systemMsgGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<r:FunctionRole functionRoleId="add_system_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_system_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_system_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove" onclick=";">删除</a>
		</r:FunctionRole>
		
	</div>
	<!-- 添加用户 -->
	<div id="systemMsgView" class="easyui-dialog" closed="true"
		style="width:400px;height:280px;padding:10px 20px">
<!-- 		<div class="ftitle">分享内容设置</div> -->
		<form id="systemMsgForm" method="post" name='systemMsgForm'>
		   <input type="hidden" name="id" id="id">
			<div class="fitem">
			 <label>类型:</label>
			<input class="easyui-combobox" name="msgType"
					data-options=" valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
							label: '退款',
							value: '退款'
						},{
							label: '审核',
							value: '审核'
						}]"
					required="true" id="msgType" missingMessage="请选择类型" style="width: 70%">
			</div>
			<div class="fitem">
				<label>标题:</label> <input name="msgTitle" class="easyui-textbox"
					required="true" missingMessage="请填写标题" id="msgTitle"  style="width: 70%"/>
			</div>
			<div class="fitem">
				<label>内容:</label> 
				<input  name="msgContent" id="msgContent"  class="easyui-textbox"   data-options="multiline:true" style="width: 70%; height: 150px;" 	required="true"  missingMessage="请填写内容"/>
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