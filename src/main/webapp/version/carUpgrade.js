$(function() {
	//table
	$('#versionGrid').datagrid({
		title : '升级管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		url : 'upgrade_list',
		queryParams:{
			'lpn':$("#s-lpn").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'lpn',
		columns : [ [ {
			field : 'id',
			checkbox : true
		}, {
			field : 'cityName',
			title : '城市',
			width : $(this).width() * 0.03,
			align : 'center'
		}, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.03,
			align : 'center'
		}, {
            field : 'brandName',
            title : '车型',
            width : $(this).width() * 0.03,
            align : 'center'
        }, {
            field : 'tboxVersion',
            title : 'tboxVersion',
            width : $(this).width() * 0.03,
            align : 'center'
        }, {
            field : 'status',
            title : '车辆状态',
            width : $(this).width() * 0.03,
            align : 'center',
            formatter : function(val, rec) {
                if (val =="empty"){
                    return "空闲" ;
                }else if (val =="charging"){
                    return "补电" ;
                }else if (val =="maintain"){
                    return "维护" ;
                }else if (val =="repair"){
                    return "维修" ;
                }else  {
                    return "运营中" ;
                }
            }

        }, {
			field : 'carRearviewVersionSoftTypeName',
			title : '后视镜版本类型',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'carRearviewVersionNo',
			title : '后视镜版本号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(parseFloat(rec.carVersionCode) < parseFloat(rec.rearviewVersionRecord)) { //车的记录数 < 新记录数
					//if(rec.rearviewBoxIsIncrement == 0){ //全量
						return "<a href=\"javascript:showUpgradeRecord('"+rec.lpn+"','rearview')\" >"+val+"</a> <font color='red'>【升】</font>" ;
					//}else{ //增量
					//	return "<a href=\"javascript:showUpgradeRecord('"+rec.lpn+"','rearview')\" >"+val+"</a> <font color='red'>【升】</font> <font color='#C76114'>【增】</font>" ;
					//}
				}else{
					return "<a href=\"javascript:showUpgradeRecord('"+rec.lpn+"','rearview')\" >"+val+"</a> <font color='green'>【新】</font> " ;
				}
			}
		}, {
			field : 'rearviewVersionId',
			title : '新后视镜版本ID',
			width : $(this).width() * 0.03,
			hidden: true ,
			align : 'center'
		},{
			field : 'rearviewVersionNo',
			title : '后视镜最新版本号',
			width : $(this).width() * 0.03,
			align : 'center'
		}, {
			field : 'boxVersionId',
			title : '新盒子版本ID',
			width : $(this).width() * 0.03,
			hidden: true ,
			align : 'center'
		}, {
			field : 'carBoxSoftTypeName',
			title : '宝盒版本类型',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'carBoxVersionCode',
			title : '宝盒版本号',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(parseFloat(rec.carBoxVersionCode) < parseFloat(rec.boxVersionNo)) { //宝盒的版本号 < 新版本号
					//if(rec.boxIsIncrement == 0){ //全量
						return "<a href=\"javascript:showUpgradeRecord('"+rec.lpn+"','"+rec.carBoxSoftType+"')\" >"+val+"</a> <font color='red'>【升】</font>" ;
					//}else{ //增量
					//	return "<a href=\"javascript:showUpgradeRecord('"+rec.lpn+"','box')\" >"+val+"</a> <font color='red'>【升】</font> <font color='#C76114'>【增】</font> " ;
					//}
				}else{
					return "<a href=\"javascript:showUpgradeRecord('"+rec.lpn+"','"+rec.carBoxSoftType+"')\" >"+val+"</a> <font color='green'>【新】</font> " ;
				}
			}
		}, {
			field : 'boxVersionNo',
			title : '宝盒最新版本号',
			width : $(this).width() * 0.03,
			align : 'center'
		}, {
            field : 'lastUploadTime',
            title : '最后一次数据上传时间',
            width : $(this).width() * 0.06,
            align : 'center'
        }] ],
		pagination : true,
		rownumbers : true
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#versionGrid").datagrid("load",{
			'lpn':$("#s-lpn").textbox("getValue"),
			'cityCode':$("#scityCode").combobox("getValue"),
            'status':$("#e-status").combobox("getValue"),
            'tboxVersion':$("#tboxVersion").combobox("getValue"),
            'brandName':$("#s-brand-name").textbox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		$("#versionForm").form("clear");
	}); 
	
	//宝盒升级
	$("#boxUpgrade").bind("click",function(){
		var boxIds = [];
		var lpns = [];
		var tboxVersions = [];
		var rows = $('#versionGrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			if(parseFloat(rows[i].carBoxVersionCode) < parseFloat(rows[i].boxVersionNo)){ //车宝盒版本小于系统版本时，发送升级指令
				boxIds.push(rows[i].boxVersionId);
				lpns.push(encodeURI(rows[i].lpn));
                tboxVersions.push(rows[i].tboxVersion);
			}
		}
		if(lpns.length >0) {
			$.ajax({
				url:"box_upgrade?boxIds="+boxIds+"&lpns="+lpns+"&tboxVersions="+tboxVersions,
                success:function(data){
                    if(data == "succ"){
                        alert("下发升级宝盒指令成功");
                    }else{
                        alert("下发升级宝盒指令失败");
                    }
				},
				error:function () {
                    alert("下发升级宝盒指令失败");
                }
			});
		}else{
			alert("请选择低版本车辆在下发指令");
		}
	}); 	
	
	//后视镜升级
	$("#rearviewUpgrade").bind("click",function(){
		var rearviewIds = [];
		var lpns = [];
		var rows = $('#versionGrid').datagrid('getSelections');
		for(var i=0; i<rows.length; i++){
			if(parseFloat(rows[i].carVersionCode) < parseFloat(rows[i].rearviewVersionRecord)) { //车的记录数 小于 系统记录数 ， 发送升级指令
				rearviewIds.push(rows[i].rearviewVersionId);
				lpns.push(encodeURI(rows[i].lpn));
			}
		}
		if(lpns.length >0) {
			$.post("rearview_upgrade?rearviewIds="+rearviewIds+"&lpns="+lpns,{"rearviewIds":rearviewIds,"lpns":lpns},function(data){
				if(data=="succ"){
					alert("下发升级后视镜指令成功");
					getData();//重新加载车辆
				}else{
					alert("下发升级后视镜指令失败");
					getData();//重新加载车辆
				}
			});
		}else{
			alert("请选择低版本车辆在下发指令");
		}
	}); 

	//全部宝盒升级
	$("#allBoxUpgrade").bind("click",function(){
		$.messager.confirm('提示','是否一键升级全部盒子?',function(result){
		    if (result){
				$.post("all_box_upgrade?rearviewIds", function(data){
					if(data == "succ"){
		    			alert("下发升级宝盒指令成功");
		    		}else{
		    			alert("下发升级宝盒指令失败");
		    		}
			    });
		    }
		});
	}); 
	//全部后视镜升级
	$("#allRearviewUpgrade").bind("click",function(){	
		$.messager.confirm('提示','是否一键升级全部后视镜?',function(result){
		    if (result){
				$.post("all_rearview_upgrade", function(data){
					if(data == "succ"){
		    			alert("下发升级后视镜指令成功");
		    		}else{
		    			alert("下发升级后视镜指令失败");
		    		}
			    });
		    }
		});
	}); 
});

//版本更新日志
function showUpgradeRecord(lpn,upgradeType){
	$("#showVersionView").dialog("open").dialog("setTitle", "版本更新日志");
	var url='version_upgrade_list?lpn='+encodeURI(lpn)+'&upgradeType='+upgradeType;
	$('#showVersionGrid').datagrid( {
		width : 'auto',
		height : 'auto',
		fit:true,
		fitColumns: true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		url : url,
		pageSize:20,
		columns : [ [ {
			field : 'lpn',
			title : '车牌号1',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null")
			       return "";
			    else
			       return val;
			}
		}, {
			field : 'upgradetype',
			title : '类型',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 'box')
			       return "宝盒";
			    else if(val == 'rearview')
			       return "后视镜";
			}
		}, {
			field : 'currentVersionNo',
			title : '当前版本',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'upgradeVersionNo',
			title : '更新版本',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'status',
			title : '更新状态',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 1)
			       return "<font color='red'>失败</font>";
			    else if(val == 0)
			       return "<font color='green'>成功</font>";
			}
		}, {
			field : 'createTime',
			title : '创建时间',
			width : $(this).width() * 0.3,
			align : 'center'
		},{
			field : 'remark',
			title : '描述',
			width : $(this).width() * 0.5,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});
} 