$(function(){
	//清空
	$("#clearQuery").bind("click",function(){
		$('#toolbar').form('clear');
	});
	$("#btnQuery").bind("click", function(){
		$("#grid").datagrid("clearSelections");
		$('#grid').datagrid("load", {
			'levelCode':$("#s-levelCode").combobox("getValue"),
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'carTypeId':$("#s-carTypeId").combobox("getValue")
		});
	});
	
	$('#grid').datagrid({
		title : '超长订单参数设置',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		pagination : true,
		rownumbers : true,
		/*onLoadSuccess : computeSum,*/
		url : 'longOrder_list',
		queryParams:{
			'levelCode':$("#s-levelCode").combobox("getValue"),
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'carTypeId':$("#s-carTypeId").combobox("getValue")
		},
		pageSize : 30,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'cityName',
			title : '城市',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'levelName',
			title : '会员等级',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'carTypeName',
			title : '车型',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'budgetAmount',
			title : '订单金额阀值(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter:function(val){
				var n = (val/100).toFixed(2);
				if(!isNaN(n)){
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
				}else{
					return "";
				}
			}
		}, {
			field : 'budgetTime',
			title : '租车时限(小时)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter:function(val){
				var n = (val).toFixed(2);
				if(!isNaN(n)){
					var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					return n.replace(re, "$1,");	
				}else{
					return "";
				}
			}
				
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'createName',
			title : '创建人',
			width : $(this).width() * 0.1,
			align : 'center'
		}
		]],
	});
	
	$("#btnSave").bind("click",function(){
		clearAll();
		$("#editView").dialog({title:"超长订单参数设置"});
		$("#editView").dialog("open");	
	});
	
	var clearAll = function(){
		$("#editForm").form('clear');
	}
	
	$("#btnEdit").bind("click",function(){
		clearAll();
		var selectedRows = $("#grid").datagrid("getSelections");
		console.log(selectedRows);
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-cityCode").combobox("setValue",selectedRows[0].cityCode);
			$("#e-levelCode").combobox("setValue",selectedRows[0].levelCode);
			$("#e-carTypeId").combobox("setValue",selectedRows[0].carTypeId);
			$("#e-moneyLimit").textbox("setValue",(selectedRows[0].budgetAmount/100).toFixed(2));
			$("#e-timeLimit").textbox("setValue",selectedRows[0].budgetTime);
			$("#editView").dialog({title:"超长订单参数设置"});
			$("#editView").dialog("open");		
		}
	});
	
	
	
	$("#editView").dialog( {
		width : "450",
		height : "400",
		top : "100",
		modal:true,
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$.messager.progress({
					text:"正在加载，请稍等！"
				});
				var flag = $("#editForm").form("validate");
				if(!flag){
					$.messager.progress('close'); 
					return;
				}
				//return flag;
				
				$.ajax({
					type : "post",
					url : "saveOrUpdateLongOrderParam",
					data : {
						id : $("#e-id").val(),
						cityCode:$("#e-cityCode").combobox("getValue"),
						levelCode:$("#e-levelCode").combobox("getValue"),
						carTypeId:$("#e-carTypeId").combobox("getValue"),
						budgetAmount:$("#e-moneyLimit").textbox("getValue"),
						budgetTime:$("#e-timeLimit").textbox("getValue")
					},
					success : function(data) {
						$.messager.progress('close');
						//var data = eval("("+result+")");
						if (data.status == "success") {
							$.messager.alert("提示", data.msg, "info");
							$("#editView").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "info");
						} 
					}
					
				});
				
				
//				$("#editForm").form("submit", {
//					url : "saveOrUpdateLongOrderParam",
//					onSubmit : function() {
//					},
//					success : function(result) {
//						$.messager.progress('close');
//						var data = eval("("+result+")");
//						if (data.status == "success") {
//							$.messager.alert("提示", data.msg, "info");
//							$("#editView").dialog("close");
//							$("#grid").datagrid("reload");
//						}else{
//							$.messager.alert("提示", data.msg, "info");
//						} 
//					}
//				});
				
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