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
    <title></title>
    <!-- Path to Framework7 iOS CSS theme styles-->
    <link rel="stylesheet" href="css/framework7.ios.min.css">
    <!-- Path to Framework7 iOS related color styles -->
    <link rel="stylesheet" href="css/framework7.ios.colors.min.css">
    <!-- Path to your custom app styles-->
    <link rel="stylesheet" href="css/my-app.css">
  	<style>
  		#goCounpon{
  			
  		}
  	</style>
  	<script type="text/javascript">
  		$(function(){
  			$("#btn").click(function(){
  				var number =$("#phone").val();
  				var batchNo = $("#batchNo").val();
  				var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
  			   
  				if (reg.test(number)) {
  				  $.ajax({
                      url:"goNumberRobCounpons",//方法路径URL
                      data: {"number":number,"batchNo":batchNo},//参数
                      dataType: 'json',
                      type: 'POST',
                      success: function (result) { 
                          if (result.status == "0") {
                              alert(result.msg);
                          }else {
                        	  alert(result.msg);
                          }
                      },
                      error: function () {
                          alert('领取失败！');
                      }
                  })
  				 }else{
  				      alert("请输入正确的手机号码");
  				 };
  			});
  		});
  	
  	</script>
  </head>
  <body>
  	<div id="body">
  		<form action="" id="goCounpon" method="">
  			<p>输入手机号抢优惠券：</p><br>
  			<input type="text" id="phone" name="phone"/><input type="button" value="领取" id="btn" /></br>
  			<input type="hidden" id="batchNo" value="20160930103111"/>
  		</form>
  	</div>
  
  	
   
  </body>
</html> 