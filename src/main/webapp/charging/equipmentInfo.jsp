<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>电桩设备管理</title>
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
			   	var equipmentCode = $("input[name='_equipment_code']").val();
			    var stationName = $("input[name='_station_name']").val(); 
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        equipmentCode:equipmentCode,
		           	stationName:stationName  
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			  	var equipmentCode = $("input[name='_equipment_code']").val();
			    var stationName = $("input[name='_station_name']").val(); 
			    $('#dataGrid').datagrid('load',{
		           equipmentCode:equipmentCode,
		           stationName:stationName  
		        });
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var equipmentCode = $("input[name='_equipment_code']").val();
			    var stationName = $("input[name='_station_name']").val(); 
				$("#equipmentForm").form("submit", {
					url : "export_equipment_report?equipmentCode="+equipmentCode+"&stationName="+stationName,
					onSubmit : function() {
					},
					success : function(result) {
					}
				});

			});
			$('#dataGrid').datagrid( {
				title : '电桩设备管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				//singleSelect : true,
				url : 'dataListEquipmentInfo.do',
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
					field : 'stationName',
					title : '网点名称',
					width : $(this).width() * 0.1,
					align : 'center'	
				},{
					field : 'equipmentCode',
					title : '充电桩编码',
					align : 'center',
					width : $(this).width() * 0.15,
					
				},
				{
					field : 'equipmentType',
					title : '充电桩类型',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
						if(val=='1'){
						  return "直流设备";
						}else if(val=='2'){
						  return "交流设备";
						}else if(val=='3'){
						  return "交直流一体设备";
						}
					}	
				},{
					field : 'equipmentModel',   
					title : '充电桩型号',
					width : $(this).width() * 0.08,
					align : 'center'
				},{
					field : 'connectorNumber',   
					title : '充电枪个数（个）',
					width : $(this).width() * 0.08,
					align : 'center'
				}/* ,   
				  {
					field : 'manufacturerId',
					title : '设备生产商组织机构代码',
					align : 'center',
					width : $(this).width() * 0.15,
					
				} */
				,
				{
					field : 'softTypeName',
					title : '电桩版本类型',
					width : $(this).width() * 0.08,
					align : 'center'
				},
				{
					field:'versionCode',
					title:'电桩版本',
					align:'center',
					width:$(this).width() * 0.08,
					formatter : function(val, rec) {
						if((rec.versionRecord == "") || (parseFloat(rec.versionRecord) < parseFloat(rec.pileVersionRecord))) { //车的记录数 < 新记录数
							return "<a href=\"javascript:showUpgradeRecord('"+rec.equipmentCode+"','pile')\" >"+val+"</a> <font color='red'>【升】</font>" ;
						}else{
							return "<a href=\"javascript:showUpgradeRecord('"+rec.equipmentCode+"','pile')\" >"+val+"</a> <font color='green'>【新】</font> " ;
						}
					}
				},
				{
					field:'pileVersionNo',
					title:'电桩最新版本',
					align:'center',
					width:$(this).width() * 0.08
				},
				{
					field : 'affordName',
					title : '设备提供商名称',
					width : $(this).width() * 0.15,
					align : 'center'	
				},
				{
					field : 'productionDate',
					title : '生产日期',
					width : $(this).width() * 0.15,
					align : 'center'	
				}/* ,
				{
					field : 'equipmentLng',
					title : '设备经度',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'equipmentLat',
					title : '设备纬度',
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
						
				} */
				] ],
				detailFormatter:function(index,row){
		            return "<div style='padding:2px'><table class='ddv'></table></div>";
		        },
		        onExpandRow: function(index,row){
		            var ddv = $(this).datagrid('getRowDetail',index).find('table.ddv');
		            ddv.datagrid({
		                url:'connector_attachment_preview.do?id='+row.id,
		                fitColumns:false,
		                loadMsg:'',
		                height:'auto',
		                width:'100%',
		                columns:[[
		                	{
								field : 'connectorCode',
								title : '充电枪编码',
								width : '8%',
								align : 'center'
							}, 
					        {
								field : 'connectorName',
								title : '充电枪名称',
								width : '5%',
								align : 'center',
								
							}, {
								field : 'connectorType',
								title : '充电枪类型',
								width : '8%',
								align : 'center',
								formatter:function(val,rec){
									if(val=="1"){
										return "家用插座";
									}else if(val="2"){
										return "交流接口插座";
									}else if(val=="3"){
										return "交流接口插头";
									}else if(val=="4"){
										return "直流接口枪头";
									}
								}
								
								
							},
							{
								field : 'parkNo',
								title : '车位号',
								width : '5%',
								align : 'center'
							}, {
								field : 'voltageUpperLimits',
								title : '额定电压上限(V)',
								width : '6%',
								align : 'center'
								
							},
							{
								field : 'voltageLowerLimits',
								title : '额定电压下限(V)',
								width : '6%',
								align : 'center'
							},
							{
								field : 'current',
								title : '额定电流(A)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'power',
								title : '额定功率(KW)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'status',
								title : '充电枪状态',
								width : '8%',
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
								width : '5%',
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
								width : '5%',
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
								width : '5%',
								align : 'center'
							},
							{
								field : 'currentA',
								title : 'A相电流(A)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'currentB',
								title : 'B相电流(A)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'currentC',
								title : 'C相电流(A)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'voltageA',
								title : 'A相电压(V)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'voltageB',
								title : 'B相电压(V)',
								width : '5%',
								align : 'center'
							},
							{
								field : 'voltageC',
								title : 'C相电压(V)',
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
		    	}
				
			});
			
			
			//添加设备
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加设备");
			 	$("#supportBrand").combogrid("reset");
				$('#stationName').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
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
							$('#stationName').combogrid("grid").datagrid('options').queryParams = 
								JSON.parse('{"parkName":"' + parkName + '"}');
							// 重新加载
							$('#stationName').combogrid("grid").datagrid("reload");
							$('#stationName').combogrid("setValue",parkName);
							 
						}
					}
				});
				$("#equipmentCode").textbox('textbox').attr('readonly',false);
				$("#connectorNumber").combobox("setValue",1);
				$("#stationName").combogrid('enable');
			});
			$('#supportBrand').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
				 	delay:500,// 1s延时查询
				 	editable : true,
					panelHeight: 'auto', 
					panelWidth: 300,
					idField: 'id',
					textField: 'brandName',
					url:'allBrand',
					pageSize:10,
					required:true,
					multiple:true,
					columns : [ [ {
						field : 'brandName',
						title : '品牌名称',
						width : '80%',
						align : 'center'
					}] ],
					pagination : true,//是否分页
					rownumbers : true,//序号
					/* keyHandler : {
						query : function(brandName) { // 动态搜索处理
						$("#supportBrand").combogrid("grid").datagrid("options").queryParams = 
							JSON.parse('{"brandName":"' + brandName + '"}');
						// 重新加载
						$("#supportBrand").combogrid("grid").datagrid("reload");
					    $("#supportBrand").combogrid("setValue",brandName); 
							 
						}
					}, */
				});
			
			
			//构造对话框
			$("#addView").dialog( {
				width : "510",
				height : "400",
				top : "50",
				left:"400",
				modal:true,
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						var selectedRows = $("#supportBrand").combogrid('getValues');
						$("#addForm").form("submit", {
							url : "saveOrUpdateEquipmentInfo.do?ids="+selectedRows,
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
			
			//支持车辆品牌
			$("#btnBrand").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要支持的充电设备", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
				   	$("#typeId").val(JsonData.id);
					$("#brandView").dialog("open").dialog("setTitle", "支持车辆品牌");
					$("#brandGrid").datagrid( {
						width : 'auto',
						height : 'auto',
						fit : true,
						fitColumns : true,
						nowrap : true,
						striped : true,
						collapsible : true,
						rownumbers : true,
						singleSelect : false,
						selectOnCheck: true,
						checkOnSelect: true,
						url : 'charging_car_brand_list_by_equipmentId.do?equipmentId='+JsonData.id,
						pageSize : 20,
						idField : 'id',
						pagination : true,
						rownumbers : true,
						columns : [ [ 
						{
							field : 'false',
							checkbox:true
						}, {
							field : 'brandName',
							title : '车辆品牌名称',
							width : $(this).width() * 0.3,
							align : 'center'
						}, {
							field : 'images',
							title : '车辆品牌图片',
							width : $(this).width() * 0.3,
							align : 'center',
							formatter:function(value, row, index){
							   if(value != ""){
							      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: absolute;'><img width='300'  src='"+value+"'/></div>";
							   }
							}
						}] ]
					});
		  		}	     
			});
			
			//构造对话框
			$("#brandView").dialog( {
				top : "30"
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
							$.post("deleteEquipmentInfoById.do",{"id":JsonData.id},function(data){
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
				
			
			
			//修改充电设备信息
			$("#btnEdit").bind("click",function(){
			$("#viewForm").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				var ids = selectedRows[0].brandIds.split(","); 
				$("#supportBrand").combogrid("grid").datagrid('clearSelections');//取消之前选择的行
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要修改的记录", "error");
				}else{
						$("#id").val(selectedRows[0].id);
						$("#equipmentCode").textbox("setValue",selectedRows[0].equipmentCode);
						$("#equipmentCode").textbox('textbox').attr('readonly',true);
						$("#stationName").combogrid("setValue",selectedRows[0].stationName);
						$("#stationName").combogrid('disable');
						$("#manufacturerId").textbox("setValue",selectedRows[0].manufacturerId);
						$("#equipmentModel").textbox("setValue",selectedRows[0].equipmentModel);
						$("#equipmentLng").textbox("setValue",selectedRows[0].equipmentLng);
						$("#equipmentLat").textbox("setValue",selectedRows[0].equipmentLat);
						$("#equipmentType").combobox("setValue",selectedRows[0].equipmentType);
						$("#connectorNumber").combobox("setValue",selectedRows[0].connectorNumber);
						$("#affordName").textbox("setValue",selectedRows[0].affordName);
						for (var i=0;i<ids.length;i++){ 
						$("#supportBrand").combogrid("grid").datagrid("selectRecord",ids[i]); 
						};
						
						$("#productionDate").datetimebox("setValue",selectedRows[0].productionDate);
						$("#addView").dialog({title:"修改充电设备信息"});
						$("#addView").dialog("open");
						
						
				}
			});
			//构造保存对话框
			$("#addBrandView").dialog( {
				width : "500",
				height : "400",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						var selectedRows = $("#brandName").combogrid('getValues');
						$("#addBrandForm").form("submit", {
							url : "equipment_brand_add?ids="+selectedRows,
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
								    $("#brandGrid").datagrid("reload");
									$("#addBrandView").dialog("close");
									$("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "保存失败", "info");
								    
									$("#addBrandView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#addBrandView").dialog("close");
							
							
					}
				}]
			});
			//设置支持汽车品牌
			$("#btnEditSetBrand").bind("click",function(){
				$("#addBrandView").dialog("open").dialog("setTitle","添加支持汽车品牌");
				var selectedRow =$("#dataGrid").datagrid("getSelections");
				var url = 'allNotBelongBrand?equipmentId='+selectedRow[0].id;
				$("#equipment_id").val(selectedRow[0].id);
				//生成数据下拉列表
				$("#brandName").combogrid({
					delay:500,// 1s延时查询
				 	editable : true,
					panelHeight: 'auto', 
					panelWidth: 300,
					idField: 'id',
					textField: 'brandName',
					url:url,
					striped : true,
					pageSize:10,
					required:true,
					multiple:true,
					pagination : true,//是否分页
					rownumbers : true,//序号
					columns : [ [ {
					field : 'ck',
					checkbox:true
					},{
					field : 'brandName',
					title : '车辆品牌名称',
					width : '80%',
					align : 'center'
					}/* ,{
					field : 'images',
					title : '车辆品牌图片',
					width : '80%',
					align : 'center',
					formatter:function(value, row, index){
							   if(value != ""){
							      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: absolute;'><img width='300'  src='"+value+"'/></div>";
							   }
							}
					} */] ],
					keyHandler : {
					query : function(brandName) { // 动态搜索处理
					$("#brandName").combogrid("grid").datagrid("options").queryParams = 
						JSON.parse('{"brandName":"' + brandName + '"}');
					// 重新加载
					$("#brandName").combogrid("grid").datagrid("reload");
					$("#brandName").combogrid("setValue",brandName);
					 
				}
			}
					
				});
				
			});
			
		
		 //设备支持品牌取消
			$("#btnEditCancelBrand").bind("click",function(){
				var selectedRows = $("#brandGrid").datagrid("getSelections");
				var JsonData='';
				if(selectedRows.length <=0){
					$.messager.alert("提示", "请选择要取消支持的品牌", "error");
				}else{
					for(var i=0;i<selectedRows.length;i++){
						
						JsonData = JsonData+ selectedRows[i].id+',';
					}
					$.messager.confirm("提示","确定要移除吗?",function(r){
			      		if(r){
							$.post("deleteRecordsById",{"id":JsonData},function(data){
								if(data=="success"){
									$.messager.alert("提示", "移除成功", "info");
								    $("#brandGrid").datagrid("reload");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "移除失败", "info");
								}
							},"text");
						}
					});
				}
			});	
				
				
				//电桩升级
		$("#pileUpgrade").bind("click",function(){
			var pileCodes = "";
			var rows = $('#dataGrid').datagrid('getSelections');
			var equipmentType = "";
			for(var i=0; i<rows.length; i++){
				if((rows[i].versionRecord == "") || (parseFloat(rows[i].versionRecord) < parseFloat(rows[i].pileVersionRecord))) { //车的记录数 小于 系统记录数 ， 发送升级指令
					pileCodes= pileCodes +rows[i].pileVersionId+"%-%"+ rows[i].equipmentCode+",";
				}
			}
			if(pileCodes != "") {
				$.post("pile_upgrade",{"pileCodes":pileCodes},function(data){
					if(data=="succ"){
						$.messager.alert("提示", "下发升级充电桩指令成功", "info");
					}else{
						$.messager.alert("提示", "下发升级充电桩指令失败", "info");
					}
				});
			}else{
				$.messager.alert("提示", "请选择低版本电桩在下发指令", "info");
			}
		}); 
		
			//全部宝盒升级
			$("#allpileUpgrade").bind("click",function(){
				$.post("all_pile_upgrade", function(data){
					if(data == "succ"){
						$.messager.alert("提示", "下发升级充电桩指令成功", "info");
		    		}else{
		    			$.messager.alert("提示", "下发升级充电桩指令失败", "info");
		    		}
			    });
			}); 	
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		
		//版本更新日志
		function showUpgradeRecord(lpn,upgradeType){
			$("#showVersionView").dialog("open").dialog("setTitle", "版本更新日志");
			var url='version_upgrade_list';
			$('#showVersionGrid').datagrid( {
				width : 'auto',
				height : 'auto',
				fit:true,
				fitColumns: true,
				singleSelect : true,
				selectOnCheck: true,
				checkOnSelect: true,
				url : url,
				queryParams: {
					lpn:lpn,
					upgradeType:upgradeType
				},
				pageSize:20,
				columns : [ [ {
					field : 'lpn',
					title : '充电桩编码',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return val;
					}
				}, {
					field : 'currentVersionNo',
					title : '当前版本',
					width : $(this).width() * 0.2,
					align : 'center'
				}, {
					field : 'upgradeVersionNo',
					title : '更新版本',
					width : $(this).width() * 0.2,
					align : 'center'
				}, {
					field : 'status',
					title : '更新状态',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == 1)
					       return "<font color='red'>失败</font>";
					    else if(val == 0)
					       return "<font color='green'>成功</font>";
					}
				}, {
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.3,
					align : 'center'
				},{
					field : 'remark',
					title : '描述',
					width : $(this).width() * 0.5,
					align : 'center'
				}] ],
				pagination : true,
				rownumbers : true
			});
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
		<div id="showVersionView" class="easyui-dialog" closed="true" style="width:800px;height:400px;padding-bottom: 25px;">
			<table id="showVersionGrid"></table>
		</div>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	    <form action="" id="equipmentForm">
			网点名称:<input id="_station_name"  name="_station_name" class="easyui-textbox" style="width:100px"/>  
			充电桩编码:<input id="_equipment_code"  name="_equipment_code" class="easyui-textbox" style="width:100px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>

            <r:FunctionRole functionRoleId="add_equipmentInfo">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_equipmentInfo">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_equipmentInfo">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="support_brand_management">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="btnBrand">支持品牌设置</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="increase_pile">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-wrench-orange" plain="true" id="pileUpgrade" onclick=";">电桩升级</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="all_pile_increase">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-hammer" plain="true" id="allpileUpgrade" onclick=";">全部电桩升级</a>
			</r:FunctionRole>
			<r:FunctionRole functionRoleId="export_equipment">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
			</r:FunctionRole>
			</form>
         </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td>网点名称:</td>
		    			<td>
		    			 <input class="easyui-combogrid" name='stationName' id="stationName" style="width:300px;">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>充电桩编码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="equipmentCode" name="equipmentCode" style="width:100%;height:24px" data-options="required:true,missingMessage:'设备编码不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>充电桩类型:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="equipmentType" name="equipmentType" style="width:100%;height:24px" data-options="
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
		                    panelHeight:'auto'">
		    			</td>
		    		</tr><tr>
		    			<td>充电桩型号:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="equipmentModel" name="equipmentModel" style="width:100%;height:24px">
		    			</td>
		    		</tr><tr>
		    			<td>充电枪个数:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="connectorNumber" name="connectorNumber" style="width:100%;height:24px"
		    			data-options="
		                    valueField:'value',
		                    textField:'label',
		                    data: [{
								label: '1个',
								value: '1'
							},{
								label: '2个',
								value: '2'
							}],
		                    panelHeight:'auto'"
		    			>
		    			</td>
		    		</tr><tr>
		    			<td>支持车辆品牌:</td>
		    			<td>
		    			<input class="easyui-combogrid"  id="supportBrand" name="supportBrand" style="width:100%;height:24px">
		    			</td>
		    		</tr><tr>
		    			<td>设备提供商名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="affordName" name="affordName" style="width:100%;height:24px">
		    			</td>
		    		</tr>
		    		<!-- <tr>
		    			<td>生产商组织机构代码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="manufacturerId" name="manufacturerId" style="width:100%;height:24px">
		    			</td>
		    		</tr> --><tr>
		    			<td>生产日期:</td>
		    			<td>
		    			<input class="easyui-datetimebox"  id="productionDate" name="productionDate" style="width:100%;height:24px">
		    			<!-- <input class="easyui-datetimebox"  name="start_time" id="start_time" style="width:100%;height:24px" data-options="required:true,missingMessage:'生效时间不能为空',groupSeparator:','"> -->
		    			</td>
		    		</tr><!-- <tr>
		    			<td>接口列表:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="connectorInfos" name="connectorInfos" style="width:100%;height:24px">
		    			</td>
		    		</tr> --><!-- <tr>
		    			<td>设备经度:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="equipmentLng" name="equipmentLng" style="width:100%;height:24px">
		    			</td>
		    		</tr><tr>
		    			<td>设备纬度:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="equipmentLat" name="equipmentLat" style="width:100%;height:24px">
		    			</td>
		    		</tr> -->
		    		
		    	</table> 
		    	
			</form>
		 </div>	
		 
		  <!-- brand -->
         <div id="brandView" class="easyui-dialog" closed="true" style="width:800px;height:500px;padding-bottom: 25px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-application_windows_okay" plain="true" id="btnEditSetBrand">设置支持</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-application_windows_remove" plain="true" id="btnEditCancelBrand">取消支持</a>
			<table id="brandGrid"></table>
			<!-- <input type="hidden" name="typeId" id="typeId" /> -->
			<div id="addBrandView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addBrandForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="equipment_id" id="equipment_id" />
				
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td>车辆品牌名称:</td>
		    			<td>
		    			<input class="easyui-combogrid"  id="brandName" name="brandName" style="width:300px;height:24px">
		    			</td>
		    		</tr> 
		    	</table> 
			</form>
			 </div>
			
		 </div>	 
	</body>
</html>