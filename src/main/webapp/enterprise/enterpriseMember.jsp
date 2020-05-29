<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>企业会员管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="enterprise/enterpriseMember.js" ></script>
<style type="text/css">
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
<body>
   <table id="grid" toolbar="#toolbar"></table>
     
      <!-- 工具栏  start-->
   <div id="toolbar" style="height:auto">
      <div><!-- <form name="memberSearchForm" id="memberSearchForm"> -->
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'" style="width: 80px;" >
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name" >
		企业名称:<input  name="enterpriseName" class="easyui-textbox" id="s-enterpriseName" />
		手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery" onclick=";">清空</a>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a> -->
		<%--<r:FunctionRole functionRoleId="update_enterpriseMember">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>--%>
		<%--<r:FunctionRole functionRoleId="delete_fingerprint">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-finger" plain="true" id="btnFinger" onclick=";">删除指纹</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="reset_passwod">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-password" plain="true" id="btnRestPassword" onclick=";">重置密码</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="audit">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="btnExamine" onclick=";">审核</a>
		</r:FunctionRole>--%>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-user-id" plain="true" id="btnRestPassword" onclick=";">实名认证</a> -->
		<!-- </form> -->
      </div>
   </div>
     <!-- 工具栏  end-->


    
    <!-- add view -->
   <div id="addView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data">
		   <div class="fitem">
				<label>手机号码:</label> <input  name="phone" class="easyui-textbox"  required="true"  missingMessage="请填写手机号码"/>
		   </div>
		   <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" 
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择所属城市">
		   </div>
           <div class="fitem">
				<label>姓名:</label> <input  name="name"  class="easyui-textbox"  />
		   </div>
		    <div class="fitem">
				<label>性别:</label> <input class="easyui-combobox" name="sex"  
					data-options=" url:'sys_dic?dicCode=DIC_SEX',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
				
		   </div>
		    <div class="fitem">
				<label>Email:</label> <input  name="email"  class="easyui-textbox"  />
		   </div>
		    <div class="fitem">
				<label>身份证号码:</label> <input  name="idcard"  class="easyui-textbox"  />
		   </div>
		      <div class="fitem">
				<label>驾驶证号码:</label> <input  name="driverIdcard"  class="easyui-textbox"  />
		   </div>
		   <div class="fitem">
				<label>驾驶证照片:</label> <input  name="driverIdcardMultipartFile" type="file"
					onchange="javascript:setImagePreview(this,driverIdcardFileImg,driverIdcardFilePhoto);" />
				<div id="driverIdcardFileImg">
					<img style="margin-left: 85px;" width="160" height="120"
						id="driverIdcardFilePhoto" alt="预览图片"
						onclick="over(driverIdcardFilePhoto,driverIdcardFileImg);" />
				</div>
		</div>

       </form>
   </div>
   
   
    <!-- add view -->
   <div id="editView" class="easyui-dialog" closed="true"
		style="padding:20px 20px 20px 50px">
       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="id" id="e-id">
		   <div class="fitem">
				<label>手机号码:</label> <input  name="phone" id="e-phone" class="easyui-textbox"  required="true"  missingMessage="请填写手机号码"/>
		   </div>
		   <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="e-cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择所属城市">
		   </div>
           <div class="fitem">
				<label>姓名:</label> <input  name="name" id="e-name" class="easyui-textbox"  />
		   </div>
		    <div class="fitem">
				<label>性别:</label>  <input class="easyui-combobox" name="sex"  id="e-sex"
					data-options=" url:'sys_dic?dicCode=DIC_SEX',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   </div>
		    <div class="fitem">
				<label>Email:</label> <input  name="email" id="e-email" class="easyui-textbox"  />
		   </div>
		    <div class="fitem">
				<label>身份证号码:</label> <input  name="idcard" id="e-idcard"  class="easyui-textbox"  />
		   </div>
		      <div class="fitem">
				<label>驾驶证号码:</label> <input  name="driverIdcard" id="e-driverIdcard" class="easyui-textbox"  />
		   </div>
		   <div class="fitem">
				<label>驾驶证照片:</label> <input  name="driverIdcardMultipartFile" type="file" id="edriverIdcardMultipartFile"
					onchange="javascript:setImagePreview(this,edriverIdcardFileImg,edriverIdcardFilePhoto);" />
				<div id="edriverIdcardFileImg">
					<img style="margin-left: 85px;" width="160" height="120"
						id="edriverIdcardFilePhoto" alt="预览图片"
						onclick="over(edriverIdcardFilePhoto,edriverIdcardFileImg);" />
				</div>
		</div>
       </form>
   </div>
   
   <!-- 审核 -->
   <div id="examineView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
        <form  name="examineForm" id="examineForm" method="post">
        <input type="hidden" name="id" id="examine_id">
       <div class="fitem">
<!--          <label>姓名:</label><input   id="examine_name"  class="easyui-textbox"  disabled="disabled"/> -->
        <label>姓名:</label><span id="examine_name"></span>
         <span style="color:green;" id="examine_result_1"></span>
         <span style="color:red;" id="examine_result_2"></span>
       </div>
       
       <div class="fitem">
<!--          <label>手机号码:</label><input   id="examine_phone"  class="easyui-textbox"  disabled="disabled"/> -->
         <label>手机号码:</label><span id="examine_phone"></span>
       </div>
       
       <div class="fitem">
<!--          <label>身份证号码:</label><input   id="examine_idcard"  class="easyui-textbox"  disabled="disabled"/> -->
         <label>身份证号码:</label><span id="examine_idcard"></span>
       </div>
       
       
       <div class="fitem" >
<!--          <label>驾驶证号码:</label><input   id="examine_driverIdcard"  class="easyui-textbox"  disabled="disabled"/> -->
         <label>驾驶证号码:</label><span id="examine_driverIdcard"></span>
       </div>
       
<!--        <div class="fitem" align="center"> -->
<!--          <label>驾驶证照片:</label> -->
<!--        </div> -->
       <div class="fitem">
<!-- 				<img width="408" height="250" alt="点击预览图片" onclick="openImgAction(this)" id="examine_driverIdcardPhotoUrl"/> -->
 			<a id="examine_driverIdcardPhotoUrlAHERF" target="_blank"><img width="408" height="250" alt="点击预览图片" onclick="" id="examine_driverIdcardPhotoUrl"/></a>	
       </div>
        <div class="fitem">
         <label>审核结果:</label>
         <input class="easyui-combobox" name="remark"  style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=DIC_EXAMINE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'" />

       </div>
       </form>
    </div>
    
    
       
</body>
</html>