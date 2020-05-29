$(function() {

	//隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();
    // 根据不同周期（年、月、季度、自选）来显示相关label
    $('#period').combobox({
        onSelect : function(param) {
            onSelPeriod();
        }
    });
	//dataGrid加载数据
	function loadData(){
		$('#dataGrid').datagrid('load',{
	    	'cityCode' : $("#hidCityCode").val(),
	    	'type' : $("#hidType").val(),
	    	'parkId' : $("#hidParkId").val(),
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val()
        });
	}
	
	//查询链接
	$(document).keydown(function(event){
		if(event.keyCode==13){
			var period = $("input[name='period']").val();
			//如果没选周期，默认为自选区间
			if(null == period){
				period = 0;
			}
			setBeginAndEnd(period);
			loadData();
		}
	});
	//查询链接
	$("#btnQuery").bind("click",function(){
		var period = $("input[name='period']").val();
		//如果没选周期，默认为自选区间
		if(null == period){
			period = 0;
		}
		setBeginAndEnd(period);
		loadData();
	});
	
	// 专车收入查询导出excel链接
	$("#btnExport").bind("click", function() {
		var cityCode = $("#hidCityCode").val();
		var type = $("#hidType").val();
		var parkId = $("#hidParkId").val();
		var begin = $("#hidBegin").val();
		var end =  $("#hidEnd").val();
		$("#chargingReportForm").form("submit", {
			url : "export_buid_charging_report_excel",
			onSubmit : function(param) {
				param.cityCode = cityCode;
				param.begin = begin;
				param.end = end;
				param.type = type;
				param.parkId = parkId;
			},
			success : function(result) {
			}
		});
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
	
	$("#dataGrid").datagrid({
		title : '充电桩运营报表',
		width : 'auto',
		height : 'auto',
		fit : false,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		url : 'buid_charging_report.do',
		queryParams : {// 在请求远程数据时发送额外的参数。
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val()
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'equipmentId',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'stationName',
			title : '网点名',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'equipmentCode',
			title : '充电桩编码',
			width : $(this).width() * 0.2,
			align : 'center'
	  	}, 
		  {	//桩类型和枪数目：如 "快充-单枪"
			field : 'typeAndCnt',
			title : '桩类型',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{
			field : 'chargedTimes',   
			title : '充电次数',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{	//桩类型和枪数目：如 "快充-单枪"
			field : 'useTime',
			title : '使用时间(小时)',
			width : $(this).width() * 0.3,
			align : 'center',
			formatter : function(val) {
				var min = ((null != val) ? val : 0);
				return Math.floor(min / 60);
			}
		},
		{
			field : 'chargedFreq',   
			title : '充电使用率(%)',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{
			field : 'chargedQuantity',   
			title : '充电电量',
			width : $(this).width() * 0.3,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(2);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		},
		{
			field : 'totalChargingTime',   
			title : '充电时长',
			width : $(this).width() * 0.3,
			align : 'center',
			formatter : function(val) {
				n = val.toFixed(0);
				var re = /(\d{1,3})(?=(\d{3}))/g;
				return n.replace(re, "$1,");
			}
		},
		{
			field : 'fee',   
			title : '订单金额（元）',
			width : $(this).width() * 0.3,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		}
	  	
	  	] ],
	  	onSelect:function(index, row){
	  		$("#hidEquipmentId").val(row.equipmentId);
		},
		onLoadSuccess:function(data){
			$("#dataGrid").datagrid("appendRow", {
				stationName: '<span class="subtotal">合计</span>',
				//chargedTimes: computeSubtotal("#dataGrid", "chargedTimes"),
				chargedQuantity: computeSubtotal("#dataGrid", "chargedQuantity"),
				totalChargingTime: computeSubtotal("#dataGrid", "totalChargingTime"),
				fee: computeSubtotal("#dataGrid", "fee")
            });
			
			var foot = $('#dataGrid').datagrid('getFooterRows');
			if (foot[0] != null){
				$("#dataGrid").datagrid('appendRow', {stationName: '总合计：', chargedTimes:foot[0].chargedTimesSum,useTime: foot[0].useTimeSum,chargedQuantity: foot[0].chargingAmountSum,totalChargingTime: foot[0].chargingTimeSum,fee: foot[0].orderMoneySum.toFixed(2)});
			}else{
				$("#dataGrid").datagrid('appendRow', {stationName: '总合计：',chargedTimes:0, useTime:0,chargedQuantity: 0,totalChargingTime: 0,fee: 0.00});
			}
		},
	  	onClickRow:function(index, row){
//	  		$("#onePileChargingDetailGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
//	  			'equipmentId' : row.equipmentId
//			});
//			$("#onePileChargingDetailView").dialog("setTitle",
//					"充电桩充电明细--【" + row.stationName + "】【" + row.equipmentCode + "】【" + row.typeAndCnt + "】");
//			$('#onePileChargingDetailView').window('open').window('resize', {top: "100px", left:"180px", width : '1000px', height : '500px'});
			
		},
		
	});
	
	//一个桩的充电明细对话框
	$("#onePileChargingDetailGrid").datagrid( {
		width : '1000px',
		height : '500px',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		url : 'get_one_pile_charging_detail.do',
		pageSize : 20,
		columns : [ [{
			field : 'memberName',
			title : '使用者',
			width : $(this).width() * 0.3,
			align : 'center'
		} , {
			field : 'memberIdcard',
			title : '使用者身份证',
			width : $(this).width() * 0.7,
			align : 'center'
		}, 
		{
			field : 'memberPhone',
			title : '手机号码',
			width : $(this).width() * 0.7,
			align : 'center'
		},
		{
			field : 'chargedQuantity',   
			title : '充电电量',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{  
			field : 'totalChargingTime',   
			title : '充电时长',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{
			field : 'fee',
			title : '计费金额（元）',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
			    return val/100;
			}
		},{
			field : 'consumption',
			title : '消费金额（元）',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
			    return val/100;
			}
		},{
			field : 'payMoney',
			title : '支付金额（元）',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
			    return val/100;
			}
		},{
			field : 'couponAmount',
			title : '优惠券（元）',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
			    return val/100;
			}
		}
		
		] ]
		
	});
	
	
});