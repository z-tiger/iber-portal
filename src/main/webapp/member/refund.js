$(function(){
	
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'status':$("#s-status").combobox("getValue"),
			'isHandleReturn':$("#s-isHandleReturn").combobox("getValue")
		});
	});
	
	$("#passBtnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'passTime':'1'
		});
	});
	
	// 导出退款记录excel
	$("#btnExport").bind("click", function() {
		var cityCode = $('#scityCode').textbox('getValue');
		var name = $('#s-name').textbox('getValue');
		var status = $('#s-status').textbox('getValue');
		var phone = $('#s-phone').textbox('getValue');
		var isHandleReturn = $("#s-isHandleReturn").combobox("getValue");
		$("#queryMemberRefund").form("submit", {
			url : "export_member_refund_excel",
			onSubmit : function(param) {
				param.name = encodeURIComponent(name);
				param.cityCode = cityCode;
				param.status = status;
				param.phone = phone;
				param.isHandleReturn = isHandleReturn;
			},
			success : function(result) {
			}
		});

	});
	
	$("#btnClear").bind("click",function(){
		$("#queryMemberRefund").form("clear");
	});
	
	
	$("#refundAuditWorkflowLookDialog").dialog({
		title : "退款审核明细",
		width : "700",
		height : '650',
		modal: true,
		buttons:[{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function(){
				$("#refundAuditWorkflowLookDialog").dialog("close");
			}
		}]
	});
	
	
	$("#refundAuditWorkflowDialog").dialog({
		title : "退款工单审核",
		width : "700",
		height : 'auto',
		modal: true,
		buttons : [   { //
			text : "提交",
			iconCls : "icon-ok",
			handler : function() {
				$("#refunFlowForm").form("submit", {
					url : "member_refund_worder_detail",
					success : function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#refundAuditWorkflowDialog").dialog("close");
							$('#grid').datagrid("reload");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result == 'noPay') {
							$.messager.alert("提示", "该会员还有未支付的订单，审核不能通过", "error");
						}else if(result == "hadCancel"){
							$.messager.alert("提示", "该会员已经取消退款", "error");
							$('#grid').datagrid("reload");
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
				$("#refundAuditWorkflowDialog").dialog("close");
			}
		} ]
	});
	
	//退款--原路返回
	$("#member_refund").click(function(){
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要退款的退款记录", "error");
		}else {
			if(selectedRows[0].status != 5){//如果状态不为退款中，那么不能进行退款操作
				$.messager.alert("提示", "该退款记录不为退款中，不能进行退款", "error");
				return;
			}
			$.ajax({
				type:"post",
				url:"member_refund",
				data:{id:selectedRows[0].id},
				success:function(data){
					if(data == "success"){
						openRefundReturnDialg(selectedRows[0].id,selectedRows[0].memberId);
					}else if (data == "disagree"){
						$.messager.alert("提示", "该退款记录不为退款中，不能进行退款", "error");
						return;
					}else{
						openRefundHandleReturnDialog(selectedRows[0].id,selectedRows[0].memberId);
					}
				}
			})
		}
		
	})
	
});




function openRefundAuditDialog(_id, _curr_id, _memberId){
	//alert(_memberId);
	//$("#refunFlowForm").form("clear");
	//$("input:radio[name='auditorResult']").eq(0).attr("checked",'checked');\
	 //$("#auditorResult-1").removeAttr("checked");
	 //$("#auditorResult-1").attr("checked",'checked');
	$("#auditorRemark").val("");
	$("#uploadFile").val("");
	initRefundAudtiBaseInfo("a",_id);
	initAuditLog("a",_id);
	initCustCardInfo(_memberId);
	$("#rid").val(_id);
	$("#currRoleId").val(_curr_id);
	$("#refundId").val()
	$("#refundAuditWorkflowDialog").dialog("open");
	$("#refundAuditWorkflowDialog").window('center');
	
	//在模态框弹出后再去查询该订单对应的订单违章记录
	$("#queryWzRecord").datagrid({
		title : '订单违章记录',
		width : 'auto',
		height : '150',
		nowrap : true,
		singleSelect : true,
		url : 'query_wz_record',
		queryParams:{
			'memberId':_memberId,
		},
		idField : 'pId',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		},{
			field : 'orderId',
			title : '订单号',
			align : 'center'	
		},{
			field : 'hphm',
			title : '车牌号',
			align : 'center'	
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
		},
		/*{
			field : 'wzPayStatus',
			title : '违章支付状态',
			align : 'center',
			formatter : function(value,row,index) {
				if(row.memberRefundWorderUsecar != null){
					if(row.memberRefundWorderUsecar.wzPayStatus == "1"){
						return "由个人支付";
					}else if(row.memberRefundWorderUsecar.wzPayStatus == "2"){
						return "由公司支付";
					}
				}else{
					return "";
				}
			}
		},{
			field : 'wzFee',
			title : '违章事故赔偿金额',
			align : 'center',
			formatter : function(value,row,index) {
				return row.memberRefundWorderUsecar.wzFee == 0 ? null : row.memberRefundWorderUsecar.wzFee;
			}
		},{
			field : 'deposit',
			title : '押金',
			align : 'center',
			formatter : function(value,row,index) {
				return row.deposit == 0 ? null : row.deposit;
			}
		},{
			field : 'returnFee',
			title : '退款金额',
			align : 'center',
			formatter : function(value,row,index) {
				console.log(row);
				return row.returnFee == 0 ? null : row.returnFee;
			}
		}*/
		] ]
	});
}

function openRefundLookDialog(_id,status,_memberId){
	initRefundAudtiBaseInfo("l",_id);
	initAuditLog("l",_id);
	$("#refundAuditWorkflowLookDialog").dialog("open");
	$("#refundAuditWorkflowLookDialog").window('center');

	//查询会员充值记录
    $("#refundLogGrid").datagrid({
        title : '会员充值记录',
        fit : true,
        fitColumns : false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
        url : 'query_member_charege_log',
        queryParams:{
            'id':_id,
			'memberId':_memberId
        },
        idField : 'pId',
        columns : [ [ {
            field : 'ck',
            checkbox : true

        },{
            field : 'phone',
            title : '会员手机号',
            width : $(this).width() * 0.06,
            align : 'center'
        },{
            field : 'name',
            title : '会员姓名',
            width : $(this).width() * 0.04,
            align : 'center'
        },{
            field : 'tradeNo',
            title : '流水号',
            width : $(this).width() * 0.12,
            align : 'center',
        },{
            field : 'bankCategory',
            title : '退回账户类型',
            width : $(this).width() * 0.12,
            align : 'center',
            formatter:function (val) {
                if (val == 'A'){
                	return "支付宝";
				}else {
                	return "微信"
				}
            }
        },{
            field : 'createTime',
            title : '充值时间',
            width : $(this).width() * 0.08,
            align : 'center',
        },{
            field : 'money',
            title : '充值金额',
            width : $(this).width() * 0.04,
            align : 'center',
			formatter:function (val) {
				return val/100
            }
        },{
            field : 'status',
            title : '退款状态',
            width : $(this).width() * 0.04,
            align : 'center',
			formatter:function (val,rec) {
            	console.log(rec)
            	if (rec.refundStatus == 3){
                    if (val == 0){
                        return "<span style='color:red'>失败</span>";;
                    }else if (val == 1){
                        return "成功";
                    }
				}else{
            		return "退款中";
				}

            }
        }] ]
    });

    //查询会员退款驳回记录
    $("#rejectLogGrid").datagrid({
        title : '会员退款驳回记录',
        fit : true,
        fitColumns : false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
        url : 'query_member_reject_log',
        queryParams:{
            'id':_memberId,
        },
        idField : 'pId',
        columns : [ [ {
            field : 'ck',
            checkbox : true

        },{
            field : 'rejectTime',
            title : '驳回时间',
            width : $(this).width() * 0.10,
            align : 'center'
        },{
            field : 'rejectReason',
            title : '驳回原因',
            width : $(this).width() * 0.16,
            align : 'center'
        },{
            field : 'operationPerson',
            title : '驳回的操作人员',
            width : $(this).width() * 0.12,
            align : 'center',
        }] ]
    });
}


function initRefundAudtiBaseInfo(_p, _id){
	$.ajax({
		type:"post",
		url:"refund_audit_base_info",
		data:{id:_id},
		success:function(data){
			var _json = eval("("+data+")");
			$("#"+_p+"-name").html(_json.name);
			$("#"+_p+"-phone").html(_json.phone);
			var _money = _json.money;
			if(null != _money && _money !='' && _money != 'null' && _money !='NULL'){
				_money = _money/100;
			}
			$("#"+_p+"-money").html(_money);
			$("#"+_p+"-createTime").html(_json.createTime);
			$("#"+_p+"-bankCardUserName").html(_json.bankCardUserName);
			$("#"+_p+"-bankCard").html(_json.bankCard);
			$("#"+_p+"-bankName").html(_json.bankName);
			$("#"+_p+"-refundUserMoblie").html(_json.refundUserMoblie);
			$("#"+_p+"-reason").html(_json.reason);
			$("#"+_p+"-levelName").html(getLevelName(_json.levelCode));
		}
	});
}

function getLevelName(level){
	switch (level) {
	case 1:
		return "一星会员";
		break;
	case 2:
		return "二星会员";
		break;
	case 3:
		return "三星会员";
		break;
	case 0:
		return "黑名单";
		break;
	case 4:
		return "四星会员";
		break;
	case 5:
		return "五星会员";
		break;

	}
}

function initCustCardInfo(_memberId){
	/*$.ajax({
		type:"post",
		url:"refund_audit_cust_card_info",
		data:{memberId:_memberId},
		success:function(data){
			var _json = eval("("+data+")");
			$("#a-isTrafficCitation").html(_json.isTrafficCitation);
			
			var _trafficCitationCharge = _json.trafficCitationCharge;
			if(null != _trafficCitationCharge && _trafficCitationCharge !='' && _trafficCitationCharge != 'null' && _trafficCitationCharge !='NULL'){
				_trafficCitationCharge = _trafficCitationCharge/100;
			}
			
			$("#a-trafficCitationCharge").html(_trafficCitationCharge);
		}
	});*/
}

function initAuditLog(_p,_id){
	$.ajax({
		type:"post",
		url:"refund_worder_detail_log",
		data:{id:_id},
		success:function(data){
			//alert(data);
			$("#"+_p+"-audit-table").html(data);
		}
	});
}
//原路返回
function openRefundReturnDialg(_id,_memberId){
	initRefundAudtiBaseInfo("d",_id);
	initAuditLog("d",_id);
	initCustCardInfo(_memberId);
	$("#refundReturnDialog").dialog({
		title:"退款",
		width : "700",
		height : 'auto',
		modal: true,
		buttons : [   { //
			text : "原路返回金额",
			iconCls : "icon-ok",
			handler : function() {
				var validator = $('#refunFlowForm').form('validate');
		        if(validator){
		          $.messager.progress();
		        }
				$("#refunFlowForm").form("submit", {	
					url : "member_return_refund?returnId="+_id,
					success : function(result) {
						$.messager.progress('close'); 
						if (result== "success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#refundReturnDialog").dialog("close");
							$('#grid').datagrid("reload");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if (result == "hadfinish") {
							$.messager.alert("提示", "该退款已经完成，请勿重复提交", "error");
						}
					}
				});		
				
			}
		  } ,{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#refundReturnDialog").dialog("close");
			}
		} ]
	})
	$("#refundReturnDialog").dialog("open");
	$("#refundReturnDialog").window('center');
}

function openRefundHandleReturnDialog(_id,_memberId){
	initRefundAudtiBaseInfo("h",_id);
	initAuditLog("h",_id);
	initCustCardInfo(_memberId);
	$("#refundHandleReturnDialog").dialog({
		title:"退款",
		width : "700",
		height : 'auto',
		modal: true,
		buttons : [
		           {
						text : "驳回",
						iconCls : "icon-ok",
						handler : function() {
							$("#contradictViewForm").form("clear");
							$("#contradictView").dialog({
								title:"驳回原因",
								width : "400",
								height : 'auto',
								top : "30%",
								left : "30%",
								modal: true,
								buttons : [{
											text : "确定",
											iconCls : "icon-ok",
											handler : function() {
												var validator = $('#contradictViewForm').form('validate');
										        if(validator){
										          $.messager.progress();
										        }
												$("#contradictViewForm").form("submit", {
													url : "contradict_refund",
													success : function(result) {
														$.messager.progress('close'); 
														
														if (result== "success") {
															$.messager.alert("提示", "操作成功", "info");
															$("#refundHandleReturnDialog").dialog("close");
															$("#contradictView").dialog("close");
															$('#grid').datagrid("reload");
														} else if(result == "cancel") {
															$("#contradictView").dialog("close");
															$.messager.alert("提示", "该退款已取消", "error");
														} else if (result == "done") {
															$("#contradictView").dialog("close");
															$.messager.alert("提示", "该退款已经完成，请勿重复提交", "error");
														}else if (result == "contradict") {
															$("#contradictView").dialog("close");
															$.messager.alert("提示", "该退款已被驳回，请勿重复提交", "error");
														}else if (result == "reasonNull") {
															$.messager.alert("提示", "驳回原因不能为空", "error");
														}
													}
												});		
											}
								          },
								           {
												text : "取消",
												iconCls : "icon-cancel",
												handler : function() {
													$("#contradictViewForm").form("clear");
													$("#contradictView").dialog("close");
												}
								            } 
								          ]
							});
							$("#refund-id").val(_id);
							$("#contradictView").dialog("open");
						}
			          },
		          {
					text : "手动打款",
					iconCls : "icon-ok",
					handler : function() {
						var validator = $('#refunFlowForm').form('validate');
				        if(validator){
				          $.messager.progress();
				        }
						$("#refunFlowForm").form("submit", {
							url : "member_handle_return_refund?handleId="+_id,
							success : function(result) {
								$.messager.progress('close'); 
								if (result== "success") {
									$.messager.alert("提示", "操作成功", "info");
									$("#refundHandleReturnDialog").dialog("close");
									$('#grid').datagrid("reload");
								} else if(result == "fail") {
									$.messager.alert("提示", "操作失败", "error");
								} else if (result == "hadfinish") {
									$.messager.alert("提示", "该退款已经完成，请勿重复提交", "error");
								}
							}
						});		
					}
		          },
		          {
					text : "取消",
					iconCls : "icon-cancel",
					handler : function() {
						$("#contradictViewForm").form("clear");
						$("#contradictView").dialog("close");
						$("#refundHandleReturnDialog").dialog("close");
					}
		          }]
	})
	$("#refundHandleReturnDialog").dialog("open");
	$("#refundHandleReturnDialog").window('center');
}