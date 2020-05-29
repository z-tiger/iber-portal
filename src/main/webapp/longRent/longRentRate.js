$(function () {
    //查询链接
    $(document).keydown(function (event) {
        if (event.keyCode === 13) {
            loadData()
        }
    });

    function loadData() {
        var cityCode = $("#city_code").combobox('getValue');
        var carTypeId = $("#carTypeId").combobox('getValue');
        var from = $("#queryDateFrom").datebox('getValue');
        var to = $("#queryDateTo").datebox('getValue');
        $('#dataGrid').datagrid('load', {
            carTypeId: carTypeId,
            from: from,
            to: to,
            cityCode: cityCode
        });
    }

    var carType;
    var cityData;

    //查询链接
    $("#btnQuery").bind("click", loadData);

    $('#dataGrid').datagrid({
        title: '日租计费',
        width: 'auto',
        height: 'auto',
        fit: true,
        fitColumns: true,
        nowrap: true,
        striped: true,
        collapsible: true,
        singleSelect: true,
        url: 'long_rent_rate_list',
        pageSize: 30,
        pageList: [50, 30, 10],
        pageIndex: 1,
        idField: 'id',
        columns: [[{
            field: 'id',
            checkbox: true
        }, {
            field: 'cityCode',
            title: '地区',
            width: $(this).width() * 0.2,
            align: 'center',
            formatter: function (value) {
                if (!cityData){
                    cityData = $("#city_code").combobox('getData');
                }
                $.each(cityData,function (i,item) {
                    if (item.id == value){
                        value = item.text;
                    }
                });
                return value;
            }
        }, {
            field: 'carTypeId',
            title: '车辆型号',
            width: $(this).width() * 0.2,
            align: 'center',
            formatter: function (value) {
                if (!carType){
                    carType = $("#carTypeId").combobox('getData');
                }
                $.each(carType,function (i,item) {
                    if (item.id == value){
                        value = item.text;
                    }
                });
                return value;
            }
        }, {
            field: 'rentDate',
            title: '日期',
            width: $(this).width() * 0.2,
            align: 'center',
            formatter: function (value) {
                if (value) {
                    var d = new Date();
                    d.setTime(value);
                    return d.toLocaleDateString()
                }
            }
        },
            {
                field: 'money',
                title: '日租费（元）',
                width: $(this).width() * 0.2,
                align: 'center',
                formatter: function (val) {
                    if (!val)
                        return "";
                    else
                        return (val / 100).toFixed(2);
                }
            }
        ]],
        pagination: true,
        rownumbers: true
    });


    //添加用户链接
    $("#btnAdd").bind("click", function () {
        clearForm();
        $("#addView").dialog("open").dialog("setTitle", "添加计费");
    });

    //编辑用户链接
    $("#btnEdit").bind("click", function () {
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if (selectedRow.length <= 0) {
            $.messager.alert("提示", "请选择要编辑的计费策略", "error");
        } else {
            var JsonData = selectedRow[0];
            $("#updateId").val(JsonData.id);
            $("#updateMoney").textbox('setValue',JsonData.money/100);
            var d = new Date();
            d.setTime(JsonData.rentDate);
            $("#date").text(d.toLocaleDateString());
            $("#updateView").dialog("open").dialog("setTitle", "修改计费");
        }
    });

    //构造对话框
    $("#addView").dialog({
        width: "630",
        height: "230",
        top: "80",
        buttons: [{
            text: "保存",
            iconCls: "icon-save",
            handler: function () {
                $("#addForm").form("submit", {
                    url: "long_rent_rate_add",
                    onSubmit: function () {
                        $.messager.progress({
                            text: "正在加载，请稍等！"
                        });
                        var flag = $(this).form("validate");
                        if (!flag) {
                            $.messager.progress('close');
                        }
                        return flag;
                    },
                    success: function (result) {
                        $.messager.progress('close');
                        const data = JSON.parse(result);
                        console.log(result);
                        if (data.code === 1) {
                            $.messager.alert("提示", "保存成功", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#addView").dialog("close");
                        } else {
                            $.messager.alert("提示", data.message, "info");
                            $("#dataGrid").datagrid("reload");
                            $("#addView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text: "取消",
            iconCls: "icon-cancel",
            handler: function () {
                $("#addView").dialog("close");
            }
        }]
    });

    //构造对话框
    $("#updateView").dialog({
        width: "430",
        height: "180",
        top: "80",
        buttons: [{
            text: "保存",
            iconCls: "icon-save",
            handler: function () {
                $("#updateForm").form("submit", {
                    url: "long_rent_rate_update",
                    onSubmit: function () {
                        $.messager.progress({
                            text: "正在加载，请稍等！"
                        });
                        var flag = $(this).form("validate");
                        if (!flag) {
                            $.messager.progress('close');
                        }
                        return flag;
                    },
                    success: function (result) {
                        $.messager.progress('close');
                        const data = JSON.parse(result);
                        if (data.code === 1) {
                            $.messager.alert("提示", "保存成功", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#updateView").dialog("close");
                        } else {
                            $.messager.alert("提示", data.message, "info");
                            $("#dataGrid").datagrid("reload");
                            $("#updateView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text: "取消",
            iconCls: "icon-cancel",
            handler: function () {
                $("#updateView").dialog("close");
            }
        }]
    });


    //删除操作
    $("#btnRemove").bind("click", function () {
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if (selectedRow.length <= 0) {
            $.messager.alert("提示", "请选择要删除的策略", "error");
        } else {
            var JsonData = selectedRow[0];
            $.messager.confirm("提示", "确定要删除吗?", function (r) {
                if (r) {
                    $.post("timeshare_rate_del.do", {"id": JsonData.id}, function (data) {
                        if (data == "success") {
                            //$.messager.alert("提示", "删除成功", "info");
                            $("#dataGrid").datagrid("reload");
                        } else {
                            $.messager.alert("提示", "删除失败", "info");
                        }
                    }, "text");
                }
            });
        }
    });

    //清空
    $("#clearQuery").bind("click", function () {
        clearQueryForm();
    });

    //清空Combogrid
    $("#clearCombogrid").bind("click", function () {
        $("#car_type_id").combogrid("clear");
    });

});

function clearForm() {
    $('#addForm').form('clear');
}

function clearQueryForm() {
    $('#queryForm').form('clear');
}