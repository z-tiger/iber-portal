<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>充电站管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<!--<script type="text/javascript" src="ui_lib/js/datagrid-detailview.js"></script> -->
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//回车键查询
			$(document).keydown(function(event){
			   	var address = $("input[name='_address']").val();
			    var name = $("#_name").combobox("getValue"); 
			    
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        address:address,
		           	name:name  
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			    var address = $("input[name='_address']").val();
			    var name = $("#_name").combobox("getValue"); 
			  
			    $('#dataGrid').datagrid('load',{
		           address:address,
		           name:name  
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '充电站管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dataListStationInfo.do',
				pageSize : 20,
				idField : 'id',
				pagination : true,
				rownumbers : true,
				/* view: detailview, */
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
				  {
					field : 'name',
					title : '充电站名称',
					align : 'center',
					width : $(this).width() * 0.08,
					
				},
				{
					field : 'operatorName',   
					title : '运营商名称',
					width : $(this).width() * 0.08,
					align : 'center'
				},
				{
					field : 'address',
					title : '详细地址',
					width : $(this).width() * 0.15,
					align : 'center'	
				},
				{
					field : 'stationPhone',
					title : '站点电话',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'servicePhone',
					title : '服务电话',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'parkNums',
					title : '车位数量',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'electricityFee',
					title : '充电电费率',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'serviceFee',
					title : '服务费率',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'parkFee',
					title : '停车费',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'equipmentOwnerId',
					title : '设备所属方id',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'matchCars',
					title : '使用车型描述',
					width : $(this).width() * 0.15,
					align : 'center'	
				},
				{
					field : 'parkInfo',
					title : '车位楼层及数量描述',
					width : $(this).width() * 0.15,
					align : 'center'	
				},
				{
					field : 'type',
					title : '站点类型',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'status',
					title : '站点状态',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "0")
					       return "启用";
					    else if(val =="1")
					       return "关闭";
					}	
				},
				{
					field : 'businessHours',
					title : '营业时间',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'payment',
					title : '支付方式',
					width : $(this).width() * 0.1,
					align : 'center'	
				},
				{
					field : 'isOrder',
					title : '是否支持预约',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "0")
					       return "支持";
					    else
					       return "不支持";
					}
				},
				{
					field : 'guide',
					title : '站点引导',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'construction',
					title : '建设场所',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'photo',
					title : '站点照片',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				/* {  
					field : 'equipmentBelongId',   
					title : '设备所属方id',
					width : $(this).width() * 0.08,
					align : 'center'
				}, */
				{
					field : 'countryCode',
					title : '充电站国家代码',
					width : $(this).width() * 0.1,
					align : 'center',
				},{
					field : 'cityCode',
					title : '充电站省市辖区编码',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'longitude',
					title : '经度',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'latitude',
					title : '纬度',
					width : $(this).width() * 0.08,
					align : 'center'	
				},{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'createName',
					title : '创建人',
					width : $(this).width() * 0.08,
					align : 'center'
				} , {
					field : 'updateTime',
					title : '更新时间',
					width : $(this).width() * 0.1,
					align : 'center'
				}, 
				{
					field : 'updateName',
					title : '更新人',
					width : $(this).width() * 0.08,
					align : 'center',
					
				},
				{
					field : 'remark',
					title : '备注',
					width : $(this).width() * 0.08,
					align : 'center'	
				}
				] ]/* ,
				detailFormatter:function(index,row){
		            return "<div style='padding:2px'><table class='ddv'></table></div>";
		        },
		        onExpandRow: function(index,row){
		            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
		            ddv.datagrid({
		                url:'equipment_attachment_preview.do?id='+row.id,
		                fitColumns:false,
		                loadMsg:'',
		                height:'auto',
		                width:'100%',
		                columns:[[
		                	{
								field : 'equipmentId',
								title : '设备编码',
								width : '5%',
								align : 'center'
							}, 
					        {
								field : 'manufacturerId',
								title : '设备生产商组织机构代码',
								width : '5%',
								align : 'center',
								
							}, {
								field : 'equipmentModel',
								title : '设备型号',
								width : '5%',
								align : 'center',
								
								
							}, {
								field : 'productionDate',
								title : '生产日期',
								width : '5%',
								align : 'center',
								
							},
							{
								field : 'equipmentType',
								title : '设备类型',
								width : '5%',
								align : 'center',
								formatter : function(val, rec) {
									if(val == 1)
								       return "直流设备";
								    else if(val == 2)
								       return "交流设备";
								    else if(val == 3)
								       return "交直流一体设备";
								    else
								    	return val;
								}
								
							},
							{
								field : 'equipmentLng',
								title : '充电设备经度',
								width : '5%',
								align : 'center',
								
							},
							{
								field : 'equipmentLat',
								title : '充电设备纬度',
								width : '5%',
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
		    	} */
				
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加充电站");
			 	$("#name").textbox('textbox').attr('readonly',false);
			 	$("#countryCode").textbox('textbox').attr('readonly',false);
			 /* 	$("#cityCode").textbox('textbox').attr('readonly',false); */
			 	$("#address").textbox('textbox').attr('readonly',false);
			});
			
			
			
			//构造对话框
			$("#addView").dialog( {
				width : "480",
				height : "610",
				top : "50",
				left:"400",
				modal:true,
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "saveOrUpdateStationInfo.do",
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
							$.post("deleteStationInfoById.do",{"id":JsonData.id},function(data){
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
			
				//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
				
			
			
			//修改运营商
			$("#btnEdit").bind("click",function(){
			$("#viewForm").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要修改的记录", "error");
				}else{
						$("#id").val(selectedRows[0].id);
						$("#name").textbox("setValue",selectedRows[0].name);
						$("#name").textbox('textbox').attr('readonly',true);
						$("#operatorId").textbox("setValue",selectedRows[0].operatorName);
					/* 	$("#operatorId").textbox('textbox').attr('readonly',true); */
						$("#countryCode").textbox("setValue",selectedRows[0].countryCode);
						$("#countryCode").textbox('textbox').attr('readonly',true);
					/* 	$("#cityCode").textbox("setValue",selectedRows[0].cityCode);
						$("#cityCode").textbox('textbox').attr('readonly',true); */
						$("#address").textbox("setValue",selectedRows[0].address);
						$("#address").textbox('textbox').attr('readonly',true);
						$("#stationPhone").textbox("setValue",selectedRows[0].stationPhone);
						$("#servicePhone").textbox("setValue",selectedRows[0].servicePhone);
						$("#parkNums").textbox("setValue",selectedRows[0].parkNums);
						$("#businessHours").textbox("setValue",selectedRows[0].businessHours);
						$("#longitude").textbox("setValue",selectedRows[0].longitude);
						$("#latitude").textbox("setValue",selectedRows[0].latitude);
						$("#guide").textbox("setValue",selectedRows[0].guide);
						$("#equipmentOwnerId").textbox("setValue",selectedRows[0].equipmentOwnerId);
						$("#photo").textbox("setValue",selectedRows[0].photo);
						$("#matchCars").textbox("setValue",selectedRows[0].matchCars);
						$("#parkInfo").textbox("setValue",selectedRows[0].parkInfo);
						$("#electricityFee").textbox("setValue",selectedRows[0].electricityFee);
						$("#serviceFee").textbox("setValue",selectedRows[0].serviceFee);
						$("#parkFee").textbox("setValue",selectedRows[0].parkFee);
						$("#payment").textbox("setValue",selectedRows[0].payment);
						$("#remark").textbox("setValue",selectedRows[0].remark);
						
						$("#status").combobox("setValue",selectedRows[0].status);
						$("#isOrder").combobox("setValue",selectedRows[0].isOrder);
						$("#type").combobox("setValue",selectedRows[0].type);
						$("#construction").combobox("setValue",selectedRows[0].construction);
						$("#addView").dialog({title:"修改运营商"});
						$("#addView").dialog("open");
				}
			});
			
			
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		
	</script> 
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
			充电站名称:<input id="_name"  name="_name" class="easyui-combobox" style="width:120px" 
						data-options=" url:'station_Combobox',
	                    method:'get',
	                    valueField:'text',
	                    textField:'text',
	                    panelHeight:'auto'">
			详细地址:<input id="_address"  name="_address" class="easyui-textbox" style="width:80px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
	        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
         </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<!-- <table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;"> -->
		    		<!-- <tr>
		    			<td>充电站名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'运营商id不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>充电站名称:</label> <input  id="name" name="name"  class="easyui-textbox" style="width: 25%;" 	required="true"  missingMessage="请填写充电站名称"/>
		  			
		    		<!-- <tr>
		    			<td>运营商id:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="operatorId" name="operatorId" style="width:100%;height:24px" data-options="required:true,missingMessage:'运营商名称不能为空'">
		    			</td>
		    		</tr> --> 
		    		<span style="margin-left: 15px;"></span>
		    			<label>国家代码:</label> <input  id="countryCode" name="countryCode"  class="easyui-textbox" style="width: 25%;" 	required="true"  missingMessage="请填写充电站国家代码"/>
						
		  			</div>
		  			<div class="fitem">
						<label>详细地址:</label> <input id="address" name="address"  class="easyui-textbox" style="width: 75%;" 	required="true"  missingMessage="请填写详细地址"/>
		  			</div>
		    		<!-- <tr>
		    			<td>充电站国家代码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="countryCode" name="countryCode" style="width:100%;height:24px" data-options="required:true,missingMessage:'联系电话1不能为空'">
		    			</td>
		    		</tr> -->
		    			<label>运营商组织机构代码:</label> <input id="equipmentOwnerId" name="equipmentOwnerId"  class="easyui-combobox" style="width: 67%;" 	required="true" 
		    			data-options=" url:'operatorId_Combobox',
		                    method:'get',
		                    valueField:'text',
		                    textField:'text',
		                    panelHeight:'auto'">
		    		<div class="fitem">
						
		  			
		    		<!-- <tr>
		    			<td>充电站辖区编码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="cityCode" name="cityCode" style="width:100%;height:24px" data-options="required:true,missingMessage:'联系电话2不能为空'">
		    			</td>
		    		</tr> -->
		    		<!-- <span style="margin-left: 15px;"></span>
						<label>辖区编码:</label> <input id="cityCode" name="cityCode"  class="easyui-textbox" style="width: 25%;" 	required="true"  missingMessage="请填写充电站辖区编码"/> -->
		  			</div>
		    		<!-- <tr>
		    			<td>详细地址:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="address" name="address" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		
		    		<!-- <tr>
		    			<td>站点电话:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="stationPhone" name="stationPhone" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">	
		  			<label>运营商名称:</label> <input id="operatorId" name="operatorId"  class="easyui-combobox" style="width: 25%;" 
		  				data-options=" url:'operatorName_Combobox',
		                    method:'get',
		                    valueField:'text',
		                    textField:'text',
		                    panelHeight:'auto'">	
		    		<!-- <tr>
		    			<td>服务电话:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="servicePhone" name="servicePhone" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<span style="margin-left: 15px;"></span>
						<label>服务电话::</label> <input id="servicePhone" name="servicePhone"  class="easyui-textbox" style="width: 25%;" />
		  			</div>
		    	<!-- 	<tr>
		    			<td>站点类型:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="type" name="type" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>站点类型:</label> <input id="type" name="type"  class="easyui-combobox" style="width: 25%;" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '公共',
							value: '1'
						},{
							label: '个人',
							value: '50'
						},{
							label: '公交（专用）',
							value: '100'
						},{
							label: '环卫（专用）',
							value: '101'
						},{
							label: '物流（专用）',
							value: '102'
						},{
							label: '出租车（专用）',
							value: '103'
						},{
							label: '其他',
							value: '255'
						}],
	                    panelHeight:'auto'">
		  			
		    	<!-- 	<tr>
		    			<td>站点状态:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="status" name="status" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<span style="margin-left: 15px;"></span>
						<label>站点状态:</label> <input id="status" name="status"  class="easyui-combobox" style="width: 25%;" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '未知',
							value: '0'
						},{
							label: '建设中',
							value: '1'
						},{
							label: '关闭下线',
							value: '5'
						},{
							label: '维护中',
							value: '6'
						},{
							label: '正常使用',
							value: '50'
						}],
	                    panelHeight:'auto'" />
		  			</div>
		    		<!-- <tr>
		    			<td>车位数量:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="parkNums" name="parkNums" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>车位数量:</label> <input id="parkNums" name="parkNums"  class="easyui-textbox" style="width: 25%;"/>
		  			
		  			<!-- <tr>
		    			<td>营业时间:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="businessHours" name="businessHours" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<span style="margin-left: 15px;"></span>
						<label>营业时间:</label> <input id="businessHours" name="businessHours"  class="easyui-textbox" style="width: 25%;"/>
		  			</div>
		    		<!-- <tr>
		    			<td>经度:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="longitude" name="longitude" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>经度:</label> <input id="longitude" name="longitude"  class="easyui-textbox" style="width: 25%;"/>
		  			
		    		<!-- <tr>
		    			<td>纬度:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="latitude" name="latitude" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<span style="margin-left: 15px;"></span>
						<label>纬度:</label> <input id="latitude" name="latitude"  class="easyui-textbox" style="width: 25%;"/>
		  			</div>
		    		<!-- <tr>
		    			<td>站点引导:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="guide" name="guide" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>站点引导:</label> <input id="guide" name="guide"  class="easyui-textbox" style="width: 75%;"/>
		  			</div>
		    		<!-- <tr>
		    			<td>建设场所:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="construction" name="construction" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>建设场所:</label> <input id="construction" name="construction"  class="easyui-combobox" style="width: 25%;"
						data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '居民区',
							value: '1'
						},{
							label: '公共机构',
							value: '2'
						},{
							label: '企事业单位',
							value: '3'
						},{
							label: '写字楼',
							value: '4'
						},{
							label: '工业园区',
							value: '5'
						},{
							label: '交通枢纽',
							value: '6'
						},{
							label: '大型文体设施',
							value: '7'
						},{
							label: '城市绿地',
							value: '8'
						},{
							label: '大型建筑配建网点',
							value: '9'
						},{
							label: '路边停车位',
							value: '10'
						},{
							label: '城际高速服务区',
							value: '11'
						},{
							label: '其他',
							value: '255'
						}],
	                    panelHeight:'auto'"
						/>
						<span style="margin-left: 15px;"></span>
						<label>站点电话:</label> <input id="stationPhone" name="stationPhone"  class="easyui-textbox" style="width: 25%;"/>
						
		  			</div>
		    		<!-- <tr>
		    			<td>站点照片:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="photo" name="photo" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>站点照片:</label> <input id="photo" name="photo"  class="easyui-textbox" style="width: 75%;"/>
		  			</div>
		    		<!-- <tr>
		    			<td>使用车型描述:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="matchCars" name="matchCars" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>使用车型描述:</label> <input id="matchCars" name="matchCars"  class="easyui-textbox" style="width: 75%;"/>
		  			</div>
		    		<!-- <tr>
		    			<td>车位楼层及数量描述:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="parkInfo" name="parkInfo" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>车位楼层及数量描述:</label> <input id="parkInfo" name="parkInfo"  class="easyui-textbox" style="width: 75%;height:60px" data-options="multiline:true"/>
		  			</div>
		    		
		    		<!-- <tr>
		    			<td>充电电费率:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="electricityFee" name="electricityFee" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>充电电费率:</label> <input id="electricityFee" name="electricityFee"  class="easyui-textbox" style="width: 25%;"/>
		  			
		    		<!-- <tr>
		    			<td>服务费率:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="serviceFee" name="serviceFee" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<span style="margin-left: 15px;"></span>
						<label>服务费率:</label> <input id="serviceFee" name="serviceFee"  class="easyui-textbox" style="width: 25%;"/>
		  			</div>
		    		<!-- <tr>
		    			<td>停车费:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="parkFee" name="parkFee" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>停车费:</label> <input id="parkFee" name="parkFee"  class="easyui-textbox" style="width: 25%;"/>
		  		
		  			<!-- <tr>
		    			<td>是否支持预约:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="isOrder" name="isOrder" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<span style="margin-left: 15px;"></span>
						<label>是否支持预约:</label> <input id="isOrder" name="isOrder"  class="easyui-combobox" style="width: 25%;" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '不支持',
							value: '0'
						},{
							label: '支持',
							value: '1'
						}],
	                    panelHeight:'auto'" >
		  			</div>
		    		<!-- <tr>
		    			<td>支付方式:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="payment" name="payment" style="width:100%;height:60px"  data-options="multiline:true">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>支付方式:</label> <input id="payment" name="payment"  class="easyui-textbox" style="width: 75%;"/>
		  			</div>
		    		
		    		<!-- <tr>
		    			<td>备注:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="remark" name="remark" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr> -->
		    		<div class="fitem">
						<label>备注:</label> <input id="remark" name="remark"  class="easyui-textbox" style="width: 75%;height:60px"  data-options="multiline:true"/>
		  			</div>
		    		<!-- <tr>
		    			<td>充电信息列表:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="equipmentInfo" name="equipmentInfo" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr>
		    		<div class="fitem">
						<label>充电站辖区编码:</label> <input  name="countryCode"  class="easyui-textbox" style="width: 75%;" 	required="true"  missingMessage="请填写充电站辖区编码"/>
		  			</div> -->
		    	</table> 
		    	
		    	
			</form>
		 </div>		 
	</body>
</html>