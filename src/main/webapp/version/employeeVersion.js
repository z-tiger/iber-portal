$(function() {
	$('#dataGrid').datagrid({
		title : '员工版本管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		url : 'manager_employee_list',
		queryParams : {
			'versionNo' : $("#versionsNo").textbox("getValue"),
			//'categoryCode' : $("#categoryCode").combobox("getValue")
		},
		pageSize : 100,
		pageList : [ 100, 50, 30, 10 ],
		columns : [ [ {
			field : 'id',
			checkbox : true
		}, {
			field : 'versionName',
			title : '版本名称',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'categoryName',
			title : '版本类型',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'versionNo',
			title : '版本号',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'versionRecord',
			title : '版本记录数',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'createName',
			title : '创建人',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.8,
			align : 'center',
			formatter : function(val, rec) {
					return formatDate(val);
				 }
		}, {
			field : 'updateTime',
			title : '更新时间',
			width : $(this).width() * 0.8,
			align : 'center',
			 formatter : function(val, rec) {
					return formatDate(val);
				 }
		}, {
			field : 'updateName',
			title : '更新人',
			width : $(this).width() * 0.4,
			align : 'center'
		} ] ],
		pagination : true,
		rownumbers : true
	});

	// query
	$("#btnQuery").bind("click", function() {
		$("#dataGrid").datagrid("load", {
			'versionNo' : $("#versionsNo").textbox("getValue"),
			//'categoryCode' : $("#categoryCode").combobox("getValue")
		});
	});
	function clearToolbar(){
		$('#toolbar').form('clear');
	}
//清空
$("#clearQuery").bind("click",function(){
	clearToolbar();
});
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
	 	$("#addView").dialog("open").dialog("setTitle", "添加版本记录");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#categoryCode").combobox("setValue",JsonData.categoryCode);
			$("#versionName").textbox("setValue",JsonData.versionName);
			$("#versionNo").textbox("setValue",JsonData.versionNo);
			$("#versionRecord").textbox("setValue",JsonData.versionRecord);
			$("#addView").dialog("open").dialog("setTitle", "编辑记录");
  		}	     
	});
	//构造对话框
	$("#addView").dialog( {
		width : "500",
		height : "400",
		top : "5",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "add_versions_employee",
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
	
	//删除操作
	$("#btnDelete").bind("click",function(){
      		var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除记录", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("versions_employ_del",{"id":JsonData.id},function(data){
						if(data=="success"){
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});	

})