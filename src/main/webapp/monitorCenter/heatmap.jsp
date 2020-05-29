<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宜步出行运营支撑系统</title>
<title>热力图</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/sdmenu.css"/>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/newLoginStyle.css"/>
	<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
	<link rel='stylesheet' href='https://unpkg.com/nprogress@0.2.0/nprogress.css'/>

	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<!-- 高德地图 -->
	<script src="http://webapi.amap.com/maps?v=1.4.0&key=a22fc1682b40fc7774e90ee30fa36466"></script>
	<script type="text/javascript" src="http://momentjs.com/downloads/moment.min.js"></script>
	<script type="text/javascript" src='https://unpkg.com/nprogress@0.2.0/nprogress.js'></script>
	<script type="text/javascript" src="monitorCenter/heatmap.js"></script>
</head>
<body>
<div id="container"></div>
<div class="button-group">
	<input type="date" min="2017-09-01" id="date_input"/>
	<input type="button" class="button" value="查询" id="search_btn"/>
	<%--<input type="button" class="button" value="一天" id="day_btn"/>--%>
	<%--<input type="button" class="button" value="一周" id="week_btn"/>--%>
</div>
</body>
</html>