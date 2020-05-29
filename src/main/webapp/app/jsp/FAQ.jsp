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
    <title>常见问题</title>
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
  	<!-- <div id="head">
  		<img src="images/app/back.png" style="display:none"><h1>常见问题</h1>
  	</div> --> 
  	<div id="body">
  		<p>
  		Q：如何预约车辆</br>
		A：首先您需要成为宜步出行正式会员，其次通过APP缴纳租车押金，最后查找附近的车辆进行约车。
  		</p>
  		<p>
  		Q：如何成为宜步出行会员</br>
		A：通过宜步出行APP可以注册为宜步出行体验会员，体验会员可以通过APP查看车辆信息，也可以通过APP查找充电网点给自己的电动汽车充电。</br>
		</p>
		<p>
		您如果想约车，可以通过手机APP上传您的身份证信息及驾照图片，宜步出行运营人员在审核通过后，您即成为宜步出行的正式会员。
		<p>	
  		<p>
  		Q：租车需要押金吗？</br>
		A：需要。在预订车辆时，可通过充值方式缴纳租车押金。
  		</p>
  		<p>
  		Q：如何预订车辆</br>
		A：通过手机客户端预订。
  		</p>
  		<p>
  		Q：可以随时还车么？</br>
		A：可以随时还车。还车时，前往任一宜步出行网点，即可还车。
  		</p>
  		<p>
  		Q：租车有里程限制吗？</br>
		A：分时共享车辆均无里程限制，但需客户注意车辆电量续航情况，保证车辆顺利归还至网点。
  		</p>
  		<p>
  		Q：预订后，如何取车？</br>
		A：成功预订后，请在规定时间内到达预订的网点，可以通过APP中找车的功能寻找车辆，找到车辆后可以通过APP打开车门、启动车辆。
  		</p>
  		<p>
  		Q：每次租车的数量有限制吗？</br>
		A：有。每位客户每次限租1辆，即在您所租车辆未归还前，不能租用其他车辆。
  		</p>
  		<p>
  		Q：租车时是否能够用现金作为担保押金？</br>
		A：不能。
  		</p>
  		<p>
  		Q：还车后，如何支付租车费用？</br>
		A：租车费用是预付费的方式，您可以先充值再约车，也可以先用车，还车后通过支付订单在APP中支付租车费用。
  		</p>
  		<p>
  		我们的APP支持微信、支付宝支付。
  		</p>
  		<p>
  		Q：我不是中国大陆公民，可以在你们这里租车吗？</br>
		A：暂时不可以。
  		</p>
  		<p>
  		Q：租用车辆时费用包含保险费吗？</br>
		A：不包含，由用户自己选择。
  		</p>
  		<p>
  		Q：当车辆发生事故时，该怎么办？</br>
		A：车辆发生事故时，请第一时间致电客服中心，客服人员将提供专业处理指导。
  		</p>
  		<p>
  		Q：救援车多长时间可以到达现场？</br>
		A：通常情况下，我们救援人员会第一时间与您确定到达时间。
  		</p>
  		<p>
  		Q：车辆违章怎么处理？</br>
		A：通常情况下，车辆违章是由会员自行处理，特殊情况下，宜步出行可以协助会员处理，并收取一定的手续费。
  		</p>
  		<p>
  		Q：是否可以申请退款？</br>
		A：可以申请退押金。宜步出行收到您的退款申请后，会核对您订单支付、租车期间车辆是否损伤、是否违章记录等信息，如果有以上情况有一定费用发生，宜步出行会与您确认后扣除这些费用并将最终退款返还至您填写的银行账户。
  		</p>
  		<p>
  		Q：充值余额可以申请退款吗？</br>
		A：不可以。余额可以用于用车消费，不能进行退款，请您在充值时认真考虑您的用车额度。
  		</p>
  		
  	</div>
  	<div id="foot">
  		
  	</div>
  	
    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="js/framework7.min.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="js/my-app.js"></script>
  </body>
</html> 