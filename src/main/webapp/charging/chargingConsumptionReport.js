/**
 * 用电量报表 【所属城市】【网点名】【网点类型】（自有、自建）【充电桩数】（两个小项：【快充、慢充】）【用电量】，
 * 按城市、网点（模糊查询）、类型、桩类型、日期区间查询
 */

$(function() {
	
	//隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();
	
	function loadData(){
		$('#dataGrid').datagrid('load',{
			'cityCode' : $("input[name='cityCode']").val(),
			'stationName' : $("input[name='stationName']").val(),
			'cooperationType' : $("input[name='cooperationType']").val(),
			'equipmentType' : $("input[name='equipmentType']").val(),
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val()
       });
	}
	
	//查询链接
	$(document).keydown(function(event){
		if(event.keyCode==13){
			loadData();
		}
	});
	
	//查询链接
	$("#btnQuery").bind("click",function(){
		loadData();
	});
	
	//清空
	$("#clearQuery").bind("click",function(){
		clearQueryForm();
	});
	
	function clearQueryForm(){
		$("#toolbar").form('clear');
	}
	
	$("#dataGrid").datagrid( {
		title : '用电量报表',
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
		url : 'get_consumption_report.do',
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		columns : [ [{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.6,
			align : 'center'
		}, {
			field : 'stationName',
			title : '网点名',
			width : $(this).width() * 0.6,
			align : 'center'
		}, {
			field : 'cooperationType',
			title : '网点类型',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				if(0 == val){
					return "自有网点";
				}else{
					return "合作网点";
				}
			}
		}, 
		{	//两个小项：【快充、慢充】
			field : 'equipmentCnt',
			title : '充电桩数',
			width : $(this).width() * 0.4,
			align : 'center'
		},
		{
			field : 'chargedQuantity',   
			title : '用电量',
			width : $(this).width() * 0.3,
			align : 'center'
		}
		
		] ]
		
	});
});