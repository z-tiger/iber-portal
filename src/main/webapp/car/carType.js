$(function() {
	//table
	$('#carTypeGrid').datagrid({
		title : '车型管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_type_list',
		queryParams:{
			'type':$("#s-type").textbox("getValue"),
			'brance':$("#s-brance").textbox("getValue"),
			'typeName':$("#s-type_name").combobox("getValue")
		},
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'type',
			title : '车辆型号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.05,
			align : 'center'
		}, {
			field : 'typeName',
			title : '车辆类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "ELECTRIC") {return "电动"}
				if(val == "FUEL") {return "燃油"}
				if(val == "MIXEDPOWER") {return "混合动力"}
			}
		}, {
			field : 'brance',
			title : '车辆品牌',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'carriage',
			title : '车厢数量',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'seatNumber',
			title : '座位数量',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'maxSpeed',
			title : '最高速度',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'endurance',
			title : '续航里程',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val ==null || val == "null"){
					return "";
				}else{
					n = val.toFixed(0);
		   			var re = /(\d{1,3})(?=(\d{3}))/g;
	       			return n.replace(re, "$1,");
				}
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	//add view
	$("#addView").dialog( {
		width : "400",
		height : "380",
		top : "100",
		left:"450",
		modal:true,
		title:"添加车辆类型信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit",{
					url:'car_type_save',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carTypeGrid").datagrid("reload");
							$("#addView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#addView").dialog("close");
			}
		}]
	});
	
	//edit view
	$("#editView").dialog( {
		width : "400",
		height : "380",
		top : "100",
		left:"450",
		modal:true,
		title:"修改车辆类型信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit",{
					url:'car_type_edit',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carTypeGrid").datagrid("reload");
							$("#editView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#editView").dialog("close");
			}
		}]
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#carTypeGrid").datagrid("load",{
			'type':$("#s-type").textbox("getValue"),
			'brance':$("#s-brance").textbox("getValue"),
			'cityCode':$("#s-city_code").combobox("getValue"),
			'typeName':$("#s-type_name").combobox("getValue")
		});
	});
	
	//save
	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#addView").dialog("open");
	});
	
	//edit
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#carTypeGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-type").textbox("setValue",selectedRows[0].type);
			$("#e-typeName").combobox("setValue",selectedRows[0].typeName);
			$("#e-brance").textbox("setValue",selectedRows[0].brance);
			$("#e-carriage").textbox("setValue",selectedRows[0].carriage);
			$("#e-seatNumber").textbox("setValue",selectedRows[0].seatNumber);
			$("#e-endurance").textbox("setValue",selectedRows[0].endurance);
			$("#e-maxSpeed").textbox("setValue",selectedRows[0].maxSpeed);
			$("#e-cityCode").combobox('setValue',selectedRows[0].cityCode);
			$("#editView").dialog("open");
		 }
	});
	
	
	//del
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#carTypeGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("car_type_del",{id:_id},function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#carTypeGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
});