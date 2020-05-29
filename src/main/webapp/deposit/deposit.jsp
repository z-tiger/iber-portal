<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
  <head>
    <meta charset="UTF-8">
    <title>押金管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="version/common.js"></script>
	<script type="text/javascript" src="deposit/deposit.js"></script>
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
	            驾龄：<input class="easyui-combobox"  name="driverAge" id="driverAge" style="width:10%;height:25px" data-options="
		    		    valueField:'value',
	                    textField:'label',
	                    data: [{label: '与驾龄无关',
							value: '3'
						},{
							label: '少于一年（0<=驾龄<365）',
							value: '1'
						},{
							label: '驾龄大于一年',
							value: '2'
						}],
	                    panelHeight:'auto'"
		    			/>
		             芝麻信用：<input class="easyui-combobox"  name="sesameCredit" id="sesameCredit" style="width:10%;height:25px" data-options="
		    				 valueField:'value',
		    				  textField:'label',
	                    data: [{
							label: '与芝麻信用无关',
							value: '0'
						},{
							label: '<700',
							value: '1'
						},{
						   label: '>=700',
							value: '2'
						}],
	                    panelHeight:'auto'"
		    				/>
		    	会员等级：<input class="easyui-combobox"  name="memberLevel" id="memberLevel" style="width:10%;height:25px" data-options="
		    		    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '1星会员',
							value: '1'
						},{
							label: '2星会员',
							value: '2'
						},{
						   label: '3星会员',
							value: '3'
						},{
						   label: '4星会员',
							value: '4'
						},{
						   label: '5星会员',
							value: '5'
						}],
	                    panelHeight:'auto'"
		    				/>
		        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		        <r:FunctionRole functionRoleId="add_deposit">
		        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
		        </r:FunctionRole>
				<r:FunctionRole functionRoleId="update_deposit">
		        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
		        </r:FunctionRole>
		        <r:FunctionRole functionRoleId="delete_deposit">
		        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete">删除</a>
		        </r:FunctionRole>
		</div>	
        
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:500px;height:500px;padding:10px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;">
					<tr>
		    			<td>驾龄:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="driverAge" id="driverAge" style="width:100%;height:25px" data-options="
		    		    valueField:'value',
	                    textField:'label',
	                    required:true,
	                    data: [{label: '与驾龄无关',
							value: '3'
						},{
							label: '少于一年（0<=驾龄<365）',
							value: '1'
						},{
							label: '驾龄大于一年',
							value: '2'
						}],
	                    panelHeight:'auto'"
		    			/>
		    			  </td>
		    		</tr>
		    		<tr>
		    			<td>芝麻积分:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="sesameCredit" id="sesameCredit" style="width:100%;height:25px" data-options="
		    				 valueField:'value',
	                    textField:'label',
	                    required:true,
	                    data: [{
							label: '与芝麻信用无关',
							value: '0'
						},{
							label: '<700',
							value: '1'
						},{
						   label: '>=700',
							value: '2'
						}],
	                    panelHeight:'auto'"
		    				/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>会员等级:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="memberLevel" id="memberLevel" style="width:100%;height:25px" data-options="
		    		    valueField:'value',
	                    textField:'label',
	                    required:true,
	                    data: [{
							label: '1星会员',
							value: '1'
						},{
							label: '2星会员',
							value: '2'
						},{
						   label: '3星会员',
							value: '3'
						},{
						   label: '4星会员',
							value: '4'
						},{
						   label: '5星会员',
							value: '5'
						}],
	                    panelHeight:'auto'"
		    				/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>押金值:<label style="color: red;">￥</label></td>
		    			<td>
		    				<input class="easyui-textbox"  name="depositValue" id="depositValue" style="width:100%;height:25px" data-options="required:true" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>押金值描述:</td>
		    			<td>
			    			<input class="easyui-textbox"  name="detail" id="detail" style="width:100%;height:70px" data-options="multiline:true,required:true">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
  </body>
</html>
