<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>附属订单列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="timeShare/orderAttached.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
	
	<style type="text/css" >
	#conversionForm {
		margin: 0;
		padding: 10px 30px;
	}
	
	.ftitle {
		font-size: 14px;
		font-weight: bold;
		padding: 5px 0;
		margin-bottom: 10px;
		border-bottom: 1px solid #ccc;
	}
	
	.fitem {
		margin-bottom: 5px;
	}
	
	.fitem label {
		display: inline-block;
		width: 80px;
		
	}
	
	.fitem input {
		width: 160px;
	}
	
	.fitem a {
		margin-right: 5px;
	}
	#sp1{margin-right:10px;}
	</style>
</head>

<body>
   
	<table id="order_attached_grid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
	<form action="" id="orderAttachedForm">
		<div>
			订单编号:<input type="text" id="orderId"  class="easyui-textbox" style="width: 100px">
			责任类型:<input class="easyui-combobox" name="toUser" id="toUser" style="width: 100px"
						data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						}
	                    ,{
							label: '用户责任',
							value: '1'
						},{
							label: '我方责任',
							value: '0'
						}],
	                    panelHeight:'auto'">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px"
				   data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
			姓名:<input type="text" id="memberName"  class="easyui-textbox" style="width: 100px">
			手机号码:<input type="text" id="phoneNumber"  class="easyui-textbox" style="width: 100px">
			车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width: 100px">
			创建时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox s" style="width:160px"/>
			到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datetimebox e" style="width:160px"/>
			订单类型: <input class="easyui-combobox" name="type" id="type" style="width: 80px"
										data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						}<%--,{
							label: '违章',
							value: '0'
						}--%>,{
							label: '维修',
							value: '1'
						},{
							label: '救援',
							value: '2'
						}],
	                    panelHeight:'auto'">
			是否支付: <input class="easyui-combobox" name="ispay" id="ispay" style="width: 80px"
						 data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '全部',
							value: ''
						}
	                    ,{
							label: '未支付',
							value: '0'
						},{
							label: '已支付',
							value: '1'
						}],
	                    panelHeight:'auto'">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
		<r:FunctionRole functionRoleId="save_order_attached">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnadd">新增</a>
		</r:FunctionRole>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-more" plain="true" id="btn_view_detail">查看详情</a>
		<r:FunctionRole functionRoleId="export_order_attached">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</div>
		</form>
	</div>

	<div id="addView" class="easyui-dialog" closed="true"
		 style="padding:10px 20px;height: 271px;">
		<form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data">
			<div class="fitem">
				<label>订单编号:</label> <input  name="orderId"  class="easyui-textbox"  required="true" id="ordernum"  missingMessage="订单编号"/>
				<label>创建原因:</label> <input  name="createReason"   class="easyui-textbox"    missingMessage="创建原因"/>
			</div>

			<div class="fitem">
				<label>订单类型:</label> <input class="easyui-combobox" name="type"
											editable="false" data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [<%--{
							label: '违章',
							value: 'VIOLATION'
						},--%>{
							label: '维修',
							value: 'REPAIR'
						},{
							label: '救援',
							value: 'RESCUE'
						}],
	                    panelHeight:'auto'" required="true"  missingMessage="请选择订单类型">
				<label>责任类型:</label> <input class="easyui-combobox" name="toUser" id="typeSelect"
											editable="false" data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '用户责任',
							value: '1'
						},{
							label: '我方责任',
							value: '0'
						}],
	                    panelHeight:'auto'" required="true"  missingMessage="责任类型">
			</div>

			<div class="fitem item">
				误&nbsp&nbsp&nbsp工&nbsp&nbsp&nbsp&nbsp费:<input type="checkbox" name="item[0].ischeck" id="itemcheck1" style="width: 15px"/><input type="hidden" name="item[0].item" value="DELAY">
				 金额: <input class="easyui-numberbox"  disabled="ture"  precision="2" style="width: 60px;margin-left: 0px;" id="days"/>天×
				<input class="easyui-numberbox" disabled="ture"  precision="2" style="width: 60px;margin-left: 0px;" id="cost"/>元
				<label style="width: 20px">=</label><input class="easyui-numberbox"  precision="2"  readonly="ture" name="item[0].money" id="allcost" style="width: 50px;"/>
				说明:<input class="easyui-textbox" name="item[0].explanation" readonly="ture" style="width: 110px;margin-left: 0px;"  id="explanation">
			</div>
			<div class="fitem item">
				救&nbsp&nbsp&nbsp援&nbsp&nbsp&nbsp&nbsp费:<input type="checkbox"  name="item[1].ischeck"  id="itemcheck2" style="width: 15px"/><input type="hidden" name="item[1].item" value="RESCUE">
				金额: <input class="easyui-numberbox" name="item[1].money" disabled="ture" id="money2"  precision="2"  style="width: 80px;margin-left: 0px;"/>
				说明:<input class="easyui-combobox" editable="false" name="item[1].explanation" id="explanation2" style="width: 80px;margin-left: 0px;"
						  data-options="
	                  	valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '市内',
							value: '市内救援'
						},{
							label: '市外',
							value: '市外救援'
						}],
	                    panelHeight:'auto'" >
			</div>
			<div class="fitem item">
				维&nbsp&nbsp&nbsp修&nbsp&nbsp&nbsp&nbsp费:<input type="checkbox" name="item[2].ischeck" id="itemcheck3" style="width: 15px"/><input type="hidden" name="item[2].item" value="REPAIR">
				金额: <input class="easyui-numberbox" name="item[2].money" disabled="ture" id="money3"  precision="2" style="width: 80px;margin-left: 0px;"/>
				说明:<input class="easyui-textbox" name="item[2].explanation" style="width: 260px;margin-left: 0px;" >
			</div>
			<div class="fitem item">
				保险上涨费:<input type="checkbox" name="item[3].ischeck" id="itemcheck4" style="width: 15px"/>
				金额: <input class="easyui-numberbox" name="item[3].money" disabled="ture" id="money4"  precision="2" style="width: 80px;margin-left: 0px;"/><input type="hidden" name="item[3].item" value="INSURANCE">
				说明:<input class="easyui-textbox" name="item[3].explanation" style="width: 260px;margin-left: 0px;" >
			</div>
			<div style="margin-bottom:10px" id="repair_license_pic">
				<span>维修单据:</span>
				<input  name="repairUriFile" id="repair" type="file" onchange="javascript:setImagePreview(this,repairLocalImag,addRepairImg);"  data-options="prompt:'维修单据...',required:true"  style="width:100%;height:24px">
				<div id="repairLocalImag">
					<img style="margin-left: 0px;" width="200"  id="addRepairImg" />
				</div>
				<span>车辆损毁照片:</span>
				<input  name="carDamageUriFile" id="carDamage" type="file" onchange="javascript:setImagePreview(this,carDamageImag,addCarDamageImg);"  data-options="prompt:'车辆损毁照片...',required:true"  style="width:100%;height:24px">
				<div id="carDamageImag">
					<img style="margin-left: 0px;" width="200"  id="addCarDamageImg" />
				</div>
			</div>
			<%--<div class="fitem item">
				违章处理费:<input type="checkbox" name="item[3].ischeck" id="itemcheck4"   style="width: 15px"/><input type="hidden" name="item[3].item" value="VIOLATION">
				金额: <input class="easyui-numberbox" name="item[3].money" disabled="ture" id="money4"precision="2" style="width: 80px;margin-left: 0px;"/>
				说明:<input class="easyui-textbox" name="item[3].explanation" style="width: 260px;margin-left: 0px;">
				&lt;%&ndash;时间:<input id="violationtime"  name="violationtime" class="easyui-datebox" style="width:100px"/>&ndash;%&gt;
			</div>--%>
			<%--<div class="fitem item">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				地点:<input id="place"  name="place" class="easyui-textbox"style="width:100px"/>
				行为:<input id="action"  name="action" class="easyui-textbox"style="width:100px"/>
			</div>--%>
		</form>
	</div>

	<div id="addView2" class="easyui-dialog" closed="true" style="height: 300px">
		<table id="table_view_detail" style="width: 33em"></table>
	</div>
</body>
<script type="text/javascript">
    //检查图片的格式是否正确,同时实现预览
    function setImagePreview(obj, localImagId, imgObjPreview) {
        var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp','GIF', 'JPEG', 'PNG', 'JPG', 'BMP'); // 可以上传的文件类型
        if (obj.value == '') {
            $.messager.alert("请选择要上传的图片!");
            return false;
        }else {
            var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; // 这个文件类型正则很有用
            // //布尔型变量
            var isExists = false;
            // 循环判断图片的格式是否正确
            for (var i in array) {
                if (fileContentType.toLowerCase() == array[i].toLowerCase()) {
                    // 图片格式正确之后，根据浏览器的不同设置图片的大小
                    if (obj.files && obj.files[0]) {
                        // 火狐下，直接设img属性
                        imgObjPreview.style.display = 'block';
                        //    imgObjPreview.style.width = '100px';
                        //      imgObjPreview.style.height = '130px';
                        // 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
                        imgObjPreview.src = window.URL.createObjectURL(obj.files[0]);
                    }else {
                        // IE下，使用滤镜
                        obj.select();
                        var imgSrc = document.selection.createRange().text;
                        // 必须设置初始大小
                        localImagId.style.display = "";
                        localImagId.style.width = "230px";
                        // 图片异常的捕捉，防止用户修改后缀来伪造图片
                        try {
                            localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                            localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;
                        }catch (e) {
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

    function display(id){
        document.getElementById("box"+id).style.display="block";
    }
    function disappear(id){
        document.getElementById("box"+id).style.display="none";
    }
    function display_box(id){
        document.getElementById("box-"+id).style.display="block";
    }
    function disappear_box(id){
        document.getElementById("box-"+id).style.display="none";
    }
</script>
</html>