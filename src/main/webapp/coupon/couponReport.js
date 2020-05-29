$(function() {
	
	//隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();

	//根据不同周期（年、月、季度、自选）来显示相关label
	$('#period').combobox({
		onSelect: function(param){
			onSelPeriod();
		}
	});

	
	
	//查询链接
	$(document).keydown(function(event){
		if(event.keyCode==13){
		  $('#dataGrid').datagrid('load',{
			    'title' : $("#_title").textbox("getValue"),
				'begin' : $("#hidBegin").val(),
				'end' : $("#hidEnd").val()
          });
		}
	});
	//查询链接
	$("#btnQuery").bind("click",function(){
		var period = $("input[name='period']").val();
		//如果没选周期，默认为自选区间
		if(null == period){
			period = 0;
		}
		setBeginAndEnd(period);
	    $('#dataGrid').datagrid('load',{
	    	'title' : $("#_title").textbox("getValue"),
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val()
        });
	});
	
	$("#dataGrid").datagrid( {
		title : '优惠券报表',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'coupon_report_getGroupByTitle.do',
		queryParams : {// 在请求远程数据时发送额外的参数。
					'title' : $("#_title").textbox("getValue"),
					'begin' : $("#hidBegin").val(),
					'end' : $("#hidEnd").val()
				},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [{
			field : 'ck',
			checkbox:true
		}, {
			field : 'title',
			title : '活动名称',
			width : $(this).width() * 0.2,
			align : 'center'
		}, {
			field : 'totalAmount',
			title : '优惠券总额',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter : function(val, rec) {
				if(val != 0){
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    v = n.replace(re, "$1,");
					return "<a href='javascript:void(0)' onclick=\"getCouponDetail('"
						+ rec.title + "','total')\">" + v + "</a>";
				}else{
					return 0;
				}
			}
		},  {
			field : 'collectedAmount',
			title : '已领取总额',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter : function(val, rec) {
				if(val != 0){
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    v = n.replace(re, "$1,");
					return "<a href='javascript:void(0)' onclick=\"getCouponDetail('"
						+ rec.title + "','collected')\">" + v + "</a>";
				}else{
					return '0.00';
				}
			}
		},  {
			field : 'invalidAmount',
			title : '已作废总额',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter : function(val, rec) {
				if(val != 0){
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    v = n.replace(re, "$1,");
					return "<a href='javascript:void(0)' onclick=\"getCouponDetail('"
						+ rec.title + "','invalid')\">" + v + "</a>";
				}else{
					return '0.00';
				}
			}
		},  {
			field : 'usedAmount',
			title : '已消费总额',
			width : $(this).width() * 0.15,
			align : 'center',
			formatter : function(val, rec) {
				if(val != 0){
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    v = n.replace(re, "$1,");
					return "<a href='javascript:void(0)' onclick=\"getCouponDetail('"
						+ rec.title + "','used')\">" + v + "</a>";
				}else{
					return '0.00';
				}
			}
		}
		
		] ],
		pagination : true,
		rownumbers : true
	});
	

	//明细对话框
	$("#couponDetailDataGrid").datagrid( {
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		url : 'couponDetail.do',
		pageSize : 20,
		idField : 'id',
		columns : [ [{
			field : 'batchNo',
			title : '批次号',
			width : $(this).width() * 0.08,
			align : 'center'
		} , {
			field : 'couponNo',
			title : '优惠券编号',
			width : $(this).width() * 0.08,
			align : 'center'
		}, 
		{
			field : 'balance',
			title : '面额/折扣',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
			    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
   			    return n.replace(re, "$1,");	
			}
		},
		{
			field : 'startTime',   
			title : '生效时间',
			width : $(this).width() * 0.1,
			align : 'center'
		},
		{  
			field : 'endTime',   
			title : '失效时间',
			width : $(this).width() * 0.1,
			align : 'center'
		},
		{
			field : 'status',
			title : '是否领用',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1"){
			       	return "<font color='red'>是</font>";
				}else if(val == "0"){
				   	return "<font color='green'>否</font>";
			    }else{
			    	return "";
			    }
			}
		},{
			field : 'useStatus',
			title : '是否使用',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "1"){
			       	return "<font color='red'>是</font>";
				}else if(val == "0"){
				   	return "<font color='green'>否</font>";
			    }else{
			    	return "";
			    }
			}
		},{
			field : 'memberName',
			title : '会员姓名',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "0"){
			       	return "";
			    }else{
			    	return val;
			    }
			}
		},{
			field : 'useTime',
			title : '使用时间',
			width : $(this).width() * 0.1,
			align : 'center'
		}
		
		] ],
		pagination : true,
		rownumbers : true
	});

	$("#couponDetailView").dialog( {
		width : "1000",
		height : "500",
		top : "80",
	});
			
});



/**
 * 打开总额明细对话框
 */
function getCouponDetail(title, type){
	$("#_title").val(title);
	$("#_type").val(type);
	$("#couponDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'title' : title,
			'begin' : $("#hidBegin").val(),
			'end' : $("#hidEnd").val(),
			'type' : type
	});
	//根据不同的统计类型来定对话框标题
	var titlePre = "";
	switch(type){
		case "total":
			titlePre = "全部";
			break;
		case "collected":
			titlePre = "已领取";
			break;
		case "invalid":
			titlePre = "已作废";
			break;
		case "used":
			titlePre = "已消费";
			break;
	}
	$("#couponDetailView").dialog("setTitle", titlePre + "--优惠券明细");
	$('#couponDetailView').window('open').window('resize',{top: "100px", left:" 100px"});
}
$(function(){
	$("#btn").bind("click",function(){
		search();
	})
	$("#btnClear").bind("click",function(){
		$("#_batch_no").textbox('setValue','');
		$("#memberName").textbox('setValue','');
	})
})
function search() {
	var batNo = $("#_batch_no").textbox('getValue');
	var memberName = $("#memberName").textbox('getValue');
	$('#couponDetailDataGrid').datagrid('load', {
		"batchNo" : batNo,
		"memberName" : memberName,
		'title' : $("#_title").val(),
		'begin' : $("#hidBegin").val(),
		'end' : $("#hidEnd").val(),
		'type' : $("#_type").val()
	});
}

