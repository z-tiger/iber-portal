$(function () {
    //查询链接
    $(document).keydown(function (event) {
        var method = $('#method').val();
        var start = $('#start').textbox('getValue');
        var end = $('#end').textbox('getValue');

        if (event.keyCode == 13) {
            $('#dataGrid').datagrid('load', {
                method: method,
                ueryDateFrom: start,
                end: end
            });
        }
    });
    //查询链接
    $("#btnQuery").bind("click", function () {
        var name = $('#name').val();
        var phone = $('#phone').val();
        var scene = $('#scene').val();
        var start = $('#start').textbox('getValue');
        if (start) start = new Date(start).getTime();
        var end = $('#end').textbox('getValue');
        if (end) end = new Date(end).getTime();
        $('#dataGrid').datagrid('load', {
            name: name,
            phone: phone,
            scene: scene,
            start: start,
            end: end
        });
    });

    $('#dataGrid').datagrid({
        title: '会员操作日志管理',
        width: 'auto',
        height: 'auto',
        fit: true,
        fitColumns: true,
        nowrap: true,
        striped: true,
        collapsible: true,
        rownumbers: true,
        singleSelect: true,
        url: 'memberOperateLog',
        pageSize: 30,
        pageList: [30, 10],
        idField: '_id',
        columns: [[
            {field: 'memberId', title: '会员id', align: 'center', width: $(this).width() * 0.03},
            {field: 'name', title: '姓名', align: 'center', width: $(this).width() * 0.03},
            {field: 'phone', title: '手机号', align: 'center', width: $(this).width() * 0.05},
            {field: 'os', title: '终端', align: 'center', width: $(this).width() * 0.2},
            {field: 'scene', title: '操作描述', align: 'center', width: $(this).width() * 0.2},
            {field: 'createTime', title: '操作时间', align: 'center', width: $(this).width() * 0.08,
                formatter: function (value) {
                    if (value) {
                        var d = new Date();
                        d.setTime(value);
                        return d.toLocaleString()
                    }
                }
            }
        ]],
        pagination: true,
        rownumbers: true
    });

    //清空
    $("#clearQuery").bind("click", function () {
        $('#name').val('');
        $('#phone').val('');
        $('#scene').val('');
        $('#start').textbox('setValue', '');
        $('#end').textbox('setValue', '');
    });

    //开始时间默认00:00:00
    $("#start").datetimebox({
        onShowPanel:function(){
            $(this).datetimebox("spinner").timespinner("setValue","00:00:00");
        }
    });
    //结束时间默认23:59:59
    $("#end").datetimebox({
        onShowPanel:function(){
            $(this).datetimebox("spinner").timespinner("setValue","23:59:59");
        }
    })
});
		