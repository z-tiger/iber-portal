<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="car/carMrg.js"></script>
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
</head>
<body>

 <table id="carMrgGrid" toolbar="#toolbar"></table>

 
    <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
   <form action="" id="carMrgForm">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
		车辆状态:<input class="easyui-combobox" id="status" name="status" 
			           			data-options="
			                    valueField:'value',
			                    textField:'label',
			                    data: [{
									label: '全部',
									value: ''
								},{
									label:'启用',
									value:'1',
									selected : true
								},{
									label:'关闭',
									value:'0'
								}],
			                    panelHeight:'auto'"  style="width:150px;" > 
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		<r:FunctionRole functionRoleId="add_car_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_car_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
<!-- 		<r:FunctionRole functionRoleId="delete_car_msg"> -->
<!-- 			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" onclick=";">删除</a> -->
<!-- 		</r:FunctionRole> -->
		<r:FunctionRole functionRoleId="download_batch_car_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_medium_down" plain="true" id="btnDownload" onclick=";">车辆信息批量导入样表下载</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="batch_import_car_msg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_medium_up" plain="true" id="btnImport" onclick=";">批量导入车辆信息</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="audit_lpn_bind">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_edit" plain="true" id="btnBind" onclick=";">T-Box设备绑定</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="audit_lpn_bind">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" id="btnUnBind" onclick=";">T-Box设备解绑</a>
		</r:FunctionRole>

		<input type="hidden" class="easyui-textbox" id="q-shortcut" value="666">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="annualInspectionBtnQuery" onclick=";">车辆年检临期快捷查询</a>
		
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setStatus" onclick=";">设置车辆停运</a>
		<r:FunctionRole functionRoleId="export_car_mrg">
	         <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
	    </r:FunctionRole>
	    </form>
   </div>
   	<div id="addRemarkView" class="easyui-dialog" closed="true">
		<form id="addRemarkForm" method="post" style="margin-top: 20px;">
		   <span id="confirm" style="margin-bottom: 10px;margin-left: 77px"></span>
		   <input type="hidden" name="lpn" id="r-lpn">
		   <input type="hidden" name="status" id="r-status">
		   <br><br>
           <div class="fitem">
           	<label>备注原因:</label><input class="easyui-textbox" name="remark" id="r-remark" data-options="multiline:true" style="height:50px;" required="true" >
           </div>
		</form>
  	</div>
	<!-- add view -->
  <div id="addView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data" >
           <div class="fitem">
				<label>车牌号码:</label> <input  name="lpn"  class="easyui-textbox" style="width: 75%;" 	required="true"  missingMessage="请填写车牌号码"/>
		   </div>
		   <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="a-cityCode"  style="width: 25%;"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择所属城市">

<!-- 		   <div class="fitem"> -->
<!-- 				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="a-cityCode" -->
<!-- 					data-options=" url:'sys_cityCombobox', -->
<!-- 	                    method:'get', -->
<!-- 	                    valueField:'id', -->
<!-- 	                    textField:'text', -->
<!-- 	                    panelHeight:'auto', -->
<!-- 	                    onSelect:function(param){ -->
<!-- 	                       var _url = 'park_city_code?cityCode='+param.id; -->
<!-- 	                       $('#a-parkId').combobox('reload', _url); -->
<!-- 	                    }" -->
<!-- 					required="true"  missingMessage="请选择所属城市"> -->

<!-- 		   </div> -->
<!-- 		   <div class="fitem"> -->
<!-- 				<label>所属网点:</label> <input class="easyui-combobox" name='parkId' id="a-parkId" -->
<!-- 					data-options=" -->
<!-- 	                    valueField:'id', -->
<!-- 	                    textField:'name', -->
<!-- 	                    panelHeight:'auto'" -->
<!-- 					required="true"  missingMessage="请选择网点"> -->
<!-- 		   </div> -->
                <span style="margin-left: 15px;"></span>
				<label>车辆型号:</label> <input class="easyui-combobox" name="modelId" style="width: 25%;"
					data-options=" url:'carTypeCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择车辆型号">

		   </div>
		   <div class="fitem">
				<label>发动机号:</label> <input  name="engineno"  class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写发动机号"/>
		   </div>
		   <div class="fitem">
				<label>车架号:</label> <input  name="classno"  class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写车架号：17位字母和数字组合"/>
		   </div>
           <div class="fitem">
				<label>蓝牙编号:</label> <input  name="bluetoothNo" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <%--<div class="fitem">--%>
				<%--<label>电子车牌:</label> <input  name="eLpn"  class="easyui-textbox" style="width: 75%;" />--%>
		   <%--</div>--%>
		    <%--<div class="fitem">--%>
				<%--<label>UUID:</label> <input  name="iosUuid"  class="easyui-textbox"  style="width: 75%;"/>--%>
		   <%--</div>--%>
           <div class="fitem">
				<label>车载设备类型:</label>
			   <select name="tboxVersion" id="tboxVersionSelect">
				   <option value ="2" selected>二代</option>
				   <option value ="3">三代</option>
			   </select>
		   </div>
           <div class="fitem">
				<label>车身颜色:</label> <input class="easyui-combobox" name="color"  style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=CAR_COLOR',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   <span style="margin-left: 15px;"></span>
<!-- 				<label>座位数:</label> <input  name="seats"  class="easyui-textbox"  style="width: 25%;"/> -->
		   </div>
<!-- 		   <div class="fitem"> -->
<!-- 				<label>车辆最高速度:</label> <input  name="speed"  class="easyui-textbox"  style="width: 25%;"/> -->
<!-- 		   <span style="margin-left: 15px;"></span> -->
<!-- 				<label>续航里程:</label> <input  name="mileage"  class="easyui-textbox"  style="width: 25%;"/> -->
<!-- 		   </div> -->
		   <div class="fitem">
				<label>导航:</label> <input class="easyui-combobox" name="isNav" id="add-isNav" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   <span style="margin-left: 15px;"></span>
				<label>一键呼叫:</label> <input class="easyui-combobox" name="isCall" id="add-isCall" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   </div>
		   <div class="fitem">
				<label>行车记录:</label> <input class="easyui-combobox" name="isTripRecord" id="add-isTripRecord" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   <span style="margin-left: 15px;"></span>
				<label>车辆所属:</label> <input class="easyui-combobox" name="owner" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=CAR_OWNER',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   </div>
		   <div class="fitem">
		        <label>联系电话:</label> <input  name="phone"  class="easyui-textbox"  style="width: 25%;"/>
		        <span style="margin-left: 15px;"></span>
		        <label>车辆年检时间:</label> <input class="easyui-datebox" type="text" name="annualInspectionTime" id="annualInspectionTime" data-options="required:true,missingMessage:'车辆年检时间不能为空',editable:false" style="width:25%">
		   </div> 
		   
			<!-- <div style="margin-bottom:10px" id="xztp">
				<input  name="file" id="file" type="file" onchange="javascript:setImagePreview(this,localImag,adPhoto);"  data-options="prompt:'选择图片...',required:true"  style="width:100%;height:24px">
				<div id="localImag">
	              	<img style="margin-left: 0px;" width="200"  id="adPhoto" />
	            </div>
			</div> -->
			
			<!--  车辆行驶证反面、行驶证图片  -->		   
			<div style="margin-bottom:10px" id="insur_license_pic">
				<span>行驶证反面:</span>
				<input  name="insuranceFile" id="insuranceFile" type="file" onchange="javascript:setImagePreview(this,insuranceLocalImag,addInsuranceImg);"  data-options="prompt:'选择行驶证反面图片...',required:true"  style="width:100%;height:24px">
				<div id="insuranceLocalImag">
	              	<img style="margin-left: 0px;" width="200"  id="addInsuranceImg" />
	            </div>
				<span>行驶证正面:</span>
				<input  name="drivingLicenseFile" id="drivingLicenseFile" type="file" onchange="javascript:setImagePreview(this,drivingLicenseLocalImag,addDrivingLicenseImg);"  data-options="prompt:'选择行驶证正面图片...',required:true"  style="width:100%;height:24px">
				<div id="drivingLicenseLocalImag">
	              	<img style="margin-left: 0px;" width="200"  id="addDrivingLicenseImg" />
	           </div>
	          <div style="margin-bottom:10px" id="insur_license_pic">
	            	<span>交强险照片:</span>
					<input  name="insuranceStrongUriFile" id="insuranceStrong" type="file" onchange="javascript:setImagePreview(this,insuranceStrongLocalImag,addInsuranceStrongImg);"  data-options="prompt:'交强险图片...',required:true"  style="width:100%;height:24px">
					<div id="insuranceStrongLocalImag">
		              	<img style="margin-left: 0px;" width="200"  id="addInsuranceStrongImg" />
		            </div>
					<span>商业险照片:</span>
					<input  name="insuranceBusUriFile" id="insuranceBus" type="file" onchange="javascript:setImagePreview(this,insuranceBusImag,addinsuranceBusImg);"  data-options="prompt:'商业险图片...',required:true"  style="width:100%;height:24px">
					<div id="insuranceBusImag">
		              	<img style="margin-left: 0px;" width="200"  id="addinsuranceBusImg" />
		            </div>
	          </div>
		    </div>
       </form>
   </div>
   
  <!-- edit view -->
  <div id="editView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="id" id="e-id">
           <div class="fitem">
				<label>车牌号码:</label> <input  name="lpn"  class="easyui-textbox"  id='e-lpn' style="width: 75%;"	required="true"  missingMessage="请填写车牌号码" readonly="readonly"/>
		   </div>
		   <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="e-cityCode" style="width: 25%;"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择所属城市">

		   
<!-- 		   <div class="fitem"> -->
<!-- 				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="e-cityCode" -->
<!-- 					data-options=" url:'sys_cityCombobox', -->
<!-- 	                    method:'get', -->
<!-- 	                    valueField:'id', -->
<!-- 	                    textField:'text', -->
<!-- 	                    panelHeight:'auto', -->
<!-- 	                    onSelect:function(param){ -->
<!-- 	                       var _url = 'park_city_code?cityCode='+param.id; -->
<!-- 	                       $('#e-parkId').combobox('reload', _url); -->
<!-- 	                    }" -->
<!-- 					required="true"  missingMessage="请选择所属城市"> -->

<!-- 		   </div> -->
<!-- 		    <div class="fitem"> -->
<!-- 				<label>所属网点:</label> <input class="easyui-combobox" name='parkId' id="e-parkId" -->
<!-- 					data-options=" url:'park_all', -->
<!-- 	                    method:'get', -->
<!-- 	                    valueField:'id', -->
<!-- 	                    textField:'name', -->
<!-- 	                    panelHeight:'auto'" -->
<!-- 					required="true" id="e-parkId" missingMessage="请选择网点"> -->
<!-- 		   </div> -->
		    <span style="margin-left: 15px;"></span>
				<label>车辆型号:</label> <input class="easyui-combobox" name="modelId" id="e-modelId" style="width: 25%;"
					data-options=" url:'carTypeCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择车辆型号">

		   </div>
		    <div class="fitem">
				<label>发动机号:</label> <input  name="engineno" id="e-engineno"  class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写发动机号"/>
		   </div>
		   <div class="fitem">
				<label>车架号:</label> <input  name="classno" id="e-classno"   class="easyui-textbox" style="width: 75%;"	required="true"  missingMessage="请填写车架号"/>
		   </div>
           <div class="fitem">
				<label>蓝牙编号:</label> <input  name="bluetoothNo" id="e-bluetoothNo" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <%--<div class="fitem">--%>
				<%--<label>电子车牌:</label> <input  name="eLpn" id="e-eLpn" class="easyui-textbox"  style="width: 75%;"/>--%>
		   <%--</div>--%>
		    <%--<div class="fitem">--%>
				<%--<label>UUID:</label> <input  name="iosUuid" id="e-iosUuid"  class="easyui-textbox"  style="width: 75%;"/>--%>
		   <%--</div>--%>
		   <div class="fitem">
			   <label>tboxImei:</label>
			   <input  name="tboxImei" class="easyui-textbox" id="tboxImei" readonly style="width: 75%;"/>
		   </div>
		   <div class="fitem">
			   <label>车载设备类型:</label>
			   <select name="tboxVersion" id="tboxVersion">
				   <option value ="2">二代</option>
				   <option value ="3">三代</option>
			   </select>
		   </div>
           <div class="fitem">
				<label>车身颜色:</label> <input class="easyui-combobox" name="color"  id="e-color" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=CAR_COLOR',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		  <span style="margin-left: 15px;"></span>
<!-- 				<label>座位数:</label> <input  name="seats" id="e-seats"  class="easyui-textbox"  style="width: 25%;"/> -->
		   </div>
<!-- 		   <div class="fitem"> -->
<!-- 				<label>车辆最高速度:</label> <input  name="speed" id="e-speed"  class="easyui-textbox"  style="width: 25%;"/> -->
<!-- 		  <span style="margin-left: 15px;"></span> -->
<!-- 				<label>续航里程:</label> <input  name="mileage" id="e-mileage" class="easyui-textbox"  style="width: 25%;"/> -->
<!-- 		   </div> -->
		   <div class="fitem">
				<label>导航:</label> <input class="easyui-combobox" name="isNav"  id="e-isNav" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   <span style="margin-left: 15px;"></span>
				<label>一键呼叫:</label> <input class="easyui-combobox" name="isCall" id="e-isCall" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   </div>
		   <div class="fitem">
				<label>行车记录:</label> <input class="easyui-combobox" name="isTripRecord" id="e-isTripRecord" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   <span style="margin-left: 15px;"></span>
		        <label>车辆所属:</label> <input class="easyui-combobox" name="owner" id="e-owner" style="width: 25%;"
					data-options=" url:'sys_dic?dicCode=CAR_OWNER',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		   </div>
		   <div class="fitem">
				<label>联系电话:</label> <input  name="phone" id="e-phone"  class="easyui-textbox"  style="width: 25%;"/>
				<span style="margin-left: 15px;"></span>
		        <label>车辆年检时间:</label> <input class="easyui-datebox" type="text" name="annualInspectionTime" id="e_annualInspectionTime" style="width:25%">
		   </div>
		   <!--  车辆行驶证反面、行驶证图片  -->		   
			<div style="margin-bottom:10px" id="insur_license_pic">
				<span>行驶证反面:</span>
				<input  name="insuranceFile" id="insuranceFile" type="file" onchange="javascript:setImagePreview(this,insuranceLocalImag,addInsurancePreview);"  data-options="prompt:'选择行驶证反面图片...',required:true"  style="width:100%;height:24px">
				<div id="insuranceLocalImag">
	              	<img style="margin-left: 0px;" width="200"  id="addInsurancePreview" />
	            </div>
				<span>行驶证正面:</span>
				<input  name="drivingLicenseFile" id="drivingLicenseFile" type="file" onchange="javascript:setImagePreview(this,drivingLicenseLocalImag,addDrivingLicensePreview);"  data-options="prompt:'选择行驶证正面图片...',required:true"  style="width:100%;height:24px">
				<div id="drivingLicenseLocalImag">
	              	<img style="margin-left: 0px;" width="200"  id="addDrivingLicensePreview" />
	            </div>
		    </div>
		    <div>
		    	<span>交强险照片:</span>
				<input  name="insuranceStrongUriFile" id="insuranceStrong" type="file" onchange="javascript:setImagePreview(this,insuranceStrongLocalImag,addInsuranceStrongPreview);"  data-options="prompt:'交强险图片...',required:true"  style="width:100%;height:24px">
				<div id="insuranceStrongLocalImag">
	              	<img style="margin-left: 0px;" width="200"  id="addInsuranceStrongPreview" />
	            </div>
				<span>商业险照片:</span>
				<input  name="insuranceBusUriFile" id="insuranceBus" type="file" onchange="javascript:setImagePreview(this,insuranceBusImag,addinsuranceBusPreview);"  data-options="prompt:'商业险图片...',required:true"  style="width:100%;height:24px">
				<div id="insuranceBusImag">
	              	<img style="margin-left: 0px;" width="200"  id="addinsuranceBusPreview" />
	            </div>
		    </div>
		   <!-- <div style="margin-bottom:10px" id="xztp">
                <span>车辆图片:</span>
				<input  name="file" id="file" type="file" onchange="javascript:setImagePreview(this,localImag,adPhotoPreview);"  data-options="prompt:'选择图片...',required:true"  style="width:75%;height:24px">
				<div id="localImag" align="center">
	              	<img style="margin-left: 0px;" width="100" height="130" id="adPhotoPreview" />
	            </div>
	            <span>车辆小图片:</span>
				<div id="localSmallImag" align="center">
	              	<img style="margin-left: 0px;" width="100" height="130" id="addSmallPhotoPreview" />
	            </div>
			</div> -->
       </form>
   </div>
    
     
      <div id="uploadFileView" class="easyui-dialog" closed="true" style="padding:10px 20px">
          <form  method="post" name="uploadFileForm" id="uploadFileForm" enctype="multipart/form-data">
             <div class="fitem">
              <label>选择文件:</label><input type="file" name="uploadFile" id="uploadFile" required="true"  missingMessage="请选择文件">
             </div>
          </form>
       </div>


 <!-- imeiView -->
 <div id="imeiView" class="easyui-dialog" closed="true"
	  style="padding:10px 20px">
	 <form name="imeiViewForm" id="imeiViewForm" method="post" >
		 <div class="fitem">
			 <label>车牌号码:</label>
			 <input id="lpn"  name="lpn" style="width: 25%;" readonly/>
			 <label>tboxImei:</label>
			 <%--<input list="imeiSelect" name="imei" placeholder="选择imei..." />--%>
			 <%--<datalist id="imeiSelect" >--%>
			 <%--</datalist>--%>
			 <input id="imeiSelect" class="easyui-combobox" name="imei"
					data-options="valueField:'imei',textField:'imei',method:'get',url:'getNotBindImeis'">
			 <a id="imeiReload" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'"></a>
		 </div>
	 </form>
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
		
		//行驶证反面和行驶证
		function displayInsurance(id){
			document.getElementById("insurance"+id).style.display="block"; 
		}
		function displayDrivLic(id){
			document.getElementById("license"+id).style.display="block"; 
		}
		function hideInsurance(id){
			document.getElementById("insurance"+id).style.display="none"; 
		}
		function hideDrivLic(id){
			document.getElementById("license"+id).style.display="none"; 
		}
		
		// 强险隐藏放大
		function displayForcedInsurancePic(id){
			document.getElementById("forcedInsurance"+id).style.display="block"; 
		}
		
		function hideForcedInsurancePic(id){
			document.getElementById("forcedInsurance"+id).style.display="none"; 
		}
		
		// 商险隐藏放大 Commercial Insurance
		function displayCommercialInsurancePic(id){
			document.getElementById("CommercialInsurance"+id).style.display="block"; 
		}
		
		function hideCommercialInsurancePic(id){
			document.getElementById("CommercialInsurance"+id).style.display="none"; 
		}
</script>
</html>