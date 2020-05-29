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
	    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="js/framework7.min.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="js/my-app.js"></script>
    
    <!-- Your app title -->
    <title>日租须知</title>
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
  		p{
  			word-spacing:8px; letter-spacing: 1px;
  			font-size:13px;
  			line-height:2.5;
  			font-family:"Times New Roman",Georgia,Serif;
  		}
  	</style>
  </head>
  <body>
  	 <div id="head">
  		<img src="images/app/back.png" style="display:none"><h1>日租须知</h1>
  	</div>  
  	<div id="body">
  		<p>
  		1.日租1天起租，最长租期30天。</br>
  		</p>
  		<p>
  		2.非取车网点还车，收取50元还车调度费。</br>
  		</p>
		<p>
		3.创建订单2小时内需支付，否则订单自动取消。	</br>	
		</p>	
  		<p>
		4.提前取车时间2小时以上免费取消订单，2小时内取消订单收20%手续费，取消订单后退款至原支付账户。</br>	
  		</p>
  		<p>
  		5.取车时间前1小时，系统会推送车牌号至APP上，会员到预约取车网点找到该车可开始用车。</br>	
  		</p>
  		<p>
  		6.可提前1小时取车，即系统推送车牌号后便可取车，不另计费。</br>	
  		</p>
  		<p>
  		7.每个订单可延期还车一次，即变更还车日期，原订单租期和延期租期总和不可超过30天，延期后需支付相应费用。</br>	
  		</p>
  		<p>
  		8.提前还车不退款，超时还车收费10元/小时。</br>	
  		</p>

  	</div>
