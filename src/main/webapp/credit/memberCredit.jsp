<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>

<html>
  <head>
   <meta charset="UTF-8">
    <title>会员信用管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="version/common.js"></script>
<script type="text/javascript" src="credit/memberCredit.js"></script>
<style type="text/css">
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
     <table id="grid" toolbar="#toolbar"></table>
       
       <div id="toolbar" style="height:auto">
       	<form action="" id="creditForm">
       	 区域:
		<input class="easyui-combobox" style="width:100px;" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_cityCombobox',
	                    method:'post',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">  
	                     分类:
		<input class="easyui-combobox" style="width:100px;" name="behaviorTypeName" id="s-behaviorTypeName"
					data-options=" url:'getBehaviorType_list',
	                    method:'post',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">  
           &nbsp; 车牌号:<input type="text" name="lpn" style="width:100px;" class="easyui-textbox" id="s-lpn">
           &nbsp; 追究会员姓名:<input type="text" style="width:100px;" name="reportedMemberName" class="easyui-textbox" id="s-reportedMemberName">
           &nbsp; 反馈人姓名:<input type="text" name="createName" style="width:100px;" class="easyui-textbox" id="s-createName">
         		  审核状态<input class="easyui-combobox" style="width:100px;" name="status" id="s-status"
					data-options=" valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '未审核',
							value: '0'
						},{
							label: '审核通过',
							value: '1'
						},{
							label: '审核不通过',
							value: '2'
						}],
	                    panelHeight:'auto'">
	                    
	    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
	    <r:FunctionRole functionRoleId="add_credit">
	    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a>
	    </r:FunctionRole>
		<r:FunctionRole functionRoleId="audit_credit">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-license" plain="true" id="btnExamine" onclick=";">审核</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="export_credit">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
       </div>
      <div id="addMemberCreditView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addMemberCreditForm" name="addMemberCreditForm" method="post" action=""enctype="multipart/form-data" >
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td nowrap="nowrap">区域:</td>
		    			<td >
		    				<input class="easyui-combobox" style="width:140px;" name="cityCode" id="cityCode"
											data-options=" url:'sys_not00_cityCombobox',
							                    method:'post',
							                    valueField:'id',
							                    textField:'text',
							                    panelHeight:'auto',required:true">  
		    			</td>
		    			<td><!-- 反馈类型: --></td>
		    			<td ><!-- <input class="easyui-combobox" style="width:140px;" name="objType" id="objType"
											data-options=" valueField:'value',
							                    textField:'label',
							                    data: [{
													label: '车',
													value: '1'
												},{
													label: '充电桩',
													value: '2'
												}],
							                    panelHeight:'auto',required:true">   -->
						</td>
		    			
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">分类:</td>
		    			<td> <input class="easyui-combobox" style="width:140px;" name="behaviorParentId" id="behavior_parent_id"
										data-options=" url:'getCanAddBehaviorType_list',
						                    method:'post',
						                    valueField:'id',
						                    textField:'text',
						                    panelHeight:'auto',required:true">
	                    </td>
		    			<td nowrap="nowrap">子分类:</td>
		    			<td id="son_behavor1">
		    				<input class="easyui-combobox" style="width:140px;" name="behaviorChildrenId" id="behavior_children_id"
										data-options="
						                    valueField:'id',
						                    textField:'text',
						                    panelHeight:'auto',required:true">
						</td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">追究会员名:</td>
		    			<td><input type="text" name="reportedMemberName" style="width:140px;" class="easyui-textbox" data-options="required:true" id="reportedMemberName"></td>
		    			<td nowrap="nowrap">手机号码:</td>
		    			<td><input type="text" name="reportedPhone" style="width:140px;" class="easyui-textbox" id="reportedPhone" data-options="required:true"></td>
		    		</tr>
		    		<tr id="car_show">
		    			<td nowrap="nowrap"> 车牌号:</td>
		    			<td><input type="text" name="lpn" style="width:140px;" class="easyui-textbox" id="lpn"></td>
		    			<td></td>
		    			<td></td>
		    		</tr>
		    		<tr id="other_show">
		    			<td nowrap="nowrap"> 贡献值:</td>
		    			<td><input type="text" name="contriVal" style="width:140px;" class="easyui-textbox" id="contriVal"></td>
		    			<td></td>
		    			<td></td>
		    		</tr>
		    		<tr id="park_show">
		    			<td nowrap="nowrap"> 网点:</td>
		    			<td><input type="text" name="parkName" style="width:140px;" class="easyui-textbox" id="park_name"></td>
		    			<td>车位号码:</td>
		    			<td><input type="text" name="parkNo" style="width:140px;" class="easyui-textbox" id="park_no"></td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">原因:</td>
		    			<td colspan="3">
		    				<input id="audit_explain1" name="auditExplain" class="easyui-textbox"  data-options="multiline:true,required:true"  style="width:350px;height:60px"/>
		    			</td>
		    		</tr>  
		    		<tr>
		    			<td nowrap="nowrap">图片凭证:</td>
		    			<td colspan="3">
		    				  <input  name="boxfile" id="boxfile" type="file" 
											onchange="javascript:setImagePreview(this,reportFileImg,reportFilePhoto,1);" />
		    				  <input  name="boxfile1" id="boxfile1" type="file" 
											onchange="javascript:setImagePreview(this,reportFileImg1,reportFilePhoto1,2);" style="display: none;"/>
		    				  <input  name="boxfile2" id="boxfile2" type="file" 
											onchange="javascript:setImagePreview(this,reportFileImg2,reportFilePhoto2,3);"  style="display: none;"/>
		    				  <input  name="boxfile3" id="boxfile3" type="file" 
											onchange="javascript:setImagePreview(this,reportFileImg3,reportFilePhoto3,4);"  style="display: none;"/>
		    			</td>
		    		</tr>  
		    		<tr>
		    			
		    			<td colspan="4">
		    				  <div class="fitem" id='add_img'>
										
						        </div>
		    			</td>
		    		</tr>  
		    	</table> 
			</form>
			 </div>
      <div id="editMemberCreditView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="editMemberCreditForm" name="editMemberCreditForm" method="post" action=""enctype="multipart/form-data" >
			<input type="hidden" id="e_id" name="id">
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td nowrap="nowrap">区域:</td>
		    			<td >
		    				<span id="e_cityName"></span>
		    			</td>
		    			<td></td>
		    			<td >
						</td>
		    			
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">分类:</td>
		    			<td> <input class="easyui-combobox" style="width:140px;" name="behaviorParentId" id="e_behavior_parent_id"
										data-options=" url:'getCanAddBehaviorType_list',
						                    method:'post',
						                    valueField:'id',
						                    textField:'text',
						                    panelHeight:'auto',required:true">
	                    </td>
		    			<td nowrap="nowrap">子分类:</td>
		    			<td id="son_behavor">
		    				<input class="easyui-combobox" style="width:140px;" name="behaviorChildrenId" id="e_behavior_children_id"
										data-options="
						                    valueField:'id',
						                    textField:'text',
						                    panelHeight:'auto',required:true">
						</td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">追究会员名:</td>
		    			<td><input type="text" name="reportedMemberName" style="width:140px;" class="easyui-textbox" id="e_reportedMemberName"></td>
		    			<td nowrap="nowrap">手机号码:</td>
		    			<td><input type="text" name="reportedPhone" style="width:140px;" class="easyui-textbox" id="e_reportedPhone"></td>
		    		</tr>
		    		<tr id="e_car_show">
		    			<td nowrap="nowrap"> 车牌号:</td>
		    			<td> <input type="text" name="lpn" style="width:140px;" class="easyui-textbox" id="e_lpn" disabled="disabled">
		    			</td>
		    			<td></td>
		    			<td></td>
		    		</tr>
		    		<tr id="e_park_show">
		    			<td nowrap="nowrap"> 网点:</td>
		    			<td><input type="text" name="parkId" style="width:140px;" class="easyui-textbox" id="e_park_name" disabled="disabled"></td>
		    			<td>车位号码:</td>
		    			<td><input type="text" name="parkNo" style="width:140px;" class="easyui-textbox" id="e_park_no" disabled="disabled"></td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">反馈原因:</td>
		    			<td colspan="3">
		    				<input id="e_remark" name="remark" class="easyui-textbox"  data-options="multiline:true"  style="width:350px;height:60px" disabled="disabled"/>
		    			</td>
		    		</tr>  
		    		<tr>
		    			<td nowrap="nowrap">图片凭证:</td>
		    			<td colspan="3">
		    			</td>
		    		</tr>  
		    		<tr>
		    			
		    			<td colspan="4">
		    				  <div class="evidence_img fitem"  >
										
						        </div>
		    			</td>
		    		</tr>  
		    	</table> 
			</form>
			 </div>
		<div id="getOrderInfoView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			 <table id="getOrderInfoGrid" toolbar="#toolbars"></table>
		</div> 
      <div id="checkMemberCreditView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="checkMemberCreditForm" name="checkMemberCreditForm" method="post" action=""enctype="multipart/form-data" >
			<input type="hidden" id="c_id" name="id">
			<input type="hidden" id="c_type" name="typeCode">
			<input type="hidden" id="c_createId" name="createId">
			<input type="hidden" id="c_memberId" name="memberId">
			<input type="hidden" id="c_parkId" name="parkId">
			<input type="hidden" id="c_isMemberComplain" name="isMemberComplain">
			<input type="hidden" id="c_orderId" name="orderId">
			<span style="color: red;">注意：请先关联订单再核实通过/不通过</span>
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td nowrap="nowrap">区域:</td>
		    			<td >
		    				<span id="c_cityName"></span>
		    			</td>
		    			<td></td>
		    			<td >
						</td>
		    			
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">分类:</td>
		    			<td> <input class="easyui-combobox" style="width:140px;" name="behaviorParentId" id="c_behavior_parent_id"
										data-options="
						                    valueField:'id',
						                    textField:'text',
						                    panelHeight:'auto',required:true">
	                    </td>
		    			<td nowrap="nowrap">子分类:</td>
		    			<td id="son_behavor2">
		    				<input class="easyui-combobox" style="width:140px;" name="behaviorChildrenId" id="c_behavior_children_id"
										data-options="
						                    valueField:'id',
						                    textField:'text',
						                    panelHeight:'auto',required:true">
						</td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">追究会员名:</td>
		    			<td><input type="text" name="reportedMemberName" style="width:140px;" class="easyui-textbox" id="c_reportedMemberName"></td>
		    			<td nowrap="nowrap">手机号码:</td>
		    			<td><input type="text" name="reportedPhone" style="width:140px;" class="easyui-textbox" id="c_reportedPhone"></td>
		    		</tr>
		    		<tr id="c_car_show">
		    			<td nowrap="nowrap"> 车牌号:</td>
		    			<td><input type="text" name="lpn" style="width:140px;" class="easyui-textbox" id="c_lpn"></td>
		    			<td><span style="cursor: pointer;color: #2779AA;" class="getOrderInfo">关联订单</span></td>
		    			<td></td>
		    		</tr>
		    		<tr id="c_park_show">
		    			<td nowrap="nowrap"> 网点:</td>
		    			<td><input type="text" name="parkName" style="width:140px;" class="easyui-textbox" id="c_park_name"></td>
		    			<td>车位号码:</td>
		    			<td><input type="text" name="parkNo" style="width:140px;" class="easyui-textbox" id="c_park_no"><span style="cursor: pointer;color: #2779AA;" class="getOrderInfo">关联订单</span></td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">反馈原因:</td>
		    			<td colspan="3" >
		    				<span id="c_remark"></span>
		    			</td>
		    		</tr>  
		    		<tr>
		    			<td nowrap="nowrap">图片凭证:</td>
		    			<td colspan="3" >
		    			
		    			</td>
		    		</tr>  
		    		<tr>
		    			
		    			<td colspan="4">
		    				  <div class="evidence_img fitem"  >
										
						        </div>
		    			</td>
		    		</tr>  
		    		<tr>
		    			<td>反馈人:</td><td ><span id="c_create_name"></span></td><td nowrap="nowrap">反馈人身份:</td><td ><span id='c_isMemberComplainName'></span></td>
		    		</tr>
		    		<tr>
		    			<td>反馈时间:</td><td ><span id="c_create_time"></span></td><td></td><td ></td>
		    		</tr>
		    		<tr>
		    			<td>审核结果:</td><td ></td><td></td><td ></td>
		    		</tr>
		    		<tr>
		    			<td colspan="4">
		    					<input id="audit_explain" name="auditExplain" class="easyui-textbox"  data-options="multiline:true"  style="width:420px;height:60px" />
		    			</td>
		    		</tr>
		    	</table> 
			</form>
			 </div>
			 
			  <div id="LookMemberCreditView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			  <form id="LookMemberCreditForm" name="checkMemberCreditForm" method="post" action=""enctype="multipart/form-data" >
				<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
				<tr>
		    			<td nowrap="nowrap">区域:</td>
		    			<td >
		    				<span id="d_cityName"></span>
		    			</td>
		    			<td></td>
		    			<td >
						</td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">分类:</td>
		    			<td> <span id='d_behavior_parent_name'></span> </td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">子分类:</td>
		    			<td> <span id='d_behavior_children_name'></span>
						</td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">追究会员名:</td>
		    			<td><span id="d_reportedMemberName"></span></td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">手机号码:</td>
		    			<td><span id="d_reportedPhone"></span></td>
		    		</tr>
		    		<tr id="d_car_show">
		    			<td nowrap="nowrap"> 车牌号:</td>
		    			<td><span id="d_lpn"></span></td>
		    			<td></td>
		    			<td></td>
		    		</tr>
		    		<tr id="d_park_show">
		    			<td nowrap="nowrap"> 网点:</td>
                        <td><span id="d_park_name"></span></td>
		    		</tr>
		    		<tr>
		    			<td>车位号码:</td>
		    			<td><span id ='d_park_no'></span></td>
		    		</tr>
		    		<tr>
		    			<td nowrap="nowrap">反馈原因:</td>
		    			<td colspan="3" >
		    				<span id="d_remark"></span>
		    			</td>
		    		</tr>  
		    		<tr>
		    			<td nowrap="nowrap">图片凭证:</td>
		    			<td colspan="3" >
		    				
		    			</td>
		    		</tr>  
		    		
		    		<tr>
		    			<td colspan="4">
		    				  <div class="evidence_img_detail fitem"   >
										
						      </div>
		    			</td>
			 		</tr>
		    		<tr>
		    			<td>反馈人:</td><td align="left" colspan="3"><span id="d_createName" style="text-align: left;"></span></td>
		    			
	    			</tr>
	    			<tr>
		    			<td>反馈人身份:</td><td align="left" colspan="3"><span id="d_ismember" style="text-align: left;"></span></td>
			 		</tr>
		    		<tr>
		    			<td>反馈时间:</td><td colspan="3" align="left"><span id="d_createTime" style="text-align: left;"></span></td>
			 		</tr>
		    		<tr>
		    			<td>审核结果:</td><td colspan="3" align="left"><span id="d_auditExplain" style="text-align: left;"></span></td>
			 		</tr>
		    		<tr>
		    			<td>审核人:</td><td colspan="3" align="left"><span id="d_auditName" style="text-align: left;"></span></td>
			 		</tr>
		    		<tr>
		    			<td>审核时间:</td><td colspan="3" align="left"><span id="d_auditTime" style="text-align: left;"></span></td>
			 		</tr>
		    		<tr>
		    			<td>审核状态:</td><td colspan="3" align="left"><span id="d_status" style="text-align: left;"></span></td>
			 		</tr>
		    	</table> 
			 </form>
			 </div>
  </body>
  </html>