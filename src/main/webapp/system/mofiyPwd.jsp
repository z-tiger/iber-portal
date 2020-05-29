<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>修改密码</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="system/sysuser.js"></script>
	<style type="text/css">
	.ftitle {
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 25px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}


.fitem a {
	margin-right: 5px;
}
	</style>
</head>

<body>
	<div align="center" class="easyui-panel" style="margin-top:100px; border: 0;">
		<form name="modifyPwdForm" id="modifyPwdForm" method="post">
		    <input name="uid" id="pd-uid" type="hidden" value="${sessionScope.user.id}">
		     <div class="fitem">
			 <label>原密码:</label>
				<input name="oldPwd" type="password" class="easyui-textbox" required="true"
					missingMessage="请填写原密码" id="oldPwd" />
			</div>
			 <div class="fitem">
			 <label>新密码:</label>
				<input name="newPwd" type="password" class="easyui-textbox" required="true"
					missingMessage="请填写新密码" id="newPwd" />
			</div>
			 <div class="fitem">
			 <label>确认新密码:</label>
				<input name="newPwd2" type="password" class="easyui-textbox" required="true"
					missingMessage="请填写确认新密码" id="newPwd2" />
			</div>
			<div align="center" class="fitem"><a href="#" id="saveBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'">确认</a></div>
		</form>
	</div>	
	<script type="text/javascript">
	$(function(){
	     $("#saveBtn").bind("click", function(){
	        $("#modifyPwdForm").form("submit", {
						url : "sys_modify_pwd",
						onSubmit : function() {
							if($(this).form("validate")){

                                var _oldPwd = $("#oldPwd").textbox("getValue");
								var _newPwd = $("#newPwd").textbox("getValue");
								var _newPwd2 = $("#newPwd2").textbox("getValue");
								var reg = /^[A-Za-z0-9]{6,20}$/;
								var reg1 = /[0-9]+/;
								var reg2 = /[A-Za-z]+/;
								if( !reg.test(_newPwd) || !reg1.test(_newPwd) || !reg2.test(_newPwd)){
									$.messager.alert("提示", "新密码不能小于6位，必须是数字加字母组合，请重新输入", "error");
									return false;
								}
								if(_newPwd != _newPwd2){
									$.messager.alert("提示", "2次输入的新密码不一致，请重新输入", "error");
									return false;
								}
                                if(_newPwd == _oldPwd){
                                    $.messager.alert("提示", "新密码与原密码一样，请重新输入", "error");
                                    return false;
                                }
								
							}
							
							
							return $(this).form("validate");
						},
						success : function(result) {
                            var data = eval('(' + result + ')');
							if (data.code == "1") {
								$.messager.alert("提示", "操作成功，请重新登录", "info", function() {
                                    window.parent.location.href='index.jsp';
                                });
							    // $("#sysUserGrid").datagrid("reload");
								$("#modifyPwdView").dialog("close");
							}else if(data.code == "0"){
								$.messager.alert("提示", data.message, "error");
							} else {
								$.messager.alert("提示", "操作失败", "error");
							} 
						}
					});
	     });
	});
	</script>
</body>
</html>