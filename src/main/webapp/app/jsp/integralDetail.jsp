<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
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
    <title>积分详情</title>
    <!-- Path to Framework7 iOS CSS theme styles-->
    <link rel="stylesheet" href="css/framework7.ios.min.css">
    <!-- Path to Framework7 iOS related color styles -->
    <link rel="stylesheet" href="css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="css/my-app.css">
  	<style>
  		
  	</style>
  </head>
  <body>
  	  <!-- 加分项 -->
   <div style="width:100%;">
   	    <div style="margin-top:10px;border-bottom:1px solid #DDDDDD;padding-bottom:10px;font-family: LucidaGrande;">
   	         <span style="color:#999999;margin-left:6px;font-weight:bolder;">加分项</span>  <span style="font-weight:bolder;color:#999999;float:right;margin-right:16px;">具体加分</span>
   	    </div>
   	    <div style="width:100%;">
   	    	<c:forEach items="${BehaviorList}" var="user" varStatus="vs">  
		         <c:choose>
				   <c:when test="${user.isRatio== '0' && user.isIncrease== '1'}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:36px;line-height:36px;font-size:13px;font-family: LucidaGrande;">
				      	 <span style="margin-left:6px;">${user.name}</span>  <span style="color:red;float:right;margin-right:16px;"> +&nbsp;${user.contriValue}</span> 
				      </div>     
				   </c:when>
				 </c:choose>
				 <c:choose>
				   <c:when test="${user.isRatio== '1' && user.isIncrease== '1'}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:36px;line-height:36px;font-size:13px;font-family: LucidaGrande;">
				      	 <span style="margin-left:6px;">${user.name}&nbsp;+1</span>  <span style="color:red;float:right;margin-right:16px;"> +&nbsp;${user.contriValue}</span> 
				      </div>     
				   </c:when>
				 </c:choose>
				 
			</c:forEach>  
   	    </div>
   	    <div style="border-bottom:1px solid #DDDDDD;height:36px;"></div>
   	    <!-- 减分项 -->
   	    <div style="margin-top:10px;border-bottom:1px solid #DDDDDD;padding-bottom:10px;font-family: LucidaGrande;">
   	         <span style="color:#999999;margin-left:6px;font-weight:bolder;">减分项</span>  <span style="font-weight:bolder;color:#999999;float:right;margin-right:16px;">具体减分</span>
   	    </div>
   	    <div style="width:100%;">
	   	    <c:forEach items="${BehaviorList}" var="user" varStatus="vs">  
		       <c:choose>
				   <c:when test="${user.isRatio== '0' && user.isIncrease== '0' && user.contriValue =='0' && user.behaviorId == 24}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:30px;line-height:30px;font-size:13px;">
				      	 <span style="margin-left:6px;">${user.name}</span>  <span style="color:green;float:right;margin-right:15px;"> 0</span> 
				      </div>     
				   </c:when> 
				   <c:when test="${user.isRatio== '0' && user.isIncrease== '0' && user.contriValue !='0' && user.behaviorId == 24}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:30px;line-height:30px;font-size:13px;">
				      	 <span style="margin-left:6px;">${user.name}</span>  <span style="color:green;float:right;margin-right:15px;"> -&nbsp;${user.contriValue}</span> 
				      </div>     
				   </c:when> 
				   <c:when test="${user.isRatio== '0' && user.isIncrease== '0' && user.contriValue !='0'}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:36px;line-height:36px;font-size:13px;font-family: LucidaGrande;">
				      	 <span style="margin-left:6px;">${user.name}</span>  <span style="color:green;float:right;margin-right:16px;"> -&nbsp;${user.contriValue}</span> 
				      </div>     
				   </c:when>
				  <c:when test="${user.isRatio== '0' && user.isIncrease== '0' && user.contriValue =='0'}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:30px;line-height:30px;font-size:13px;">
				      	 <span style="margin-left:6px;">${user.name}</span>  <span style="color:green;float:right;margin-right:15px;"> 扣至0分</span> 
				      </div>     
				   </c:when> 
			   </c:choose>
			   <c:choose>
				   <c:when test="${user.isRatio== '1' && user.isIncrease== '0'}">  
				      <div style="border-bottom:1px solid #DDDDDD;font-color:#333333;height:36px;line-height:36px;font-size:13px;font-family: LucidaGrande;">
				      	 <span style="margin-left:6px;">${user.name}&nbsp;-1</span>  <span style="color:green;float:right;margin-right:16px;">-&nbsp;${user.contriValue}</span> 
				      </div>     
				   </c:when>
				 </c:choose>
			</c:forEach>  
   	    </div>
   </div>
  	
  </body>
  
</html> 