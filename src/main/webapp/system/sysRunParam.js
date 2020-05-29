

$(function() {
    /**
	 * 初始化短信配置combobox
     */
    $.get("sys_param_init",function(data){
        if(data){
        	$("#sys_run_param").combobox("setValue",data);
        }
    });

    /**
	 * 点击保存
     */
	$("#btnQuery").click(function () {
        $.post("sys_param_save",{"value":$("#sys_run_param").combobox("getValue")},function(data){
            if(data=='ok'){
                $.messager.alert("提示", "保存成功", "info");
            }else{
                $.messager.alert("提示", "保存失败", "info");
			}
        },"text");
    })

    /**
     * 点击保存
     */
    $("#btnSend").click(function () {
        var phone = $("#phone").textbox("getValue");
        $.post("sys_param_send",{"phone":phone},function(data){
            if(data=='ok'){
                $.messager.alert("提示", "发送成功", "info");
            }else{
                $.messager.alert("提示", "发送失败", "info");
            }
        },"text");
    })
});