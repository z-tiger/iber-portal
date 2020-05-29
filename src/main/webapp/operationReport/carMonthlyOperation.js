$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '车辆运营月报表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				onLoadSuccess: function(data){
					compute();
					console.log(data);
					for (var i = 0; i < data.length; i++) {
						
					}
				},//加载完毕后执行计算
				url : 'car_monthly_operation',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					/*'idcard': $('#idcard').textbox('getValue'),*/
                   // 'queryMonth': $('#queryMonth').textbox('getValue')
					'yearValue':$("#yearValue").val(),
					'monthValue':$("#monthValue").val()
					
				},
                columns: [[/*{
        					field : 'name',
        					title : '会员姓名',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				},{
        					field : 'idcard',
        					title : '会员身份证',
        					width : $(this).width() * 0.03,
        					align : 'center'
        				},*/{
        					field : 'lpn',
        					title : '车牌号',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				},{
        					field : 'carType',
        					title : '车辆型号',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				}/*,{
        					field : 'rentType',
        					title : '租赁类型',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter:function(val,rec){
        						if(val=="enterpriseOrder"){
        							return "企业"
        						}else if(val=="personOrder"){
        							return "个人"
        						}
        					}
        				}*/,{
        					field : 'rentTime',
        					title : '租赁次数',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				}/*,{
        					field : 'carUseRate',
        					title : '车辆使用率',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter:function(val,row){
        						if(row.lpn!='合计：' && row.lpn != '总合计：'){
        							return ((row.rentTime/row.rentTotal)*100).toFixed(2)+"%";	
        						}
        						
        					}
        				}*//*,{
        					field : 'beginTime',
        					title : '开始时间',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'endTime',
        					title : '还车时间',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				}*/,{
        					field : 'totalMileage',
        					title : '行驶里程（公里）',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val) {
        						n = parseFloat(val).toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'totalMinute',
        					title : '行驶时间（分）',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val) {
        						n = parseFloat(val).toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'payMoney',
        					title : '支付金额（元）',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0){
        							return "0.00";
        						}else{
        							n = parseFloat(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        				},{
        					field : 'sumCompensation',
        					title : '商业保险（元）',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0){
        							return "0.00";
        						}else{
        							n = parseFloat(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        				},{
        					field : 'totalPayMoney',
        					title : '订单金额(元)',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0){
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
        console.log(rows);
        var totalMinuteTotal = 0
        ,totalMileageTotal=0
        ,totalPayMoneyTotal=0 
        ,payMoneyTotal=0
        ,sumCompensationTotal=0
        ,rentTimeTotal=0
        
        

        
        for (var i = 0; i < rows.length; i++) {
        	totalMinuteTotal += parseInt(rows[i]['totalMinute']);
        	totalMileageTotal += parseFloat(rows[i]['totalMileage']);
        	totalPayMoneyTotal += parseFloat(rows[i]['totalPayMoney']);
        	payMoneyTotal +=parseFloat(rows[i]['payMoney']);
        	sumCompensationTotal+=parseFloat(rows[i]['sumCompensation']);
        	rentTimeTotal+=parseInt(rows[i]['rentTime']);
        	
        }
        
       // debugger ;
        
        var totalMileageTotalVal = Number(totalMileageTotal) ;  
        if(!isNaN(parseFloat(totalMileageTotalVal))) {    
        	totalMileageTotalVal = totalMileageTotalVal;    
        } 
        
        var totalPayMoneyTotalVal = Number(totalPayMoneyTotal)   ;
        if(!isNaN(parseFloat(totalPayMoneyTotalVal))) {    
        	totalPayMoneyTotalVal = totalPayMoneyTotalVal.toFixed(2);    
        }
        
        var payMoneyTotalVal = Number(payMoneyTotal)   ;
        if(!isNaN(parseFloat(payMoneyTotalVal))) {    
        	payMoneyTotalVal = payMoneyTotalVal.toFixed(2);    
        }
        
        var sumCompensationTotalVal = Number(sumCompensationTotal)   ;
        if(!isNaN(parseFloat(sumCompensationTotalVal))) {    
        	sumCompensationTotalVal = sumCompensationTotalVal.toFixed(2);    
        }
        
        //新增一行显示统计信息
        $('#dataGrid').datagrid('appendRow', { lpn: '合计：',rentTime:rentTimeTotal,sumCompensation:sumCompensationTotalVal,payMoney:payMoneyTotalVal, totalMinute: totalMinuteTotal,totalMileage: totalMileageTotalVal,totalPayMoney: totalPayMoneyTotalVal});
        
        var foot = $('#dataGrid').datagrid('getFooterRows');
        if (foot) {
        	$("#dataGrid").datagrid('appendRow', {lpn: '总合计：', rentTime: foot.rentTimeSum,sumCompensation: foot.freeCompensationMoneySum.toFixed(2),payMoney: foot.payMoneySum.toFixed(2),totalMinute: foot.totalMinuteSum,totalMileage: foot.totalMileageSum.toFixed(2),totalPayMoney: foot.totalPayMoneySum.toFixed(2)});
        }else {
        	$("#dataGrid").datagrid('appendRow', {lpn: '总合计：', rentTime: 0.00,sumCompensation: '0.00',payMoney: '0.00',totalMinute: 0.00,totalMileage: 0.00,totalPayMoney: '0.00'});
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
		/*if($('#idcard').length > 0) {
			$('#idcard').textbox('setValue', '');
		}*/
		var date = new Date();
		var _y = date.getFullYear();
		var _m = date.getMonth() + 1;
		 $("#yearValue option[value='"+_y+"']").attr("selected", "selected");
		 $("#monthValue option[value='"+_m+"']").attr("selected", "selected");
//
//		$('#queryMonth').textbox('setValue', '');
		 $('#lpn').textbox('setValue', '');
		 $('#carType').combobox('setValue', '');
		/* $('#rentType').combobox('setValue', '');*/
	});
	
 
	// 查询
	function search() {	
    	  $('#dataGrid').datagrid('load', {
    		 /* 'idcard' : $('#idcard').textbox('getValue'),*/
    		  //'queryMonth' : $('#queryMonth').textbox('getValue')
    		  'yearValue':$("#yearValue").val(),
			  'monthValue':$("#monthValue").val(),
			  'lpn':$("#lpn").textbox('getValue'),
			  'carType':$("#carType").combobox('getValue'),
			  /*'rentType':$("#rentType").combobox('getValue'),*/
    	  });	
	}

	// 查询导出excel链接
	$("#btnExport").bind("click",function(){
//		var idcard= $.trim($("input[name='idcard]").val());
//	    var queryMonth = $.trim($("input[name='queryMonth']").val());
		/*var  yearValue= $("#yearValue").val();
	    var monthValue = $("#monthValue").val();
	    var lpn =$("#lpn").textbox('getValue');
		var carType =$("#carType").combobox('getValue');
		var rentType = $("#rentType").combobox('getValue');*/
	   /*var idcard = $('#idcard').textbox('getValue');*/
	    $("#data").form("submit", {
					url : "export_car_monthly_operation_excel",
	    	         // url : 'export_car_monthly_operation_excel',
					onSubmit : function() {
						return true;
					},
					success : function(result) {
					}
				});
	    });	
	});
