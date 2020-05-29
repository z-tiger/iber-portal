$(function() {
	$('#orderEvaluateGrid')
	.datagrid(
			{
				title : '用车订单评价',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'order_evaluate_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'evaluateType': $('#evaluateType').textbox('getValue'),
					'orderId': $('#orderId').textbox('getValue'),
					'name': $('#name').textbox('getValue'),
					'phone': $('#phone').textbox('getValue'),
					'evaluateStar': $('#evaluateStar').textbox('getValue')
				},
                columns: [[
                          {
           					field : 'orderId',
           					title : '订单号',
           					width : $(this).width() * 0.4,
           					align : 'center'
           				},{
        					field : 'evaluateType',
        					title : '类型',
        					width : $(this).width() * 0.1,
        					align : 'center',
    	    				formatter : function(val, rec) {
    	    					var textTemp = ""
    	    					if(val == "driving"){
    	    						textTemp = "分时租赁";
    	    					}else if(val == "dayRent"){
    	    						textTemp = "日租";
    	    					}else if(val == "charging"){
    	    						textTemp = "充电";
    	    					}
    	    					return textTemp;
    	    				}
        				},{
        					field : 'name',
        					title : '会员',
        					width : $(this).width() * 0.2,
        					align : 'center'
        				},{
        					field : 'phone',
        					title : '会员手机',
        					width : $(this).width() * 0.2,
        					align : 'center'
        				},{
        					field : 'evaluateStar',
        					title : '星级得分',
        					width : $(this).width() * 0.1,
        					align : 'center'
        				},{
        					field : 'evaluateContent',
        					title : '评价内容',
        					width : $(this).width() * 0.3,
        					align : 'center'
        				},{
        					field : 'label',
        					title : '评价标签',
        					width : $(this).width() * 0.3,
        					align : 'center'
        				},{
        					field : 'evaluateTime',
        					title : '评价时间',
        					width : $(this).width() * 0.2,
        					formatter : function(val, rec) {
        						return formatDate(val);
        					},
        					align : 'center'
        				},{
        					field : 'checkStatus',
        					title : '审核状态',
        					width : $(this).width() * 0.2,
        					align : 'center',
        					formatter : function(val, rec) {
        						if(val == "noPass") {
        							return "不通过" ;
        						}else{
        							return "通过" ;
        						}
        					}
        				},{
        					field : 'checkReason',
        					title : '审核内容',
        					width : $(this).width() * 0.2,
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
		
		if($('#evaluateType').length > 0) {
			$('#evaluateType').textbox('setValue', '');
		}
		
		if($('#orderId').length > 0) {
			$('#orderId').textbox('setValue', '');
		}
		
		if($('#name').length > 0) {
			$('#name').textbox('setValue', '');
		}
		if($('#phone').length > 0) {
			$('#phone').textbox('setValue', '');
		}
		if($('#evaluateStar').length > 0) {
			$('#evaluateStar').textbox('setValue', '');
		}
	});
	// 查询
	function search() {	
		$('#orderEvaluateGrid').datagrid('load', {
			'evaluateType': $('#evaluateType').textbox('getValue'),
			'orderId': $('#orderId').textbox('getValue'),
			'name': $('#name').textbox('getValue'),
			'phone': $('#phone').textbox('getValue'),
			'evaluateStar': $('#evaluateStar').textbox('getValue')
		});	
	}
	
	
	//add view
	$("#checkView").dialog( {
		width : "480",
		height : "250",
		top : "100",
		left:'400',
		modal:true,
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#viewForm").form("submit",{
					url:"order_evaluate_check",
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#orderEvaluateGrid").datagrid("reload");
							$("#checkView").dialog("close");
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
				$("#checkView").dialog("close");
			}
		}]
	});
	
	$("#btnCheck").bind("click", function() {
		$("#viewForm").form("clear");
		$("#checkView").dialog({title:"评价审核"});
		var selectedRows = $("#orderEvaluateGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-checkReason").textbox("setValue",selectedRows[0].checkReason);
			$("#e-checkStatus").combobox("setValue",selectedRows[0].checkStatus);
			$("#checkView").dialog("open");
		 }
	});
});
