$(function() {
	$("#btnQuery").bind("click", function() {
		$('#grid').datagrid('load', {
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'activityType' : $("#s-activityType").textbox("getValue"),
			'memberName' : $("#s-memberName").textbox("getValue"),
			'memberPhone' : $("#s-memberPhone").textbox("getValue"),
			'prizeName' : $("#s-prizeName").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue")
			
		});
		//清除
		$("#btnClear").bind("click",function() {
			$('#s-cityCode').combobox("setValue","");
			$('#s-activityType').textbox("setValue","");
			$("#s-memberName").textbox("setValue","");
			$("#s-memberPhone").textbox("setValue","");
			$("#s-prizeName").textbox("setValue","");
			$("#s-bt").datetimebox("setValue","");
			$("#s-et").datetimebox("setValue","");
		});
	});
	
	$('#grid').datagrid({
		title : '抽奖记录明细管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'lottery_draw_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		pagination : true,
		rownumbers : true,
		url : 'lottery_draw_log_list',
		pageSize : 30,
		pageList : [ 100, 50, 30, 10 ],
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
			field : 'prizeName',
			title : '中奖项目',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'name',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'createTime',
			title : '中奖时间',
			width : $(this).width() * 0.1,
			align : 'center'
		} , {
			field : 'orderId',
			title : '订单号',
			width : $(this).width() * 0.1,
			align : 'center'
		} ] ],
	});
});


