<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>计费策略管理</title>
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
			    var name = $("input[name='_name']").val();
			    var city_code = $("input[name='_city_code']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           name : name,
			           city_code : city_code
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var name = $("input[name='_name']").val();
			    var city_code = $("input[name='_city_code']").val();
			    $('#dataGrid').datagrid('load',{
		    	   name : name,
			       city_code : city_code
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '计费策略',
				width : 'auto',
				height : 'auto',
				fit:true,
				fitColumns: false,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'timeshare_rate_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				pageIndex:1,
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
				  {
					field : 'cityName',
					title : '地区',
					width : $(this).width() * 0.08,
					align : 'center'
					
				},{
					field : 'name',
					title : '策略名称',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return val;
					}
				}, {
					field : 'carTypeName',
					title : '车辆型号',
					width : $(this).width() * 0.08,
					align : 'center'
				} , {
					field : 'timeUnit',
					title : '时间单元',
					width : $(this).width() * 0.04,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return val;
					}
				}, 
				{
					field : 'timeRate',
					title : '日时间计费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},
				{
					field : 'nightTimeRate',
					title : '夜时间计费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},
				{
					field : 'timeDiscount',
					title : '折扣(日时间计费)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
							if(0>=val){
								return "无";
							}else{
						        n = (val/10).toFixed(1);
						        return n.replace(re, "$1,")+"折";
							}
						}
				},
				{
					field : 'timeDiscountRate',
					title : '折后价(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},
				{
					field : 'discountStartTime',
					title : '折扣开始时间',
					width : $(this).width() * 0.08,
					align : 'center'
				},
				{
					field : 'discountEndTime',
					title : '折扣结束时间',
					width : $(this).width() * 0.08,
					align : 'center'
				},
				{
					field : 'milesRate',   
					title : '日里程计费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},
				{
					field : 'nightMilesRate',   
					title : '夜里程计费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},
				
				/* 
				{  
					field : 'otherCost',   
					title : '其他费用',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return val/100;
					}
				},
				
				{
					field : 'discount',
					title : '折扣',
					width : $(this).width() * 0.2,
					align : 'center'
				},{
					field : 'timeDiscount',
					title : '时间折扣',
					width : $(this).width() * 0.2,
					align : 'center'
				},{
					field : 'milesDiscount',
					title : '里程折扣',
					width : $(this).width() * 0.2,
					align : 'center'
				}, */
				{
					field : 'maxFreeCompensationPrice',
					title : '最高商业保险(元/天)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},
				{
					field : 'freeCompensationPrice',
					title : '商业保险',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},{
					field : 'minConsump',
					title : '最低消费',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},{
					field : 'maxConsump',
					title : '最高消费',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
					}
				},{
					field : 'status',
					title : '启用状态',
					width : $(this).width() * 0.06,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "1"){
					       	return "是";
						}else if(val == "0"){
						   	return "否";
					    }else{
					    	return "";
					    }
					}
				}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加计费策略");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的计费策略", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#city_code").combobox("setValue",JsonData.cityCode);
					$("#name").textbox("setValue",JsonData.name);
					$("#description").textbox("setValue",JsonData.description);
					
					$("#time_unit").numberbox("setValue",JsonData.timeUnit);
					$("#time_rate").textbox("setValue",JsonData.timeRate/100);
					$("#miles_rate").textbox("setValue",JsonData.milesRate/100);
					$("#other_cost").numberbox("setValue",JsonData.otherCost/100);
					$("#discount").numberbox("setValue",JsonData.discount);
					
					if(0>=JsonData.timeDiscount){
						$("#time_discount").textbox("setValue","无");
					}else{
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					    n = (JsonData.timeDiscount/10).toFixed(1);
						$("#time_discount").textbox("setValue",n.replace(re, "$1,")+"折");
					}
					$("#miles_discount").numberbox("setValue",JsonData.milesDiscount);
					$("#min_consump").numberbox("setValue",JsonData.minConsump/100);
					$("#max_consump").numberbox("setValue",JsonData.maxConsump/100);
					
					$("#status").combobox("setValue",JsonData.status);
					$("#car_type_id").combogrid("setValue",JsonData.carTypeId);
					$("#freeCompensationPrice").textbox("setValue",JsonData.freeCompensationPrice/100);
					
					$("#dis_start_time").datetimebox("setValue",JsonData.discountStartTime); 
					$("#dis_end_time").datetimebox("setValue",JsonData.discountEndTime); 
					$("#timeDiscountRate").textbox("setValue",JsonData.timeDiscountRate/100);
					
					$("#max_free_compensation_price").textbox("setValue",JsonData.maxFreeCompensationPrice/100);
					$("#night_time_rate").textbox("setValue",JsonData.nightTimeRate/100);
					$("#night_miles_rate").textbox("setValue",JsonData.nightMilesRate/100);
					
					$("#addView").dialog("open").dialog("setTitle", "修改计费策略");
		  		}	     
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "630",
				height : "430",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "timeshare_rate_saveOrUpdate.do",
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
								}else if(result == "timeError"){
									$.messager.alert("提示", "折扣开始时间不能晚于结束时间", "info");
								}
								else{
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
					$.messager.alert("提示", "请选择要删除的策略", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("timeshare_rate_del.do",{"id":JsonData.id},function(data){
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
				$("#car_type_id").combogrid("clear");
			});
			
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		$(function () {  
		     $("#timeDiscountRate").textbox({  
		          onChange: function (newValue, oldValue) {  
		             var timeRate= $("#time_rate").textbox("getValue");
					 var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					 n = (newValue/timeRate*10).toFixed(1);
					 var val = n.replace(re, "$1,")+"折";
					 $("#time_discount").textbox("setValue",val);
		          }  
		      });  
		 });
		$(function () {  
		     $("#time_rate").textbox({  
		          onChange: function (newValue, oldValue) {  
		             var timeDiscountRate= $("#timeDiscountRate").textbox("getValue");
					 var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					 n = (timeDiscountRate/newValue*10).toFixed(1);
					 var val = n.replace(re, "$1,")+"折";
					 $("#time_discount").textbox("setValue",val);
		          }  
		      });  
		 });
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			策略名称:<input id="_name"  name="_name" class="easyui-textbox"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
         	<r:FunctionRole functionRoleId="add_calculate_fee_plot">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_calculate_fee_plot">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_calculate_fee_plot">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
	        
	     </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
					<tr>
						<td>策略名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:22px" data-options="required:true,missingMessage:'策略名称不能为空'">
		    			</td>
		    			
		    			<td>所属区域:</td>
		    			<td>
		    			<input class="easyui-combobox" name="city_code" id="city_code" style="width:100%;height:22px" 
							data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    			
		    		</tr>
		    		<tr>
		    			<td>车辆型号:</td>
		    			<td>
		    			<input class="easyui-combogrid" name="car_type_id" id="car_type_id" style="width:85%;height:22px" 
							data-options="
								editable:false,
								panelHeight: 'auto',
								panelWidth: 300,
								idField: 'id',
								textField: 'type',
								url : 'car_type_list.do',
								pageSize:10,
								required:true,
								columns : [ [ 
								{
									field : 'type',
									title : '车辆型号',
									width : '80%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							">
							<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:12%;" title="清空"></a> -->
		    			</td>
		    			<td>时间单元(分钟):</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="time_unit" id="time_unit" style="width:100%;height:22px" data-options="required:true,missingMessage:'时间单元不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>日时间计费(元):</td>
		    			<td>
		    			<input class="easyui-textbox"  name="time_rate" id="time_rate" style="width:100%;height:22px" data-options="required:true,missingMessage:'时间计费不能为空'">
		    			</td>
		    		    <td>夜时间计费(元):</td>
		    			<td>
		    			<input class="easyui-textbox"  name="night_time_rate" id="night_time_rate" style="width:100%;height:22px" data-options="required:true,missingMessage:'时间计费不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>折后价(元):</td>
		    			<td>
		    			<input class="easyui-textbox"  name="timeDiscountRate" id="timeDiscountRate" style="width:100%;height:22px" data-options="required:true,missingMessage:'时间计费不能为空'">
		    			</td>
<%--		    		    <td>夜时间计费:</td>--%>
<%--		    			<td>--%>
<%--		    			<input class="easyui-textbox"  name="night_time_rate" id="night_time_rate" style="width:100%;height:22px" data-options="required:true,missingMessage:'时间计费不能为空'">--%>
<%--		    			</td>--%>
		    			<td>时间折扣:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="time_discount" id="time_discount" style="width:100%;height:22px" data-options="editable:false,required:true,missingMessage:'时间折扣不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>折扣期间:</td>
		    			<td>
		    			<input class="easyui-datetimebox"  name="dis_start_time" id="dis_start_time" style="width:100%;height:22px" data-options="required:true,missingMessage:'里程折扣不能为空',groupSeparator:','">
		    			</td><td>
		    			<input class="easyui-datetimebox"  name="dis_end_time" id="dis_end_time" style="width:100%;height:22px" data-options="required:true,missingMessage:'不能为空',groupSeparator:','">
		    			</td>
<%--		    			<td>折扣结束时间:</td>--%>
<%--		    			<td>--%>
<%--		    			<input class="easyui-datetimebox"  name="dis_end_time" id="dis_end_time" style="width:100%;height:22px" data-options="required:true,missingMessage:'最低消费不能为空',groupSeparator:','">--%>
<%--		    			</td>--%>
		    		</tr>
		    		<tr>
		    			<td>日里程计费(元):</td>
		    			<td>
		    			<input class="easyui-textbox"  name="miles_rate" id="miles_rate" style="width:100%;height:22px" data-options="required:true,missingMessage:'里程计费不能为空'">
		    			</td>
		    			
		    			<td>夜里程计费(元):</td>
		    			<td>
		    			<input class="easyui-textbox"  name="night_miles_rate" id="night_miles_rate" style="width:100%;height:22px" data-options="required:true,missingMessage:'里程计费不能为空'">
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>商业保险:</td>
		    			<td><input class="easyui-textbox"  name=freeCompensationPrice id="freeCompensationPrice" style="width:100%;height:22px" data-options="required:true,missingMessage:'商业保险不能为空'"></td>
		    			<td>商业保险上限(元/天):</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="max_free_compensation_price" id="max_free_compensation_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'其他费用不能为空',precision:0">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>折扣:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="discount" id="discount" style="width:100%;height:22px" data-options="required:true,missingMessage:'折扣不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>里程折扣:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="miles_discount" id="miles_discount" style="width:100%;height:22px" data-options="required:true,missingMessage:'里程折扣不能为空',groupSeparator:','">
		    			</td>
		    			<td>最低消费(元):</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="min_consump" id="min_consump" style="width:100%;height:22px" data-options="required:true,missingMessage:'最低消费不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>最高消费(元/天):</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="max_consump" id="max_consump" style="width:100%;height:22px" data-options="required:true,missingMessage:'最高消费不能为空',groupSeparator:','">
		    			</td>
		    			<td>启用状态:</td>
		    			<td>
		    			<input class="easyui-combobox" name="status" id="status" style="width:100%;height:22px"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false,required:true,missingMessage:'启用状态不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>描述:</td>
		    			<td colspan="3">
		    			<input class="easyui-textbox"  id="description" name="description" style="width:100%;height:22px" >
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>