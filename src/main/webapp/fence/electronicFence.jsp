<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆类型</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="fence/common.js"></script>
<script type="text/javascript" src="fence/electronicFence.js"></script>
<!-- 地图搜索 -->
<script type="text/javascript" src="common/amapSearch.js"></script>
<!-- 高德地图 -->
<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
<script type="text/javascript" src="https://cache.amap.com/lbs/static/addToolbar.js"></script>
<style type="text/css">
.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 100px;
}

.fitem input {
	width: 160px;
}

.fitem a {
	margin-right: 5px;
}
</style>
<script type="text/javascript">
var mapObj;
var lnglatXY;
var marker = new Array();
var windowsArr = new Array();  
var fencePoint = new Array() ;
function init(){
	mapInit();
}
//初始化地图
function mapInit(){
    var opt = {  
        level: 12, //设置地图缩放级别    
        center: new AMap.LngLat(parseFloat("${longitude}"), parseFloat("${latitude}"))//设置地图中心点   
    }
    mapObj = new AMap.Map("iMap", opt);  
	
	 //在地图中添加MouseTool插件
    mapObj.plugin(["AMap.MouseTool"], function() {
        var mouseTool = new AMap.MouseTool(mapObj);
        //鼠标工具插件添加draw事件监听
        AMap.event.addListener(mouseTool, "draw", function callback(e) {
            var eObject = e.obj;//obj属性就是鼠标事件完成所绘制的覆盖物对象。
            //debugger ;
            for(var i = 0 ; i < eObject.G.path.length ; i ++){
            	fencePoint.push(eObject.G.path[i].lng+"#"+eObject.G.path[i].lat) ;
            }
            $("#fencePoint").val(fencePoint);
        });
        mouseTool.measureArea();  //调用鼠标工具的面积量测功能
    });
}  
    
  //构造地点查询类 
 /* function searchAddress() {
 	var address = $("#s-address").val() ;
      mapObj.clearMap(); 
    mapObj.plugin(["AMap.PlaceSearch"], function() {          
        MSearch = new AMap.PlaceSearch({ //构造地点查询类  
            pageSize:10,  
            pageIndex:1 
        });   
        AMap.event.addListener(MSearch, "complete", Search_CallBack);//返回地点查询结果  
        MSearch.search(address); //关键字查询  
    });  
 }

//回调函数  
function Search_CallBack(data) {  
    var poiArr = data.poiList.pois;  
    var resultCount = poiArr.length;  
    for (var i = 0; i < resultCount; i++) {  
         addmarker(i, poiArr[i]);  
    }  
    mapObj.setFitView();  
}  
 
 function addmarker(i, d) {  
	var lngX; 
	var latY;
	var iName;
	var iAddress;
	if(d.location){
		lngX = d.location.getLng();  
		latY = d.location.getLat();  
	}else{
		lngX = d._location.getLng();  
		latY = d._location.getLat(); 
	}
	if(d.name){
		iName = d.name;
	}else{
		iName = d._name;
	}
	if(d.name){
		iAddress = d.address;
	}else{
		iAddress = d._address;
	}
    var markerOption = {  
        map:mapObj,  
        icon:"https://webapi.amap.com/images/" + (i + 1) + ".png",  
        position:new AMap.LngLat(lngX, latY)  
    };  
    var mar = new AMap.Marker(markerOption);            
    marker.push(new AMap.LngLat(lngX, latY));  
  
    var infoWindow = new AMap.InfoWindow({  
        content:"<h3><font color=\"#00a6ac\">" + (i + 1) + ". " + iName + "</font></h3>" ,  
        size:new AMap.Size(300, 0),   
        autoMove:true,    
        offset:new AMap.Pixel(0,-30)  
    });  
    windowsArr.push(infoWindow);   
    var aa = function (e) {infoWindow.open(mapObj, mar.getPosition());};  
    AMap.event.addListener(mar, "click", aa);  
}     */

</script>
</head>
<body onload="init();">

 <table id="electronicFenceGrid" toolbar="#toolbar"></table>
 
 
    <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		围栏名称:<input type="text" name="fenceName" class="easyui-textbox" id="s-fenceName">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		<r:FunctionRole functionRoleId="add_elec_fence">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_elec_fence">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_elec_fence">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" onclick=";">删除</a>
		</r:FunctionRole>
		
   </div>

  <div id="view" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="viewForm" id="viewForm" method="post" >
          <input type="hidden" name='id' id="e-id">
          <input type="hidden" name='fencePoint' id="fencePoint">
           <div class="fitem">
				<label>所属城市:</label>
				<input class="easyui-combobox" name="cityCode" id="e-cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
	            <label>&nbsp;电子围栏名称:</label> <input  name="fenceName" id="e-fenceName" class="easyui-textbox"  />
	            
		   </div> 
		   <div class="fitem">
				<label>电子围栏类型:</label> 
				<input class="easyui-combobox" name="type"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '自定义',
						value: '0'
					},{
						label: '行政区划',
						value: '1'
					}]"
					 id="e-type" >
				<label>&nbsp;电子围栏描述:</label> 
				<input class="easyui-textbox" id="e-describe" name="describe" data-options="multiline:true" style="width:50%;height:40px"></input>
		   </div>
		   <div id="iMap"  style="width:99.9%;height:580px;border:#ccc solid 1px;"></div>
		    <!-- 悬浮 -->
			<div id="searchFrame" style="width:200px; background:#fff; border:1px solid #666;z-index:3;position:absolute; top:110px;right:44px;">
				<div id="searchContent" style="margin: 5px;" align="center">
					<input type="text" name="s-address" class="easyui-textbox" id="s-address" style="height:30px;width: 100px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick="searchAddress();">查询</a>
				</div>
			</div>
       </form>
   </div>

	  <div id="detailView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
		   <div id="detailMap"  style="width:99.9%;height:500px;border:#ccc solid 1px;"></div>
   </div>
   
</body>
</html>