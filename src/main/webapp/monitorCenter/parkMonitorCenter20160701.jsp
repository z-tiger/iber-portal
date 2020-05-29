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
	<!-- 高德地图 -->
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
 
    <link rel="stylesheet" type="text/css" href="ui_lib/css/newLoginStyle.css"/>
 
 <script type="text/javascript">
  var parkArr= new Array();
  var markerArr = new Array();
    function getData(){
        $.ajaxSetup({async:false});
        $.post("park_list_code", function(data){
          parkArr=eval(data);
          if(markerArr.length>0){
             for(var j=0;j<markerArr.length;j++){
                if(markerArr[j]){
                  markerArr[j].setMap(null);
                }
             }
          }
           for ( var i = 0; i < parkArr.length; i++) {
            markerArr[i]=addMarkerPark(parkArr[i]);//向地图中添加网点marker
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
		//地图上添加网点
		function addMarkerPark(json) {
			var p0 = json.longitude;
			var p1 = json.latitude;
			//点标记中的文本
			var markerContent = document.createElement("div");
	        markerContent.className = "markerContentStyle";
	        var markerImg= document.createElement("img");
            markerImg.className="markerlnglat";
            markerImg.src="images/monitorCenter/park.png";	
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
		info.push("<div>"+json.address+"</div>");
		info.push("<div>地址："+json.address+"</div>");
		info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
		var freeNum = 0 ; //空闲车位数
		var carArrList = new Array();
		//通过网点ID获取所有车辆
		$.post("query_carRun_park_id?parkId="+encodeURIComponent(json.id),function(data){
		    carArrList = eval(data);
		    freeNum = json.parkNums - carArrList.length ;
		});
		info.push("<div>车位：<font color='green'>"+freeNum+"</font>/"+json.parkNums+"</div>"); 
		if(carArrList.length > 0) {
	    	for ( var i = 0; i < carArrList.length; i++) {
          		var content = "<div style='width:620px;'>" +carArrList[i].lpn+
                 "&nbsp;&nbsp;<input type='button' id='opendoor' value='打开' onclick=\"javascript:openDoor('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='closedoor' value='关闭' onclick=\"javascript:closeDoor('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='startEngine' value='启动' onclick=\"javascript:startEngine('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='fireOff' value='熄火' onclick=\"javascript:fireOff('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='order' value='预约' onclick=\"javascript:bookCar('"+carArrList[i].lpn+"','"+carArrList[i].status+"');\"> "+
                 "<input type='button' id='returnCar' value='还车' onclick=\"javascript:returnCar('"+carArrList[i].lpn+"','"+carArrList[i].status+"');\"> "+
                 "<input type='button' id='navigate' value='导航' onclick=\"javascript:navigate('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='cancelnavigate' value='取消导航' onclick=\"javascript:cancelnavigate('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='carLocus' value='轨迹' onclick=\"javascript:carLocus('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='catchedPhoto' value='抓拍' onclick=\"javascript:catchedPhoto('"+json.lpn+"');\"> "+
                 "<input type='button' id='call' value='呼叫' onclick=\"javascript:call('"+carArrList[i].lpn+"');\"></div>";
                 info.push(content);
        	} 
		 }else{
		    	info.push("<div style='width:300px;'><font color='red'>当前暂无车辆</font></div>");
		 }

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
					}else{
						alert("车门打开失败！");
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
    
    //设置导航
     function navigate(lpn){
    	 pwin=window.open("monitorCenter/getpos.jsp?lpn='"+lpn+"'","dd","menubar=no,location=no");
		 pwin.document.write(code.value);
		 pwin.document.charset="UTF-8";
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
					if(data=="succ"){
						alert("取消导航成功！");
					}else{
						alert("取消导航失败！");
					}
				});
			}
		});
    }
    
        //轨迹
    function carLocus(lpn){
    	window.open("car_locus?lpn="+encodeURI(lpn),"dd","menubar=no,location=no");
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
		var parkName = $("#s-parkName").textbox("getValue").toUpperCase() ;
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
</script>
</head>
<body>
<body style="width:100%; height:100%; position: absolute;">
    <div id="dituContent"  style="height: 100%; width:100%;"></div>
     <!-- 悬浮 -->
	<div id="searchFrame" style="width:240px; background:#fff; border:1px solid #666;z-index:3;position:absolute; top:0px;right:1px;">
		<div style="height:28px; line-height: 28px; text-align:center; font-size:14px; border-bottom:1px solid #666;">
			请输入网点名称
			<div style="width:20px; height:30px;float: right;">
				<a href="javascript:searchHideObj('searchFrame','searchContent')" style="text-decoration:none;color:Blue;">X</a>
			</div>
		</div>
		<div id="searchContent" style="margin: 5px;" align="center">
			<input type="text" name="s-parkName" class="easyui-textbox" id="s-parkName" style="height:30px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick="searchCar();">查询</a>
			<br/><span id="resultMsg" style="height:28px; line-height: 28px; text-align:center; font-size:14px; color: red"></span>
		</div>
	</div>
	<div id="prompt" style="width:20px; background:#fff; border:1px solid #666; z-index:3;position:absolute; top:0px; right:1px; padding:10px 4px; text-align:center; display: none;cursor: pointer;"  onclick="javascript:searchShowObj('searchFrame','searchContent')">网点名称搜索</div>
	
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
	                    
	                       <select id="isEnterpriseOrder" name="isEnterpriseOrder" style="width:250px;height:40px;">
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