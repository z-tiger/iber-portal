$(function() {
	
	//隐藏年月季度的标签和numberbox，即除了datetimebox之外的label
	$("label:not(.cls_label_datetime)").hide();
	// 根据不同周期（年、月、季度、自选）来显示相关label
	$('#period').combobox({
		onSelect : function(param) {
			onSelPeriod();
		}
	});
	function loadData(){
		setBeginAndEnd2();
		var options = $('#dataGrid').datagrid('options');
		options.url = 'member_original_refund_list.do';
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
        var phoneNumber = $('#phoneNumber').val();
		if(!begin || !end){
			$.messager.alert('提示','请输入日期！');
			return;
		}
		$('#dataGrid').datagrid('load',{
			'name' : $("#name").val(),
            'phoneNumber':phoneNumber,
			'begin' : begin,
			'end' : end
       });
	}
	
	//綁定enter
	$(document).keydown(function(event){
		if(event.keyCode==13){
			loadData();
		}
	});
	//清空
	$("#clearQuery").bind("click",function(){
		clearQueryForm();
	});
	
	function clearQueryForm(){
		$("#toolbar").form('clear');
	}
	//查询链接
	$("#btnQuery").bind("click",function(){
		loadData();
	});
	//导出链接
	$("#btnExport").bind("click", function() {
		setBeginAndEnd2();
		var name= $("#name").val();
		var phoneNumber= $("#phoneNumber").val();
		var begin = $("#hidBegin").val();
		var end = $("#hidEnd").val();
		$("#data").form("submit", {
			url : "member_original_refund_excel",
			onSubmit : function(param) {
				param.name = name;
				param.phoneNumber = phoneNumber;
				param.begin = begin;
				param.end = end;
			},
			success : function(result) {
			}
		});

	});
	
	$("#dataGrid").datagrid( {
		title : '原路退款报表',
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
		url : 'member_original_refund_list',
		pageSize : 100,// 设置分页属性的时候初始化页面大小
		pageList : [100,50,30,10],
		columns : [ [{
			field : 'mid',
			title : '会员ID',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'name',
			title : '会员姓名',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'money',
			title : '押金充值',
			width : $(this).width() * 0.09,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		},{
			field : 'createTime',
			title : '充值日期',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'refundMoney',
			title : '退款金额',
			width : $(this).width() * 0.09,
			align : 'center',
			formatter : function(val, rec) {
				n = (val/100).toFixed(2);
				var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			return n.replace(re, "$1,");	
			}
		},{
			field : 'applyCreateTime',
			title : '申请退款时间',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'lastHandleTime',
			title : '原路退回时间',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'lastHandleUser',
			title : '最后处理人',
			width : $(this).width() * 0.09,
			align : 'center'
		},{
			field : 'bankCategory',
			title : '退回账户类型',
			width : $(this).width() * 0.09,
			align : 'center',
			formatter : function(val){
				if(val == 'U'){
					return '银联';
				}else if(val == 'A'){
					return '支付宝';
				}else if(val == 'WX'){
					return '微信';
				}else if(val == 'T'){
					return '财付通';
				}else{
					return '其他';
				}
			}
		},{
			field : 'refundUserMoblie',
			title : '退回账户账号',
			width : $(this).width() * 0.09,
			align : 'center'
		}
		
		] ]
		
	});
});