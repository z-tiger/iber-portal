<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>充电桩管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="network/pile.js"></script>
<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=add84e19ee7ef7540e570cbf4c82cbfa"></script>
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

  <table id="pileGrid" toolbar="#toolbar"></table>
    
   <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		充电桩名称:<input type="text" name="name" class="easyui-textbox" id="sname">
		状态:<input class="easyui-combobox" name="status" id="sstatus"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		充电桩类型:<input class="easyui-combobox" name="type" id="stype"
					data-options=" url:'sys_dic?dicCode=PILE_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" >查询</a>
		
		<r:FunctionRole functionRoleId="add_pile">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_pile">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" >删除</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_pile">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" >修改</a>
		</r:FunctionRole>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-marker_squared_red" plain="true" id="btnMapLook" >地图查看</a>
   </div>


 <!-- add pile info -->
   <div id="addView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" >
           <div class="fitem">
				<label>充电桩名称:</label> <input  name="name" id="a-name"   class="easyui-textbox" style="width: 75%;" 	required="true"  missingMessage="充电桩名称"/>
		   </div>
           <div class="fitem">
				<label>所属网点:</label> <input class="easyui-combobox" name='parkId' style="width: 75%;"
					data-options=" url:'park_all',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="a-park_id" missingMessage="请选择网点">
		   </div>
           <div class="fitem">
				<label>充电桩类型:</label> <input class="easyui-combobox" name="type" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=PILE_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="a-type" missingMessage="请选择充电桩类型">
		   </div>
           <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" style="width: 75%;"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true" id="a-cityCode" missingMessage="请选择所属城市">

		   </div>
           <div class="fitem">
				<label>状态:</label> <input class="easyui-combobox" name="status" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="a-status" missingMessage="请选择状态">

		   </div>
           <div class="fitem">
				<label>纬度:</label> <input  name="latitude" id="a-latitude" class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写纬度"/>
		   </div>
           <div class="fitem">
				<label>经度:</label> <input  name="longitude" id="a-longitude" class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写经度"/>
		   </div>
       </form>
   </div>
   
   <!-- modify pile info -->
   <div id="editView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" >
           <input type="hidden" id="eid" name="id">
           <div class="fitem">
				<label>充电桩名称:</label> <input  name="name" id="e-name"   class="easyui-textbox" style="width: 75%;" 	required="true"  missingMessage="充电桩名称"/>
		   </div>
           <div class="fitem">
				<label>所属网点:</label> <input class="easyui-combobox" name='parkId' style="width: 75%;"
					data-options=" url:'park_all',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="e-park_id" missingMessage="请选择网点">
		   </div>
           <div class="fitem">
				<label>充电桩类型:</label> <input class="easyui-combobox" name="type" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=PILE_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="e-type" missingMessage="请选择充电桩类型">
		   </div>
           <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" style="width: 75%;"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true" id="e-cityCode" missingMessage="请选择所属城市">

		   </div>
           <div class="fitem">
				<label>状态:</label> <input class="easyui-combobox" name="status" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
					required="true" id="e-status" missingMessage="请选择状态">

		   </div>
           <div class="fitem">
				<label>纬度:</label> <input  name="latitude" id="e-latitude" class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写纬度"/>
		   </div>
           <div class="fitem">
				<label>经度:</label> <input  name="longitude" id="e-longitude" class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写经度"/>
		   </div>
       </form>
   </div>
   
   
     <div id="mapLookDialgView" class="easyui-dialog" closed="true"style="padding:10px 20px">	
   	    <div id="imapLookDialgView"  style="width:99.9%;height:380px;border:#ccc solid 1px;"></div>
   	</div>

</body>
</html>