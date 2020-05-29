$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '会员信息报表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'member_info',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'code': $('#code').textbox('getValue'),
					'registerCategory': $('#registerCategory').textbox('getValue'),
					'memberPhone': $('#memberPhone').textbox('getValue'),
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue'),
					'examine_start_time': $('#examine_start_time').datebox('getValue'),
					'examine_end_time': $('#examine_end_time').datebox('getValue'),
					'examine_id': $('#examine_id').textbox('getValue'),
					'memberLevel': $('#memberLevel').textbox('getValue')
				},
                columns: [[/*{
        					field : 'id',
        					title : '会员ID',
        					width : $(this).width() * 0.01,
        					align : 'center'
        				},*/{
        					field : 'name',
        					title : '会员姓名',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
           					field : 'sex',
           					title : '性别',
           					width : $(this).width() * 0.05,
           					align : 'center'
           				},{
           					field : 'phone',
           					title : '手机号',
           					width : $(this).width() * 0.1,
           					align : 'center'
           				},{
        					field : 'idcard',
        					title : '身份证',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'driverIdcard',
        					title : '驾驶证',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'status',
        					title : '状态',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
        					field : 'registerIp',
        					title : '注册IP',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
        					field : 'registerCategory',
        					title : '注册方式',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
        					field : 'createTime',
        					title : '注册时间',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'examineTime',
        					title : '审核时间',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						return formatDate(val);
        					}
        				},{
        					field : 'userName',
        					title : '审核人',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
        					field : 'cityName',
        					title : '注册城市',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
        					field : 'memberLevel',
        					title : '会员等级',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
        					field : 'integral',
        					title : '会员贡献值',
        					width : $(this).width() * 0.05,
        					align : 'center'
        				},{
        					field : 'timeRentUserTotal',
        					title : '时租用车次数',
        					width : $(this).width() * 0.05,
        					align : 'center'
        				},{
        					field : 'dayRentUserTotal',
        					title : '日租用车次数',
        					width : $(this).width() * 0.05,
        					align : 'center'
        				},{
        					field : 'chargingTotal',
        					title : '充电次数',
        					width : $(this).width() * 0.05,
        					align : 'center'
        				}
                       ]],
				pagination : true,
				rownumbers : true
			});
	

	// 查询
	$("#btnQuery").bind("click", function() {
		search();
	});
	
	// 本周报表查询
	$("#btnWeekQuery").bind("click", function() {
		$('#beginTime').datebox('setValue', getWeekFirstDay());
		$('#endTime').datebox('setValue', getWeekLastDay());
		monthSearch();
	});
	
	// 本月报表查询
	$("#btnMonthQuery").bind("click", function() {
		$('#beginTime').datebox('setValue', getMonthStartDate());
		$('#endTime').datebox('setValue', getMonthEndDate());
		monthSearch();
	});
	
	// 本月报表查询
	function monthSearch() {
		
		$('#dataGrid').datagrid('load', {
			'code' : $('#code').textbox('getValue'),
			'registerCategory' : $('#registerCategory').textbox('getValue'),
			'memberPhone' : $('#memberPhone').textbox('getValue'),
			'beginTime' : $('#beginTime').textbox('getValue'),
			'endTime' : $('#endTime').textbox('getValue'),
			'examine_start_time': $('#examine_start_time').datebox('getValue'),
			'examine_end_time': $('#examine_end_time').datebox('getValue'),
			'examine_id': $('#examine_id').textbox('getValue'),
			'memberLevel':$('#memberLevel').textbox('getValue')
		});	
	}
	
	
	
	// 绑定enter建查询
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});
	// 清空
	$("#btnClear").bind("click", function() {

		if($('#code').length > 0) {
			$('#code').textbox('setValue', '');
		}

		if($('#registerCategory').length > 0) {
			$('#registerCategory').textbox('setValue', '');
		}		
		if($('memberLevel').length > 0){
			$('#memberLevel').textbox('setValue', '');
		}
		$('#memberPhone').textbox('setValue', '');
		$('#beginTime').datebox('setValue', '');
		$('#endTime').datebox('setValue', '');
		$('#examine_start_time').datebox('setValue',"");
		$('#examine_end_time').datebox('setValue',"");
		$('#examine_id').textbox('setValue',"");
	
	});
	
 
	// 查询
	function search() {	
		if($("#data").form("validate")){
			$('#dataGrid').datagrid('load', {
				'code' : $('#code').textbox('getValue'),
				'registerCategory' : $('#registerCategory').textbox('getValue'),
				'memberPhone' : $('#memberPhone').textbox('getValue'),
				'beginTime' : $('#beginTime').textbox('getValue'),
				'endTime' : $('#endTime').textbox('getValue'),
				'examine_start_time': $('#examine_start_time').datebox('getValue'),
				'examine_end_time': $('#examine_end_time').datebox('getValue'),
				'examine_id': $('#examine_id').textbox('getValue'),
				'memberLevel':$('#memberLevel').textbox('getValue')
			});	
		}
	}

	// 查询导出excel链接
	$("#btnExport").bind("click",function(){
		var code= $.trim($("input[name='code']").val());
		var registerCategory= $.trim($("input[name='registerCategory']").val());
		var memberPhone= $.trim($("input[name='memberPhone']").val());
	    var beginTime = $.trim($("input[name='beginTime']").val());
	    var endTime = $.trim($("input[name='endTime']").val());
	    var memberLevel = $.trim($("input[name='memberLevel']").val());
	    var examine_start_time=$('#examine_start_time').datebox('getValue');
		var examine_end_time= $('#examine_end_time').datebox('getValue');
		var examine_id=$('#examine_id').textbox('getValue');
	    $("#data").form("submit", {
					url : "export_member_info_excel?code="+code+"&memberPhone="+memberPhone+"&registerCategory="+registerCategory+"&beginTime="+
							beginTime+"&endTime="+endTime+"&memberLevel="+memberLevel+"&examine_start_time="+examine_start_time+"&examine_end_time="+examine_end_time+"&examine_id="+examine_id,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });	
	});
