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
    title{
	    font-family: PingFangSC-Medium;
		font-size: 17px;
		color: #FFFFFF;
		line-height: 17px;
    } 
    
	input::-webkit-input-placeholder{
	        color: #DDDDDD;
	}
	input::-moz-placeholder{ 
	        color: #DDDDDD;        
	}
	input:-ms-input-placeholder{  
	        color: #DDDDDD;        
	}
    </style>
    <script type="text/javascript">
	    var InterValObj; //timer变量，控制时间
	    var count = 120; //间隔函数，1秒执行
	    var curCount;//当前剩余秒数
		$(function() {
			$("#tip_word").hide();
			var url = window.location.href;
			var name = "";
			var value = "";
			var num = url.indexOf("?")
			url = url.substr(num + 1); //取得所有参数   
			var arr = url.split("&"); //各个参数放到数组里
			for (var i = 0; i < arr.length; i++) {
				num = arr[i].indexOf("=");
				if (num > 0) {
					name = arr[i].substring(0, num);
					if (name == "invitationCode") {
						value = arr[i].substr(num + 1);
						break;
					}
				}
			}
			$("#invitationCode").text(value);
			$('#code').bind({
				click:function(){
					this.src = 'certCode.jsp?it='+Math.random();
				}
			});
		});

		function phoneSys(uri) {
			var u = navigator.userAgent.toLowerCase();
			var isAndroid = u.indexOf('android') > -1
					|| u.indexOf('linux') > -1; //g
			var isIOS = !!u.match(/\(i[^;]+;( u;)? cpu.+mac os x/); //ios终端
			if (isAndroid) {
				window.location.href=uri+"?psys=android";
			}
			if (isIOS) {
				window.location.href=uri+"?psys=ios";
			}
		}
		
		function getCode(){
			var phone = $("#phone").val();
			var imgcode  = $("#imgcode").val();
			if(!(phone && phone != "")){
				$("#tip_word").html("*手机号码不能为空");
				$("#tip_word").show();
				return;
			}
			if(!(phone.length == 11)){
				$("#tip_word").html("*手机号码不正确");
				$("#tip_word").show();
				return;
			}
			if(imgcode == ""){
				$("#tip_word").html("*图形验证码不能为空");
				$("#tip_word").show();
				return;
			}
			curCount = count;
			$("#btnSendCode").text(curCount + "秒后重新发送");
		    $("#btnSendCode").removeAttr("onclick");
		    $("#btnSendCode").css("background","#cccccc");
		    InterValObj = window.setInterval(setRemainTime, 1000); //启动计时器，1秒执行一次
			$.ajax({  
					type:"POST",
		            url : "../../app_invitation_code",  
		            data:{"phone":phone,"imgcode":imgcode},
		            success : function(data){
		            	if(data.status =="success"){
		            		//alert("fasongchenggong");
		            	}else{
		            		$("#tip_word").html("*"+data.msg);
		    				$("#tip_word").show();
		    				 window.clearInterval(InterValObj);//停止计时器
	    			        $("#btnSendCode").attr("onclick","getCode();");
	    			        $("#btnSendCode").text("获取验证码");
	    			        $("#btnSendCode").css("background","#00ADEF");
		            	}
		            }  
		    });
		}
		
		//timer处理函数
		function setRemainTime() {
		      if (curCount == 1) {        
		        window.clearInterval(InterValObj);//停止计时器
		        //$("#btnSendCode").removeAttr("disabled");//启用按钮
		        $("#btnSendCode").attr("onclick","getCode();");
		        $("#btnSendCode").text("获取验证码");
		        $("#btnSendCode").css("background","#00ADEF");
		      } else {
		        curCount--;
		        $("#btnSendCode").text(curCount + "秒后重新获取");
		      }
		 }
		var doflag = true;
		function register(){
			var phone = $("#phone").val();
			var vcode  = $("#vcode").val();
			var imgcode  = $("#imgcode").val();
			var invitationCode = $("#invitationCode").text();
			if(phone == ""){
				$("#tip_word").html("*手机号码不能为空");
				$("#tip_word").show();
				return;
			}
			if(vcode == ""){
				$("#tip_word").html("*短信验证码不能为空");
				$("#tip_word").show();
				return;
			}
			if(imgcode == ""){
				$("#tip_word").html("*图形验证码不能为空");
				$("#tip_word").show();
				return;
			}
			//控制重复提交
			if(!doflag){
				return ;
			}
			doflag = false;
			$.ajax({  
					type:"POST",
		            url : "../../appInvitationRegister",  
		            data:{"phone":phone,"vcode":vcode,"invitationCode":invitationCode,"imgcode":imgcode},
		            success : function(data){
		            	if(data.status == 'success'){
							phoneSys("invitation_success.jsp");
		            	}else{
		            		$("#tip_word").html("*"+data.msg);
		    				$("#tip_word").show();
		            	}
		            } ,
		            complete :function(){
		            	doflag = true;
		            }
		    });
		}
  </script>
  </head>
  <body>
  	<div style="width: 100%;height: auto;" >
  		<img src="../images/invitation/bg1.png" style="width: 100%;">
  	</div>
  	<div style="margin-top:3%;width: 100%;height: auto;text-align: center;font-size: 14px;color: #666666;line-height: 22px;" >
  		注册并认证成功，马上送您20元免费用车现金券。
  	</div>
  	<div style="width: 100%;text-align: center;margin-top:3%;">
  		<span style="font-size: 16px;color: #333333;">邀请码</span>
  		<span style="font-size: 16px;color: #00ADEF;" id="invitationCode"></span>
  	</div>
  	<div style="width: 100%;height: auto;margin-top: 4%;">
	 	<div style="position: relative;margin-left: 7.5%;width:85%;">
			<i style="position: absolute;left: 0;top:13%;z-index:5;background-size:100%;background-image: url('../images/invitation/login_number.png');background-repeat: no-repeat;background-position: 0px 0px;width: 26px;height: 32px;"></i>
			<input id="phone"  style="margin-left:35px;width:60%;border: 0; outline:none; font-size:14px;height: 32px;line-height: 32px;" placeholder="手机号码"/>
  			<div style="border-bottom: 1px solid #DDDDDD;z-index: 99;margin-top: 1%;"></div>
		</div>
  	</div>
  	<div  style="width: 100%;height: auto;margin-top: 4%;">
  		<div style="position: relative;margin-left: 7.5%;width:85%;">
  			<i style="position: absolute;left: 0;top:13%;z-index:5;background-size:100%;background-image: url('../images/invitation/login_image.png');background-repeat: no-repeat;background-position: 0px 0px;width: 26px;height: 32px;"></i>
  			<input id="imgcode"  style="margin-left:35px;width:60%;border: 0; outline:none; font-size:14px;height: 32px;line-height: 32px;" placeholder="图形验证码"/>
  			<i style="height:32px;width:40%;text-align:center;line-height:32px;position: absolute;border-radius: 3px;right:0;font-size: 14px;color: #FFFFFF;">
  				<img src="certCode.jsp" id="code"  style="display:block; height:34px; width:100%;margin-bottom:10px; cursor: pointer;" alt="看不清楚,换一张">
  			</i>
  			<div style="border-bottom: 1px solid #DDDDDD;z-index: 99;margin-top: 1%;"></div>
  		</div>
  	</div>
  	<div  style="width: 100%;height: auto;margin-top: 4%;">
  		<div style="position: relative;margin-left: 7.5%;width:85%;">
  			<i style="position: absolute;left: 0;top:13%;z-index:5;background-size:100%;background-image: url('../images/invitation/login_check.png');background-repeat: no-repeat;background-position: 0px 0px;width: 26px;height: 32px;"></i>
  			<input id="vcode"  style="margin-left:35px;width:60%;border: 0; outline:none; font-size:14px;height: 32px;line-height: 32px;" placeholder="短信验证码"/>
  			<span id="btnSendCode" onclick="getCode();" style="height:32px;width:40%;text-align:center;line-height:32px;position: absolute;background: #00ADEF;border-radius: 3px;right:0;font-size: 14px;color: #FFFFFF;">获取验证码</span>
  			<div style="border-bottom: 1px solid #DDDDDD;z-index: 99;margin-top: 1%;"></div>
  		</div>
  	</div>
  	<div style="font-size:14px;color:red;text-align: center; float: left;margin-left:12%;margin-top: 5%; " id="tip_word"></div>
  	<div style="width: 100%;margin-top: 22%;">
  		<div id="register" onclick="register();" style="width: 85%;height:42px; font-size: 17px;color: #FFFFFF;line-height:42px;text-align: center;background: #00ADEF;border-radius: 3px;margin-left: 7.5%;">
  			立即注册
  		</div>
  	</div>
  	<div style="width: 100%;font-size: 10px;color: #3D6878;text-align: center;margin-top: 16%;margin-bottom: 4%;">
  		本活动最终解释权归宜步出行所有，<br/>
		有任何问题咨询请致电：400-0769-755。
  	</div>
  </body>