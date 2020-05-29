<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>广告管理</title>
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
			    var is_show = $("input[name='_is_show']").val();
			    var adpid = $("input[name='_adpid']").val();
			    var title = $("input[name='_title']").val();
			    var area_code = $("input[name='_area_code']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           adpid:adpid,
			           is_show:is_show,
			           title:title,
			           area_code:area_code,
			           queryDateFrom:queryDateFrom,
			           queryDateTo:queryDateTo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    var area_code = $("input[name='_area_code']").val();
			    var is_show = $("input[name='_is_show']").val();
			    var adpid = $("input[name='_adpid']").val();
			    var title = $("input[name='_title']").val();
			    $('#dataGrid').datagrid('load',{
		           adpid:adpid,
		           is_show:is_show,
		           title:title,
		           area_code:area_code,
		           queryDateFrom:queryDateFrom,
		           queryDateTo:queryDateTo
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '广告管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'ad_base_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				pagination : true,
				rownumbers : true,
				view: detailview,
				columns : [ [ {
					field : 'ck',
					checkbox:true
					
				}, {
					field : 'cityName',
					title : '区域',
					width : $(this).width() * 0.08,
					align : 'center'
					
				}, {
					field : 'title',
					title : '广告名称',
					width : $(this).width() * 0.08,
					align : 'center'
					
				}, {
					field : 'adpName',
					title : '广告位名称',
					width : $(this).width() *  0.08,
					align : 'center'
				}, {
					field : 'pointName',
					title : '投放点名称',
					width : $(this).width() *  0.08,
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
					width : $(this).width() * 0.05,
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
					width : $(this).width() * 0.12,
					align : 'center'
				}, {
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.08,
					align : 'center'
				}] ],
				detailFormatter:function(index,row){
		            return "<div style='padding:2px'><table class='ddv'></table></div>";
		        },
		        onExpandRow: function(index,row){
		            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
		            ddv.datagrid({
		                url:'ad_attachment_preview.do?id='+row.id,
		                fitColumns:true,
		                loadMsg:'',
		                height:'auto',
		                columns:[[
		                	{
								field : 'attachName',
								title : '图片名称',
								width : '15%',
								align : 'center'
							}, 
					        {
								field : 'attachType',
								title : '图片类型',
								width : '10%',
								align : 'center',
								formatter : function(val, rec) {
									if(val == 1)
								       return "简图";
								    else if(val == 2)
								       return "详图";
								    else if(val == "null")
								       return "";
								    else
								    	return val;
								}
							}, {
								field : 'uploadUrl',
								title : '图片',
								width : '20%',
								align : 'center',
								formatter:function(value, row, index){
								   if(value != ""){
								      return "<img width='40' height='40' src='"+value+"' />";
								   }
								}
								
							}, {
								field : 'attachSize',
								title : '图片大小',
								width : '10%',
								align : 'center',
								
							},
							{
								field : 'isShow',
								title : '审核状态',
								width : '10%',
								align : 'center',
								formatter : function(val, rec) {
									if(val == 1)
								       return "审核通过";
								    else if(val == 0)
								       return "未审核";
								    else if(val == "null")
								       return "";
								    else
								    	return val;
								}
								
							},
							{
								field : 'linkUrl',
								title : '图片超链接',
								width : '20%',
								align : 'center',
								formatter : function(value, row, index) {
									if(value != "" && value != "null"){
								      return "<a href='"+value+"' target='_blank' title='浏览'>"+value+"</a>";
								    }else{
								   	   return "";
								    }
								}
							},
							{
								field : 'createTime',
								title : '创建时间',
								width : '15%',
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
			 	$("#addView").dialog("open").dialog("setTitle", "添加广告");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的广告", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#title").textbox("setValue",JsonData.title);
					if(JsonData.pointId!=0){
						$("#pointid").combogrid("setValue",JsonData.pointId);
					}
					$("#adpid").combogrid("setValue",JsonData.adPid);
					$("#is_show").combobox("setValue",JsonData.isShow);
					$("#simple_context").textbox("setValue",JsonData.simpleContext);
					
					$("#addView").dialog("open").dialog("setTitle", "编辑广告");
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
							url : "ad_base_saveOrUpdate.do",
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
					$.messager.alert("提示", "请选择要删除的广告", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("ad_base_del.do",{"id":JsonData.id},function(data){
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
			
				//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
			
			//清空Combogrid
			$("#clearCombogrid").bind("click",function(){
				$("#pointid").combogrid("clear");
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
 	    <div id="toolbar" style="height:auto">
        	所属地区:<input class="easyui-combobox" name="_area_code" id="_area_code" style="width: 80px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			广告名称:<input id="_title"  name="_title" class="easyui-textbox" style="width: 120px;" />
		          广告位名称：<input class="easyui-combogrid" name="_adpid" id="_adpid" style="width:300px;"  data-options="
								editable:false,
								panelHeight: 250,
								idField: 'id',
								textField: 'name',
								url : 'ad_position_list.do',
								pageSize:10,
								columns : [ [ 
								{
									field : 'cityName',
									title : '区域',
									width : '20%',
									align : 'center'
								},{
									field : 'name',
									title : '广告位名称',
									width : '30%',
									align : 'center'
								},{
									field : 'page',
									title : '广告位置',
									width : '30%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							" />
	 	           广告状态:<select class="easyui-combobox" name="_is_show" id="_is_show" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
					 <option value=""></option>
		             <option value="1">启用</option>
		             <option value="0">禁用</option>
		          </select>
       		时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width: 100px;" />
          	到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width: 100px;" />
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
           
            <r:FunctionRole functionRoleId="add_advertisement">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_advertisement">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_advertisement">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
	        
	        
         </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 500px;height: 500px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 40px;">
					<tr>
		    			<td>广告名称:</td>
		    			<td width="250px;"><input class="easyui-textbox" type="text" name="title" id="title" data-options="required:true,missingMessage:'广告名称不能为空'" style="width:100%;height:32px"></input></td>
		    		</tr>
		    		<tr>
		    			<td>所属广告位:</td>
		    			<td>
			    			<input class="easyui-combogrid" name="adpid" id="adpid" style="width:100%;height:32px" data-options="
								required:true,
								editable:false,
								panelHeight: 250,
								idField: 'id',
								textField: 'name',
								url : 'ad_position_list.do',
								pageSize:10,
								columns : [ [ 
								{
									field : 'cityName',
									title : '区域',
									width : '15%',
									align : 'center'
								},{
									field : 'name',
									title : '广告位名称',
									width : '30%',
									align : 'center'
								},{
									field : 'page',
									title : '广告位置',
									width : '25%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>选择投放点:</td>
		    			<td>
			    			<input class="easyui-combogrid" name="pointid" id="pointid" style="width:85%;height:32px" data-options="
								editable:false,
								panelHeight: 250,
								idField: 'id',
								textField: 'adPointName',
								url : 'ad_point_list.do',
								pageSize:10,
								pageIndex:1,
								columns : [ [ 
								{
									field : 'adPointName',
									title : '投放点名称',
									width : '80%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							" />
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:10%;" title="清空"></a>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>广告状态:</td>
		    			<td>
		    			<select class="easyui-combobox" name="is_show" id="is_show" id="is_show" style="width:100%;height:32px" data-options="required:true,editable:false,panelHeight:'auto'">
							<option value="1">启用</option>
				        	<option value="0">禁用</option>
				        </select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>简介:</td>
		    			<td><input class="easyui-textbox" data-options="multiline:true" name="simple_context" id="simple_context" data-options="" style="width:100%;height:100px"></input></td>
		    		</tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>