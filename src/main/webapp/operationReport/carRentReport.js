$(function() {
	//查询链接
	$("#btnQuery").bind("click",function(){
		var memberName = $.trim($("#memberName").val()) ;
		var cityCode = $('#code').combobox('getValue') ;
		var lpn = $('#lpn').textbox('getValue');
		var phone = $('#phone').textbox('getValue');
		var invoiceStatus = $('#invoiceStatus').combobox('getValue');
		var bt = $("#bt").datetimebox("getValue");
		var et = $("#et").datetimebox("getValue");
		$('#dataGrid').datagrid('load',{
			memberName : memberName ,
			cityCode :cityCode,
			lpn : lpn,
			phone : phone,
			invoiceStatus : invoiceStatus,
			bt : bt ,
			et : et
		}) ;
	});
	//导出execl
	$("#btnExport").bind("click",function(){
		var param=   $('#dataGrid').datagrid('options').queryParams;//查询参数
		var url = "export_carDayRent_report?"+jQuery.param(param);
		window.location.href = url ;
	});
	
	$('#dataGrid').datagrid( {
		title : '日租收入列表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		pagination : true,
		rownumbers : true,
//		onLoadSuccess: carRentReportCompute,//加载完毕后执行计算
		url : 'day_rent_report_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox:true
		},{
			field : 'cityName',
			title : '所属地区',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'orderId',
			title : '订单号',
			width : $(this).width() * 0.2,
			align : 'center',
		}, {
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'type',
			title : '车型',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if(val != null){
					if(val.indexOf(",") !=-1 ){
						var val2 = val.substring(val.lastIndexOf(",")+1,val.length);
						if(val2 != "" && val2.indexOf("•") < 0){
							return val2.substring(0,2) + "•" + val2.substring(2);
						}
					}else{
						if(val != "" && val.indexOf("•") < 0){
							return val.substring(0,2) + "•" + val.substring(2);
						}
					}
				}
			}
		},{
			field : 'beginTime',
			title : '订单开始时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'returnTimes',
			title : '订单结束时间',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
		}, {
			field : 'payStatus',
			title : '         ',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
		}, {
			field : 'returnTime',
			title : '订单结束时间',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec){
				if(val != null ){
					var array = [] ;
					array = rec.payStatus.split(",");
					if(array[0] == 'finish' && array[1] == 'finish'){
						return val;
					}else if(array[0] == 'finish' && array[1] != 'finish'){
						return rec.returnTimes ;
					}else{
						return rec.returnTimes;
					}
				}
			}
		}, {
			field : 'orderMoney',
			title : '首租金额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'reletOrderMoney',
			title : '续租金额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'freeCompensateMoney',
			title : '首租商业保险(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'reletFreeMoney',
			title : '续租商业保险(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'couponBalance',
			title : '首租优惠券面值(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'reletCouponBalance',
			title : '续租优惠券面值(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'discountMoney',
			title : '首租折扣金额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'reletDiscountMoney',
			title : '续租折扣金额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		} , {
			field : 'payMoney',
			title : '支付总金额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if (val) {
					n =  parseFloat(val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");
				}else if (rec.lpn) {
					return '0.00';
				}
			}
		}, {
			field : 'payType',
			title : '支付方式',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if("B"==val) return "余额";
				if("A"==val) return "支付宝";
				if("T"==val) return "财付通";
				if("WX"==val) return "微信";
				if("U"==val) return "银联";
				if("AP"==val) return "apple pay" ;
			}
		}, {
			field : 'invoiceStatus',
			title : '是否开票',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				switch (val) {
				case 0:
					return "未开票";
					break;
				case 1:
					return "未开票";
					break;
				case 2:
					return "开票中";
					break;
				case 3:
					return "已开票";
					break;
				}
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
});
//function carRentReportCompute() {//计算函数
//    var rows = $('#dataGrid').datagrid('getRows');
//    var totalOrderMoney = 0 ;
//    var totalReletOrderMoney = 0 ;
//    var TotalFreeCompensateMoney = 0 ;
//    var TotalReletFreeMoney = 0;
//    var totalCouponBalance = 0 ;
//    var totalReletCouponBalance = 0;
//    var totalDiscountMoney = 0;
//    var totalReletDiscountMoney = 0;
//    var totalPayMoney = 0;
//    
//    for (var i = 0; i < rows.length; i++) {
//    	totalOrderMoney += parseFloat(rows[i]['orderMoney']);
//    	totalReletOrderMoney += parseFloat(rows[i]['reletOrderMoney']);
//    	TotalFreeCompensateMoney += parseFloat(rows[i]['freeCompensateMoney']);
//    	TotalReletFreeMoney += parseFloat(rows[i]['reletFreeMoney']);
//    	totalCouponBalance += parseFloat(rows[i]['couponBalance']);
//    	totalReletCouponBalance += parseFloat(rows[i]['reletCouponBalance']);
//    	totalDiscountMoney += parseFloat(rows[i]['discountMoney']);
//    	totalReletDiscountMoney += parseFloat(rows[i]['reletDiscountMoney']);
//    	totalPayMoney += parseFloat(rows[i]['payMoney']);
//    }
//    
//    var totalOrderMoney = Number(totalOrderMoney) ;  
//    var totalReletOrderMoney = Number(totalReletOrderMoney) ;  
//    var TotalFreeCompensateMoney = Number(TotalFreeCompensateMoney) ;  
//    var TotalReletFreeMoney = Number(TotalReletFreeMoney) ;  
//    var totalCouponBalance = Number(totalCouponBalance) ;  
//    var totalReletCouponBalance = Number(totalReletCouponBalance) ;  
//    var totalDiscountMoney = Number(totalDiscountMoney) ;  
//    var totalReletDiscountMoney = Number(totalReletDiscountMoney) ;  
//    var totalPayMoney = Number(totalPayMoney) ; 
//    
//    
//    if(!isNaN(parseFloat(totalOrderMoney))) totalOrderMoney = totalOrderMoney.toFixed(2); 
//    if(!isNaN(parseFloat(totalReletOrderMoney))) totalReletOrderMoney = totalReletOrderMoney.toFixed(2); 
//    if(!isNaN(parseFloat(TotalFreeCompensateMoney))) TotalFreeCompensateMoney = TotalFreeCompensateMoney.toFixed(2); 
//    if(!isNaN(parseFloat(TotalReletFreeMoney))) TotalReletFreeMoney = TotalReletFreeMoney.toFixed(2); 
//    if(!isNaN(parseFloat(totalCouponBalance))) totalCouponBalance = totalCouponBalance.toFixed(2); 
//    if(!isNaN(parseFloat(totalReletCouponBalance))) totalReletCouponBalance = totalReletCouponBalance.toFixed(2); 
//    if(!isNaN(parseFloat(totalDiscountMoney))) totalDiscountMoney = totalDiscountMoney.toFixed(2); 
//    if(!isNaN(parseFloat(totalReletDiscountMoney))) totalReletDiscountMoney = totalReletDiscountMoney.toFixed(2); 
//    if(!isNaN(parseFloat(totalPayMoney))) totalPayMoney = totalPayMoney.toFixed(2); 
//    
//    $('#dataGrid').datagrid('appendRow', { cityName: '合计：', 
//    	orderMoney: totalOrderMoney,
//    	reletOrderMoney: totalReletOrderMoney,
//    	freeCompensateMoney: TotalFreeCompensateMoney,
//    	reletFreeMoney: TotalReletFreeMoney,
//    	couponBalance: totalCouponBalance,
//    	reletCouponBalance: totalReletCouponBalance,
//    	discountMoney: totalDiscountMoney,
//    	reletDiscountMoney: totalReletDiscountMoney,
//    	payMoney: totalPayMoney,
//    	
//    });
//    
//}