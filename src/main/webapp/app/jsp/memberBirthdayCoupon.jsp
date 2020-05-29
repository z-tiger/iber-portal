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
    <title>权益详情</title>
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
  <body style="background-color: rgb(221,221,221);">
  	<div id="body" style="margin-top: -10px;">
  		<ul id="links" style="background-color: white;width: 103%; margin: 0 auto;padding-left: 0px;position:relative;left:-5px;height:80%;" >
  			<li style="list-style-type: none; height: 118px;">
  				<img src="app/images/memberRight.png" style="margin-top: 20px; margin-right: 10px;margin-left:5px; width: 28px;height: 28px;">
  				<span style="color:#666666; font-size: 18px; line-height:70px;margin-left: -6px;">权益名称</span>
  				<ul style="list-style-type: none;">    
  					<li style="font-size: 18px; color: #333333; margin-top: 18px; margin-left:-1px; position: relative; top: -45px">生日大礼包</li>
  				</ul> 
  			</li>
  			<div style="margin:0 auto;margin-top:-35px;border-top:1px solid #d2d2d2;;width:98%;margin-right: 0px;"></div>
  			<li style="list-style-type: none; height: 480px;margin-top: -10px;">
  				<img src="app/images/rightIntroduce.png" style="margin-top: 20px; margin-right: 10px;margin-left:5px;width:28px;height:28px;">
  				<span style="color:#666666; font-size: 18px; line-height:70px;margin-left: -6px;">权益介绍</span>
  				<ul style="text-align:justify; text-justify:inter-ideograph; position: relative; left: -1px; margin-top: -20px; font-size: 26px;margin-right: 10px;line-height:2.0;">
  					<li style="font-size: 10px; color: #666666;list-style:disc;">四星、五星会员在生日时进行租车消费时享有生日优惠。</li>
  					<li style="font-size: 10px; color: #666666;list-style:disc;">会员等级变化，会员享受的生日大礼包权益也会相应发生变化。</li>
  					<li style="font-size: 10px; color: #666666;list-style:disc;">会员支付时，会员可选择生日大礼包权益对订单进行折扣优惠。</li>
  					<li style="font-size: 10px; color: #666666;list-style:disc;">
						不同等级的会员享受生日大礼包权益为：<br/>
						四星会员生日当天享受五折优惠1次;<br/>
  						五星会员生日当天享受免单优惠1次;<br/>
  					</li>
  					<!-- <li style="font-size: 10px; color: #666666;list-style:disc;">从现金券发放之日起，现金券有效期为30天，超过有效期的现金券会自动失效。</li> -->
  				</ul>
  			</li>
  			<div style="margin:0 auto;margin-top:-50%;border-top:1px solid #d2d2d2;width:98%;margin-right: 0px;"></div>
  			<li style="list-style-type: none;">
  				<ul style="list-style-type: none;">
  					<li style="height:6px;"></li>
  					<span style="color:red;font-size:30px;left:-4px;float: left;margin-top: -20px;margin-left: -30px;">*</span>
  					<li style="font-size: 11px; color: #666666;margin-left: -10px;margin-top:4px;line-height:180%;">重要提示：宜步出行保留调整权益内容和方式的权利</li>
  					<li style="height:10px;"></li>
  				</ul>
  			</li>
  		</ul>
  	</div>
  	<div id="foot" style="margin-top:22px;height: 5%;">
  	    <div style="font-size: 13px;color: #666666;margin:0 auto;text-align:center">  --------免责声明--------</div>
  		<!--<div style="width:171px;margin:0 auto;background:url('app/images/titlebg.png');height:12px;background-size:cover;"></div>-->
  		<div style="margin-top: 16px;font-size: 11px;color: #666666;margin-left: 12px;line-height:180%;">
  			本页面内容仅供参考，并非所有VIP会员都可以获得所有权益，会员是否满足权益获得的条件，以宜步出行对该服务的具体规定为准。
			宜步出行享有权益条款的最终解释权。
  		</div>
  </div>
  </body>
  
</html> 