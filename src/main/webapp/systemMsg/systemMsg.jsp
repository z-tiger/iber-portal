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
	<title>消息推送</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="systemMsg/systemMsg.js"></script>
	<style type="text/css">

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

	<table id="grid" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:auto">
		<form id="data">
			类型:
			<input class="easyui-combobox" name="type" id="type"
				   data-options=" url:'sys_dic?dicCode=MSG_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'
	                    ">
			标题:<input type="text" name="title" class="easyui-textbox" id="title">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<r:FunctionRole functionRoleId="add_msg">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd" onclick=";">添加</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="attachment_download">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_medium_down" plain="true" id="btnDownload" onclick=";">附件表样下载</a>
			</r:FunctionRole>
		</form>
	</div>
	<!--添加系统消息-->
	<div id="addSystemMsgView" class="easyui-dialog" closed="true"
		style="width:400px;height:280px;padding:10px 20px">
		<form id="addSystemMsgForm" method="post" name='systemMsgForm' enctype="multipart/form-data">
			<div class="fitem">
				<label>类型:</label>
				<input class="easyui-combobox" name="msgType"
						data-options="url:'sys_dic?dicCode=MSG_TYPE',
							valueField:'code',
							textField:'name',
							panelHeight:'auto'"
						required="true" id="msgType" missingMessage="请选择类型" style="width: 70%">
			</div>
			<div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 70%;"
											data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'" required="true"  missingMessage="请选择所属城市">
			</div>
			<div class="fitem">
				<label>指定用户:</label>
				<input  name="targetUser" id="targetUser" class="easyui-combobox" style="width: 70%" required="true"  missingMessage="请填写内容"  data-options="
							valueField:'value',
			                textField:'label',
			                data: [{
								label:'是',
								value:'1'
							},{
								label:'否',
								value:'0'
							}],
			                panelHeight:'auto'"/>
			</div>
			<div class="fitem">
				<label>标题:</label> <input name="msgTitle" class="easyui-textbox"
					required="true" missingMessage="请填写标题" id="msgTitle"  style="width: 70%"/>
			</div>
			<div class="fitem">
				<label>内容:</label> 
				<input  name="msgContent" id="msgContent"  class="easyui-textbox"   data-options="multiline:true" style="width: 70%; height: 150px;" 	required="true"  missingMessage="请填写内容"/>
			</div>

			<div class="fitem" id="addFile">
				<label>添加附件:</label>
				<input type="file"  name="importUrl" id="importUrl" required="required">
			</div>
		</form>
	</div>
</body>
</html>