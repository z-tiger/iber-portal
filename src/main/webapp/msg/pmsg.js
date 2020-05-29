$(function(){
	
	$('#pmsgGrid')
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
				url : 'protal_psg_list_data',
				queryParams:{
					'title':$("#s_title").textbox("getValue"),
					'status':$("#s-status").combobox("getValue"),
					'kssj':$("#s-kssj").datetimebox("getValue"),
					'jssj':$("#s-jssj").datetimebox("getValue")
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
					width : $(this).width() * 0.04,
					align : 'center'
				}, {
					field : 'createTimeStr',
					title : '发布时间',
					width : $(this).width() * 0.04,
					align : 'center'
				}, {
					field : 'createUser',
					title : '发布人',
					width : $(this).width() * 0.04,
					align : 'center'
				}, {
					field : 'clickRecords',
					title : '浏览数',
					width : $(this).width() * 0.04,
					align : 'center'
				}, {
					field : 'msgStatus',
					title : '审核状态',
					width : $(this).width() * 0.04,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "1"){
							return "待审核";
						}else if(val == "2"){
							return "通过";
						}else {
							return "不通过";
						}
					}
				}, {
					field : 'auditTime',
					title : '审核时间',
					width : $(this).width() * 0.04,
					align : 'center'
				}, {
					field : 'auditUser',
					title : '审核人员',
					width : $(this).width() * 0.04,
					align : 'center'
				}] ],
				pagination : true,
				rownumbers : true
			});

	$("#btnQuery").bind("click",function(){
		$("#pmsgGrid").datagrid("load",{
			'title' : $("#s_title").textbox("getValue"),
			'status' :	$("#s-status").combobox("getValue"),
			'kssj' :	$("#s-kssj").datetimebox("getValue"),
			'jssj' :	$("#s-jssj").datetimebox("getValue")
		});
	});
	//图片URL
	$(function(){
		$("#msgUrlRadioId").bind("click",function(){
		     $('#msgUrl').textbox({    
                 required:true,
                 editable:true
                }); 
//		     $('#msgContent').textbox({    
//                 required:false, 
//                 editable:false
//                }); 
		     $('#uploadFile').filebox({    
                 required:false,
                 disabled:true
		     });
		})
	});
	// 正文
	$(function(){
		$("#msgContentRadioId").bind("click",function(){
		     $('#msgContent').textbox({    
                 required:true,  
                 editable:true
                }); 
		     $('#msgUrl').textbox({    
                 required:false,
                 editable:false
                }); 
		     $('#uploadFile').filebox({    
                 required:false, 
                 disabled:true
                });
		})
	});
	// .zip文件
	$(function(){
		$("#uploadFileRadioId").bind("click",function(){
		     $('#uploadFile').filebox({    
                 required:true,  
                 disabled:false
                });
//		     $('#msgContent').textbox({    
//                 required:false, 
//                 editable:false
//                }); 
		     $('#msgUrl').textbox({    
                 required:false,   
                 editable:false
                }); 
		})
	});

	$("#clearQuery").bind("click",function(){
		$("#queryForm").form("clear");
	});
	
	$("#btnAdd").bind("click",function(){
		 $("#addForm").form("clear");
	     $('#msgContent').textbox({    
             required:false, 
             editable:false
            }); 
	     $('#uploadFile').filebox({    
             required:false, 
             disabled:true
            });
	    document.getElementById('msgUrlRadioId').checked = true;
	     $('#msgUrl').textbox({    
             required:true,
             editable:true
            });
		$("#addView").dialog("open").dialog("setTitle", "新增图文信息");
	});
	
	$("#addView").dialog( {
		title:"图文消息管理",
		width : "450",
		height : "220",
		top : "80",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "protal_psg_save_update",
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
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
						    $("#pmsgGrid").datagrid("reload");
							$("#addView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result == "fileFormatErr"){ 
							$.messager.alert("提示", "上传文件格式不正确,必须是zip包格式", "error");
						}else if(result == "picFormatErr"){ 
							$.messager.alert("提示", "上传图片格式不正确", "error");
						}
						else{
							   $.messager.alert("提示", "操作失败", "error");
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
		} ]
	});
	
	
	$("#btnRemove").bind("click",function(){
		var selectedRows = $("#pmsgGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			 var _id =  selectedRows[0].id;
				$.messager.confirm("提示","确定要删除吗?",function(r){
					if(r){
						$.post("protal_psg_del",{id:_id},function(data){
							if(data == "succ"){
								$.messager.alert("提示", "操作成功", "info");
								$("#pmsgGrid").datagrid("reload");
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
		document.getElementById('msgUrlRadioId').checked = true;
		var selectedRows = $("#pmsgGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要修改的记录", "error");
		}else{
			$("#id").val(selectedRows[0].id);
			$("#msgTitle").textbox("setValue",selectedRows[0].msgTitle);
			$("#msgContent").textbox("setValue",selectedRows[0].msgContent);
			$("#msgUrl").textbox("setValue",selectedRows[0].msgUrl);
			$("#addView").dialog("open").dialog("setTitle", "修改图文信息");
		}
	});
	
	
	$("#btnExamine").bind("click",function(){
		var selectedRows = $("#pmsgGrid").datagrid("getSelections");
		 $("#ExamineFrom").form("clear");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要审核的记录", "error");
		}else{
			$("#ex-id").val(selectedRows[0].id);
			$("#ex-msgTitle").html(selectedRows[0].msgTitle);
			$("#ex-msgContent").html(selectedRows[0].msgContent);
			//$("#ex-msgFirstP").html(selectedRows[0].msgFirstP);
			$("#ex-msgFirstP").attr("src", selectedRows[0].msgFirstP);
			//$("#ex-msgUrl").html(selectedRows[0].msgUrl);
			$("#ex-msgUrl").attr("href", selectedRows[0].msgUrl);
			$("#ExamineView").dialog("open");
		}
	});
	
	
	$("#ExamineView").dialog( {
		title:"图文消息审核",
		width : "450",
		height : "680",
		top : "80",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#ExamineFrom").form("submit", {
					url : "protal_psg_ex",
					onSubmit : function() {
						$.messager.progress({ 
		                    text:"正在加载，请稍等！"
		                });
						var flag = $(this).form("validate");
		                if(!flag){
		                	$.messager.progress('close'); 
		                }
						return flag;
				//		return $(this).form("validate");
					},
					success : function(result) {
						$.messager.progress('close');
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
						    $("#pmsgGrid").datagrid("reload");
							$("#ExamineView").dialog("close");
						} else if("statusErr"){
							$.messager.alert("提示", "请选择审核结果", "error");
						}
						else if(result == "fail") {
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
				$("#ExamineView").dialog("close");
			}
		} ]
	});
	
	
});