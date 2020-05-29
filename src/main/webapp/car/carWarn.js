$(function(){
	//table list view
	//table
	$('#carWarnGrid').datagrid({
		title : '车辆报警记录',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_warn_list',
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'name',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		},
		/*{
			field : 'cityName',
			title : '公司名称',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				return "交通科技" ;
			}
		},*/
		{
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•")) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
				
			}
		}, {
			field : 'warnType',
			title : '报警类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				return "超速" ;
			}
		}, {
			field : 'createTime',
			title : '时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'address',
			title : '地址',
			width : $(this).width() * 0.08,
			align : 'center',
		},{
			field : 'speed',
			title : '备注',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				return "时速"+val+"km/h";
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		var queryDateFrom =$("input[name='queryDateFrom']").val();
		var queryDateTo = $("input[name='queryDateTo']").val();
		$("#carWarnGrid").datagrid("load",{
			'lpn':$("#s-lpn").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
			'queryDateFrom':queryDateFrom,
			'queryDateTo':queryDateTo
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		/*$("#warnForm").form("clear");*/
	});
});