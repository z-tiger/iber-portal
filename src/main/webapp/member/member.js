$(function(){
	//清空Combogrid
	$("#clearCombogrid").bind("click",function(){
		$("#enterpriseId").combogrid("clear");
	});
	
	//设置 企业
	$("#btnSetEnterprise").bind("click",function(){
		var selectedRow = $("#grid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要绑定企业的会员", "error");
   		}else{
   			var JsonData = selectedRow[0];
   			if("manager" == JsonData.memberLevel){
   				$.messager.alert("提示", "该用户为企业管理员", "error");
   			}else{
   				clearSetEnterpriseForm();
   				$("#set_enterprise_id").val(JsonData.id);
   				$("#setEnterpriseView").dialog("open").dialog("setTitle", "绑定企业");
   			}
  		}	
	});
	
	//构造对话框
	$("#setEnterpriseView").dialog( {
		width : "480",
		height : "240",
		top : "100",
		left:'400',
		modal:true,
		title:"绑定企业",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			
			handler : function() {
				$("#setEnterpriseForm").form("submit", {
					url : "member_set_enterprise.do",
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
						if (result == "success") {
							$.messager.alert("提示", "保存成功", "info");
						    $("#grid").datagrid("reload");
							$("#setEnterpriseView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#grid").datagrid("reload");
							$("#setEnterpriseView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#setEnterpriseView").dialog("close");
			}
		}]
	});
	
	$('#grid').datagrid({
		title : '会员管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'member_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'status':$("#s-status").combobox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue")
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
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'driverIdcardValidityTime',
			title : '驾驶证有效期',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'driverIdCardTime',
			title : '驾驶证初次领证日期',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'idcard',
			title : '身份证号码',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'driverIdcard',
			title : '驾驶证号码',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'uploadTime',
			title : '资料上传时间',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'driverIdcardPhotoUrl',
			title : '驾驶证照片',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value, row, index) {
				if(null == value || value == ""){
					return "未上传";
				}else{
					 //return "<span onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'>预览</span><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: absolute;'><img width='300'  src='"+value+"'/></div>";
					return "已上传";
				}
			}
		},{
            field : 'faceId',
            title : '人脸识别',
            width : $(this).width() * 0.06,
            align : 'center',
            formatter : function(val, rec) {
                if(null == val || val == ""){
                    return "未录入";
                }else{
                    return "已录入";
                }
            }
        },{
			field : 'fingerPrint',
			title : '指纹',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(null == val || val == ""){
					return "未录入";
				}else{
					return "已录入";
				}
			}
		},{
            field : 'tboxFingerPrint',
            title : '三代指纹',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(null == val || val == ""){
                    return "未录入";
                }else{
                    return "已录入";
                }
            }
        },{
			field : 'status',
			title : '状态',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "ready")
			       return "就绪";
			    else if(val == "experience")
			    	if(rec.refuseReason != null && rec.refuseReason != ''){
			    		return "体验 ("+rec.refuseReason+")";
			    	}else{
			    		return "体验";
			    	}
			    else if(val=="ordered")
			    	return "预约";
			    else if(val=="useCar")
			       return "用车";
			    else if(val=="planReturn")
			       return "计划还车";
			    else if(val=="waitQueue")
			       return "排队等待";
			    else if(val=="wait")
			        return "等待用车";
			     else if(val=="return")
			        return "还车";
			    else return val;   
			}
		},{
			field : 'registerIp',
			title : '注册IP',
			align : 'center'
		},{
			field : 'registerCategory',
			title : '注册终端',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "platform"){
					return "平台注册";
				}else if(val == "member"){
					return "安卓手机终端";
				}else if(val == "weixin"){
					return "微信";
				}else if(val == "ios"){
					return "IOS手机终端";
				}else if(val == "weibo"){
					return "微博";
				}else if(val == "qq"){
					return "QQ";
				}else{
					return "其它";
				}
			}
		},{
			field : 'accoutStatus',
			title : '资金状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val == "0") {
					return "正常";
				}else if(val == "1"){
					if (rec.refundMoney + rec.requiredDeposit > rec.deposit) {
						return "<span style='color:red'>冻结</span>";
					}else{
						return "正常";
					}
				}else if(val == "5"){
					return "<span style='color:red'>冻结</span>";
				}
			}
		},{
			field : 'enterpriseId',
			title : '会员类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val,rec){
				if(val=="0"){
					return "个人会员";
				}else{
					return "政企会员";
				}
			}
		},{
			field : 'levelCode',
			title : '会员等级',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val,rec){
				if(val=="0"){
					return "黑名单";
				}else if(val=="1"){
					return "一星会员";
				}else if(val=="2"){
					return "二星会员";
				}else if(val=="3"){
					return "三星会员";
				}else if(val=="4"){
					return "四星会员";
				}else if(val=="5"){
					return "五星会员";
				}
			}
		},{
			field : 'contributedVal',
			title : '会员贡献值',
			width : $(this).width() * 0.08,
			align : 'center'
		}/*,{
			field : 'memberLevel',
			title : '会员等级',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "account"){
					return "企业账号";
				}else if(val == "manager"){
					return "企业管理员";
				}else if(val == "person"){
					return "企业普通会员";
				}else if(val == "platinum"){
					return "白金企业用户";
				}else if(val == "general"){
					return "个人普通";
				}else if(val == "vip"){
					return "vip会员";
				}else if(val == "gold"){
					return "黄金会员";
				}else{
					return val;
				}
			}
		}*/
		,{
			field : 'createTime',
			title : '注册时间',
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	
	//add view
	$("#addView").dialog( {
		width : "480",
		height : "300",
		top : "100",
		left:'400',
		modal:true,
		title:"添加会员信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit",{
					url:'member_save',
					/*onSubmit:function(){
						var firstTime = $("#driverIdCardTime").datebox("getValue");
						var validTime = $("#driverIdcardValidityTime").datebox("getValue");
						if(firstTime != "" && validTime != ""){
							var start = new Date(firstTime);
							var end =new  Date(validTime);
							if(start.getTime() >= end.getTime()){
								$.messager.alert("提示", "初次领证时间必须小于驾驶证有效时间", "info");
								return false;
							}
						}
						return $(this).form("validate");
					},*/
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#addView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result== "phoneExist"){
							$.messager.alert("提示", "手机号码已存在", "error");
						}else if(result== "phoneFormatErr"){
							$.messager.alert("提示", "手机号码格式不正确", "error");
						}else if(result== "idCardFormatErr"){
							$.messager.alert("提示", "身份证号码格式错误", "error");
						}else if(result== "idCardExistsErr"){
							$.messager.alert("提示", "身份证号码已存在", "error");
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
	
	
	
	$("#editView").dialog( {
		width : "480",
		height : "450",
		top : "100",
		left:'400',
		modal:true,
		title:"修改会员信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				if("" != document.getElementById('phoneMsg').innerHTML){
					$.messager.alert("提示", document.getElementById('phoneMsg').innerHTML, "info");
				}else if("" != document.getElementById('idcardMsg').innerHTML){
					$.messager.alert("提示", document.getElementById('idcardMsg').innerHTML, "info");
				}else{
					$("#editViewForm").form("submit",{
						url:'member_edit',
						onSubmit:function(){
							var firstTime = $("#e-driverIdCardTime").datebox("getValue");
							var validTime = $("#e-driverIdcardValidityTime").datebox("getValue");
							if(firstTime != "" && validTime != ""){
								var start = new Date(firstTime);
								var end =new  Date(validTime);
								if(start.getTime() >= end.getTime()){
									$.messager.alert("提示", "初次领证时间必须小于驾驶证有效时间", "info");
									return false;
								}
							}
							return $(this).form("validate");
						},
						success:function(result) {
							if (result== "succ") {
								$.messager.alert("提示", "操作成功", "info");
								$("#grid").datagrid("reload");
								$("#grid").datagrid("clearSelections");
								$("#editView").dialog("close");
							} else if(result == "fail") {
								$.messager.alert("提示", "操作失败", "error");
							}else{
							    $.messager.alert("提示", result, "error");
							}
						}
					});
				}
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#grid").datagrid("clearSelections");
				$("#editView").dialog("close");
			}
		}]
	});

	//校验手机号是否已存在
	$("#e-phone").textbox('textbox').bind('blur', function(){
		var phone = $("#e-phone").textbox("getValue");
		if(0 < $.trim(phone).length){
			$.ajax({
				type: 'POST',
				url: "isExists",
				data: {
					id:$("#e-id").val(),
					type : 1,//1表示电话，2表示身份证号码
					value:phone
				},
				success: function(data){
					if("true" == data){
						$.messager.alert("提示", "手机号重复:" + phone, "info");
						document.getElementById('phoneMsg').innerHTML="手机号重复";
					}else{
						document.getElementById('phoneMsg').innerHTML="";
					}
				}
			})
		}
	});
	
	//校验身份证号码号是否已存在
	$("#e-idcard").textbox('textbox').bind('blur', function(){
		var idcard = $("#e-idcard").textbox("getValue");
		if(0 < $.trim(idcard).length){
			$.ajax({
				type: 'POST',
				url: "isExists",
				data: {
					id:$("#e-id").val(),
					type : 2,//1表示电话，2表示身份证号码
					value:idcard
				},
				success: function(data){
					if("true" == data){
						$.messager.alert("提示", "身份证号码重复:" + idcard, "info");
						document.getElementById('idcardMsg').innerHTML="身份证号码重复";
					}else{
						document.getElementById('idcardMsg').innerHTML="";
					}
				}
			})
		}
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("clearSelections");
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'status':$("#s-status").combobox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $("#scityCode").combobox("getValue");
	    var name = $("#s-name").textbox("getValue");
	    var status = $("#s-status").combobox("getValue");
	    var phone = $("#s-phone").textbox("getValue") ;
	    var bt = $("#s-bt").datetimebox("getValue");
	    var et = $("#s-et").datetimebox("getValue");
		$("#memberForm").form("submit", {
			url : "export_member_list?cityCode="+cityCode+"&name="+name+"&status="+status+"&phone="+phone+"&bt="+bt+"&et="+et,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	//clear
	$("#btnClear").bind("click",function(){
		$("#memberSearchForm").form("clear");
	});
	
	
	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#driverIdcardFilePhoto").attr("src","");
		$("#addView").dialog("open");
	});
	
	$("#btnEdit").bind("click",function(){
		$("#addViewForm").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要修改的用户", "error");
		}else{
			
			$("#e-id").val(selectedRows[0].id);
			$("#e-phone").textbox("setValue",selectedRows[0].phone);
			$("#e-cityCode").combobox("setValue",selectedRows[0].cityCode);
			$("#e-name").textbox("setValue",selectedRows[0].name);
			$("#e-sex").combobox("setValue",selectedRows[0].sex);
			$("#e-email").textbox("setValue",selectedRows[0].email);
			$("#e-idcard").textbox("setValue",selectedRows[0].idcard);
			$("#e-driverIdcard").textbox("setValue",selectedRows[0].driverIdcard);
			$("#edriverIdcardFilePhoto").attr("src", selectedRows[0].driverIdcardPhotoUrl);
			$("#e-driverIdCardTime").datebox("setValue",selectedRows[0].driverIdCardTime);
			$("#eIdcardFilePhoto").attr("src", selectedRows[0].idcardPhotoUrl);
			$("#e-driverIdcardValidityTime").datebox("setValue",selectedRows[0].driverIdcardValidityTime);
			$("#editView").dialog("open");
			document.getElementById('phoneMsg').innerHTML="";
			document.getElementById('idcardMsg').innerHTML="";
		}
	});

	//删除用户人脸信息
    $('#btnFace').on("click",function () {
        $("#deleteFaceView").form("clear");
        var selectedRow = $("#grid").datagrid("getSelected");
        // console.log("selectedRow :" + selectedRow);
        if(selectedRow == null){
            $.messager.alert("提示", "请选择需要删除人脸的用户", "error");
            return;
        }
        // console.log("selectedRow.faceID:"+selectedRow.faceId);
        if($.trim(selectedRow.faceId)==''||selectedRow.faceId==null){
            $.messager.alert("提示", "用户未录入人脸识别信息", "error");
        }else{
            $("#m_id").val(selectedRow.id);
            $("#m_idCard").val(selectedRow.idcard);
            $("#m_cityCode").val(selectedRow.cityCode);
            $("#idCardSpan1").html(selectedRow.idcard.substring(0,6));
            $("#idCardSpan2").html(selectedRow.idcard.substring(6,14));
            $("#idCardSpan3").html(selectedRow.idcard.substring(14));
            $("#nameSpan").html(selectedRow.name);
            $("#deleteFaceView").dialog("open");
        }
    });
	
	//删除用户指纹信息
	$("#btnFinger").bind("click",function(){
		$("#deleteFingerView").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要删除指纹的用户", "error");
			return;
		}

        const selectedRow = selectedRows[0];
        if(!selectedRow.fingerPrint && !selectedRow.tboxFingerPrint){
			 $.messager.alert("提示", "用户未录入指纹", "error");
		}else{
			$("#e_id").val(selectedRow.id);
			$("#e_idCard").val(selectedRow.idcard);
			$("#e_cityCode").val(selectedRow.cityCode);
			$("#sp1").html(selectedRow.idcard.substring(0,6));
			$("#sp2").html(selectedRow.idcard.substring(6,14));
			$("#sp3").html(selectedRow.idcard.substring(14));
			$("#sp4").html(selectedRow.name);
			$("#deleteFingerView").dialog("open");
		}
		/*var selectedRows = $("#grid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除指纹的用户", "error");
		 }else if(selectedRows[0].fingerPrint == null || selectedRows[0].fingerPrint == ""){
			 $.messager.alert("提示", "用户未录入指纹", "error");
		 }else{
			 var _id =  selectedRows[0].id;
			 var _idcard = selectedRows[0].idcard;
				$.messager.confirm("提示","确定要删除吗?",function(r){
					if(r){
						$.post("member_del_finger",{id:_id,idcard:_idcard},function(data){
							if(data == "succ"){
								$.messager.alert("提示", "操作成功", "info");
								$("#grid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }*/
	});
	/*删除人脸对话框*/
    $("#deleteFaceView").dialog({
        width : "400",
        height : "280",
        top : "200",
        left:"600",
        modal:true,
        title:"删除人脸",
        buttons : [{
            text : "属实并删除人脸",
            iconCls : "icon-ok",
            id:'examineBtn',
            handler : function() {
                var _id = $("#m_id").val();
                var _idcard = $("#m_idCard").val();
                var reason = $('#deleteFaceReason').combobox("getText");
                // var reason = $("#deleteFaceReason option:selected").text();
                var cityCode = $("#m_cityCode").val();
                $.messager.progress();
                $.post("member_del_face",
                    {id:_id,idcard:_idcard,
                        reason:reason,
                        cityCode:cityCode
                        },function(data){
                        $.messager.progress('close');
                        console.log(data);
                        console.log("datamessage:" + data.message);
                        if(data.code == 1){
                            $.messager.alert("提示", "该会员人脸信息已删除!", "info");
                            $("#grid").datagrid("reload");
                            $('#deleteFingerAndFaceLogDatagrid').datagrid('reload');
                        }else {
                            $.messager.alert("提示", data.message, "info");
                        }
                        $("#deleteFaceView").dialog("close");
                    },"json");
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#deleteFaceView").dialog("close");
            }
        }]
    });
    /*删除指纹对话框*/
	$("#deleteFingerView").dialog({
		width : "400",
		height : "280",
		top : "200",
		left:"600",
		modal:true,
		title:"会员认证",
		buttons : [{
			text : "属实并删除指纹",
			iconCls : "icon-ok",
			id:'examineBtn',
			handler : function() {
				var _id = $("#e_id").val();
				var _idcard = $("#e_idCard").val();
				var reason = $("#reason option:selected").text();
				var cityCode = $("#e_cityCode").val();
				var clearAll = $("#allCheck").prop("checked");
				$.post("member_del_finger",
					{id:_id,idcard:_idcard,
						reason:reason,
						cityCode:cityCode,
                        clearAll:clearAll},function(data){
					if(data && data.code === 1){
						$.messager.alert("提示", "该会员指纹已删除!", "info");
						$("#grid").datagrid("reload");
						$("#deleteFingerLogDatagrid").datagrid("reload");
					}else if (data.message){
						 $.messager.alert("提示", data.message, "info");
					}else {
                        $.messager.alert("提示", "操作失败！", "info");
					}
					$("#deleteFingerView").dialog("close");
				},"json");
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#deleteFingerView").dialog("close");
			}
		}]
	});
	
	//用户重置密码
	$("#btnRestPassword").bind("click",function(){
		var selectedRows = $("#grid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要重置密码的用户", "error");
		 }else{
			 var _id =  selectedRows[0].id;
				$.messager.confirm("提示","确定要重置吗?<br/><br/><b>重置后的密码为手机号码后6位</b>",function(r){
					if(r){
						$.post("member_rest_password",{id:_id},function(data){
							if(data == "succ"){
								$.messager.alert("提示", "操作成功", "info");
								$("#grid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }
	});
	
	
	$("#examineView").dialog( {
		width : "650",
		height : "530",
		top : "40",
		modal:true,
		title:"会员实名认证及驾照审核",
		buttons : [{
			text : "实名认证",
			iconCls : "icon-ok",
			id:'examineBtn',
			handler : function() {
				var _name = $("#examine_name").textbox("getValue");
				var _idcard = $("#examine_idcard").textbox("getValue");
				$.post("member_examine",{name:_name, idcard:_idcard},function(data){
					if(data == "1"){
						$("#examine_result_1").html("实名认证通过");
					}else{
						$("#examine_result_2").html("实名认证不通过");
					}
				},"text");
				$("#examineBtn").linkbutton({ disabled: true });
			}
		},{
			text : "审核",
			iconCls : "icon-save",
			handler : function() {
				$("#examineForm").form("submit",{
					url:'member_examine_save',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#examineView").dialog("close");
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
				$("#examineView").dialog("close");
			}
		}]
	});
	
	
	//审核
	$("#btnExamine").bind("click",function(){
		$("#examineForm").form("clear");
		$("#examineBtn").linkbutton({ disabled: false });
		$("#examine_result_1").html("");
		$("#examine_result_2").html("");
		var selectedRows = $("#grid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择实名认证及驾照审核的用户", "error");
		 }else if(selectedRows[0].driverIdcard == "" || selectedRows[0].driverIdcardPhotoUrl == "" ){
			 $.messager.alert("提示", "该会员资料不完善，请先完善资料", "error");
		 }else if(selectedRows[0].status != "experience"){
			 $.messager.alert("提示", "该会员已经审核通过", "error");
		 }else{
			    $("#examine_id").val(selectedRows[0].id);
			    $("#examine_name").textbox("setValue",selectedRows[0].name);
				$("#examine_phone").textbox("setValue",selectedRows[0].phone);
				$("#examine_idcard").textbox("setValue",selectedRows[0].idcard);
				$("#examine_driverIdcard").textbox("setValue",selectedRows[0].driverIdcard);
				//alert(selectedRows[0].driverIdcardPhotoUrl);
				$("#examine_driverIdcardPhotoUrl").attr("src", selectedRows[0].driverIdcardPhotoUrl);
			 $("#examineView").dialog("open");
		 }
	});
	
	$("#btnQueryDriver").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'status':$("#s-status").combobox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue"),
			'driverIdcardValidityTime':'driverIdcardValidityTime'
		});
	});
	$("#deleteFingerQuery").bind("click",function(){
		$("#deleteFingerLogView").dialog("open");
	});
	$("#deleteFingerLogView").dialog( {
		width : "900",
		height : "600",
		top : "100",
		left:'400',
		modal:true,
		title:"指纹人脸删除日志",
        onBeforeClose:function () {
            $('#deleteFingerLogForm').form('clear');
        },
        onClose:function () {
            reloadFingerAndFaceLog();
        }

	});
	$("#deleteFingerAndFaceLogDatagrid").datagrid({
		width : 'auto',
		height : '500',
		fit : false,
		fitColumns : true,
		nowrap : true,
        // cache:false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'getAllDeleteLog.do',
		pageSize : 20,
		idField : 'id',
		columns : [ [{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.12,
			align : 'center'
		} , {
			field : 'name',
			title : '会员姓名',
			width : $(this).width() * 0.12,
			align : 'center'
		}, 
		{
			field : 'phone',
			title : '会员号码',
			width : $(this).width() * 0.15,
			align : 'center',
		},
        {
                field : 'deleteType',
                title : '删除类型',
                width : $(this).width() * 0.32,
                align : 'center',
                formatter : function(val) {
                    if (val==1) {
                        return "指纹删除";
                    }else {
                        return "人脸删除";
                    }
                }
            },
		{
			field : 'reason',   
			title : '删除原因 ',
			width : $(this).width() * 0.32,
			align : 'center',
			formatter : function(val) {
				if (val) {
					return val;
				}else {
					return "无";
				}
			}
		},{
			field : 'createName',   
			title : '操作人',
			width : $(this).width() * 0.12,
			align : 'center',
		},{
			field : 'createTime',   
			title : '操作时间',
			width : $(this).width() * 0.22,
			align : 'center',
		}
		] ],
		pagination : true,
		rownumbers : true
	})
	$("#btnDeleteLogQuery").bind("click",function(){
		$("#deleteFingerAndFaceLogDatagrid").datagrid("load",{
			'cityCode':$("#d_cityCode").combobox("getValue"),
			'name':$("#d_name").textbox("getValue"),
			'phone':$("#d_phone").textbox("getValue"),
            'deleteType':$('#deleteType').combobox("getValue")
		});

	});

	$('#fingerAndFaceLogClear').bind('click',function () {
        $('#deleteFingerLogForm').form('clear');
    });


});

function reloadFingerAndFaceLog() {
    $("#deleteFingerAndFaceLogDatagrid").datagrid("load",{
        'cityCode':$("#d_cityCode").combobox("getValue"),
        'name':$("#d_name").textbox("getValue"),
        'phone':$("#d_phone").textbox("getValue"),
        'deleteType':$('#deleteType').combobox("getValue")
    });
}

  


//检查图片的格式是否正确,同时实现预览  
function setImagePreview(obj, localImagId, imgObjPreview) {  
	//alert(imgObjPreview.src);
    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); //可以上传的文件类型  
    if (obj.value == '') {  
        $.messager.alert("让选择要上传的图片!");  
        return false;  
    }  
    else {  
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
                    imgObjPreview.style.width = '160px';  
                    imgObjPreview.style.height = '120px';  
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式   
                    imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
                }  
                else {  
                    //IE下，使用滤镜   
                    obj.select();  
                    var imgSrc = document.selection.createRange().text;  
                    //必须设置初始大小   
                    localImagId.style.display = "";
                    localImagId.style.width = "160px";  
                    localImagId.style.height = "120px";  
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



//显示图片    
function over(imgid, obj) {  
	//大图显示的最大尺寸  4比3的大小 160 120
    maxwidth = 160;  
    maxheight = 120;  
    //1、宽和高都超过了，看谁超过的多，谁超的多就将谁设置为最大值，其余策略按照2、3    
    //2、如果宽超过了并且高没有超，设置宽为最大值    
    //3、如果宽没超过并且高超过了，设置高为最大值    
		if (img.width > maxwidth && img.height > maxheight) {  
	    	pare = (img.width - maxwidth) - (img.height - maxheight);  
	        if (pare >= 0){
				img.width = maxwidth;  
	        }else{
				img.height = maxheight;
	        }  
		}else if (img.width > maxwidth && img.height <= maxheight) {  
            img.width = maxwidth;
	    }else if(img.width <= maxwidth && img.height > maxheight){
	    	img.height = maxheight;
	    }
}

function clearSetEnterpriseForm(){
	$('#setEnterpriseForm').form('clear');
}


function display(id){
	document.getElementById("box"+id).style.display="block"; 
}
function disappear(id){
	document.getElementById("box"+id).style.display="none"; 
}