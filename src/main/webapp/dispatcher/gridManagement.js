var _json;
$(function() {
			//查询链接
			$("#btnQuery").bind("click",function(){
			    $("#dataGrid").datagrid("load",{
					'gridName':$("#_gridName").textbox("getValue"),
				});
			});
			
			$('#dataGrid').datagrid( {
				title : '网格管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'grid_management_page',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
					{field:'cityName',title:'所属城市',align:'center',width : $(this).width() * 0.08,
							formatter:function(value,row,index){
								return row.cityName;
							}
						},
					{field:'name',title:'网格名称',align:'center',width : $(this).width() * 0.08,
							formatter:function(value,row,index){
								return row.name;
							}
						},
					
					{field:'parkNumber',title:'网点数',align:'center',width : $(this).width() * 0.08,
							formatter:function(value,row,index){
								return "<a href='javascript:void(0)' onclick=getParkDetail('"
								+ row.id + "')>" + row.parkNumber + "</a>";
							}
						},
					{field:'dispatcherNumber',title:'调度员数',align:'center',width : $(this).width() * 0.08,
							formatter:function(value, row, index){
									return "<a href='javascript:void(0)' onclick=getDispatcherDetail('"
									+ row.id + "')>" + row.dispatcherNumber + "</a>";
							}
						},
					{field:'createUser',title:'创建人',align:'center',width : $(this).width() * 0.08,
							formatter:function(value,row,index){
								return row.createUser;
							}
					},
					{field:'createTime',title:'创建时间',align:'center',width : $(this).width() * 0.12,
							formatter:function(value,row,index){
								return row.createTime;
							}
						},
					{field:'updateUser',title:'修改人',align:'center',width : $(this).width() * 0.12,
							formatter:function(value,row,index){
								return row.updateUser;
							}
						},
					{field:'updateTime',title:'修改时间',align:'center',width : $(this).width() * 0.08,
							formatter:function(value,row,index){
								return row.updateTime;
							}
					}
				] ],
				pagination : true,
				rownumbers : true
			}); 
			
	//构造对话框
	$("#editView").dialog( {
		width : "800",
		height : "400",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit", {
					url : "saveOrUpdateGrid",
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
							$("#editView").dialog("close");
						}else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#editView").dialog("close");
						}else if(result == "contains") {
							$.messager.alert("提示", "您所画的网格中包含了其他网格中存在的网点", "error");
						    $("#dataGrid").datagrid("reload");
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
	
	//设置网点
	$("#manageParkView").dialog({
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#manageParkViewForm").form("submit", {
					url : "managePark",
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
							$("#manageParkView").dialog("close");
						}else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#manageParkView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#manageParkView").dialog("close");
			}
		}]
	})
	//设置网点
	$("#mamagePoint").click(function(){
		$("#manageParkViewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要设置网点的网格", "error");
		}else{
			$('#parkId').combobox('reload',"getAllParkByCityCode?cityCode="+selectedRows[0].cityCode);
			$("#gridName").textbox('setValue',selectedRows[0].name);
			$("#e-gridId").val(selectedRows[0].id);
			$("#manageParkView").dialog({title:"设置网点",width: 500,height: 300});
			$("#manageParkView").dialog("open");
		}
		
	})
	
	//设置调度员
	//设置网点
	$("#manageDispatcherView").dialog({
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#manageDispatcherViewForm").form("submit", {
					url : "manageDispatcher",
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
							$("#manageDispatcherView").dialog("close");
						}else if(result == "fail"){
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#manageDispatcherView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#manageDispatcherView").dialog("close");
			}
		}]
	})
	//设置调度员
	$("#mamageDispatcher").click(function(){
		$("#manageDispatcherViewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要设置调度员的网格", "error");
		}else{
			$('#dispatcherId').combobox('reload',"getAllDispatcherByCityCode?cityCode="+selectedRows[0].cityCode);
			$("#grid").textbox('setValue',selectedRows[0].name);
			$("#e-newGridId").val(selectedRows[0].id);
			$("#e-dispatcherId").val(selectedRows[0].id);
			$("#manageDispatcherView").dialog({title:"设置调度员",width: 500,height: 300});
			$("#manageDispatcherView").dialog("open");
		}
		
	})
	//添加
	$("#btnAdd").bind("click",function(){
		$("#editViewForm").form("clear");
		$("#editView").dialog({title:"创建网格",width: 1000,height: 600});
		$.ajax({
			type: 'POST',
			  url: "get_login_location",
			  success: function(data){
				  var _json = eval("("+data+")");
				  mapInit(_json);
			  },
		})
		$("#editView").dialog("open");
		$("#cityCode").combobox({
			onSelect:function(param){
				$.ajax({
					  type: 'POST',
					  url: "selectLocationByCityCode",
					  data: {cityCode:param.id},
					  success: function(data){
						  console.log(data)
						  result = eval("("+data+")");
						  mapInit(result);
					  }, 
				});
			}
		})
	});
			
	//edit
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			$("#e-id").val(selectedRows[0].id); 
			$("#hideCityCode").val(selectedRows[0].cityCode);
			$("#cityCode").combobox('setValue',selectedRows[0].cityName);
			$("#e-gridName").textbox('setValue',selectedRows[0].name);
			$("#editView").dialog({width: 600,height: 600});
			$("#editView").dialog({title:"修改信息"});
			var gpsArray;
			$.ajax({
				  type: 'POST',
				  url: "selectElectronicGridGpsByGridId",
				  data: {gridId:selectedRows[0].id},
				  success: function(data){
					  console.log(data);
					  gpsArray = eval(data);
				  }, 
			});
			$.ajax({
				  type: 'POST',
				  url: "selectLocationByCityCode",
				  data: {cityCode:selectedRows[0].cityCode},
				  success: function(data){
					  _json = eval("("+data+")");
					  mapInit(_json,gpsArray,selectedRows[0].id);
				  }, 
			});
			$("#editView").dialog({width: 1000,height: 600});
			$("#editView").dialog("open");
		}
	});
	
	//删除操作
	$("#btnRemove").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的网格", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("deleteGrid.do",{"id":JsonData.id},function(data){
						if(data=="success"){
							$.messager.alert("提示", "删除成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	
	$("#btnParkQuery").click(function(){
		getParkList($("#gridId").val(),$("#_parkName").val());
	})
	
	$("#btnDispatcherQuery").click(function(){
		getDispatcherList($("#gridId").val(),$("#_dispatcherName").val());
	})
	
	//移除网格下的网点
	$("#btnParkDelete").bind("click",function(){
      	var selectedRow = $("#parkDetailDataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的网格", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("deleteParkOnGrid.do",{"id":JsonData.id,"gridId":$("#gridId").val()},function(data){
						if(data=="success"){
							$.messager.alert("提示", "删除成功", "info");
						    $("#parkDetailDataGrid").datagrid("reload");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	
	//移除网格下的调度员
	$("#btnDispatcherDelete").bind("click",function(){
      	var selectedRow = $("#dispatherDetailDataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的调度员", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("deleteDispatcherOnGrid.do",{"id":JsonData.id,"gridId":$("#gridId").val()},function(data){
						if(data=="success"){
							$.messager.alert("提示", "删除成功", "info");
						    $("#dispatherDetailDataGrid").datagrid("reload");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
	
	//设置网格管理员
	$("#manageGridAdministration").bind("click",function(){
		var selectedRow = $("#dispatherDetailDataGrid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要设置为网格管理员的员工", "error");
		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要设置为网格管理员的员工吗?",function(r){
				if(r){
					$.post("manageGridAdministration.do",{"id":JsonData.id},function(data){
						if(data=="success"){
							$.messager.alert("提示", "设置成功", "info");
							$("#dispatherDetailDataGrid").datagrid("reload");
							$("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "该员工为临时工，不能设置为网格管理员", "info");
						}
					},"text");
				}
			});
		}	     
	});
	//取消网格管理员
	$("#cancelGridAdministration").bind("click",function(){
		var selectedRow = $("#dispatherDetailDataGrid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要取消网格管理员的员工", "error");
		}else{
			var JsonData = selectedRow[0];
			var isManager=JsonData.isManager;
			if(isManager=="1"){
			$.messager.confirm("提示","确定要取消为网格管理员的员工吗?",function(r){
				if(r){
					$.post("cancelGridAdministration.do",{"id":JsonData.id},function(data){
						if(data=="success"){
							$.messager.alert("提示", "取消成功", "info");
							$("#dispatherDetailDataGrid").datagrid("reload");
							$("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "该员工为临时工，不能设置为网格管理员", "info");
						}
					},"text");
				}
			});
			}else{
				$.messager.alert("提示", "该员工不是网格管理员，不能取消", "info");
			}
		}	     
	});
	
	//网点明细对话框
	$("#parkDetailDataGrid").datagrid( {
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'parkDetail.do',
		pageSize : 20,
		idField : 'id',
		columns : [ [{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		} , {
			field : 'name',
			title : '网点名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, 
		{
			field : 'categoryName',
			title : '网点类型',
			width : $(this).width() * 0.08,
			align : 'center',
		},
		{
			field : 'cooperationType',   
			title : '合作类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val == 0) {
					return "自有网点"
				}else{
					return "合作网点"
				}
			}
		},
		{  
			field : 'parkNums',   
			title : '车位数',
			width : $(this).width() * 0.08,
			align : 'center'
		},
		{
			field : 'liablePerson',
			title : '责任人',
			width : $(this).width() * 0.08,
			align : 'center',
		},{
			field : 'address',
			title : '地址',
			width : $(this).width() * 0.35,
			align : 'center',
			
		},{
			field : 'isCoexist',
            title : '多片区共存',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter:function(val,rec){
                if(val==1){
                    return "是";
                }else {
                    return "否";
                }
            }
			
		},{
			field : 'remark',
			title : '备注',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				
			}
		}
		] ],
		pagination : true,
		rownumbers : true
	});
	
	//调度员明细对话框
	$("#dispatherDetailDataGrid").datagrid( {
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'dispatcherDetail.do',
		pageSize : 20,
		idField : 'id',
		columns : [ [{
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		} , {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, 
		{
			field : 'gridName',
			title : '所属网格',
			width : $(this).width() * 0.08,
			align : 'center',
		},
		{
			field : 'identifyLabel',   
			title : '身份',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val){
				if (val == '1') {
					return "调度管理员";
				}else if (val == '2') {
					return "城市管理员";
				}else {
					return "调度员";
				}
			}
		},
		{  
			field : 'status',   
			title : '状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val){
				if (val == 'closed') {
					return "下班";
				}else if (val == 'working') {
					return "上班";
				}
			}
		},
		{
			field : 'phone',
			title : '手机号',
			width : $(this).width() * 0.08,
			align : 'center',
		},{
			field : 'remark',
			title : '备注',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				
			}
		}
		] ],
		pagination : true,
		rownumbers : true
	});
});


//加载地图
function mapInit(data,gpsArray,gridId){
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
	var mapObj = new AMap.Map('gridContent', {
		center : point, //地图中心点
		level : 12       
	});
	//地图上绑定点击事件
	if (gpsArray && gpsArray.length > 0) {
		mapObj.on('click', function(e) {
			$.ajax({
				  type: 'POST',
				  url: "deleteGridByGridId",
				  data: {gridId:gridId},
				  success: function(){
					  mapInit(_json)
				  }, 
			});
	    });
	}
	
	//回显在地图上描的点
    if(gpsArray && gpsArray.length > 0) {
        var polygonArr = new Array();//多边形覆盖物节点坐标数组
        mapObj.setCenter(new AMap.LngLat(gpsArray[0].longitude , gpsArray[0].latitude)); //设置地图中心点
	      for(var i = 0 ; i < gpsArray.length ; i ++) {
	          polygonArr.push([gpsArray[i].longitude , gpsArray[i].latitude]);
	      }
	      var polygon = new AMap.Polygon({
   		  path: polygonArr,//设置多边形边界路径
   		  strokeColor: "#FF33FF", //线颜色
   		  strokeOpacity: 0.2, //线透明度
   		  strokeWeight: 3,    //线宽
   		  fillColor: "#1791fc", //填充色
   		  fillOpacity: 0.35//填充透明度
        });
	    polygon.setMap(mapObj);
    }
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
        
        var markers = [];
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
    	}
    	console.log(markers)
	});
	
	var fencePoint = new Array() ;
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

function getParkDetail(gridId){
	$("#_parkName").textbox('setValue','');
	$("#parkDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'gridId' : gridId,
	});
	
	$("#parkDetailView").dialog("setTitle", "网点列表");
	$("#gridId").val(gridId);
	$('#parkDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
}

function getParkList(gridId,parkName){
	
	$("#parkDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'gridId' : gridId,
			parkName : parkName
	});
	
	$("#parkDetailView").dialog("setTitle", "网点列表");
	$('#parkDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
}


function getDispatcherDetail(gridId){
	$("#_dispatcherName").textbox('setValue','');
	$("#dispatherDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'gridId' : gridId,
	});
	
	$("#dispatcherDetailView").dialog("setTitle", "调度员列表");
	$("#gridId").val(gridId);
	$('#dispatcherDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
}

function getDispatcherList(gridId,dispathcherName){
	$("#dispatherDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'gridId' : gridId,
			dispathcherName : dispathcherName
	});
	
	$("#dispatcherDetailView").dialog("setTitle", "网点列表");
	$('#dispatcherDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
}

