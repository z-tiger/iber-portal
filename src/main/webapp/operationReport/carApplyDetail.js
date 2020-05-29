$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '车辆时租明细表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				onLoadSuccess: compute,//加载完毕后执行计算
				url : 'car_apply_detail',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'lpn': $('#lpn').textbox('getValue'),
					'name': $('#name').textbox('getValue'),
					'phone': $('#phone').textbox('getValue'), 
					'idCard': $('#idCard').textbox('getValue'),
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue'),
					'carType':$('#carType').combobox('getValue')
				},
                columns: [[{
        					field : 'lpn',
        					title : '车牌号',
        					width : $(this).width() * 0.015,
        					align : 'center'
        				},{
        					field : 'carType',
        					title : '车型',
        					width : $(this).width() * 0.015,
        					align : 'center'
        				},{
        					field : 'useDate',
        					title : '使用日期',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDay(val);
        					}
        				},{
        					field : 'beginTime',
        					title : '开始时间',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'endTime',
        					title : '结束时间',
        					width : $(this).width() * 0.02,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
           					field : 'name',
           					title : '使用者',
           					width : $(this).width() * 0.02,
           					align : 'center'
           				},{
        					field : 'idCard',
        					title : '使用者身份证',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				},{
        					field : 'phone',
        					title : '手机',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				},{
        					field : 'totalMinute',
        					title : '使用时长(分)',
        					width : $(this).width() * 0.015,
        					align : 'center',
        					formatter : function(val) {
        						n = val.toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'totalMileage',
        					title : '使用里程(公里)',
        					width : $(this).width() * 0.015,
        					align : 'center',
        					formatter : function(val) {
        						n = parseInt(val).toFixed(0);
        						var re = /(\d{1,3})(?=(\d{3}))/g;
        						return n.replace(re, "$1,");
        					}
        				},{
        					field : 'parkName',
        					title : '取车地点',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				},{
        					field : 'returnParkName',
        					title : '还车地点',
        					width : $(this).width() * 0.02,
        					align : 'center'
        				},{
        					field : 'totalPayMoney',
        					title : '订单金额(元)',
        					width : $(this).width() * 0.015,
        					align : 'center',
        					formatter : function(val) {
        						n = val.toFixed(2);
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
        ,totalPayMoneyTotal=0
        
       
        for (var i = 0; i < rows.length; i++) {
        	totalMinuteTotal += parseInt(rows[i]['totalMinute']);
        	totalMileageTotal += parseFloat(rows[i]['totalMileage']);
        	totalPayMoneyTotal += parseFloat(rows[i]['totalPayMoney']);
        }
        
        var totalMileageTotalVal = Number(totalMileageTotal) ;  
        if(!isNaN(parseFloat(totalMileageTotalVal))) {    
        	totalMileageTotalVal = totalMileageTotalVal.toFixed(2);    
        } 
        
        var totalPayMoneyTotalVal = Number(totalPayMoneyTotal)   ;
        if(!isNaN(parseFloat(totalPayMoneyTotalVal))) {    
        	totalPayMoneyTotalVal = totalPayMoneyTotalVal;    
        }
        
        
		//新增一行显示统计信息
        $('#dataGrid').datagrid('appendRow', {lpn: '合计：', totalMinute: totalMinuteTotal,totalMileage: totalMileageTotalVal,totalPayMoney: totalPayMoneyTotalVal});
        
        var foot = $('#dataGrid').datagrid('getFooterRows');
        $("#dataGrid").datagrid('appendRow', {lpn: '总合计：', totalMinute: foot.totalMinute,totalMileage: foot.totalMileage.toFixed(2),totalPayMoney: foot.totalPayMoney});
    }
	
    /*function all(){
    	var totalMinuteSum = 0,
        totalMileageSum = 0,
        totalPayMoneySum = 0
    	//获得行数总和
        
        var totalPage = Math.ceil(options.total/options.pageSize);
        for(var i = 0; i < totalPage; i++) {
        	
        }
        //新增一行显示每一页的数据的总和
        
    }*/
	
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
//		if($('#lpn').length > 0) {
//			$('#lpn').textbox('setValue', '');
//		}
//		if($('#name').length > 0) {
//			$('#name').textbox('setValue', '');
//		}
//		if($('#phone').length > 0) {
//			$('#phone').textbox('setValue', '');
//		}
//		
//		if($('#idCard').length > 0) {
//			$('#idCard').textbox('setValue', '');
//		}
//		
//		$('#beginTime').datebox('setValue', '');
//		$('#endTime').datebox('setValue', '');
	
	});
	
 
	// 查询
	function search() {	
		if($("#data").form("validate")){
			$('#dataGrid').datagrid('load', {
				'lpn' : $('#lpn').textbox('getValue'),
				'name' : $('#name').textbox('getValue'),
				'phone' : $('#phone').textbox('getValue'),
				'idCard' : $('#idCard').textbox('getValue'),
				'beginTime' : $('#beginTime').textbox('getValue'),
				'endTime' : $('#endTime').textbox('getValue'),
				'carType':$('#carType').combobox('getValue')
			});	
		}
	}

	// 车辆查询查询导出excel链接
	$("#btnExport").bind("click",function(){
	    $("#data").form("submit", {
	    	         url :"export_car_apply_detail_excel",
					onSubmit : function() {
						return $("#data").form("validate");
					},
					success : function(result) {
					}
				});
	    });	
	});
