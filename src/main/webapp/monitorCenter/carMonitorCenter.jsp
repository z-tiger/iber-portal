<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宜步出行运营支撑系统</title>
<title>当前订单列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/sdmenu.css"/>

    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="monitorCenter/carRun.js"></script>
	<!-- 高德地图 -->
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
 <link rel="stylesheet" type="text/css" href="ui_lib/css/newLoginStyle.css"/>
 <style type="text/css"> 
 	.table-cls{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	}
	
	.table-cls td{
	  border-left: 1px solid #E0E0E0;
	  border-top: 1px solid #E0E0E0;
	}
	
	.myButton {
	-moz-box-shadow: 0px 1px 0px 0px #f0f7fa;
	-webkit-box-shadow: 0px 1px 0px 0px #f0f7fa;
	box-shadow: 0px 1px 0px 0px #f0f7fa;
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #5ac8ed), color-stop(1, #019ad2));
	background:-moz-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:-webkit-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:-o-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:-ms-linear-gradient(top, #5ac8ed 5%, #019ad2 100%);
	background:linear-gradient(to bottom, #5ac8ed 5%, #019ad2 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#5ac8ed', endColorstr='#019ad2',GradientType=0);
	background-color:#5ac8ed;
	-moz-border-radius:6px;
	-webkit-border-radius:6px;
	border-radius:6px;
	border:1px solid #057fd0;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:15px;
	font-weight:bold;
	padding:13px 3px;
	text-decoration:none;
	text-shadow:0px -1px 0px #5b6178;
}
.myButton:hover {
	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #019ad2), color-stop(1, #5ac8ed));
	background:-moz-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:-webkit-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:-o-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:-ms-linear-gradient(top, #019ad2 5%, #5ac8ed 100%);
	background:linear-gradient(to bottom, #019ad2 5%, #5ac8ed 100%);
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#019ad2', endColorstr='#5ac8ed',GradientType=0);
	background-color:#019ad2;
}
.myButton:active {
	position:relative;
	top:1px;
}
	
 </style>
</head>

 <script type="text/javascript">
	 //网点监控
     var parkArr= new Array();
     var markerParkArr = new Array();
     var carRunParkInfo;



	 //车辆监控
	var carArr= new Array();
	var carLable="";
	var markerArr = new Array();
	var carRunOrderInfo;
	var carNavigateInfo;

	function getData(){

		$.ajax({
		   async: false,
		   type : "POST",
		   url : "allCarRunOrderInfo",
		   success : function(data) {
			   carRunOrderInfo =$.parseJSON(data);
		   }
		});
		$.ajax({
			type : "post",
			 url : "car_navigate_info",
			 async : false,   //采用同步
			 success : function(data){
				carNavigateInfo = $.parseJSON( data );
			 }
		});
		$.post("car_run_list", function(data){
			  carArr=eval(data);
			  if(markerArr.length>0){
				 for(var j=0;j<markerArr.length;j++){
					if(markerArr[j]){
					  markerArr[j].setMap(null);
					}
				 }
			  }
			  for ( var i = 0; i < carArr.length; i++) {
				markerArr[i]=addMarkerCar(carArr[i],carRunOrderInfo,carNavigateInfo);//向地图中添加汽车marker
			  }
		});
		//网点监控
		$.ajax({
			async: false,
			type : "POST",
			url : "query_carRun_park_id",
			success : function(data) {
				carRunParkInfo = $.parseJSON(data);
			}
		});
		$.post("park_list_code", function(data){
			parkArr=eval(data);
			if(markerParkArr.length>0){
				for(var j=0;j<markerParkArr.length;j++){
					if(markerParkArr[j]){
                        markerParkArr[j].setMap(null);
					}
				}
			}
			for ( var i = 0; i < parkArr.length; i++) {
                markerParkArr[i]=addMarkerPark(parkArr[i],carRunParkInfo);//向地图中添加网点marker
			}
		});
	}
     setTimeout("getData()", 1000*60*2);
 </script>
<script type="text/javascript">
   function refreshme() {
        setInterval("getData();", 1000*60*2); //2分钟执行一次
   }
   function refresh(){
       mapInit();
   }
   $(document).ready(function () {
	 mapInit();
   });
   
    var mapObj,contextMenu,lng,lat;
	var point = new AMap.LngLat(parseFloat("${longitude}"), parseFloat("${latitude}"));
	var buildingLayer = new AMap.Buildings(); //实例化3D地图图层
	var trafficLayer = new AMap.TileLayer.Traffic({
		zIndex : 10
	}); //实时路况图层
	var roadNetLayer = new AMap.TileLayer.RoadNet({
		zIndex : 10
	}); //实例化路网图层
	var satellLayer = new AMap.TileLayer.Satellite({
		zIndex : 10
	}); //实例化卫星图
	
	function mapInit() { //初始化地图对象，加载地图。
		mapObj = new AMap.Map("dituContent", {
			center : point, //地图中心点
			level : 12
		//地图显示的缩放级别
		});
		mapObj.plugin([ "AMap.ToolBar", "AMap.OverView", "AMap.Scale",
				"AMap.MapType", "AMap.Geolocation" ], function() {
			//加载工具条
			tool = new AMap.ToolBar({
				direction : true,//隐藏方向导航
				ruler : true,//隐藏视野级别控制尺
				autoPosition : false
			//禁止自动定位
			});
			mapObj.addControl(tool);
			//加载鹰眼
			view = new AMap.OverView();
			mapObj.addControl(view);
			//加载比例尺
			scale = new AMap.Scale();
			mapObj.addControl(scale);
			//加载地图类型切换
			mapType = new AMap.MapType();
			//mapObj.addControl(mapType);
			//加载浏览器定位
			geolocation = new AMap.Geolocation();
			//mapObj.addControl(geolocation);
			//设置默认鼠标样式
			//mapObj.setDefaultCursor("url('images/openhand.cur'),pointer");
				//创建右键菜单
	        contextMenu = new AMap.ContextMenu();
	         //右键放大
	        contextMenu.addItem("放大一级",function(){
	          mapObj.zoomIn();	
	        },0);
	        //右键缩小
	        contextMenu.addItem("缩小一级",function(){
		      mapObj.zoomOut();
	        },1);
	        contextMenu.addItem("缩放至全国范围",function(e){
		    mapObj.setZoomAndCenter(4,new AMap.LngLat(108.946609,34.262324));
	        },2);
	        
	        
	        contextMenu.addItem("设为地图中心点",function(e){
	        mapObj.setZoomAndCenter(12,new AMap.LngLat(lng,lat));
	        },3);
	        
	        //地图绑定鼠标右击事件——弹出右键菜单
	       AMap.event.addListener(mapObj,'rightclick',function(e){
		     contextMenu.open(mapObj,e.lnglat);
		     contextMenuPositon = e.lnglat;
		      lng=contextMenuPositon.getLng();
		      lat = contextMenuPositon.getLat();
		     
	       });
		});
		 getData();
		
	}
		boo=true;
		//地图上添加汽车
		function addMarkerCar(json,carRunOrderInfos,carNavigateInfos) {
			
			var p0 = json.longitude;
			var p1 = json.latitude;
			if(p0 == null||p0 == ""||p1==null||p1 =="") { // 未上传gps，地图上不显示
				return ;
			}
			//点标记中的文本
			var markerContent = document.createElement("div");
	        markerContent.className = "markerContentStyle";
	        var markerImg= document.createElement("img");
            markerImg.className="markerlnglat";
            var statusName = "" ;
            var timeContext = "" ;
            var statusContext = "" ;
            var runInfos =carRunOrderInfos[''+json.lpn];
            if(json.status == 'empty') {
            	statusName = "闲置中" ; 
<%--            	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){--%>
<%--            		if(data == "fail") {--%>
<%--            			timeContext = "<h1>此车还未开始使用</h1>" ;--%>
<%--            		}else{--%>
<%--            			var carOrder = $.parseJSON( data ); --%>
<%--			          //获取车辆最后使用时间--%>
<%--			          timeContext = "<h1>开始时间："+carOrder.endTime+"</h1>" ;--%>
<%--            		}--%>
<%--			    });--%>
                if(runInfos==null){
                	timeContext = "<h1>此车还未开始使用</h1>";
                }else{
                	timeContext = "<h1>开始时间："+runInfos.endTime+"</h1>" ;
                }
		    	statusContext = "<h1>电量："+json.restBattery+"%"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+"车载CPU温度："+json.cpuTemperature+"°C</h1>";
		    	markerImg.src="images/monitorCenter/carEmpty.png";	
		    } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
		    	statusName = "运营中" ;

<%--	    		$.ajax({  --%>
<%--		            type : "post",  --%>
<%--		             url : "car_run_order_info",  --%>
<%--		             data : "lpn=" + encodeURI(json.lpn),  --%>
<%--		             async : false,  --%>
<%--		             success : function(data){  --%>
<%--		            	 if(data != "fail") {--%>
<%--		            		 var carOrder = $.parseJSON( data ); --%>
<%--				    			if(json.status == 'ordered'){--%>
<%--					            	timeContext = "<h1>预约时间："+carOrder.orderTime+"</h1>" ;--%>
<%--					            }else{--%>
<%--					            	timeContext = "<h1>开始时间："+carOrder.beginTime+"</h1>" ;--%>
<%--					            }--%>
<%--				    			if (carOrder.userType == '1') {--%>
<%--				    				statusContext = "<h1>使用人："+carOrder.name+"(员工使用)"+"</h1>" ;--%>
<%--								}else if (carOrder.userType == '0') {--%>
<%--									statusContext = "<h1>使用人："+carOrder.name+"</h1>" ;--%>
<%--								} --%>
<%--		            	 }--%>
<%--		             }  --%>
<%--		        });--%>
		    	if(runInfos!=null){
		            if(json.status == 'ordered'){
			            timeContext = "<h1>预约时间："+runInfos.orderTime+"</h1>" ;
		            }else{
		            	timeContext = "<h1>开始时间："+runInfos.beginTime+"</h1>" ;
		            }
	    			if (runInfos.userType == '1') {
	    				statusContext = "<h1>使用人："+runInfos.name+"(员工使用)"+"</h1>" ;
					}else if (runInfos.userType == '0') {
						statusContext = "<h1>使用人："+runInfos.name+"</h1>" ;
					} else if (runInfos.userType == '2') {
                        statusContext = "<h1>使用人："+runInfos.name+"(日租订单)"+"</h1>" ;
                    }
		    	}
		    	markerImg.src="images/monitorCenter/carError.png";	
		    }else if(json.status == 'repair'){
		    	statusName = "维修中" ;
		    	$.post("car_repair_info?status=1&lpn="+encodeURI(json.lpn), function(data){
		    		if(data != "fail") {
		    			var carOrder = $.parseJSON( data ); 
			           //获取车辆最后使用时间
			           timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
			           statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
		    		}
	    		  	  
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";	
		    }else if(json.status == 'maintain'){
		    	statusName = "维护中" ;
		    	$.post("car_repair_info?status=2&lpn="+encodeURI(json.lpn), function(data){
		    		if(data != "fail") {
		    			 var carOrder = $.parseJSON( data ); 
			         	 //获取车辆最后使用时间
			          	 timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
				         statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
			    	}
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";    	
		    }else if(json.status == 'charging'){
                statusName = "补电中" ;
                $.post("car_repair_info?status=3&lpn="+encodeURI(json.lpn), function(data){
                    if(data != "fail") {
                        var carOrder = $.parseJSON( data );
                        //获取车辆最后使用时间
                        timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
                        statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
                    }
                });
                markerImg.src="images/monitorCenter/carOffline.png";
            }
		    
		   markerImg.title=json.lpn ;
	       markerContent.appendChild(markerImg);
	     /* var markerSpan = document.createElement("span");
	      markerSpan.innerHTML = "<div style='width:80px;'>"+json.lpn+"</div>";
	    markerContent.appendChild(markerSpan); */
			var marker = new AMap.Marker({ //创建自定义点标注                 
// 				map : mapObj,
				position : new AMap.LngLat(p0, p1),
				offset : new AMap.Pixel(-10, -34),
				//icon : "${WEB_PATH}/html/images/statusImg/carBook.png"
				content:markerContent
			});
            marker.setMap(mapObj); 
           
           var content = "<div class='bubble-butbox'><dl>";
             content += "<dt><a class=myButton href=\"javascript:queryCarRunInfo('"+json.lpn+"');\">运行信息</a></dt>"; 
             content += " <dd class='but' style='width: 500px'>";
             content += "<a href=\"javascript:openDoor('"+json.lpn+"');\" id='opendoor' class='left'>开门</a>";
	          content += "<a href=\"javascript:closeDoor('"+json.lpn+"');\" id='closedoor' class='right'>关门</a>";
	    	  content += "<a href=\"javascript:startEngine('"+json.lpn+"');\" id='startEngine' class='left'>启动</a>";
	    	  content += "<a href=\"javascript:fireOff('"+json.lpn+"');\" id='fireOff' class='right'>熄火</a>";
	    	  content += "<a href=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\" id='order' class='left'>预约</a>";
	    	  content += "<a href=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\" id='returnCar' class='right' style='margin-right:0;'>还车</a>";
			  content += "<a href=\"javascript:longRentReturnCar('"+json.lpn+"','"+json.status+"');\" id='longRentReturnCar' class='right'>日租还车</a>";
	    	  content += "</dd>";
	    	  content += " <dd class='cut'>";
// 	    	  content += "<a href=\"javascript:navigate('"+json.lpn+"');\" id='navigate'><img src='images/newLogin/bubble-Direction.png' >导航</a>";
// 	    	  content += "<a href=\"javascript:cancelnavigate('"+json.lpn+"');\" id='cancelnavigate'><img src='images/newLogin/bubble-Target.png' >取消导航</a>";
	    	  content += "<a href=\"javascript:carLocus('"+json.lpn+"');\" id='carLocus'><img src='images/newLogin/bubble-avtivity.png' >轨迹</a>";
	    	  content += "<a href=\"javascript:catchedPhoto('"+json.lpn+"');\" id='catchedPhoto'><img src='images/newLogin/bubble-photo.png' >抓拍</a>";

	    	  content += "</dd>";
           content += " </dl> </div>"; 
         /*  
		  var content = "<div style='width:550px;'><div>" +
                 "<div><input type='button' id='opendoor' value='打开' onclick=\"javascript:openDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='closedoor' value='关闭' onclick=\"javascript:closeDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='startEngine' value='启动' onclick=\"javascript:startEngine('"+json.lpn+"');\"> "+
                 "<input type='button' id='fireOff' value='熄火' onclick=\"javascript:fireOff('"+json.lpn+"');\"> "+
                 "<input type='button' id='order' value='预约' onclick=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='returnCar' value='还车' onclick=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='navigate' value='导航' onclick=\"javascript:navigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='cancelnavigate' value='取消导航' onclick=\"javascript:cancelnavigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='carLocus' value='轨迹' onclick=\"javascript:carLocus('"+json.lpn+"');\"> "+
                 "<input type='button' id='catchedPhoto' value='抓拍' onclick=\"javascript:catchedPhoto('"+json.lpn+"');\"> "+
                 "<input type='button' id='call' value='呼叫' onclick=\"javascript:call('"+json.lpn+"');\"></div>";
             */    
                 
		var info = [];
        var preOfflineStatus = "<font color=red style='font-size:2px;line-height:20px;'>(预下线)</font>";
		if(statusName=="运营中"){
			//<b>"+statusName +" "  + json.lpn +"<div>速度" +json.speed+"km/h</b>
			//info.push("<div>速度：" +json.speed+"km/h<span style='float:left;width:400px;'>"+statusName+" "+json.lpn+"</span></div></div>");
	//		info.push("<div style='width:580px;' class='bubble'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
			if(json.preOffline=='1'){
				info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+""+preOfflineStatus+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
			}else {
				info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
			}
		}else{
			info.push("<div style='width:620px;' class='bubble'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em></h1>");
		}
		//info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
		info.push("<h1>地点："+json.address+"</h1>");
		if (json.preOffline == '1') {
			info.push("<h1><font color=red>预下线原因: "+json.preofflineReason+"</font></h1></div>");
		}
		else{
			info.push("</div>");
		}
		if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
			//获取导航地址
<%--			$.post("car_navigate_info?orderId="+json.orderId, function(data){--%>
<%--			     if(data != "fail") {--%>
<%--			     	var carNavigate = $.parseJSON( data ); --%>
<%--			     	info.push("<h1>导航地址："+carNavigate.address+"</h1>");--%>
<%--			     }--%>
<%--			});--%>
           var cnInfo = carNavigateInfos[''+json.orderId];
           if(cnInfo!=null){
	          info.push("<h1>导航地址："+cnInfo.address+"</h1>");
            }
		}
		info.push("<div class='bubble-con'>");
		info.push(timeContext) ;
		info.push(statusContext) ;
		info.push("</div>");
		info.push("<div class='ubble-butbox'>");
		info.push(content);
		info.push("</div>");

		AMap.event.addListener(marker, "click", function(e) {
			new AMap.InfoWindow({
				offset : new AMap.Pixel(0, -23),
				content : info.join("")
			}).open(mapObj, marker.getPosition());
		});

		AMap.event.addListener(mapObj, 'click', getLnglat); //点击事件
		return marker;
	}

   //地图上添加网点
   function addMarkerPark(json,carRunParkInfos) {
       var p0 = json.longitude;
       var p1 = json.latitude;
       //点标记中的文本
       var markerContent = document.createElement("div");
       markerContent.className = "markerContentStyle";
       var markerImg= document.createElement("img");
       markerImg.className="markerlnglat";
       markerImg.src="images/monitorCenter/"+json.cooperationType+"-"+json.category+"s.png";
       // markerImg.title=json.lpn ;
       markerContent.appendChild(markerImg);

       var markerSpan = document.createElement("span");
       markerSpan.innerHTML = "<div style='width:80px;'>"+json.name+"</div>";
       markerContent.appendChild(markerSpan);
       var marker = new AMap.Marker({ //创建自定义点标注
// 				map : mapObj,
           position : new AMap.LngLat(p0, p1),
           offset : new AMap.Pixel(-10, -34),
           //icon : "${WEB_PATH}/html/images/statusImg/carBook.png"
           content:markerContent
       });
       marker.setMap(mapObj);
       var info = [];
       info.push("<div style='width:620px; max-height: 400px' class='bubble'><div class='bubble-title'><h1><span>"+json.address+"</span></h1>");
       info.push("<h2>地址："+json.address+"</h2>");
       info.push("</div><div class='bubble-con' >");
       var freeNum = 0 ; //空闲车位数
       var carArrList = new Array();
       //通过网点ID获取所有车辆
//		$.ajaxSetup({async:false});
//		$.post("query_carRun_park_id?parkId="+encodeURIComponent(json.id),function(data){
//		    carArrList =  eval(data);
//		    freeNum = json.parkNums - carArrList.length ;
//		});
//		$.ajaxSetup({async:true});
       if(null!=carRunParkInfos['parkId'+json.id]){
           carArrList = carRunParkInfos['parkId'+json.id];
       }
       freeNum = json.parkNums - carArrList.length ;
       info.push("<h1>车位："+freeNum+"<span>/"+json.parkNums+"</span></h1></div>");
       if(carArrList.length > 0) {
           var _subContentHtml = "<div class='bubble-butbox' >";
           for ( var i = 0; i < carArrList.length; i++) {
               _subContentHtml += "<dl><dt>"+carArrList[i].lpn+"<a class=myButton href=\"javascript:queryCarRunInfo('"+carArrList[i].lpn+"');\">运行信息</a></dt>";
               _subContentHtml += "<dd class='but'>";
               _subContentHtml += "<a href=\"javascript:openDoor('"+carArrList[i].lpn+"');\" id='opendoor' class='left'>开门</a>";
               _subContentHtml += "<a href=\"javascript:closeDoor('"+carArrList[i].lpn+"');\" id='closedoor' class='right'>关门</a>";
               _subContentHtml += "<a href=\"javascript:startEngine('"+carArrList[i].lpn+"');\" id='startEngine' class='left'>启动</a>";
               _subContentHtml += "<a href=\"javascript:fireOff('"+carArrList[i].lpn+"');\" id='fireOff' class='right'>熄火</a>";
               _subContentHtml += "<a href=\"javascript:bookCar('"+carArrList[i].lpn+"','"+carArrList[i].status+"');\" id='order' class='left'>预约</a>";
               _subContentHtml += "<a href=\"javascript:returnCar('"+carArrList[i].lpn+"','"+carArrList[i].status+"');\" id='returnCar' class='right' style='margin-right:0;'>还车</a>";
               _subContentHtml += "<a href=\"javascript:longRentReturnCar('"+json.lpn+"','"+json.status+"');\" id='longRentReturnCar' class='left'>日租还车</a>";
               _subContentHtml += "</dd>";
               _subContentHtml += " <dd class='cut'>";
// 	    	  _subContentHtml += "<a href=\"javascript:navigate('"+carArrList[i].lpn+"');\" id='navigate'><img src='images/newLogin/bubble-Direction.png' >导航</a>";
// 	    	  _subContentHtml += "<a href=\"javascript:cancelnavigate('"+carArrList[i].lpn+"');\" id='cancelnavigate'><img src='images/newLogin/bubble-Target.png' >取消导航</a>";
               _subContentHtml += "<a href=\"javascript:carLocus('"+carArrList[i].lpn+"');\" id='carLocus'><img src='images/newLogin/bubble-avtivity.png' >轨迹</a>";
               _subContentHtml += "<a href=\"javascript:catchedPhoto('"+carArrList[i].lpn+"');\" id='catchedPhoto'><img src='images/newLogin/bubble-photo.png' >抓拍</a>";
               _subContentHtml += "</dd></dl>";
           }
           _subContentHtml += "</div>";
           info.push(_subContentHtml);

       }else{
           info.push("<div style='width:300px;'><font color='red'>当前暂无车辆</font></div>");
       }
       info.push("</div>");
       AMap.event.addListener(marker, "click", function(e) {
           new AMap.InfoWindow({
               offset : new AMap.Pixel(0, -23),
               content : info.join("")
           }).open(mapObj, marker.getPosition());
       });

       AMap.event.addListener(mapObj, 'click', getLnglat); //点击事件
       return marker;
   }

		//关闭信息窗体  
	function closeInfoWindow() {
		mapObj.clearInfoWindow();
	}

	//鼠标点击，获取经纬度坐标
	function getLnglat(e) {
		var x = e.lnglat.getLng();
		var y = e.lnglat.getLat();
		//document.getElementById("lnglat").innerHTML = x + "," + y;
	}
	function addBuildingLayer() { //3D网
		buildingLayer.setMap(mapObj); //在map中添加3D图层
		satellLayer.setMap(null); //隐藏卫星图
		//cloudDataLayer.setMap(null);
		mapObj.setZoom(18);
	}
	function addTraffic() { //交通，实时路况
		trafficLayer.setMap(mapObj); //添加实时路况图层
		roadNetLayer.setMap(null); //隐藏路网图层
		satellLayer.setMap(null); //隐藏卫星图
		//cloudDataLayer.setMap(null);
		mapObj.setZoom(15);
	}
	function addRoadNetLayer() { //路网
		roadNetLayer.setMap(mapObj); //在map中添加路网图层
		trafficLayer.setMap(null); //隐藏实时路况图层
		satellLayer.setMap(null); //隐藏卫星图
		//cloudDataLayer.setMap(null);
		mapObj.setZoom(15);
	}
	function to2D() {
		buildingLayer.setMap(null);
		roadNetLayer.setMap(null);
		trafficLayer.setMap(null);
		mapObj.setZoom(15);
		//groundImage.hide();
		//google.setMap(null);
		//cloudDataLayer.setMap(null);
	}
	function addsatellLayer() {
		satellLayer.setMap(mapObj); //在map中添加卫星图
		buildingLayer.setMap(null);
		roadNetLayer.setMap(null);
		trafficLayer.setMap(null);
		//cloudDataLayer.setMap(null);
		mapObj.setZoom(18);
	}
	function yunLayer() {
		buildingLayer.setMap(null);
		roadNetLayer.setMap(null);
		trafficLayer.setMap(null);
		mapObj.setZoom(15);
		//groundImage.hide();
		//加载云图层插件
		mapObj.plugin('AMap.CloudDataLayer', function() {
			var layerOptions = {
				query : {
					keywords : ''
				},
				clickable : true
			};
			var cloudDataLayer = new AMap.CloudDataLayer(
					'5358f853e4b01214f369d851', layerOptions); //实例化云图层类
			cloudDataLayer.setMap(mapObj); //叠加云图层到地图
		});
	}
	//街景
	function jiejing() {
		var opts = {
			pov : {
				heading : 270,
				pitch : 0
			},
			position : point
		};
		var panorama = new AMap.Panorama('iCenter', opts);
	}
		
    
   $(function(){
    /* $("#isFreeCompensate").prop("checked", true); */
   /*  $("input[name='isFreeCompensate']").eq(1).click(); */
 	$("#bookcarOper").dialog( {
		width : "400",
		height : "350",
		top : "40",
		buttons : [ {
			text : "预约",
			iconCls : "icon-save",
			handler : function() {
				var isEnterpriseOrder = $("#isEnterpriseOrder").val() ;
				if(isEnterpriseOrder == "null" || isEnterpriseOrder== null){
					$.messager.alert("提示", "用车类型不能为空", "info");
					return ;
				}
				$("#bookcarForm").form("submit", {
					url : "order_car",
					data:{lpn:lpn},
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "预约成功", "info");
							$("#bookcarOper").dialog("close");
							getData();//重新加载车辆
						}else if(result == "noMember"){
							$.messager.alert("提示", "会员不存在", "info");
						}else if(result == "ordered"){
							$.messager.alert("提示", "车辆已被预约,请刷新页面", "info");
						}  
						else{
						    $.messager.alert("提示", result, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#bookcarOper").dialog("close");
			}
		} ]
	});
	
 	$("#carRunInfoOper").dialog( {
		width : "500",
		height : "350",
		top : "40",
		buttons : [{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carRunInfoOper").dialog("close");
			}
		} ]
	});
	
	//还车
	$("#returnCarOper").dialog( {
		width : "400",
		height : "300",
		top : "40",
		buttons : [ {
			text : "还车",
			iconCls : "icon-save",
			handler : function() {
				$("#returnCarForm").form("submit", {
					url : "return_order_car",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "还车成功", "info");
						    $("#bookingGrid").datagrid("reload");
						    document.getElementById("ss").style.display="none";
							$("#status").combobox({required:false});
             				$("#reason").textbox({required:false});
							$("#status").combobox("clear");
             				$("#reason").textbox("clear");
							$("#returnCarOper").dialog("close");
							getData();//重新加载车辆
						}else if(result == "noCheck"){
						    $.messager.alert("提示", "该会员还未通过审核，不能预约车辆", "error");
						}else if(result == "noMember"){
						    $.messager.alert("提示", "会员身份证号不正确，请重新输入", "error");
						}else if(result == "returnCarInEmployeeMonitorCenter"){
							$.messager.alert("提示", "该车辆为员工使用车辆，请在调度员监控还车", "error");
						}else if(result == "noReason"){
							$.messager.alert("提示","不能有特殊字符","error");
						}else if (result == "isLongRent"){
                            $.messager.alert("提示","该车辆绑定的是日租订单，请在日租还车进行还车","error");
                        }else{
						    $.messager.alert("提示",result, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#returnCarOper").dialog("close");
				document.getElementById("ss").style.display="none"; 
			}
		} ]
	});

       //日租还车
       $("#longRentReturnCarView").dialog( {
           width : "400",
           height : "300",
           top : "40",
           buttons : [ {
               text : "还车",
               iconCls : "icon-save",
               handler : function() {
                   $("#longRentReturnCarForm").form("submit", {
                       url : "return_long_rent_car",
                       onSubmit : function() {
                           return $(this).form("validate");
                       },
                       success : function(result) {
                           if (result == "succ") {
                               $.messager.alert("提示", "还车成功", "info");
                               $("#bookingGrid").datagrid("reload");
                               document.getElementById("ll").style.display="none";
                               $("#carStatus").combobox({required:false});
                               $("#offLineReason").textbox({required:false});
                               $("#carStatus").combobox("clear");
                               $("#offLineReason").textbox("clear");
                               $("#longRentReturnCarView").dialog("close");
                               getData();//重新加载车辆
                           }else if (result == "notUsing"){
                               $.messager.alert("提示","该车辆不处于用车状态，不可进行还车", "error");
                           }else {
                               $.messager.alert("提示",result, "error");
						   }
                       }
                   });
               }
           }, {
               text : "取消",
               iconCls : "icon-cancel",
               handler : function() {
                   $("#longRentReturnCarView").dialog("close");
                   document.getElementById("ll").style.display="none";
               }
           } ]
       });
});

    //预约
    function bookCar(lpn,status){
       if(status != 'empty'){
	      $.messager.alert("消息提示","车辆状态为不可预约，请选择其他车辆");
	    }else{
	     	$("#bookcarForm").form("clear");
		    $("#bookcarForm").form('load', {
	              lpn : lpn
	          	});
			$("#bookcarOper").dialog("open").dialog("setTitle", "预约车辆");
	    }
    }
    //还车
    function returnCar(lpn,status){
    	 if(status == 'useCar' || status == 'return'){
	      	$("#returnCarForm").form("clear");
			$("#returnCarForm").form('load', {
               returnCarLpn : lpn.substring(0,2) +"•"+lpn.substring(2),
           });
			$("#returnCarOper").dialog("open").dialog("setTitle", "还车");
			$('#parkId').combobox({  
			    valueField : 'id',  
			    textField : 'text',
			    url: 'get_return_park_list'+'?lpn='+lpn,    
			    editable:true,  
			    filter: function(q, row){  
			        var opts = $(this).combobox('options');  
			        return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配  
			    },  
			});
	    }else{
	    	$.messager.alert("消息提示","车辆不为运营状态");
	    }
    }

    //日租还车
   function longRentReturnCar(lpn,status){
       if(status == 'useCar'){
           $("#longRentReturnCarForm").form("clear");
           $("#longRentReturnCarForm").form('load', {
               longRentReturnCarLpn : lpn.substring(0,2) +"•"+lpn.substring(2),
           });
           $("#longRentReturnCarView").dialog("open").dialog("setTitle", "日租还车");
           $('#longRentParkId').combobox({
               valueField : 'id',
               textField : 'text',
               url: 'get_park_list',
               editable:true,
               filter: function(q, row){
                   var opts = $(this).combobox('options');
                   return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配
               },
           });
       }else{
           $.messager.alert("消息提示","车辆不为运营状态");
       }
   }
     //强制还车
    $(function(){
    	$("#forceReturnCar").bind("click",function(){
    	if($("#ss").css("display")=="none"){ 
                document.getElementById("ss").style.display="block";
                $("#status").combobox({required:true});
                $("#status").combobox({editable:false});
             	$("#reason").textbox({required:true});
            }else{
                document.getElementById("ss").style.display="none";
             	$("#status").combobox("clear");
             	$("#reason").textbox("clear");
             	$("#status").combobox({editable:false});
             	$("#status").combobox({required:false});
             	$("#reason").textbox({required:false});
            } 
    	});
    })

   //日租强制还车
   $(function(){
       $("#longRentForceReturnCar").bind("click",function(){
           if($("#ll").css("display")=="none"){
               document.getElementById("ll").style.display="block";
               $("#carStatus").combobox({required:true});
               $("#carStatus").combobox({editable:false});
               $("#offLineReason").textbox({required:true});
           }else{
               document.getElementById("ll").style.display="none";
               $("#carStatus").combobox("clear");
               $("#offLineReason").textbox("clear");
               $("#carStatus").combobox({editable:false});
               $("#carStatus").combobox({required:false});
               $("#offLineReason").textbox({required:false});
           }
       });
   })
    //打开车门
    function openDoor(lpn){
    	$.messager.confirm("提示","确定打开车门吗?",function(r){
	      if(r){
   			$.post("open_car_door",{"lpn":lpn},function(data){
					if(data=="succ"){
						$.messager.alert("消息提示","车门打开成功！");
						getData();//重新加载车辆
					}else{
						$.messager.alert("消息提示","车门打开失败！");
						getData();//重新加载车辆
					}
					if (data == "notUse"){
						$.messager.alert("消息提示","日租车辆不为用车状态");
					}
				});
			}
		});
    }
    
    //关闭车门
    function closeDoor(lpn){
    	$.messager.confirm("提示","确定关闭车门吗?",function(r){
	      if(r){
	   		$.post("close_car_door",{"lpn":lpn},function(data){
					if(data=="succ"){
						$.messager.alert("消息提示","车门关闭成功！");
					}else{
						$.messager.alert("消息提示","车门关闭失败！");
					}
				});
			}
		});
    }
    
    //启动车辆
    function startEngine(lpn){
    	$.messager.confirm("提示","确定启动车吗?",function(r){
	      if(r){
   			$.post("start_car_engine",{"lpn":lpn},function(data){
					if(data=="succ"){
						$.messager.alert("消息提示","引擎启动成功！");
					}else{
						$.messager.alert("消息提示","引擎启动失败！");
					}
				});
			}
		});
    }
    
    //熄火
    function fireOff(lpn){
    	$.messager.confirm("提示","确定熄火吗?",function(r){
	      if(r){
   			$.post("car_fire_off",{"lpn":lpn},function(data){
					if(data=="succ"){
						$.messager.alert("消息提示","熄火成功！");
					}else{
						$.messager.alert("消息提示","熄火失败！");
					}
				});
			}
		});
    }
    
    //轨迹
    function carLocus(lpn){
    	window.open ('http://ibgoing.com:8080/bdcarMonitor/') ;
    	//window.open ('carLocus.html')//车辆轨迹将百度鹰眼集成进来
    	/*window.open("car_locus?lpn="+encodeURI(lpn),"dd","menubar=no,location=no"); */
    }
    //设置导航
     function navigate(lpn){
    	 pwin=window.open("monitorCenter/getpos.jsp?lpn='"+lpn+"'","dd","menubar=no,location=no");
		 pwin.document.write(code.value);
		 pwin.document.charset="UTF-8";
		 getData();//重新加载车辆
    }
    
    //获取导航gps信息
    function setPos(position,lpn){
		$.post("open_navigate",{"position":position,"lpn":lpn},function(data){
			if(data=="succ"){
					$.messager.alert("消息提示","目的地已经设置成功！");
			}else{
					$.messager.alert("消息提示","导航失败！");
			}
		});
	}
     //取消导航
     function cancelnavigate(lpn){
   		 $.messager.confirm("提示","确定取消导航吗?",function(r){
	      if(r){
   			$.post("cancel_navigation",{"lpn":lpn},function(data){
   					getData();//重新加载车辆
					if(data=="succ"){
						$.messager.alert("消息提示","取消导航成功！");
					}else{
						$.messager.alert("消息提示","取消导航失败！");
					}
				});
			}
		});
    }
    
    function catchedPhoto(lpn){
    	$.messager.confirm("提示","确定抓拍吗?",function(r){
	      if(r){
   			$.post("catched_photo",{"lpn":lpn},function(data){
					if(data=="succ"){
						$.messager.alert("消息提示","抓拍成功！");
					}else{
						$.messager.alert("消息提示","抓拍失败！");
					}
				});
			}
		});
    }
    
     //呼叫
     function call(lpn){
   		 $.messager.confirm("提示","确定呼叫车主吗?",function(r){
	      if(r){
   			$.post("call_car",{"lpn":lpn},function(data){
					if(data=="succ"){
						$.messager.alert("消息提示","呼叫成功！");
					}else{
						$.messager.alert("消息提示","呼叫失败！");
					}
				});
			}
		});
    }
	//查询网点
   function searchCarPark(){
       var parkName = $("#s-parkNamePark").textbox("getValue").toUpperCase() ;
       var isExist=false ;
       for(var i = 0 ; i < parkArr.length ; i ++) {
           if(parkArr[i].name.indexOf(parkName) >= 0){
               var json = parkArr[i] ;
               mapObj.setZoomAndCenter(18,new AMap.LngLat(json.longitude,json.latitude));
               isExist = true ;
               //mapObj.setCenter(new AMap.LngLat(json.longitude,json.latitude)); //设置地图中心点
           }
       }

       if(isExist){
           $("#resultMsg").html("");
       }else if(isExist == false){
           $("#resultMsg").html("未查询到相关数据");
       }
   }
    //query
	function searchCar(){
		var lpn = $("#s-lpn").textbox("getValue") ;
		for(var i = 0 ; i < carArr.length ; i ++) {
			if(carArr[i].lpn.indexOf(lpn) >= 0){
				var json = carArr[i] ;
				mapObj.setZoomAndCenter(18,new AMap.LngLat(json.longitude,json.latitude));
				//mapObj.setCenter(new AMap.LngLat(json.longitude,json.latitude)); //设置地图中心点
						
				var statusName = "" ;
	            var timeContext = "" ;
	            var statusContext = "" ;
	            if(json.status == 'empty') {
	            	statusName = "闲置中" ; 
	            	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){
	            		if(data == "fail") {
	            			timeContext = "<h1>此车还未开始使用</h1>" ;
	            		}else{
	            			var carOrder = $.parseJSON( data ); 
				          //获取车辆最后使用时间
				          timeContext = "<h1>开始时间："+carOrder.endTime+"</h1>" ;
	            		}
				    });
			    	statusContext = "<h1>电量："+json.restBattery+"%</h1>" ;
			    } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
			    	statusName = "运营中" ;
			    	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){
			    		if(data == "fail"){
			    			timeContext = "<h1>还未运营记录</h1>" ;
			    		}else{
			    			var carOrder = $.parseJSON( data ); 
				            //获取车辆最后使用时间
				            timeContext = "<h1>开始时间："+carOrder.beginTime+"</h1>" ;
				            statusContext = "<h1>使用人："+carOrder.name+"</h1>" ;
			    		}
				    });
			    }else if(json.status == 'repair'){
			    	statusName = "维修中" ;
			    	$.post("car_repair_info?status=1&lpn="+encodeURI(json.lpn), function(data){
		    		  	  var carOrder = $.parseJSON( data ); 
				          //获取车辆最后使用时间
				          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
				          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
				    });
			    }else if(json.status == 'maintain'){
			    	statusName = "维护中" ;
			    	$.post("car_repair_info?status=2&lpn="+encodeURI(json.lpn), function(data){
		    		  	  var carOrder = $.parseJSON( data ); 
				          //获取车辆最后使用时间
				          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
				          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
				    });
			    }
			    
			    var content = "<div class='bubble-butbox'>";
		        content += "<dl><dt>"+json.lpn+"</dt>";
		        content += " <dd class='but'>";
		        content += "<a href=\"javascript:openDoor('"+json.lpn+"');\" id='opendoor' class='left'>开门</a>";
		        content += "<a href=\"javascript:closeDoor('"+json.lpn+"');\" id='closedoor' class='right'>关门</a>";
		        content += "<a href=\"javascript:startEngine('"+json.lpn+"');\"  id='startEngine' class='left'>启动</a>";
		        content += "<a href=\"javascript:fireOff('"+json.lpn+"');\" class='right'>熄火</a>";
		        content += "<a href=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\" class='left'>预约</a>";
		        content += "<a href=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\" class='right' style='margin-right:0;'>还车</a>";
                content += "<a href=\"javascript:longRentReturnCar('"+json.lpn+"','"+json.status+"');\" id='longRentReturnCar' class='left'>日租还车</a>";
		        content += " </dd>";
		        content += " <dd class='cut'>";
// 		        content += "<a href=\"javascript:navigate('"+json.lpn+"');\"><img src='images/newLogin/bubble-Direction.png'>导航</a>";
// 		        content += " <a href=\"javascript:cancelnavigate('"+json.lpn+"');\"><img src='images/newLogin/bubble-Target.png' >取消导航</a>";
		        content += " <a href=\"javascript:carLocus('"+json.lpn+"');\"><img src='images/newLogin/bubble-avtivity.png' >轨迹</a>";
		        content += "<a href=\"javascript:catchedPhoto('"+json.lpn+"');\"><img src='images/newLogin/bubble-photo.png' >抓拍</a>";
		        content += "</dd>";
		        content += " </dl> </div>";
		    	
		    	/*var content = "<div style='width:620px;' align=\"center\"><div>" +
                 "<div><input type='button' id='opendoor' value='打开' onclick=\"javascript:openDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='closedoor' value='关闭' onclick=\"javascript:closeDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='startEngine' value='启动' onclick=\"javascript:startEngine('"+json.lpn+"');\"> "+
                 "<input type='button' id='fireOff' value='熄火' onclick=\"javascript:fireOff('"+json.lpn+"');\"> "+
                 "<input type='button' id='order' value='预约' onclick=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='returnCar' value='还车' onclick=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='navigate' value='导航' onclick=\"javascript:navigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='cancelnavigate' value='取消导航' onclick=\"javascript:cancelnavigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='carLocus' value='轨迹' onclick=\"javascript:carLocus('"+json.lpn+"');\"> "+
                 "<input type='button' id='call' value='呼叫' onclick=\"javascript:call('"+json.lpn+"');\"></div>";*/
                 
				var info = [];
		
				if(statusName=="运营中"){
					//<b>"+statusName +" "  + json.lpn +"<div>速度" +json.speed+"km/h</b>
					//info.push("<div>速度：" +json.speed+"km/h<span style='float:left;width:400px;'>"+statusName+" "+json.lpn+"</span></div></div>");
					info.push("<div style='width:620px;' class='bubble'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
				}else{
					//info.push("<div>"+statusName +" "  + json.lpn +"</div>");
					info.push("<div style='width:620px;' class='bubble'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em></h1>");
				}
				//info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
				//info.push("<div>地点："+json.address+"</div>");
				info.push("<h2>地址："+json.address+"</h2></div>");

				info.push("<div class='bubble-con'>");
				info.push(timeContext) ;
				info.push(statusContext) ;
				info.push("</div>");
				info.push(content);
		       info.push("</div>");
				
				new AMap.InfoWindow({
					offset : new AMap.Pixel(0, -23),
					content : info.join("")
				}).open(mapObj, markerArr[i].getPosition());
			}
		}
	};
	
	function queryCarRunInfo(lpn){
		$.post("car_run_info",{"lpn":lpn}, function(data){
    		if(data == "fail"){
    			$.messager.alert("消息提示","获取车辆运行信息失败！");
    		}else{
    			var carRun = $.parseJSON( data );
    			if (carRun.lpn.indexOf("•") < 0) {
					$("#r-lpn").html(carRun.lpn.substring(0,2)+"•"+carRun.lpn.substring(2));
				}else {
					$("#r-lpn").html(carRun.lpn);
				}
    			$("#r-lpn").html(carRun.lpn);
    			var status = "" ;
    			if(carRun.status=="empty"){
    				status = "空闲中";
    			}else if(carRun.status=="maintain"){
    				status = "维护中";
    			}else if(carRun.status=="repair"){
    				status = "维修中";
    			}else if(carRun.status=="charging"){
    				status = "补电中";
    			}else{
    		
    				if(carRun.preOffline =="1"){
    					status = "运营中(<font color=red>预下线</font>)";
					}else{
						status = "运营中";
					}
    			}
    			$("#r-status").html(status);
    			$("#r-cpuTemperature").html(carRun.cpuTemperature);
    			$("#r-speed").html(carRun.speed);
    			$("#r-mileage").html(carRun.mileage);
    			$("#r-brandName").html(carRun.brandName);
    			var chargeStatus = "" ;
    			if(carRun.batStatus=="1"){
    				chargeStatus = "充电中" ;
    			}else{
    				chargeStatus = "未充电" ;
    			}
    			$("#r-batStatus").html(chargeStatus);
    			var doorStatus = "" ;
    			if(carRun.doorStatus=="1"){
    				doorStatus = "开" ;
    			}else{
    				doorStatus = "关" ;
    			}
    			$("#r-doorStatus").html(doorStatus);
    			$("#r-enduranceMileage").html(carRun.enduranceMileage);
    			$("#r-restBattery").html(carRun.restBattery);
    			$("#r-smallBatteryVoltage").html(carRun.smallBatteryVoltage/10);
    			var smallBatteryChargeStatus = "" ;
    			if(carRun.smallBatteryChargeStatus=="1"){
    				smallBatteryChargeStatus = "正在充" ;
    			}else{
    				smallBatteryChargeStatus = "未充" ;
    			}
    			$("#r-smallBatteryChargeStatus").html(smallBatteryChargeStatus);
    			var carSignal = "" ;
    			if(carRun.carSignal=="1"){
    				carSignal = "开" ;
    			}else{
    				carSignal = "关" ;
    			}
    			$("#r-carSignal").html(carSignal);
    			var safeBeltStatus = "" ; 
    			if(carRun.safeBeltStatus=="1"){
    				safeBeltStatus = "已系" ;
    			}else{
    				safeBeltStatus = "未系" ;
    			}
    			$("#r-safeBeltStatus").html(safeBeltStatus);
    			if(carRun.readyStatus=="1"){
    				readyStatus = "启动" ;
    			}else{
    				readyStatus = "熄火" ;
    			}
    			$("#r-readyStatus").html(readyStatus);
    			var gear= "" ;
    			if(carRun.gear=="13"){
    				gear = "前进" ;
    			}else if(carRun.gear=="11"){
    				gear = "后退" ;
    			}else if(carRun.gear=="12"){
    				gear = "空档" ;
    			}else if(carRun.gear == "14"){
    			    gear = "驻车档";
				}
    			$("#r-gear").html(gear);
    			var handBreakStatus = "" ;
    			if(carRun.handBreakStatus=="1"){
    				handBreakStatus = "拉紧" ;
    			}else{
    				handBreakStatus = "松开" ;
    			}
    			$("#r-handBreakStatus").html(handBreakStatus);
    			var lightStatus = "" ;
    			if(carRun.lightStatus=="1"){
    				lightStatus = "开启" ;
    			}else{
    				lightStatus = "关闭" ;
    			}
    			$("#r-lightStatus").html(lightStatus);
    			var windowStatus = "" ;
    			if(carRun.windowStatus=="1"){
    				windowStatus = "开启" ;
    			}else{
    				windowStatus = "关闭" ;
    			}
    			$("#r-windowStatus").html(windowStatus);
    			var trunkStatus = "" ;
    			if(carRun.trunkStatus=="1"){
    				trunkStatus = "开启" ;
    			}else{
    				trunkStatus = "关闭" ;
    			}
    			$("#r-trunkStatus").html(trunkStatus);
    			$("#carRunInfoOper").dialog("open").dialog("setTitle", "车辆运行信息");
    		}
	    });
	}
    // 刷新
    function btnReload(){
    	mapInit();
        $("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'parkName':$("#s-parkName").textbox("getValue")
        });
    }
</script>
<body style="width:100%; height:100%; position: absolute;">
	<div id="searchFrame" style="width:240px; background:#fff; border:1px solid #666;z-index:3;position:absolute; top:0px;right:1px;">
		<div style="height:28px; line-height: 28px; text-align:center; font-size:14px; border-bottom:1px solid #666;">
			请输入网点名称
			<div style="width:20px; height:30px;float: right;">
				<a href="javascript:searchHideObj('searchFrame','searchContent')" style="text-decoration:none;color:Blue;">X</a>
			</div>
		</div>
		<div id="searchContent" style="margin: 5px;" align="center">
			<input type="text" name="s-parkName" class="easyui-textbox" id="s-parkNamePark" style="height:30px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQueryPark" onclick="searchCarPark();">查询</a>
			<br/><span id="resultMsg" style="height:28px; line-height: 28px; text-align:center; font-size:14px; color: red"></span>
		</div>
		<div id="refreshDiv" style="height: 30px; margin-top: 2px" align="center">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="btnReloadPark" onclick="refresh();">刷新</a>
		</div>
	</div>
	<div id="prompt" style="width:20px; background:#fff; border:1px solid #666; z-index:3;position:absolute; top:0px; right:1px; padding:10px 4px; text-align:center; display: none;cursor: pointer;"  onclick="javascript:searchShowObj('searchFrame','searchContent')">网点名称搜索</div>

	<div id="pMap" class="easyui-panel" title="运营车辆地图展示"
	    style="width:100%;height:70%;padding:10px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',
	    collapsible:true,maximizable:true">
	     <div id="dituContent"  style="height: 100%; width:100%;"></div>
	</div>
	
    <div id="pList" class="easyui-panel" title="运营车辆列表展示"
	    style="width:100%;height:30%;padding-bottom:20px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',maximizable:true">
	    <table id="grid" toolbar="#toolbar"></table>
	      
		<div id="toolbar" style="height:auto">
	      <div><form name="scarMrgForm" id="scarMrgForm">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="scityCode"
						data-options=" url:'sys_optional_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
			车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
			网点:<input type="text" name="parkName" class="easyui-textbox" id="s-parkName">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="btnReload" onclick="btnReload();">刷新</a>
			</form>
		  </div>
	   </div>
   
	</div>
	
    <!-- 悬浮 
	<div id="searchFrame" style="width:240px; background:#fff; border:1px solid #666;z-index:3;position:absolute; top:0px;right:1px;">
		<div style="height:28px; line-height: 28px; text-align:center; font-size:14px; border-bottom:1px solid #666;">
			请输入车牌号
			<div style="width:20px; height:30px;float: right;">
				<a href="javascript:searchHideObj('searchFrame','searchContent')" style="text-decoration:none;color:Blue;">X</a>
			</div>
		</div>
		<div id="searchContent" style="margin: 5px;">
			<input type="text" name="s-lpn" class="easyui-textbox" id="s-lpn" style="height:30px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick="searchCar();">查询</a>
		</div>
	</div>
	<div id="prompt" style="width:20px; background:#fff; border:1px solid #666; z-index:3;position:absolute; top:0px; right:1px; padding:10px 4px; text-align:center; display: none;cursor: pointer;"  onclick="javascript:searchShowObj('searchFrame','searchContent')">车牌号搜索</div>
	-->				
					
    <!-- 预约车辆表单 -->
   	<div id="bookcarOper" class="easyui-dialog" closed="true" style="width:400px;height:400px;padding-left:20px">
		<form id="bookcarForm" method="post">
			   <br/><br/>
			   <div class="fitem">
	               <label>车　　牌:</label>
	               <input id="lpn" name="lpn" class="easyui-textbox" readonly="readonly" style="width:250px;height:40px;">
	           </div>
	           <br/><br/>
	           <div class="fitem">
	               <label>手机号码:</label>
	               <input id="phone" name="phone" class="easyui-textbox"  required="true" style="width:250px;height:40px;">
	           </div>
	           <br/><br/>
	           <div class="fitem">
	               <label>用车类型:</label>
	              <!--  <input class="easyui-combobox" id="parkId" name="parkId" 
            		data-options="url:'sys_dic?dicCode=IS_ENTERPRISS_ORDER',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'" required="true" style="width:250px;height:40px;"> -->
	                    
	                       <select style="width:250px;height:40px;" id="isEnterpriseOrder" name="isEnterpriseOrder">
						       <option value ="false" selected="selected">个人用车</option>
						       <option value ="true">企业用车</option>
						     </select>
	           </div>
	           <div class="fitem" style="margin-top:26px;">
	               <label>商业保险:</label>
                      <select style="width:250px;height:40px;" id="isFreeCompensate" name="isFreeCompensate">
				       <option value ="1" selected="selected">需要</option>
				       <option value ="0">不需要</option>
				     </select>
	           </div>
	           
		</form>
	</div>
	
	<!-- 日租还车 / 日租强制还车-->
	<div id="longRentReturnCarView" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
		<form id="longRentReturnCarForm" method="post">
            <div class="fitem">
                <!-- <br/><br/> -->
			    <div class="fitem">
	               <label>车　　牌:</label>
	               <input id="longRentReturnCarLpn" name="longRentReturnCarLpn" class="easyui-textbox" readonly="readonly" style="width:250px;height:40px;">
	            </div>
	            <br/>
                <label>选网点:</label>
                <input class="easyui-combobox" id="longRentParkId" name="longRentParkId"
            		data-options="panelHeight:'250'" required="true" style="width:250px;height:40px;">
				<r:FunctionRole functionRoleId="long_rent_force_return_car">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"  id="longRentForceReturnCar">强制还车</a>
				</r:FunctionRole>
				<br/><br/>
				<div id="ll" style="display:none">
					<label>下线状态:</label>
					<input class="easyui-combobox" id="carStatus" name="carStatus"
						   data-options="
		                    valueField:'value',
		                    textField:'label',
		                    data: [{
								label: '维修',
								value: 'repair'
							},{
								label:'维护',
								value:'maintain'
							},{
								label:'补电',
								value:'charging'
							}],
		                    panelHeight:'auto'"  style="width:250px;height:40px;" >
					<br/><br/>
					<label>还车原因:</label>
					<input class="easyui-textbox" name="offLineReason" id="offLineReason"
						   data-options="multiline:true" style="height:70px;width:250px">
				</div>
            </div>
		</form>
	</div>

	<div id="returnCarOper" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
		<form id="returnCarForm" method="post">
			<div class="fitem">
				<!-- <br/><br/> -->
				<div class="fitem">
					<label>车　　牌:</label>
					<input id="returnCarLpn" name="returnCarLpn" class="easyui-textbox" readonly="readonly" style="width:250px;height:40px;">
				</div>
				<br/>
				<label>选网点:</label>
				<input class="easyui-combobox" id="parkId" name="parkId"
					   data-options="panelHeight:'250'" required="true" style="width:250px;height:40px;">
				<br/><br/>
				<r:FunctionRole functionRoleId="force_return_car">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" plain="true"  id="forceReturnCar">强制还车</a>
				</r:FunctionRole>
				<br/><br/>
				<div id="ss" style="display:none">
					<label>下线状态:</label>
					<input class="easyui-combobox" id="status" name="status"
						   data-options="
		                    valueField:'value',
		                    textField:'label',
		                    data: [{
								label: '维修',
								value: 'repair'
							},{
								label:'维护',
								value:'maintain'
							},{
								label:'补电',
								value:'charging'
							}],
		                    panelHeight:'auto'"  style="width:250px;height:40px;" >
					<br/><br/>
					<label>还车原因:</label>
					<input class="easyui-textbox" name="reason" id="reason"
						   data-options="multiline:true" style="height:70px;width:250px">
				</div>
			</div>
		</form>
	</div>
	
	<!-- 车辆运行信息 -->
	<div id="carRunInfoOper" class="easyui-dialog" closed="true" style="width:500px;height:300px;padding:10px 20px">
	     <table class="table-cls" width="100%" cellpadding="2" style="text-align: center;">
             <tr>
                <td align="right">车牌:</td>
                <td id='r-lpn'></td>
                <td align="right">车辆状态:</td>
                <td id='r-status'></td>
             </tr>
             <tr>
                <td align="right">CPU温度:</td>
                <td id='r-cpuTemperature'></td>
                <td align="right">车速（KM/H）:</td>
                <td id='r-speed'></td>
  			 </tr>
             <tr>
                <td align="right">充电状态:</td>
                <td id='r-batStatus'></td>
                <td align="right">车门状态:</td>
                <td id='r-doorStatus'></td>
             </tr>
             <tr>
                <td align="right">续航里程(KM):</td>
                <td id='r-enduranceMileage'></td>
                <td align="right">电池电量:</td>
                <td id='r-restBattery'></td>
             </tr>
             <tr>
                <td align="right">小电瓶电压(V):</td>
                <td id='r-smallBatteryVoltage'></td>
                <td align="right">小电瓶是否充电:</td>
                <td id='r-smallBatteryChargeStatus'></td>
             </tr>
             <tr>
                <td align="right">ACC 状态:</td>
                <td id='r-carSignal'></td>
                <td align="right">安全带状态</td>
                <td id='r-safeBeltStatus'></td>
             </tr>
             <tr>
                <td align="right">Ready状态:</td>
                <td id='r-readyStatus'></td>
                <td align="right">档位:</td>
                <td id='r-gear'></td>
             </tr>
             <tr>
                <td align="right">手刹状态:</td>
                <td id='r-handBreakStatus'></td>
                <td align="right">车灯是否开启:</td>
                <td id='r-lightStatus'></td>
             </tr>
             <tr>
                <td align="right">车窗状态:</td>
                <td id='r-windowStatus'></td>
                <td align="right">后备箱状态:</td>
                <td id='r-trunkStatus'></td>
             </tr>
             <tr>
             	<td align="right">车辆行驶总里程(KM):</td>
             	<td id="r-mileage"></td>
				 <td align="right">车型:</td>
				 <td id="r-brandName"></td>
             </tr>
          </table>
	</div>
	
</body>
<script language="javascript" type="text/javascript">
    function searchHideObj(frameid,contentid){
        document.getElementById(frameid).style.display = 'none';
        document.getElementById("prompt").style.display = 'block';
    }
    
    function searchShowObj(frameid, contentid) {
        var theContentObj = document.getElementById(frameid);
        if (theContentObj.style.display == 'none') {
            theContentObj.style.display = 'block';
            document.getElementById("prompt").style.display = 'none';
        }
    }
</script>
</html>