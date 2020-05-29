$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '会员充电消费明细',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				onLoadSuccess: compute,//加载完毕后执行计算
				url : 'member_chargingDetail',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'name': $('#name').textbox('getValue'),
                    'phoneNumber': $('#phoneNumber').textbox('getValue'),
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue')
				},
                columns: [[{
        					field : 'name',
        					title : '会员姓名',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'phone',
        					title : '手机号码',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
           					field : 'sex',
           					title : '性别',
           					width : $(this).width() * 0.1,
           					align : 'center'
           				},{
        					field : 'startTime',
        					title : '开始充电时间',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'chargingAmount',
        					title : '充电量(kw/h)',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'memberLevel',
        					title : '会员等级',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'integral',
        					title : '会员贡献值',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'chargingTime',
        					title : '充电时长(分钟)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val) {
        						n = val.toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'orderMoney',
        					title : '订单金额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(isNaN(val) ||val == 0){
        							return "0.00";
        						}else{
        							n = parseFloat(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        				},{
        					field : 'payMoney',
        					title : '支付金额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(isNaN(val) || val == 0){
        							return "0.00";
        						}else{
        							n = parseFloat(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        					
        				},{
        					field : 'couponValue',
        					title : '优惠券(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(isNaN(val) || val == 0){
        							return "0.00";
        						}else{
        							n = parseFloat(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        				}
                       ]],
				pagination : true,
				rownumbers : true
			});
	

    function compute() {//计算函数
        var rows = $('#dataGrid').datagrid('getRows')//获取当前的数据行
        var chargingAmountTotal = 0
        ,chargingTimeTotal=0
        ,payMoneyTotal=0
        ,orderMoneyTotal=0
        ,couponValueTotal=0
        
        
        for (var i = 0; i < rows.length; i++) {
        	chargingAmountTotal += parseFloat(rows[i]['chargingAmount']);
        	chargingTimeTotal += parseFloat(rows[i]['chargingTime']);
        	payMoneyTotal += parseFloat(rows[i]['payMoney']);
        	orderMoneyTotal +=parseFloat(rows[i]['orderMoney']);
        	couponValueTotal +=parseFloat(rows[i]['couponValue']);
        	
        	

        }
        
      /*  debugger ;*/
        
        
        var chargingAmountTotalVal = Number(chargingAmountTotal)   ;
        if(!isNaN(parseFloat(chargingAmountTotalVal))) {    
        	chargingAmountTotalVal = chargingAmountTotalVal;    
        }
        
        var chargingTimeTotalVal = Number(chargingTimeTotal)   ;
        if(!isNaN(parseFloat(chargingTimeTotalVal))) {    
        	chargingTimeTotalVal = chargingTimeTotalVal;    
        }
       
        var payMoneyTotalVal = Number(payMoneyTotal);
        if(!isNaN(parseFloat(payMoneyTotalVal))) {    
        	payMoneyTotalVal = payMoneyTotalVal.toFixed(2);    
        }        
        
        var orderMoneyTotalVal = Number(orderMoneyTotal);
        if(!isNaN(parseFloat(orderMoneyTotalVal))) {    
        	orderMoneyTotalVal = orderMoneyTotalVal.toFixed(2);    
        }
        
        var couponValueTotalVal = Number(couponValueTotal);
        if(!isNaN(parseFloat(couponValueTotalVal))) {    
        	couponValueTotalVal = couponValueTotalVal.toFixed(2);    
        }
        
        //新增一行显示统计信息
        $('#dataGrid').datagrid('appendRow', { name: '合计：',chargingAmount:chargingAmountTotalVal,chargingTime:chargingTimeTotalVal,payMoney:payMoneyTotalVal,orderMoney:orderMoneyTotalVal,couponValue:couponValueTotalVal});
        var foot = $('#dataGrid').datagrid('getFooterRows');
        console.log(foot)
        if (foot) {
        	$("#dataGrid").datagrid('appendRow', {name: '总合计：', chargingAmount: foot.chargingAmountSum,chargingTime: foot.chargingTimeSum,payMoney: foot.payMoneySum.toFixed(2),orderMoney: foot.orderMoneySum.toFixed(2),couponValue: foot.couponMoneySum.toFixed(2)});
        }else {
        	$("#dataGrid").datagrid('appendRow', {name: '总合计：', chargingAmount: 0.00,chargingTime: 0.00,payMoney: '0.00',orderMoney: '0.00',couponValue: '0.00'});
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

		if($('#name').length > 0) {
			$('#name').textbox('setValue', '');
		}

		$('#beginTime').datebox('setValue', '');
		$('#endTime').datebox('setValue', '');
	
	});
	
 
	// 查询
	function search() {	
		if($("#data").form("validate")){
			$('#dataGrid').datagrid('load', {
				'name' : $('#name').textbox('getValue'),
				'phoneNumber' : $('#phoneNumber').textbox('getValue'),
				'beginTime' : $('#beginTime').textbox('getValue'),
				'endTime' : $('#endTime').textbox('getValue')
			});	
		}
	}

	// 查询导出excel链接
	$("#btnExport").bind("click",function(){
		var name= $.trim($("input[name='name']").val());
		var phoneNumber= $.trim($("input[name='phoneNumber']").val());
	    var beginTime = $.trim($("input[name='beginTime']").val());
	    var endTime = $.trim($("input[name='endTime']").val());
	    $("#data").form("submit", {
					url : "export_member_ChargingCapital_excel?name="+name+"&beginTime="+beginTime+"&endTime="+endTime+"&phoneNumber="+phoneNumber,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });	
	});
