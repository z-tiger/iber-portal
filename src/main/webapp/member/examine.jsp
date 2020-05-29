<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>会员审核</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js"></script>
<script type="text/javascript" src="member/examine.js"></script>
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
   <div id="toolbar" style="height:auto;">
   <form name="memberSearchForm" id="memberSearchForm" >
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
		手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
	      资料上传时间:
	       <input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" />

		  <input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>	
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<r:FunctionRole functionRoleId="audit_member">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="btnExamine" onclick=";">审核</a>
		</r:FunctionRole>
      <%-- <r:FunctionRole functionRoleId="data_change_audit">
           <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="dataChangeBtn" onclick=";">资料变更审核</a>
       </r:FunctionRole>--%>
		</form>
   </div>
     <!-- 工具栏  end-->

   <!-- 注册审核 -->
   <div id="examineView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
        <form  name="examineForm" id="examineForm" method="post">
        <input type="hidden" name="id" id="examine_id">
        <input type ="hidden" name ="channel" id = "channel">
        <input type ="hidden" name ="phone" id = "phone">
       <div class="fitem">
       		<div class="fitem">
	         <label>手机号码:</label><span id="examine_phone"></span>
	       </div>
	       
        <label>姓名:</label>
        <input   id="examine_name" name="examineName" class="easyui-textbox" />
        <span style="color:green;" id="examine_result_1"></span> 
        <span style="color:red;" id="examine_result_2"></span>
       </div>
       
       <div class="fitem">
         <label>身份证号码:</label>
          <input name="examineIdcard" value="" id="examine_idcard" class="easyui-textbox easyui-validatebox" data-options="required:true,missingMessage:'请输入真实身份证',validType:'idcared'"/>
         <span style="color:red;" id="idcard_result"></span>
       </div>
       
       <div class="fitem" >
          <label>驾驶证号码:</label>
          <input required=true id="examine_driverIdcard" name="examineDriverIdcard" class="easyui-textbox" />
        <span style="color:red;" id="driver_card_result"></span>
       </div>
       
       <div class="fitem" >
          <label>初次领证日期:</label>
          <input id="examine_driverIdcardTime"  name="examineDriverIdcardTime" class="easyui-datebox" data-options="required:true"/>
        <!--  <label>驾驶证号码:</label><span id="examine_driverIdcard"></span> -->
          <span style="color:red;" id="driver_idcard_time_result"></span>
       </div>
       <div class="fitem" >
          <label>驾驶证有效期:</label>
          <input id="examine_driverIdcardValidityTime"  name="examineDriverIdcardValidityTime" class="easyui-datebox" data-options="required:true" />
       </div>
       <div class="fitem">
<!-- 				<img width="408" height="250" alt="点击预览图片" onclick="openImgAction(this)" id="examine_driverIdcardPhotoUrl"/> -->
 			<a id="examine_driverIdcardPhotoUrlAHERF" target="_blank"><img width="408" height="250" alt="点击预览图片" onclick="" id="examine_driverIdcardPhotoUrl"/></a>	
       </div>
       <div class="fitem">
 			<a id="examine_idcardPhotoUrlAHERF" target="_blank"><img width="408" height="250" alt="点击预览图片" onclick="" id="examine_idcardPhotoUrl"/></a>	
       </div>
        <div class="fitem">
         <label>审核结果:</label>
         <input class="easyui-combobox" name="remark" id="remark"  style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=DIC_EXAMINE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'" />
       </div>
       </form>
    </div>
  <div id="examineViewImg" class="easyui-dialog" closed="true" style="padding:10px 20px">
		<img id="dispalyPhoneDivImg">
  </div>		
</body>
</html>