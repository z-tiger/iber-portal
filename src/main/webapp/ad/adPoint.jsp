<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>投放点管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<!-- 高德地图 -->
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
	<script type="text/javascript">
	var mapObj;
	var lnglatXY;
	function init(){
		var wd = '${latitude}';
		var jd = '${longitude}';
// 		alert(jd+"===="+wd);
		mapInit(jd,wd);
	}
	
// 	window.setTimeout(show,3000); 
// 	function show() 
// 	{ 
// 		lnglatXY = new AMap.LngLat(114.065947,22.547111);
// 		mapObj.clearMap();
// 	} 
	//初始化地图
	function mapInit(val1,val2){
	    var opt = {  
	        level: 10, //设置地图缩放级别    
	        center: new AMap.LngLat(val1,val2) //设置地图中心点   
	    }
	    mapObj = new AMap.Map("iMap", opt);  
		AMap.event.addListener(mapObj,'click',getLnglat); //点击事件
	}
	function geocoder() {
	    var MGeocoder;
	    //加载地理编码插件
	    mapObj.plugin(["AMap.Geocoder"], function() {        
	        MGeocoder = new AMap.Geocoder({ 
	            radius: 1000,
	            extensions: "all"
	        });
	        //返回地理编码结果 
	        AMap.event.addListener(MGeocoder, "complete", geocoder_CallBack); 
	        //逆地理编码
	        MGeocoder.getAddress(lnglatXY); 
	    });
	    //加点
	    var marker = new AMap.Marker({
	        map:mapObj,
	        icon: new AMap.Icon({
	            image: "images/Map_Marker.png",
	            size:new AMap.Size(24,24)
// 	            imageOffset: new AMap.Pixel(-32, -0)
	        }),
	        position: lnglatXY,
	        offset: new AMap.Pixel(-5,-30)
	    });
	   // mapObj.setFitView();
	}
	//回调函数
	function geocoder_CallBack(data) {
	    var address;
	    //返回地址描述
	    address = data.regeocode.formattedAddress;
	    //返回结果拼接输出
	    //document.getElementById("adpointname").innerHTML = address;
	    $("#adpointadd").textbox("setValue",address);
	}  
	//鼠标点击，获取经纬度坐标  
	function getLnglat(e){    
		mapObj.clearMap();
		var x = e.lnglat.getLng();
		var y = e.lnglat.getLat(); 
		//document.getElementById("coordinate").innerHTML = x + "," + y;
		$("#coordinate").textbox("setValue",x + "," + y);
		
		lnglatXY = new AMap.LngLat(x,y);
		geocoder();
	}
	
	 function placeSearch() {
	    var MSearch;
	    mapObj.plugin(["AMap.PlaceSearch"], function() {       
	        MSearch = new AMap.PlaceSearch({ //构造地点查询类
	            pageSize:10,
	            pageIndex:1,
	            city:'${cityName}' //城市
	        });
	        var destination=document.getElementById("destination").value;
	        AMap.event.addListener(MSearch, "complete", keywordSearch_CallBack);//返回地点查询结果  
	        MSearch.search(destination); //关键字查询  
	    });
	 }
	 //回调函数  
	function keywordSearch_CallBack(data) {  
	    var poiArr = data.poiList.pois;  
	    var resultCount = poiArr.length;  
	    for (var i = 0; i < resultCount; i++) {  
	         addmarker(i, poiArr[i]);  
	    }  
	    mapObj.setFitView();  
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
	} 
	
	$(function() {
		//查询链接
		$(document).keydown(function(event){
		    var adpointname = $("input[name='_adpointname']").val();
		    var adpointadd = $("input[name='_adpointadd']").val();
		    var area_code = $("input[name='_area_code']").val();
		    var queryDateFrom =$("input[name='queryDateFrom']").val();
		    var queryDateTo = $("input[name='queryDateTo']").val();
			if(event.keyCode==13){
				$('#dataGrid').datagrid('load',{
		           adpointname:adpointname,
		           area_code:area_code,
		           adpointadd:adpointadd,
		           queryDateFrom:queryDateFrom,
		           queryDateTo:queryDateTo
	        	});
			}
		});
		//查询链接
		$("#btnQuery").bind("click",function(){
			var adpointname = $("input[name='_adpointname']").val();
			var adpointadd = $("input[name='_adpointadd']").val();
			var area_code = $("input[name='_area_code']").val();
		    var queryDateFrom =$("input[name='queryDateFrom']").val();
		    var queryDateTo = $("input[name='queryDateTo']").val();
		    $('#dataGrid').datagrid('load',{
	    	   adpointname:adpointname,
	    	   adpointadd:adpointadd,
	    	   area_code:area_code,
	           queryDateFrom:queryDateFrom,
	           queryDateTo:queryDateTo
	        });
		});
				
		$('#dataGrid').datagrid( {
			title : '投放点管理',
			width : 'auto',
			height : 'auto',
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			rownumbers : true,
			singleSelect : true,
			url : 'ad_point_list.do',
			pageSize : 100,
			pageList : [100,50,30,10],
			idField : 'id',
			columns : [ [ {
				field : 'ck',
				checkbox:true
			}, {
				field : 'adPointName',
				title : '投放点名称',
				width : $(this).width() * 0.15,
				align : 'center'
			}, {
				field : 'adPointAddress',
				title : '投放点地址',
				width : $(this).width() * 0.25,
				align : 'center'
			}, {
				field : 'gps',
				title : '投放点坐标',
				width : $(this).width() * 0.1,
				align : 'center'
			},
			{
				field : 'limits',
				title : '投放范围(km)',
				width : $(this).width() * 0.05,
				align : 'center'
			},
			{
				field : 'cityName',
				title : '所属地区',
				width : $(this).width() * 0.08,
				align : 'center'
			},
			{
				field : 'createTime',
				title : '创建时间',
				width : $(this).width() * 0.1,
				align : 'center'
			}] ],
			pagination : true,
			rownumbers : true
		});
		
		
		//添加链接
		$("#btnAdd").bind("click",function(){
		 	clearForm();
	 		$("#addView").dialog("open").dialog("setTitle", "添加投放点");
		});
		
		//编辑链接
		$("#btnEdit").bind("click",function(){
	      	var selectedRow = $("#dataGrid").datagrid("getSelections");
	   		if(selectedRow.length <= 0){
				$.messager.alert("提示", "请选择要编辑的投放点", "error");
	   		}else{
			   	var JsonData = selectedRow[0];
				$("#id").val(JsonData.id);
				$("#adpointname").textbox("setValue",JsonData.adPointName);
				$("#limits").numberspinner("setValue",JsonData.limits);
				$("#area_code").combobox("setValue",JsonData.cityCode);
				$("#adpointadd").textbox("setValue",JsonData.adPointAddress);
				$("#coordinate").textbox("setValue",JsonData.gps);
				var jwd = JsonData.gps;
				mapInit(jwd.split(",")[0],jwd.split(",")[1]);
				lnglatXY = new AMap.LngLat(jwd.split(",")[0],jwd.split(",")[1]);
				geocoder();
				$("#addView").dialog("open").dialog("setTitle", "编辑投放点信息");
	  		}	     
		});
		
		//构造对话框
		$("#addView").dialog( {
			width : "500",
			height : "500",
			top : "30",
			buttons : [ {
				text : "保存",
				iconCls : "icon-save",
				handler : function() {
					$("#addForm").form("submit", {
						url : "ad_point_saveOrUpdate.do",
						onSubmit : function() {
							$.messager.progress({
			                    text:"正在加载，请稍等！"
			                });
			                var flag = $(this).form("validate");
			                if(!flag){
			                	$.messager.progress('close'); 
			                }
							return flag;
						},
						success : function(result) {
							$.messager.progress('close'); 
							if (result == "success") {
								$.messager.alert("提示", "保存成功", "info");
							    $("#dataGrid").datagrid("reload");
								$("#addView").dialog("close");
							}else{
								$.messager.alert("提示", "保存失败", "info");
							    $("#dataGrid").datagrid("reload");
								$("#addView").dialog("close");
							} 
						}
					});
					}
				}, {
					text : "取消",
					iconCls : "icon-cancel",
					handler : function() {
						$("#addView").dialog("close");
				}
			}]
		});
		
		
		//删除操作
		$("#btnRemove").bind("click",function(){
	      		var selectedRow = $("#dataGrid").datagrid("getSelections");
	   		if(selectedRow.length <= 0){
				$.messager.alert("提示", "请选择要删除的投放点", "error");
	   		}else{
				var JsonData = selectedRow[0];
				$.messager.confirm("提示","确定要删除吗?",function(r){
		      		if(r){
						$.post("ad_point_del.do",{"id":JsonData.id},function(data){
							if(data=="success"){
								//$.messager.alert("提示", "删除成功", "info");
							    $("#dataGrid").datagrid("reload");
							}else{
								$.messager.alert("提示", "删除失败", "info");
							}
						},"text");
					}
				});
	  		}	     
		});
		
			//清空
		$("#clearQuery").bind("click",function(){
			clearQueryForm();
		});
		
	});
	function clearForm(){
		$('#addForm').form('clear');
	}
	function clearQueryForm(){
		$('#queryForm').form('clear');
	}
	</script> 
</head>

	<body onload="init();">
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
        	所属地区:<input class="easyui-combobox" name="_area_code" id="_area_code" style="width: 80px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			投放点名称:<input id="_adpointname"  name="_adpointname" class="easyui-textbox" style="width: 120px;" />
			投放点地址:<input id="_adpointadd"  name="_adpointadd" class="easyui-textbox" style="width: 120px;" />
       		时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width: 100px;" />
          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width: 100px;" />
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
           
            <r:FunctionRole functionRoleId="add_put_position">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="update_put_position">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_put_position">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            
         </div> 
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 450px;height: 450px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 10px;">
					<tr>
		    			<td>投放点名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="adpointname" name="adpointname" style="width:100%;height:24px" data-options="required:true,missingMessage:'投放点名称不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>所属地区:</td>
		    			<td>
		    			<input class="easyui-combobox" name="area_code" id="area_code" style="width:100%;height:24px" 
						data-options="url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>投放范围（公里）:</td>
		    			<td>
		    			<input class="easyui-numberspinner"  id="limits" name="limits" style="width:100%;height:24px" data-options="required:true,min:0,max:1000,missingMessage:'投放点范围不能为空'">
		    			</td>
		    		</tr>
					<tr>
		    			<td>投放点地址:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="adpointadd" name="adpointadd" style="width:100%;height:24px" data-options="required:true,missingMessage:'投放点地址不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>投放点坐标:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="coordinate" name="coordinate" style="width:100%;height:24px" data-options="required:true,missingMessage:'投放点坐标不能为空'">
		    			</td>
		    		</tr>
		    		
		    	</table>
		    	<div id="iMap"  style="width:100%;height:190px;border:#ccc solid 0px;" ></div>
		    	<h2 style="font-size: 12px;">目的地：<input name="destination" id="destination"   type="text"><input type="button" onclick="placeSearch()" value="搜索"/></h2> 
			</form>
		 </div>
	</body>
</html>