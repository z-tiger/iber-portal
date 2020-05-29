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
    <title>网点服务说明</title>
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
  			<li><span style="color:#00adef;font-weight:bold">1S网点</span>
  				<ul>
  					<li>宜步出行新能源汽车停车、还车</li>
  				</ul>
  			</li>
  			<li><span style="color:#00adef;font-weight:bold">2S网点</span>
  				<ul>
  					<li>新能源汽车租赁、还车</li>
  					<li>充电服务</li>
  				</ul>
  			</li>
  			<li><span style="color:#00adef;font-weight:bold">3S体验中心</span>
  				<ul>
  					<li>新车展示、销售</li>
  					<li>新能源汽车租赁、充电服务</li>
  					<li>宜步出行平台会员现场认证注册</li>
  					<li>试乘试驾、宣讲平台、信息反馈</li>
  				</ul>
  			</li>
  			<li><span style="color:#00adef;font-weight:bold">6S体验中心</span>
  				<ul>
  					<li>新车展示、销售</li>
  					<li>新能源汽车租赁、充电服务</li>
  					<li>宜步出行平台会员现场认证注册</li>
  					<li>新能源汽车零配件供应 </li>
  					<li>售后服务及维修保养</li>
  					<li>试乘试驾、宣讲平台、信息反馈</li>
  				</ul>	
  			</li>
  		</ul>
  	</div>
  	<div id="foot">
  		
  	</div>
  	
    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="js/framework7.min.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="js/my-app.js"></script>
  </body>
</html> 