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
  					<li style="font-size: 18px; color: #333333; margin-top: 18px; margin-left:-1px; position: relative; top: -45px">优先选车</li>
  				</ul> 
  			</li>
  			<div style="margin:0 auto;margin-top:-35px;border-top:1px solid #d2d2d2;;width:98%;margin-right: 0px;"></div>
  			<li style="list-style-type: none; height: 480px;margin-top: -10px;">
  				<img src="app/images/rightIntroduce.png" style="margin-top: 20px; margin-right: 10px;margin-left:5px;width:28px;height:28px;">
  				<span style="color:#666666; font-size: 18px; line-height:70px;margin-left: -6px;">权益介绍</span>
  				<ul style="text-align:justify; text-justify:inter-ideograph; position: relative; left: -1px; margin-top: -20px; font-size: 26px;margin-right: 10px;line-height:2.0;">
  					<li style="font-size: 10px; color: #666666;list-style:disc;">五星会员在日常出行租车时享有优先选车的权利。</li>
  					<li style="font-size: 10px; color: #666666;list-style:disc;">
						五级的会员享受的优先选车权益为：<br/>
  						会员在APP上预约车辆后，客户联系客服为其保留车辆，即客服在订单管理上可针对该预约订单进行手动“车辆保留”的操作。到时间也不会自动取消预约。<br/>
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