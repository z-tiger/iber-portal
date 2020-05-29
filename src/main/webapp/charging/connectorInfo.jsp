<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电枪管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/datagrid-detailview.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		function clearToolbar(){
			$('#toolbar').form('clear');
		}
		$(function() {
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			//回车键查询
			$(document).keydown(function(event){
			   	var connectorName = $("input[name='_connectorName']").val();
			    var equipmentCode = $("input[name='_equipmentCode']").val(); 
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        connectorName:connectorName,
		           	equipmentCode:equipmentCode  
		          });
				}
			});
			//导出充电枪excel
			$("#btnExport").bind("click", function() {
				var connectorName = $("input[name='_connectorName']").val();
			    var equipmentCode = $("input[name='_equipmentCode']").val();
				$("#connectoForm").form("submit", {
					url : "export_connector_report",
					onSubmit : function(param) {
						param.connectorName = connectorName;
						param.equipmentCode = equipmentCode;
					},
					success : function(result) {
					}
				});
		
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			    var connectorName = $("input[name='_connectorName']").val();
			    var equipmentCode = $("input[name='_equipmentCode']").val(); 
			    $('#dataGrid').datagrid('load',{
		            connectorName:connectorName,
		           	equipmentCode:equipmentCode  
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '充电枪管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dataListConnectorInfo.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				pagination : true,
				rownumbers : true,
				view: detailview,
				columns : [ [{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'equipmentCode',
					title : '充电桩编码',
					width : $(this).width() * 0.12,
					align : 'center'	
				},   
				  {
					field : 'connectorCode',
					title : '充电枪编码',
					align : 'center',
					width : $(this).width() * 0.12,
					
				}, 
				{
					field : 'connectorNo',
					title : '充电枪编号 ',
					align : 'center',
					width : $(this).width() * 0.08,
					formatter:function(val,rec){
						if(val=="1"){
							return "A";
						}else if(val=="2"){
							return "B";
						}
				  }
				},     
				  {
					field : 'connectorName',
					title : '充电枪名称',
					align : 'center',
					width : $(this).width() * 0.08,
					
				},
				{
					field : 'connectorType',   
					title : '充电枪类型',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
						if(val=="1"){
							return "家用插座";
						}else if(val=="2"){
							return "交流接口插座";
						}else if(val=="3"){
							return "交流接口插头";
						}else if(val=="4"){
							return "直流接口枪头";
						}
					}
				},
				{
					field : 'voltageUpperLimits',
					title : '额定电压上限(V)',
					width : $(this).width() * 0.08,
					align : 'center'
						
				},
				{
					field : 'voltageLowerLimits',
					title : '额定电压下限(V)',
					width : $(this).width() * 0.08,
					align : 'center'
					
				},
				{
					field : 'current',
					title : '额定电流(A)',
					width : $(this).width() * 0.08,
					align : 'center'
				
				},
				{
					field : 'power',
					title : '额定功率(KW)',
					width : $(this).width() * 0.08,
					align : 'center'
				},
				{
					field : 'parkNo',
					title : '车位号',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'lockCode',
					title : '地锁编码',
					width : $(this).width() * 0.08,
					align : 'center'	
				},{
				    field : 'lockStatus',
					title : '是否有地锁',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
						if(val=="1"){
							return "是";
						}else if(val=="0"){
							return "否";
						}
					}
				}
				/* ,
				{
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
					
				} */
				] ],
				 detailFormatter:function(index,row){
		            return "<div style='padding:2px'><table class='ddv'></table></div>";
		        },onExpandRow: function(index,row){
		            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
		            ddv.datagrid({
		                url:'connectorStatus_attachment_preview.do?id='+row.id,
		                fitColumns:false,
		                loadMsg:'',
		                height:'auto',
		                width:'1200px',
		                columns:[[
							{
								field : 'status',
								title : '接口状态',
								width : '10%',
								align : 'center',
								formatter:function(val,rec){
									if(val=="0"){
										return "离网";
									}else if(val=="1" || val=="2"){
										return "空闲";
									}/*else if(val=="2"){
										return "占用（未充电）";
									}*/else if(val=="3"){
										return "占用（充电中）";
									}else if(val=="4"){
										return "占用（预约锁定）";
									}else if(val=="255"){
									   return "故障";
									}
								}
							},
							{
								field : 'parkStatus',
								title : '车位状态',
								width : '10%',
								align : 'center',
								formatter:function(val,rec){
									if(val=="10"){
										return "空闲";
									}else if(val=="50"){
										return "占用";
									}else {
										return "未知";
									}
								}
							},
							{
								field : 'lockStatus',
								title : '地锁状态',
								width : '10%',
								align : 'center',
								formatter:function(val,rec){
									if(val=="10"){
										return "已解锁";
									}else if(val=="50"){
										return "已上锁";
									}else {
										return "未知";
									}
								}
							},
							{
								field : 'soc',
								title : '剩余电量(%)',
								width : '10%',
								align : 'center',
								
							},
							{
								field : 'currentA',
								title : 'A相电流(A)',
								width : '10%',
								align : 'center',
				
							},
							{
								field : 'currentB',
								title : 'B相电流(A)',
								width : '10%',
								align : 'center',
							
							},
							{
								field : 'currentC',
								title : 'C相电流(A)',
								width : '10%',
								align : 'center',

							},
							{
								field : 'voltageA',
								title : 'A相电压(V)',
								width : '10%',
								align : 'center',
			
							},
							{
								field : 'voltageB',
								title : 'B相电压(V)',
								width : '10%',
								align : 'center',
	
							},
							{
								field : 'voltageC',
								title : 'C相电压(V)',
								width : '10%',
								align : 'center',
		
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
			 	$("#addView").dialog("open").dialog("setTitle", "添加充电接口");
			 	$("#connectorName").textbox('textbox').attr('readonly',false);
			 	$("#equipmentCode").combogrid('enable');
			 	$("#connectorCode").textbox('textbox').attr('readonly',false);
			 	/* $("#lockStatus").combogrid({
			 	     textField: 'lockStatus',
			 	     
			 	}); */
			 	$('#equipmentCode').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
				 	delay:500,// 1s延时查询
				 	editable : true,
					panelHeight: 'auto', 
					panelWidth: 300,
					idField: 'id',
					textField: 'equipmentCode',
					url:'parkEquipment_allByPage',
					pageSize:10,
					required:true,
					columns : [ [ {
						field : 'stationName',
						title : '网点名称',
						width : '45%',
						align : 'center'
					},{
						field : 'equipmentCode',
						title : '设备编码',
						width : '45%',
						align : 'center'
					}] ],
					pagination : true,//是否分页
					rownumbers : true,//序号
					keyHandler : {
						query : function(equipmentCode) { // 动态搜索处理
							$('#equipmentCode').combogrid("grid").datagrid('options').queryParams = 
								JSON.parse('{"equipmentCode":"' + equipmentCode + '"}');
							// 重新加载
							$('#equipmentCode').combogrid("grid").datagrid("reload");
							$('#equipmentCode').combogrid("setValue",equipmentCode);
							 
						}
					}
				});
				 $("#carParkView").dialog("open");
			 	
			});
			
			
			
			//构造对话框
			$("#addView").dialog( {
				width : "480",
				height : "480",
				top : "50",
				left:"400",
				modal:true,
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "saveOrUpdateConnectorInfo.do",
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
							$.post("deleteConnectorInfoById.do",{"id":JsonData.id},function(data){
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
						$("#connectorName").textbox("setValue",selectedRows[0].connectorName);
						$("#connectorName").textbox('textbox').attr('readonly',false);
						$("#equipmentCode").textbox("setValue",selectedRows[0].equipmentCode);
						$("#equipmentCode").combogrid('disable');
						$("#connectorCode").textbox("setValue",selectedRows[0].connectorCode);
						$("#connectorCode").textbox('textbox').attr('readonly',false);
						$("#connectorNo").textbox("setValue",selectedRows[0].connectorNo);
						$("#connectorNo").textbox('textbox').attr('readonly',false);
						$("#connectorType").combobox("setValue",selectedRows[0].connectorType);
						/* $("#connectorType").combobox('combobox').attr('readonly',true); */
						$("#voltageUpperLimits").textbox("setValue",selectedRows[0].voltageUpperLimits);
						$("#voltageLowerLimits").textbox("setValue",selectedRows[0].voltageLowerLimits);
						$("#current").textbox("setValue",selectedRows[0].current);
						$("#power").textbox("setValue",selectedRows[0].power);
						$("#parkNo").textbox("setValue",selectedRows[0].parkNo);
						$("#lockCode").textbox("setValue",selectedRows[0].lockCode);
						$("#lockStatus").combobox("setValue",selectedRows[0].lockStatus);
						$("#addView").dialog({title:"修改充电接口信息"});
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
 	    	<form action="" id="connectoForm">
	 	    	充电桩编码:<input id="_equipmentCode"  name="_equipmentCode" class="easyui-textbox" style="width:100px"/>
				充电枪名称:<input id="_connectorName"  name="_connectorName" class="easyui-textbox" style="width:80px"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	         
	            <r:FunctionRole functionRoleId="add_connectorInfo">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="update_connectorInfo">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="delete_connectorInfo">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="export_connectorInfo">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
	            </r:FunctionRole>
            </form>
          </div>
         <!-- add -->
       	  
          <div id="addView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				 <table cellpadding="5" style="font-size: 12px;margin-left:30px;margin-top: 0px;"> 
			 		<tr>
		    			<td>网点及设备编码:</td>
		    			<td>
		    			<input class="easyui-combogrid"  id="equipmentCode" name="equipmentCode" style="width:85%;height:24px"/>
				    		<!-- 	data-options=" url:'equipmentId_Combobox',
			                    method:'get',
			                    valueField:'text',
			                    textField:'text',
			                    panelHeight:'auto'" -->
		    			</td>	
		    		</tr>
		    		<tr>
		    			<td>充电枪名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="connectorName" name="connectorName" style="width:85%;height:24px"/>
		    			</td>
		    		</tr> 
		    		<tr>
		    			<td>充电枪编码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="connectorCode" name="connectorCode" style="width:85%;height:24px"/>
		    			</td>
		    		</tr> 
		    		<tr>
		    			<td>充电枪编号:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="connectorNo" name="connectorNo" style="width:85%;height:24px" data-options="
		    			valueField:'value',
	                    textField:'label',
	                    required:true,
	                    data: [{
	                    label: 'A',
							value: '1'
						},{
							label: 'B',
							value: '2'
						}],panelHeight:'auto'
		    			"/>
		    			</td>
		    		</tr> 
		    		<tr>
		    			<td>充电枪类型:</td>
		    			<td>
			    			<input class="easyui-combobox"  id="connectorType" name="connectorType" style="width:85%;height:24px" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '家用插座',
							value: '1'
						},{
							label: '交流接口插座',
							value: '2'
						},{
							label: '交流接口插头',
							value: '3'
						},{
							label: '直流接口枪头',
							value: '4'
						}],
	                    panelHeight:'auto'" >
			    		</td>
		    		</tr> 
		    	
		    		<tr>
		    			<td>额定电压上限:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="voltageUpperLimits" name="voltageUpperLimits" style="width:85%;height:24px" />：V
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>额定电压下限:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="voltageLowerLimits" name="voltageLowerLimits" style="width:85%;height:24px" />：V
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>额定电流:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="current" name="current" style="width:85%;height:24px" />：A
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>额定功率:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="power" name="power" style="width:85%;height:24px" />：kW
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>车位号:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="parkNo" name="parkNo" style="width:85%;height:24px" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>地锁编码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="lockCode" name="lockCode" style="width:85%;height:24px" data-options="required:true"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>是否有地锁:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="lockStatus" name="lockStatus" style="width:85%;height:24px" data-options="
		    			valueField:'value',
	                    textField:'label',
	                    required:true,
	                    data: [{
							label: '否',
							value: '0'
						},{
							label: '是',
							value: '1'
						}],
		    			panelHeight:'auto'" >
		    			</td>
		    		</tr>
		    		<input type="hidden" id="lockStatus" name="lockStatus"/>
		    	</table> 
			</form>
		 </div>	 
		 	 
	</body>
</html>