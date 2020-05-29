$(function() {
	$('#carIncomeGrid')
	.datagrid(
			{
				title : '车辆收入报表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				onLoadSuccess: compute,//加载完毕后执行计算
				url : 'car_income_report',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
                columns: [[
                          {
           					field : 'cityName',
           					title : '所属城市',
           					width : $(this).width() * 0.13,
           					align : 'center'
           				},{
        					field : 'lpn',
        					title : '车牌号',
        					width : $(this).width() * 0.13,
        					align : 'center'
        				},{
        					field : 'brandName',
        					title : '车辆品牌',
        					width : $(this).width() * 0.13,
        					align : 'center'
        				},{
        					field : 'type',
        					title : '车类型',
        					width : $(this).width() * 0.13,
        					align : 'center'
        				},{
        					field : 'color',
        					title : '车辆颜色',
        					width : $(this).width() * 0.13,
        					align : 'center'
        				}/*,{			 
           					field : 'timeShareMoney',
           					title : '时租收入（元）',
           					width : $(this).width() * 0.13,
           					align : 'center',
           					formatter : function(val, rec) {
           						return val ;
           					}
           				},{
        					field : 'totalMoney',
        					title : '收入合计（元）',
        					width : $(this).width() * 0.13,
        					align : 'center',
           					formatter : function(val, rec) {
           						return val ;
           					}
        				}*/
        				,{
        					field : 'timeShareMoney',
        					title : '时租收入',
        					width : $(this).width() * 0.13,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0){
        							return "0.00";
        						}else{
        							n = parseInt(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        				},{
        					field : 'dayRentMoney',
        					title : '日租收入',
        					width : $(this).width() * 0.13,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == 0){
        							return "0.00";
        						}else{
        							n = parseInt(val).toFixed(2);
        							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        			       			return n.replace(re, "$1,");	
        					    }
        						 
        					}
        				},{
        					field : 'longTimeRentMoney',
        					title : '长租收入',
        					width : $(this).width() * 0.13,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(!val){
        							return "0.00";
        						}else{
        							n = val.toFixed(2);
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
        var rows = $('#carIncomeGrid').datagrid('getRows')//获取当前的数据行
        var total = 0//总和
        ,timeShareTotal=0//统计租赁收入的总和
        ,dayRentMoneyTotal=0//统计日租收入的总和
        for (var i = 0; i < rows.length; i++) {
        	timeShareTotal += parseFloat(rows[i]['timeShareMoney']);
        	dayRentMoneyTotal+=parseFloat(rows[i]['dayRentMoney']);
        }
        
       /* debugger ;*/
        
        var timeShareTotalVal = Number(timeShareTotal) ;  
        if(!isNaN(parseFloat(timeShareTotalVal))) {    
        	timeShareTotalVal = timeShareTotalVal.toFixed(2);    
        } 
        console.log(timeShareTotalVal)
        var dayRentMoneyTotalVal = Number(dayRentMoneyTotal) ;
        if(!isNaN(parseFloat(dayRentMoneyTotalVal))){
        	dayRentMoneyTotalVal=dayRentMoneyTotalVal.toFixed(2);
        }
        console.log(dayRentMoneyTotalVal)
        //新增一行显示统计信息
        $('#carIncomeGrid').datagrid('appendRow', { cityName: '合计：', timeShareMoney: timeShareTotalVal,dayRentMoney:dayRentMoneyTotalVal});
        var foot = $('#carIncomeGrid').datagrid('getFooterRows');
        console.log(foot)
        if (foot[0]) {
        	$("#carIncomeGrid").datagrid('appendRow', {cityName: '总合计：', timeShareMoney: foot[0].timeShareMoneySum.toFixed(2),dayRentMoney: foot[0].dayRentMoneySum.toFixed(2)});	
        }else{
        	$("#carIncomeGrid").datagrid('appendRow', {cityName: '总合计：', timeShareMoney: '0.00',dayRentMoney: '0.00'});	
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
	});

	// 查询
	function search() {	
		$('#carIncomeGrid').datagrid('load', {
			'cityCode' : $('#cityCode').textbox('getValue'),
			'lpn' : $('#lpn').textbox('getValue'),
			'queryDateFrom' : $('#queryDateFrom').textbox('getValue'),
			'queryDateTo' : $('#queryDateTo').textbox('getValue')
		});	
	}

	// 专车收入查询导出excel链接
	$("#btnExport").bind("click",function(){
		var cityCode = $('#cityCode').textbox('getValue') ;
		var lpn = $('#lpn').textbox('getValue');
		var queryDateFrom = $('#queryDateFrom').textbox('getValue');
		var queryDateTo = $('#queryDateTo').textbox('getValue');
  
	    $("#queryCarIncome").form("submit", {
					url : "export_car_income_excel?cityCode="+cityCode+"&lpn="+lpn+"&queryDateFrom="+queryDateFrom+"&queryDateTo="+queryDateTo,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
			});
	    });	
	});
