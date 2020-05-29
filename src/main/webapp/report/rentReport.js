$(function() {

	// 隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();

	// 根据不同周期（年、月、季度、自选）来显示相关label
	$('#period').combobox({
		onSelect : function(param) {
			onSelPeriod();
		}
	});

	$('#dataGrid').datagrid({
		title : '租赁收入报表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		singleSelect : true,
		// onLoadSuccess: computeTodayAndThisMonthAndYear,//加载完毕后执行计算
		onLoadSuccess : computeYearTotal,// 加载完毕后执行计算,只是计算当前区间的总和和年度总和
		// onLoadSuccess: compute,
		url : 'rent_report',
		pageSize : 100,
		pageList : [ 100, 50, 30, 10 ],
		idField : 'id',
		columns : [ [ {
			field : 'endTime',
			title : '日期',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.08,
			align : 'center'

		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
			
		}, {
			field : 'brandName',
			title : '车辆品牌',
			width : $(this).width() * 0.08,
			align : 'center'

		}, {
			field : 'orderId',
			title : '车辆品牌',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden: true

		}, {
			field : '     ',
			title : '订单类型',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val,rec) {
				if(rec.orderId != null && rec.orderId != ""){
					if(rec.orderId.indexOf('TS')!= -1){
						return "时租";
					}else{
						return "日租";
					}
				}
			}

		}, {
			field : 'memberId',
			title : '会员ID',
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
			field : 'totalMinute',
			title : '租赁时长（分钟）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		}, {
			field : 'rate',
			title : '单价（元/30分钟）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val) {
					n = val.toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}

			}
		}, {
			field : 'totalPayMoney',
			title : '订单金额（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}

		}, 
			{
			field : 'couponBalance',
			title : '优惠券面值（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}
		}, {
			field : 'nightReductionMoney',
			title : '夜间优惠（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}
		},
			{
			field : 'reductionMoney',
			title : '总优惠（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}
		}, {
			field : 'freeCompensationMoney',
			title : '保险（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}
		}, {
			field : 'payMoney',
			title : '实际收入（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}
		}, {
			field : 'invoiceMoney',
			title : '开票金额（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = parseFloat(val).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");
			}
		} ] ],
		pagination : true,
		rownumbers : true
	});

	/*
	 * function compute() {//计算函数
	 *  }
	 */

	/*
	 * 统计本日、本月、本年的合计
	 */
	// function computeTodayAndThisMonthAndYear(){
	// var rows = $('#dataGrid').datagrid('getRows')//获取当前的数据行
	// var totalMinuteTotal = 0//计算租赁时长/分钟的总和
	// ,totalPayMoney=0//总收入
	// ,totalCoupon=0//优惠券总和
	// ,totalFreeCompensation=0//保险
	// , totalIncome=0;//总实际收入
	//        
	// for (var i = 0; i < rows.length; i++) {
	// totalMinuteTotal += parseFloat(rows[i]['totalMinute']);
	// totalPayMoney += parseFloat(rows[i]['totalPayMoney']);
	// totalCoupon += parseFloat(rows[i]['couponBalance']);
	// totalFreeCompensation += parseFloat(rows[i]['freeCompensationMoney']);
	// totalIncome += parseFloat(rows[i]['payMoney']);
	// }
	//       
	// var totalMinuteTotalVal = Number(totalMinuteTotal) ;
	// if(!isNaN(parseFloat(totalMinuteTotalVal))) {
	// totalMinuteTotalVal = totalMinuteTotalVal.toFixed(2);
	// }
	//        
	// var totalMoneyVal = Number(totalPayMoney) ;
	// if(!isNaN(parseFloat(totalMoneyVal))) {
	// totalMoneyVal = totalMoneyVal.toFixed(2);
	// }
	//         
	// var totalCouponVal = Number(totalCoupon) ;
	// if(!isNaN(parseFloat(totalCouponVal))) {
	// totalCouponVal = totalCouponVal.toFixed(2);
	// }
	//        
	// var totalFreeCompensationVal = Number(totalFreeCompensation) ;
	// if(!isNaN(parseFloat(totalFreeCompensationVal))) {
	// totalFreeCompensationVal = totalFreeCompensationVal.toFixed(2);
	// }
	//        
	// var totalIncomeVal = Number(totalIncome) ;
	// if(!isNaN(parseFloat(totalIncomeVal))) {
	// totalIncomeVal = totalIncomeVal.toFixed(2);
	// }
	//        
	// //新增一行显示统计信息
	// $('#dataGrid').datagrid('appendRow', { endTime: '合计：', totalMinute:
	// totalMinuteTotalVal,
	// payMoney: totalIncomeVal, couponBalance: totalCouponVal,
	// freeCompensationMoney: totalFreeCompensationVal, totalPayMoney:
	// totalMoneyVal});
	// //新增总合计
	// var foot = $('#dataGrid').datagrid('getFooterRows');
	// console.log(foot);
	// if (foot[0] != null){
	// $("#dataGrid").datagrid('appendRow', {endTime: '总合计：', totalMinute:
	// foot[0].totalMinuteSum,
	// payMoney: foot[0].payMoneySum.toFixed(2) ,couponBalance:
	// foot[0].couponBalanceSum.toFixed(2),
	// freeCompensationMoney: foot[0].freeCompsationMoneySum.toFixed(2),
	// totalPayMoney: foot[0].totalPayMoneySum.toFixed(2)});
	// }else{
	//
	// $("#dataGrid").datagrid('appendRow', {endTime: '总合计：', totalMinute:
	// '0.00',
	// payMoney: '0.00' ,couponBalance: '0.00',
	// freeCompensationMoney: '0.00', totalPayMoney: '0.00'});
	// }
	//        
	// $.post("getTodayAndThisMonthAndYearAmount",
	// {
	// 'cityCode' : $('#cityCode').textbox('getValue'),
	// 'lpn' : $('#lpn').textbox('getValue'),
	// 'invoiceStatus' : $('#invoiceStatus').textbox('getValue'),
	// 'name' : $('#name').textbox('getValue')
	// },
	// function(data){
	// var json = $.parseJSON(data);
	// var todayAmount = json.todayAmount;
	// var thisMonthAmount = json.thisMonthAmount;
	// var thisYearAmount = json.thisYearAmount;
	// getAmount(todayAmount, "今日合计:");
	// getAmount(thisMonthAmount, "本月合计:");
	// getAmount(thisYearAmount, "本年合计:");
	// });
	// }

	function computeYearTotal() {
		// 总合计
		getTotal();
		// 年度数据
		getRentYearTotal();
		// $.post("getRentYearTotal",
		// {
		// 'cityCode' : $('#cityCode').textbox('getValue'),
		// 'lpn' : $('#lpn').textbox('getValue'),
		// 'name' : $('#name').textbox('getValue')
		// },
		// function(data){
		// console.log(data);
		// var json = $.parseJSON(data);
		// var thisYearAmount = json.thisYearAmount;
		// getAmount(thisYearAmount, "本年合计:");
		// });
	}

	// 计算年度
	function getRentYearTotal() {
		$.ajax({
			type : 'POST',
			url : 'getRentYearTotal',
			dataType : 'JSON',
			async : 'false',
			data : {
				'cityCode' : $('#cityCode').textbox('getValue'),
				'lpn' : $('#lpn').textbox('getValue'),
				'invoiceStatus' :  $('#invoiceStatus').combobox('getValue'),
				'name' : $('#name').textbox('getValue')
			},
			success : function(data) {
				//console.log(data);
				var thisYearAmount = data.thisYearAmount;
				getAmount(thisYearAmount, "本年合计:");
			}
		});
	}

	// 计算合计数据
	function getTotal() {
		// 年度数据
		// var totalMinuteTotal = 0//计算租赁时长/分钟的总和
		// ,totalPayMoney=0//总收入
		// ,totalCoupon=0//优惠券总和
		// ,totalFreeCompensation=0//保险
		// ,totalIncome=0;//总实际收入
		// $.post("getRentTotal", {
		// 'cityCode' : $('#cityCode').textbox('getValue'),
		// 'lpn' : $('#lpn').textbox('getValue'),
		// 'begin' : $("#hidBegin").val(),
		// 'end' : $("#hidEnd").val(),
		// 'name' : $('#name').textbox('getValue')
		// },
		// function(data){
		// console.log(data);
		// var json = $.parseJSON(data);
		// var rentTotal = json.rentTotal;
		// getAmount(rentTotal, "总合计:");
		// });

		// 使用同步方法
		$.ajax({
			type : 'POST',
			url : 'getRentTotal',
			dataType : 'JSON',
			async : 'false',
			data : {
				'cityCode' : $('#cityCode').textbox('getValue'),
				'lpn' : $('#lpn').textbox('getValue'),
				'invoiceStatus' :  $('#invoiceStatus').combobox('getValue'),
				'begin' : $("#hidBegin").val(),
				'end' : $("#hidEnd").val(),
				'name' : $('#name').textbox('getValue'),
				'orderType': $('#orderType').combobox('getValue')
			},
			success : function(data) {
				//console.log(data);
				// var json = $.parseJSON(data);
				var rentTotal = data.rentTotal;
				getAmount(rentTotal, "总合计:");
			}
		});
	}

	function getAmount(amount, title) {
		// debugger;
		if (null != amount) {
			var totalMinute = amount.totalMinute;
			var payMoney = amount.payMoney;
			var couponBalance = amount.couponBalance;
			var freeCompensationMoney = amount.freeCompensationMoney;
			var totalPayMoney = amount.totalPayMoney;
			var nightReductionMoney = amount.nightReductionMoney;
			var reductionMoney = amount.reductionMoney;
			var totalInvoiceMoney = amount.totalInvoiceMoney;
			totalMinute = Number(totalMinute);
			if (!isNaN(totalMinute)) {
				totalMinute = totalMinute.toFixed(2);
			} else {
				totalMinute = 0;
			}

			payMoney = Number(payMoney);
			if (!isNaN(payMoney)) {
				payMoney = payMoney.toFixed(2);
			} else {
				payMoney = 0;
			}

			couponBalance = Number(couponBalance);
			if (!isNaN(couponBalance)) {
				couponBalance = couponBalance.toFixed(2);
			} else {
				couponBalance = 0;
			}

			freeCompensationMoney = Number(freeCompensationMoney);
			if (!isNaN(freeCompensationMoney)) {
				freeCompensationMoney = freeCompensationMoney.toFixed(2);
			} else {
				freeCompensationMoney = 0;
			}

			totalPayMoney = Number(totalPayMoney);
			if (!isNaN(totalPayMoney)) {
				totalPayMoney = totalPayMoney.toFixed(2);
			} else {
				totalPayMoney = 0;
			}
			
			nightReductionMoney = Number(nightReductionMoney);
			if (!isNaN(nightReductionMoney)) {
				nightReductionMoney = nightReductionMoney.toFixed(2);
			} else {
				nightReductionMoney = 0;
			}
			
			reductionMoney = Number(reductionMoney);
			if (!isNaN(reductionMoney)) {
				reductionMoney = reductionMoney.toFixed(2);
			} else {
				reductionMoney = 0;
			}
			totalInvoiceMoney = Number(totalInvoiceMoney);
			if (!isNaN(totalInvoiceMoney)) {
				totalInvoiceMoney = totalInvoiceMoney.toFixed(2);
			} else {
				totalInvoiceMoney = 0;
			}
			
			// 新增一行显示统计信息
			$('#dataGrid').datagrid('appendRow', {
				endTime : title,
				totalMinute : totalMinute,
				payMoney : payMoney,
				couponBalance : couponBalance,
				freeCompensationMoney : freeCompensationMoney,
				nightReductionMoney : nightReductionMoney,
				reductionMoney : reductionMoney,
				totalPayMoney : totalPayMoney,
				invoiceMoney : totalInvoiceMoney
			});
		} else {
			$('#dataGrid').datagrid('appendRow', {
				endTime : title,
				totalMinute : 0.00,
				payMoney : 0.00,
				couponBalance : 0.00,
				freeCompensationMoney : 0.00,
				nightReductionMoney : 0.00,
				reductionMoney : 0.00,
				totalPayMoney : 0.00,
				invoiceMoney : 0.00
			});
		}
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

	// 清空
	$("#btnClear").bind("click", function() {
		$('#cityCode').textbox('setValue', '');
		$('#lpn').textbox('setValue', '');
		$('#name').textbox('setValue', '');
		$('#invoiceStatus').combobox('setValue', '');
		$('#orderType').combobox('setValue', '');
	});

	// 查询
	function search() {
		setBeginAndEnd2();
		var options = $('#dataGrid').datagrid('options');
		options.url = 'rent_report.do';
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		if(!begin || !end){
			$.messager.alert('提示','请输入日期！');
			return;
		}

		$('#dataGrid').datagrid('load', {
			'cityCode' : $('#cityCode').textbox('getValue'),
			'invoiceStatus' :  $('#invoiceStatus').combobox('getValue'),
			'lpn' : $('#lpn').textbox('getValue'),
			'begin' : begin,
			'end' : end,
			'name' : $('#name').textbox('getValue'),
			'orderType' : $('#orderType').combobox('getValue')
		});
	}

	// 专车收入查询导出excel链接
	$("#btnExport").bind("click", function() {
		setBeginAndEnd2();

		var cityCode = $('#cityCode').textbox('getValue');
		var lpn = $('#lpn').textbox('getValue');
		var invoiceStatus = $('#invoiceStatus').combobox('getValue');
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		var name = $('#name').textbox('getValue');
		var orderType = $('#orderType').combobox('getValue');
		$("#rentReportForm").form("submit", {
			url : "export_rent_report_excel",
			onSubmit : function(param) {
				param.name = encodeURIComponent(name);
				param.cityCode = cityCode;
				param.lpn = encodeURIComponent(lpn);
				param.begin = begin;
				param.invoiceStatus = invoiceStatus;
				param.end = end;
				param.orderType = orderType;
			},
			success : function(result) {
			}
		});
	});

});
