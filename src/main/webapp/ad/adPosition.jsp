<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>广告位管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/datagrid-detailview.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    var area_code = $("input[name='_city_code']").val();
			    var adpname = $("input[name='adpname']").val();
			    var adpstatus = $("input[name='adpstatus']").val();
			    var adppage = $("input[name='adppage']").val();
				if(event.keyCode==13){
			  		$('#dataGrid').datagrid('load',{
			           area_code:area_code,
			           adpname:adpname,
			           adpstatus:adpstatus,
			           adppage:adppage,
			           queryDateFrom:queryDateFrom,
			           queryDateTo:queryDateTo
			        });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var area_code = $("input[name='_city_code']").val();
			    var adpname = $("input[name='adpname']").val();
			    var adpstatus = $("input[name='adpstatus']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    var adppage = $("input[name='adppage']").val();
			    $('#dataGrid').datagrid('load',{
		    	   area_code:area_code,
			       adpname:adpname,
			       adpstatus:adpstatus,
			       adppage:adppage,
		           queryDateFrom:queryDateFrom,
		           queryDateTo:queryDateTo
	          	});
			});
			
			
			$('#dataGrid').datagrid( {
				title : '广告位管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				pagination : true,
				rownumbers : true,
				url : 'ad_position_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				view: detailview,
				columns : [ [ 
				{
					field : 'ck',
					checkbox:true
				}, {
					field : 'name',
					title : '广告位名称',
					width : '20%',
					align : 'center'
				},  {
					field : 'page',
					title : '广告位置',
					width : '10%',
					align : 'center'
				},{
					field : 'describe',
					title : '广告位描述',
					width : '20%',
					align : 'center',
					
				}, {
					field : 'isShow',
					title : '广告位状态',
					width : '10%',
					align : 'center',
					formatter : function(val, rec) {
						if(val == 1)
					       return "启用";
					    else if(val == 0)
					       return "禁用";
					    else if(val == "null")
					       return "";
					    else
					    	return val;
					}
				},
				{
					field : 'cityName',
					title : '所属地区',
					width : '10%',
					align : 'center'
					
				}, {
					field : 'positionNo',
					title : '位置编号',
					width : '10%',
					align : 'center'
				},   {
					field : 'createTime',
					title : '创建时间',
					width : '16%',
					align : 'center'
				}
				] ],
		        detailFormatter:function(index,row){
		            return "<div style='padding:2px'><table class='ddv'></table></div>";
		        },
		        onExpandRow: function(index,row){
		            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
		            ddv.datagrid({
		                url:'ad_base_preview.do?id='+row.id,
		                fitColumns:true,
		                loadMsg:'',
		                height:'auto',
		                columns:[[
		                    {
								field : 'title',
								title : '广告名称',
								width : '20%',
								align : 'center'
								
							},{
								field : 'pointName',
								title : '投放点名称',
								width : '20%',
								align : 'center',
								formatter : function(val, rec) {
								    if(val == "null")
								       return "";
								    else
								    	return val;
								}
							}, 
							{
								field : 'isShow',
								title : '广告状态',
								width : '15%',
								align : 'center',
								formatter : function(val, rec) {
									if(val == 1)
								       return "启用";
								    else if(val == 0)
								       return "禁用";
								    else if(val == "null")
								       return "";
								    else
								    	return val;
								}
							}, {
								field : 'simpleContext',
								title : '广告简介',
								width : '25%',
								align : 'center'
							}, {
								field : 'createTime',
								title : '创建时间',
								width : '20%',
								align : 'center'
							}
		                ]],
		                onResize:function(){
		                    $('#dataGrid').datagrid('fixDetailRowHeight',index);
		                },
		                onLoadSuccess:function(){
		                    setTimeout(function(){
		                        $('#dataGrid').datagrid('fixDetailRowHeight',index);
		                    },0);
		                }
		            });
		            $('#dataGrid').datagrid('fixDetailRowHeight',index);
		    	}
				
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加广告位");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
				var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的广告位", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#add_adppage").combobox("setValue",JsonData.page);
					$("#add_adpname").textbox("setValue",JsonData.name);
					$("#add_adstatus").combobox("setValue",JsonData.isShow);
					$("#add_adarea").combobox("setValue",JsonData.cityCode);
					$("#add_adpdescribe").textbox("setValue",JsonData.describe);
					$("#add_position_no").numberspinner("setValue",JsonData.positionNo);
					
					$("#addView").dialog("open").dialog("setTitle", "编辑广告位");
		  		}
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "450",
				height : "450",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "ad_position_saveOrUpdate.do",
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
					$.messager.alert("提示", "请选择要删除的广告位", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("ad_position_del.do",{"id":JsonData.id},function(data){
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
			
			//推送车载
			$("#btnPush").bind("click",function(){
		      		var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要推送的车载广告位", "error");
		   		}else{
					var JsonData = selectedRow[0];
					if("car" == JsonData.page){
						$.messager.confirm("提示","确定要推送吗?",function(r){
				      		if(r){
								$.post("ad_position_push_car.do",{"cityCode":JsonData.cityCode},function(data){
									if(data=="success"){
									    $.messager.alert("提示", "推送成功", "info");
									    //$("#dataGrid").datagrid("reload");
									}else{
										$.messager.alert("提示", "推送失败", "info");
									}
								},"text");
							}
						});
					}else{
						$.messager.alert("提示", "请选择车载广告位", "error");
					}
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
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
        	所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			广告位置：<input class="easyui-combobox" name="adppage" id="adppage" style="width:80px;" 
		    			data-options=" url:'sys_dic?dicCode=AD_POSITION',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false">
			广告位名称:<input id="adpname"  name="adpname" class="easyui-textbox"/>
			广告位状态:<select class="easyui-combobox" id="adpstatus" name="adpstatus"  style="width:80px;" data-options="editable:false,panelHeight:'auto'"> 
		             <option value="">全部</option>
		             <option value="1">启用</option>
		             <option value="0">禁用</option>
		          </select>
<!--        		时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox"/> -->
<!--           	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox"/> -->
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            
            <r:FunctionRole functionRoleId="add_advertisement_position">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_advertisement_position">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_advertisement_position">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="push_car_load">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-slider_no_pointy_thing" plain="true" id="btnPush">推送车载</a>
            </r:FunctionRole>
            
	     </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 450px;height: 450px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
					<tr>
		    			<td>广告位名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="add_adpname" name="add_adpname" style="width:100%;height:32px" data-options="required:true,missingMessage:'广告位名称不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>所属地区:</td>
		    			<td>
		    			<input class="easyui-combobox" name="add_adarea" id="add_adarea" style="width:100%;height:32px" 
					data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>广告位置:</td>
		    			<td>
		    			<input class="easyui-combobox" name="add_adppage" id="add_adppage" style="width:100%;height:32px" 
		    			data-options="url:'sys_dic?dicCode=AD_POSITION',method:'get',valueField:'code',editable:false,textField:'name',panelHeight:'auto',required:true">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>位置编号:</td>
		    			<td>
		    			<input class="easyui-numberspinner"  id="add_position_no" name="add_position_no" style="width:100%;height:32px" data-options="required:true,min:1,max:1000,missingMessage:'位置编号'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>广告位状态:</td>
		    			<td>
		    			<select class="easyui-combobox" name="add_adstatus" id="add_adstatus" style="width:100%;height:32px" data-options="required:true,editable:false,panelHeight:'auto'">
							<option value="1">启用</option>
				            <option value="0">禁用</option>
				        </select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>广告位描述:</td>
		    			<td>
		    			<input class="easyui-textbox" data-options="multiline:true" name="add_adpdescribe" id="add_adpdescribe" style="width:100%;height:100px">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>