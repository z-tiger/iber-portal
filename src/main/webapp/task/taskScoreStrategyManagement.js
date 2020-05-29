$(function() {
    //查询链接
    $("#btnQuery").bind("click",function(){
        $("#dataGrid").datagrid("load",{
            'cityCode':$("#cityCode").combobox("getValue"),
            'type':$("#type").combobox("getValue"),
            'maintain':$("#maintain").combobox("getValue")
        });
    });

    // 清空
    $("#clearQuery").bind("click", function() {
        $('#cityCode').textbox('setValue', '');
        $('#type').textbox('setValue', '');
        $('#maintain').textbox('setValue', '');

    });

    $('#dataGrid').datagrid( {
        title : '任务积分策略管理',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        rownumbers : true,
        singleSelect : true,
        url : 'task_score_strategy_page',
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
            },{
                field:'cityCode',
                title:'所属城市',
                align:'center',
                hidden:'true',
                width : $(this).width() * 0.05,
            },{
                field:'maintainType',
                title:'维修类型',
                align:'center',
                hidden:'true',
                width : $(this).width() * 0.05,
            },
            {   field:'type',
                title:'类型',
                align:'center',
                width : $(this).width() * 0.06,
                formatter: function(val){
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
                },
            },
            {
                field:'maintain_name',
                title:'维修大类',
                align:'center',
                width : $(this).width() * 0.06
            },
            {   field:'minMileage',
                title:'最小距离(KM)',
                align:'center',
                width : $(this).width() * 0.06
            },
            {  field:'maxMileage',
                title:'最大距离(KM)',
                align:'center',
                width : $(this).width() * 0.10,
            },
            {   field:'score',
                title:'分数',
                align:'center',
                width : $(this).width() * 0.06
            },
            {   field:'create_time',
                title:'创建时间',
                align:'center',
                width : $(this).width() * 0.06
            },
            {  field:'modifier',
                title:'最后修改人',
                align:'center',
                width : $(this).width() * 0.10,
            },
            {  field:'creator',
                title:'创建人',
                align:'center',
                width : $(this).width() * 0.10,
            }
        ] ],
        pagination : true,
        rownumbers : true
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
                    url : "task_score_strategy_saveorupdate",
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
                        }else if (result=="exist") {
                            $.messager.alert("提示", "相同条件的分值已经存在！", "error");
                        }else{
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
        $("#addView").dialog({title:"保存任务积分策略"});
        $("#addViewForm").form("clear");
        $("#addView").dialog("open");
    });

    $("#btnEdit").bind("click",function(){
        $("#addView").dialog({title:"编辑任务积分策略"});
        $("#addView").dialog("open");
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择编辑项目", "error");
        }else{
            $('#addViewForm').form('load',selectedRow[0])
        }
    });


    $('#e-taskType').combobox({
        onChange: function (newValue, oldValue) {

            if(newValue=="1"){
                $("#min_mileage").numberbox({ "disabled":false });
                $("#max_mileage").numberbox({ "disabled":false });
                $("#maintain2").combobox({ "disabled":true });
            }
            if(newValue=="2"){
                $("#min_mileage").numberbox({ "disabled":true });
                $("#max_mileage").numberbox({ "disabled":true });
                $("#maintain2").combobox({ "disabled":false });
            }
            if(newValue=="3"){
                $("#min_mileage").numberbox({ "disabled":true });
                $("#max_mileage").numberbox({ "disabled":true });
                $("#maintain2").combobox({ "disabled":true });
            }
            $('#maintain2').combobox('setValue', '');
            $('#min_mileage').textbox('setValue', '');
            $('#max_mileage').textbox('setValue', '');
        }
    });

});


