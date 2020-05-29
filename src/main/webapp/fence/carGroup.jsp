<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆类型</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="fence/common.js"></script>
<script type="text/javascript" src="fence/carGroup.js"></script>
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
	width: 90px;
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

 <table id="carGroupGrid" toolbar="#toolbar"></table>
 
    <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		组名称:<input type="text" name="name" class="easyui-textbox" id="s-name">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		
		<r:FunctionRole functionRoleId="add_car_group">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_car_group">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_car_group">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" onclick=";">删除</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="manage_elec_fence">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setFence" onclick=";">设置电子围栏</a>
		</r:FunctionRole>
		
   </div>

 <!-- view -->
  <div id="view" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="viewForm" id="viewForm" method="post" >
       		<input type="hidden" name='id' id="e-id">
            <div class="fitem">
				<label>所属城市:</label><input class="easyui-combobox" name="cityCode" id="e-cityCode" style="width: 75%;"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		   </div>
           <div class="fitem">
				<label>组名称:</label><input  name="name" id="e-name" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <div class="fitem">
				<label>组描述:</label><input class="easyui-textbox" id="e-describe" name="describe" data-options="multiline:true" style="width:75%;height:60px"></input>
		   </div>
       </form>
   </div> 

	<div id="showCarView" class="easyui-dialog" closed="true" style="width:800px;height:500px;padding-bottom: 25px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">移除</a>
		
		<table id="showCarGrid"></table>
		
		<div id="addCarView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
				<form id="addCarForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
					<input type="hidden" name="groupId" id="groupId" />
					<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
			    		<tr>
			    			<td>车牌号:</td>
			    			<td>
			    				<input class="easyui-textbox"  name="lpn" id="lpn" style="width:100%;height:24px" data-options="required:true,missingMessage:'车牌号不能为空'" >
			    			</td>
			    		</tr> 
			    	
			    	</table>
				</form>
			</div>
			
	</div>
	
	<!-- view -->
  <div id="fenceView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="fenceViewForm" id="fenceViewForm" method="post" >
       		<input type="hidden" name='groupId' id="g-id">
       		<input type="hidden" name='category' id="g-category" value="group">
            <div class="fitem">
				<label>请选择电子围栏:</label><input class="easyui-combobox" name="fenceId" id="g-fence" style="width: 75%;"
					data-options=" url:'',
	                    method:'get',
	                    valueField:'id',
	                    textField:'fenceName',
	                    panelHeight:'auto'">
		   </div>
       </form>
   </div> 
   	
   <div id="showFenceView" class="easyui-dialog" closed="true" style="width:800px;height:500px;padding-bottom: 25px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemoveFence">移除</a>
		<table id="showFenceGrid"></table>
   </div>
	
</body>
</html>