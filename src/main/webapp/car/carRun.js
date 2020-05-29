function setLevelCode(levelCode) {
    switch (levelCode) {
        case 0:
            $("#level-code").textbox("setValue","黑名单");
            break;
        case 1:
            $("#level-code").textbox("setValue","一星会员");
            break;
        case 2:
            $("#level-code").textbox("setValue","二星会员");
            break;
        case 3:
            $("#level-code").textbox("setValue","三星会员");
            break;
        case 4:
            $("#level-code").textbox("setValue","四星会员");
            break;
        case 5:
            $("#level-code").textbox("setValue","五星会员");
            break;
    }
}
function setOrderStatus(orderStatus) {
    switch (orderStatus) {
        case "ordered":
            $("#order-status").textbox("setValue","预约");
            break;
        case "useCar":
            $("#order-status").textbox("setValue","用车中");
            break;
        case "return":
            $("#order-status").textbox("setValue","还车");
            break;
        case "finish":
            $("#order-status").textbox("setValue","完成");
            break;
        // case "charging":
        //     $("#order-status").textbox("setValue","充电中");
        //     break;
        // case "repair":
        //     $("#order-status").textbox("setValue","维修");
        //     break;
        // case "maintain":
        //     $("#order-status").textbox("setValue","维护");
        //     break;
    }
}
$(function() {
    // 当页面点击了车辆运营管理后会自动加载carRun.jsp页面从而自动装载此datagrid数据表格
    // 清空Combogrid
	$("#clearCombogrid").bind("click", function() {
		$("#g-groupId").combogrid("clear");
	});
	
	$('#grid')
			.datagrid(
					{
						title : '车辆运营管理',
						width : 'auto',
						height : 'auto',
						fit : true,
						fitColumns : true,// 自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动
						nowrap : true,// 在同一行中显示数据
						striped : true,// 是否显示斑马线效果
						collapsible : true,// 否显示可折叠按钮
						singleSelect : true,// 如果为true，则只允许选择一行
						url : 'car_mrg_run_list',// 会自动发送异步的请求
						queryParams : {// 在请求远程数据时发送额外的参数。
							'cityCode' : $("#scityCode").combobox("getValue"),
							'lpn' : $("#s-lpn").textbox("getValue"),
							'parkName' : $("#s-parkName").textbox("getValue")
							
						},
						pageSize : 100,// 设置分页属性的时候初始化页面大小
						pageList : [100,50,30,10],
						idField : 'id', // 指明哪一个字段是标识字段
						columns : [ [
								{// DataGrid列配置对象
									field : 'ck', // 列字段名称
									checkbox : true
								// 如果为true，则显示复选框

								},
								{
									field : 'cityCode',
									title : '所属城市',
									hidden : true,
									width : $(this).width() * 0.08,
									align : 'center'
								},
								{
									field : 'cityName',
									title : '所属城市',
									width : $(this).width() * 0.08,
									align : 'center'
								},
								{
									field : 'lpn',
									title : '车牌号码',
									width : $(this).width() * 0.08,
									align : 'center',
									formatter : function(val,rec) {
										
										if (val.indexOf("•") < 0){
											return "<img width='20' height='10' src='"+rec.brandUrl+"'/>" + val.substring(0,2) + "•" + val.substring(2);
										}else {
											return "<img width='20' height='10' src='"+rec.brandUrl+"'/>" + val;
										}
									}
								},
								{
									field : 'brandName',
									title : '品牌',
									width : $(this).width() * 0.08,
									align : 'center'
								},
								{
									field : 'carTypeType',
									title : '型号',
									width : $(this).width() * 0.08,
									align : 'center'
								},
								{
									field : 'carRunStatus',
									title : '车辆状态',
									width : $(this).width() * 0.08,
									align : 'center',
									formatter : function(val, rec) {
										if (val == "empty" || val == ""
												|| val == null || val == "null"
												|| val == "NULL") {
											return "闲置中"
										} else if (val == "repair") {
											return "维修中"
										} else if (val == "maintain") {
											return "维护中"
										}else if(val=="rescue"){
											return "救援中"
										}else if(val=="accident"){
											return "事故处理中"
										}else if (val == "charging"){
											return "补电中"
										}else {
											if(rec.preOffline =="1"){
												return "运营中(<font color=red>预下线</font>)"
											}else{
												return "运营中"
											}
										}
									}
								},
								{
									field : '        ',
									title : '会员是否可见',
									width : $(this).width() * 0.08,
									align : 'center',
									formatter : function(val,rec) {
										if (rec.carRunStatus == "empty" || rec.carRunStatus == ""
											|| rec.carRunStatus == null || rec.carRunStatus == "null"
											|| rec.carRunStatus == "NULL") {
											if(rec.restBattery >= rec.offlineThreshold && 
													Number(rec.smallBatteryVoltage) >= Number(rec.sysSmallBatteryVoltage)){
												return "是";
											}else{
												return "否";
											}
										} else if (rec.carRunStatus == "repair") {
											return "否"
										} else if (rec.carRunStatus == "maintain") {
											return "否"
										}else if(rec.carRunStatus=="rescue"){
											return "否"
										}else if(rec.carRunStatus=="accident"){
											return "否"
										}else if (rec.carRunStatus == "charging"){
											return "否"
										}else {
											if(rec.preOffline =="1"){
												return "是"
											}else{
												return "是"
											}
										}
									}
								},
								{
									field : 'onlineLowerLimit',
									title : '补电车辆下线阀值',
									width : $(this).width() * 0.08,
									align : 'center',
									hidden : true
								},
								{
									field : 'offlineThreshold',
									title : '车辆自动下线电量阀值',
									width : $(this).width() * 0.08,
									align : 'center',
									hidden : true
								},
								{
									field : 'restBattery',
									title : '剩余电量',
									width : $(this).width() * 0.08,
									align : 'center',
									formatter : function(val, rec) {
										if (val < 30) {
											if(rec.batStatus == 1){
												return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_smaill.png'/> "
													+ val+"（<font color=red>充电中</font>）";	
											}else{
												return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_smaill.png'/> "
												+ val+"（未充电）";	
											}
										} else if (30 <= val && val <= 80) {
											if(rec.batStatus == 1){
												return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_medium.png'/> "
													+ val+"（<font color=red>充电中</font>）";	
											}else{
												return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_medium.png'/> "
													+ val+"（未充电）";	
											}
										} else if (val > 80) {
											if(rec.batStatus == 1){
												return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_big.png'/> "
													+ val+"（<font color=red>充电中</font>）";	
											}else{
												return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_big.png'/> "
													+ val+"（未充电）";	
											}
										}
									}
								}, {
									field : 'cpuTemperature',
									title : '车载CPU温度',
									width : $(this).width() * 0.06,
									align : 'center',
									styler : function(value,row) {
										if(value<70){
											return "color:green";
										}else {
											return "color:red";
										}
									},
									formatter:function(val,rec){
										if(val==null || val==''){
											return "";
										}else{
											return val+"°C";
										}
									}
								},
								/*{
									field : 'smallBatteryChargeStatus',
									title : '小电瓶充电状态',
									width : $(this).width() * 0.08,
									align : 'center',
									formatter : function(val, rec) {
										if (val == "0") {
											return "未充电";
										} else if (val == "1") {
											return "正在充电";
										} else {
											return val;
										}
									}
								},*/
								{
									field : 'smallBatteryVoltage',
									title : '小电瓶电压',
									width : $(this).width() * 0.08,
									align : 'center',
									formatter : function(val, rec) {
										if (val < 100) {
											return "<font color=red>"
													+ parseFloat(val / 10)
													+ "V</font>";
										} else {
											return parseFloat(val / 10) + "V";
										}
									}
								}, {
									field : 'sysSmallBatteryVoltage',
									title : '系统电压',
									width : $(this).width() * 0.08,
									align : 'center',
									hidden :true
								},{
									field : 'parkName',
									title : '所在网点',
									align : 'center'
								},{
									field : 'areaName',
									title : '所属城/镇区',
									align : 'center'
                            	}, {
									field : 'giName',
									title : '所属片区',
									width : $(this).width() * 0.06,
									align : 'center'
								}/*
									 * ,{ field : 'deviceStatus', title :
									 * '设备状态', width : $(this).width() * 0.08,
									 * align : 'center' }
									 */, {
									field : 'bluetoothNo',
									title : '设备蓝牙地址',
									width : $(this).width() * 0.08,
									align : 'center'
								}, {
									field : 'versionName',
									title : '设备软件版本号',
									width : $(this).width() * 0.08,
									align : 'center'
								}, {
									field : 'updateTime',
									title : '设备上次上报时间',
									width : $(this).width() * 0.08,
									align : 'center'
								}, {
                                field : 'tfcard',
                                title : 'tf卡状态',
                                width : $(this).width() * 0.08,
                                align : 'center',
                                formatter : function(val, rec) {
                                    if (val == 1) {
                                        return "<font color=green>正常</font>";
                                    } else {
                                        return "<font color=red>已拔出</font>";
                                    }
                                }
								},{
									field : 'getuiStatus',
									title : '个推状态',
									width : $(this).width() * 0.06,
									align : 'center',
									formatter : function(val, rec) {
										var result = "";
										if(val == "Offline") {
											return "<font color=red>离线</font>";
										}else if(val == "Online"){
											return "在线";
										}
										return result;
									}
								}, {
									field : 'iccId',
									title : 'ICCID',
									width : $(this).width() * 0.16,
									align : 'center'
								}
						] ],
						pagination : true, // 如果为true，则在DataGrid控件底部显示分页工具栏
						rownumbers : true
					// 如果为true，则显示一个行号列
					});

	// car repair view
	$("#carRepairView").dialog({
		width : "520",
		height : "320",
		top : "100",
		left : "400",
		modal : true,
		title : "车辆维修",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carRepairViewForm").form("submit", {
					url : 'car_repair_save',
					onSubmit : function() {
						return $(this).form("validate");
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
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carRepairView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else if (result == "noRepair") {
							$.messager.alert("提示", "该车辆已处于维修，维护或者补电中，请勿重复提交维修!", "info");
							$("#grid").datagrid("reload");
							$("#carRepairView").dialog("close");
						}else if(result == "noEmpty"){
							$.messager.alert("提示", "该车辆不为空闲状态！", "info");
							$("#grid").datagrid("reload");
							$("#carRepairView").dialog("close");
						}else if(result == "sameStatus"){
							$.messager.alert("提示", "该车不能切换到相同的下线状态", "info");
							$("#grid").datagrid("reload");
							$("#carRepairView").dialog("close");
						}else if(result == "modifyFailed"){
							$.messager.alert("提示", "维护中或维修中的车不可切换到补电中", "info");
							$("#grid").datagrid("reload");
							$("#carRepairView").dialog("close");
					    }else if(result == "running"){
					    	$.messager.alert("提示", "车辆正在运营中...", "error");
							$("#grid").datagrid("reload");
							$("#carRepairView").dialog("close");
					    }
						else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carRepairView").dialog("close");
			}
		} ]
	});

	// 车辆预下线弹框
	$("#carPreOfflineView").dialog({
		width : "460",
		height : "260",
		top : "100",
		left : "400",
		modal : true,
		title : "车辆预下线",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carPreOfflineViewForm").form("submit", {
					url : 'changePreOnlineStatus',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carPreOfflineView").dialog("close");
						}else if(result == "fail"){
							$.messager.alert("提示", "操作失败", "error");
							$("#grid").datagrid("reload");
						}else if(result == "noWord"){
							$.messager.alert("提示", "请输入预下线原因", "error");
						}else if(result == "preOffline"){
							$.messager.alert("提示", "该车辆已预下线", "error");
						}else {
							$.messager.alert("提示", result, "info");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carPreOfflineView").dialog("close");
			}
		} ]
	});
	
	
	
	// car repair
	$("#btnRepair").bind("click", function() {
		$("#carRepairViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if (selectedRows.length <= 0) {
			$.messager.alert("提示", "请选择要维修的车辆", "error");
		} 
//		else if (selectedRows[0].carRunStatus == "repair") {
//			$.messager.alert("提示", "车辆已在维修中...", "error");
//		}
//		else if(selectedRows[0].carRunStatus == "maintain"){
//			$.messager.alert("提示", "车辆已在维护中...", "error");
//		}
		else if(selectedRows[0].carRunStatus == "ordered"||selectedRows[0].carRunStatus =="return"||selectedRows[0].carRunStatus =="useCar"){
			$.messager.alert("提示", "车辆正在运营中...", "error");
		}else{
			$("#carRepairViewForm").form("clear");
			$("#r-id").val(selectedRows[0].carRunId);
			$("#r_parkId").val(selectedRows[0].parkId);
			$("#r-cityCode").val(selectedRows[0].cityCode);
			if (selectedRows[0].lpn.indexOf("•") < 0) {
				$("#r-lpn-1").val(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
				$("#r-lpn-2").html(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
			}else {
				$("#r-lpn-1").val(selectedRows[0].lpn);
				$("#r-lpn-2").html(selectedRows[0].lpn);
			}
			
			$("#carRepairView").dialog("open");
		}
	});
	// prepareToOffline 车辆预下线状态设置
	$("#prepareToOffline").bind("click", function() {
		$("#carPreOfflineViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if (selectedRows.length <= 0) {
			$.messager.alert("提示", "请选择要预下线的车辆", "error");
		}else if(selectedRows[0].preOffline=="1"){
			$.messager.alert("提示", "该车辆已预下线", "error");
		}
		else{
			 var _lpn =  selectedRows[0].lpn;
			 var _carStatus = selectedRows[0].carRunStatus;
			 var opreationType = "PREOFFLINE";
			 if(_carStatus == "ordered"||_carStatus=="return"||_carStatus=="useCar"){
				 
						$("#carPreOfflineViewForm").form("clear");
						$("#p-id").val(selectedRows[0].carRunId);
						$("#p_parkId").val(selectedRows[0].parkId);
						$("#p-cityCode").val(selectedRows[0].cityCode);
						$("#p-opreationType").val("PREOFFLINE");
						$("#p-lpn").val(selectedRows[0].lpn);
						if (selectedRows[0].lpn.indexOf("•") < 0) {
							$("#p-lpn-1").val(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
							$("#p-lpn-1").html(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
						}else {
							$("#p-lpn-1").val(selectedRows[0].lpn);
							$("#p-lpn-1").html(selectedRows[0].lpn);
						}
						$("#carPreOfflineView").dialog("open");
				 
			 }else {
				 // 车辆处于非预约(ordered),非用车(useCar),非还车(return)状态不设置预下线状态
				 $.messager.alert("提示", "非运营中车辆不能设置预下线状态", "error");
			 }
		}
	});
	// 车辆取消预下线状态returnToOnline
	$("#returnToOnline").bind("click", function() {
		$("#carRepairViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if (selectedRows.length <= 0) {
			$.messager.alert("提示", "请选择要取消预下线状态的车辆", "error");
		}else{
			 var _lpn =  selectedRows[0].lpn;
			 var _carStatus = selectedRows[0].carRunStatus;
			 var opreationType = "CANCEL_PREOFFLINE";
				 if(selectedRows[0].preOffline=="1"){
						$.messager.confirm("提示","确定要取消该车预下线状态吗?",function(r){
							if(r){
								$.post("changePreOnlineStatus",{lpn:_lpn,opreationType:opreationType},function(data){
									if(data == "succ"){
										$.messager.alert("提示", "车辆预下线设置成功", "info");
										$("#grid").datagrid("reload");
									}else{
										$.messager.alert("提示", "操作失败", "error");
										$("#grid").datagrid("reload");
									}
								},"text");
							}
						});
				 }else{
					 // 非预下线状态不能取消预下线
					 $.messager.alert("提示", "该车辆未设置预下线状态", "error");
				 }
		}
	});
	
	//car Accident view
	$("#btnAccident").bind("click", function() {
		$("#carAccidentViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if (selectedRows.length <= 0) {
			$.messager.alert("提示", "请选择事故车辆", "error");
		} else if (selectedRows[0].carRunStatus == "repair") {
			$.messager.alert("提示", "车辆已在维修中...", "error");
		}else if(selectedRows[0].carRunStatus == "maintain"){
			$.messager.alert("提示", "车辆已在维护中...", "error");
		}else{
			$("#carAccidentViewForm").form("clear");
			$("#accident-id").val(selectedRows[0].carRunId);
			if (selectedRows[0].lpn.indexOf("•") < 0) {
				$("#accident-lpn-1").val(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
				$("#accident-lpn-2").html(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
			}else {
				$("#accident-lpn-1").val(selectedRows[0].lpn);
				$("#accident-lpn-2").html(selectedRows[0].lpn);
			}
			
			$("#carAccidentView").dialog("open");
		}
		$("#memberCompensate").prop('checked', 'checked');
        $("#is_insurance").prop('checked', 'checked');
	});
	//构造车辆事故保存对话框
	$("#carAccidentView").dialog({
		width : "500",
		height : "400",
		top : "100",
		left : "400",
		modal : true,
		title : "车辆事故",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carAccidentViewForm").form("submit", {
					url : 'accident_car_save',
					onSubmit : function() {
						var myRes;
						$("input[name='memberCompensate']:checked").each(function(){
							myRes = $(this).val();
						})
						console.log(myRes)
						var money = $("#accident-money").val();
						if (myRes == 1 && money == "") {//如果由会员承担，那么必须填写赔偿金额
							$.messager.alert("提示","请填写赔偿金额","error");
							return false;
						}
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carAccidentView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carAccidentView").dialog("close");
			}
		} ]
	});

	
	
	//car Rescue view
	$("#btnRescue").bind("click", function() {
		$("#carRescueViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if (selectedRows.length <= 0) {
			$.messager.alert("提示", "请选择要救援的车辆", "error");
		}else if(selectedRows[0].carRunStatus == "accident"){
			$.messager.alert("提示", "车辆事故处理中...", "error");
		}else if(selectedRows[0].carRunStatus == "rescue"){
			$.messager.alert("提示", "车辆已在救援中...", "error");
		}else{
			console.log(selectedRows[0]);
			$("#carRescueViewForm").form("clear");
			$("#rescue-id").val(selectedRows[0].carRunId);
            var orderStatus = selectedRows[0].orderStatus;
            if (orderStatus != ""){
                $("#brand-name").html(selectedRows[0].brandName);
                $("#member-name").textbox("setValue",selectedRows[0].name);
                $("#member-phone").textbox("setValue",selectedRows[0].phone);
                var levelCode = selectedRows[0].levelCode;
                setLevelCode(levelCode);
                $("#order-id").textbox("setValue",selectedRows[0].orderId);
                setOrderStatus(orderStatus);
			}
            $("#rescue-address").textbox("setValue",selectedRows[0].carAddress);
			if (selectedRows[0].carRunLongitude != "" && selectedRows[0].carRunLatitude != ""){
                mapInit(selectedRows[0]);
			}
			if (selectedRows[0].lpn.indexOf("•") < 0) {
				$("#rescue-lpn-1").val(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
				$("#rescue-lpn-2").html(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
			}else {
				$("#rescue-lpn-1").val(selectedRows[0].lpn);
				$("#rescue-lpn-2").html(selectedRows[0].lpn);
			}
			$("#carRescueView").dialog("open");
		}
	});
	//构造车辆救援保存对话框
	$("#carRescueView").dialog({
		width : "800",
		height : "420",
		top : "100",
		left : "400",
		modal : true,
		title : "车辆救援",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carRescueViewForm").form("submit", {
					url : 'rescue_car_save',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carRescueView").dialog("close");
						}else if(result == "rescuring"){
							$.messager.alert("提示", "该车辆已经处于救援状态，请不要重复申请车辆救援", "error");
						}else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carRescueView").dialog("close");
			}
		} ]
	});
	
	// query
	$("#btnQuery").bind("click", function() {
		$("#grid").datagrid("load", {
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn' : $("#s-lpn").textbox("getValue"),
			'parkName' : $("#s-parkName").textbox("getValue"),
			'status':$("#e-status").combobox("getValue"),
			'preStatus':$("#e-repStatus").combobox("getValue"),
			'tfstatus':$("#e-tfcardStatus").combobox("getValue"),
			'isLook':$("#e-isLook").combobox("getValue"),
            'brandName':$("#s-brandName").textbox("getValue"),
            'areaName':$("#areaName").combobox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var param=   $('#grid').datagrid('options').queryParams;//查询参数
		var url = "export_carRun_list?"+jQuery.param(param);
		window.location.href = url ;
		
//		var cityCode = $("#scityCode").combobox("getValue");
//	    var lpn = $("#s-lpn").textbox("getValue");
//	    var status = $("#e-status").combobox("getValue");
//	    var parkName = $("#s-parkName").textbox("getValue");
//	    var preStatus = $("#e-repStatus").combobox("getValue");
//	    var tfstatus = $("#e-tfcardStatus").combobox("getValue");
//		$("#carRunForm").form("submit", {
//			url : "export_carRun_list?cityCode="+cityCode+"&lpn="+lpn+"&status="+status+"&parkName="+parkName+"&preStatus="+preStatus+"&tfstatus="+tfstatus,
//			onSubmit : function() {
//			},
//			success : function(result) {
//			}
//		});

	});

    // 剩余电量降序查询
	$("#upToDownQuery").bind("click", function() {
		$("#grid").datagrid("load", {
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn' : $("#s-lpn").textbox("getValue"),
			'parkName' : $("#s-parkName").textbox("getValue"),
			'status':$("#e-status").combobox("getValue"),
			'preStatus':$("#e-repStatus").combobox("getValue"),
            'tfstatus':$("#e-tfcardStatus").combobox("getValue"),
            'brandName':$("#s-brandName").textbox("getValue"),
            'areaName':$("#areaName").combobox("getValue"),
			'orderRule':'DESC'
		});
	});
	// 剩余电量升序查询
	$("#downToUpQuery").bind("click", function() {
		$("#grid").datagrid("load", {
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn' : $("#s-lpn").textbox("getValue"),
			'parkName' : $("#s-parkName").textbox("getValue"),
			'status':$("#e-status").combobox("getValue"),
			'preStatus':$("#e-repStatus").combobox("getValue"),
            'tfstatus':$("#e-tfcardStatus").combobox("getValue"),
            'brandName':$("#s-brandName").textbox("getValue"),
            'areaName':$("#areaName").combobox("getValue"),
			'orderRule':'ASC'
		});
	});
	
	// 设备上载时间升序查询
	$("#deviceUploadToUpQuery").bind("click", function() {
		$("#grid").datagrid("load", {
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn' : $("#s-lpn").textbox("getValue"),
			'parkName' : $("#s-parkName").textbox("getValue"),
			'status':$("#e-status").combobox("getValue"),
			'preStatus':$("#e-repStatus").combobox("getValue"),
            'tfstatus':$("#e-tfcardStatus").combobox("getValue"),
            'brandName':$("#s-brandName").textbox("getValue"),
			'deviceUploadOrderRule': 'ASC'
		});
	});
	// 小电瓶电压升序查询
	$("#batteryVToUpQuery").bind("click", function() {
		$("#grid").datagrid("load", {
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn' : $("#s-lpn").textbox("getValue"),
			'parkName' : $("#s-parkName").textbox("getValue"),
			'status':$("#e-status").combobox("getValue"),
			'preStatus':$("#e-repStatus").combobox("getValue"),
            'tfstatus':$("#e-tfcardStatus").combobox("getValue"),
            'brandName':$("#s-brandName").textbox("getValue"),
            'areaName':$("#areaName").combobox("getValue"),
			'batteryVOrderRule': 'ASC'
		});
	});
	$("#queryCarSpot").bind("click", function() {
		$("#getTotalParkGrid").datagrid("load", {
			'parkName' : $("#s-repairCarSpot").textbox("getValue")
		});
	});
	
	// clear
	$("#btnClear").bind("click", function() {
		/* $("#scarMrgForm").form("clear"); */
	});

	$("#btnUpgradet").bind("click", function() {

	});

	$("#btnHeartbeat").bind("click", function() {

	});

	$("#carParkView").dialog({
		width : "450",
		height : "200",
		top : "100",
		left : "400",
		modal : true,
		title : "网点",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carParkViewForm").form("submit", {
					url : 'save_park_info',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carParkView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carParkView").dialog("close");
			}
		} ]
	});

	// 设置所在网点
	$("#btnPark").bind("click",function(){  
		var selectedRows = $("#grid").datagrid("getSelections");//返回所有被选中的行
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择记录", "error");
		 }else{
			 var tmpArr = new Array();
			 var lpns = "";
			 for(var i=0; i<selectedRows.length; i++){
				 tmpArr[i] = selectedRows[i].carRunId;
				 if (i<selectedRows.length - 1) {
					 lpns = lpns + selectedRows[i].lpn + ",";
				 }else {
					 lpns = lpns + selectedRows[i].lpn;
				}
				 
			 }
			 $("#p-carid").val(tmpArr.toString());
			 $("#p-park-lpn").textbox("setValue", lpns);
			 var _url = 'park_allByPage?cityCode='+selectedRows[0].cityCode;  	
			 $('#p-parkId').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
				 	delay:500,// 1s延时查询
				 	editable : true,
					panelHeight: 'auto', 
					panelWidth: 300,
					idField: 'id',
					textField: 'name',
					url:_url,
					pageSize:10,
					required:true,
					columns : [ [ {
						field : 'name',
						title : '所属网点',
						width : '80%',
						align : 'center'
					}] ],
					pagination : true,//是否分页
					rownumbers : true,//序号
					keyHandler : {
						query : function(parkName) { // 动态搜索处理
							$('#p-parkId').combogrid("grid").datagrid('options').queryParams = 
								JSON.parse('{"parkName":"' + parkName + '"}');
							// 重新加载
							$('#p-parkId').combogrid("grid").datagrid("reload");
							$('#p-parkId').combogrid("setValue",parkName);
							 
						}
					}
    
				});
			 $("#carParkView").dialog("open");
			 	
		 }
	});

	$("#carGroupView").dialog({
		width : "450",
		height : "200",
		top : "100",
		left : "400",
		modal : true,
		title : "车辆组设置",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carGroupViewForm").form("submit", {
					url : 'set_car_group',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carGroupView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carGroupView").dialog("close");
			}
		} ]
	});

	$("#boxType").bind("click", function() {
		$("#carTypeViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if (selectedRows.length <= 0) {
			$.messager.alert("提示", "请选择记录", "error");
		}else{
			$("#t-lpn").val(selectedRows[0].lpn);
			$("#carTypeView").dialog("open");
		}
	});
	
	$("#carTypeView").dialog({
		width : "450",
		height : "200",
		top : "100",
		left : "400",
		modal : true,
		title : "设置车型",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carTypeViewForm").form("submit", {
					url : 'server_push_set_box_type',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#carTypeView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carTypeView").dialog("close");
			}
		} ]
	});
	
	
	//服务器推送重启后视镜
	$("#system").bind("click",function(){
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length <= 0) {
			$.messager.alert("提示", "请选择记录", "error");
		} else {
			$.ajax({
				type:"post",
				url:"server_push_reboot_system",
				data:{lpn:rows[0].lpn},
				success:function(result){
					if(result=="succ"){
						$.messager.alert("消息提示","重启后视镜成功！");
					}else{
						$.messager.alert("消息提示","重启后视镜失败！");
					}
				}
			})
		}
	})
	//服务器推送重启盒子
	$("#box").bind("click",function(){
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length <= 0) {
			$.messager.alert("提示", "请选择记录", "error");
		} else {
			$.ajax({
				type:"post",
				url:"server_push_reboot_box",
				data:{lpn:rows[0].lpn},
				success:function(result){
					if(result=="succ"){
						$.messager.alert("消息提示","重启盒子成功！");
					}else{
						$.messager.alert("消息提示","重启盒子失败！");
					}
				}
			})
		}
	});
	
	$("#restartCar").bind("click",function(){
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length <= 0) {
			$.messager.alert("提示", "请选择记录", "error");
		} else {
			$.ajax({
				type:"post",
				url:"server_push_start_car",
				data:{lpn:rows[0].lpn},
				success:function(result){
					if(result=="succ"){
						$.messager.alert("消息提示","车辆一键启动成功！");
					}else{
						$.messager.alert("消息提示","车辆一键启动失败！");
					}
				}
			})
		}
	})

	$("#getTotalParkView").dialog({
		width : "600",
		height : '490',
		top : "90",
		left:'400',
		modal:true,
		buttons : [{text : "确定",
					iconCls : "icon-save",
					handler : function() {
						var selectedRow = $("#getTotalParkGrid").datagrid("getSelections");
						if(selectedRow.length <= 0){
							$.messager.alert("提示", "请选择要关联的记录", "error");
				   		}else{

							$("#r_parkId").val("");
							$("#r-park").textbox('setValue','');
							$("#s-repairCarSpot").textbox('setValue','');
				   			var JsonData = selectedRow[0];
							$("#r_parkId").val(JsonData.id);
							$("#r-park").textbox('setValue',JsonData.name);
							$("#getTotalParkView").dialog("close");
				   		}
					}},{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#r_parkId").val("");
							$("#r-park").textbox('setValue',"");
							$("#s-repairCarSpot").textbox('setValue','');
							$("#getTotalParkGrid").datagrid('loadData',{total:0,rows:[]});
							$("#getTotalParkView").dialog("close");
						}
					}]
	});
	
	$("#getOrderInfoView").dialog({
		width : "800",
		height : '520',
		top : "90",
		left:'400',
		modal:true,
		buttons : [{text : "确定",
					iconCls : "icon-save",
					handler : function() {
						var selectedRow = $("#getOrderInfoGrid").datagrid("getSelections");
						if(selectedRow.length <= 0){
							$.messager.alert("提示", "请选择要关联的记录", "error");
				   		}else{
				   			var JsonData = selectedRow[0];
							$("#accident-relativeOrderId").textbox('setValue',JsonData.orderId);
				   		}
						$("#getOrderInfoView").dialog("close");
					}},{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#getOrderInfoView").dialog("close");
						}
					}]
	});
	
	//关联订单
	$(".getOrderInfo").bind("click",function(){
		var memberPhone = $("#accident-memberPhone").val();
        $("#getOrderDetail").form("reset");
		if (memberPhone == "") {
			$.messager.alert("提示","请填写会员手机号","error");
			return;
		}
		$("#getOrderInfoView").dialog({
			"title":"订单关联"
		});
        $("#getOrderInfoView").dialog("open")
		$("#getOrderInfoGrid").datagrid({
			width : '700',
			height : '300',
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			rownumbers : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			url : 'getOrderInfoByMemberPhone',
			queryParams:{
				"memberPhone":memberPhone,
				"beginTime":$("#s-bt").datetimebox('getValue'),
				"endTime":$("#s-et").datetimebox('getValue'),
				"lpn":$("#accident-lpn-1").val()
			},
			pageSize : 10,
			idField : 'orderId',
			pagination : true,
			rownumbers : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true

			}, {
				field : 'memberName',
				title : '会员姓名',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'memberPhone',
				title : '会员电话',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'orderId',
				title : '订单号',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'beginTime',
				title : '订单开始时间',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'endTime',
				title : '订单结束时间',
				width : $(this).width() * 0.25,
				align : 'center'
			}] ]
		});
	})

    $(".getRescueEmployeeInfo").bind("click",function(){
        $("#getRescueEmployeeInfoView").dialog({
            "title":"救援人员列表",
			"top":"200px",
			"left":"500px",
			"height":"500px"
        });
        $("#rescue-name").textbox('setValue','');
        $("#rescue-phone").textbox('setValue','');
        $("#getRescueEmployeeInfoView").dialog("open");
        $("#getRescueEmployeeInfoGrid").datagrid({
            width : '700',
            height : '300',
            fit : true,
            fitColumns : true,
            nowrap : false,
            striped : true,
            collapsible : true,
            rownumbers : true,
            singleSelect : true,
            selectOnCheck : true,
            checkOnSelect : true,
            url : 'getEmployeeInfo',
            queryParams:{
                'phone':$("#rescue-phone").val(),
                'name':$("#rescue-name").val()
            },
            pageSize : 10,
            pagination : true,
            rownumbers : true,
            columns : [ [ {
                field : 'ck',
                checkbox : true

            }, {
                field : 'name',
                title : '姓名',
                width : $(this).width() * 0.5,
                align : 'center'
            }, {
                field : 'phone',
                title : '电话',
                width : $(this).width() * 0.5,
                align : 'center'
            }] ]
        });
    })
    
	$("#queryOrderByPhone").bind("click",function(){
		$("#getOrderInfoGrid").datagrid("load",{
			'lpn':$("#accident-lpn-1").val(),
			'memberPhone':$("#accident-memberPhone").val(),
			'beginTime':$("#s-bt").datetimebox("getValue"),
			'endTime':$("#s-et").datetimebox("getValue"),
		});
	});

    $("#queryRescueEmployeeInfo").bind("click",function(){
        $("#getRescueEmployeeInfoGrid").datagrid("load",{
            'phone':$("#rescue-phone").val(),
            'name':$("#rescue-name").val()
        });
    });

    $("#getRescueEmployeeInfoView").dialog({
        top : "90",
        left:'400',
        modal:true,
        buttons : [{text : "确定",
            iconCls : "icon-save",
            handler : function() {
                var selectedRow = $("#getRescueEmployeeInfoGrid").datagrid("getSelections");
                if(selectedRow.length <= 0){
                    $.messager.alert("提示", "请选择救援人员", "error");
                    return;
                }else{
                    var JsonData = selectedRow[0];
                    $("#employeeId").val(JsonData.id)
                    $("#relativeEmployeeName").textbox('setValue',JsonData.name);
                }
                $("#getRescueEmployeeInfoView").dialog("close");
            }},{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#getRescueEmployeeInfoView").dialog("close");
            }
        }]
    });
//---------------------------------------------------------------------------------------------------
    
    $('#problemType').combobox({
        url:'getSysDicType',
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',
        valueField:'id',
        textField:'name',
        onHidePanel: function(){
            $("#rescueType").combobox("setValue",'');
            var id = $('#problemType').combobox('getValue');

            $.ajax({
                type: "POST",
                url: 'getSysDic?id=' + id,
                cache: false,
                dataType : "json",
                success: function(data){
                    $("#rescueType").combobox("loadData",data);
                }
            });
        }
    });

    $('#rescueType').combobox({
        editable:false, //不可编辑状态
        cache: false,
        panelHeight: 'auto',//自动高度适合
        valueField:'id',
        textField:'name',
        multiple:true,
		width:300
    });
});

function mapInit(data) {
    var point = new AMap.LngLat(data.carRunLongitude,data.carRunLatitude);
    var buildingLayer = new AMap.Buildings(); //实例化3D地图图层
    var trafficLayer = new AMap.TileLayer.Traffic({
        zIndex : 10
    }); //实时路况图层
    var roadNetLayer = new AMap.TileLayer.RoadNet({
        zIndex : 10
    }); //实例化路网图层
    var satellLayer = new AMap.TileLayer.Satellite({
        zIndex : 10
    }); //实例化卫星图
    var mapObj = new AMap.Map('dituContent', {
        center : point, //地图中心点
        level : 12
    });
    var polygonArr = new Array();//多边形覆盖物节点坐标数组
    mapObj.setCenter(new AMap.LngLat(data.carRunLongitude , data.carRunLatitude)); //设置地图中心点
	polygonArr.push([data.carRunLongitude , data.carRunLatitude]);
    var polygon = new AMap.Polygon({
        path: polygonArr,//设置多边形边界路径
        strokeColor: "#FF33FF", //线颜色
        strokeOpacity: 0.2, //线透明度
        strokeWeight: 3,    //线宽
        fillColor: "#1791fc", //填充色
        fillOpacity: 0.35//填充透明度
    });
    polygon.setMap(mapObj);
    mapObj.plugin([ "AMap.ToolBar", "AMap.OverView", "AMap.Scale",
        "AMap.MapType", "AMap.Geolocation" ], function() {
        //加载工具条
        tool = new AMap.ToolBar({
            direction : true,//隐藏方向导航
            ruler : true,//隐藏视野级别控制尺
            autoPosition : false
            //禁止自动定位
        });
        mapObj.addControl(tool);
        //加载鹰眼
        view = new AMap.OverView();
        mapObj.addControl(view);
        //加载比例尺
        scale = new AMap.Scale();
        mapObj.addControl(scale);
        //加载地图类型切换
        mapType = new AMap.MapType();
        //mapObj.addControl(mapType);
        //加载浏览器定位
        geolocation = new AMap.Geolocation();
        //mapObj.addControl(geolocation);
        //设置默认鼠标样式
        //mapObj.setDefaultCursor("url('images/openhand.cur'),pointer");
        //创建右键菜单
        contextMenu = new AMap.ContextMenu();
        //右键放大
        contextMenu.addItem("放大一级",function(){
            mapObj.zoomIn();
        },0);
        //右键缩小
        contextMenu.addItem("缩小一级",function(){
            mapObj.zoomOut();
        },1);
        contextMenu.addItem("缩放至全国范围",function(e){
            mapObj.setZoomAndCenter(4,new AMap.LngLat(108.946609,34.262324));
        },2);


        contextMenu.addItem("设为地图中心点",function(e){
            mapObj.setZoomAndCenter(12,new AMap.LngLat(lng,lat));
        },3);

        //地图绑定鼠标右击事件——弹出右键菜单
        AMap.event.addListener(mapObj,'rightclick',function(e){
            contextMenu.open(mapObj,e.lnglat);
            contextMenuPositon = e.lnglat;
            lng=contextMenuPositon.getLng();
            lat = contextMenuPositon.getLat();

        });

        var markers = [];
        marker = new AMap.Marker({
            position: [data.carRunLongitude,data.carRunLatitude],
            map: mapObj
        });
        markers.push(marker);

    });

}

