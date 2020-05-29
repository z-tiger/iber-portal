$(function(){
	
	$('#grid').datagrid({
		title : '会员账户冻结日志',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
		url : 'member_card_freeze_log_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'reason':$("#s-reason").textbox("getValue"),
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'createTime',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '所属城市',
            width : $(this).width() * 0.16,
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.16,
			align : 'center'
		},{
			field : 'reason',
			title : '原因',
			width : $(this).width() * 0.28,
			align : 'center',
			formatter:function (val,rec) {
				if (rec.status == 0){
                	return '<span style="color:red;">'+val+'</span>';
				}else{
                    return '<span style="color:black;">'+val+'</span>';
				}
            }
		},{
			field : 'createName',
			title : '操作人',
			width : $(this).width() * 0.16,
			align : 'center'
		},{
			field : 'createTime',
			title : '操作时间',
			width : $(this).width() * 0.12,
			align : 'center'
		}] ],
		pagination : true,
		rownumbers : true
	});
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'reason':$("#s-reason").textbox("getValue"),
		});
	});

	$("#btnExport").click(function () {
		var cityCode = $("input[name='cityCode']").val();
		var name = $("input[name='name']").val();
		var phone = $("input[name='phone']").val();
		var reason = $("input[name='reason']").val();
		$("#data").form("submit",{
			url:"export_freeze_log?cityCode="+cityCode+"&name="+name+"&phone="+phone+"&reason="+reason,
            onSubmit : function() {

            },
            success : function(result) {
            }
		});
    })
});