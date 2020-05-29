$(function() {

	$('#sharContentGrid')
	.datagrid(
			{
				title : '分享内容设置',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'share_content_data',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				},  {
					field : 'shareModuName',
					title : '分享入口',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'shareTitle',
					title : '标题',
					width : $(this).width() * 0.3,
					align : 'center'
				}, {
					field : 'shareContent',
					title : '内容',
					width : $(this).width() * 0.3,
					align : 'center'
				}, {
					field : 'shareUrl',
					title : 'URL',
					width : $(this).width() * 0.3,
					align : 'center'
				}] ],
				pagination : false,
				rownumbers : true
			});


	$("#btnAdd").bind("click",function(){
		 $("#sharcontentForm").form("clear");
		 $("#sharcontentView").dialog("open");
	});
	
	
	$("#btnRemove").bind("click",function(){
		var selectedRow = $("#sharContentGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的分享内容", "error");
   		}else{
   			var _id = selectedRow[0].id;
   			$.messager.confirm("提示","确定要删除吗?",function(r){
      			if(r){
					$.post("share_content_del",{"id":_id},function(data){
						if(data){
							if(data=="succ"){
								$.messager.alert("提示", "删除成功", "info");
								 $("#sharContentGrid").datagrid("reload");
					    		$("#sharContentGrid").datagrid("clearSelections");
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
		 $("#sharcontentForm").form("clear");
		var selectedRow = $("#sharContentGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要修改的分享内容", "error");
   		}else{
   			var _id = selectedRow[0].id;
   			$("#id").val(_id);
   			$("#shareModu").combobox("setValue",selectedRow[0].shareModu);
   			$("#shareTitle").textbox("setValue",selectedRow[0].shareTitle);
   			$("#shareUrl").textbox("setValue",selectedRow[0].shareUrl);
   			$("#shareContent").textbox("setValue",selectedRow[0].shareContent);
   		   $("#sharcontentView").dialog("open");
   		}
	});
	
	
	$("#sharcontentView").dialog( {
		title:"分享内容设置",
		width : "400",
		height : "350",
		top : "40",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#sharcontentForm").form("submit", {
					url : "share_content_save_update",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
						    $("#sharContentGrid").datagrid("reload");
							$("#sharcontentView").dialog("close");
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
				$("#sharcontentView").dialog("close");
			}
		} ]
	});
	
	
	

	});