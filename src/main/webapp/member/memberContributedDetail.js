
$(function() {
	
	//查询链接
	$("#btnQuery").bind("click",function(){
		$("#dataGrid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'memberLevel':$("#smemberLevel").combobox("getValue"),
			'memberName':$("#s-memberName").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue")
		});
	});
	//导出excel
	$("#btnExport").bind("click", function() {
		var cityCode = $("#scityCode").combobox("getValue");
	    var memberLevel = $("#smemberLevel").combobox("getValue");
	    var memberName = $("#s-memberName").textbox("getValue");
	    var phone = $("#s-phone").textbox("getValue");
		$("#contributedForm").form("submit", {
			url : "export_contributed_report?cityCode="+cityCode+"&memberLevel="+memberLevel+"&memberName="+memberName+"&phone="+phone,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	
	
	$('#dataGrid').datagrid( {
		title : '贡献值明细管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'dataListMemberContributedDetail',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [{ 
			field : 'ck',
			checkbox:true
		},
		{
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.1,
			align : 'center'	
		},   
		  {
			field : 'memberName',
			title : '会员姓名',
			align : 'center',
			width : $(this).width() * 0.1
			
		}, {
			field : 'phone',
			title : '手机号码',
			align : 'center',
			width : $(this).width() * 0.2
			
		},{
			field : 'levelCode',   
			title : '会员等级',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter: function(value, row, index){
				if (value == 0) {//表示被系统自动拉入黑名单
					return "黑名单";
				}else if(value == 1){
					return "一星会员";
				}else if(value == 2){
					return "二星会员";
				}else if(value == 3){
					return "三星会员";
				}else if(value == 4){
					return "四星会员";
				}else if(value == 5){
					return "五星会员";
				}
			}
		},{
			field : 'memberContributedVal',
			title : '贡献值',
			width : $(this).width() * 0.2,
			align : 'center',
			formatter:function(value,row,index){
				if(row.isIncrease == 0){
					return "-"+row.memberContributedVal;
				}else{
					return row.memberContributedVal;
				}
			}
		},{
			field:'action',
			title:'操作',
			align:'center',
			width : $(this).width() * 0.08,
			formatter:function(value, row, index){
				return "<a href='javascript:void(0)' onclick=getContributedDetail('"
				+ row.memberId + "')>" + "查看贡献值明细" + "</a>";
			}
		}
		] ],
		pagination : true,
		rownumbers : true
	});
	
	//贡献值明细对话框
	$("#contributedDetailDataGrid").datagrid( {
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		url : 'getContributedDetail.do',
		pageSize : 20,
		idField : 'id',
		columns : [ [{
			field : 'parentName',
			title : '分类',
			width : $(this).width() * 0.08,
			align : 'center'
		} , {
			field : 'childrenName',
			title : '子分类',
			width : $(this).width() * 0.08,
			align : 'center'
		}, 
		{
			field : 'contributeVal',
			title : '增减贡献值',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter:function(value,row,index){
				if(row.isIncrease == 0){
					return "-"+row.contributeVal;
				}else{
					return row.contributeVal;
				}
			}
		},
		{
			field : 'createName',   
			title : '操作人员',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if (val) {
					return val;
				}else {
					return "系统";
				}
			}
		},{
			field : 'createTime',   
			title : '操作时间',
			width : $(this).width() * 0.08,
			align : 'center',
		}
		] ],
		pagination : true,
		rownumbers : true
	});
})

function getContributedDetail(memberId){
	$("#contributedDetailDataGrid").datagrid("load", {// 在请求远程数据时发送额外的参数。
			'memberId' : memberId,
	});
	
	$("#contributedDetailView").dialog("setTitle", "贡献值明细列表");
	$('#contributedDetailView').window('open').window('resize',{top: "200px", left:" 200px"});
}