$(function() {
    //查询链接
    $("#btnQuery").bind("click",function(){
        $("#dataGrid").datagrid("load",{
            'cityCode':$("#cityCode").combobox("getValue"),
            'area':$("#area").textbox("getValue")
        });
    });

    // 清空
    $("#clearQuery").bind("click", function() {
        $('#cityCode').textbox('setValue', '');
        $('#area').textbox('setValue', '');
    });

    $('#dataGrid').datagrid( {
        title : '地区管理',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        rownumbers : true,
        singleSelect : true,
        url : 'area_management_page',
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
                hidden:'true'
            },{
                field:'areaName',
                title:'区域名称',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'bindNum',
                title:'绑定网点个数',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'longitude',
                title:'经度',
                align:'center',
                hidden:'true',
                width : $(this).width() * 0.05,
            },{
                field:'latitude',
                title:'纬度',
                align:'center',
                hidden:'true',
                width : $(this).width() * 0.05,
            },
            {  field:'creator',
                title:'创建人',
                align:'center',
                width : $(this).width() * 0.10,
            },
            {  field:'modifier',
                title:'最后修改人',
                align:'center',
                width : $(this).width() * 0.10,
            },
            {   field:'create_time',
                title:'创建时间',
                align:'center',
                width : $(this).width() * 0.06
            }
        ] ],
        pagination : true,
        rownumbers : true
    });


    $('#dataGridPark').datagrid( {
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        rownumbers : true,
        singleSelect : false,
        url : 'area_management_park',
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
                field:'parkName',
                title:'网点名称',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'category',
                title:'网点类型',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'address',
                title:'网点地址',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'longitude',
                title:'经度',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'latitude',
                title:'纬度',
                align:'center',
                width : $(this).width() * 0.05,
            }
        ] ],
        pagination : true,
        rownumbers : true
    });

    $('#dataGridParkBind').datagrid( {
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : true,
        nowrap : false,
        striped : true,
        collapsible : false,
        rownumbers : true,
        singleSelect : false,
        url : 'area_management_unbind',
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
                field:'parkName',
                title:'网点名称',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'category',
                title:'网点类型',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'areaName',
                title:'已绑区域',
                align:'center',
                width : $(this).width() * 0.05,
            },{
                field:'address',
                title:'地址',
                align:'center',
                width : $(this).width() * 0.05,
            }
        ] ],
        pagination : true,
        rownumbers : true
    });




    $("#addView").dialog( {
        width : "800",
        height : "auto",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#addViewForm").form("submit", {
                    url : "area_management_saveorupdate",
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
                            $.messager.alert("提示", "该城市区域名称已存在！", "error");
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
        $("#addView").dialog({title:"保存区域网点"});
        $("#addViewForm").form("clear");
        $("#addView").dialog("open");
    });

    $("#btnEdit").bind("click",function(){
        $("#addView").dialog({title:"编辑区域网点"});
        $("#addView").dialog("open");
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择编辑项目", "error");
            return;
        }else{
            $('#addViewForm').form('load',selectedRow[0])
            var _latitude = selectedRow[0].latitude;
            var _longitude = selectedRow[0].longitude;
            var opt = {
                level: 180, //设置地图缩放级别
                center: new AMap.LngLat(_longitude,_latitude) //设置地图中心点
            }
            var mapObj = new AMap.Map("iMap", opt);
            new AMap.Marker({
                map:mapObj,
                icon: new AMap.Icon({
                    image: "images/Map_Marker.png",
                    size:new AMap.Size(24,24)
                }),
                position: new AMap.LngLat(_longitude,_latitude),
                offset: new AMap.Pixel(-5,-30)
            });
        }
    });

    $("#btnBind").bind("click",function(){
        $("#bindView").dialog({title:"区域网点"});

        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择一行", "error");
            return;
        }else {
            /*$('#bindForm').form('load', selectedRow[0])*/
            $("#areaid").val(selectedRow[0].id)
            $("#dataGridPark").datagrid("load",{
                'id':selectedRow[0].id,
                'park':$("#park").textbox("getValue"),
                'cityCode':$("#city_code").combobox("getValue")
            });
        }
        $("#bindView").dialog("open");
    });

    //查询链接
    $("#ParkbtnQuery").bind("click",function(){
        $("#dataGridPark").datagrid("load",{
            'id':$("#areaid").val(),
            'park':$("#park").textbox("getValue"),
            'cityCode':$("#city_code").combobox("getValue")
        });
    });

    // 清空
    $("#ParkclearQuery").bind("click", function() {
        $('#park').textbox('setValue', '');
        $('#city_code').combobox('setValue', '');
    });

    $("#btnBindPark").bind("click",function(){
        $("#addParkView").dialog({title:"绑定网点"});
        $("#addParkView").dialog("open");
        $("#parkName").textbox("setValue","");
        $("#city_code2").combobox("setValue","");
        $("#address").textbox("setValue","");
        $("#dataGridParkBind").datagrid("load",{
            'id':$("#areaid").val(),
            'park':$("#parkName").textbox("getValue"),
            'cityCode':$("#city_code2").combobox("getValue"),
            'address':$("#address").textbox("getValue")
        });
    });

    $("#btnRemove").bind("click",function(){
        var selectedRow = $("#dataGridPark").datagrid("getSelections");
        if (selectedRow.length<= 0){
            $.messager.alert("提示", "请选择一行", "error");
            return;
        }

        $.messager.confirm("提示","确定要移除吗?",function(r){
            if(r){
                var ids =new Array();
                for (var i = 0;i<selectedRow.length;i++){
                    ids[i]=selectedRow[i].id
                }
                $.ajax({
                    type : "POST",
                    url : "unbindAreaPark",
                    data:{
                        ids:JSON.stringify(ids)
                    },
                    success : function(data) {
                        if (data =='ok'){
                            $.messager.alert("提示", "移除成功", "info");
                            $("#dataGridPark").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "移除失败", "error");
                        }
                    }
                });
            }
        });

    });

    $("#parkQuery").bind("click",function(){
        $("#dataGridParkBind").datagrid("load",{
            'id':$("#areaid").val(),
            'park':$("#parkName").textbox("getValue"),
            'cityCode':$("#city_code2").combobox("getValue"),
            'address':$("#address").textbox("getValue")
        });
    });

    //构造保存对话框
    $("#addParkView").dialog( {
        width : "800",
        height : "400",
        top : "80",
        buttons : [ {
            text : "添加到区域网点",
            iconCls : "icon-save",
            handler : function() {
                var selectedRow = $("#dataGridParkBind").datagrid("getSelections");
                if (selectedRow.length<= 0){
                    $.messager.alert("提示", "请选择一行", "error");
                    return;
                }
                var ids =new Array();
                for (var i = 0;i<selectedRow.length;i++){
                    ids[i]=selectedRow[i].id
                }
                $.ajax({
                    type : "POST",
                    url : "bindAreaPark",
                    data:{
                        ids:JSON.stringify(ids),
                        areaId:$("#areaid").val()
                    },
                    success : function(data) {
                        if (data =='ok'){
                            $.messager.alert("提示", "添加成功", "info");
                            $("#dataGridParkBind").datagrid("reload");
                            $("#dataGridPark").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "添加失败", "error");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addParkView").dialog("close");
            }
        }]
    });

});


