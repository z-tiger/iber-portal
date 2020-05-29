$(function() {
	$("#currentMonth").html(getCurrentMonth());
	$("#monthStartDate").html(getMonthStartDate());
	$("#monthEndDate").html(getMonthEndDate());
	$("#currentBeginTime").html(getTodayBeginTimeString());
	$("#currentEndTime").html(getTodayEndTimeString());
	// 查询
	$("#btnQuery").bind("click", function() {
		search();
	});	
	
	// 查询
	function search() {	

		if ($('#beginTime').textbox('getValue') == '') {
			alert("请输入开始时间");
			return;
		}
		if ($('#endTime').textbox('getValue') == '') {
			alert("请输入结束时间");
			return;
		}
		$('#dataGrid').datagrid('load', {
			'beginTime' : $('#beginTime').textbox('getValue'),
			'endTime' : $('#endTime').textbox('getValue')
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
		
		$('#beginTime').datebox('setValue', '');
		$('#endTime').datebox('setValue', '');
	
	});
	
	// 查询测试链接
	$("#btnExport").bind("click",function(){
		
	    var queryTime = $.trim($("input[name='queryTime']").val());
	    $("#data").form("submit", {
					url : "test_list?queryTime="+queryTime,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });

});