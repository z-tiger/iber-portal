<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电桩类型管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var brand_name = $("input[name='_brand_name']").val();
				if(event.keyCode==13){
					$('#dataGrid').datagrid('load',{
			           brand_name : brand_name
		        	});
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var brand_name = $("input[name='_brand_name']").val();
			    $('#dataGrid').datagrid('load',{
		    	   brand_name : brand_name
	        	});
			});
			
			
			$('#dataGrid').datagrid( {
				title : '充电桩类型管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'charging_pile_type_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				pagination : true,
				rownumbers : true,
				columns : [ [ 
				{
					field : 'false',
					checkbox:true
				}, {
					field : 'brandName',
					title : '充电桩类型名称',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'pileType',
					title : '充电桩型号',
					width : $(this).width() * 0.14,
					align : 'center'
					
				},{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.12,
					align : 'center'
				}] ]
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
				//adPhoto.src = "";
				$("#adPhoto").attr("src", "");
			 	$("#addView").dialog("open").dialog("setTitle", "添加充电桩类型");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的充电桩类型", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#brand_name").textbox("setValue",JsonData.brandName);
					$("#pile_type").textbox("setValue",JsonData.pileType);
					$("#addView").dialog("open").dialog("setTitle", "编辑充电桩类型");
		  		}	     
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "400",
				height : "400",
				top : "0",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "charging_pile_type_saveOrUpdate.do",
							onSubmit : function() {
								$.messager.progress({
				                    text:"正在加载，请稍等！"
				                });
				                var flag = $(this).form("validate");
				                if(!flag){
				                	$.messager.progress('close'); 
				                }
								return flag;
							},
							success : function(result) {
								$.messager.progress('close'); 
								if (result == "success") {
									$.messager.alert("提示", "保存成功", "info");
								    $("#dataGrid").datagrid("reload");
									$("#addView").dialog("close");
								}else{
									$.messager.alert("提示", "保存失败", "info");
								    $("#dataGrid").datagrid("reload");
									$("#addView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#addView").dialog("close");
					}
				}]
			});
			
			//支持车辆品牌
			$("#btnBrand").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要支持的充电桩类型", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
				   	$("#typeId").val(JsonData.id);
					$("#brandView").dialog("open").dialog("setTitle", "支持车辆品牌");
					$("#brandGrid").datagrid( {
						width : 'auto',
						height : 'auto',
						fit : true,
						fitColumns : true,
						nowrap : true,
						striped : true,
						collapsible : true,
						rownumbers : true,
						singleSelect : true,
						url : 'charging_car_brand_list_by_type.do?typeId='+JsonData.id,
						pageSize : 20,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						columns : [ [ 
						{
							field : 'false',
							checkbox:true
						}, {
							field : 'brandName',
							title : '车辆品牌名称',
							width : $(this).width() * 0.3,
							align : 'center'
						}, {
							field : 'images',
							title : '车辆品牌图片',
							width : $(this).width() * 0.3,
							align : 'center',
							formatter:function(value, row, index){
							   if(value != ""){
							      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: absolute;'><img width='300'  src='"+value+"'/></div>";
							   }
							}
						}, {
							field : 'typeId',
							title : '充电支持',
							width : $(this).width() * 0.3,
							align : 'center',
							formatter : function(val, rec) {
								if(val != "null" && val != ""  && val!= null){
							       return "<font color='green'>是</font>";
								}else{
							       return "<font color='red'>否</font>";
								}
							}
						}] ]
					});
		  		}	     
			});
			
			//构造对话框
			$("#brandView").dialog( {
				top : "30"
			});
			
			//删除操作
			$("#btnRemove").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要删除的充电桩类型", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("charging_pile_type_del.do",{"id":JsonData.id},function(data){
								if(data=="success"){
									//$.messager.alert("提示", "删除成功", "info");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "删除失败", "info");
								}
							},"text");
						}
					});
		  		}	     
			});
			
			//设置支持
			$("#btnEditSetBrand").bind("click",function(){
		      	var selectedRow = $("#brandGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要设置的品牌", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要设为支持品牌吗?",function(r){
			      		if(r){
							$.post("charging_pile_type_set_brand.do",{"brandId":JsonData.id,"typeId":$("#typeId").val()},function(data){
								if(data=="success"){
								    $("#brandGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "设置失败", "info");
								}
							},"text");
						}
					});
		  		}	     
			});
			
			//取消支持
			$("#btnEditCancelBrand").bind("click",function(){
		      	var selectedRow = $("#brandGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要设置的品牌", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要设为不支持品牌吗?",function(r){
			      		if(r){
							$.post("charging_pile_type_cancel_brand.do",{"brandId":JsonData.id,"typeId":$("#typeId").val()},function(data){
								if(data=="success"){
								    $("#brandGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "设置失败", "info");
								}
							},"text");
						}
					});
		  		}	     
			});
			
			//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
			
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		function display(id){
			document.getElementById("box"+id).style.display="block"; 
		}
		function disappear(id){
			document.getElementById("box"+id).style.display="none"; 
		}
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto;">
	        <form id="queryForm" method="post" action="#" enctype="multipart/form-data" style="height:14px">
	        	充电桩品牌名称:<input id="_brand_name"  name="_brand_name" class="easyui-textbox" style="width:150px;"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	            


	            <r:FunctionRole functionRoleId="add_pile_type">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="update_pile_type">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="delete_pile_type">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="support_brand_management">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="btnBrand">支持品牌设置</a>
	            </r:FunctionRole>
	       </form>
        </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 450px;height: 450px;padding: 10px 100px 10px 100px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<div style="margin-bottom:10px">
					<div>充电桩品牌名称:</div>
					<input id="brand_name"  name="brand_name" class="easyui-textbox" style="width:100%;height:24px" data-options="required:true" />
				</div>
				<div style="margin-bottom:10px">
					<div>充电桩型号:</div>
					<input id="pile_type"  name="pile_type" class="easyui-textbox" style="width:100%;height:24px" data-options="required:true" />
				</div>
			</form>
		 </div>
         <!-- brand -->
         <div id="brandView" class="easyui-dialog" closed="true" style="width:800px;height:500px;padding-bottom: 25px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-application_windows_okay" plain="true" id="btnEditSetBrand">设置支持</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-application_windows_remove" plain="true" id="btnEditCancelBrand">取消支持</a>
			<table id="brandGrid"></table>
			<input type="hidden" name="typeId" id="typeId" />
		 </div>
	</body>
</html>