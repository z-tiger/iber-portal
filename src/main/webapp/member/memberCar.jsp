<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员车牌管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="member/memberCar.js"></script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	    		会员名称:<input id="_memberName"  name="_memberName" class="easyui-textbox"/>
				车牌号:<input id="_lpn"  name="_lpn" class="easyui-textbox"/>
				审核状态:<input id="_checkStatus"  name="_checkStatus" class="easyui-combobox" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '通过',
							value: '1'
						},{
							label: '未通过',
							value: '0'
						}],
	                    panelHeight:'auto'"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	            <r:FunctionRole functionRoleId="audit_member_car">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">审核</a>
	            </r:FunctionRole> 
         	</div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="addForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 10px;">
					<tr>
		    			<td>会员名称:</td>
		    			<td>
		    			 <!-- <input class="easyui-textbox" name='memberName' id="memberName" style="width:300px;"> -->
		    			 <span name='memberName' id="memberName"></span>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>车牌号:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="lpn" name="lpn" style="width:300px;height:24px" data-options="required:true,missingMessage:'设备编码不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>审核状态:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="checkStatus" name="checkStatus	" style="width:100%;height:24px" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '未通过',
							value: '0'
						},{
							label: '通过',
							value: '1'
						}],
	                    panelHeight:'auto'"/>
		    			</td>
		    		</tr><tr>
		    			<td>审核理由:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="checkReason" name="checkReason" style="width:100%;height:24px">
		    			</td>
		    		<tr>
		    		<span style="margin-left:20px;margin-top:20px;"></span>
		    		<a id="examine_lpnPhotoUrlAHERF" target="_blank"><img width="408" height="250" alt="点击预览图片" onclick="" id="examine_lpnPhotoUrl"/></a>
		    		</tr>	
		    	</table>
			</form>
		 </div>
	</body>
</html>