$(function(){
	
	$('#grid').datagrid({
		title : '会员账户管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'member_card_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue")
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
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'accoutStatus',
			title : '资金状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val == "0") {
					return "正常";
				}else if(val == "1"){
					if (rec.refundMoney + rec.requiredDeposit > rec.deposit) {
						return "<span style='color:red'>冻结</span>";
					}else{
						return "正常";
					}
				}else if(val == "5"){
					return "<span style='color:red'>冻结</span>";
				}
			}
		}/*,{
			field : 'timeShareOrderId',
			title : '订单类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == ""){
					return "无";
				}else if(rec.timeShareOrderId.length>0){
					return "时租";
				}else if(rec.dayRentOrderId.length>0){
					return "日租";
				}
			}
		},{
			field : 'dayRentOrderId',
			title : '订单号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == ""){
					return "无";
				}else if(rec.timeShareOrderId.length>0){
					return rec.timeShareOrderId;
				}else if(rec.dayRentOrderId.length>0){
					return rec.dayRentOrderId;
				}
			}
		}*/,{
			field : 'money',
			title : '余额(元)',
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
			field : 'deposit',
			title : '押金(元)',
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
			field : 'requiredDeposit',
			title : '会员应缴押金',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value,row,index){
				if (row.deposit > row.requiredDeposit) {
					return 0;
				}else {
					return (row.requiredDeposit - row.deposit)/100;
				}
			}
		},{
			field : 'quota',
			title : '用车额度(元)',
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
			field : 'quotaMonth',
			title : '用车额度期限(月)',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'quotaUseMoney',
			title : '已用额度(元)',
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
			field : 'totalChargeMoney',
			title : '累计充值金额(元)',
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
			field : 'totalRefundMoney',
			title : '累计退款金额(元)',
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
			field : 'totalNotUsecoupon',
			title : '未使用优惠券总额度(元)',
			width : $(this).width() * 0.12,
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
		}, {
			field : 'blockingReason',
			title : '冻结原因',
			width : $(this).width() * 0.15,
			align : 'center',
            formatter : function(val, rec) {
               	if (rec.accoutStatus == "1"){
               		if (rec.refundMoney + rec.requiredDeposit > rec.deposit) {
                        return "会员退押金中，押金不满足条件";
                    }
				}else if (rec.accoutStatus == "5"){
               		return val;
				}
            }
		},{
			field : 'zhimaScore',
			title : '芝麻信用分数',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'remark',
			title : '备注',
			width : $(this).width() * 0.08,
			align : 'center'
		}/*,{
			field : 'acpPreAuth',
			title : '预授权(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(null != val && val !='' && val != 'null' && val !='NULL'){
					var tmp = val/100;
					if(tmp == 0){
						return "0.00";
					}else{
						return tmp.toFixed(2);
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
			field : 'acpPreAuthDate',
			title : '预授权有效期',
			align : 'center'
		}*/] ],
		pagination : true,
		rownumbers : true
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
	    var param=   $('#grid').datagrid('options').queryParams;//查询参数
		var url = "export_card_list?"+jQuery.param(param);
		window.location.href = url ;
	});
	//查询欠款会员
	$("#btnDebit").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'debit':'debit'
		});
	});
	//clear
	$("#btnClear").bind("click",function(){
		$("#memberSearchForm").form("clear");
	});
	//冻结顾客资金
	$("#btnFrozen").bind("click",function(){
		$('#frozenForm').form('clear');
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择需要冻结资金的用户", "error");
		}else if(selectedRows[0].accoutStatus == "5"){
			$.messager.alert("提示", "用户资金已被冻结", "error");
		}else if(selectedRows[0].accoutStatus == "1"){
			$.messager.alert("提示", "该会员存在退款中的记录，已经冻结资金", "error");
		}else{
				$("#memberId").val(selectedRows[0].memberId);
				$("#accoutStatus").val("5");
				$("#frozenView").dialog({title:"冻结资金"});
				$("#frozenView").dialog("open");
		}
	 	
	});
	//构造保存对话框
	$("#frozenView").dialog( {
		width : "430",
		height : "150",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#frozenForm").form("submit", {
					url : "member_card_frozen_thaw",
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
						if (result == "succ") {
							$.messager.alert("提示", "保存成功", "info");
						    $("#grid").datagrid("reload");
							$("#frozenView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    
							$("#frozenView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#frozenView").dialog("close");
					
					
			}
		}]
	});
	
	/*$("#btnFrozen").bind("click",function(){
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要冻结资金的用户", "error");
		}else if(selectedRows[0].accoutStatus == "5"){
			$.messager.alert("提示", "用户资金已被冻结", "error");
		}else{
			var _memberId = selectedRows[0].memberId;
			var _accoutStatus = "5";
			$.post("member_card_frozen_thaw",{memberId:_memberId,accoutStatus:_accoutStatus},function(data){
				if(data == "succ"){
					$.messager.alert("提示", "操作成功", "info");
					$("#grid").datagrid("reload");
				}else{
					 $.messager.alert("提示", "操作失败", "error");
				}
			},"text");
		}
	});*/
	
	$("#btnThaw").bind("click",function(){
        $('#unFrozenForm').form('clear');
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要解冻资金的用户", "error");
		}else if(selectedRows[0].accoutStatus == "0"){
			$.messager.alert("提示", "用户资金已被解冻", "error");
		}else{
            $("#u_memberId").val(selectedRows[0].memberId);
            $("#u_accoutStatus").val("0");
            $("#unFrozenView").dialog({title:"解冻资金"});
            $("#unFrozenView").dialog("open");
		}
	});

    //构造保存对话框
    $("#unFrozenView").dialog( {
        width : "430",
        height : "150",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#unFrozenForm").form("submit", {
                    url : "member_card_frozen_thaw",
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
                        if (result == "succ") {
                            $.messager.alert("提示", "保存成功", "info");
                            $("#grid").datagrid("reload");
                            $("#unFrozenView").dialog("close");
                        }else if (result == "hadRefund"){
                            $.messager.alert("提示", "该会员存在退款中的记录，不能解冻资金", "error");
                            $("#unFrozenView").dialog("close");
						}else{
                            $.messager.alert("提示", "保存失败", "info");
                            $("#unFrozenView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#unFrozenView").dialog("close");


            }
        }]
    });
});