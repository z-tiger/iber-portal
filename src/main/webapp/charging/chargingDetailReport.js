/**
 * 充电桩充电明细表
 */
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
		$('#chargingDetailGrid').datagrid('load',{
			'cityCode' : $("#hidCityCode").val(),
			'type' : $("#hidType").val(),
			'parkId' : $("#hidParkId").val(),
			'equipmentId' : $("#hidEquipmentId").val(),
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val()
		});
	}
	
	function onClickQueryBtn(){
		var period = $("input[name='period']").val();
		//如果没选周期，默认为自选区间
		if(null == period){
			period = 0;
		}
		setBeginAndEnd(period);
		loadData();
	}
	
	//查询链接
	$(document).keydown(function(event){
		if(event.keyCode==13){
			onClickQueryBtn();
		}
	});
	
	//充电桩充电明细
	$("#btnQueryDetail").bind("click",function(){
		onClickQueryBtn();
//		$("#chargingDetailView").dialog("setTitle", "充电桩充电明细");
//		$('#chargingDetailView').window('open').window('resize', {top: "100px", left:"180px", width : '1500px', height : '500px'});
		
	});
	// 导出
	$("#btnExport").bind("click",function(){
		var param=   $('#chargingDetailGrid').datagrid('options').queryParams;//查询参数
		var url = "export_charging_detail?"+jQuery.param(param);
		window.location.href = url ;
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
	
	//充电桩充电明细表
	$("#chargingDetailGrid").datagrid( {
		title : '充电桩充电明细表',
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
		url : 'get_charging_detail_report.do',
		
		columns : [ [{
			field : 'stationName',
			title : '网点名',
			width : $(this).width() * 0.6,
			align : 'center'
		}, {
			field : 'equipmentCode',
			title : '充电桩编码',
			width : $(this).width() * 0.4,
			align : 'center'
	  	}, 
		  {	//桩类型和枪数目：如 "快充-单枪"
			field : 'typeAndCnt',
			title : '桩类型',
			width : $(this).width() * 0.4,
			align : 'center'
		},
		{	
			field : 'carport',
			title : '车位号',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{
			field : 'memberName',
			title : '使用者',
			width : $(this).width() * 0.4,
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
			width : $(this).width() * 0.5,
			align : 'center'
		},
		{
			field : 'chargedQuantity',   
			title : '充电电量',
			width : $(this).width() * 0.4,
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
			width : $(this).width() * 0.4,
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
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		}
		/*,{
			field : 'consumption',
			title : '消费金额（元）',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				return val/100;
			}
		}*/
		,{
			field : 'payMoney',
			title : '支付金额（元）',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		},{
			field : 'couponAmount',
			title : '优惠券（元）',
			width : $(this).width() * 0.4,
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
			width : $(this).width() * 0.25,
			align : 'center',
			formatter : function(val){
				if(val=="Y"){
					return "是";
				}else{
					return "否";
				}
			}
		},
		{	
			field : 'freeReason',
			title : '免单原因',
			width : $(this).width() * 0.4,
			align : 'center',
 		}
		
		] ],
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		onLoadSuccess:function(data){
			$("#chargingDetailGrid").datagrid("appendRow", {
				stationName: '<span class="subtotal">合计</span>',
				chargedQuantity: computeSubtotal("#chargingDetailGrid", "chargedQuantity"),
				totalChargingTime: computeSubtotal("#chargingDetailGrid", "totalChargingTime"),
				fee: computeSubtotal("#chargingDetailGrid", "fee"),
				consumption: computeSubtotal("#chargingDetailGrid", "consumption"),
				payMoney: computeSubtotal("#chargingDetailGrid", "payMoney"),
				couponAmount: computeSubtotal("#chargingDetailGrid", "couponAmount")
            });
			
			var foot = $('#chargingDetailGrid').datagrid('getFooterRows');
			if (foot[0] != null){
				$("#chargingDetailGrid").datagrid('appendRow', {stationName: '总合计：', chargedQuantity: foot[0].chargingAmountSum,totalChargingTime: foot[0].chargingTimeSum,fee: foot[0].orderMoneySum.toFixed(2), payMoney: foot[0].payMoneySum.toFixed(2), couponAmount: foot[0].couponValueSum.toFixed(2)});
			}else{
				$("#chargingDetailGrid").datagrid('appendRow', {stationName: '总合计：', chargedQuantity: 0,totalChargingTime: 0,fee: 0.00, payMoney: 0.00, couponAmount: 0.00});
			}

	        
		}
	});
	
	
});