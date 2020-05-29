$(function(){
	
	$('#grid').datagrid({
		title : '会员贡献值明细',
		width : '700',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'query_member_contribute_item',
		queryParams:{
			'dicCode':$("#sMemberContribute").combobox("getValue"),
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'type',
			title : '贡献值来源',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'contributedValDelta',
			title : '贡献值',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'orderAmount',
			title : '订单金额',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter :function(val) {
				n = val.toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
    			return n.replace(re, "$1,");	
			}
		},{
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.2,
			align : 'center',
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'dicCode':$("#sMemberContribute").combobox("getValue"),
		});
	});
	
	
	
});