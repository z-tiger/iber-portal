$(function(){
	//导出excel
	$("#btnExport").bind("click", function() {
		var name = $("#s-name").textbox("getValue");
	    var phone = $("#s-phone").textbox("getValue");
		$("#blackListLogForm").form("submit", {
			url : "export_blockListLog_report?name="+name+"&phone="+phone,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	$('#grid').datagrid({
		title : '会员管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'dataListMemberBlacklistLog',
		queryParams:{
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'memberName',
			title : '姓名',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '手机号',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'operate',
			title : '操作',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(1==val){
					return "撤销黑名单";
				}else{
					return "列入黑名单";
				}
			}
		}, {
			field : 'reason',
			title : '原因',
			width : $(this).width() * 0.3,
			align : 'center'
		}, {
			field : 'createName',
			title : '操作人',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'createTime',
			title : '操作时间',
			width : $(this).width() * 0.2,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	
	$("#btnQuery").bind("click",function(){
		$('#grid').datagrid("load",{
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
		});
	});
});