$(function() {

	$('#buttonGrid')
	.datagrid(
			{
				title : '按钮管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'sys_button_list',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				}, {
					field : 'name',
					title : '按钮名称',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'code',
					title : '按钮编码',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'img',
					title : '按钮图标',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
					     return "<img src='ui_lib/css/themes/icons/"+val+"'/>";
						 //return "<img src='"+val+"'/>";
					}
				}
				] ],
				pagination : true,
				rownumbers : true
			});
	
	
	 //添加
		$("#btnSave").bind("click",function(){
					    $("#buttonForm").form("clear");
					    $("#buttonView").dialog("open").dialog("setTitle", "添加按钮");
					    url = "sys_button_saveOrUpdate";
			});
		
		//编辑
		$("#btnEdit").bind("click",function(){
		       var selectedRow = $("#buttonGrid").datagrid("getSelections");
			   if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择一条记录", "error");
			   }else{
		 				var userJsonData = selectedRow[0];
					   $("#buttonForm").form("clear");
					   $("#id").val(userJsonData.id);
					   $("#name").textbox('setValue',userJsonData.name);
					   $("#code").textbox('setValue',userJsonData.code);
					   $("#img").textbox('setValue',userJsonData.img);
					   $("#buttonView").dialog("open").dialog("setTitle", "修改按钮");
					    url = "sys_button_saveOrUpdate";
			  }	     
			});
		
		
		//删除
		$("#btnDel").bind("click",function(){
		       var selectedRow = $("#buttonGrid").datagrid("getSelections");
			   var id=selectedRow[0].id;
			   if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要删除的按钮", "error");
			   }else{
			      $.messager.confirm("提示","确定要删除选中的按钮吗?",function(r){
			      if(r){
					$.post("sys_button_del",{"id":id},function(data){
					if(data){
						if(data=="succ"){
							$.messager.alert("提示", "删除成功", "info");
								    $("#buttonGrid").datagrid("reload");
								    $("#buttonGrid").datagrid("clearSelections");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
			      }
				},"text");
				}
				});
			  }	     
			});
		
		
		//添加对话框
		$("#buttonView").dialog( {
			width : "450",
			height : "220",
			top : "40",
			buttons : [ {
				text : "确定",
				iconCls : "icon-save",
				handler : function() {
					$("#buttonForm").form("submit", {
						url : url,
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							if (result== "succ") {
								$.messager.alert("提示", "操作成功", "info");
							    $("#buttonGrid").datagrid("reload");
								$("#buttonView").dialog("close");
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
					$("#buttonView").dialog("close");
				}
			} ]
		});
		
		
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
	//检查图片的格式是否正确,同时实现预览
	function setImagePreview(obj, localImagId, imgObjPreview) {  
	    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); // 可以上传的文件类型
	    if (obj.value == '') {  
	        $.messager.alert("让选择要上传的图片!");  
	        return false;  
	    }  
	    else {  
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
	                    imgObjPreview.style.width = '160px';  
	                    imgObjPreview.style.height = '120px';  
	                    // 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
	                    imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
	                }  
	                else {  
	                    // IE下，使用滤镜
	                    obj.select();  
	                    var imgSrc = document.selection.createRange().text;  
	                    // 必须设置初始大小
	                    localImagId.style.display = "";
	                    localImagId.style.width = "160px";  
	                    localImagId.style.height = "120px";  
	                    // 图片异常的捕捉，防止用户修改后缀来伪造图片
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

    // 显示图片
    function over(imgid, obj, imgbig) {  
        // 大图显示的最大尺寸 4比3的大小 160 120
        maxwidth = 160;  
        maxheight = 120;  

        // 显示
        // obj.style.display = "";
        imgbig.src = imgid.src;  

        // 1、宽和高都超过了，看谁超过的多，谁超的多就将谁设置为最大值，其余策略按照2、3
        // 2、如果宽超过了并且高没有超，设置宽为最大值
        // 3、如果宽没超过并且高超过了，设置高为最大值

        if (img.width > maxwidth && img.height > maxheight) {  
            pare = (img.width - maxwidth) - (img.height - maxheight);  
            if (pare >= 0)  
                img.width = maxwidth;  
            else  
                img.height = maxheight;  
        }  
        else if (img.width > maxwidth && img.height <= maxheight) {  
            img.width = maxwidth;  
        }  
        else if (img.width <= maxwidth && img.height > maxheight) {  
            img.height = maxheight;  
        }  
    }  