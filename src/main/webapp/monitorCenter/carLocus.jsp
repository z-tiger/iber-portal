<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>宜步出行运营支撑系统</title>
<title>轨迹查询</title>
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
 
 <script language="javascript">  
 
$(function() {
	//查询链接
	$("#btnQuery").bind("click", function() {
		search() ;
	});
	$("#btnStop").bind("click", function() {
		 endRun() ;
	});
	$("#btnStart").bind("click", function() {
		startRun() ;
	});
	
});

var mapObj;  
//初始化地图对象，加载地图  
function mapInit(){  
	
    mapObj = new AMap.Map("iCenter",{  
        zooms:[3,18],  
        //二维地图显示视口  
        view: new AMap.View2D({  
            center:new AMap.LngLat(parseFloat("${longitude}"), parseFloat("${latitude}")),//地图中心点  
            zoom:13 //地图显示的缩放级别  
        }),  
        continuousZoomEnable:false  
    });  
    
}  

function search(){
	mapObj.clearMap() ; //清除地图标记 marker
	var queryDateFrom = $.trim($("input[name='queryDateFrom']").val());
    var queryDateTo = $.trim($("input[name='queryDateTo']").val());
    //var orderId = $("input[name='orderId']").val();
    var orderId = $('#cc').combogrid('getValue')
	AMap.event.addListener(mapObj,"complete",completeEventHandler("${lpn}",queryDateFrom,queryDateTo,orderId));      
}

var moveMarker ;
var lineArr = new Array();  
function completeEventHandler(lpn,queryDateFrom,queryDateTo,orderId){
		lineArr = new Array();  
		 $.post('query_car_run_log_locus?lpn='+encodeURI(lpn)+'&queryDateFrom='+queryDateFrom+'&queryDateTo='+queryDateTo+'&orderId='+orderId, function(data){
		 
		  	  if(data != 'empty'){
		  	  	 carArr=eval(data);
		  	  	if(carArr.length >0){
		  	  		for(var j=0;j<carArr.length;j++){
			  	  	 	if(j == 0){
			  	  	 		moveMarker = new AMap.Marker({  
						        map:mapObj,  
						        icon: new AMap.Icon({
						            image: "images/monitorCenter/carEmpty2.png",
						            size:new AMap.Size(58,30)
						        }),
						        draggable:true, //是否可拖动  
						        position:new AMap.LngLat(carArr[j].longitude,carArr[j].latitude),//基点位置  
						        offset:new AMap.Pixel(0,0), //相对于基点的位置  
						        autoRotation:true ,
						        //content:"<div style=width:200px;><img src='images/monitorCenter/carEmpty.png'/>"+carArr[j].lpn+"当前车速：" + carArr[j].speed+"km/h</div>"
						       content:"<img src='images/monitorCenter/carEmpty2.png'/>"
					    	});
				    		lineArr.push(new AMap.LngLat(carArr[j].longitude,carArr[j].latitude));  
			  	  	 	}else{
			  	  	 		marker = new AMap.Marker({  
						        map:mapObj,  
						        //icon: new AMap.Icon({
						        //    image: "images/monitorCenter/carError.png",
						        //    size:new AMap.Size(58,30)
						       // }),
						        //draggable:true, //是否可拖动  
						        position:new AMap.LngLat(carArr[j].longitude,carArr[j].latitude),//基点位置  
						        offset:new AMap.Pixel(0,0), //相对于基点的位置  
						        autoRotation:true ,
					        //content:"<div style=width:200px;>"+carArr[j].lpn+"当前车速：" + carArr[j].speed+"km/h</div>" 
								content:"" 
					    	});
					    	lineArr.push(new AMap.LngLat(carArr[j].longitude,carArr[j].latitude));  
			  	  	 	}  
	             	}
		             //绘制轨迹  
				    new AMap.Polyline({  
				        map:mapObj,  
				        path:lineArr,  
				  		strokeColor:"red",//线颜色  
				        strokeOpacity:-5,//线透明度  
				        strokeWeight:2,//线宽  
				        strokeStyle:"dashed"//线样式  
				    });  
			    	mapObj.setFitView();  
			    	moveMarker.moveAlong(lineArr,200);	 //开始轨迹回放 
		  	  	}else{
		  	  		alert("该订单无车辆运行记录");
		  	  	}
			  } 
        }); 
}

function startRun(){  //开始播放动画
	moveMarker.moveAlong(lineArr,80);	 //开始轨迹回放
}
function endRun(){   //结束动画播放
	moveMarker.stopMove();  //暂停轨迹回放
}

 $(function(){
  $("#cc").combogrid({
   panelWidth:600,
   idField:"orderId",
   textField:"orderId",
   url:"timeShare_history_order_list_finish?lpn="+encodeURI("${lpn}"),
   columns:[[  
         {field:'orderId',title:'订单号',width:250,align : 'center'},  
         {field:'memberName',title:'会员姓名',width:100,align : 'center'},  
         {field:'orderTime',title:'预约时间',width:130,align : 'center'} 
      ]],
    pagination : true,
	rownumbers : true
  });
 });
 
</script>  
</head>
<body style="width:100%; height:100%; position: absolute;"  onLoad="mapInit();search() ;"> 
	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		<div>
			订单号:
			<!-- <input type="text" id="orderId" name="orderId"  class="easyui-textbox" style="width: 280px;"> -->
			<!-- <input class="easyui-combobox" name="orderId" id="orderId"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'orderId',
	                    textField:'text',
	                    panelHeight:'auto'"> -->
	        <select id="cc" class="easyui-combogrid"  style="width:250px;"></select>
	        <%-- <input class="easyui-combogrid" name="orderId" id="orderId" style="width:25%;height:24px" 
							data-options="
								panelHeight: 'auto',
								panelWidth: 500,
								idField: 'orderId',
								textField: 'orderId',
								url : 'timeShare_history_order_list_finish?lpn=${lpn}',
								pageSize:10,
								columns : [ [ 
								{
									field : 'orderId',
									title : '订单号',
									width : '42%',
									align : 'center'
								},
								{
									field : 'memberName',
									title : '会员姓名',
									width : '20%',
									align : 'center'
								},
								{
									field : 'orderTime',
									title : '预约时间',
									width : '25%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							"> --%>
							
	                    
			时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox"/>
          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datetimebox"/>	
          	 <a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-hammer-screwdriver" plain="true" id="btnStop">暂停</a>
			<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-car-repair" plain="true" id="btnStart">开始</a>
					
		</div>
	</div>
    <div class="iCenter" id="iCenter"style="height: 97%; width:100%;" toolbar="#toolbar"></div>  
</body>  
</html>