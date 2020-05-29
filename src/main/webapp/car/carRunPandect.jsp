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
   	    line-height:60px;
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
   	    font-size:16px;
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
   	    a:hover {
			text-decoration: none;
			cursor:text;
		}
    </style>
</head>

<body> 
	 <input type="hidden" value=<%=(String)session.getAttribute("cityCode")%> id="sessionCityCode"/>
	 <input type="hidden" value=<%=(String)session.getAttribute("cityName")%> id="sessionCityName"/>
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
		<div id="middle-left" style="height:60px;"> <!-- 左侧整体布局 -->
			<img  src="echarts/asset/img/newUi/data.PNG" id="m1">
		    <span id="sp3"><span id="sp14">运营车辆数据</span></span>
		    
	    	<div id="middle-carType" class="bkwhite">
	    		<span id="sp5"><span id="sp15">车辆类型</span></span>
	    	</div>
	    	<div id="middle-carStatus" class="bkblue">
	    		<span id="sp6"><span id="sp16">车辆状态</span></span>
	    	</div>
	    	<div id="middle-left-map1" >
	    		
	    	</div>
	    	<div id="middle-left-map2" >
	    	</div>
	    	
	    	<div id="car_max_run" style="position:relative;top: -45px;left:20%;">
	    		<span id="car_max_run_1"></span>
	    		<span><mark id="car_max_run_2"></mark></span>
	    		<span id="car_max_run_3"></span>
	    	</div>
	     </div>	
	  </div>
	    	
		<div id="middle-right"><!-- 右侧整体布局 -->
			<img  src="echarts/asset/img/newUi/run.PNG" id="m1">
		    <span id="sp3"><span id="sp17">运营车辆数据</span></span>
		    <div id="middle-right-memberType" class="bkwhite" >
	    		<span id="sp5"><span id="sp20">会员类型</span></span>
	    	</div>
	    	<div id="middle-right-orderType" class="bkwhite">
	    		<span id="sp5"><span id="sp19">订单类型</span></span>
	    	</div>
	    	<div id="middle-right-carType" class="bkblue">
	    		<span id="sp5"><span id="sp18">车辆类型</span></span>
	    	</div>
	    	<div id="middle-right-map1">
	    	</div>
	    	<div id="middle-right-map2">
	    	</div>
	    	<div id="middle-right-map3">
	    	</div>
	      </div>
	  <!-- 底部折线趋势图 -->
	 <div id="foot">
	 	<div id="foot-title"><!-- 条件筛选栏 -->
	 		<img  src="echarts/asset/img/newUi/zhexian.PNG" id="m1">
	 		<span id="sp3"><span id="sp21"></span></span>
				 <span class="checkboxMiddleStyle" id="id_radioCnt" >
					<input type="radio" name="radioCnt" id="radioOrderCar" value="1" style="margin-left:10px" />
						预约
					<input type="radio" name="radioCnt" id="radioReturn" value="2" style="margin-left:10px" />
						还车
				</span>  
				
			 	<!-- 预约次数中的全部或取消 -->
			 	<span class="checkboxMiddleStyle" id="id_radioOrderCnt">
					<input type="radio" name="radioOrderCnt" id="radioAll" value="1" style="margin-left:10px" />
						全部
					<input type="radio" name="radioOrderCnt" id="radioCancel" value="2" style="margin-left:10px" />
						取消
				</span>  
	 		<div id="checkboxs">
	 			<!-- 隐藏域 -->
	 			<!-- 统计维度：次数、人数、时长、里程、收入 -->
	 			<input type="hidden" name='countType' id="countType" />
	 			<input type="hidden" name='hidBrandName' id="hidBrandName" />
	 			<input type="hidden" name='hidMemberType' id="hidMemberType" />
	 			<input type="hidden" name='hidOrderType' id="hidOrderType" />
	 			<input type="hidden" name='hidCityCode' id="hidCityCode" />
	 			<input type="hidden" name='hidLayer' id="hidLayer" />
	 			
	 			<span class="checkboxStyle">
	 				统计维度
	 			<input class="easyui-combobox" name="countTypeList" id="countTypeList" style="width:90px; margin-right:22px"
					data-options="valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '租赁次数',
							value: '0'
						},{
							label: '租赁人数',
							value: '1'
						},{
							label: '租赁时长',
							value: '2'
						},{
							label: '租赁里程',
							value: '3'
						},{
							label: '租赁收入',
							value: '4'
						},{
							label: '预约次数',
							value: '5'
						}]">
				</span>    
	 			<span class="checkboxStyle">
	 				<input type="radio" name="radio" id="radioBrand" value="1" style="margin-left:10px" />
	 				按车辆品牌
	 			
	 			<input class="easyui-combobox" name="brandNameList" id="brandNameList" style="width:80px; margin-right:22px"
					data-options="url:'getBrandNameList.do', 
						method:'get',
					    valueField:'id',
					    textField:'brandName',
					    panelHeight:'auto'">
				</span>    
	 			
	 			<span class="checkboxStyle">
	 				<input type="radio" name="radio" id="radioMember" value="2" style="margin-left:10px" />
	 				按会员类型
	 			<input class="easyui-combobox" name="memberTypeList" id="memberTypeList" style="width:100px; margin-right:22px"
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
	 				<input type="radio" name="radio" id="radioOrderType" value="3" style="margin-left:10px" />
	 				按订单类型
	 			<input class="easyui-combobox" name="orderTypeList" id="orderTypeList" style="width:60px; margin-right:22px"
					data-options="
					    valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:true,
						data: [{
							label: '分时',
							value: 'TS'
						},{
							label: '日租',
							value: 'DR'
						}]">
				</span>
				<span class="checkboxStyle">
	 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnQuery"
	 					 onclick="clearSelRent();" >清空</a>
	 			</span>
	 		</div>
	 	</div>
	 	<div id="foot-body"><!-- 数据展示区域 -->
	 	
	 		<div id="characterArea"><!-- 文字图片说明区 -->
	 		<!-- 租赁次数中的预约或还车 -->
			 	
	 			<!-- 选择的是“今天”还是“本月” -->
	 			<input type="hidden" name='periodType' id="periodType" />
	 			
	 			<div id="characterArea-left" ><!-- 左 -->
	 				<a href="javascript:void(0);" onclick="setLineOptions(0);" />
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/today.PNG" >
	 			 		<span id="todayCnt" style="margin-left:90px;font-size:36px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:20px;font-weight:bold;color:white;position:relative;top:20px;"class="unit">(次)</span>
	 			 	</div>
	 			 	<div  style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">今日</span>
	 			 		<span class="cntText" style="margin-left:70px;font-size:24px;color:white">车辆租赁次数</span>
	 			 	</div>
	 				<div  style="margin-top:16px;margin-left:70px;">
	 			 		<span id="ratioDayCnt" style="margin-left:140px;font-size:40px;font-weight:bold;color:green;white-space: nowrap;"></span>
	 			 	</div>
	 			</div>
	 			<div  id="characterArea-middle" ><!-- 中-->
	 				<a href="javascript:void(0);" onclick="setLineOptions(1);" />
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/benyue.PNG" >
	 			 		<span id="thisMonthCnt" style="margin-left:90px;font-size:36px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:20px;font-weight:bold;color:white;position:relative;top:20px;"class="unit">(次)</span>
	 			 	</div>
	 			 	<div style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">本月</span>
	 			 		<span class="cntText" style="margin-left:70px;font-size:24px;color:white">车辆租赁次数</span>
	 			 	</div>
	 				<div  style="margin-top:16px;margin-left:70px;">
	 			 		<span id="ratioMonthCnt" style="margin-left:140px;font-size:40px;font-weight:bold;color:green;white-space: nowrap;"></span>
	 			 	</div>
	 			</div>
	 			<div id="characterArea-right"><!-- 右 -->
	 				<a href="javascript:void(0);"/>
	 				<div  style="margin-top:15px;margin-left:70px;">
	 			 		<img  src="echarts/asset/img/newUi/leiji.PNG" >
	 			 		<span id="totalCnt" style="margin-left:80px;font-size:36px;font-weight:bold;color:white;position:relative;top:20px;"></span><span style="font-size:20px;font-weight:bold;color:white;position:relative;top:20px;"class="unit">(次)</span>
	 			 	</div>
	 			 	<div   style="margin-top:0px;margin-left:70px;">
	 			 		<span style="margin-left:10px;font-size:24px;color:white;">累计</span>
	 			 		<span class="cntText" style="margin-left:70px;font-size:24px;color:white">车辆租赁次数</span>
	 			 	</div>
	 				<div style="margin-top:16px;margin-left:70px;">
	 			 		<span  style="margin-left:180px;font-size:28px;color:white;"></span>
	 			 	</div>
	 			</div>
	 			
	 			
	 		</div> 
	 	</div>
	 	<div id="foot-bottom"><!-- 折线图名称 折线图示 -->
	 		<div class="clsLine" id="cntLine"></div>
	 	</div>
		
	 </div>
	
	 <div id="carRunAnalysis">
	 	<div id="foot-title" style="margin-top:180px">
	 		<img  src="echarts/asset/img/newUi/zhexian.PNG" id="m1">
	 		 <span id="sp3"><span id="sp22">车辆运营分析</span></span>
	 		<div style="margin-right: 30px; display: inline-block; float: right;">
	 		<div id ="last_month" onclick="getCarRunStatistics('last_month',this)" style="display: inline-block;width: 96px; height: 40px; border: 1px solid #79c1f2;float: right; margin-top: 9px;" class="bkwhite">
	 			<span style='margin-left: 35px;line-height: 40px; font-size: 14px;color: #247ea2;'><span>上 月</span></span>
	 		</div>
	 		<div id="last_week" onclick="getCarRunStatistics('last_week',this)" style="display: inline-block;width: 96px; height: 40px; border: 1px solid #79c1f2;float: right; margin-top: 9px;" class="bkwhite">
	 			<span style='margin-left: 35px;line-height: 40px; font-size: 14px;color: #247ea2;'><span>上周</span></span>
	 		</div>
	 		<div id="is_today" onclick="getCarRunStatistics('is_today',this)" style="display: inline-block; width: 96px; height: 40px; border: 1px solid #79c1f2;float: right; margin-top: 9px;" class="bkblue">
	 			<span style='margin-left: 35px;line-height: 40px; font-size: 14px;color: #247ea2;'><span >昨日</span></span>
	 		</div>
	 		</div>
	 	</div>
	 	<div id="carRunAnalysisBody" style="margin-top:26px;">
	 		<div id="carRunCharacterArea">
	 			<div id="carRunLeft" style="float:left; width:24%;height:200px;margin-left:40px;background-color:rgb(110,191,235);text-align:center;" >
	 				
	 				<div  style="margin-top:10%;  width:100%;height:20%;">
	 			 		<span style="font-size:30px;color:white;">租车率</span>
	 			 	</div>
	 			 	<div  style="margin-top:3%; width:100%;height:20%;">
	 			 		<span style="font-size:60px;color:white;" id="sp24"></span><span style="font-size:36px;color:white;">%</span>
	 			 	</div>
	 				
	 			</div>
	 			<div  id="carRunMeidum" style="float:left; width:24%;height:200px;background-color:rgb(38,169,231);margin-left:2px;text-align:center;" >
	 				<div  style="margin-top:10%;  width:100%;height:20%;">
	 			 		<span style="font-size:30px;color:white;">流转率</span>
	 			 	</div>
	 			 	<div  style="margin-top:3%;  width:100%;height:20%;">
	 			 		<span style="font-size:60px;color:white;" id="sp25"></span><span style="font-size:36px;color:white;">%</span>
	 			 	</div>
	 				
	 			</div>
	 			<div id="carRunright" style="float:left; width:24%;height:200px;background-color:rgb(16,143,203);margin-left:2px;text-align:center;" >
	 				<div  style="margin-top:10%;  width:100%;height:20%;">
	 			 		<span style="font-size:30px;color:white;">平均租车时长</span>
	 			 	</div>
	 			 	<div  style="margin-top:3%;  width:100%;height:20%;">
	 			 		<span style="font-size:60px;color:white;" id="sp26"></span><span style="font-size:30px;color:white;">(分钟)</span>
	 			 	</div>
	 				
	 			</div>
	 			<div id="carRunMostRight" style="float:left; width:23%;height:200px;background-color:rgb(10,114,169);margin-left:2px;text-align:center;" >
	 				<div  style="margin-top:10%;  width:100%;height:20%;">
	 			 		<span style="font-size:30px;color:white;">平均单车收入</span>
	 			 	</div>
	 			 	<div  style="margin-top:3%;  width:100%;height:20%;">
	 			 		<span style="font-size:60px;color:white;" id="sp27"></span><span style="font-size:30px;color:white;">(元)</span>
	 			 	</div>
	 				
	 			</div>
	 		</div> 
	 	</div>
	</div> 
	<script src="echarts/core/www/dist/echarts.js"></script>
	<script type="text/javascript" src="echarts/asset/js/run_statistics_echarts_line.js"></script>
	<script type="text/javascript" src="echarts/asset/js/run_statistics_echartsExample.js"></script>
</body>
</html>