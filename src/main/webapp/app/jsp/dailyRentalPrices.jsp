<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
  <head>
    <!-- Required meta tags-->
    <meta charset="utf-8">
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
    <script type="text/javascript" src="js/prices.js"></script>
    <!-- Your app title -->
    <title>日租价格说明</title>
    <!-- Path to Framework7 iOS CSS theme styles-->
    <link rel="stylesheet" href="css/framework7.ios.min.css">
    <!-- Path to Framework7 iOS related color styles -->
    <link rel="stylesheet" href="css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="css/my-app.css">
 	<style >
 	   *{
 	   	margin:0;padding:0;
 	   }
 	   body{
 	   	line-height:1.5;
 	   }
 		#headDiv{
 		font-size:16px;
 		line-height:1.5;
 		margin-left:15px;
 		margin-right:15px;
		background-image:url(../app/images/juxing.png);
		
		}
		img { 
		height: 20%; width: auto\9; width:100%;
	   }
	   #m1{
	   width:50%;
	   margin-left:25%;
	   margin-top:0px;
	   position: relative;
  	   top: -14px;
	   }
	   #m2{
	    width:18%;
	    position:absolute;
		right:12px;
		top:12px;
	   }
	  #m3{
	  	width:100%;
	  	position: relative;
	  	left:-15px;
	  	bottom:-10px;
	  }
	  #c1{
	  	 position: relative;
	  	 left: -10px;
	  }
	  #c2{
	  	position: relative;
	  	 left: -10px;
	  	 top:5px;
	  }
	  #m4{
	  	width:10%;
	  	position:absolute;
	  	top:20%;
	  }
	  #m5{
	  	width:16%;
	  	position:absolute;
	  	bottom:30%;
	  	right:0%;
	  } 
 	</style>
  </head>
  
  <body style="background-color:#b9e7fe;height:100%;" >
	<div style="height:60px;"><!-- 撑高度 给价格说明板块和底部有间距 -->
	</div>
	<div id="m2" ><!-- 页面右上角云朵图片 -->
		<img src="app/images/yun1.png" style="width:100%;height:100%;">
	</div>
	<div id="m4" ><!-- 左侧云朵图片 -->
		<img src="app/images/yun2.png" style="width:100%;height:100%;">
	</div>
	<div id="m5" ><!-- 底部云朵图片 -->
		<img src="app/images/yun1.png" style="width:100%;height:100%;">
	</div>
  	<div id="headDiv" class="list-block cards-list" style="width:auto;height:90%;" >
		  			<div  id="m1"><!--价格说明图片 -->
		  			<img src="app/images/prices.png">
		  			</div> 
		  			 <div style="font-size:13px;margin-left:50px;color:red;">
  				  		说明：打开车门十分钟内车辆未行驶即免费。 
			 		  </div>
	  	<c:if test = "${not empty dayRentPriceDetail}">
		  	<c:forEach items="${dayRentPriceDetail}" var="rate" >
		  		<c:if test = "${not empty rate}">
	  			 	
				      <div class="card-header" style="margin-left:50px; font-size:18px;margin-top:8px;"id="c1">
				      <div id="c2" style="display:inline-block;height:20px;width:8px;background-color:#00BFFF;"></div>	 
				      <label><b>品牌: ${rate.brandName}</b></label>&nbsp;&nbsp;&nbsp;<label><b>车型: ${rate.modelName}</b></label>
				      </div>
				      
				      <div class="card-content" style="margin-left:50px;">
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>基础价格 : ${rate.basePrice}</label>
					      		</div>
					      	</div>
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>保险费 : ${rate.insurancePrice}</label>
					      		</div>
					      	</div>
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>手续费 : ${rate.procedurePrice}</label>
					      		</div>
					      	</div>
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>异地还车费 : ${rate.remotePrice}</label>
					      		</div>
					      	</div>
					      	<div class="card-content" >
					      		<div class="card-content-inner">
					      			<label>商业保险费 : ${rate.freeCompensationPrice}</label>
					      		</div>
					      	</div>
					      	<div class="card-content" >
					      		<div class="card-content-inner">
					      			<label>超时费 : ${rate.timeoutPrice}</label> 
					      		</div>
					      	</div>
					      	<div style="height:20px;">
					      	</div>
					    </div>
			    </c:if>
		  	</c:forEach>
	  	</c:if>
  		<div id="m3">  <!-- 底部 建筑车辆图片 -->
			<img  src="app/images/jianzhu.png" style="opacity:1.0;width:108%" >
		</div>
	</div>
  </body>
</html> 