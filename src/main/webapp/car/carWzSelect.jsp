<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
  <head>
    <meta charset="UTF-8">
    <title>车辆周边城市管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="car/carWzSelect.js"></script>
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

.fundSettlementWorkflowTable{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	
	}
	
	.fundSettlementWorkflowTable td{
	  border-left: 1px solid #E0E0E0;
	  border-top: 1px solid #E0E0E0;
	}
</style>
  </head>
      
  <body style="width:100%;height:100%;">
       <table id="grid" toolbar="#toolbar"></table>
       
        <div id="toolbar" style="height:auto">	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="add_version_type">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="update_version_type">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="delete_version_type">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete">删除</a>	
			</r:FunctionRole>  		
	   </div>
	   <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:300px;height:500px;padding:10px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;">
		    		<tr>
		    			<td>中心城市:</td>
		    			<td>
		    			<input class="easyui-combobox" name="cityCode" id="cityCode"  data-options="required:true,valueField:'id',textField:'text',url:'sys_optional_cityCombobox',panelHeight:'160'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>周边城市:</td>
		    			<td>
		    			<input class="easyui-combogrid"  id="associatedCity" name="associatedCity" style="width:100%;height:24px">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
  </body>
</html>