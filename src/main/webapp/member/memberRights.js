$(function(){
			$('#dataGrid').datagrid( {
				title : '会员权益管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dataListMemberRights.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
					{
						field:'rightsName',
						title:'权益名称',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'iconUrl',
						title:'权益图标',
						align:'center',
						width : $(this).width() * 0.08,
						formatter:function(value, row, index){
							   if(value != ""){
							      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: fixed;top:100px;'><img width='300'  src='"+value+"'/></div>";
							   }
						}
					},
					{
						field:'grayIconUrl',
						title:'权益灰图标',
						align:'center',
						width : $(this).width() * 0.08,
						formatter:function(value, row, index){
							   if(value != ""){
							      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: fixed;top:100px;'><img width='300'  src='"+value+"'/></div>";
							   }
						}
						
					},
					{
						field:'type',
						title:'权益类型',
						align:'center',
						width : $(this).width() * 0.08,
						formatter:function(val,rec){
							if(val=='0'){
								return "优惠券";
							}else if(val=="1"){
								return "折扣券";
							}
						}
						
					},
					{
						field:'descUrl',
						title:'权益描述',
						align:'center',
						width : $(this).width() * 0.15,	
						
					},
					{
						field:'value',
						title:'面值(元)',
						align:'center',
						width : $(this).width() * 0.07,	
						
					},
					{
						field:'number',
						title:'数量(张)',
						align:'center',
						width : $(this).width() * 0.07,
					},
					 {
						field:'createTime',
						title:'创建时间',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'createName',
						title:'创建人',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'updateTime',
						title:'最后更新时间',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'updateName',
						title:'最后更新人',
						align:'center',
						width : $(this).width() * 0.1,
					}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			//添加会员等级
			$("#btnAdd").bind("click",function(){
				clearForm();
				$("#adPhoto").attr("src", "");
				$("#adPhoto1").attr("src", "");
			 	$("#addView").dialog("open").dialog("setTitle", "添加会员等级");
			});
				//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			}); 
			//回车键查询
			$(document).keydown(function(event){
			   	var rightsName = $("input[name='q_rightsName']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
					  rightsName:rightsName,
		           	 
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var rightsName = $("input[name='q_rightsName']").val();
			    $('#dataGrid').datagrid('load',{
			    	rightsName:rightsName,
		          
		        });
			});
				
						
				//修改信息
			$("#btnEdit").bind("click",function(){
				$("#addView").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要修改的记录", "error");
				}else{
					var JsonData = selectedRows[0];
					$("#id").val(JsonData.id);
					$("#rightsName").textbox("setValue",JsonData.rightsName);
					$("#type").combobox("setValue",JsonData.type);
					$("#adPhoto").attr("src", JsonData.iconUrl);
					$("#descUrl").textbox("setValue",JsonData.descUrl);
					$("#value").textbox("setValue",JsonData.value);
					$("#number").textbox("setValue",JsonData.number);
					$("#adPhoto1").attr("src", JsonData.grayIconUrl);
					$("#addView").dialog({title:"修改信息"});
					$("#addView").dialog("open");
				}
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "400",
				height : "520",
				top : "80",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addViewForm").form("submit", {
							url : "saveOrUpdateMemberRights.do",
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
					$.messager.alert("提示", "请选择要删除的记录", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("deleteMemberRightsById.do",{"id":JsonData.id},function(data){
								if(data=="success"){
									$.messager.alert("提示", "删除成功", "info");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "删除失败", "info");
								}
							},"text");
						}
					});   
		  		}	     
			});
			
		});

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
			                // 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
			                imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
			            }else {  
			                // IE下，使用滤镜
			                obj.select();  
			                var imgSrc = document.selection.createRange().text;  
			                // 必须设置初始大小
			                localImagId.style.display = "";
			                localImagId.style.width = "250px";  
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
		
		function display(id){
			document.getElementById("box"+id).style.display="block"; 
		}
		function disappear(id){
			document.getElementById("box"+id).style.display="none"; 
		}
	    function clearForm(){
			$('#addViewForm').form('clear');
		}