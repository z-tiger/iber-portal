
$(function() {
	
	//查询链接
	$("#btnQuery").bind("click",function(){
		$("#dataGrid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var name = $("#s-name").textbox("getValue");
	    var cityCode = $("#scityCode").combobox("getValue");
	    var phone = $("#s-phone").textbox("getValue");
	    var bt = $("#s-bt").datetimebox("getValue");
	    var et = $("#s-et").datetimebox("getValue");
		$("#blacklistForm").form("submit", {
			url : "export_blacklist_report?name="+name+"&cityCode="+cityCode+"&phone="+phone+"&bt="+bt+"&et="+et,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	$("#btnContributeValQuery").bind("click",function(){
		$("#cancelBlecklistDatagrid").datagrid("load",{
			'memberId':$("#e-memberId").val(),
			'behaviourName':$("#s-behaviourName").textbox("getValue")
		});
	});
	
	$('#dataGrid').datagrid( {
		title : '黑名单管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'dataListMemberBlacklist',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		pagination : true,
		rownumbers : true,
		columns : [ [{ 
			field : 'ck',
			checkbox:true
		},
		{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.1,
			align : 'center'	
		},   
		  {
			field : 'name',
			title : '姓名',
			align : 'center',
			width : $(this).width() * 0.1
			
		}, {
			field : 'sex',
			title : '性别',
			align : 'center',
			width : $(this).width() * 0.2,
			formatter: function(val){
				if (val == '1') {
					return "男";
				}else if(val == '2'){
					return "女";
				}
			}
			
		},
		{
			field : 'phone',   
			title : '手机号码',
			width : $(this).width() * 0.1,
			align : 'center'
		},
		{
			field : 'idCard',
			title : '身份证号码',
			width : $(this).width() * 0.2,
			align : 'center'	
		},{
			field : 'accoutStatus',
			title : '资金状态',
			width : $(this).width() * 0.13,
			align : 'center',
			formatter: function(value, row, index){
				if (value == '0') {//表示被系统自动拉入黑名单
					return "正常";
				}else if(value == '5'){
					return "冻结";
				}
			}
		} , {
			field : 'levelCode',
			title : '会员等级',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter: function(value, row, index){
				if (value == 0) {//表示被系统自动拉入黑名单
					return "黑名单";
				}else if(value == 1){
					return "一星会员";
				}else if(value == 2){
					return "二星会员";
				}else if(value == 3){
					return "三星会员";
				}else if(value == 4){
					return "四星会员";
				}else if(value == 5){
					return "五星会员";
				}
			}
		}, {
			field : 'contributedVal',
			title : '会员贡献值',
			width : $(this).width() * 0.12,
			align : 'center'
		}, {
			field : 'reason',
			title : '原因',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter: function(value, row, index){
				if (row.isAuto == 0) {//表示被系统自动拉入黑名单
					return "贡献值过低，系统自动拉入黑名单";
				}else{
					return row.reason;
				}
			}
		}, {
			field : 'createTime',
			title : '操作时间',
			width : $(this).width() * 0.12,
			align : 'center'
		}, {
			field : 'creator',
			title : '操作人员',
			width : $(this).width() * 0.12,
			align : 'center'
		}
		] ],
		pagination : true,
		rownumbers : true
	});
	
	$("#btnCancelBlacklist").bind("click",function(){
		$("#editViewForm").form("clear");
		$("#error_redio").prop('checked',true);
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要撤销黑名单的会员", "error");
		}else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-memberId").val(selectedRows[0].memberId);
			$("#cancelBlecklistDatagrid").datagrid({
				width : 'auto',
				height : '350',
				fit : false,
				fitColumns : true,
				nowrap : true,     
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'getDecreaseContributedDetail.do',
				queryParams:{
					'memberId':selectedRows[0].memberId,
				},
				pageSize : 20,
				idField : 'id',
				columns : [ [{
					field : 'parentName',
					title : '分类',
					width : $(this).width() * 0.12,
					align : 'center'
				} , {
					field : 'childrenName',
					title : '子分类',
					width : $(this).width() * 0.20,
					align : 'center'
				}, 
				{
					field : 'contributeVal',
					title : '增减贡献值',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(value,row,index){
						if(row.isIncrease == 0){
							return "-"+row.contributeVal;
						}else{
							return row.contributeVal;
						}
					}
				},
				{
					field : 'createName',   
					title : '操作人员',
					width : $(this).width() * 0.12,
					align : 'center',
					formatter : function(val) {
						if (val) {
							return val;
						}else {
							return "系统";
						}
					}
				},{
					field : 'createTime',   
					title : '操作时间',
					width : $(this).width() * 0.32,
					align : 'center',
				}
				] ],
				pagination : true,
				rownumbers : true
			})
			$("#cancelBlecklistDatagrid").datagrid("unselectAll");
			$("#editView").dialog("open");
		}
	});
	
	
	
	$("#editView").dialog( {
		width : "700",
		height : "550",
		top : "100",
		left:'400',
		modal:true,
		title:"撤销黑名单",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit",{
					url:'cancel_blacklist',
					onSubmit:function(){
						var selectedRows = $("#cancelBlecklistDatagrid").datagrid("getSelections");
						console.log(selectedRows);
						var contributeValSum = 0;
						for(var i=0; i<selectedRows.length; i++){
							contributeValSum = contributeValSum + selectedRows[i].contributeVal;
						}
						$("#e-contributeVal").val(contributeValSum);
						if (selectedRows[0]) {
							$("#e-contributeDetailId").val(selectedRows[0].id);
						}
						return $(this).form("validate");	
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#dataGrid").datagrid("reload");
							$("#editView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result == "noContributeVal"){
							$.messager.alert("提示", "请选择对应的贡献值明细", "error");
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
				$("#editView").dialog("close");
			}
		}]
	});
})	
