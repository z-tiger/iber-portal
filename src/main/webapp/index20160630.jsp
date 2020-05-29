<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分时租赁管理平台</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/login.css"/>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css"/>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css"/>
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
</head>


<body onload="getExplorerInfo()">
   <div style="height: 250px;border: 0px solid red;background-image:url(images/login-1.jpg); background-size: cover;padding-top: 20px;">
	<div class="logo_big"></div>
   </div>
   <form id="ff" name="loginform" method="post" action="login">
    <div class="login_content">
      <div style="width:350px; margin:0 auto;">
        <div class="dl">
           <div style="font-size:22px; color:red;">${error}</div>
            <div class="loginIpt clearfix" style="margin-bottom:19px;">
				<span class="login_user">用户名</span>
                <input name="username" id="username" type="text" value="${username }"  class="loginTdIpt" />
			</div>
            <div class="loginIpt clearfix" style="margin-bottom:19px;">
	             <span class="login_user">密码</span>
	             <input name="pwd" id="pwd" type="password" value="${pwd }" class="loginTdIpt"/>
			</div>
            <div class="loginIpt clearfix" style="margin-bottom:19px;">
				<span class="login_user">验证码</span>
	            <input name="vcode" id="vcode" class="loginTdIpt" style="width:120px;" />
	            <img src="makeCertPic.jsp" id="code" value="${vcode}" style="display:block; float:right; cursor: pointer;width:125px;" alt="看不清楚,换一张">
			</div>
	        <a id="submitForm()" name="submitForm()" onClick="submitForm()" href="#" class="login-but">登录</a>
         </div>
    </div>
    </form>
</div>
<div class="foot">© 2016 出行 <a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备16029016号-1</a></div>
<script type="text/javascript">

	$("#username").focus();
	
    $("#ff").on("keypress",function(e){
        var key= e.which || e.keyCode;
        if(key==13){
            submitForm() ;
        }
    });
    
	function submitForm() {
		
		if ($('#username').val()=='') {
			alert('必须输入用户名');
			$("#username").focus();
			return false;
		}
		
		if ($('#pwd').val()=='') {
			alert('必须输入密码');
			$("#pwd").focus();
			return false;
		}
		
		if ($('#vcode').val()=='') {
			alert('必须输入验证码');
			$("#vcode").focus();
			return false;
		}
		
		document.loginform.submit();
		
	}

	$(function() {
		bindShow('vcode');
		$('#code').bind({
			click:function(){
				this.src = 'makeCertPic.jsp?it='+Math.random();
			}
		});
	});

	function bindShow(inputname) {
		if ($('#'+inputname).val() != '') $('#'+inputname+'_tips').hide();
		$('#'+inputname).bind({
			focus:function(){
				$('#'+inputname+'_tips').hide();
			},
			blur:function(){
				if (this.value == ""){
					$('#'+inputname+'_tips').show();
				}
			}
		});
	}
	
	
	function getExplorerInfo() {
		 var explorer = window.navigator.userAgent.toLowerCase() ;
		 //ie 
		 if (explorer.indexOf("msie") >= 0) {
		 		jQuery.messager.show({ 
					title:'温馨提示:', 
					msg:'<div sttyle="font-size: 14px;"><font style="line-height:190%;">IE下有部分功能加载速度过慢，为了您更好的体验，推荐使用谷歌和火狐浏览器!</font><br/><a href="http://xycarimg.oss-cn-hangzhou.aliyuncs.com/explorer%2FChromeStandalone_50.0.2661.102_Setup.exe" target=_blank><font color="red" >谷歌浏览器下载</font></a>&nbsp;&nbsp;&nbsp;<a href="http://xycarimg.oss-cn-hangzhou.aliyuncs.com/explorer%2FFirefox-latest.exe" target=_blank><font color="red" >火狐浏览器下载</font></a></div>', 
					timeout:500000, 
					showType:'slide',
					height:'200px',
					widht:'300px'
				}); 
		 }
 	}
</script>

</body>

</html>
