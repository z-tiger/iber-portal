function clearToolbar(){
			$('#toolbar').form('clear');
		}
$(function() {
	//清空
	$("#clearQuery").bind("click",function(){
		clearToolbar();
	});
	//查询链接
	$(document).keydown(function(event){
	    var lpn = $("input[name='_lpn']").val();
	    var memberName  = $("input[name='_memberName']").val();
	    var checkStatus = $("input[name='_checkStatus']").val();
		if(event.keyCode==13){
		  $('#dataGrid').datagrid('load',{
			  lpn : lpn,
			  memberName:memberName,
			  checkStatus : checkStatus
          });
		}
	});
	//查询链接
	$("#btnQuery").bind("click",function(){
		var lpn = $("input[name='_lpn']").val();
		var memberName  = $("input[name='_memberName']").val();
	    var checkStatus = $("input[name='_checkStatus']").val();
	    $('#dataGrid').datagrid('load',{
	    	lpn : lpn,
	    	memberName:memberName,
			checkStatus : checkStatus
        });
	});
	
	
	
	$('#dataGrid').datagrid( {
		title : '会员车牌管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'dataListMemberCar.do',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		pagination : true,
		rownumbers : true,
		columns : [ [{
			field : 'ck',
			checkbox:true
		},
		{
			field : 'memberName',
			title : '会员名称',
			width : $(this).width() * 0.1,
			align : 'center'	
		},   
		  {
			field : 'lpn',
			title : '车牌号',
			align : 'center',
			width : $(this).width() * 0.1,
			
		},   
		  {
			field : 'url',
			title : '图片',
			align : 'center',
			width : $(this).width() * 0.2,
			formatter:function(value, row, index){
				   if(value != ""){
				      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: fixed;top:100px;'><img width='480' height='600' src='"+value+"'/></div>";
				   }
			}
			
		},
		{
			field : 'checkStatus',   
			title : '审核状态',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter:function(val,rec){
				if(val=="1"){
					return "通过";
				}else if(val=="0"){
					return "未通过";
				}else{
					return "未审核";
				}
			}
		},
		{
			field : 'checkReason',
			title : '审核理由',
			width : $(this).width() * 0.2,
			align : 'center'	
		},{
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.13,
			align : 'center'
		} , {
			field : 'updateTime',
			title : '更新时间',
			width : $(this).width() * 0.12,
			align : 'center'
		}
		] ]
	});
	//修改
	$("#btnEdit").bind("click",function(){
			var selectedRows = $("#dataGrid").datagrid("getSelections");
			if(selectedRows.length <= 0){
					$.messager.alert("提示", "请选择要修改的记录", "error");
			}else{
					$("#id").val(selectedRows[0].id);
					$("#memberName").html(selectedRows[0].memberName);
					$("#lpn").textbox("setValue",selectedRows[0].lpn);
					$("#url").textbox("setValue",selectedRows[0].url);
					$("#checkStatus").combobox("setValue",selectedRows[0].checkStatus);
					$("#checkReason").textbox("setValue",selectedRows[0].checkReason);
					$("#examine_lpnPhotoUrl").attr("src", selectedRows[0].url);
					$("#examine_lpnPhotoUrlAHERF").attr("href", "lpnImgOpen?img="+selectedRows[0].url);
					$("#addView").dialog({title:"审核会员车牌信息"});
					$("#addView").dialog("open");		
			}
		});
	//构造保存对话框
	$("#addView").dialog( {
		width : "480",
		height : "500",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				debugger;
				$("#addForm").form("submit", {
					url : "memberCar_modify",
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
							$("#addView").dialog("close");
							$("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "保存失败", "info");
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
	
	
})	
function display(id){
	document.getElementById("box"+id).style.display="block"; 
}
function disappear(id){
	document.getElementById("box"+id).style.display="none"; 
}