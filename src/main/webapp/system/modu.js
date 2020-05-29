$(function() {

	$('#moduGrid').treegrid({
		url : 'sys_modu_buidTree',
		idField : 'id',
		treeField : 'name',
		animate:false,
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}
		, {
			field : 'name',
			title : '名称',
			width : $(this).width() * 0.3,
			align : 'left'
		}, {
			field : 'remark',
			title : '级别',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val== "mkjd") {
					//return "<font color='#FF792B'>模块</font>";
					return "模块";
				} else if(val == "cdjd"){
					//return "<font color='#3BFF3B'>菜单</font>";
					return "菜单";
				}else if(val=="button"){
					//return "<font color='#2414FF'>按钮</font>";
					return "按钮";
				}else if(val=="gncd"){
					return "功能";
				}else{
					//return "<font color='red'>其他</font>";
					return "其他";
				}
			}
	  	}, {
			field : 'link',
			title : '链接',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'grade',
			title : '级别',
			width : $(this).width() * 0.12,
			align : 'center'
		}, {
			field : 'iconImg',
			title : '图标',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter : function(val, rec) {
				if (val != "") {
					return "<img src='ui_lib/css/themes/icons/" + val + "'/>";
				} else {
					return "";
				}
			}
		}, {
			field : 'isShow',
			title : '是否显示',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if (val == "1") {
					return "显示";
				} else {
					return "不显示";
				}
			}
		}, {
			field : 'sort',
			title : '排序',
			width : $(this).width() * 0.12,
			align : 'center'
		} ] ]
	});
	
	
});