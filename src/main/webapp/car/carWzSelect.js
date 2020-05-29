$(function(){
	//table list view
	//table
	$('#grid').datagrid({
		title : '周边城市管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_wzcity_list',
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityCode',
			title : '城市编码',
			width : $(this).width() * 0.08,
			align : 'center'
		},
		{
			field : 'cityName',
			title : '中心城市',
			width : $(this).width() * 0.08,
			align : 'center',
		},{
			field : 'associatedCity',
			title : '周边城市',
			width : $(this).width() * 0.08,
			align : 'center',
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		var queryDateFrom =$("input[name='queryDateFrom']").val();
		var queryDateTo = $("input[name='queryDateTo']").val();
		$("#carWarnGrid").datagrid("load",{
			//'lpn':$("#s-lpn").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
			'queryDateFrom':queryDateFrom,
			'queryDateTo':queryDateTo
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		/*$("#warnForm").form("clear");*/
	});
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
		$("#associatedCity").combogrid("reset");
	 	$("#addView").dialog("open").dialog("setTitle", "添加版本记录");
	});
	$('#associatedCity').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
	 	delay:500,// 1s延时查询
	 	editable : true,
		panelHeight: 'auto', 
		panelWidth: 300,
		idField: 'code',
		textField: 'text',
		url:'all_city_list',
		pageSize:10,
		required:true,
		multiple:true,
		columns : [ [ {
			field : 'text',
			title : '城市列表',
			width : '80%',
			align : 'center'
		}] ],
		pagination : true,//是否分页
		rownumbers : true,//序号
		keyHandler : {
		query : function(text) { // 动态搜索处理
		$("#associatedCity").combogrid("grid").datagrid("options").queryParams = 
			JSON.parse('{"code":"' + code + '"}');
		// 重新加载
		$("#associatedCity").combogrid("grid").datagrid("reload");
	    $("#associatedCity").combogrid("setValue",text); 
			 
		}
	   }, 
	});
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
		$("#viewForm").form("clear");
      	var selectedRow = $("#grid").datagrid("getSelections");
      	//var JsonData = selectedRow[0];
      	//$("#associatedCity").combogrid("setValue",selectedRow[0].associatedCity);
      	var ids = selectedRow[0].associatedCity.split(",");
      	$("#associatedCity").combogrid("grid").datagrid('clearSelections');
      	//
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
		   	//var JsonData = selectedRow[0];
			$("#id").val(selectedRow[0].id);
			$("#cityCode").combobox("setValue",selectedRow[0].cityCode);
			for (var i=0;i<ids.length;i++){ 
				$("#associatedCity").combogrid("grid").datagrid("selectRecord",ids[i]); 
			};
			//$("#associatedCity").combobox("grid",JsonData.associatedCity);
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
				var selectedRows = $("#associatedCity").combogrid('getValues');
				$("#addForm").form("submit", {
					url : "add_wzcity_update.do?ids="+selectedRows,
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
						    $("#grid").datagrid("reload");
							$("#addView").dialog("close");
						}else if(result == "false"){
							$.messager.alert("提示", "中心城市没有开放", "info");
						    $("#grid").datagrid("reload");
							$("#addView").dialog("close");
						}else if(result == "defalt"){
							$.messager.alert("提示", "中心城市已经添加", "info");
						    $("#grid").datagrid("reload");
							$("#addView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#grid").datagrid("reload");
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
      		var selectedRow = $("#grid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除记录", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("del_wzCity_list",{"id":JsonData.id},function(data){
						if(data=="success"){
						    $("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});	
});