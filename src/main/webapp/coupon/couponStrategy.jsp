<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="UTF-8">
<title>优惠券策略配置</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="coupon/couponStrategy.js"></script>
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

		<table id="grid" toolbar="#toolbar"></table>	
   
     
      <!-- 工具栏  start-->
   <div id="toolbar" style="height:auto">
		优惠券类型:
		<input class="easyui-combobox" name="itemName" id="itemName"
					data-options=" url:'couponnames',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
	    所属城市:<input class="easyui-combobox" name="city_code" id="q_city_code" style="width:80px"
				   data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
		优惠券状态:<input class="easyui-combobox" name="couponState" id="couponState" style="width:90px; margin-right:22px"
					data-options="valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '未过期',
							value: '0'
						},{
							label: '已过期',
							value: '1'
						}]">
	      生效时间:
	      
	       <input name="queryDateFrom" id="queryDateFrom"   class="easyui-datebox" size="18" />       	       	       	       
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelete" >删除
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
   </div>
     <!-- 工具栏  end-->
    
    <!-- add view -->
   <div id="addView" class="easyui-dialog" closed="true" style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data">
		   <input type="hidden" name="id" id="id" />
		   <div class="fitem">
			   <label>所属区域:</label>
			   <input class="easyui-combobox" name="city_code" id="city_code" style="width:55%;"
										  data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true,missingMessage:'所属区域不能为空'">
		   </div>
		   <div class="fitem">
				<label>策略类型:</label><input id="AitemName" name="itemName"  class="easyui-combobox"  style="width: 55%;" 
					data-options="
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择优惠券类型">
		   </div>
		    <div class="fitem">
				<label>优惠券类型:</label>
				<input class="easyui-combobox" name="couUseType" id="couUseType" style="width:100px;height:24px"
					   data-options="valueField:'value',
					                 textField:'label',
					                 data: [{
											label: '现金券',
											value: '0'
										},{
											label: '满减券',
											value: '1'
										},{
											label: '折扣券',
											value: '2'
										}],
										onSelect: function(rec){
								          if(rec.value=='1'){
								         	 $('#a-maxDeductionVal').hide(); 
                                          	  $('#a-minUseVal').show(); 
								             $('#minUseValue').textbox({    
                                             required:true,    
                                          		});    
								          } else if(rec.value=='2'){
								          	 $('#a-maxDeductionVal').show(); 
                                          	  $('#a-minUseVal').hide(); 
								            $('#minUseValue').textbox({    
                                            required:false,    
                                     		 });  
								  		 } else{
								  		   $('#a-maxDeductionVal').hide(); 
                                          	  $('#a-minUseVal').hide(); 
								            $('#minUseValue').textbox({    
                                             required:false,    
                                     		 });  
								  		 }   
                                 },
					                    panelHeight:'auto'" required="true"  missingMessage="请选择优惠券类型">
		   </div>
		   <div class="fitem"  style="display: none" >
				<input id="itemCode" name="itemCode">  
				
		   </div>   
		   		   <div class="fitem"  style="display: none" >
				<input id="createTime" name="createTime">  
				
		   </div>   		   		  		   		   		   		   
           <div class="fitem">
				<label>应用此策略的下限:</label>
				<input  id="min"  name="min"  class="easyui-textbox"  style="width: 45%;" />&nbsp元
		   </div>
		   <div class="fitem">
				<label>应用此策略的上限:</label>
				<input   id="max" name="max"  class="easyui-textbox"  style="width: 45%;" />&nbsp元
		   </div>
		    <div class="fitem">
				<label>优惠券面值:</label>
				<input   id="balance"    name="balance"  class="easyui-textbox"  style="width: 45%;" />&nbsp元(折)
		   </div>
		    <div class="fitem">
				<label>优惠券张数:</label>
				<input  id="number"  name="number"  class="easyui-textbox"  style="width: 45%;" />&nbsp张
		   </div>
		    <div class="fitem"  style=" display: none">
				<label>合计优惠金额:</label>
				<input  id="total"   name="total"  class="easyui-textbox"  style="width: 45%;" />&nbsp元
		   </div>
		   <div class="fitem">
				<label>策略生效时间:</label>
				<input id="startTime" name="startTime" class="easyui-datetimebox" size="18" style="width: 55%;"  class="easyui-datebox" data-options="required:true,missingMessage:'请选择优惠券生效时间',editable:false" />
		   </div>
		   <div class="fitem">
				<label>策略有效期限:</label>
				<input  id="deadline" name="deadline"  class="easyui-textbox" id="e-deadline" style="width: 45%;" />&nbsp天	
		   </div>
		   <div class="fitem" id="a-minUseVal">
				<label>最低使用值:</label>
				<input  id="minUseValue" name="minUseValue"  class="easyui-textbox"  style="width: 45%;" data-options="missingMessage:'仅对满减券有效',groupSeparator:','"/>&nbsp元	
		   </div>          
		   <div class="fitem" id="a-maxDeductionVal">
				<label>封顶额:</label>
				<input  id="maxDeductionValue" name="maxDeductionValue"  class="easyui-textbox" style="width: 45%;" data-options="missingMessage:'仅对满减券有效',groupSeparator:','"/>&nbsp元	
		   </div>          
       </form>
   </div>
   
   
    <!-- update view -->
   <div id="editView" class="easyui-dialog" closed="true" style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
      	 <input type="hidden" name="id" id="e-id">
		 <div class="fitem">
			 <label>所属区域:</label>
			 <input class="easyui-combobox" name="city_code" id="city_code2" style="width:55%;"
					data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true,missingMessage:'所属区域不能为空'">
		 </div>
      	 <div class="fitem">
				<label>策略类型:</label><input class="easyui-combobox" name="itemName"  id="e-itemName" style="width: 55%;" 
					data-options="
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
					required="true"  missingMessage="请选择优惠券类型">
		   </div>
		     <div class="fitem"  style="display: none" >
				<input id="U_itemCode" name="itemCode">  
				
		   </div> 
		   <div class="fitem">
		    	<label>优惠券类型:</label>   
		    					<input class="easyui-combobox" name="couUseType" id="e-couUseType" style="width:100px;height:24px"
					   data-options="valueField:'value',
					                 textField:'label',
					                 data: [{
											label: '现金券',
											value: '0'
										},{
											label: '满减券',
											value: '1'
										},{
											label: '折扣券',
											value: '2'
										}],
										onSelect: function(rec){
								        if(rec.value=='1'){
                                          	  $('#maxDeductionVal').hide(); 
                                          	  $('#minUseVal').show(); 
								             $('#e-minUseValue').textbox({    
                                             required:true,    
                                          	}); 
								        } else if(rec.value=='2'){
								       		 $('#maxDeductionVal').show(); 
                                          	  $('#minUseVal').hide(); 
								            $('#e-minUseValue').textbox({    
                                            required:false,    
                                       		});  
								  		}else{
								  		 	$('#maxDeductionVal').hide(); 
                                          	$('#minUseVal').hide(); 
                                          	$('#e-minUseValue').textbox({    
                                            required:false,    
                                       		}); 
								  		}    
                                 },
					    panelHeight:'auto'" required="true"  missingMessage="请选择优惠券类型">
           </div> 
           <div class="fitem">
				<label>应用此策略的下限:</label><input  name="min"  class="easyui-textbox" id="e-min" style="width: 45%;" />&nbsp元
		   </div>
		   <div class="fitem">
				<label>应用此策略的上限:</label><input  name="max"  class="easyui-textbox" id="e-max" style="width: 45%;" />&nbsp元
		   </div>
		    <div class="fitem" >
				<label>优惠券面值:</label><input  name="balance"  class="easyui-textbox"  id="e-balance" style="width: 45%;" />&nbsp元(折)
		   </div>
		    <div class="fitem">
				<label>优惠券张数:</label><input  name="number"  class="easyui-textbox" id="e-number" style="width: 45%;"  />&nbsp张
		   </div>
		   <div class="fitem"  style=" display: none">
				<label>合计优惠金额:</label><input  name="total"  class="easyui-textbox" id="e-total" style="width: 45%;" />&nbsp元
		   </div>
		   <div class="fitem">
		   									
		   <label>策略生效时间:</label> <input  name="startTime" id="e-startTime" class="easyui-datetimebox" size="18" style="width: 45%;"  class="easyui-datebox" data-options="required:true,missingMessage:'请选择优惠券生效时间',editable:false" />
		   </div>
		   <div class="fitem">
				<label>策略有效期限:</label>   <input  name="deadline"  class="easyui-textbox" id="e-deadline" style="width: 45%;" data-options="required:true,missingMessage:'期限不能为空'" />	&nbsp天
           </div>
           <div class="fitem"  id="minUseVal">
				<label>最低使用值:</label><input  name="minUseValue"  class="easyui-textbox" id="e-minUseValue" style="width: 45%;" />&nbsp元
		   </div>    
           <div class="fitem" id="maxDeductionVal">
				<label>封顶额:</label><input  name="maxDeductionValue"  class="easyui-textbox" id="e-maxDeductionValue" style="width: 45%;" />&nbsp元
		   </div>    
       </form>
   </div>
 
    
       
</body>
</html>