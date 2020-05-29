function clearToolbar(){
			$('#toolbar').form('clear');
		}
$(function() {
	
	$("#branchOfficeQuery").bind("click",function(){
	    $("#branchOfficeDetailDataGrid").datagrid("load",{
	    	'parentId':$("#parentId").val(),
			'enterprise_name':$("#branchOfficeName").textbox("getValue")
		});
	});

	//清空
	$("#clearQuery").bind("click",function(){
		clearToolbar();
	}); 
	//查询链接
	$(document).keydown(function(event){
	    var enterprise_name = $("input[name='_enterprise_name']").val();
	    var city_code = $("input[name='_city_code']").val();
		if(event.keyCode==13){
		  $('#dataGrid').datagrid('load',{
	           enterprise_name : enterprise_name,
	           city_code : city_code
          });
		}
	});
	//查询链接
	$("#btnQuery").bind("click",function(){
		var enterprise_name = $("input[name='_enterprise_name']").val();
	    var city_code = $("input[name='_city_code']").val();
	    $('#dataGrid').datagrid('load',{
    	   enterprise_name : enterprise_name,
	       city_code : city_code
        });
	});
	//企业管理列表
	$('#dataGrid').datagrid( {
		title : '企业管理',
		width : 'auto',
		height : 'auto',
		fit:true,
		fitColumns: false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'enterprise_list.do',
		pageSize : 100,
		pageList : [100,50,30,10],
		pageIndex:1,
		idField : 'id',
		columns : [ [{
			field : 'ck',
			checkbox:true
		},   
		  {
			field : 'cityName',
			title : '地区',
			width : $(this).width() * 0.08,
			align : 'center'
			
		},{
			field : 'enterpriseName',
			title : '企业名称',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		},
// 				{  
// 					field : 'businessLicense',   
// 					title : '营业执照编号',
// 					width : $(this).width() * 0.2,
// 					align : 'center',
// 					formatter : function(val, rec) {
// 						if(val == "null")
// 					       return "";
// 					    else
// 					       return val;
// 					}
// 				} ,

		{  
			field : 'enterpriseMemberNum',   
			title : '企业会员数',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,row,index) {
				if(val == "null")
			       return "";
			    else
			       return "<a href=\"javascript:showMember("+row.id+")\" >"+val+"</a>";
			}
		},
        /*{
            field : 'isContactPark',
            title : '是否关联网点',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val,row,index) {
                if(val == 0)
                    return "否";
                else
                    return "是"
            }
        },*/
		{  
			field : 'enterpriseParkNum',   
			title : '企业所属网点',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val,row,index) {
			       return "<a href=\"javascript:showPark("+row.id+")\" >"+val+"</a>";
			}
		},
		{
                field : 'enterpriseCarNum',
                title : '企业所属车辆',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter : function(val,row,index) {
                    return "<a href=\"javascript:showEnterpriseCar("+row.id+")\" >"+val+"</a>";
                }
         },
         {
             field : 'branchOfficeNum',
             title : '旗下分公司',
             width : $(this).width() * 0.08,
             align : 'center',
             formatter : function(val,row,index) {
                 // return "<a href=\"javascript:showBranchOffice("+row.id+","+"'')\" >"+val+"</a>";
                 return "<a href=\"javascript:showBranchOffice("+row.id+")\" >"+val+"</a>";;
             }
         },
         {
			field : 'totalRecharge',
			title : '企业充值总额(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val,row,index) {
				if(val == "null")
			       return "";
			    else
			       n = (val/100).toFixed(2);
				   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
			       return n.replace(re, "$1,");
			}
		},
		{  
			field : 'totalConsumption',
			title : '企业消费总额(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val,row,index) {
				if(val == "null")
			       return "";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
			        return n.replace(re, "$1,");
			}
		},
		{  
			field : 'totalRefundSum',
			title : '企业退款总额(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val,row,index) {
				if(val == "null")
			       return "";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
			        return n.replace(re, "$1,");
			}
		},
		{  
			field : 'accountMoney',
			title : '账户金额(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
			        return n.replace(re, "$1,");
			}
		},
		{  
			field : 'totalDeposit',
			title : '账户押金(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			    	n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
			        return n.replace(re, "$1,");
			}
		},{
                field : 'depositLimit',
                title : '企业应缴押金金额（元）',
                width : $(this).width() * 0.1,
                editor:'numberbox',
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                        n = (val/100).toFixed(2);
                    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                    return n.replace(re, "$1,");
                }
            },{
                field : 'overdraftMoney',
                title : '透支金额（元）',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                        n = (val/100).toFixed(2);
                    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                    return n.replace(re, "$1,");
                }
            },{
                field : 'canOverdraftMoney',
                title : '可透支金额（元）',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                        n = (val/100).toFixed(2);
                    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                    return n.replace(re, "$1,");
                }
            },{
                field : 'overdraftNum',
                title : '透支次数',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                        return val
                }
            },{
                field : 'canOverdraftNum',
                title : '可透支次数',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                       return val
                }
            },

            {
                field : 'admin',
                title : '管理员姓名',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                        return val;
                }
            },
            {
                field : 'adminMobile',
                title : '管理员手机号',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null")
                        return "";
                    else
                        return val;
                }
            },
		/*{
			field : 'levelName',   
			title : '企业等级',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		},*/
		{  
			field : 'address',   
			title : '企业地址',
			width : $(this).width() * 0.25,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		},{
		field : 'legalPerson',
			title : '企业法人',
			width : $(this).width() * 0.1,
			align : 'center'
		} , {
			field : 'enterpriseTel',
			title : '企业电话',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		} 
		] ],
		pagination : true,
		rownumbers : true
	});
	
	//调度员明细对话框
	$("#branchOfficeDetailDataGrid").datagrid( {
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'enterprise_list.do',
		pageSize : 20,
		idField : 'id',
		columns : [ [
		        {
		     			field : 'cityName',
		     			title : '地区',
		     			width : $(this).width() * 0.08,
		     			align : 'center'
		     			
		     	},
		     	{
		     			field : 'enterpriseName',
		     			title : '企业名称',
		     			width : $(this).width() * 0.1,
		     			align : 'center',
		     			formatter : function(val, rec) {
		     				if(val == "null")
		     			       return "";
		     			    else
		     			       return val;
		     			}
		     	},
		     	{  
					field : 'accountMoney',
					title : '账户金额(元)',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					    	n = (val/100).toFixed(2);
						    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					        return n.replace(re, "$1,");
					}
				},
				{  
					field : 'totalDeposit',
					title : '账户押金(元)',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					    	n = (val/100).toFixed(2);
						    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
					        return n.replace(re, "$1,");
					}
				}
		] ],
		pagination : true,
		rownumbers : true
	});
	
	
	//添加用户链接
	$("#btnAdd").bind("click",function(){
		clearForm();
		//enterprisePhoto.src = "";
		$("#enterprisePhoto").attr("src", "");
		document.getElementById('enterprise_tel_msg').innerHTML = "";
		document.getElementById('admin_mobile_msg').innerHTML = "";
	 	$("#addView").dialog("open").dialog("setTitle", "添加企业");
	});
	
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的企业", "error");
   		}else{
		   	var JsonData = selectedRow[0];
		   	document.getElementById('enterprise_tel_msg').innerHTML = "";
			document.getElementById('admin_mobile_msg').innerHTML = "";
			$("#id").val(JsonData.id);
			$("#city_code").combobox("setValue",JsonData.cityCode);
			// $("#level").combogrid("setValue",JsonData.level);
			$("#enterprise_name").textbox("setValue",JsonData.enterpriseName);
			$("#admin").textbox("setValue",JsonData.admin);
			$("#admin_mobile").textbox("setValue",JsonData.adminMobile);
			$("#legal_person").textbox("setValue",JsonData.legalPerson);
			$("#enterprise_tel").textbox("setValue",JsonData.enterpriseTel);
			$("#address").textbox("setValue",JsonData.address);
			$("#business_license").textbox("setValue",JsonData.businessLicense);
//			enterprisePhoto.src = JsonData.licenseFileUrl;
			$("#enterprisePhoto").attr("src", JsonData.licenseFileUrl);
			$("#enterprisePhoto").attr("width", 250);

            $('#depositLimit').textbox('setValue', (JsonData.depositLimit/100).toFixed(2));
            $('#overdraftMoney').textbox('setValue', (JsonData.overdraftMoney/100).toFixed(2));
            $('#overdraftNum').textbox('setValue', JsonData.overdraftNum);

			$("#addView").dialog("open").dialog("setTitle", "编辑企业");
  		}	     
	});
	
	//构造对话框
	$("#addView").dialog( {
		width : "550",
		height : "530",
		top : "0",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "enterprise_saveOrUpdate.do",
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
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else if(result == "phoneExist"){
							$.messager.alert("提示", "管理员电话已存在", "info");
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
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
		}]
	});
	
	
	//删除操作
	$("#btnRemove").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的企业", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("enterprise_del.do",{"id":JsonData.id},function(data){
						if(data=="success"){
							//$.messager.alert("提示", "删除成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	
	//企业用户添加
	$("#memberAdd").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要添加用户的企业", "error");
   		}else{
			clearMemberAddForm();
			document.getElementById('m_phone_msg').innerHTML = "";
			document.getElementById('m_email_msg').innerHTML = "";
			document.getElementById('m_idcard_msg').innerHTML = "";
		   	var JsonData = selectedRow[0];
			$("#m_id").val(JsonData.id);
			$("#m_enterpris_name").textbox("setValue",JsonData.enterpriseName);
			$("#memberAddView").dialog("open").dialog("setTitle", "企业用户添加");
  		}	     
	});
	
	//构造对话框
	$("#memberAddView").dialog( {
		width : "500",
		height : "530",
		top : "0",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#memberAddForm").form("submit", {
					url : "enterprise_member_add.do",
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
						    $("#dataGrid").datagrid("reload");
							$("#memberAddView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#memberAddView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#memberAddView").dialog("close");
			}
		}]
	});
	
	//企业所属网点添加
	$("#btnEditSetPark").bind("click",function(){
		$("#addParkView").dialog("open").dialog("setTitle", "添加网点");
		var selectedRow = $("#dataGrid").datagrid("getSelections");
		
		$("#enterprise_id").val(selectedRow[0].id);
		$('#parkName').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
		 	delay:500,// 1s延时查询
		 	editable : true,
			panelHeight: 'auto', 
			panelWidth: 300,
            // singleSelect : true,
            // selectOnCheck: false,
            // checkOnSelect: false,
			idField: 'id',
			textField: 'name',
			url:'allNotBelongPark?',
			striped : true,
			pageSize:10,
			required:true,
			multiple:true,
			pagination : true,//是否分页
			rownumbers : true,//序号
			columns : [ [ {
				field : 'ck',
				checkbox:true
				},{
				field : 'name',
				title : '网点名称',
				width : '80%',
				align : 'center'
			}] ],
			keyHandler : {
				query : function(parkName) { // 动态搜索处理
					$('#parkName').combogrid("grid").datagrid('options').queryParams = 
						JSON.parse('{"parkName":"' + parkName + '"}');
					// 重新加载
					$('#parkName').combogrid("grid").datagrid("reload");
					// $('#parkName').combogrid("setValue",parkName);
					 
				}
			},
            onLoadSuccess:function(){

                /*var g =$('#parkName').combogrid("grid");
                var selectedRow = g.datagrid('getSelections');
                // console.log(selectedRow);*/
                var park= new Array();
                /*if(selectedRow.length>0) {
                    park.push(selectedRow.parkName);
                }*/
               $('#parkName').combogrid('setValues',park);
            }

		});
	});


    //企业所属车辆添加
    $("#addEnterpriseCar").bind("click",function(){
        console.log("enter add enterprise car view ")
        $("#addEnterpriseCarView").dialog("open").dialog("setTitle", "添加企业所属车辆");
        var selectedRow = $("#dataGrid").datagrid("getSelections");

        $("#enter_id").val(selectedRow[0].id);
        $('#carList').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
            delay:500,// 1s延时查询
            editable : true,
            panelHeight: 'auto',
            panelWidth: 'auto',
            // singleSelect : true,
            // selectOnCheck: false,
            // checkOnSelect: false,
            idField: 'id',
            textField: 'lpn',
            url:'findEnterpriseCarList?',
            striped : true,
            pageSize:10,
            required:true,
            multiple:true,
            pageList : [100,50,30,10],
            pageIndex:1,
            pagination : true,//是否分页
            rownumbers : true,//序号
            columns : [ [ {
                field : 'ck',
                checkbox:true
            },{
                field : 'lpn',
                title : '车牌号',
                width : '90%',
                align : 'left'
            }
            ] ],
            keyHandler : {
                query : function(lpn) { // 动态搜索处理
                    $('#carList').combogrid("grid").datagrid('options').queryParams =
                        JSON.parse('{"lpn":"' + lpn + '"}');
                    // 重新加载
                    $('#carList').combogrid("grid").datagrid("reload");
                    // $('#carList').combogrid("setValue",lpn);

                }
            },
            onLoadSuccess:function(){
              /*  var g =$('#carList').combogrid("grid");
                var selectedRow = g.datagrid('getSelections');*/
                // console.log(selectedRow);
                var park= new Array();
               /* if(selectedRow.length>0) {
                    park.push(selectedRow.carList);
                }*/
                $('#carList').combogrid('setValues',park);
            }
        });
    });
    //构造保存企业所属车辆对话框
    $("#addEnterpriseCarView").dialog( {
        width : 350,
        height : 300,
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                var selectedRows = $("#carList").combogrid('getValues');

                var enterpriseID = $('#enter_id').val();
                selectedRows.toString()
                // console.log("selectedRows>>>>>>")
                // console.log(selectedRows);
                // console.log( selectedRows.toString());
                // console.log("enterpriseID:" + enterpriseID);
                $("#addEnterpriseCarForm").form("submit", {
                    url : "addEnterpriseCar?carid="+selectedRows.toString()+"&enterpriseID="+enterpriseID,
                    // queryParams:{"ids":selectedRows,"enterpriseId":enterpriseID},
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
                    success : function(jsonResult) {
                        var result = JSON.parse(jsonResult);
                        // console.log("result>>.");
                        $.messager.progress('close');
                        // console.log(result.status);
                        // console.log(result.data);
                        if (result.status ==1) {
                            $.messager.alert("提示", "添加成功", "info");
                            $("#showEnterpriseCarGrid").datagrid("reload");
                            $("#dataGrid").datagrid("reload");
                            $("#addEnterpriseCarView").dialog("close");
                        }else{
                            $.messager.alert("提示", result.data, "info");
                            $("#showEnterpriseCarGrid").datagrid("reload");
                            $("#addEnterpriseCarView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addEnterpriseCarView").dialog("close");
            }
        }]
    });
    //移除企业所属车辆
    $("#removeEnterpriseCar").bind("click",function(){
        var selectedRows = $("#showEnterpriseCarGrid").datagrid("getSelections");
        // console.log(selectedRows);
        var JsonData= new Array();
        var JsonCar = new Array();

        if(selectedRows.length <=0){
            $.messager.alert("提示", "请选择要移除的车辆", "error");
        }else{
            for(var i=0;i<selectedRows.length;i++){
                JsonData.push(selectedRows[i].enterpriseRelationCarId);
                JsonCar.push(selectedRows[i].id);
            }
            $.messager.confirm("提示","确定要移除吗?",function(r){
                if(r){
                    $.post("removeEnterpriseCar",{"id":JsonData.toString(),"enterpriseID":selectedRows[0].enterpriseId,"carID":JsonCar.toString()},function(data){
                        console.log(data);
                        if(data.code==1){
                            $.messager.alert("提示", "移除成功", "info");
                            $("#showEnterpriseCarGrid").datagrid("reload");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "移除失败", "info");
                        }
                    });
                }
            });
        }
    });

	//构造保存对话框
	$("#addParkView").dialog( {
		width : "460",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				var selectedRows = $("#parkName").combogrid('getValues');
				$("#addParkForm").form("submit", {
					url : "enterprise_park_add?ids="+selectedRows,
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
						    $("#showParkGrid").datagrid("reload");
						    $("#dataGrid").datagrid("reload");
							$("#addParkView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#showParkGrid").datagrid("reload");
							$("#addParkView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#addParkView").dialog("close");
			}
		}]
	});
	//企业所属网点移除
	$("#btnEditCancelPark").bind("click",function(){
		var selectedRows = $("#showParkGrid").datagrid("getSelections");
		var JsonData='';
		if(selectedRows.length <=0){
			$.messager.alert("提示", "请选择要移除的网点", "error");
		}else{
			for(var i=0;i<selectedRows.length;i++){
				
				JsonData = JsonData+ selectedRows[i].id+',';
			}
			$.messager.confirm("提示","确定要移除吗?",function(r){
	      		if(r){

					$.post("deleteEnterpriseById",{"id":JsonData},function(data){
						if(data=="success"){
							$.messager.alert("提示", "移除成功", "info");
						    $("#showParkGrid").datagrid("reload");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "移除失败", "info");
						}
					},"text");
				}
			});
		}
	});

/*	//构造对话框
	$("#importMemberView").dialog( {
		width : "350",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#importMemberForm").form("submit", {
					url : "enterprise_improt_member.do",
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
						var str = "共"+result.split(",")[0]+"条，成功导入"+result.split(",")[1]+"条。";
						$.messager.alert("提示", str, "info");
					    $("#dataGrid").datagrid("reload");
						$("#importMemberView").dialog("close");
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#importMemberView").dialog("close");
			}
		}]
	});
	
	*/
	//企业用户导入
	$("#membersAdd").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要导入用户的企业", "error");
   		}else{
			clearImportMemberForm();
			var JsonData = selectedRow[0];
			$("#import_member_id").val(JsonData.id);
			$("#enterpris_name_imp").textbox("setValue",JsonData.enterpriseName);
			$("#importMemberView").dialog("open").dialog("setTitle", "企业会员导入");
  		}	     
	});
	//构造对话框
	$("#importMemberView").dialog( {
		width : "350",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#importMemberForm").form("submit", {
					url : "enterprise_improt_member.do",
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
						var str = "共"+result.split(",")[0]+"条，成功导入"+result.split(",")[1]+"条。";
						$.messager.alert("提示", str, "info");
					    $("#dataGrid").datagrid("reload");
						$("#importMemberView").dialog("close");
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#importMemberView").dialog("close");
			}
		}]
	});
	
	//导入样表下载
	$("#membersAddUploud").bind("click",function(){
		//window.location.href = "";
	});
	
	//企业账户充值
	$("#moneyAdd").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要充值的企业", "error");
   		}else{
			clearMoneyAddForm();
		   	var JsonData = selectedRow[0];
			$("#enterpriseId").val(JsonData.id);
			$("#enterpriseName").textbox("setValue",JsonData.enterpriseName);
			$("#moneyAddView").dialog("open").dialog("setTitle", "账户充值");
  		}	     
	});
	
	
	//分公司账户充值
	$("#branchOfficeMoneyAdd").bind("click",function(){
      	var selectedRow = $("#branchOfficeDetailDataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要充值的企业", "error");
   		}else{
			clearMoneyAddForm();
		   	var JsonData = selectedRow[0];
			$("#enterpriseId").val(JsonData.id);
			$("#enterpriseName").textbox("setValue",JsonData.enterpriseName);
			$("#moneyAddView").dialog("open").dialog("setTitle", "账户充值");
  		}	     
	});
	
	//构造对话框
	$("#moneyAddView").dialog( {
		width : "350",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#moneyAddForm").form("submit", {
					url : "enterprise_money_add.do",
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
					success : function(data) {
                        var result = JSON.parse(data);
                        console.log(data);
						$.messager.progress('close'); 
						if (result.code== 1) {
							$.messager.alert("提示", "保存成功", "info");
						    $("#dataGrid").datagrid("reload");
						    $("#branchOfficeDetailDataGrid").datagrid("reload");
							$("#moneyAddView").dialog("close");
						}else{
							$.messager.alert("提示", result.message, "info");
						    $("#dataGrid").datagrid("reload");
						    $("#branchOfficeDetailDataGrid").datagrid("reload");
							$("#moneyAddView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#moneyAddView").dialog("close");
			}
		}]
	});
	
	//企业押金充值
	$("#depositAdd").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要充值的企业", "error");
   		}else{
			clearDepositAddForm();
		   	var JsonData = selectedRow[0];
			$("#deposit_add_id").val(JsonData.id);
			$("#enterpris_name_d").textbox("setValue",JsonData.enterpriseName);
			$("#depositAddView").dialog("open").dialog("setTitle", "押金充值");
  		}	     
	});
	
	//分公司押金充值
	$("#branchOfficeDepositAdd").bind("click",function(){
      	var selectedRow = $("#branchOfficeDetailDataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要充值的企业", "error");
   		}else{
			clearDepositAddForm();
		   	var JsonData = selectedRow[0];
			$("#deposit_add_id").val(JsonData.id);
			$("#enterpris_name_d").textbox("setValue",JsonData.enterpriseName);
			$("#depositAddView").dialog("open").dialog("setTitle", "押金充值");
  		}	     
	});
	
	//构造对话框
	$("#depositAddView").dialog( {
		width : "350",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#depositAddForm").form("submit", {
					url : "enterprise_deposit_add.do",
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
					success : function(data) {
                        var result = JSON.parse(data);
						$.messager.progress('close'); 
						if (result.code == 1) {
							$.messager.alert("提示", "保存成功", "info");
						    $("#dataGrid").datagrid("reload");
						    $("#branchOfficeDetailDataGrid").datagrid("reload");
							$("#depositAddView").dialog("close");
						}else{
							$.messager.alert("提示",result.message, "info");
						    $("#dataGrid").datagrid("reload");
						    $("#branchOfficeDetailDataGrid").datagrid("reload");
							$("#depositAddView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#depositAddView").dialog("close");
			}
		}]
	});
	
	//清空
	$("#clearQuery").bind("click",function(){
		clearQueryForm();
	});
	
	//清空Combogrid
	$("#clearCombogrid").bind("click",function(){
		$("#level").combogrid("clear");
	});
	
	//设置额度
	$("#btnEditMoney").bind("click",function(){
		var selectedRow = $("#showMemberGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要设置额度的会员", "error");
   		}else{
   			clearEditMoneyForm();
		   	var JsonData = selectedRow[0];
			$("#member_id").val(JsonData.id);
			$("#name").textbox("setValue",JsonData.name);
			$("#quota_month").combobox("setValue","1");
			$("#editMoneyView").dialog("open").dialog("setTitle", "设置额度");
  		}	
	});
	
/*	//设置管理员
	$("#btnEditSetManager").bind("click",function(){
      	var selectedRow = $("#showMemberGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要设置管理员的会员", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要设为管理员吗?",function(r){
	      		if(r){
					$.post("enterprise_set_manager.do",{"id":JsonData.id},function(data){
						if(data=="success"){
						    $("#showMemberGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "设置失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});*/
	
	/*//取消管理员
	$("#btnEditCancelManager").bind("click",function(){
      	var selectedRow = $("#showMemberGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要取消管理员的会员", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要取消管理员吗?",function(r){
	      		if(r){
					$.post("enterprise_cancel_manager.do",{"id":JsonData.id},function(data){
						if(data=="success"){
						    $("#showMemberGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "设置失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	
   //移除会员
	$("#btnRemoveMember").bind("click",function(){
		var selectedRow =$("#showMemberGrid").datagrid("getSelections");
		if(selectedRow.length <=0){
			$.messager.alert("提示","请选择要移除的企业会员","error");
		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要移除吗?",function(r){
	      		if(r){
					$.post("enterprise_cancel_member.do",{"id":JsonData.id},function(data){
						if(data=="success"){
						    $("#showMemberGrid").datagrid("reload");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "设置失败", "info");
						}
					},"text");
				}
			});
		}
	});
	*/

	//构造对话框
	$("#editMoneyView").dialog( {
		width : "350",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#editMoneyForm").form("submit", {
					url : "enterprise_member_quota.do",
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
						    $("#showMemberGrid").datagrid("reload");
							$("#editMoneyView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#showMemberGrid").datagrid("reload");
							$("#editMoneyView").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#editMoneyView").dialog("close");
			}
		}]
	});
	
	$('#file').filebox({
	    buttonText: '选择图片',
	    onChange: function(){
	    	var val = $(this).filebox("getValue");
	    	var filetype = val.match(/^(.*)(\.)(.{1,8})$/)[3];
	    	filetype = filetype.toUpperCase();
	    	//alert(filetype);
	    	if(filetype=="gif" || filetype=="jpeg" || filetype=="png" || filetype=="jpg" || filetype=="bmp"|| filetype=="GIF"|| filetype=="JPEG" || filetype=="PNG" || filetype=="JPG" || filetype=="BMP"){
	    	}else{
	    		alert("图片格式不正确");
	    		$(this).filebox("setValue","");
	    	}
	    }
	});
	$("input",$("#enterprise_name").next("span")).blur(function(){
    	var val = $("#enterprise_name").textbox("getValue");
    	if(val.length>0){
    		$.post("enterprise_name_check.do",{"enterpriseName":val},function(data){
    			
				if(data=="success"){
					document.getElementById('enterprise_name_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
				}else{
					document.getElementById('enterprise_name_msg').innerHTML = "<font color='red'>名称已存在！</font>";
					$('#enterprise_name').textbox("setValue",""); 
				}
			},"text");
 		}
    });
	
	/*$("input",$("#admin_mobile").next("span")).blur(function(){
    	var val = $("#admin_mobile").textbox("getValue");
    	if(IsPhone(val)){
    		$.post("enterprise_check_manager.do",{"m_phone":val},function(data){
				if(data=="success"){
					document.getElementById('admin_mobile_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
				}else if(data=="phoneExist"){
					document.getElementById('admin_mobile_msg').innerHTML = "<font color='red'>非管理员号码！</font>"; 
				}else{
					document.getElementById('admin_mobile_msg').innerHTML = "<font color='red'>手机已为管理员！</font>";
					$('#admin_mobile').textbox("setValue",""); 
				}
			},"text");
			document.getElementById('admin_mobile_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
 		}else{
	        document.getElementById('admin_mobile_msg').innerHTML = "<font color='red'>验证失败！</font>"; 
	        $("#admin_mobile").textbox("setValue","");
    	}
    });*/

    /*
    $("input",$("#enterprise_tel").next("span")).blur(function(){
    	var val = $("#enterprise_tel").textbox("getValue");
    	if(IsPhone(val)){
			document.getElementById('enterprise_tel_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
 		}else if(IsTell(val)){
 			document.getElementById('enterprise_tel_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
 		}else{
	        document.getElementById('enterprise_tel_msg').innerHTML = "<font color='red'>验证失败！</font>"; 
	        $("#enterprise_tel").textbox("setValue","");
    	}
    });*/
    
   /* $("input",$("#m_phone").next("span")).blur(function(){
    	var val = $("#m_phone").textbox("getValue");
    	if(IsPhone(val)){
    		$.post("enterprise_check_phone.do",{"m_phone":val},function(data){
				if(data=="success"){
					document.getElementById('m_phone_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
				}else{
					document.getElementById('m_phone_msg').innerHTML = "<font color='red'>手机已存在！</font>";
					$('#m_phone').textbox("setValue",""); 
				}
			},"text");
 		}else{
	        document.getElementById('m_phone_msg').innerHTML = "<font color='red'>验证失败！</font>"; 
	        $("#m_phone").textbox("setValue","");
    	}
    });
    */
    $("input",$("#m_idcard").next("span")).blur(function(){
    	var val = $("#m_idcard").textbox("getValue");
    	if(IsIdCard(val)){
    		$.post("enterprise_check_idcard.do",{"m_idcard":val},function(data){
				if(data=="success"){
					document.getElementById('m_idcard_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
				}else{
					document.getElementById('m_idcard_msg').innerHTML = "<font color='red'>身份证已存在！</font>";
					$('#m_idcard').textbox("setValue",""); 
				}
			},"text");
 		}else{
	        document.getElementById('m_idcard_msg').innerHTML = "<font color='red'>验证失败！</font>"; 
	        $("#m_idcard").textbox("setValue","");
    	}
    });
    
    $("input",$("#m_email").next("span")).blur(function(){
    	var val = $("#m_email").textbox("getValue");
    	if(IsMail(val)){
    		$.post("enterprise_check_email.do",{"m_email":val},function(data){
				if(data=="success"){
					document.getElementById('m_email_msg').innerHTML = "<font color='green'>验证通过！</font>"; 
				}else{
					document.getElementById('m_email_msg').innerHTML = "<font color='red'>邮箱已存在！</font>";
					$('#m_email').textbox("setValue",""); 
				}
			},"text");
 		}else{
	        document.getElementById('m_email_msg').innerHTML = "<font color='red'>验证失败！</font>"; 
	        $("#m_email").textbox("setValue","");
    	}
    });
    
  //构造对话框
	$("#showMemberView").dialog( {
		width : 800,
		height : 450,
		top : "80"
	});
	
});
function clearForm(){
	$('#addForm').form('clear');
}
function clearQueryForm(){
	$('#queryForm').form('clear');
}
function clearEditMoneyForm(){
	$('#editMoneyForm').form('clear');
}
function clearDepositAddForm(){
	$('#depositAddForm').form('clear');
}
function clearMoneyAddForm(){
	$('#moneyAddForm').form('clear');
}
function clearMemberAddForm(){
	$('#memberAddForm').form('clear');
}
function clearImportMemberForm(){
	$('#importMemberForm').form('clear');
}

//检查图片的格式是否正确,同时实现预览
function setImagePreview(obj, localImagId, imgObjPreview) {  
    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp','GIF', 'JPEG', 'PNG', 'JPG', 'BMP'); // 可以上传的文件类型
    if (obj.value == '') {  
        $.messager.alert("让选择要上传的图片!");  
        return false;  
    }else {  
        var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; // 这个文件类型正则很有用
        // //布尔型变量
        var isExists = false;  
        // 循环判断图片的格式是否正确
        for (var i in array) {  
            if (fileContentType.toLowerCase() == array[i].toLowerCase()) {  
                // 图片格式正确之后，根据浏览器的不同设置图片的大小
                if (obj.files && obj.files[0]) {  
                    // 火狐下，直接设img属性
                    imgObjPreview.style.display = 'block';  
                    imgObjPreview.style.width = '250px';  
                    imgObjPreview.style.height = '150px';  
                    // 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                    imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
                }else {  
                    // IE下，使用滤镜
                    obj.select();  
                    var imgSrc = document.selection.createRange().text;  
                    // 必须设置初始大小
                    localImagId.style.display = "";
                    localImagId.style.width = "250px";  
                    localImagId.style.height = "150px";  
                    // 图片异常的捕捉，防止用户修改后缀来伪造图片
                    try {  
                        localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;  
                    }catch (e) {  
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

//构造企业用车人数明细列表	
function showMember(enterpriseId){
	$("#showMemberView").dialog("open").dialog("setTitle", "企业员工列表");
	var selectedRow = $("#dataGrid").datagrid("getSelections");
	/*if(selectedRow[0].levelName=="企业免押金个人帐号押金500企业" && selectedRow[0].money<=0){
		$("#btnEditMoney").hide();
	}else{
		$("#btnEditMoney").show();
	}*/
	var url='enterprise_member_list_by_id.do?enterpriseId='+enterpriseId;
	$('#showMemberGrid').datagrid( {
		width : 'auto',
		height : 200,
		fit:true,
		fitColumns: false,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		onLoadSuccess:function(data){
			if(data){
	            $.each(data.rows, function(index, item){
	            	$('#personGrid').datagrid('clearSelections');
	            });
	        }
	    },
		url : url,
		pageSize:20,
		columns : [ [ {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		}, {
            field : 'phone',
            title : '联系电话',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val=="null")
                    return "";
                else
                    return val;
            }
        } ,{
			field : 'memberLevel',
			title : '会员等级',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "account"){
					return "企业账号";
				}else if(val == "manager"){
					return "<font color='red'>企业管理员</font>";
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
		},  {
			field : 'money',
			title : '会员余额(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val=="null")
			       return "";
			    else
			       return (val/100).toFixed(2);
			}
		}, {
			field : 'deposit',
			title : '会员押金(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val=="null")
			       return "";
			    else
			       return (val/100).toFixed(2);
			}
//		}, {
//			field : 'integral',
//			title : '当前积分',
//			width : $(this).width() * 0.05,
//			align : 'center',
//			formatter : function(val, rec) {
//				if(val=="null")
//			       return "";
//			    else
//			       return val;
//			}
		},{
			field : 'memberAllAvail',
			title : '用车额度(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val ==null || val == "null"){
					return "";
				}else{
					return (val/100).toFixed(2);
				}
			}
		}, {
			field : 'memberAvail',
			title : '可用额度(元)',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val ==null || val == "null"){
					return "";
				}else{
					return (val/100).toFixed(2);
				}
			}
		},{
			field : 'status',
			title : '会员状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "ready")
			       return "就绪";
			    else if(val == "experience")
			       return "体验";
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

		}] ],
		pagination : true,
		rownumbers : true
	});
}
//企业所属车辆列表
function showEnterpriseCar(enterpriseId) {
    $("#showEnterpriseCarView").dialog("open").dialog("setTitle", "企业所属车辆列表");
    var url='getEnterpriseCarList?id='+enterpriseId;
    $('#showEnterpriseCarGrid').datagrid( {
        width : 500,
        height : 200,
        fit:true,
        nowrap : true,
        striped : true,
        collapsible : true,
        fitColumns: false,
        singleSelect :false,
        selectOnCheck: true,
        checkOnSelect: true,
        onLoadSuccess:function(data){
            if(data){
                $.each(data.rows, function(index, item){
                    $('#personGrid').datagrid('clearSelections');
                });
            }
        },
        url : url,
        pageSize:20,
        columns : [ [{
            field : 'ck',
            checkbox:true
        }, {
            field : 'lpn',
            title : '车牌号',
            width :$(this).width() * 0.2,
            align : 'center',
            formatter : function(val, rec) {
                if(val == "null")
                    return "";
                else
                    return val;
            }
        }, {
            field : 'brandName',
            title : '汽车品牌',
            width : $(this).width() * 0.2,
            align : 'center',
            formatter:function (val) {
                if(val == null||val=='') {
                    return ""
                }else {
                    return val;
                }

            }
        },
        {
            field : 'cityName',
            title : '所属城市',
            width : $(this).width() * 0.2,
            align : 'center',
            formatter:function (val) {
                if(val==null || $.trim(val)=='') {
                    return ""
                }else {
                    return val;
                }

            }
        }
        ] ],
        pagination : true,
        rownumbers : true
    });

}

//构造企业专属网点详细列表
function showPark(enterpriseId){
	
	$("#showParkView").dialog("open").dialog("setTitle", "企业所属网点表");
	var url='dataListPark.do?id='+enterpriseId;
	$('#showParkGrid').datagrid( {
        width : 500,
        height : 300,
		fit:true,
		nowrap : true,
		striped : true,
		collapsible : true,
		fitColumns: false,
		singleSelect :false,
		selectOnCheck: true,
		checkOnSelect: true,
		onLoadSuccess:function(data){
			if(data){
	            $.each(data.rows, function(index, item){
	            	$('#personGrid').datagrid('clearSelections');
	            });
	        }
	    },
		url : url,
		pageSize:20,
		columns : [ [{
			field : 'ck',
			checkbox:true
			}, {
			field : 'name',
			title : '网点名称',
			width : $(this).width() * 0.3,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
			}, {
				field : 'address',
				title : '网点地址',
				width : $(this).width() * 0.3,
				align : 'center'
				} 
			] ],
		pagination : true,
		rownumbers : true
	});
} 




function IsPhone(mail){
    // var mobile = /^(((13[0-9]{1})|(17[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    var mobile = /^((1[3,4,5,6,7,8,9]{1})+\d{9})$/;

    return (mail.length == 11 && mobile.test(mail));
}

function IsTell(mail){
	//^(0[0-9]{2,3}\-) ([2-9][0-9]{6,7})+(\-[0-9]{1,4}) $
    return (new RegExp(/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/).test(mail));
}

function IsMail(mail){
    return (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
}

function IsIdCard(num){
    var len = num.length, re;
    if (len == 15)
        re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
    else if (len == 18)
        re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/);
    else {
        return false;
    }
    var a = num.match(re);
    if (a != null){
        if (len==15){
            var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
            var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
        }else{
            var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
            var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
        }
        if (!B) {
            return false;
        }
    }
    return true;
}

/*function showBranchOffice(parentId,branchOfficeName){
	$("#parentId").val(parentId);
	$("#branchOfficeDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'parentId' : parentId,
			enterprise_name : branchOfficeName
	});
	$("#branchOfficeDetailView").dialog("setTitle", "旗下分公司列表");
	$('#branchOfficeDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
	$('#branchOfficeDetailDataGrid').datagrid('clearSelections');
}*/

function showBranchOffice(parentId){
    // reloadBranchCompany()
    $("#branchOfficeDetailView").dialog('open').dialog("setTitle", "旗下分公司列表");
    $('#branchCompanyForm').form('clear');
    $("#parentId").val(parentId);
    // var url = 'get_branch_company?parentId='+parentId
    $("#branchOfficeDetailDataGrid").datagrid({
        width : 'auto',
        height : 200,
        fit:true,
        fitColumns: false,
        nowrap : true,
        striped : true,
        collapsible : true,
        idField:'id',
        url : 'get_branch_company',
        queryParams:{
            'parentId':$("#parentId").val(),
            'enterprise_name':$("#branchOfficeName").textbox("getValue"),
        },
        pageSize:20,
        columns : [ [ {
            field : 'cityName',
            title : '所属城市',
            // width : $(this).width() * 0.2,
            width:'150px',
            align : 'center',
            formatter : function(val, rec) {
                if(val == "null")
                    return "";
                else
                    return val;
            }
            }, {
            field : 'enterpriseName',
            title : '分公司名称',
            // width : $(this).width() * 0.25,
            width:'160px',
            align : 'center',
            formatter : function(val, rec) {
                if(val=="null")
                    return "";
                else
                    return val;
            }

        }] ],
        pagination : true,
        rownumbers : true

    });

}


