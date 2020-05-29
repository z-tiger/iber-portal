$(function(){
	
	//table list view
	//table
	$('#carRepairGrid').datagrid({
		title : '车辆维修管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		
		collapsible : true,
		pagination : true,
		rownumbers : true,
		
		singleSelect : true,
		url : 'car_repair_list',
		queryParams:{
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.06,
			align : 'center'
		},

		{
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.06,
			align : 'center',
			styler: function(value,row){
				if (row.predictTime <new Date().Format("yyyy-MM-dd hh:mm:ss")&& row.endTime==""&& row.status=="1"){
					return 'color:red';
				}
				if(row.predictTime <new Date().Format("yyyy-MM-dd hh:mm:ss")&& row.endTime==""&& row.status=="2"){
					return 'color:red';
				}
			},
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
				
			}

		},{
                field : 'battery',
                title : '剩余电量',
                width : $(this).width() * 0.07,
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
                field : 'voltage',
                title : '小电瓶电压',
                width : $(this).width() * 0.06,
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
            },{
			field : 'parkName',
			title : '所在网点',
			width : $(this).width() * 0.08,
			align : 'center'
		},
		{
			field : 'status',
			title : '维修状态',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1" &&rec.statusCache=='1') {return "维修中"}
				else if(val == "2" &&rec.statusCache=='2') {return "维护中"}
				else if(val == "3" &&rec.statusCache=='3') {return "补电中"}
				else if(val == "0" &&rec.statusCache=='1') {return "已维修"}
                else if(val == "0" &&rec.statusCache=='2') {return "已维护"}
                else if(val == "0" &&rec.statusCache=='3') {return "已补电"}
			}
		}, {
			field : 'responsiblePerson',
			title : '下线人',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'responsiblePersonPhone',
			title : '下线人手机号',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
            field : 'recoverUser',
            title : '上线人',
            width : $(this).width() * 0.06,
            align : 'center'
        }, {
            field : 'recoverUserPhone',
            title : '上线人手机号',
            width : $(this).width() * 0.06,
            align : 'center'
        }, {
			field : 'reason',
			title : '下线原因',
            width : $(this).width() * 0.14,
            align : 'center'
        }, {
            field : 'recoverReason',
            title : '上线原因',
            width : $(this).width() * 0.14,
            align : 'center'
            }, {
			field : '	  ',
			title : '下线时长（小时）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec){
				if(rec.status == '0'){
					return getInervalHour(rec.startTime, rec.endTime);
				}else{
					var currDate = new Date().Format("yyyy-MM-dd hh:mm:ss");
					return getInervalHour(rec.startTime, currDate);
				}
			}
		},{
			field : 'startTime',
			title : '维修开始时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'endTime',
			title : '维修结束时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'predictTime',
			title : '预计上线时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'category',
			title : '网点类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val){
				if(val == 1){
					return "1S网点" ;
				}else if(val == 2){
					return "2S网点" ;
				}else if(val == 3){
					return "4S网点" ;
				}else if(val == null){
					return "" ;
				}
			}
		},{
			field : 'giName',
			title : '网点所属片区',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'latestUpdateTime',
			title : '设备上次上报时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'bluetoothNo',
			title : '蓝牙地址',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'tfCardStatus',
			title : 'TF卡状态',
			width : $(this).width() * 0.06,
			align : 'center',
            formatter : function(val, rec) {
                if (val == 1) {
                    return "<font color=green>正常</font>";
                } else {
                    return "<font color=red>已拔出</font>";
                }
            }
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	
	//car repair view
	$("#carRepairView").dialog( {
		width : "450",
		height : "320",
		top : "100",
		left:"400",
		modal:true,
		title:"车辆维修",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carRepairViewForm").form("submit",{
					url:'car_repair_edit',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carRepairGrid").datagrid("reload");
							$("#carRepairView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carRepairView").dialog("close");
			}
		}]
	});
	
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#timeOut").val("");  
		$("#repairStatus").combobox('enable');
		$("#scityCode").combobox('enable');
		$("#s-lpn").textbox('textbox').attr('readonly',false);
		$("#carRepairGrid").datagrid("load",{
			'lpn':$("#s-lpn").textbox("getValue"),
			'repairStatus':$("#repairStatus").combobox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
			'reason':$("#r-reason").textbox('getValue'),
			'category':$("#s-category").combobox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
	    var lpn = $("#s-lpn").textbox("getValue");
	    var repairStatus = $("#repairStatus").combobox("getValue");
	    var cityCode = $("#scityCode").combobox("getValue");
	    var reason = $("#r-reason").textbox('getValue');
	    var category = $("#s-category").combobox("getValue");
		$("#carRepairForm").form("submit", {
			url : "export_carRepair_list?cityCode="+cityCode+"&lpn="+lpn+"&repairStatus="+repairStatus+"&reason="+reason+"&category="+category,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	//超时未上线查询
	$("#outTimeQuery").bind("click",function(){
		$("#repairStatus").combobox("setValue","");
		$("#s-lpn").textbox("setValue","");
		$("#scityCode").combobox("setValue","");
		$("#carRepairGrid").datagrid("load",{
		 'timeOut':$("#timeOut").textbox("getValue")
		});
	});
	
	
	//clear
	$("#btnClear").bind("click",function(){
		/*$("#scarRepairForm").form("clear");*/
	});
	
	$("#btnEdit").bind("click",function(){
		var selectedRows = $("#carRepairGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else if(selectedRows[0].status=='0'){
			 $.messager.alert("提示", "该维修记录已结束", "error");
		 }
		 else{
			 $("#e-id").val(selectedRows[0].id);
			 $("#r_parkId").val("");
			 $("#r-park").textbox('setValue','');
			 if (selectedRows[0].lpn.indexOf("•") < 0) {
				 $("#e-lpn-1").val(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
				 $("#e-lpn-2").html(selectedRows[0].lpn.substring(0,2)+"•"+selectedRows[0].lpn.substring(2));
			 }else{
				 $("#e-lpn-1").val(selectedRows[0].lpn);
				 $("#e-lpn-2").html(selectedRows[0].lpn);
			 }
			 var rr = selectedRows[0].reason;
			 if(rr.indexOf("强制还车:")!=-1){
				 $("#e-responsiblePerson").textbox({readonly:true});
			 }else{
				 $("#e-responsiblePerson").textbox({readonly:false});
			 }
			 $("#e-responsiblePerson").textbox("setValue",selectedRows[0].responsiblePerson);
			 $("#e-responsiblePersonPhone").textbox("setValue",selectedRows[0].responsiblePersonPhone);
			 $("#e-reason").textbox("setValue",selectedRows[0].reason);
			 $("#carRepairView").dialog("open");
		 }
	});
	$(".getTotalParks").bind("click",function(){
		//关联维修网点
		$("#getTotalParkView").dialog("open").dialog("setTitle", "维修网点关联");
		//获取网点
		$("#getTotalParkGrid").datagrid({
			width : 'auto',
			height : 'auto',
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			rownumbers : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			queryParams : {
				parkName: ''
			},
			url : 'getTotalParks',
			pageSize : 10,
			idField : 'id',
			pagination : true,
			rownumbers : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true

			}, 
			{
				field : 'id',
				title : '网点Id',
				hidden : true,
				align : 'center'
			}, 
			{
				field : 'name',
				title : '网点名',
				width : $(this).width() * 0.35,
				align : 'center'
			}, {
				field : 'address',
				title : '网点地址',
				width : $(this).width() * 0.65,
				align : 'center'
			}] ]
		});
	});	
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

							$("#s-repairCarSpot").textbox('clear');
				   			var JsonData = selectedRow[0];
				   			console.log(JsonData);
							$("#r_parkId").val(JsonData.id);
							$("#r-park").textbox('setValue',JsonData.name);
							$("#getTotalParkView").dialog("close");
				   		}
					}},{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#s-repairCarSpot").textbox('clear');
							$("#getTotalParkView").dialog("close");
						}
					}]
	});
	$("#queryCarSpot").bind("click", function() {
		$("#getTotalParkGrid").datagrid("load", {
			'parkName' : $("#s-repairCarSpot").textbox("getValue")
		});
	});
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#carRepairGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			var _lpn =  selectedRows[0].lpn;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("car_repair_del",{id:_id,lpn:_lpn},function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#carRepairGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	
	//resume
	$("#btnResume").bind("click",function(){
		var selectedRows = $("#carRepairGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要恢复运营的车辆", "error");
		 }else if(selectedRows[0].status == "0"){
			 $.messager.alert("提示", "车辆已在运营中...", "error");
		 }else{
			 var _id =  selectedRows[0].id;
				$.messager.prompt("提示","确定要恢复运营吗?<br/>上线原因（必填）",function(r){
					if(r) {
                            $.post("car_resume", {
                                id: _id,
                                lpn: selectedRows[0].lpn,
                                recoverReason: r
                            }, function (data) {
                                if (data == "succ") {
                                    $.messager.alert("提示", "操作成功", "info");
                                    $("#carRepairGrid").datagrid("reload");
                                } else if (data == "employee") {
                                    $.messager.alert("提示", "员工正在使用中，待还车后操作", "error");
                                } else if (data == "closeCar") {
                                    $.messager.alert("提示", "车辆已关闭，不可恢复运营", "error");
                                } else {
                                    $.messager.alert("提示", "操作失败", "error");
                                }
                            }, "text");
					}else if(r == "") {
                        $.messager.alert("提示", "请填写上线原因", "error");
                    }
				});
		 }
	});
	
	//时间格式化function：
		Date.prototype.Format = function(format){ 
		var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
		}

		if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		}
		for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
		format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		 } 
		} 
		return format; 
		}
});
function getInervalHour(startDate, endDate) {
	if (startDate == "" || endDate == "") return 0 ; 
	var start = new Date(startDate.replace(/-/g, "/"));
	var end = new Date(endDate.replace(/-/g, "/"));
	var ms = end.getTime() - start.getTime() ;
	if (ms < 0) return 0 ;
	return Math.floor(ms/1000/60/60);
}
