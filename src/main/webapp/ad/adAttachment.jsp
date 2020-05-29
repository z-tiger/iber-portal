<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>图片管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var attachname = $("input[name='attachname']").val();
			    var attach_type = $("input[name='attach_type']").val();
			    var is_show = $("input[name='is_show']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
				if(event.keyCode==13){
					$('#dataGrid').datagrid('load',{
			           attachname:attachname,
				       attach_type:attach_type,
				       is_show:is_show,
			           queryDateFrom:queryDateFrom,
			           queryDateTo:queryDateTo
		        	});
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var attachname = $("input[name='attachname']").val();
			    var attach_type = $("input[name='attach_type']").val();
			    var is_show = $("input[name='is_show']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    var queryTitle = $("#query_title").textbox("getValue");
			    $('#dataGrid').datagrid('load',{
		    	   attachname:attachname,
			       attach_type:attach_type,
			       is_show:is_show,
		           queryDateFrom:queryDateFrom,
		           queryDateTo:queryDateTo,
		           title:queryTitle
	        	});
			});
			
			
			$('#dataGrid').datagrid( {
				title : '图片管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'ad_attachment_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				pagination : true,
				rownumbers : true,
				columns : [ [ 
				{
					field : 'false',
					checkbox:true
				}, {
					field : 'adName',
					title : '广告名称',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'attachType',
					title : '图片类型',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == 1)
					       return "简图";
					    else if(val == 2)
					       return "详图";
					    else if(val == "null")
					       return "";
					    else
					    	return val;
					}
					
				}, {
					field : 'attachName',
					title : '图片名称',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'uploadUrl',
					title : '图片',
					width : $(this).width() * 0.14,
					align : 'center',
					formatter:function(value, row, index){
					   if(value != ""){
					      return "<img width='40' height='40' src='"+value+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")'/><div id='box"+row.id+"' onmouseover='display("+row.id+")' onmouseout='disappear("+row.id+")' style='display: none;position: absolute;'><img width='300'  src='"+value+"'/></div>";
					   }
					}
					
				}, {
					field : 'attachSize',
					title : '图片大小',
					width : $(this).width() * 0.08,
					align : 'center',
					
				},
				{
					field : 'isShow',
					title : '审核状态',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == 1)
					       return "审核通过";
					    else if(val == 0)
					       return "未审核";
					    else if(val == "null")
					       return "";
					    else
					    	return val;
					}
					
				},
				{
					field : 'linkUrl',
					title : '点击图片地址',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(value, row, index) {
						if(value != "" && value != "null"){
					      return "<a href='"+value+"' target='_blank' title='浏览'>"+value+"</a>";
					    }else{
					   	   return "";
					    }
					}
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.12,
					align : 'center'
				}] ]
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
				//adPhoto.src = "";
				$("#adPhoto").attr("src", "");
			 	$("#addView").dialog("open").dialog("setTitle", "添加图片");
			});
			
			//编辑用户链接
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要编辑的图片", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#id").val(JsonData.id);
					$("#adId").combogrid("setValue",JsonData.adId);
					$("#attachType").combobox("setValue",JsonData.attachType);
					$("#linkUrl").textbox("setValue",JsonData.linkUrl);
// 					adPhoto.src = JsonData.uploadUrl;
					$("#adPhoto").attr("src", JsonData.uploadUrl);
					$("#addView").dialog("open").dialog("setTitle", "编辑广告");
		  		}	     
			});
			
			//构造对话框
			$("#addView").dialog( {
				width : "500",
				height : "500",
				top : "0",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "ad_attachment_saveOrUpdate.do",
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
					$.messager.alert("提示", "请选择要删除的图片", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("ad_attachment_del.do",{"id":JsonData.id},function(data){
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
			
			//审核操作
			$("#btnAuditing").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要审核的图片", "error");
		   		}else{
		   			var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定审核通过吗?",function(r){
			      		if(r){
							$.post("ad_attachment_auditing.do",{"id":JsonData.id},function(data){
								if(data=="success"){
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "审核失败", "info");
								}
							},"text");
						}
					});
		  		}	     
			});
			
			//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
			
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
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
		                    localImagId.style.width = "230px";  
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
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	        
        	广告名称:<input id="query_title"  name="query_title" class="easyui-textbox" style="width:100px;"/>
			图片名称:<input id="attachname"  name="attachname" class="easyui-textbox" style="width:100px;"/>
			图片类型:<select class="easyui-combobox" id="attach_type" name="attach_type"  style="width:80px;" data-options="editable:false,panelHeight:'auto'" > 
		             <option value="">全部</option>
		             <option value="1">简图</option>
		             <option value="2">详图</option>
		          </select>
		          审核状态:<select class="easyui-combobox" id="is_show" name="is_show"  style="width:80px;" data-options="editable:false,panelHeight:'auto'"> 
		             <option value="">全部</option>
		             <option value="1">审核通过</option>
		             <option value="0">未审核</option>
		          </select>
       		时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px;"/>
          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px;"/>
          	
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <r:FunctionRole functionRoleId="add_img">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_img">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_img">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="audit_img">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_okay" plain="true" id="btnAuditing">审核</a>
            </r:FunctionRole>
	        
	     </div>
         
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 450px;height: 450px;padding: 10px 100px 10px 100px">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<div style="margin-bottom:10px">
					<div>选择广告:</div>
					<input class="easyui-combogrid" name="adId" id="adId" style="width:100%;height:24px" data-options="
						required:true,
						editable:false,
						panelHeight: 250,
						idField: 'id',
						textField: 'title',
						url : 'ad_base_list.do',
						pageSize:10,
						pageIndex:1,
						columns : [ [ 
						{
							field : 'title',
							title : '广告名称',
							width : $(this).width() * 0.08,
							align : 'center'
						},  {
							field : 'simpleContext',
							title : '广告简介',
							width : $(this).width() * 0.12,
							align : 'center',
							
						}] ],
						pagination : true,
						rownumbers : true
					">
				</div>
				<div style="margin-bottom:10px">
					<div>图片类型:</div>
					<select class="easyui-combobox" name="attachType" id="attachType" style="width:100%;height:24px" data-options="required:true,editable:false,panelHeight:'auto'">
			        	<option value="1">简图</option>
		             	<option value="2">详图</option>
			        </select>
				</div>
				<div style="margin-bottom:10px">
					<div>点击图片地址:</div>
					<input id="linkUrl"  name="linkUrl" class="easyui-textbox" style="width:100%;height:24px" />
				</div>
				<div style="margin-bottom:10px" id="xztp">
					<input  name="file" id="file" type="file" onchange="javascript:setImagePreview(this,localImag,adPhoto);"  data-options="prompt:'选择图片...',required:true"  style="width:100%;height:24px">
					<div id="localImag">
		              	<img style="margin-left: 0px;" width="200"  id="adPhoto" />
		            </div>
				</div>
			</form>
		 </div>
	</body>
</html>