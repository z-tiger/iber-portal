<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
    
    <!-- Your app title -->
    <title>价  格</title>
    <!-- Path to Framework7 iOS CSS theme styles-->
    <link rel="stylesheet" href="css/framework7.ios.min.css">
    <!-- Path to Framework7 iOS related color styles -->
    <link rel="stylesheet" href="css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="css/my-app.css">
  </head>
  <body>
  	<!-- List View with Cards -->
  	<div class="list-block cards-list">
	  <ul>
  		<li class="card">
	      <div class="card-header" ">
	      	<label>车型 : ${timeShareRate.carTypeName}</label>
	      </div>
	      
	      <div class="card-content">
	      	<div class="card-content">
	      		<div class="card-content-inner">
	      			<label>白天价格 : <fmt:formatNumber value= "${timeShareRate.timeRate /100}" type="currency" pattern="＄.00"/> 元 / 小时</label>
	      		</div>
	      	</div>
	      	<div class="card-content">
	      		<div class="card-content-inner">
	      			<label>晚上价格 : <fmt:formatNumber value= "${timeShareRate.nightTimeRate /100}" type="currency" pattern="＄.00"/> 元 / 小时</label>
	      		</div>
	      	</div>
	      	<div class="card-content">
	      		<div class="card-content-inner">
	      			<label>每天封顶价格 : <fmt:formatNumber value= "${timeShareRate.maxConsump /100}" type="currency" pattern="＄.00"/> 元 / 天</label>
	      		</div>
	      	</div>
	      	<div class="card-content">
	      		<div class="card-content-inner">
	      			<label>保险费 : <fmt:formatNumber value= "${timeShareRate.freeCompensationPrice /100}" type="currency" pattern="＄.00"/> 元 / 小时</label>
	      		</div>
	      	</div>
	      	<div class="card-content">
	      		<div class="card-content-inner">
	      			<label>每天封顶保险费 : <fmt:formatNumber value= "${timeShareRate.maxFreeCompensationPrice /100}" type="currency" pattern="＄.00"/> 元 </label>
	      		</div>
	      	</div>
	      </div>
	      <div class="card-footer">Card footer 1</div>
	     </li>
	  </ul>
	</div>

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
    <!-- Path to Framework7 Library JS-->
    <script type="text/javascript" src="js/framework7.min.js"></script>
    <!-- Path to your app js-->
    <script type="text/javascript" src="js/prices.js"></script>
  </body>
</html> 