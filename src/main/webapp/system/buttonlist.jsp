<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>按钮列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="system/button.js"></script>	
	
	<style type="text/css" >
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
	</style>
</head>

<body>
   
	<table id="buttonGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	
	<div id="toolbar" style="height:auto">
		 <r:FunctionRole functionRoleId="add_button">
		 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave">添加按钮</a>
		 </r:FunctionRole>
		 <r:FunctionRole functionRoleId="update_button">
		 	<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" id="btnEdit">修改按钮</a>
		 </r:FunctionRole>
		 <r:FunctionRole functionRoleId="delete_button">
		 	<a href="javascript:void(0);" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" id="btnDel">删除按钮</a> 
		 </r:FunctionRole>
		    
	</div>
	
	<!-- 添加角色 -->
	<div id="buttonView" class="easyui-dialog" closed="true" style="width:400px;height:380px;padding:10px 20px">
			<form id="buttonForm" method="post" action="sys_button_saveOrUpdate" enctype="multipart/form-data"
				onsubmit="return checkButton(this)">
					<div class="fitem">
					<input type="hidden" id="id" name="id">
		                <label>名称:</label>
		                <input name="name" class="easyui-textbox" required="true" missingMessage="请填写名称" id="name" style="width: 75%;"/>
		            </div>
		            <div class="fitem">
		                <label>按钮编码:</label>
		                    <input name="code" class="easyui-textbox" required="true" missingMessage="请填写按钮编码" id="code" style="width: 75%;"/>
		            </div>
		             <div class="fitem">
		                <label>按钮图标路径:</label>
		                    <input name="img" class="easyui-textbox" id="img" style="width: 75%;"/>
		            </div>
		            
		             <!--
		            <div class="fitem">
		            	 
		             <label>按钮图片:</label> <input id="fileche" name="fileche" type="file"
						onchange="javascript:setImagePreview(this,localImag,carPhoto);" />
						<div id="localImag">
							<img style="margin-left: 85px;" width="160" height="120"
								id="carPhoto" alt="预览图片"
								onclick="over(carPhoto,divImage,imgbig);" />
						</div>
					</div>	 
					
					 -->
		           
			</form>
			 </div>
		</div>	

</body>
</html>