$(function(){
	$('#grid').datagrid({
		title : '发票管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		pagination : true,
		rownumbers : true,
		onLoadSuccess : computeSum,
		url : 'invoice_list',
		queryParams:{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'invocieHead':$("#s-invocieHead").textbox("getValue"),
			'invoiceType':$("#s-invoiceType").combobox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue"),
			'invoiceStatus':$("#s-invoiceStatus").combobox("getValue")
		},
		pageSize : 30,
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
		}, {
			field : 'memberName',
			title : '会员名',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '会员电话',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'invoiceType',
			title : '发票类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				var result = "";
				if(null != val && val == 1){
						/*if(rec.personType == 1){
							result = "个人电子发票";
						}else{
							result = "企业电子发票";
						}*/
					result = "普通电子发票";
				}else if(null != val && val == 2){
					/*if(rec.personType == 1){
						result = "个人纸质发票";
					}else{
						result = "企业纸质发票";
					}*/
					result = "普通纸质发票";
				}else if(null != val && val == 3){
					result = "专用纸质发票";
				}
				return result;
			}
		}, {
			field : 'invocieHead',
			title : '发票抬头',
			width : $(this).width() * 0.06,
			align : 'center'
//		}, {
//			field : 'taxpayerCode',
//			title : '纳税人识别号',
//			width : $(this).width() * 0.06,
//			align : 'center'
//		}, {
//			field : 'invoiceAddress',
//			title : '发票电话地址',
//			width : $(this).width() * 0.06,
//			align : 'center'
//		}, {
//			field : 'bankDetail',
//			title : '开户行及账号',
//			width : $(this).width() * 0.06,
//			align : 'center'
		}, {
			field : 'serverType',
			title : '发票内容',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				var result = val;
				// '发票内容（1汽车服务费、2充电服务费）',
				if(null != val){
					switch (val) {
					case 2:
						result = "充电服务费";
						break;
					case 3:
					case 6:
					case 7:
						result = "汽车租赁+充电服务费";
						break;
					case 1:
					case 4:
					case 5:
						result = "汽车租赁费";
						break;
					default:
						break;
					}
				}
				return result;
			}
		}, {
			field : 'money',
			title : '发票金额',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter:function(val){
				var n = (val/100).toFixed(2);
				if(!isNaN(n)){
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
				}else{
					return "";
				}
			}
		}, {
			field : 'orderMoney',
			title : '汽车租赁费',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val){
				var n = (val/1.0).toFixed(2);
				if(!isNaN(n)){
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
				}else{
					return "";
				}
			}
		}, {
			field : 'chargingMoney',
			title : '充电服务费',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val){
				var n = (val/1.0).toFixed(2);
				if(!isNaN(n)){
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
				}else{
					return "";
				}
			}
		}, {
			field : 'postage',
			title : '邮费',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter:function(val){
				var n = (val/100).toFixed(2);
				if(!isNaN(n)){
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
				}else{
					return "0";
				}
			}
		}, {
			field : 'status',
			title : '开票状态',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				var result = "";
				//0待开票、1开票中、2已开票、3取消
				if(null != val){
					switch (val) {
					case 1:
						result = "待开票";
						break;
					case 2:
						result = "开票中";
						break;
					case 3:
						result = "已开票";
						break;
					case 4:
						result = "取消";
						break;
					case 5:
						result = "已驳回";
						break;
					default:
						break;
					}
				}
				return result;
			}
		}, {
			field : 'invoiceNo',
			title : '发票号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val, rec){
				var result = "";
				if(rec.invoiceNo != null && rec.invoiceNo != ""){
//					var json = eval("("+rec.invoiceNo+")");
//					if(json.tS != null  && json.tS != null){
//						result =json.tS ;
//					}
					result = val;
				}
				return result;
			}
		/*}, {
			field : 'invoiceNo',
			title : '充电服务费发票号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function( val, rec){
				var result = "";
				if(rec.invoiceNo != null && rec.invoiceNo != ""){
					var json = eval("("+rec.invoiceNo+")");
					if(json.charging != null  && json.charging != null){
						result = json.charging
					}
				}
				return result;
			}*/
		}, {
			field : 'createTime',
			title : '申请时间',
			width : $(this).width() * 0.08,
			align : 'center'
		/*}, {
			field : 'receiver',
			title : '收件人',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'receiverPhone',
			title : '收件联系电话',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'receiverAddress',
			title : '收件地址',
			width : $(this).width() * 0.14,
			align : 'center'
		}, {
			field : 'fastMailCompany',
			title : '快递公司名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'fastMailNo',
			title : '快递单号',
			width : $(this).width() * 0.12,
			align : 'center'*/
		}, {
			field : 'operator',
			title : '发票开具人员',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : '       ',
			title : '开票订单详情',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val,rec){
				if(rec.id != null && rec.id != ""){
					return "<a href='javascript:void(0);' onclick='toSeeOrderInfo(\""+rec.id+"\")'>订单详情</a>"
				}else{
					return "";
				}
			}
	/*	}, {
			field : 'payStatus',
			title : '支付状态',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val){
				var result = "";
				switch (val) {
				case 0:
					result = "<font color = 'red'>未支付</font>";
					break;
				case 1:
					result = "已支付";
					break;
				default:
					break;
				}
				return result;
			}
		}, {
			field : 'payType',
			title : '支付方式',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val){
				var result = "";
				switch (val) {
				case "U":
					result = "银联";
					break;
				case "A":
					result = "支付宝";
					break;
				case "B":
					result = "余额";
					break;
				case "WX":
					result = "微信";
					break;
				default:
					break;
				}
				return result;
			}*/
		}, {
			field : '    ',
			title : '操作',
			width : $(this).width() * 0.10,
			align : 'center',
			formatter : function(val,rec){
				var status = rec.status;
				var result = "<a href='javascript:void(0);' onclick='lookInvoice(\""+rec.id+"\")' >查看</a>";;
				var btnEdit = $("#btnEdit");
				if(btnEdit.length > 0){
					if(status != null){
						switch (status) {
						case 1:
							result += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='toUpdateInvoice(\""+rec.id+"\")'>接受申请</a>";
							if(rec.invoiceType == 1){
								result += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='toRefuseInvoice(\""+rec.id+"\")' >驳回</a>";
							}
							break;
						case 2:
							result += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='doneUpdateInvoice(\""+rec.id+"\", \""+rec.invoiceType+"\",\""+rec.memberName+"\",\""+rec.memberPhone+"\", \""+rec.cityName+"\",\""+rec.serverType+"\")'>发票上传</a>";
							break;
						case 5:
							result += "&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='lookRefuseInvoice(\""+rec.remark+"\")' >驳回原因</a>";
							break;
						default:
							break;
						}
					}
				}
				if(rec.id != null && rec.id != ""){
					return result;
				}else{
					return "";
				}
			}
		}

		]],
	});
	
	function computeSum() {
		 var rows = $('#grid').datagrid('getRows');// 获取当前的数据行
		var totalPostage = 0,postageSum=0;
		for ( var i = 0; i < rows.length; i++) {
			if(rows[i]['payStatus'] ==1 ){
				totalPostage += parseFloat(rows[i]['postage']);
			}
			postageSum = rows[i]['postageSum'];
		}
		var totalPostageVal = Number(totalPostage);
		if (!isNaN(parseFloat(totalPostageVal))) {
			totalPostageVal = totalPostageVal.toFixed(2);
		}
		
		var postageSumVal = Number(postageSum);
		if (!isNaN(parseFloat(postageSumVal))) {
			postageSumVal = postageSumVal.toFixed(2);
		}
		
		//新增一行显示统计信息
		$('#grid').datagrid('appendRow', {
			cityName : "已支付合计",
			postage : totalPostageVal
		});
		// 使用同步方法

		$('#grid').datagrid('appendRow', {
			cityName : "已支付总合计",
			postage : postageSumVal
		});
	}
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("clearSelections");
		$("#grid").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'invocieHead':$("#s-invocieHead").textbox("getValue"),
			'invoiceType':$("#s-invoiceType").combobox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue"),
			'invoiceStatus':$("#s-invoiceStatus").combobox("getValue")
		});
	});
	
	$("#btnEdit").bind("click",function(){
		clearInvoice();
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-mailId").val(selectedRows[0].mailInfoId);
			$("#e-memberName").html(selectedRows[0].memberName);
			$("#e-memberPhone").html(selectedRows[0].memberPhone);
			$("#e-invoiceType").combobox("setValue",selectedRows[0].invoiceType);
			$("#e-personType").combobox("setValue",selectedRows[0].personType);
			$("#e-invocieHead").textbox("setValue",selectedRows[0].invocieHead);
//			if(selectedRows[0].invoiceNo != null && selectedRows[0].invoiceNo != ""){
//				var invoicejson = eval("("+selectedRows[0].invoiceNo+")");
//				$("#e-invoiceNo1").textbox("setValue",invoicejson.tS);
//				$("#e-invoiceNo2").textbox("setValue",invoicejson.charging);
//			}
			
			$("#e-invoiceNo").textbox("setValue",selectedRows[0].invoiceNo);
			$("#e-fastMailCompany").textbox("setValue",selectedRows[0].fastMailCompany);
			$("#e-fastMailNo").textbox("setValue",selectedRows[0].fastMailNo);
			
			var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
			var money = (selectedRows[0].money/100).toFixed(2);
			$("#e-money").textbox("setValue",money.replace(re, "$1,"));
			
			var postage = (selectedRows[0].postage/100).toFixed(2);
			$("#e-postage").html(postage.replace(re, "$1,"));
			
			$("#e-receiver").textbox("setValue",selectedRows[0].receiver);
			$("#e-receiverPhone").textbox("setValue",selectedRows[0].receiverPhone);
			$("#e-address").textbox("setValue",selectedRows[0].receiverAddress);
			
			$("#e-serverType").combobox("setValue",selectedRows[0].serverType);
			//设置地区
			$("#e-province").textbox("setValue",selectedRows[0].province);
			$("#e-city").textbox("setValue", selectedRows[0].city);
			$("#e-area").textbox("setValue",selectedRows[0].area);
			var serverType = selectedRows[0].serverType;
			switch (serverType) {
			case 1:
				$(".car_rent_invoice").show();
				$(".charging_invoice").hide()
				break;
			case 2:
				$(".car_rent_invoice").hide();
				$(".charging_invoice").show()
				break;
			default:
				$(".car_rent_invoice").show();
				$(".charging_invoice").show()
				break;
			}
			var invoiceType = selectedRows[0].invoiceType;
			$("#e-elecInvoiceUrl").html("");
			$("#e-elecInvoiceUrl").attr("href","");
			switch (invoiceType) {
			case 1:
				$(".ele_invoice_info").show();
				$(".paper_invoice_info").hide();
//				if(selectedRows[0].elecInvoiceUrl != null && selectedRows[0].elecInvoiceUrl != ""){
//					var elecUrlData = eval("("+selectedRows[0].elecInvoiceUrl+")");
//					$("#e-elecInvoiceUrl1").attr("href",elecUrlData.tS);
//					$("#e-elecInvoiceUrl1").html(elecUrlData.tS);
//					$("#e-elecInvoiceUrl2").attr("href",elecUrlData.charging);
//					$("#e-elecInvoiceUrl2").html(elecUrlData.charging);
//				}
				$("#e-elecInvoiceUrl").attr("href",selectedRows[0].elecInvoiceUrl);
				$("#e-elecInvoiceUrl").html(selectedRows[0].elecInvoiceUrl);
				/*$(".car_ele_invoice").show();
				$(".charging_ele_invoice").show()
				switch (serverType) {
				case 1:
					$(".car_ele_invoice").show();
					$(".charging_ele_invoice").hide()
					break;
				case 2:
					$(".car_ele_invoice").hide();
					$(".charging_ele_invoice").show()
					break;
				default:
					break;
				}*/
				break;
			case 2:
				$(".ele_invoice_info").hide();
				$(".paper_invoice_info").show();
				break;
			default:
				break;
			}
			$("#editView").dialog({title:"编辑发票申请"});
			$("#editView").dialog("open");		
		}
	});
	
	
	$("#editView").dialog( {
		width : "680",
		height : "550",
		top : "80",
		modal:true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editForm").form("submit", {
					url : "updateInvoice",
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
						var data = eval('(' + result + ')');
						if (data.status == "success") {
							$.messager.alert("提示", data.msg, "info");
							$("#editView").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "info");
						} 
					}
				});
				
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#editView").dialog("close");		
			}
		}]
	});
	
	$("#refuseEditView").dialog({
		width : "500",
		height : "200",
		top : "80",
		modal:true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#refuseEditForm").form("submit", {
					url : "refuseInvoice",
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
						var data = eval('(' + result + ')');
						if (data.status == "success") {
							$.messager.alert("提示", "驳回成功", "info");
							$("#refuseEditView").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", "驳回失败", "info");
						} 
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
					$("#refuseEditView").dialog("close");		
			}
		}]
	});
	
	
	//构造保存对话框
	$("#paperEditView").dialog( {
		width : "560",
		height : "400",
		top : "80",
		modal:true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#paperEditForm").form("submit", {
					url : "sendPaperInvoice",
					onSubmit : function() {
						$.messager.progress({
		                    text:"正在加载，请稍等！"
		                });
					/*	var serverType = $("#p-serverType").val();
						if(serverType){
							switch (serverType) {
							case "1":
								var invoiceNo1 = $("#p-invoiceNo1").textbox("getValue");
								if(!(invoiceNo1 != undefined && invoiceNo1 != '')){
									$.messager.alert("提示","发票号不能为空","info");
									$.messager.progress('close'); 
									return false;
								}
								
								break;
							case "2":
								var invoiceNo2 = $("#p-invoiceNo2").textbox("getValue");
								if(!(invoiceNo2 != undefined && invoiceNo2 != '')){
									$.messager.alert("提示", "发票号不能为空","info");
									$.messager.progress('close'); 
									return false;
								}
								break;
							default:
								var invoiceNo1 = $("#p-invoiceNo1").textbox("getValue");
								var invoiceNo2 = $("#p-invoiceNo2").textbox("getValue");
								if(!(invoiceNo1 != undefined && invoiceNo1 != '') || !(invoiceNo2 != undefined && invoiceNo2 != '')){
									$.messager.alert("提示", "发票号不能为空","info");
									$.messager.progress('close'); 
									return false;
								}
								break;
							}
						}*/
						
						var invoiceNo = $("#p-invoiceNo").textbox("getValue");
						if(!(invoiceNo != undefined && invoiceNo != '')){
							$.messager.alert("提示","发票号不能为空","info");
							$.messager.progress('close'); 
							return false;
						}
						
		                var flag = $(this).form("validate");
		                if(!flag){
		                	$.messager.progress('close'); 
		                }
						return flag;
					},
					success : function(result) {
						$.messager.progress('close');
						var data = eval('(' + result + ')');
						if (data.status == "success") {
							$.messager.alert("提示", data.msg, "info");
							$("#paperEditView").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "info");
						} 
					}
				});
				
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#paperEditView").dialog("close");		
		}
		}]
	});
	
	
	//构造保存对话框
	$("#lookEditView").dialog( {
		width : "650",
		height : "500",
		top : "80",
		modal:true
	});
	
	//构造保存对话框
	$("#eleEditView").dialog( {
		width : "480",
		height : "400",
		top : "100",
		modal:true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#eleEditForm").form("submit", {
					url : "sendEleInvoice",
					onSubmit : function() {
						$.messager.progress({
							text:"正在加载，请稍等！"
						});
						/*var serverType = $("#ee-serverType").val();
						if(serverType){
							switch (serverType) {
							case "1":
								var invoiceNo1 = $("#ee-invoiceNo1").textbox("getValue");
								if(!(invoiceNo1 != undefined && invoiceNo1 != '')){
									$.messager.alert("提示", "发票号不能为空","info");
									$.messager.progress('close'); 
									return false;
								}
								
								break;
							case "2":
								var invoiceNo2 = $("#ee-invoiceNo2").textbox("getValue");
								if(!(invoiceNo2 != undefined && invoiceNo2 != '')){
									$.messager.alert("提示","发票号不能为空","info");
									$.messager.progress('close'); 
									return false;
								}
								break;
							default:
								var invoiceNo1 = $("#ee-invoiceNo1").textbox("getValue");
								var invoiceNo2 = $("#ee-invoiceNo2").textbox("getValue");
								if(!(invoiceNo1 != undefined && invoiceNo1 != '') || !(invoiceNo2 != undefined && invoiceNo2 != '')){
									$.messager.alert("提示","发票号不能为空","info");
									$.messager.progress('close'); 
									return false;
								}
								break;
							}
						}*/
						
						var invoiceNo = $("#ee-invoiceNo").textbox("getValue");
						if(!(invoiceNo != undefined && invoiceNo != '')){
							$.messager.alert("提示", "发票号不能为空","info");
							$.messager.progress('close'); 
							return false;
						}
						var flag = $(this).form("validate");
						if(!flag){
							$.messager.progress('close'); 
						}
						return flag;
					},
					success : function(result) {
						$.messager.progress('close');
						var data = eval('(' + result + ')');
						if (data.status == "success") {
							$.messager.alert("提示", data.msg, "info");
							$("#eleEditView").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "info");
						} 
					}
				});
				
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#eleEditView").dialog("close");		
			}
		}]
	});
	
	$("#getOrderInfoView").dialog({
		width : "800",
		height : '500',
		top : "90",
		left:'400',
		modal:true,
	});
		
	//query
	$("#btnQueryOrder").bind("click",function(){
		$("#getOrderInfoGrid").datagrid("load",{
			"id":$("#s-invoiceId").val(),
			"orderId":$('#s-orderId').textbox("getValue"),
			"orderType":$('#s-orderType').combobox("getValue")
		});
	});
	
	//专车收入查询导出excel链接
	$("#btnExport").bind("click",function(){
  
		var cityCode = $("#s-cityCode").combobox("getValue");
		var name = $("#s-name").textbox("getValue");
		var invocieHead = $("#s-invocieHead").textbox("getValue");
		var invoiceType = $("#s-invoiceType").combobox("getValue");
		var bt = $("#s-bt").datetimebox("getValue");
		var et = $("#s-et").datetimebox("getValue");
		var invoiceStatus = $("#s-invoiceStatus").combobox("getValue");
	    $("#queryTimeLease").form("submit", {
					url : "invoice_list_excel?name="+name+"&cityCode="+cityCode+"&invocieHead="+invocieHead+"&invoiceType="+invoiceType+"&bt="+bt+"&et="+et+"&invoiceStatus="+invoiceStatus,
					onSubmit : function() {
//						
					},
					success : function(result) {
					}
				});
	    });	
});

	/**
	 * 更新开票状态
	 * 
	 * @param id
	 */
	function toUpdateInvoice(id) {
		$.messager.confirm("提示","确定要将申请状态修改为开票中吗?",function(r){
      		if(r){
				$.ajax({
					type : "post",
					url : "updateMemberInvoice",
					data : {id : id	},
					success : function(result) {
						var data = result;
						if (data.status == "success") {
							$.messager.alert("提示", data.msg, "info");
							$("#grid").datagrid( "load",
									{   'cityCode' : $("#s-cityCode").combobox("getValue"),
										'name' : $("#s-name").textbox("getValue"),
										'invocieHead' : $("#s-invocieHead").textbox("getValue"),
										'invoiceType' : $("#s-invoiceType").combobox( "getValue"),
										'bt' : $("#s-bt").datetimebox("getValue"),
										'et' : $("#s-et").datetimebox("getValue"),
										'invoiceStatus' : $("#s-invoiceStatus").combobox("getValue")
									});
						} else {
							$.messager.alert("提示", data.msg, "info");
						}
					}
				});
      		}
		});
	}
	
	function clearInvoice(){
		 $("#ee-memberName").html("");
		 $("#ee-cityName").html("");
		 $("#ee-memberPhone").html("");
		 $("#ee-id").val("");
		 $("#ee-invoiceNo").textbox("setValue","");
		 $("#ee-boxfile1").val("");
		 
		 $("#p-cityName").html("");
		 $("#p-invoiceNo").textbox("setValue","");
//		 $("#p-invoiceNo2").textbox("setValue","");
		 $("#p-memberName").html("");
		 $("#p-memberPhone").html("");
		 $("#p-id").val("");
		 $("#p-fastMailNo").textbox("setValue","");
		 $("#p-fastMailCompany").textbox("setValue","");
		 
		 $("#l-elecInvoiceUrl").attr("href","");
		 $("#l-elecInvoiceUrl").html("");
		 $("#l-fastMailNo").html("");
		 $("#l-fastMailCompany").html("");
		 $("#l-memberName").html("");
		 $("#l-memberPhone").html("");
		 $("#l-invoiceNo").html("");
		 $("#l-personType").html("");
		 $("#l-invoiceType").html("");
		 $("#l-invocieHead").html("");
		 $("#l-serverType").html("");
		 $("#l-taxpayerCode").html("");
		 $("#l-invoiceAddress").html("");
		 $("#l-bankDetail").html("");
		 $("#l-invoiceNo").html("");
		 $("#l-postage").html("");
		 $("#l-money").html("");
		 $("#l-chargingMoney").html("");
		 $("#l-orderMoney").html("");
		 $("#l-province").html("");
		 $("#l-city").html("");
		 $("#l-area").html("");
		 $("#l-address").html("");
		 $("#l-receiver").html("");
		 $("#l-receiverPhone").html("");
			
		 
		 
		 $('#eleEditForm').form('clear');
		 $('#paperEditForm').form('clear');
		 $('#lookEditForm').form('clear');
		 $('#editView').form('clear');
	}
	/**
	 * 已经开票 更新开票信息
	 * 
	 * @param id
	 */
	function doneUpdateInvoice(id, invoiceType, memberName, memberPhone, cityName, serverType) {
		clearInvoice();
		if (invoiceType == '1') {//电子发票
			 $("#ee-cityName").html(cityName);
			 $("#ee-memberName").html(memberName);
			 $("#ee-memberPhone").html(memberPhone);
			 $("#ee-id").val(id);
			 $("#ee-serverType").val(serverType);
			 $("#eleEditView").dialog({title:"编辑电子发票信息"});
			 $("#eleEditView").dialog("open");	
		} else {
			 $("#p-cityName").html(cityName);
			 $("#p-memberName").html(memberName);
			 $("#p-memberPhone").html(memberPhone);
			 $("#p-id").val(id);
			 $("#p-serverType").val(serverType);
			 $("#paperEditView").dialog({title:"编辑纸质发票信息"});
			 $("#paperEditView").dialog("open");	
		}
//		$(".car_rent_invoice").show();
//		$(".charging_invoice").show()
//		switch (serverType) {
//		case "1":
//			$(".car_rent_invoice").show();
//			$(".charging_invoice").hide()
//			break;
//		case "2":
//			$(".car_rent_invoice").hide();
//			$(".charging_invoice").show()
//			break;
//		default:
//			break;
//		}
	}
	
	function toSeeOrderInfo(id){
		
		$("#getOrderInfoGrid").html("");
		$('#s-orderId').textbox("setValue","")
		$('#s-orderType').textbox("setValue","")
		$("#s-invoiceId").val("")
		
		$("#s-invoiceId").val(id)
		$("#getOrderInfoView").dialog("open").dialog("setTitle", "开票订单详情");
		$("#getOrderInfoGrid").datagrid({
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
			url : 'getOrderInfoList',
			queryParams:{ 
				"id":id,
				"orderId":$('#s-orderId').textbox("getValue"),
				"orderType":$('#s-orderType').combobox("getValue")
			},
			pageSize : 10,
			idField : 'orderId',
			pagination : true,
			rownumbers : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'orderId',
				title : '订单号',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'orderTime',
				title : '订单时间',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'lpn',
				title : '车牌',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'orderType',
				title : '订单类型',
				width : $(this).width() * 0.25,
				align : 'center',
				formatter :function(val){
					if('TS' ==val){return "时租";}else if('lr'==val){return '长租';} else if('charging'== val){return '充电';}
				}
			}, {
				field : 'payMoney',
				title : '订单金额',
				width : $(this).width() * 0.25,
				align : 'center'
			}] ]
		});
	}

	function getPersonType(personType){
		var result = "";
		switch (personType) {
		case 1:
			result = "个人";
			break;
		case 2:
			result = "企业";
			break;
		default:
			break;
		}
		return result;
	}
	
	function getInvoiceType(invoiceType){
		var result = "";
		switch (invoiceType) {
		case 1:
			 result = "普通电子发票";
			break;
		case 2:
			 result = "普通纸质发票";
			break;
		case 3:
			result = "专用纸质发票";
			break;
		default:
			break;
		}
		return result;
	}
	
	function getServerType(serverType){
		var result = "";
		switch (serverType) {
		case 1:
		case 4:
		case 5:
			 result = "汽车服务费";
			break;
		case 2:
			 result = "充电服务费";
			break;
		case 3:
		case 6:
		case 7:
			 result = "汽车+充电服务费";
			break;
		default:
			break;
		}
		return result;
	}
	/**
	 * 驳回
	 * 
	 * @param id
	 */
	function toRefuseInvoice(id){
		$("#refuseEditView").dialog({title:"驳回发票申请"});
		$("#refuse-id").val(id);
		$("#refuse-remark").textbox("setValue","");
		$("#refuseEditView").dialog("open");
	}
	function lookRefuseInvoice(remark){
		$("#d-refuse-remark").textbox("setValue","");
		$("#refuseDetailsView").dialog({
			width : "500",
			height : "200",
			top : "80",
			modal:true,
			title:"驳回原因"});
		$("#d-refuse-remark").textbox("setValue", remark);
		$("#refuseDetailsView").dialog("open");
	}
	function lookInvoice(id){
		clearInvoice();
		$.ajax({
			type : "post",
			url : "selectInvoice",
			data : {id : id	},
			success : function(result) {
				var data = result;
				if (data.status == "success") {
					if(data.data.invoiceType == 1){
						$("#lookEditView").dialog({title:"查看电子发票信息"});
					}else{
						$("#lookEditView").dialog({title:"查看纸质发票信息"});
					}
					
					$("#l-memberName").html(data.data.memberName);
					$("#l-memberPhone").html(data.data.memberPhone);
					$("#l-personType").html(getPersonType(data.data.personType));
					$("#l-invoiceType").html(getInvoiceType(data.data.invoiceType));
					$("#l-invocieHead").html(data.data.invocieHead);
					$("#l-serverType").html(getServerType(data.data.serverType));
					$("#l-taxpayerCode").html(data.data.taxpayerCode);
					$("#l-invoiceAddress").html(data.data.invoiceAddress);
					$("#l-bankDetail").html(data.data.bankDetail);
					$("#l-invoiceNo").html(data.data.invoiceNo);
					
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					var money = (data.data.money/100).toFixed(2);
					if(!isNaN(money)){
						$("#l-money").html(money.replace(re, "$1,"));
					}else{
						$("#l-money").html("");
					}
					
					var postage = (data.data.postage/100).toFixed(2);
					if(!isNaN(postage)){
						$("#l-postage").html(postage.replace(re, "$1,"));
					}else{
						$("#l-postage").html("");
					}
					
					if(data.data.orderMoney)
						$("#l-orderMoney").html((data.data.orderMoney).toFixed(2));
					if(data.data.chargingMoney)
						$("#l-chargingMoney").html((data.data.chargingMoney).toFixed(2));
					
//					if(data.data.elecInvoiceUrl != null && data.data.elecInvoiceUrl != ""){
//						var elecUrlData = eval("("+data.data.elecInvoiceUrl+")");
//						$("#l-elecInvoiceUrl1").attr("href",elecUrlData.tS);
//						$("#l-elecInvoiceUrl1").html(elecUrlData.tS);
//						$("#l-elecInvoiceUrl2").attr("href",elecUrlData.charging);
//						$("#l-elecInvoiceUrl2").html(elecUrlData.charging);
//					}
					$("#l-elecInvoiceUrl").attr("href",data.data.elecInvoiceUrl);
					$("#l-elecInvoiceUrl").html(data.data.elecInvoiceUrl);
//					if(data.data.invoiceNo && data.data.invoiceNo != null){
//						var invoiceData = eval("("+data.data.invoiceNo+")");
//						$("#l-invoiceNo1").html(invoiceData.tS);
//						$("#l-invoiceNo2").html(invoiceData.charging);
//					}
					
					$("#l-province").html(data.data.province);
					$("#l-city").html(data.data.city);
					$("#l-area").html(data.data.area);
					
					$("#l-address").html(data.data.receiverAddress);
					$("#l-receiver").html(data.data.receiver);
					$("#l-receiverPhone").html(data.data.receiverPhone);
					
					$("#l-fastMailNo").html(data.data.fastMailNo);
					$("#l-fastMailCompany").html(data.data.fastMailCompany);
					
					
					switch (data.data.invoiceType) {
					case 1:
						$(".paper_invoice_info").hide();
						$(".ele_invoice_info").show();
						break;
					case 2:
						$(".paper_invoice_info").show();
						$(".ele_invoice_info").hide();
						break;
					default:
						$(".paper_invoice_info").show();
						$(".ele_invoice_info").hide();
						break;
					}
					$("#lookEditView").dialog("open");
					
				} else {
					$.messager.alert("提示", data.msg, "info");
				}
			}
		});
	}
	
	
	
	