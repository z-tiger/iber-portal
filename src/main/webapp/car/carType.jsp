<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆类型</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="car/carType.js"></script>
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

 <table id="carTypeGrid" toolbar="#toolbar"></table>
 
 
    <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
      	所属城市:
		<input class="easyui-combobox" name="cityCode" id="s-city_code"
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		车辆类型:
		<input class="easyui-combobox" name="typeName" id="s-type_name"
					data-options=" url:'sys_dic?dicCode=CAR_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
		车辆型号:<input type="text" name="type" class="easyui-textbox" id="s-type">
		车辆品牌:<input type="text" name="brance" class="easyui-textbox" id="s-brance">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a> -->
		
		<r:FunctionRole functionRoleId="add_car_type">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_car_type">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_car_type">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" onclick=";">删除</a>
		</r:FunctionRole>
		
	</div>

 <!-- add view -->
  <div id="addView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" >
       	   <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" 
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true" missingMessage="请选择所属城市">
		   </div>
           <div class="fitem">
				<label>车辆型号:</label> <input  name="type"  class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写车辆型号"/>
		   </div>
           <div class="fitem">
				<label>车辆类型:</label> <input class="easyui-combobox" name="typeName" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=CAR_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
	                    required="true"  missingMessage="请选择车辆类型">
		   </div>
           <div class="fitem">
				<label>车辆品牌:</label> <input  name="brance" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <div class="fitem">
				<label>车厢数量:</label> <input  name="carriage"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		    <div class="fitem">
				<label>座位数量:</label> <input  name="seatNumber"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <div class="fitem">
				<label>续航里程:</label> <input  name="endurance"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>最高速度:</label> <input  name="maxSpeed"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
       </form>
   </div>

<!-- edit view -->
  <div id="editView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" >
          <input type="hidden" name='id' id="e-id">
           <div class="fitem">
				<label>所属城市:</label> <input class="easyui-combobox" name="cityCode" 
					data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true" id="e-cityCode" missingMessage="请选择所属城市">
		   </div>
           <div class="fitem">
				<label>车辆型号:</label> <input  name="type" id="e-type" class="easyui-textbox"  style="width: 75%;"	required="true"  missingMessage="请填写车辆型号"/>
		   </div>
           <div class="fitem">
				<label>车辆类型:</label> <input class="easyui-combobox" name="typeName"  id="e-typeName" style="width: 75%;"
					data-options=" url:'sys_dic?dicCode=CAR_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'"
	                    required="true"  missingMessage="请选择车辆类型">
		   </div>
           <div class="fitem">
				<label>车辆品牌:</label> <input  name="brance" id="e-brance" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <div class="fitem">
				<label>车厢数量:</label> <input  name="carriage" id="e-carriage"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		    <div class="fitem">
				<label>座位数量:</label> <input  name="seatNumber" id="e-seatNumber"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
           <div class="fitem">
				<label>续航里程:</label> <input  name="endurance" id="e-endurance" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>最高速度:</label> <input  name="maxSpeed" id="e-maxSpeed" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
       </form>
   </div>

</body>
</html>