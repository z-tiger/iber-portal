/**
 * 充电桩收入报表  【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）
 * 【充电电量】【价格】（两个小项：【物业电价】、【会员电价】）【计费金额（元）】【充电收入】
 */
$(function() {
	
	// 隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();

	//清空
    $('#clearBtn').on('click',function () {
        console.log("click");
        $('#toolbar').form('clear');
    })

	//dataGrid加载数据
	function loadData(){
		$('#incomeDetailGrid').datagrid('load',{
			'cityCode' : $("#hidCityCode").val(),
			'invoiceStatus' : $("#invoiceStatus").combobox("getValue"),
			'type' : $("#hidType").val(),
			'parkId' : $("#hidParkId").val(),
			'equipmentId' : $("#hidEquipmentId").val(),
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val(),
            'payBeginTime':$('#hidPayBeginTime').val(),
            'payEntTime':$('#hidPayEndTime').val()

		});
	}
	//导出excel
	$("#btnExport").bind("click", function() {
		var param=   $('#incomeDetailGrid').datagrid('options').queryParams;//查询参数
		var url = "export_chargeInCome_list?"+jQuery.param(param);
		window.location.href = url ;
	});
	// 根据不同周期（年、月、季度、自选）来显示相关label
	$('#period').combobox({
		onSelect : function(param) {
			onSelPeriod();
		}
	});

	//查询链接
	$(document).keydown(function(event){
		if(event.keyCode==13){
			setBeginAndEnd2();
			loadData();
		}
	});
	
	//充电桩收入报表
	$("#btnQueryIncome").bind("click",function(){
		setBeginAndEnd2();
		loadData();
	});
	
	//城市-充电站树形列表
	$("#cityParkTree").tree({
		url : 'city_list',
		animate:true,
		lines:true,
		loadFilter: function(rows){
			//把扁平数据转换为树
			return convert2Tree(rows);
		},
		//在节点折叠（展开）时展开（折叠）
		onSelect: function(node) {  
			onSelectCityParkTree(node);
			//加载数据
			loadData();
		}
		
	});
	
	//充电桩收入报表  【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【充电电量】【价格】（两个小项：【物业电价】、【会员电价】）【计费金额（元）】【充电收入】
	$("#incomeDetailGrid").datagrid( {
		title : '充电桩收入报表',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : false,
        nowrap : true,
        striped : true,
        collapsible : true,
        pagination : true,
        rownumbers : true,
        singleSelect : true,
		url : 'get_income_report.do',
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		columns : [ [{
			field : 'stationName',
			title : '网点名',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'equipmentCode',
			title : '充电桩编码',
			width : $(this).width() * 0.06,
			align : 'center'
		}, 
		{	//桩类型和枪数目：如 "快充-单枪"
			field : 'typeAndCnt',
			title : '桩类型',
			width : $(this).width() * 0.06,
			align : 'center'
		},
		{
			field : 'lpn',
			title : '充电车辆',
			width : $(this).width() * 0.06,
			align : 'center'
		}, 
		{
			field : 'carport',
			title : '车位号',
			width : $(this).width() * 0.06,
			align : 'center'
		}, 
		{
			field : 'startTime',
			title : '充电开始时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
                field : 'payTime',
                title : '支付时间',
                width : $(this).width() * 0.1,
                align : 'center'
            },
            {
			field : 'chargedQuantity',   
			title : '充电电量',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val){
				if(val == null ){ 
					return "0.00" ;
				}else{
					n = val.toFixed(2);
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
	       			return n.replace(re, "$1,");
				}
			}
		},
		{
			field : 'totalPrice',
			title : '价格',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		},
		{
			field : 'fee',
			title : '计费金额（元）',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		},{
			field : 'payMoney',
			title : '充电收入（元）',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if (rec.isFreeOrder == "Y") {
					return "0.00" ;
				}else{
					if ( val == null || val == 0) {
						return "0.00"; 
					}else{
						n = (val/100).toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
		       			return n.replace(re, "$1,");	
					}
				}
			}
		},{
			field : 'invoiceMoney',
			title : '开票金额（元）',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
				return n.replace(re, "$1,");	
			}
		},
		{
			field : 'isFreeOrder',
			title : '是否免单',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "Y"){
                   return "是";					
				}else{
                  return "否";					
				}
			}
		}, {
			field : 'freeReason',
			title : '免单原因',
			width : $(this).width() * 0.1,
			align : 'center',
		}
		
		] ],
		onLoadSuccess:function(data){
			$("#incomeDetailGrid").datagrid("appendRow", {
				stationName: '<span class="subtotal">合计</span>',
				chargedQuantity: computeSubtotal("#incomeDetailGrid", "chargedQuantity"),
				totalPrice: computeSubtotal("#incomeDetailGrid", "totalPrice"),
				fee: computeSubtotal("#incomeDetailGrid", "fee"),
				payMoney: computeSubtotal("#incomeDetailGrid", "payMoney") ,
				invoiceMoney: computeSubtotal("#incomeDetailGrid", "invoiceMoney")   
            });
			
			
			var foot = $('#incomeDetailGrid').datagrid('getFooterRows');
			if (foot[0] != null){
				$("#incomeDetailGrid").datagrid('appendRow', {
					stationName: '总合计：', 
					chargedQuantity: foot[0].chargingAmountSum,
					totalPrice: foot[0].totalPriceSum.toFixed(2),
					fee: foot[0].feeSum.toFixed(2), 
					payMoney: foot[0].incomeSum.toFixed(2),
					invoiceMoney:foot[0].invoiceMoneySum.toFixed(2)
				});
			}else{
				$("#incomeDetailGrid").datagrid('appendRow', {
					stationName: '总合计：',
					chargedQuantity: 0,
					totalPrice: 0.00,
					fee: 0.00, 
					payMoney: 0.00, 
					invoiceMoney:0.00
				});
			}
		}
		
	});
});