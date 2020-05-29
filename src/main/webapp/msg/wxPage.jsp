<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>微信文章管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="msg/wxPage.js"></script>
</head>
<body>
		<table id="articleMsgGrid" toolbar="#toolbar"></table>
     <!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
				标题:<input id="s_title"   class="easyui-textbox"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	           	<r:FunctionRole functionRoleId="add_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd" onclick=";">添加</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="update_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="delete_img_text_msg">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove" onclick=";">删除</a>
	            </r:FunctionRole>
        <div>
     

	<!-- 添加用户 -->
	<div id="addView" class="easyui-dialog" closed="true"
		style="width:400px;height:280px;padding:10px 20px">
		<form id="addForm" method="post" name='addForm' enctype="multipart/form-data">
		   <input type="hidden" name="id" id="id">
			<div class="fitem">
				<label>标题:</label> <input name="msgTitle" class="easyui-textbox"
					required="true" missingMessage="请填写标题" id="msgTitle"  style="width: 70%"/>
			</div>
			<div class="fitem">
				<label>正文URL地址:</label> 
				<input  name="msgUrl" id="ContentUrl"  class="easyui-textbox" style='width: 70%;' />
			</div>
			<div style="margin-bottom:10px" id="xztp">
				<input  name="file" id="file" type="file" onchange="javascript:setImagePreview(this,localImag,adPhoto);"  data-options="prompt:'选择图片...',required:true"  style="width:100%;height:24px">
				<div id="localImag">
	              	<img style="margin-left: 0px;" width="200"  id="adPhoto" />
	            </div>
			</div>
			
		</form>
	</div>
	<!-- edit view -->
  <div id="editView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="id" id="e_id">
           <div class="fitem">
				<label>标题:</label> <input name="msgTitle"  class="easyui-textbox"
					required="true" missingMessage="请填写标题" id="e_msgTitle"  style="width: 70%"/>
			</div>
			<div class="fitem">
				<label>正文URL地址:</label> 
				<input  name="msgUrl" id="e_msgUrl"  class="easyui-textbox" style='width: 70%;' />
			</div>
		   
		   <div style="margin-bottom:10px" id="xztp">
				<input  name="efile" id="efile" type="file" onchange="javascript:setImagePreview(this,localImag,adPhotoPreview);"  data-options="prompt:'选择图片...',required:true"  style="width:100%;height:24px">
				<div id="localImag" align="center">
	              	<img style="margin-left: 0px;" width="100" height="130" id="adPhotoPreview" />
	            </div>
			</div>
       </form>
   </div>
	<style type="text/css">
		#conversionForm {
			margin: 0;
			padding: 10px 30px;
		}
		
		.ftitle {
			font-size: 14px;
			font-weight: bold;
			padding: 5px 0;
			margin-bottom: 10px;
			border-bottom: 1px solid #ccc;
		}
		
		.fitem {
			margin-bottom: 5px;
		}
		
		.fitem label {
			display: inline-block;
			width: 80px;
		}
		
		.fitem input {
			width: 160px;
		}
		
		.fitem a {
			margin-right: 5px;
		}
		.table-cls{
			font-size: 10pt;
			border-right: 1px solid #E0E0E0;
			border-bottom: 1px solid #E0E0E0;
			}
			
			.table-cls td{
			  border-left: 1px solid #E0E0E0;
			  border-top: 1px solid #E0E0E0;
			}
</style>
</body>
<script type="text/javascript">
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
</html>