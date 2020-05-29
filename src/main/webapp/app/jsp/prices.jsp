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
    <title>价格说明</title>
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
		<img src="../app/images/yun1.png" style="width:100%;height:100%;">
	</div>
	<div id="m4" ><!-- 左侧云朵图片 -->
		<img src="../app/images/yun2.png" style="width:100%;height:100%;">
	</div>
	<div id="m5" ><!-- 底部云朵图片 -->
		<img src="../app/images/yun1.png" style="width:100%;height:100%;">
	</div>
  	<div id="headDiv" class="list-block cards-list" style="width:auto;height:90%;" >
		  			<div  id="m1"><!--价格说明图片 -->
		  			<img src="../app/images/prices.png">
		  			</div> 
		  			 <div style="font-size:13px;margin-left:50px;color:red;">
  				  		说明：打开车门十分钟内车辆未行驶即免费。 
			 		  </div>
	  	<c:if test = "${not empty timeShareRate}">
		  	<c:forEach items="${timeShareRate}" var="rate" >
		  		<c:if test = "${not empty rate}">
	  			 	
				      <div class="card-header" style="margin-left:50px; font-size:18px;margin-top:8px;"id="c1">
				      <div id="c2" style="display:inline-block;height:20px;width:8px;background-color:#00BFFF;"></div>	 
				      <label><b>品牌: ${rate.brandName}</b></label>&nbsp;&nbsp;&nbsp;<label><b>车型: ${rate.carTypeName}</b></label>
				      </div>
				      <%-- 
				      <div class="card-content">
				        <a href="get_by_city_code_and_car_type_id?cityCode=${rate.cityCode}&carTypeId=${rate.carTypeId}" 
				        	class="easyui-linkbutton" plain="true" id="priceDetail" 
				        		onclick="getDetailByCityCodeAndCarTypeId(${rate.cityCode}, ${rate.carTypeId})">详情</a>
				        	<a href="javascript:void(0);" class="easyui-linkbutton" plain="true" id="priceDetail" 
				        		onclick="getDetailByCityCodeAndCarTypeId(${rate.cityCode}, ${rate.carTypeId})">详情</a>
				      </div>
				       --%>
				      <div class="card-content" style="margin-left:50px;">
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>白天价格 : 
					      				<c:choose>
					      					<c:when test="${rate.timeRate / 100 > '1'}">
					      						<fmt:formatNumber value= "${rate.timeRate /100}" type="currency" pattern=".00"/> 元 / 30分钟
					      					</c:when>
					      					<c:when test="${rate.timeRate / 100 <= '1'}">
					      						<fmt:formatNumber value= "${rate.timeRate /100}" type="currency" pattern="0.00"/> 元 / 30分钟
					      					</c:when>
					      					
					      				</c:choose>
					      			</label>
					      		</div>
					      	</div>
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>晚上价格 : 
										<c:choose>
					      					<c:when test="${rate.nightTimeRate / 100 > '1'}">
					      						<fmt:formatNumber value= "${rate.nightTimeRate /100}" type="currency" pattern=".00"/> 元 / 30分钟
					      					</c:when>
					      					<c:when test="${rate.nightTimeRate / 100 <= '1'}">
					      						<fmt:formatNumber value= "${rate.nightTimeRate /100}" type="currency" pattern="0.00"/> 元 / 30分钟
					      					</c:when>
					      					
					      				</c:choose>
									</label>
					      		</div>
					      	</div>
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>日封顶价格 : 
					      				<c:choose>
					      					<c:when test="${rate.maxConsump / 100 > '1'}">
					      						<fmt:formatNumber value= "${rate.maxConsump /100}" type="currency" pattern=".00"/> 元
					      					</c:when>
					      					<c:when test="${rate.maxConsump / 100 <= '1'}">
					      						<fmt:formatNumber value= "${rate.maxConsump /100}" type="currency" pattern="0.00"/> 元
					      					</c:when>
					      					
					      				</c:choose>
					      		</div>
					      	</div>
					      	<div class="card-content">
					      		<div class="card-content-inner">
					      			<label>商业保险 :
					      				<c:choose>
					      					<c:when test="${rate.freeCompensationPrice / 100 > '1'}">
					      						<fmt:formatNumber value= "${rate.freeCompensationPrice /100}" type="currency" pattern=".00"/> 元 / 30分钟
					      					</c:when>
					      					<c:when test="${rate.freeCompensationPrice / 100 <= '1'}">
					      						<fmt:formatNumber value= "${rate.freeCompensationPrice /100}" type="currency" pattern="0.00"/> 元 / 30分钟
					      					</c:when>
					      					
					      				</c:choose>
					      		</div>
					      	</div>
					      	<div class="card-content" >
					      		<div class="card-content-inner">
					      			<label>商业保险日封顶 :
					      				<c:choose>
					      					<c:when test="${rate.maxFreeCompensationPrice / 100 > '1'}">
					      						<fmt:formatNumber value= "${rate.maxFreeCompensationPrice /100}" type="currency" pattern=".00"/> 元
					      					</c:when>
					      					<c:when test="${rate.maxFreeCompensationPrice / 100 <= '1'}">
					      						<fmt:formatNumber value= "${rate.maxFreeCompensationPrice /100}" type="currency" pattern="0.00"/> 元
					      					</c:when>
					      					
					      				</c:choose>
					      		</div>
					      	</div>
					      	<div style="height:20px;">
					      	</div>
					    </div>
			    </c:if>
		  	</c:forEach>
	  	</c:if>  
	  			  <div style="color:red;text-indent:1em;font-size:14px;margin-left:40px;margin-right:20px;margin-top:10px;text-align:justify; text-justify:inter-ideograph;">
	  					注: 日封顶价格为一个租车周期内每天的最高计费价，从打开车门开始到还车结束，以24小时计算。 
	  			  </div>
	  				
  				  <div style="color:#696969;font-size:16px;margin-left:40px;margin-right:20px;margin-top:10px;text-align:justify; text-justify:inter-ideograph;">
  				  	<span style="color:red;font-size:30px;position:relative;top:8px;left:-8px;">*</span>&nbsp;<span style="font-size:17px;color:black;font-weight:bold;">以北汽EV160为例</span>
  				  	<p style="text-indent:1em;margin-top:8px;color:black;">租车费用以24小时计算，不超过24小时，租车费用封顶129元。超过24小时，超出部分按30分钟计费，若达到24小时129元封顶费用，叠加租车费用。（商业保险费用计算规则亦同)</p>
  				  	<div style="height:20px;"></div>
  				  	<div id="c2" style="display:inline-block;height:20px;width:8px;background-color:#00BFFF;"></div>
  				  	<span style="font-size:17px;color:black;font-weight:bold;">案例A:租车时间不超过24小时</span>
  				  	<p style="text-indent:1em;margin-top:8px;color:black;">会员当天上午11：00租了一辆北汽EV160，购买了车辆商业保险服务：</p>
					<p style="text-indent:1em;margin-top:5px;margin-bottom:5px;color:black;">1.	当会员用车时间 <=6小时，5小时15分钟（按5.5小时计算）为例：</p>
					<p style="text-indent:1em;color:black;">会员需支付的费用为 10×2×5.5=110元，商业保险费用0.75×2×5.5=8.25元，合计118.25元</p>
					<p style="text-indent:1em;margin-top:5px;margin-bottom:5px;color:black;">2.	6小时<会员当次用车时间<=24小时：</p>
					<p style="text-indent:1em;color:black;">租车费用以129元封顶价格计算，商业保险服务价格以9.9元封顶价格计算，合计138.9元；</p>
					<p style="text-indent:1em;color:red;margin-top:6px;">注：每30分钟10元，1小时租车费用20元；商业保险每30分钟0.75元，1小时计费1.5元。</p>
					
  				  	<!-- <p style="text-indent:1em;margin-top:8px;">会员A当天上午9:30租了一辆北汽EV160,下午5:29还车,购买了车辆商业保险服务。</p>
  				  	<p style="text-indent:1em;">会员A应支付的租车费=租车费(129元)+商业保险费(9.9元)=138.9元,计费规则如下:</p>
					<p style="text-indent:1em;">租车时间:还车时间-约车时间=7小时59分,按8小时计费;</p>
					<p style="text-indent:1em;">租车费:8小时*20元/小时=160元,超过当天封顶价格129元,按129元计费;</p>   
					<p style="text-indent:1em;">商业保险费:8小时*1.5元/小时=12元,超过当天封顶价格9.9元,按9.9元计费。</p> -->
					</p>
					<div style="height:20px;"></div>
					<div id="c2" style="display:inline-block;height:20px;width:8px;background-color:#00BFFF;"></div>
					<span style="font-size:17px;color:black;font-weight:bold;">案例B:租车时间超过24小时</span>
					<p style="text-indent:1em;margin-top:8px;color:black;">会员当天上午11：00租了一辆北汽EV160，购买了车辆商业保险服务：</p>
					<p style="text-indent:1em;margin-top:5px;margin-bottom:5px;color:black;">1.	当会员租车时间<=30小时，以29小时40分钟（按30小时计算）为例：</p>
					<p style="text-indent:1em;color:black;">会员需支付的租车费用为129元+(10×2×6)=249元，商业保险费用为9.9元+（0.75×2×6）=18.9元，合计267.9元。</p>
					<p style="text-indent:1em;margin-top:5px;margin-bottom:5px;color:black;">2.	30小时<租车时间<=48小时:</p>
					<p style="text-indent:1em;color:black;">租车费用以封顶价129元叠加计算，共258元；商业保险服务费以9.9元封顶叠加计算，共19.8元，合计费用277.8元；</p>
					<p style="text-indent:1em;color:red;margin-top:6px;">注：租车费用前24小时按封顶价129元计算，后6小时按10元/30分钟计算；商业保险前24小时按封顶价9.9元计算，后6小时按每30分钟0.75元，1小时计费1.5元，共6小时。</p>
					
					<!-- <p style="text-indent:1em;margin-top:8px;">会员B当天中午13:30租了一辆奇瑞EQ，第二天下午午6:29还车，购买了车辆商业保险服务。</p>
					<p style="text-indent:1em;">会员B应支付的租车费=租车费(198元)+商业保险费(17.6元) =215.6元,计费规则如下:</p>
					<p style="text-indent:1em;">租车时间:当天租车时间10小时30(从租车时间截至到当天凌晨)+第二天租车时间18小时29分(从凌晨截至到还车时间)</p>
					<p style="text-indent:1em;">租车费: 99元/天(当天封顶价格)+99元/天(第二天封顶价格)=198元;</p>
					<p style="text-indent:1em;">商业保险费:8.8元/天(当天封顶价格)+8.8元/天(第二天封顶价格)= 17.6元。</p> -->
					</p>
  				  </div>
			           <div id="m3">  <!-- 底部 建筑车辆图片 -->
			 	           <img  src="../app/images/jianzhu.png" style="opacity:1.0;width:108%" >
			           </div>
</div>
	<!--   <div id="m3">
	  <img  src="../app/images/jianzhu.png" style="opacity:1.0;" >
	  </div> -->
	<!-- 
    Status bar overlay for full screen mode (PhoneGap)
    <div class="statusbar-overlay"></div>
    Panels overlay
    <div class="panel-overlay"></div>
    Left panel with reveal effect
    <div class="panel panel-left panel-reveal">
      <div class="content-block">
        <p>Left panel content goes here</p>
      </div>
    </div>
    Views
    <div class="views">
      Your main view, should have "view-main" class
      <div class="view view-main">
        Top Navbar
        <div class="navbar">
          <div class="navbar-inner">
            We need cool sliding animation on title element, so we have additional "sliding" class
            <div class="center sliding">Awesome App</div>
            <div class="right">
              
                Right link contains only icon - additional "icon-only" class
                Additional "open-panel" class tells app to open panel when we click on this link
             
              <a href="#" class="link icon-only open-panel"><i class="icon icon-bars-blue"></i></a>
            </div>
          </div>
        </div>
        Pages container, because we use fixed-through navbar and toolbar, it has additional appropriate classes
        <div class="pages navbar-through toolbar-through">
          Page, "data-page" contains page name
          <div data-page="index" class="page">
            Scrollable page content
            <div class="page-content">
              <p>Page content goes here</p>
              Link to another page
              <a href="about.html">About app</a>
            </div>
          </div>
        </div>
        Bottom Toolbar
        <div class="toolbar">
          <div class="toolbar-inner">
            Toolbar links
            <a href="#" class="link">Link 1</a>
            <a href="#" class="link">Link 2</a>
          </div>
        </div>
      </div>
    </div>
     -->
   
  </body>
</html> 