function search(){
	var cityCode = $('#cityCode').textbox('getValue') ;
    var type = $('#type').textbox('getValue') ;
	var lpn = $('#lpn').textbox('getValue');
	var memberName = $.trim($("#memberName").val()) ;
	var phoneNumber = $.trim($("#phoneNumber").val()) ;
    var queryDateFrom = $('#queryDateFrom').textbox('getValue');
    var queryDateTo = $('#queryDateTo').textbox('getValue');
    var payStatus = $('#payStatus').combobox('getValue');

	$('#order_attached_grid').datagrid('load',{
		memberName : memberName ,
		cityCode :cityCode,
		lpn : lpn,
		phoneNumber :phoneNumber,
        type : type,
        queryDateFrom : queryDateFrom,
        queryDateTo : queryDateTo,
        payStatus:payStatus
	}) ;
}

$(function() {
	//查询链接
	$("#btnQuery").bind("click", function() {
		search() ;
	});

    // 清空
    $("#clearQuery").bind("click", function() {
        $('#attachedReportForm').form('clear');
       /* $('#cityCode').textbox('setValue', '');
        $('#lpn').textbox('setValue', '');
        $('#memberName').textbox('setValue', '');
        $('#phoneNumber').textbox('setValue', '');
        $('#type').textbox('setValue', '');
        $('#queryDateFrom').textbox('setValue', '');
        $('#queryDateTo').textbox('setValue', '');*/
    });
    // 附属订单收入导出excel链接
    $("#outPutQuery").bind("click", function() {

        var cityCode = $('#cityCode').textbox('getValue') ;
        var type = $('#type').textbox('getValue') ;
        var lpn = $('#lpn').textbox('getValue');
        var memberName = $.trim($("#memberName").val()) ;
        var phoneNumber = $.trim($("#phoneNumber").val()) ;
        var queryDateFrom = $('#queryDateFrom').textbox('getValue');
        var queryDateTo = $('#queryDateTo').textbox('getValue');
        var payStatus = $('#payStatus').combobox('getValue');

        $("#attachedReportForm").form("submit", {
            url : "export_orderattached_report_excel",
            onSubmit : function(param) {
                param.memberName = memberName ;
                param.cityCode =cityCode;
                param.lpn = lpn;
                param.phoneNumber =phoneNumber;
                param.type = type;
                param.queryDateFrom = queryDateFrom;
                param.queryDateTo = queryDateTo;
                param.payStatus = payStatus;
                /*param.cityCode = cityCode;
                param.begin = begin;
                param.end = end;*/
            },
            success : function(result) {
            }
        });
    });

    /**
     * 附属订单列表
     */
    $('#order_attached_grid').datagrid(
	{
		title : '违章事故收入报表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		url : 'page_orderattached_report',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		},{
            field : 'city',
            title : '所属城市',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
			field : 'orderId',
			title : '约车订单编号',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'membername',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
            field : 'phone',
            title : '手机号码',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
            field : 'brandName',
            title : '车辆品牌',
            width : $(this).width() * 0.06,
            align : 'center'
        }, {
			field : 'type',
			title : '订单类型',
			width : $(this).width() * 0.08,
			align : 'center'
		},  {
			field : 'createtime',
			title : '订单创建时间',
			width : $(this).width() * 0.10,
			align : 'center'
		}, {
            field : 'completiontime',
            title : '付款时间',
            width : $(this).width() * 0.10,
            align : 'center'
        }, {
			field : 'creater',
			title : '创建人',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
				field : 'paystatus',
				title : '支付状态',
				width : $(this).width() * 0.06,
				align : 'center',
            formatter : function (val) {
                if(val=='未支付'){
                    return '<font color=red>未支付</font>'
                }else{
                    return val;
                }
            }
		}, {
            field : 'paytype',
            title : '支付方式',
            width : $(this).width() * 0.06,
            align : 'center'
        },{
            field : 'lostIncome',
            title : '误工费',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'maintainCost',
            title : '维修费',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'rescueCost',
            title : '救援费',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'illegalProcessingCost',
            title : '违章处理费',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'insurancePremiumCost',
            title : '保险上涨费',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'ordermoney',
            title : '实际收入',
            width : $(this).width() * 0.06,
            align : 'center'
        }
		] ],
		pagination : true,
		rownumbers : true
	});

});

