<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>优惠券发放记录</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="member/memberRights.js"></script>
	
</head>
	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
				
				<!--级别类型:<input id="level_type"  name="level_type" class="easyui-textbox" style="width:80px"/>
				 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a> 
				等级编号:<input id="level_code"  name="level_code" class="easyui-textbox" style="width:80px"/>
				-->
				会员权益名称:<input id="q_rightsName"  name="q_rightsName" class="easyui-textbox" style="width:80px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
				<r:FunctionRole functionRoleId="add_member_right">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
				</r:FunctionRole>
				<r:FunctionRole functionRoleId="update_member_right">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
				</r:FunctionRole>
				<r:FunctionRole functionRoleId="delete_member_right">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a> 
				</r:FunctionRole>
         </div>
        <!-- update -->
         <div id="addView" class="easyui-dialog" closed="true" style="">
			<form id="addViewForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
		    		<tr>
		    			<td>权益名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="rightsName" name="rightsName" style="width:90%;height:24px" data-options="required:true,missingMessage:'等级名称不能为空'">
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>权益类型:</td>
		    			<td>
		    			<input class="easyui-combobox"  name="type" id="type" style="width:90%;height:24px" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '优惠券',
							value: '0'
						},{
							label: '折扣券',
							value: '1'
						}],
	                    panelHeight:'auto'"
		    			>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>权益描述:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="descUrl" id="descUrl" style="width:90%;height:24px">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>面值:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="value" id="value" style="width:90%;height:24px">(元)
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>数量:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="number" id="number" style="width:90%;height:24px">(张)
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>权益图标：</td>
		    			<td>
		    				<input  name="iconUrlMultipartFile" id="iconUrlMultipartFile" type="file" onchange="javascript:setImagePreview(this,localImag,adPhoto);"  data-options="prompt:'选择图片...',required:true"  style="width:100%;height:24px">
							<div id="localImag">
				              	<img style="margin-left: 0px;" width="250" height="200"  id="adPhoto" />
				            </div>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>权益灰色图标：</td>
		    			<td>
		    				<input  name="grayIconUrlMultipartFile" id="grayIconUrlMultipartFile" type="file" onchange="javascript:setImagePreview(this,localImag1,adPhoto1);"  data-options="prompt:'选择图片...',required:true"  style="width:100%;height:24px">
							<div id="localImag1">
				              	<img style="margin-left: 0px;" width="250" height="200"  id="adPhoto1" />
				            </div>
		    			</td>
		    		</tr>
		    	
		    	</table>
			</form>
		 </div>
	</body>
</html>