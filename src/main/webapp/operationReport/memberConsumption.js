$(function() {

	$('#dataGrid')
	.datagrid(
			{
				title : '会员分时消费明细',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				onLoadSuccess: compute,//加载完毕后执行计算
				url : 'member_consumption',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
                    'cityName':$('#cityName').combobox('getValue'),
                    'brandName': $('#brandName').combobox('getValue'),
					'name': $('#name').textbox('getValue'),
					'phoneNumber': $('#phoneNumber').textbox('getValue'),
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue')
				},
                columns: [[
							{
							field : 'cityName',
							title : '所属城市',
							width : $(this).width() * 0.1,
							align : 'center'
						},
						{
							field : 'lpn',
							title : '车辆号码',
							width : $(this).width() * 0.1,
							align : 'center'
						},
						{
							field : 'brandName',
							title : '车辆品牌',
							width : $(this).width() * 0.1,
							align : 'center'
						},
                		{
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
        					field : 'idcard',
        					title : '出生年月',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'useDate',
        					title : '当次使用日期',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDay(val);
        					}
        				},{
        					field : 'beginTime',
        					title : '取车时间',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'endTime',
        					title : '还车时间',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'payType',
        					title : '支付方式',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter:function(val,rec){
        					 	if(val=="B"){
        					 		return "余额支付";
        					 	}else if(val=="A"){
        					 		return "支付宝支付";
        					 	}else if(val=="T"){
        					 		return "财付通支付";
        					 	}else if(val=="WX"){
        					 		return "微信支付";
        					 	}else if(val=="U"){
        					 		return "银联支付";
        					 	}else if(val=="AP"){
        					 		return "apple pay支付";
        					 	}else if(val=="EB"){
                                    return "企业余额支付";
                                }
        					}
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
        					field : 'totalMinute',
        					title : '使用时长(分钟)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val) {
        						n = val.toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'totalMileage',
        					title : '使用里程(公里)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val) {
        						n = parseFloat(val).toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'payMoney',
        					title : '支付金额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val){
        						n = parseFloat(val).toFixed(2);
        						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			return n.replace(re, "$1,");	
        					}
        					 
        				},{
        					field : 'totalPayMoney',
        					title : '订单金额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val){
        						n = parseFloat(val).toFixed(2);
        						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			return n.replace(re, "$1,");	
            				}
        				},{
        					field : 'couponBalance',
        					title : '优惠券(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val){
        						n = parseFloat(val).toFixed(2);
        						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			return n.replace(re, "$1,");	
        					}
        					
        				},{
        					field : 'freeCompensateMoney',
        					title : '商业保险(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val){
        						n = parseFloat(val).toFixed(2);
        						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			return n.replace(re, "$1,");	
        					}
        				}
                       ]],
				pagination : true,
				rownumbers : true
			});
	

    function compute() {//计算函数
        var rows = $('#dataGrid').datagrid('getRows')//获取当前的数据行
        var totalMinuteTotal = 0
        ,totalMileageTotal=0
        ,payMoneyTotal=0
        ,totalPayMoneyTotal=0
        ,couponBalanceTotal=0
        ,freeCompensateMoneyTotal=0
        
        for (var i = 0; i < rows.length; i++) {
        	totalMinuteTotal += parseFloat(rows[i]['totalMinute']);
        	totalMileageTotal += parseFloat(rows[i]['totalMileage']);
        	payMoneyTotal += parseFloat(rows[i]['payMoney']);
        	totalPayMoneyTotal +=parseFloat(rows[i]['totalPayMoney']);
        	couponBalanceTotal +=parseFloat(rows[i]['couponBalance']);
        	freeCompensateMoneyTotal +=parseFloat(rows[i]['freeCompensateMoney']);
        	

        }
        
      /*  debugger ;*/
        
        
        var totalMileageTotalVal = Number(totalMileageTotal)   ;
        if(!isNaN(parseFloat(totalMileageTotalVal))) {    
        	totalMileageTotalVal = totalMileageTotalVal.toFixed(2);    
        }
       
        var payMoneyTotalVal = Number(payMoneyTotal);
        if(!isNaN(parseFloat(payMoneyTotalVal))) {    
        	payMoneyTotalVal = payMoneyTotalVal.toFixed(2);    
        }        
        
        var totalPayMoneyTotalVal = Number(totalPayMoneyTotal);
        if(!isNaN(parseFloat(totalPayMoneyTotalVal))) {    
        	totalPayMoneyTotalVal = totalPayMoneyTotalVal.toFixed(2);    
        }
        
        var couponBalanceTotalVal = Number(couponBalanceTotal);
        if(!isNaN(parseFloat(couponBalanceTotalVal))) {    
        	couponBalanceTotalVal = couponBalanceTotalVal.toFixed(2);    
        }
        
        var freeCompensateMoneyTotalVal = Number(freeCompensateMoneyTotal);
        if(!isNaN(parseFloat(freeCompensateMoneyTotalVal))) {    
        	freeCompensateMoneyTotalVal = freeCompensateMoneyTotalVal;    
        }
        
        //新增一行显示统计信息
        $('#dataGrid').datagrid('appendRow', { name: '合计：',freeCompensateMoney:freeCompensateMoneyTotalVal,couponBalance:couponBalanceTotalVal,totalPayMoney:totalPayMoneyTotalVal, totalMinute: totalMinuteTotal,totalMileage: totalMileageTotalVal,payMoney: payMoneyTotalVal});
        var foot = $('#dataGrid').datagrid('getFooterRows');
        console.log(foot)
        if (foot) {
        	$("#dataGrid").datagrid('appendRow', {name: '总合计：', freeCompensateMoney: foot.freeCompensateMoneySum,couponBalance: foot.couponBalanceSum.toFixed(2),totalPayMoney: foot.totalPayMoneySum.toFixed(2),totalMinute: foot.totalMinuteSum,totalMileage: foot.totalMileageSum.toFixed(2),payMoney: foot.payMoneySum.toFixed(2)});
        }else {
        	$("#dataGrid").datagrid('appendRow', {name: '总合计：', freeCompensateMoney: 0.00,couponBalance: '0.00',totalPayMoney: '0.00',totalMinute: 0.00,totalMileage: '0.00',payMoney: '0.00'});
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
			    'cityName':$('#cityName').combobox('getValue'),
                'brandName': $('#brandName').combobox('getValue'),
				'name' : $('#name').textbox('getValue'),
                'phoneNumber':$('#phoneNumber').textbox('getValue'),
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
        var cityName = $.trim($('#cityName').combobox('getValue'));
        var brandName = $.trim($('#brandName').combobox('getValue'));
	    $("#data").form("submit", {
					url : "export_member_consumption_excel?name="+name+"&beginTime="
                    +beginTime+"&endTime="+endTime+"&cityName="+cityName+"&brandName="+brandName+"&phoneNumber="+phoneNumber,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });



	});
