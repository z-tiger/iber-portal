<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电价格管理</title>
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
			    var pile_type = $("input[name='_equipment_type']").val();
			    var city_code = $("input[name='_city_code']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           name : name,
			           pile_type : pile_type,
			           city_code : city_code
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var name = $("input[name='_name']").val();
				var pile_type = $("input[name='_equipment_type']").val();
			    var city_code = $("input[name='_city_code']").val();
			    $('#dataGrid').datagrid('load',{
		    	   name : name,
		    	   pile_type : pile_type,
			       city_code : city_code
		        });
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var name = $("input[name='_name']").val();
				var pile_type = $("input[name='_equipment_type']").val();
			    var city_code = $("input[name='_city_code']").val();
				$("#chargePriceForm").form("submit", {
					url : "export_chargePirce_report",
					onSubmit : function(param) {
						param.name = name;
						param.pile_type = pile_type;
						param.city_code = city_code;
					},
					success : function(result) {
					}
				});
		
			});
			$('#dataGrid').datagrid( {
				title : '充电价格',
				width : 'auto',
				height : 'auto',
				fit:true,
				fitColumns: true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'charging_pile_price_list.do',
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
					width : $(this).width() * 0.2,
					align : 'center'
					
				},{
					field : 'name',
					title : '网点名称',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return val;
					}
				}, {
					field : 'equipmentType',
					title : '桩型号',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter:function(val,rec){
						if(val==1){
							return "直流设备";
						}else if(val==2){
							return "交流设备";
						}else if(val==3){
							return "交直流一体设备";
						}else{
							return "未知";
						}
					}
				} , {
					field : 'chargingPrice',
					title : '充电费（元/度）',
					width : $(this).width() * 0.2,
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
					field : 'servicePrice',
					title : '服务费（元/度）',
					width : $(this).width() * 0.2,
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
					field : 'otherPrice',   
					title : '其他费用',
					width : $(this).width() * 0.2,
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
					field : 'discount',
					title : '折扣',
					width : $(this).width() * 0.2,
					align : 'center'
				},{
					field : 'status',
					title : '启用状态',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "1"){
					       	return "<font color='green'>是</font>";
						}else if(val == "0"){
						   	return "<font color='red'>否</font>";
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
				$('#park_id').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
				 	delay:500,// 1s延时查询
				 	editable : true,
					panelHeight: 'auto', 
					panelWidth: 300,
					idField: 'id',
					textField: 'name',
					url:'parkName_allByPage',
					pageSize:10,
					required:true,
					columns : [ [ {
						field : 'name',
						title : '网点名称',
						width : '80%',
						align : 'center'
					}] ],
					pagination : true,//是否分页
					rownumbers : true,//序号
					keyHandler : {
						query : function(parkName) { // 动态搜索处理
							$('#park_id').combogrid("grid").datagrid('options').queryParams = 
								JSON.parse('{"parkName":"' + parkName + '"}');
							// 重新加载
							$('#park_id').combogrid("grid").datagrid("reload");
							$('#park_id').combogrid("setValue",parkName);
							 
						}
					}
					});
				$("#park_id").combogrid("enable");
			 	$("#addView").dialog("open").dialog("setTitle", "添加充电价格");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的充电价格", "error");
		   		}else{
		   		debugger ;
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#city_code").combobox("setValue",JsonData.cityCode);
					
					$("#park_id").combogrid("disable");
					$("#park_id").combogrid("setValue",JsonData.name);
					$("#equipmentType").combobox("setValue",JsonData.equipmentType);
					$("#charging_price").textbox("setValue",JsonData.chargingPrice/100);
					$("#service_price").textbox("setValue",JsonData.servicePrice/100);
					$("#other_price").textbox("setValue",JsonData.otherPrice/100);
					$("#discount").textbox("setValue",JsonData.discount);
					$("#status").combobox("setValue",JsonData.status);
					$("#addView").dialog("open").dialog("setTitle", "编辑充电价格");
		  		}	     
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "650",
				height : "350",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "charging_pile_price_saveOrUpdate.do",
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
					$.messager.alert("提示", "请选择要删除的策略", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("charging_pile_price_del.do",{"id":JsonData.id},function(data){
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
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			
			//清空Combogrid
			$("#clearCombogrid").bind("click",function(){
				$("#park_id").combogrid("clear");
			});
			//清空Combogrid
			$("#clearPCombogrid").bind("click",function(){
				$("#equipmentType").combobox("clear");
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
        <form id="chargePriceForm">
			所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			网点名称:<input id="_name"  name="_name" class="easyui-textbox"/>
			桩型号:<input id="_equipment_type"  name="_equipment_type" class="easyui-combobox" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '直流设备',
							value: '1'
						},{
							label: '交流设备',
							value: '2'
						},{
							label: '交直流一体设备',
							value: '3'
						}],
	                    panelHeight:'auto'"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            <r:FunctionRole functionRoleId="add_elec_price">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_elec_price">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_elec_price">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
           	<r:FunctionRole functionRoleId="export_chargingPilePrice">
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
	        </r:FunctionRole>
         </form>
         </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
					<tr>
		    			<td>所属区域:</td>
		    			<td colspan="3">
		    			<input class="easyui-combobox" name="city_code" id="city_code" style="width:100%;height:22px" 
							data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>网点:</td>
		    			<td colspan="3">
		    			<input class="easyui-combogrid" name="park_id" id="park_id" style="width:85%;height:22px" 
							/>
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:12%;" title="清空"></a>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>桩型号:</td>
		    			<td colspan="3">
		    			<input class="easyui-combobox" name="equipmentType" id="equipmentType" style="width:85%;height:22px" 
							data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '直流设备',
							value: '1'
						},{
							label: '交流设备',
							value: '2'
						},{
							label: '交直流一体设备',
							value: '3'
						}],
	                    panelHeight:'auto',required:true">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearPCombogrid" style="width:12%;" title="清空"></a>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>充电费（元/度）:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="charging_price" id="charging_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'充电费（元/度）不能为空',groupSeparator:','">
		    			</td>
		    			<td>服务费（元/度）:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="service_price" id="service_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'服务费（元/度）不能为空',precision:0">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>其他费用:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="other_price" id="other_price" style="width:100%;height:22px" data-options="required:true,missingMessage:'其他费用不能为空',precision:0">
		    			</td>
		    			<td>折扣:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="discount" id="discount" style="width:100%;height:22px" data-options="required:true,missingMessage:'折扣不能为空',groupSeparator:','">
		    			</td>
		    		</tr>
		    		
		    		<tr>
		    			<td>启用状态:</td>
		    			<td colspan="3">
		    			<input class="easyui-combobox" name="status" id="status" style="width:100%;height:22px"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false,required:true,missingMessage:'启用状态不能为空'">
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>