<%--<%@ page language="java" pageEncoding="utf-8"%>
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
	<title>用户列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" ></script>
	<script type="text/javascript" src="system/sysuser.js"></script>
</head>--%>

<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
    <%@include file="../common/common-header.jsp"%>
    <title>用户列表</title>
    <script type="text/javascript" src="system/sysuser.js"></script>
</head>
<body>

<body>

	<table id="sysUserGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		登陆账号:<input type="text" id="LoginUAcc" name="LoginUAcc"  class="easyui-textbox">
		姓名:<input type="text" id="LoginUName" name="LoginUName" class="easyui-textbox">
        启用状态:<select class="easyui-combobox" id="seach_status" name="status"  style="width:80px;" data-options="editable:false,panelHeight:'auto'">
                    <option value="" selected></option>
                    <option value="1">启用</option>
                    <option value="0">未启用</option>
                </select>

		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a>
		<r:FunctionRole functionRoleId="add_user">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-contact_blue_add" plain="true" id="btnSave" onclick=";">添加用户</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="assign_role">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-contact_blue_new" plain="true" id="btnSendRole" onclick=";">分派角色</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_user">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-contact_blue_edit" plain="true" id="btnEdit" onclick=";">修改用户</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="assign_right">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-contact_blue" plain="true" id="btnJurisdiction" onclick=";">分派权限</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_password">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnModifyPwd" onclick=";">修改密码</a>
		</r:FunctionRole>
		
<!-- 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnResetPwd" onclick=";">重置密码</a> -->
		
	</div>
	
	<!-- modify password -->
	<div id="modifyPwdView" class="easyui-dialog" closed="true"
		style="width:400px;height:280px;padding:10px 20px">
		<form name="modifyPwdForm" id="modifyPwdForm" method="post">
		    <input name="uid" id="pd-uid" type="hidden">
		     <div class="fitem">
			 <label>原密码:</label>
				<input name="oldPwd" type="password" class="easyui-textbox" required="true"
					missingMessage="请填写原密码" id="oldPwd" />
			</div>

            <div class="fitem">
                <label>新密码:</label>
                <input  name="newPwd" id="pwd" type="password"  class="easyui-textbox"  	required="true"  missingMessage="请确认密码"/>
            </div>
            <div class="fitem">
                <label>确认新密码:</label>
                <input id="pwd2"  class="easyui-textbox" type="password" data-options="required:true"   validType="same['pwd']" missingMessage="请确认密码" invalidMessage="两次输入密码不一致">
            </div>
		</form>
		
	</div>	
	
	<!-- 添加用户 -->
	<div id="sysUserView" class="easyui-dialog" closed="true"
		style="width:400px;height:280px;padding:10px 20px">
		<form id="sysUserForm" method="post" action=""
			onsubmit="return checkButton(this)">
			<div class="fitem">
			 <label>姓名:</label>
				<input name="name" class="easyui-textbox" required="true"
					missingMessage="请填写名称" id="name" style="width: 75%;"/>
			</div>
			<div class="fitem">
				<label>登陆账号:</label> <input name="account" class="easyui-textbox"
					required="true" missingMessage="请填写登陆账号" id="account" style="width: 75%;"/>
			</div>
			<div class="fitem">
				<label>密码:</label> <input  name="password" id="password" type="password"  class="easyui-textbox"  	required="true"  missingMessage="请确认密码" style="width: 75%;"/>
			</div>
			 <div class="fitem">
		                <label>确认密码:</label>
		                <input id="password2"  class="easyui-textbox" type="password" data-options="required:true"   validType="same['password']" missingMessage="请确认密码" invalidMessage="两次输入密码不一致" style="width: 75%;">
		     </div>
			<div class="fitem">
				<label>手机号码:</label> <input name="phone" class="easyui-textbox"
					required="true" missingMessage="请填写手机号码" id="phone"
					data-options="validType:'mobile'" style="width: 75%;" />
			</div>
			
	       <div class="fitem">
				<label>邮箱:</label> <input name="email" class="easyui-textbox"
					required="true" missingMessage="请填写邮箱" id="email"
					data-options="validType:'email'" style="width: 75%;"/>
			</div>
			
		    <div class="fitem">
				<label>所属区域:</label> 
				<input class="easyui-combobox" name="cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'200'"
					required="true" id="cityCode" missingMessage="请填选择所属区域" style="width: 75%;">
			</div>
			
			
			
			<div class="fitem">
				<label>是否启用:</label> <input class="easyui-combobox" name="status"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="status" missingMessage="请填选择是否可用" style="width: 75%;">
			</div>
		</form>
	</div>
	
		<!-- 修改用户 -->
	<div id="sysUserUpdateView" class="easyui-dialog" closed="true"
		style="width:400px;height:450px;padding:10px 20px">
		<form id="sysUserUpdateForm" method="post" action=""
			onsubmit="return checkButton(this)">
			<div class="fitem">
				<input type="hidden" id="eid" name="id">
				<input type="hidden" id="epassowrd" name="passowrd"> 
				 <label>姓名:</label>
				<input name="name" class="easyui-textbox" required="true"
					missingMessage="请填写名称" id="ename" style="width: 75%;"/>
			</div>
			<div class="fitem">
				<label>登陆账号:</label> <input name="account" class="easyui-textbox"
					required="true" missingMessage="请填写登陆账号" id="eaccount" style="width: 75%;"/>
			</div>
			<div class="fitem">
				<label>手机号码:</label> <input name="phone" class="easyui-textbox"
					required="true" missingMessage="请填写手机号码" id="ephone"
					data-options="validType:'mobile'" style="width: 75%;"/>
			</div>
			
	       <div class="fitem">
				<label>邮箱:</label> <input name="email" class="easyui-textbox"
					required="true" missingMessage="请填写邮箱" id="eemail"
					data-options="validType:'email'" style="width: 75%;"/>
			</div>
			
		    <div class="fitem">
				<label>所属区域:</label> 
				<input class="easyui-combobox" name="cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'200'"
					required="true" id="ecityCode" missingMessage="请填选择所属区域" style="width: 75%;">
			</div>
			
			
						
			<div class="fitem">
				<label>是否启用:</label> <input class="easyui-combobox" name="status"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="estatus" missingMessage="请填选择是否可用" style="width: 75%;">
			</div>
		</form>
	</div>
	<!-- 分派角色 -->
		<div id="sendRoleView" class="easyui-dialog" closed="true" style="width:500px;height:450px;padding:10px 20px">
				<ul id="roletree" ></ul>
				<form id="sendRoleForm" method="post" action=""  onsubmit="return checkButton(this)">
				<input type="hidden" name="roleIds" id="roleIds"/>
				<input type="hidden" name="userIds" id="userIds"/>
			</form>
		    </div>	
		    
		    <!-- 分派菜单 -->
		 	<div id="sysModuView" class="easyui-dialog" closed="true" style="width:500px;height:450px;padding:10px 20px">
				<ul id="cdtree" ></ul>
				<form id="sysModuForm" method="post" action=""  onsubmit="return checkButton(this)">
				<input type="hidden" name="moduIds" id="moduIds"/>
				<input type="hidden" name="userIds" id="cduserId"/>
				<input type="hidden" name="is_shows" id="is_show"/>
				<input type="hidden" name="pids" id="pid"/>
				<input type="hidden" name="names" id="text">
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