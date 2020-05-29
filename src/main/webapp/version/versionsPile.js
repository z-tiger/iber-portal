$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : '充电桩版本管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'versions_pile_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				queryParams: {
					'categoryCode': $('#categoryCode').textbox('getValue')
				},
                columns: [[{
				       field : 'ck',
		               checkbox : true
			          },{
      					   field : 'categoryName',
    					   title : '版本类型',
    					   width : $(this).width() * 0.08,
    					  align : 'center'
    			     },
    			     {
      					   field : 'versionName',
    					   title : '版本名称',
    					   width : $(this).width() * 0.08,
    					  align : 'center'
    			     },{
      					   field : 'versionNo',
    					   title : '版本号',
    					   width : $(this).width() * 0.08,
    					  align : 'center'
    			     },{
      					   field : 'versionRecord',
    					   title : '版本记录数',
    					   width : $(this).width() * 0.06,
    					   align : 'center'
    			    },{
  					     field : 'remark',
					     title : '描述',
					     width : $(this).width() * 0.2,
					     align : 'center'
			     },{
   					   field : 'versionUrl',
					   title : '版本路径',
					   width : $(this).width() * 0.38,
					  align : 'center'
			     }
    			     /*,{
  					     field : 'isIncrement',
					     title : '是否增量',
					     width : $(this).width() * 0.2,
					     align : 'center',
     					formatter : function(val, rec) {
    						if(val == "null"){
    					       return "";
    						}else if(val == "0"){
    					       return "全量";
    					    }else if(val == "1"){
    					       return "增量";
    					    }else{
    							return val;
    						}
    					}
			     }*/
    			     ,{
   					      field : 'createName',
					      title : '创建人',
					      width : $(this).width() *0.06,
					      align : 'center'
			        },{
  					      field : 'createTime',
					      title : '创建时间',
					      width : $(this).width() * 0.08,
					      align : 'center',
      					  formatter : function(val, rec) {
    						return formatDate(val);
    					 }
			        },{
	  					   field : 'updateName',
						   title : '更新人',
						   width : $(this).width() * 0.06,
					       align : 'center'
				   },{
  					     field : 'updateTime',
					     title : '更新时间',
					     width : $(this).width() * 0.08,
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
		
		if($('#categoryCode').length > 0) {
			$('#categoryCode').textbox('setValue', '');
		}
	});
	
	
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
	 	$("#addView").dialog("open").dialog("setTitle", "添加版本记录");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#code").textbox("setValue",JsonData.categoryCode);
			if("1"==JsonData.categoryCode){
				$("#code").textbox("setText","直流");
			}
			if("2"==JsonData.categoryCode){
				$("#code").textbox("setText","交流");
			}
			if("3"==JsonData.categoryCode){
				$("#code").textbox("setText","交直流一体");
			}
			$("#versionName").textbox("setValue",JsonData.versionName);
			$("#versionUrl").textbox("setValue",JsonData.versionUrl);
			$("#versionNo").textbox("setValue",JsonData.versionNo);
			$("#versionRecord").textbox("setValue",JsonData.versionRecord);
			$("#remark").textbox("setValue",JsonData.remark);
			//$("#isIncrement").textbox("setValue",JsonData.isIncrement);
			$("#addView").dialog("open").dialog("setTitle", "编辑记录");
  		}	     
	});
	
	
	//构造对话框
	$("#addView").dialog( {
		width : "500",
		height : "400",
		top : "5",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "add_versions_pile",
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
			$.messager.alert("提示", "请选择要删除记录", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("versions_pile_del",{"id":JsonData.id},function(data){
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
			'categoryCode': $('#categoryCode').textbox('getValue')	
		});	
	}
	
	});
