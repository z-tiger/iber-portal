<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script type="text/javascript" src="ui_lib/js/common.js"></script>
    <script type="text/javascript" src="ui_lib/js/sdmenu.js"></script>
    <script type="text/javascript" src="monitorCenter/carRun.js"></script>
	<!-- 高德地图 -->
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
 
 <script type="text/javascript">
  var carArr= new Array();
  var carLable="";
  var markerArr = new Array();
    function getData(){
         $.ajaxSetup({async:false});
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
            markerArr[i]=addMarkerCar(carArr[i]);//向地图中添加汽车marker
           }
           
        });
        $.ajaxSetup({async:true});
   }

 </script>
<script type="text/javascript">
   function refreshme() {
        setInterval("getData();", 1000*60*2); //2分钟执行一次
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
		function addMarkerCar(json) {
			var p0 = json.longitude;
			var p1 = json.latitude;
			//点标记中的文本
			var markerContent = document.createElement("div");
	        markerContent.className = "markerContentStyle";
	        var markerImg= document.createElement("img");
            markerImg.className="markerlnglat";
            var statusName = "" ;
            var timeContext = "" ;
            var statusContext = "" ;
            if(json.status == 'empty') {
            	statusName = "闲置中" ; 
            	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){
            		if(data == "fail") {
            			timeContext = "<div>此车还未开始使用</div>" ;
            		}else{
            			var carOrder = $.parseJSON( data ); 
			          //获取车辆最后使用时间
			          timeContext = "<div>开始时间："+carOrder.endTime+"</div>" ;
            		}
			    });
		    	statusContext = "<div>电量："+json.restBattery+"%</div>" ;
		    	markerImg.src="images/monitorCenter/carEmpty.png";	
		    } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
		    	statusName = "运营中" ;
		    	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){
		    		if(data == "fail"){
		    			timeContext = "<div>还未运营记录</div>" ;
		    		}else{
		    			var carOrder = $.parseJSON( data ); 
			            if(json.status == 'ordered'){
			            	timeContext = "<div>预约时间："+carOrder.orderTime+"</div>" ;
			            }else{
			            	timeContext = "<div>开始时间："+carOrder.beginTime+"</div>" ;
			            }
			            statusContext = "<div>使用人："+carOrder.name+"</div>" ;
		    		}
			    });
		    	
		    	markerImg.src="images/monitorCenter/carError.png";	
		    }else if(json.status == 'repair'){
		    	statusName = "维修中" ;
		    	$.post("car_repair_info?status=1&lpn="+encodeURI(json.lpn), function(data){
		    		if(data != "fail") {
		    			var carOrder = $.parseJSON( data ); 
			           //获取车辆最后使用时间
			           timeContext = "<div>开始时间："+carOrder.startTime+"</div>" ;
			           statusContext = "<div>处理人："+carOrder.responsiblePerson+"</div>" ;
		    		}
	    		  	  
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";	
		    }else if(json.status == 'maintain'){
		    	statusName = "维护中" ;
		    	$.post("car_repair_info?status=2&lpn="+encodeURI(json.lpn), function(data){
		    		if(data != "fail") {
		    			 var carOrder = $.parseJSON( data ); 
			         	 //获取车辆最后使用时间
			          	 timeContext = "<div>开始时间："+carOrder.startTime+"</div>" ;
				         statusContext = "<div>处理人："+carOrder.responsiblePerson+"</div>" ;
			    	}
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";	
		    }
		    
	       markerContent.appendChild(markerImg);
	     var markerSpan = document.createElement("span");
	      markerSpan.innerHTML = "<div style='width:80px;'>"+json.lpn+"</div>";
	    markerContent.appendChild(markerSpan);
			var marker = new AMap.Marker({ //创建自定义点标注                 
// 				map : mapObj,
				position : new AMap.LngLat(p0, p1),
				offset : new AMap.Pixel(-10, -34),
				//icon : "${WEB_PATH}/html/images/statusImg/carBook.png"
				content:markerContent
			});
            marker.setMap(mapObj); 
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
		var info = [];
		
		if(statusName=="运营中"){
			//<b>"+statusName +" "  + json.lpn +"<div>速度" +json.speed+"km/h</b>
			info.push("<div>速度：" +json.speed+"km/h<span style='float:left;width:400px;'>"+statusName+" "+json.lpn+"</span></div></div>");
		}else{
			info.push("<div>"+statusName +" "  + json.lpn +"</div>");
		}
		info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
		info.push("<div>地点："+json.address+"</div>");
		if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
			//获取导航地址
			$.post("car_navigate_info?orderId="+json.orderId, function(data){
			     if(data != "fail") {
			     	var carNavigate = $.parseJSON( data ); 
			     	info.push("<div>导航地址："+carNavigate.address+"</div>");
			     }
			});
		}
		info.push(timeContext) ;
		info.push(statusContext) ;
		info.push(content);

		AMap.event.addListener(marker, "click", function(e) {
			new AMap.InfoWindow({
				offset : new AMap.Pixel(0, -23),
				content : info.join("<br>")
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
						} else{
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
							$("#returnCarOper").dialog("close");
							getData();//重新加载车辆
						} else if(result == "noCheck"){
						    $.messager.alert("提示", "该会员还未通过审核，不能预约车辆", "error");
						}else if(result == "noMember"){
						    $.messager.alert("提示", "会员身份证号不正确，请重新输入", "error");
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
    
    function returnCar(lpn,status){
    	 if(status == 'useCar' || status == 'return'){
	      	$("#returnCarForm").form("clear");
			$("#returnCarForm").form('load', {
               returnCarLpn : lpn
           });
			$("#returnCarOper").dialog("open").dialog("setTitle", "还车");
	    }else{
	    	$.messager.alert("消息提示","车辆不为运营状态");
	    }
    }
    
    //打开车门
    function openDoor(lpn){
    	$.messager.confirm("提示","确定打开车门吗?",function(r){
	      if(r){
   			$.post("open_car_door",{"lpn":lpn},function(data){
					if(data=="succ"){
						alert("车门打开成功！");
						getData();//重新加载车辆
					}else{
						alert("车门打开失败！");
						getData();//重新加载车辆
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
						alert("车门关闭成功！");
					}else{
						alert("车门关闭失败！");
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
						alert("引擎启动成功！");
					}else{
						alert("引擎启动失败！");
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
						alert("熄火成功！");
					}else{
						alert("熄火失败！");
					}
				});
			}
		});
    }
    
    //轨迹
    function carLocus(lpn){
    	window.open("car_locus?lpn="+encodeURI(lpn),"dd","menubar=no,location=no");
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
					alert("目的地已经设置成功！");
			}else{
					alert("导航失败！");
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
						alert("取消导航成功！");
					}else{
						alert("取消导航失败！");
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
						alert("抓拍成功！");
					}else{
						alert("抓拍失败！");
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
						alert("呼叫成功！");
					}else{
						alert("呼叫失败！");
					}
				});
			}
		});
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
	            			timeContext = "<div>此车还未开始使用</div>" ;
	            		}else{
	            			var carOrder = $.parseJSON( data ); 
				          //获取车辆最后使用时间
				          timeContext = "<div>开始时间："+carOrder.endTime+"</div>" ;
	            		}
				    });
			    	statusContext = "<div>电量："+json.restBattery+"%</div>" ;
			    } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
			    	statusName = "运营中" ;
			    	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){
			    		if(data == "fail"){
			    			timeContext = "<div>还未运营记录</div>" ;
			    		}else{
			    			var carOrder = $.parseJSON( data ); 
				            //获取车辆最后使用时间
				            timeContext = "<div>开始时间："+carOrder.beginTime+"</div>" ;
				            statusContext = "<div>使用人："+carOrder.name+"</div>" ;
			    		}
				    });
			    }else if(json.status == 'repair'){
			    	statusName = "维修中" ;
			    	$.post("car_repair_info?status=1&lpn="+encodeURI(json.lpn), function(data){
		    		  	  var carOrder = $.parseJSON( data ); 
				          //获取车辆最后使用时间
				          timeContext = "<div>开始时间："+carOrder.startTime+"</div>" ;
				          statusContext = "<div>处理人："+carOrder.responsiblePerson+"</div>" ;
				    });
			    }else if(json.status == 'maintain'){
			    	statusName = "维护中" ;
			    	$.post("car_repair_info?status=2&lpn="+encodeURI(json.lpn), function(data){
		    		  	  var carOrder = $.parseJSON( data ); 
				          //获取车辆最后使用时间
				          timeContext = "<div>开始时间："+carOrder.startTime+"</div>" ;
				          statusContext = "<div>处理人："+carOrder.responsiblePerson+"</div>" ;
				    });
			    }
		    	
		    	var content = "<div style='width:620px;' align=\"center\"><div>" +
                 "<div><input type='button' id='opendoor' value='打开' onclick=\"javascript:openDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='closedoor' value='关闭' onclick=\"javascript:closeDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='startEngine' value='启动' onclick=\"javascript:startEngine('"+json.lpn+"');\"> "+
                 "<input type='button' id='fireOff' value='熄火' onclick=\"javascript:fireOff('"+json.lpn+"');\"> "+
                 "<input type='button' id='order' value='预约' onclick=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='returnCar' value='还车' onclick=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='navigate' value='导航' onclick=\"javascript:navigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='cancelnavigate' value='取消导航' onclick=\"javascript:cancelnavigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='carLocus' value='轨迹' onclick=\"javascript:carLocus('"+json.lpn+"');\"> "+
                 "<input type='button' id='call' value='呼叫' onclick=\"javascript:call('"+json.lpn+"');\"></div>";
                 
				var info = [];
		
				if(statusName=="运营中"){
					//<b>"+statusName +" "  + json.lpn +"<div>速度" +json.speed+"km/h</b>
					info.push("<div>速度：" +json.speed+"km/h<span style='float:left;width:400px;'>"+statusName+" "+json.lpn+"</span></div></div>");
				}else{
					info.push("<div>"+statusName +" "  + json.lpn +"</div>");
				}
				info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
				info.push("<div>地点："+json.address+"</div>");
				info.push(timeContext) ;
				info.push(statusContext) ;
				info.push(content);
				
				new AMap.InfoWindow({
					offset : new AMap.Pixel(0, -23),
					content : info.join("<br>")
				}).open(mapObj, markerArr[i].getPosition());
			}
		}
	};
</script>
</head>
<body>
<body style="width:100%; height:100%; position: absolute;">
	<div id="pMap" class="easyui-panel" title="运营车辆地图展示"
	    style="width:100%;height:70%;padding:10px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',
	    collapsible:true,maximizable:true">
	     <div id="dituContent"  style="height: 100%; width:100%;"></div>
	</div>
	
    <div id="pList" class="easyui-panel" title="运营车辆列表展示"
	    style="width:100%;height:30%;mar;padding-bottom:20px;background:#fafafa;"
	    data-options="iconCls:'icon-car-repair',maximizable:true">
	    <table id="grid" toolbar="#toolbar"></table>
	      
		<div id="toolbar" style="height:auto">
	      <div><form name="scarMrgForm" id="scarMrgForm">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="scityCode"
						data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
			车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
			网点:<input type="text" name="parkName" class="easyui-textbox" id="s-parkName">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a>
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
   	<div id="bookcarOper" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
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
	           
		</form>
	</div>
	
	 <!-- 还车 -->
	<div id="returnCarOper" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
		<form id="returnCarForm" method="post">
            <div class="fitem">
                <br/><br/>
			    <div class="fitem">
	               <label>车　　牌:</label>
	               <input id="returnCarLpn" name="returnCarLpn" class="easyui-textbox" readonly="readonly" style="width:250px;height:40px;">
	            </div>
	            <br/><br/>
                <label>选网点:</label>
                <input class="easyui-combobox" id="parkId" name="parkId" 
            		data-options=" url:'park_list_code',
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
	                    panelHeight:'auto'" required="true" style="width:250px;height:40px;">
            </div>
		</form>
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