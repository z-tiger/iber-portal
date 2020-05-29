function search(){
	var cityCode = $('#cityCode').textbox('getValue') ;
	var lpn = $('#lpn').textbox('getValue');
	var queryDateFrom = $('#queryDateFrom').textbox('getValue');
	var queryDateTo = $('#queryDateTo').textbox('getValue');
	var memberName = $.trim($("#memberName").val()) ;
	var isLongOrder = $("#s-isLongOrder").combobox('getValue');
	var isLockCar = $("#s-isLockCar").combobox('getValue');
	var isEnterpriseUseCar = $("#isEnterpriseUseCar").combobox('getValue');
	var phoneNumber = $.trim($("#phoneNumber").val()) ;
	$('#orderGrid').datagrid('load',{
	    isEnterpriseUseCar:isEnterpriseUseCar,
		memberName : memberName ,
		cityCode :cityCode,
		lpn : lpn,
		phoneNumber :phoneNumber,
		queryDateFrom : queryDateFrom,
		queryDateTo : queryDateTo,
		isLongOrder : isLongOrder,
		isLockCar : isLockCar
	}) ;
}

$(function() {
	//查询链接
	$("#btnQuery").bind("click", function() {
		search() ;
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $('#cityCode').textbox('getValue') ;
		var lpn = $('#lpn').textbox('getValue');
		var queryDateFrom = $('#queryDateFrom').textbox('getValue');
		var queryDateTo = $('#queryDateTo').textbox('getValue');
		var memberName = $.trim($("#memberName").val()) ;
		var isLongOrder = $("#s-isLongOrder").combobox('getValue');
		var isLockCar = $("#s-isLockCar").combobox('getValue');
		var phoneNumber = $.trim($("#phoneNumber").val()) ;
        var isEnterpriseUseCar = $("#isEnterpriseUseCar").combobox('getValue');
		$("#timeShareOrderForm").form("submit", {
							url : "export_timeshareOrder_list?cityCode="
									+ cityCode + "&lpn=" + lpn + "&queryDateFrom="
									+ queryDateFrom + "&queryDateTo=" + queryDateTo + "&memberName="
									+ memberName + "&isLongOrder=" + isLongOrder + "&isLockCar="
									+ isLockCar + "&phoneNumber=" + phoneNumber+"&isEnterpriseUseCar="+isEnterpriseUseCar,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	// 清空
	$("#clearQuery").bind("click", function() {
        $('#timeShareOrderForm').form('clear');
		/*$('#cityCode').textbox('setValue', '');
		$('#lpn').textbox('setValue', '');
		$('#queryDateFrom').textbox('setValue', '');
		$('#queryDateTo').textbox('setValue', '');
		$('#memberName').textbox('setValue', '');
		$('#phoneNumber').textbox('setValue', '');*/
	}); 
	
	//手动保留订单
	$("#btnSave").bind("click",function(){
		var selectedRows = $("#orderGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择手动保留的订单", "error");
		}else{
			$.messager.confirm("提示","确定手动保留订单吗?",function(r){
				if (r) {
					$.ajax({
						  type: 'POST',
						  url: "saveOrder?memberId="+selectedRows[0].memberId+"&orderId="+selectedRows[0].orderId,
						  success: function(result){
							  if (result == 'noOrder') {//该订单不是预约状态
								  $.messager.alert("提示", "该订单不为预约状态，不能手动保留", "info");
							  }else if(result == 'notEnoughLevel') {
								  $.messager.alert("提示", "该会员不为五星会员，不能手动保留", "info");
							  }else if (result == 'succ') {
								  $.messager.alert("提示", "操作成功", "info");
							  }else {
								  $.messager.alert("提示", "操作失败", "error");
							  }
						  }, 
					});
				}
			})
			
		}
	})
	
	/*	
	 * 计算当前订单金额
	 
	function calculateCurrOrdMoney2(){
		debugger;
		var selectedRow = $("#orderGrid").datagrid("getSelected");
		var rowIndex = $('#orderGrid').datagrid('getRowIndex',data);
		calculateCurrOrdMoney(selectedRow, rowIndex);
	}
	
	
	 * 计算当前订单金额
	 
	$("#btnCal").bind("click", function() {
		var selectedRow = $("#orderGrid").datagrid("getSelected");
		if(null == selectedRow){
			$.messager.alert("提示", "请选择一个订单", "error");
		}else{
			var rowIndex = $('#orderGrid').datagrid('getRowIndex', selectedRow);
			calculateCurrOrdMoney(selectedRow, rowIndex);			
		}
	});
	*/
	
	$('#orderGrid')
	.datagrid(
	{
		title : '当前订单',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		url : 'timeShare_order_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'orderId',
			title : '订单编号',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'parkName',
			title : '预约网点',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
				
			}
		}, {
			field : 'orderTime',
			title : '预约时间',
			width : $(this).width() * 0.14,
			align : 'center'
		}, {
			field : 'beginTime',
			title : '上车时间',
			width : $(this).width() * 0.14,
			align : 'center'
		},
		   /*{
			field : 'endTime',
			title : '还车时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}, */
		   {
			field : 'status',
			title : '订单状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
			    if(val == "ordered")
			    	return "预约状态" ;
			    if(val == "useCar")
			    	return "用车状态" ;
			    if(val == "return")
			    	return "还车状态" ;
			}
		}, {
            field : 'isEnterpriseUseCar',
            title : '订单类型',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                console.log("isEnterpriseUseCar>>>");
                console.log(val)
                if(val == "false")
                    return "个人约车" ;
                if(val == "")
                    return "" ;
                if(val == "true")
                    return "企业约车" ;
            }
        },
        {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.06,
			align : 'center'
		}, 
/*		{
			field : 'returnParkName',
			title : '还车网点',
			width : $(this).width() * 0.06,
			align : 'center'
		}, */
		   {
				field : 'isFreeCompensate',
				title : '是否购买商业保险',
				width : $(this).width() * 0.09,
				align : 'center',
				formatter:function(val,rec){
					if(val=="0"){
						return "<font color='red'>否</font>";
					}else{
						return "是";
					}
				}
			}, {
				field : 'balance',
				title : '余额',
				width : $(this).width() * 0.06,
				align : 'center',
				formatter:function(val, rec){
					return (parseFloat(val) / 100.0).toFixed(2);
				}
			}, {
				field : 'deposit',
				title : '押金',
				width : $(this).width() * 0.06,
				align : 'center',
				formatter:function(val, rec){
					return (parseFloat(val) / 100.0).toFixed(2);
				}
			}, {
				field : 'score',
				title : '芝麻信用分',
				width : $(this).width() * 0.06,
				align : 'center'
			}, {
				field : 'isLongOrder',
				title : '超长订单',
				width : $(this).width() * 0.06,
				align : 'center',
				formatter:function(val,rec){
					if(val=="0"){
						return "否";
					}else{
						return "是";
					}
				}
			}, {
				field : 'isLockCar',
				title : '是否锁车',
				width : $(this).width() * 0.06,
				align : 'center',
				formatter:function(val,rec){
					if(val=="0"){
						return "否";
					}else{
						return "是";
					}
				}
			}, {
				field : 'currentOrderMoney',
				title : '当前订单金额',
				width : $(this).width() * 0.09,
				align : 'center',
				formatter : function(val, rec,rowIndex) {
					return "<span id='"+rec.orderId+"'></span>"+"　"+"<a href='javascript:void(0);' class='btncls' onClick=calculateCurrOrdMoney('"+rec.orderId+"')>计算</a>" ;
				}		
			}
		] ],
		onLoadSuccess:function(data){  
	        $('.btncls').linkbutton({text:'计算', plain:true, iconCls:'icon-search'});  
	    },
		pagination : true,
		rownumbers : true
	}); 

});

/*
 * 计算当前订单金额
 */

function calculateCurrOrdMoney(data){
	// 使用同步方法
	$.ajax({
		type : 'POST',
		url : 'calculateCurrOrdMoney',
		data : {
			'orderId':data
		},
		success : function(result) {
			if(result == null || result == 0){
				$("#"+data).html("0.00");
			}else{
				$("#"+data).html(result);
			}
		}
	});
}

