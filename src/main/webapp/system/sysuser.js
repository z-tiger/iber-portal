$(function() {

	$('#sysUserGrid')
	.datagrid(
			{
				title : '用户管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'sys_user_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				}
				, {
					field : 'cityName',
					title : '所属区域',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'name',
					title : '姓名',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'account',
					title : '登陆账号',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'phone',
					title : '手机号码',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
						field : 'email',
						title : '邮箱',
						width : $(this).width() * 0.08,
						align : 'center'
				}, {
						field : 'status',
						title : '是否启用',
						width : $(this).width() * 0.08,
						align : 'center',
						formatter : function(val, rec) {
							if (val== "1") {
								return "<font color='#FF792B'>启用</font>";
							} else if(val == "0"){
								return "<font color='#3BFF3B'>未启用</font>";	
							}else {
								return "<font color='#2414FF'>异常</font>";
							}
							}
				}
				] ],
				pagination : true,
				rownumbers : true
			});
	// 查询
	$("#btnQuery").bind("click", function() {
		search();
	});
	// 绑定enter建查询
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			search();
		}
	});
	
	$("#btnModifyPwd").bind("click", function(){
		var selectedRow = $("#sysUserGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要修改密码的用户", "error");
   		}else{
   			$("#pd-uid").val(selectedRow[0].id);
   			$("#modifyPwdView").dialog("open");
   		}
	});

	function search() {
		var LoginUAcc = $.trim($("#LoginUAcc").val());
		var LoginUName = $.trim($("#LoginUName").val());
        var status = $('#seach_status').combobox('getValue');
        console.log(status);
		$('#sysUserGrid').datagrid('load', {
			account : LoginUAcc,
			name : LoginUName,
            status:status
		});
	}
	//重置密码
	//重置密码操作
	$("#btnResetPwd").bind("click",function(){
		var selectedRow = $("#sysUserGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要重置密码的用户", "error");
   		}else{
  	 		var userJsonData = selectedRow[0];
   			$.messager.confirm("提示","确定要重置该用户的密码吗?",function(r){
      			if(r){
					$.post("sys_user_resetPwd",{"id":userJsonData.id},function(data){
						if(data){
							if(data=="succ"){
								$.messager.alert("提示", "重置密码成功", "info");
					    		$("#sysUserGrid").datagrid("reload");
					    		$("#sysUserGrid").datagrid("clearSelections");
							}else{
								$.messager.alert("提示", "重置密码失败", "error");
							}
      					}
					},"text");
				}
			});
		}	     
	});
	//清空
	$("#btnClear").bind("click", function() {
        $('#toolbar').form('clear');
		$('#LoginUAcc').textbox('setValue','');
		$('#LoginUName').textbox('setValue','');
		$('#sysUserGrid').datagrid('reload', {});
	});
	 // 添加
		$("#btnSave").bind("click",function(){
					    $("#sysUserForm").form("clear");
					    $("#sysUserView").dialog("open").dialog("setTitle", "添加用户");
					    url = "sys_user_save";
			});
		
		//用户受角色
		$('#btnSendRole').bind("click",function(){
			var selectedRow = $("#sysUserGrid").datagrid("getSelections");
			if (selectedRow.length <= 0) {
				$.messager.alert("提示", "请选择一个用户进行分配角色", "error");
			} else{
				clearRoleTree();
				  $("#sendRoleView").dialog("open").dialog("setTitle", "分配角色");
					var userCheckId1=selectedRow[0].id;
				  $.post("sys_user_checkRoleModu?userId="+userCheckId1, function(data){
					    var json = eval('(' + data + ')');
					    for(var i=0; i<json.length; i++)  
					    {  
					       var tempId=parseInt(json[i].role_id);
					       var node = $("#roletree").tree('find',tempId);
					       $('#roletree').tree('check', node.target);
					    }  
					 });
			}
     });
		
		// 用户受角色对话框
		$("#sendRoleView").dialog( {
			width : "400",
			height : "450",
			top : "40",
			modal:true,
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					var rolenodes = $('#roletree').tree('getChecked');
					//用户id
					var userId = $('#sysUserGrid').datagrid("getSelections")[0].id;
					//角色ids
					var roleIds = '';
					for ( var i = 0; i < rolenodes.length; i++) {
						if (roleIds != '')
							roleIds += ',';
						roleIds += rolenodes[i].id;
					}
					$('#roleIds').val(roleIds);
					$('#userIds').val(userId);
					$("#sendRoleForm").form("submit", {
						url : "sys_user_grantRole",
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							//alert(result);
							if (result== "succ") {
								$.messager.alert("提示", "操作成功", "info");
								$("#sendRoleView").dialog("close");
							} else if(result == "fail") {
								$.messager.alert("提示", "操作失败", "error");
							}
							else{
							    $.messager.alert("提示", "操作失败", "error");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#sendRoleView").dialog("close");
				}
			} ]
		});
		
		//用户受权限
		$('#btnJurisdiction').bind("click",function(){
			var selectedRow = $("#sysUserGrid").datagrid("getSelections");
			if (selectedRow.length <= 0) {
				$.messager.alert("提示", "请选择一个用户进行分配权限", "error");
			} else{
				  clearCdTree();
				  $("#sysModuView").dialog("open").dialog("setTitle", "分配权限");
				  var userCheckId=selectedRow[0].id;
				  $.post("sys_user_checkUserModu?userId="+userCheckId, function(data){
					    var json = eval('(' + data + ')');
					    for(var i=0; i<json.length; i++)  
					    {  
					       var tempId=parseInt(json[i].modu_id);
					       var node = $("#cdtree").tree('find',tempId);
					       if (node != null) {//node节点可能为空，如果不加判断，那么node.target会报错，就不能回显
					    	   if($('#cdtree').tree('isLeaf', node.target)) {
						    	   $('#cdtree').tree('check', node.target);
						       }
					       }
					       
					    }  
				  });
			}
		  
		    
     });
		
		
		// 用户受角色对话框
		$("#sysModuView").dialog( {
			width : "400",
			height : "450",
			top : "40",
			modal:true,
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					var cdnodes = $('#cdtree').tree('getChecked');
					console.log(cdnodes);
					var userId = $('#sysUserGrid').datagrid("getSelections")[0].id;
					var moduIds = '';
					var is_shows = '';
					var pids = '';
					var names = '';
					for ( var i = 0; i < cdnodes.length; i++) {
						if (moduIds != '')
							moduIds += ',';
						moduIds += cdnodes[i].id;
						if (is_shows != '') {
							is_shows += ',';
						}
						is_shows += cdnodes[i].isShow;
						if (pids != '') {
							pids += ',';
						}
						pids += cdnodes[i].pid;
						if (names != '') {
							names += ',';
						}
						names += cdnodes[i].text;
						
					}
					//如果选中下级节点，则上级节点也设置为选中
					var roots = $('#cdtree').tree('getRoots');
					var root = null;
					for(var i = 0; i < roots.length; i++) {
						root = roots[i];
						if(!root.checked && !$('#cdtree').tree('isLeaf', root.target)) {	//未选中且非叶子节点
							var childNodeChecked = false;	//子节点是否有被选中
							var rootChildren = $('#cdtree').tree('getChildren', root.target);	//二级节点列表
							for(var j = 0; j < rootChildren.length; j++) {
								var rootChild = rootChildren[j];
								if($('#cdtree').tree('isLeaf', rootChild.target)) {
									if(rootChild.checked) {//子节点是叶子节点且被选中选中，则默认也选中上级节点
										childNodeChecked = true;
									}
								} else {
									if(rootChild.checked) {
										childNodeChecked = true;
									} else {
										var nodeChildren = $('#cdtree').tree('getChildren', rootChild.target);	//三级节点列表
										for(var k = 0; k < nodeChildren.length; k++) {
											var nodeChild = nodeChildren[k];
											if(nodeChild.checked) {
												if (moduIds != '') {
													moduIds += ',';
												}
												moduIds += rootChild.id;
												if (is_shows != '') {
													is_shows += ',';
												}
												is_shows += rootChild.isShow;
												if (pids != '') {
													pids += ',';
												}
												pids += rootChild.pid;
												if (names != '') {
													names += ',';
												}
												names += rootChild.text;
												
												childNodeChecked = true;
												break;
											}
										}
									}
								}
							}
							if(childNodeChecked) {
								if (moduIds != '') {
									moduIds += ',';
								}
								moduIds += root.id;
								if (is_shows != '') {
									is_shows += ',';
								}
								is_shows += root.isShow;
								if (pids != '') {
									pids += ',';
								}
								pids += root.pid;
								if (names != '') {
									names += ',';
								}
								names += root.text;
							}
						}
					}
					$('#cduserId').val(userId);
					$('#moduIds').val(moduIds);
					$("#is_show").val(is_shows);
					$("#pid").val(pids);
					$("#text").val(names);
					//alert(userId);
					$("#sysModuForm").form("submit", {
						url : "sys_user_saveUserModu",
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							if (result== "succ") {
								$.messager.alert("提示", "操作成功", "info");
								$("#sysModuView").dialog("close");
							} else if(result == "fail") {
								$.messager.alert("提示", "操作失败", "error");
							}
							else{
							    $.messager.alert("提示", "操作失败", "error");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#sysModuView").dialog("close");
				}
			} ]
		});
		//角色树
		$("#roletree").tree({
			url : "sys_role_buildRoleTree",
			cascadeCheck : true,
			checkbox : true,
			lines : true
		});
		
		
		//菜单树
		$("#cdtree").tree({
			url : "sys_modu_showList",
			cascadeCheck : true,
			checkbox : true,
			lines : true
		});
		// 编辑
		$("#btnEdit").bind("click",function(){
		       var selectedRow = $("#sysUserGrid").datagrid("getSelections");
			   if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择一条记录", "error");
			   }else{
		 				var userJsonData = selectedRow[0];
					   $("#sysUserForm").form("clear");
					   $("#eid").val(userJsonData.id);
					   $("#epassword").val(userJsonData.password);
					   $("#ename").textbox('setValue',userJsonData.name);
					   $("#eaccount").textbox('setValue',userJsonData.account);
					   $("#ephone").textbox('setValue',userJsonData.phone);
					   $("#ecityCode").combobox('setValue',userJsonData.cityCode);
					   $("#eemail").textbox('setValue',userJsonData.email);
					   $("#estatus").combobox('setValue',userJsonData.status);
					   $("#sysUserUpdateView").dialog("open").dialog("setTitle", "修改用户");
					    url = "enerprise_sys_user_update";
			  }	     
			});
		
		
		// 删除
		$("#btnDel").bind("click",function(){
		       var selectedRow = $("#sysUserGrid").datagrid("getSelections");
			   var UId=selectedRow[0].UId;
			   if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要删除的用户", "error");
			   }else{
			      $.messager.confirm("提示","确定要删除选中的用户吗?",function(r){
			      if(r){
					$.post("sysUser_del.do",{"UId":UId},function(data){
					if(data){
						if(data=="succ"){
							$.messager.alert("提示", "删除成功", "info");
								    $("#sysUserGrid").datagrid("reload");
								    $("#sysUserGrid").datagrid("clearSelections");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
			      }
				},"text");
				}
				});
			  }	     
			});
		
		
		// 对话框
		$("#sysUserView").dialog( {
			width : "400",
			height : "350",
			top : "40",
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					$("#sysUserForm").form("submit", {
						url : url,
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
                            var data = JSON.parse(result);
							if (data.code== 1) {
								$.messager.alert("提示", "操作成功", "info");
							    $("#sysUserGrid").datagrid("reload");
								$("#sysUserView").dialog("close");
							}else{
								   $.messager.alert("提示", data.message, "error");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#sysUserView").dialog("close");
				}
			} ]
		});
		 
		
		$("#modifyPwdView").dialog( {
			width : "380",
			height : "220",
			top : "40",
			title:"修改密码",
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					$("#modifyPwdForm").form("submit", {
						url : "sys_modify_pwd",
						onSubmit : function() {
                            if($(this).form("validate")){
                                var _oldPwd = $("#oldPwd").textbox("getValue");
                                var _newPwd = $("#pwd").textbox("getValue");
                                var _newPwd2 = $("#pwd2").textbox("getValue");
                                var reg = /^[A-Za-z0-9]{6,20}$/;
                                var reg1 = /[0-9]+/;
                                var reg2 = /[A-Za-z]+/;
                                if( !reg.test(_newPwd) || !reg1.test(_newPwd) || !reg2.test(_newPwd)){
                                    $.messager.alert("提示", "新密码不能小于6位，必须是数字加字母组合，请重新输入", "error");
                                    return false;
                                }
                                if(_newPwd != _newPwd2){
                                    $.messager.alert("提示", "2次输入的新密码不一致，请重新输入", "error");
                                    return false;
                                }
                                if(_newPwd == _oldPwd){
                                    $.messager.alert("提示", "新密码与原密码一样，请重新输入", "error");
                                    return false;
                                }

                            }
							return $(this).form("validate");
						},
						success : function(result) {
                            var data = eval('(' + result + ')');
                            if (data.code==1) {
                                $.messager.alert("提示", "操作成功", "info");
                                $("#sysUserGrid").datagrid("reload");
                                $("#modifyPwdView").dialog("close");
                            }else if(data.code == "0"){
                                $.messager.alert("提示", data.message, "error");
                            } else {
                                $.messager.alert("提示", "操作失败", "error");
                            }
                        }
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#modifyPwdView").dialog("close");
				}
			} ]
		});
		
		
		// 修改对话框
		$("#sysUserUpdateView").dialog( {
			width : "400",
			height : "320",
			top : "40",
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					$("#sysUserUpdateForm").form("submit", {
						url : url,
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
                            var data = JSON.parse(result);
							if (data.code== 1) {
								$.messager.alert("提示", "操作成功", "info");
							    $("#sysUserGrid").datagrid("reload");
								$("#sysUserUpdateView").dialog("close");
							}
							else{
							    $.messager.alert("提示",data.message, "error");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#sysUserUpdateView").dialog("close");
				}
			} ]
		});
		
		
	  	$('#eareaCode').combobox({
			  onSelect: function(record){
					  $('#ecompanyId').combobox({   
					    url:'sys_companyCombobox?areaCode='+record.id,   
					    valueField:'id',   
					    textField:'text'  
			         });  
			},
		});
	  	
	  	$('#areaCode').combobox({
			  onSelect: function(record){
					  $('#companyId').combobox({   
					    url:'sys_companyCombobox?areaCode='+record.id,   
					    valueField:'id',   
					    textField:'text'  
			         });  
			},
		});
	  	
	  	
	  	 
	  	//清除角色树
		function clearRoleTree(){
		     var nodes = $('#roletree').tree('getChecked');
	         for (var i = 0; i < nodes.length; i++) {
	        	   $('#roletree').tree('uncheck', nodes[i].target);
	         }
		}
		//清除菜单树
		function clearCdTree(){
			   var nodes = $('#cdtree').tree('getChecked');
		         for (var i = 0; i < nodes.length; i++) {
		        	   $('#cdtree').tree('uncheck', nodes[i].target);
		         }
			
		}
	    
		 function checkButton(form){
			 
		        if($(form).valid()){
		                return true;
		        }
		            else {
		                alertMsg.error("字段填写不合规范，请检查！");
		                return false;
		            }
		    }
		 
	
			

	});