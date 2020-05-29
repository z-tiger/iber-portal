var data;
var mapObj;
var dispatcherArr = new Array();
var markerArr = new Array();
var markers = [];
$(function(){
	var locations = $("#locations").val();
	data = eval(locations);
	mapInit(data);
	
	$('#grid').datagrid({
		width : 'auto',
		height : 'auto',
		fit:true,
		fitColumns: true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'park_equip_conn', 
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'parkName':$("#s-parkName").textbox("getValue"),
			'connectorStatus':$("#connectorStatus").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'parkName',
			title : '所在网点',
			width : $(this).width() * 0.08,
			align : 'center'
		}, 
		{
			field : 'cooperationType',
			title : '网点类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value, rec){
				if(value == "0"){
					return "自有网点";					
				} else {
					return "合作网点";					
				} 
			}
		}, 
		{
			field : 'equipmentCode',
			title : '桩编码',
			width : $(this).width() * 0.08,
			align : 'center'  
		}, 
		{           
			field : 'equipmentType',
			title : '桩类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value, rec){
				if(value == "1"){
					return "直流快充";					
				} else if(value == "2"){
					return "交流慢充";					
				} else if(value == "3"){
					return "交直流混合快充";					
				} 
			}
		}, 
		{
			field : 'parkNo',
			title : '车位号',
			width : $(this).width() * 0.08,
			align : 'center'  
		},{
			field : 'connectorNo',
			title : '枪编号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(1 == val){
					return "A";
				}else if(2 == val){
					return "B";
				}
			}
		},{
			field : 'status',
			title : '枪状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value, rec){
				if(value == "0"){
					return "离网";					
				} else if(value == "1"){
					return "空闲";					
				} else if(value == "3"){
					return "充电中";					
				} else if(value == "4"){
					return "已被预约";					
				} else if(value == "255"){
					return "故障";					
				} else{
					return "&nbsp;&nbsp;";
				}
			}
		},
		{
			field : 'faultCode',
			title : '故障类型',
			width : $(this).width() * 0.08,
			align : 'center' ,
			formatter : function(value, rec){
				if(value == "1"){
					return "单体电池温度过高";			
				} else if(value == "2"){
					return "单体电池电压过压";					
				} else if(value == "4"){
					return "绝缘故障";					
				} else if(value == "8"){
					return "负绝缘故障";					
				} else if(value == "16"){
					return "电池反接";					
				} else if(value == "32"){
					return "急停故障";
				}else if(value == "64"){
					return "BMS通讯故障";
				}else if(value == "128"){
					return "输出过流";
				}else if(value == "256"){
					return "输出过压";
				}else if(value == "512"){
					return "系统无输出";
				}else if(value == "1024"){
					return "交流输入过压";
				}else if(value == "2048"){
					return "交流输入欠压";
				}else if(value == "4096"){
					return "输入缺相";
				}else if(value == "8192"){
					return "车辆连接故障";
				}else if(value == "16384"){
					return "直流表通讯故障";
				}else if(value == "32768"){
					return "读卡器通讯通讯故障";
				}else if(value == "65536"){
					return "触摸屏通讯故障";
				}else if(value == "131072"){
					return "后台通讯故障";
				}else if(value == "262144"){
					return "BMS原件过温";
				}else if(value == "524288"){
					return "系统环境温度";
				}else if(value == "1048576"){
					return "防雷器故障";
				}else if(value == "2097152"){
					return "接触器故障";
				}else if(value == "4194304"){
					return "并机接触器故障";
				}else if(value == "8388608"){
					return "模块无输出";
				}else if(value == "16777216"){
					return "充电模块通讯故障";
				}else if(value == "33554432"){
					return "系统温度传感器故障";
				}else if(value == "67108864"){
					return "枪锁故障";
				}else if(value == "134217728"){
					return "枪温度过高";
				}else if(value == "268435456"){
					return "交流表通讯故障";
				}else if(value == "536870912"){
					return "模块DC断路";
				}else{
					return "无";
				}
				
			}
		},
		{
			field : 'lockStatus',
			title : '地锁状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(value, rec){
				if(value == "1"){
					return "已升起";					
				} else if(value == "2"){
					return "已降下";					
				} else if(value == "3"){
					return "故障";					
				} 
			}
		},{
			field : 'id',
			title : '充电枪id',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'connectorNumber',
			title : '枪数量',
			width : $(this).width() * 0.08,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true,
		onClickRow: function(value,rec){
			var content = "";
			for ( var k = 0; k < markers.length; k++) {
				if (markers[k].G.title == rec.parkName) {
					$.ajax({
						type:"post",
						url:"getAllChargingPileByParkId",
						data:{parkName:rec.parkName,
							  cooperationType:rec.cooperationType,stationId:rec.parkId},
						success:function(result){
							json = eval(result);
							var p0 = json[0].longitude;
							var p1 = json[0].latitude;
							if(p0 == "") { // 未上传gps，地图上不显示
								return ;
							}
							mapObj.setZoomAndCenter(12,new AMap.LngLat(p0,p1));
							//点标记中的文本
							
							for ( var m = 0; m < json.length; m++) {
								content += "<dl>";
					            content += "<div style='float:left;'><img style='height:50px;width:50px;' src='images/monitorCenter/parking.png'></img></div>";
					            if (json[m].statusName == "占用（预约锁定）") {
					            	var myResult = "";
					            	$.ajax({
					    				type:"get",
					    				url:"selectUserByConnectorId",
					    				data:{connectorId:json[m].connectorId,status:'ordered,preOrdered'},
					    				async:false,
					    				success:function(result){
					    					myResult = eval("("+result+")");
					    				}
					    			})
					    			if (myResult && myResult.memberName && myResult.startTime) {
					    				content += "<h1 style='positon: relative;left:20px;font-size:15px;'><div style='height=20px;float:left;margin-left:45px;'>使用人："+myResult.memberName+"</div></h1>";
				    					content += "<h1 style='positon: relative;left:20px;font-size:15px;'><div style='height=20px;'>开始时间："+myResult.startTime+"</div></h1>";
									}
					
								}else if (json[m].statusName == "占用（充电中）"){
									var myResult = "";
									$.ajax({
					    				type:"get",
					    				url:"selectUserByConnectorId",
					    				data:{connectorId:json[m].connectorId,status:'charging'},
					    				async:false,
					    				success:function(result){
					    					myResult = eval("("+result+")");
					    				}
					    			})
					    			if (myResult && myResult.memberName&&myResult.startTime) {
					    				content += "<h1 style='positon: relative;left:20px;font-size:15px;'>使用人："+myResult.memberName+"</h1>";
				    					content += "<h1 style='positon: relative;left:20px;font-size:15px;'>开始时间："+myResult.startTime+"</h1>";
									}
					    			
								}else{
									content += "<span style='positon: relative;left:20px;top:40px;color:#00FF00;font-size:20px;'>"+json[m].statusName+"</span>";
								}
					            
					            content += "<div style='position:relative;left:-40px;top:30px;font-size:15px;'>"+json[m].parkNo+"</div>"
					            content += '<div style="position: relative;top: -1px;left:10px;margin-left: 45px;"><dd class="but">'; 
//					            content += "<a href=\"javascript:ordered('"+json[m].parkName+"','"+json[m].equipmentId+"','"+json[m].parkNo+"','"+json[m].connectorId+"','"+json[m].parkId+"','"+json[m].status+"','"+json[m].cooperationType+"','"+json[m].operatorId+"');\" id='opendoor' class='left'>预约</a>";
//						        content += "<a href=\"javascript:cancelOrdered('"+json[m].status+"','"+json[m].connectorId+"','"+json[m].parkName+"','"+json[m].parkNo+"','"+json[m].parkId+"','"+json[m].operatorId+"');\" id='closedoor' class='right'>取消预约</a>";
						    	content += "<a href=\"javascript:lockUp('"+json[m].equipmentCode+"','"+json[m].connectorNo+"','"+json[m].connectorId+"','"+json[m].connectorNumber+"','"+1+"');\" id='startEngine' class='left'>降下地锁</a>";
						    	content += "<a href=\"javascript:lockUp('"+json[m].equipmentCode+"','"+json[m].connectorNo+"','"+json[m].connectorId+"','"+json[m].connectorNumber+"','"+0+"');\" id='fireOff' class='right'>升起地锁</a>";
						    	content += "<a href=\"javascript:startCharging('"+json[m].connectorCode+"','"+json[m].connectorId+"','"+json[m].status+"','"+json[m].operatorId+"','"+json[m].equipmentCode+"');\" id='order' class='left'>开始充电</a>";
						    	content += "<a href=\"javascript:endCharging('"+json[m].status+"','"+json[m].parkId+"','"+json[m].connectorId+"','"+json[m].operatorId+"');\" id='returnCar' class='right' style='margin-right:0;'>结束充电</a>";
						    	content += "</dd></div>";  
						    	content += "</dl>";
							}
					    	var info = [];
					    	info.push('<div class="bubble-title" style="width:540px;height:80px;margin:0px;">');
					    	info.push("<h7 style='position:relative;left:12px;top:8px;font-size:17px;width:400px;'>"+json[0].parkName+json[0].categoryType+"</h7><br>");
					    	info.push("<h7 style='position:relative;left:12px;top:10px;font-size:17px;width:400px;'>地址："+json[0].parkAddress+"</h7>");
							info.push("</div>");
							info.push('<div class="bubble-butbox">');
							info.push(content);
							info.push("</div></div>");
							new AMap.InfoWindow({
								offset : new AMap.Pixel(0, -23),
								content : info.join("")
							}).open(mapObj, [json[0].longitude,json[0].latitude]);
							
						}
					})
					
				}
			}
		} 
	}); 
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'parkName':$("#s-parkName").textbox("getValue"),
			'connectorStatus':$("#connectorStatus").combobox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		$("#scarMrgForm").form("clear");
	}); 
	
	$("#orderedCharingPile").dialog( {
		width : "400",
		height : "200",
		top : "40",
		buttons : [ {
			text : "预约",
			iconCls : "icon-save",
			handler : function() {
				var phone = $("#phone").val() ;
				if(phone == "null" || phone== null){
					$.messager.alert("提示", "会员手机号不能为空", "info");
					return ;
				}
				var lpn = $("#lpn").val() ;
				if(lpn == "null" || lpn== null){
					$.messager.alert("提示", "车牌号不能为空", "info");
					return ;
				}
				$("#orderedCharingPileForm").form("submit", {
					url : "ordered",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "预约成功", "info");
							$("#orderedCharingPile").dialog("close");
						}else if(result == "noMember"){
							$.messager.alert("提示", "会员不存在", "info");
						}else if(result == 'ordered'){
							$.messager.alert("提示", "该充电枪已经被预约，请预约其他充电枪", "info");
						}else{
						    $.messager.alert("提示", result, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#orderedCharingPile").dialog("close");
			}
		} ] 
	})
	
	
	//启动充电
	$("#startCharging").dialog( {
		width : "400",
		height : "200",
		top : "40",
		buttons : [ {
			text : "启动充电",
			iconCls : "icon-save",
			handler : function() {
				var phone = $("#phone1").val() ;
				if(phone == "null" || phone== null ||phone == ""){
					$.messager.alert("提示", "会员手机号不能为空", "info");
					return ;
				}
				$("#startChargingForm").form("submit", {
					url : "startCharging",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "启动充电成功", "info");
							$("#startCharging").dialog("close");
						}else if(result == "noMember"){
							$.messager.alert("提示", "会员不存在", "info");
						}else if(result == "noOrder"){
							$.messager.alert("提示", "该订单不为预约状态，不可启动充电", "info");
							$("#startCharging").dialog("close");
						}  else{
						    $.messager.alert("提示", result, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#startCharging").dialog("close");
			}
		} ] 
	})
	
	//结束充电
	$("#endCharging").dialog( {
		width : "400",
		height : "200",
		top : "40",
		buttons : [ {
			text : "结束充电",
			iconCls : "icon-save",
			handler : function() {
				var phone = $("#phone").val() ;
				if(phone == "null" || phone== null){
					$.messager.alert("提示", "会员手机号不能为空", "info");
					return ;
				}
				$("#endChargingForm").form("submit", {
					url : "endCharging",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "结束充电成功", "info");
							$("#endCharging").dialog("close");
						}else if(result == "noMember"){
							$.messager.alert("提示", "会员不存在", "info");
						}else if(result == "noCharging"){
							$.messager.alert("提示", "该订单不可结束充电", "info");
							$("#endCharging").dialog("close");
						}else if(result == 'employeeCharging'){
							$.messager.alert("提示", "该充电桩为员工使用，不能在管理平台结束充电", "info");
							$("#endCharging").dialog("close");
						}else{
						    $.messager.alert("提示", result, "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#endCharging").dialog("close");
			}
		} ] 
	})
});

function mapInit(data){
	
	var point = new AMap.LngLat(parseFloat(data[0].longitude), parseFloat(data[0].latitude));
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
	mapObj = new AMap.Map('dispatcherContent', {
		center : point, //地图中心点
		level : 12       
	});
	//getData();
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
       markers = [];
	   for(var i = 0; i<data.length;i++){
	   		var marker;
	   		if (data[i].parkLongitude && data[i].parkLatitude) {
	   			//点标记中的文本
	   			var markerContent = document.createElement("div");
	   		    markerContent.className = "markerContentStyle";
	   		    var markerImg= document.createElement("img");
	   		    markerImg.className="markerlnglat";
	   		    markerImg.src="images/monitorCenter/"+data[i].cooperationType+"-"+data[i].category+"s.png";	
	   		   // markerImg.title=json.lpn ;
	   		    markerContent.appendChild(markerImg);
	   		    
	   		     var markerSpan = document.createElement("span");
	   		    markerSpan.innerHTML = "<div style='width:80px;'>"+data[i].parkName+"</div>";
	   		    markerContent.appendChild(markerSpan); 
	   			marker = new AMap.Marker({
	       			position: [data[i].parkLongitude,data[i].parkLatitude],
	       			title: data[i].parkName,
	       			content:markerContent,
	       			map: mapObj
	       		});
			}
	   		markers.push(marker);
	   		
	   		//预加载点击事件
	   		getAllChargingPileByParkId(data[i].parkName,data[i].cooperationType,data[i].id);
	   	}
	})
}

function getAllChargingPileByParkId(parkName,cooperationType,stationId){
	$.ajax({
		type:"post",
		url:"getAllChargingPileByParkId",
		data:{parkName:parkName,cooperationType:cooperationType,stationId:stationId},
		success:function(result){
			json = eval(result);
			var content = "";
			var timeContent = "";
			var statusContent = "";
			if (json.length>0) {
				for ( var i = 0; i < markers.length; i++) {
					if (markers[i].G.title == json[0].parkName) {
						var p0 = json[0].longitude;
						var p1 = json[0].latitude;
						if(p0 == "") { // 未上传gps，地图上不显示
							return ;
						}
						//点标记中的文本
						for ( var j = 0; j < json.length; j++) {
							content += "<dl>";
				            content += "<div style='float:left;'><img style='height:50px;width:50px;' src='images/monitorCenter/parking.png'></img></div>";
				            if (json[j].statusName == "占用（预约锁定）") {
				            	var myResult = "";
				            	$.ajax({
				    				type:"get",
				    				url:"selectUserByConnectorId",
				    				data:{connectorId:json[j].connectorId,status:'ordered,preOrdered'},
				    				async:false,
				    				success:function(result){
				    					myResult = eval("("+result+")");
				    				}
				    			})
				    			if (null != myResult && null != myResult.memberName && null != myResult.startTime) {
				    				content += "<h1 style='positon: relative;left:20px;font-size:15px;'>使用人："+myResult.memberName+"</h1>";
			    					content += "<h1 style='positon: relative;left:20px;font-size:15px;'>开始时间："+myResult.startTime+"</h1>";
								}
				    			
							}else if (json[j].statusName == "占用（充电中）"){
								var myResult = "";
								$.ajax({
				    				type:"get",
				    				url:"selectUserByConnectorId",
				    				data:{connectorId:json[j].connectorId,status:'charging'},
				    				async:false,
				    				success:function(result){
				    					myResult = eval("("+result+")");
				    				}
				    			})
				    			if (myResult && myResult.memberName && myResult.startTime) {
				    				content += "<h1 style='positon: relative;left:20px;font-size:15px;'>使用人："+myResult.memberName+"</h1>";
			    					content += "<h1 style='positon: relative;left:20px;font-size:15px;'>开始时间："+myResult.startTime+"</h1>";
								}
							}else{
								content += "<span style='positon: relative;left:20px;top:40px;color:#00FF00;font-size:20px;'>"+json[j].statusName+"</span>";
							}
				            content += "<div style='position:relative;left:-40px;top:30px;font-size:15px;'>"+json[j].parkNo+"</div>"
				            content += '<div style="position: relative;top: -1px;left:10px;margin-left: 45px;"><dd class="but">'; 
//				            content += "<a href=\"javascript:ordered('"+json[j].parkName+"','"+json[j].equipmentId+"','"+json[j].parkNo+"','"+json[j].connectorId+"','"+json[j].parkId+"','"+json[j].status+"','"+json[j].cooperationType+"','"+json[j].operatorId+"');\" id='opendoor' class='left'>预约</a>";
//					        content += "<a href=\"javascript:cancelOrdered('"+json[j].status+"','"+json[j].connectorId+"','"+json[j].parkName+"','"+json[j].parkNo+"','"+json[j].parkId+"','"+json[j].cooperationType+");\" id='closedoor' class='right'>取消预约</a>";
					    	content += "<a href=\"javascript:lockUp('"+json[j].equipmentCode+"','"+json[j].connectorNo+"','"+json[j].connectorId+"','"+json[j].connectorNumber+"','"+1+"');\" id='startEngine' class='left'>降下地锁</a>";
					    	content += "<a href=\"javascript:lockUp('"+json[j].equipmentCode+"','"+json[j].connectorNo+"','"+json[j].connectorId+"','"+json[j].connectorNumber+"','"+0+"');\" id='fireOff' class='right'>升起地锁</a>";
					    	content += "<a href=\"javascript:startCharging('"+json[j].connectorCode+"','"+json[j].connectorId+"','"+json[j].status+"','"+json[j].cooperationType+"','"+json[j].equipmentCode+"');\" id='order' class='left'>开始充电</a>";
					    	content += "<a href=\"javascript:endCharging('"+json[j].status+"','"+json[j].parkId+"','"+json[j].connectorId+"','"+json[j].cooperationType+"');\" id='returnCar' class='right' style='margin-right:0;'>结束充电</a>";
					    	content += "</dd></div>";
					    	content += "</dl>";
						}
				    	var info = [];
				    	info.push('<div class="bubble-title" style="width:540px;height:80px;margin:0px;">');
				    	info.push("<h7 style='position:relative;left:12px;top:8px;font-size:17px;width:400px;'>"+json[0].parkName+json[0].categoryType+"</h7><br>");
				    	info.push("<h7 style='position:relative;left:12px;top:10px;font-size:17px;width:400px;'>地址："+json[0].parkAddress+"</h7>");
						info.push("</div>");
						info.push("<div class='bubble-con'>");
						info.push(timeContent) ;
						info.push(statusContent) ;
						info.push("</div>");
						info.push('<div class="bubble-butbox">');
						info.push(content);
						info.push("</div></div>");
						AMap.event.addListener(markers[i], "click", function(d) {
							new AMap.InfoWindow({
								offset : new AMap.Pixel(0, -23),
								content : info.join("")
							}).open(mapObj, [p0,p1]);
						});
						break;
					}
				}
			}
			
			
		}
	})
	
}
//预约充电桩
function ordered(parkName,equipmentId,parkNo,connectorId,parkId,status,cooperationType,operatorId){
	if(operatorId.substring(0,5)!='EAST_'){
	   if(status != '1'){
	        $.messager.alert("消息提示","桩状态为不可预约，请选择其他桩");
	   }else{
	      $("#orderedCharingPileForm").form("clear");
	      $("#equipmentId").val(equipmentId);
	      $("#parkNo").val(parkNo);
	      $("#connectorId").val(connectorId);
	      $("#parkId").val(parkId);
	      $("#parkName").val(parkName);
	      $("#cooperationType").val(cooperationType);
	      $("#operatorId").val(operatorId);
		  $("#orderedCharingPile").dialog("open").dialog("setTitle", "预约充电桩");
	   }
	}else{
		$.ajax({
			type:"post",
			contentType:"application/x-www-form-urlencoded",
			url:"checkPartnerConnectStatus",
			data:{stationId:parkId,connectorId:connectorId},
			success:function(result){
				var json = JSON.parse(result);
				if(json.msg=="succ"){
				      $("#orderedCharingPileForm").form("clear");
				      $("#equipmentId").val(equipmentId);
				      $("#parkNo").val(parkNo);
				      $("#connectorId").val(connectorId);
				      $("#parkId").val(parkId);
				      $("#parkName").val(parkName);
				      $("#cooperationType").val(cooperationType);
				      $("#operatorId").val(operatorId);
					  $("#orderedCharingPile").dialog("open").dialog("setTitle", "预约充电桩");
				}else{
					$.messager.alert("消息提示","操作失败！");
				}
			}
		})
	}
	
}
//取消预约
function cancelOrdered(status,connectorId,parkName,parkNo,parkId,operatorId){
	if(operatorId.substring(0,5)!='EAST_'){
		if(status != '4'){
		      $.messager.alert("消息提示","桩不为预约状态，不可取消预约");
		}else{
			$.messager.confirm("提示","确定要取消预约吗?",function(r){
				if (r) {
					$.ajax({
						type:"post",
						contentType:"application/x-www-form-urlencoded",
						url:"cancelOrdered",
						data:{connectorId:connectorId, parkName:parkName, parkNo:parkNo,operatorId:operatorId},
						success:function(result){
							if(result=="succ"){
								$.messager.alert("消息提示","操作成功！");
							}else{
								$.messager.alert("消息提示","操作失败！");
							}
						}
					})
				}
			})
		}
	}else {
		$.ajax({
			type:"post",
			contentType:"application/x-www-form-urlencoded",
			url:"checkPartnerConnectStatus",
			data:{stationId:parkId,connectorId:connectorId},
			success:function(result){
				var json = JSON.parse(result);
				// succ表示空闲，空闲就不能取消预约
				if(json.status!='4'){
					 $.messager.alert("消息提示","桩不为预约状态，不可取消预约");
				}else{
					$.messager.confirm("提示","确定要取消预约吗?",function(r){
						if (r) {
							$.ajax({
								type:"post",
								contentType:"application/x-www-form-urlencoded",
								url:"cancelOrdered",
								data:{connectorId:connectorId, parkName:parkName, parkNo:parkNo,cooperationType:cooperationType},
								success:function(result){
									if(result=="succ"){
										$.messager.alert("消息提示","操作成功！");
									}else{
										$.messager.alert("消息提示","操作失败！");
									}
								}
							})
						}
					})
					
				}
			}
		})
	}

	
}

//升起地锁
function lockUp(equipmentCode,connectorNo,connectorId,connectorNumber,isUp){
	$.messager.confirm("提示","确定要控制地锁吗?",function(r){
		if (r) {
			$.ajax({
				type:"get",
				url:"lockUp",
				data:{equipmentCode:equipmentCode,connectorNo:connectorNo,connectorId:connectorId,connectorNumber:connectorNumber,isUp:isUp},
				success:function(result){
					if(result=="succ"){
						$.messager.alert("消息提示","操作成功！");
					}else{
						$.messager.alert("消息提示","操作失败！");
					}
				}
			})
		}
	})
}
//启动充电
function startCharging(connectorCode,connectorId,status,operatorId,equipmentCode){
	if(status != '1'){
	      $.messager.alert("消息提示","桩状态为不为空闲状态，不可进行充电");
	}else{
		/*$.ajax({
			type:"post",
			contentType:"application/x-www-form-urlencoded",
			url:"checkPartnerConnectStatus",
			data:{stationId:parkId,connectorId:connectorId},
			success:function(result){
				var json = JSON.parse(result);
				if(json.msg=="succ"){
				      $("#orderedCharingPileForm").form("clear");
				      $("#equipmentId").val(equipmentId);
				      $("#parkNo").val(parkNo);
				      $("#connectorId").val(connectorId);
				      $("#parkId").val(parkId);
				      $("#parkName").val(parkName);
				      $("#cooperationType").val(cooperationType);
				      $("#operatorId").val(operatorId);
					  $("#orderedCharingPile").dialog("open").dialog("setTitle", "预约充电桩");
				}else{
					$.messager.alert("消息提示","操作失败！");
				}
			}
		
			$("#orderedCharingPileForm").form("clear");
	        $("#connectorId").val(connectorId);
	        $("#connectorCode").val(connectorCode);
		    $("#startCharging").dialog("open").dialog("setTitle", "启动充电桩");
		});*/
//        $("#connectorId").val(connectorId);
//        $("#connectorCode").val(connectorCode);
//	    $("#startCharging").dialog("open").dialog("setTitle", "启动充电桩");
//		$.messager.confirm("提示","确定要启动充电吗?",function(r){
//			if (r) {
//				$.ajax({
//					type:"get",
//					url:"startCharging",
//					data:{connectorId:connectorId,connectorCode:connectorCode,operatorId:operatorId,equipmentCode:equipmentCode},
//					success:function(result){
//						if (result == "succ") {
//							$.messager.alert("提示", "启动充电成功", "info");
//							$("#startCharging").dialog("close");
//						}else if(result == "noOrder"){
//							$.messager.alert("提示", "该订单不为预约状态，不可启动充电", "info");
//							$("#startCharging").dialog("close");
//						}else{
//						    $.messager.alert("提示", result, "error");
//						}
//					}
//				})
//			}
//		})
		  $("#startChargingForm").form("clear");
		  $("#connectorCode").val(connectorCode);
		  $("#connectorId1").val(connectorId);
		  $("#startCharging").dialog("open").dialog("setTitle", "启动充电");
	}
}
//结束充电
function endCharging(status,parkId,connectorId,operatorId){
	if(operatorId.substring(0,5)=='EAST_'){
		// 合作网点结束充电,结束充电时实时查合作网点的充电枪状态
		var val = cooperationType;
		$.ajax({
			type:"post",
			contentType:"application/x-www-form-urlencoded",
			url:"checkPartnerConnectStatus",
			data:{stationId:parkId,connectorId:connectorId},
			success:function(result){
				var json = JSON.parse(result);
				if(json.status!='3'){
					 $.messager.alert("消息提示","桩状态不为充电中，不可结束充电");
				}else{
					stopCharging(connectorId,operatorId);
				}
			}
		})
	}else{
		// 自有网点结束充电
		if(status != '3'){
		      $.messager.alert("消息提示","桩状态不为充电中，不可结束充电");
		}else{
			$.messager.confirm("提示","确定要结束充电吗?",function(r){
				if (r) {
					stopCharging(connectorId,operatorId);
				}
			})
		}
	}
}
function stopCharging(connectorId,operatorId) {
	$.ajax({
		type:"get",
		url:"endCharging",
		data:{connectorId:connectorId,operatorId:operatorId},
		success:function(result){
			if (result == "succ") {
				$.messager.alert("提示", "结束充电成功", "info");
				$("#endCharging").dialog("close");
			}else if(result == "noCharging"){
				$.messager.alert("提示", "该订单不可结束充电", "info");
				$("#endCharging").dialog("close");
			}/*else if(result == 'employeeCharging'){
				$.messager.alert("提示", "该充电桩为员工使用，不能在管理平台结束充电", "info");
				$("#endCharging").dialog("close");
			}*/else{
			    $.messager.alert("提示", result, "error");
			}
		}
	})
	
}
function refresh(){
	
	$.ajax({
		type:"get",
		url:"charging_pile_monitoring",
		success:function(result){
			var locations = $("#locations").val();
			data = eval(locations);
			mapInit(data);
		}
	});
	$('#grid').datagrid("load",{
	});
}

