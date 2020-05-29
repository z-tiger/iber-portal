<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="activity/page-activity.js"></script>
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
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			标题:<input id="_title"  name="title" class="easyui-textbox" style="width:160px"/>
			活动类型:
			<input name="code" id="_code" class="easyui-combobox"
				data-options=" url:'sys_dic?dicCode=ACTIVITY_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"/>
			状态:<label><input class="easyui-combobox" name="status"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '全部',
						value: ''
					},{
						label: '开启',
						value: '1'
					},{
						label: '关闭',
						value: '0'
					}]"
					 id="_status" ></label>
	                   
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            
            <r:FunctionRole functionRoleId="add_activity">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole> 
            <r:FunctionRole functionRoleId="update_activity">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole> 
            <r:FunctionRole functionRoleId="delete_activity">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole> 
            
	     </div>
      
		 <div id="editView" class="easyui-dialog" closed="true" style="padding:10px 20px">
	       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
	           <input type="hidden" name="id" id="e-id">
	           <div>
	           		<label>所属城市:</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           		<input class="easyui-combobox" name="cityCode" id="e-cityCode" style="width: 75%;"  required="true"
							data-options=" url:'sys_cityCombobox',
			                    method:'get',
			                    valueField:'id',
			                    textField:'text',
			                    panelHeight:'auto'">
	           </div>
	           <br />
			   <div class="fitem">
					<label>标题:</label><input name="title" id="e-title" class="easyui-textbox" style="width: 75%;"  required="true"  missingMessage="请填写手机号码"/>
			   </div>
			   <div class="fitem">
					<label>活动类型:</label><input name="code" id="e-code" class="easyui-combobox"  style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=ACTIVITY_TYPE',
		                    method:'get',
		                    valueField:'code',
		                    textField:'name',
		                    panelHeight:'auto'"
		                    required="true"  missingMessage="请选择活动类型"/>
			   </div>
			   <div class="fitem">
					<label>活动链接:</label><input name="url" id="e-url" class="easyui-textbox" style="width: 75%;"/>
			   </div>
			    <div class="fitem">
					<label>活动生效时间:</label><input class="easyui-datetimebox s" data-options="required:true,missingMessage:'请选择活动生效日期时间',editable:false"  name="startTime"  id="e-startTime" style="width: 75%;">
			   </div>
			    <div class="fitem">
					<label>活动结束时间:</label><input class="easyui-datetimebox e" data-options="required:true,missingMessage:'请选择活动结束日期时间',editable:false"  name="endTime" id="e-endTime"  style="width: 75%;"/>
			   </div>
			   <div class="fitem">
					<label>状态:</label><input name="status" id="e-status"  class="easyui-combobox"  style="width: 75%;"
					data-options="
		                    valueField:'code',
		                    textField:'label',
		                    data: [{
									label: '活动开启',
									code: '1'
								},{
									label: '活动关闭',
									code: '0'
								}],
		                    panelHeight:'auto'"/>
			   	</div>
				<div class="fitem">
					<label>活动图片:</label><input name="activityMultipartFile" type="file" id="activityMultipartFile"
						onchange="javascript:setImagePreview(this,activityFileImg,activityFilePhoto);" />
			  	</div>
			  	<div class="fitem">
					<div id="activityFileImg">
						<img  width="93%" height="120"
							id="activityFilePhoto"  />
					</div>
			  	</div>
	
	       </form>
   </div>
   		 
	</body>
</html>