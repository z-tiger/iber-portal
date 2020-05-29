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
    <title>用户指南</title>
  	<style>
  	    body{
  	     font-size:13px;
  	    }
  		#all{
  		 width:90%;
  		/*  border:1px solid red;    */
  		 margin-left:5%;
  		}
  		.indiv{
  		 margin-top:13%;
  		 width:100%;
  		 height:60px;
  	/* 	  border:1px solid yellow;  */
  		}
  		.left{
  		 	width:14%;
  		 	float:left;
  		 	height:60px;
  		 	margin-left:8%;
  		 	margin-right:21%;
  		 /* 	border:1px solid blue;  */
  		}
  		.medium{
  			width:14%;
  			float:left;
  			height:60px;
  			/* border:1px solid green;  */
  			margin-right:21%;
  		}
  		.right{
  			width:14%;
  			float:left;
  			height:60px;
  			margin-right:8%;
  		 /*    border:1px solid black;  */
  		}
  		#all{
  		text-align:center;
  		}
  		a{
			text-decoration:none;
			color:black;
		}
  		
  	</style>
  </head>
  <body>
    <div id="all">
    	<div id="d1" class="indiv">
    		<div class="left">
	    		<a href="userGuide.html#aaa">
	    			<div>
	    				<img  src="app/images/userGuide/icon_huiyuan@3x.png" width="100%" height="100%" >
	    			</div>
	    			<div>
	    				<span>会员</span>
	    			</div>
	    		</a>	
    		</div>
    		<div class="medium">
    			<a href="userGuide.html#bbb">
	    			<div>
	    				<img  src="app/images/userGuide/icon_wangdian@3x.png" width="100%" height="100%" >
	    			</div>
	    			<div>
	    				<span>网点</span>
	    			</div>
    			</a>
    				
    		</div>
    		<div class="right">
    			<a href="userGuide.html#ccc">
	    			<div>
	    				<img  src="app/images/userGuide/icon_sizu@3x.png" width="100%" height="100%" >
	    			</div>
	    			<div>
	    				<span>租车</span>
	    			</div>
	    		</a>	
    				
    		</div>
    	</div>
    	<div id="d2" class="indiv">
    		<div class="left">
				<a href="userGuide.html#qqq">
					<div>
						<img  src="app/images/userGuide/icon_carController@3x.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;" >
						<span>车辆操作</span>
					</div>
				</a>
    			
    		</div>
    		<div class="medium">
				<a href="userGuide.html#lll">
					<div>
						<img  src="app/images/userGuide/icon_houshi@3x.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;">
						<span>后视镜</span>
					</div>
				</a>

    		</div>
    		<div class="right">
				<a href="userGuide.html#mmm">
					<div>
						<img  src="app/images/userGuide/charging.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;">
						<span>充电</span>
					</div>
				</a>
    		</div>
    	</div>
    	<div id="d3" class="indiv">
			<div class="left">
				<a href="userGuide.html#ddd">
					<div>
						<img  src="app/images/userGuide/icon_jiage@3x.png" width="100%" height="100%" >
					</div>
					<div>
						<span>价格</span>
					</div>
				</a>

			</div>
			<div class="medium">
				<a href="userGuide.html#eee">
					<div>
						<img  src="app/images/userGuide/icon_yajin@3x.png" width="100%" height="100%" >
					</div>
					<div>
						<span>押金</span>
					</div>
				</a>

			</div>
			<div class="right">
				<a href="userGuide.html#fff">
					<div>
						<img  src="app/images/userGuide/icon_yue@3x.png" width="100%" height="100%" >
					</div>
					<div>
						<span>余额</span>
					</div>
				</a>
			</div>
    	</div>
    	<div id="d4" class="indiv">
			<div class="left">
				<a href="userGuide.html#ggg">
					<div>
						<img  src="app/images/userGuide/icon_tuikuan@3x.png" width="100%" height="100%" >
					</div>
					<div>
						<span>退款</span>
					</div>
				</a>

			</div>
			<div class="medium">
				<a href="userGuide.html#hhh">
					<div>
						<img  src="app/images/userGuide/icon_xinyon@3x.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;" >
						<span>芝麻信用</span>
					</div>
				</a>

			</div>
			<div class="right">
				<a href="userGuide.html#iii">
					<div>
						<img  src="app/images/userGuide/icon_xianjin@3x.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;">
						<span>现金券</span>
					</div>
				</a>
			</div>
    	</div>
    	<div id="d5" class="indiv">
			<div class="left">
				<a href="userGuide.html#jjj">
					<div>
						<img  src="app/images/userGuide/icon_dinwei@3x.png" width="100%" height="100%" >
					</div>
					<div>
						<span>定位</span>
					</div>
				</a>
			</div>
			<div class="medium">
				<a href="userGuide.html#kkk">
					<div>
						<img  src="app/images/userGuide/icon_wangluo@3x.png" width="100%" height="100%" >
					</div>
					<div>
						<span>网络</span>
					</div>
				</a>
			</div>
    		<div class="right">
				<a href="userGuide.html#nnn">
					<div>
						<img  src="app/images/userGuide/sos.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;" >
						<span>道路救援</span>
					</div>
				</a>
    		</div>
    	</div>
    	<div id="d6" class="indiv">
    		<div class="left">
				<a href="userGuide.html#ooo">
					<div>
						<img  src="app/images/userGuide/icon_jubao.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;" >
						<span>举报</span>
					</div>
				</a>
			</div>
			<div class="medium">
				<a href="userGuide.html#ppp">
					<div>
						<img  src="app/images/userGuide/icon_heimingdan.png" width="100%" height="100%" >
					</div>
					<div style="width:140%;position:relative;left:-20%;">
						<span>黑名单</span>
					</div>
				</a>
			</div>
    	</div>
    </div>
  </body>
</html> 