<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	
    <script src="echarts/asset/js/esl/esl.js"></script>
    <script src="echarts/asset/js/codemirror.js"></script>
    <script src="echarts/asset/js/javascript.js"></script>
    
    <link href="echarts/asset/css/bootstrap.css" rel="stylesheet">
    <link href="echarts/asset/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="echarts/asset/css/codemirror.css" rel="stylesheet">
    <link href="echarts/asset/css/monokai.css" rel="stylesheet">
    <link href="echarts/asset/css/echartsHome.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/newLoginStyle.css"/>
    <link rel="shortcut icon" href="echarts/asset/ico/favicon.png">
    
    
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	
		
    <style type="text/css">
        .CodeMirror {
            height: 550px;
        }
        body {
    		padding-top: 16px;
    		padding-left:20px;	
    		background-color:rgb(240,240,240);	
   	    }
   	    #title{
   	     width:99%;
   	     height:60px;
   	     background-color:#e7f7ff;
   	     margin-right:40px;
   	     border-bottom:1px solid #bcddf4;
   	     box-shadow:inset  1px 1px 2px 0px #E0FFFF ;
   	    }
   	    #m1{
   	   	margin-left:30px;
   	    }
   	    #sp1{
   	     font-size:16px;
   	     color:#247ea2;
   	     font-weight:bold;
   	     line-height:60px;
   	     margin-left:6px;
   	    }
   	    #sp2{
   	    font-size:14px;
   	    color:#247ea2;
   	    font-weight:bold;
   	    position: absolute;
   	    top:45px;
   	    right:32px;
   	    }
   	    #sp3{
   	     font-size:16px;
   	     color:#247ea2;
   	     font-weight:bold;
   	     line-height:60px;
   	     margin-left:6px;
   	    }
   	   
   	    #middle{
   	    width:99%;
   	    margin-top:20px;
   	    }
   	    #middle-left{
   	    background-color:rgb(231,247,255);
   	    width:49%;
   	    height:60px;
   	    float:left;
   	    border-bottom:1px solid #bcddf4;
   	    }
   	    #middle-right{
   	    background-color:#e7f7ff;
   	    width:50%;
   	    height:60px;
   	    float:right;
   	    
   	    border-bottom:1px solid #bcddf4;
   	    }
   	    #middle-carStatus{
   	    width:96px;
   	    height:40px;
   	    
   	    border:1px solid #79c1f2;
   	    float:right;
   	    margin-top:9px;
   	    
   	    }
   	    #middle-carType{
   	    width:96px;
   	    height:40px;
   	    border:1px solid #79c1f2;
   	    float:right;
   	    margin-top:9px;
   	    margin-right:20px;
   	    }
   	    #sp5{
   	    line-height:40px;
   	    font-size:14px;
   	    color:#247ea2;
   	    margin-left:20px;
   	    }
   	    #sp6{
   	    line-height:40px;
   	    font-size:14px;
   	    color:#247ea2;
   	    margin-left:20px;
   	    }
   	    #middle-right-carType,#middle-right-expenseType{
   	    width:96px;
   	    height:40px;
   	    border:1px solid #79c1f2;
   	    float:right;
   	    margin-top:9px;
   	    }
   	    #middle-right-orderType{
   	    width:96px;
   	    height:40px;
   	    border:1px solid #79c1f2;
   	    float:right;
   	    margin-top:9px;
   	    }
   	    #middle-right-memberType{
   	    width:96px;
   	    height:40px;
   	    border:1px solid #79c1f2;
   	    float:right;
   	    margin-top:9px;
   	    margin-right:20px;
   	    }
   	    .bkblue{
   	    background-color:#afe0fc;
   	    }
   	    .bkwhite{
   	    background-color:white;
   	    }
   	    #middle-left-map1{
   	    background-color:white;
   	    height:400px;
   	    border-top:1px solid #bcddf4;
   	    }
   	    #middle-left-map2,#middle-left-map3{
   	    background-color:white;
   	    height:400px;
   	    border-top:1px solid #bcddf4;
   	    }
   	    #middle-right-map1{
   	    background-color:white;
   	    height:400px;
   	    border-top:1px solid #bcddf4;
   	    }
   	    #middle-right-map2{
   	    background-color:white;
   	    height:400px;
   	    border-top:1px solid #bcddf4;
   	    }
   	    #middle-right-map3{
   	    background-color:white;
   	    height:400px;
   	    border-top:1px solid #bcddf4;
   	    }
   	    #middle-right-map4{
   	    background-color:white;
   	    height:400px;
   	    border-top:1px solid #bcddf4;
   	    }
   	    #foot,#foot-member{
   	    margin-top:496px;
   	    height:500px;
   	   
   	    }
   	    #foot-title,#foot-member-title,#runAnalysis{
   	    width:99%;
   	    background-color:rgb(231,247,255);
   	    height:60px;
   	   	line-height:60px;
   	   	border-bottom:1px solid #bcddf4;
   	    }
   	    #footnetwork{
   	    margin-top:496px;
   	    height:500px;
   	   
   	    } 
   	    #sp7{
   	    font-size:16px;
   	     color:#247ea2;
   	     font-weight:bold;
   	     margin-left:6px;
   	    }
   	    #checkboxs{	
        display:inline-block;
        float:right;
        margin-right:10px;
   	    }
   	    #checkboxs a{	
        	text-decoration:none;
   	    }
   	    .checkboxStyle{
   	    margin-left:12px;
   	    margin-right:22px;
   	    color:#247ea2;
   	    }
   	    /** 居中 */
   	    .checkboxMiddleStyle{
   	    /* position:relative;
   	    z-index:99999; */
   	    background:top center;
   	    text-align:center;
   	    font-size:30px;
   	    margin-left:auto;
   	    margin-right:auto;
   	    margin-top:-40px;
   	    color:#247ea2;
   	    }
   	    /* #foot-body{
   	    width:99%;
   	  
  
   	    } */
   	    #characterArea,#memberCharacterArea{
   	   
   	    margin-right:32px; 
   	    width:99%;
   	    height:230px;
   	    padding-top:20px;
   	    background-color:white;
   	    }
   	    #characterArea-left{
   	     background-size:100%;background:url(echarts/asset/img/newUi/backimg.jpg) ;
   	     /* background-image: url(); */
   	     margin-left:40px;
   	    /*  width:426px; */
   	     width:32%;
   	     height:230px;
   	     float:left;
   	     margin-right:2px;
   	    }
   	    #characterArea-middle{
   	    background-size:100%;background:url(echarts/asset/img/newUi/backimg4.jpg) ;
   	     /* background-color:#26a7e6; */
        /* width:426px; */
   		 width:32%;
   	     height:230px;
   	     float:left;
   	     margin-right:2px;
   	    }
   	    #characterArea-right{
   	    background-color:#118ecb;
   	    height:200px;
   	    /* width:422px; */
   	    width:32%;
   	    float:left;
   	   
   	    
   	    } 
   	     #characterArea_left,#member_characterArea_left{
   	     background-size:100%;background:url(echarts/asset/img/newUi/backimg.jpg) ;
   	     /* background-image: url(); */
   	     margin-left:40px;
   	    /*  width:426px; */
   	     width:32%;
   	     height:230px;
   	     float:left;
   	     margin-right:2px;
   	    }
   	    #characterArea_middle,#member_characterArea_middle{
   	    background-size:100%;background:url(echarts/asset/img/newUi/backimg4.jpg) ;
   	     /* background-color:#26a7e6; */
        /* width:426px; */
   		 width:32%;
   	     height:230px;
   	     float:left;
   	     margin-right:2px;
   	    }
   	    #characterArea_right,#member_characterArea_right{
   	    background-color:#118ecb;
   	    height:200px;
   	    /* width:422px; */
   	    width:32%;
   	    float:left;
   	   
   	    
   	    } 
   	    #img1{
   	    width:44px;
   	    float:left;
   	    position:relative;
   	    top:-11px;
   	    margin-left:230px;
   	    }
   	    #img2{
   	    width:44px;
   	    float:left;
   	    position:relative;
   	    top:-11px;
   	    margin-left:380px;
   	    display:none;
   	    }
   	    #characterArea-left,#characterArea-middle,#middle-carType,#middle-carStatus,#middle-right-memberType,#middle-right-orderType,#middle-right-carType,#middle-memberLevel,#middle-right-expenseType{
   	    	cursor:pointer;
   	    }
   	    .clsLine{
   	    /*margin-top:20px;*/
   	    margin-left:40px;
   	    width:96%;
   	    height:400px;
   	   
   	    top:-40px;
   	    }
   	    .clshistogram{
   	    /*margin-top:20px;*/
	   	    margin-left:40px;
	   	    width:1000px;
	   	    height:400px;
	   	    top:-40px;
   	    }
   	    #foot-bottom,#line-member{
   	    width:99%;
   	    height:400px;
   	    position:relative;
   	    top:-40px;
   	    }
   	    #bar-member{
   	    width:99%;
   	    height:360px;
   	    position:relative;
   	    top:-40px;
   	    }
   	    #characterArea a{
   	    	text-decoration:none;
   	    }
   	    #memberCharacterArea  a{
   	    	text-decoration:none;
   	    }
   	    /** */
   	    .page-nav { overflow:hidden; padding-bottom:10px; margin-bottom:20px;  border-bottom:1px solid #aed0ea;}
        .page-nav ul { display:block; float:left; overflow:hidden; }
		.page-nav ul span {display:block; float:left; height:32px; line-height:32px; color:#000;}
		.page-nav ul li {display:block; float:left; margin-right:10px;}
		.page-nav ul li a {display:block; width:72px; height:32px; line-height:32px; text-align:center; color:#000; background:#e3f0fc;}
		.page-nav ul li a:hover { background:#3baae3; color:#fff;text-decoration: none;}
		.page-nav ul li .active { background:#3baae3; color:#fff;text-decoration: none;}
   	    
    </style>
</head>

<body> 
	 <input type="hidden" value=<%=(String)session.getAttribute("cityCode")%> id="sessionCityCode"/>
	 <input type="hidden" value=<%=(String)session.getAttribute("cityName")%> id="sessionCityName"/>
   <!-- 统计类型切换 -->
	 <div class="page-nav" style="border:0px;margin-bottom:0px;">
			<input type="hidden" name="statisticsTypeValue" id="statisticsTypeValue" value="member"> 
	 </div>
   <!-- 标题部分 -->
	<div id="title"> 
	  <img  src="echarts/asset/img/newUi/china.PNG" id="m1">
	  <span id="sp1"><span id="sp12"></span></span>
	  <span id="sp2"><span id="sp13"></span></span>
	</div>
	<!-- 省市区车辆总数 -->
	<div class="carTotalBox" style="height:600px;width:99%;"> 
		<div class="map-main-left" id="main" style="height:600px;width:100%;background-color:white;box-shadow:inset  1px 1px 2px 0px #E0FFFF;">
		</div>
	</div>
	<!-- 按 条件统计显示车辆数饼状图-->
	<div id="middle">
		<div id="middle-left"> <!-- 左侧整体布局 -->
			<img  src="echarts/asset/img/newUi/data.PNG" id="m1">
		    <span id="sp3"><span id="sp14"> </span></span>
		    
	    	<div id="middle-carType" class="bkwhite">
	    		<span id="sp5"><span id="sp15"></span></span>
	    	</div>
	    	<div id="middle-carStatus" class="bkblue">
	    		<span id="sp6"><span id="sp16">会员类型</span></span>
	    	</div>
	    	<div id="middle-memberLevel" class="bkwhite" style=" width:96px;height:40px;border:1px solid #79c1f2;float:right;margin-top:9px;">
	    		<span id="sp6"><span id="sp22">会员等级</span></span>
	    	</div>
	    	<div id="middle-left-map1" >
	    		
	    	</div>
	    	<div id="middle-left-map2" >
	    	</div>
	    	<div id="middle-left-map3" >
	    	</div>
	    	
	     </div>	
    </div>
	    	
		<div id="middle-right"><!-- 右侧整体布局 -->
			<img  src="echarts/asset/img/newUi/run.PNG" id="m1">
		    <span id="sp3"><span id="sp17"></span></span>
		    <div id="middle-right-memberType" class="bkwhite" >
	    		<span id="sp5"><span id="sp20"></span></span>
	    	</div>
	    	<div id="middle-right-orderType" class="bkwhite">
	    		<span id="sp5"><span id="sp19"></span></span>
	    	</div>
	    	<div id="middle-right-carType" class="bkblue">
	    		<span id="sp5"><span id="sp18"></span></span>
	    	</div>
	    	<div id="middle-right-expenseType" class="bkblue">
	    		<span id="sp5"><span id="sp23">消费类型</span></span>
	    	</div>
	    	<div id="middle-right-map1">
	    	</div>
	    	<div id="middle-right-map2">
	    	</div>
	    	<div id="middle-right-map3">
	    	</div>
	    	<div id="middle-right-map4">
	    	</div>
	   </div>
	 <!-- 会员折线图区域 -->
	 <div id="foot-member" >
	 	<div id="foot-member-title" ><!-- 条件筛选栏 -->
	 		<img  src="echarts/asset/img/newUi/zhexian.PNG" id="m1">
	 		<span id="sp3"><span id="sp21"></span></span>
	 		<div id="checkboxs">
	 			<!-- 隐藏域 -->
	 			<!-- 统计维度：注册数、正式会员数、充值押金、充值余额、充值会员数-->
	 			<input type="hidden" name='MemberCountType' id="MemberCountType" />
	 			<input type="hidden" name='hidMemberType' id="hidMemberType" />
	 			<input type="hidden" name='hidMemberLevel' id="hidMemberLevel" />
	 			<input type="hidden" name='hidMemberCityCode' id="hidMemberCityCode" />
	 			<input type="hidden" name='hidMemberLayer' id="hidMemberLayer" />
	 			<span class="checkboxStyle">
	 				统计维度
	 			<input class="easyui-combobox" name="MemberCountTypeList" id="MemberCountTypeList" style="width:100px; margin-right:22px"
					data-options="valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '注册数',
							value: '1'
						},{
							label: '正式会员数',
							value: '2'
						},{
							label: '充值押金',
							value: '3'
						},{
							label: '充值余额',
							value: '4'
						},{
							label: '充值会员数',
							value: '5'
						},{
							label: '消费金额',
							value: '6'
						},{
							label: '未支付金额',
							value: '7'
						}
						]">
				</span>    
	 			<span class="checkboxStyle">
	 				<input type="radio" name="radio2" id="radioMemberType2" value="1" style="margin-left:10px" />
	 				按会员类型
	 			
	 			<input class="easyui-combobox" name="memberTypeList2" id="memberTypeList2" style="width:100px; margin-right:22px"
					data-options="
					    valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '个人会员',
							value: '0'
						},{
							label: '政企会员',
							value: '1'
						}]">
				</span>    
	 			
	 			<span class="checkboxStyle">
	 				<input type="radio" name="radio2" id="radioMemberLevel2" value="2" style="margin-left:10px" />
	 				按会员等级
	 			<input class="easyui-combobox" name="memberLevelList2" id="memberLevelList2" style="width:100px; margin-right:22px"
					data-options="
					    valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '黑名单',
							value: '0'
						},{
							label: '一星会员',
							value: '1'
						},{
							label: '二星会员',
							value: '2'
						},{
							label: '三星会员',
							value: '3'
						},{
							label: '四星会员',
							value: '4'
						},{
							label: '五星会员',
							value: '5'
						}]">
				</span>	
	 			
				<span class="checkboxStyle">
	 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnQuery"
	 					 onclick="clearSelMember();" >清空</a>
	 			</span>
	 		</div>
	 	</div>
	 	<div id="foot-body"><!-- 数据展示区域 -->
	 		<div id="memberCharacterArea"><!-- 文字图片说明区 -->
	 			<!-- 选择的是“今天”还是“本月” -->
	 			<input type="hidden" name='period_Type2' id="period_Type2" />
	 			
	 			<div id="member_characterArea_left" ><!-- 左 -->  
	 				<a href="javascript:void(0);" onclick="setMemberLineOptions(0);" />
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/today.PNG" >
	 			 		<span id="todayCnt_member" style="margin-left:90px;font-size:26px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:13px;font-weight:bold;color:white;position:relative;top:20px;"class="unit"></span>
	 			 	</div>
	 			 	<div  style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">今日</span>
	 			 		<span class="cntText_member" style="margin-left:70px;font-size:24px;color:white"></span>
	 			 	</div>
	 				<div  style="margin-top:16px;margin-left:70px;">
	 			 		<span id="ratioDayCnt_member" style="white-space: nowrap;margin-left:140px;font-size:40px;font-weight:bold;color:green;"></span>
	 			 	</div>
	 			</div>
	 			<div  id="member_characterArea_middle" ><!-- 中-->
	 				<a href="javascript:void(0);" onclick="setMemberLineOptions(1);" />
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/benyue.PNG" >
	 			 		<span id="thisMonthCnt_member" style="margin-left:90px;font-size:26px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:13px;font-weight:bold;color:white;position:relative;top:20px;"class="unit"></span>
	 			 	</div>
	 			 	<div style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">本月</span>
	 			 		<span class="cntText_member" style="margin-left:70px;font-size:24px;color:white"></span>
	 			 	</div>
	 				<div  style="margin-top:16px;margin-left:70px;">
	 			 		<span id="ratioMonthCnt_member" style="white-space: nowrap;margin-left:140px;font-size:40px;font-weight:bold;color:green;"></span>
	 			 	</div>
	 			</div>
	 			<div id="member_characterArea_right"><!-- 右 -->
	 				<a href="javascript:void(0);"/>
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/leiji.PNG" >
	 			 		<span id="totalCnt_member" style="margin-left:80px;font-size:26px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:13px;font-weight:bold;color:white;position:relative;top:20px;"class="unit"></span>
	 			 	</div>
	 			 	<div   style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">累计</span>
	 			 		<span class="cntText_member" style="margin-left:70px;font-size:24px;color:white"></span>
	 			 	</div>
	 				<div style="margin-top:16px;margin-left:70px;">
	 			 		<span  style="margin-left:180px;font-size:28px;color:white;"></span>
	 			 	</div>
	 			</div>
	 		</div> 
	 		
	 	</div>
	 	<div id="line-member" ><!-- 折线图名称 折线图示 -->
	 		<div class="clsLine" id="cntLineMember"></div>
	 	</div>
	 </div>
	 
	<script src="echarts/core/www/dist/echarts.js"></script>
	<script type="text/javascript" src="echarts/asset/js/run_statistics_echarts_member.js"></script>
	<script type="text/javascript" src="echarts/asset/js/run_statistics_echartsExample.js"></script>
</body>
</html>