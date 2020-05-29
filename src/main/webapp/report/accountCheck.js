$(function() {


	//隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();

	//根据不同周期（年、月、季度、自选）来显示相关label
	$('#period').combobox({
		onSelect: function(param){
			onSelPeriod();
		}
	});
	//清空
    $('#clearBtn').bind('click',function () {
        $('#accountCheckForm').form('clear');

    })
	
	$('#dataGrid').datagrid(
			{
				title : '会员消费对账单',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
//				onLoadSuccess: computeTodayAndThisMonthAndYear,//compute,//加载完毕后执行计算
// 				onLoadSuccess: computeYearTotal,//compute,//加载完毕后执行计算
				url : '',
				pageSize : 50,
				pageList : [100,50,30,10],
				idField : 'endTime',
                columns: [[
                            {
           					field : 'cityName',
           					title : '所属城市',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				},{
           					field : 'lpn',
           					title : '车牌号码',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				    
           				},{
           					field : 'brandName',
           					title : '车辆品牌',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				    
           				},{
           					field : 'endTime',
           					title : '交易日期',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				}, {
           					field : 'memberId',
           					title : '会员ID',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				}, {
           					field : 'name',
           					title : '会员姓名',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				}, {
           					field : 'phone',
           					title : '手机号码',
           					width : $(this).width() * 0.1,
           					align : 'center'

           				    
           				},
                        {
                            field : 'payType',
                            title : '支付方式',
                            width : $(this).width() * 0.1,
                            align : 'center'

                        },
                        {
                            field : 'consumptionType',
                            title : '费用类型',
                            width : $(this).width() * 0.1,
                            align : 'center',
                            formatter:function (val) {
                                if(isEmpty(val))
                                    return ''
                                else
                                    return val

                            }
                        },
                        {
                            field : 'payMoney',
                            title : '支付金额',
                            width : $(this).width() * 0.1,
                            align : 'center'

                        },
                        {
                            field : 'deposit',
                            title : '押金',
                            width : $(this).width() * 0.1,
                            align : 'center',
                            formatter : function(val, rec) {
                                if(!isNaN(val)){
                                    n = (val/100.0).toFixed(2);
                                    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                                    return n.replace(re, "$1,");
                                }
                            }

                        },{
                            field : 'balance',
        					title : '余额',
        					width : $(this).width() * 0.1,
        					align : 'center',
           					formatter : function(val, rec) {
           						if(!isNaN(val)){
           							n = (val/100.0).toFixed(2);
           						    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
           		       			    return n.replace(re, "$1,");	        							
           						}
           					}
        				}
                       ] ],
				pagination : true,
				rownumbers : true
			});
	

    /*
	 * 统计本日、本月、本年的合计
	 */


	function computeYearTotal() {
        // 总合计
        getAccountTotal();
        // 年度数据
        getAccountYearTotal();
    }


    // 计算年度
    function getAccountYearTotal() {
    	$.ajax({
  		  type: 'POST',
  		  url: 'getAccountYearTotal',
  		  dataType: 'JSON',
  		  async: 'false',
  		  data: {
	    		'name' : $('#name').textbox('getValue')
  			},success: function(data){
	  				console.log(data);
					getTotalLine(data, "本年合计:");
  			}
  		});
	}
    
    
    // 计算合计数据
    function getAccountTotal(){
    	// 使用同步方法
    	$.ajax({
    		  type: 'POST',
    		  url: 'getAccountTotal',
    		  dataType: 'JSON',
    		  async: 'false',
    		  data: {
					'begin' : $("#hidBegin").val(),
					'end' : $("#hidEnd").val(),
					'name' : $('#name').textbox('getValue')
    			},success: function(data){
	  				console.log(data);
//					var json = $.parseJSON(data);
					getTotalLine(data, "总合计:");
    			}
    		});
    }

    function getAmount(amount, title){
//    	debugger;
    	if(null != amount){



		} else{

		}
    }
    
    function getTotalLine(amount, title){
//    	debugger;
    	if(null != amount){

    		var balance = amount.balanceTotal;
    		var deposit = amount.depositTotal;

    		deposit = Number(deposit);
    		if(!isNaN(deposit)){
    			deposit = deposit.toFixed(2);
    		}else{
    			deposit = 0;
    		}
    		//新增一行显示统计信息
    		$('#dataGrid').datagrid('appendRow', { endTime: title,
    			deposit: deposit, balance: balance});
    	} else{
    		$('#dataGrid').datagrid('appendRow', { endTime: title,
    			deposit: 0.0, balance: 0.0});
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
		$('#name').textbox('setValue', '');
		$('#s-brandName').textbox('setValue','');
	});
	  
	// 查询
	function search() {	
		setBeginAndEnd2();
		var options = $('#dataGrid').datagrid('options');
		options.url = 'account_report.do';
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		var cityName = $('#s-cityCode').combobox('getText') ;
		var brandName = $('#s-brandName').textbox('getText');
        var comsuptionType = $('#consumptionType').combobox('getValue');
        var payType = $('#payType').combobox('getValue');
	/*	if(!begin || !end){
			$.messager.alert('提示','请输入日期！');
			return;
		}*/
		$('#dataGrid').datagrid('load', {
			'begin' : begin,
			'end' : end,
			'name' : $('#name').textbox('getValue'),
			'cityName':cityName,
			'brandName':brandName,
            'comsuptionType':comsuptionType,
            'payType':payType

           /* 'payBeginTime':$('#hidPayBeginTime').val(),
            'payEntTime':$('#hidPayEndTime').val()*/
		});	
	}

	//专车收入查询导出excel链接
	$("#btnExport").bind("click", function(){
		setBeginAndEnd2();
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		var name = $('#name').textbox('getValue');
		var cityCode = $('#s-cityCode').combobox('getValue') ;
		var brandName = $('#s-brandName').textbox('getValue');

        var comsuptionType = $('#consumptionType').combobox('getValue');
        var payType = $('#payType').combobox('getValue');

	    $("#accountCheckForm").form("submit", {
					url : "export_account_report_excel",
					onSubmit : function(param) {
						param.name = encodeURIComponent(name);
						param.begin = begin;
						param.end = end;
						param.cityCode = cityCode;
						param.brandName = brandName;
						param.comsuptionType= comsuptionType;
						param.payType= payType;

					},
					success : function(result) {
					}
				});
	    });	
	
});
