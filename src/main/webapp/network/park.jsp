<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>网点管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="network/park.js"></script>
<!-- 地图搜索 -->
<script type="text/javascript" src="common/amapSearch.js"></script>
<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=add84e19ee7ef7540e570cbf4c82cbfa"></script>
<style type="text/css">
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
    //document.getElementById("adpointname").innerHTML = address;
    $("#a-address").textbox("setValue",address);
    $("#e-address").textbox("setValue",address);
   // $("#e-areaName").textbox("setValue",data.regeocode.addressComponent.district);
    //$("#e-areaCode").textbox("setValue",data.regeocode.addressComponent.district);
}  
//鼠标点击，获取经纬度坐标  
function getLnglat(e){    
	mapObj.clearMap();
	var x = e.lnglat.getLng();
	var y = e.lnglat.getLat(); 
	//alert(x + "," + y);
	//document.getElementById("coordinate").innerHTML = x + "," + y;
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
         /*onSelect: function (record) {
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
         }*/
     });
     $('#g-areaCode').combobox({
         onSelect : function(r){
             $('#e-areaName').val(r.text) ;
             $('#e-areaCode').val(r.value) ;
         }
     });
 });
        
</script>
</head>
<body onload="init();">

 <table id="parkGrid" toolbar="#toolbar"></table>
    
   <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
   		<form action="" id="parkFrom">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode" style="width: 100px"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		网点名称:<input type="text" name="name" class="easyui-textbox" id="sname" style="width: 120px" >
		所属城/镇区:<input class="easyui-combobox" name="gareaCode" id = "gareaCode" data-options="
				url: 'get_area_group_by_city',
				method: 'get',
				valueField:'value',
				textField:'text',
				groupField:'group'
				">
		网点功能:</label> <input class="easyui-combobox" name="parkType" style="width: 80px"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '车',
						value: '1'
					},{
						label: '桩',
						value: '2'
					},{
						label: '车&桩',
						value: '3'
					}]"
					 id="s-parkType" >
		网点类型:</label> <input class="easyui-combobox" name="category" style="width: 80px"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '全部',
						value: ''
					},{
						label: '1S网点',
						value: '1'
					},{
						label: '2s网点',
						value: '2'
					},{
						label: '4s网点',
						value: '3'
					}]"
					 id="s-category" >
		网点属性:</label> <input class="easyui-combobox" name="isTemporary" style="width: 90px"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '全部',
						value: ''
					},{
						label: '临时网点',
						value: '0'
					},{
						label: '运营网点',
						value: '1'
					}]"
					 id="s-isTemporary" >
		网点状态:<label><input class="easyui-combobox" name="status" style="width: 80px"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '全部',
						value: ''
					},{
						label: '开启',
						value: '1',
						selected : true
					},{
						label: '关闭',
						value: '0'
					}]"
					 id="s-status" ></label>
		车位满时不可还车:<label><input class="easyui-combobox" name="fullNoParking" style="width: 100px"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '全部',
						value: ''
					},{
						label: '否',
						value: '0'
					},{
						label: '是',
						value: '1'
					}]"
					 id="s-fullNoParking" ></label>			 
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		<r:FunctionRole functionRoleId="add_carparking">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_carparking">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="set_park_status">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setStatus" onclick=";">网点关闭</a>
		</r:FunctionRole>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="cancelStatus" onclick=";">取消网点预开启</a>                                 -->
<!-- 		<r:FunctionRole functionRoleId="delete_carparking"> -->
<!-- 			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" onclick=";">删除</a> -->
<!-- 		</r:FunctionRole> -->
		<r:FunctionRole functionRoleId="install_elec_fence">
			
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setFence" onclick=";">设置电子围栏</a> 
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="remove_elec_fence">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-no" plain="true" id="removeFence" onclick=";">移除电子围栏</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="examine_map">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-marker_squared_red" plain="true" id="btnMapLook" >地图查看</a>                      
		</r:FunctionRole>
		<!-- <r:FunctionRole functionRoleId="set_status">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setStatus" onclick=";">设置状态</a> 
		</r:FunctionRole>    -->
		<r:FunctionRole functionRoleId="export_park">
	         <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
	    </r:FunctionRole>
	   </form>
   </div>
    
   <!-- 添加 网点信息 -->
   <div id="view" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="viewForm" id="viewForm" method="post" >
            <input type="hidden" id="eid" name="id">
           <div class="fitem">
				<label>网点名称:</label> <input  name="name" id="e-name"   class="easyui-textbox" required="true"  missingMessage="网点名称如：大厦、广场、公寓..."/>
				 <span style="margin-left: 99px;"></span>
				<label>责任人:</label> <input  name="liablePerson" id="e-liablePerson"    class="easyui-textbox"/>
		   </div>
           <div class="fitem">
				<label>网点地址:</label> <input  name="address" id="e-address"  class="easyui-textbox" style="width: 81%;"  	required="true"  missingMessage="请填写网点地址（区＋详细地址）"/>
		   </div>
           <div class="fitem">
				<label>所属城市:</label>
				<input class="easyui-combobox" id="e-cityCode" name="cityCode" style="width:166px"
                                 data-options="valueField:'id', textField:'text', panelHeight:'auto'" required="true" missingMessage="请选择所属城市">  
                                 
				<!-- <input class="easyui-combobox" name="cityCode" 
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true" id="e-cityCode" missingMessage="请选择所属城市"> -->
		        <span style="margin-left: 96px;"></span>
				<label>所属城/镇区:</label>
			   <input class="easyui-combobox" name="gareaCode" id = "g-areaCode" data-options="
				url: 'get_area_group_by_city',
				method: 'get',
				valueField:'value',
				textField:'text',
				groupField:'group'
				">
				<input  name="areaName" id="e-areaName" type="hidden"/>
				<input  name="areaCode" id="e-areaCode" type="hidden"/>
		   </div>
           <div class="fitem">
				<label>纬度:</label> <input  name="latitude" id="e-latitude" class="easyui-textbox" required="true"  missingMessage="请填写纬度"/>
				<span style="margin-left: 99px;"></span>
				<label>经度:</label> <input  name="longitude" id="e-longitude" class="easyui-textbox" required="true"  missingMessage="请填写经度"/>
		   </div>
		   <div class="fitem">
				<label>运营开始时间:</label>
				<input class="easyui-textbox" required="true" missingMessage="请填写运营开始时间" 
				onchange="" name="runStartTime" id="e-runStartTime" />
				<span style="color:red;" id="run_start_time_result"></span>
			</div>
			<div class="fitem">
				<label>运营结束时间:</label>
				<input class="easyui-textbox" required="true" missingMessage="请填写运营结束时间" 
				onchange="" name="runEndTime" id="e-runEndTime" />
				<span style="color:red;" id="run_end_time_result"></span>
			</div>
			
		   <!-- <div class="fitem">  
				<label>网点功能:</label> <input class="easyui-combobox" name="parkType"
					data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '车',
						value: '1'
					},{
						label: '桩',
						value: '2'
					},{
						label: '车&桩',
						value: '3'
					}]"
					required="true" id="e-parkType" missingMessage="请选择">

		         <span style="margin-left: 99px;"></span>
				<label>责任人:</label> <input  name="liablePerson" id="e-liablePerson"    class="easyui-textbox"/>
		   </div> -->
		   <div class="fitem">
				<label>网点类型:</label>
				<input class="easyui-combobox" name="category" id="e-category" style="width: 25%;"
					data-options=" url:'parkCategoryCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择网点类型">
				<span style="margin-left: 96px;"></span>
				<label>停车位数量:</label> <input  name="parkNums" id="e-parkNums"   class="easyui-textbox" 	required="true"  missingMessage="请填写停车位数量"/>
		   </div>
		  
		   <div class="fitem">
				<label>停车费:</label> <input  name="parkFee" id="e-parkFee"   class="easyui-textbox" required="true"  missingMessage="请填写网点每小时费用"/>&nbsp;元
				 <span style="margin-left: 80px;"></span>
				<label>停车费描述:</label> <input  name="parkFeeDesc" id="e-parkFeeDesc"    class="easyui-textbox"/>
		   </div>
		   <div class="fitem">
		         <label>是否是临时网点:</label> <input  name="isTemporary" id="isTemporary"    class="easyui-combobox"
				data-options="
				valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '是',
						value: '0'
					},{
						label: '否',
						value: '1'
					}]"
					required="true" missingMessage="是否是临时网点"
				/>
				<span style="margin-left: 60px;"></span>
			</div>
		    <div class="fitem">
				<label>网点合作类型:</label> <input  name="cooperationType" id="e-cooperation_type"   class="easyui-combobox"
				data-options="
					valueField: 'value',
					textField: 'label',
					panelHeight:'auto',
					data: [{
						label: '自有网点',
						value: '0'
					},{
						label: '合作网点',
						value: '1'
					}]"
					required="true" missingMessage="请选择网点合作类型"
				/>
			    <span style="margin-left: 60px;"></span>
		   		<span id="s1" style="display:none;" >运营商组织机构代码:&nbsp; <input  name="operatorId" id="operatorId" class="easyui-textbox" /> </span>
		   		<span id="s2" style="display:none; margin-left:30px;">所属物业公司:&nbsp; <input  name="propertyManagementCompany" id="propertyManagementCompany" class="easyui-textbox" /> </span>
		   </div>
		   <div class="fitem">
				<label>网点全景链接:</label>
				<input class="easyui-textbox"  name="parkPanoramaLink" id="e-parkPanoramaLink" />
			</div>
		   <div class="fitem">
			   <label style="width: 200px">车位满时不可还车:</label>
			   <input  name="fullNoParking" id="fullNoParking" type="checkbox" value="1" style="width: 20px"/>
			   <span style="margin-left: 96px;"></span>
			   <label>能否共存多个片区:</label> <input name="isCoexist" id="isCoexist" value="1" style="width: 20px" type="checkbox"/>
		   </div>
		   <div class="fitem">
				<label>网点描述:</label> <input  name="remark" id="e-remark" class="easyui-textbox" style="width: 34%;"/>
		   		<!-- <span style="margin-left: 60px;"></span>
		   		运营商组织机构代码:&nbsp; <input  name="operatorId" id="operatorId" class="easyui-textbox" /> -->
		   </div>

		   <div id="iMap"  style="width:98%;height:300px;border:#ccc solid 1px;"></div>
		   <!-- 查询，悬浮 -->
			<div id="searchFrame" style="width:200px; background:#fff; border:1px solid #666;z-index:3;position:absolute; top:260px;right:40px;">
				<div id="searchContent" style="margin:5px;" align="center">
					<input type="text" name="s-address" class="easyui-textbox" id="s-address" style="height:30px;width: 100px;">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick="searchAddress();">查询</a>
				</div>
			</div>
       </form>
   </div>
   
   	<!-- view  style="padding:10px 20px"  style="width: 350px;height: 250px;"-->
  <div id="fenceView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
       <form name="fenceViewForm" id="fenceViewForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
			<input type="hidden" id="g-id" name="groupId" />
       		<input type="hidden" name='category' id="g-category" value="park">
       		<div cellpadding="5" style="font-size: 12px;margin:0 auto;margin-top: 50px;">
       				<label>请选择电子围栏:</label>
		            <!-- 换为用combogrid -->
	    				<input class="easyui-combogrid" name="fenceId" id="g-fence" style="width:40%;height:24px" >
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:10%;" title="清空"></a>
		   </div>
       </form>
   </div>  
   
    <div id="mapLookDialgView" class="easyui-dialog" closed="true"style="padding:10px 20px">	
   	    <div id="imapLookDialgView"  style="width:99.9%;height:380px;border:#ccc solid 1px;"></div>
   	</div>
   	
</body>
</html>