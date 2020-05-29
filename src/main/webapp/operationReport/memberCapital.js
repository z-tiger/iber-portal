$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '会员资金报表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				onLoadSuccess: compute,//加载完毕后执行计算
				url : 'member_capital',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'code': $('#code').textbox('getValue'),
					'phone': $('#phone').textbox('getValue'),
					'name': $('#name').textbox('getValue'),
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue')
				},
                columns: [[/*{
					       field : 'memberId',
					       title : '会员ID',
					       width : '0',
					       hidden: 'true'
  				       },*/
                          {
        					field : 'cityName',
        					title : '区域名称',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'createTime',
        					title : '用车时间',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
           					field : 'phone',
           					title : '手机号码',
           					width : $(this).width() * 0.1,
           					align : 'center'
           				},{
        					field : 'name',
        					title : '姓名',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'deposit',
        					title : '会员押金(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0)
        							return "0.00";
        					    else
        					       n = parseInt(val).toFixed(2);
        						   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			   return n.replace(re, "$1,");	
        					}
        				},{
        					field : 'money',
        					title : '会员余额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0)
        							return "0.00";
        					    else
        					    	n = parseInt(val).toFixed(2);
        						   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			   return n.replace(re, "$1,");	
        					}
        				},{
        					field : 'totalChargeMoney',
        					title : '总充值金额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0){
        							   return "0.00";
        							 }
        					    else {
        					         n = parseFloat(val).toFixed(2);
        						     var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        						     if(rec.cityName.indexOf("合计")!=-1){
        						    	 return n.replace(re, "$1,");
        						     }
        						     else{
        		       			        return n.replace(re, "$1,")+"<a href=\"javascript:showUpgradeRecord('"+rec.memberId+"')\">"+"【明细】"+"</a>";	
        						     }
        		       			   }
        					}
        				},{
        					field : 'totalConsumeMoney',
        					title : '总消费金额-支付(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0)
        							return "0.00";
        					    else
        					    	n = parseInt(val).toFixed(2);
        						   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			   return n.replace(re, "$1,");	
        					}
        				},{
        					field : 'totalCouponMoney',
        					title : '总消费金额-优惠券(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0)
        							return "0.00";
        					    else
        					    	n = parseInt(val).toFixed(2);
        						   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			   return n.replace(re, "$1,");	
        					}
        				},{
        					field : 'totalRefundMoney',
        					title : '总退款金额(元)',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0)
        							return "0.00";
        					    else
        					    	n = parseInt(val).toFixed(2);
        						   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        		       			   return n.replace(re, "$1,");	
        					}
        				},{
        					field : 'levelCode',
        					title : '会员等级',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter:function(val,rec){
        						if(val=="0"){
        							return "黑名单";
        						}else if(val=="1"){
        							return "一星会员";
        						}else if(val=="2"){
        							return "二星会员";
        						}else if(val=="3"){
        							return "三星会员";
        						}else if(val=="4"){
        							return "四星会员";
        						}else if(val=="5"){
        							return "五星会员";
        						}
        					}
        				},{
        					field : 'contributedVal',
        					title : '会员贡献值',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				}
                       ]],
				pagination : true,
				rownumbers : true
			});
	

    function compute() {//计算函数
        var rows = $('#dataGrid').datagrid('getRows')//获取当前的数据行
        var moneyTotal = 0//总和
        ,depositTotal=0//押金的总和
        ,totalChargeMoneyTotal=0 // 充值金额总和
        ,totalConsumeMoneyTotal=0//消费金额总和
        ,totalRefundMoneyTotal=0 //退款金额总和
        ,totalCouponMoneyTotal=0//优惠金额总和（目前只包含时租的）
        
        for (var i = 0; i < rows.length; i++) {
        	depositTotal += parseFloat(rows[i]['deposit']);
        	moneyTotal += parseFloat(rows[i]['money']);
        	totalChargeMoneyTotal += parseFloat(rows[i]['totalChargeMoney']);
        	totalConsumeMoneyTotal += parseFloat(rows[i]['totalConsumeMoney']);
        	totalRefundMoneyTotal += parseFloat(rows[i]['totalRefundMoney']);
        	totalCouponMoneyTotal += parseFloat(rows[i]['totalCouponMoney']);
        }
        
       // debugger ;
        
        var depositTotalVal = Number(depositTotal) ;  
        if(!isNaN(parseFloat(depositTotalVal))) {    
        	depositTotalVal = depositTotalVal.toFixed(2);    
        } 
        
        var moneyTotalVal = Number(moneyTotal)   ;
        if(!isNaN(parseFloat(moneyTotalVal))) {    
        	moneyTotalVal = moneyTotalVal.toFixed(2);    
        }
       
        var totalChargeMoneyTotalVal = Number(totalChargeMoneyTotal);
        if(!isNaN(parseFloat(totalChargeMoneyTotalVal))) {    
        	totalChargeMoneyTotalVal = totalChargeMoneyTotalVal.toFixed(2);    
        }        
        
        
        var totalConsumeMoneyTotalVal = Number(totalConsumeMoneyTotal)   ;
        if(!isNaN(parseFloat(totalConsumeMoneyTotalVal))) {    
        	totalConsumeMoneyTotalVal = totalConsumeMoneyTotalVal.toFixed(2);    
        }
        
        var totalRefundMoneyTotalVal = Number(totalRefundMoneyTotal)   ;
        if(!isNaN(parseFloat(totalRefundMoneyTotalVal))) {    
        	totalRefundMoneyTotalVal = totalRefundMoneyTotalVal.toFixed(2);    
        }
        
        var totalCouponMoneyTotalVal = Number(totalCouponMoneyTotal)   ;
        if(!isNaN(parseFloat(totalCouponMoneyTotalVal))) {    
        	totalCouponMoneyTotalVal = totalCouponMoneyTotalVal.toFixed(2);    
        }
        
        //新增一行显示统计信息
        $('#dataGrid').datagrid('appendRow', { cityName: '合计：', totalCouponMoney:totalCouponMoneyTotalVal,deposit: depositTotalVal,totalChargeMoney: totalChargeMoneyTotalVal,totalConsumeMoney: totalConsumeMoneyTotalVal,totalRefundMoney: totalRefundMoneyTotalVal,money: moneyTotalVal });
        
        var foot = $('#dataGrid').datagrid('getFooterRows');
        if (foot) {
        	$("#dataGrid").datagrid('appendRow', {cityName: '总合计：', totalCouponMoney: foot.totalCouponMoneySum.toFixed(2),deposit: foot.depositSum.toFixed(2),totalChargeMoney: foot.totalChargeMoneySum.toFixed(2),totalConsumeMoney: foot.totalConsumeMoneySum.toFixed(2),totalRefundMoney: foot.totalRefundMoneySum.toFixed(2),money: foot.moneySum.toFixed(2)});
        }else {
        	$("#dataGrid").datagrid('appendRow', {cityName: '总合计：', totalCouponMoney: '0.00',deposit: '0.00',totalChargeMoney: '0.00',totalConsumeMoney: '0.00',totalRefundMoney: '0.00',money: '0.00'});
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
		$("#data").form("clear");
//		if($('#code').length > 0) {
//			$('#code').textbox('setValue', '');
//		}
//		if($('#phone').length > 0) {
//			$('#phone').textbox('setValue', '');
//		}
//		if($('#name').length > 0) {
//			$('#name').textbox('setValue', '');
//		}
//
//		$('#beginTime').datebox('setValue', '');
//		$('#endTime').datebox('setValue', '');
	
	});
	
 
	// 查询
	function search() {	
		if($("#data").form("validate")){
			$('#dataGrid').datagrid('load', {
				'code' : $('#code').textbox('getValue'),
				'phone' : $('#phone').textbox('getValue'),
				'name' : $('#name').textbox('getValue'),
				'beginTime' : $('#beginTime').textbox('getValue'),
				'endTime' : $('#endTime').textbox('getValue')
			});	
		}
	}

	// 查询导出excel链接
	$("#btnExport").bind("click",function(){
		var code= $('#code').textbox('getValue');
		var phone= $('#phone').textbox('getValue');
		var name= $('#name').textbox('getValue');
		var beginTime= $('#beginTime').textbox('getValue');
		var endTime= $('#endTime').textbox('getValue');
		$("#code1").val(code);
		$("#phone1").val(phone);
		$("#name1").val(name);
		$("#beginTime1").val(beginTime);
		$("#endTime1").val(endTime);
		$("#exportForm").form("submit");
//	    $("#data").form("submit", {
//	    	         //url :"export_member_capital_excel",
//	    	url : "export_member_capital_excel?code="+code+"&phone="+phone+"&name="+name+"&beginTime="+beginTime+"&endTime="+endTime,
//					onSubmit : function() {
//						return $("#data").form("validate");
//					},
//					success : function(result) {
//					}
//				});
	    });	
	
});

function showUpgradeRecord(memberId){
	$("#showRechargeDetailView").dialog("open").dialog("setTitle", "会员充值明细");
	var url='member_recharge_detail?memberId='+memberId;
	$('#showMemberRechargeGrid').datagrid( {
		width : 'auto',
		height : 'auto',
		fit:true,
		fitColumns: true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		url : url,
		pageSize:20,
		columns : [ [ {
			field : 'rechargeId',
			title : '订单号',
			width : $(this).width() * 0.45,
			align : 'center',
		}, {
			field : 'rechargeCategory',
			title : '充值类型',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 'D')
			       return "押金";
			    else
			       return "余额";
			}
		}, {
			field : 'rechargeMoney',
			title : '充值金额(元)',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
                 return parseFloat(val,2)/100;
			}
		}, {
			field : 'tradeTime',
			title : '充值时间',
			width : $(this).width() * 0.3,
			align : 'center'
		}, {
			field : 'bankCategory',
			title : '充值渠道',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "A")
			       return "支付宝";
			    else if(val == "WX")
			       return "微信";
				else 
				   return "银行";	
			}
		},{
			field : 'tradeStatus',
			title : '充值状态',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 1)
			       return "充值失败！";
			    else
			       return "充值成功！";
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
} 


