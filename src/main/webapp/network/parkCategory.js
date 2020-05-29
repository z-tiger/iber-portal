$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var title = $("input[name='_title']").val();
			    
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           title : title
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var title = $("input[name='_title']").val();
			    
			    $('#dataGrid').datagrid('load',{
		    	   title : title
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '网点类型管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dataListXParkCategory.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
					{field:'name',title:'网点类型名称',align:'center',width : $(this).width() * 0.2,
							formatter:function(value,row,index){
								return row.name;
							}
						},
					{field:'remark',title:'网点描述',align:'left',width : $(this).width() * 0.7,
							formatter:function(value,row,index){
								return row.remark;
							}
						},
				] ],
				pagination : true,
				rownumbers : true
			}); 
			
	//构造对话框
	$("#view").dialog( {
		width : "400",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#viewForm").form("submit", {
					url : "saveOrUpdateXParkCategory.do",
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
							$("#view").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#view").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#view").dialog("close");
			}
		}]
	});
			
	//添加
	$("#btnAdd").bind("click",function(){
		$("#viewForm").form("clear");
		$("#view").dialog({title:"添加信息"});
		$("#view").dialog("open");
	});
			
	//edit
	$("#btnEdit").bind("click",function(){
		$("#viewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			$("#id").val(selectedRows[0].id); 
																			$("#name").textbox('setValue',selectedRows[0].name); 
																$("#remark").textbox('setValue',selectedRows[0].remark); 
										$("#view").dialog({title:"修改信息"});
			$("#view").dialog("open");
		}
	});
	
	//删除操作
	$("#btnRemove").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的策略", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("deleteXParkCategoryById.do",{"id":JsonData.id},function(data){
						if(data=="success"){
							//$.messager.alert("提示", "删除成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
			
	//清空
	$("#clearQuery").bind("click",function(){
		 
	});
});
		