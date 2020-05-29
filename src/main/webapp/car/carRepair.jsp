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
<script type="text/javascript" src="car/carRepair.js"></script>
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
   <table id="carRepairGrid" toolbar="#toolbar"></table>
     
      <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
   <form action="" id="carRepairForm">
                   所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn"  style="width: 100px">
		维修状态：<input class="easyui-combobox" name="repairStatus" id="repairStatus"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [
	                    {
							label: '维修中',
							value: '1'
						},{
							label: '维护中',
							value: '2'
						},{
							label: '补电中',
							value: '3'
						},{
							label: '已维修',
							value: '0,1'
						},{
							label: '已维护',
							value: '0,2'
						},{
							label: '已补电',
							value: '0,3'
						}],
	                    panelHeight:'auto'" >
	              网点类型:</label> <input class="easyui-combobox" name="category"
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
	            下线原因:<input type="text" name="reason" class="easyui-textbox" id="r-reason">         
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		<r:FunctionRole functionRoleId="update_car_preserve">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_car_preserve">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" >删除</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="car_restore_run">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnResume" onclick=";">恢复运营</a>
		</r:FunctionRole>
		
		<input type="hidden" name="timeOut" id="timeOut"  class="easyui-textbox" value="123">  
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="outTimeQuery" onclick=";">超时未上线查询</a>
		<r:FunctionRole functionRoleId="export_carRepair">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
   </div>
   
   
     <!--  car repair view -->
     <div id="carRepairView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carRepairViewForm" id="carRepairViewForm" method="post" >
          <input type="hidden" name="id" id="e-id">
          <input type="hidden" name="lpn" id="e-lpn-1">
          <input type="hidden" id="r_parkId" name="parkId"> 	
          <div class="fitem">
				<label>车牌号码:</label><span id="e-lpn-2"></span>
		   </div>
          <div class="fitem">
				<label>负责人:</label><input  name="responsiblePerson"  id="e-responsiblePerson"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>手机号码:</label><input  name="responsiblePersonPhone" id="e-responsiblePersonPhone"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
<%--		   <div class="fitem">--%>
<%--				<label>维修网点:</label><input  name="parkName" id="r-park"  class="easyui-textbox"  style="width: 60%;"/>--%>
<%--				<span style="cursor: pointer;color: #2779AA;" class="getTotalParks">请选择网点</span>--%>
<%--		   </div>--%>
		   <div class="fitem">
				<label>维修原因:</label><input  name="reason" id="e-reason"  class="easyui-textbox"  data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
     </div>
        <div id="getTotalParkView" class="easyui-dialog" closed="true" style="padding:2px 20px 30px 20px;">
                    网点:<input type="text" name="repairCarSpot" class="easyui-textbox" id="s-repairCarSpot">
          <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="queryCarSpot" onclick=";">查询</a>
		  <table id="getTotalParkGrid" toolbar="#toolbars"></table>
	 </div>
 </body>
 </html>