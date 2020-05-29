<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆救援管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="car/carRescue.js"></script>
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
   <table id="carRescueGrid" toolbar="#toolbar"></table>
     
      <!-- 工具栏 -->
   <div id="toolbar" style="height:auto">
   <form action="" id="carRescueForm">
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
		会员姓名:<input type="text" name="memberName" class="easyui-textbox" id="memberName">
		会员联系方式:<input type="text" name="memberPhone" class="easyui-textbox" id="memberPhone">
		救援状态：<input class="easyui-combobox" name="rescueStatus" id="rescueStatus"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '已结束',
							value: '0'
						},{
							label: '救援中',
							value: '1'
						},{
							label: '已取消',
							value: '2'
						}],
	                    panelHeight:'auto'" >           
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清空</a>
	    <r:FunctionRole functionRoleId="update_car_assist">
	        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnDetail" onclick=";">查看</a>
	    </r:FunctionRole>
	    <r:FunctionRole functionRoleId="update_car_assist">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a> 
	    </r:FunctionRole>
	    <r:FunctionRole functionRoleId="finish_assist">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnResume" onclick=";">结束救援</a>
	    </r:FunctionRole>
	    <r:FunctionRole functionRoleId="export_car_rescue">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
   </div>
  
   <div id="carRescueView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="carRescueViewForm" id="carRescueViewForm" method="post" >
          <input type="hidden" name="id" id="e-id">
          <input type="hidden" name="lpn" id="e-lpn-1">
          <div class="fitem">
				<label>车牌号码:</label><span id="e-lpn-2"></span>
		   </div>
          <div class="fitem">
				<label>任务执行人:</label><input name="responsiblePerson" id="e-responsiblePerson" class="easyui-textbox" style="width: 75%;" disabled="disabled" />
		   </div>
		   <div class="fitem">
				<label>执行人号码:</label><input name="responsiblePersonPhone" id="e-responsiblePersonPhone" class="easyui-textbox" style="width: 75%;" disabled="disabled" />
		   </div>
		   <div class="fitem">
				<label>事故经过:</label><input name="reason" id="e-reason" class="easyui-textbox" data-options="multiline:true" style="width: 75%;height:100px"/>
		   </div>
       </form>
   </div>
     
    <!-- end Rescue -->
   	<div id="endRescueView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="endRescueViewForm" id="endRescueViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="m_id" id="end-id">
           <input type="hidden" name="s-lpn-1" id="s-lpn-1">
           <input type="hidden" name="r-lpn-1" id="e-reason-1">
           <input type="hidden" name="carProRemark" id="carProRemark">
		   <input type="hidden" name="tecProRemark" id="tecProRemark">
           <input type="hidden" name="sysDicIds" id="sysDicIds">
           <div class="fitem">
				<label style="width:85px;">救援车牌:</label><span id="s-lpn-2"/>
          </div>
		   <div class="fitem">
				<label style="width:85px;">事故经过:</label>
				<input class="easyui-textbox" name="e-reason-2" id="e-reason-2" style="width:300px;height:80px"  data-options="multiline:true"/>
		   </div>
           <div class="fitem">
				<label style="width:85px;">责任类型:</label><input class="easyui-combobox" name='responsibleType' id="responsibleType" required="required" style="width:300px;height:22px" 
				data-options="  url:'rescueBehaviorType?behaviorId=24',
				        editable:false,
	                    method:'get',
	                    valueField:'id',
	                    textField:'name',
						panelHeight:'auto'"/>
		   </div>
		   <div id="responsibleDescriptionId" class="fitem" style="display:none">
				<label style="width:85px;">责任类型说明:</label>
				<input class="easyui-textbox" name="responsibleDescription" id="responsibleDescription" style="width:300px;height:80px"  data-options="multiline:true" required="required"/>
		   </div>
           <div class="fitem">
                <div>
				  <label style="width:85px;">上传照片凭证:</label>
				  <input name="picture" type="file" id="picture" onchange="javascript:setImagePreview(this,pictureFileImg,pictureFilePhoto);"/>
		        </div>
		        <div>
				  <label style="width:85px;">&nbsp;</label>
				  <input name="picture1" type="file" id="picture1" onchange="javascript:setImagePreview(this,pictureFileImg,pictureFilePhoto1);"/>
		        </div>
		        <div>
				  <label style="width:85px;">&nbsp;</label>
				  <input name="picture2" type="file" id="picture2" onchange="javascript:setImagePreview(this,pictureFileImg,pictureFilePhoto2);"/>
		        </div>
		        <div>
				  <label style="width:85px;">&nbsp;</label>
				  <input name="picture3" type="file" id="picture4" onchange="javascript:setImagePreview(this,pictureFileImg,pictureFilePhoto3);"/>
		        </div>
		   </div>
		   <div class="fitem">
				<div id="pictureFileImg">
				   <table>
				     <tr>
				        <td><img  width="25%" height="120" id="pictureFilePhoto" style="display: none;"/></td>
				        <td><img  width="25%" height="120" id="pictureFilePhoto1" style="display: none;"/></td>
				     </tr>
				     <tr>
				        <td><img  width="25%" height="120" id="pictureFilePhoto2" style="display: none;"/></td>
				        <td><img  width="25%" height="120" id="pictureFilePhoto3" style="display: none;"/></td>
				     </tr>
				   </table>
				</div>
		  </div>
		   <div class="fitem">
				<label style="cursor: pointer;color: #2779AA;width:85px;" class="getProblemAndRescueTypeInfo">问题及救援类型</label>
			    <input name="RescueDetails" id="RescueDetails"  class="easyui-textbox" required="true" data-options="multiline:true,editable:false" style="width: 300px;height:80px" 
			           missingMessage="请点击左方问题及救援类型蓝色文字进行选择"/>
		   </div>
		   <div class="fitem">
				<label style="width:85px;">救援结果:</label>
				<input class="easyui-textbox" name="result" id="result" style="width:300px;height:80px"  data-options="multiline:true" required="required"/>
		   </div>
       </form>
    </div>
     <!--  photo evidence view -->
     <div id="picEvidenceView" class="easyui-dialog" closed="true" style="padding:1px 2px;width:640px;height:160px;">
	          <table id="picTable" style="width: 100%;height: 100%;text-align: center;">
	             <tr>
	                  <td id="pic0" style="width: 25%;height: 100%;text-align: center;"></td>
	                  <td id="pic1" style="width: 25%;height: 100%;text-align: center;"></td>
	                  <td id="pic2" style="width: 25%;height: 100%;text-align: center;"></td>
	                  <td id="pic3" style="width: 25%;height: 100%;text-align: center;"></td>
	             </tr>
	          </table>
     </div>
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
     <div id="getProblemAndRescueTypeInfoView" class="easyui-dialog" closed="true" style="padding:1px 20px 28px 20px">
		<br/>
		<table id="getProblemAndRescueTypeInfoGrid" toolbar="#toolbars"></table>
	</div>
     <div id="detailView" class="easyui-dialog" closed="true" style="width: 600px;height: 400px;">
				<table cellpadding="5" style="font-size: 12px;margin-top: 0px; width: 560px;">
		    		<tr>
		    			<td colspan="3" style="font-weight: bold;">救援信息</td>
		    		</tr>
		    		<tr id="appointmenTr1">
		    			<td width="40%">救援车牌：<span id="lpn"></span></td>
		    			<td width="30%">事故经过：<span id="reason"></span></td>
		    		</tr>
		    		<tr id="appointmenTr2">
		    			<td>任务执行人：<span id="responsiblePerson"></span></td>
		    			<td>执行人号码：<span id="responsiblePersonPhone"></span></td>
		    		</tr>
		    		<tr id="appointmenTr3">
		    		 <td>救援地址：<span id="appointmenCarRentMoney"></span></td>
		    		 <td>救援开始时间：<span id="startTime"></span></td>
		    		</tr>
		    	</table>
		    	<hr style="height:1px;border:none;border-top:1px dashed #A2A2A2;" />
		    	<table cellpadding="5" style="font-size: 12px;margin-top: 0px; width: 560px;">
		    		<tr>
		    			<td style="font-weight: bold;">救援结果</td>
		    		</tr>
		    		<tr>
		    			<td>车辆状态：<span id="status"></span></td>
		    			<td>救援结束时间：<span id="endTime"></span></td>
		    		</tr>
		    		<tr>
		    		    <td>救援结果：<span id="myResult"></span></td>
		    		</tr>
		    	</table>
		 </div>
   	     <div id="carRescueProblemView" class="easyui-dialog" closed="true" style="padding:2px 20px 31px 28px;width: 700px;height: 600px;">
     	   <form style="height: 0px;" id="carRescueProblemForm">     
		   </form><br/>
		   <table id="carRescueProblemGrid" toolbar="#toolbars"></table>	
	    </div>
	    
	    
	    <div id="rescueProblemDetailView" class="easyui-dialog" closed="true"
			style="padding-left:20px;padding-right:20px;padding-top:3px;padding-bottom:30px; width: 1000px; height: 400px;">
			<input type="hidden" name="recId" id="recId">       
			<table id="rescueProblemGrid" 
			 	cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px; left:80px;width:600px;height:200px">
            </table>
            <div id="toolbar1" class="fitem" style="height:auto"></div>
		</div>

</body>
 </html>