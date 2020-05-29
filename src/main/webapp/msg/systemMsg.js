$(function() {

	$('#systemMsgGrid')
	.datagrid(
			{
				title : '系统通知设置',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'system_msg_data',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [  {
					field : 'ck',
					checkbox : true

				}, {
					field : 'msgType',
					title : '类型',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'msgTitle',
					title : '标题',
					width : $(this).width() * 0.3,
					align : 'center'
				}, {
					field : 'msgContent',
					title : '内容',
					width : $(this).width() * 0.7,
					align : 'center'
				}] ],
				pagination : false,
				rownumbers : true
			});


	$("#btnAdd").bind("click",function(){
		 $("#systemMsgForm").form("clear");
		 $("#systemMsgView").dialog("open");
	});
	
	
	$("#btnRemove").bind("click",function(){
		var selectedRow = $("#systemMsgGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的内容", "error");
   		}else{
   			var _id = selectedRow[0].id;
   			$.messager.confirm("提示","确定要删除吗?",function(r){
      			if(r){
					$.post("system_msg_del",{"id":_id},function(data){
						if(data){
							if(data=="succ"){
								$.messager.alert("提示", "删除成功", "info");
								 $("#systemMsgGrid").datagrid("reload");
					    		$("#systemMsgGrid").datagrid("clearSelections");
							}else{
								$.messager.alert("提示", "删除失败", "error");
							}
      					}
					},"text");
				}
			});
   		}
	});
	
	
	$("#btnEdit").bind("click",function(){
		 $("#systemMsgForm").form("clear");
		var selectedRow = $("#systemMsgGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要修改的内容", "error");
   		}else{
   			var _id = selectedRow[0].id;
   			$("#id").val(_id);
   			$("#msgType").combobox("setValue",selectedRow[0].msgType);
   			$("#msgTitle").textbox("setValue",selectedRow[0].msgTitle);
   			$("#msgContent").textbox("setValue",selectedRow[0].msgContent);
   		   $("#systemMsgView").dialog("open");
   		}
	});
	
	
	$("#systemMsgView").dialog( {
		title:"系统通知设置",
		width : "400",
		height : "350",
		top : "40",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#systemMsgForm").form("submit", {
					url : "system_msg_save_update",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
						    $("#systemMsgGrid").datagrid("reload");
							$("#systemMsgView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else{
							   $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#systemMsgView").dialog("close");
			}
		} ]
	});
	
	
	

	});