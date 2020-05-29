var _actionUrl;

$(function() {
	//table
	$('#carGroupGrid').datagrid({
		title : '车辆组管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_group_list',
		queryParams:{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '城市',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'name',
			title : '组名称',
			width : $(this).width() * 0.09,
			align : 'center'
		}, {
			field : 'carNumber',
			title : '车辆数',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val,row) {
				if(val == "null")
			       return "0";
			    else
			       return "<a href=\"javascript:showCar("+row.id+")\" >"+val+"</a>";
			}
		}, {
			field : 'fenceNumber',
			title : '电子围栏数',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val,row) {
				if(val == "null")
			       return "0";
			    else
			       return "<a href=\"javascript:showFence("+row.id+")\" >"+val+"</a>";
			}
		},{
			     field : 'describe',
			     title : '描述',
			     width : $(this).width() * 0.12,
			     align : 'center'
	     },{
				  field : 'createName',
			      title : '创建人',
			      width : $(this).width() * 0.04,
			      align : 'center'
	        },{
				  field : 'createTime',
			      title : '创建时间',
			      width : $(this).width() * 0.04,
			      align : 'center'
	        },{
				   field : 'updateName',
				   title : '更新人',
				   width : $(this).width() * 0.04,
			       align : 'center'
		   },{
				 field : 'updateTime',
			     title : '更新时间',
			     width : $(this).width() * 0.04,
			     align : 'center'
	     }
		
		] ],
		pagination : true,
		rownumbers : true
	});
	
	//add view
	$("#view").dialog( {
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
					url:_actionUrl,
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carGroupGrid").datagrid("reload");
							$("#view").dialog("close");
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
				$("#view").dialog("close");
			}
		}]
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#carGroupGrid").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		/*$('#sCarGroupForm').form('clear');*/
	});
	
	//save
	$("#btnSave").bind("click",function(){
		$("#viewForm").form("clear");
		$("#view").dialog({title:"添加车辆组信息"});
		_actionUrl = "car_group_save";
		$("#view").dialog("open");
	});
	
	//edit
	$("#btnEdit").bind("click",function(){
		$("#viewForm").form("clear");
		$("#view").dialog({title:"修改车辆组信息"});
		_actionUrl = "car_group_edit";
		var selectedRows = $("#carGroupGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-name").textbox("setValue",selectedRows[0].name);
			$("#e-describe").textbox("setValue",selectedRows[0].describe);
			$("#e-cityCode").combobox("setValue",selectedRows[0].cityCode);
			$("#view").dialog("open");
		 }
	});
	
	
	//del
	$("#btnDelete").bind("click",function(){
		 var selectedRows = $("#carGroupGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("car_group_del",{id:_id},function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#carGroupGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	  //构造对话框
	$("#showCarView").dialog( {
		width : "800",
		height : "600",
		top : "80"
	});
	
	//构造对话框
	$("#addCarView").dialog( {
		width : "350",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addCarForm").form("submit", {
					url : "car_group_add_car",
					success : function(result) {
						$.messager.progress('close'); 
						if (result == "succ") {
							$.messager.alert("提示", "保存成功", "info");
						    $("#showCarGrid").datagrid("reload");
						    $("#carGroupGrid").datagrid("reload");
							$("#addCarView").dialog("close");
						}else if(result == "no"){
							$.messager.alert("提示", "车辆不存在", "info");
						} else {
							$.messager.alert("提示", "保存失败", "info");
						    $("#showCarGrid").datagrid("reload");
							$("#addCarView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#addCarView").dialog("close");
			}
		}]
	});
	
	$("#btnAdd").bind("click",function(){
		$("#lpn").textbox("setValue","");
		$("#addCarView").dialog("open").dialog("setTitle", "添加车辆");
		
	});
	
	
	
	$("#btnRemove").bind("click",function(){
		 var selectedRows = $("#showCarGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要移除的记录", "error");
		 }else{
			 var tmpArr = new Array();
			 for(var i=0; i<selectedRows.length; i++){
				 tmpArr[i] = selectedRows[i].id;
			 }
			$.messager.confirm("提示","确定要移除吗?",function(r){
				if(r){
					$.post("car_group_relation_del?ids="+tmpArr,function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#showCarGrid").datagrid("reload");
							$("#carGroupGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
		
	});
	
	
	//构造对话框
	$("#fenceView").dialog( {
		width : "480",
		height : "180",
		top : "100",
		left:'400',
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#fenceViewForm").form("submit", {
					url : "car_group_add_fence",
					success : function(result) {
						$.messager.progress('close'); 
						if (result == "succ") {
							$.messager.alert("提示", "保存成功", "info");
						    $("#carGroupGrid").datagrid("reload");
							$("#fenceView").dialog("close");
						}else if(result == "exist"){
							$.messager.alert("提示", "已经存在此电子围栏", "info");
						}else {
							$.messager.alert("提示", "保存失败", "info");
						    $("#carGroupGrid").datagrid("reload");
							$("#fenceView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#fenceView").dialog("close");
			}
		}]
	});
	
	$("#setFence").bind("click",function(){
		$("#fenceViewForm").form("clear");
		$("#fenceView").dialog({title:"设置电子围栏信息"});
		var selectedRows = $("#carGroupGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else{
			$("#g-id").val(selectedRows[0].id); 
			$("#g-category").val("group"); 
			//根据组区域获取电子围栏
			var _url = 'electronic_fence_city_code?cityCode='+selectedRows[0].cityCode; 
			$('#g-fence').combobox('reload', _url); 
			$("#fenceView").dialog("open");
		 }
	});
	
	  //构造对话框
	$("#showFenceView").dialog( {
		width : "800",
		height : "600",
		top : "80"
	});
	
	$("#btnRemoveFence").bind("click",function(){
		 var selectedRows = $("#showFenceGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要移除的记录", "error");
		 }else{
			 var tmpArr = new Array();
			 for(var i=0; i<selectedRows.length; i++){
				 tmpArr[i] = selectedRows[i].id;
			 }
			$.messager.confirm("提示","确定要移除吗?",function(r){
				if(r){
					$.post("fence_group_relation_del?ids="+tmpArr,function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#carGroupGrid").datagrid("reload");
							$("#showFenceGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 } 
	});
	
});

function showFence(groupId){
	$("#showFenceView").dialog("open").dialog("setTitle", "电子围栏列表");
	var url='fence_group_list?groupId='+groupId;
	$('#showFenceGrid').datagrid( {
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		url : url,
		pageSize:10,
		columns : [ [  {
			field : 'ck',
			checkbox : true

		}, {
			field : 'fenceName',
			title : '电子围栏名称',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
}

function showCar(groupId){
	$("#showCarView").dialog("open").dialog("setTitle", "车辆列表");
	$("#groupId").val(groupId);
	var url='car_group_lpn?groupId='+groupId;
	$('#showCarGrid').datagrid( {
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		url : url,
		pageSize:10,
		columns : [ [  {
			field : 'ck',
			checkbox : true

		}, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
}