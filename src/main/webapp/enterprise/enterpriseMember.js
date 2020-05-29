function clearToolbar(){
    $('#toolbar').form('clear');
}
$(function(){
    //清空
    $("#clearQuery").bind("click",function(){
        clearToolbar();
    });
    $('#grid').datagrid({
        title : '企业会员管理',
        width : 'auto',
        height : 'auto',
        fit : true,
        fitColumns : false,
        nowrap : true,
        striped : true,
        collapsible : true,
        singleSelect : true,
        url : 'enterpriseMember_list',
        queryParams:{
            'cityCode':$("#scityCode").combobox("getValue"),
            'name':$("#s-name").textbox("getValue"),
            'phone':$("#s-phone").textbox("getValue"),
            'enterpriseName':$("#s-enterpriseName").textbox("getValue")
        },
        pageSize : 100,
        pageList : [100,50,30,10],
        idField : 'id',
        columns : [ [ {
            field : 'ck',
            checkbox : true

        }, {
            field : 'cityName',
            title : '所属城市',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'enterpriseName',
            title : '所属企业',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'name',
            title : '姓名',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'sex',
            title : '性别',
            width : $(this).width() * 0.08,
            align : 'center'
        }, {
            field : 'phone',
            title : '手机号码',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'email',
            title : 'Email',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'idcard',
            title : '身份证号码',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'driverIdcard',
            title : '驾驶证号码',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'driverIdcardPhotoUrl',
            title : '驾驶证照片',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(null == val || val == ""){
                    return "未上传";
                }else{
                    return "已上传";
                }
            }
        },{
            field : 'fingerPrint',
            title : '指纹',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(null == val || val == ""){
                    return "未录入";
                }else{
                    return "已录入";
                }
            }
        },{
            field : 'status',
            title : '状态',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val == "ready")
                    return "就绪";
                else if(val == "experience")
                    return "体验";
                else if(val=="ordered")
                    return "预约";
                else if(val=="useCar")
                    return "用车";
                else if(val=="planReturn")
                    return "计划还车";
                else if(val=="waitQueue")
                    return "排队等待";
                else if(val=="wait")
                    return "等待用车";
                else if(val=="return")
                    return "还车";
                else return val;
            }
        },{
            field : 'registerIp',
            title : '注册IP',
            width : $(this).width() * 0.08,
            align : 'center'
        },{
            field : 'registerCategory',
            title : '注册终端',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val == "platform"){
                    return "平台注册";
                }else if(val == "member"){
                    return "手机终端";
                }else{
                    return "其它";
                }
            }
        },{
            field : 'accoutStatus',
            title : '资金状态',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val == "0"){
                    return "正常";
                }else{
                    return "冻结";
                }
            }
        },{
            field : 'memberLevel',
            title : '员工等级',
            width : $(this).width() * 0.08,
            align : 'center',
            formatter : function(val, rec) {
                if(val == "account"){
                    return "企业账号";
                }else if(val == "manager"){
                    return "企业管理员";
                }else if(val == "person"){
                    return "企业普通会员";
                }else if(val == "platinum"){
                    return "白金企业用户";
                }else if(val == "general"){
                    return "个人普通";
                }else if(val == "vip"){
                    return "vip会员";
                }else if(val == "gold"){
                    return "黄金会员";
                }else{
                    return val;
                }
            }
        },{
            field : 'createTime',
            title : '注册时间',
            width : $(this).width() * 0.08,
            align : 'center'
        }] ],
        pagination : true,
        rownumbers : true
    });


    //add view
    $("#addView").dialog( {
        width : "400",
        height : "480",
        top : "80",
        modal:true,
        title:"添加会员信息",
        buttons : [{
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                $("#addViewForm").form("submit",{
                    url:'member_save',
                    onSubmit:function(){
                        return $(this).form("validate");
                    },
                    success:function(result) {
                        if (result== "succ") {
                            $.messager.alert("提示", "操作成功", "info");
                            $("#grid").datagrid("reload");
                            $("#addView").dialog("close");
                        } else if(result == "fail") {
                            $.messager.alert("提示", "操作失败", "error");
                        }else if(result== "phoneExist"){
                            $.messager.alert("提示", "手机号码已存在", "error");
                        }else if(result== "phoneFormatErr"){
                            $.messager.alert("提示", "手机号码格式不正确", "error");
                        }else{
                            $.messager.alert("提示", "操作失败", "error");
                        }
                    }
                });
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#addView").dialog("close");
            }
        }]
    });



    $("#editView").dialog( {
        width : "400",
        height : "480",
        top : "80",
        modal:true,
        title:"修改企业会员信息",
        buttons : [{
            text : "确定",
            iconCls : "icon-save",
            handler : function() {
                $("#editViewForm").form("submit",{
                    url:'member_edit',
                    onSubmit:function(){
                        return $(this).form("validate");
                    },
                    success:function(result) {
                        if (result== "succ") {
                            $.messager.alert("提示", "操作成功", "info");
                            $("#grid").datagrid("reload");
                            $("#editView").dialog("close");
                        } else if(result == "fail") {
                            $.messager.alert("提示", "操作失败", "error");
                        }else{
                            $.messager.alert("提示", "操作失败", "error");
                        }
                    }
                });
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#editView").dialog("close");
            }
        }]
    });


    //query
    $("#btnQuery").bind("click",function(){
        $("#grid").datagrid("load",{
            'cityCode':$("#scityCode").combobox("getValue"),
            'name':$("#s-name").textbox("getValue"),
            'phone':$("#s-phone").textbox("getValue"),
            'enterpriseName':$("#s-enterpriseName").textbox("getValue")
        });
    });

    //clear
    $("#btnClear").bind("click",function(){
        $("#memberSearchForm").form("clear");
    });


    $("#btnSave").bind("click",function(){
        $("#addViewForm").form("clear");
        $("#driverIdcardFilePhoto").attr("src","");
        $("#addView").dialog("open");
    });

    $("#btnEdit").bind("click",function(){
        $("#addViewForm").form("clear");
        var selectedRows = $("#grid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择需要修改的企业用户", "error");
        }else{
            $("#e-id").val(selectedRows[0].id);
            $("#e-phone").textbox("setValue",selectedRows[0].phone);
            $("#e-cityCode").combobox("setValue",selectedRows[0].cityCode);
            $("#e-name").textbox("setValue",selectedRows[0].name);
            $("#e-sex").combobox("setValue",selectedRows[0].sex);
            $("#e-email").textbox("setValue",selectedRows[0].email);
            $("#e-idcard").textbox("setValue",selectedRows[0].idcard);
            $("#e-driverIdcard").textbox("setValue",selectedRows[0].driverIdcard);
            //alert(selectedRows[0].driverIdcardPhotoUrl);
            $("#edriverIdcardFilePhoto").attr("src", selectedRows[0].driverIdcardPhotoUrl);
            $("#editView").dialog("open");
        }
    });

    //删除用户指纹信息
    $("#btnFinger").bind("click",function(){
        var selectedRows = $("#grid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择要删除指纹的用户", "error");
        }else if(selectedRows[0].fingerPrint == null || selectedRows[0].fingerPrint == ""){
            $.messager.alert("提示", "用户未录入指纹", "error");
        }else{
            var _id =  selectedRows[0].id;
            $.messager.confirm("提示","确定要删除吗?",function(r){
                if(r){
                    $.post("member_del_finger",{id:_id},function(data){
                        if(data == "succ"){
                            $.messager.alert("提示", "操作成功", "info");
                            $("#grid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "操作失败", "error");
                        }
                    },"text");
                }
            });
        }
    });

    //用户重置密码
    $("#btnRestPassword").bind("click",function(){
        var selectedRows = $("#grid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择要重置密码的用户", "error");
        }else{
            var _id =  selectedRows[0].id;
            $.messager.confirm("提示","确定要重置吗?<br/><br/><b>重置后的密码为手机号码后6位</b>",function(r){
                if(r){
                    $.post("member_rest_password",{id:_id},function(data){
                        if(data == "succ"){
                            $.messager.alert("提示", "操作成功", "info");
                            $("#grid").datagrid("reload");
                        }else{
                            $.messager.alert("提示", "操作失败", "error");
                        }
                    },"text");
                }
            });
        }
    });


    $("#examineView").dialog( {
        width : "480",
        height : "520",
        top : "100",
        left:"400",
        modal:true,
        title:"会员实名认证及驾照审核",
        buttons : [{
            text : "实名认证",
            iconCls : "icon-ok",
            id:'examineBtn',
            handler : function() {
                var _name = $("#examine_name").textbox("getValue");
                var _idcard = $("#examine_idcard").textbox("getValue");
                $.post("member_examine",{name:_name, idcard:_idcard},function(data){
                    if(data == "1"){
                        $("#examine_result_1").html("实名认证通过");
                    }else{
                        $("#examine_result_2").html("实名认证不通过");
                    }
                },"text");
                $("#examineBtn").linkbutton({ disabled: true });
            }
        },{
            text : "审核",
            iconCls : "icon-save",
            handler : function() {
                $("#examineForm").form("submit",{
                    url:'member_examine_save',
                    onSubmit:function(){
                        return $(this).form("validate");
                    },
                    success:function(result) {
                        if (result== "succ") {
                            $.messager.alert("提示", "操作成功", "info");
                            $("#grid").datagrid("reload");
                            $("#examineView").dialog("close");
                        } else if(result == "fail") {
                            $.messager.alert("提示", "操作失败", "error");
                        }else{
                            $.messager.alert("提示", "操作失败", "error");
                        }
                    }
                });
            }
        },{
            text : "取消",
            iconCls : "icon-cancel",
            handler : function() {
                $("#examineView").dialog("close");
            }
        }]
    });


    $("#examineViewImg").dialog( {
        left:"10",
        top:"10",
        title:"驾照预览",
    });

    //审核
    $("#btnExamine").bind("click",function(){
        $("#examineForm").form("clear");
        $("#examineBtn").linkbutton({ disabled: false });
        $("#examine_result_1").html("");
        $("#examine_result_2").html("");
        var selectedRows = $("#grid").datagrid("getSelections");
        if(selectedRows.length <= 0){
            $.messager.alert("提示", "请选择实名认证及驾照审核的用户", "error");
        }else if(selectedRows[0].driverIdcard == "" || selectedRows[0].driverIdcardPhotoUrl == "" ){
            $.messager.alert("提示", "该会员资料不完善，请先完善资料", "error");
        }else if(selectedRows[0].status != "experience"){
            $.messager.alert("提示", "该会员已经审核通过", "error");
        }else{
            $("#examine_id").val(selectedRows[0].id);
            $("#examine_name").html(selectedRows[0].name);
            $("#examine_phone").html(selectedRows[0].phone);
            $("#examine_idcard").html(selectedRows[0].idcard);
            $("#examine_driverIdcard").html(selectedRows[0].driverIdcard);
            // $("#examine_name").textbox("setValue",selectedRows[0].name);
            //$("#examine_phone").textbox("setValue",selectedRows[0].phone);
            //$("#examine_idcard").textbox("setValue",selectedRows[0].idcard);
            //$("#examine_driverIdcard").textbox("setValue",selectedRows[0].driverIdcard);
            //alert(selectedRows[0].driverIdcardPhotoUrl);
            $("#examine_driverIdcardPhotoUrl").attr("src", selectedRows[0].driverIdcardPhotoUrl);
            $("#examine_driverIdcardPhotoUrlAHERF").attr("href", "drierCarImgOpen?img="+selectedRows[0].driverIdcardPhotoUrl);
            $("#examineView").dialog("open");
        }
    });

});




//检查图片的格式是否正确,同时实现预览
function setImagePreview(obj, localImagId, imgObjPreview) {
    //alert(imgObjPreview.src);
    var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp'); //可以上传的文件类型
    if (obj.value == '') {
        $.messager.alert("让选择要上传的图片!");
        return false;
    }
    else {
        var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; //这个文件类型正则很有用
        ////布尔型变量
        var isExists = false;
        //循环判断图片的格式是否正确
        for (var i in array) {
            if (fileContentType.toLowerCase() == array[i].toLowerCase()) {
                //图片格式正确之后，根据浏览器的不同设置图片的大小
                if (obj.files && obj.files[0]) {
                    //火狐下，直接设img属性
                    imgObjPreview.style.display = 'block';
                    imgObjPreview.style.width = '160px';
                    imgObjPreview.style.height = '120px';
                    //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                    imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);
                }
                else {
                    //IE下，使用滤镜
                    obj.select();
                    var imgSrc = document.selection.createRange().text;
                    //必须设置初始大小
                    localImagId.style.display = "";
                    localImagId.style.width = "160px";
                    localImagId.style.height = "120px";
                    //图片异常的捕捉，防止用户修改后缀来伪造图片
                    try {
                        localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                        localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;
                    }
                    catch (e) {
                        $.messager.alert("您上传的图片格式不正确，请重新选择!");
                        return false;
                    }
                    imgObjPreview.style.display = 'none';
                    document.selection.empty();
                }
                isExists = true;
                return true;
            }
        }
        if (isExists == false) {
            $.messager.alert("上传图片类型不正确!");
            return false;
        }
        return false;
    }
}



//显示图片
function over(imgid, obj) {
    //大图显示的最大尺寸  4比3的大小 160 120
    maxwidth = 160;
    maxheight = 120;
    //1、宽和高都超过了，看谁超过的多，谁超的多就将谁设置为最大值，其余策略按照2、3
    //2、如果宽超过了并且高没有超，设置宽为最大值
    //3、如果宽没超过并且高超过了，设置高为最大值
    if (img.width > maxwidth && img.height > maxheight) {
        pare = (img.width - maxwidth) - (img.height - maxheight);
        if (pare >= 0){
            img.width = maxwidth;
        }else{
            img.height = maxheight;
        }
    }else if (img.width > maxwidth && img.height <= maxheight) {
        img.width = maxwidth;
    }else if(img.width <= maxwidth && img.height > maxheight){
        img.height = maxheight;
    }
}
