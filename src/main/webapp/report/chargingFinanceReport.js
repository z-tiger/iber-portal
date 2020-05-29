$(function() {

	// 隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();

	// 根据不同周期（年、月、季度、自选）来显示相关label
	$('#period').combobox({
		onSelect : function(param) {
			onSelPeriod();
		}
	});


    //清空
    $('#clearBtn').on('click',function () {
        // console.log("click");
        $('#toolbar').form('clear');
    })

    $('#dataGrid').datagrid(
        {
            title : '充电财务报表',
            width : 'auto',
            height : 'auto',
            fit : true,
            fitColumns : false,
            nowrap : true,
            striped : true,
            collapsible : true,
            singleSelect : true,
            // onLoadSuccess: computeSum,//加载完毕后执行计算
            url : 'charging_finance_report',
            pageSize : 50,
            pageList : [100,50,30,10],
            idField : 'id',
            columns: [[
                {
                    field : 'cityName',
                    title : '所属城市',
                    width : $(this).width() * 0.08,
                    align : 'center'
                },
                {
                    field : 'createTime',
                    title : '充电开始时间',
                    width : $(this).width() * 0.1,
                    align : 'center'
                }, {
                    field : 'payTime',
                    title : '支付时间',
                    width : $(this).width() * 0.1,
                    align : 'center'
                },{
                    field : 'lpn',
                    title : '车牌号',
                    width : $(this).width() * 0.08,
                    align : 'center'

                },{
                    field : 'payStatus',
                    title : '支付状态',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter:function (val) {
                        if(val=='finish') {
                            return "已支付";
                        }
                        if(val=='noPay') {
                            return "未支付";
                        }
                    }

                },/*{
			field : 'brandName',
			title : '车辆品牌',
			width : $(this).width() * 0.08,
			align : 'center'

		},*/ {
                    field : 'memberId',
                    title : '会员ID',
                    width : $(this).width() * 0.08,
                    align : 'center'
                }, {
                    field : 'parkName',
                    title : '网点名称',
                    width : $(this).width() * 0.08,
                    align : 'center'

                }, {
                    field : 'equipmentCode',
                    title : '充电桩编码',
                    width : $(this).width() * 0.08,
                    align : 'center'

                }, {
                    field : 'equipmentType',
                    title : '类型【慢快充】',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if(1 == val){
                            return "快";
                        }else if(2 == val){
                            return "慢";
                        }
                    }
                }, {
                    field : 'chargingAmount',
                    title : '充电度数',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if (val) {
                            if(!isNaN(Number(val))){
                                n = parseFloat(val).toFixed(2);
                                var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                                return n.replace(re, "$1,");
                            }else{
                                return "";
                            }
                        }

                    }
                }, {
                    field : 'chargingPrice',
                    title : '电费单价（元/度）',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if(!isNaN(Number(val))){
                            n = parseFloat(val).toFixed(2);
                            var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                            return n.replace(re, "$1,");
                        }else{
                            return "";
                        }
                    }

                }, {
                    field : 'servicePrice',
                    title : '服务费单价（元/度）',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if(!isNaN(Number(val))){
                            n = parseFloat(val).toFixed(2);
                            var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                            return n.replace(re, "$1,");
                        }else{
                            return "";
                        }
                    }

                },{
                    field : 'payMoney',
                    title : '充电收入（元）',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if(!isNaN(Number(val))){
                            n = parseFloat(val).toFixed(2);
                            var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                            return n.replace(re, "$1,");
                        }else{
                            return "";
                        }
                    }
                },{
                    field : 'orderMoney',
                    title : '计费金额（元）',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if(!isNaN(Number(val))){
                            n = parseFloat(val).toFixed(2);
                            var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                            return n.replace(re, "$1,");
                        }else{
                            return "";
                        }
                    }
                } ,{
                    field : 'invoiceMoney',
                    title : '开票金额（元）',
                    width : $(this).width() * 0.08,
                    align : 'center',
                    formatter : function(val) {
                        if(!isNaN(Number(val))){
                            n = parseFloat(val).toFixed(2);
                            var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                            return n.replace(re, "$1,");
                        }else{
                            return "";
                        }
                    }
                }
            ]],
            pagination : true,
            rownumbers : true
        });





    function computeSum() {
		// 本年合计、总合计
		getTotal();
	}


	// 计算合计数据
	function getTotal() {

		// 使用同步方法
		$.ajax({
			type : 'POST',
			url : 'getChargingSum',
			dataType : 'JSON',
			async : 'false',
			data : {
				'cityCode' : $('#cityCode').textbox('getValue'),
				'invoiceStatus' :  $('#invoiceStatus').combobox('getValue'),
				'begin' : $("#hidBegin").val(),
				'end' : $("#hidEnd").val(),
                'payBeginTime':$('#hidPayBeginTime').val(),
                'payEntTime':$('#hidPayEndTime').val(),
                'payStatus':$('#payStatus').combobox('getValue')


            },
			success : function(data) {
				// var json = $.parseJSON(data);
				var chargingAmountThisYearSum = data.chargingAmountThisYearSum;
				var payMoneyThisYearSum = data.payMoneyThisYearSum;
				var invoiceMoneyYearSum = data.invoiceMoneyYearSum;
				
				var chargingAmountSum = data.chargingAmountSum;
				var payMoneySum = data.payMoneySum;
				var invoiceMoneySum = data.invoiceMoneySum;
				// 新增2行显示统计信息
				$('#dataGrid').datagrid('appendRow', {
					createTime : "本年合计",
					chargingAmount : chargingAmountThisYearSum,
					payMoney : payMoneyThisYearSum,
					invoiceMoney : invoiceMoneyYearSum
				});
				$('#dataGrid').datagrid('appendRow', {
					createTime : "总合计",
					chargingAmount : chargingAmountSum,
					payMoney : payMoneySum,
					invoiceMoney : invoiceMoneySum
				});
			}
		});
	}

	// 查询
	$("#btnQuery").bind("click", function() {
		search();
	});
	// 绑定enter建查询
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});


	// 查询
	function search() {
		setBeginAndEnd2();
		// var options = $('#dataGrid').datagrid('options');
		// options.url = 'charging_finance_report.do';
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		/*if(!begin || !end){
			$.messager.alert('提示','请输入日期！');
			return;
		}*/

		$('#dataGrid').datagrid('load', {
			'cityCode' : $('#cityCode').textbox('getValue'),
			'invoiceStatus' :  $('#invoiceStatus').combobox('getValue'),
			'begin' : begin,
			'end' : end,
            'payBeginTime':$('#hidPayBeginTime').val(),
            'payEndTime':$('#hidPayEndTime').val(),
            'payStatus':$('#payStatus').combobox('getValue')

		});
	}

	// 专车收入查询导出excel链接
	$("#btnExport").bind("click", function() {
		setBeginAndEnd2();

		var cityCode = $('#cityCode').textbox('getValue');
		var invoiceStatus =  $('#invoiceStatus').combobox('getValue');
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		var payBeginTime = $('#hidPayBeginTime').val();
		var payEndTime = $('#hidPayEndTime').val();
        var payStatus = $('#payStatus').combobox('getValue');

		$("#chargingReportForm").form("submit", {
			url : "export_charging_finance_report_excel",
			onSubmit : function(param) {
				param.cityCode = cityCode;
				param.invoiceStatus = invoiceStatus;
				param.begin = begin;
				param.end = end;
				param.payBeginTime = payBeginTime;
				param.payEntTime = payEndTime;
				param.payStatus = payStatus;
			},
			success : function(result) {
			}
		});
	});

});
