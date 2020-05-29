$(function() {
	
	$('#moduGrid').treegrid({
		url : 'city_manage_page_buidTree',
		title : '城市管理',
		idField : 'id',
		treeField : 'name',
		animate:false,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}
		, {
			field : 'name',
			title : '名称',
			width : $(this).width() * 0.2,
			align : 'left'
		}, {
			field : 'code',
			title : '区域编码',
			width : $(this).width() * 0.2,
			align : 'center'
	  	}, 
		  {
			field : 'status',
			title : '是否启用',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val == "1") {
					return "启用";
				} else {
					return "未启用";
				}
			}
		},
		{
			field : 'startTime',   
			title : '上班时间',
			width : $(this).width() * 0.3,
			align : 'center'
		},
		{
			field : 'endTime',   
			title : '下班时间',
			width : $(this).width() * 0.3,
			align : 'center'
		}
	  	
	  	] ],
		onClickRow:function(row){
			
			if(row.code!='00'){
				
				//将ID值赋给隐藏表单 ID , controller 中接收ID值就可以了
			  	//<input type="hidden" name="id" " id="id" />
				$("#addView").dialog("open").dialog("setTitle", "修改城市信息");
			}
			
			$("#id").val(row.id);
//			alert(row.name);
			$("#name").val(row.name);
			
		}
	});
	//构造对话框
	$("#addView").dialog( {
		width : "480",
		height : "180",
		top : "100",
		left:'400',
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#UpdateCity").form("submit", {
					url:"city_update.do",
					success:function(result) {
						$.messager.progress('close'); 
						if (result == "success") {
							$.messager.alert("提示", "保存成功", "info");
							$("#moduGrid").treegrid("reload");
							$("#addView").dialog("close");
						}else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#moduGrid").treegrid("reload");
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
	
	
	
});