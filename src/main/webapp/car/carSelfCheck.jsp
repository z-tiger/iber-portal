<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<meta charset="utf-8">
    <title>自检项配置页面</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="car/carSelfCheck.js"></script>
	<style type="text/css">
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
</style>
</head>
  <body>
    	<table id="dataGrid" toolbar="#toolbar"></table>
    	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
           <form id="queryCarSelfCheck">
				自检类型:<input class="easyui-combobox" id="c-itemType" name="c-itemType" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label: '全部',
										value: ''
									},{
										label:'上车自检',
										value:'0'
									},{
										label:'下车自检',
										value:'1'
									}],
				                    panelHeight:'auto'"  style="width:150px;" >
				自检项状态:<input class="easyui-combobox" id="c-status" name="c-status" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label: '全部',
										value: ''
									},{
										label:'关闭',
										value:'0'
									},{
										label:'启用',
										value:'1',
										selected : true
									}],
				                    panelHeight:'auto'"  style="width:150px;" >   
				客户端类型:<input class="easyui-combobox" id="c-appType" name="c-appType" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label: '全部',
										value: ''
									},{
										label:'会员端',
										value:'member'
									},{
										label:'员工端',
										value:'employee',
									}],
				                    panelHeight:'auto'"  style="width:150px;" >   
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="setStatus" onclick=";">设置状态</a>
			</form>
  	 	</div>
  	 	<!-- add view -->
		  <div id="addView" class="easyui-dialog" closed="true" style="padding:10px 20px">
		       <form name="addViewForm" id="addViewForm" method="post"   enctype="multipart/form-data">
		       	   <input type="hidden" name="id" id="id" />
			       <div class="fitem">
			       		 <label style="width:30%; height: 30px">客户端类型:</label>
			       		  <input class="easyui-combobox" id="appType" name="appType" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label:'员工端',
										value:'employee'
									},{
										label:'会员端',
										value:'member'
									}],
				                    panelHeight:'auto'"  required="true"  missingMessage="请选择客户端类型" style="width:55%; height: 30px" >
			       </div>
			       <div class="fitem">
			       		 <label style="width:30%; height: 30px">自检类型:</label>
			       		 <input class="easyui-combobox" id="itemType" name="itemType" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label:'上车自检',
										value:'0'
									},{
										label:'下车自检',
										value:'1'
									}],
				                    panelHeight:'auto'"  required="true"  missingMessage="请选择自检类型" style="width:55%; height: 30px" >
			       </div>
				   <div class="fitem">
					   <label style="width:30%; height: 30px">必须上传照片：</label>
					   <input class="easyui-combobox" id="uploadStatus" name="uploadStatus"
							  data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label:'否',
										value:'0',
										selected:true
									},{
										label:'是',
										value:'1'
									}],
				                    panelHeight:'auto'"  style="width:15%; height: 30px" >
				   </div>
			        <div class="fitem">
			        	 <label style="width:30%; height: 30px">异常必须上传照片：</label>
			        	 <input class="easyui-combobox" id="exceptionUploadStatus" name="exceptionUploadStatus" 
				           			data-options="
				                    valueField:'value',
				                    textField:'label',
				                    data: [{
										label:'否',
										value:'0',
										selected:true
									},{
										label:'是',
										value:'1'
									}],
				                    panelHeight:'auto'"  style="width:15%; height: 30px" >
			        </div>
		           <div class="fitem">
						<label style="width:30%; height: 30px">自检项名:</label> 
						<input  name="itemName" id="itemName"  class="easyui-textbox"  style="width: 55%;height:30px;" required="true"  missingMessage="请填写自检项名,长度小于6"/>
				   </div>
				   <div class="fitem">
					   <label style="width:30%; height: 30px">示例图片:</label>
					   <input  id="samplePhoto" name="samplePhoto" type="file" onchange="javascript:setImagePreview(this,samplePhotoImag,addsamplePhoto);"  data-options="prompt:'示例图片...'"  style="width:100%;height:24px"     />
					   <div id="samplePhotoImag">
						   <img style="margin-left: 0px;" width="200"  id="addsamplePhoto" />
					   </div>
		       </form>
		   </div>
  </body>

  <script >

      //检查图片的格式是否正确,同时实现预览
      function setImagePreview(obj, localImagId, imgObjPreview) {
          var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp','GIF', 'JPEG', 'PNG', 'JPG', 'BMP'); // 可以上传的文件类型
          if (obj.value == '') {
              $.messager.alert("请选择要上传的图片!");
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
                          //    imgObjPreview.style.width = '100px';
                          //      imgObjPreview.style.height = '130px';
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
                              $.messager.alert("提示","您上传的图片格式不正确，请重新选择!");
                              obj.value = "";
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
                  $.messager.alert("提示","上传图片类型不正确!");
				  obj.value = "";
                  return false;
              }
              return false;
          }
      }
  </script>
</html>
