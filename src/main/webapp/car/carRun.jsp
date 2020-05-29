<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆运营管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="car/carRun.js"></script>
<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
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
	width:80px;
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
	<!-- 页面主体，通过datagrid生成的数据表格展现出来 -->
   <table id="grid" toolbar="#toolbar"></table>
   
     <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
   <form action="" id="carRunForm">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"  style="width: 100px;"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn" style="width: 100px;">
	    品牌:<input type="text" name="brandName" class="easyui-textbox" id="s-brandName" style="width: 100px;"	>
		网点:<input type="text" name="parkName" class="easyui-textbox" id="s-parkName" style="width: 100px;"	>
	    城镇/区:<input class="easyui-combobox" name="areaName" id = "areaName" style="width:150px;" data-options="
				url: 'get_area_group_by_city',
				method: 'get',
				valueField:'value',
				textField:'text',
				groupField:'group'
			">
		车辆运营状态：<input class="easyui-combobox" name="status" id="e-status" style="width: 80px;"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
	                    	label: '全部',
							value: ''
	                    },{
							label: '闲置中',
							value: 'empty'
						},{
							label: '维修中',
							value: 'repair'
						},{
							label:'维护中',
							value:'maintain'
						},{
							label:'运营中',
							value:'run'
						},{
							label:'补电中',
							value:'charging'
						}],
	                    panelHeight:'auto'" >
	    会员是否可见：<input class="easyui-combobox" name="isLook" id="e-isLook" style="width: 80px;"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
	                    	label: '全部',
							value: ''
	                    },{
							label: '是',
							value: '1'
						},{
							label: '否',
							value: '0'
						}],
	                    panelHeight:'auto'" >
	         预下线状态：<input class="easyui-combobox" name="preOfflineStatus" id="e-repStatus" style="width: 100px;"
					data-options="
	                    valueField:'value1',
	                    textField:'label1',
	                    data: [{
							label1: '全部',
							value1: ''
						},{
							label1: '预下线',
							value1: '1'
						},{
							label1: '非预下线',
							value1: '0'
						}],
	                    panelHeight:'auto'" >
	  tf状态：<input class="easyui-combobox" name="tfcard" id="e-tfcardStatus" style="width: 100px;"
					data-options="
	                    valueField:'value2',
	                    textField:'label2',
	                    data: [{
							label2: '全部',
							value2: ''
						},{
							label2: '正常',
							value2: '1'
						},{
							label2: '已拔出',
							value2: '0'
						}],
	                    panelHeight:'auto'" >
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton"  plain="true" id="btnUpgradet" onclick=";">设备升级</a> -->

		<r:FunctionRole functionRoleId="car_preserve">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnRepair" onclick=";">车辆下线</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="car_prepare_to_offline">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="prepareToOffline" onclick=";">车辆预下线</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="car_return_to_online">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="returnToOnline" onclick=";">车辆取消预下线状态</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="carparking">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-park"  plain="true" id="btnPark" onclick=";">所在网点</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="install_group">
			<a href="javascript:void(0);" class="easyui-linkbutton"  plain="true" iconCls="icon-large-clipart" id="btnGroup" onclick=";">设置组</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="car_accident">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnAccident" onclick=";">车辆事故</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="car_assist">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnRescue" onclick=";">车辆救援</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="server_push_reboot_box">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="box" onclick=";">服务器推送重启盒子</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="server_push_reboot_system">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="system" onclick=";">推送重启后视镜</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="server_push_set_box_type">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="boxType" onclick=";">服务器推送设置盒子类型</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="server_push_start_car">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="restartCar" onclick=";">车辆一键启动</a>
		</r:FunctionRole>
<!-- 		<a href="javascript:void(0);" class="easyui-linkbutton"  plain="true" id="btnHeartbeat" onclick=";">设备心跳时间</a> -->
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_large_down" plain="true" id="upToDownQuery" onclick=";">剩余电量降序查询</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_large_up" plain="true" id="downToUpQuery" onclick=";">剩余电量升序查询</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_large_up" plain="true" id="deviceUploadToUpQuery" onclick=";">设备上报时间升序查询</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_large_up" plain="true" id="batteryVToUpQuery" onclick=";">小电瓶电压升序查询</a>
	    <r:FunctionRole functionRoleId="export_car_Run_mrg">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
	</div>
   	<!-- 车辆维修弹框 -->
     <div id="carRepairView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carRepairViewForm" id="carRepairViewForm" method="post" >
          <input type="hidden" name="carId" id="r-id">
          <input type="hidden" name="cityCode" id="r-cityCode">
          <input type="hidden" id="r_parkId" name="parkId"> 	
          <input type="hidden" name="lpn" id="r-lpn-1">
          <input type="hidden" name="employeePhone" id="e-employeePhone">
          <div class="fitem">
				<label>车牌号码:</label><span id="r-lpn-2"></span>
		   </div>
          <div class="fitem">
				<label>负责人手机号:</label><input  name="responsiblePersonPhone" id="e-seats"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>状态:</label><input class="easyui-combobox" name="status" id="e-status" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=REPAIR_STATUS',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    editable:false,
	                    panelHeight:'auto'" required="true"  missingMessage="请选择状态">
		   </div>
		   <div class="fitem">
		   		<label>预计上线时间:</label><input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox" style="width: 75%;" />
		   </div>
		   <div class="fitem">
				<label>下线原因:</label><input  name="reason" id="e-seats"  class="easyui-textbox"   data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
     </div>
     <%--  车辆预下线弹框    --%>
     <div id="carPreOfflineView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carPreOfflineViewForm" id="carPreOfflineViewForm" method="post" >
          <input type="hidden" name="carId" id="p-id">
          <input type="hidden" name="opreationType" id="p-opreationType">
          <input type="hidden" name="cityCode" id="p-cityCode">
          <input type="hidden" name="lpn" id="p-lpn">
          <div class="fitem">
				<label>车牌号码:</label><span id="p-lpn-1"></span>
		   </div>
		   <div class="fitem">
				<label>状态:</label><input class="easyui-combobox" name="status" id="p-status" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=REPAIR_STATUS',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    editable:false,
	                    panelHeight:'auto'" required="true"  missingMessage="请选择状态">
		   </div>
		   <div class="fitem">
		   		<label>预计上线时间:</label><input id="predictTime"  name="predictTime" class="easyui-datetimebox" style="width: 75%;" />
		   </div>
		   <div class="fitem">
				<label>预下线原因:</label><input  name="reason" id="p-reason"  class="easyui-textbox"   data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
     </div>
     
     </div>
        <div id="getEmployeeInfoView" class="easyui-dialog" closed="true" style="padding:2px 20px 30px 20px;">
                    员工姓名:<input type="text" name="employeeName" class="easyui-textbox" id="e-employeeName">
          <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="queryEmployeeInfo" onclick=";">查询</a>
		  <table id="getEmployeeInfoGrid" toolbar="#toolbars"></table>
	 </div>
	 
	 
     <!--  car Accident view -->
     <div id="carAccidentView" class="easyui-dialog" closed="true" style="padding:10px 20px">
       <form name="carAccidentViewForm" id="carAccidentViewForm" method="post" >
          <input type="hidden" name="accident-id" id="accident-id">
          <input type="hidden" name="accident-lpn-1" id="accident-lpn-1">
          <div class="fitem">
				<label>车牌号码:</label><span id="accident-lpn-2"></span>
		   </div>
          <div class="fitem">
				<label>负责人:</label><input  name="accident-responsiblePerson" id="accident-responsiblePerson"  class="easyui-textbox"  required="true"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>负责人手机号码:</label><input  name="accident-responsiblePersonPhone" id="accident-responsiblePersonPhone"  required="true" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
		   		<label>会员手机号码:</label><input  name="accident-memberPhone" id="accident-memberPhone"  required="true" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
		   		<tr>
		   			<td>
						<span style="cursor: pointer;color: #2779AA;" class="getOrderInfo">关联订单</span>
					</td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<td>
						<input  name="relativeOrderId" id="accident-relativeOrderId" required="true" class="easyui-textbox"  style="width: 75%;"/>
					</td>
				</tr>
		   </div>
		   <div>
		   		<label>会员赔偿:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="memberCompensate"  value="1"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="memberCompensate" id="memberCompensate"  value="0"/>否</label>
		   </div>
		   <div class="fitem">
		   		<label>赔偿金额:</label><input  name="accident-money" id="accident-money"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>事故原因:</label><input  name="accident-reason" id="accident-reason"  class="easyui-textbox"  required="true" data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
     </div>
     <div id="getOrderInfoView" class="easyui-dialog" closed="true" style="padding:1px 20px 28px 20px">
		 <form id="getOrderDetail">
			  订单开始时间:
		   <input id="s-bt" name="bt" class="easyui-datetimebox" size="18" />
			  订单结束时间:
		   <input id="s-et" name="et" class="easyui-datetimebox" size="18"/>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="queryOrderByPhone" onclick=";">查询</a>
		 </form>

		   <br/>
		   <table id="getOrderInfoGrid" toolbar="#toolbars"></table>
	 </div>
     <!--  car Rescue view -->
     <div id="carRescueView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carRescueViewForm" id="carRescueViewForm" method="post" >
          <input type="hidden" name="rescue-id" id="rescue-id">
          <input type="hidden" name="rescue-lpn-1" id="rescue-lpn-1">
		  <input type="hidden" name="employeeId" id="employeeId">
          <div class="fitem">
				<label>车牌号码:</label><span id="rescue-lpn-2"></span>
		   </div>
		   <div class="fitem">
			   <label>车型:</label><span id="brand-name"></span>
		   </div>
		   <div class="fitem">
			   <label>会员姓名:</label><input  name="member-name" id="member-name"  class="easyui-textbox" data-options="readonly:true"/>
		   </div>
		   <div class="fitem">
			   <label>会员联系方式:</label><input  name="member-phone" id="member-phone"   class="easyui-textbox" data-options="readonly:true"/>
		   </div>
		   <div class="fitem">
			   <label>会员等级:</label><input  name="level-code" id="level-code"  class="easyui-textbox" data-options="readonly:true"/>
		   </div>
		   <div class="fitem">
			   <label>订单号:</label><input  name="order-id" id="order-id"  class="easyui-textbox" data-options="readonly:true" style="width: 45%;"/>
		   </div>
		   <div class="fitem">
			   <label>订单状态:</label><input  name="order-status" id="order-status"  class="easyui-textbox" data-options="readonly:true"/>
		   </div>
		   <div class="fitem">
			   <label>救援地址:</label><input  name="rescue-address" id="rescue-address"  class="easyui-textbox" style="width: 45%;"/>
		   </div>
		   <div class="fitem">
		   		<label>任务限期:</label><input id="deadline" required="true" name="deadline" class="easyui-datetimebox" style="width: 45%;" data-options="editable:false"/>
		   </div>
		   <div class="fitem">
			   <tr>
				   <td>
					   <span style="cursor: pointer;color: #2779AA;" class="getRescueEmployeeInfo">选择救援人员:</span>
				   </td>
				   <td>
					   <input  name="relativeEmployeeName" id="relativeEmployeeName" required="true" data-options="editable:false" class="easyui-textbox" missingMessage="请点击左方选择救援人员蓝色文字进行选择" style="width: 25%;"/>
				   </td>
			   </tr>
		   </div>
           <div class="fitem">
				<label>任务等级:</label><input class="easyui-combobox" name="taskLevel" id="taskLevel" required="true" data-options=" url:'get_task_grade_list',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
		   </div>
		   <div class="fitem">
			   <label>事件经过:</label><input  name="eventCource" id="eventCource"  class="easyui-textbox" required="true" data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
		   <div class="fitem">
			   <label>责任判定建议:</label><input  name="responsibilityJudgeAdvice" id="responsibilityJudgeAdvice"  class="easyui-textbox"   data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
		   <div id="pMap" class="easyui-panel" title="车辆位置展示"
				style="width:100%;height:70%;padding:10px;background:#fafafa;"
				data-options="collapsible:true,maximizable:true">
			   <div id="dituContent"  style="height: 100%; width:100%;"></div>
		   </div>
       </form>
     </div>
	<div id="getRescueEmployeeInfoView" class="easyui-dialog" closed="true" style="padding:1px 20px 28px 20px">
		姓名:
		<input id="rescue-name" name="rescue-name" class="easyui-textbox" size="18" />
		手机号码:
		<input id="rescue-phone" name="rescue-phone" class="easyui-textbox" size="18"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="queryRescueEmployeeInfo" onclick=";">查询</a>
		<br/>
		<table id="getRescueEmployeeInfoGrid" toolbar="#toolbars"></table>
	</div>
	<div id="getProblemAndRescueTypeInfoView" class="easyui-dialog" closed="true" style="padding:1px 20px 28px 20px">
		<br/>
		<table id="getProblemAndRescueTypeInfoGrid" toolbar="#toolbars"></table>
	</div>
	
       <!--  car park view --><!-- 所在网点所 -->
     <div id="carParkView" class="easyui-dialog" closed="true"
        style="padding:10px 20px">
       <form name="carParkViewForm" id="carParkViewForm" method="post" >
          <input type="hidden" name="carRunId" id="p-carid">
          	<div class="fitem">
				<label>车牌号:</label>
				 <input class="easyui-textbox" name='lpn' id="p-park-lpn" style="width:75%;">
		   </div>
           <div class="fitem">
				<label>所属网点:</label>
				 <input class="easyui-combogrid" name='parkId' id="p-parkId" style="width:75%;">
		   </div>
       </form>
     </div>
   
     <!--  car group view -->
     <div id="carGroupView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carGroupViewForm" id="carGroupViewForm" method="post" >
          <input type="hidden" name="lpns" id="g-lpns">
          <div cellpadding="5" style="font-size: 12px;margin:0 auto;margin-top: 50px;">
       				<label>请选择车辆组:</label>
		            <!-- 换为用combogrid -->
	    				<input class="easyui-combogrid" name="groupId" id="g-groupId" style="width:40%;height:24px" missingMessage="请选择车辆组">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:10%;" title="清空"></a>
		   </div>
       </form>
     </div>
     
      <!--  car park view --><!-- 所在网点所 -->
     <div id="carTypeView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carTypeViewForm" id="carTypeViewForm" method="post" >
       		<input type="hidden" name="lpn" id="t-lpn">
          	<div class="fitem">
				<label>车型:</label>
				 <input class="easyui-combobox" name='carType' id="p-carType" style="width:75%;" data-options="
				 			valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto',
		                    data:[{   
    							'id':0,   
   							    'text':'北汽'  
							},{   
							    'id':1,   
							    'text':'奇瑞'  
							},{   
							    'id':2,   
							    'text':'众泰',   
							},{
							    'id':3,
							    'text':'东风',
							}] ">
		   </div>
       </form>
     </div>
</body>
 </html>   