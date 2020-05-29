var _actionUrl;

$(function() {
	//清空Combogrid
	$("#clearCombogrid").bind("click",function(){
		$("#g-fence").combogrid("clear");
	});
	
//	$('#g-fence').combogrid({
//	    onSelect: function () {//选中处理  
//	    	console.log(fenceGroupId); 
////	    	$('#g-fence').combogrid('grid').datagrid('getSelected').
//			$("#g-id").val(fenceGroupId);
//        }
//	}); 
	
	$('#parkGrid').datagrid({
		title : '网点管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'park_list',
		queryParams:{
			'status' : $("#s-status").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		},{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.05,
			align : 'center'
		},{
            field : 'areaName',
            title : '所属城/镇区',
            width : $(this).width() * 0.05,
            align : 'center'
        },{
			field : 'name',
			title : '网点名称',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'status',
			title : '状态',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val,row){
				if(val == 1){
					return "开启";
				}else{
					return "关闭";
				}
			}
		},{
			field : 'categoryName',
			title : '网点类型',
			width : $(this).width() * 0.06,
			align : 'center'
		},{
			field : 'cooperationType',
			title : '网点合作类型',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter:function(val,rec){
				if(val=="0"){
					return "自有网点";
				}else{
					return "合作网点";
				}
			}
		},{
			field : 'isRun',
			title : '网点状态',
			width : $(this).width() * 0.08,
			align : 'center',
			hidden : true
			
		},{
			field : 'operatorId',
			title : '运营商组织机构代码',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'propertyManagementCompany',
			title : '所属物业公司',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'address',
			title : '网点地址',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'fenceName',
			title : '电子围栏名称',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'parkNums',
			title : '停车位数量',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'parkFee',
			title : '停车费(元/小时)',	
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(val,rec){
				if(val !=null || val !=''　){
					n = (val/100).toFixed(2);
		   			var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
	       			return n.replace(re, "$1,");
				}
			}
		}, {
			field : 'parkFeeDesc',
			title : '停车费描述',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'liablePerson',
			title : '责任人',
			width : $(this).width() * 0.06,
			align : 'center'
		},{
			field : 'isTemporary',
			title : '是否为临时网点',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter:function(val,rec){
				if(val=="0"){
					return "是";
				}else if(val=="1"){
					return "否";
				}
			}
		},{
			field : 'runStartTime',
			title : '运营开始时间',
			width : $(this).width() * 0.06,
			align : 'center'
		},{
			field : 'runEndTime',
			title : '运营结束时间',
			width : $(this).width() * 0.06,
			align : 'center'
		}
		,{
			field : 'enterpriseName',
			title : '所属企业名称',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
			field : 'remark',
			title : '描述',
			width : $(this).width() * 0.1,
			align : 'center'
		}, {
                field : 'fullNoParking',
                title : '车位满时不可还车',
                width : $(this).width() * 0.08,
                align : 'center',
                formatter:function(val,rec){
                    if(val==true){
                        return "是";
                    }else {
                        return "否";
                    }
                }
            }, {
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
            }, {
                field : 'parkPanoramaLink',
                title : '网点全景链接',
                width : $(this).width() * 0.08,
                align : 'center'
            }
                ] ],
		pagination : true,
		rownumbers : true
	});
	

	
//	//edit view
//	$("#editView").dialog( {
//		width : "400",
//		height : "350",
//		top : "40",
//		modal:true,
//		title:"修改网点信息",
//		buttons : [{
//			text : "确认",
//			iconCls : "icon-save",
//			handler : function() {
//				$("#editViewForm").form("submit",{
//					url:'edit_park',
//					onSubmit:function(){
//						return $(this).form("validate");
//					},
//					success:function(result) {
//						if (result== "succ") {
//							$.messager.alert("提示", "操作成功", "info");
//							$("#parkGrid").datagrid("reload");
//							$("#editView").dialog("close");
//						} else if(result == "fail") {
//							$.messager.alert("提示", "操作失败", "error");
//						}else{
//							$.messager.alert("提示", "操作失败", "error");
//						}
//					}
//				});
//			}
//		},{
//			text : "取消",
//			iconCls : "icon-cancel",
//			handler : function() {
//				$("#editView").dialog("close");
//			}
//		}]
//	});
	
	
	//查询
	$("#btnQuery").bind("click",function(){
		$("#parkGrid").datagrid("load",{
			'name':$("#sname").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
			'isTemporary':$("#s-isTemporary").combobox("getValue"),
			'parkType':$("#s-parkType").combobox("getValue"),
			'status' : $("#s-status").combobox("getValue"),
			'fullNoParking' : $("#s-fullNoParking").combobox("getValue"),
			'category':$("#s-category").combobox("getValue"),
            'gareaCode':$("#gareaCode").combobox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var name = $("#sname").textbox("getValue");
		var cityCode = $("#scityCode").combobox("getValue");
	    var isTemporary = $("#s-isTemporary").combobox("getValue");
	    var parkType = $("#s-parkType").combobox("getValue");
	    var status = $("#s-status").combobox("getValue");
	    var fullNoParking = $("#s-fullNoParking").combobox("getValue");
	    var category = $("#s-category").combobox("getValue");
        var gareaCode=$("#gareaCode").combobox("getValue");
		$("#parkFrom").form("submit", {
			url : "export_park_list?name="+name+"&cityCode="+cityCode+"&isTemporary="+isTemporary+"&parkType="+parkType+"&status="+status+"&fullNoParking="+fullNoParking+"&category="+category+"&gareaCode="+gareaCode,
			onSubmit : function() {
//				param.name = name;
//				param.cityCode = cityCode;
//				param.isTemporary = isTemporary;
//				param.parkType = parkType;
//				param.status = status;
//				param.fullNoParking = fullNoParking;
//				param.category = category;
			},
			success : function(result) {
			}
		});

	});
	//clear
	$("#btnClear").bind("click",function(){
		//$("#sparkForm").form("clear");
	});
	
	
	//save park info
	$("#btnSave").bind("click",function(){
		init();
		$("#viewForm").form("clear");
		$("#titleDiv").html("添加网点信息");
		$("#view").dialog({title:"添加网点信息"});
		_actionUrl = "save_park";
		$("#e-runStartTime").textbox('setValue',"00:00");
		$("#e-runEndTime").textbox('setValue',"24:00");
		$("#view").dialog("open");
		if ($("#view").parent().is(":hidden")==false){
			$("input",$("#e-runStartTime").next("span")).blur(function(){
				var value = $("#e-runStartTime").val();
				var reg = /^(0\d{1}|1\d{1}|2[0-4]):([0-5]\d{1})$/;
				if (!reg.test(value)) {
					$("#run_start_time_result").html("时间格式不正确,正确的格式为 00:00，请以24小时制作为标准");
				}else{
					$("#run_start_time_result").html("");
				}
			})
		}
		
		if ($("#view").parent().is(":hidden")==false){
			$("input",$("#e-runEndTime").next("span")).blur(function(){
				var value = $("#e-runEndTime").val();
				var reg = /^(0\d{1}|1\d{1}|2[0-4]):([0-5]\d{1})$/;
				if (!reg.test(value)) {
					$("#run_end_time_result").html("时间格式不正确,正确的格式为 00:00，请以24小时制作为标准");
				}else{
					$("#run_end_time_result").html("");
				}
			})
		}
		$("#s2").hide();$("#s1").hide();
	});
	
	$('#e-cooperation_type').combobox({  
        onChange:function(newValue,oldValue){  
    	if(newValue=='0'){
    		$("#s2").show();
    		$("#s1").hide();
    		$("#operatorId").textbox("setValue",'');
    		
    	}else{
    		$("#s1").show();
    		$("#s2").hide();
    	    $("#propertyManagementCompany").textbox("setValue",'');
    		
    	} 
       }  
    });
	//修改网点信息
	$("#btnEdit").bind("click",function(){
		$("#viewForm").form("clear");
		$("#titleDiv").html("修改网点信息");
		_actionUrl = "edit_park";
		$("#view").dialog({title:"修改网点信息"});
		var selectedRows = $("#parkGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			if(selectedRows[0].propertyManagementCompany !=""){
				$("#s2").show();	
			}else{
				$("#s2").hide();
			}
			if(selectedRows[0].operatorId !=""){
				$("#s1").show();
			}else{
				$("#s1").hide();
			}
			var _id =  selectedRows[0].id;
			var _name = selectedRows[0].name;
			var _address = selectedRows[0].address;
			var _parkNums = selectedRows[0].parkNums;
			var _liablePerson = selectedRows[0].liablePerson;
			var _cityCode = selectedRows[0].cityCode;
			var _latitude = selectedRows[0].latitude;
			var _longitude = selectedRows[0].longitude;
			$("#eid").val(_id);
			$("#e-name").textbox("setValue",_name);
			$("#e-address").textbox("setValue",_address);
			$("#e-parkNums").textbox("setValue",_parkNums);
			$("#e-latitude").textbox("setValue",_latitude);
			$("#e-longitude").textbox("setValue",_longitude);
			$("#e-parkType").combobox("setValue",selectedRows[0].parkType);
			$("#e-liablePerson").textbox("setValue",_liablePerson);
			$("#e-cityCode").combobox('setValue',_cityCode);
			$("#e-runStartTime").textbox('setValue',selectedRows[0].runStartTime);
			$("#e-runEndTime").textbox('setValue',selectedRows[0].runEndTime);
			$("#g-areaCode").combobox('setValue',selectedRows[0].areaName);
			$("#e-areaCode").val(selectedRows[0].areaCode);
			$("#e-areaName").val(selectedRows[0].areaName);
			$("#e-category").combobox('setValue',selectedRows[0].category);
			$("#e-remark").textbox("setValue",selectedRows[0].remark);
			$("#operatorId").textbox("setValue",selectedRows[0].operatorId);
			$("#e-parkFee").textbox("setValue",selectedRows[0].parkFee/100);
			$("#e-parkFeeDesc").textbox("setValue",selectedRows[0].parkFeeDesc);
			$("#e-cooperation_type").combobox('setValue',selectedRows[0].cooperationType);
			$("#e-parkPanoramaLink").textbox("setValue",selectedRows[0].parkPanoramaLink);
			$("#isTemporary").combobox('setValue',selectedRows[0].isTemporary);
			$("#propertyManagementCompany").textbox("setValue",selectedRows[0].propertyManagementCompany);
			if (selectedRows[0].fullNoParking==true){
				$("#fullNoParking").prop("checked",true);
			}
			if (selectedRows[0].isCoexist==1){
				$("#isCoexist").prop("checked",true);
			}
			$("#view").dialog("open");
			
			if ($("#view").parent().is(":hidden")==false){
				$("input",$("#e-runStartTime").next("span")).blur(function(){
					var value = $("#e-runStartTime").val();
					var reg = /^(0\d{1}|1\d{1}|2[0-4]):([0-5]\d{1})$/;
					if (!reg.test(value)) {
						$("#run_start_time_result").html("时间格式不正确,正确的格式为 00:00，请以24小时制作为标准");
					}else{
						$("#run_start_time_result").html("");
					}
				})
			}
			
			if ($("#view").parent().is(":hidden")==false){
				$("input",$("#e-runEndTime").next("span")).blur(function(){
					var value = $("#e-runEndTime").val();
					var reg = /^(0\d{1}|1\d{1}|2[0-4]):([0-5]\d{1})$/;
					if (!reg.test(value)) {
						$("#run_end_time_result").html("时间格式不正确,正确的格式为 00:00，请以24小时制作为标准");
					}else{
						$("#run_end_time_result").html("");
					}
				})
			}
			
			var _latitude = selectedRows[0].latitude;
			var _longitude = selectedRows[0].longitude;
			var opt = {
			        level: 180, //设置地图缩放级别    
			        center: new AMap.LngLat(_longitude,_latitude) //设置地图中心点   
			    }
			var mapObj = new AMap.Map("iMap", opt);
            mapObj.on('click',function(e){
                getLnglat(e);
			});
			 new AMap.Marker({
		        map:mapObj,
		        icon: new AMap.Icon({
		            image: "images/Map_Marker.png",
		            size:new AMap.Size(24,24)
		        }),
		        position: new AMap.LngLat(_longitude,_latitude),
		        offset: new AMap.Pixel(-5,-30),
                clickable : true , //是否可点击
		    });
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
                $("#a-address").textbox("setValue",address);
                $("#e-address").textbox("setValue",address);
                // $("#e-areaName").textbox("setValue",data.regeocode.addressComponent.district);
                //$("#e-areaCode").textbox("setValue",data.regeocode.addressComponent.district);
            }
//鼠标点击，获取经纬度坐标
            function getLnglat(e){
                console.log("aaaaaa");
                mapObj.clearMap();
                var x = e.lnglat.getLng();
                var y = e.lnglat.getLat();
                //alert(x + "," + y);
                //document.getElementById("coordinate").innerHTML = x + "," + y;
                $("#a-longitude").textbox("setValue",x);
                $("#a-latitude").textbox("setValue",y);
                $("#e-longitude").textbox("setValue",x);
                $("#e-latitude").textbox("setValue",y);
                lnglatXY = new AMap.LngLat(x,y);
                geocoder();
            }
		}
	});


	//add view
	$("#view").dialog( {
		width : "700",
		height : "610",
		top : "40",
		left:"300",
		modal:true,
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#viewForm").form("submit",{
					url:_actionUrl,
					onSubmit:function(){
						if($("#e-name").textbox("getValue").length > 8 ){
							$('#e-name').textbox('textbox').focus(); 
							$.messager.alert("提示", "网点名称不能大于8个字符", "info");
 							return false ;
						}
						if($("#e-remark").textbox("getValue").length > 16 ){
							$('#e-remark').textbox('textbox').focus(); 
							$.messager.alert("提示", "网点描述不能大于16个字符", "info");
 							return false ;
						}
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#parkGrid").datagrid("reload");
							$("#view").dialog("close");
							
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
							
						}else{
						    $.messager.alert("提示", "操作失败", "error");
						   
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#view").dialog("close");
				
			}
		}]
	});
	
	$("#mapLookDialgView").dialog( {
		width : "750",
		height : "450",
		top : "40",
		left: "300",
		title:"地图位置",
		modal:true
	});
	
	$("#btnMapLook").bind("click", function(){
		var selectedRows = $("#parkGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要查看地址位置的记录", "error");
		 }else{
				var _latitude = selectedRows[0].latitude;
				var _longitude = selectedRows[0].longitude;
				var opt = {  
				        level: 180, //设置地图缩放级别    
				        center: new AMap.LngLat(_longitude,_latitude) //设置地图中心点   
				    }
				var mapObj = new AMap.Map("imapLookDialgView", opt); 
				 new AMap.Marker({
			        map:mapObj,
			        icon: new AMap.Icon({
			            image: "images/Map_Marker.png",
			            size:new AMap.Size(24,24)
			        }),
			        position: new AMap.LngLat(_longitude,_latitude),
			        offset: new AMap.Pixel(-5,-30)
			    });
				$("#mapLookDialgView").dialog("open");
		 }
	});
	
	//delete park info
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#parkGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("del_park",{id:_id},function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#parkGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	
	//构造对话框
	$("#fenceView").dialog( {
		width : "380",
		height : "250",
		top : "80",
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#fenceViewForm").form("submit", {
					url : "car_group_add_fence",
					success : function(result) {
						$.messager.progress('close'); 
						if (result == "succ") {
							$.messager.alert("提示", "保存成功", "info");
							$("#s2").hide();$("#s1").hide();
						    $("#parkGrid").datagrid("reload");
							$("#fenceView").dialog("close");
							
						}else if(result == "exist"){
							$.messager.alert("提示", "该网点已经存在电子围栏", "info");
						}else {
							$.messager.alert("提示", "保存失败", "info");
							$("#s2").hide();$("#s1").hide();
						    $("#parkGrid").datagrid("reload");
							$("#fenceView").dialog("close");
						}
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#s2").hide();$("#s1").hide();
					$("#fenceView").dialog("close");
			}
		}]
	});
	
	$("#setFence").bind("click",function(){
//		debugger ;
//		$("#fenceViewForm").form("clear");
		$("#fenceView").dialog({title:"设置电子围栏信息"});
		var selectedRows = $("#parkGrid").datagrid("getSelections");
		debugger ;
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else{
			 clearFenceForm();
			$("#g-id").val(selectedRows[0].id); 
			$("#g-category").val("park"); 
			//根据组区域获取电子围栏
			var _url = 'electronic_fence_city_code?cityCode='+selectedRows[0].cityCode;
			var fname = '';// 电子围栏名
			//换用combogrid
			$('#g-fence').combogrid({   

				delay : 500,// 1s延时查询
				editable : true,
				panelHeight : 'auto',
				panelWidth : 300,
				value: selectedRows[0].fenceId,
				idField : 'id',
				textField : 'fenceName',
				url : _url,
				pageSize : 10,
				required : true,
				columns : [ [ 
				{
					field : 'fenceName',
					title : '电子围栏',
					width : '80%',
					align : 'center'
				}] ],
				pagination : true,
				rownumbers : true,
				keyHandler : {
					query : function(fenceName) { // 动态搜索处理
						fname = fenceName;
						$('#g-fence').combogrid("grid").datagrid('options').queryParams = 
							JSON.parse('{"fenceName":"' + fenceName + '"}');
						// 重新加载
						$('#g-fence').combogrid("grid").datagrid("reload");

						$('#g-fence').combogrid("setValue", fenceName);
					}
				}
			
			});  
//			$('#g-fence').combogrid('grid').datagrid('reload', _url);
//			$('#g-fence').combogrid("setValue", selectedRows[0].fenceId);
			$("#fenceView").dialog("open");
		 }
	});
	
	//清空combogrid
	function clearFenceForm(){
		$('#fenceViewForm').form('clear');
	}
	
	$("#setStatus").bind("click",function(){
		var selectedRows = $("#parkGrid").datagrid("getSelections");
		if(selectedRows[0].status ==1){
			var val = "网点正在运营,确定关闭吗?";
		}else if(selectedRows[0].status == 0){
			var val = "网点非运营,确定开启吗?";
		}
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择网点", "error");
		 }else{
			 $.messager.confirm("提示",val,function(r){
					if(r){
						$.post("set_park_status?id="+selectedRows[0].id,function(data){
							if(data == "existsCar"){
								$.messager.alert("提示", "此网点下有车,不可关闭", "error");
							}else if(data == "closeSucc"){
								$.messager.alert("提示", "网点关闭成功", "info");
								$("#parkGrid").datagrid("reload");
							}/*else if(data == "perSucc"){
								$.messager.alert("提示", "网点开启成功!", "info");
								$("#parkGrid").datagrid("reload");
							}*/else if(data == "openSucc"){
								$.messager.alert("提示", "网点开启成功", "info");
								$("#parkGrid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }
	})
	
	$("#removeFence").bind("click",function(){
		 var selectedRows = $("#parkGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要移除的记录", "error");
		 }else{
			$.messager.confirm("提示","确定要移除吗?",function(r){
				if(r){
					$.post("fence_group_relation_del?ids="+selectedRows[0].id+"&category=park",function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#parkGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 } 
	})
	
})	