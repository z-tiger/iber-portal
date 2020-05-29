$(function() {
	//查询链接
	$("#btnQuery").bind("click",function(){
		$("#dataGrid").datagrid("load",{
			'lpn':$("#lpn2").textbox("getValue"),
			'cityCode':$("#cityCode").combobox("getValue"),
			'taskType':$("#_taskType").combobox("getValue"),
			'status':$("#_taskStatus").combobox("getValue"),
            'taskStatus':$("#taskStatus").combobox("getValue"),
            'employeeName':$("#employeeName").textbox("getValue"),
            'employeePhone':$("#employeePhone").textbox("getValue")
		});
	});

	$('#dataGrid').datagrid( {
		title : '任务池管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		rownumbers : true,
		singleSelect : true,
		url : 'task_pool_page',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [{
			field : 'ck',
			checkbox:true
		 },
		 {
			 field:'cityName',
			 title:'所属城市',
			 align:'center',
			 width : $(this).width() * 0.05,
		 }, {   field:'gridName',
			 title:'所属网格',
			 align:'center',
			 width : $(this).width() * 0.08,
			 formatter: function(val){
					if (val != '') {
						return val;
					}else {
						return "无";
					}
				},
		 },
		 {   field:'lpn',
			 title:'车牌号',
			 align:'center',
			 width : $(this).width() * 0.05,
		 },
		{   field:'carId',
			title:'车的id',
            hidden:'true'
		},
		{   field:'beginPark',
			title:'开始网点',
			align:'center',
            hidden:'true'
		},
		 {   field:'parkName',
			 title:'所属网点',
			 align:'center',
			 width : $(this).width() * 0.06,
		 },
		 {   field:'status',
			 title:'派单状态',
			 align:'center',
			 width : $(this).width() * 0.06,
			 formatter: function(val){
				if (val == '1') {
					return "未接单";
				}
				if (val == '2') {
					return "<font color='green'>已接单</font>";
				}
				if (val == '3') {
					return "已指派";
				}
                 if (val == '4') {
                     return "已取消";
                 }
				},
		 },
		{   field:'taskStatus',
			title:'任务状态',
			align:'center',
			width : $(this).width() * 0.06,
			formatter: function(val){

				if (val == '1') {
					return "<font color='green'>创建</font>";
				}
				if (val == '2') {
					return "正在进行";
				}
				if (val == '3') {
					return "已完成";
				}
                if (val == '4') {
                    return "已取消";
                }
			},
		},
		 {   field:'type',
			 title:'类型',
			 align:'center',
			 width : $(this).width() * 0.06,
             formatter: function(val){
                 /*1.调度   2.维护  3.充电    4.维修    5.救援  6.其他*/
                 if (val == '1') {
                     return "调度";
                 }
                 if (val == '2') {
                     return "维护";
                 }
                 if (val == '3') {
                     return "充电";
                 }
                 if (val == '4') {
                     return "维修";
                 }
                 if (val == '5') {
                     return "救援";
                 }
                 if (val == '6') {
                     return "其他";
                 }
             }
		 },
          {  field:'poolCreateTime',
                title:'任务生成时间',
                align:'center',
                width : $(this).width() * 0.10,
          },
		 {   field:'empName',
			 title:'执行人',
			 align:'center',
			 width : $(this).width() * 0.06
		  },
            {   field:'empPhone',
                title:'执行人电话',
                align:'center',
                width : $(this).width() * 0.06
            },
		  {  field:'taskCreateTime',
				 title:'任务接单时间',
				 align:'center',
				 width : $(this).width() * 0.10,
		  },
		  {  field:'doneTime',
				 title:'任务结束时间',
				 align:'center',
				 width : $(this).width() * 0.10,
		  },
			{  field:'doneExpendTime',
				title:'任务耗时',
				align:'center',
				width : $(this).width() * 0.08,
			},
		  {  field:'doneRemark',
			 title:'任务反馈',
			 align:'center',
			 width : $(this).width() * 0.13,
		  }
		] ],
		pagination : true,
		rownumbers : true,
		rowStyler: function (index, row) {
            if(row.timeOut>=5&&row.status==1){
                return 'color:red;';
            }

        }
	});

    //构造对话框
    $("#editView").dialog( {
        width : "500",
        height : "auto",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#editViewForm").form("submit", {
                    url : "saveOrUpdateTask",
                    onSubmit : function() {
                        $.messager.progress({
                            text:"正在加载，请稍等！"
                        });
                        var flag = $(this).form("validate");
                        if(!flag){
                            $.messager.progress('close');
                        }
                        return flag;
                    },
                    success : function(result) {
                        $.messager.progress('close');
                        if (result == "success") {
                            $.messager.alert("提示", "指派成功", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#editViewForm").form("clear");
                            $("#editView").dialog("close");
                            var locations = $("#locations").val();
                            data = eval(locations);
                            mapInit(data);
                        }else if(result == "fail"){
                            $.messager.alert("提示", "指派失败", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#editViewForm").form("clear");
                            $("#editView").dialog("close");
                        }else if(result == "existing"){
                            $.messager.alert("提示", "该车辆已存在相同的任务，请勿重复创建", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#editViewForm").form("clear");
                            $("#editView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#editView").dialog("close");
            }
        }]
    });

    $("#addView").dialog( {
        width : "500",
        height : "auto",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#addViewForm").form("submit", {
                    url : "task_pool_save",
                    onSubmit : function() {
                        $.messager.progress({
                            text:"正在加载，请稍等！"
                        });
                        var flag = $(this).form("validate");
                        if(!flag){
                            $.messager.progress('close');
                        }
                        return flag;
                    },
                    success : function(result) {
                        $.messager.progress('close');
                        if (result =='ok'){
                            $.messager.alert("提示", "创建成功", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#addViewForm").form("clear");
                            $("#addView").dialog("close");
                        }else if (result == 'exist'){
                            $.messager.alert("提示", "该车辆已经存在任务池中！", "error");
                        }else {
                            $.messager.alert("提示", "创建失败", "error");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addView").dialog("close");
            }
        }]
    });

	//add
    $("#btnAdd").bind("click",function(){
        $("#addView").dialog({title:"生成任务池任务"});
        $("#addView").dialog("open");
    });

    $('#e-taskType').combobox({
        onChange: function (newValue, oldValue) {
           if(newValue=="3"){
               $('#endParkId').combobox({required: false})
           }else{
               $('#endParkId').combobox({required: true})
           }
        }
    });

    $('#carLpn').combobox({
        valueField : 'text',
        textField : 'text',
        url: 'query_total_lpn',
        editable:true,
        filter: function(q, row){
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配
        }
    });

    $('#endParkId').combobox({
        valueField : 'id',
        textField : 'text',
        url: 'get_park_list',
        required: true,
        editable:true,
        filter: function(q, row){
            var opts = $(this).combobox('options');
            return row[opts.textField].indexOf(q) >= 0;//这里改成>=即可在任意地方匹配
        },
    });
    //指派
    $("#btnAllocation").bind("click",function(){
        $("#editViewForm").form("clear");
        var selectedRows = $("#dataGrid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择要修改的记录", "error");
        }else if(selectedRows[0].status=='4'){
            $.messager.alert("提示", "任务已取消！", "error");
        }else if(selectedRows[0].status=='2'){
            $.messager.alert("提示", "任务已被接单！", "error");
        }else if(selectedRows[0].status=='3'){
            $.messager.alert("提示", "任务已被指派！", "error");
        }else if(selectedRows[0].status=='1'){
            $("#requestType").val("1");//代表是任务池分发的
            $("#taskType").val(selectedRows[0].type);
            $("#lpn").val(selectedRows[0].carId);
            $("#beginParkId").val(selectedRows[0].beginPark);
            $("#taskId").val(selectedRows[0].id);
            $("#editView").dialog({title:"任务指派"});
            $("#editView").dialog("open");

        }
    });
    //取消
    $("#btnCancel").bind("click",function(){
        var selectedRows = $("#dataGrid").datagrid("getSelections");
        if(selectedRows[0].status=='4'){
            $.messager.alert("提示", "任务已取消！", "error");
            return;
        }
        $.messager.confirm("提示","确定要取消该条任务池记录吗?",function(r){
            if(r){
                $.ajax({
                    type: 'POST',
                    url: "task_pool_cancel",
                    data: {
                        id:selectedRows[0].id
                    },
                    success: function(data){
                        if("ok" == data){
                            $.messager.alert("提示", "取消成功" , "info");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "取消失败,车辆不为充电或者运营状态" , "error");
                        }
                    }
                })
            }
        });
    })
});


