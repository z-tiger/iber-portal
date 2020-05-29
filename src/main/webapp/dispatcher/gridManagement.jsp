<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>网格管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
	<script type="text/javascript" src="dispatcher/gridManagement.js"></script>
	<style type="text/css">
		/*.ftitle {
			font-size: 14px;
			font-weight: bold;
			padding: 5px 0;
			margin-bottom: 10px;
			border-bottom: 1px solid #ccc;
		}
		
		.fitem {
			margin-bottom: 5px;
		}*/
		
		.fitem label {
			display: inline-block;
			width: 80px;
		}
		
		/*.fitem input {
			width: 160px;
		}
		
		.fitem a {
			margin-right: 5px;
		}*/

	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" class="fitem" style="height:auto">
			网格名称:<input id="_gridName"  name="gridName" class="easyui-textbox" style="width:150px"/>           
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="add_grid">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
			</r:FunctionRole>
        	<r:FunctionRole functionRoleId="update_grid">
        		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
        	</r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_grid">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="manage_park">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="mamagePoint">设置网点</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="manage_dispatcher">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="mamageDispatcher">设置调度员</a>
            </r:FunctionRole>
	    </div>
      	<!-- 编辑对话框 -->
		 <div id="editView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="editViewForm" id="editViewForm">
	         	<input type="hidden" name="id" id="e-id">
	         	<input type="hidden" name='fencePoint' id="fencePoint">
	         	<input type="hidden" id='hideCityCode' name='hideCityCode'>
			   <div class="fitem">
			   		<tr>
			   			<td>所属城市:</td>
			   			<td><input class="easyui-combobox" name="cityCode" id="cityCode" data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		                </td>
		                <td>网格名称:</td>
		                <td><input name="gridName" id="e-gridName" class="easyui-textbox" style="width: 30%;"  required="true"  missingMessage="请填写网格名称"/></td>
		            </tr>
			   </div>
			   <div id="gridContent"  style="height: 100%; width:100%;"></div>
	       </form>
   		</div>
   	
   		<!-- 设置网点对话框 -->
   		 <div id="manageParkView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="manageParkViewForm" id="manageParkViewForm">
	       	 	<input type="hidden" name="gridId" id="e-gridId">
			   <div class="fitem">
			   		<tr>
			   			<td>网格名称:</td>
			   			<td>
			   				<input class="easyui-textbox" name="gridName" id="gridName" data-options="disabled:true"/>
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>选择网点:</td>
			   			<td>
			   				<input class="easyui-combobox" name="parkId" id="parkId" data-options=" url:'get_all_park',
		                    method:'get',
		                    valueField:'id',
		                    textField:'name',
		                    panelHeight:'200px'">
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="e-specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>	
	       </form>
   		</div>
   		
   		<!-- 设置调度员 -->
   		<div id="manageDispatcherView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="manageDispatcherViewForm" id="manageDispatcherViewForm">
	       	 	<input type="hidden" name="newGridId" id="e-newGridId">
			   <div class="fitem">
			   		<tr>
			   			<td>网格名称:&nbsp;</td>
			   			<td>
			   				<input class="easyui-textbox" name="gridName" id="grid" data-options="disabled:true"/>
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>选择调度员:</td>
			   			<td>
			   				<input class="easyui-combobox" name="dispatcherId" id="dispatcherId" data-options=" url:'get_all_dispatcher',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'200px'">
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="e-specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>	
	       </form>
   		</div>
   		
   		<div id="parkDetailView" class="easyui-dialog" closed="true"
			style="padding-left:20px;padding-right:20px;padding-top:3px;padding-bottom:30px; width: 1000px; height: 400px;">
			网点名称:<input id="_parkName"  name="parkName" class="easyui-textbox" style="width:150px"/>
			<input type="hidden" name="gridId" id="gridId">           
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnParkQuery">查询</a>
			<r:FunctionRole functionRoleId="remove_park">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnParkDelete">移除</a>
			</r:FunctionRole>
			 <table id="parkDetailDataGrid" 
			 	cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px; left:80px;width:600px;height:200px""></table>
			 <div id="toolbar" class="fitem" style="height:auto"></div>
	    <div id="dispatcherDetailView" class="easyui-dialog" closed="true"
			style="padding-left:20px;padding-right:20px;padding-top:3px;padding-bottom:30px; width: 1000px; height: 400px;">
			调度员名称:<input id="_dispatcherName"  name="dispatcherName" class="easyui-textbox" style="width:150px"/>
			<input type="hidden" name="gridId" id="_gridId">           
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnDispatcherQuery">查询</a>
			<r:FunctionRole functionRoleId="remove_dispatcher">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDispatcherDelete">移除</a>
			</r:FunctionRole>
			<!-- 该位置有两个功能按钮被删 -->
			 <table id="dispatherDetailDataGrid" 
			 	cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px; left:80px;width:600px;height:200px"">
			 	</table>
			 <div id="toolbar" class="fitem" style="height:auto"></div>
	    </div> 
	</body>
</html>