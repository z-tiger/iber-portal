$(function() {
	$('#pileGrid').datagrid({
		title : '充电桩管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'pile_list',
		queryParams:{
			'name':$("#sname").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
			'status':$("#sstatus").combobox("getValue"),
			'type':$("#stype").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'name',
			title : '充电桩名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'parkName',
			title : '网点名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'parkAddress',
			title : '网点地址',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'type',
			title : '充电桩类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1") {return "快充"}
				if(val == "2") {return "慢充"}
			}
		}, {
			field : 'latitude',
			title : '纬度',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'longitude',
			title : '经度',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'status',
			title : '状态',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "0") {return "未启用"}
				if(val == "1") {return "启用"}
			}
		} ] ],
		pagination : true,
		rownumbers : true
	});
	
	//add pile info
	$("#addView").dialog( {
		width : "400",
		height : "350",
		top : "40",
		modal:true,
		title:"添加充电桩信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit",{
					url:'save_pile',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#pileGrid").datagrid("reload");
							$("#addView").dialog("close");
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
				$("#addView").dialog("close");
			}
		}]
	});
	
	
	//edit view
	$("#editView").dialog( {
		width : "400",
		height : "350",
		top : "40",
		modal:true,
		title:"修改充电桩信息",
		buttons : [{
			text : "确认",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit",{
					url:'edit_pile',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#pileGrid").datagrid("reload");
							$("#editView").dialog("close");
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
				$("#editView").dialog("close");
			}
		}]
	});
	
	//查询
	$("#btnQuery").bind("click",function(){
		$("#pileGrid").datagrid("load",{
			'name':$("#sname").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
			'status':$("#sstatus").combobox("getValue"),
			'type':$("#stype").combobox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		$("#spileForm").form("clear");
	});
	
	
	//save pile info
	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#addView").dialog("open");
	});
	
	
	//edit pile info
	$("#btnEdit").bind("click",function(){
		$("#editViewForm").form("clear");
		var selectedRows = $("#pileGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			$("#eid").val(selectedRows[0].id);
			$("#e-name").textbox("setValue",selectedRows[0].name);
			$("#e-park_id").combobox("setValue",selectedRows[0].parkId);
			$("#e-type").combobox("setValue",selectedRows[0].type);
			$("#e-latitude").textbox("setValue",selectedRows[0].latitude);
			$("#e-longitude").textbox("setValue",selectedRows[0].longitude);
			$("#e-status").combobox("setValue",selectedRows[0].status);
			$("#e-cityCode").combobox('setValue',selectedRows[0].cityCode);
			$("#editView").dialog("open");
		}
	});
	
	
	//delete pile info
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#pileGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("del_pile",{id:_id},function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#pileGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	
	
	$("#mapLookDialgView").dialog( {
		width : "750",
		height : "450",
		top : "40",
		left: "150",
		title:"地图位置",
		modal:true
	});
	
	$("#btnMapLook").bind("click", function(){
		var selectedRows = $("#pileGrid").datagrid("getSelections");
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
})	