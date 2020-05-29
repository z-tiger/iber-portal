$(function() {
	$('#dataGrid')
	.datagrid(
			{
				title : 'APP版本管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'app_version_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
                columns: [[{
				       field : 'ck',
		               checkbox : true
			          },{
      					   field : 'appName',
    					   title : '版本名称',
    					   width : $(this).width() * 0.1,
    					   align : 'center'
    			     },{
      					   field : 'appType',
    					   title : '版本类型',
    					   width : $(this).width() * 0.1,
    					  align : 'center'
    			     },{
      					   field : 'currentRecord',
    					   title : '版本记录数',
    					   width : $(this).width() * 0.1,
    					  align : 'center'
    			     },{
      					   field : 'versionNo',
    					   title : '版本号',
    					   width : $(this).width() * 0.1,
    					  align : 'center'
    			     },{
      					   field : 'appSize',
    					   title : '版本大小(KB)',
    					   width : $(this).width() * 0.1,
    					  align : 'center'
    			     },
    			     {
      					   field : 'isForceUpdate',
    					   title : '是否强制升级',
    					   width : $(this).width() * 0.1,
    					   align : 'center',
    					   formatter : function(val, rec) {
    						   if(val == "F"){
    							   return "否";
    						   }else if(val == "T"){
    							   return "是";
    						   }
      					   }
    			    },
    			    {
  					      field : 'publishDate',
					      title : '创建时间',
					      width : $(this).width() * 0.2,
					      align : 'center',
      					  formatter : function(val, rec) {
      						 var dt = new Date(val) ;
    						 return dt.getFullYear() +"-" + (dt.getMonth()+1) +"-"+dt.getDate()+" "+dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
    					  }
			        },{
  					     field : 'appDesc',
					     title : '描述',
					     width : $(this).width() * 0.2,
					     align : 'center'
			     },{
   					   field : 'downloadUrl',
					   title : '版本路径',
					   width : $(this).width() * 0.5,
					   align : 'center'
			     }
			     ]],
				pagination : true,
				rownumbers : true
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
	 	$("#addView").dialog("open").dialog("setTitle", "发布新版本");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
		   	console.log(JsonData);
			$("#id").val(JsonData.id);
			$("#_appType").combobox("setValue",JsonData.appType);
			$("#appName").textbox("setValue",JsonData.appName);
			$("#versionNo").textbox("setValue",JsonData.versionNo);
			$("#isForceUpdate").textbox("setValue",JsonData.isForceUpdate);
			$("#currentRecord").textbox("setValue",JsonData.currentRecord);
			$("#appDesc").textbox("setValue",JsonData.appDesc);
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
					url : "add_app_versions",
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
					$.post("del_app_versions",{"id":JsonData.id},function(data){
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
	
});
 
function getLocalTime(nS) {       
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");        
 }    