function search(){
	var memberName = $.trim($("#memberName").val()) ;
	var phoneNumber = $.trim($("#phoneNumber").val());
	var cityCode = $('#cityCode').textbox('getValue') ;
	var lpn = $('#lpn').textbox('getValue');
	var orderId = $("#orderId").textbox('getValue');
	var queryDateFrom = $('#queryDateFrom').textbox('getValue');
	var queryDateTo = $('#queryDateTo').textbox('getValue');
	var status = $('#status').combobox('getValue');
    var isEnterpriseUseCar = $('#isEnterpriseUseCar').combobox('getValue');
	$('#orderGrid').datagrid('load',{
		memberName : memberName ,
		cityCode :cityCode,
		phoneNumber :phoneNumber,
		lpn : lpn,
		queryDateFrom : queryDateFrom,
		queryDateTo : queryDateTo,
		status : status,
		orderId : orderId,
        isEnterpriseUseCar:isEnterpriseUseCar
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
		var phoneNumber = $.trim($("#phoneNumber").val());
		var cityCode = $('#cityCode').textbox('getValue') ;
		var lpn = $('#lpn').textbox('getValue');
		var orderId = $("#orderId").textbox('getValue');
		var queryDateFrom = $('#queryDateFrom').textbox('getValue');
		var queryDateTo = $('#queryDateTo').textbox('getValue');
		var status = $('#status').combobox('getValue');
		var isEnterpriseUseCar = $('#isEnterpriseUseCar').combobox('getValue');
		$("#historyOrderForm").form("submit", {
							url : "export_historyOrder_list?memberName=" + memberName
									+ "&phoneNumber=" + phoneNumber + "&cityCode=" + cityCode
									+ "&lpn=" + lpn + "&orderId=" + orderId
									+ "&queryDateFrom=" + queryDateFrom + "&queryDateTo=" + queryDateTo
									+ "&status=" + status+"&isEnterpriseUseCar="+isEnterpriseUseCar,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	// 清空
	$("#clearQuery").bind("click", function() {
        $('#historyOrderForm').form('clear');
		/*$('#cityCode').textbox('setValue', '');
		$('#lpn').textbox('setValue', '');
		$('#phoneNumber').textbox('setValue', '');
		$('#queryDateFrom').textbox('setValue', '');
		$('#queryDateTo').textbox('setValue', '');
		$('#memberName').textbox('setValue', '');
		$('#orderId').textbox('setValue', '');*/
	}); 
	
	$('#orderGrid')
	.datagrid(
	{
		title : '时租订单',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'timeShare_history_order_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'orderId',
			title : '订单编号',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '会员号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'parkName',
			title : '预约网点',
			width : $(this).width() * 0.08,
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
			field : 'orderTime',
			title : '预约时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'beginTime',
			title : '上车时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'endTime',
			title : '还车时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'status',
			title : '订单状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
			    if(val == "cancel"){
			    	return "已取消" ;
			    }else if(val == "finish"){
			    	if(rec.payStatus == "noPay"){
			    		return "<font color='red'>未支付</font>" ;
			    	}else{
			    		return "已完成" ;
			    	}
			    }
			}
		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
            field : 'isEnterpriseUseCar',
            title : '订单类型',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val == "false")
                    return "个人约车" ;
                if(val == "FALSE")
                    return "个人约车" ;
                if(val == "true")
                    return "企业约车" ;
            }
        },
        {
            field : 'payMoney',
            title : '个人支付金额',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                // console.log("lastMoney>>>>");
                // console.log(val);
                // console.log(rec);
                if(val == ""||val==null)
                    return 0 ;
               else
                   return val/100;
            }
        },
        {
            field : 'entprisePayMoney',
            title : '企业支付金额',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(rec.isEnterpriseUseCar=='true'){
                	if(rec.payStatus == "noPay"){
                		return "<font color='red'>未支付</font>" ;
                	}else{
                		return val/100;
                	}
                }else{
                	// 如果这个是个人预约的订单,企业支付金额默认为0
                	return 0 ;
                }
            }
        }
        ,{
			field : 'orderPayMoney',
			title : '总支付金额（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = ((rec.entprisePayMoney+rec.payMoney)/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		},
        {
			field : 'returnParkName',
			title : '还车网点',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'totalMinute',
			title : '使用时长（分）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		},{
			field : 'totalMinuteCost',
			title : '时长花费（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
					return "0.00";
			    else
			       n = (val/100).toFixed(2);
				   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			   return n.replace(re, "$1,");	
			}
		}, {
			field : 'nightTotalMinute',
			title : '使用时长(夜)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		}, {
			field : 'totalMileage',
			title : '行驶里程（公里）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		},{
			field : 'totalMileageCost',
			title : '里程花费（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0){
					return "0.00";
				}else{
					n = (val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
	       			return n.replace(re, "$1,");	
			    }
				 
			}
		},{
			field : 'freeCompensationMoney',
			title : '商业保险（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		},{
			field : 'totalPayMoney',
			title : '订单消费（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		},{
			field : 'couponNo',
			title : '优惠券编号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'couponBalance',
			title : '优惠券面值（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		},{
			field : 'nightMinuteReductionMoney',
			title : '夜时长优惠',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
					return "0.00";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		} ,{
			field : 'dayRentReductionPayMoney',
			title : '日上限优惠',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (rec.consumpVal != 0 && rec.totalPayMoney > rec.consumpVal) {
					n = ((rec.totalPayMoney - (rec.payMoney - rec.freeCompensationMoney + rec.couponBalance))/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
	       			return n.replace(re, "$1,");	
				}else{
					return "0.00";
				}
			    
		    }
		},{
			field : 'reductionPayMoney',
			title : '总优惠金额（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		},{
			field : 'consumpVal',
			title : '日租封顶价格（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");	
			}
		},{
			field : 'discountMoney',
			title : '折扣优惠金额（元）',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0)
			       return "0.00";
			    else
			    	n = (val/100).toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
			}
		},{
			field : 'memberLevelDiscount',
			title : '会员等级折扣',
			width : $(this).width() * 0.08,
			align : 'center',
		}
		] ],
		pagination : true,
		rownumbers : true
	});
});

