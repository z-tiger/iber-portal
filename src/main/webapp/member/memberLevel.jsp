<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员等级管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="member/memberLevel.js"></script>
</head>
	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
				
				<!--级别类型:<input id="level_type"  name="level_type" class="easyui-textbox" style="width:80px"/>
				 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a> 
				等级编号:<input id="level_code"  name="level_code" class="easyui-textbox" style="width:80px"/>
				-->
				会员级别名称:<input id="q_name"  name="q_name" class="easyui-textbox" style="width:80px"/>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
				<r:FunctionRole functionRoleId="add_member_level">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
				</r:FunctionRole>
	            <r:FunctionRole functionRoleId="delete_member_level">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a> 
	            </r:FunctionRole>
		        <r:FunctionRole functionRoleId="distribute_right">
		        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAddRights">分配权益</a>
		        </r:FunctionRole>
		        <r:FunctionRole functionRoleId="update_member_level">
		        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
		        </r:FunctionRole>

         </div>
        <!-- update -->
         <div id="addView" class="easyui-dialog" closed="true" style="">
			<form id="addViewForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
		    		<tr>
		    			<td>等级名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'等级名称不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>等级编号：</td>
		    			<td>
		    			<input class="easyui-textbox"  id="levelCode" name="levelCode" style="width:100%;height:24px" data-options="required:true,missingMessage:'等级编码不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>最高贡献值:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="integralUpLimit" id="integralUpLimit" style="width:100%;height:24px">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>最低贡献值:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="integralDownlimit" id="integralDownlimit" style="width:100%;height:24px">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 
		 <!-- 为会员等级分配权益 -->
		 <div id="addRightsView" class="easyui-dialog" closed="true" style="">
		 	<form id="addRightsViewForm" method="post" action="" >
		 		<input type="hidden" name="memberLevelId" id="memberLevelId" />
		 		<div style="margin-top:20px;margin-left:120px;">为<span id="sp1" style="font-weight:bold;font-size:14px;color:blue;"></span>分配权益:</div>
		 		<div style="margin-top:20px;">---------------------------------------------------------</div>
		 		<div id="showMemberRights" style="margin-left:20px;margin-top:20px;">
		 			<c:forEach  items= "${memberRightsList}"  var="mt">
						 <input  type="checkbox" name ="rightsId" value= "${mt.id}" > ${mt.rightsName}
					</c:forEach>
		 			 
		 		</div>
		 	</form>
		 </div>
		 
	</body>
</html>