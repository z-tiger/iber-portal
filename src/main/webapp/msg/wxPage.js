$(function(){
	
	$('#articleMsgGrid')
	.datagrid(
			{
				title : '图文消息设置',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'getAllArticle',
				queryParams:{
				},
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				},  {
					field : 'msgTitle',
					title : '标题',	
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'msgFirstP',
					title : '首图',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(value, row, index){
					   if(value != ""){
					      return "<img width='50' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: absolute;'><img width='300'  src='"+value+"'/></div>";
					   }
					}
					
				}, {
					field : 'msgUrl',
					title : '正文URL',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'createTimeStr',
					title : '发布时间',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'createUser',
					title : '发布人',
					width : $(this).width() * 0.08,
					align : 'center'
				}
				] ],
				pagination : true,
				rownumbers : true
			});

	$("#btnQuery").bind("click",function(){
		$("#articleMsgGrid").datagrid("load",{
			'title' : $("#s_title").textbox("getValue"),
		});
	});
	
	

	$("#clearQuery").bind("click",function(){
		$("#queryForm").form("clear");
	});
	
	$("#btnAdd").bind("click",function(){
		clearForm();
		$("#adPhoto").attr("src", "");
		 $("#addView").dialog("open");
	});
	
	//add view
	$("#addView").dialog( {
		width : "480",
		height : "610",
		top : "100",
		left:"400",
		modal:true,
		title:"添加车辆信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit",{
					url:'article_msg_save',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#articleMsgGrid").datagrid("reload");
							$("#addView").dialog("close");
							$("#addForm").form("clear");
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
				$("#addForm").form("clear");
			}
		}]
	});
	
	
	$("#btnRemove").bind("click",function(){
		var selectedRows = $("#articleMsgGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			 var _id =  selectedRows[0].id;
				$.messager.confirm("提示","确定要删除吗?",function(r){
					if(r){
						$.post("article_psg_del",{id:_id},function(data){
							if(data == "succ"){
								$.messager.alert("提示", "操作成功", "info");
								$("#articleMsgGrid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }
	});
	
	$("#btnEdit").bind("click",function(){
		$("#addForm").form("clear");
		var selectedRows = $("#articleMsgGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要修改的记录", "error");
		}else{
			$("#e_id").val(selectedRows[0].id);
			$("#e_msgTitle").textbox("setValue",selectedRows[0].msgTitle);
			$("#e_msgUrl").textbox("setValue",selectedRows[0].msgUrl);
			$("#adPhotoPreview").attr("src", selectedRows[0].msgFirstP);
			$("#editView").dialog("open");
		}
	});
	
	//edit view
	$("#editView").dialog( {
		width : "480",
		height : "610",
		top : "100",
		left:"400",
		modal:true,
		title:"修改微信文章信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit",{
					url:'article_mrg_edit',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#articleMsgGrid").datagrid("reload");
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
	function clearForm(){
		$('#addForm').form('clear');
	}
	
});