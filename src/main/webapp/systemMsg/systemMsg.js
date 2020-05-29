$(function() {
    $('#grid').datagrid({
        title : '消息推送',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
        url : 'push_msg_list',
        queryParams:{
            'type':$("#type").combobox("getValue"),
            'title':$("#title").textbox("getValue"),
        },
        pageSize : 100,
        pageList : [100,50,30,10],
        idField : 'createTime',
        columns : [ [ {
            field : 'ck',
            checkbox : true

        }, {
            field : 'msgType',
            title : '类型',
            width : $(this).width() * 0.1,
            align : 'center'
        },  {
            field : 'cityName',
            title : '城市',
            width : $(this).width() * 0.1,
            align : 'center'
        },{
            field : 'msgTitle',
            title : '标题',
            width : $(this).width() * 0.16,
            align : 'center'
        },{
            field : 'msgContent',
            title : '内容',
            width : $(this).width() * 0.25,
            align : 'center'
        },{
            field : 'isSpecifyUser',
            title : '指定用户',
            width : $(this).width() * 0.1,
            align : 'center',
            formatter:function (value) {
                if (value == 0){
                    return "否"
                }else{
                    return "是"
                }
            }
        },{
            field : 'createUser',
            title : '创建人',
            width : $(this).width() * 0.1,
            align : 'center'
        }, {
            field: 'createTime',
            title: '创建时间',
            width: $(this).width() * 0.16,
            align: 'center',
            formatter: function (value) {
                var JsonDateValue = new Date(value.time);
                var text = JsonDateValue.toLocaleString();
                return text;
            }
        }] ],
        pagination : true,
        rownumbers : true
    });

    //query
    $("#btnQuery").bind("click",function(){
        $("#grid").datagrid("load",{
            'type':$("#type").combobox("getValue"),
            'title':$("#title").textbox("getValue"),
        });
    });

	$("#btnAdd").bind("click",function(){
		 $("#addSystemMsgForm").form("clear");
		 $("#addSystemMsgView").dialog("open");
	});

	$("#addSystemMsgView").dialog( {
		title:"系统通知设置",
		width : "400",
		height : "400",
		top : "40",
		buttons : [ {
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addSystemMsgForm").form("submit", {
					url : "save_system_msg",
					onSubmit : function() {
						return $(this).form("validate");
					},
					success : function(result) {
                        var json = eval('(' + result + ')');
						if (json.code == 1) {
							$.messager.alert("提示", json.result, "info");
						    $("#grid").datagrid("reload");
							$("#addSystemMsgView").dialog("close");
						} else{
                            $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		}, {
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#addSystemMsgView").dialog("close");
			}
		} ]
	});

    $("#btnDownload").bind("click",function(){
        window.location.href = "member_phone_template_file_download";
    });

    $("#targetUser").combobox({
        onChange: function () {
            var value = $("#targetUser").combobox('getValue');
            if (value){
                if (value == 0){
                    $("#addFile").attr("style","display:none")
                }else{
                    $("#addFile").attr("style","display:block")
                }
            }
        }
    })
});