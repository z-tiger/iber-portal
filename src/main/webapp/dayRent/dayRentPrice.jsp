<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>日租价格管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/datagrid-detailview.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		var json = "";
		var jsonU = "";
		var weekP="";
		var modelIdUpdate="";
		var cityCodeUpdate="";
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    var car_type_id = $("input[name='_car_type_id']").val();
			    var city_code = $("input[name='_city_code']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           car_type_id : car_type_id,
			           city_code :city_code,
			           queryDateFrom:queryDateFrom,
			           queryDateTo:queryDateTo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    var car_type_id = $("input[name='_car_type_id']").val();
			    var city_code = $("input[name='_city_code']").val();
			    $('#dataGrid').datagrid('load',{
		           car_type_id : car_type_id,
		           city_code :city_code,
		           queryDateFrom:queryDateFrom,
		           queryDateTo:queryDateTo
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '日租价格管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dayRent_price_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox:true
				},{
					field : 'cityName',
					title : '所属地区',
					width : $(this).width() * 0.1,
					align : 'center'
				},{
					field : 'modelName',
					title : '车型',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'ordinaryCoefficient',
					title : '平时系数',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							return val;
						}
					}
				}, {
					field : 'weekedCoefficient',
					title : '周末系数',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							return val;
						}
					}
				},
				{
					field : 'festivalCoefficient',
					title : '法定节假日系数',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							return val;
						}
					}
				}, {
					field : 'basePrice',
					title : '基础价格(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
						}
					}
				}, {
					field : 'insurancePrice',
					title : '基本保险费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
						}
					}
				},
				{
					field : 'procedurePrice',
					title : '手续费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
						}
					}
				}, {
					field : 'freeCompensationPrice',
					title : '商业保险(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
						}
					}
				},
				{
					field : 'timeoutPrice',
					title : '超时费用(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
						}
					}
				},
				{
					field : 'remotePrice',
					title : '异地还车费(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
				    	   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    	   return n.replace(re, "$1,");
						}
					}
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.12,
					align : 'center'
				},{  
					field : 'id',   
					title : '价格日历',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val,row,index) {
					    return "<a href=\"javascript:showPrice("+row.id+",'"+row.cityName+"','"+row.modelName+"')\" >查看</a>";
					}
				}] ],
				pagination : true,
				rownumbers : true
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加日租价格");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的日租价格", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#city_code").combobox("setValue",JsonData.cityCode);
					if(JsonData.pointId!=0){
						$("#model_id").combogrid("setValue",JsonData.modelId);
					}
					$("#ordinary_coefficient").numberbox("setValue",JsonData.ordinaryCoefficient);
					$("#weeked_coefficient").numberbox("setValue",JsonData.weekedCoefficient);
					$("#festival_coefficient").numberbox("setValue",JsonData.festivalCoefficient);
					$("#base_price").numberbox("setValue",JsonData.basePrice/100);
					$("#insurance_price").numberbox("setValue",JsonData.insurancePrice/100);
					$("#procedure_price").numberbox("setValue",JsonData.procedurePrice/100);
					$("#free_compensation_price").numberbox("setValue",JsonData.freeCompensationPrice/100);
					$("#timeout_price").numberbox("setValue",JsonData.timeoutPrice/100);
					$("#remote_price").numberbox("setValue",JsonData.remotePrice/100);
					
					$("#addView").dialog("open").dialog("setTitle", "编辑日租价格");
		  		}	     
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "500",
				height : "500",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "dayRent_price_saveOrUpdate.do",
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
					$.messager.alert("提示", "请选择要删除的日租价格", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("dayRent_price_del.do",{"id":JsonData.id},function(data){
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
				$("#model_id").combogrid("clear");
			});
			
			//生成价格
			$("#btnPrice").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要生成的价格策略", "error");
		   		}else{
					clearPriceAddForm();
				   	var JsonData = selectedRow[0];
					$("#add_id").val(JsonData.id);
					$("#add_cityName").textbox("setValue",JsonData.cityName);
					$("#add_modelName").textbox("setValue",JsonData.modelName);
					var myDate = new Date();
					var year = myDate.getFullYear();
					var str = "<select class='easyui-combobox' name='add_year' id='add_year' style='width:100%;height:24px'>";
   				    for(var i=year;i<year+5;i++){
   				       str += "<option value='"+i+"'>"+i+"</option>"
   				    }
   				    str += "</select>"
					$("#add_year_td").html(str);	
					$("#priceAddView").dialog("open").dialog("setTitle", "生成价格");
		  		}	     
			});
			
			//构造对话框
			$("#priceAddView").dialog( {
				width : "350",
				height : "250",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#priceAddForm").form("submit", {
							url : "dayRent_price_add.do",
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
									$("#priceAddView").dialog("close");
								}else{
									$.messager.alert("提示", "保存失败", "info");
								    $("#dataGrid").datagrid("reload");
									$("#priceAddView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#priceAddView").dialog("close");
					}
				}]
			});
			
			//构造对话框
			$("#showPriceView").dialog( {
				width : "500",
				height : "500",
				top : "30"
			});
			
			$("#myCalendar").calendar({
				onSelect: function(date){
					var dateUpdate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
					clearUpdatePriceForm();
					$("#modelIdUpdate").val(modelIdUpdate);
					$("#cityCodeUpdate").val(cityCodeUpdate);
					$("#dateUpdate").textbox("setValue",dateUpdate);
					$("#updatePriceView").dialog("open").dialog("setTitle",dateUpdate+" 价格修改");
				}
			});
			
			//构造对话框
			$("#updatePriceView").dialog( {
				width : "350",
				height : "250",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#updatePriceForm").form("submit", {
							url : "dayRent_price_update.do",
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
								var rs = $.parseJSON(result);
								jsonU = rs.data;
								if(rs.msg  == "success"){
									$("#myCalendar").calendar({
										formatter:formatDay
									});
									$("#updatePriceView").dialog("close");
								}else{
									$.messager.alert("提示", "当前日期价格未生成", "info");
									$("#updatePriceView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#updatePriceView").dialog("close");
					}
				}]
			});
			
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearPriceAddForm(){
			$('#priceAddForm').form('clear');
		}
		function clearUpdatePriceForm(){
			$('#updatePriceForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		
		
		function showPrice(id,cityName,modelName){
			json = "";
			jsonU = "";
			weekP="";
			modelIdUpdate="";
			cityCodeUpdate="";
			$("#showPriceView").dialog("open").dialog("setTitle", cityName+" "+modelName+" 价格日历");
			$.post("dayRent_price_detail.do",{"id":id},function(data){
				var rs = $.parseJSON(data);
				if(rs.msg  == "success"){
					json = rs.data;
					if(""!=json){
						weekP=json[0].actualPrice;
						modelIdUpdate=json[0].modelId;
						cityCodeUpdate=json[0].cityCode;
					}
					$("#myCalendar").calendar({
						formatter:formatDay
					});
				}else{
					$.messager.alert("提示", "获取价格失败", "info");
				}
				
			},"text");
		}
		function formatDay(date){
			var y = date.getFullYear();
			var m = date.getMonth()+1;
			var d = date.getDate();
			var opts = $("#myCalendar").calendar("options");
			for(var i=0;i<json.length;i++){
				var priceDate = new Date(json[i].currDate);
				var py = priceDate.getFullYear();
				var pm = priceDate.getMonth()+1;
				var pd = priceDate.getDate();
				var pw = priceDate.getDay();
				var price = json[i].actualPrice;
				if (y == py && opts.month == m && m ==pm && d == pd){
					if(pw == 0){
						weekP = price;
					}
					
					if("" != jsonU){
						if(jsonU.currDate == json[i].currDate && jsonU.cityCode == json[i].cityCode && jsonU.modelId == json[i].modelId ){
							price = jsonU.actualPrice;
						}
					}
					
					if(price>weekP){
						return "<div class='md'>" + d + "<br/>￥" + (price/100).toFixed(2) + "</div>";
					}else{
						return d + "<br/>￥" + (price/100).toFixed(2);
					}
				}
			}
			return d+"<br/>";
		}
	</script> 
	<style scoped="scoped">
		.md{
			color:blue;
		}
	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">

        <form id="queryForm" method="post" action="#" enctype="multipart/form-data" style="height:15px">
        	所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			车辆型号:<input class="easyui-combogrid" name="_car_type_id" id="_car_type_id" 
							data-options="
								editable:false,
								panelHeight: 'auto',
								panelWidth: 300,
								idField: 'id',
								textField: 'type',
								url : 'car_type_list.do',
								pageSize:10,
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
       		时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width: 100px;" />
          	到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width: 100px;" />
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
          
            <r:FunctionRole functionRoleId="add_day_rent_calculate_fee_plot">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_day_rent_calculate_fee_plot">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_add_day_rent_calculate_fee_plot">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="generate_price">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnPrice">生成价格</a>
            </r:FunctionRole>
           
        </form>
         
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
					<tr>
		    			<td>所属区域:</td>
		    			<td>
		    			<input class="easyui-combobox" name="city_code" id="city_code" style="width:100%;height:22px" 
							data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>车辆型号:</td>
		    			<td>
		    			<input class="easyui-combogrid" name="model_id" id="model_id" style="width:85%;height:22px" 
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
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:12%;" title="清空"></a>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>平时系数:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="ordinary_coefficient" id="ordinary_coefficient" style="width:100%;height:22px" data-options="required:true,missingMessage:'平时系数不能为空',precision:2">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>周末系数:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="weeked_coefficient" id="weeked_coefficient" style="width:100%;height:22px" data-options="required:true,missingMessage:'周末系数不能为空',precision:2">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>法定节假日系数:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="festival_coefficient" id="festival_coefficient" style="width:100%;height:22px" data-options="required:true,missingMessage:'法定节假日系数不能为空',precision:2">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>基础价格:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="base_price" id="base_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'基础价格不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>基本保险费:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="insurance_price" id="insurance_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'基本保险费不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>手续费:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="procedure_price" id="procedure_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'手续费不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>商业保险:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="free_compensation_price" id="free_compensation_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'商业保险不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>超时费用:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="timeout_price" id="timeout_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'超时费用不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>异地还车费:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="remote_price" id="remote_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'异地还车费不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 <!-- priceAdd -->
         <div id="priceAddView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="priceAddForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="add_id" id="add_id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 30px;">
					<tr>
		    			<td>所属区域:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="add_cityName" id="add_cityName" style="width:100%;height:24px" data-options="required:true,missingMessage:'区域不能为空'" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>车辆型号:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="add_modelName" id="add_modelName" style="width:100%;height:24px" data-options="required:true,missingMessage:'车辆型号不能为空'" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>生成年份:</td>
		    			<td id="add_year_td">
		    			<select class="easyui-combobox" name="add_year" id="add_year" style="width:100%;height:24px">
						</select>
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 <div id="showPriceView" class="easyui-dialog" closed="true" style="width: 500px;height: 500px;" align="center">
		 	<div class='easyui-calendar' id='myCalendar' style='width:100%;height:100%;' data-options=''></div>
		 </div>
		 
		 <!-- updatePriceView -->
         <div id="updatePriceView" class="easyui-dialog" closed="true" style="width: 300px;height: 300px;">
			<form id="updatePriceForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="cityCodeUpdate" id="cityCodeUpdate" />
				<input type="hidden" name="modelIdUpdate" id="modelIdUpdate" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 30px;">
					<tr>
		    			<td>日期:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="dateUpdate" id="dateUpdate" style="width:100%;height:24px" data-options="required:true,missingMessage:'日期不能为空'" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>价格:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="actualPriceUpdate" id="actualPriceUpdate" style="width:100%;height:22px" data-options="required:true,missingMessage:'价格不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>