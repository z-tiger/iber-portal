$(function(){	
	//query
	$("#btnQuery").bind("click",function(){ 
		$("#carAccidentGrid").datagrid("load",{
			'lpn':$("#s-lpn").textbox("getValue"),
			'accidentStatus':$("#accidentStatus").combobox("getValue"),
			'memberName':$("#s-memberName").textbox("getValue"),
            'memberPhome':$("#s-memberPhome").textbox("getValue")
		});
	});
	//clear清空
	$("#btnClear").bind("click",function(){
		clearToolbar();
	});
	//导出excel
	$("#btnExport").bind("click", function() {
	    var lpn = $("#s-lpn").textbox("getValue");
	    var accidentStatus = $("#accidentStatus").combobox("getValue");
	    var memberName=$("#s-memberName").textbox("getValue");
	    var memberPhome=$("#s-memberPhome").textbox("getValue");
		var url="export_carAccident_list?accidentStatus="+encodeURI(accidentStatus)+"&memberName="+encodeURI(memberName)+"&memberPhome="+encodeURI(memberPhome)+"&lpn="+encodeURI(lpn);
		console.log(url);
		$("#carAccidentForm").form("submit", {
			url : "export_carAccident_list",
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	$('#carAccidentGrid').datagrid({
		title : '车辆事故管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'dataListCarAccident',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
			}
		}, {
			field : 'status',
			title : '事故处理状态',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1"){
					return "处理中"
				}else if(val=="0") {
					return "处理完毕"
				}
			}
		}, {
			field : 'responsiblePerson',
			title : '责任人',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'responsiblePersonPhone',
			title : '手机号码',
			width : $(this).width() * 0.06,
			align : 'center'
		},{
            field : 'memberName',
            title : '会员姓名',
            width : $(this).width() * 0.06,
            align : 'center'
        },{
			field : 'memberPhone',
			title : '会员手机号码',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'orderId',
			title : '关联订单号',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'money',
			title : '赔偿金额',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter:function(val, rec){
				return (parseFloat(val) / 100.0).toFixed(2);
			}
				
		},{
			field : 'handleByCustomer',
			title : '是否由会员处理',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val){
				if (val == 0) {
					return "由公司处理";
				}else {
					return "由会员处理";
				}
			}
		},{
			field : 'startTime',
			title : '处理开始时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'endTime',
			title : '处理结束时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'reason',
			title : '事故原因',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'result',
			title : '处理结果',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'isInsurance',
			title : '是否需报保险',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter:function(val,rec){
				if(val=="0"){
					return "不需要";
				}else if(val=="1"){
					return "需要";
				}
			}
		},{
			field : 'insuranceCode',
			title : '保险单号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
            field : 'predictTime',
            title : '预计上线时间',
            width : $(this).width() * 0.1,
            align : 'center'
        },{
            field : 'assessmentTime',
            title : '定损时间',
            width : $(this).width() * 0.1,
            align : 'center'
        },{
            field : 'assessmentMoney',
            title : '定损金额',
            width : $(this).width() * 0.1,
            align : 'center'
        },{
            field : 'responsibility',
            title : '责任判定',
            width : $(this).width() * 0.08,
            align : 'center'
        }] ],
		pagination : true,
		rownumbers : true
	});
	
	//修改救援相关信息
	$("#btnEdit").bind("click",function(){
		$("#carAccidentViewForm").form("clear");
		var selectedRows = $("#carAccidentGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else if(selectedRows[0].status == '0'){
			 $.messager.alert("提示", "该车辆事故记录已处理", "error");
		 }
		 else{
			 $("#e-id").val(selectedRows[0].id);
			 if (selectedRows[0].lpn.indexOf("•") < 0) {
				 $("#e-lpn-1").val(selectedRows[0].lpn.substring(0,2) + "•" + selectedRows[0].lpn.substring(2));
				 $("#e-lpn-2").html(selectedRows[0].lpn.substring(0,2) + "•" + selectedRows[0].lpn.substring(2));
			 }else {
				 $("#e-lpn-1").val(selectedRows[0].lpn);
				 $("#e-lpn-2").html(selectedRows[0].lpn);
			 }
			 
			 $("#e-responsiblePerson").textbox("setValue",selectedRows[0].responsiblePerson);
			 $("#e-responsiblePersonPhone").textbox("setValue",selectedRows[0].responsiblePersonPhone);
			 $("#e-reason").textbox("setValue",selectedRows[0].reason);
			 $("#e-memberPhone").textbox("setValue",selectedRows[0].memberPhone);
			 $("#accident-relativeOrderId").textbox("setValue",selectedRows[0].orderId);
			// $("#e-status").combobox("setValue",selectedRows[0].status); 
			 $("#accident-money").textbox("setValue",selectedRows[0].money/100);
             $("#assessment_time").textbox("setValue",selectedRows[0].assessmentTime);
             $("#predictTime").textbox("setValue",selectedRows[0].predictTime);
             $("#responsibility").textbox("setValue",selectedRows[0].responsibility);
			 if (selectedRows[0].handleByCustomer == 0) {
				$("#no").prop('checked',true);
			 }else {
				 $("#yes").prop('checked',true);
			 }
			 $("input[name='memberCompensate']:checked").val
			 $("#carAccidentView").dialog("open");
		 }
	});
	//car repair view
	$("#carAccidentView").dialog( {
		width : "450",
		height : "320",
		top : "100",
		left:"400",
		modal:true,
		title:"车辆事故",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carAccidentViewForm").form("submit",{
					url:'saveOrUpdateCarAccident',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carAccidentGrid").datagrid("reload");
							$("#carAccidentView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result=="num-wrong"){
							$.messager.alert("提示", "赔偿金额格式有误", "error");
						}
						else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carAccidentView").dialog("close");
			}
		}]
	});
	
	//关联订单
	$(".getOrderInfo").bind("click",function(){
		var memberPhone = $("#e-memberPhone").val();
        $("#orderForm").form("reset");
		if (memberPhone == "") {
			$.messager.alert("提示","请填写会员手机号","error");
			return;
		}
		$("#getOrderInfoView").dialog("open").dialog("setTitle", "订单关联");
		$("#getOrderInfoGrid").datagrid({
			width : '100%',
			height : '300',
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			url : 'getOrderInfoByMemberPhone',
			queryParams:{
				"memberPhone":memberPhone,
				"beginTime":$("#s-bt").datetimebox('getValue'),
				"endTime":$("#s-et").datetimebox('getValue'),
				"lpn":$("#e-lpn-1").val()
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
	
	$("#queryOrderByPhone").bind("click",function(){
		$("#getOrderInfoGrid").datagrid("load",{
			'lpn':$("#e-lpn-1").val(),
			'memberPhone':$("#e-memberPhone").val(),
			'beginTime':$("#s-bt").datetimebox("getValue"),
			'endTime':$("#s-et").datetimebox("getValue"),
		});
	});
	
	$("#getOrderInfoView").dialog({
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
				   			console.log(JsonData);
							$("#accident-relativeOrderId").textbox('setValue',JsonData.orderId);
							$('#orderForm').form('clear');
							$("#getOrderInfoView").dialog("close");
				   		}
					}},{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$('#orderForm').form('clear');
							$("#getOrderInfoView").dialog("close");
						}
					}]
	});
	//结束救援
	$("#btnResume").bind("click",function(){
		$("#carAccidentViewForm").form("clear");
		var selectedRows = $("#carAccidentGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要结束事故的车辆", "error");
		 }else if(selectedRows[0].status=="0"){
			 $.messager.alert("提示", "事故处理已完毕", "error");
		 }else{
             $("#endAccidentViewForm").form('clear');
			 $("#endAccidentView").dialog("open");
			 $("#end-id").val(selectedRows[0].id);

         }
	});

    $('#is_insurance').combobox({
		onChange : function(n,o){
            if (n==1){
                $("#insurance_code").textbox({ required: true});
            }else{
                $("#insurance_code").textbox({ required: false});
            }
        }
    });
	//弹出结束救援对话框
	$("#endAccidentView").dialog({
		width : "480",
		height : "320",
		top : "100",
		left:"400",
		modal:true,
		title:"结束事故",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#endAccidentViewForm").form("submit",{
					url:'endAccident',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carAccidentGrid").datagrid("reload");
							$("#endAccidentView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#endAccidentView").dialog("close");
			}
		}]
	});
	
	//删除记录
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#carAccidentGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			/*var _lpn =  selectedRows[0].lpn;*/
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("deleteCarAccidentById",{id:_id,},function(data){
						if(data == "success"){
							$.messager.alert("提示", "操作成功", "info");
							$("#carAccidentGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	
	// 弹出车辆维修任务窗口  (功能已屏蔽,车辆转维修功能可在车辆运营管理页面进行修改)
//	$("#btnToRepair").bind("click", function() {
//		$("#carRepairViewForm").form("clear");
//		var selectedRows = $("#carAccidentGrid").datagrid("getSelections");
//		if (selectedRows.length <= 0) {
//			$.messager.alert("提示", "请选择要维修的车辆", "error");
//		} else{
//			$("#carRepairViewForm").form("clear");
//			$("#r-carId").val(selectedRows[0].carId);
//			if (selectedRows[0].lpn.indexOf("•") < 0) {
//				 $("#r-lpn-1").val(selectedRows[0].lpn.substring(0,2) + "•" + selectedRows[0].lpn.substring(2));
//				 $("#r-lpn-2").html(selectedRows[0].lpn.substring(0,2) + "•" + selectedRows[0].lpn.substring(2));
//			 }else {
//				 $("#r-lpn-1").val(selectedRows[0].lpn);
//				 $("#r-lpn-2").html(selectedRows[0].lpn);
//			 }
//			$("#carRepairView").dialog("open");
//		}
//	});
	
	// 弹出保存对话框
	$("#carRepairView").dialog({
		width : "450",
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
					url : 'accident_to_repair_save',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carRepairView").dialog("close");
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
				$("#carRepairView").dialog("close");
			}
		} ]
	});
	
});
//清空查询栏
function clearToolbar(){
	$('#toolbar').form('clear');
}