$(function() {
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $("#s-cityCode").combobox("getValue");
	    var behaviorParentId = $("#s-behaviorTypeName").combobox("getValue");
	    var lpn = $("#s-lpn").textbox("getValue");
	    var reportedMemberName = $("#s-reportedMemberName").textbox("getValue");
	    var createName = $("#s-createName").textbox("getValue");
	    var status = $("#s-status").combobox("getValue");
		$("#creditForm").form("submit", {
			url : "export_credit_report?cityCode=" + cityCode
					+ "&behaviorParentId=" + behaviorParentId
					+ "&lpn=" + lpn + "&reportedMemberName="
					+ reportedMemberName + "&createName="
					+ createName + "&status=" + status,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	$("#grid").datagrid({
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck : true,
		checkOnSelect : true,
		url : 'credit_list',
		queryParams:{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'behaviorParentId':$("#s-behaviorTypeName").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'reportedMemberName':$("#s-reportedMemberName").textbox("getValue"),
			'createName':$("#s-createName").textbox("getValue"),
			'status': $("#s-status").combobox("getValue")
		},
		pageSize : 20,
		idField : 'id',
		pagination : true,
		rownumbers : true,
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '区域',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'reportedMemberName',
			title : '被追究会员姓名',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'reportedPhone',
			title : '手机号码',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'parkName',
			title : '网点',
			width : $(this).width() * 0.1,
			align : 'left'
		}, {
			field : 'parkNo',
			title : '车位号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'behaviorType',
			title : '分类',
			width : $(this).width() * 0.1,
			align : 'left'
		}, {
			field : 'behaviorName',
			title : '子分类',
			width : $(this).width() * 0.15,
			align : 'left'
		}, {
			field : 'createName',
			title : '反馈人',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'isMemberComplain',
			title : '反馈人身份',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,rec) {
				return getMemberType(val);
			}
		}, {
			field : 'createTime',
			title : '反馈时间',
			width : $(this).width() * 0.2,
			align : 'center'
			
		}, {
			field : 'status',
			title : '审核状态',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val,rec) {
				var result = "";
				switch (val) {
				case 0:
					result="未审核";
					break;
				case 1:
					result="<span style='cursor: pointer;color: #2779AA;' onclick='lookDetail(\""+rec.id+"\")'>通过</span>";
					break;
				case 2:
					result="<span style='cursor: pointer;color: #2779AA;' onclick='lookDetail(\""+rec.id+"\")'>不通过<span>";
					break;
				default:
					break;
				}
				return result;
			}
		}  ] ]
	});

	var getCheckStatus = function(val){
		var result = "";
		switch (val) {
		case 0:
			result="未审核";
			break;
		case 1:
			result="通过";
			break;
		case 2:
			result="不通过";
			break;
		default:
			break;
		}
		return result;
	}
	var getMemberType = function(val){
		var result = "";
		switch (val) {
		case 0:
			result="用户";
			break;
		case 1:
			result="员工";
			break;
		case 2:
			result="客服";
			break;
		default:
			break;
		}
		return result;
	};
	
	var reloadGrid = function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'behaviorParentId':$("#s-behaviorTypeName").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'reportedMemberName':$("#s-reportedMemberName").textbox("getValue"),
			'createName':$("#s-createName").textbox("getValue"),
			'status': $("#s-status").combobox("getValue")
		});
	}
	
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'behaviorParentId':$("#s-behaviorTypeName").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'reportedMemberName':$("#s-reportedMemberName").textbox("getValue"),
			'createName':$("#s-createName").textbox("getValue"),
			'status': $("#s-status").combobox("getValue")
		});
	});
	
	$("#btnEdit").bind("click",function(){
		
		var selectedRow = $("#grid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
   			$("#addMemberCreditForm").form('clear');
   			$(".evidence_img").html("");
   			$("#e_cityName").text("");
   			$("#e_remark").text("");
   			var JsonData = selectedRow[0];
   			$("#e_id").val(JsonData.id);
   			getReportEvidenceImg(JsonData.id);
   			$('#e_cityName').text(JsonData.cityName);
   			$('#e_behavior_parent_id').combobox("setValue",JsonData.behaviorParentId);
   			$('#e_behavior_parent_id').combobox("setValue",JsonData.behaviorChildrenId);
   			dynamicSetChildrenBehavior(JsonData.behaviorParentId,JsonData.behaviorChildrenId,"e_behavior_children_id");
   			$("#e_reportedMemberName").textbox("setValue",JsonData.reportedMemberName);
   			$("#e_reportedPhone").textbox("setValue",JsonData.reportedPhone);
   			if(JsonData.typeCode != null && JsonData.typeCode.indexOf("CHARGING_PILE") >= 0 ){//租车
   				$("#e_park_name").textbox("setValue",JsonData.parkName);
   				$("#e_parkNo").textbox("setValue",JsonData.parkNo);
   				$("#e_car_show").hide();
   				$("#e_park_show").show();
   			}else{//充电桩
   				$("#e_lpn").textbox("setValue",JsonData.lpn);
   				$("#e_park_show").hide();
   				$("#e_car_show").show();
   			}
   			
   			$("#e_remark").html(JsonData.remark);
   			$("#editMemberCreditView").dialog("open").dialog("setTitle", "编辑记录");
   		}
	});
	
	/**
	 * 编辑弹框
	 */
	$("#editMemberCreditView").dialog({
		width : "520",
		height : '490',
		top : "90",
		left:'400',
		modal:true,
		title:"新增信用记录",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editMemberCreditForm").form("submit",{
					url:'editMemberReport',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						 var json = JSON.parse(result);
							if("success" == json.status){
								$.messager.alert("提示", "保存成功", "info");
								 $("#editMemberCreditView").dialog("close");
								 reloadGrid();
							}else{
								$.messager.alert("提示", json.message, "error");	
							}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#editMemberCreditView").dialog("close");
			}
		}]
	});
	
	
	
	var dynamicSetChildrenBehavior = function(parentId,childrenId,objId){
		 $.ajax({  
	           type : "post",  
	            url : "getCanAddBehaviorByTypeId?",  
	            data:{"parentId":parentId},
	            async : false,   //采用同步
	            success : function(data){
	            	var json = JSON.parse(data);
	    			$("#"+objId).combobox("loadData", json);
	    			$('#'+objId).combobox("setValue",childrenId);
	            }  
	      });
//		$.post("getCanAddBehaviorByTypeId",{"parentId":parentId},function(data){
//			var json = JSON.parse(data);
//			$("#"+objId).combobox("loadData", json);
//			$('#'+objId).combobox("setValue",childrenId);
//		},"text");   
	};
	
	$("#behavior_parent_id").combobox({onChange: function (n,o) {
		var parentId = $("#behavior_parent_id").combobox("getValue");
		$.post("getCanAddBehaviorByTypeId",{"parentId":parentId},function(data){
			var json = JSON.parse(data);
			$("#behavior_children_id").combobox("loadData", json);
			$("#behavior_children_id").combobox("setText", "");
		},"text");
	}
	});
	
	$("#c_behavior_parent_id").combobox({onChange: function (n,o) {
		var parentId = $("#c_behavior_parent_id").combobox("getValue");
		var isMemberComplain = $("#c_isMemberComplain").val();
		$.post("getVaildBehaviorByTypeId",{"parentId":parentId,"isMemberComplain":isMemberComplain},
				function(data){
					var json = JSON.parse(data);
					$("#c_behavior_children_id").combobox("loadData", json);
					$("#c_behavior_children_id").combobox("setText", "");
				},
		"text");
	}
	});
	
	$("#e_behavior_parent_id").combobox({onChange: function (n,o) {
		var parentId = $("#e_behavior_parent_id").combobox("getValue");
		$.post("getCanAddBehaviorByTypeId",{"parentId":parentId},function(data){
			var json = JSON.parse(data);
			$("#e_behavior_children_id").combobox("loadData", json);
			$("#e_behavior_children_id").combobox("setText", "");
		},"text");
	}
	});

	
	
	$("#behavior_children_id").combobox({onChange: function (n,o) {
		var id = $("#behavior_children_id").combobox("getValue");
		$.post("getTypeCodeById",{"id":id},function(data){
			if( data.indexOf("CHARGING_PILE") >= 0 ){
				$("#car_show").hide();
				$("#park_show").show();
			}else{
				$("#car_show").show();
				$("#park_show").hide();
			}
			if(data.indexOf("CIVILIZED_OTHER_THING") >= 0){
				$("#other_show").show();
			}else{
				$("#other_show").hide();
			}
		},"text");
	}
	});
	
	$("#btnSave").bind("click",function(){
		$("#addMemberCreditForm").form("clear");
		$("#boxfile1").hide();
		$("#boxfile2").hide();
		$("#boxfile3").hide();
		$("#add_img").html('<div id="reportFileImg3"  style="display: none;">'
				+'<img  style=" width:25%;float: left;height:120px;" id="reportFilePhoto3" />'
				+'</div>'
				+'<div id="reportFileImg2"  style="display: none;">'
				+	'<img   style=" width:25%;float: left;height:120px;"  id="reportFilePhoto2" />'
				+'</div>'
				+'<div id="reportFileImg1"  style="display: none;">'
				+	'<img  style=" width:25%;float: left;height:120px;" id="reportFilePhoto1" />'
				+'</div>'
				+'<div id="reportFileImg"  style="display: none;">'
				+	'<img   style=" width:25%;float: left;height:120px;"  id="reportFilePhoto" />'
				+'</div>');
		$("#addMemberCreditView").dialog("open");
		$("#car_show").show();
		$("#park_show").hide();
		$("#other_show").hide();
		
	});
	
	
	/**
	 * 新增弹框
	 */
	$("#LookMemberCreditView").dialog({
		width : "520",
		height : '490',
		top : "90",
		left:'400',
		modal:true,
		title:"查看审核结果"
	});
	
	$("#addMemberCreditView").dialog({
		width : "520",
		height : '490',
		top : "90",
		left:'400',
		modal:true,
		title:"新增信用记录",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				var parentId = $("#behavior_parent_id").combobox("getValue");
				var isImportment = false;
				$.ajax({  
			           type : "post",  
			            url : "getSeveritiyBehavior",  
			            data:{parentId:parentId},
			            async : false,   //采用同步
			            success : function(data){ 
			            	if("success" == data){
			            		isImportment = true;
			            	}
			            }  
			      });
				if(isImportment){
					$.messager.confirm("提示","确定将会员列入黑名单吗?",function(r){
			      		if(r){
			      			addMemberCredit();
			      		}
					});
				}else{
					addMemberCredit();
				}
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#boxfile1").hide();
				$("#boxfile2").hide();
				$("#boxfile3").hide();
				$("#reportFileImg").empty();
				$("#reportFileImg1").empty();
				$("#reportFileImg2").empty();
				$("#reportFileImg3").empty();
				$("#add_img").html('');
				$("#addMemberCreditForm").form("clear");
				$("#addMemberCreditView").dialog("close");
			}
		}]
	});
	
	var addMemberCredit = function(){
		$("#addMemberCreditForm").form("submit",{
			url:'addMemberCredit',
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result) {
				var json = JSON.parse(result);
				if("success" == json.status){
					$.messager.alert("提示", "操作成功", "info");
					$("#addMemberCreditView").dialog("close");
					reloadGrid();
				}else{
					$.messager.alert("提示", json.message, "error");	
				}
			}
		});
	}

/*	$("#objType").combobox({onChange: function (n,o) {
		var objType = $("#objType").combobox("getValue");
		if("1"== objType){
			$("#car_show").show();
			$("#park_show").hide();
		}else if('2' == objType){
			$("#car_show").hide();
			$("#park_show").show();
		}
	}
	});*/
	
//	$('#reportedMemberName').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
//	 	delay:1000,// 1s延时查询
//	 	editable : true,
//		panelHeight: 'auto', 
//		panelWidth: 300,
//		idField: 'id',
//		textField: 'reportedMemberName',
//		url:'reportedByName_list',
//		required:true,
//		columns : [ [ {
//			field : 'reportedMemberName',
//			title : '用户名称',
//			width : '80%',
//			align : 'center'
//		}] ],
//		pagination : false,//是否分页
//		rownumbers : false,//序号
//		keyHandler : {
//			query : function(reportedMemberName) { // 动态搜索处理
//				console.log(reportedMemberName)
//				$('#reportedMemberName').combogrid("grid").datagrid('options').queryParams = 
//					JSON.parse('{"reportedMemberName":"' + reportedMemberName + '"}');
//				// 重新加载
//				$('#reportedMemberName').combogrid("grid").datagrid("reload");
//				$('#reportedMemberName').combogrid("setValue",reportedMemberName);
//				 
//			}
//		}
//	});
//	
	
	var getReportEvidenceImg = function(id){
		$.post("getReportEvidenceImg",{"reportId":id},function(data){
			var json = JSON.parse(data);
			if(json && json.length > 0){
				for(var index = 0 ; index < json.length; index++){
					$(".evidence_img").append("<a href='drierCarImgOpen?img="+json[index].pictureEvidenceUrl+"' target='_blank'><img  style='width:25%;float: left;height:120px;' src='"+json[index].pictureEvidenceUrl+"'/></a>");
				}
			}
		},"text");
	};
	
	$("#btnExamine").bind("click",function(){
		var selectedRow = $("#grid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要审核的记录", "error");
   		}else{
   			$("#checkMemberCreditForm").form('clear');
   			$(".evidence_img").html("");
   			$("#c_cityName").text("");
   			$("#c_remark").text("");
   			$("#c_id").val("");
   			$("#c_type").val("");
   			$("#c_createId").val("");
   			$("#c_isMemberComplain").val("");
   			$("#c_memberId").val("");
   			$("#c_orderId").val("");
   			$("#c_park_no").val("");
   			$("#c_park_name").val("");
   			$("#c_lpn").val("");
   			var JsonData = selectedRow[0];
   			if(JsonData.status != 0){
   				$.messager.alert("提示", "该记录已经审核", "info");
   				return;
   			}
   			loadBehaviorParent(JsonData.isMemberComplain);
   			$("#c_id").val(JsonData.id);
   			$("#c_type").val(JsonData.typeCode);
   			$("#c_createId").val(JsonData.createId);
   			$("#c_isMemberComplain").val(JsonData.isMemberComplain);
   			getReportEvidenceImg(JsonData.id);
   			$('#c_cityName').text(JsonData.cityName);
   			$('#c_behavior_parent_id').combobox("setValue",JsonData.behaviorParentId);
   			
   			$("#c_behavior_children_id").combobox("loadData", "getVaildBehaviorByTypeId?parentId="+JsonData.behaviorParentId+"&isMemberComplain="+JsonData.isMemberComplain);
	   			setTimeout(function(){
	   				$("#c_behavior_children_id").combobox("setValue",JsonData.behaviorChildrenId);
	   				$("#c_behavior_children_id").combobox("setText",JsonData.behaviorName);
	   			}, 500);
   			$("#c_reportedMemberName").textbox("setValue",JsonData.reportedMemberName);
   			$("#c_reportedPhone").textbox("setValue",JsonData.reportedPhone);
   			if(JsonData.typeCode != null && JsonData.typeCode.indexOf("CHARGING_PILE") >= 0 ){//租车
   				$("#c_park_name").textbox("setValue",JsonData.parkName);
   				$("#c_park_no").textbox("setValue",JsonData.parkNo);
   				$("#c_car_show").hide();
   				$("#c_park_show").show();
   			}else{
   				$("#c_lpn").textbox("setValue",JsonData.lpn);
   				$("#c_park_show").hide();
   				$("#c_car_show").show();
   			}
   			
   			$("#c_remark").text(JsonData.remark);
   			$("#c_isMemberComplainName").text(getMemberType(JsonData.isMemberComplain));
   			$("#c_create_name").text(JsonData.createName);
   			$("#c_create_time").text(JsonData.createTime);
   			$("#checkMemberCreditView").dialog("open").dialog("setTitle", "审核反馈记录");
   		}
	});
	
	var loadBehaviorParent = function (isMemberComplain){
		 $.ajax({  
	           type : "post",  
	            url : "getValidBehaviorType_list?",  
	            data:{isMemberComplain:isMemberComplain},
	            async : false,   //采用同步
	            success : function(data){
	            	var json = JSON.parse(data);
	    			$("#c_behavior_parent_id").combobox("loadData", json);
	            }  
	      });
	};
	
	var checkMemberCredit = function(){
		$("#checkMemberCreditForm").form("submit",{
			url:'auditMemberCreditPass',
			onSubmit:function(){
				$("#checkMemberCreditView").dialog("close");
				return $(this).form("validate");
			},
			success:function(result) {
				var json = JSON.parse(result);
				if(json.code == 1){
					$.messager.alert("提示", "操作成功", "info");
					reloadGrid();
					
				}else{
					$.messager.alert("提示", json.message, "error");	
				}
			}
		});
	}
	
	/**
	 * 审核弹框
	 */
	$("#checkMemberCreditView").dialog({
		width : "520",
		height : '490',
		top : "90",
		left:'400',
		modal:true,
		title:"新增信用记录",
		buttons : [{
			text : "审核通过",
			iconCls : "icon-save",
			handler : function() {
				var orderId = $("#c_orderId").val();
				if(orderId == ''){
					$.messager.alert("提示", "请先关联订单后再通过", "info");
					return ;
				}
				var parentId = $("#c_behavior_parent_id").combobox("getValue");
				var isImportment = false;
				console.log(this);
				$.ajax({  
			           type : "post",  
			            url : "getSeveritiyBehavior",  
			            data:{parentId:parentId},
			            async : false,   //采用同步
                        beforeSend:function () {
                            /*var tag =$(this).form("validate");
                            if(tag) {
                                $.messager.progress();
                            }
                            return tag;*/
                            $.messager.progress();

                        },
			            success : function(data){
                            $.messager.progress('close');
			            	if("success" == data){
			            		isImportment = true;
								$.messager.confirm("提示","确定将会员列入黑名单吗?",function(r){
						      		if(r){
						      			checkMemberCredit();
						      		}
								});
			            	}else {
			            		checkMemberCredit();
			            	}
			            }  
			      });
			}
		},{
			text : "审核不通过",
			iconCls : "icon-save",
			handler : function() {

				$("#checkMemberCreditForm").form("submit",{
					url:'auditMemberCreditRefuse',
					onSubmit:function(){
					    var tag =$(this).form("validate");
					    if(tag) {
                            $.messager.progress();
                        }
						return tag;
					},
					success:function(result) {
                        $.messager.progress('close');
                        console.log(result);
                        var json = eval('(' + result + ')');
                        if(json.code ==1){
							$.messager.alert("提示", "操作成功", "info");
							reloadGrid();
							$("#checkMemberCreditView").dialog("close");
						}else{
							$.messager.alert("提示", json.message, "error");	
						}
					}
				});
			
			}
		}]
	});

	$("#getOrderInfoView").dialog({
		width : "800",
		height : '490',
		top : "90",
		left:'400',
		modal:true,
		buttons : [{text : "确定",
					iconCls : "icon-save",
					handler : function() {
						var selectedRow = $("#getOrderInfoGrid").datagrid("getSelections");
						if(selectedRow.length <= 0){
							$.messager.alert("提示", "请选择要关联的记录", "error");
				   		}else{
				   			$("#c_reportedMemberName").textbox('setValue',"");
							$("#c_reportedPhone").textbox('setValue',"");
							$("#c_memberId").val("");
							$("#c_parkId").val("");
							$("#c_park_no").textbox('setValue',"");
							$("#c_orderId").val("");
				   			var JsonData = selectedRow[0];
							$("#c_reportedMemberName").textbox('setValue',JsonData.memberName);
							$("#c_reportedPhone").textbox('setValue',JsonData.memberPhone);
							$("#c_memberId").val(JsonData.memberId);
							$("#c_parkId").val(JsonData.parkId);
							$("#c_park_no").textbox('setValue',JsonData.parkNo);
							$("#c_orderId").val(JsonData.orderId);
							$("#getOrderInfoView").dialog("close");
				   		}
					}},{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#getOrderInfoView").dialog("close");
						}
					}]
	});
	
	$(".getOrderInfo").bind("click",function(){
	
		//关联订单
		var id = $("#c_id").val();
		var typeCode = $("#c_type").val();
		var behaviorParentId =$('#c_behavior_parent_id').combobox("getValue");
		var behaviorChildrenId = $('#c_behavior_children_id').combobox("getValue");
		var lpn =$('#c_lpn').textbox("getValue");
		var parkName = $('#c_park_name').textbox("getValue");
		var parkNo = $('#c_park_no').textbox("getValue");
		if(typeCode != null && typeCode.indexOf("CHARGING_PILE") >= 0 ){
			if(parkName == ''){
				$.messager.alert("提示", '网点信息不能为空', "info");	return;
			}
		}else{
			if(lpn == ''){
				$.messager.alert("提示", '车牌信息不能为空', "info");	return;
			}
		}
		$("#getOrderInfoView").dialog("open").dialog("setTitle", "订单关联");
		
		//获取管理订单信息列表
		$("#getOrderInfoGrid").datagrid({
			width : 'auto',
			height : 'auto',
			fit : true,
			fitColumns : true,
			nowrap : false,
			striped : true,
			collapsible : true,
			rownumbers : true,
			singleSelect : true,
			selectOnCheck : true,
			checkOnSelect : true,
			url : 'getOrderInfo',
			queryParams:{
				"id":id,
				"typeCode":typeCode,
				"behaviorParentId":behaviorParentId,
				"behaviorChildrenId":behaviorChildrenId,
				"lpn":lpn,
				"parkName":parkName,
				"parkNo":parkNo
			},
			pageNumber:1,
			pageSize : 10,
			idField : 'orderId',
			pagination : true,
			rownumbers : true,
			columns : [ [ {
				field : 'ck',
				checkbox : true

			}, {
				field : 'memberName',
				title : '会员姓名',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'memberPhone',
				title : '会员电话',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'orderId',
				title : '订单号',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'orderType',
				title : '订单类型',
				width : $(this).width() * 0.25,
				align : 'center',
				formatter :function(val){
					if('TS' ==val){return "时租";}else if('DR'==val){return '日租';} else if('CH'== val){return '充电';}
				}
			}, {
				field : 'createTime',
				title : '时间',
				width : $(this).width() * 0.25,
				align : 'center'
			}] ]
		});
		$("#getOrderInfoGrid").datagrid("clearSelections");
//		$.post("getOrderInfo",
//				{
//					"id":id,
//					"typeCode":typeCode,
//					"behaviorParentId":behaviorParentId,
//					"behaviorChildrenId":behaviorChildrenId,
//					"lpn":lpn,
//					"parkName":parkName,
//					"parkNo":parkNo
//				},
//		function(data){
////			var json = JSON.parse(data);
////			if(json.data){
////				$("#c_reportedMemberName").textbox('setValue',json.data.memberName);
////				$("#c_reportedPhone").textbox('setValue',json.data.memberPhone);
////				$("#c_memberId").val(json.data.memberId);
////				$("#c_parkId").val(json.data.parkId);
////				$("#c_park_no").textbox('setValue',json.data.parkNo);
////				$("#c_orderId").val(json.data.orderId);
////			}else{
////				$.messager.alert("提示", '关联失败', "info");	;
////			}
//		},"text");
	});
});
function clearLookDetail(){
	$(".evidence_img_detail").html("");
	$("#d_cityName").html("");
	$("#d_behavior_parent_name").html("");
	$("#d_behavior_children_name").html("");
	$("#d_reportedMemberName").html("");
	$("#d_reportedPhone").html("");
	$("#d_lpn").html("");
	$("#d_park_name").html("");
	$("#d_park_no").html("");
	$("#d_remark").html("");
	$("#d_createName").html("");
	$("#d_ismember").html("");
	$("#d_createTime").html("");
	$("#d_auditExplain").html("");
	$("#d_auditName").html("");
	$("#d_auditTime").html("");
	$("#d_status").html("");
}
function lookDetail(id){
	clearLookDetail();
	$.post("getRepordsDetail",{"id":id},
		function(data){
			var json = JSON.parse(data);
			if("success" == json.status){
				if(json.dataList && json.dataList.length>0){
					for(var index = 0 ; index < json.dataList.length; index++){
						$(".evidence_img_detail").append("<a href='drierCarImgOpen?img="+json.dataList[index].pictureEvidenceUrl+"' target='_blank'><img  style='width:25%;float: left;height:120px;' src='"+json.dataList[index].pictureEvidenceUrl+"'/></a>");
					}
				}
				if(json.data){
					console.log(json.data);
					$("#d_cityName").text(json.data.cityName);
					$("#d_behavior_parent_name").text(json.data.behaviorType);
					$("#d_behavior_children_name").text(json.data.behaviorName);
					$("#d_reportedMemberName").text(json.data.reportedMemberName);
					$("#d_reportedPhone").text(json.data.reportedPhone);
					$("#d_lpn").text(json.data.lpn);
					$("#d_park_name").text(json.data.parkName);
					$("#d_park_no").text(json.data.parkNo);
					$("#d_remark").text(json.data.remark);
					if(json.data.isMemberComplain == 1){
						$("#d_ismember").text("员工");
						$("#d_createName").text(json.data.createName);
					}else if(json.data.isMemberComplain == 2){
						$("#d_ismember").text("客服");
						$("#d_createName").text(json.data.auditName);
					}else{
						$("#d_ismember").text("会员");
						$("#d_createName").text(json.data.createName);
					}
					$("#d_auditTime").text(formatDate(json.data.auditTime));
					$("#d_createTime").text(formatDate(json.data.createTime));
					$("#d_auditExplain").text(json.data.auditExplain);
					$("#d_auditName").text(json.data.auditName);
					if(json.data.status == 1 ){
						$("#d_status").text("通过");
					}else if(json.data.status == 2 ){
						$("#d_status").text("不通过");
					}else{
						$("#d_status").text("未审核");
					}
					
				}
				$("#LookMemberCreditView").dialog("open");
			}else{
				$.messager.alert("提示", json.message, "info");	
			}
		},"text");
}

//检查图片的格式是否正确,同时实现预览  
function setImagePreview(obj, localImagId, imgObjPreview,num) {  
		//alert(imgObjPreview.src);
	var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); //可以上传的文件类型  
	if (obj.value == '') {  
		$("#boxfile"+num).hide();
		imgObjPreview.style.display = 'none'; 
	    return false;  
	} else {  
	    var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; //这个文件类型正则很有用   
	    ////布尔型变量  
	    var isExists = false;  
	    //循环判断图片的格式是否正确  
	    for (var i in array) {  
		        if (fileContentType.toLowerCase() == array[i].toLowerCase()) {  
		            //图片格式正确之后，根据浏览器的不同设置图片的大小  
			            if (obj.files && obj.files[0]) {  
				                //火狐下，直接设img属性   
			                imgObjPreview.style.display = 'block';  
			               // imgObjPreview.style.width = '160px';  
			               // imgObjPreview.style.height = '120px';  
			                //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式   
			                imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
				         }else {  
		                //IE下，使用滤镜   
			                obj.select();  
			                var imgSrc = document.selection.createRange().text;  
			                //必须设置初始大小   
			                localImagId.style.display = "";
			               // localImagId.style.width = "160px";  
			                //localImagId.style.height = "120px";  
			                //图片异常的捕捉，防止用户修改后缀来伪造图片   
			                try {  
			                    localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
			                    localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;  
			                }  
			                catch (e) {  
			                    $.messager.alert("您上传的图片格式不正确，请重新选择!");  
			                    return false;  
			                }  
			                imgObjPreview.style.display = 'none';  
			                document.selection.empty();  
			            }  
	            isExists = true;
	            if(num > 0){
	            	$("#boxfile"+num).show();
	            	$("#reportFileImg"+( num > 1 ? num-1:"")).show()
	            }
	            return true;  
		    }  
		 }  
	    if (isExists == false) {  
	        $.messager.alert("上传图片类型不正确!");  
	        return false;  
	    }  
	    return false;  
	}  
} 