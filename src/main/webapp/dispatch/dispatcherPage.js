$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '调度员管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dispatcher_page_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'memberName': $('#memberName').textbox('getValue')
					
				},
                columns: [[{
				       field : 'ck',
		               checkbox : true
			          },{
	   					  field : 'memberId',
	   					  title : '会员ID',
	   					  width : $(this).width() * 0.05,
	   					  align : 'center'
	   				   },{
	   					  field : 'memberName',
	   					  title : '会员名称',
	   					  width : $(this).width() * 0.05,
	   					  align : 'center'
	   				   },{
	   					  field : 'isEnable',
	   					  title : '是否有效',
	   					  width : $(this).width() * 0.01,
	   					  align : 'center',
						  formatter : function(val, rec) {
							if(val == "0"){
						       return "失效";
							  }else if (val == "1"){
						       return "有效";
						     }else {
						       return " ";
						     }
						   }
	   				   },{
		   					  field : 'comment',
		   					  title : '备注',
		   					  width : $(this).width() * 0.05,
		   					  align : 'center'
		   			   },{
							  field : 'createTime',
							  title : '创建时间',
							  width : $(this).width() * 0.02,
							  align : 'center',
							  formatter : function(val, rec) {
								return formatDate(val);
							  }
						   }
                       ]],
				pagination : true,
				rownumbers : true
			});
	// 查询
	$("#btnQuery").bind("click", function() {
		search();
	});
	// 绑定enter建查询
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});
	// 清空
	$("#btnClear").bind("click", function() {
		if($('#memberName').length > 0) {
			$('#memberName').textbox('setValue', '');
		}
	
	});
	
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
	 	$("#addView").dialog("open").dialog("setTitle", "添加调度员");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#memberId").combobox("setValue",JsonData.memberId);
			$("#isEnable").combobox("setValue",JsonData.isEnable);
			$("#comment").textbox("setValue",JsonData.comment);
			$("#addView").dialog("open").dialog("setTitle", "修改调度员页面");
  		}	     
	});
	
	
	//构造对话框
	$("#addView").dialog( {
		width : "500",
		height : "400",
		top : "40",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "add_dispatcher",
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
	
	
	// 查询
	function search() {	
		$('#dataGrid').datagrid('load', {
			'memberName': $('#memberName').textbox('getValue')
		});	
	}
	
	});
