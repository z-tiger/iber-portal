$(function(){
			$('#dataGrid').datagrid( {
				title : '会员等级管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dataListMemberLevel.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
					{
						field:'name',
						title:'等级名称',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'levelCode',
						title:'级别编码',
						align:'center',
						width : $(this).width() * 0.07,
					},
					{
						field:'integralDownlimit',
						title:'贡献值下限',
						align:'center',
						width : $(this).width() * 0.07,	
						formatter : function(val) {
							n = val.toFixed(0);
							var re = /(\d{1,3})(?=(\d{3}))/g;
							return n.replace(re, "$1,");
						}
					},
					{
						field:'integralUpLimit',
						title:'贡献值上限',
						align:'center',
						width : $(this).width() * 0.07,
						formatter:function(val,rec){
							if(val==0){
								return"无上限";
							}else{
								n = val.toFixed(0);
								var re = /(\d{1,3})(?=(\d{3}))/g;
								return n.replace(re, "$1,");
							}
						}
						
					},
					{
						field:'rightsName',
						title:'会员权益',
						align:'center',
						width : $(this).width() * 0.24,
					},
					 {
						field:'createTime',
						title:'创建时间',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'createName',
						title:'创建人',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'updateTime',
						title:'最后更新时间',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'updateName',
						title:'最后更新人',
						align:'center',
						width : $(this).width() * 0.1,
					}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			//添加会员等级
			$("#btnAdd").bind("click",function(){
				clearForm();
			 	$("#addView").dialog("open").dialog("setTitle", "添加会员等级");
			});
				//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			}); 
			//回车键查询
			$(document).keydown(function(event){
			   	var name = $("input[name='q_name']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        name:name,
		           	 
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var name = $("input[name='q_name']").val();
			    $('#dataGrid').datagrid('load',{
		           name:name,
		          
		        });
			});
				
						
				//修改信息
			$("#btnEdit").bind("click",function(){
				$("#addView").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要修改的记录", "error");
				}else{
					$("#id").val(selectedRows[0].id); 
						$("#name").textbox('setValue',selectedRows[0].name); 
						$("#levelCode").textbox('setValue',selectedRows[0].levelCode);
						$("#integralUpLimit").textbox('setValue',selectedRows[0].integralUpLimit);
						$("#integralDownlimit").textbox('setValue',selectedRows[0].integralDownlimit); 
						$("#addView").dialog({title:"修改信息"});
						$("#addView").dialog("open");
				}
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "400",
				height : "280",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addViewForm").form("submit", {
							url : "saveOrUpdateMemberLevel.do",
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
			$("#btnRemove").bind("click",function(){
		      		var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要删除的记录", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("deleteMemberLevelById.do",{"id":JsonData.id},function(data){
								if(data=="success"){
									$.messager.alert("提示", "删除成功", "info");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "删除失败", "info");
								}
							},"text");
						}
					});   
		  		}	     
			});
			
			$("#btnAddRights").bind("click",function(){
				$("#addRightsViewForm").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要分配权益的会员等级", "error");
				}else{
					$("#memberLevelId").val(selectedRows[0].id);
					$("#sp1").html(selectedRows[0].name);
					$("#addRightsView").dialog({title:"分配权益"});
					$("#addRightsView").dialog("open");
				}
			});
			//构造保存会员权益对话框
			$("#addRightsView").dialog({
				width : "360",
				height : "280",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addRightsViewForm").form("submit", {
							url : "saveMemberRightsInfo.do",
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
									$("#addRightsView").dialog("close");
									 $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "保存失败", "info");
								    $("#dataGrid").datagrid("reload");
									$("#addRightsView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#addRightsView").dialog("close");
					}
				}]
			});
			
		});
	    function clearForm(){
			$('#addViewForm').form('clear');
		}