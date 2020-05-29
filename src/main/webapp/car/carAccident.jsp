<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆事故管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="car/carAccident.js"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
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
   <table id="carAccidentGrid" toolbar="#toolbar"></table>
     
      <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
   <form action="" id="carAccidentForm">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
		事故处理状态：<input class="easyui-combobox" name="accidentStatus" id="accidentStatus"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '已结束',
							value: '0'
						},{
							label: '处理中',
							value: '1'
						}],
	                    panelHeight:'auto'" >
	     会员姓名:<input type="text" name="memberName" class="easyui-textbox" id="s-memberName">
	    会员手机号:<input type="text" name="memberPhome" class="easyui-textbox" id="s-memberPhome">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a>
	    
	    <r:FunctionRole functionRoleId="update_car_accident">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
	    </r:FunctionRole>
	    <r:FunctionRole functionRoleId="delete_car_accident">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" >删除</a>
	    </r:FunctionRole>
	    <r:FunctionRole functionRoleId="finish_accident_handler">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnResume" onclick=";">结束事故处理</a>
	    </r:FunctionRole>
	 <!--    <r:FunctionRole functionRoleId="turn_preserve"> -->
	 <!--    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnToRepair" onclick=";">转为维修</a>-->
	 <!--    </r:FunctionRole> -->
	 	<r:FunctionRole functionRoleId="export_car_accident">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
	</div>
	<!--  carAccident view -->
     <div id="carAccidentView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carAccidentViewForm" id="carAccidentViewForm" method="post" >
          <input type="hidden" name="id" id="e-id">
          <input type="hidden" name="lpn" id="e-lpn-1"> 	
          <div class="fitem">
				<label>车牌号码:</label><span id="e-lpn-2"></span>
		   </div>
          <div class="fitem">
				<label>负责人:</label><input  name="responsiblePerson" required="true" id="e-responsiblePerson"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>手机号码:</label><input  name="responsiblePersonPhone" required="true" id="e-responsiblePersonPhone"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>会员手机号码:</label><input  name="memberPhone" id="e-memberPhone" required="true" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
		   		<tr>
		   			<td>
						<span style="cursor: pointer;color: #2779AA;" class="getOrderInfo">关联订单</span>
					</td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<td>
						<input  name="relativeOrderId" id="accident-relativeOrderId"  class="easyui-textbox" required="true"  style="width: 75%;"/>
					</td>
				</tr>
		   </div>
		   <div>
		   		<label>会员赔偿:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="memberCompensate" id="yes" value="1"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="memberCompensate" id="no" value="0"/>否</label>
		   </div>
		   <div class="fitem">
		   		<label>赔偿金额:</label><input  name="accident-money" id="accident-money" required="true" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <%--<div class="fitem">
			   <label>定损时间:</label><input  name="assessmentTime" id="assessment_time" required="true" class="easyui-datetimebox" style="width: 75%;" />
		   </div>
		   <div class="fitem">
			   <label>预计上线时间:</label><input  name="predictTime" id="predictTime" required="true" class="easyui-datetimebox" style="width: 75%;" />
		   </div>
		   <div class="fitem">
			   <label>责任判定:</label><input  name="responsibility"  id="responsibility" required="true" class="easyui-textbox"  style="width: 75%;"/>
		   </div>--%>
		   <div class="fitem">
				<label>事故原因:</label><input  name="reason" id="e-reason"  class="easyui-textbox"required="true"  data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
     </div>
     <div id="getOrderInfoView" class="easyui-dialog" closed="true" style="padding:2px 20px 31px 28px;width: 700px;height: 600px;">
     	 <form style="height: 0px;" id="orderForm">
     	     订单开始时间:
	       <input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" />
		      订单结束时间:
		   <input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>
		   <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="queryOrderByPhone" onclick=";">查询</a>
		   </form><br/>
		   <table id="getOrderInfoGrid" toolbar="#toolbars"></table>	
	 </div>
    <!-- end Rescue -->
   	<div id="endAccidentView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="endAccidentViewForm" id="endAccidentViewForm" method="post" >
          <input type="hidden" name="m_id" id="end-id">
		   <div class="fitem">
				是否要报保险:&nbsp;&nbsp;<input  name="is_insurance" id="is_insurance" required="true" class="easyui-combobox"  style="width: 75%;" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '不需要',
							value: '0'
						},{
							label: '需要',
							value: '1',
							selected:true
						}],
	                    panelHeight:'auto'" />
		   </div>
		   <div class="fitem">
			   <label>保险单号:</label><input  name="insurance_code" id="insurance_code"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
			   <label>定损金额:</label><input  name="assessment_money" id="assessment_money" required="true" class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
			   <label>定损时间:</label><input  name="assessment_time" class="easyui-datetimebox" required="true" style="width: 75%;" />
		   </div>
		   <div class="fitem">
			   <label>预计上线时间:</label><input  name="predictTime" class="easyui-datetimebox" required="true" style="width: 75%;" />
		   </div>
		   <div class="fitem">
			   <label>责任判定:</label><input  name="responsibility"  class="easyui-textbox" required="true"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>事故结果:</label><input class="easyui-textbox" name='result' id="result" style="width:75%;height:60px" required="true" data-options="multiline:true"/>
		   </div>
       </form>
     </div>
     
      <!--  car repair view -->
     <div id="carRepairView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carRepairViewForm" id="carRepairViewForm" method="post" >
          <input type="hidden" name="r-carId" id="r-carId">
          <input type="hidden" name="r-lpn-1" id="r-lpn-1">
          <div class="fitem">
				<label>车牌号码:</label><span id="r-lpn-2"></span>
		   </div>
          <div class="fitem">
				<label>负责人:</label><input  name="r-responsiblePerson" id="r-responsiblePerson"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
				<label>手机号码:</label><input  name="r-responsiblePersonPhone" id="r-responsiblePersonPhone"  class="easyui-textbox"  style="width: 75%;"/>
		   </div>
		   <div class="fitem">
		   		<label>预计上线时间:</label><input id="r-predictTime"  name="r-predictTime" class="easyui-datetimebox" style="width: 75%;" required="true"  missingMessage="请选择预计上线时间" />
		   </div>
		   <div class="fitem">
				<label>维修原因:</label><input  name="r-reason" id="r-reason"  class="easyui-textbox"   data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
     </div>

   	
 </body>
 </html>