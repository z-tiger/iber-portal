$(function () {
    //按键
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            loadData();
        }
    });

    //单击事件
    $("#btnQuery").bind("click", function () {
        loadData();
    });

    //加载数据
    function loadData() {
        var method = $('#method').val();
        var methodDescribe = $('#methodDescribe').val();
        var name = $('#name').val();
        var memberId = $('#memberId').val();
        var param = $('#param').val();
        var start = $('#start').textbox('getValue');
        if (start) start = new Date(start).getTime();
        var end = $('#end').textbox('getValue');
        if (end) end = new Date(end).getTime();

        $('#dataGrid').datagrid('load', {
            method: method,
            name: name,
            methodDescribe: methodDescribe,
            memberId: memberId,
            param: param,
            start: start,
            end: end
        });

        $('#dataGrid').datagrid('clearSelections');
    }

    $('#dataGrid').datagrid({
        title: '平台操作日志管理',
        width: 'auto',
        height: 'auto',
        fit: true,
        fitColumns: true,
        nowrap: false,
        striped: true,
        collapsible: true,
        rownumbers: true,
        singleSelect: true,
        url: 'platformOperateLog',
        pageSize: 30,
        pageList: [50, 30, 10],
        idField: '_id',
        columns: [[
            {field: 'userId', title: '员工id', align: 'center', width: $(this).width() * 0.05},
            {field: 'name', title: '姓名', align: 'center', width: $(this).width() * 0.05},
            {field: 'ip', title: 'ip', align: 'center', width: $(this).width() * 0.08},
            {field: 'inParam', title: '输入参数', align: 'center', width: $(this).width() * 0.25},
            {field: 'methodDescribe', title: '方法描述', align: 'center', sortable: true, width: $(this).width() * 0.15},
            {field: 'methodName', title: '方法', align: 'center', sortable: true, width: $(this).width() * 0.2},
            {
                field: 'createTime', title: '操作时间', align: 'center', sortable: true, width: $(this).width() * 0.1,
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
        onLoadSuccess:function () {
            $(this).datagrid('selectRow',0);
        }
    });

    //清空
    $("#clearQuery").bind("click", function () {
        $('#method').val('');
        $('#methodDescribe').val('');
        $('#name').val('');
        $('#memberId').val('');
        $('#param').val('');
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
		