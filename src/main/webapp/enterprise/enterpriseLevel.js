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
        var name = $("input[name='_name']").val();
        if(event.keyCode==13){
            $('#dataGrid').datagrid('load',{
                name : name
            });
        }
    });
    //查询链接
    $("#btnQuery").bind("click",function(){
        var name = $("input[name='_name']").val();
        $('#dataGrid').datagrid('load',{
            name : name
        });
    });


    $('#dataGrid').datagrid( {
        title : '企业等级管理',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : true,
        nowrap : true,
        striped : true,
        collapsible : true,
        rownumbers : true,
        singleSelect : true,
        url : 'enterprise_level_list.do',
        pageSize : 100,
        pageList : [100,50,30,10],
        idField : 'id',
        columns : [ [ {
            field : 'ck',
            checkbox : true

        }, {
            field : 'name',
            title : '企业等级',
            width : $(this).width() * 0.01,
            align : 'center',
        },{
            field : 'depositLimit',
            title : '企业应邀押金金额',
            width : $(this).width() * 0.01,
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
            field : 'memberDepositLimit',
            title : '员工应邀押金金额',
            width : $(this).width() * 0.01,
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
            field : 'overdraft',
            title : '可透支金额',
            width : $(this).width() * 0.01,
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
            field : 'depositNumber',
            title : '可透支次数',
            width : $(this).width() * 0.01,
            align : 'center',
            formatter : function(val, rec) {
                if(val ==null || val == "null"){
                    return "";
                }else{
                   return val;
                }
            }
        }

        ] ],
        pagination : true,
        rownumbers : true
    });


    //添加用户链接
    $("#btnAdd").bind("click",function(){
        clearForm();
        $("#addView").dialog("open").dialog("setTitle", "添加企业等级");
    });

    //编辑用户链接
    $("#btnEdit").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要编辑的企业等级", "error");
        }else{
            var JsonData = selectedRow[0];
            console.log(JsonData);
            $("#id").val(JsonData.id);
            $("#name").textbox("setValue",JsonData.name);
            $("#deposit_limit").numberbox({precision:2});
            $("#deposit_limit").numberbox("setValue",JsonData.depositLimit/100);
            $("#member_deposit_limit").numberbox({precision:2});
            $("#member_deposit_limit").numberbox("setValue",(JsonData.memberDepositLimit/100).toFixed(2));
            $("#overdraft").numberbox({precision:2});
            $("#overdraft").numberbox("setValue",(JsonData.overdraft/100).toFixed(2));
            $("#deposit_number").numberbox("setValue",(JsonData.depositNumber));
            $("#addView").dialog("open").dialog("setTitle", "编辑企业等级");
        }
    });

    //构造对话框
    $("#addView").dialog( {
        width : "400",
        height : "350",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#addForm").form("submit", {
                    url : "enterprise_level_saveOrUpdate.do",
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
                            $("#dataGrid").datagrid("reload");
                            $("#addView").dialog("close");
                        }else{
                            $.messager.alert("提示", "保存失败", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#addView").dialog("close");
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


    //删除操作
    $("#btnRemove").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要删除的企业等级", "error");
        }else{
            var JsonData = selectedRow[0];
            $.messager.confirm("提示","确定要删除吗?",function(r){
                if(r){
                    $.post("enterprise_level_del.do",{"id":JsonData.id},function(data){
                        if(data=="success"){
                            //$.messager.alert("提示", "删除成功", "info");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "删除失败", "info");
                        }
                    },"text");
                }
            });
        }
    });

    //清空
    $("#clearQuery").bind("click",function(){
        clearQueryForm();
    });

});
function clearForm(){
    $('#addForm').form('clear');
}
function clearQueryForm(){
    $('#queryForm').form('clear');
}