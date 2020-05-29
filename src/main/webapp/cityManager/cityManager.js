$(function() {
			//查询链接
			$("#btnQuery").bind("click",function(){
			    $("#dataGrid").datagrid("load",{
					'cityManagerName':$("#cityManagerName").textbox("getValue"),
					'cityCode':$("#cityCode").combobox("getValue"),
					'status':$("#status").combobox("getValue")
				});
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var cityManagerName = $("#cityManagerName").textbox("getValue");
			    var cityCode = $("#cityCode").combobox("getValue");
			    var status = $("#status").combobox("getValue");
				$("#cityManagerForm").form("submit", {
					url : "export_cityManager_report?cityManagerName="+cityManagerName+"&cityCode="+cityCode+"&status="+status,
					onSubmit : function() {
					},
					success : function(result) {
					}
				});

			});
			$('#dataGrid').datagrid( {
				title : '城市管理员',
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
				url : 'getCityManagerInfos',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'cityName',
					title : '所属城市',
					width : $(this).width() * 0.1,
					align : 'center'
				},
				{
					field : 'name',
					title : '姓名',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'profession',
					title : '工种',
					hidden : true,
					width : $(this).width() * 0.1,
					align : 'center'
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
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.2,
					align : 'center',
				},
				{
					field : 'updateTime',
					title : '最新修改时间',
					width : $(this).width() * 0.2,
					align : 'center',
				},
				{
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
					width : $(this).width() * 0.2,
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
					url : "addOrUpdateCityManagerInfo",
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
							$.messager.alert("提示", "该电话号码已经存在,请使用新的手机号码创建或修改员工账号信息", "info");
							$("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else if (result == "p-wrong") {
							$.messager.alert("提示", "请输入正确的手机号码", "info");
						}else if (result == "c-wrong") {
							$.messager.alert("提示", "请选择正确的城市", "info");
						}else if (result == "email-fail") {
                            $.messager.alert("提示", "请输入正确的邮箱", "info");
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
		$("#e-oType").val('ADD_RESCUER');
		$("#addView").dialog({title:"添加城市管理员",width: 700,height: 400});
		$("#addView").dialog("open");
	});
	
	//edit
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
		}else if(selectedRows[0].status == 'freeze'){
			$.messager.alert("提示", "当前账户已被冻结", "error");
		}
		else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-cityManagerName").textbox('setValue',selectedRows[0].name);
			$("#e-phone").textbox('setValue',selectedRows[0].phone);
			$("#e-specialComment").textbox('setValue',selectedRows[0].remark);
			$("#e-identy").combobox('setValue',selectedRows[0].identifyLabel);
			$("#e-sex").combobox('setValue',selectedRows[0].sex);
			$("#e-city").combobox('setValue',selectedRows[0].cityCode); 
			$("#e-type").combobox('setValue',"4"); 
			$("#e-worker").combobox('setValue',selectedRows[0].profession);
            $("#e-position").textbox('setValue',selectedRows[0].position);
            $("#e-email").textbox('setValue',selectedRows[0].email);
			$("#editView").dialog({title:"修改城市管理员"});
			$("#editView").dialog("open");
		}
	});
	
	//冻结操作
	$("#btnFrozen").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要冻结的救援员", "error");
   		}else if(selectedRow[0].status == 'freeze'){
			$.messager.alert("提示", "该账户已冻结", "error");
		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要冻结吗?",function(r){
	      		if(r){
					$.post("freezeOrActiveCityManagerAccount",{"id":JsonData.id},function(data){
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
    //重置密码
    $("#resetPassword").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要重置密码的城市管理员", "error");
        } else{
            var JsonData = selectedRow[0];
            $.messager.confirm("提示","确定要重置密码吗?",function(r){
                if(r){
                    $.post("city_manager_reset_password",{"id":JsonData.id},function(data){
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
					$.post("freezeOrActiveCityManagerAccount",{"id":JsonData.id,operationType:'active'},function(data){
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
								$("#grid").datagrid("reload");
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

	$("#editView").dialog( {
		width : "700",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit", {
					url : "addOrUpdateCityManagerInfo",
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
						}else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#editView").dialog("close");
						}else if (result == "exist") {
							$.messager.alert("提示", "该电话号码已经存在,请使用新的手机号码创建或修改员工账号信息", "info");
						}else if (result == "p-wrong") {
							$.messager.alert("提示", "请输入正确的手机号码", "info");
						}else if (result == "c-wrong") {
							$.messager.alert("提示", "请选择正确的城市", "info");
						}else if(result == "hasTask"){
							$.messager.alert("提示", "该员工有未完成的任务,请先完成任务再操作", "info");
						}else if (result == "email-fail") {
                            $.messager.alert("提示", "请输入正确的邮箱", "info");
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
	
});


