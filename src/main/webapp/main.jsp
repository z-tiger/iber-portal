<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<c:if test='${ null == sessionScope.user}'>
    <c:redirect url="./SessionTimeOut.html"></c:redirect>
</c:if>

<title>宜步出行运营支持系统</title>
	 <!-- 三级下拉菜单 -->
    <link rel="stylesheet" type="text/css" href="ui_lib/css/LevelThreeMenu/bootstrap.min.css"> 
    <link rel="stylesheet" type="text/css" href="ui_lib/css/LevelThreeMenu/font-awesome.min.css"> 
    <link rel="stylesheet" type="text/css" href="ui_lib/css/LevelThreeMenu/metismenu.min.css"> 
    <link rel="stylesheet" type="text/css" href="ui_lib/css/LevelThreeMenu/demo.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/LevelThreeMenu/prism.min.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/LevelThreeMenu/default.css">  
    
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css"/>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css"/>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/sdmenu.css"/>
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

        .fitem {
            margin-bottom: 5px;
        }

        .fitem label {
            display: inline-block;
            width: 80px;
        }

        .fitem input {
            width: 160px;
        }

        .fitem a {
            margin-right: 5px;
        }
    </style>
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="ui_lib/js/common.js"></script>
    <script type="text/javascript" src="ui_lib/js/sdmenu.js"></script>
    <!-- 三级下拉菜单 -->
    <script type="text/javascript" src="ui_lib/js/metismenu.js"></script>
    <script type="text/javascript" src="ui_lib/js/prism.min.js"></script> 
	<!-- 高德地图 -->
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
</head>

<body class="easyui-layout" >
    <!-- 头部 -->    
    <div data-options="region:'north',title:'',split:false" style="height:82px;">
        <%@ include file="_head.jsp" %>
    </div>

      <div data-options="region:'south',title:'',split:false" style="height:20px;background-color: #d7ebf9;" >
           <span style="margin-left: 10px;">用户:${sessionScope.user.name }&nbsp;&nbsp;
             角色:${sessionScope.roleName_1 }</span>
         <span style="float: right;">© 2016 宜步出行  粤ICP备16087356号&nbsp;&nbsp; V2.28&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
         <span style="clear: both;"></span>
    </div>

    <div data-options="region:'west',title:'功能导航',split:true" style="width:180px;">
      <%@ include file="_menu.jsp" %>
    </div>

    <div id="mainPanle" data-options="region:'center',title:'',split:false" ">
        <input type="hidden" id="current_page_href">
        <div id="tabs" class="easyui-tabs" fit="true" border="false">
            <div title="欢迎登陆" data-options="closable:true" style="background-color: #FFFFFF; display: table-cell; vertical-align: middle;" align="center">
                <img src="images/welcome.png" alt="无法显示图片" style="display:inline-block" draggable="false"/>
            </div>
           <div title="宜步出行运营支持系统" style="width:100%;height:100%;border:#ccc solid 1px;padding: 20px;overflow: hidden;" id="dituContent" >
            		 <!-- 悬浮 -->
					<div id="searchFrame" style="width:240px; background:#fff; border:1px solid #666;z-index:3;position:absolute; top:0px;right:1px;">
						<div style="height:28px; line-height: 28px; text-align:center; font-size:14px; border-bottom:1px solid #666;">
							请输入网点或车牌
							<div style="width:20px; height:30px;float: right;">
								<a href="javascript:searchHideObj('searchFrame','searchContent')" style="text-decoration:none;color:Blue;">X</a>
							</div>
						</div>
						<div id="searchContent" style="margin: 5px;" align="center">
							<input type="text" name="s-parkCarName" class="easyui-textbox" id="s-parkCarName" style="height:30px">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick="searchCar();">查询</a>
							<br/><span id="resultMsg" style="height:28px; line-height: 28px; text-align:center; font-size:14px; color: red"></span>
						</div>
					</div>
					<div id="prompt" style="width:20px; background:#fff; border:1px solid #666; z-index:3;position:absolute; top:0px; right:1px; padding:10px 4px; text-align:center; display: none;cursor: pointer;"  onclick="javascript:searchShowObj('searchFrame','searchContent')">网点或车牌搜索</div>
	
	
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
            </div>
        </div>
    </div>
    <div style="margin-top: 500px;">
    <!-- 车辆运行信息 -->
	<div id="carRunInfoOper" class="easyui-dialog" closed="true" style="width:400px;height:300px;padding:10px 20px; top: 520px; left:880px;">
	     <table class="table-cls" width="100%" cellpadding="2">
             <tr>
                <td align="right">车牌:</td>
                <td id='r-lpn'></td>
                <td align="right">车辆状态:</td>
                <td id='r-status'></td>
             </tr>
             <tr>
                <td align="right">后视镜CPU温度:</td>
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
          </table>
	</div>
	</div>

    <div id="modifyPwdView" class="easyui-dialog" closed="true"
         style="width:400px;height:280px;padding:10px 20px">
        <div style="color: red;" align="center">
            您的密码已过期，请修改密码，否则您的账号将被冻结
            <br/>
            <br/>
        </div>
        <form name="modifyPwdForm" id="modifyPwdForm" method="post">
            <input name="uid" id="pd-uid" type="hidden" value="${sessionScope.user.id}">
            <div class="fitem">
                <label>原密码:</label>
                <input name="oldPwd" type="password" class="easyui-textbox" required="true"
                       missingMessage="请填写原密码" id="oldPwd" />
            </div>

            <div class="fitem">
                <label>新密码:</label>
                <input  name="newPwd" id="pwd" type="password"  class="easyui-textbox"  	required="true"  missingMessage="请确认密码"/>
            </div>
            <div class="fitem">
                <label>确认新密码:</label>
                <input id="pwd2"  class="easyui-textbox" type="password" data-options="required:true"   validType="same['pwd']" missingMessage="请确认密码" invalidMessage="两次输入密码不一致">
            </div>
        </form>

    </div>
</body>



<script type="text/javascript">
	/*function warningBlock(){
		document.getElementById("warningFrame").style.display = 'block';
	}*/
                
  //  var myMenu;
    window.onload = function() {
        //myMenu = new SDMenu("my_menu");
       // myMenu.init();
       //  mapInit() ;
        welcome();
    };

    //添加一个TAB，并打开页面
    function addTab(subtitle, url) {
       //alert(subtitle);
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
        var links = $(".sidemenu li a");
        for (var y = 0; y < links.length; y++) {
            links[y].className = "";
        }
        for (var i = 0; i < links.length; i++) {
            if (links[i].id == $('#current_page_href').val()) {
                links[i].className = "active";
                break;
            }
        }
    }

    function modifyPwd() {
        $("#modifyPwdView").dialog("open");
    }

    function createFrame(url) {
    	//document.getElementById("warningFrame").style.display = 'none';
    	//document.getElementById("prompt").style.display = 'none';
        var s = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
        return s;
    }

// 加载地图
  
  var parkArr= new Array();
  var markerArr = new Array();
  var carRunParkInfo ;
  function getData(){ //网点数据
   // setTimeout(function () {
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
          if(markerArr.length>0){
             for(var j=0;j<markerArr.length;j++){
                if(markerArr[j]){
                  markerArr[j].setMap(null);
                }
             }
          }
           for ( var i = 0; i < parkArr.length; i++) {
           		markerArr[i]=addMarkerPark(parkArr[i],carRunParkInfo);//向地图中添加网点marker
           }
           setTimeout("getData()", 1000*60*2);
        });
	// },1000); 
   }
   
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
		 getCarData() ;
		
	}

    function welcome(){
	    $('#tabs').tabs('close','宜步出行运营支持系统');
    }
		boo=true;
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
           // markerImg.title=json.name ;
	        markerContent.appendChild(markerImg);
	        var markerSpan = document.createElement("span");
	        markerSpan.innerHTML = "<div style='width:80px;'>"+json.name+"</div>";
	        markerContent.appendChild(markerSpan); 
	        if(p0&&p1){
	        	var marker = new AMap.Marker({ //创建自定义点标注                 
//	 				map : mapObj,
					position : new AMap.LngLat(p0, p1),
					offset : new AMap.Pixel(-10, -34),
					//icon : "${WEB_PATH}/html/images/statusImg/carBook.png"
					content:markerContent
				});
	            marker.setMap(mapObj);
	        }
			 
		var info = [];
		info.push("<div class='bubble'><div class='bubble-title'><h1><span>"+json.name+"</span></h1>");
		info.push("<h2>地址："+json.address+"</h2>");
		info.push("</div><div class='bubble-con'>");
		var freeCar = 0 ; //空闲车位数
		var carArrList = new Array();
		//通过网点ID获取所有车辆
//		$.ajaxSetup({async:false});
//		$.post("query_carRun_park_id?parkId="+encodeURIComponent(json.id),function(data){
//		    carArrList = eval(data);
//		});
//		$.ajaxSetup({async:true});
        if(null!=carRunParkInfos['parkId'+json.id]){
        	carArrList = carRunParkInfos['parkId'+json.id];
        }
		if(carArrList.length > 0) {
	    	for ( var i = 0; i < carArrList.length; i++) {
	    		if(carArrList[i].status == 'empty'){
	    			freeCar += 1 ;
	    		}
        	}
		}
		var freeNum = json.parkNums - freeCar ; //总车位 - 空闲车数 = 空闲停车位
		
		info.push("<h1>车位："+freeNum+"<span>/"+json.parkNums+"</span></h1>"); 
		info.push("<h1>车辆："+freeCar+"</h1>");
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
 	$("#bookcarOper").dialog( {
		width : "400",
		height : "500",
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
							getCarData();//重新加载车辆
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
							getCarData();//重新加载车辆
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
						$.messager.alert("消息提示","车门打开成功！");
						getData();//重新加载车辆
					}else{
						$.messager.alert("消息提示","车门打开失败！");
						getData();//重新加载车辆
					}
                    if (data == "notUse"){
                        $.messager.alert("消息提示","日租车辆不为用车状态");
                        getData();
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
    	$.messager.confirm("提示","确定熄火车吗?",function(r){
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
    
    //设置导航
     function navigate(lpn){
    	 pwin=window.open("monitorCenter/getpos.jsp?lpn='"+lpn+"'","dd","menubar=no,location=no");
		 pwin.document.write(code.value);
		 pwin.document.charset="UTF-8";
		 getCarData();//重新加载车辆
    }
    
    //获取导航gps信息
    function setPos(position,lpn){
		$.post("open_navigate",{"position":position,"lpn":lpn},function(data){
			getCarData();//重新加载车辆
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
    //轨迹
    function carLocus(lpn){
    	window.open ('http://ibgoing.com/bdcarMonitor/') ;
    	//window.open("car_locus?lpn="+encodeURI(lpn),"dd","menubar=no,location=no");
    }
</script>

 <script type="text/javascript">
 	function refreshme() {
        setInterval("getData();", 1000*60*2); //2分钟执行一次
        setInterval("getCarData();", 1000*60*2); //2分钟执行一次,获取异常车辆时已执行，此处不在加载车辆信息
    }
		
	var refreshTime = 1000*60*2;	//数据刷新时间，单位为毫秒
	//var timerTimes = 0;	//刷新倒计时
	var timerDiv = new Date().getTime(); 
	var isOpenTimer = false;
	//倒计时
	/* function timer() {  
		
    	var ts = refreshTime - timerTimes;//计算剩余的毫秒数  
        var mm = parseInt(ts / 1000 / 60 % 60, 10);//计算剩余的分钟数  
        var ss = parseInt(ts / 1000 % 60, 10);//计算剩余的秒数  
        mm = checkTime(mm);  
        ss = checkTime(ss);  
        $('#refreshTimer').html('&nbsp;刷新倒计时：' + mm + '分' + ss + '秒');  
        timerTimes = timerTimes + 1000;
        if(!isOpenTimer) {
			setInterval("timer()",1000);  
			isOpenTimer = true;
		}
   } */
   
         
   function checkTime(i)  {    
        if (i < 10) {    
    	     i = "0" + i;    
        }    
    	return i;    
   	}   
   		
   /**获取车辆数据*/
  var carArr= new Array();
  var carLable="";
  var carMarkerArr = new Array();
  var carRunOrderInfo;
  var carNavigateInfo;
  function getCarData(){ //车辆数据  
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
         	if(carArr[i].status == 'ordered' || carArr[i].status == 'useCar' || carArr[i].status == 'return'  ){
	           carMarkerArr[i]=addMarkerCar(carArr[i],carRunOrderInfo,carNavigateInfo);//向地图中添加 运营汽车
         	}
         }
        setTimeout("getCarData()", refreshTime);
       });
       $.ajaxSetup({async:true}); 
   }
   	
   	//地图上添加汽车
	function addMarkerCar(json,carRunOrderInfos,carNavigateInfos) {
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
           			timeContext = "<h1>此车还未开始使用</h1>" ;
           		}else{
           			var carOrder = $.parseJSON( data ); 
		          //获取车辆最后使用时间
		          timeContext = "<h1>开始时间："+carOrder.endTime+"</h1>" ;
           		}
		    });
	    	statusContext = "<h1>电量："+json.restBattery+"%</h1>" ;
	    	markerImg.src="images/monitorCenter/carEmpty.png";	
		 } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
	    	statusName = "运营中" ;
	    	var infos =carRunOrderInfos[''+json.lpn];
	    	if(infos==null){
	    		timeContext = "<h1>还未运营记录</h1>" ;
	    	}else{
	            if(json.status == 'ordered'){
		            timeContext = "<h1>预约时间："+infos.orderTime+"</h1>" ;
	            }else{
	            	timeContext = "<h1>开始时间："+infos.beginTime+"</h1>" ;
	            }
    			if (infos.userType == '1') {
    				statusContext = "<h1>使用人："+infos.name+"(员工使用)"+"</h1>" ;
				}else if (infos.userType == '0') {
					statusContext = "<h1>使用人："+infos.name+"</h1>" ;
				} else if (infos.userType == '2') {
                    statusContext = "<h1>使用人："+infos.name+"(日租订单)"+"</h1>" ;
                }
	       //     statusContext = "<h1>使用人："+infos.name+"</h1>" ;
	    	}
<%--	    	$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){--%>
<%--		        if(data == "fail"){--%>
<%--	    			timeContext = "<h1>还未运营记录</h1>" ;--%>
<%--	    		}else{--%>
<%--	    			var carOrder = $.parseJSON( data ); --%>
<%--		            if(json.status == 'ordered'){--%>
<%--			            timeContext = "<h1>预约时间："+carOrder.orderTime+"</h1>" ;--%>
<%--		            }else{--%>
<%--		            	timeContext = "<h1>开始时间："+carOrder.beginTime+"</h1>" ;--%>
<%--		            }--%>
<%--		            statusContext = "<h1>使用人："+carOrder.name+"</h1>" ;--%>
<%--	    		}--%>
<%--		    });--%>
	    	
	    	markerImg.src="images/monitorCenter/carError.png";	
		 }else if(json.status == 'repair'){
		    	statusName = "维修中" ;
		    	$.post("car_repair_info?lpn="+encodeURI(json.lpn), function(data){
			          var carOrder = $.parseJSON( data ); 
			          //获取车辆最后使用时间
			          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
			          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";	
		 }else if(json.status == 'maintain'){
		    	statusName = "维护中" ;
		    	$.post("car_repair_info?lpn="+encodeURI(json.lpn), function(data){
			          var carOrder = $.parseJSON( data ); 
			          //获取车辆最后使用时间
			          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
			          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
			    });
		    	markerImg.src="images/monitorCenter/carOffline.png";	
		 }
		 //markerImg.title=json.lpn ;
	     markerContent.appendChild(markerImg);
	      var markerSpan = document.createElement("span");
	     markerSpan.innerHTML = "<div style='width:80px;'>"+json.lpn+"</div>";
	     markerContent.appendChild(markerSpan); 
	     if (p0&&p1) {
	    	 var marker = new AMap.Marker({ //创建自定义点标注                 
//	    	 		map : mapObj,
	    			position : new AMap.LngLat(p0, p1),
	    			offset : new AMap.Pixel(-10, -34),
	    			//icon : "${WEB_PATH}/html/images/statusImg/carBook.png"
	    					content:markerContent
	    	});
	    	marker.setMap(mapObj); 
		 }
		
        var content = "<div class='bubble-butbox'><dl>";
        content += "<dt><a class=btnCar href=\"javascript:queryCarRunInfo('"+json.lpn+"');\">运行信息</a></dt>"; 
        content += " <dd class='but'>";
        content += "<a href=\"javascript:openDoor('"+json.lpn+"');\" id='opendoor' class='left'>开门</a>";
        content += "<a href=\"javascript:closeDoor('"+json.lpn+"');\" id='closedoor' class='right'>关门</a>";
        content += "<a href=\"javascript:startEngine('"+json.lpn+"');\"  id='startEngine' class='left'>启动</a>";
        content += "<a href=\"javascript:fireOff('"+json.lpn+"');\" class='right'>熄火</a>";
        content += "<a href=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\" class='left'>预约</a>";
        content += "<a href=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\" class='right' style='margin-right:0;'>还车</a>";
        content += " </dd>";
        content += " <dd class='cut'>";
//         content += "<a href=\"javascript:navigate('"+json.lpn+"');\"><img src='images/newLogin/bubble-Direction.png'>导航</a>";
//         content += " <a href=\"javascript:cancelnavigate('"+json.lpn+"');\"><img src='images/newLogin/bubble-Target.png' >取消导航</a>";
        content += " <a href=\"javascript:carLocus('"+json.lpn+"');\"><img src='images/newLogin/bubble-avtivity.png' >轨迹</a>";
        content += "<a href=\"javascript:catchedPhoto('"+json.lpn+"');\"><img src='images/newLogin/bubble-photo.png' >抓拍</a>";
        content += " <a href=\"javascript:call('"+json.lpn+"');\" style='margin-right:0'><img src='images/newLogin/bubble-Headphone.png' >呼叫</a>";
        content += "</dd>";
        content += " </dl> </div>";
		var info = [];
		
		if(statusName=="运营中"){
			//<b>"+statusName +" "  + json.lpn +"<div>速度" +json.speed+"km/h</b>
			info.push("<div style='width:580px;' class='bubble'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
			// timeContext = "<h1>速度："+json.speed+"km/h</h1>" ;
			//info.push("<div>速度：" +json.speed+"km/h<span style='float:left;width:400px;'>"+statusName+" "+json.lpn+"</span></div></div>");
		}else{
			info.push("<div style='width:580px;' class='bubble'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em></h1>");
		}
		//info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
		info.push("<h2>地址："+json.address+"</h2></div>");
		
		if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
			//获取导航地址
<%--			$.ajax({  --%>
<%--	            type : "post",  --%>
<%--	             url : "car_navigate_info",  --%>
<%--	             data : "orderId=" +json.orderId,  --%>
<%--	             async : false,   //采用同步--%>
<%--	             success : function(data){  --%>
<%--	            	 if(data != "fail") {--%>
<%--					     	var carNavigate = $.parseJSON( data ); --%>
<%--					     	console.log(carNavigate);--%>
<%--					     	info.push("<h1>导航地址："+carNavigate.address+"</h1>");--%>
<%--					 }--%>
<%--	             }  --%>
<%--	        });--%>
           var cnInfo = carNavigateInfos[''+json.orderId];
           if(cnInfo!=null){
        	   info.push("<h1>导航地址："+cnInfo.address+"</h1>");
           }
		}
		
		info.push("<div class='bubble-con'>");
		info.push(timeContext) ;
		info.push(statusContext) ;
		info.push("</div>");
		info.push(content);
       info.push("</div>");
       if(null!=marker){
   		AMap.event.addListener(marker, "click", function(e) {
			new AMap.InfoWindow({
				offset : new AMap.Pixel(0, -23),
				content : info.join("")
			}).open(mapObj, marker.getPosition());
		});

		AMap.event.addListener(mapObj, 'click', getLnglat); //点击事件
       }
		return marker;
	}
	
	      //query
	function searchCar(){
		var parkName = $("#s-parkCarName").textbox("getValue").toUpperCase() ;
		var isExist=false ;
		for(var i = 0 ; i < parkArr.length ; i ++) {
			if(parkArr[i].name.indexOf(parkName) >= 0){
				var json = parkArr[i] ;
				mapObj.setZoomAndCenter(18,new AMap.LngLat(json.longitude,json.latitude));
				//mapObj.setCenter(new AMap.LngLat(json.longitude,json.latitude)); //设置地图中心点
				isExist = true ;
			}
		}
		
		for(var i = 0 ; i < carArr.length ; i ++) {
			if(carArr[i].status == 'return' || carArr[i].status == 'ordered' || carArr[i].status == 'useCar'){
				if(carArr[i].lpn.indexOf(parkName) >= 0){
					var json = carArr[i] ;
					mapObj.setZoomAndCenter(18,new AMap.LngLat(json.longitude,json.latitude));
					//mapObj.setCenter(new AMap.LngLat(json.longitude,json.latitude)); //设置地图中心点
					isExist = true ;
				}
			}
		}
		if(isExist){
			$("#resultMsg").html("");
		}else if(isExist == false){
			$("#resultMsg").html("未查询到相关数据");
		}
	}
	
 </script>
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
    
    function queryCarRunInfo(lpn){
		$.post("car_run_info",{"lpn":lpn}, function(data){
    		if(data == "fail"){
    			$.messager.alert("消息提示","获取车辆运行信息失败！");
    		}else{
    			var carRun = $.parseJSON( data );
    			$("#r-lpn").html(carRun.lpn);
    			var status = "" ;
    			if(carRun.status=="empty"){
    				status = "空闲中";
    			}else if(carRun.status=="maintain"){
    				status = "维护中";
    			}else if(carRun.status=="repair"){
    				status = "维护中";
    			}else{
    				status = "运营中";
    			}
    			$("#r-status").html(status);
    			$("#r-cpuTemperature").html(carRun.cpuTemperature);
    			$("#r-speed").html(carRun.speed);
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
    			if(carRun.smallBatteryChargeStatus=="1"){
    				smallBatteryChargeStatus = "正在充" ;
    			}else{
    				smallBatteryChargeStatus = "未充" ;
    			}
    			$("#r-smallBatteryChargeStatus").html(smallBatteryChargeStatus);
    			if(carRun.carSignal=="1"){
    				carSignal = "开" ;
    			}else{
    				carSignal = "关" ;
    			}
    			$("#r-carSignal").html(carSignal);
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
    			if(carRun.gear=="1"){
    				gear = "前进" ;
    			}else if(carRun.gear=="11"){
    				gear = "后退" ;
    			}else if(carRun.gear=="12"){
    				gear = "空档" ;
    			}
    			$("#r-gear").html(gear);
    			if(carRun.handBreakStatus=="1"){
    				handBreakStatus = "拉紧" ;
    			}else{
    				handBreakStatus = "松开" ;
    			}
    			$("#r-handBreakStatus").html(handBreakStatus);
    			if(carRun.lightStatus=="1"){
    				lightStatus = "开启" ;
    			}else{
    				lightStatus = "关闭" ;
    			}
    			$("#r-lightStatus").html(lightStatus);
    			if(carRun.windowStatus=="1"){
    				windowStatus = "开启" ;
    			}else{
    				windowStatus = "关闭" ;
    			}
    			$("#r-windowStatus").html(windowStatus);
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

    $(function() {
        $("#modifyPwdView").dialog({
            closable: false,
            shadow: false,
            width: "380",
            height: "220",
            top: "70",
            title: "修改密码",
            buttons: [{
                text: "确定",
                iconCls: "icon-save",
                handler: function () {
                    $("#modifyPwdForm").form("submit", {
                        url: "sys_modify_pwd",
                        onSubmit: function () {
                            if ($(this).form("validate")) {
                                var _oldPwd = $("#oldPwd").textbox("getValue");
                                var _newPwd = $("#pwd").textbox("getValue");
                                var _newPwd2 = $("#pwd2").textbox("getValue");
                                var reg = /^[A-Za-z0-9]{6,20}$/;
                                var reg1 = /[0-9]+/;
                                var reg2 = /[A-Za-z]+/;
                                if (!reg.test(_newPwd) || !reg1.test(_newPwd) || !reg2.test(_newPwd)) {
                                    $.messager.alert("提示", "新密码不能小于6位，必须是数字加字母组合，请重新输入", "error");
                                    return false;
                                }
                                if (_newPwd != _newPwd2) {
                                    $.messager.alert("提示", "2次输入的新密码不一致，请重新输入", "error");
                                    return false;
                                }
                                if (_newPwd == _oldPwd) {
                                    $.messager.alert("提示", "新密码与原密码一样，请重新输入", "error");
                                    return false;
                                }

                            }
                            return $(this).form("validate");
                        },
                        success: function (result) {
                            var data = eval('(' + result + ')');
                            if (data.code == 1) {
                                $.messager.alert("提示", "操作成功，请重新登录", "info", function() {
                                    window.location.href='index.jsp';
                                });
                                $("#modifyPwdView").dialog("close");
                            } else if (data.code == "0") {
                                $.messager.alert("提示", data.message, "error");
                            } else {
                                $.messager.alert("提示", "操作失败", "error");
                            }
                        }
                    });
                }
            }, {
                text: "取消",
                iconCls: "icon-cancel",
                handler: function () {
                    $.post("frozen_account",null,function(data){
                        if (data.code == 1) {
                            $.messager.alert("", "您的账户已被冻结，请联系管理员", "info", function() {
                                window.location.href='index.jsp';
                            });
                        } else if (data.code == "0") {
                            $.messager.alert("提示", data.message, "error");
                         } else {
                            $.messager.alert("提示", "操作失败", "error");
                        }
                    });
                }
            }]
        });
    });
    
</script>
</html>


