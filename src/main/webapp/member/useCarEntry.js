$(function(){
	
	$("#usrCarEntryListTable").datagrid({
		title : '会员违章结案',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'use_car_entry_data',
		queryParams:{
			'cityCode':$("#s-cityCode").combobox("getValue"),
		    'custName':$("#s-custName").textbox("getValue"),
		    'custPhone':$("#s-custPhone").textbox("getValue"),
		    'lpn':$("#s-lpn").textbox("getValue"),
		    'bt':$("#s-bt").datetimebox("getValue"),
		    'et':$("#s-et").datetimebox("getValue"),
		    'isTrafficCitation':$("#s-isTrafficCitation").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'pId',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		},  {
			field : 'cityCodeName',
			title : '区域',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'orderId',
			title : '订单号',
			width : $(this).width() * 0.15,
			align : 'center'
		},{
			field : 'custName',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'custPhone',
			title : '会员手机',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0){
					return val.substring(0,2) +"•"+val.substring(2);
				}else {
					return val;
				}
			}
		},{
			field : 'engineno',
			title : '发动机号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'classno',
			title : '车架号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'payMoney',
			title : '消费金额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(null != val && val !='' && val != 'null' && val !='NULL'){
					var tmp = val/100;
					if(tmp == 0){
						return "0.00";
					}else{
						n = tmp.toFixed(2);
						 var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
					}
				}else{
					if(val == 0){
						return "0.00";
					}else{
						return val;
					}
				}
			}
		},{
			field : 'beginTime',
			title : '用车开始时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'endTime',
			title : '用车结束时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'totalMinute',
			title : '用车时长(分钟)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		},{
			field : 'totalMileage',
			title : '行驶里程(公里)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		},{
			field : 'isTrafficCitation',
			title : '是否违章',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter: function(value,row,index){
				if(value == "1"){
					return "是";
				}else if(value =='0'){
					return "否";
				}
			}
		},{
			field : 'ckad',
			title : '违章事故',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter: function(value,row,index){
				if(row.isTrafficCitation == "1"){
					return "<a href='javascript:void(0);' onclick=openLookDialog('"+row.orderId+"')>查看</a>";
				}else{
					return "";
				}
			}
		}
		] ],
		pagination : true,
		rownumbers : true
	});
	
	
	$("#btnClear").bind("click",function(){
		$("#usrCarentrySearchForm").form("clear");	
	});
	
	$("#btnQuery").bind("click",function(){
		$("#usrCarEntryListTable").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
		    'custName':$("#s-custName").textbox("getValue"),
		    'custPhone':$("#s-custPhone").textbox("getValue"),
		    'lpn':$("#s-lpn").textbox("getValue"),
		    'orderId':$("#s-orderId").textbox("getValue"),
		    'bt':$("#s-bt").datetimebox("getValue"),
		    'et':$("#s-et").datetimebox("getValue"),
		    'isTrafficCitation':$("#s-isTrafficCitation").combobox("getValue")
		});
	});
	
	/* || _row.isTrafficCitation =='0'*/
	$("#btnWorder").bind("click",function(){
		//$("#useCarEntryAuditForm").form("clear");
		var _row = $("#usrCarEntryListTable").datagrid("getSelected");
		console.log(_row)
		if(_row){
			if(_row.isTrafficCitation =='1'){
				$.messager.alert('信息提示','此订单已审核，请选择新的订单记录','info');
			}else{
				$("#a-orderId").val(_row.orderId);
				if (_row.lpn.indexOf("•") < 0) {
					console.log(_row.lpn.substring(0,2) +"•"+_row.lpn.substring(2))
					$("#a_lpn").val(_row.lpn.substring(0,2) +"•"+_row.lpn.substring(2));
				}else{
					$("#a_lpn").val(_row.lpn);
				}
				$("#a_myOrderId").val(_row.orderId);
				$("input[name=hiddenLpn]").val(_row.lpn);
				$("#u-trafficCitationCharge").textbox("setValue","");
				$("#u-carVin").textbox("setValue",_row.classno);
				$("#u-carEngine").textbox("setValue",_row.engineno);
				$("#u-trafficContent").val("");
				$("#hiddenLpn").val(_row.lpn)
				$("#u-uploadFile").val("");
				$("#useCarEntryWorderDialog").dialog("open");
				//$("#useCarEntryWorderDialog").window('center');
				initUseCarEntryDetail(_row.orderId);
				
				//在模态框弹出后再去查询该订单对应的订单违章记录
				$("#wzOrderRecord").datagrid({
					title : '订单违章记录',
					width : 'auto',
					height : '200',
					nowrap : true,
					singleSelect : true,
					url : 'wz_order_record',
					queryParams:{
						'orderId':_row.orderId,
					},
					idField : 'pId',
					columns : [ [ {
						field : 'ck',
						checkbox : true

					},{
						field : 'hphm',
						title : '车牌号',
						align : 'center',	
						formatter : function(val) {
							if (val.indexOf("•") < 0){
								return val.substring(0,2) +"•"+val.substring(2);
							}else {
								return val;
							}
						}
					},{
						field : 'area',
						title : '违章地点',
						align : 'center',
					},{
						field : 'act',
						title : '违章行为',
						align : 'center',
					},{
						field : 'date',
						title : '违章时间',
						align : 'center',
					},{
						field : 'money',
						title : '违章罚款',
						align : 'center',
					},{
						field : 'fen',
						title : '违章扣分',
						align : 'center',
					},{
						field : 'status',
						title : '处理状态',
						align : 'center',
						formatter : function(val, rec) {
							if(val == "1"){
								return "未处理";
							}else if(val=="2"){
								return "已处理";
							}
						}
					}
					] ]
				});
				
			}
		}else{
			$.messager.alert('信息提示','请先选择需要审核的订单记录','info');
		}
	});
	
	//填写买分总费用后，在处理违章总金额上显示总金额
	$("input[name=buyPointFee]").change(function(){
		var totalFee = parseInt($("input[name=wzFee]").val()) + parseInt($(this).val());
		$("input[name=totalWzFee]").val(totalFee);
	})
	
	
	
	$("input[type='radio'][name='isTrafficCitation']").bind("click", function(){
		var _value = $("input[type='radio'][name='isTrafficCitation']:checked").val();
		if(_value == '1'){
			$("input[name=hiddenIsTrafficCitation]").val(_value);
			$("#useCarEntryAudit").show();
			$("#useCarEntryAuditPanel").dialog({
				closed:false,
				width: 460,   
				 height: 330,
				 left: 359,
				 top: 150,
				 buttons : [   { 
						text : "提交",
						iconCls : "icon-ok",
						handler : function() {
							$("#useCarEntryAuditForm").form("submit", {
								url : "use_car_entry_save",
								success : function(result) {
									if (result== "succ") {
										$.messager.alert("提示", "操作成功", "info");
										$("#useCarEntryWorderDialog").dialog("close");
										$("#useCarEntryAuditPanel").dialog("close");
										$('#usrCarEntryListTable').datagrid("reload");
									} else if(result == "fail") {
										$.messager.alert("提示", "操作失败", "error");
									} else if(result == "auditFail") {
										$.messager.alert("提示", "押金不足，审核失败", "error");
									}
									else{
									    $.messager.alert("提示", "操作失败", "error");
									}
								}
							});		
						}
					  } ,{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#useCarEntryAuditPanel").dialog("close");
						}
					} ]
			});
			
			//点击审核发送ajax请求，将订单的违章总费用以及违章分数显示出来
			$.ajax({
				type:"post",
				url:"query_wzFee_and_wzPoint",
				data:{orderId:$("#a_myOrderId").val()},
				success:function(data){
					var _json = eval("("+data+")");
					$("input[name=wzFee]").val(_json.wzFee);
					$("input[name=wzPoint]").val(_json.wzPoint);
				}
			})
		}else if(_value == '0'){
			$("#useCarEntryAudit").hide();
		}
	});
	
	//点击违章费用由宜步出行支付才显示这个面板
	$("input[type='radio'][name='isFeeByCompany']").bind("click", function(){
		var _value = $("input[type='radio'][name='isFeeByCompany']:checked").val();
		if(_value == '2'){
			$("#useCarEntryAuditPanel").dialog({
				 height: 330
			})
			$("#wzFeeByCompany").show();
		}else if(_value == '1'){
			$("#wzFeeByCompany").hide();
			$("#useCarEntryAuditPanel").dialog({
				 height: 210
			})
			
		}
	});
	
	$("#useCarEntryWorderDialog").dialog({
		title : "租车订单违章事故审核",
		width : "700",
		height : 'auto',
		top:50,
		left:250,
		modal: true,
		buttons : [   { //
			text : "提交",
			iconCls : "icon-ok",
			handler : function() {
				/*$("#useCarEntryAuditForm").form("submit", {
					url : "use_car_entry_save",
					success : function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#useCarEntryWorderDialog").dialog("close");
							$('#usrCarEntryListTable').datagrid("reload");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else if(result == "auditFail") {
							$.messager.alert("提示", "押金不足，审核失败", "error");
						}
						else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});		*/
			}
		  } ,{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#useCarEntryWorderDialog").dialog("close");
			}
		} ]
	});
	
	$("#useCarEntryWorderLookDialog").dialog({
		title : "租车订单违章事故明细",
		width : "700",
		height : 'auto',
		top:50,
		left:250,
		modal: true,
		buttons : [ {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#useCarEntryWorderLookDialog").dialog("close");
			}
		} ]
	});
});


function initUseCarEntryDetail(_orderId){
	$.ajax({
		type:"post",
		url:"use_car_entry_detail",
		data:{orderId:_orderId},
		success:function(data){
			console.log(data);
			var _json = eval("("+data+")");
			$("#a_cityCodeName").html(_json.cityCodeName);
			$("#a_orderId").html(_json.orderId);
			if (_json.lpn.indexOf("•") < 0) {
				$("#a_lpn").html(_json.lpn.substring(0,2) +"•"+_json.lpn.substring(2));
			}else{
				$("#a_lpn").html(_json.lpn);
			}
			var _payMoney =_json.payMoney;
			if(null != _payMoney && _payMoney !='' && _payMoney != 'null' && _payMoney !='NULL'){
				_payMoney = _payMoney/100;
			}
			
			$("#a_payMoney").html(_payMoney);
			$("#a_custName").html(_json.custName);
			$("#a_custPhone").html(_json.custPhone);
			$("#a_beginTime").html(_json.beginTime);
			$("#a_endTime").html(_json.endTime);
			$("#a_totalMinute").html(_json.totalMinute);
			$("#a_totalMileage").html(_json.totalMileage);
		}
	});
}


function openLookDialog(_orderId){
	//alert(_pid);
	//$("#useCarEntryWorderLookDialog").window('center');
	$("#useCarEntryWorderLookDialog").dialog("open");
	$.ajax({
		type:"post",
		url:"use_car_entry_query_detail",
		data:{orderId:_orderId},
		success:function(data){
			var _json = eval("("+data+")");
			$("#l_cityCodeName").html(_json.cityCodeName);
			$("#l_orderId").html(_json.orderId);
			if (_json.lpn.indexOf("•") < 0) {
				$("#l_lpn").html(_json.lpn.substring(0,2) +"•"+_json.lpn.substring(2));
			}else{
				$("#l_lpn").html(_row.lpn);
			}
			//$("#l_lpn").html(_json.lpn);
			
			var _payMoney =_json.payMoney;
			if(null != _payMoney && _payMoney !='' && _payMoney != 'null' && _payMoney !='NULL'){
				_payMoney = _payMoney/100;
			}
			
			$("#l_payMoney").html(_payMoney);
			$("#l_custName").html(_json.custName);
			$("#l_custPhone").html(_json.custPhone);
			$("#l_beginTime").html(_json.beginTime);
			$("#l_endTime").html(_json.endTime);
			$("#l_totalMinute").html(_json.totalMinute);
			$("#l_totalMileage").html(_json.totalMileage);
			var _isTrafficCitationStr = "";
			if(_json.isTrafficCitation == "1"){
				_isTrafficCitationStr = "是";
			}
			if(_json.isTrafficCitation == "0"){
				_isTrafficCitationStr = "否";
			}
			$("#l_isTrafficCitation").html(_isTrafficCitationStr);
			
			var _trafficCitationCharge = _json.trafficCitationCharge;
			if(null != _trafficCitationCharge && _trafficCitationCharge !='' && _trafficCitationCharge != 'null' && _trafficCitationCharge !='NULL'){
				_trafficCitationCharge = _trafficCitationCharge/100;
			}
			
			$("#l_trafficCitationCharge").html(_trafficCitationCharge);
			$("#l_carVin").html(_json.carVin);
			$("#l_carEngine").html(_json.carEngine);
			$("#l_tcContent").html(_json.trafficContent);
			var _fileHtml = "";
			if(_json.auditorAccessoryFilename != null && _json.auditorAccessoryFilename != ""){
				_fileHtml = "<a href='"+_json.auditorAccessoryFile+"'>"+_json.auditorAccessoryFilename+"</a>";
			}
			$("#l_auditorAccessoryFilename").html(_fileHtml);
		}
	});
}