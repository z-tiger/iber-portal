<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>调度员管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="dispatch/common.js"></script>	
	<script type="text/javascript" src="dispatch/dispatcherPage.js"></script>
	
	<style type="text/css" >
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
</head>

<body>
   
	<table id="dataGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
	<!-- <form id="data" method="post" action="#" enctype="multipart/form-data" > -->
			<label class="label">会员名称:&nbsp;</label>
			<input class="easyui-textbox"" name="memberName" id="memberName" style="width: 120px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear">清空</a>
			
			<r:FunctionRole functionRoleId="add_dispatcher">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="update_dispatcher">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
			</r:FunctionRole>
   		
	<!-- </form> -->
		</div>	
        
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:600px;height:400px;padding:50px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;width:350px">
					<tr>
		    			<td>会员名称:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="memberId" id="memberId" style="width:90%;height:36px" 
			    				data-options=" url:'dispatcher_member_type',
				                    method:'get',
				                    valueField:'id',
				                    textField:'name',
				                    panelHeight:'auto',
				                    editable:false,required:true,missingMessage:'会员不能为空'" />
		    			  </td>
		    		</tr>
		    		<tr>
		    			<td>是否有效:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="isEnable" id="isEnable" style="width:90%;height:36px" 
			    				data-options=" url:'sys_dic?dicCode=YES_NO',
				                    method:'get',
				                    valueField:'code',
				                    textField:'name',
				                    panelHeight:'auto',
				                    editable:false,required:true,missingMessage:'是否有效不能为空'" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>备注:</td>
		    			<td>
			    			<input class="easyui-textbox"  name="comment" id="comment" style="width:90%;height:80px" data-options="multiline:true,required:true">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
</body>
</html>