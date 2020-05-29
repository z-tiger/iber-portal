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
	<meta charset="UTF-8">
	<title>图文消息管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="msg/pmsg.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
</head>

<body>

	<table id="pmsgGrid" toolbar="#toolbar"></table>

     <!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
				标题:<input id="s_title"   class="easyui-textbox"/>
				发布日期:<input class="easyui-datetimebox s" id="s-kssj" />至<input class="easyui-datetimebox e" id="s-jssj"/>
				状态:<input class="easyui-combobox" id="s-status" data-options="
				   valueField: 'id',
                   textField: 'text',
                   panelHeight :'auto',
                   data: [{
						id: '1',
						text: '待审核'
					},{
						id: '2',
						text: '通过'
					},{
						id: '3',
						text: '不通过'
					}]
				" />
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	           	<r:FunctionRole functionRoleId="add_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd" onclick=";">添加</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="update_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="delete_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove" onclick=";">删除</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="audit_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_edit"  plain="true" id="btnExamine" onclick=";">审核</a>
	            </r:FunctionRole>
		      
        <div>
     

	<!-- 添加用户 -->
	<div id="addView" class="easyui-dialog" closed="true"
		style="width:500px;height:80px;padding:10px 20px">
		<form id="addForm" method="post" name='addForm' enctype="multipart/form-data">
		   <input type="hidden" name="id" id="id">
			<div class="fitem">
				<label>标题:</label> <input name="msgTitle" class="easyui-textbox"
					required="true" missingMessage="请填写标题" id="msgTitle"  style="width: 70%"/>
			</div>
			<div class="fitem">
				<label>消息小图片:</label> <input name="msgPicture" class="easyui-filebox"
					required="true" missingMessage="请上传图片" id="msgPicture"  style="width: 70%" data-options="buttonText:'选择文件'"/>
			</div>

			<div >
			<input type="radio" name="msgText" value="msgURL" id="msgUrlRadioId" checked="checked">
			<span>消息URL地址: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<input name="msgUrl" id="msgUrl" class="easyui-textbox" style='width: 60%;' />
			<br/>
			</div>
<%--			<div>--%>
<%--			<input type="radio" name="msgText" value="mainBody" id="msgContentRadioId">--%>
<%--			<span>正文: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>--%>
<%--			<input name="msgContent" id="msgContent"  class="easyui-textbox" data-options="multiline:true" style="width: 70%; height: 270px;" />--%>
<%--			<br/><br/>--%>
<%--			</div>--%>
			<input type="radio" name="msgText" value="document" id="uploadFileRadioId">
			<span>文件: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<input name="uploadFile" id="uploadFile" class="easyui-filebox" style="width: 70%;" data-options="buttonText:'选择文件'" />
			<br/>

		</form>
	</div>
	
	
	
		<div id="ExamineView" class="easyui-dialog" closed="true">
		<form action="" name="ExamineFrom" id="ExamineFrom">
		   <input type="hidden" name="id" id="ex-id">
			<table  class="table-cls" border="0" width="100%">
			    <tr>
			      <td nowrap="nowrap">审核结果:</td>
			      <td><input type="radio" name="msgStatus" value="2" >通过
				          <input type="radio" name="msgStatus" value="3">不通过</td>
			   </tr>
			   <tr>
			      <td nowrap="nowrap">标题:</td>
			      <td id="ex-msgTitle"></td>
			   </tr>
			   <tr>
			      <td nowrap="nowrap">正文:</td>
			      <td id="ex-msgContent"></td>
			   </tr>
<!-- 			   <tr> -->
<!-- 			      <td nowrap="nowrap">首图URL:</td> -->
<!-- 			      <td id="ex-msgFirstP"></td> -->
<!-- 			   </tr> -->
<!-- 			   <tr> -->
<!-- 			      <td nowrap="nowrap">URL:</td> -->
<!-- 			      <td id="ex-msgUrl"></td> -->
<!-- 			   </tr> -->
                <tr>
                   <td colspan="2">
                        <a target="_bank" id="ex-msgUrl"><img width="400" id="ex-msgFirstP"></a>
                   </td>                
                </tr>
			</table>
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
.table-cls{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	}
	
	.table-cls td{
	  border-left: 1px solid #E0E0E0;
	  border-top: 1px solid #E0E0E0;
	}
</style>
</body>
</html>