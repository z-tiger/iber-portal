$(function() {
	$("#btnQuery").bind("click", function() {
		$('#grid').datagrid('load', {
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'title' : $("#s-title").textbox("getValue"),
			'activityType' : $("#s-activityType").textbox("getValue"),
			'status' : $("#s-status").combobox("getValue")
		});
	});
	//清除
	$("#btnClear").bind("click",function() {
		$('#s-cityCode').combobox("setValue","");
		$('#s-title').textbox("setValue","");
		$('#s-activityType').textbox("setValue","");
		$('#s-status').textbox("setValue","");
	});
	//编辑
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#grid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择编辑项目", "error");
   		}else{
			$("#t-id").val(selectedRow[0].id);
			$("#t-cityCode").combobox("setValue",selectedRow[0].cityCode);
			$("#t-shareText").textbox("setValue",selectedRow[0].shareText);
			$("#t-activityType").combobox("setValue",selectedRow[0].activityType);
			$("#t-startTime").datetimebox("setValue",selectedRow[0].startTime);
			$("#t-endTime").datetimebox("setValue",selectedRow[0].endTime);
			$("#t-shareUrl").textbox("setValue",selectedRow[0].shareUrl);
			$("#addViewLottery").dialog("open").dialog("setTitle", "编辑抽奖详情信息");
  		}	     
	});
	//新增
	$("#btnSave").bind("click",function() {
		$("#addLotteryForm").form("clear");
		$("#addViewLottery").dialog({title:"编辑抽奖活动管理"});
		$("#addViewLottery").dialog("open");
	});
	//新增
	$("#addViewLottery").dialog( {
		width : "380",
		height : "300",
		top : "250",
		left : "610",
		modal:true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addLotteryForm").form("submit", {
					url : "editLotteryManage",
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
						if (data.status == "succ") {
							$.messager.alert("提示", data.msg, "info");
							$("#addViewLottery").dialog("close");
							$("#grid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "error");
						} 
					}
				});
				
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#addViewLottery").dialog("close");		
			}
		}]
	});
	//lottery_draw_list
	$('#grid').datagrid({
		title : '抽奖管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'lottery_draw_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		pagination : true,
		rownumbers : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'shareText',
			title : '标题',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'activityType',
			title : '活动类型',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'status',
			title : '活动状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val == false) {
					return '关闭';
				} else {
					return '开启';
				}
			}
		}, {
			field : 'startTime',
			title : '活动开始时间',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'endTime',
			title : '活动结束时间',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'userName',
			title : '创建人',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'action',
			title : '抽奖项目',
			width : $(this).width() * 0.08,
			align : 'center' ,
			formatter : function(val,row,index) {
				return "<a href='javascript:void(0);' onclick='seePrizeName("+row.id+");'>查看抽奖项目详情</a>";
			}
		}, {
			field : 'shareTextUrl',
			title : '分享内容',
			width : $(this).width() * 0.2,
			align : 'center'
		} ] ],
	});
	$("#btnQueryLotteryItem").bind("click", function() {
		$('#getprizeNameGrid').datagrid('load', {
			'prizeName':$("#s-prizeName").textbox("getValue")
		});
	});
	//清除
	$("#btnClearLotteryItem").bind("click",function() {
		$('#s-prizeName').textbox("setValue","");
	});
	//新增
	$("#btnSaveLotteryItem").bind("click",function() {
		$("#addViewForm").form("clear");
		$("#addView").dialog({title:"编辑抽奖详情信息"});
		$("#addView").dialog("open");
	});

    //编辑优惠券策略
    $("#btnSaveLotteryCoupon").bind("click",function() {
        $("#addCoupnViewForm").form("clear");
        var selectedRow = $("#getprizeNameGrid").datagrid("getSelections");
        $("#lotteryDrawItemId").val(selectedRow[0].id)
        $("#addCoupnView").dialog({title:"编辑优惠券策略"});
        $("#addCoupnView").dialog("open");
    });

	$("#addView").dialog( {
		width : "380",
		height : "200",
		top : "250",
		left : "610",
		modal:true,
	});
    $("#addCoupnView").dialog( {
        width : "300",
        height : "auto",
        top : "250",
        left : "610",
		buttons : [ {
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                $("#addCoupnViewForm").form("submit", {
                    url : "saveOrUpdateDrawCouponStrategy",
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
                        console.log(result)
                        if (result == "ok") {
                            $.messager.alert("提示", "创建成功", "info");
                            $("#lotteryCouponGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "创建失败", "error");
                        }
                    }
                });

            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addCoupnView").dialog("close");
            }
        }]
    });
	//编辑
	$("#btnEditLotteryItem").bind("click",function(){
      	var selectedRow = $("#getprizeNameGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择编辑项目", "error");
   		}else{
			$("#id").val(selectedRow[0].id);
			$("#e-prizeName").textbox("setValue",selectedRow[0].prizeName);
			$("#e-prizeAmount").textbox("setValue",selectedRow[0].prizeAmount);
			$("#e-prizeRestAmount").textbox("setValue",selectedRow[0].prizeRestAmount);
			$("#e-prizeWeight").textbox("setValue",selectedRow[0].prizeWeight);
			$("#addView").dialog("open").dialog("setTitle", "编辑抽奖详情信息");
  		}	     
	});

    //编辑
    $("#btnEditLotteryCoupon").bind("click",function(){
        var selectedRow = $("#lotteryCouponGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择编辑项目", "error");
        }else{
            $("#addCoupnViewForm").form("clear");
            $('#addCoupnViewForm').form('load',selectedRow[0])
            var selectedRow = $("#getprizeNameGrid").datagrid("getSelections");
            $("#lotteryDrawItemId").val(selectedRow[0].id)
            $("#addCoupnView").dialog({title:"编辑优惠券策略"});
            $("#addCoupnView").dialog("open");
        }
    });

	//新增
	$("#addView").dialog( {
		width : "380",
		height : "200",
		top : "250",
		left : "610",
		modal:true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit", {
					url : "saveOrUpdateLottery",
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
						if (data.status == "succ") {
							$.messager.alert("提示", data.msg, "info");
							$("#addView").dialog("close");
							$("#getprizeNameGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "error");
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
//	$("#btnRemoveLotteryItem").bind("click",function(){
//      	var selectedRow = $("#getprizeNameGrid").datagrid("getSelections");
//   		if(selectedRow.length <= 0){
//			$.messager.alert("提示", "请选择要删除的图片", "error");
//   		}else{
//			var JsonData = selectedRow[0];
//			$.messager.confirm("提示","确定要删除吗?",function(r){
//	      		if(r){
//					$.post("LotteryItemDel",{"id":JsonData.id},function(result){
//						var data = eval('(' + result + ')');
//						if(data.status =="succ"){
//							$.messager.alert("提示", data.msg , "info");
//							$("#getprizeNameGrid").datagrid("reload");
//						    $("#getprizeNameGrid").datagrid("clearSelections");
//						}else{
//							$.messager.alert("提示", data.msg, "error");
//						}
//					},"text");
//				}
//			});
//			
//  		}	     
//	});
	$("#btnRemoveLotteryCoupon").bind("click",function(){
		var selectedRow = $("#lotteryCouponGrid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的图片", "error");
		}else{
			var id = selectedRow[0].id;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("delete_lottery_coupon",{"id":id},function(result){
						var data = eval('(' + result + ')');
						if(data.status =="succ"){
							$.messager.alert("提示", data.msg , "info");
                            $("#lotteryCouponGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "error");
						}
					},"text");
				}
			});

		}
	});

    $("#lottery_coupon").click(function () {

        var selectedRow = $("#getprizeNameGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择一个项目", "info");
            return;
        }else{
            $("#lotteryCouponView").dialog("open").dialog("setTitle", "设置优惠券策略");
            $('#lotteryCouponGrid').datagrid('load', {
                'id':selectedRow[0].id
            });
        }

    });

	$("#getprizeNameView").dialog({
		width : "800",
		height : '500',
		top : "90",
		left:'400',
		modal:true,
	});

    $("#lotteryCouponView").dialog({
        width : "1000",
        height : '500',
        top : "90",
        left:'400',
        modal:true
    });

    $("#lotteryCouponGrid").datagrid({
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : true,
        nowrap : true,
        striped : true,
        collapsible : true,
        rownumbers : true,
        singleSelect : true,
        url : 'getPrizeCoupnConfigInfo',
        pageSize : 10,
        idField : 'id',
        pagination : true,
        rownumbers : true,
        columns : [ [ {
            field : 'ck',
            checkbox : true
        }, {
            field : 'useType',
            title : '优惠券类型',
            width : $(this).width() * 0.25,
            align : 'center',
            formatter : function(val) {
                if (val == 1) {
                    return '现金券';
                } else if (val == 2){
                    return '满减券';
                }else if (val == 3){
                    return '抵扣券';
                }
            }
        }, {
            field : 'couponAccount',
            title : '优惠券数量',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'minBalance',
            title : '优惠券最低面值',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'maxBalance',
            title : '优惠券最高面值',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'minUseValue',
            title : '满减券最低限额（元）',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'maxDeductionValue',
            title : '折扣券最大抵扣额（元）',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'deadline',
            title : '优惠券有效天数',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'createTime',
            title : '创建时间',
            width : $(this).width() * 0.25,
            align : 'center'
        }, {
            field : 'editor',
            title : '操作人',
            width : $(this).width() * 0.25,
            align : 'center'
        }] ]
    });
    $('#useType').combobox({
        onChange: function (newValue, oldValue) {
            if(newValue=="1"){
                $("#minUseValue").numberbox({ "disabled":true });
                $("#maxDeductionValue").numberbox({ "disabled":true });
            }
            if(newValue=="2"){
                $("#minUseValue").numberbox({ "disabled":false });
                $("#maxDeductionValue").numberbox({ "disabled":true });
            }
            if(newValue=="3"){
                $("#minUseValue").numberbox({ "disabled":true });
                $("#maxDeductionValue").numberbox({ "disabled":false });
            }
        }
    });
});

function seePrizeName(id) {
	$("#getprizeNameGrid").html("");
	$('#s-prizeName').textbox("setValue","");
	
	$("#getprizeNameView").dialog("open").dialog("setTitle", "抽奖项目详情");
	$.messager.alert("提示", "<font color=red>注意：添加和修改奖品概率时，概率总和必须为1</font>", "warning");
	$("#getprizeNameGrid").datagrid({
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		onLoadSuccess: compute,//加载完毕后执行计算
		url : 'getPrizeNameInfo',
		queryParams:{ 
			"id":id,
			"prizeName":$('#s-prizeName').textbox("getValue")
		},
		pageSize : 10,
		idField : 'id',
		pagination : true,
		rownumbers : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'prizeName',
			title : '项目名称',
			width : $(this).width() * 0.25,
			align : 'center'
		}, {
			field : 'prizeAmount',
			title : '奖品数量',
			width : $(this).width() * 0.25,
			align : 'center'
		}, {
			field : 'prizeRestAmount',
			title : '奖品剩余数量',
			width : $(this).width() * 0.25,
			align : 'center'
		}, {
			field : 'prizeWeight',
			title : '奖品概率',
			width : $(this).width() * 0.25,
			align : 'center'
		}] ]
	});
}
function compute() {//计算函数
    var rows = $('#getprizeNameGrid').datagrid('getRows');
    var totalPrizeWeight = 0 ;
    
    for (var i = 0; i < rows.length; i++) {
    	totalPrizeWeight += parseFloat(rows[i]['prizeWeight']);
    }
    
    var totalPrizeWeightVal = Number(totalPrizeWeight) ;  
    if(!isNaN(parseFloat(totalPrizeWeightVal))) {    
//    	totalPrizeWeightVal = totalPrizeWeightVal.toFixed(2);    
    	totalPrizeWeightVal = totalPrizeWeightVal;    
    }
    
  //新增一行显示统计概率总和(必须为1)
    $('#getprizeNameGrid').datagrid('appendRow', { prizeName: '合计：', prizeWeight: totalPrizeWeightVal});
    
}
