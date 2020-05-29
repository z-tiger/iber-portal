function clearToolbar(){
    $('#toolbar').form('clear');
}
$(function() {
    //清空
    $("#clearQuery").bind("click",function(){
        clearToolbar();
    });
    //查询链接
    $(document).keydown(function(event){
        var enterpriseName = $("#entepriseName").textbox('getValue');
        var memberName = $("#memberName").textbox('getValue');
        var phone = $("#phone").textbox('getValue');
        var checkStatus = $("#checkStatus").combobox('getValue');
        // var orderType = $("#orderType").combobox('getValue');

        if(event.keyCode==13){
            $('#dataGrid').datagrid('load',{
                enterpriseName : enterpriseName,
                memberName : memberName,
                phone : phone,
                checkStatus : checkStatus
                // orderType : orderType
            });
        }
    });
    //查询链接
    $("#btnQuery").bind("click",function(){
        var enterpriseName = $("#entepriseName").textbox('getValue');
        var memberName = $("#memberName").textbox('getValue');
        var phone = $("#phone").textbox('getValue');
        var checkStatus = $("#checkStatus").combobox('getValue');
        // var orderType = $("#orderType").combobox('getValue');
        $('#dataGrid').datagrid('load',{
            enterpriseName : enterpriseName,
            memberName : memberName,
            phone : phone,
            checkStatus : checkStatus
            // orderType : orderType
        });
    });

    //超额申请记录列表
    $('#dataGrid').datagrid( {
        title : '超额申请记录',
        width : 'auto',
        height : 'auto',
        fit:true,
        fitColumns: false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
        url : 'enterpriseOverApplyRecordList.do',
        pageSize : 100,
        pageList : [100,50,30,10],
        pageIndex:1,
        idField : 'id',
        columns : [ [{
            field : 'ck',
            checkbox:true
        },{
            field : 'enterpriseName',
            title : '公司名称',
            width : $(this).width() * 0.1,
            align : 'center',
        }, {
            field : 'memberName',
            title : '会员名称',
            width : $(this).width() * 0.1,
            align : 'center',
        },{
            field : 'memberPhone',
            title : '手机号码',
            width : $(this).width() * 0.1,
            align : 'center',
        }, {
            field : 'applyCityName',
            title : '用车城市',
            width : $(this).width() * 0.1,
            align : 'center',
        },{
            field : 'actualCityName',
            title : '实际用车城市',
            width : $(this).width() * 0.1,
            align : 'center',
        }, {
            field : 'useTime',
            title : '实际用车时长',
            width : $(this).width() * 0.1,
            align : 'center',
            formatter:function (val,rec) {
                if(!val)
                    return 0
                else
                    return secondToDate(val)

            }
        },  {
            field : 'planUseTime',
            title : '预计用车时长',
            width : $(this).width() * 0.1,
            align : 'center',
            formatter:function (val,rec) {
                if(!val)
                    return 0
                else
                    return secondToDate(val)

            }
        },
            {
                field : 'reason',
                title : '用车说明',
                width : $(this).width() * 0.1,
                align : 'center',
            }, {
                field : 'lowestEstimatedCost',
                title : '预估费用最低值',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val ==null || val == "null"){
                        return "";
                    }else{
                        n = (val/100).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }
                }
            }, {
                field : 'highestEstimatedCost',
                title : '预估费用最高值',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val ==null || val == "null"){
                        return "";
                    }else{
                        n = (val/100).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }
                }
            },  {
                field : 'exceed',
                title : '超支费用',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val ==null || val == "null"){
                        return "";
                    }else{
                        n = (val/100).toFixed(2);
                        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
                        return n.replace(re, "$1,");
                    }
                }
            },{
                field : 'remark',
                title : '超支费用说明',
                width : $(this).width() * 0.1,
                align : 'center'

            }, {
                field : 'rejectReason',
                title : '拒绝原因',
                width : $(this).width() * 0.1,
                align : 'center'

            }, {
                field : 'status',
                title : '审批结果',
                width : $(this).width() * 0.1,
                align : 'center',
                formatter : function(val, rec) {
                    if(val == "null"){
                        return "";
                    }else if(val == 1){
                        return "待审核";
                    }else if(val == 2){
                        return "通过";
                    }else if(val ==3){
                        return "不通过";
                    }else {
                        return "取消"
                    }
                }
            }, {
                field : 'checkMemberName',
                title : '审批人',
                width : $(this).width() * 0.1,
                align : 'center'
            }

        ] ],
        pagination : true,
        rownumbers : true
    });


    //清空
    $("#clearQuery").bind("click",function(){
        clearQueryForm();
    });

});

function clearQueryForm(){
    $('#queryForm').form('clear');
}