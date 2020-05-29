$(function() {
			//查询链接
			$("#btnQuery").bind("click",function(){
			    $("#dataGrid").datagrid("load",{
					'title':$("#_title").textbox("getValue"),
					'code':$("#_code").combobox("getValue"),
					'status':$("#_status").combobox("getValue"),
				});
			});
	$('#dataGrid').datagrid({
				title : '管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'charge_activity_page',
				pageSize : 100,
				pageList : [ 100, 50, 30, 10 ],
				idField : 'id',
				columns : [ [
						{
							field : 'ck',
							checkbox : true
						},
						{
							field : 'title',
							title : '标题',
							align : 'center',
							width : $(this).width() * 0.08,
							formatter : function(value, row, index) {
								return row.title;
							}
						},
						{
							field : 'cityName',
							title : '所属城市',
							width : $(this).width() * 0.08,
							align : 'center'
						},
						{
							field : 'sdName',
							title : '活动类型',
							align : 'center',
							width : $(this).width() * 0.08
						},

						{
							field : 'url',
							title : '活动的URL',
							align : 'center',
							width : $(this).width() * 0.08,
							formatter : function(value, row, index) {
								return row.url;
							}
						},
						{
							field : 'imgUrl',
							title : '活动图片',
							align : 'center',
							width : $(this).width() * 0.08,
							formatter : function(value, row, index) {
								if (value != "") {
									return "<img width='50' height='40' src='"
											+ value
											+ "' onmouseover='display("
											+ row.id
											+ ")' onmouseout='disappear("
											+ row.id
											+ ")'/><div id='box"
											+ row.id
											+ "' onmouseover='display("
											+ row.id
											+ ")' onmouseout='disappear("
											+ row.id
											+ ")' style='display: none;position: fixed;top:100px;'><img width='300'  src='"
											+ value + "'/></div>";
								}
							}
						}, {
							field : 'status',
							title : '状态',
							align : 'center',
							width : $(this).width() * 0.08,
							formatter : function(value, row, index) {
								if (value == 1) {
									return "活动开启";
								} else {
									return "活动关闭";
								}
							}
						}, {
							field : 'startTime',
							title : '活动生效日期时间',
							align : 'center',
							width : $(this).width() * 0.12,
							formatter : function(value, row, index) {
								return row.startTime;
							}
						}, {
							field : 'endTime',
							title : '活动结束日期时间',
							align : 'center',
							width : $(this).width() * 0.12,
							formatter : function(value, row, index) {
								return row.endTime;
							}
						}
//						}, {
//							field : 'createrName',
//							title : '创建人',
//							align : 'center',
//							width : $(this).width() * 0.08,
//							formatter : function(value, row, index) {
//								return row.createrName;
//							}
//						}, {
//							field : 'createTime',
//							title : '创建时间',
//							align : 'center',
//							width : $(this).width() * 0.12,
//							formatter : function(value, row, index) {
//								return row.createTime;
//							}
//						}, {
//							field : 'updaterName',
//							title : '更新人',
//							align : 'center',
//							width : $(this).width() * 0.08,
//							formatter : function(value, row, index) {
//								return row.updaterName;
//							}
//						}, {
//							field : 'updateTime',
//							title : '更新时间',
//							align : 'center',
//							width : $(this).width() * 0.12,
//							formatter : function(value, row, index) {
//								return row.updateTime;
//							}
//						}, 
						] ],
				pagination : true,
				rownumbers : true
			}); 
			
	//构造对话框
	$("#editView").dialog( {
		width : "400",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit", {
					url : "saveOrUpdateActivity",
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
						var data = eval('('+ result +')') ;
						if(data.status == "succ"){
							$.messager.alert("提示", data.msg, "info");
						    $("#dataGrid").datagrid("reload");
						    $("#editView").dialog("close");
						}else{
							$.messager.alert("提示", data.msg, "error");
						    $("#dataGrid").datagrid("reload");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#editView").dialog("close");
			}
		}]
	});
	
	
			
	//添加
	$("#btnAdd").bind("click",function(){
		$("#editViewForm").form("clear");
		$("#activityFilePhoto").prop("src","");
		$("#editView").dialog({title:"添加信息"});
		$("#editView").dialog("open");
	});
			
	//edit
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			$("#e-id").val(selectedRows[0].id);
			$("#e-cityCode").combobox('setValue',selectedRows[0].cityCode);
			$("#e-title").textbox('setValue',selectedRows[0].title); 
			$("#e-url").textbox('setValue',selectedRows[0].url); 
			$("#e-startTime").textbox('setValue',selectedRows[0].startTime); 
			$("#e-endTime").textbox('setValue',selectedRows[0].endTime); 
			$("#activityFilePhoto").attr("src", selectedRows[0].imgUrl);
			$("#e-status").combobox('setValue',selectedRows[0].status);
			$("#e-code").combobox('setValue',selectedRows[0].code);
			$("#editView").dialog({title:"修改信息"});
			$("#editView").dialog("open");
		}
	});
	
	//删除操作
	$("#btnRemove").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的策略", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("deleteActivityById.do",{"id":JsonData.id},function(data){
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
			
	//清空
	$("#clearQuery").bind("click",function(){
		$("#_title").textbox('setValue',''); 
		$("#_status").combobox('setValue',''); 
		$("#_code").combobox('setValue','');
	});
});

//检查图片的格式是否正确,同时实现预览  
function setImagePreview(obj, localImagId, imgObjPreview) {  
	console.log(obj);
	//alert(imgObjPreview.src);
    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); //可以上传的文件类型  
    if (obj.value == '') {  
        $.messager.alert("请选择要上传的图片!");  
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

function display(id){
	document.getElementById("box"+id).style.display="block"; 
}
function disappear(id){
	document.getElementById("box"+id).style.display="none"; 
}
		