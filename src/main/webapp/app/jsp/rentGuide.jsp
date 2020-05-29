<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <!-- Required meta tags-->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <!-- Your app title -->
    <title>租车指引</title>
    <!-- Path to Framework7 Library CSS, iOS Theme -->
    <link rel="stylesheet" href="app/css/framework7.ios.min.css">
    <!-- Path to Framework7 color related styles, iOS Theme -->
    <link rel="stylesheet" href="app/css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="app/css/my-app.css">
  </head>
  <body>
   <div class="statusbar-overlay"></div>
    <div class="panel-overlay"></div>
    
    <div class="views">
      <div class="view view-main">
		<!--导航 -->
        <div class="navbar">
            <div class="navbar-inner">
            </div>
        </div>
        <div class="pages">
           <div data-page="loginPage" class="page">
               	<div class="page-content">
							  <div class="content-block row">
							  </div>
           </div>
        </div>
        
        
      </div>
    </div>
    <script type="text/javascript" src="app/js/framework7.min.js"></script>
    <script type="text/javascript" src="app/js/jquery.min.js"></script>
    <script type="text/javascript">
			    var myApp = new Framework7({
			    	modalButtonOk : "确定"
			    });
			    //var $$ = Dom7;	
				var mainView = myApp.addView('.view-main', {
			  		dynamicNavbar: true
				});
			/*=== 默认为 standalone ===*/
			var myPhotoBrowserStandalone = myApp.photoBrowser({
			    photos : [
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/1.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/2.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/3.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/4.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/5.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/6.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/7.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/8.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/9.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/10.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/11.png',
			        'http://eberimg.oss-cn-shenzhen.aliyuncs.com/flowChart/12.png'  
			    ],
			    theme: 'dark',
			    navbar:false,
			    swipeToClose:false,
			    toolbar:true,
			    backLinkText:'',
			    zoom:false
			});
			//加载页面时显示指引图片
			$(function(){
				 myPhotoBrowserStandalone.open();
			});
			
    </script>
</body>
</html>