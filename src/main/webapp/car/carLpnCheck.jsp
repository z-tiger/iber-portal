<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>车牌审核</title>
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
				var lpn = $("input[name='_lpn']").val();
			    var status = $("input[name='_status']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        lpn : lpn,
	           		status : status,
	           		queryDateFrom : queryDateFrom,
	           		queryDateTo : queryDateTo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			    var lpn = $("input[name='_lpn']").val();
			    var status = $("input[name='_status']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
			    $('#dataGrid').datagrid('load',{
		    	    lpn : lpn,
	           		status : status,
	           		queryDateFrom : queryDateFrom,
	           		queryDateTo : queryDateTo
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '车牌绑定审核',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'car_lpn_check_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

// 				}, {
// 					field : 'cid',
// 					title : 'cid',
// 					width : $(this).width() * 0.2,
// 					align : 'center',
				},{
					field : 'lpn',
					title : '车牌号',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter : function(val) {
						if (val.indexOf("•") < 0){
							return val.substring(0,2) + "•" + val.substring(2);
						}else {
							return val;
						}
					}
				},{
					field : 'carStatus',
					title : '车辆状态',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "empty" || val == "" || val == null || val == "null" || val == "NULL") {return "闲置中"}
						else if(val == "repair") {return "维修中"}
						else if(val == "maintain") {return "维护中"}
						else {return "运营中"}
					}
				}, {
					field : 'status',
					title : '审核状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else if(val == "checkWait"){
							return "待审核";
						}else if(val == "checkSucc"){
							return "审核通过";
						}else if(val == "checkFail"){
							return "审核不通过";
						}else{
							return val;
						}
					}
				},{
					field : 'checkTime',
					title : '审核时间',
					width : $(this).width() * 0.15,
					align : 'center',
				},{
					field : 'checkUser',
					title : '审核人',
					width : $(this).width() * 0.1,
					align : 'center',
				},{
					field : 'remark',
					title : '备注',
					width : $(this).width() * 0.15,
					align : 'center',
				},{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.15,
					align : 'center',
				}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			//审核
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择待审核车牌", "error");
		   		}else{
		   			var JsonData = selectedRow[0];
			   		if(JsonData.status != "checkWait"){
			   			$.messager.alert("提示", "请选择待审核车牌", "error");
			   		}else{
			   			if(JsonData.carStatus != "repair" && JsonData.carStatus != "maintain"){
			   				$.messager.alert("提示", "该车辆不属于维修或维护车辆，确认审核请先修改车辆状态。", "error");
			   			}else{
							$.post("car_lpn_check_is_exist.do",{"lpn":JsonData.lpn},function(data){
								var isExist = "已绑定";
								if(data=="success"){
								    isExist = "未绑定";
								}
								clearForm();
								$("#id").val(JsonData.id);
								if (JsonData.lpn.indexOf("•")<0){
									$("#lpn").textbox("setValue",JsonData.lpn.substring(0,2)+"•"+JsonData.lpn.substring(2));
								}else {
									$("#lpn").textbox("setValue",JsonData.lpn);
								}
								
								$("#isExist").textbox("setValue",isExist);
								$("#addView").dialog("open").dialog("setTitle", "车牌审核");
								
							},"text");
			   			}
					}
		  		}	     
			});
			
			
		
			
			//构造对话框
			$("#addView").dialog( {
				width : "380",
				height : "300",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "car_lpn_check_checking.do",
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
			
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			
		});
	
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearToolbar(){
			$('#toolbar').form('clear');
		}
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
        
        <!-- <form id="queryForm" method="post" action="#" enctype="multipart/form-data" > -->
			车牌号:<input id="_lpn"  name="_lpn" class="easyui-textbox" style="width:100px"/>
	                    审核状态:<select class="easyui-combobox" name="_status" id="_status" style="width:100px;" data-options="editable:false,panelHeight:'auto'">
					 <option value=""></option>
		             <option value="checkWait">待审核</option>
		             <option value="checkSucc">审核通过</option>
		             <option value="checkFail">审核不通过</option>
		          </select>
			时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px"/>
          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            <r:FunctionRole functionRoleId="audit_lpn_bind">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_edit" plain="true" id="btnEdit">审核</a>
            </r:FunctionRole>
	        
         <!-- </form> -->
         <div>
         <div id="addView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
			<form id="addForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
		    		<tr>
		    			<td>车牌号:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="lpn" id="lpn" style="width:100%;height:24px" data-options="" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>是否绑定:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="isExist" id="isExist" style="width:100%;height:24px" data-options="" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>是否审核:</td>
		    			<td>
		    				<select class="easyui-combobox" name="status" id="status" style="width:100%;height:24px" data-options="editable:false,panelHeight:'auto',required:true">
					            <option value="checkSucc">审核通过</option>
					            <option value="checkFail">审核不通过</option>
					        </select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>备注:</td>
		    			<td>
		    				<input class="easyui-textbox" data-options="multiline:true"  name="remark" id="remark" style="width:100%;height:50px" data-options="">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		</div>
	</body>
</html>