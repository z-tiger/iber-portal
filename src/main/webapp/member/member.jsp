<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>会员管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="member/member.js"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>

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
   <form action="" id="memberForm">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode" style="width:100px;height:24px"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name" style="width:120px;height:24px">
		手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone" style="width:120px;height:24px">
		状态:<input class="easyui-combobox" name="status" id="s-status" style="width:100px;height:24px"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '体验',
							value: 'experience'
						},{
							label: '就绪',
							value: 'ready'
						}]">
	      资料上传时间:
	       <input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" />
		  --
		  <input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>	
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<r:FunctionRole functionRoleId="member_add_member">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update__member">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
       <r:FunctionRole functionRoleId="delete_face">
           <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-man" plain="true" id="btnFace" >删除人脸</a>
       </r:FunctionRole>
		<r:FunctionRole functionRoleId="member_delete_fingerprint">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-finger" plain="true" id="btnFinger" onclick=";">删除指纹</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="reset_password">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-password" plain="true" id="btnRestPassword" onclick=";">重置密码</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="bind_enterprise">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_share" plain="true" id="btnSetEnterprise" onclick=";">绑定企业</a>
		</r:FunctionRole>
	
<!-- 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="btnExamine" onclick=";">审核</a> -->
		
<!-- 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-user-id" plain="true" id="btnRestPassword" onclick=";">实名认证</a> -->
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-user-id" plain="true" id="btnQueryDriver" onclick=";">驾驶证到期查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="deleteFingerQuery" onclick=";">指纹及人脸删除日志</a>
		<r:FunctionRole functionRoleId="export_member">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
   </div>
     <!-- 工具栏  end-->


    
    <!-- add view -->
   <div id="addView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data">
		   <div class="fitem">
				<label>手机号码:</label><input  name="phone" class="easyui-textbox"  required="true" style="width: 75%;"  missingMessage="请填写手机号码"/>
		   </div>
		   <div class="fitem">
				<label>所属城市:</label><input class="easyui-combobox" name="cityCode"  style="width: 75%;" 
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					 missingMessage="请选择所属城市">
		   </div>
           <div class="fitem">
				<label>姓名:</label><input  name="name"  class="easyui-textbox"  style="width: 75%;" />
		   </div>
		    <div class="fitem">
				<label>性别:</label><input class="easyui-combobox" name="sex"   style="width: 75%;" 
					data-options=" url:'sys_dic?dicCode=DIC_SEX',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
				
		   </div>
		    <div class="fitem">
				<label>Email:</label><input  name="email"  class="easyui-textbox"  style="width: 75%;" />
		  </div>
		   <%--  <div class="fitem">
				<label>身份证号码:</label><input  name="idcard"  class="easyui-textbox"  style="width: 75%;" data-options="required:true"/>
		   </div>
		      <div class="fitem">
				<label>驾驶证号码:</label><input  name="driverIdcard"  class="easyui-textbox"  style="width: 75%;" data-options="required:true"/>
		   </div>
		   <div class="fitem">
				<label>驾驶证初次领证日期:</label> <input id="driverIdCardTime"  name="driverIdCardTime" class="easyui-datebox" data-options="required:true" />
		   </div>
		   <div class="fitem">
				<label>驾驶证有效期:</label> <input id="driverIdcardValidityTime"  name="driverIdcardValidityTime" class="easyui-datebox" data-options="required:true" />
		   </div>
		   <div class="fitem">
				<label>驾驶证照片:</label><input  name="driverIdcardMultipartFile" type="file"
					onchange="javascript:setImagePreview(this,driverIdcardFileImg,driverIdcardFilePhoto);" />
		  </div>
        <div class="fitem">
				<div id="driverIdcardFileImg">
					<img  width="93%" height="120"
						id="driverIdcardFilePhoto" />
				</div>
        </div>
        
        <div class="fitem">
					<label>身份证相片:</label> <input  name="idcardMultipartFile" type="file"
						onchange="javascript:setImagePreview(this,idcardFileImg,idcardFilePhoto);" />
		</div>
		<div class="fitem">
				<div id="idcardFileImg">
					<img  width="93%" height="120"
						id="idcardFilePhoto"  />
				</div>
		  </div>--%>
       </form>
   </div>
   
   
    <!-- add view -->
   <div id="editView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="id" id="e-id">
		   <div class="fitem">
				<label>手机号码:</label> <input  name="phone" id="e-phone" class="easyui-textbox" style="width: 55%;"  required="true"  missingMessage="请填写手机号码"/>
		   		<span id="phoneMsg" style="color:red;position:relative;left:0px;font-size:10px;"></span>
		   </div>
		   <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="e-cityCode" style="width: 75%;"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					 missingMessage="请选择所属城市">
		   </div>
           <div class="fitem">
				<label>姓名:</label> <input  name="name" id="e-name" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		    <div class="fitem">
				<label>性别:</label>  <input class="easyui-combobox" name="sex"  id="e-sex" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=DIC_SEX',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   </div>
		    <div class="fitem">
				<label>Email:</label> <input  name="email" id="e-email" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		    <div class="fitem">
				<label>身份证号码:</label> <input  name="idcard" id="e-idcard"  class="easyui-textbox"  style="width: 55%;"/>
				<span id="idcardMsg" style="color:red;position:relative;left:0px;font-size:10px;"></span>
		   </div>
		      <div class="fitem">
				<label>驾驶证号码:</label> <input  name="driverIdcard" id="e-driverIdcard" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   
		   <div class="fitem">
				<label>驾驶证初次领证日期:</label> <input id="e-driverIdCardTime"  name="driverIdCardTime" class="easyui-datebox" data-options="required:true,validType:'checkDate'" />
				<span style="color:red;" id="driver_idcard_time_result"></span>
		   </div>
		  <div class="fitem">
				<label>驾驶证有效期:</label> <input id="e-driverIdcardValidityTime"  name="driverIdcardValidityTime" class="easyui-datebox" data-options="required:true" />
		   </div>
	
		   <div class="fitem">
				<label>驾驶证照片:</label> <input  name="driverIdcardMultipartFile" type="file" id="edriverIdcardMultipartFile"
					onchange="javascript:setImagePreview(this,edriverIdcardFileImg,edriverIdcardFilePhoto);" />
		  </div>
		  <div class="fitem">
				<div id="edriverIdcardFileImg">
					<img  width="93%" height="120"
						id="edriverIdcardFilePhoto"  />
				</div>
		  </div>
		  
		  <div class="fitem">
					<label>身份证相片:</label> <input  name="idcardMultipartFile" type="file"
						onchange="javascript:setImagePreview(this,eIdcardFileImg,eIdcardFilePhoto);" />
		</div>
		<div class="fitem">
				<div id="eIdcardFileImg">
					<img  width="93%" height="120"
						id="eIdcardFilePhoto"  />
				</div>
		  </div>
       </form>
   </div>
   <!-- 删除指纹弹框 -->
   <div id="deleteFingerView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="fingerDeleteForm" id="fingerDeleteForm" method="post">
           <input type="hidden" name="e_id" id="e_id">
           <input type="hidden" name="e_idCard" id="e_idCard">
           <input type="hidden" name="e_cityCode" id="e_cityCode">
           <div class="fitem">
				请会员报出身份证号出生年月日，客服核对是否一致:
		   </div>
		   <div class="fitem" style="margin-top:16px;" >
				<label>会员姓名:</label> <span id="sp4"style="position:relative;left:-30px;font-size:14px;"/>
		   </div>
		   <div class="fitem" style="margin-top:16px;">
				<label>身份证号:</label>  <span id="sp1"style="position:relative;left:-30px;font-size:14px;"></span><span id="sp2" style="font-size:14px;color:red;position:relative;left:-30px;"></span><span id="sp3"style="font-size:14px;position:relative;left:-30px;"></span>
		   </div>
		   <div class="fitem" style="margin-top:16px;">
				删除原因: <select id="reason" >   
							  <option   value="1">手指受伤了无法读指纹</option>   
							  <option   value="2">多次验证指纹失败</option>
							  <option   value="2">忘记了指纹是哪根手指</option>     
						</select>
		   </div>
		   <div style="margin-top:16px;" align="left">
			   <label for="allCheck">清空所有指纹: </label><input id="allCheck" type="checkbox" name="clearAll" />
		   </div>
       </form>
   </div>
      <%--删除人脸弹框--%>
    <div id="deleteFaceView" class="easyui-dialog" closed="true"
         style="padding:10px 20px">
        <form name="faceForm" id="faceDeleteForm" method="post">
            <input type="hidden" name="m_id" id="m_id">
            <input type="hidden" name="m_idCard" id="m_idCard">
            <input type="hidden" name="m_cityCode" id="m_cityCode">
            <div class="fitem">
                请会员报出身份证号出生年月日，客服核对是否一致:
            </div>
            <div class="fitem" style="margin-top:16px;" >
                <label>会员姓名:</label> <span id="nameSpan"style="position:relative;left:-30px;font-size:14px;"/>
            </div>
            <div class="fitem" style="margin-top:16px;">
                <label>身份证号:</label>
                <span id="idCardSpan1"style="position:relative;left:-30px;font-size:14px;"></span>
                <span id="idCardSpan2" style="font-size:14px;color:red;position:relative;left:-30px;"></span>
                <span id="idCardSpan3"style="font-size:14px;position:relative;left:-30px;"></span>
            </div>
            <div class="fitem" style="margin-top:16px;">
                删除原因: <select id="deleteFaceReason" class="easyui-combobox" data-options="panelHeight:'auto'">
                <option   value="1">多次验证不通过</option>
            </select>
            </div>
           <%-- <div style="margin-top:16px;" align="left">
                <label for="allCheck">清空所有指纹: </label><input id="allCheck" type="checkbox" name="clearAll" />
            </div>--%>
        </form>
    </div>
   <!-- 审核 -->
   <div id="examineView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <div class="ftitle">会员实名认证及驾照审核</div>
        <form  name="examineForm" id="examineForm" method="post">
        <input type="hidden" name="id" id="examine_id">
       <div class="fitem" align="center">
         <label>姓名:</label><input   id="examine_name"  class="easyui-textbox"  disabled="disabled"/>
         <span style="color:green;" id="examine_result_1"></span>
         <span style="color:red;" id="examine_result_2"></span>
       </div>
       
       <div class="fitem" align="center">
         <label>手机号码:</label><input   id="examine_phone"  class="easyui-textbox"  disabled="disabled"/>
       </div>
       
       <div class="fitem" align="center">
         <label>身份证号码:</label><input   id="examine_idcard"  class="easyui-textbox"  disabled="disabled"/>
       </div>
       
       
       <div class="fitem" align="center">
         <label>驾驶证号码:</label><input   id="examine_driverIdcard"  class="easyui-textbox"  disabled="disabled"/>
       </div>
       
<!--        <div class="fitem" align="center"> -->
<!--          <label>驾驶证照片:</label> -->
<!--        </div> -->
       <div class="fitem">
				<img  width="580"  id="examine_driverIdcardPhotoUrl" alt="预览图片"/>
       </div>
        <div class="fitem" align="center">
         <label>审核结果:</label>
         <input class="easyui-combobox" name="remark" 
					data-options=" url:'sys_dic?dicCode=DIC_EXAMINE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'" />

       </div>
       </form>
    </div>
    
    <div id="setEnterpriseView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
		<form id="setEnterpriseForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
			<input type="hidden" name="set_enterprise_id" id="set_enterprise_id" />
			<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 50px;">
				<tr>
	    			<td>企业名称:</td>
	    			<td>
	    				<input class="easyui-combogrid" name="enterpriseId" id="enterpriseId" style="width:88%;height:24px" 
							data-options="
								editable:false,
								panelHeight: 'auto',
								panelWidth: 300,
								idField: 'id',
								textField: 'enterpriseName',
								url : 'enterprise_list.do',
								pageSize:10,
								required:true,
								columns : [ [ 
								{
									field : 'enterpriseName',
									title : '企业名称',
									width : '80%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:10%;" title="清空"></a>
		    		</td>
	    		</tr>
	    	</table>
		</form>
	</div>
    <!-- 指纹删除记录 -->
	<div id="deleteFingerLogView" class="easyui-dialog" closed="true" style="padding:10px 20px">
       	  <form name="deleteFingerLogForm" id="deleteFingerLogForm" method="post" >
	       	   <div id="toolbar" style="height:auto;margin-bottom:6px;">
					所属城市:<input class="easyui-combobox" name="d_cityCode"  id="d_cityCode" style="width: 15%;"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
	              
		            <label style="margin-left:15px;">会员姓名:</label><input   id="d_name"  class="easyui-textbox" style="width: 15%;" />
		            <label style="margin-left:15px;">手机号码:</label><input   id="d_phone"  class="easyui-textbox" style="width: 15%;" />
                   <label style="margin-left: 15px" >删除类型</label>
                   <select id="deleteType" class="easyui-combobox" name="deleteType" style="width:15%;">
                       <option value=""></option>
                       <option value="1">指纹删除</option>
                       <option value="2">人脸删除</option>
                   </select>
				    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnDeleteLogQuery">查询</a>       	     
				    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="fingerAndFaceLogClear">清空</a>
		       </div>
	       	   <table id="deleteFingerAndFaceLogDatagrid" style="width: 90%;height:90%;"></table>
    	</form>
    </div>
       
</body>
</html>