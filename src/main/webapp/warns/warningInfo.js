var itemOptions ;
$(function() {
    // 设置默认值
	$('#beginTime').datetimebox('setValue', getNowBeforeFormatDate());

	$('#endTime').datetimebox('setValue', getNowFormatDate());
    var rowIndex = 0;

	$('#dataGrid')
	.datagrid(
			{
				title : '预警信息',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : false,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'warning_info_list',
				pageSize : 50,
				pageList : [50,30,10],
				idField : 'id',
				queryParams: {
					'beginTime': $('#beginTime').datebox('getValue'),
					'endTime': $('#endTime').datebox('getValue')
				},
                columns: [[{
        					field : 'warnContent',
        					title : '预警信息',
        					width : $(this).width() * 0.06,
        					align : 'center'
        				},{
           					field : 'warnItemCode',
           					title : '类型',
           					width : $(this).width() * 0.02,
           					align : 'center'
           				},{
        					field : 'createTime',
        					title : '时间',
        					width : $(this).width() * 0.02,
        					align : 'center',
							formatter: function (value) {
								if (value) {
									var d = new Date();
									d.setTime(value);
									return d.toLocaleString()
								}
							}
        				},{
							field : 'isRead',
							title : '状态',
							width : $(this).width() * 0.01,
							align : 'center',
							formatter : function(val) {
								if(val){
                                    return "已读";
								}else{
									return "未读";
								}
							}
           				},{
           					field : 'toDispatch',
           					title : '详情信息',
           					width : $(this).width() * 0.01,
           					align : 'center',
           					formatter : function(val, rec) {
           					return "<a href=\"javascript:showWarnInfo('"+rec._id+"','"+rec.warnContent+"','"+rec.warnItemCode+"','"+rec.createTime+"','"+rec.isRead+"','"+rec.toDispatch+"')\" >"+"详情信息"+"</a>" ;
           					}
           				}
                       ]],
				pagination : true,
				rownumbers : true,
				onLoadSuccess:function(){
					$('#dataGrid').datagrid('selectRow',rowIndex);
					if (!itemOptions){
                        itemOptions = $("#s-item").combobox('getData');
                    }
				},
				onClickRow:function(index){
                    rowIndex=index;
				}
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
	// 清空
	$("#btnClear").bind("click", function() {		
		
		$('#beginTime').datebox('setValue', '');
		$('#endTime').datebox('setValue', '');
		$('#s-item').textbox('setValue', '');
		
	});
	
 
	//编辑用户链接
	$("#btnEdit").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
   		}else{
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$("#itemName").textbox("setValue",JsonData.itemName);
			$("#createTime").textbox("setValue",formatDate(JsonData.createTime));
			if(JsonData.status==""){ 
			$("#status").textbox("setValue","未读");	
			}else{$("#status").textbox("setValue","已读");	
			}
			$("#warnContent").textbox("setValue",JsonData.warnContent);
			$("#addView").dialog("open").dialog("setTitle", "预警信息详情");
  		}	     
	});
	
	
	//构造对话框
	
	$("#addView").dialog( {
		width : "500",
		height : "450",
		top : "40",
		buttons : [ {
			text : "转为调度任务",
			iconCls : "icon-save",
			handler : function() {
				$("#addForm").form("submit", {
					url : "change_dispatcher_task",
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
							$.messager.alert("提示", "转为调度任务成功", "info");
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						}else{
							$.messager.alert("提示", "转为调度任务失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#addView").dialog("close");
						} 
					}
				});
				}
			}]
	});	
	

	
	
	
	$("#noChangeView").dialog( {
		width : "500",
		height : "450",
		top : "40"
	});	
	
	// 最新时间报表查询
	$("#btnNewestQuery").bind("click", function() {
		$('#beginTime').datetimebox('setValue', getNowMinuteFormatDate());
		$('#endTime').datetimebox('setValue', getNowFormatDate());
		search()
	});

	// 查询
	function search() {	
		
		$('#dataGrid').datagrid('load', {
			
			'beginTime' : $('#beginTime').textbox('getValue'),
			'endTime' : $('#endTime').textbox('getValue'),
			'item':$('#s-item').textbox('getValue'),
		    'lpn':$('#s-lpn').textbox('getValue')
		});	
	}


	// 预警信息导出excel链接
	$("#btnExport").bind("click",function(){
        var  data = $("#dataGrid").datagrid("getData");
	    var beginTime = $('#beginTime').textbox('getValue');
	    var endTime = $('#endTime').textbox('getValue');
	    var item = $('#s-item').textbox('getValue');
	    var lpn=$('#s-lpn').textbox('getValue');
        location.href = "warning_info_excel?beginTime="+beginTime+"&endTime="+endTime+"&item="+item+"&lpn="+lpn+"&total="+data.total;
	    });


	//预警信息状态更改为已读

	$("#btnMarkRead").bind("click",function(){
		
		var rows = $("#dataGrid").datagrid("getSelections");
        if(rows.length <= 0){
			$.messager.alert("提示", "请选择记录", "error");
			}else{
      			for(var i=0; i<rows.length; i++){
					$.post("warning_mark_read",{"id":rows[i]._id},function(data){
					 },"text");
	      		}
      			$("#dataGrid").datagrid("reload");
			}
   });


	//全部设已读状态
  $("#btnAllMarkRead").bind("click",function(){
		$.post("all_mark_read",function(data){
			if(data=="success"){
			    $("#dataGrid").datagrid("reload");
			}else{
				$.messager.alert("提示", "全部设已读状态失败", "info");
			}
		 },"text");	
   });

});


//转为预警信息
function showWarnInfo(id,warnContent,itemName,createTime,status,toDispatch){
	$("#addForm").form("clear");
	if(toDispatch == 0){
	   	$("#nid").val(id);
        $.each(itemOptions,function (i, item) {
			if (item.id === itemName){
				itemName = item.text;
			}
        });
		$("#nitemName").text(itemName);
        var createTimeStr = new Date();
        createTimeStr.setTime(createTime);
		$("#ncreateTime").text(createTimeStr.toLocaleString());
		if(status){
			$("#nstatus").text("已读");
		}else{
			$("#nstatus").text("未读");
		}
		$("#nwarnContent").textbox("setValue",warnContent);
		$("#noChangeView").dialog("open").dialog("setTitle", "预警信息详情");
	}else{
		$("#id").val(id);
        $.each(itemOptions,function (i, item) {
            if (item.id === itemName){
                itemName = item.text;
            }
        });
		$("#itemName").text(itemName);
        var createTimeStr = new Date();
        createTimeStr.setTime(createTime);
		$("#createTime").text(createTimeStr.toLocaleString());
        if(status){
            $("#status").text("已读");
		}else{
            $("#status").text("未读");
		}
		 $("#warnContent").textbox("setValue",warnContent); 
		 $("#addView").dialog("open").dialog("setTitle", "预警信息详情");
	} 
} 


