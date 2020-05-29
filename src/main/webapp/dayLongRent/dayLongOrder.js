$(function() {
	//查询链接
	$("#btnQuery").bind("click",function(){
		var memberName = $.trim($("#s-memberName").val()) ;
		var cityCode = $('#s-cityCode').combobox('getValue') ;
		var lpn = $('#s-lpn').textbox('getValue');
		var phone = $('#s-phone').textbox('getValue');
		var orderStatus = $('#s-orderStatus').combobox('getValue');
		var bt = $("#s-bt").datetimebox("getValue");
		var et = $("#s-et").datetimebox("getValue");
		$('#dataGrid').datagrid('load',{
			memberName : memberName ,
			cityCode :cityCode,
			lpn : lpn,
			phone : phone,
			orderStatus : orderStatus,
			bt : bt ,
			et : et
		}) ;
	});
	$('#dataGrid').datagrid( {
		title : '日租订单列表',
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
		url : 'dayLongRent_order_list',
		queryParams:{
			'orderStatus':$("#s-orderStatus").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox:true
		},{
			field : 'cityName',
			title : '所属地区',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'orderId',
			title : '订单号',
			width : $(this).width() * 0.2,
			align : 'center',
		}, {
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'type',
			title : '车型',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if(val.indexOf(",") !=-1 ){
					var val2 = val.substring(val.lastIndexOf(",")+1,val.length);
					if(val2 != "" && val2.indexOf("•") < 0){
						return val2.substring(0,2) + "•" + val2.substring(2);
					}
				}else{
					if(val != "" && val.indexOf("•") < 0){
						return val.substring(0,2) + "•" + val.substring(2);
					}
				}
			}
		}, {
			field : 'parkName',
			title : '取车网点',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'beginTime',
			title : '订单开始时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'returnTimes',
			title : '订单结束时间',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
		}, {
			field : 'returnTime',
			title : '订单结束时间',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec){
				var array = [] ;
				array = rec.payStatus.split(",");
				if(array[0] == 'finish' && array[1] == 'finish'){
					return val;
				}else if(array[0] == 'finish' && array[1] != 'finish'){
					return rec.returnTimes ;
				}else{
					return rec.returnTimes;
				}
			}
		}, {
			field : 'endTime',
			title : '实际还车时间',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec){
				if(rec.payStatus != 'noPay'){
					return val;
				}
			}
		}, {
			field : 'orderStatus',
			title : '订单状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 'ordered'){
					return '预约' ;
				}else if(val == 'useCar'){
					return '<font color=red>用车</font>' ;
				}else if(val == 'finish'){
					return '完成' ;
				}else if(val == 'cancel'){
					return '取消' ;
				}
			}
		}, {
			field : 'payStatus',
			title : '支付状态',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
		}, {
			field : 'orderMoney',
			title : '订单金额(总/元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if(rec.payStatus != 'noPay'){
					if (val) {
						n =  parseFloat(val/100).toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
						}else if (rec.lpn) {
							return '0.00';
						}
				}
			}
		}, {
			field : 'freeCompensateMoney',
			title : '商业保险(总/元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if(rec.payStatus != 'noPay'){
					if (val) {
						n =  parseFloat(val/100).toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
						}else if (rec.lpn) {
							return '0.00';
						}
				}
			}
		}, {
			field : 'payMoney',
			title : '支付金额(总/元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				if(rec.payStatus != 'noPay'){
					if (val) {
						n =  parseFloat(val/100).toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
						}else if (rec.lpn) {
							return '0.00';
						}
				}
			}
		}, {
			field : '        ',
			title : '更换车辆次数',
			width : $(this).width() * 0.10,
			align : 'center',
			formatter : function(val, rec) {
				return "<a href='#' onClick=changeCarDetail('"+rec.orderId+"')>更换车辆详情</a>" ;
			}
		}, {
			field : 'reletNo',
			title : '       ',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
		}, {
			field : 'extendTsOrderId',
			title : '       ',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
		}, {
			field : '     ',
			title : '订单详情',
			width : $(this).width() * 0.10,
			align : 'center',
			formatter : function(val, rec) {
				if(rec.payStatus != 'noPay'){
					return "<a href='#' onClick=reletNoDetail('"+rec.orderId+"','"+rec.extendTsOrderId+"','"+rec.orderStatus+"')>进入订单详情</a>" ;
				}
			}
		}] ],
		pagination : true,
		rownumbers : true
	});

    $('#grid').datagrid({
		title : '车辆运营列表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,// 自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动
		nowrap : true,// 在同一行中显示数据
		striped : true,// 是否显示斑马线效果
		collapsible : true,// 否显示可折叠按钮
		singleSelect : true,// 如果为true，则只允许选择一行
		url : 'car_mrg_run_list',// 会自动发送异步的请求
		queryParams : {// 在请求远程数据时发送额外的参数。
			'cityCode' : $("#scityCode").combobox("getValue"),
			'lpn' : $("#lpn").textbox("getValue"),
			'parkName' : $("#s-parkName").textbox("getValue"),
            'status':"empty"
		},
		pageSize : 10,// 设置分页属性的时候初始化页面大小
		pageList : [25,20,15,10],
		idField : 'id', // 指明哪一个字段是标识字段
		columns : [ [
			{// DataGrid列配置对象
				field : 'ck', // 列字段名称
				checkbox : true
				// 如果为true，则显示复选框

			},
			{
				field : 'cityCode',
				title : '所属城市',
				hidden : true,
				width : $(this).width() * 0.08,
				align : 'center'
			},
			{
				field : 'cityName',
				title : '所属城市',
				width : $(this).width() * 0.08,
				align : 'center'
			},
			{
				field : 'lpn',
				title : '车牌号码',
				width : $(this).width() * 0.08,
				align : 'center',
				formatter : function(val,rec) {

					if (val.indexOf("•") < 0){
						return "<img width='20' height='10' src='"+rec.brandUrl+"'/>" + val.substring(0,2) + "•" + val.substring(2);
					}else {
						return "<img width='20' height='10' src='"+rec.brandUrl+"'/>" + val;
					}
				}
			},
			{
				field : 'carRunStatus',
				title : '车辆状态',
				width : $(this).width() * 0.08,
				align : 'center',
				formatter : function(val, rec) {
					if (val == "empty" || val == ""
						|| val == null || val == "null"
						|| val == "NULL") {
						return "闲置中"
					} else if (val == "repair") {
						return "维修中"
					} else if (val == "maintain") {
						return "维护中"
					}else if(val=="rescue"){
						return "救援中"
					}else if(val=="accident"){
						return "事故处理中"
					}else if (val == "charging"){
						return "补电中"
					}else {
						if(rec.preOffline =="1"){
							return "运营中(<font color=red>预下线</font>)"
						}else{
							return "运营中"
						}
					}
				}
			},
			{
				field : 'restBattery',
				title : '剩余电量',
				width : $(this).width() * 0.08,
				align : 'center',
				formatter : function(val, rec) {
					if (val < 30) {
						if(rec.batStatus == 1){
							return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_smaill.png'/> "
								+ val+"（<font color=red>充电中</font>）";
						}else{
							return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_smaill.png'/> "
								+ val+"（未充电）";
						}
					} else if (30 <= val && val <= 80) {
						if(rec.batStatus == 1){
							return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_medium.png'/> "
								+ val+"（<font color=red>充电中</font>）";
						}else{
							return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_medium.png'/> "
								+ val+"（未充电）";
						}
					} else if (val > 80) {
						if(rec.batStatus == 1){
							return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_big.png'/> "
								+ val+"（<font color=red>充电中</font>）";
						}else{
							return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_big.png'/> "
								+ val+"（未充电）";
						}
					}
				}
			},
			{
				field : 'smallBatteryVoltage',
				title : '小电瓶电压',
				width : $(this).width() * 0.08,
				align : 'center',
				formatter : function(val, rec) {
					if (val < 100) {
						return "<font color=red>"
							+ parseFloat(val / 10)
							+ "V</font>";
					} else {
						return parseFloat(val / 10) + "V";
					}
				}
			}, {
				field : 'parkName',
				title : '所在网点',
				align : 'center'
			}, {
				field : 'giName',
				title : '所属片区',
				width : $(this).width() * 0.06,
				align : 'center'
			},{
				field : 'getuiStatus',
				title : '个推状态',
				width : $(this).width() * 0.06,
				align : 'center',
				formatter : function(val, rec) {
					
					var result = "";
					if(val == "Offline") {
						return "<font color=red>离线</font>";
					}else if(val == "Online"){
						return "在线";
					}
					return result;
				}
			}
		] ],
		pagination : true, // 如果为true，则在DataGrid控件底部显示分页工具栏
		rownumbers : true
		// 如果为true，则显示一个行号列
	});


    $('#gridhistory').datagrid({
        width : '100%',
        height : '200px',
        fit : true,
        fitColumns : false,// 自动展开/收缩列的大小，以适应网格的宽度，防止水平滚动
        nowrap : true,// 在同一行中显示数据
        striped : true,// 是否显示斑马线效果
        collapsible : true,// 否显示可折叠按钮
        singleSelect : true,// 如果为true，则只允许选择一行
        url : 'get_change_history',// 会自动发送异步的请求
        queryParams : {
        },
        pageSize : 10,// 设置分页属性的时候初始化页面大小
        pageList : [25,20,15,10],
        idField : 'id', // 指明哪一个字段是标识字段
        columns : [ [
            {// DataGrid列配置对象
                field : 'ck', // 列字段名称
                checkbox : true
                // 如果为true，则显示复选框

            },
            {
                field : 'orderId',
                title : '订单号',
                hidden : true,
                width : $(this).width() * 0.08,
                align : 'center'
            },
            {
                field : 'beforeLpn',
                title : '更换前车牌',
                width : $(this).width() * 0.06,
                align : 'center'
            },
            {
                field : 'afterLpn',
                title : '更换后车牌',
                width : $(this).width() * 0.06,
                align : 'center'
            },
            {
                field : 'createTime',
                title : '更换时间',
                width : $(this).width() * 0.08,
                align : 'center'
            },
            {
                field : 'createUser',
                title : '操作人',
                width : $(this).width() * 0.08,
                align : 'center'
            },
            {
                field : 'reason',
                title : '更换原因',
                width : $(this).width() * 0.08,
                align : 'center'
            }, {
                field : 'beginTime',
                title : '车辆使用开始时间',
                align : 'center'
            }, {
                field : 'endTime',
                title : '车辆使用结束时间',
                width : $(this).width() * 0.06,
                align : 'center'
            },{
                field : 'mileage',
                title : '里程',
                hidden : true,
                width : $(this).width() * 0.06,
                align : 'center'
            },{
                field : 'address',
                title : '换车地址',
                width : $(this).width() * 0.06,
                align : 'center'
            }
        ] ],
        pagination : false, // 如果为true，则在DataGrid控件底部显示分页工具栏
        rownumbers : true
        // 如果为true，则显示一个行号列
    });

    $("#changeLpnView").dialog({
        width : "auto",
        height : "auto",
        top : "100",
        left : "400",
        modal : true,
        title : "日租换车",
        buttons : [ {
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                var selectedRows = $("#grid").datagrid("getSelections");
                if(selectedRows.length <= 0){
                    $.messager.alert("提示", "请选择需要更换的车辆", "error");
                }else{
                	var buttonObj =  $(this);
                    buttonObj.linkbutton('disable');
                    $('#changeLpnForm').form('submit', {url:"long_rent_request_change_car",
                        onSubmit: function(param){
                            param.lpn= $("#r-lpn-2").html();
                            param.afterLpn=selectedRows[0].lpn
                        },success : function(result) {
                            if (result == "notUse"){
                                $.messager.alert("提示", "当前订单的车辆不为用车状态", "error");
                            }else if(result == 'noCity'){
                                $.messager.alert("提示", "当前订单车辆所属城市错误！", "error");
                            }else if(result == 'ok'){
                                $.messager.alert("提示", "换车成功！", "success");
                                $("#dataGrid").datagrid("reload");
                                $("#changeLpnView").dialog("close");
                            }else if(result == 'orderid_not_true'){
                                $.messager.alert("提示", "换车失败！日租订单和车辆订单不一致！", "error");
                            }else if (result == 'ordernotUse'){
                                $.messager.alert("提示", "换车失败！日租订单不为用车状态", "error");
							} else if (result == "getui_offline"){
                                $.messager.alert("提示", "换车失败,选择的车辆个推不在线！", "error");
							}else if (result == 'car_is_use'){
                                $.messager.alert("提示", "换车失败,选择的车辆已被预约！", "error");
							}
							else {
                                $.messager.alert("提示", "换车失败", "error");
                            }
                            buttonObj.linkbutton('enable');
                        }
                    });
				}

            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#changeLpnView").dialog("close");
            }
        } ]
    });

    $("#changeLpnHistory").dialog({
        width : "50%",
        height : "300",
        top : "100",
        left : "400",
        modal : true,
        title : "日租换车记录",
        buttons : [  {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#changeLpnHistory").dialog("close");
            }
        } ]
    });

	// query
    $("#car_btnQuery").bind("click", function() {
        initCarList();
    });
    // 剩余电量降序查询
    $("#upToDownQuery").bind("click", function() {
        $("#grid").datagrid("load", {
            'cityCode' : $("#scityCode").combobox("getValue"),
            'lpn' : $("#s-lpn").textbox("getValue"),
            'parkName' : $("#s-parkName").textbox("getValue"),
            'status':"empty",
            'orderRule':'DESC'
        });
    });
	//清空
	$("#clearQuery").bind("click",function(){
		clearQueryForm();
	});
	$("#changeLpn").bind("click",function(){
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择换车订单", "error");
		}else if (selectedRows[0].orderStatus!="useCar"){
            $.messager.alert("提示", "订单不为用车状态", "error");
		}else{
            $("#changeLpnForm").form("clear");
            if (selectedRows[0].lpn.indexOf(",") < 0) {
                $("#r-lpn-2").html("<font color='green'>"+selectedRows[0].lpn.split(",")[0]+"</font>");
            }else {
                $("#r-lpn-2").html("<font color='green'>"+selectedRows[0].lpn.split(",")[0]+"</font>");
            }
            $("#orderId").val(selectedRows[0].orderId)
            clearQueryCarList();
            $('#grid').datagrid('clearSelections');
            initCarList();
            $("#changeLpnView").dialog("open");
		}
	})
	function clearQueryCarList() {
        $("#scityCode").combobox("setValue","");
        $("#lpn").textbox("setValue","");
        $("#s-parkName").textbox("setValue","");
    }
	function initCarList(){
        $("#grid").datagrid("load", {
            'cityCode' : $("#scityCode").combobox("getValue"),
            'lpn' : $("#lpn").textbox("getValue"),
            'parkName' : $("#s-parkName").textbox("getValue"),
            'status':"empty"
        });
	}
	$("#expireDetailInfoView").dialog({
		width : "800",
		height : '500',
		top : "90",
		left:'400',
		modal:true,
	});
});
	/**
	 * 清空查询
	 */
	function clearQueryForm(){
		$('#queryForm').form('clear');
	}
	/**
	 * 查看历史换车
	 * @param orderId
	 */
	function changeCarDetail(orderId) {
		$("#gridhistory").datagrid("load", {
			'orderId' : orderId
		});
		$("#changeLpn").bind("click",function(){
			var selectedRow = $("#dataGrid").datagrid("getSelections");
			if(selectedRow.length <= 0){
				$.messager.alert("提示", "请选择选择一条记录", "error");
	   		}else{
	   			
	  		}
		});
		$("#changeLpnHistory").dialog("open");
	}
		/*延期详情*/
		function reletNoDetail(orderId,extendTsOrderId,orderStatus){
			$("#expireDetailInfoGrid").html("")
			$("#s-orderId").val("");
			$("#s-extendTsOrderId").val("");
			$("#s-orderStatus").val("");
			
			$("#s-orderId").val(orderId);
			$("#s-extendTsOrderId").val(extendTsOrderId);
			$("#s-orderStatus").val(orderStatus);
			$("#expireDetailInfoView").dialog("open").dialog("setTitle", "续租订单详情");
			$("#expireDetailInfoGrid").datagrid({
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : false,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				selectOnCheck : true,
				checkOnSelect : true,
				url : 'reletDetailList',
				queryParams:{ 
					"orderId" : orderId,
					"extendTsOrderId" : extendTsOrderId,
					"orderStatus" : orderStatus
				},
				pageSize : 10,
				idField : 'id',
				pagination : true,
				rownumbers : true,
				columns : [ [ 
				{
					field : 'id',
					checkbox : true
				}, {
					field : 'orderId',
					title : '订单号',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'beginTime',
					title : '订单开始时间',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'returnCarTime',
					title : '订单结束时间',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'endTime',
					title : '实际还车时间',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : '     ',
					title : '订单类型',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter :function(val,rec){
						if(rec.orderId.indexOf("DR") != -1){
							return "日租" ;
						}else if(rec.orderId.indexOf("TS") != -1){
							return "时租" ;
						}
					}
				}, {
					field : 'reletNo',
					title : '订单类型',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val){
						if(val == -1){
							return "" ;
						}else if(val == 0){
							return "首租" ;
						}else{
							return "续租" ;
						}
					}
				}, {
					field : 'orderMoney',
					title : '订单金额(单/元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val,rec) {
						if (val) {
							n =  parseFloat(val/100).toFixed(2);
							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
							return n.replace(re, "$1,");
							}else if (rec.lpn) {
								return '0.00';
							}
						}
				}, {
					field : 'freeCompensateMoney',
					title : '商业保险(单/元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val,rec) {
						if (val) {
							n =  parseFloat(val/100).toFixed(2);
							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
							return n.replace(re, "$1,");
							}else if (rec.lpn) {
								return '0.00';
							}
						}
				}, {
					field : 'payMoney',
					title : '支付金额(单/元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val,rec) {
						if (val) {
							n =  parseFloat(val/100).toFixed(2);
							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
							return n.replace(re, "$1,");
							}else if (rec.lpn) {
								return '0.00';
							}
						}
				}] ]
			});
		}
