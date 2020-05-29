$(function(){
	$('#grid').datagrid({
		title : '邮寄地址管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		pagination : true,
		rownumbers : true,
		url : 'member_mail_list',
		queryParams:{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue")
			/*'orderType':$("#s-orderType").combobox("getValue")*/
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'memberName',
			title : '会员名',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '会员电话',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'receiver',
			title : '收件人',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'phone',
			title : '收件人电话',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'areaName',
			title : '所在地区',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'address',
			title : '详细地址',
			width : $(this).width() * 0.06,
			align : 'center'
		/*}, {
			field : 'orderType',
			title : '订单类型',
			width : $(this).width() * 0.06,
			align : 'center' ,
			formatter:function(val){
				if(val != null){
					return getOrderTypeName(val);
				}
				return "";
			}*/
//		}, {
//			field : 'email',
//			title : '电子邮箱',
//			width : $(this).width() * 0.06,
//			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.06,
			align : 'center'
		}
		]],
	});
	var getOrderTypeName = function(orderType){
		var result = "";
		switch (orderType) {
		case "TS":
			result="时租";
			break;
		case "charging":
			result="充电"
			break;
		default:
			break;
		}
		return result;
	}
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("clearSelections");
		$("#grid").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue")
			/*'orderType':$("#s-orderType").combobox("getValue")*/
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $("#s-cityCode").combobox("getValue");
	    var name = $("#s-name").textbox("getValue");
	    var phone = $("#s-phone").textbox("getValue");
		$("#mailForm").form("submit", {
			url : "export_mail_list?cityCode="+cityCode+"&name="+name+"&phone="+phone,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	//修改
	$("#btnEdit").bind("click",function(){
			var selectedRows = $("#grid").datagrid("getSelections");
			if(selectedRows.length <= 0){
					$.messager.alert("提示", "请选择要修改的记录", "error");
			}else{
					$("#e-id").val(selectedRows[0].id);
					$("#e-memberName").html(selectedRows[0].memberName);
					$("#e-memberPhone").html(selectedRows[0].memberPhone);
					$("#e-orderType").html(getOrderTypeName(selectedRows[0].orderType));
					$("#e-receiver").textbox("setValue",selectedRows[0].receiver);
					$("#e-receiverPhone").textbox("setValue",selectedRows[0].phone);
					$("#e-address").textbox("setValue",selectedRows[0].address);
//					$("#e-email").textbox("setValue",selectedRows[0].email);
					//设置地区
					$("#e-province").textbox("setValue",selectedRows[0].province);
					$("#e-city").textbox("setValue", selectedRows[0].city)
					$("#e-area").textbox("setValue",selectedRows[0].area);
					$("#editView").dialog({title:"编辑邮寄地址"});
					$("#editView").dialog("open");		
			}
	});
	

	//构造保存对话框
	$("#editView").dialog( {
		width : "480",
		height : "460",
		top : "80",
		modal:true,
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#editForm").form("submit", {
					url : "memberMail_modify",
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
						var data = eval('(' + result + ')');
						if (data.status == "success") {
							$.messager.alert("提示", data.msg, "info");
							$("#editView").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "info");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#editView").dialog("close");		
			}
		}]
	});
	

	
	
});