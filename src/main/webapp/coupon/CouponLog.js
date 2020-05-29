$(function() {
			$("#sendEdit").bind("click",function(){
				var selectedRow = $("#dataGrid").datagrid("getSelections");
				if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要重发的记录", "error");
		   		}else{
					var JsonData = selectedRow[0];
					if(JsonData.status == 1){
						$.messager.alert("提示", "请选择失败的记录", "error");
						return ;
					}
					$.messager.confirm("提示","确定要需要重发吗?",function(r){
			      		if(r){
			      			$.messager.progress({
			                    text:"正在加载，请稍等！"
			                });
							$.post("reSendCoupon",
 									{
								
										"id" : JsonData.id,
										"memberPhone" : JsonData.memberPhone,
										"batchno":JsonData.batchno,
										"couponno":JsonData.couponno,
										"couponNum":JsonData.couponNum
									},
							function(data){
								$.messager.progress('close'); 
								data = eval("("+data+")");
								if(data.status=="success"){
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", data.msg, "info");
								}
							},"text");
						}
					});
		   		}
			});
			//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
			//查询链接
			$(document).keydown(function(event){
			    var status = $("#e-status").combobox("getValue");
			    var batchNo = $("#_batchno").val();
			    var memberName = $("#_memberName").textbox("getValue");
			    var memberPhone = $("#_memberPhone").textbox("getValue");
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
					   status:status,
					   memberName:memberName,
					   memberPhone:memberPhone,
					   batchNo:batchNo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var status = $("#e-status").combobox("getValue");
				var batchNo = $("#_batchno").val();
				var memberName = $("#_memberName").textbox("getValue");
				var memberPhone = $("#_memberPhone").textbox("getValue");
			    $('#dataGrid').datagrid('load',{
				   status:status,
				   memberName:memberName,
				   memberPhone:memberPhone,
				   batchNo:batchNo
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '优惠券发放记录',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dataListCouponLog.do',
				queryParams :{
					   status:$("#e-status").combobox("getValue"),
					   memberName:$("#_memberName").textbox("getValue"),
					   memberPhone:$("#_memberPhone").textbox("getValue"),
					   batchNo:$("#_batchno").val()
				},
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
					{
						field:'memberName',
						title:'会员姓名',
						width : $(this).width() * 0.1,
						align:'center'
					},
					{
						field:'memberPhone',
						title:'会员手机号',
						width : $(this).width() * 0.1,
						align:'center'
					},
					{
						field:'batchno',
						title:'批次号',
						align:'center',
						width : $(this).width() * 0.2,
						formatter:function(value,row,index){
								return row.batchno;
						}
					},
					{
						field:'couponno',
						title:'优惠券编号',
						align:'center',
						width : $(this).width() * 0.1,
					},{
						field:'couponNum',
						title:'数量',
						align:'center',
						width : $(this).width() * 0.1,
						formatter:function(value,row,index){
							if(value == null ||value =="" ||value == 0){
								value = 1;
							}
							return value;
						}
					},
					{
						field:'status',
						title:'状态',
						align:'center',
						width : $(this).width() * 0.1,
						formatter:function(val,rec){
								if(val==0){
									return "失败"
								}else{
									return "成功"
								}
						}
					},
					
					{
						field:'createName',
						title:'创建人',
						align:'center',
						width : $(this).width() * 0.1,	
					},
					{
						field:'createtime',
						title:'创建时间',
						align:'center',
						width : $(this).width() * 0.2,
					}
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
					url : "saveOrUpdateCouponLog.do",
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
				$("#couponno").textbox('setValue',selectedRows[0].couponno); 
				$("#memberid").textbox('setValue',selectedRows[0].memberid); 
				$("#status").textbox('setValue',selectedRows[0].status); 
				$("#batchno").textbox('setValue',selectedRows[0].batchno); 
				$("#createid").textbox('setValue',selectedRows[0].createid); 
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
					$.post("deleteCouponLogById.do",{"id":JsonData.id},function(data){
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
	
	//清空Combogrid
	$("#clearCombogrid").bind("click",function(){
		$("#model_id").combogrid("clear");
	});
			
});
		function clearQueryForm(){
			$('#toolbar').form('clear');
		}
	
		