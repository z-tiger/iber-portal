<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
    <script src="echarts/asset/js/esl/esl.js"></script>
    <script src="echarts/asset/js/codemirror.js"></script>
    <script src="echarts/asset/js/javascript.js"></script>
    <link href="echarts/asset/css/bootstrap.css" rel="stylesheet">
    <link href="echarts/asset/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="echarts/asset/css/codemirror.css" rel="stylesheet">
    <link href="echarts/asset/css/monokai.css" rel="stylesheet">
    <link href="echarts/asset/css/echartsHome.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/newLoginStyle.css"/>
    <link rel="shortcut icon" href="echarts/asset/ico/favicon.png">
    <style type="text/css">
        .CodeMirror {
            height: 550px;
        }
        body {
    		padding-top: 10px;
   	    }
    </style>
</head>

<body>
	<div class="map-box">
		<div class="map-nav">
			<ul>
			    <span>统计类型选择：</span>
				<li><a href="#" id="car" class="active" onclick="statisticsType('car')">车辆</a></li>
				<li><a href="#" id="pile" onclick="statisticsType('pile')">充电桩</a></li>
				<li><a href="#" id="park" onclick="statisticsType('park')">网点</a></li>
				<input type="hidden" name="statisticsTypeValue" id="statisticsTypeValue" value="car"> 
			</ul>
			<a href="#" class="map-nav-back" onclick="backType()">返回</a>
		</div>
	
		<div class="map-main">
			<div class="map-main-left" id="main"></div>
			<div class="map-main-right" id="main1"></div>
		</div>
	</div>

    <script src="echarts/asset/js/jquery.js"></script>
    <script src="echarts/asset/js/bootstrap-transition.js"></script>
    <script src="echarts/asset/js/bootstrap-alert.js"></script>
    <script src="echarts/asset/js/bootstrap-modal.js"></script>
    <script src="echarts/asset/js/bootstrap-dropdown.js"></script>
    <script src="echarts/asset/js/bootstrap-scrollspy.js"></script>
    <script src="echarts/asset/js/bootstrap-tab.js"></script>
    <script src="echarts/asset/js/bootstrap-tooltip.js"></script>
    <script src="echarts/asset/js/bootstrap-popover.js"></script>
    <script src="echarts/asset/js/bootstrap-button.js"></script>
    <script src="echarts/asset/js/bootstrap-collapse.js"></script>
    <script src="echarts/asset/js/bootstrap-carousel.js"></script>
    <script src="echarts/asset/js/bootstrap-typeahead.js"></script>
    <script src="echarts/asset/js/echartsExample.js"></script>
    <script src="echarts/asset/js/config.js"></script>
</body>
</html>