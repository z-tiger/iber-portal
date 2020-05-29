$(function() {
	//list
	$('#carMrgGrid').datagrid({
		title : '车辆信息管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_mrg_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'status':$("#status").combobox("getValue")
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
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) +"•" +val.substring(2);
				}else{
					return val;
				}
				
			} 
		},{
			field : 'status',
			title : '车辆状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val){
				if(val == true){
					return "启用";
				}else{
					return "关闭";
				}
			}
		},{
			field : 'engineno',
			title : '发动机号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'classno',
			title : '车架号（VIN）',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'carImgUri',
			title : '图片',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value, row, index){
			   if(value != ""){
			      return "<img width='50' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: fixed;top:100px;'><img width='300'  src='"+value+"'/></div>";
			   }
			}
			
		}, {
			field : 'smallCarImgUri',
			title : '车辆小图片',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value, row, index){
			   if(value != ""){
			      return "<img width='50' height='40' src='"+value+"' onmouseover='display_box("+row.id+")' onmouseout='disappear_box("+row.id+")'/><div id='box-"+row.id+"' onmouseover='display_box("+row.id+")' onmouseout='disappear_box("+row.id+")' style='display: none;position: fixed;top:100px;'><img width='300'  src='"+value+"'/></div>";
			   }
			}
			
		}, {
			field : 'insuranceFileUri',
			title : '行驶证反面',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value, row, index){
			   if(value != ""){
			      return "<img width='50' height='40' src='"+value+"' onmouseover='displayInsurance("+row.id+")' onmouseout='hideInsurance("+row.id+")'/><div id='insurance"+row.id+"' onmouseover='displayInsurance("+row.id+")' onmouseout='hideInsurance("+row.id+")' style='display: none;position: fixed;top:10px;left:500px;'><img width='800'  src='"+value+"'/></div>";
			   }
			}
			
		}, {
			field : 'drivingLicenseFileUri',
			title : '行驶证正面',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value, row, index){
			   if(value != ""){
			      return "<img width='50' height='40' src='"+value+"' onmouseover='displayDrivLic("+row.id+")' onmouseout='hideDrivLic("+row.id+")'/><div id='license"+row.id+"' onmouseover='displayDrivLic("+row.id+")' onmouseout='hideDrivLic("+row.id+")' style='display: none;position: fixed;top:10px;left:500px;'><img width='800'  src='"+value+"'/></div>";
			   }
			}
			
		},{
			field : 'insuranceStrongUri',
			title : '交强险图片',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value, row, index){
			   if(value != ""){
			      return "<img width='50' height='40' src='"+value+"' onmouseover='displayForcedInsurancePic("+row.id+")' onmouseout='hideForcedInsurancePic("+row.id+")'/><div id='forcedInsurance"+row.id+"' onmouseover='displayForcedInsurancePic("+row.id+")' onmouseout='hideForcedInsurancePic("+row.id+")' style='display: none;position: fixed;top:10px;left:500px;'><img width='800'  src='"+value+"'/></div>";
			   }
			}
			
		},{
			field : 'insuranceBusUri',
			title : '商业险图片',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value, row, index){
			   if(value != ""){
			      return "<img width='50' height='40' src='"+value+"' onmouseover='displayCommercialInsurancePic("+row.id+")' onmouseout='hideCommercialInsurancePic("+row.id+")'/><div id='CommercialInsurance"+row.id+"' onmouseover='displayCommercialInsurancePic("+row.id+")' onmouseout='hideCommercialInsurancePic("+row.id+")' style='display: none;position: fixed;top:10px;left:500px;'><img width='800'  src='"+value+"'/></div>";
			   }
			}
			
		},
		/*{
			field : 'carRunStatus',
			title : '使用状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "empty" || val == "" || val == null || val == "null" || val == "NULL") {return "空闲"}
				else if(val == "repair") {return "维修中"}
				else {return "使用中"}
			}
		}, {
			field : 'restBattery',
			title : '剩余电量',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'parkName',
			title : '所在网点',
			align : 'center'
		},*/
		{
			field : 'carTypeTypeName',
			title : '车辆类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "ELECTRIC") {return "电动"}
				if(val == "FUEL") {return "燃油"}
				if(val == "MIXEDPOWER") {return "混合动力"}
			}
		},{
			field : 'carTypeBrance',
			title : '车辆品牌',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'carTypeType',
			title : '车辆型号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'color',
			title : '车牌颜色',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'annualInspectionTime',
			title : '车辆年检时间',
			width : $(this).width() * 0.14,
			align : 'center',
			styler:function(value,row){
				if (row.annualInspectionTime <new Date().Format("yyyy-MM-dd hh:mm:ss")){
					return 'color:red';
				}
				if(row.annualInspectionTime <new Date().Format("yyyy-MM-dd hh:mm:ss")){
					return 'color:red';
				}
			}
		},{
			field : 'seats',
			title : '座厢数',
			width : $(this).width() * 0.08,
			align : 'center'  
		},{
			field : 'mileage',
			title : '最大续航(公里)',
			align : 'center',
			formatter : function(val, rec) {
				if(!val){
					return "0";
				}else{
					n = parseInt(val).toFixed(0);
		   			var re = /(\d{1,3})(?=(\d{3}))/g;
	       			return n.replace(re, "$1,");
				}
			}
		},{
			field : 'speed',
			title : '最高时速(公里/小时)',
			align : 'center'
		},{
			field : 'isNav',
			title : '导航',
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1") {return "是";}
				else {return "否";}
			}
		},{
			field : 'isCall',
			title : '一键呼叫',
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1") {return "是";}
				else {return "否";}
			}
		},{
			field : 'isTripRecord',
			title : '行车记录',
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1") {return "是";}
				else {return "否";}
			}
		},{
			field : 'owner',
			title : '车辆所属',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "OWNERER") {return "自有车辆"}
				if(val == "TESTER") {return "测试车辆"}
			}
		},{
			field : 'phone',
			title : '车载电话',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'bluetoothNo',
			title : '蓝牙编号',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'boxType',
			title : '盒子类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 0) {return "北汽"}
				if(val == 1) {return "奇瑞"}
				if(val == 2) {return "众泰"}
				/*添加了盒子类为3 是东风*/
				if(val == 3) { return "东风" }

			}
		}, {
			field : 'tboxVersion',
			title : 'tboxVersion',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'tboxImei',
			title : 'tboxImei',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'remark',
			title : '备注原因',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'userName',
			title : '操作人',
			width : $(this).width() * 0.08,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});
	
//	$("#setStatus").bind("click",function(){
//		 var selectedRows = $("#carMrgGrid").datagrid("getSelections");
//		 if(true == selectedRows[0].status){
//			 var val  = "车辆启用状态,确定关闭吗?";
//		 }else if(false == selectedRows[0].status){
//			 var val =  "车辆关闭状态,确定启用吗?";
//		 }
//		 if(selectedRows.length <= 0){
//				$.messager.alert("提示", "请选择车辆", "error");
//		 }else{
//			 $.messager.confirm("提示",val,function(r){
//					if(r){
//						$.post("set_car_status?lpn="+selectedRows[0].lpn+"&status="+selectedRows[0].status,function(data){
//							if(data == "closeErr"){
//								$.messager.alert("提示", "车辆正在运营中,不可关闭", "error");
//							}else if(data == "closeSucc"){
//								$.messager.alert("提示", "车辆关闭成功", "info");
//								$("#carMrgGrid").datagrid("reload");
//							}else if(data == "openErr"){
//								$.messager.alert("提示", "车辆启用失败", "error");
//								$("#carMrgGrid").datagrid("reload");
//							}else if(data == "openSucc"){
//								$.messager.alert("提示", "车辆启用成功", "info");
//								$("#carMrgGrid").datagrid("reload");
//							}else{
//								 $.messager.alert("提示", "操作失败", "error");
//							}
//						},"text");
//					}
//				});
//		 }
//	})
	$("#setStatus").bind("click",function(){
		 $("#addRemarkForm").form("clear");
		 var selectedRows = $("#carMrgGrid").datagrid("getSelections");
	     if(selectedRows.length <= 0){
	    	 $.messager.alert("提示", "请选择车辆", "error");
		 }else{
			 if(true == selectedRows[0].status){
				 $("#confirm").html("<font color=red>"+"车辆当前状态:启用中"+"</font>");
			 }
			$("#r-lpn").val(selectedRows[0].lpn);
			$("#r-status").val(selectedRows[0].status);
			$("#r-remark").val(selectedRows[0].remark);
			$("#addRemarkView").dialog({title:"添加关闭车辆原因"});
			$("#addRemarkView").dialog("open")
		 }
	});
	$("#addRemarkView").dialog( {
		width : "280",
		height : "200",
		top : "150",
		left:"650",
		modal:true,
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addRemarkForm").form("submit",{
					url:'set_car_status',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						var data = eval('(' + result + ')');
						if (data.status == "succ") {
							$.messager.alert("提示", data.msg, "info");
							$("#addRemarkView").dialog("close");
							$("#carMrgGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", data.msg, "error");
						} 
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#addRemarkView").dialog("close");
			}
		}]
	});
	//add view
	$("#addView").dialog( {
		width : "580",
		height : "480",
		top : "100",
		left:"400",
		modal:true,
		title:"添加车辆信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit",{
					url:'car_mrg_save',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carMrgGrid").datagrid("reload");
							$("#addView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else if(result == "existException"){
							$.messager.alert("提示", "车牌号已经存在", "error");
						} else if(result == "classNoErr"){
							$.messager.alert("提示", "车架号字母和数字17位组合", "error");
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
				$("#addView").dialog("close");
			}
		}]
	});
	
	
	//edit view
	$("#editView").dialog( {
		width : "480",
		height : "480",
		top : "100",
		left:"400",
		modal:true,
		title:"修改车辆信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit",{
					url:'car_mrg_edit',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carMrgGrid").datagrid("reload");
							$("#editView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result == "classNoErr"){
							$.messager.alert("提示", "车架号字母和数字17位组合", "error");
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
				$("#editView").dialog("close");
			}
		}]
	});


    //imeiView
    const $imeiView = $("#imeiView");
    $imeiView.dialog( {
        width : "620",
        height : "150",
        top : "100",
        left:"400",
        modal:true,
        title:"tbox绑定车牌",
        buttons : [{
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                $("#imeiViewForm").form("submit",{
                    url:'bindTboxImei',
                    success:function(data) {
                        if(!data){
                            $.messager.alert("提示", "操作失败！", "info");
                            $imeiView.dialog("close");
                            return;
						}
                        const parse = JSON.parse(data);
                        if (parse.code === 1){
                            $.messager.alert("提示", "绑定成功！", "info");
                        }else {
                            $.messager.alert("提示", parse.message, "info");
						}
						$imeiView.dialog("close");
                        reload();
                    }
                });
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $imeiView.dialog("close");
            }
        }]
    });

	$("#uploadFileView").dialog( {
		width : "400",
		height : "220",
		top : "40",
		modal:true,
		title:"批量导入车辆信息",
		buttons : [{
			text : "导入",
			iconCls : "icon-save",
			handler : function() {
				$("#uploadFileForm").form("submit",{
					url:'inport_car',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carMrgGrid").datagrid("reload");
							$("#uploadFileView").dialog("close");
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
				$("#uploadFileView").dialog("close");
			}
		}]
	});
	
	//query
	$("#btnQuery").bind("click",reload);

	function reload(){
        $("#carMrgGrid").datagrid("load",{
            'cityCode':$("#scityCode").combobox("getValue"),
            'lpn':$("#s-lpn").textbox("getValue"),
			'status':$("#status").combobox("getValue")
        });
	}
	
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $("#scityCode").combobox("getValue");
	    var lpn = $("#s-lpn").textbox("getValue");
	    var status = $("#status").combobox("getValue");
		$("#carMrgForm").form("submit", {
			url : "export_carMrg_list?cityCode="+cityCode+"&lpn="+lpn+"&status="+status,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	
    //annualInspectionTimeQuery  年检临期快捷查询
	$("#annualInspectionBtnQuery").bind("click",function(){
		$("#carMrgGrid").datagrid("load",{
		'shortcut':$("#q-shortcut").textbox("getValue")
		});
	});
	
	
	//clear
	$("#btnClear").bind("click",function(){
		/*$("#scarMrgForm").form("clear");*/
	});
	
	//save
	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#add-isNav").combobox("setValue","1");
		$("#add-isCall").combobox("setValue","1");
		$("#add-isTripRecord").combobox("setValue","1");
		$("#tboxVersionSelect").val(2);
		$("#addInsuranceImg").attr("src", "");
		$("#addDrivingLicenseImg").attr("src", "");
		$("#addInsuranceStrongImg").attr("src","");
		$("#addinsuranceBusImg").attr("src","");
		$("#addView").dialog("open");
	});
	
	
	//edit
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#carMrgGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-lpn").textbox("setValue",selectedRows[0].lpn.substring(0,2) + "•" + selectedRows[0].lpn.substring(2));
			$("#e-cityCode").combobox("setValue",selectedRows[0].cityCode);
			$("#e-modelId").combobox("setValue",selectedRows[0].modelId);
			$("#e-bluetoothNo").textbox("setValue",selectedRows[0].bluetoothNo);
			// $("#e-eLpn").textbox("setValue",selectedRows[0].eLpn);
			// $("#e-iosUuid").textbox("setValue",selectedRows[0].iosUuid);
             $("#tboxImei").textbox("setValue",selectedRows[0].tboxImei);
             $("#tboxVersion").val(selectedRows[0].tboxVersion);
			$("#e-color").combobox("setValue",selectedRows[0].color);
			$("#e-seats").textbox("setValue",selectedRows[0].seats);
			$("#e-speed").textbox("setValue",selectedRows[0].speed);
			$("#e-mileage").textbox("setValue",selectedRows[0].mileage);
			$("#e_annualInspectionTime").datebox("setValue",selectedRows[0].annualInspectionTime);
			
			var _isNav = selectedRows[0].isNav;
			if(_isNav == "" || _isNav == null || _isNav == "null" || _isNav == "NULL"){
				_isNav = "0";
			}
			var _isCall = selectedRows[0].isCall;
			if(_isCall == "" || _isCall == null || _isCall == "null" || _isCall == "NULL"){
				_isCall = "0";
			}
			var _isTripRecord = selectedRows[0].isTripRecord;
			if(_isTripRecord == "" || _isTripRecord == null || _isTripRecord == "null" || _isTripRecord == "NULL"){
				_isTripRecord = "0";
			}
			$("#e-isNav").combobox("setValue",_isNav);
			$("#e-isCall").combobox("setValue",_isCall);
			$("#e-isTripRecord").combobox("setValue",_isTripRecord);
			$("#e-phone").textbox("setValue",selectedRows[0].phone);
			$("#e-owner").combobox("setValue",selectedRows[0].owner);
			$("#e-parkId").combobox("setValue",selectedRows[0].parkId);
			$("#e-engineno").textbox("setValue",selectedRows[0].engineno);
			$("#e-classno").textbox("setValue",selectedRows[0].classno);
			$("#adPhotoPreview").attr("src", selectedRows[0].carImgUri);
			$("#addSmallPhotoPreview").attr("src", selectedRows[0].smallCarImgUri);
			
			$("#addInsurancePreview").attr("src", selectedRows[0].insuranceFileUri);
			$("#addDrivingLicensePreview").attr("src", selectedRows[0].drivingLicenseFileUri);
			$("#addInsuranceStrongPreview").attr("src", selectedRows[0].insuranceStrongUri);
			$("#addinsuranceBusPreview").attr("src", selectedRows[0].insuranceBusUri);
			$("#editView").dialog("open");
		 }
	});
	
//	//del
//	$("#btnDelete").bind("click",function(){
//		var selectedRows = $("#carMrgGrid").datagrid("getSelections");
//		 if(selectedRows.length <= 0){
//				$.messager.alert("提示", "请选择要删除的记录", "error");
//		 }else{
//			var _id =  selectedRows[0].id;
//			$.messager.confirm("提示","确定要删除吗?",function(r){
//				if(r){
//					$.post("car_mrg_del",{id:_id},function(data){
//						if(data == "succ"){
//							$.messager.alert("提示", "操作成功", "info");
//							$("#carMrgGrid").datagrid("reload");
//						}else{
//							 $.messager.alert("提示", "操作失败", "error");
//						}
//					},"text");
//				}
//			});
//		 }
//	});

	// 第三代产品tbox绑定车牌
	$("#btnBind").click(function () {
        var selectedRows = $("#carMrgGrid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择要绑定的车牌", "error");
        }else{
            // 获取车牌、imei、产品代数
            const selectedRow = selectedRows[0];
            var tboxImei =  selectedRow.tboxImei;
            var tboxVersion =  selectedRow.tboxVersion;
            if (tboxImei){
                $.messager.alert("提示", "车牌已经绑定！", "info");
                return;
			}
            if (tboxVersion !== 3){
                $.messager.alert("提示", "不是三代tbox产品！", "info");
                return;
			}

            // 弹出列表
            $imeiView.dialog("open");
            $("#lpn").val(selectedRow.lpn);

            // getNotBindImeis();
        }
    });

	// 第三代产品tbox解除绑定
	$("#btnUnBind").click(function () {
        var selectedRows = $("#carMrgGrid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择要解除绑定的车牌", "info");
        }else{
            // 获取车牌、imei、产品代数
            const selectedRow = selectedRows[0];
            var tboxImei =  $.trim(selectedRow.tboxImei);
            var tboxVersion =  selectedRow.tboxVersion;
            if (!tboxImei){
                $.messager.alert("提示", "车牌还未绑定！", "info");
                return;
			}
            if (tboxVersion !== 3){
                $.messager.alert("提示", "不是三代tbox产品！", "info");
                return;
			}

            const lpn = selectedRow.lpn;
            $.messager.confirm("提示","确定要解除"+lpn+"绑定吗?",function(r){
                if(r){
                    $.post("unBindTboxImei",
						{lpn:lpn, imei:tboxImei},
						function(data){
                            if(!data){
                                $.messager.alert("提示", "操作失败！", "info");
                                return;
                            }
                            if (data.code === 1){
                                $.messager.alert("提示", "解除绑定成功！", "info");
                                reload();
                            }else {
                                $.messager.alert("提示", data.message, "info");
                            }
                    },"json");
                }
            });
        }
    });

	$("#imeiReload").click(function () {
        $('#imeiSelect').combobox('reload');
    });

    /**获取未绑定的imei集合 */
    // function getNotBindImeis() {
    //     // 查询imei
    //     $.getJSON("getNotBindImeis",function (data) {
    //         $("#imeiSelect").empty();
    //         if (data && data.length){
    //             $.each(data,function (index,item) {
    //                 $("#imeiSelect").append("<option value='"+item+"'>");
    //             })
    //         }
    //     });
    // }

	function display(id){
		document.getElementById("box"+id).style.display="block"; 
	}
	function disappear(id){
		document.getElementById("box"+id).style.display="none"; 
	}
	
	//保险单和行驶证 forced insurance ForcedInsurancePic
	function displayInsurance(id){
		document.getElementById("insurance"+id).style.display="block"; 
	}
	function displayDrivLic(id){
		document.getElementById("license"+id).style.display="block"; 
	}
	function hideInsurance(id){
		document.getElementById("insurance"+id).style.display="none"; 
	}
	function hideDrivLic(id){
		document.getElementById("license"+id).style.display="none"; 
	}
	$("#btnDownload").bind("click",function(){
		window.location.href = "car_inport_template_file_download";
	});
	
	//inport car info
	$("#btnImport").bind("click",function(){
		$("#uploadFileForm").form("clear");
		$("#uploadFileView").dialog("open");
	});
	//时间格式化function：
	Date.prototype.Format = function(format){ 
	var o = { 
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	}

	if(/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	}
	for(var k in o) { 
	if(new RegExp("("+ k +")").test(format)) { 
	format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
	 } 
	} 
	return format; 
	}
});