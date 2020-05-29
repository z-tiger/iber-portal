$(function() {
	// 设置默认值
	$('#beginTime').datetimebox('setValue', getNowBeforeFormatDate());
	$('#endTime').datetimebox('setValue', getTodayEndTimeString());
	
	$('#dataGrid')
	.datagrid(
			{
				title : '调度任务',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dispatch_task_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				pageSize : 20,
				idField : 'id',
				queryParams: {
					'status': $('#status').textbox('getValue'),
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue')				
				},
                columns: [[{
			         field : 'ck',
	                 checkbox : true
		            },{
					  field : 'taskDesc',
					  title : '调度信息',
					  width : $(this).width() * 0.18,
					  align : 'left'
				   },{
   					  field : 'dispatcherName',
   					  title : '执行人员',
   					  width : $(this).width() * 0.02,
   					  align : 'center'
   				   },{
					  field : 'createTime',
					  title : '时间',
					  width : $(this).width() * 0.02,
					  align : 'center',
					  formatter : function(val, rec) {
						return formatDate(val);
					  }
				   },{
   					  field : 'status',
   					  title : '状态',
   					  width : $(this).width() * 0.02,
   					  align : 'center',
					  formatter : function(val, rec) {
						if(val == "0"){
					       return "新建";
						  }else if (val == "1"){
					       return "已指派进行中";
					     }else if (val == "2"){
					       return "完成";
					     }else if(val == "3"){
					       return "已取消";
					     }else {
					       return " ";
					     }
					   }
   				   }
                       ]],
				pagination : true,
				rownumbers : true
			});
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
		
		$('#beginTime').datebox('setValue', '');
		$('#endTime').datebox('setValue', '');
		
		if($('#status').length > 0) {
			$('#status').textbox('setValue', '');
		}
	});
	
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
	 	$("#addView").dialog("open").dialog("setTitle", "添加调度任务");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑调度任务记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
		   	$("#id").val(JsonData.id);
		   	if($.trim(JsonData.dispatcherId) =="0"){
		   		$("#dispatcherId").combobox("setValue","");	
		   	}else{
		   		$("#dispatcherId").combobox("setValue",JsonData.dispatcherId);		
		   	}
			$("#modStatus").combobox("setValue",JsonData.status);
			$("#taskDesc").textbox("setValue",JsonData.taskDesc);
			$("#addView").dialog("open").dialog("setTitle", "调度任务详情");
  		}	     
	});
	
	
	//构造对话框
	$("#addView").dialog( {
		width : "550",
		height : "400",
		top : "40",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "add_dispatcher_task",
					onSubmit : function() {
						$.messager.progress({
		                    text:"正在加载，请稍等！"
		                });
		                var flag = $(this).form("validate");
		                if(!flag){
		                	$.messager.progress('close'); 
		                }
						return flag;
					},
					success : function(result) {
						$.messager.progress('close'); 
						if (result == "success") {
							$.messager.alert("提示", "保存成功", "info");
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#addView").dialog("close");
			}
		}]
	});	
	
	
	// 调度任务导出excel链接
	$("#btnExport").bind("click",function(){

	    var status = $.trim($("input[name='status']").val());
		var beginTime = $.trim($("input[name='beginTime']").val());
	    var endTime = $.trim($("input[name='endTime']").val());
	    $("#data").form("submit", {
					url : "dispatcher_task_excel?beginTime="+beginTime+"&endTime="+endTime+"&status="+status,
					onSubmit : function() {
						
					},
					success : function(result) {
					}
				});
	    });
	

	
	// 查询
	function search() {	
		$('#dataGrid').datagrid('load', {			
			'status': $('#status').textbox('getValue'),
			'beginTime': $('#beginTime').datebox('getValue'),
			'endTime': $('#endTime').datebox('getValue')		
		});	
	}
	
	});
