$(function() {
	$('#roleGrid').datagrid({
		title : '角色管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'sys_role_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'name',
			title : '角色名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'description',
			title : '创建备注',
			width : $(this).width() * 0.08,
			align : 'center'
		} ] ],
		pagination : true,
		rownumbers : true
	});

	$("#btnJurisdiction").bind("click", function() {
		var selectedRow = $("#roleGrid").datagrid("getSelections");
		if (selectedRow.length <= 0) {
			$.messager.alert("提示", "请选择一个角色进行授权", "error");
		} else {
			var roleId=selectedRow[0].id;
			clearTree();
			$("#sysModuView").dialog("open").dialog("setTitle", "分派角色");
			$.post("sys_modu_checkRoleModu?roleId="+roleId, function(data){
				console.log(data);
			    var json = eval('(' + data + ')');
			    for(var i=0; i<json.length; i++)  
			    {  
			       var tempId=parseInt(json[i].modu_id);
			       var node = $("#tree").tree('find',tempId);
			       if(node){
			    	   if($('#tree').tree('isLeaf', node.target)) {
				    	   $('#tree').tree('check', node.target);
				       }
			       }
			       
			    }  
			 });
		}
	});
	$("#tree").tree({
		url : "sys_modu_showList",
		cascadeCheck : true,
		checkbox : true,
		lines : true
	});
	//添加
	$("#btnSave").bind("click", function() {
		$("#roleForm").form("clear");
		$("#roleView").dialog("open").dialog("setTitle", "添加角色");
		url = "sys_role_saveOrUpdate";
	});

	//编辑
	$("#btnEdit").bind("click", function() {
		var selectedRow = $("#roleGrid").datagrid("getSelections");
		if (selectedRow.length <= 0) {
			$.messager.alert("提示", "请选择一条记录", "error");
		} else {
			var userJsonData = selectedRow[0];
			$("#roleForm").form("clear");
			$("#roleId").val(userJsonData.id);
			$("#roleName").textbox('setValue', userJsonData.name);
			$("#roleDescription").textbox('setValue', userJsonData.description);
			$("#roleView").dialog("open").dialog("setTitle", "修改角色");
			url = "sys_role_saveOrUpdate";
		}
	});

	//删除
	$("#btnDel").bind("click", function() {
		var selectedRow = $("#roleGrid").datagrid("getSelections");
		if (selectedRow.length <= 0) {
			$.messager.alert("提示", "请选择要删除的角色", "error");
		} else {
			var id = selectedRow[0].id;
			$.messager.confirm("提示", "确定要删除选中的角色吗?", function(r) {
				if (r) {
					$.post("sys_role_del", {
						"id" : id
					}, function(data) {
						if (data) {
							if (data == "succ") {
								$.messager.alert("提示", "删除成功", "info");
								$("#roleGrid").datagrid("reload");
								$("#roleGrid").datagrid("clearSelections");
							} else {
								$.messager.alert("提示", "删除失败", "info");
							}
						}
					}, "text");
				}
			});
		}
	});

	//添加对话框
	$("#roleView").dialog({
		width : "400",
		height : "180",
		top : "40",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#roleForm").form("submit", {
					url : url,
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#roleGrid").datagrid("reload");
							$("#roleView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else if(result=="ishave") {
							$.messager.alert("提示", "角色名称已经存在", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#roleView").dialog("close");
			}
		} ]
	});

	//授权对话框
	$("#sysModuView").dialog({
		width : "500",
		height : "450",
		top : "40",
		modal : true,
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				var nodes = $('#tree').tree('getChecked');
				var roleId = $('#roleGrid').datagrid("getSelections")[0].id;
				var moduIds = '';
				var is_shows = '';
				var pids = '';
				var names = '';
				for ( var i = 0; i < nodes.length; i++) {
					if (moduIds != '')
						moduIds += ',';
					moduIds += nodes[i].id;
					if (is_shows != '') {
						is_shows += ',';
					}
					is_shows += nodes[i].isShow;
					if (pids != '') {
						pids += ',';
					}
					pids += nodes[i].pid;
					if (names != '') {
						names += ',';
					}
					names += nodes[i].text;
					
				}
				//如果选中下级节点，则上级节点也设置为选中
				var roots = $('#tree').tree('getRoots');
				var root = null;
				for(var i = 0; i < roots.length; i++) {
					root = roots[i];
					if(!root.checked && !$('#tree').tree('isLeaf', root.target)) {	//未选中且非叶子节点
						var childNodeChecked = false;	//子节点是否有被选中
						var rootChildren = $('#tree').tree('getChildren', root.target);	//二级节点列表
						for(var j = 0; j < rootChildren.length; j++) {
							var rootChild = rootChildren[j];
							if($('#tree').tree('isLeaf', rootChild.target)) {
								if(rootChild.checked) {//子节点是叶子节点且被选中选中，则默认也选中上级节点
									childNodeChecked = true;
								}
							} else {
								if(rootChild.checked) {
									childNodeChecked = true;
								} else {
									var nodeChildren = $('#tree').tree('getChildren', rootChild.target);	//三级节点列表
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
				
				$('#roleIds').val(roleId);
				$('#moduIds').val(moduIds);
				$("#is_show").val(is_shows);
				$("#pid").val(pids);
				$("#text").val(names);
				//alert(roleId);
				$("#sysModuForm").form("submit", {
					url : "sys_role_saveRoleModu",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						//alert(result);
						if (result == "succ") {
							$.messager.alert("提示", "授权成功", "info");
							$("#sysModuView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "授权失败", "error");
						} else {
							$.messager.alert("提示", "授权失败", "error");
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

	function checkRole(form) {

		if ($(form).valid()) {
			return true;
		} else {
			alertMsg.error("字段填写不合规范，请检查！");
			return false;
		}
	}
	
	function clearTree(){
	     var nodes = $('#tree').tree('getChecked');
         for (var i = 0; i < nodes.length; i++) {
        	   $('#tree').tree('uncheck', nodes[i].target);
         }
	}
});