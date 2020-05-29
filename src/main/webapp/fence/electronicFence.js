var _actionUrl;

$(function() {
	//table
	$('#electronicFenceGrid').datagrid({
		title : '电子围栏管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'electronic_fence_list',
		queryParams:{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'fenceName':$("#s-fenceName").textbox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		},{
			field : 'cityName',
			title : '城市',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'fenceName',
			title : '电子围栏名称',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				return "<a onClick=javascript:fenceDetail("+rec.id+")> <font color='green'>"+val+"</font></a>" ;
			}
			
		}, {
			field : 'type',
			title : '电子围栏类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "0"){
					return "自定义" ;
				}else if(val == "1") {
					return "行政区划" ;
				}
			}
			
		},{
		     field : 'describe',
		     title : '描述',
		     width : $(this).width() * 0.2,
		     align : 'center'
	     },{
			  field : 'createName',
		      title : '创建人',
		      width : $(this).width() * 0.08,
		      align : 'center'
	        },{
			  field : 'createTime',
		      title : '创建时间',
		      width : $(this).width() * 0.08,
		      align : 'center'
	        },{
			   field : 'updateName',
			   title : '更新人',
			   width : $(this).width() * 0.08,
		       align : 'center'
		   },{
			 field : 'updateTime',
		     title : '更新时间',
		     width : $(this).width() * 0.08,
		     align : 'center'
	     }] ],
		pagination : true,
		rownumbers : true
	});
	
	$("#view").dialog( {
		width : "900",
		height : "750",
		top : "40",
		modal:true,
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#viewForm").form("submit",{
					url:_actionUrl,
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#electronicFenceGrid").datagrid("reload");
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
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#electronicFenceGrid").datagrid("load",{
			'cityCode':$("#s-cityCode").combobox("getValue"),
			'fenceName':$("#s-fenceName").textbox("getValue")
		});
	});
	
	//clear
	/*$("#btnClear").bind("click",function(){
		$("#sElectronicFenceForm").form("clear");
	});*/
	
	//save
	$("#btnSave").bind("click",function(){
		mapObj.clearMap(); 
		fencePoint = new Array() ;
		$("#viewForm").form("clear");
		$("#view").dialog({title:"添加电子围栏信息"});
		_actionUrl = "electronic_fence_save";
		$("#view").dialog("open");
	});
	
	//edit
	$("#btnEdit").bind("click",function(){
		mapObj.clearMap(); 
		$("#viewForm").form("clear");
		$("#view").dialog({title:"修改电子围栏信息"});
		_actionUrl = "electronic_fence_edit";
		var selectedRows = $("#electronicFenceGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else{
			mapObj.clearMap(); 
			fencePoint = new Array() ;
			$("#e-id").val(selectedRows[0].id);
			/**根据fenceId获取电子围栏gps信息*/
			var gpsArray = new Array() ;
	        $.post("electronic_fence_gps?fenceId="+selectedRows[0].id, function(data){
	        	 gpsArray=eval(data);
	        	 
	        	 if(gpsArray.length > 0) {
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
	        });
			$("#e-fenceName").textbox("setValue",selectedRows[0].fenceName);
			$("#e-describe").textbox("setValue",selectedRows[0].describe);
			$("#e-cityCode").combobox("setValue",selectedRows[0].cityCode);
			$("#e-type").combobox("setValue",selectedRows[0].type);
			$("#view").dialog("open");
		 }
	});
	
	
	//del
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#electronicFenceGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("electronic_fence_del",{id:_id},function(data){
						if(data == "succ"){
							$.messager.alert("提示", "操作成功", "info");
							$("#electronicFenceGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	
	$("#detailView").dialog( {
		width : "500",
		height : "580",
		top : "40",
		title:"电子围栏详情",
		modal:true
	});
	
}); 

function fenceDetail(fenceId){
	var mapObj1; 
	var marker = new Array();  
	//初始化地图
    var opt = {  
        level: 17 //设置地图缩放级别    
    }
    mapObj1 = new AMap.Map("detailMap", opt);  
	
	 //在地图中添加MouseTool插件
    mapObj1.plugin(["AMap.MouseTool"], function() {
        var mouseTool = new AMap.MouseTool(mapObj);
        //鼠标工具插件添加draw事件监听
        AMap.event.addListener(mouseTool, "draw", function callback(e) {
            var eObject = e.obj;//obj属性就是鼠标事件完成所绘制的覆盖物对象。
        });
        mouseTool.measureArea();  //调用鼠标工具的面积量测功能
    });
	   
    /**根据fenceId获取电子围栏gps信息*/
	var gpsArray = new Array() ;
    $.post("electronic_fence_gps?fenceId="+fenceId, function(data){
    	 gpsArray=eval(data);
    	 
    	 if(gpsArray.length > 0) {
    		 var polygonArr = new Array();//多边形覆盖物节点坐标数组
    		 mapObj1.setCenter(new AMap.LngLat(gpsArray[0].longitude , gpsArray[0].latitude)); //设置地图中心点
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
 		    polygon.setMap(mapObj1);
    	 }
    }); 
    $("#detailView").dialog("open");  
}

