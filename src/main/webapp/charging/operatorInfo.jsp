<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>电桩运营商管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//回车键查询
			$(document).keydown(function(event){
				var operatorId = $("input[name='operatorId']").val();
			    var operatorName = $("input[name='operatorName']").val();
			    var registerAddress = $("input[name='_registerAddress']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        operatorId : operatorId,
		           registerAddress:registerAddress,
		           operatorName  :  operatorName 
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			    var operatorId = $("input[name='operatorId']").val();
			    var operatorName = $("input[name='operatorName']").val();
			    var registerAddress = $("input[name='_registerAddress']").val();
			  
			    $('#dataGrid').datagrid('load',{
		           operatorId : operatorId,
		           registerAddress:registerAddress,
		            operatorName   : operatorName  
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '运营商管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dataListOperatorinfo.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},{
					field : 'name',
					title : '运营商名称',
					width : $(this).width() * 0.2,
					align : 'center'
				},   
				  {
					field : 'operatorId',
					title : '组织机构代码',
					width : $(this).width() * 0.15,
					align : 'center'
					
				}, 
				{
					field : 'registerAddress',
					title : '注册地址',
					width : $(this).width() * 0.15,
					align : 'center',
					
				},   
				  {
					field : 'runDivideInto',
					title : '运营分成比例（ % ）',
					width : $(this).width() * 0.1,
					align : 'center'
					
				}, {
					field : 'phone1',
					title : '联系电话1',
					width : $(this).width() * 0.1,
					align : 'center'
				} , {
					field : 'phone2',
					title : '联系电话2',
					width : $(this).width() * 0.1,
					align : 'center'
				},   
				  {
					field : 'url',
					title : '运营商接口url',
					width : $(this).width() * 0.28,
					align : 'center'
					
				},
				{
					field : 'remark',   
					title : '备注',
					width : $(this).width() * 0.3,
					align : 'center'
				}/* ,
				{  
					field : 'createTime',   
					title : '创建时间',
					width : $(this).width() * 0.2,
					align : 'center'
				},
				{
					field : 'createName',
					title : '创建人',
					width : $(this).width() * 0.15,
					align : 'center',
				},{
					field : 'updateTime',
					title : '更新时间',
					width : $(this).width() * 0.2,
					align : 'center',
				},
				{
					field : 'updateName',
					title : '更新人',
					width : $(this).width() * 0.15,
					align : 'center'	
				} */
				] ],
				pagination : true,
				rownumbers : true
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加运营商");
			 	$("#operatorId").textbox('textbox').attr('readonly',false);
			 	$("#name").textbox('textbox').attr('readonly',false);
			 	$("#registerAddress").textbox('textbox').attr('readonly',false);
			 	$("#url").textbox('textbox').attr('readonly',false);
			});
			
			
			
			//构造对话框
			$("#addView").dialog( {
				width : "400",
				height : "400",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "saveOrUpdateOperatorinfo.do",
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
			
			
			//删除操作
			$("#btnRemove").bind("click",function(){
		      		var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要删除的记录", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("deleteOperatorinfoById.do",{"id":JsonData.id},function(data){
								if(data=="success"){
									$.messager.alert("提示", "删除成功", "info");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "删除失败", "info");
								}
							},"text");
						}
					});
		  		}	     
			});

			//修改运营商
			$("#btnEdit").bind("click",function(){
			$("#viewForm").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要修改的记录", "error");
				}else{
						$("#id").val(selectedRows[0].id);
						$("#operatorId").textbox('setValue',selectedRows[0].operatorId); 
						$("#operatorId").textbox('textbox').attr('readonly',true);
						$("#name").textbox('setValue',selectedRows[0].name); 
						$("#name").textbox('textbox').attr('readonly',true);
						$("#url").textbox('setValue',selectedRows[0].url); 
						$("#url").textbox('textbox').attr('readonly',true);
						$("#phone1").textbox('setValue',selectedRows[0].phone1); 	
						$("#phone2").textbox('setValue',selectedRows[0].phone2); 
						$("#registerAddress").textbox('setValue',selectedRows[0].registerAddress);
						$("#registerAddress").textbox('textbox').attr('readonly',true);
						$("#remark").textbox('setValue',selectedRows[0].remark); 
						$("#runDivideInto").textbox('setValue',selectedRows[0].runDivideInto); 
						$("#addView").dialog({title:"修改运营商"});
						$("#addView").dialog("open");
				}
			});
			
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			
		});
		function clearToolbar(){
			$('#toolbar').form('clear');
		}
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		
	</script> 
	
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			运营商名称:<input id="operatorName"  name="operatorName" class="easyui-textbox" style="width:80px"/>
			注册地址:<input id="_registerAddress"  name="_registerAddress" class="easyui-textbox" style="width:80px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
			<r:FunctionRole functionRoleId="add_operator">
 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
 			</r:FunctionRole>
 			<r:FunctionRole functionRoleId="delete_operator">
 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
 			</r:FunctionRole>
 			<r:FunctionRole functionRoleId="update_operator">
 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
 			</r:FunctionRole>
         </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
					
		    		<tr>
		    			<td>组织机构代码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="operatorId" name="operatorId" style="width:100%;height:24px" data-options="required:true,missingMessage:'组织机构代码不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>运营商名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'运营商名称不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>运营商接口url:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="url" name="url" style="width:100%;height:24px" data-options="required:true,missingMessage:'运营商url不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>注册地址:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="registerAddress" name="registerAddress" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>运营分成比例:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="runDivideInto" name="runDivideInto" style="width:90%;height:24px" data-options="required:true,missingMessage:'运营分成比例不能为空'">&nbsp;%
		    			</td>
		    		</tr><tr>
		    			<td>联系电话1:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="phone1" name="phone1" style="width:100%;height:24px" data-options="required:true,missingMessage:'联系电话1不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>联系电话2:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="phone2" name="phone2" style="width:100%;height:24px" data-options="required:true,missingMessage:'联系电话2不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>备注:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="remark" name="remark" style="width:100%;height:60px"  data-options="multiline:true">
		    			</td>
		    		</tr>
		    	</table> 
		    	
		    	
			</form>
		 </div>
		    
   		
	</body>
</html>