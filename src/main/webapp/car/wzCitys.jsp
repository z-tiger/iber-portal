<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title>查询违章城市列表</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({
		title : '违章城市列表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'wz_citys_lists',
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'province',
			title : '省份名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'provinceCode',
			title : '省份代码',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'cityName',
			title : '城市名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'cityCode',
			title : '城市代码',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'engine',
			title : '是否需要发动机号0,不需要 1,需要',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'engineno',
			title : '需要几位发动机号',
			width : $(this).width() * 0.08,
			align : 'center',
		},{
			field : 'classa',
			title : '是否需要车架号0,不需要 1,需要',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'classno',
			title : '需要几位车架号',
			width : $(this).width() * 0.08,
			align : 'center'
		}] ],
		pagination : false,
		rownumbers : true
	});

});
</script>
</head>
<body>
   <table id="grid" ></table>
 </body>
 </html>