<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>调度任务</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="dispatch/common.js"></script>	
	<script type="text/javascript" src="dispatch/dispatchTask.js"></script>
	
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
			<label class="label" style="text-align: center;">日期:&nbsp;</label>
			<input id="beginTime" name="beginTime" class="easyui-datetimebox" style="width:160px;height:24px" data-options="required:true,missingMessage:'开始时间不能为空'"/>
			<label class="label" style="text-align: center;">至:</label>
			<input id="endTime" name="endTime" class="easyui-datetimebox" style="width:160px;height:24px" data-options="required:true,missingMessage:'结束时间不能为空'"/>
			<c:choose>
				<c:otherwise>
				  <label class="label" style="text-align: right;">状态:&nbsp;</label>
				  <input class="easyui-combobox" name="status" id="status"  data-options="valueField:'code',textField:'name',url:'sys_dic?dicCode=DISPATCH_STATUS',panelHeight:'80',panelWidth:'110'" style="width: 110px;" > 
				</c:otherwise>
			</c:choose>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		
			<r:FunctionRole functionRoleId="dispathJob_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="add_dispathJob">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="update_dispathJob">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
			</r:FunctionRole>
		    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear">清空</a>
            
	        			
	<!-- </form> -->
		</div>	
        
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width:600px;height:400px;padding:50px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;">				
				    <tr>
		    		   <td >执行人员:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="dispatcherId" id="dispatcherId" style="width:140px;height:36px" 
			    				data-options=" url:'dispatcher_list',
				                    method:'get',
				                    valueField:'memberId',
				                    textField:'memberName',
				                    panelHeight:'auto',
				                    editable:false,required:true,missingMessage:'执行人员不能为空'" />
		    			</td>
		    		
		    			<td>状态:</td>
		    			<td>
		    				<input class="easyui-combobox"  name="modStatus" id="modStatus" style="width:140px;height:36px" 
			    				data-options=" url:'sys_dic?dicCode=DISPATCH_STATUS',
				                    method:'get',
				                    valueField:'code',
				                    textField:'name',
				                    panelHeight:'auto',
				                    editable:false,required:true,missingMessage:'状态不能为空'" />
		    			</td>
		    		</tr>
		    		
		    		<tr>
						<td>订单编号:</td>
		    			<td colspan="3">
                        <input class="easyui-textbox"  name="orderId" id="orderId" style="width:330px;height:36px" >
		    			</td>
                        
		    		
		    		</tr>
		    		
		    		<tr>
		    			<td>调度信息:</td>
		    			<td colspan="3">
			    			<input class="easyui-textbox"  name="taskDesc" id="taskDesc" style="width:330px;height:100px" data-options="multiline:true,required:true,missingMessage:'调度信息不能为空'">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
</body>
</html>