$(function(){

	$("#grid").datagrid( {
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		url : 'behaviorType_list',
		pageSize : 20,
		idField : 'id',
		pagination : true,
		rownumbers : true,
		columns : [ [ 
		{
			field : 'false',
			checkbox:true
		}, {
			field : 'behaviorName',
			title : '行为类型名称',
			width : $(this).width() * 0.2,
			align : 'center'
		},{
			field : 'behaviorType',
			title : '类型',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter : function(val) {
				var result = "普通";
				if(val == '1'){
					result = "道路救援";
				}else if(val == '2'){
					reuslt="严重";
				}
				return result;
			}
		},{
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.26,
			align : 'center',
			formatter : function(val) {
				return formatDate(val);
			}
		},{
			field : 'createName',
			title : '创建人',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{
			field : 'complain',
			title : '是否是举报项',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter : function(val) {
				var result = "不是";
				if(val == '1'){
					result = "是";
				}
				return result;
			}
		},{
			field : 'canAdd',
			title : '客服是否可添加',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter : function(val) {
				var result = "不是";
				if(val == '1'){
					result = "是";
				}
				return result;
			}
		}, {
			field : 'behaviorDetail',
			title : '行为描述',
			width : $(this).width() * 0.3,
			align : 'center'
		},{
			field : 'updateTime',
			title : '更改时间',
				width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val) {
				return formatDate(val);
			}
		},{
			field : 'updateName',
			title : '更改人',
			width : $(this).width() * 0.3,
			align : 'center'
		}] ]
	});
	
	//添加行为分类
	$("#addBehaviorType").bind("click",function(){
		$("#addBehaviorTypeForm").form("clear");
		$("#addBehaviorTypeView").dialog("open");
		$("#show_complain_type").hide();
	});
	

	//编辑行为分类
	$("#updateBehaviorType").bind("click",function(){
      	var selectedRow = $("#grid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
   			$("#addBehaviorTypeForm").form("clear")
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#behaviorTypeName").textbox("setValue",JsonData.behaviorName);
			$("#behaviorDetail").textbox("setValue",JsonData.behaviorDetail);
			$('#complain').combobox('setValue', JsonData.complain);
			if("1" == JsonData.complain ){
				$('#complain_type').combobox('setValue', JsonData.complainType);
				$("#show_complain_type").show();
			}else{
				$("#show_complain_type").hide();
			}
			$('#behaviorType').combobox('setValue', JsonData.behaviorType);
			$('#can_add').combobox('setValue', JsonData.canAdd);
			$("#addBehaviorTypeView").dialog("open").dialog("setTitle", "编辑记录");
  		}	     
	});
	$("#complain").combobox({onChange: function (n,o) {
		var complain = $("#complain").combobox("getValue");
		if(complain == "1"){
			$("#show_complain_type").show();
			$('#complain_type').combobox('setValue', "0");
		}else{
			$("#show_complain_type").hide();
		}
		}
	});

	$("#addBehaviorTypeView").dialog({
		width : "500",
		height : "380",
		top : "80",
		title:"添加行为分类",
		modal:true,
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addBehaviorTypeForm").form("submit", {
					url : "addOrUpdateBehaviorType",
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
						var json = JSON.parse(result);
						if(!json.status){
							$.messager.alert("提示", "网络繁忙", "info");
						}
						if (json.status == "success") {
							$.messager.alert("提示", "保存成功", "info");
							$("#grid").datagrid("reload");
							$("#addBehaviorTypeView").dialog("close");
						}else{
							$.messager.alert("提示", json.message, "info");
							$("#addBehaviorTypeView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#addBehaviorTypeView").dialog("close");
			}
		}]
	});
	

	//删除操作
	$("#deleteBehaviorType").bind("click",function(){
      		var selectedRow = $("#grid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除记录", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("delete_behaviorType",{"id":JsonData.id},function(data){
						var json = JSON.parse(data);
						if(!json.status){
							$.messager.alert("提示", "网络异常，删除失败", "info");
						}
						if(json.status=="success"){
						    $("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", json.message, "info");
						}
					},"text");
				}
			});
  		}	     
	});	
	
	
	
});


