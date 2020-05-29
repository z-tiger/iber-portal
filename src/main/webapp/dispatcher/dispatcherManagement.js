$(function() {
			//查询链接
			$("#btnQuery").bind("click",function(){
			    $("#dataGrid").datagrid("load",{
					'dispatcherName':$("#_dispatcherName").textbox("getValue"),
					'griddingId':$("#griddingName").combobox("getValue"),
					'cityCode':$("#cityCode").combobox("getValue"),
					'status':$("#status").combobox("getValue")
				});
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var dispatcherName = $("#_dispatcherName").textbox("getValue");
			    var griddingId = $("#griddingName").combobox("getValue");
			    var cityCode = $("#cityCode").combobox("getValue");
			    var status = $("#status").combobox("getValue");
				$("#dispatcherForm").form("submit", {
					url : "export_dispatcher_report?dispatcherName="+dispatcherName+"&griddingId="+griddingId+"&cityCode="+cityCode+"&status="+status,
					onSubmit : function() {
					},
					success : function(result) {
					}
				});

			});

    //重置密码
    $("#resetPassword").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要重置密码的调度员", "error");
        } else{
            var JsonData = selectedRow[0];
            $.messager.confirm("提示","确定要重置密码吗?",function(r){
                if(r){
                    $.post("dispatcher_reset_password",{"id":JsonData.id},function(data){
                        if(data=="success"){
                            $.messager.alert("提示", "重置密码成功", "info");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "重置密码失败", "info");
                        }
                    },"text");
                }
            });
        }
    });


			$('#dataGrid').datagrid( {
				title : '调度员管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				queryParams: {
					'status':$("#status").combobox("getValue")
				},
				url : 'dispatcher_management_page',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'name',
					title : '姓名',
					width : $(this).width() * 0.2,
					align : 'center',
				} , {
					field : 'cityName',
					title : '所属城市',
					width : $(this).width() * 0.2,
					align : 'center'
				}, 
				{
					field : 'gridName',
					title : '所属网格',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val){
						if (val != '') {
							return val;
						}else {
							return "无";
						}
					}
				},
				{
					field : 'identifyLabel',   
					title : '身份',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val){
						if (val == '1') {
							return "调度管理员";
						}else {
							return "调度员";
						}
					}
				},
				{  
					field : 'status',   
					title : '状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val){
						if (val == 'working') {
							return "上班";
						}else if (val == 'ordered') {
							return "预约车辆中";
						}else if(val =="useCar"){
							return "使用车辆中";
						}else if(val == "freeze"){
							return "<span style='color:red'>冻结</span>";
						}else {
							return "下班";
						}
					}
				},
				{
					field : 'phone',
					title : '手机号',
					width : $(this).width() * 0.2,
					align : 'center',
				},{
					field : 'currentTask',
					title : '当前任务数',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'todayCompleteTask',
					title : '今日完成数',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'processTask',
					title : '正在执行',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'totalCompleteTask',
					title : '累计完成',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'todayScore',
					title : '今日积分',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'monthScore',
					title : '本月积分',
					width : $(this).width() * 0.1,
					align : 'center',
				},{
					field : 'totalScore',
					title : '总积分',
					width : $(this).width() * 0.1,
					align : 'center',
				},{
					field : 'remark',
					title : '备注',
					width : $(this).width() * 0.3,
					align : 'center'
				}
				] ],
				pagination : true,
				rownumbers : true
			}); 
			
	//构造对话框
	$("#addView").dialog( {
		width : "700",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit", {
					url : "saveOrUpdateDispatcher",
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
						}else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else if (result == "exist") {
							$.messager.alert("提示", "该员工已经存在，请使用新的手机号创建员工账号", "info");
						}else if (result == "p-wrong") {
							$.messager.alert("提示", "请输入正确的手机号码", "info");
						}else if (result == "c-wrong") {
							$.messager.alert("提示", "请选择正确的城市", "info");
						}else if(result =="email-fail"){
                            $.messager.alert("提示", "邮箱格式不正确", "info");
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
	
	//add
	$("#btnAdd").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#o-type").val('ADD_DISPATER');
		$("#addView").dialog({title:"创建调度员",width: 700,height: 400});
		$("#addView").dialog("open");
	});
	
	//edit
	$("#btnEdit").bind("click",function(){
		$("#updateViewForm").form("clear");
		
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		}else if(selectedRows[0].status == 'freeze'){
			$.messager.alert("提示", "当前账户已被冻结,不能修改", "error");
		}else{
			$("#u-id").val(selectedRows[0].id);
			$("#u-ident").combobox('setValue',selectedRows[0].identifyLabel);
			$("#u-dispatherName").textbox('setValue',selectedRows[0].name);
			$("#u-phone").textbox('setValue',selectedRows[0].phone);
			$("#u-sex").combobox('setValue',selectedRows[0].sex);
			$("#u-type").combobox('setValue',selectedRows[0].type); 
			$("#u-city").combobox('setValue',selectedRows[0].cityCode); 
			$("#u-worker").combobox('setValue',selectedRows[0].profession); 
			$("#u-specialComment").textbox('setValue',selectedRows[0].remark);
            $("#u-position").textbox('setValue',selectedRows[0].position);
            $("#u-email").textbox('setValue',selectedRows[0].email);
			$("#updateView").dialog({title:"修改调度员"});
			$("#updateView").dialog("open");
			
		}
	});
	// 修改对话框
	$("#updateView").dialog( {
		width : "700",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#updateViewForm").form("submit", {
					url : "saveOrUpdateDispatcher",
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
						}else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#updateView").dialog("close");
						}else if (result == "exist") {
							$.messager.alert("提示", "该员工已经存在,请勿重复创建员工账号", "info");
							$("#dataGrid").datagrid("reload");
							$("#updateView").dialog("close");
						}else if (result == "no-grid") {
							$.messager.alert("提示", "请为该调度员设置所属片区", "info");
							$("#updateView").dialog("close");
						}else if (result == "p-wrong") {
							$.messager.alert("提示", "请输入正确的手机号码", "info");
						}else if (result == "c-wrong") {
							$.messager.alert("提示", "请选择正确的城市", "info");
						}else if(result == "hasTask"){
							$.messager.alert("提示", "该员工有未完成的任务,请先完成任务再操作", "info");
						}else if(result =="email-fail"){
                            $.messager.alert("提示", "邮箱格式不正确", "info");
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
	
	//冻结操作
	$("#btnFrozen").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要冻结的调度员", "error");
   		}else if(selectedRow[0].status == 'freeze'){
			$.messager.alert("提示", "该账户已冻结", "error");
		}
   		else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要冻结吗?",function(r){
	      		if(r){
					$.post("freezeOrActiveDispatcherAccount",{"id":JsonData.id},function(data){
						if(data=="success"){
							$.messager.alert("提示", "冻结成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "冻结失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	//启用操作
	$("#btnActive").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要启用的救援员", "error");
   		}else if(selectedRow[0].status != 'freeze'){
			$.messager.alert("提示", "该账户已启用", "error");
		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要启用吗?",function(r){
	      		if(r){
					$.post("freezeOrActiveDispatcherAccount",{"id":JsonData.id,operationType:'active'},function(data){
						if(data=="success"){
							$.messager.alert("提示", "启用成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "启用失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	//删除员工指纹信息
	$("#btnFinger").bind("click",function(){
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除指纹的员工", "error");
		 }else if(selectedRows[0].fingerUrl == null || selectedRows[0].fingerUrl == ""){
			 $.messager.alert("提示", "员工未录入指纹", "error");
		 }else{
			 var _id =  selectedRows[0].id;
				$.messager.confirm("提示","确定要删除吗?",function(r){
					if(r){
						$.post("employee_del_finger",{id:_id},function(data){
							if(data == "succ"){
								$.messager.alert("提示", "操作成功", "info");
								$("#dataGrid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }
	});
	//删除三代员工指纹信息
	$("#btnTboxFinger").bind("click",function(){
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除指纹的员工", "error");
		 }else if(!selectedRows[0].tboxFinger){
			 $.messager.alert("提示", "员工未录入指纹", "error");
		 }else{
			 var _id =  selectedRows[0].id;
				$.messager.confirm("提示","确定要删除三代指纹吗?",function(r){
					if(r){
						$.post("employeeDelTboxFinger",{id:_id},function(data){
                            if(data){
                                if (data.code === 1){
                                    $.messager.alert("提示", "操作成功", "info");
                                    $("#dataGrid").datagrid("reload");
                                }else {
                                    $.messager.alert("提示", data.message, "info");
                                }
                            }else{
                                $.messager.alert("提示", "操作失败", "error");
                            }
                        },"json");
					}
				});
		 }
	});
});


