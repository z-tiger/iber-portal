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
   	     line-height:60px;
   	     background-color:#e7f7ff;
   	     margin-right:40px;
   	     border-bottom:1px solid #bcddf4;
   	     box-shadow:inset  1px 1px 2px 0px #E0FFFF ;
   	    }
   	    #m2{
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
   	    top:30px;
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
   	    line-height:60px;
   	    border-bottom:1px solid #bcddf4;
   	    }
   	    #middle-right{
   	    background-color:#e7f7ff;
   	    width:50%;
   	    height:60px;
   	    float:right;
   	    line-height:60px;
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
			<input type="hidden" name="statisticsTypeValue" id="statisticsTypeValue" value="park"> 
	 </div>
   <!-- 标题部分 -->
	<div id="title"> 
	  <img  src="echarts/asset/img/newUi/china.PNG" id="m2">
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
			<img  src="echarts/asset/img/newUi/data.PNG" id="m2">
		    <span id="sp3"><span id="sp14"> </span></span>
		    
	    	<div id="middle-carType" class="bkwhite">
	    		<span id="sp5"><span id="sp15">服务类型</span></span>
	    	</div>
	    	<div id="middle-carStatus" class="bkblue">
	    		<span id="sp6"><span id="sp16">网点类型</span></span>
	    	</div>
	    	<div id="middle-left-map1" >
	    	</div>
	    	<div id="middle-left-map2" >
	    	</div>
	     </div>	
	  </div>
	   	<div id="middle-right"><!-- 右侧整体布局 -->
			<img  src="echarts/asset/img/newUi/run.PNG" id="m2">
		    <span id="sp3"><span id="sp17"></span></span>
		    <div id="middle-right-memberType" class="bkwhite" >
	    		<span id="sp5"><span id="sp20">车辆类型</span></span>
	    	</div>
	    	<div id="middle-right-orderType" class="bkblue">
	    		<span id="sp5"><span id="sp19">充电枪类型</span></span>
	    	</div>
	    	<div id="middle-right-map2">
	    	</div>
	    	<div id="middle-right-map3">
	    	</div>
	      </div>
	    	
		
	 <!-- 网点柱状图       style="display:none" -->
	 <div id="footnetwork">
	 	<div id="foot-title"><!-- 条件筛选栏 -->
	 		<img  src="echarts/asset/img/newUi/zhexian.PNG" id="m2">
	 		<span id="sp3"><span id="sp28"></span>网点使用次数</span>
	 		<div id="checkboxs">
	 			<input type="hidden" name='hidCountType1' id="hidCountType1" />
	 			<input type="hidden" name='hidOrderType1' id="hidOrderType1" />
	 			<input type="hidden" name='hidMemberType1' id="hidMemberType1" />	 			
	 			<input type="hidden" name='hidCityCode' id="hidCityCode" />
	 			<input type="hidden" name='hidLayer' id="hidLayer" />
	 			<span class="checkboxStyle">
	 			<input type="radio" name="radio1" id="radioOrder1" value="3" style="margin-left:10px" />		   
					按订单类型
	 			<input class="easyui-combobox" name="orderTypeList1" id="orderTypeList1" style="width:90px; margin-right:22px"
					data-options="valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '时租',
							value: 'TS'
						},{
							label: '日租',
							value: 'DR'
						},{
							label: '充电',
							value: 'charging'
						}]"> 
						</span> 
	 			
	 			<span class="checkboxStyle">
	 				<input type="radio" name="radio1" id="radioMember1" value="2" style="margin-left:10px" />
	 				按会员类型
	 			<input class="easyui-combobox" name="memberTypeList1" id="memberTypeList1" style="width:100px; margin-right:22px"
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
	 				<input type="radio" name="radio1" id="radioCount1" value="1" style="margin-left:10px" />
	 				按统计类型
	 			<input class="easyui-combobox" name="countTypeList1" id="countTypeList1" style="width:60px; margin-right:22px"
					data-options="
					    valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:true,
						data: [{
							label: '约车',
							value: '0'
						},{
							label: '还车',
							value: '1'
						}
						,{
							label: '充电',
							value: '2'
						}]">
				</span>
				<span class="checkboxStyle">
	 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnQuery"
	 					 onclick="clearSelPark();" >清空</a>
	 			</span>
	 		</div>
	 	</div>
	 	<div id="foot-network-body"><!-- 数据展示区域 -->
	 		<div id="characterArea"><!-- 文字图片说明区 -->
	 			<!-- 选择的是“今天”还是“本月” -->
	 			<input type="hidden" name='period_Type' id="period_Type" />
	 			
	 			<div id="characterArea_left" ><!-- 左 -->
	 				<a href="javascript:void(0);" onclick="sethistogramOptions(0);" />
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/today.PNG" >
	 			 		<span id="todayCnt1" style="margin-left:60px;font-size:36px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:20px;font-weight:bold;color:white;position:relative;top:20px;">(次)</span>
	 			 	</div>
	 			 	<div  style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">今日</span>
	 			 		<span class="cntText" style="margin-left:70px;font-size:24px;color:white">网点使用次数</span>
	 			 	</div>
	 				<div  style="margin-top:16px;margin-left:70px;">
	 			 		<span id="ratioDayCnt1" style="margin-left:140px;font-size:40px;font-weight:bold;color:green;"></span>
	 			 	</div>
	 			</div>
	 			<div  id="characterArea_middle" ><!-- 中-->
	 				<a href="javascript:void(0);" onclick="sethistogramOptions(1);" />
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/benyue.PNG" >
	 			 		<span id="thisMonthCnt1" style="margin-left:60px;font-size:36px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:20px;font-weight:bold;color:white;position:relative;top:20px;">(次)</span>
	 			 	</div>
	 			 	<div style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">本月</span>
	 			 		<span class="cntText" style="margin-left:70px;font-size:24px;color:white">网点使用次数</span>
	 			 	</div>
	 				<div  style="margin-top:16px;margin-left:70px;">
	 			 		<span id="ratioMonthCnt1" style="margin-left:140px;font-size:40px;font-weight:bold;color:green;"></span>
	 			 	</div>
	 			</div>
	 			<div id="characterArea_right"><!-- 右 -->
	 				<a href="javascript:void(0);"/>
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/leiji.PNG" >
	 			 		<span id="totalCnt1" style="margin-left:60px;font-size:36px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:20px;font-weight:bold;color:white;position:relative;top:20px;">(次)</span>
	 			 	</div>
	 			 	<div   style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">累计</span>
	 			 		<span class="cntText" style="margin-left:70px;font-size:24px;color:white">网点使用次数</span>
	 			 	</div>
	 				<!-- <div style="margin-top:16px;margin-left:70px;">
	 			 		<span  style="margin-left:180px;font-size:28px;color:white;"></span>
	 			 	</div> -->
	 			</div>	 				 			
	 		</div> 
	 	</div>
	 	<!-- 折线图名称 折线图示 -->
	 	<div id="foot-net-work-bottom" >
	 	<div class="clshistogram" id="clshistogram" style="width:99%;"></div>
	 	</div>
	 </div>
	 <!-- 网点运营分析 -->
	 <div id="networkRunAnalysis" style="margin-top:180px;">
	 	<div id="foot-title"><!-- 条件筛选栏 -->
	 		<img  src="echarts/asset/img/newUi/zhexian.PNG" id="m2">
	 		<span id="sp3">网点运营分析</span>
	 		<div id="checkboxs">
	 			<input type="hidden" name='hidNetworkCountByType' id="hidNetworkCountByType" />
	 			<input type="hidden" name='hidNetworkCountTime' id="hidNetworkCountTime" />
	 			<input type="hidden" name='hidNetworkCountTo' id="hidNetworkCountTo" />	 			
	 		<label class="label" style="text-align: center;color:rgb(36,126,162);background-color:rgb(231,247,255);">时间:&nbsp;</label>
			<input id="networkCountBeginTime" name="networkCountBeginTime" class="easyui-datebox" />
			<label class="label" style="text-align: center;color:rgb(36,126,162);background-color:rgb(231,247,255);">到：</label>
			<input id="networkCountEndTime" name="networkCountEndTime" class="easyui-datebox"  />	
	 			<span class="checkboxStyle">
	 				按统计类型
	 			<input class="easyui-combobox" name="networkCountByType" id="networkCountByType" style="width:100px; margin-right:22px"
					data-options="
					    valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '约车',
							value: '1'
						},{
							label: '还车',
							value: '2'
						},{
							label: '充电',
							value: '3'
						}]">
				</span>	
	 		
				<span class="checkboxStyle">
	 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnQuery"
	 					 onclick="clearSelPark1();" >清空</a>
	 			</span>
	 		</div>
	 	</div>
	 	<!-- 折线图名称 折线图示 -->
	 	<div id="networkRunAnalysis-bottom" >
	 	<div class="clshistogram"  id="networkRunAnalysisChart" style="width:99%;height:400px;"></div>
	 	</div>
	 </div>
	<script src="echarts/core/www/dist/echarts.js"></script>
	<script type="text/javascript" src="echarts/asset/js/run_statistics_echarts_bar.js"></script>
	<script type="text/javascript" src="echarts/asset/js/run_statistics_echartsExample.js"></script>
</body>
</html>