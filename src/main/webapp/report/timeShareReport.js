$(function() {
	$('#timeLeaseGrid').datagrid(
        {
        title : '时租收入报表',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : false,
        nowrap : false,
        striped : true,
        collapsible : true,
        singleSelect : true,
        onLoadSuccess: compute,//加载完毕后执行计算
        url : 'time_share_list_report',
        pageSize : 30,
        pageList : [100,50,30,10],
        idField : 'id',
        columns: [[
            {
                field : 'endTime',
                title : '还车时间',
                width : $(this).width() * 0.08,
                align : 'center',
                /*formatter : function(val, rec) {
                    return val.substring(0,val.length-2);
                },*/
                rowspan:2
            },{
                field : 'payTime',
                title : '支付时间',
                width : $(this).width() * 0.08,
                align : 'center',
                /*formatter : function(val, rec) {
                    return val.substring(0,val.length-2);
                },*/
                rowspan:2
            },{
                field : 'payStatus',
                title : '支付状态',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter:function (val) {
                    if(val=="finish"){
                        return "已支付"
                    }
                    if(val=="noPay") {
                        return "未支付"
                    }


                },
                rowspan:2
            },{
                field : 'cityName',
                title : '所属城市',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            },{
                field : 'lpn',
                title : '车牌号',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            },{
                field : 'brandName',
                title : '车辆品牌',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            },{
                field : 'carModel',
                title : '车型号',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            },{
                field : 'type',
                title : '车类型',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            }, {
                field : 'name',
                title : '会员姓名',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            }, {
                field : 'phone',
                title : '手机号码',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            },{
                title:'价格系数',
                colspan:4
            },{
                field : 'totalMinute',
                title : '租赁时长(分钟)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val) {
                    n = parseInt(val).toFixed(0);
                    var re = /(\d{1,3})(?=(\d{3}))/g;
                    return n.replace(re, "$1,");
                },
                rowspan:2

            },{
                field : 'totalMileage',
                title : '行驶里程(公里)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val) {
                    n = parseInt(val).toFixed(0);
                    var re = /(\d{1,3})(?=(\d{3}))/g;
                    return n.replace(re, "$1,");
                },
                rowspan:2
            },{
                field : 'payMoney',
                title : '租赁收入(元)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val) {
                    n =  parseFloat(val).toFixed(2);
                    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                    return n.replace(re, "$1,");
                },
                rowspan:2
            },{
                field : 'totalPayMoney',
                title : '订单金额(元)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val) {
                    if (!isNaN(parseFloat(val))) {
                        n = parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3}))/g;
                        return n.replace(re, "$1,");
                    } else {
                        return "";
                    }
                } ,
                rowspan:2
            },{
                field : 'freeCompensationMoney',
                title : '商业保险(元)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val) {
                    if (!isNaN(parseFloat(val))) {
                        n = parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3}))/g;
                        return n.replace(re, "$1,");
                    } else {
                        return "";
                    }
                } ,
                rowspan:2
            },{
                field : 'reductionPayMoney',
                title : '优惠金额(元)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val) {
                    n =  parseFloat(val).toFixed(2);
                    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                    return n.replace(re, "$1,");
                },
                rowspan:2
            },{
                field : 'couponNo',
                title : '优惠券编号',
                width : $(this).width() * 0.08,
                align : 'center',
                rowspan:2
            },{
                field : 'couponBalance',
                title : '优惠券面值(元)',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val,rec) {
                    if (val) {
                        n =  parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");

                    }else if (rec.lpn) {
                        return '0.00';
                    }

                },
                rowspan:2
            },{
                field : 'invocieMoney',
                title : '开票金额（元）',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val,rec) {
                    if (val) {
                        n =  parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");

                    }else if (rec.lpn) {
                        return '0.00';
                    }

                },
                rowspan:2
            }
        ],[
            {
                field : 'timeRate',
                title : '单价(元/30分钟)',
                width : $(this).width() * 0.08,
                formatter : function(val) {
                    if (val) {
                        n =  parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }

                },
                rowspan:1,
                align : 'center'
            },{
                field : 'milesRate',
                title : '里程单价(元/公里)',
                width : $(this).width() * 0.08,
                formatter : function(val,rec) {
                    if (val) {
                        n =  parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }else if (rec.lpn) {
                        return '0.00';
                    }

                },
                rowspan:1,
                align : 'center'
            },{
                field : 'minConsump',
                title : '最低消费(元)',
                width : $(this).width() * 0.08,
                formatter : function(val,rec) {
                    if (val) {
                        n =  parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }else if (rec.lpn) {
                        return '0.00';
                    }

                },
                rowspan:1,
                align : 'center'
            },{
                field : 'maxConsump',
                title : '最高消费(元)',
                width : $(this).width() * 0.08,
                formatter : function(val) {
                    if (val) {
                        n =  parseFloat(val).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }

                },
                rowspan:1,
                align : 'center'
            }
        ]],
        pagination : true,
        rownumbers : true
    });
    //清空
    $('#clearBtn').bind('click',function () {
        $('#queryTimeLease').form('clear');

    })
	
    function compute() {//计算函数
        var rows = $('#timeLeaseGrid').datagrid('getRows')//获取当前的数据行
        var totalMinuteTotal = 0//计算租赁时长/分钟的总和
        ,totalKgTotal=0//行使里程的总和
        ,totalMoney=0//总收入
        ,totalPayMoney = 0
        ,totalReduction=0
        ,totalFreeComsationMoney =0
        ,totalInvoiceMoney=0;//优惠券兑换的总和
        
        for (var i = 0; i < rows.length; i++) {
        	totalMinuteTotal += parseFloat(rows[i]['totalMinute']);
        	totalKgTotal += parseFloat(rows[i]['totalMileage']);
        	totalMoney += parseFloat(rows[i]['payMoney']);
        	totalPayMoney += parseFloat(rows[i]['totalPayMoney']);
        	totalReduction += parseFloat(rows[i]['reductionPayMoney']);
        	totalInvoiceMoney += parseFloat(rows[i]['invocieMoney']);
        	totalFreeComsationMoney+= parseFloat(rows[i]['freeCompensationMoney']);
        }
       
        var totalMinuteTotalVal = Number(totalMinuteTotal) ;  
        if(!isNaN(parseFloat(totalMinuteTotalVal))) {    
        	totalMinuteTotalVal = totalMinuteTotalVal.toFixed(2);    
        }
        
        var totalKgTotalVal = Number(totalKgTotal)  ; 
        if(!isNaN(parseFloat(totalKgTotalVal))) {    
        	totalKgTotalVal = totalKgTotalVal.toFixed(2);    
        }
        
        var totalMoneyVal = Number(totalMoney)  ; 
        if(!isNaN(parseFloat(totalMoneyVal))) {    
        	totalMoneyVal = totalMoneyVal.toFixed(2);    
        }
        
        var totalPayMoneyVal = Number(totalPayMoney)  ; 
        if(!isNaN(parseFloat(totalPayMoneyVal))) {    
        	totalPayMoneyVal = totalPayMoneyVal.toFixed(2);    
        }
         
        var totalReductionVal = Number(totalReduction) ;  
        if(!isNaN(parseFloat(totalReductionVal))) {    
        	totalReductionVal = totalReductionVal.toFixed(2);    
        } 
        
        var totalInvoiceMoneyVal = Number(totalInvoiceMoney) ;  
        if(!isNaN(parseFloat(totalInvoiceMoneyVal))) {    
        	totalInvoiceMoneyVal = totalInvoiceMoneyVal.toFixed(2);    
        }
        var totalFreeComsationMoneyVal = Number(totalFreeComsationMoney) ;
        if(!isNaN(parseFloat(totalFreeComsationMoneyVal))) {
            totalFreeComsationMoneyVal = totalFreeComsationMoneyVal.toFixed(2);
        }

        //新增一行显示统计信息
		$('#timeLeaseGrid').datagrid('appendRow', {
			endTime : '合计：',
			totalMinute : totalMinuteTotalVal,
			totalMileage : totalKgTotalVal,
			payMoney : totalMoneyVal,
			totalPayMoney : totalPayMoneyVal,
			invocieMoney : totalInvoiceMoneyVal,
			reductionPayMoney : totalReductionVal,
            freeCompensationMoney:totalFreeComsationMoneyVal
		});
        
        var foot = $('#timeLeaseGrid').datagrid('getFooterRows');
        if (foot[0]) {
			$("#timeLeaseGrid").datagrid('appendRow', {
				endTime : '总合计：',
				totalMinute : foot[0].totalMinuteSum,
				totalMileage : foot[0].totalMileageSum,
				payMoney : foot[0].payMoneySum.toFixed(2),
				totalPayMoney : foot[0].totalPayMoneySum.toFixed(2),
				invocieMoney : foot[0].invoiceMoneySum.toFixed(2),
				reductionPayMoney : foot[0].reductionPayMoneySum.toFixed(2),
                freeCompensationMoney:foot[0].totalCompensationSum.toFixed(2)
			});
        }else{
			$("#timeLeaseGrid").datagrid('appendRow', {
				endTime : '总合计：',
				totalMinute : '0.00',
				totalMileage : '0.00',
				payMoney : '0.00',
				totalPayMoney : '0.00',
				invocieMoney : '0.00',
				reductionPayMoney : '0.00',
                freeCompensationMoney:'0.00'
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
		$('#queryDateFrom').textbox('setValue', '');
		$('#queryDateTo').textbox('setValue', '');
		$('#name').textbox('setValue', '');
		$('#invoiceStatus').combobox('invoiceStatus', '');
	
	}); 
	  
	// 查询
	function search() {	
		
		$('#timeLeaseGrid').datagrid('load', {
			'cityCode' : $('#cityCode').textbox('getValue'),
			'lpn' : $('#lpn').textbox('getValue'),
			'queryDateFrom' : $('#queryDateFrom').datetimebox('getValue'),
			'queryDateTo' : $('#queryDateTo').datetimebox('getValue'),
			'invoiceStatus' : $('#invoiceStatus').combobox('getValue'),
			'name' : $('#name').textbox('getValue'),
            'payBeginTime':$('#payBeginTime').combobox('getValue'),
            'payEndTime':$('#payEndTime').combobox('getValue'),
            'payStatus':$('#payStatus').combobox('getValue')



		});	
	}

	//专车收入查询导出excel链接
	$("#btnExport").bind("click",function(){
		var cityCode = $('#cityCode').textbox('getValue') ;
		var lpn = $('#lpn').textbox('getValue');
		var queryDateFrom = $('#queryDateFrom').textbox('getValue');
		var queryDateTo = $('#queryDateTo').textbox('getValue');
		var name = $('#name').textbox('getValue');
		var invoiceStatus = $('#invoiceStatus').combobox('getValue');
        var payBeginTime = $('#payBeginTime').combobox('getValue');
        var payEndTime = $('#payEndTime').combobox('getValue');
        var payStatus = $('#payStatus').combobox('getValue');

	    $("#queryTimeLease").form("submit", {
					url : "export_time_share_excel?name="+name+"&cityCode="+cityCode+
                    "&lpn="+lpn+"&queryDateFrom="+queryDateFrom+"&queryDateTo="+queryDateTo+
                    "&invoiceStatus="+invoiceStatus+'&payBeginTime='+payBeginTime+
                    '&payEndTime='+payEndTime+'&payStatus=0'+payStatus,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });	
	});
