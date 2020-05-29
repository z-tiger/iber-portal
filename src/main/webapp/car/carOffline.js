$(function(){
	//查询链接
	$(document).keydown(function(event){
	    var cityCode = $("#cityCode").combobox("getValue");
	    var lpn =$("#lpn").textbox("getValue");
	    var reason = $("#reason").textbox("getValue");
	    var auditResult = $("#auditResult").combobox("getValue");
	    var offLineType = $("#offLineType").combobox("getValue");
	    var applicant = $("#applicant").textbox("getValue");
		if(event.keyCode==13){
			$('#dataGrid').datagrid('load',{
				cityCode:cityCode,
				lpn:lpn,
				reason:reason,
				auditResult:auditResult,
				offLineType:offLineType,
				applicant:applicant
        	});
		}
	});
	//查询链接
	$("#btnQuery").bind("click",function(){
		$("#dataGrid").datagrid("load",{
			'cityCode':$("#cityCode").combobox("getValue"),
			'lpn':$("#lpn").textbox("getValue"),
			'reason':$("#reason").textbox("getValue"),
			'auditResult':$("#auditResult").combobox("getValue"),
			'offLineType' : $("#offLineType").combobox("getValue"),
			'applicant':$("#applicant").textbox("getValue")
		});
	});
	//清空
	$("#btnClear").bind("click",function(){
		$("#queryCarOfflineApply").form("clear");
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var param=   $('#dataGrid').datagrid('options').queryParams;//查询参数
		var url = "export_carOffline_list?"+jQuery.param(param);
		window.location.href = url ;
	});
	
	$("#dataGrid").datagrid({
		title : '车辆状态申请',
		width : 'auto',
		height : 'auto',
		fit : true,
	//	fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		
		fitColumns : true,
		
		collapsible : true,
		pagination : true,
		rownumbers : true,
		
		singleSelect : true,
		
		
		url : 'car_offline_apply_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		pagination : true,
		rownumbers : true,
		queryParams: {
			'auditResult':$("#auditResult").combobox("getValue")
		},
		columns : [ [ 
		{
			field : 'ck',
			checkbox:true
		}/*,{
			field : 'id',
			title : '编号',
			width : $(this).width() * 0.08,
			align : 'center'
		}*/,{
			field : 'cname',
			title : '所属城市',
			width : $(this).width() * 0.06,
			align : 'center'
		},{
			field : 'preoffline',
			title : '',
			width : $(this).width() * 0.06,
			align : 'center',
			hidden : true
		},{
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val){
				if(val.indexOf("•")<0){
					return val.substring(0,2)+"•"+val.substring(2) 
				}else{
					return val;
				}
			}
		},{
			field : 'cstatus',
			title : '车辆当前状态',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "empty" || val == "" || val == null || val == "null" || val == "NULL") {return "空闲中"}
				else if(val == "repair") {return "维修中"}
				else if(val == "maintain") {return "维护中"}
				else if(val == "charging"){return "补电中"}
				else {
					if(rec.preOffline =="1"){
						return "运营中(<font color=red>预下线</font>)"
					}else{
						return "运营中"
					}
					}
			}
		},{
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
		},{
			field : 'smallBatteryVoltage',
			title : '小电瓶电压',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val, rec) {
				if(val < 100) {
					return "<font color=red>"+parseFloat(val/10)+"V</font>";
				}else{
					return parseFloat(val/10)+"V";
				}
			}
		},{
			field : 'cupdateTime',
			title : '设备信息上报时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'pname',
			title : '所属网点',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'offLineType',
			title : '申请状态',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter:function(val){
				if("1" == val){
					return "维修";
				}else if("2" == val){
					return "维护";
				}else if("3" == val){
					return "补电";
				}else if("4" == val){
					return "空闲";
				}else {
					return val;
				}
			}
		},{
			field : 'applicant',
			title : '申请人',
			width : $(this).width() * 0.05,
			align : 'center'
		},{
			field : 'applicantPhone',
			title : '手机号',
			width : $(this).width() * 0.06,
			align : 'center'
		},{
			field : 'createTime',
			title : '申请时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'reason',
			title : '变更原因',
			width : $(this).width() * 0.15,
			align : 'center'
		},{
			field : 'auditResult',
			title : '审核状态',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter:function(val,row,index){

					if("1"==val){
						return "审核通过";
					}else if("2"==val){
						return "审核不通过";
					}else if("0"==val){
						return "<font color='red'>未审核</font>";
					}else{
						return val;
					}
			}
		},{
			field : 'auditHuman',
			title : '审核人员',
			width : $(this).width() * 0.05,
			align : 'center'
		},{
			field : 'action',
			title : '操作',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter:function(val,row,index){
				return "<a href='javascript:void(0);' onclick='openOfflineAuditDialog("+row.id+","+row.auditResult+");'>审核</a>";
			}
		}] ]
	});
	
	$("#AuditApplyDialog").dialog( {
		title : "审核详情",
		width : "510",
		height : "auto",
		buttons : [ {
			text : "预下线",
			iconCls : "icon-save",
			handler : function() {
				$("#offlineApplyForm").form("submit",{
					url:"car_offline_pre",
					onsubmit:function(){
						 var flag = $(this).form("validate");
			             if(!flag){
			                $.messager.progress('close');
			               }
						return flag;	
					},
					success:function(result){
						$.messager.progress('close');
						if (result == "succ") {
							$.messager.alert("提示", "操作成功!", "info");
						    $("#dataGrid").datagrid("reload");
							$("#AuditApplyDialog").dialog("close");
						}else if(result == "noSucc"){
							$.messager.alert("提示", "非运营状态车辆不可在此操作", "error");
						    $("#dataGrid").datagrid("reload");
							//$("#AuditApplyDialog").dialog("close");
						}else if(result == "noPre"){
							$.messager.alert("提示", "此申请状态不可预下线", "error");
						    $("#dataGrid").datagrid("reload");
						}else if(result == "check"){
							$.messager.alert("提示", "此车辆已审核,请勿重复操作", "info");
						    $("#dataGrid").datagrid("reload");
							$("#AuditApplyDialog").dialog("close");
						} else{
							$.messager.alert("提示", "操作失败!", "error");
							$("#AuditApplyDialog").dialog("close");
						} 
					}
				});
				}
			},{
				text : "下线",
				iconCls : "icon-save",
				handler : function() {
					$("#offlineApplyForm").form("submit", {
						url : "car_offline_apply_check",
						onSubmit : function() {
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
							    $("#dataGrid").datagrid("reload");
								$("#AuditApplyDialog").dialog("close");
							}else if(result == "noOperate"){
								$.messager.alert("提示", "运营车辆不可下线，请点击预下线按钮！", "error");
							}else if(result == "noPre"){
								$.messager.alert("提示","维修，维护，补电车辆不可设置为下线！","error");
							}
							else if(result == "sameStatus"){
								$.messager.alert("提示","该车不可下线到相同的下线状态","error");
							}
							else if(result == "modifyFailed"){
								$.messager.alert("提示","维修中或维护中的车辆不可下线到补电中","error");
							}else if(result == "typeExcep"){
								$.messager.alert("提示","此申请状态不可下线！","error");
							}
							else if(result == "check"){
								$.messager.alert("提示", "此车辆已审核,请勿重复操作", "info");
							    $("#dataGrid").datagrid("reload");
								$("#AuditApplyDialog").dialog("close");
							}else{
								$.messager.alert("提示","操作失败！","error");
								$("#AuditApplyDialog").dialog("close");
							} 
						}
					});
					}
				},{
					text : "上线",
					iconCls : "icon-save",
					handler : function() {
						$("#offlineApplyForm").form("submit", {
							url : "car_online_apply_check",
							onSubmit : function() {
				                var flag = $(this).form("validate");
				                if(!flag){
				                	$.messager.progress('close');
				                }
								return flag;
							},
							success : function(result) {
								$.messager.progress('close');
								var data = eval('(' + result + ')');
								if (data.status == "succ") {
									$.messager.alert("提示", data.msg, "info");
									$("#dataGrid").datagrid("reload");
									$("#AuditApplyDialog").dialog("close");
								}else{
									$.messager.alert("提示",data.msg,"error");
								} 
							}
						});
					  }
					},{
					text : "拒绝",
					iconCls : "icon-save",
					handler : function() {
						$("#offlineApplyForm").form("submit", {
							url : "car_offline_apply_refuse",
							onSubmit : function() {
				                var flag = $(this).form("validate");
				                if(!flag){
				                	$.messager.progress('close');
				                }
								return flag;
							},
							success : function(result) {
								$.messager.progress('close');
								if (result == "succ") {
									$.messager.alert("提示", "操作成功!", "info");
								    $("#dataGrid").datagrid("reload");
									$("#AuditApplyDialog").dialog("close");
								}else if(result == "noSucc"){
									$.messager.alert("提示", "运营、空闲车辆不可在此操作", "error");
								    $("#dataGrid").datagrid("reload");
									//$("#AuditApplyDialog").dialog("close");
								}else if(result == "check"){
									$.messager.alert("提示", "此车辆已审核,请勿重复操作", "info");
								    $("#dataGrid").datagrid("reload");
									$("#AuditApplyDialog").dialog("close");
								}else{
									$.messager.alert("提示", "操作失败!", "error");
								    $("#dataGrid").datagrid("reload");
									$("#AuditApplyDialog").dialog("close");
								} 
							}
						});
						}
					}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#AuditApplyDialog").dialog("close");

			}
		}]
	});
});


//车辆上-下线审核
function openOfflineAuditDialog(_id,_auditResult){
	if("0"==_auditResult){
		initOfflineApplyInfo("l",_id);
		$("#AuditApplyDialog").dialog("open");
		$("#AuditApplyDialog").window('center');
	}else{
		$.messager.alert("提示", "该记录已审核过", "error");
	}

}
function initOfflineApplyInfo(_p,_id){
	$.ajax({
		type:'post',
		url:'car_offline_apply_info',
		data:{id:_id},
		success:function(data){
			var _json = eval("("+data+")");
			$("#"+_p+"-id").textbox("setValue",_id);
			$("#"+_p+"-cname").textbox("setValue",_json.cname);
			$("#"+_p+"-applicant").textbox("setValue",_json.applicant);
			var lpn = _json.lpn;
			if(lpn.indexOf("•")<0){
				$("#"+_p+"-lpn").textbox("setValue",lpn.substring(0,2)+"•"+lpn.substring(2));
			}else{
				$("#"+_p+"-lpn").textbox("setValue",lpn);//•
			}
			var val = _json.cstatus;
			if(val == "empty" || val == "" || val == null || val == "null" || val == "NULL") {
				$("#"+_p+"-cstatus").textbox("setValue","空闲中");
			}else if(val == "repair") {
				$("#"+_p+"-cstatus").textbox("setValue","维修中");
			}else if(val == "maintain") {
				$("#"+_p+"-cstatus").textbox("setValue","维护中");
			}else if(val == "charging"){
				$("#"+_p+"-cstatus").textbox("setValue","补电中");
			}else{
				$("#"+_p+"-cstatus").textbox("setValue","运营中");	
			}
			$("#"+_p+"-ctatus").textbox("setValue",_json.cstatus);
			$("#"+_p+"-restBattery").textbox("setValue",_json.restBattery);
			$("#"+_p+"-smallBatteryVoltage").textbox("setValue",_json.smallBatteryVoltage/10+"V");
			$("#"+_p+"-cupdateTime").textbox("setValue",_json.cupdateTime);
			var offLineType = _json.offLineType;
			if(offLineType == "1"){
				$("#"+_p+"-offLineType").textbox("setValue","维修");
			}else if(offLineType == "2"){
				$("#"+_p+"-offLineType").textbox("setValue","维护");
			}else if(offLineType == "3"){
				$("#"+_p+"-offLineType").textbox("setValue","补电");
			}else if(offLineType == "4"){
				$("#"+_p+"-offLineType").textbox("setValue","空闲");
			}else{
				$("#"+_p+"-offLineType").textbox("setValue",_json.offLineType);
			}
			$("#"+_p+"-pname").textbox("setValue",_json.pname);
			$("#"+_p+"-createTime").textbox("setValue",_json.createTime);
			$("#"+_p+"-reason").textbox("setValue",_json.reason);
			var result = _json.auditResult;
			if(result == "1"){
				$("#"+_p+"-auditResult").textbox("setValue","审核通过");
			}else if(result == "2"){
				$("#"+_p+"-auditResult").textbox("setValue","审核不通过");
			}else if(result == "0"){
				$("#"+_p+"-auditResult").textbox("setValue","未审核");
			}else{
				$("#"+_p+"-auditResult").textbox("setValue",_json.auditResult);
			}
		}
	})
}
