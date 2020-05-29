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
</head>
<body  onload="mapInit();" style="width:100%; height:100%; position: absolute;">
<div class="demo_box">  
    <h2 style="font-size: 12px;">目的地：<input name="destination" id="destination"   type="text"><input type="button" onclick="placeSearch()" value="搜索"/></h2> 
</div>    
<div id="iCenter"  style="height: 100%; width:100%;"></div>  
<script type="text/javascript">
var mapObj;  
var marker = new Array();  
var windowsArr = new Array();   
//基本地图加载  
function mapInit() {  
    mapObj = new AMap.Map("iCenter");  
  AMap.event.addListener(mapObj,'click',getLnglat);
}            

//鼠标在地图上点击，获取经纬度坐标
function getLnglat(e){  
   var val=e.lnglat.getLng() + "|" + e.lnglat.getLat();
      var lpn = <%=request.getParameter("lpn")%>;
      if(val=="") alert("请先点击地图，确定位置！");
      else {
          window.opener.window.setPos(val,lpn);window.close();
      }
} 
    
    
 function placeSearch() {
    var MSearch;
    mapObj.plugin(["AMap.PlaceSearch"], function() {       
        MSearch = new AMap.PlaceSearch({ //构造地点查询类
            pageSize:10,
            pageIndex:1,
            city:"深圳" //城市
        });
        var destination=document.getElementById("destination").value;
        AMap.event.addListener(MSearch, "complete", keywordSearch_CallBack);//返回地点查询结果  
        MSearch.search(destination); //关键字查询  
        //关键字查询
        
    });
    
}
//添加marker&infowindow      
function addmarker(i, d) {  
    var lngX = d.location.getLng();  
    var latY = d.location.getLat();  
    var markerOption = {  
        map:mapObj,  
        icon:"http://webapi.amap.com/images/" + (i + 1) + ".png",  
        position:new AMap.LngLat(lngX, latY)  
    };  
    var mar = new AMap.Marker(markerOption);            
    marker.push(new AMap.LngLat(lngX, latY));  
  
    var infoWindow = new AMap.InfoWindow({  
        content:"<h3><font color=\"#00a6ac\">&nbsp;&nbsp;" + (i + 1) + ". " + d.name + "</font></h3>" + TipContents(d.type, d.address, d.tel),  
        size:new AMap.Size(300, 0),   
        autoMove:true,    
        offset:new AMap.Pixel(0,-30)  
    });  
    windowsArr.push(infoWindow);   
    var aa = function (e) {infoWindow.open(mapObj, mar.getPosition());};  
    AMap.event.addListener(mar, "click", getLnglat);  
}  
//回调函数  
function keywordSearch_CallBack(data) {  
    var resultStr = "";  
    var poiArr = data.poiList.pois;  
    var resultCount = poiArr.length;  
    for (var i = 0; i < resultCount; i++) {  
        resultStr += "<div id='divid" + (i + 1) + "' onmouseover='openMarkerTipById1(" + i + ",this)' onmouseout='onmouseout_MarkerStyle(" + (i + 1) + ",this)' style=\"font-size: 12px;cursor:pointer;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\"><table><tr><td><img src=\"http://webapi.amap.com/images/" + (i + 1) + ".png\"></td>" + "<td><h3><font color=\"#00a6ac\">名称: " + poiArr[i].name + "</font></h3>";  
            resultStr += TipContents(poiArr[i].type, poiArr[i].address, poiArr[i].tel) + "</td></tr></table></div>";  
            addmarker(i, poiArr[i]);  
    }  
    mapObj.setFitView();  
    document.getElementById("result").innerHTML = resultStr;  
}  
function TipContents(type, address, tel) {  //窗体内容  
    if (type == "" || type == "undefined" || type == null || type == " undefined" || typeof type == "undefined") {  
        type = "暂无";  
    }  
    if (address == "" || address == "undefined" || address == null || address == " undefined" || typeof address == "undefined") {  
        address = "暂无";  
    }  
    if (tel == "" || tel == "undefined" || tel == null || tel == " undefined" || typeof address == "tel") {  
        tel = "暂无";  
    }  
    var str = "&nbsp;&nbsp;地址：" + address + "<br />&nbsp;&nbsp;电话：" + tel + " <br />&nbsp;&nbsp;类型：" + type;  
    return str;  
}  
function openMarkerTipById1(pointid, thiss) {  //根据id 打开搜索结果点tip  
    thiss.style.background = '#CAE1FF';  
    windowsArr[pointid].open(mapObj, marker[pointid]);  
}  
function onmouseout_MarkerStyle(pointid, thiss) { //鼠标移开后点样式恢复  
    thiss.style.background = "";  
}  
    	   
	function searchLocal(){
	  	var local = new AMap.LocalSearch(map, {
			renderOptions:{map: map}
		});
		var destination=document.getElementById("destination").value;
		local.search(destination); 
	}   
                  //启用滚轮放大缩小



  
</script>
</body>
</html>