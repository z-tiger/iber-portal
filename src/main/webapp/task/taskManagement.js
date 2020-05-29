function showPhoto(url){

    $("#contentView").html("");
    var urls=url.split(",");
    var html='';
    for(var i=0 ;i<urls.length;i++){
    	html = html + "<div><img width='300'  src='"+urls[i]+"'/></div><br>"
	}
    $("#photoView").dialog({title:"任务图片"});
    $("#photoView").dialog("open");
    $("#contentView").html(html);
}
$(function() {
			//查询链接
			$("#btnQuery").bind("click",function(){
			    $("#dataGrid").datagrid("load",{
			    	'name':$("#name").textbox("getValue"),
					'taskName':$("#_taskName").textbox("getValue"),
					'cityCode':$("#cityCode").combobox("getValue"),
					'taskType':$("#_taskType").combobox("getValue"),
					'status':$("#_taskStatus").combobox("getValue"),
                    'queryDateFrom':$("#queryDateFrom").textbox("getValue"),
                    'queryDateTo':$("#queryDateTo").textbox("getValue")
				});
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var name = $("#name").textbox("getValue");
			    var taskName = $("#_taskName").textbox("getValue");
			    var cityCode = $("#cityCode").combobox("getValue");
			    var taskType = $("#_taskType").combobox("getValue");
			    var status = $("#_taskStatus").combobox("getValue");
				$("#taskForm").form("submit", {
					url : "export_task_list?name="+name+"&taskName="+taskName+"&cityCode="+cityCode+"&taskType="+taskType+"&status="+status,
					onSubmit : function() {
					},
					success : function(result) {
					}
				});

			});
			$('#dataGrid').datagrid( {
				title : '任务管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : false,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'task_management_page',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				 },   
				 { 
					 field:'cityName',
					 title:'所属城市',
					 align:'center',
					 width : $(this).width() * 0.05,
				 },
				 {   field:'gridName',
					 title:'所属网格',
					 align:'center',
					 width : $(this).width() * 0.08,
					 formatter: function(val){
							if (val != '') {
								return val;
							}else {
								return "无";
							}
						},
				 },
				 {   field:'taskLevelName',
					 title:'任务级别',
					 align:'center',
					 width : $(this).width() * 0.05,	
				 },
				 {   field:'taskName',
					 title:'任务名称',
					 align:'center',
					 width : $(this).width() * 0.06,
				 },
				 {   field:'taskType',
					 title:'任务类型',
					 align:'center',
					 width : $(this).width() * 0.06,
					 formatter: function(val){
							if (val == '1'||val == '3'||val == '6') {
								return "调度任务";
							}else if (val == '5') {
								return "救援任务";
							}else {
								return "维保任务";
							}
						},
				 },
				 {   field:'employeeName',
					 title:'执行人',
					 align:'center',
					 width : $(this).width() * 0.06,
				 },
				 // {   field:'employeeLeader',
					//  title:'执行人上级',
					//  align:'center',
					//  width : $(this).width() * 0.12,
					//  formatter: function(val){
					// 		if (val != '') {
					// 			return val;
					// 		}else {
					// 			return "无";
					// 		}
					// 	}
				 // },
				 {   field:'status',
					 title:'状态',
					 align:'center',
					 width : $(this).width() * 0.06,
					 formatter: function(val){
								if (val == '1') {
									return "创建";
								}else if (val == '2') {
									return "正在执行";
								}else if (val == '3') {
									return "完成";
								}else if (val == '4') {
                                    return "取消";
                                }
							},
				  },
					{   field:'score',
						title:'积分',
						align:'center',
						width : $(this).width() * 0.06,
					},
					{   field:'planMileage',
                        title:'规划里程',
                        align:'center',
                        width : $(this).width() * 0.06,
                    },
                    {   field:'photoUrl',
                        title:'图片',
                        align:'center',
                        width : $(this).width() * 0.06,
                        formatter:function(value, row, index){
                    	console.log(value)
                    	if (value==''||value==null){
                            return '暂无图片';
						} else{
                            return '<a href=\'javascript:showPhoto('+JSON.stringify(value)+');\' >查看图片</a>';
						}

                        }
                    },
				  {  field:'createTime',
						 title:'任务创建时间',
						 align:'center',
						 width : $(this).width() * 0.08,
				  },
				  {  field:'updateTime',
						 title:'任务更新时间',
						 align:'center',
						 width : $(this).width() * 0.08,
				  },
				  {  field:'taskBeginTime',
						 title:'任务开始时间',
						 align:'center',
						 width : $(this).width() * 0.08,
				  },
				  {  field:'doneTime',
						 title:'任务结束时间',
						 align:'center',
						 width : $(this).width() * 0.08,
				  },
				  {  field:'deadline',
						 title:'任务完成期限',
						 align:'center',
						 width : $(this).width() * 0.08,
						formatter : function(val, rec) {
							var deadline;
							var doneTime;
							if(rec.doneTime!=''&& val!=''){
								doneTime = new Date(rec.doneTime);
								deadline = new Date(val);
							    if(deadline.getTime() < doneTime.getTime()){
							    	return "<font color=red>"+val+"</font>";
							    }else{
							    	return val;
							    }
							}else{
								return val;
							}
						}
				  },
				  {  field:'taskExplain',
					 title:'任务说明',
					 align:'center',
					 width : $(this).width() * 0.13,
				  },
				  {  field:'doneRemark',
					 title:'任务反馈',
					 align:'center',
					 width : $(this).width() * 0.13,
				  }
				] ],
				pagination : true,
				rownumbers : true
			});
    $("#photoView").dialog( {
        width : "500",
        height : "auto",
		align:"center"
    });

	//构造对话框
	$("#editView").dialog( {
		width : "700",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit", {
					url : "updateTask",
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
							$("#editView").dialog("close");
						}else if(result == "same"){
							$.messager.alert("提示", "该车辆还有相同类型的任务没完成,不能修改", "info");
						}
						else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#editView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#editView").dialog("close");
			}
		}]
	});
	//非调度对话框
	$("#updateView").dialog( {
		width : "700",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#updateViewForm").form("submit", {
					url : "updateTask",
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
							$("#updateView").dialog("close");
						}else if(result == "same"){
							$.messager.alert("提示", "该车辆还有相同类型的任务没完成,不能修改", "info");
						}
						else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#updateView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#updateView").dialog("close");
			}
		}]
	});



	//edit
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		}else if(selectedRows[0].status=='3'){
			$.messager.alert("提示", "任务已完成,不允许修改", "error");
		}else if(selectedRows[0].taskType=='1'||selectedRows[0].taskType=='3'){
			$("#e-id").val(selectedRows[0].id);
			$("#e-oldEmployeeId").val(selectedRows[0].employeeId);
			$("#e-taskType").combobox('setValue',selectedRows[0].taskType);
			$("#e-taskTypeNo").val(selectedRows[0].taskType);
			node = $("#employee").combotree('tree').tree('find',selectedRows[0].employeeId);
			$("#currentName").textbox('setValue',selectedRows[0].employeeName);
			$("#employee").combotree('setValue',selectedRows[0].employeeId);
			$("#e-carLpn").val(selectedRows[0].lpnId);
			$("#carLpn").textbox('setValue',selectedRows[0].lpn);
			$("#beginParkId").combobox('setValue',selectedRows[0].beginParkId); 
			$("#endParkId").combobox('setValue',selectedRows[0].endParkId); 
			$("#taskLevel").combobox('setValue',selectedRows[0].taskLevel); 
			$("#requestPayTime").combobox('setValue',selectedRows[0].timeLimit);
			$("#specialComment").textbox('setValue',selectedRows[0].taskExplain);
			if(selectedRows[0].deadline==''){
			    $("#e-deadline").datetimebox({  
	    			required : true,  
	   			 	onShowPanel:function(){  
	        		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
	    			}  
				});
			}else{
				$("#e-deadline").datetimebox("setValue",selectedRows[0].deadline);
			}
			$("#editView").dialog({title:"修改任务"});
			$("#editView").dialog("open");
			$("#employee").combo({
				onChange:function(node){
					var t = $('#employee').combotree('tree');	// 得到树对象
					var n = t.tree('getSelected');		// 得到选择的节点
					if (n && n.children) {
						$('#employee').combotree('clear'); 
						$.messager.alert("提示", "请选择子节点", "error");
					}
				}
			})
		}else{// 非调度任务弹框
			$("#updateViewForm").form("clear");
			$("#u-currentName").textbox('setValue',selectedRows[0].employeeName);
			$("#u-oldEmployeeId").val(selectedRows[0].employeeId);
			$("#u-taskTypeNo").val(selectedRows[0].taskType);
			$("#u-taskId").val(selectedRows[0].id);
			$("#u-carLpn").val(selectedRows[0].lpnId);
			$("#presentEId").combobox('setValue',selectedRows[0].employeeId);
			$("#taskCategory").combobox('setValue',selectedRows[0].taskType);
			$("#licensePlate").textbox('setValue',selectedRows[0].lpn); 
			if(selectedRows[0].beginParkId!=0){
				$("#startingParkId").combobox('setValue',selectedRows[0].beginParkId);
			}
			if(selectedRows[0].endParkId!=0){
				$("#overParkId").combobox('setValue',selectedRows[0].endParkId); 
			}
			if(selectedRows[0].deadline==''){
			    $("#u-deadline").datetimebox({  
	    			required : true,  
	   			 	onShowPanel:function(){  
	        		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
	    			}  
				});
			}else{
				$("#u-deadline").datetimebox("setValue",selectedRows[0].deadline);				
			}
			$("#taskGrade").combobox('setValue',selectedRows[0].taskLevel); 
			$("#requestedTime").combobox('setValue',selectedRows[0].timeLimit);
			$("#specialExplain").textbox('setValue',selectedRows[0].taskExplain);
			$("#updateView").dialog({title:"修改任务"});
			$("#updateView").dialog("open");
			$("#u-employee").combo({
				onChange:function(node){
					var t = $('#u-employee').combotree('tree');	// 得到树对象
					var n = t.tree('getSelected');		// 得到选择的节点
					if (n && n.children) {
						$('#u-employee').combotree('clear'); 
						$.messager.alert("提示", "请选择子节点", "error");
					}
				}
			})
		}
	});

});




