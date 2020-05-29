$(function() {
	// 查询链接
	$("#btnQuery").bind("click", function() {
		$('#dataGrid').datagrid('load', {
			'status':$("#c-status").combobox("getValue"),
			'itemType':$("#c-itemType").combobox("getValue"),
			'appType':$("#c-appType").combobox("getValue")
		});
	});

	$('#dataGrid').datagrid({
		title : '车辆自检项配置',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		queryParams: {
			'status':$("#c-status").combobox("getValue"),
			'itemType':$("#c-itemType").combobox("getValue"),
			'appType':$("#c-appType").combobox("getValue")
		},
		url : 'car_self_check_list',
		pageSize : 100,
		pageList : [ 100, 50, 30, 10 ],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'appType',
			title : '客户端类型',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter:function(val){
				if(val == "member"){
					return "会员端";
				}else{
					return "员工端";
				}
			}
		}, {
			field : 'itemType',
			title : '自检类型',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter:function(val){
				if(val == false){
					return "上车自检项目";
				}else{
					return "下车自检项目";
				}
			}
		}, {
			field : 'itemName',
			title : '自检项名',
			width : $(this).width() * 0.2,
			align : 'center'
		},{
            field : 'uploadStatus',
            title : '必须上传照片',
            width : $(this).width() * 0.1,
            align : 'center',
            formatter:function(val){
                if(val=="0"){
                    return "否";
                }else{
                    return "是";
                }
            }
        }, {
			field : 'exceptionUploadStatus',
			title : '异常必须上传照片',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter:function(val){
				if(val=="0"){
					return "否";
				}else{
					return "是";
				}
			}
		},  {
			field : 'status',
			title : '状态',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter:function(val){
				if(val==false){
					return "关闭";
				}else{
					return "开启";
				}
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'updateTime',
			title : '修改时间',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'uname',
			title : '创建人',
			width : $(this).width() * 0.1,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});

	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
        $("#addsamplePhoto").attr("src","");
        $("#addsamplePhoto").css("display","none");
		$("#exceptionUploadStatus").combobox("setValue","0");
		$("#uploadStatus").combobox("setValue","0");
		$(".employee_show").hide();
		$("#addView").dialog("open");
	});
	// 构造对话框
	$("#addView").dialog({
		title : '车辆自荐项新增',
		width : "500",
		height : "350",
		top : "150",
		left :"600",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit", {
					url : "save_car_self_check",
					onSubmit : function() {
						$.messager.progress({
							text : "正在加载，请稍等！"
						});
						var flag = $(this).form("validate");
						if (!flag) {
							$.messager.progress('close');
						}
						return flag;
					},
					success : function(result) {
						$.messager.progress('close');
						if (result == "succ") {
							$.messager.alert("提示", "保存成功", "info");
							$("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else if(result == "NameErr"){
							$.messager.alert("提示", "自检项名必须小于六个字符", "error");
						}else {
							$.messager.alert("提示", "保存失败", "error");
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
		} ]
	});
	//编辑
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
        $("#addsamplePhoto").css("display","");
        $("#samplePhoto").attr("value","");
        $("#samplePhoto").val("");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择编辑项目", "error");
   		}else{
			$("#id").val(selectedRow[0].id);
			$("#itemType").combobox("setValue",selectedRow[0].itemType);
			$("#appType").combobox("setValue",selectedRow[0].appType);
			$("#itemName").textbox("setValue",selectedRow[0].itemName);
			$("#exceptionUploadStatus").combobox("setValue",selectedRow[0].exceptionUploadStatus);
			$("#uploadStatus").combobox("setValue",selectedRow[0].uploadStatus);
		    var samplePhotoUri=selectedRow[0].samplePhotoUri;

			$("#addsamplePhoto").attr("src",samplePhotoUri);
			if(selectedRow[0].appType == "member")
				$(".employee_show").hide();
			else 
				$(".employee_show").show();
			$("#addView").dialog("open").dialog("setTitle", "编辑自检项");
  		}	     
	});
	

	$("#appType").combobox({
		onChange : function(n, o) {
			var appType = $("#appType").combobox("getValue");
			if(appType == "member")
				$(".employee_show").hide();
			else 
				$(".employee_show").show();
		}
	});
	$("#setStatus").bind("click",function(){
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择配置项", "error");
		 }else{
			 var title = "";
			 if(selectedRows[0].status == 0){
				 title = "确定要开启自检项("+selectedRows[0].itemName+")吗";
			 }else if(selectedRows[0].status == 1){
				 title = "确定要关闭自检项("+selectedRows[0].itemName+")吗";
			 }
			 $.messager.confirm("提示",title,function(r){
					if(r){
						$.post("set_check_status?id="+selectedRows[0].id+"&status="+selectedRows[0].status,function(data){
							if(data == "closeSucc"){
								$.messager.alert("提示", "关闭成功", "info");
								$("#dataGrid").datagrid("reload");
							}else if(data == "openSucc"){
								$.messager.alert("提示", "开启成功", "info");
								$("#dataGrid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }
	});
})