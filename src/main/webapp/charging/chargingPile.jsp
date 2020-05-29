<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电桩管理</title>
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
			    var park_name = $("input[name='_park_name']").val();
			    var pile_type = $("input[name='_pile_type']").val();
			    var city_code = $("input[name='_city_code']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           park_name : park_name,
			           pile_type : pile_type,
			           city_code : city_code
		          });
				}
			});
			//导出充电桩excel
			$("#btnExport").bind("click", function() {
				var park_name = $("input[name='_park_name']").val();
			    var pile_type = $("input[name='_pile_type']").val();
			    var city_code = $("input[name='_city_code']").val();
				$("#queryForm").form("submit", {
					url : "export_pile_execl_report",
					onSubmit : function(param) {
						param.park_name = park_name;
						param.pile_type = pile_type;
						param.city_code = city_code;
					},
					success : function(result) {
					}
				});
		
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var park_name = $("input[name='_park_name']").val();
				var pile_type = $("input[name='_pile_type']").val();
			    var city_code = $("input[name='_city_code']").val();
			    $('#dataGrid').datagrid('load',{
		    	   park_name : park_name,
		    	   pile_type : pile_type,
			       city_code : city_code
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '充电桩管理',
				width : 'auto',
				height : 'auto',
				fit:true,
				fitColumns: true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'charging_pile_list.do',
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
				}, 
				{
					field : 'typeId',
					title : '桩型号Id',
					width : $(this).width() * 0.2,
					hidden : true
				} , 
				{
					field : 'pileType',
					title : '桩型号',
					width : $(this).width() * 0.2,
					align : 'center'
				} , {
					field : 'pileNo',
					title : '设备编号',
					width : $(this).width() * 0.2,
					align : 'center'
				}, 
				{
					field : 'carNo',
					title : '车位号',
					width : $(this).width() * 0.2,
					align : 'center'
				},
				{
					field : 'chargingType',   
					title : '充电类型',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "fast"){
					       	return "快充";
						}else if(val == "slow"){
						   	return "慢充";
					    }else{
					    	return val;
					    }
					}
				},
				{
					field : 'voltage',
					title : '电压',
					width : $(this).width() * 0.2,
					align : 'center'
				},
				{
					field : 'electricity',
					title : '电流',
					width : $(this).width() * 0.2,
					align : 'center'
				},
				{
					field : 'power',
					title : '功率',
					width : $(this).width() * 0.2,
					align : 'center'
				},
				{
					field : 'dimensionRule',
					title : '二维码',
					width : $(this).width() * 0.2,
					align : 'center'
				},
				{
					field : 'status',
					title : '状态',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "empty"){
					       	return "<font color='green'>空闲</font>";
						}else if(val == "handshake"){
						   	return "<font color='red'>握手</font>";
						}else if(val == "charging"){
						   	return "<font color='red'>充电</font>";
						}else if(val == "repair"){
						   	return "<font color='red'>维修</font>";
					    }else{
					    	return val;
					    }
					}
				},
				{
					field : 'isOnline',
					title : '是否在线',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "1"){
					       	return "<font color='green'>是</font>";
						}else if(val == "0"){
						   	return "<font color='red'>否</font>";
					    }else{
					    	return val;
					    }
					}
				},
				{
					field : 'pileCategory',
					title : '电桩所属',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "1"){
					       	return "<font color='green'>自有</font>";
						}else if(val == "2"){
						   	return "<font color='red'>外部</font>";
					    }else{
					    	return val;
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
			 	$("#addView").dialog("open").dialog("setTitle", "添加充电桩");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的充电桩", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#city_code").combobox("setValue",JsonData.cityCode);
					$("#charging_type").combobox("setValue",JsonData.chargingType);
					$("#park_id").combogrid("setValue",JsonData.parkId);
					$("#pile_id").combogrid("setValue",JsonData.typeId);
					$("#voltage").numberbox("setValue",JsonData.voltage);
					$("#electricity").numberbox("setValue",JsonData.electricity);
					$("#power").numberbox("setValue",JsonData.power);
					$("#status").combobox("setValue",JsonData.status);
					$("#is_online").combobox("setValue",JsonData.isOnline);
					$("#pile_category").combobox("setValue",JsonData.pileCategory);
					$("#car_no").textbox("setValue",JsonData.carNo);
					$("#pile_no").textbox("setValue",JsonData.pileNo);
					$("#dimension_rule").textbox("setValue",JsonData.dimensionRule);
					$("#addView").dialog("open").dialog("setTitle", "编辑充电桩");
		  		}	     
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "650",
				height : "400",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "charging_pile_saveOrUpdate.do",
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
							$.post("charging_pile_del.do",{"id":JsonData.id},function(data){
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
				$("#park_id").combogrid("clear");
			});
			//清空Combogrid
			$("#clearPCombogrid").bind("click",function(){
				$("#pile_id").combogrid("clear");
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
        <form id="queryForm" method="post" action="#" enctype="multipart/form-data" style="height:14px">
			所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			网点名称:<input id="_park_name"  name="_park_name" class="easyui-textbox"/>
			电桩型号:<input id="_pile_type"  name="_pile_type" class="easyui-textbox"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            
            <r:FunctionRole functionRoleId="add_pile">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_pile">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_pile">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="export_pile_execl">
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
		    			<td>
		    			<input class="easyui-combobox" name="city_code" id="city_code" style="width:100%;height:22px" 
							data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    			<td>充电类型:</td>
		    			<td>
		    			<input class="easyui-combobox" name="charging_type" id="charging_type" style="width:100%;height:22px"
							data-options=" url:'sys_dic?dicCode=CHARGING_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false,required:true,missingMessage:'充电类型不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>网点:</td>
		    			<td colspan="3">
		    			<input class="easyui-combogrid" name="park_id" id="park_id" style="width:85%;height:22px" 
							data-options="
								editable:false,
								panelHeight: 'auto',
								panelWidth: 380,
								idField: 'id',
								textField: 'name',
								url : 'park_list.do',
								pageSize:10,
								required:true,
								columns : [ [ 
								{
									field : 'name',
									title : '网点名称',
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
		    			<td>电桩型号:</td>
		    			<td colspan="3">
		    			<input class="easyui-combogrid" name="pile_id" id="pile_id" style="width:85%;height:22px" 
							data-options="
								editable:false,
								panelHeight: 'auto',
								panelWidth: 380,
								idField: 'id',
								textField: 'pileType',
								url : 'charging_pile_type_list.do',
								pageSize:10,
								required:true,
								columns : [ [ 
								{
									field : 'brandName',
									title : '桩品牌',
									width : '40%',
									align : 'center'
								},
								{
									field : 'pileType',
									title : '桩型号',
									width : '40%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearPCombogrid" style="width:12%;" title="清空"></a>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>电压:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="voltage" id="voltage" style="width:100%;height:22px" data-options="required:true,missingMessage:'电压不能为空',groupSeparator:','">
		    			</td>
		    			<td>电流:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="electricity" id="electricity" style="width:100%;height:22px" data-options="required:true,missingMessage:'电流不能为空',precision:0">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>功率:</td>
		    			<td>
		    			<input class="easyui-numberbox"  name="power" id="power" style="width:100%;height:22px" data-options="required:true,missingMessage:'电压不能为空',groupSeparator:','">
		    			</td>
		    			<td>电桩状态:</td>
		    			<td>
		    			<input class="easyui-combobox" name="status" id="status" style="width:100%;height:22px"
							data-options=" url:'sys_dic?dicCode=PILE_STATUS',
		                    method:'get',
		                    valueField:'code',
		                    textField:'name',
		                    panelHeight:'auto',
		                    editable:false,required:true,missingMessage:'电桩状态不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>是否在线:</td>
		    			<td>
		    			<input class="easyui-combobox" name="is_online" id="is_online" style="width:100%;height:22px"
							data-options=" url:'sys_dic?dicCode=YES_NO',
		                    method:'get',
		                    valueField:'code',
		                    textField:'name',
		                    panelHeight:'auto',
		                    editable:false,required:true,missingMessage:'是否在线不能为空'">
		    			</td>
		    			<td>电桩所属:</td>
		    			<td>
		    			<input class="easyui-combobox" name="pile_category" id="pile_category" style="width:100%;height:22px"
							data-options=" url:'sys_dic?dicCode=PILE_CATEGORY',
		                    method:'get',
		                    valueField:'code',
		                    textField:'name',
		                    panelHeight:'auto',
		                    editable:false,required:true,missingMessage:'电桩所属不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>车位号:</td>
		    			<td>
		    				<input id="car_no"  name="car_no" class="easyui-textbox" style="width:100%;height:22px"/>
		    			</td>
		    			<td>设备编号:</td>
		    			<td>
		    				<input id="pile_no"  name="pile_no" class="easyui-textbox" style="width:100%;height:22px"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>二维码:</td>
		    			<td colspan="3">
		    				<input id="dimension_rule"  name="dimension_rule" class="easyui-textbox" style="width:100%;height:22px"/>
		    			</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
	</body>
</html>