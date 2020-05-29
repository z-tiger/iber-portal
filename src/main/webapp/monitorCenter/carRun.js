$(function(){
	
	$('#grid').datagrid({
		width : 'auto',
		height : 'auto',
		fit:true,
		fitColumns: true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'car_mrg_run_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'parkName':$("#s-parkName").textbox("getValue")
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
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
			}
			
		},{
			field : 'carRunStatus',
			title : '车辆状态',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "empty" || val == "" || val == null || val == "null" || val == "NULL") {return "空闲中"}
				else if(val == "repair") {return "维修中"}
				else if(val == "maintain") {return "维护中"}
				else if(val == "charging"){return "补电中"}
				else {
					if(rec.preOffline =="1"){
						return "运营中(<font color=red>预下线</font>)"
					}else{
						return "运营中"
					}
					}
			}
		}, {
			field : 'restBattery',
			title : '剩余电量',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val < 30) {
					if(rec.batStatus == 1){
						return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_smaill.png'/> "
							+ val+"（<font color=red>充电中</font>）";	
					}else{
						return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_smaill.png'/> "
						+ val+"（未充电）";	
					}
				} else if (30 <= val && val <= 80) {
					if(rec.batStatus == 1){
						return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_medium.png'/> "
							+ val+"（<font color=red>充电中</font>）";	
					}else{
						return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_medium.png'/> "
							+ val+"（未充电）";	
					}
				} else if (val > 80) {
					if(rec.batStatus == 1){
						return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_big.png'/> "
							+ val+"（<font color=red>充电中</font>）";	
					}else{
						return "<img width='20' height='10' src='images/monitorCenter/electric_quantity_big.png'/> "
							+ val+"（未充电）";	
					}
				}
			}
		}, {
			field : 'cpuTemperature',
			title : '车载CPU温度',
			width : $(this).width() * 0.06,
			align : 'center',
			styler : function(value,row) {
				if(value<70){
					return "color:green";
				}else {
					return "color:red";
				}
			},
			formatter:function(val,rec){
				if(val==null || val==''){
					return "";
				}else{
					return val+"°C";
				}
			}
		}, {
			field : 'smallBatteryChargeStatus',
			title : '小电瓶充电状态',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "0"){
					return "未充电";
				}else if(val == "1"){
					return "正在充电";
				}else{
					return val;
				}
			}
		}, {
			field : 'smallBatteryVoltage',
			title : '小电瓶电压',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val < 100) {
					return "<font color=red>"+parseFloat(val/10)+"V</font>";
				}else{
					return parseFloat(val/10)+"V";
				}
			}
		},{
			field : 'parkName',
			title : '所在网点',
			align : 'center'
		}
		/*,{
			field : 'deviceStatus',
			title : '设备状态',
			width : $(this).width() * 0.08,
			align : 'center'
		}*/
		,{
			field : 'getuiStatus',
			title : '个推状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				var result = "";
				if(val == "Offline") {
					return "<font color=red>离线</font>";
				}else if(val == "Online"){
					return "在线";
				}
				return result;
			}
		}
		,{
			field : 'bluetoothNo',
			title : '设备蓝牙地址',
			width : $(this).width() * 0.08,
			align : 'center'  
		}
		,{
			field : 'versionName',
			title : '设备软件版本号',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'updateTime',
			title : '设备上次上报时间',
			width : $(this).width() * 0.08,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true,
		onClickRow: function(value,rec)
		{   $("#carRunInfoOper").dialog("close");
			for(var i = 0 ; i < carArr.length ; i ++) {
				if(carArr[i].lpn == rec.lpn){
					var json = carArr[i] ;
					if(json.longitude == "") {
						if (json.lpn.indexOf("•") < 0) {
							$.messager.alert("提示", "车辆"+json.lpn.substring(0,2) +"•"+json.lpn.substring(2) + "暂无上线", "info");
						}else {
							$.messager.alert("提示", "车辆"+json.lpn + "暂无上线", "info");
						}
						return ;
					}
					mapObj.setZoomAndCenter(12,new AMap.LngLat(json.longitude,json.latitude));
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
		            	 statusContext = "<h1>电量："+json.restBattery+"%"+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+"车载CPU温度："+json.cpuTemperature+"°C</h1>";
				    } else if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
				    		statusName = "运营中" ;
				    		$.ajax({  
					            type : "post",  
					             url : "car_run_order_info",  
					             data : "lpn=" + encodeURI(json.lpn),  
					             async : false,  
					             success : function(data){
					            	 if(data != "fail") {
					            		 var carOrder = $.parseJSON( data );
							    			if(json.status == 'ordered'){
								            	timeContext = "<h1>预约时间："+carOrder.orderTime+"</h1>" ;
								            }else{
								            	timeContext = "<h1>开始时间："+carOrder.beginTime+"</h1>" ;
								            }
							    			if (carOrder.userType == '1') {
							    				statusContext = "<h1>使用人："+carOrder.name+"(员工使用)"+"</h1>" ;
											}else if (carOrder.userType == '0') {
                                              var isEnterpriseUseCarStatus=carOrder.isEnterpriseUseCar=="false"?"个人约车":"企业约车";
												statusContext = "<h1>使用人："+carOrder.name+"&nbsp;&nbsp;("+isEnterpriseUseCarStatus+")</h1>" ;
											}else if (carOrder.userType == '2') {
                                                statusContext = "<h1>使用人："+carOrder.name+"(日租订单)"+"</h1>" ;
                                            }
							    			
					            	 }
					             }  
					        });
				    	
				    	/*$.post("car_run_order_info?lpn="+encodeURI(json.lpn), function(data){
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
					    });*/
				    	
				    }else if(json.status == 'repair'){
				    	statusName = "维修中" ;
				    		$.ajax({  
					            type : "post",  
					             url : "car_repair_info",  
					             data : "status=1&lpn=" + encodeURI(json.lpn),  
					             async : false,   //采用同步
					             success : function(data){  
					            	 if(data != "fail") {
					            		 var carOrder = $.parseJSON( data ); 
								          //获取车辆最后使用时间
								          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
								          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
					            	 }	 
					             }  
					        });
				    	
				    	/*$.post("car_repair_info?status=1&lpn="+encodeURI(json.lpn), function(data){
			    		  	  var carOrder = $.parseJSON( data ); 
					          //获取车辆最后使用时间
					          timeContext = "<div>开始时间："+carOrder.startTime+"</div>" ;
					          statusContext = "<div>处理人："+carOrder.responsiblePerson+"</div>" ;
					    });*/
				    	
				    }else if(json.status == 'maintain'){
				    	statusName = "维护中" ;
				    	/*$.post("car_repair_info?status=2&lpn="+encodeURI(json.lpn), function(data){
			    		  	  var carOrder = $.parseJSON( data ); 
					          //获取车辆最后使用时间
					          timeContext = "<div>开始时间："+carOrder.startTime+"</div>" ;
					          statusContext = "<div>处理人："+carOrder.responsiblePerson+"</div>" ;
					    });*/
				    	$.ajax({  
				            type : "post",  
				             url : "car_repair_info",  
				             data : "status=2&lpn=" + encodeURI(json.lpn),  
				             async : false,   //采用同步
				             success : function(data){  
				            	 var carOrder = $.parseJSON( data ); 
						          //获取车辆最后使用时间
						          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
						          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
				             }  
				        });
				    }else if(json.status == 'charging'){
				    	statusName = "补电中" ;
				    	$.ajax({  
				            type : "post",  
				             url : "car_repair_info",  
				             data : "status=3&lpn=" + encodeURI(json.lpn),  
				             async : false,   //采用同步
				             success : function(data){  
				            	 var carOrder = $.parseJSON( data ); 
						          //获取车辆最后使用时间
						          timeContext = "<h1>开始时间："+carOrder.startTime+"</h1>" ;
						          statusContext = "<h1>处理人："+carOrder.responsiblePerson+"</h1>" ;
				             }  
				        });
				    }
		         
						var content = "<div class='bubble-butbox'><dl>";
			            content += "<dt><a class=myButton href=\"javascript:queryCarRunInfo('"+json.lpn+"');\" >运行信息</a></dt>"; 
			            content += " <dd class='but' style='width: 500px'>";
			            content += "<a href=\"javascript:openDoor('"+json.lpn+"');\" id='opendoor' class='left'>开门</a>";
			            content += "<a href=\"javascript:closeDoor('"+json.lpn+"');\" id='closedoor' class='right'>关门</a>";
			            content += "<a href=\"javascript:startEngine('"+json.lpn+"');\"  id='startEngine' class='left'>启动</a>";
			            content += "<a href=\"javascript:fireOff('"+json.lpn+"');\" class='right'>熄火</a>";
			            content += "<a href=\"javascript:bookCar('"+json.lpn+"','"+json.status+"');\" class='left'>预约</a>";
			            content += "<a href=\"javascript:returnCar('"+json.lpn+"','"+json.status+"');\" class='right' style='margin-right:0;'>还车</a>";
                    	content += "<a href=\"javascript:longRentReturnCar('"+json.lpn+"','"+json.status+"');\" id='longRentReturnCar' class='right'>日租还车</a>";
			            content += " </dd>";
			            content += " <dd class='cut'>";
//			            content += "<a href=\"javascript:navigate('"+json.lpn+"');\"><img src='images/newLogin/bubble-Direction.png'>导航</a>";
//			            content += " <a href=\"javascript:cancelnavigate('"+json.lpn+"');\"><img src='images/newLogin/bubble-Target.png' >取消导航</a>";
			            content += " <a href=\"javascript:carLocus('"+json.lpn+"');\"><img src='images/newLogin/bubble-avtivity.png' >轨迹</a>";
			            content += "<a href=\"javascript:catchedPhoto('"+json.lpn+"');\"><img src='images/newLogin/bubble-photo.png' >抓拍</a>";
			            /*content += " <a href=\"javascript:call('"+json.lpn+"');\" style='margin-right:0'><img src='images/newLogin/bubble-Headphone.png' >呼叫</a>";*/
			            content += "</dd>";
			            content += " </dl> </div>";
					
			    	
			    	/*var content = "<div style='width:550px;'><div>" +
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
	                 "<input type='button' id='call' value='呼叫' onclick=\"javascript:call('"+json.lpn+"');\"></div>";*/
	                 
					var info = [];
					if(statusName=="运营中"){
						var preOfflineStatus = "<font color=red style='font-size:2px;line-height:20px;'>(预下线)</font>";
						//<b>"+statusName +" "  + json.lpn +"<div>速度" +json.speed+"km/h</b>
						//info.push("<div>速度：" +json.speed+"km/h<span style='float:left;width:400px;'>"+statusName+" "+json.lpn+"</span></div></div>");
						if (json.lpn.indexOf("•") < 0) {
							if(json.preOffline=='1'){
								info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn.substring(0,2)+"•"+json.lpn.substring(2) +"</span><em>"+statusName+""+preOfflineStatus+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
							}else {
								info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn.substring(0,2)+"•"+json.lpn.substring(2) +"</span><em>"+statusName+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
							}
						} else {
							if(json.preOffline=='1'){
								info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+""+preOfflineStatus+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
							}else {
								info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em><span class='bubble-speed'>速度："+json.speed+"km/h</span></h1>");
							}
						}
						
					}else if(statusName == '充电中'){
						//info.push("<div>"+statusName +" "  + json.lpn +"</div>");
						if (json.lpn.indexOf("•") < 0) {
							info.push("<div class='bubble' style='width: 620px'><div class='bubble-chargingtitle'><h1><span>"+ "<span>&nbsp;</span>"+json.lpn.substring(0,2)+"•"+json.lpn.substring(2) +"</span><em>"+statusName+"</em></h1>");
						} else{
							info.push("<div class='bubble' style='width: 620px'><div class='bubble-chargingtitle'><h1><span>"+ "<span>&nbsp;</span>"+json.lpn +"</span><em>"+statusName+"</em></h1>");
						}
						
					}else {
						if (json.lpn.indexOf("•") < 0) {
							info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn.substring(0,2)+"•"+json.lpn.substring(2) +"</span><em>"+statusName+"</em></h1>");
						} else{
							info.push("<div class='bubble' style='width: 620px'><div class='bubble-title'><h1><span>"+ json.lpn +"</span><em>"+statusName+"</em></h1>");
						}
					}
					//info.push("<hr style='height:1px;border:none;border-top:1px dashed #CCCCCC;' /></div>");
					//info.push("<div>地点："+json.address+"</div>");
					if (statusName != '充电中') {
						info.push("<h1>地址："+json.address+"</h1>");
						if (json.preOffline == '1') {
							info.push("<h1><font color=red>预下线原因: "+json.preofflineReason+"</font></h1></div>");
						}
						else{
							info.push("</div>");
						}
					}
					if(json.status == 'ordered' || json.status == 'useCar' || json.status == 'return'){
						//获取导航地址
						$.ajax({  
				            type : "post",  
				             url : "car_navigate_info",  
				             data : "orderId=" +json.orderId,  
				             async : false,   //采用同步
				             success : function(data){  
				            	 if(data != "fail") {
								     	var carNavigate = $.parseJSON( data ); 
								     	info.push("<h1>导航地址："+carNavigate.address+"</h1>");
								 }
				             }  
				        });
						/*$.post("car_navigate_info?orderId="+json.orderId, function(data){
						     if(data != "fail") {
						     	var carNavigate = $.parseJSON( data ); 
						     	info.push("<div>导航地址："+carNavigate.address+"</div>");
						     }
						});*/
					}
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
		} 
	}); 
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'lpn':$("#s-lpn").textbox("getValue"),
			'parkName':$("#s-parkName").textbox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		$("#scarMrgForm").form("clear");
	}); 
});
