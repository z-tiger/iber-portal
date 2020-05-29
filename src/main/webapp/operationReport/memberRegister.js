$(function() {
	// 查询
	$("#btnQuery").bind("click", function() {
		search();
	});	
	
	// 查询
	function search() {	

		$('#dataGrid').datagrid('load', {
			'beginTime' : $('#beginTime').textbox('getValue'),
			'endTime' : $('#endTime').textbox('getValue')
		});	
	}

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
	
	//查看会员信息
	$("#ckMemberQuery").bind("click",function(){
		var selectedRow = $("#dataGrid").datagrid("getSelections");
		if (selectedRow.length <= 0) {
			$.messager.alert("提示", "请选择一条记录", "error");
		} else {
			var userJsonData = selectedRow[0];
			$('#memberGrid').datagrid('load',{
			   'cityCode':userJsonData.cityCode,
			   'beginTime' : $('#beginTime').textbox('getValue'),
			   'endTime' : $('#endTime').textbox('getValue')        	
			});
          	$("#memberTypeView").dialog("open").dialog("setTitle",
							"会员信息列表");
		}
	});
	
	// 查询导出excel链接
	$("#btnExport").bind("click",function(){
	    var beginTime = $.trim($("input[name='beginTime']").val());
	    var endTime = $.trim($("input[name='endTime']").val());
	    $("#data").form("submit", {
					url : "export_member_register_excel?beginTime="+beginTime+"&endTime="+endTime,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });	
	
	//构造列表
	$('#memberGrid').datagrid( {
		//title : '会员信息列表',
		width : '800',
		height : '600',
		fit:true,
		fitColumns: true,
		nowrap : true,
		striped : true,
		collapsible : true,
		url : 'member_register_info',
		pageSize : 100,
		pageList : [100,50,30,10],
		pageIndex:1,
		idField : 'p_id',
		columns : [ [ {
			field : 'ck',
			checkbox:true
		}, {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.14,
			align : 'center',
			
		}, {
			field : 'sex',
			title : '性别',
			width : $(this).width() * 0.07,
			align : 'center',
			formatter : function(val, rec) {
			    if(val == "null")
			       return "";
			    else
			    	return val;
			}
			
		},{
			field : 'phone',
			title : '手机',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
			    if(val == "null")
			       return "";
			    else
			    	return val;
			}
		},{
			field : 'idcard',
			title : '身份证',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter : function(val, rec) {
			    if(val == "null")
			       return "";
			    else
			    	return val;
			}
		},{
			field : 'createTime',
			title : '注册时间',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter : function(val, rec) {
				return formatDate(val);
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	//构造列表
	$('#dataGrid').datagrid( {
		title : '会员注册报表',
		width : 'auto',
		height : 'auto',
		fit:true,
		fitColumns: true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		onLoadSuccess: compute,//加载完毕后执行计算
		url : 'member_register',
		pageSize:20,
		pageIndex:1,
		idField : 'p_id',
		columns : [ [ {
			field : 'ck',
			checkbox:true
			
		}, {
			field : 'cityName',
			title : '区域名称',
			width : $(this).width() * 0.1,
			align : 'center',
			
		}, {
			field : 'total',
			title : '注册数量',
			width : $(this).width() * 0.1,
			align : 'center',
			
		},{
			field : 'cityCode',
			title : '区域编号',
			width : $(this).width() * 0.1,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});

    function compute() {//计算函数
        var rows = $('#dataGrid').datagrid('getRows')//获取当前的数据行
        var totalVal = 0//总和
 
        
        for (var i = 0; i < rows.length; i++) {
        	totalVal += parseFloat(rows[i]['total']).toFixed(2);
      
        }
        
        //新增一行显示统计信息
        $('#dataGrid').datagrid('appendRow', { cityName: '合计：', total: totalVal});
    }




});