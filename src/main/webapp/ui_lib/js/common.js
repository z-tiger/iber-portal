// JavaScript Document
$(document).ready(function() {
	//框架
	var width=$(window).width()-$("#side-menu").width()-1;
	var height=$(window).height()-$("#header").height()-$("#footer").height()-1;
	var mainwidth=$(window).width();
	$("#side-menu").attr({
			"style":"height:"+height+"px;"
		});
	$("#content").attr({
			"style":"height:"+height+"px;"
		});
	$("#footer").attr({
			"style":"width:"+mainwidth+"px;"
		});

	//登录页表单点击文字消失
    $(".login input").each(function(){
     var thisVal=$(this).val();
     //判断文本框的值是否为空，有值的情况就隐藏提示语，没有值就显示
     if(thisVal!=""){
       $(this).siblings("span").hide();
      }else{
       $(this).siblings("span").show();
      }
      $(this).keyup(function(){
       var val=$(this).val();
       $(this).siblings("span").hide();
      }).blur(function(){
        var val=$(this).val();
        if(val!=""){
         $(this).siblings("span").hide();
        }else{
         $(this).siblings("span").show();
        }
       })
     }) 
	 
	 
	 //登录提示
    $(".login-but").click(function(){
		$(".login-tip").slideDown().delay(1500).slideUp();
		});
		
		
	//左侧菜单
	$(".menu > li > a").click(function(){
		$(this).parent().children("ul").toggle();
		//$(this).children("em").removeClass("menu-down");
		//$(this).children("em").addClass("menu-up");
	});
		
})


window.onresize=function(){  
	//框架
	var width=$(window).width()-$("#side-menu").width()-1;
	var height=$(window).height()-$("#header").height()-$("#footer").height()-1;
	var mainwidth=$(window).width();
	$("#side-menu").attr({
			"style":"height:"+height+"px;"
		});
	$("#content").attr({
			"style":"height:"+height+"px;"
		});
	$("#footer").attr({
			"style":"width:"+mainwidth+"px;"
		});
	};