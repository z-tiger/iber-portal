var url;
// 添加字典项
function openAddDicTypeView(){
	$("#dictypeForm").form("clear");
	var  id = $('#tree').tree('getSelected').id;
	$('#dicId1').val(id);
	$("#dictypeView").dialog("open").dialog("setTitle",
	"添加字典项");
	url="dictype_addDicType.do";
}

// 修改字典
function openUpdateView(){
	$("#dicForm").form("clear");
	var id = $('#tree').tree('getSelected').id;
	$.post("dic_EditDicData.do", {
		"id" : id
	}, function(data) {
			var obj = eval('(' + data + ')');
			$('#dicId').val(id);
			$('#dicname').textbox('setValue',obj.name);
			$('#diccode').textbox('setValue',obj.code);
			$('#dicsort').numberbox('setValue',obj.sort);
			$('#dicdescription').textbox('setValue',obj.description);
	}, "text");
	$("#dicView").dialog("open").dialog("setTitle", "修改字典");
	url="dic_addDictionaries.do";
}

// 添加字典
function openAddDicView(){
	$("#dicForm").form("clear");
	$("#dicView").dialog("open").dialog("setTitle",
	"添加字典");
	url="dic_addDictionaries.do";
}
// 删除字典
function delDic(){
	var id = $('#tree').tree('getSelected').id;
	  $.messager.confirm("提示","确定要删除选中的字典吗?",function(r){
		  if(r){
			$.post("dic_delDictionaries.do", {
				"id" : id
			}, function(data) {
				if(data==1){
					$.messager.alert("提示", "已经有字典项不能删除,如要删除请删除字典项", "error");
				}else if(data==0){
					$.messager.alert("提示", "删除成功", "info");
					$("#tree").tree('reload');
					$('#dicTypeGird').datagrid('reload', {});
				}else{
					$.messager.alert("提示", "删除失败", "error");	
				}
			}, "text");
		  }
	  });
}


$(function() {
	// 字典树
	  $("#tree").tree({
	        url:"sys_dic_tree",
	        lines : true,
//	        onContextMenu: function (e, node) {
//	                    e.preventDefault();
//	                    $('#tree').tree('select', node.target);
//	                    if(node.id!=-1){
//	                    $("#mm2").menu('show', {
//	                        left: e.pageX,
//	                        top: e.pageY
//	                    }).data("tabTitle", node.text);
//	                }
//	        },
	         onClick : function (node) {
	        	    var dicid=node.id;
	        	    $('#dicTypeGird').datagrid("clearSelections");
	        	    $('#dicTypeGird').datagrid('load', {
	        	    	dicId : dicid
					});
	        }
	    });
	  // 字典项
	  $('#dicTypeGird')
		.datagrid(
				{
					title : '字典项列表',
					width : 'auto',
					height : 'auto',
					fit : true,
					fitColumns : true,
					nowrap : true,
					striped : true,
					collapsible : true,
					singleSelect : true,
					url : 'sys_dic_list',
					pageSize : 100,
					pageList : [100,50,30,10],
					idField : 'id',
					columns : [ [ {
						field : 'ck',
						checkbox : true

					}, {
						field : 'name',
						title : '字典名称',
						width : $(this).width() * 0.08,
						align : 'center'
					}, {
						field : 'code',
						title : '字典代码',
						width : $(this).width() * 0.08
					}, {
						field : 'createUser',
						title : '创建人',
						width : $(this).width() * 0.08,
						align : 'center'
						}, {
							field : 'createTime',
							title : '创建时间',
							width : $(this).width() * 0.08,
							align : 'center'
							}, {
								field : 'sort',
								title : '排序',
								width : $(this).width() * 0.08,
								align : 'center'
								}
					] ],
					pagination : false,
					rownumbers : true
				});
		
	  
//	   $('#west').bind('contextmenu',function(e){
//		    $('#mm').menu('show', {
//		     left: e.pageX,
//		     top: e.pageY
//		    });
//		    return false;
//		   });
	   
		// 字典操作框
		$("#dicView").dialog({
			width : "350",
			height : "250",
			modal:true,
			top : "40",
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					$("#dicForm").form("submit", {
						url : url,
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							if (result == "succ") {
								$.messager.alert("提示", "操作成功", "info");
								$("#tree").tree('reload');
								$("#dicView").dialog("close");
							} else if (result == "fail") {
								$.messager.alert("提示", "操作失败", "error");
							} else {
								$.messager.alert("提示", "操作异常", "error");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#dicView").dialog("close");
				}
			} ]
		});
		// 字典项操作框
		$("#dictypeView").dialog({
			width : "350",
			height : "250",
			modal:true,
			top : "40",
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					$("#dictypeForm").form("submit", {
						url : url,
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							if (result == "succ") {
								var dicId = $('#tree').tree('getSelected').id;
								$.messager.alert("提示", "操作成功", "info");
								$("#dictypeView").dialog("close");
								$('#dicTypeGird').datagrid('load', {
									dicId : dicId
								});
							}else if(result == "noCheck"){
								$.messager.alert("提示", "字典项名称或字典项code已经存在", "error");
							}else if (result == "fail") {
								$.messager.alert("提示", "操作失败", "error");
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
					$("#dictypeView").dialog("close");
				}
			} ]
		});
		
//		// 查询
//		$("#btnQuery").bind("click", function() {
//			search();
//		});
//		// 绑定enter建查询
//		$(document).keydown(function(event) {
//			if (event.keyCode == 13) {
//				search();
//			}
//		});

		function search() {
			var dicId = $('#tree').tree('getSelected').id;
			var name = $.trim($("#queryname").val());
			$('#dicTypeGird').datagrid('load', {
				name : name,
				dicId:dicId
			});
		}
		
		
		// 添加字典项
		$("#btnAddDicType").bind("click",function(){
			if($('#tree').tree('getSelected') == null){
				$.messager.alert("提示", "请选择字典", "error");
			}else{
				$("#dictypeForm").form("clear");
				var dicId = $('#tree').tree('getSelected').id;
				$('#dicId1').val(dicId);
				//alert(document.getElementsByName("dicId")[0].value);
				$("#dictypeView").dialog("open").dialog("setTitle","修改字典项");
				url = "sys_dic_saveOrUpdate";
			}
		       
		});
		
		// 编辑字典项
		$("#btnEditDicType").bind("click",function(){
	       var selectedRow = $("#dicTypeGird").datagrid("getSelections");
		   if(selectedRow.length <= 0){
				$.messager.alert("提示", "请选择一条记录", "error");
		   }else{
	 				var JsonData = selectedRow[0];
				   $("#dictypeForm").form("clear");
				   $('#dicId1').val(JsonData.dicId);
				   $('#dictypeid').val(JsonData.id);
				   $("#dictypename").textbox('setValue',JsonData.name);// 赋值
				   $("#dictypecode").textbox('setValue',JsonData.code);// 赋值
				   $("#dictypesort").numberbox('setValue',JsonData.sort);// 赋值
				   $("#dictypeView").dialog("open").dialog("setTitle","修改字典项");
				   url = "sys_dic_saveOrUpdate";
		         }	     
		});
		
		// 删除字典项
		$("#btnDelDicType").bind("click",function(){
		       var selectedRow = $("#dicTypeGird").datagrid("getSelections");
			   if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择一条记录", "error");
			   }else{
				   $.messager.confirm("提示","确定要删除选中的数据吗?",function(r){
				   if(r){
				   var id = selectedRow[0].id;
				   $.post("sys_dic_del", {
						"id" : id
					}, function(data) {
						if(data=="succ"){
							var dicId= $('#tree').tree('getSelected').id;
							$.messager.alert("提示", "删除成功", "info");
							$('#dicTypeGird').datagrid('load', {
								dicId:dicId
							});
						}else{
							$.messager.alert("提示", "删除失败", "error");
						}
					}, "text");
				   }
			     });
			   }
			});
	});
