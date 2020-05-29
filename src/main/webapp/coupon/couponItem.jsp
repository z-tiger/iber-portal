<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>优惠券配置</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="coupon/couponItem.js"></script>
	<script type="text/javascript">
		$(function(){
			//开始时间默认00:00:00
		    $(".s").datetimebox({  
   			 	onShowPanel:function(){  
        		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
    			}  
			});
			//结束时间默认23:59:59  
		   $(".e").datetimebox({  
   			 	onShowPanel:function(){  
        		$(this).datetimebox("spinner").timespinner("setValue","23:59:59");  
    			} 
			});  
});
	</script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			项目名称:<input id="q_itemname"  name="_itemname" class="easyui-textbox" style="width:80px"/>
			状态:<input class="easyui-combobox" name="status" id="e-status"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '关闭',
							value: '0'
						},{
							label: '启用',
							value: '1'
						}],
	                    panelHeight:'auto'" >
			所属城市:<input class="easyui-combobox" name="city_code" id="q_city_code" style="width:80px"
					  data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">

            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            <r:FunctionRole functionRoleId="update_coupon_configuration">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            
	       <!--<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
	        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>  --> 
         </div>
         <!-- add -->
         <div id="view" class="easyui-dialog" closed="true">
			<form id="viewForm" name="viewForm" method="post">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
						<tr>
			    			<td>项名称:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="itemname" name="itemname" style="width:200px;height:24px" data-options="required:true,missingMessage:'项名称不能为空'">
			    			</td>
			    		</tr>
						<tr>
							<td>所属区域:</td>
							<td>
								<input class="easyui-combobox" name="city_code" id="city_code" style="width:100%;height:24px"
									   data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true,missingMessage:'所属区域不能为空'">
							</td>
						</tr>
						<tr>
			    			<td>项编码:</td>
			    			<td>
		    			 	<input class="easyui-textbox"  id="itemcode" name="itemcode" style="width:200px;height:24px" data-options="required:true,missingMessage:'项编码不能为空'"> 
			    			 	
			    			</td>
			    		</tr>
		    			<tr>
			    			<td>优惠券类型:</td>
			    			<td>
			    			 	<input class="easyui-combobox" name="couponUseType" id="couponUseType" style="width:200px;height:24px"
									data-options="
					                    valueField:'value',
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
								            $('#minUseValue').textbox({    
                                               required:true,    
                                             });    
								         } else if(rec.value=='2'){
								           $('#maxDeductionVal').show(); 
                                          	$('#minUseVal').hide();
								            $('#minUseValue').textbox({    
                                            required:false,    
                                            });  
								         }else{
								        	$('#maxDeductionVal').hide(); 
                                          	$('#minUseVal').hide();
								            $('#minUseValue').textbox({    
                                            required:false,    
                                            });  
								        }    
                                      }, 
					                    panelHeight:'auto'" required="true"  missingMessage="请选择状态">
			    			</td>
			    		</tr>
						<tr>
			    			<td>数量:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="number" name="number" style="width:200px;height:24px" data-options="required:true,missingMessage:'数量不能为空'">
			    			</td>
			    		</tr>
		    		
						<tr>
			    			<td>面值:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="balance" name="balance" style="width:200px;height:24px" data-options="required:true,missingMessage:'面值不能为空 满折10'">&nbsp元(折)
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>状态:</td>
			    			<td>
			    			 	<input class="easyui-combobox" name="status" id="status" style="width:200px;height:24px"
									data-options="
					                    valueField:'value',
					                    textField:'label',
					                    data: [{
											label: '关闭',
											value: '0'
										},{
											label: '启用',
											value: '1'
										}],
					                    panelHeight:'auto'" required="true"  missingMessage="请选择状态" readonly="readonly">
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>期限:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="deadline" name="deadline" style="width:200px;height:24px" data-options="required:true,missingMessage:'期限不能为空'">&nbsp天
			    			</td>
			    		</tr>
			    		
			    		<tr id="minUseVal">
			    			<td>最低使用金额:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  name="minUseValue" id="minUseValue" style="width:200px;height:24px" data-options="missingMessage:'仅对抵扣券有效',groupSeparator:','">&nbsp元
			    			</td>
			    		</tr>
			    		<tr id="maxDeductionVal">
			    			<td>封顶额:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  name="maxDeductionValue" id="maxDeductionValue" style="width:200px;height:24px" data-options="missingMessage:'仅对折扣券有效',groupSeparator:','">&nbsp元
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>活动开始时间</td>
			    			<td>
			    				<input id="start"  name="startTime" class="easyui-datetimebox s" style="width:200px;height:24px" data-options="required:true,missingMessage:'开始时间不能为空'"/>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>活动结束时间</td>
			    			<td>
			    				<input id="end"  name="endTime" class="easyui-datetimebox e" style="width:200px;height:24px" data-options="required:true,missingMessage:'结束时间不能为空'"/>
			    			</td>
			    		</tr>
		    	</table>
			</form>
		 </div> 
	</body>
</html>