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
    //用车申请记录列表
    $('#dataGrid').datagrid( {
        title : '用车申请记录',
        width : 'auto',
        height : 'auto',
        fit:true,
        fitColumns: false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
        url : 'enterpriseCarApplyRecordList.do',
        pageSize : 100,
        pageList : [100,50,30,10],
        pageIndex:1,
        idField : 'id',
        columns : [ [{
            field : 'ck',
            checkbox:true
        }, {
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
        }, {
            field : 'useTime',
            title : '实际用车时长',
            width : $(this).width() * 0.15,
            align : 'center',
            formatter:function (val,rec) {
                console.log(val);
                if(val==null||val ==''){
                    return '0'
                }else{
                    return secondToDate(val);
                }

            }
        }, {
            field : 'reason',
            title : '用车说明',
            width : $(this).width() * 0.2,
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
            field : 'remark',
            title : '拒绝原因',
            width : $(this).width() * 0.1,
            align : 'center',
            formatter : function(val) {
                if(val) {
                    return val;
                }else {
                    return "";
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


    //添加用户链接
    $("#btnAdd").bind("click",function(){
        clearForm();
        //enterprisePhoto.src = "";
        $("#enterprisePhoto").attr("src", "");
        document.getElementById('enterprise_tel_msg').innerHTML = "";
        document.getElementById('admin_mobile_msg').innerHTML = "";
        $("#addView").dialog("open").dialog("setTitle", "添加企业");
    });

    //编辑用户链接
    $("#btnEdit").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要编辑的企业", "error");
        }else{
            var JsonData = selectedRow[0];
            document.getElementById('enterprise_tel_msg').innerHTML = "";
            document.getElementById('admin_mobile_msg').innerHTML = "";
            $("#id").val(JsonData.id);
            $("#city_code").combobox("setValue",JsonData.cityCode);
            // $("#level").combogrid("setValue",JsonData.level);
            $("#enterprise_name").textbox("setValue",JsonData.enterpriseName);
            $("#admin").textbox("setValue",JsonData.admin);
            $("#admin_mobile").textbox("setValue",JsonData.adminMobile);
            $("#legal_person").textbox("setValue",JsonData.legalPerson);
            $("#enterprise_tel").textbox("setValue",JsonData.enterpriseTel);
            $("#address").textbox("setValue",JsonData.address);
            $("#business_license").textbox("setValue",JsonData.businessLicense);
//			enterprisePhoto.src = JsonData.licenseFileUrl;
            $("#enterprisePhoto").attr("src", JsonData.licenseFileUrl);
            $("#enterprisePhoto").attr("width", 250);

            $('#depositLimit').textbox('setValue', (JsonData.depositLimit/100).toFixed(2));
            $('#overdraftMoney').textbox('setValue', (JsonData.canOverdraftMoney/100).toFixed(2));
            $('#overdraftNum').textbox('setValue', JsonData.canOverdraftNum);

            $("#addView").dialog("open").dialog("setTitle", "编辑企业");
        }
    });

    //构造对话框
    $("#addView").dialog( {
        width : "550",
        height : "530",
        top : "0",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#addForm").form("submit", {
                    url : "enterprise_saveOrUpdate.do",
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
                        }else if(result == "phoneExist"){
                            $.messager.alert("提示", "管理员电话已存在", "info");
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
            $.messager.alert("提示", "请选择要删除的企业", "error");
        }else{
            var JsonData = selectedRow[0];
            $.messager.confirm("提示","确定要删除吗?",function(r){
                if(r){
                    $.post("enterprise_del.do",{"id":JsonData.id},function(data){
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

    //企业用户添加
    $("#memberAdd").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要添加用户的企业", "error");
        }else{
            clearMemberAddForm();
            document.getElementById('m_phone_msg').innerHTML = "";
            document.getElementById('m_email_msg').innerHTML = "";
            document.getElementById('m_idcard_msg').innerHTML = "";
            var JsonData = selectedRow[0];
            $("#m_id").val(JsonData.id);
            $("#m_enterpris_name").textbox("setValue",JsonData.enterpriseName);
            $("#memberAddView").dialog("open").dialog("setTitle", "企业用户添加");
        }
    });

    //构造对话框
    $("#memberAddView").dialog( {
        width : "500",
        height : "530",
        top : "0",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#memberAddForm").form("submit", {
                    url : "enterprise_member_add.do",
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
                            $("#memberAddView").dialog("close");
                        }else{
                            $.messager.alert("提示", "保存失败", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#memberAddView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#memberAddView").dialog("close");
            }
        }]
    });

    //企业所属网点添加
    $("#btnEditSetPark").bind("click",function(){
        $("#addParkView").dialog("open").dialog("setTitle", "添加网点");
        var selectedRow = $("#dataGrid").datagrid("getSelections");

        $("#enterprise_id").val(selectedRow[0].id);
        $('#parkName').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
            delay:500,// 1s延时查询
            editable : true,
            panelHeight: 'auto',
            panelWidth: 300,
            idField: 'id',
            textField: 'name',
            url:'allNotBelongPark?',
            striped : true,
            pageSize:10,
            required:true,
            multiple:true,
            pagination : true,//是否分页
            rownumbers : true,//序号
            columns : [ [ {
                field : 'ck',
                checkbox:true
            },{
                field : 'name',
                title : '网点名称',
                width : '80%',
                align : 'center'
            }] ],
            keyHandler : {
                query : function(parkName) { // 动态搜索处理
                    $('#parkName').combogrid("grid").datagrid('options').queryParams =
                        JSON.parse('{"parkName":"' + parkName + '"}');
                    // 重新加载
                    $('#parkName').combogrid("grid").datagrid("reload");
                    $('#parkName').combogrid("setValue",parkName);

                }
            }
        });
    });
    //企业所属车辆添加
    $("#addEnterpriseCar").bind("click",function(){
        console.log("enter add enterprise car view ")
        $("#addEnterpriseCarView").dialog("open").dialog("setTitle", "添加企业所属车辆");
        var selectedRow = $("#dataGrid").datagrid("getSelections");

        $("#enter_id").val(selectedRow[0].id);
        $('#carList').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
            delay:500,// 1s延时查询
            editable : true,
            panelHeight: 'auto',
            panelWidth: 300,
            idField: 'id',
            textField: 'lpn',
            url:'findEnterpriseCarList?',
            striped : true,
            pageSize:10,
            required:true,
            multiple:true,
            pageList : [100,50,30,10],
            pageIndex:1,
            pagination : true,//是否分页
            rownumbers : true,//序号
            columns : [ [ {
                field : 'ck',
                checkbox:true
            },{
                field : 'lpn',
                title : '车牌号',
                width : '45%',
                align : 'center'
            },
                {
                    field : 'brandName',
                    title : '车辆品牌',
                    width : '45%',
                    align : 'center',
                    formatter:function (val,red) {
                        if(val=="") {
                            return ""
                        }else {
                            return val
                        }

                    }
                }] ],
            keyHandler : {
                query : function(parkName) { // 动态搜索处理
                    $('#carList').combogrid("grid").datagrid('options').queryParams =
                        JSON.parse('{"parkName":"' + parkName + '"}');
                    // 重新加载
                    $('#carList').combogrid("grid").datagrid("reload");
                    // $('#carList').combogrid("setValue",parkName);
                }
            }
        });
    });
    //构造保存企业所属车辆对话框
    $("#addEnterpriseCarView").dialog( {
        width : 450,
        height : 350,
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                var selectedRows = $("#carList").combogrid('getValues');

                var enterpriseID = $('#enter_id').val();
                selectedRows.toString()
                // console.log("selectedRows>>>>>>")
                // console.log(selectedRows);
                // console.log( selectedRows.toString());
                // console.log("enterpriseID:" + enterpriseID);
                $("#addEnterpriseCarForm").form("submit", {
                    url : "addEnterpriseCar?carid="+selectedRows.toString()+"&enterpriseID="+enterpriseID,
                    // queryParams:{"ids":selectedRows,"enterpriseId":enterpriseID},
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
                    success : function(jsonResult) {
                        var result = JSON.parse(jsonResult);
                        // console.log("result>>.");
                        $.messager.progress('close');
                        // console.log(result.status);
                        // console.log(result.data);
                        if (result.status ==1) {
                            $.messager.alert("提示", "添加成功", "info");
                            $("#showEnterpriseCarGrid").datagrid("reload");
                            $("#dataGrid").datagrid("reload");
                            $("#addEnterpriseCarView").dialog("close");
                        }else{
                            $.messager.alert("提示", result.data, "info");
                            $("#showEnterpriseCarGrid").datagrid("reload");
                            $("#addEnterpriseCarView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addEnterpriseCarView").dialog("close");
            }
        }]
    });
    //移除企业所属车辆
    $("#removeEnterpriseCar").bind("click",function(){
        var selectedRows = $("#showEnterpriseCarGrid").datagrid("getSelections");
        // console.log(selectedRows);
        var JsonData= new Array();

        var JsonID = new Array();
        if(selectedRows.length <=0){
            $.messager.alert("提示", "请选择要移除的车辆", "error");
        }else{
            for(var i=0;i<selectedRows.length;i++){
                JsonData.push(selectedRows[i].enterpriseRelationCarId);
                JsonID.push(selectedRows[i].id);
            }
            console.log(JsonID.toString())
            console.log(JsonData.toString());
            $.messager.confirm("提示","确定要移除吗?",function(r){
                if(r){
                    $.post("removeEnterpriseCar",{"id":JsonData.toString(),"carID":JsonID.toString()},function(data){
                        console.log(data);
                        if(data.code==1){
                            $.messager.alert("提示", "移除成功", "info");
                            $("#showEnterpriseCarGrid").datagrid("reload");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "移除失败", "info");
                        }
                    });
                }
            });
        }
    });

    //构造保存对话框
    $("#addParkView").dialog( {
        width : "460",
        height : "400",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                var selectedRows = $("#parkName").combogrid('getValues');
                $("#addParkForm").form("submit", {
                    url : "enterprise_park_add?ids="+selectedRows,
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
                            $("#showParkGrid").datagrid("reload");
                            $("#dataGrid").datagrid("reload");
                            $("#addParkView").dialog("close");
                        }else{
                            $.messager.alert("提示", "保存失败", "info");
                            $("#showParkGrid").datagrid("reload");
                            $("#addParkView").dialog("close");
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
    //企业所属网点移除
    $("#btnEditCancelPark").bind("click",function(){
        var selectedRows = $("#showParkGrid").datagrid("getSelections");
        var JsonData='';
        if(selectedRows.length <=0){
            $.messager.alert("提示", "请选择要移除的网点", "error");
        }else{
            for(var i=0;i<selectedRows.length;i++){

                JsonData = JsonData+ selectedRows[i].id+',';
            }
            $.messager.confirm("提示","确定要移除吗?",function(r){
                if(r){

                    $.post("deleteEnterpriseById",{"id":JsonData},function(data){
                        if(data=="success"){
                            $.messager.alert("提示", "移除成功", "info");
                            $("#showParkGrid").datagrid("reload");
                            $("#dataGrid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "移除失败", "info");
                        }
                    },"text");
                }
            });
        }
    });

    //构造对话框
    $("#importMemberView").dialog( {
        width : "350",
        height : "250",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#importMemberForm").form("submit", {
                    url : "enterprise_improt_member.do",
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
                        var str = "共"+result.split(",")[0]+"条，成功导入"+result.split(",")[1]+"条。";
                        $.messager.alert("提示", str, "info");
                        $("#dataGrid").datagrid("reload");
                        $("#importMemberView").dialog("close");
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#importMemberView").dialog("close");
            }
        }]
    });


    //企业用户导入
    $("#membersAdd").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要导入用户的企业", "error");
        }else{
            clearImportMemberForm();
            var JsonData = selectedRow[0];
            $("#import_member_id").val(JsonData.id);
            $("#enterpris_name_imp").textbox("setValue",JsonData.enterpriseName);
            $("#importMemberView").dialog("open").dialog("setTitle", "企业会员导入");
        }
    });
    //构造对话框
    $("#importMemberView").dialog( {
        width : "350",
        height : "250",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#importMemberForm").form("submit", {
                    url : "enterprise_improt_member.do",
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
                        var str = "共"+result.split(",")[0]+"条，成功导入"+result.split(",")[1]+"条。";
                        $.messager.alert("提示", str, "info");
                        $("#dataGrid").datagrid("reload");
                        $("#importMemberView").dialog("close");
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#importMemberView").dialog("close");
            }
        }]
    });

    //导入样表下载
    $("#membersAddUploud").bind("click",function(){
        //window.location.href = "";
    });

    //企业账户充值
    $("#moneyAdd").bind("click",function(){
        var selectedRow = $("#dataGrid").datagrid("getSelections");
        if(selectedRow.length <= 0){
            $.messager.alert("提示", "请选择要充值的企业", "error");
        }else{
            clearMoneyAddForm();
            var JsonData = selectedRow[0];
            $("#enterpriseId").val(JsonData.id);
            $("#enterpriseName").textbox("setValue",JsonData.enterpriseName);
            $("#moneyAddView").dialog("open").dialog("setTitle", "账户充值");
        }
    });

    //构造对话框
    $("#moneyAddView").dialog( {
        width : "350",
        height : "250",
        top : "80",
        buttons : [ {
            text : "保存",
            iconCls : "icon-save",
            handler : function() {
                $("#moneyAddForm").form("submit", {
                    url : "enterprise_money_add.do",
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
                    success : function(data) {
                        var result = JSON.parse(data);
                        console.log(data);
                        $.messager.progress('close');
                        if (result.code== 1) {
                            $.messager.alert("提示", "保存成功", "info");
                            $("#dataGrid").datagrid("reload");
                            $("#moneyAddView").dialog("close");
                        }else{
                            $.messager.alert("提示", result.message, "info");
                            $("#dataGrid").datagrid("reload");
                            $("#moneyAddView").dialog("close");
                        }
                    }
                });
            }
        }, {
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#moneyAddView").dialog("close");
            }
        }]
    });


    //清空
    $("#clearQuery").bind("click",function(){
        clearQueryForm();
    });

});


function clearQueryForm(){
    $('#queryForm').form('clear');
}