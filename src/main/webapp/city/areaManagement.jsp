<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>区域网点管理</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
    <script type="text/javascript" src="city/areaManagement.js"></script>
    <!-- 地图搜索 -->
    <script type="text/javascript" src="common/amapSearch.js"></script>
    <script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=add84e19ee7ef7540e570cbf4c82cbfa"></script>
    <style type="text/css">
        /*.ftitle {
            font-size: 14px;
            font-weight: bold;
            padding: 5px 0;
            margin-bottom: 10px;
            border-bottom: 1px solid #ccc;
        }

        .fitem {
            margin-bottom: 5px;
        }*/

        .fitem label {
            display: inline-block;
            width: 80px;
        }

        /*.fitem input {
            width: 160px;
        }

        .fitem a {
            margin-right: 5px;
        }*/

    </style>
    <script type="text/javascript">
        var mapObj;
        var lnglatXY;
        var marker = new Array();
        var windowsArr = new Array();
        function init(){
            var wd = '${lat}';
            var jd = '${lng}';
            mapInit(jd,wd);
        }
        //初始化地图
        function mapInit(val1,val2){
            var opt = {
                level: 10, //设置地图缩放级别
                center: new AMap.LngLat(val1,val2) //设置地图中心点
            }
            mapObj = new AMap.Map("iMap", opt);
            AMap.event.addListener(mapObj,'click',getLnglat); //点击事件
        }

        function geocoder() {
            var MGeocoder;
            //加载地理编码插件
            mapObj.plugin(["AMap.Geocoder"], function() {
                MGeocoder = new AMap.Geocoder({
                    radius: 1000,
                    extensions: "all"
                });
                //返回地理编码结果
                AMap.event.addListener(MGeocoder, "complete", geocoder_CallBack);
                //逆地理编码
                MGeocoder.getAddress(lnglatXY);
            });
            //加点
            var marker = new AMap.Marker({
                map:mapObj,
                icon: new AMap.Icon({
                    image: "images/Map_Marker.png",
                    size:new AMap.Size(24,24)
                }),
                position: lnglatXY,
                offset: new AMap.Pixel(-5,-30)
            });
            // mapObj.setFitView();
        }
        //回调函数
        function geocoder_CallBack(data) {
            var address;
            //返回地址描述
            address = data.regeocode.formattedAddress;
            //返回结果拼接输出
            $("#a-address").textbox("setValue",address);
            $("#e-address").textbox("setValue",address);
        }
        //鼠标点击，获取经纬度坐标
        function getLnglat(e){
            mapObj.clearMap();
            var x = e.lnglat.getLng();
            var y = e.lnglat.getLat();
            $("#a-longitude").textbox("setValue",x);
            $("#a-latitude").textbox("setValue",y);
            $("#e-longitude").textbox("setValue",x);
            $("#e-latitude").textbox("setValue",y);
            lnglatXY = new AMap.LngLat(x,y);
            geocoder();
        }

        $(function () {
            $('#e-cityCode').combobox({
                url: 'sys_cityCombobox',
                editable: false,
                valueField: 'id',
                textField: 'text',
                onSelect: function (record) {
                    $('#g-areaCode').combobox({
                        disabled: false,
                        url: 'sys_areaCodeCombobox?areaCode='+record.id,
                        valueField: 'id',
                        textField: 'text',
                        onSelect : function(r){
                            $('#e-areaName').val(r.text) ;
                            $('#e-areaCode').val(r.id) ;
                        }
                    }).combobox('clear');
                }
            });
            $('#g-areaCode').combobox({
                disabled: true,
                valueField: 'id',
                textField: 'text'
            });
        });
    </script>
</head>

<body  onload="init();">
<table id="dataGrid" toolbar="#toolbar"></table>
<!--列表工具栏 -->
<div id="toolbar" class="fitem" style="height:auto">
    <form action="" id="taskForm">

        所属城市:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;"
                    data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
        区域名称:<input id="area"  name="area" class="easyui-textbox" style="width:150px"/>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="clearQuery">清除查询条件</a>
        <r:FunctionRole functionRoleId="area_management_save">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">新增</a>
        </r:FunctionRole>
        <r:FunctionRole functionRoleId="area_management_edit">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">编辑</a>
        </r:FunctionRole>
        <r:FunctionRole functionRoleId="area_management_bind">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnBind">绑定网点</a>
        </r:FunctionRole>

    </form>
</div>



<div id="addView" class="easyui-dialog" closed="true" style="padding:10px 50px">
    <form name="addViewForm" id="addViewForm">
        <input type="hidden" name="id" >
        <div class="fitem">
            <tr>
                <td>城市:</td>
                <td><input class="easyui-combobox" name="cityCode" id="cityCode2" style="width: 80px;" required="true"
                                data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
                </td>
            </tr>
            <tr>
                <td>区域名称:</td>
                <td><input class="easyui-textbox" style="width: 80px;" name="areaName" required="true"  precision="1" missingMessage="请输入分值" id="score"></td>
            </tr>
        </div>
        <div class="fitem">
            纬度:<input  name="latitude" id="e-latitude" class="easyui-textbox"style="width: 80px;" required="true"  missingMessage="请填写纬度"/>
            经度:<input  name="longitude" id="e-longitude" class="easyui-textbox"style="width: 80px;" required="true"  missingMessage="请填写经度"/>
            <input type="text" name="s-address" class="easyui-textbox" id="s-address" style="width: 80px;">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"  onclick="searchAddress()">搜索地图</a>
        </div>
        <div id="iMap"  style="width:100%;height:300px;border:#ccc solid 1px;margin-top: 5px"></div>
    </form>
</div>

<div id="bindView" class="easyui-dialog" closed="true" style="height: 500px;">
    <table id="dataGridPark" toolbar="#toolbar2"></table>
    <!--列表工具栏 -->
    <div id="toolbar2" class="fitem" style="height:30px">
        <form action="" id="bindForm">
            <input type="hidden" name="id" id="areaid">
            所属城市:<input class="easyui-combobox" name="cityCode" id="city_code" style="width: 80px;"
                        data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
            网点名称:<input id="park"  name="parkName" class="easyui-textbox" style="width:150px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="ParkbtnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="ParkclearQuery">清除查询条件</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnBindPark">绑定</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnRemove">移除</a>
        </form>
    </div>
</div>

<div id="addParkView" class="easyui-dialog" closed="true">
    <%--<form id="addParkForm" method="post" action=""
          enctype="multipart/form-data" onsubmit="checkCustomer(this)">
        <input type="hidden" name="enterprise_id" id="enterprise_id" />

        <table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">
            <tr>
                <td>网点名称:</td>
                <td>
                    <input class="easyui-combogrid"  id="parkName" name="parkName" style="width:300px;height:24px">
                </td>
            </tr>

        </table>

    </form>--%>
        <table id="dataGridParkBind" toolbar="#toolbarBind"></table>
        <!--列表工具栏 -->
        <div id="toolbarBind" class="fitem" style="height:30px">
            <form action="" id="ParkbindForm">
                所属城市:<input class="easyui-combobox" name="cityCode" id="city_code2" style="width: 80px;"
                            data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
                网点名称:<input id="parkName"  name="parkName" class="easyui-textbox" style="width:150px"/>
                网点地址:<input id="address"  name="address" class="easyui-textbox" style="width:150px"/>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="parkQuery">查询</a>
            </form>
        </div>
</div>

</body>
</html>