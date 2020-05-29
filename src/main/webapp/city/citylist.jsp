<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>菜单列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="city/city.js"></script>

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
</style>
	
</head>

<body>
<div class="easyui-layout" fit="true" border="false" >
				<div data-options="region:'center',split:false">
				     <table id="moduGrid" toolbar="#toolbar"></table>
				</div>
				<div id="addView" class="easyui-dialog" closed="true" style="">
					<form id="UpdateCity" method="post" action=""
						enctype="multipart/form-data" onsubmit="checkCustomer(this)">
			       		<input type="hidden" name='id' id="id">
			       		
						<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
							<tr>
				    			<td>所属城市:</td>
				    			<td>
				    			<input  name="name" id="name" style="width:100%;height:24px" readonly="readonly" >
				    			</td>
				    		</tr>
							<tr>
				    			<td>是否启用:</td>
				    			<td>
				    			<select class="easyui-combobox" name="city_status" id="city_status" style="width:100%;height:24px">
				    				<option value="1">是</option>
				    				<option value="2">否</option>      
				    			</select>
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>上班时间:</td>
				    			<td>
				    			<input class="easyui-datetimebox"  name="start_time" id="start_time" style="width:100%;height:24px" data-options="required:true,groupSeparator:','">
				    			</td>
				    		</tr>
				    		<tr>
				    			<td>下班时间:</td>
				    			<td>
				    			<input class="easyui-datetimebox"  name="end_time" id="end_time" style="width:100%;height:24px" data-options="required:true,precision:0">
				    			</td>
				    		</tr>
				    		
				    	</table>
					</form>
		     </div>
</div>
</body>
</html>