function search(){
	var cityCode = $('#cityCode').textbox('getValue') ;
	var memberName = $.trim($("#memberName").val()) ;
	var phoneNumber = $.trim($("#phoneNumber").val()) ;
	$('#memberGrid').datagrid('load',{
		memberName : memberName ,
		phoneNumber :phoneNumber,
		cityCode :cityCode
	}) ;
}

$(function() {
	//查询链接
	$("#btnQuery").bind("click", function() {
		search() ;
	});
	
	// 清空
	$("#clearQuery").bind("click", function() {
		$('#cityCode').textbox('setValue', '');
		$('#memberName').textbox('setValue', '');
		$('#phoneNumber').textbox('setValue', '');
	}); 
	
	$('#memberGrid')
	.datagrid(
	{
		title : '会员取消订单列表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'cancel_order_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'cityName',
			title : '区域',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'phone',
			title : '会员手机号',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'cancelNum',
			title : '取消约车次数',
			width : $(this).width() * 0.15,
			align : 'center'
		}, 
		{
			field : 'chargingCancelNum',
			title : '取消约充电次数',
			width : $(this).width() * 0.15,
			align : 'center'
		}, 
		{
			field : 'createTime',
			title : '最后取消时间',
			width : $(this).width() * 0.2,
			align : 'center'
		}
		] ],
		pagination : true,
		rownumbers : true
	}); 
	
	//重置会员取消订单次数
	$("#resetMember").bind("click", function() {
		 var selectedRows = $("#memberGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要重置的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要重置吗?",function(r){
				if(r){
					$.post("reset_member_cancel_order",{id:_id},function(data){
						if(data == "success"){
							$.messager.alert("提示", "操作成功", "info");
							$("#memberGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	}); 
	
	//重置会员取消充电订单次数
	$("#resetMemberCharing").bind("click", function() {
		var selectedRows = $("#memberGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择要重置的记录", "error");
		}else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要重置吗?",function(r){
				if(r){
					$.post("reset_member_cancel_charing_order",{id:_id},function(data){
						if(data == "success"){
							$.messager.alert("提示", "操作成功", "info");
							$("#memberGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		}
	}); 
});

