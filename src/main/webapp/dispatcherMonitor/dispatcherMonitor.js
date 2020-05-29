var data;
var mapObj;
var dispatcherArr = new Array();
var markerArr = new Array();
var arr = new Array();

$(function(){
		var locations = $("#locations").val();
		data = eval(locations);
		mapInit(data);
		$("#btnQuery").bind("click",function(){
		    $("#dataGrid").datagrid("load",{
				'dispatcherName':$("#_dispatcherName").textbox("getValue"),
				'wstatus':$("#w-status").combobox("getValue"),
				'empType':$("#empType").combobox("getValue"),
				'griddingId':$("#griddingName").combobox("getValue")
		    });
		});
		$("#btnClearParam").bind("click",function(){
			$("#scarMrgForm").form("clear");
		}); 
		$('#dataGrid').datagrid( {
			width : 'auto',
			height : 'auto',
			fit : true,
			fitColumns : true,
			nowrap : true,
			striped : true,
			collapsible : true,
			rownumbers : true,
			singleSelect : true,
			url : 'employee_working_page',
			pageSize : 100,
			pageList : [100,50,30,10],
			idField : 'id',
			columns : [ [{
				field : 'name',
				title : '姓名',
				width : $(this).width() * 0.15,
				align : 'center',
			} , {
				field : 'cityName',
				title : '所属城市',
				width : $(this).width() * 0.2,
				align : 'center'
			}, 
			{
				field : 'gridName',
				title : '所属网格',
				width : $(this).width() * 0.2,
				align : 'center',
				formatter : function(val){
					if (val == '') {
						return "无";
					}else{
						return val;
					}
				}
			},
			{
				field : 'type',   
				title : '类型',
				width : $(this).width() * 0.2,
				align : 'center',
				formatter : function(val){
					if (val == '1') {
						return "调度员";
					}else if (val == '2') {
						return "救援员";
					}else if (val == "3") {
						return "维保员";
					}else if (val == ''){
						return "城市管理员";
					}else{
						return "";
					}
				}
			},
			{
				field : 'identifyLabel',   
				title : '身份',
				width : $(this).width() * 0.15,
				align : 'center',
				formatter : function(val,rec){
					if (val == '2'&&rec.type=='') {
						return "无";
					}else if (val == '1'&&rec.type=='1') {
						return "调度管理员";
					}else if (val == "1"&&rec.type=='2') {
						return "救援主管";
					}else if(val == "1"&&rec.type=='3'){
						return "维保主管";
					}else if(val == '0'&&rec.type=='1'){
						return "调度员";
					}else if(val == '0'&&rec.type=='2'){
						return "救援员";
					}else if(val == '0'&&rec.type=='3'){
						return "维保员";
					}else{
						return "";
					}
				}
			},
			{  
				field : 'status',   
				title : '状态',
				width : $(this).width() * 0.1,
				align : 'center',
				formatter : function(val){
					if (val == 'working'||val == 'ordered'||val == 'useCar') {
						return "上班";
					}else {
						return "下班";
					}
				}
			},
			{
				field : 'phone',
				title : '手机号',
				width : $(this).width() * 0.2,
				align : 'center',
			},{
				field : 'currentTask',
				title : '当前任务数',
				width : $(this).width() * 0.2,
				align : 'center',
			},
			{
				field : 'todayCompleteTask',
				title : '今日完成数',
				width : $(this).width() * 0.2,
				align : 'center',
			},
			{
				field : 'processTask',
				title : '正在执行',
				width : $(this).width() * 0.2,
				align : 'center',
			},
			{
				field : 'totalCompleteTask',
				title : '累计完成',
				width : $(this).width() * 0.2,
				align : 'center',
			},{
				field : 'remark',
				title : '备注',
				width : $(this).width() * 0.2,
				align : 'center'
			}
			] ],
			pagination : true,
			rownumbers : true,
			onClickRow: function(value,rec){
				for ( var i = 0; i < dispatcherArr.length; i++) {
					if (dispatcherArr[i].phone == rec.phone) {
						var useCarLpn = dispatcherArr[i].lpn;
						arr[0] = dispatcherArr[i].id;
						arr[1] = i;
						mapObj.setZoomAndCenter(12,new AMap.LngLat(dispatcherArr[i].longitude,dispatcherArr[i].latitude));
						var photoDiv = "<div style='float:left;'><img style='height:100px;width:100px;' src="+dispatcherArr[i].photoUrl+"></img></div>"
						var buttonContext = "<h1></h1><input class='myButton' style='position: relative;top: 100px;margin-left:-100px;width:100px;weight:30px;' type='button' value='指派任务' onClick='assginTask("+dispatcherArr[i].id+")'>"
						var returnCarContext = "<h1></h1><input class='myButton' style='position: relative;top: 100px;margin-left:-100px;width:100px;text-align:middle;' type='button' value='还车' onClick='returnCar("+arr[0]+','+arr[1]+")'>";
						var statusContext = "";
						if(useCarLpn == "" ){
							statusContext = "<h1 style='margin-left:120px;margin-top:-70px;'>"+dispatcherArr[i].name+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+dispatcherArr[i].type+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+dispatcherArr[i].status+"</h1>"
						}else{
							statusContext = "<h1 style='margin-left:120px;margin-top:-70px;'>"+dispatcherArr[i].name+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+dispatcherArr[i].type+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+dispatcherArr[i].status+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>用车："+dispatcherArr[i].lpn+"</h1>"
						}			
						var emptyContext = "<br>"
						var taskContext = "<h1 style='margin-left:120px;margin-top:-3px;'>当前任务："+dispatcherArr[i].currentTask+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>正在执行："+dispatcherArr[i].processTask+"</h1>"
						var emptyBr = "<br>"
						var totalContext = "<h1 style='margin-left:120px;margin-top:4px;'>今日完成："+dispatcherArr[i].todayCompleteTask+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>累计完成："+dispatcherArr[i].totalCompleteTask+"</h1>"
						var addressContext = "<br><h1 style='margin-left:120px;margin-top:-10px;'></h1><span style='position:relative;top:5px;left:120px;font-size:16px;'>地址："+dispatcherArr[i].address+"</span>"
						var info = [];	
						info.push("<div style='width:520px;' class='bubble'><div class='bubble-title' style='background:#5599cc;'><h5><span>员工详情</span></h5></div>");
						info.push(' <div class="bubble-con" style="height:180px;background:#fff;">');
						info.push(photoDiv);
						info.push(buttonContext);
						info.push(returnCarContext);
						info.push(statusContext) ;
						info.push(emptyContext);
						info.push(taskContext);
						info.push(emptyBr);
						info.push(totalContext);
						info.push(addressContext);
						info.push("</div>");
						info.push("</div>");
						if (markerArr[i]) {
							new AMap.InfoWindow({
								offset : new AMap.Pixel(0, -23),
								content : info.join("")
							}).open(mapObj, markerArr[i].getPosition());
						}
						
					}	
				}
			}
		}); 
	
		//构造对话框
		$("#editView").dialog( {
			width : "400",
			height : "400",
			top : "80",
			buttons : [ {
				text : "保存",
				iconCls : "icon-save",
				handler : function() {
					$("#editViewForm").form("submit", {
						url : "saveOrUpdateTask",
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
								$.messager.alert("提示", "指派成功", "info");
							    $("#dataGrid").datagrid("reload");
							    $("#editViewForm").form("clear");
								$("#editView").dialog("close");
								var locations = $("#locations").val();
								data = eval(locations);
								mapInit(data);
							}else if(result == "fail"){
								$.messager.alert("提示", "指派失败", "info");
							    $("#dataGrid").datagrid("reload");
							    $("#editViewForm").form("clear");
								$("#editView").dialog("close");
							}else if(result == "existing"){
								$.messager.alert("提示", "该车辆已存在相同的任务，请勿重复创建", "info");
							    $("#dataGrid").datagrid("reload");
							    $("#editViewForm").form("clear");
								$("#editView").dialog("close");
							}
						}
					});
					}
				}, {
					text : "取消",
					iconCls : "icon-cancel",
					handler : function() {
						$("#editView").dialog("close");
				}
			}]
		});
		//还车-强制还车
		$("#returnCarOper").dialog( {
			width : "400",
			height : "300",
			top : "40",
			buttons : [ {
				text : "还车",
				iconCls : "icon-save",
				handler : function() {
					$("#returnCarForm").form("submit", {
						url : "return_employee_car",
						onSubmit : function() {
							return $(this).form("validate");
						},
						success : function(result) {
							if (result == 'returnFail') {
								$.messager.alert("提示", "该员工不是用车状态", "info");
							}else if (result == 'succ') {
								$.messager.alert("提示", "还车成功", "info");
								$("#parkId").combobox("clear");
								refresh();
								document.getElementById("ss").style.display="none";
								$("#status").combobox({required:false});
								$("#status").combobox({editable:false});
					         	$("#reason").textbox({required:false});
								$("#status").combobox("clear");
						        $("#reason").textbox("clear");
								$("#returnCarOper").dialog("close");
							}else if(result == 'noReason'){
								$.messager.alert("提示","不能有特殊字符","error");
							}else {
								$.messager.alert("提示", result, "info");
							}
						}
					});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#returnCarOper").dialog("close");
					document.getElementById("ss").style.display="none";
				}
			} ]
		});
})

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

function addMarkerDispatcher(data,index){
	var markers = [];
	var marker;
	if (data.longitude && data.latitude) {
		//点标记中的文本
		var markerContent = document.createElement("div");
		markerContent.className = "markerContentStyle";
		var markerImg= document.createElement("img");
		markerImg.className="markerlnglat";
		var photoDiv = "<div style='float:left;'><img style='height:100px;width:100px;' src="+data.photoUrl+"></img></div>";
		var buttonContext = "<h1></h1><input class='myButton' style='position: relative;top: 100px;margin-left:-100px;width:100px;weight:30px;' type='button' value='指派任务' onClick='assginTask("+data.id+")'>";
		var returnCarContext = "<h1></h1><input class='myButton' style='position: relative;top: 100px;margin-left:-100px;width:100px;text-align:middle;' type='button' value='还车' onClick='returnCar("+data.id+","+index+")'>";
		var lpn = data.lpn;
		var statusContext = "";
		if(lpn==""){
			statusContext = "<h1 style='margin-left:120px;margin-top:-70px;'>"+data.name+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+data.type+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+data.status+"</h1>";
		}else {
			statusContext = "<h1 style='margin-left:120px;margin-top:-70px;'>"+data.name+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+data.type+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"+data.status+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>用车："+data.lpn+"</h1>";
		}
		var emptyContext = "<br>";
		var taskContext = "<h1 style='margin-left:120px;margin-top:-3px;'>当前任务："+data.currentTask+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>正在执行："+data.processTask+"</h1>";
		var emptyBr = "<br>";
		var totalContext = "<h1 style='margin-left:120px;margin-top:4px;'>今日完成："+data.todayCompleteTask+"<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>累计完成："+data.totalCompleteTask+"</h1>";
		var addressContext = "<br><h1 style='margin-left:120px;margin-top:-10px;'></h1><span style='position:relative;top:5px;left:120px;font-size:16px;'>地址："+data.address+"</span>";
		markerImg.style.width = "40px";
		markerImg.style.height = "40px";
		markerImg.src= data.statusImg;	
		markerContent.appendChild(markerImg);
		var markerSpan = document.createElement("span");
		markerSpan.innerHTML = "<div style='width:80px;'>"+data.name+"</div>";
		markerContent.appendChild(markerSpan); 
		marker = new AMap.Marker({
    		position: [data.longitude,data.latitude],
    		title: data.name,
    		content:markerContent,
    		map: mapObj
    	});
		var info = [];	
		info.push("<div style='width:520px;' class='bubble'><div class='bubble-title' style='background:#5599cc;'><h5><span>员工详情</span></h5></div>");
		info.push(' <div class="bubble-con" style="height:180px;background:#fff;">');
		info.push(photoDiv);
		info.push(buttonContext);
		info.push(returnCarContext);
		info.push(statusContext) ;
		info.push(emptyContext);
		info.push(taskContext);
		info.push(emptyBr);
		info.push(totalContext);
		info.push(addressContext);
		info.push("</div>");
		info.push("</div>");
		AMap.event.addListener(marker, "click", function(e) {
			new AMap.InfoWindow({
				offset : new AMap.Pixel(0, -23),
				content : info.join("")
			}).open(mapObj, marker.getPosition());
		});
		
	}
	markers.push(marker);
	return marker;
}

function refresh() {
	var locations = $("#locations").val();
	data = eval(locations);
	mapInit(data);
    $("#dataGrid").datagrid("load",{
		'dispatcherName':$("#_dispatcherName").textbox("getValue"),
		'griddingId':$("#griddingName").combobox("getValue"),
		'wstatus':$("#w-status").combobox("getValue"),
		'empType':$("#empType").combobox("getValue")
    });
}

function getData(){
//    setTimeout(function () {
        $.get("query_dispatcher_list", function(data){
        	  dispatcherArr = eval(data);
	          if(markerArr.length>0){
	             for(var j=0;j<markerArr.length;j++){
	                if(markerArr[j]){
	                  markerArr[j].setMap(null);
	                }
	             }
	          }
	          for ( var i = 0; i < dispatcherArr.length; i++) {
	        	  markerArr[i]=addMarkerDispatcher(dispatcherArr[i],i);//向地图中添加调度员marker
	          }
	          setTimeout("getData()", 1000*60*2);
        });
//    }); 
}

function assginTask(employeeId){
	$("#e-id").val(employeeId);
	$.ajax({
		type:"post",
		url:"selectByEmployeeId",
		data:{employeeId:employeeId},
		success:function(result){
			res = eval("("+result+")");
			$("#employee").textbox('setValue',res.name);
		}
	})
	$("#editView").dialog({title:"创建任务",width:700,height: 420});
	$('#lpn').combobox({  
	    valueField : 'id',  
	    textField : 'text',
	    url: 'query_total_lpn',    
	    editable:true,  
	    filter: function(q, row){  
	        var opts = $(this).combobox('options');  
	        return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配  
	    },  
	});
	$('#beginParkId').combobox({  
	    valueField : 'id',  
	    textField : 'text',
	    url: 'get_park_list',    
	    editable:true,  
	    filter: function(q, row){  
	        var opts = $(this).combobox('options');  
	        return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配  
	    },  
	}); 
	$('#endParkId').combobox({  
	    valueField : 'id',  
	    textField : 'text',
	    url: 'get_park_list',    
	    editable:true,  
	    filter: function(q, row){  
	        var opts = $(this).combobox('options');  
	        return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配  
	    },  
	}); 
	$("#editView").dialog("open");
}

//员工还车
function returnCar(arr0,arr1){
	$("#e-employeeId").val(arr0);
	var empStatus = dispatcherArr[arr1].usingCarStatus;
	if(empStatus==""){
		$.messager.alert("提示", "该员工当前没有用车");
	}else if (empStatus=="ordered") {
		$.messager.alert("提示", "该员工当前仅处于预约车辆状态，不能还车");
	}else{
	$("#returnCarOper").dialog({title:"员工还车",width:400,height: 220});
	$("#returnCarOper").dialog("open");
	}
}
//强制还车
$(function(){
	$("#forceReturnCar").bind("click",function(){
	if($("#ss").css("display")=="none"){ 
            document.getElementById("ss").style.display="block"
            $("#status").combobox({required:true});
         	$("#reason").textbox({required:true});
        }else{
            document.getElementById("ss").style.display="none";
            $("#status").combobox("clear");
            $("#reason").textbox("clear");
         	$("#status").combobox({required:false});
         	$("#reason").textbox({required:false});
        } 
	});
})

