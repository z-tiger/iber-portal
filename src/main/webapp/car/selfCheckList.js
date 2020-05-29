$(function() {
	// 查询链接
	$("#btnQuery").bind("click", function() {
		$("#dataGrid").datagrid('clearChecked');
		$('#dataGrid').datagrid('load', {
			'cityCode':$("#cityCode").combobox("getValue"),
			'lpn':$("#lpn").textbox("getValue"),
			'phone':$("#phone").textbox("getValue"),
			'itemType':$("#itemType").combobox("getValue"),
			'status':$("#status").combobox("getValue"),
			'orderId':$("#orderId").textbox("getValue"),
			'exceptionStatus':$("#exceptionStatus").combobox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $("#cityCode").combobox("getValue");
	    var lpn = $("#lpn").textbox("getValue");
	    var status = $("#status").combobox("getValue");
	    var itemType = $("#itemType").combobox("getValue");
	    var orderId = $("#orderId").textbox("getValue");
	    var exceptionStatus = $("#exceptionStatus").combobox("getValue");
	    var phone = $("#phone").textbox("getValue");
		$("#queryCarSelfCheck").form("submit", {
							url : "export_carSelf_list?cityCode=" + cityCode
									+ "&lpn=" + lpn + "&status=" + status
									+ "&itemType=" + itemType + "&orderId="
									+ orderId + "&phone=" + phone
									+ "&exceptionStatus=" + exceptionStatus,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	//清除
	$("#btnClear").bind("click",function(){
		$("#lpn").textbox("setValue","");
		$("#phone").textbox("setValue","");
		$("#itemType").combobox("setValue","");
		$("#status").combobox("setValue","");
		$("#exceptionStatus").combobox("setValue","");
		$("#orderId").textbox("setValue","")
	});
	$('#dataGrid').datagrid({
		title : '车辆自检列表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : false,
		url : 'car_self_check_info',
		queryParams:{
			'cityCode':$("#cityCode").combobox("getValue"),
			'lpn':$("#lpn").textbox("getValue"),
			'phone':$("#phone").textbox("getValue"),
			'itemType':$("#itemType").combobox("getValue"),
			'orderId':$("#orderId").textbox("getValue"),
			'status':$("#status").combobox("getValue"),
			'exceptionStatus':$("#exceptionStatus").combobox("getValue")
		},
		pageSize : 100,
		pageList : [ 100, 50, 30, 10 ],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'orderId',
			title : '订单号',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'memberType',
			title : '身份',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val){
				if(val == "member"){
					return "会员";
				}else{
					return "员工";
				}
			}
		} , {
			field : 'createTime',
			title : '反馈时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'itemType',
			title : '自检类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val){
				if(val == false || val ==""){
					return "上车自检";
				}else{
					return "下车自检";
				}
			}
		}, {
			field : 'itemName',
			title : '项目',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : '       ',
			title : '是否异常',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val,row){
				val = row.handleStatus;
				if(val == "2"){
					return "正常";
				}else{
					return "<font color=red>异常</font>";
				}
			}
		}, {
			field : 'carCheckUri',
			title : '异常项目图片',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val){
				if(val == "" || val == null){
					return "未上传";
				}else{
					return "已上传";
				}
			}
		}, {
			field : 'handleStatus',
			title : '处理状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val,row){
				if(val == 0){
					return "<font color=red>未处理</font>";
				}else if(val == 2){
					return "无需处理";
				}else{
					return "已处理";
				}
			}
		}, {
			field : 'handleUser',
			title : '处理人',
			width : $(this).width() * 0.08,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});

	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#addView").dialog("open");
	});
	// 构造对话框
	$("#addView").dialog({
		title : '车辆自检处理',
		width : "450",
		height : "450",
		top : "150",
		left :"600",
		buttons : [ {
			text : "处理",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit", {
					url : "handle_car_self_check",
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
							$.messager.alert("提示", "处理成功", "info");
							$("#dataGrid").datagrid("reload");
							$("#dataGrid").datagrid('clearChecked');
							$("#addView").dialog("close");
						}else if(result == "err"){
							$.messager.alert("提示", "此车辆自检已处理,不可处理", "info");
							$("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else if(result == "pass"){
							$.messager.alert("提示", "此车辆已通过,不可处理", "error");
							$("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						} else{
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
	
	$("#batchHandle").bind("click",function(){
		var ids = [];
		var selectedRow = $("#dataGrid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要处理的记录", "error");
   		}else{
   			for ( var i = 0; i < selectedRow.length; i++) {
				ids.push(selectedRow[i].id);
			}
   			$.messager.confirm("提示","确定要处理吗?",function(r){
   				if(r){
   					$.ajax({  
   						url: 'batch_handle',  
   						data: { idList: ids },
   						dataType: "html",  
   						type: "POST",  
   						traditional: true,
   						success: function (result) {  
   							if(result=="succ"){
   								$.messager.alert("提示", "处理成功", "info");
   								$("#dataGrid").datagrid("reload");
   								$("#dataGrid").datagrid('clearChecked');
   							}else if(result=="error"){
   								$.messager.alert("提示", "处理失败,订单不一致", "error");
   								$("#dataGrid").datagrid("reload");
   							}else if(result=="numErr"){
   								$.messager.alert("提示", "处理失败,至少两条记录", "error");
   								$("#dataGrid").datagrid("reload");
   							}else if(result =="pass"){
   								$.messager.alert("提示", "处理失败,此订单已处理", "error");
   								$("#dataGrid").datagrid("reload");
   							}else{
   								$.messager.alert("提示", "操作失败", "error");
   							}
   						}
   					});  
   				}
   			});
   		}
	})
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择编辑的记录", "error");
   		}else{
   			for ( var k = 0; k < selectedRow.length; k++) {

   				$("#id").val(selectedRow[k].id);
   				console.log(selectedRow[k].id);
   				$("#s-cityCode").combobox("setValue",selectedRow[k].cityName);
   				$("#s-lpn").textbox("setValue",selectedRow[k].lpn);
   				$("#s-name").textbox("setValue",selectedRow[k].name);
   				$("#s-phone").textbox("setValue",selectedRow[k].phone);
   				if(selectedRow[k].memberType=="member"){
   					$("#s-memberType").textbox("setValue","会员");
   				}else{
   					$("#s-memberType").textbox("setValue","员工");
   				}
   				$("#s-itemType").combobox("setValue",selectedRow[k].itemType);
   				$("#s-errItem").textbox("setValue",selectedRow[k].itemName);
   				var str = selectedRow[k].carCheckUri;
   				//console.log(array.length);
   				$("#photoList").empty();
   				if(str && str != ""){
   					var array = [];
   					array = str.split(",");
   					for ( var i = 0; i < array.length; i++) {
   						var arr = array[i];
   						if(arr != ""){
//   						$("#photoList").hide();
//   					}else{
   							console.log(arr);
   							$("#photoList").show();
   							$("#photoList").append("<a href=udCarImgOpen?img="+arr+" target=_blank>"+"<img width=400 height=208 src="+arr+">"+"</a>");
   						}
   				}
   			}	
   				$("#addView").dialog("open").dialog("setTitle", "处理");
			}
			
  		}	     
	});
})
