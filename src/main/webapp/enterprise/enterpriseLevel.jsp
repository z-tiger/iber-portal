<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>企业等级管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="enterprise/enterpriseLevel.js" ></script>
</head>
	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
<!-- 			所属地区:<input class="easyui-combobox" name="_area_code" id="_area_code" style="width: 80px;"  -->
<!-- 				data-options=" url:'adposition_areaCode.do',method:'get',valueField:'code',editable:false,textField:'name',panelHeight:'auto'"> -->
			企业等级:<input id="_name"  name="_name" class="easyui-textbox"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
         
            <r:FunctionRole functionRoleId="add_enterprise_grade">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_enterprise_grade">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_enterprise_grade">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
           
        </div>

         
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 400px;height: 350px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 40px;">
		    		<tr>
		    			<td>企业等级:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'会员等级不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>押金:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="deposit_limit" id="deposit_limit" style="width:100%;height:24px" data-options="required:true,missingMessage:'押金不能为空',precision:0,groupSeparator:',',max:1000000000">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>透支额:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="overdraft" id="overdraft" style="width:100%;height:24px" data-options="required:true,missingMessage:'透支额不能为空',precision:0,groupSeparator:',',max:1000000000">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>会员押金金额:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="member_deposit_limit" id="member_deposit_limit" style="width:100%;height:24px" data-options="required:true,missingMessage:'会员押金金额不能为空',precision:0,groupSeparator:',',max:1000000000">
		    			</td>
		    		</tr>
                    <tr>
                        <td>透支次数:</td>
                        <td>
                            <input class="easyui-numberbox"  name="deposit_number" id="deposit_number" style="width:100%;height:24px" data-options="required:true,missingMessage:'会员透支次数不能为空'">
                        </td>
                    </tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>