$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '评价标签配置',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'order_evaluate_label_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'type': $('#type').textbox('getValue'),
					'star': $('#star').textbox('getValue'),
					'label': $('#label').textbox('getValue')
				},
                columns: [[{
				       field : 'ck',
		               checkbox : true
			          },{
        					field : 'type',
        					title : '类型',
        					width : $(this).width() * 0.1,
        					align : 'center',
    	    				formatter : function(val, rec) {
    	    					var textTemp = ""
    	    					if(val == "driving"){
    	    						textTemp = "分时租赁";
    	    					}else if(val == "dayRent") {
    	    						textTemp = "日租";
    	    					}else if(val == "charging") {
    	    						textTemp = "充电";
    	    					}
    	    					return textTemp;
    	    				}
        				},{
        					field : 'star',
        					title : '星级',
        					width : $(this).width() * 0.1,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == "null"){
        					       return "";
        						}else if(val == "1"){
        					       return "一星";
        					    }else if(val == "2"){
        					       return "二星";
        					    }else if(val == "3"){
        					       return "三星";
        					    }else if(val == "4"){
        					       return "四星";
        					    }else if(val == "5"){
        					       return "五星";
        						}else{
        							return val;
        						}
        					}
        				},{
        					field : 'label',
        					title : '评价标签',
        					width : $(this).width() * 0.3,
        					align : 'center'
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
		
		if($('#type').length > 0) {
			$('#type').textbox('setValue', '');
		}
		
		if($('#star').length > 0) {
			$('#star').textbox('setValue', '');
		}
		
		if($('#label').length > 0) {
			$('#label').textbox('setValue', '');
		}
	});
	
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
	 	$("#addView").dialog("open").dialog("setTitle", "添加标签");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的标签", "error");
   		}else{
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#modLabel").textbox("setValue",JsonData.label);
			$("#modStar").combobox("setValue",JsonData.star);
			$("#modType").combobox("setValue",JsonData.type);
			$("#addView").dialog("open").dialog("setTitle", "编辑标签");
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
					url : "add_evaluate_label",
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
			$.messager.alert("提示", "请选择要删除标签", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("order_evaluate_label_del",{"id":JsonData.id},function(data){
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
	
	
	
	// 查询
	function search() {	
		$('#dataGrid').datagrid('load', {
			'type': $('#type').textbox('getValue'),
			'star': $('#star').textbox('getValue'),
			'label': $('#label').textbox('getValue')
		});	
	}
	
	});
