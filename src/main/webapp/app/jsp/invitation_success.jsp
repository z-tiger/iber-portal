<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <title>“宜”起免费用车</title>
    <style type="text/css">
     body{
     	padding: 0;
     	margin: 0;
     	font-family: PingFangSC-Semibold;
     }
    </style>
    <script type="text/javascript">
	    $(function() {
	    	var uri ="https://ibgoing.com/appdl/";
			//var url = window.location.href;
			/* if(url.indexOf('ios') > -1){
				$("#erCode").html('<img id="erCode" style="width: 100%" src="../images/invitation/ybcx_ios_app.png" >');
				uri ="https://itunes.apple.com/us/app/xing-zhi-you-dao/id1035190480?l=zh&ls=1&mt=8";
			}else{
				uri ="http://eberimg.oss-cn-shenzhen.aliyuncs.com/upgrade/724e94b9-1355-450a-8053-2c3ed4035a1d.apk";
			} */
			$("#erCode").html('<img id="erCode" style="width: 100%" src="../images/invitation/ybcx_app.png" >');
			
			$("#downloadApp").click(function(){
				window.open(uri);
			});
		});
    </script>
  </head>
  <body>
  <!-- 
  	 http://ib-img-cdn-sz.ibgoing.com/upgrade/0367ce8e-11f6-4f17-821a-6d240200f5b4.apk
 	 https://itunes.apple.com/us/app/xing-zhi-you-dao/id1035190480?l=zh&ls=1&mt=8
   -->
  <div style="width: 100%;height: auto;" >
  		<img src="../images/invitation/bg2.png" style="width: 100%;">
  </div>
  <div style="width: 100%;height: auto;margin-top: 4%;">
  	<div style="position: relative;margin-left: 31.5%;">
  		<i style="position: absolute;left: 0;top:2%;z-index:5;background-size:100%;background-image: url('../images/invitation/done.png');background-repeat: no-repeat;background-position: 0px 0px;width: 26px;height: 32px;"></i>
  		<span style="font-size: 20px;color: #333333;margin-left: 18%;">注册成功</span>
  	</div>
  </div>
  <div style="width: 100%;height: auto;margin-top: 10%;" >
  	<div id="erCode" style="width: 37.3%;height: 21%;margin-left: 31.5%;">
  		
  	</div>
  	<div style="width: 100%;height: auto;text-align: center;font-size: 12px;color: #00ADEF;line-height: 22px;">
  		扫码下载宜步出行APP
  	</div>
  </div>
  <div style="width: 100%;height: auto;margin-top: 8%;">
  		<div id="downloadApp" style ="width: 85%;height:42px; font-size: 17px;color: #FFFFFF;line-height:42px;text-align: center;background: #00ADEF;border-radius: 3px;margin-left: 7.5%;">
  			立即下载宜步，“宜”起免费用车
  		</div>
  </div>
  <div style="width: 100%;height: auto; margin-top: 10%;">
  	<img src="../images/invitation/bg3.png" style="width: 100%;">
  </div>
  </body>