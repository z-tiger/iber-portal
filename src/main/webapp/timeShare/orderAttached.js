function search(){
	var cityCode = $('#cityCode').textbox('getValue') ;
    var ispay = $('#ispay').textbox('getValue') ;
    var type = $('#type').textbox('getValue') ;
	var lpn = $('#lpn').textbox('getValue');
	var memberName = $.trim($("#memberName").val()) ;
	var phoneNumber = $.trim($("#phoneNumber").val()) ;
	var orderId = $.trim($("#orderId").val())
    var queryDateFrom = $('#queryDateFrom').textbox('getValue');
    var queryDateTo = $('#queryDateTo').textbox('getValue');
    var toUser = $('#toUser').textbox('getValue');
	$('#order_attached_grid').datagrid('load',{
		memberName : memberName ,
		cityCode :cityCode,
		lpn : lpn,
		phoneNumber :phoneNumber,
        ispay : ispay,
        type : type,
        orderId : orderId,
        queryDateFrom : queryDateFrom,
        queryDateTo : queryDateTo,
        toUser:toUser
	}) ;
}

$(function() {
	//查询链接
	$("#btnQuery").bind("click", function() {
		search() ;
	});
	$("#btnadd").bind("click", function() {
        $("#addView").dialog("open").dialog("setTitle", "添加附属订单");
        formRest();

    });
	//导出excel
	$("#btnExport").bind("click", function() {
		var param=   $('#order_attached_grid').datagrid('options').queryParams;//查询参数
        var url = "export_orderAttached_list?"+jQuery.param(param);
        window.location.href = url ;

	});
	function formRest() {
        $("#addViewForm").form('reset');
        $("#days").numberbox({ "disabled":true });
        $("#cost").numberbox({ "disabled":true });
        $("#money2").numberbox({ "disabled":true });
        $("#money3").numberbox({ "disabled":true });
        $("#money4").numberbox({ "disabled":true });
        $("#money4").numberbox({ required:false });
        $("#money3").numberbox({ required:false });
        $("#days").numberbox({ required:false });
        $("#cost").numberbox({ required:false });
        $("#money2").numberbox({ required:false });
        $("#explanation2").combobox({ required:false });
        $("#money3").numberbox({ required:false });
        $("#money4").numberbox({ required:false });
        $("#addRepairImg").attr("src","");
        $("#addCarDamageImg").attr("src","");
        $(".item").show();
    }
	$("#btn_view_detail").bind("click",function () {

        var row = $('#order_attached_grid').datagrid('getSelected');
        if (row==null){
            $.messager.alert("提示", "请选择一行查看详情", "info");
        }else {
            $("#addView2").dialog("open").dialog("setTitle", "附属订单详情");
            var opts = $("#table_view_detail").datagrid("options");
            opts.url = "page_orderattached_list_detail";
            $('#table_view_detail').datagrid('load',{
                orderId : row.id
            }) ;
        }

    })
	// 清空
	$("#clearQuery").bind("click", function() {
		$('#cityCode').textbox('setValue', '');
		$('#lpn').textbox('setValue', '');
		$('#memberName').textbox('setValue', '');
		$('#phoneNumber').textbox('setValue', '');
        $('#ispay').textbox('setValue', '');
        $('#type').textbox('setValue', '');
        $('#orderId').textbox('setValue', '');
        $('#queryDateFrom').textbox('setValue', '');
        $('#queryDateTo').textbox('setValue', '');
	});

    /**
     * 附属订单列表
     */
    $('#order_attached_grid').datagrid(
	{
		title : '附属订单',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : true,
		pagination : true,
		rownumbers : true,
		singleSelect : true,
		url : 'page_orderattached_list',
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true
		}, {
			field : 'orderId',
			title : '约车订单编号',
			width : $(this).width() * 0.15,
			align : 'center'
		}, {
			field : 'membername',
			title : '会员名称',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
            field : 'phone',
            title : '手机号码',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'toUser',
            title : '责任类型',
            width : $(this).width() * 0.08,
            align : 'center'
        },  {
			field : 'reason',
			title : '创建原因',
			width : $(this).width() * 0.15,
			align : 'center',
            formatter: function (value) {
                return "<span title='" + value + "'>" + value + "</span>";
            }
		}, {
			field : 'lpn',
			title : '车牌号',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'type',
			title : '订单类型',
			width : $(this).width() * 0.08,
			align : 'center'
		}, {
			field : 'createtime',
			title : '创建时间',
			width : $(this).width() * 0.10,
			align : 'center'
		}, {
			field : 'creater',
			title : '创建人',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'city',
			title : '城市编码',
			width : $(this).width() * 0.08,
			align : 'center'
		},  {
            field : 'brandName',
            title : '车辆品牌',
            width : $(this).width() * 0.06,
            align : 'center'
        },{
			field : 'ordermoney',
			title : '应付额',
			width : $(this).width() * 0.06,
			align : 'center',
            formatter : function(val) {
                    return val+"元";
            }
		}, {
				field : 'paytype',
				title : '支付类型',
				width : $(this).width() * 0.06,
				align : 'center'
		}, {
				field : 'paystatus',
				title : '支付状态',
				width : $(this).width() * 0.06,
				align : 'center',
            formatter : function (val) {
                if(val=='未支付'){
                    return '<font color=red>未支付</font>'
                }else{
                    return val;
                }
            }
		}, {
				field : 'completiontime',
				title : '支付完成时间',
				width : $(this).width() * 0.10,
				align : 'center'
			}
		] ],
		pagination : true,
		rownumbers : true
	});
    /**
     * 附属订单详情列表
     */
    $('#table_view_detail').datagrid(
        {
            width : 'auto',
            height : 'auto',
            fit : true,
            fitColumns : false,
            nowrap : false,
            striped : true,
            collapsible : true,
            pagination : false,
            rownumbers : false,
            singleSelect : true,
            idField : 'id',
            columns : [ [ {
                field : 'itemName',
                title : '收费项目',
                width : $(this).width() * 0.10,
                align : 'center'
            }, {
                field : 'money',
                title : '金额（元）',
                width : $(this).width() * 0.10,
                align : 'center',
                formatter : function(val) {
                    return val/100;
                }
            },{
                field : 'explanation',
                title : '说明',
                width : $(this).width() * 0.10,
                align : 'center'
            }
            ] ]
        });

    $("#addView").dialog( {
        top : "100",
        width : "580",
        height : "480",
        left:"400",
        modal:true,
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#addViewForm").form("submit", {
                    url : "timeshare_order_attached_save",
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
                            $.messager.alert("提示", "保存成功", "info");
                            $("#order_attached_grid").datagrid("reload");
                            formRest();
                        }else{
                            $.messager.alert("提示", "保存失败："+result, "info");
                            $("#order_attached_grid").datagrid("reload");
                            formRest();

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
    $("#addView2").dialog( {
        top : "5",
        left:"400",
        modal:true,
        buttons : [  {
            text : "关闭",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addView2").dialog("close");
            }
        }]
    });

    /*$("input",$("#ordernum").next("span")).blur(function(){
        var orderId=$("#ordernum").textbox('getValue');
        $.ajax({
            type : 'get',
            url : 'attached_checkorder',
            data : {
                'orderId':orderId
            },
            success : function(result) {
                if (result=="success"){

                }else{
                    $.messager.alert("警告", "该订单不存在", "error");
                }
            }
        });
    });*/

    $("#days").numberbox({
        "onChange":function(){
            $('#allcost').numberbox('setValue', $("#days").numberbox("getValue")*$("#cost").numberbox("getValue"));
            $('#explanation').textbox('setValue', $("#days").numberbox("getValue")+"×"+$("#cost").numberbox("getValue")+" 元/天");
        }
    });
    $("#cost").numberbox({
        "onChange":function(){
            $('#allcost').numberbox('setValue', $("#days").numberbox("getValue")*$("#cost").numberbox("getValue"));
            $('#explanation').textbox('setValue', $("#days").numberbox("getValue")+"×"+$("#cost").numberbox("getValue")+" 元/天");
        }
    });
    $("#itemcheck1").click(function () {
    	if($(this).is(":checked")){
            $("#days").numberbox({ required:true });
            $("#cost").numberbox({ required:true });
            $("#days").numberbox({ "disabled":false });
            $("#cost").numberbox({ "disabled":false });
		}else{
            $("#days").numberbox({ required:false });
            $("#cost").numberbox({ required:false });
            $("#days").numberbox('setValue',"");
            $("#cost").numberbox('setValue',"");
            $("#days").numberbox({ "disabled":true });
            $("#cost").numberbox({ "disabled":true });
		}

    });

    $("#itemcheck2").click(function () {
        if($(this).is(":checked")){
            $("#money2").numberbox({ required:true });
            $("#money2").numberbox({ "disabled":false });
            $("#explanation2").combobox({ required:true });

        }else{
            $("#money2").numberbox({ "disabled":true });
            $("#money2").numberbox({ required:false });
            $("#explanation2").combobox({ required:false });
            $("#money2").numberbox('setValue',"");

        }

    });

    $("#itemcheck3").click(function () {
        if($(this).is(":checked")){
            $("#money3").numberbox({ required:true });
            $("#money3").numberbox({ "disabled":false });
        }else{
            $("#money3").numberbox({ required:false });
            $("#money3").numberbox('setValue',"");
            $("#money3").numberbox({ "disabled":true });
        }
    });
    $("#itemcheck4").click(function () {
        if($(this).is(":checked")){
            $("#money4").numberbox({ required:true });
            $("#money4").numberbox({ "disabled":false });
        }else{
            $("#money4").numberbox({ required:false });
            $("#money4").numberbox('setValue',"");
            $("#money4").numberbox({ "disabled":true });
        }
    });

    /**
     * 选中用户责任的时候出发的事件
     */
    $("#typeSelect").combobox({
        onChange: function (n,o) {
          if (n==1){
              $(".item").show();
          }else{
              $(".item").hide();
          }
        }
    });
});

