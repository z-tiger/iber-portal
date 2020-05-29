<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <!-- Required meta tags-->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
    
    <!-- Your app title -->
    <title>车位预约说明</title>
    <!-- Path to Framework7 iOS CSS theme styles-->
    <link rel="stylesheet" href="css/framework7.ios.min.css">
    <!-- Path to Framework7 iOS related color styles -->
    <link rel="stylesheet" href="css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="css/my-app.css">
  	<style>
  		#head{
  			height:44px;
  			background:#00adef;
  			color:white;
  			text-align:center;
  			line-height:44px;
  			font-size:13px;
  			font-family:"Times New Roman",Georgia,Serif;
  			
  		}
  		#body{
  			height:80%;
  			
  			margin-left:auto;
  			margin-right:auto;
  		}
  		body li{
  			word-spacing:8px; letter-spacing: 1px;
  			font-size:13px;
  			line-height:2.5;
  			font-family:"Times New Roman",Georgia,Serif;
  		
  		}
  		img{
  			float:left;
  		}
  		
  	</style>
  </head>
  <body>
  <!-- 	<div id="head">
  		<img src="images/app/back.png" style="display:none"><h1>网点介绍</h1>
  	</div> -->
  	<div id="body">
  		<ul id="links">
  			<li style="list-style: none;">
  				<img src="app/images/subscribe.png" style="width: 45px;height: 50px;margin-left: -36px;">
  				<ul style="list-style: none;">
  					<li style="margin-left: -15px;position:relative;top:10px;">该充电车位已被预约，请选择其他车位</li>
  				</ul>
  			</li>
  			<li style="list-style: none;">
  				<img src="app/images/elec.png" style="width: 45px;height: 50px;margin-left: -44px;margin-top: 30px;">
  				<ul style="list-style: none;">
  					<li style="margin-left: -15px;position:relative;top:40px;">该充电车位正在充电，请选择其他车位</li>
  				</ul>
  			</li>
  			<li style="list-style: none;">
  				<img src="app/images/unsubscribe.png" style="width: 55px;height: 55px;margin-left: -50px;margin-top: 60px;">
  				<ul style="list-style: none;">
  					<li style="margin-left: -15px;position:relative;top:70px;">该充电车位可进行预约</li>
  				</ul>
  			</li>
  			<li style="list-style: none;">
  				<img src="app/images/cancel.png" style="width: 55px;height: 55px;margin-left: -54px;margin-top: 94px;">
  				<ul style="list-style: none;">
  					<li style="margin-left: -15px;position:relative;top:104px;">该充电车位已被取消</li>
  				</ul>
  			</li>
  			<li style="list-style: none;">
  				<img src="app/images/maintain.png" style="width: 45px;height: 55px;margin-left: -50px;margin-top: 124px;">
  				<ul style="list-style: none;">
  					<li style="margin-left: -15px;position:relative;top:134px;">该充电车位正在维修，请选择其他车位</li>
  				</ul>
  			</li>
  			
  	</div>
  	<div id="foot">
  		
  	</div>
  	
    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="js/framework7.min.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="js/my-app.js"></script>
  </body>
</html> 