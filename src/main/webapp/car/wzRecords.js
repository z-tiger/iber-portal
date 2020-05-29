$(function(){

	function clearToolbar(){
		$('#toolbar').form('clear');
	}
    //清空
    $("#btnClear").bind("click",function(){
	clearToolbar();
    });
	$('#grid').datagrid({
		title : '违章管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'wz_records_city_member',
		queryParams:{
			'lpn':$("#s-lpn").textbox("getValue"),
			'custName':$("#s-custName").textbox("getValue"),
			'custPhone':$("#s-custPhone").textbox("getValue"),
			'status':$("#s-status").combobox("getValue"),
			'cityCode':$("#cityCode").combobox("getValue"),
			'type':$("#type").combobox("getValue"),
			'code':$("#code").combobox("getValue"),
			'orderId':$("#s-orderId").textbox("getValue"),
            'handle_type':$("#s-handle_type").combobox("getValue")
		},
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [  {
			field : 'ck',
			checkbox : true

		},{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.05,
			align : 'center'
	   },{
			field : 'type',
			title : '类型',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val) {
				if (val==2) {
					return "会员";
				}else if(val==1){
					return "员工";
				}else{
					return "";
				}
			}
		},{
			field : 'handleType',
			title : '处理类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val=="0") {
					return "用户处理";
				}else if(val=="1"){
					return "公司处理";
				}
			}
		},{
			field : 'custName',
			title : '违章姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'custPhone',
			title : '违章手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'orderId',
			title : '违章订单号',
			width : $(this).width() * 0.15,
			align : 'center'
		},{
			field : 'hphm',
			title : '车牌号码',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
			}
		}, {
			field : 'date',
			title : '违章时间',
			align : 'center'
		},{
			field : 'area',
			title : '违章地点',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'act',
			title : '违章行为',
			width : $(this).width() * 0.1,
            align : 'center',

		},{
			field : 'money',
			title : '违章罚款(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(null != val && val !='' && val != 'null' && val !='NULL'){
					var tmp = parseFloat(val);
					if(tmp == 0){
						return "0.00";
					}else{
						n = tmp.toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
					}
				}else{
					if(val == 0){
						return "0.00";
					}else{
						return val;
					}
				}
			}
		},{
			field : 'fen',
			title : '违章扣分',
			width : $(this).width() * 0.04,
			align : 'center'
		},{
			field : 'status',
			title : '处理状态',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val, rec) {
				/*if(val == "1") {return "未处理";}
				else {return "已处理";}*/
				if(val == "1"){
					return "未处理";
				}else if(val=="2"){
					return "已处理";
				}
			}
		},/*{
            field : 'archiveno',
            title : '文书编号',
            width : $(this).width() * 0.08,
            align : 'center'
        },*/{
			field : 'remark',
			title : '备注',
			width : $(this).width() * 0.08,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});

	
	$("#btnQuery").bind("click", function(){
		$("#grid").datagrid("load",{
			'lpn':$("#s-lpn").textbox("getValue"),
			'custName':$("#s-custName").textbox("getValue"),
			'custPhone':$("#s-custPhone").textbox("getValue"),
			'status':$("#s-status").combobox("getValue"),
			'orderId':$("#s-orderId").textbox("getValue"),
			'cityCode':$("#cityCode").combobox("getValue"),
			'type':$("#type").combobox("getValue"),
			'code':$("#code").combobox("getValue"),
            'handle_type':$("#s-handle_type").combobox("getValue")
		});
	});
	
	
	$("#btnExport").bind("click", function(){
          var cityCode = $("#code").combobox("getValue");
          var type = $("#type").combobox("getValue");
          var lpn = $("#s-lpn").textbox("getValue");
          var custName = $("#s-custName").textbox("getValue");
          var custPhone = $("#s-custPhone").textbox("getValue");
          var status = $("#s-status").combobox("getValue");
          var orderId = $("#s-orderId").textbox("getValue");
		  var handle_type = $("#s-handle_type").combobox("getValue");
    	  $("#queryWZRecords").form("submit", {
    			url : "export_WZ_Record_excel",
    			onSubmit : function(param) {
    				param.cityCode = cityCode;
    				param.status = status;
    				param.phone = custPhone;
    				param.name = custName;
    				param.orderId = orderId;
    				param.lpn = lpn;
    				param.type = type;
    				param.handle_type = handle_type;
    			},
    			success : function(result) {
    			}
    		});
	});
	
	$("#btnRepair").bind("click",function(){
		//$("#wzRecordForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		console.log(selectedRows)
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择记录", "error");
		 }/*if(selectedRows[0].status == "2"){
			 $.messager.alert("提示", "此违章已处理，请重新选择", "error");
		 }*/else{
			// alert(selectedRows[0].id);
			$("#ex_id").val(selectedRows[0].id);
			$("#ex_remark").textbox("setValue", "");
			$("#executeWZRerodsDialog").dialog("open");
		 }
	});
	
	$("#executeWZRerodsDialog").dialog( {
		width : "500",
		height : "220",
		top : "40",
		modal:true,
		title:"车辆违章处理",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#wzRecordForm").form("submit",{
					url:'wz_records_ex',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#executeWZRerodsDialog").dialog("close");
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
				$("#executeWZRerodsDialog").dialog("close");
			}
		}]
	});


    $("#btnSave").bind("click",function(){
        $("#addViewForm").form("clear");
        $("#addView").dialog("open");
    });

    $("#addView").dialog( {
        width : "480",
        height : "450",
        top : "100",
        left:'400',
        modal:true,
        title:"添加违章信息",
        buttons : [{
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                $("#addViewForm").form("submit",{
                    beforeSend:function () {
                        var tag =$('#addViewForm').form('validate');
                        if(tag) {
                            $.messager.progress({
                                text: "正在加载，请稍等！"
                            });
                        }else {
                            $.messager.alert("提示", "请根据提示输入正确的值", "erro");
                            return tag;
                        }
                    },
                    url:'wz_save',
                    success:function(result) {
                        var data = eval( '('+result+')' );
                        if (data.code == 1){
                            $.messager.progress('close');
                            $.messager.alert("提示",data.result,"info");
                            $("#addView").dialog('close');
                            $("#grid").datagrid("reload");
						}else{
                            $.messager.alert("提示",data.message,"error");
						}

                    }
                });
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addView").dialog("close");
            }
        }]
    });

    $("#btnEdit").bind("click",function(){
        $("#editViewForm").form("clear");
        var selectedRows = $("#grid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择需要修改的违章信息", "error");
        }else{
        	var selectedRow = selectedRows[0];
        	$("#e-id").val(selectedRow.id)
            $("#e-cityCode").combobox("setValue",selectedRow.cityCode);
            $("#e-type").combobox("setValue",selectedRow.type);
            $("#e-phone").textbox('setValue',selectedRow.custPhone)
            $("#e-name").textbox('setValue',selectedRow.custName)
            $("#e-orderId").combobox('setValue',selectedRow.orderId)
            $("#e-lpn").textbox('setValue',selectedRow.hphm)
            // $("#e-archiveno").textbox('setValue',selectedRow.archiveno)
            $("#e-act").textbox('setValue',selectedRow.act)
            $("#e-date").datetimebox('setValue',selectedRow.date)
            $("#e-area").textbox('setValue',selectedRow.area)
            $("#e-money").textbox('setValue',selectedRow.money)
            $("#e-point").textbox('setValue',selectedRow.fen)
            var phone_order = $("#e-orderId").combobox('getValue');
            console.log(phone_order)
            var index = phone_order.indexOf("_");
            var orderId = phone_order.substring(index+1);
            $("#e-lpn_orderId").val($("#e-lpn").textbox('getValue')+"_"+orderId);
            $("#editView").dialog("open").dialog("setTitle", "修改违章信息");
        }
    });

    $("#editView").dialog( {
        width : "480",
        height : "450",
        top : "100",
        left:'400',
        modal:true,
        title:"修改违章信息",
        buttons : [{
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                $("#editViewForm").form("submit",{
                    url:'wz_save',
                    beforeSend:function () {
                        var tag =$('#editViewForm').form('validate');
                        if(tag) {
                            $.messager.progress({
                                text: "正在加载，请稍等！"
                            });
                        }else {
                            $.messager.alert("提示", "请根据提示输入正确的值", "erro");
                            return tag;
                        }
                    },
                    success:function(result) {
                        var data = eval( '('+result+')' );
                        if (data.code == 1){
                            $.messager.progress('close');
                            $.messager.alert("提示",data.result,"info");
                            $("#editView").dialog('close');
                            $("#grid").datagrid("reload");
                        }else{
                            $.messager.alert("提示",data.message,"error");
                        }
                    }
                });
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#editView").dialog("close");
            }
        }]
    });
    $("input",$("#lpn").next("span")).blur(function(){

        //根据车牌加载违章订单号
        $("#orderId").combobox({
            url:'getOrderIdByLpn',
            method:'get',
            queryParams: {
            	'lpn':$("#lpn").textbox('getValue'),
				'type':$("#a-type").combobox('getValue')
            },
            valueField:'phone_orderId',
            textField:'orderId',
            onChange:function () {
                var phone_order = $("#orderId").combobox('getValue');
                var index = phone_order.indexOf("_");
                var phone = phone_order.substring(0,index);
                var orderId = phone_order.substring(index+1);
                $("#phone").textbox('setValue',phone);
				$("#lpn_orderId").val($("#lpn").textbox('getValue')+"_"+orderId);
            }
        })


    })

    $("input",$("#phone").next("span")).blur(function(){
        $.ajax({
            url:'getNameByPhone',
            data:{
                phone:$("#phone").textbox('getValue'),
                'type':$("#a-type").combobox('getValue')
            },
            type:'GET',
            success:function (result) {
                $("#name").textbox('setValue',result.data.name)
            }
        })
	});

    $("input",$("#e-lpn").next("span")).blur(function(){

        //根据车牌加载违章订单号
        $("#e-orderId").combobox({
            url:'getOrderIdByLpn',
            method:'get',
            queryParams:{
            	'lpn':$("#e-lpn").textbox('getValue'),
            	'type':$("#e-type").combobox('getValue')
            },
            valueField:'phone_orderId',
            textField:'orderId',
            onChange:function () {
                var phone_order = $("#e-orderId").combobox('getValue');
                var index = phone_order.indexOf("_");
                var phone = phone_order.substring(0,index);
                $("#e-phone").textbox('setValue',phone)
                var orderId = phone_order.substring(index+1);
                $("#e-lpn_orderId").val($("#e-lpn").textbox('getValue')+"_"+orderId);
            }
        })


    })

    $("input",$("#e-phone").next("span")).blur(function(){
        $.ajax({
            url:'getNameByPhone',
            data:{
                phone:$("#e-phone").textbox('getValue'),
                'type':$("#e-type").combobox('getValue')
            },
            type:'GET',
            success:function (result) {
                $("#e-name").textbox('setValue',result.data.name)
            }
        })
    });

});