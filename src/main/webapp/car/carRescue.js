$(function(){	
	//query
	$("#btnQuery").bind("click",function(){ 
		$("#carRescueGrid").datagrid("load",{
			'lpn':$("#s-lpn").textbox("getValue"),
			'rescueStatus':$("#rescueStatus").combobox("getValue")	,
            'memberName':$('#memberName').textbox("getValue"),
            'memberPhone':$('#memberPhone').textbox("getValue")
		});
	});
	//clear清空
	$("#btnClear").bind("click",function(){
		clearToolbar();
	});
	//导出excel
	$("#btnExport").bind("click", function() {
	    var lpn = $("#s-lpn").textbox("getValue");
	    var rescueStatus = $("#rescueStatus").combobox("getValue");
        var memberName = $('#memberName').textbox("getValue");
        var memberPhone = $('#memberPhone').textbox("getValue");
		$("#carRescueForm").form("submit", {
			url : "export_carRescue_list?lpn="+lpn+"&rescueStatus="+rescueStatus
            +"&memberName="+memberName+"&memberPhone="+memberPhone,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	$('#carRescueGrid').datagrid({
		title : '车辆救援管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'dataListCarRescue',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'lpn',
			title : '车牌号码',
			width : $(this).width() * 0.07,
			align : 'center',
			formatter : function(val) {
				if (val.indexOf("•") < 0) {
					return val.substring(0,2) + "•" + val.substring(2);
				}else {
					return val;
				}
			}
		}, 
		{
			field : 'carBranceType',
			title : '车型',
			width : $(this).width() * 0.06,
			resizable : false,
			align : 'center'
		},
		{
			field : 'status',
			title : '救援状态',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1"){
					return "救援中"
				}else if(val=="0") {
					return "已结束"
				}else if(val=="2") {
                    return "已取消"
                }
			}
		}, {
			field : 'responsiblePerson',
			title : '任务执行人',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'responsiblePersonPhone',
			title : '执行人号码',
			width : $(this).width() * 0.06,
			align : 'center'
		},
		{
			field : 'behaviorTypeName',
			title : '责任类型',
			width : $(this).width() * 0.12,
			resizable : false,
			align : 'center',
			formatter:function(value, rec){
			    if(value == '其它(不扣用户贡献值)'){
			    	return ('【其它,不扣用户贡献值】' + rec.responsibleDescription);
			    }else{
			    	return value;
			    }
			}
		},
		{
			field : 'responsibilityJudgeAdvice',
			title : '责任判定建议',
			width : $(this).width() * 0.12,
			resizable : false,
			align : 'center'
		},
		{
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'memberPhone',
			title : '会员联系方式',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'memberLevel',
			title : '会员等级',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter:function(value, rec){
				if(0==value){
					 return "";
				}
			    return value;
			}
		},
		{
			field : 'createUser',
			title : '记录人',
			width : $(this).width() * 0.06,
			align : 'center'
		},
		{
			field : 'startTime',
			title : '救援开始时间',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'endTime',
			title : '救援结束时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}
		,{
			field : 'rescueAddress',
			title : '救援地址',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'reason',
			title : '事故经过',
			width : $(this).width() * 0.15,
			align : 'center'
		},{
			field : 'result',
			title : '救援结果',
			width : $(this).width() * 0.12,
			align : 'center'
		},
		{
			field : 'rescueProblem',
			title : '问题类型',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter:function(value, rec){
			    return "<span style='cursor: pointer;color: #2779AA;' onclick='checkRescueProblem(\""+rec.id+"\")'>查看</span>";
			}
		},
		{
			field : 'ifRepair',
			title : '是否维修',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val) {
				if(val=="1"){
					return "是";
				}else{
					return "否";
				}
			}
		},
		{
			field : 'picCount',
			title : '图片凭证',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter:function(value, rec){
			    if(value > 0){
			    	return "<span style='cursor: pointer;color: #2779AA;' onclick='checkPictures(\""+rec.id+"\")'>查看</span>";
				}else {
					return "无";
				}
			}
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	
	//网点明细对话框
	$("#rescueProblemGrid").datagrid( {
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'carProblemsDetail',
		pageSize : 20,
		idField : 'id',
		columns : [ [{
			field : 'dicCode',
			title : '问题类型',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val) {
				if(val=="CAR_PROBLEM"){
					return "车辆问题";
				}else if(val=="TECHNOLOGY_PROBLEM"){
					return "技术问题";
				}else{
					return "";
				}
			}
		} , {
			field : 'problemDescription',
			title : '问题详情',
			width : $(this).width() * 0.12,
			align : 'center'
		},{
			field : 'remark',
			title : '备注',
			width : $(this).width() * 0.08,
			align : 'center'
		}
		] ],
		pagination : true,
		rownumbers : true
	});
	
	$("#detailView").dialog( {
		width : "600",
		height : "400",
		top : "80",
		buttons : [{
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#detailView").dialog("close");
			}
		}]
	});
	
	$("#btnDetail").bind("click",function(){
		var selectedRows = $("#carRescueGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要查看的记录", "error");
		}else{
			$.ajax({
				type: 'POST',
				url: "selectRescueById",
				data: {id:selectedRows[0].id},
				success: function(data){
					var rescueDetail = $.parseJSON(data);
		            $("#lpn").html(selectedRows[0].lpn); 
					$("#reason").html(rescueDetail.reason);
					$("#responsiblePerson").html(rescueDetail.responsiblePerson);
					$("#responsiblePersonPhone").html(rescueDetail.responsiblePersonPhone);
					$("#startTime").html(selectedRows[0].startTime);
					$("#endTime").html(selectedRows[0].endTime);
					$("#myResult").html(rescueDetail.result);
					$("#appointmenCarRentMoney").html(rescueDetail.rescueAddress);
					if(selectedRows[0].status=="1"){
						$("#status").html("救援中");
					}else if(selectedRows[0].status=="0"){
						$("#status").html("已结束");
					}
				}, 
			})
			$("#detailView").dialog("open").dialog("setTitle", "救援详情");
		}
	});
	//修改救援相关信息
	$("#btnEdit").bind("click",function(){
		$("#carRescueViewForm").form("clear");
		var selectedRows = $("#carRescueGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		 }else if(selectedRows[0].status=="0"){
			 $.messager.alert("提示", "车辆救援已經結束", "error");
		 }else if(selectedRows[0].status=="2"){
             $.messager.alert("提示", "已取消的救援不可进行修改", "error");
         }else{
			 $("#e-id").val(selectedRows[0].id);
			 if (selectedRows[0].lpn.indexOf("•") < 0) {
				 $("#e-lpn-1").val(selectedRows[0].lpn.substring(0,2) +"•"+selectedRows[0].lpn.substring(2));
				 $("#e-lpn-2").html(selectedRows[0].lpn.substring(0,2) +"•"+selectedRows[0].lpn.substring(2));
			 }else{
				 $("#e-lpn-1").val(selectedRows[0].lpn);
				 $("#e-lpn-2").html(selectedRows[0].lpn);
			 }
			 
			 $("#e-responsiblePerson").textbox("setValue",selectedRows[0].responsiblePerson);
			 $("#e-responsiblePersonPhone").textbox("setValue",selectedRows[0].responsiblePersonPhone);
			 $("#e-reason").textbox("setValue",selectedRows[0].reason);
			 $("#carRescueView").dialog("open");
		 }
	});
	//car repair view
	$("#carRescueView").dialog( {
		width : "450",
		height : "320",
		top : "100",
		left:"400",
		modal:true,
		title:"车辆救援",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carRescueViewForm").form("submit",{
					url:'saveOrUpdateCarRescue',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result== "success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carRescueGrid").datagrid("reload");
							$("#carRescueView").dialog("close");
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
				$("#carRescueView").dialog("close");
			}
		}]
	});
	
	
	//结束救援
	$("#btnResume").bind("click",function(){
		$("#endRescueViewForm").form("clear");
		var selectedRows = $("#carRescueGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要结束救援的车辆", "error");
		 }else if(selectedRows[0].status=="0"){
			 $.messager.alert("提示", "车辆救援已结束", "error");
		 }else if(selectedRows[0].status=="2"){
             $.messager.alert("提示", "已取消的救援不可结束救援", "error");
         }else{
			 $("#endRescueViewForm").form("clear");
			 $("#e-id").val(selectedRows[0].id);
			 $("#responsibleType").combobox({  
			       onSelect: function () {  
			           var newPtion = $("#responsibleType").combobox('getText');  
			           if (newPtion == "其它(不扣用户贡献值)") {  
			        	   $('#responsibleDescription').textbox('setValue','');
			        	   document.getElementById("responsibleDescriptionId").style.display="block"; 
			           }  
			           else {  
			        	   $('#responsibleDescription').textbox('setValue',newPtion);
			        	   document.getElementById("responsibleDescriptionId").style.display="none";
			           }  
			       }  
			   });
			 $("#e-reason-1").val(selectedRows[0].reason);
		//	 $("#e-reason-2").html(selectedRows[0].reason);
			 $("#e-reason-2").textbox('setValue',selectedRows[0].reason);
			 if (selectedRows[0].lpn.indexOf("•") < 0) {
				 $("#s-lpn-1").val(selectedRows[0].lpn.substring(0,2) +"•"+selectedRows[0].lpn.substring(2));
				 $("#s-lpn-2").html(selectedRows[0].lpn.substring(0,2) +"•"+selectedRows[0].lpn.substring(2));
			 }else{
				 $("#s-lpn-1").val(selectedRows[0].lpn);
				 $("#s-lpn-2").html(selectedRows[0].lpn);
			 }
			 $("#endRescueView").dialog("open");
			 $("#end-id").val(selectedRows[0].id);
		 }
	});
	//弹出结束救援对话框
	$("#endRescueView").dialog({
		width : "600",
		height : "400",
		top : "100",
		left:"400",
		modal:true,
		title:"结束救援",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#endRescueViewForm").form("submit",{
					url:'endRescueUpdate',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						clearPictures();
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carRescueGrid").datagrid("reload");
							$("#endRescueView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result == "finish") {
							$.messager.alert("提示", "救援已结束,请刷新页面", "error");
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
				clearPictures();
				$("#endRescueView").dialog("close");
			}
		}]
	});
	
	//-------------------------------------------------------------------------------------
	
	// 问题与救援类型详情框
    $(".getProblemAndRescueTypeInfo").bind("click",function(){
    $("#RescueDetails").textbox('setValue','');
    $("#getProblemAndRescueTypeInfoView").dialog({
        "title":"救援问题列表",
		"top":"300px",
		"left":"200px",
		"width":"700px",
		"height":"500px"
    });
    $("#getProblemAndRescueTypeInfoView").dialog("open");
    $("#getProblemAndRescueTypeInfoGrid").datagrid({
        width : '700',
        height : '300',
        fit : true,
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : true,
        rownumbers : true,
        singleSelect : false,
        selectOnCheck : true,
        checkOnSelect : true,
        url : 'getProblemAndRescueTypeInfo',
        queryParams:{
            'tecProblem':'TECHNOLOGY_PROBLEM',
            'carProblem':'CAR_PROBLEM'
        },
        pageSize : 20,
        pagination : true,
        rownumbers : true,
        columns : [ [ {
            field : 'ck',
            checkbox : true
        },{
            field : 'dicCode',
            title : '问题类型',
            width : $(this).width() * 0.5,
            align : 'center',
			formatter : function(val, rec) {
				if (val == 'CAR_PROBLEM') {
					return "车辆问题";
				} else {
					return "技术问题";
					
				}
			}
        },{
            field : 'name',
            title : '问题描述',
            width : $(this).width() * 0.5,
            align : 'center'
        } ,{
            field : 'remark',
            title : '备注',
            width : $(this).width() * 0.9,
            align : 'center',
			formatter : function(val, rec) {
				if (rec.dicCode == 'TECHNOLOGY_PROBLEM'&&rec.name == '其它') {
					return "<input class='easyui-textbox' style='width:100%;height:100%' id='tecRemark'/>";
				} else if(rec.dicCode == 'CAR_PROBLEM'&&rec.name == '其它'){
					return "<input class='easyui-textbox' style='width:100%;height:100%' id='carRemark'/>";
				}else{
					return rec.name;
				}
			}
        }
        ]]
    });
})
	    $("#getProblemAndRescueTypeInfoView").dialog({
        top : "190",
        left:'400',
        modal:true,
        buttons : [{text : "确定",
            iconCls : "icon-save",
            handler : function() {
                var selectedRow = $("#getProblemAndRescueTypeInfoGrid").datagrid("getSelections");
                if(selectedRow.length <= 0){
                    $.messager.alert("提示", "请选择救援问题类型", "error");
                    return;
                }else{
                	var rescueProblemText = '';
                	var sysDicIds = '';
                	for(var i = 0; i < selectedRow.length;i++){
                		var JsonData = selectedRow[i];
                		var values;
                		if(JsonData.dicCode == 'TECHNOLOGY_PROBLEM'&&JsonData.name =='其它'){
                			 var tec = $("#tecRemark");
                			 values = tec[0].value;
                			  if(values.length<1){
                				  $.messager.alert("提示", "技术其它问题请输入描述内容", "error");
                				  return;
                			  }else{
                				  $("#tecProRemark").val(values);
                			  }
                			  JsonData.name = values;
                		}else if(JsonData.dicCode == 'CAR_PROBLEM'&&JsonData.name =='其它'){
	               			 var carP = $("#carRemark");
	               			 values = carP[0].value;
	               			 if(values.length<1){
	               				$.messager.alert("提示", "车辆其它问题请输入描述内容", "error");
	               				return;
	               			 }else{
	               				$("#carProRemark").val(values);
	               			 }
	               			JsonData.name = values;
                		}
                		if(i==0){
                			rescueProblemText = JsonData.name; 
                			sysDicIds = JsonData.id;
                		}else{
                			rescueProblemText = rescueProblemText + ";" +JsonData.name; 
                			sysDicIds = sysDicIds + "," + JsonData.id;
                		}
                	}
                	$("#RescueDetails").textbox('setValue',rescueProblemText);
                	$("#sysDicIds").val(sysDicIds);
                	$("#getProblemAndRescueTypeInfoView").dialog("close");
                }
            }},{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#getProblemAndRescueTypeInfoView").dialog("close");
            }
        }]
    });
	
	//
	function clearPictures(){
		for(var i=0;i<4;i++){
			var pic;
			if(i==0){
				pic	= document.getElementById("pictureFilePhoto");
			}else{
				pic = document.getElementById("pictureFilePhoto"+i);
			}
			pic.style.display = 'none';
			pic.src = '';
		}
	}
	//删除记录
	$("#btnDelete").bind("click",function(){
		var selectedRows = $("#carRescueGrid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除的记录", "error");
		 }else{
			var _id =  selectedRows[0].id;
			/*var _lpn =  selectedRows[0].lpn;*/
			$.messager.confirm("提示","确定要删除吗?",function(r){
				if(r){
					$.post("deleteCarRescueById",{id:_id},function(data){
						if(data == "success"){
							$.messager.alert("提示", "操作成功", "info");
							$("#carRescueGrid").datagrid("reload");
						}else{
							 $.messager.alert("提示", "操作失败", "error");
						}
					},"text");
				}
			});
		 }
	});
	
	// 弹出保存对话框
	$("#carRepairView").dialog({
		width : "450",
		height : "320",
		top : "100",
		left : "400",
		modal : true,
		title : "车辆维修",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#carRepairViewForm").form("submit", {
					url : 'rescue_to_repair_save',
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
						if (result == "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#carRepairView").dialog("close");
						} else if (result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						} else {
							$.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#carRepairView").dialog("close");
			}
		} ]
	});
});
//清空查询栏
function clearToolbar(){
	$('#toolbar').form('clear');
}

function checkRescueProblem(recId){
	$("#rescueProblemGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'recId' : recId
	});
	$("#rescueProblemDetailView").dialog("setTitle", "救援问题详情");
	$('#rescueProblemDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
}

//检查图片的格式是否正确,同时实现预览  
function setImagePreview(obj, localImagId, imgObjPreview) {  
	//alert(imgObjPreview.src);
    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); //可以上传的文件类型  
    if (obj.value == '') {
    	imgObjPreview.src = '';  
    	imgObjPreview.style.display = 'none';
        return false;  
    }  
    else {  
        var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; //这个文件类型正则很有用   
        ////布尔型变量  
        var isExists = false;  
        //循环判断图片的格式是否正确  
        for (var i in array) {  
            if (fileContentType.toLowerCase() == array[i].toLowerCase()) {  
                //图片格式正确之后，根据浏览器的不同设置图片的大小  
                if (obj.files && obj.files[0]) {  
                    //火狐下，直接设img属性   
                    imgObjPreview.style.display = 'block';  
                    imgObjPreview.style.width = '160px';  
                    imgObjPreview.style.height = '120px';  
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式   
                    imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);  
                }  
                else {  
                    //IE下，使用滤镜   
                    obj.select();  
                    var imgSrc = document.selection.createRange().text;  
                    //必须设置初始大小   
                    localImagId.style.display = "";
                    localImagId.style.width = "160px";  
                    localImagId.style.height = "120px";  
                    //图片异常的捕捉，防止用户修改后缀来伪造图片   
                    try {  
                        localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;  
                    }  
                    catch (e) {  
                        $.messager.alert("您上传的图片格式不正确，请重新选择!");  
                        return false;  
                    }  
                    imgObjPreview.style.display = 'none';  
                    document.selection.empty();  
                }  
                isExists = true;
                return true;  
            }  
        }  
        if (isExists == false) {  
            $.messager.alert("上传图片类型不正确!");  
            return false;  
        }  
        return false;  
    }  
} 
function checkPictures(reportId){
	$.post("getRescueEviPicsByReportId",{reportId:reportId},function(data){
		var arr = data.substr(1,data.length-2).split(",");
		for(var i=0;i<4;i++){
			var tr = document.getElementById("pic"+i);
		    tr.innerHTML = '';
		}
		for(var i = 0;i<arr.length;i++){
			var pic = "<img width='140' height='115' src='"+arr[i]+"' onmouseover='display("+i+")' onmouseout='disappear("+i+")'/><div id='box"+i+"' onmouseover='display("+i+")' onmouseout='disappear("+i+")' style='display: none;position: fixed;top:100px;left:600px;'><img style='height:400px;width:600px;'src='"+arr[i]+"'/></div>";
		    var tr = document.getElementById("pic"+i);
		    tr.innerHTML = pic;
		}
		$("#picEvidenceView").dialog("open").dialog("setTitle",'救援图片凭证');
	}
	,"text");
	$("#picEvidenceView").dialog("open").dialog("setTitle", "救援详情");
	$('#picEvidenceView').window('open').window('resize',{top: "300px", left:"600px"});
}
$("#picEvidenceView").dialog({});

//显示图片    
function over(imgid, obj) {  
	//大图显示的最大尺寸  4比3的大小 160 120
    maxwidth = 160;  
    maxheight = 120;  
    //1、宽和高都超过了，看谁超过的多，谁超的多就将谁设置为最大值，其余策略按照2、3    
    //2、如果宽超过了并且高没有超，设置宽为最大值    
    //3、如果宽没超过并且高超过了，设置高为最大值    
		if (img.width > maxwidth && img.height > maxheight) {  
	    	pare = (img.width - maxwidth) - (img.height - maxheight);  
	        if (pare >= 0){
				img.width = maxwidth;  
	        }else{
				img.height = maxheight;
	        }  
		}else if (img.width > maxwidth && img.height <= maxheight) {  
            img.width = maxwidth;
	    }else if(img.width <= maxwidth && img.height > maxheight){
	    	img.height = maxheight;
	    }
}
function display(id){
	document.getElementById("box"+id).style.display="block"; 
}
function disappear(id){
	document.getElementById("box"+id).style.display="none"; 
}