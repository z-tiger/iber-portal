<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<c:if test='${ null == sessionScope.user}'>
    <c:redirect url="./SessionTimeOut.html"></c:redirect>
</c:if>

<title>协鑫智慧交通管理平台</title>

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
	<script language="javascript" src="http://webapi.amap.com/maps?v=1.3&key=add84e19ee7ef7540e570cbf4c82cbfa"></script>
</head>

<body class="easyui-layout">
    <!-- 头部 -->
    <div data-options="region:'north',title:'',split:false" style="height:92px;">
        <div id="header">
            <div class="logo"></div>
            <div class="help">
                <div class="help-user">${sessionScope.user.name }，您好！</div>
                <ul class="help-nav">
                	<!-- 
                    <li><a href="#"><i class="help-ihome"></i>系统主页</a></li>
                    <li><a href="#"><i class="help-iuser"></i>个人信息</a></li>
                    <li><a href="#"><i class="help-ipw"></i>修改密码</a></li>
                     -->
                    <li><a href="index.jsp"><i class="help-iexit"></i>安全退出</a></li>
                </ul>
            </div>
        </div>
    </div>



    <div data-options="region:'west',title:'功能导航',split:true" style="width:245px;background:#ebeced">
        <div id="side-menu">
            <div id="my_menu" class="sdmenu">
			       <c:forEach items="${moduData }" var="v" varStatus="vs">
				        <c:if test="${ v.grade == 1 and  vs.index != 0}">
					           </div>
					      </c:if>
					      <c:choose>
					         <c:when test="${v.grade == 1 }">
					             <div class="collapsed">
					             <span>${v.name }</span>
					         </c:when>
					         <c:otherwise>
					             <a href="javascript:void addTab('${v.name }','${v.link }')" id="${v.link }">${v.name }</a>
					         </c:otherwise>
					      </c:choose>
				   </c:forEach>
				   
				   </div>
            </div>
        </div>
    </div>

    <div id="mainPanle" data-options="region:'center',title:'',split:false">
        <input type="hidden" id="current_page_href">
        <div id="tabs" class="easyui-tabs" fit="true" border="false">
            <div title="协鑫智慧交通" style="width:100%;height:100%;border:#ccc solid 1px;padding: 20px;overflow: hidden;" id="dituContent"></div>
        </div>
    </div>

</body>


<script type="text/javascript">
    var myMenu;
    window.onload = function() {
        myMenu = new SDMenu("my_menu");
        myMenu.init();
        mapInit() ;
    };

    //添加一个TAB，并打开页面
    function addTab(subtitle, url) {

        // 开启标签页
        if (!$('#tabs').tabs('exists', subtitle)) {
            $('#tabs').tabs('add', {
                title: subtitle,
                content: createFrame(url),
                closable: true,
                width: $('#mainPanle').width() - 10,
                height: $('#mainPanle').height() - 26
            });
        } else {
            $('#tabs').tabs('select', subtitle);
        }

        // 设置当前菜单为选中状态
        $('#current_page_href').val(url);
        var links = $('#side-menu a');
        for (var y = 0; y < links.length; y++) {
            links[y].className = "";
        }
        for (var i = 0; i < links.length; i++) {
            if (links[i].id == $('#current_page_href').val()) {
                links[i].className = "current";
                break;
            }
        }
    }

    function createFrame(url) {
        var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
        return s;
    }

// 加载地图

  var parkArr= new Array();
  var markerArr = new Array();
    function getData(){ //网点数据
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
   
 var mapObj,contextMenu,lng,lat;
	var point = new AMap.LngLat(parseFloat("113.944445"), parseFloat("22.553821"));
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
			mapObj.addControl(mapType);
			//加载浏览器定位
			geolocation = new AMap.Geolocation();
			mapObj.addControl(geolocation);
			//设置默认鼠标样式
			mapObj.setDefaultCursor("url('images/openhand.cur'),pointer");
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
		 getCarData() ;
		
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
          		var content = "<div style='width:600px;'>" +carArrList[i].lpn+
                 "&nbsp;&nbsp;<input type='button' id='opendoor' value='打开' onclick=\"javascript:openDoor('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='closedoor' value='关闭' onclick=\"javascript:closeDoor('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='startEngine' value='启动' onclick=\"javascript:startEngine('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='fireOff' value='熄火' onclick=\"javascript:fireOff('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='order' value='预约' onclick=\"javascript:bookCar('"+carArrList[i].lpn+"','"+carArrList[i].status+"');\"> "+
                 "<input type='button' id='returnCar' value='还车' onclick=\"javascript:returnCar('"+carArrList[i].lpn+"','"+carArrList[i].status+"');\"> "+
                 "<input type='button' id='navigate' value='导航' onclick=\"javascript:navigate('"+carArrList[i].lpn+"');\"> "+
                 "<input type='button' id='cancelnavigate' value='取消导航' onclick=\"javascript:cancelnavigate('"+carArrList[i].lpn+"');\"> "+
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
						    $.messager.alert("提示", "预约失败", "error");
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
    	 if(status != 'return'){
	      $.messager.alert("消息提示","请先选择网点后，在进行还车");
	    }else{
	    	$("#returnCarForm").form("clear");
			$("#returnCarForm").form('load', {
               returnCarLpn : lpn
           });
			$("#returnCarOper").dialog("open").dialog("setTitle", "取消预约车辆");
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
    	$.messager.confirm("提示","确定启动车吗?",function(r){
	      if(r){
   			$.post("car_fire_off",{"lpn":lpn},function(data){
					if(data=="succ"){
						alert("引擎启动成功！");
					}else{
						alert("引擎启动失败！");
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
		$.post("open_navigate",{"position":position,"chepai":lpn},function(data){
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
   			$.post("cancel_navigation",{"chepai":lpn},function(data){
					if(data=="succ"){
						alert("取消导航成功！");
					}else{
						alert("取消导航失败！");
					}
				});
			}
		});
    }
    
     //呼叫
     function call(lpn){
   		 $.messager.confirm("提示","确定呼叫车主吗?",function(r){
	      if(r){
   			$.post("call_car",{"chepai":lpn},function(data){
					if(data=="succ"){
						alert("取消导航成功！");
					}else{
						alert("取消导航失败！");
					}
				});
			}
		});
    }
</script>

 <script type="text/javascript">

	var refreshTime = 30000;	//数据刷新时间，单位为毫秒
	var timerTimes = 0;	//刷新倒计时
	var timerDiv = new Date().getTime(); 
	var isOpenTimer = false;
	//倒计时
	function timer() {  
		
    	var ts = refreshTime - timerTimes;//计算剩余的毫秒数  
        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
        mm = checkTime(mm);  
        ss = checkTime(ss);  
        $('#refreshTimer' + timerDiv).html('&nbsp;刷新倒计时：' + mm + '分' + ss + '秒');  
        timerTimes = timerTimes + 1000;
        if(!isOpenTimer) {
			setInterval("timer()",1000);  
			isOpenTimer = true;
		}
   }
   
         
   function checkTime(i)  {    
        if (i < 10) {    
    	     i = "0" + i;    
        }    
    	return i;    
   	}   
   		
  var carArr= new Array();
  var carLable="";
  var carMarkerArr = new Array();
  function getCarData(){ //车辆数据
       $.ajaxSetup({async:false});
       $.post("car_run_list", function(data){
         carArr=eval(data);
         if(carMarkerArr.length>0){
            for(var j=0;j<carMarkerArr.length;j++){
               if(carMarkerArr[j]){
                 carMarkerArr[j].setMap(null);
               }
            }
         }
         for ( var i = 0; i < carArr.length; i++) {
           carMarkerArr[i]=addMarkerCar(carArr[i]);//向地图中添加汽车marker
         }
          
          //预警提示
        var messageContent = '<span class="statisticMessageContent">';
        timerDiv = new Date().getTime();
        if(carArr.length > 0){
        	for ( var i = 0; i < carArr.length; i++) {
	        	if(carArr[i].speed > 10){ //获取车辆速度，查询车辆运行详情
	        		$.post("query_car_run_log_lpn?lpn="+encodeURIComponent(carArr[i].lpn), function(data){
	        			if(data != 'empty'){
	        				 var carRunLog = $.parseJSON( data ); 
				         	 messageContent += "<div>"+carRunLog.createTime + " "+carRunLog.lpn+" 超时行驶，时速"+ carRunLog.speed + "km/h。</div> <br/>";
	        			}
				    }); 
        		}else{
        			messageContent = '<div>暂无违规车辆</div>';
        		}
        	}
        }else{
        	messageContent = '<div>暂无违规车辆</div>';
        } 
		messageContent += '<div id="refreshTimer' + timerDiv + '"></div>';
		messageContent += '</span>';
		$.messager.show({
			title:'【报警通知】',
			msg: messageContent,
			width: 270,
			height: 280,
			showType:'show',
			timeout: refreshTime + refreshTime/2
		});  
		timer(); 
		timerTimes = 0; 
          setTimeout("getCarData()", refreshTime);
       });
       $.ajaxSetup({async:true}); 
   } 
	
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
            	$.post("car_run_order_info?lpn="+encodeURIComponent(json.lpn), function(data){
			          var carOrder = $.parseJSON( data ); 
			          //获取车辆最后使用时间
			          timeContext = "<div>开始时间："+carOrder.endTime+"</div>" ;
			    });
		    	statusContext = "<div>电量："+json.restBattery+"%</div>" ;
		    	markerImg.src="images/monitorCenter/carEmpty.png";	
		 } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
		    	statusName = "运营中" ;
		    	$.post("car_run_order_info?lpn="+encodeURIComponent(json.lpn), function(data){
			          var carOrder = $.parseJSON( data ); 
			          //获取车辆最后使用时间
			          timeContext = "<div>开始时间："+carOrder.endTime+"</div>" ;
			          statusContext = "<div>使用人："+carOrder.name+"</div>" ;
			    });
		    	
		    	markerImg.src="images/monitorCenter/carError.png";	
		 }else if(json.status == 'error'){
		    	statusName = "维修中" ;
		    	$.post("car_run_order_info?lpn="+encodeURIComponent(json.lpn), function(data){
			          var carOrder = $.parseJSON( data ); 
			          //获取车辆最后使用时间
			          timeContext = "<div>开始时间："+carOrder.endTime+"</div>" ;
			          statusContext = "<div>处理人："+carOrder.name+"</div>" ;
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";	
		 }
	     markerContent.appendChild(markerImg);
	     var markerSpan = document.createElement("span");
	     markerSpan.innerHTML = "<div style='width:80px;'>"+json.lpn+"</div>";
	     markerContent.appendChild(markerSpan);
		 var marker = new AMap.Marker({ //创建自定义点标注                 
// 		map : mapObj,
		position : new AMap.LngLat(p0, p1),
		offset : new AMap.Pixel(-10, -34),
		//icon : "${WEB_PATH}/html/images/statusImg/carBook.png"
				content:markerContent
		});
        marker.setMap(mapObj); 
		var content = "<div style='width:500px;'><div>" +
                 "<div><input type='button' id='opendoor' value='打开' onclick=\"javascript:openDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='closedoor' value='关闭' onclick=\"javascript:closeDoor('"+json.lpn+"');\"> "+
                 "<input type='button' id='startEngine' value='启动' onclick=\"javascript:startEngine('"+json.lpn+"');\"> "+
                 "<input type='button' id='fireOff' value='熄火' onclick=\"javascript:fireOff('"+json.lpn+"');\"> "+
                 "<input type='button' id='order' value='预约' onclick=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='returnCar' value='还车' onclick=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\"> "+
                 "<input type='button' id='navigate' value='导航' onclick=\"javascript:navigate('"+json.lpn+"');\"> "+
                 "<input type='button' id='cancelnavigate' value='取消导航' onclick=\"javascript:cancelnavigate('"+json.lpn+"');\"> "+
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

		AMap.event.addListener(marker, "click", function(e) {
			new AMap.InfoWindow({
				offset : new AMap.Pixel(0, -23),
				content : info.join("<br>")
			}).open(mapObj, marker.getPosition());
		});

		AMap.event.addListener(mapObj, 'click', getLnglat); //点击事件
		return marker;
	}
	
	
 </script>
 
</html>


