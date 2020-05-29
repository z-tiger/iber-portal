<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
  <head>
  	<meta charset="utf-8">
    <title>自检项配置页面</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="car/selfCheckList.js"></script>
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
           <form id="queryCarSelfCheck">
           		所属城市:<input class="easyui-combobox" name="cityCode" id="cityCode"
						data-options=" url:'sys_optional_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'" style="width:100px">
		                    车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width:100px">
		                    订单号:<input type="text" name="orderId" class="easyui-textbox" id="orderId" style="width:150px">
		                    手机号码:<input type="text" name="phone" class="easyui-textbox" id="phone" style="width:100px">
				自检类型:<input class="easyui-combobox" id="itemType" name="itemType" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    panelHeight:'auto',
				                    data: [{
										label: '全部',
										value: ''
									},{
										label:'上车自检',
										value:'0'
									},{
										label:'下车自检',
										value:'1'
									}]"  style="width:100px;" >
				处理状态:<input class="easyui-combobox" name="status" id="status"
									data-options="
									valueField: 'value',
									textField: 'label',
									panelHeight:'auto',
									data: [{
										label: '全部',
										value: ''
									},{
										label: '未处理',
										value: '0',
										selected : true
									},{
										label: '已处理',
										value: '1'
									}]" style="width:100px"> 
				是否异常:<input class="easyui-combobox" name="exception" id="exceptionStatus"
									data-options="
									valueField: 'value',
									textField: 'label',
									panelHeight:'auto',
									data: [{
										label: '全部',
										value: ''
									},{
										label: '异常',
										value: '1',
									},{
										label: '正常',
										value: '2'
									}]" style="width:100px">
				<r:FunctionRole functionRoleId="select_carSelf_list">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
				</r:FunctionRole>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清除</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="btnEdit" onclick=";">处理</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="batchHandle" onclick=";">批处理</a>
				<r:FunctionRole functionRoleId="export_car_self">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
				</r:FunctionRole>
			</form>
		</div>
		  <!-- 审核 -->
		  <div id="addView" class="easyui-dialog" closed="true"
				style="padding:10px 20px">
		       <form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data" >
		       	   <input type="hidden" name="id" id="id" />
		       		<div class="fitem">
				       <lable>所属城市:</lable>
				       　       <input class="easyui-combobox" name="cityCode" id="s-cityCode"
							data-options=" url:'sys_optional_cityCombobox',
			                    method:'get',
			                    valueField:'id',
			                    textField:'text',
			                    panelHeight:'auto'" style="width:75%">
		       		</div>
		       		<br>
		       		<div class="fitem">
		       			<lable>车牌号码:</lable>
			            　		<input type="text" name="lpn" class="easyui-textbox" id="s-lpn" style="width:75%">
		       		</div>
		       		<br>
		       		<div class="fitem">
		       			<lable> 会员姓名:</lable>
			            　		<input type="text" name="name" class="easyui-textbox" id="s-name" style="width:75%">
		       		</div>
		       		<br>
		       		<div class="fitem">
		       			<lable>手机号码:</lable>
			            　		<input type="text" name="phone" class="easyui-textbox" id="s-phone" style="width:75%">
		       		</div>
		       		<br>
		       		<div class="fitem">
		       			<lable>反馈身份:</lable>
			            　 		<input type="text" name="memberType" class="easyui-textbox" id="s-memberType" style="width:75%">
		       		</div>
		       		<br>
		       		<div class="fitem">
		       			<lable>自检类型:</lable>&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="easyui-combobox" id="s-itemType" name="itemType" 
						           			data-options="
						                    valueField:'value',
						                    textField:'label',
						                    panelHeight:'auto',
						                    data: [{
												label: '全部',
												value: ''
											},{
												label:'上车自检',
												value:'0'
											},{
												label:'下车自检',
												value:'1'
											}]" style="width:75%">
		       		</div>
		       		<br>
		       		<div class="fitem">
		       			<lable>异常项目:</lable>&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="text" name="errItem" class="easyui-textbox" id="s-errItem" style="width:75%">
						<br />
						<ul id="photoList">
						</ul>
		       		</div>
		      </form>
  	 	</div>
  </body>
</html>
