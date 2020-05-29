$(function() {
	function clearToolbar(){
		$('#toolbar').form('clear');
		$("#dataGrid").datagrid("load",{});
	}
//清空
$("#clearQuery").bind("click",function(){
	clearToolbar();
});
	$("#btnQuery").bind("click",function(){
		$("#dataGrid").datagrid("load",{
			'driverAge':$("#driverAge").combobox("getValue"),
			'sesameCredit':$("#sesameCredit").combobox("getValue"),
			'memberLevel':$("#memberLevel").combobox("getValue")
		});
	});
	
	$('#dataGrid').datagrid({
		title : '押金管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		url : 'deposit_list',
		queryParams:{
			'driverAge':$("#driverAge").combobox("getValue"),
			'sesameCredit':$("#sesameCredit").combobox("getValue"),
			'memberLevel':$("#memberLevel").combobox("getValue")
		},
		pageSize : 100,
		pageList : [ 100, 50, 30, 10 ],
		columns : [ [ {
			field : 'id',
			checkbox : true
		}, {
			field : 'driverAge',
			title : '驾驶年龄',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				if(val=="1"){
					return "少于一年（0天≤驾龄＜365天";
				}else if(val=="2"){
					return "大于等于一年（驾龄≥365天）";
				}else if(val == "3"){
					return "与驾龄无关";
				} 
			}
		}, {
			field : 'sesameCredit',
			title : '芝麻信用',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val,rec) {
				if(val=="0"){
					return "与芝麻信用无关";
				}else if(val=="1"){
					return "<700";
				}else if(val=="2"){
					return ">=700";
				}
			}
		}, {
			field : 'memberLevel',
			title : '会员等级',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				if(val=="1"){
					return "1星会员";
				}else if(val=="2"){
					return "2星会员";
				}else if(val=="3"){
					return "3星会员";
				}else if(val=="4"){
					return "4星会员";
				}else if(val=="5"){
					return "5星会员";
				}
			}
		}, {
			field : 'depositValue',
			title : '押金值',
			width : $(this).width() * 0.4,
			align : 'center',
			formatter : function(val, rec) {
				if(val!=null){
					return "<font color='red'>￥"+val/100+"</font>";
				}
			}
		}, {
			field : 'createName',
			title : '创建人',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'updateTime',
			title : '更新时间',
			width : $(this).width() * 0.4,
			align : 'center'
		}, {
			field : 'updateName',
			title : '更新人',
			width : $(this).width() * 0.2,
			align : 'center'
		} ] ],
		pagination : true,
		rownumbers : true
	});
     
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		$("#addForm").form("clear");
	 	$("#addView").dialog("open").dialog("setTitle", "添加记录");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#driverAge").combobox("setValue",JsonData.driverAge);
			$("#sesameCredit").combobox("setValue",JsonData.sesameCredit);
			$("#memberLevel").combobox("setValue",JsonData.memberLevel);
			$("#depositValue").textbox("setValue",JsonData.depositValue);
			$("#detail").textbox("setValue",JsonData.detail);
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
					url : "deposit_add_update",
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
					$.post("delete_deposit",{"id":JsonData.id},function(data){
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

})