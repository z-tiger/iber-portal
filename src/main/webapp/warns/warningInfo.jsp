<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>预警信息</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="warns/common.js"></script>	
	<script type="text/javascript" src="warns/warningInfo.js"></script>	
	
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
	<form id="dataForm" style="height: 14px;"> 		类型:
		<input class="easyui-combobox" name="item" id="s-item" style="width:200px;height:24px"
					data-options=" url:'warn_item',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		    车牌号：<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
			<label class="label" style="text-align: center;">日期:&nbsp;</label>
			<input id="beginTime" name="beginTime" class="easyui-datetimebox" style="width:160px;height:24px" data-options="required:true,missingMessage:'开始时间不能为空'"/>
			<label class="label" style="text-align: center;">至:</label>
			<input id="endTime" name="endTime" class="easyui-datetimebox" style="width:160px;height:24px" data-options="required:true,missingMessage:'结束时间不能为空'"/>	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnNewestQuery">最新查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnAllMarkRead">已读最新</a>
		    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnMarkRead">已读</a>
		    <r:FunctionRole functionRoleId="export_excel_warning_msg">
		    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
		    </r:FunctionRole>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear">清空</a></form>
		</div>	
      <!-- add -->
    <div id="addView" class="easyui-dialog" closed="true" style="width:600px;height:400px;padding:50px 50px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;width:350px">	    				    		
		    		
		    		<tr>
		    			<td>类型:</td>
		    			<td id="itemName"></td>
		    		</tr>
		    	   <tr>
		    			<td>时间:</td>
		    			<td id="createTime"></td>
		    		</tr>
		    		<tr>
		    			<td>状态:</td>
		    			<td id="status"></td>
		    		</tr>   	   
		    	   <tr>
		    			<td>预警信息:</td>
		    			<td >
			    			<input class="easyui-textbox"  name="warnContent" id="warnContent" style="width:100%;height:150px" data-options="multiline:true,required:true,missingMessage:'预警信息不能为空'">
		    			</td>
		    		</tr>
		    	</table>
			</form>
	</div>

         <!-- noChangeView -->
    <div id="noChangeView" class="easyui-dialog" closed="true" style="width:600px;height:400px;padding:50px 50px">
				<input type="hidden" name="nid" id="nid" />
				<table cellpadding="5" style="font-size: 12px;width:350px">	    				    		
		    		<tr>
		    			<td>类型:</td>
		    			<td id="nitemName"></td>
		    		</tr>	    		
		    	   <tr>
		    			<td>时间:</td>
		    			<td id="ncreateTime"></td>
		    		</tr>
		    		<tr>
		    			<td>状态:</td>
                        <td id="nstatus"></td>
		    		</tr>   	   
		    	   
		    	   <tr>
		    			<td>预警信息:</td>
		    			<td >
			    			<input class="easyui-textbox"  name="nwarnContent" id="nwarnContent" style="width:100%;height:150px" data-options="multiline:true,required:true" readonly="readonly" >
		    			</td>
		    		</tr>
		    	</table>
	</div>

</body>
</html>