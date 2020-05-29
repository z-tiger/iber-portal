function search(){
	var memberName = $.trim($("#memberName").val()) ;
	var cityCode = $('#cityCode').textbox('getValue') ;
	var lpn = $('#lpn').textbox('getValue');
	var orderId = $("#orderId").textbox('getValue');
	var queryDateFrom = $('#queryDateFrom').textbox('getValue');
	var queryDateTo = $('#queryDateTo').textbox('getValue');
	var status = $('#status').combobox('getValue');
	$('#orderGrid').datagrid('load',{
		memberName : memberName ,
		cityCode :cityCode,
		lpn : lpn,
		queryDateFrom : queryDateFrom,
		queryDateTo : queryDateTo,
		status : status,
		orderId : orderId
	}) ;
}

$(function() {
	//查询链接
	$("#btnQuery").bind("click", function() {
		search() ;
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var memberName = $.trim($("#memberName").val()) ;
		var cityCode = $('#cityCode').textbox('getValue') ;
		var lpn = $('#lpn').textbox('getValue');
		var orderId = $("#orderId").textbox('getValue');
		var queryDateFrom = $('#queryDateFrom').textbox('getValue');
		var queryDateTo = $('#queryDateTo').textbox('getValue');
		var status = $('#status').combobox('getValue');
		$("#employeeOrderForm").form("submit", {
			url : "export_employeeOrder_list?cityCode="+cityCode+"&lpn="+lpn+"&status="
												+status+"&memberName="+memberName+"&queryDateFrom="
												+queryDateFrom+"&queryDateTo="+queryDateTo+"&orderId="+orderId,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	// 清空
	$("#clearQuery").bind("click", function() {
		$('#cityCode').textbox('setValue', '');
		$('#lpn').textbox('setValue', '');
		$('#queryDateFrom').textbox('setValue', '');
		$('#queryDateTo').textbox('setValue', '');
		$('#memberName').textbox('setValue', '');
        $("#orderId").textbox('setValue', '');
	}); 
	
	$('#orderGrid')
	.datagrid(
	{
		title : '员工订单',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'employee_order_list',
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
			field : 'orderNo',
			title : '订单编号',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
				
			}
		}, {
			field : 'memberName',
			title : '员工姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'parkName',
			title : '预约网点',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'returnParkName',
			title : '还车网点',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'orderTime',
			title : '预约时间',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'beginTime',
			title : '上车时间',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'endTime',
			title : '还车时间',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
            field : 'useTime',
            title : '用车时长',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val==null || val=='') {
                    return 0
                }else {
                    return secondToDate(val)
                }

            }
        },{
            field : 'actualMileage',
            title : '行驶里程(千米)',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'planMileage',
            title : '规划里程(千米)',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
			field : 'status',
			title : '订单状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
			    if(val == "cancel"){
			    	return "已取消" ;
			    }else if(val == "finish"){
			    	return "已完成";
			    }else if(val =="useCar"){
			    	return "用车中";
			    }else if(val =="ordered"){
			    	return "预约中";
			    }
			}
		}
		] ],
		pagination : true,
		rownumbers : true
	});
});

