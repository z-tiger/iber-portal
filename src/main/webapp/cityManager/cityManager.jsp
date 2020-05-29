<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>城市管理员</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
	<script type="text/javascript" src="cityManager/cityManager.js"></script>
	<style type="text/css">
		.fitem label {
			display: inline-block;
			width: 80px;
		}
	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" class="fitem" style="height:auto">
 	    <form action="" id="cityManagerForm">
 	        所属地区:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 150px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			城市管理员名称:<input id="cityManagerName"  name="cityManagerName" class="easyui-textbox" style="width:150px"/> 
			状态:<input class="easyui-combobox" id="status" name="status" 
			           			data-options="
			                    valueField:'value',
			                    textField:'label',
			                    data: [{
									label: '全部',
									value: ''
								},{
									label:'上班',
									value:'working',
									selected:true
								},{
									label:'下班',
									value:'closed'
								},{
									label:'冻结',
									value:'freeze'
								},{
									label:'使用车辆中',
									value:'useCar'
								},{
									label:'预约车辆中',
									value:'ordered'
								}],
			                    panelHeight:'auto'"  style="width:150px;" > 
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="add_cityManager">
        		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
        	</r:FunctionRole>
        	<r:FunctionRole functionRoleId="edit_cityManager">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="freeze_cityManager">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjdj" plain="true" id="btnFrozen">冻结</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="active_cityManager">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjjd" plain="true" id="btnActive">启用</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="cut_cityManagerFinger">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-finger" plain="true" id="btnFinger">删除二代指纹</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="cut_cityManagerFinger">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-finger" plain="true" id="btnTboxFinger">删除三代指纹</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="export_cityManager">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
			</r:FunctionRole>
            <r:FunctionRole functionRoleId="reset_password">
			    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-password" plain="true" id="resetPassword">重置密码</a>
            </r:FunctionRole>
            </form>
	    </div>
      	<!-- 编辑对话框 -->
		<div id="addView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="addViewForm" id="addViewForm">
	           <input type="hidden" name="id" id="a-id">
	           <input type="hidden" name="operateType" id="e-oType">
			   <div class="fitem">
			   		<tr>
			   			<td>姓名:&nbsp;&nbsp;</td>
			   			<td>
			   				<input class="easyui-textbox" name="cityManagerName"   id="a-cityManagerName" style="width:28%;"  required="true"  missingMessage="请填写姓名"/>
		                </td>
		                <td>&nbsp;&nbsp;手机号:&nbsp;&nbsp;&nbsp;</td>
			   			<td><input class="easyui-textbox" name="phone" id="a-phone" style="width: 24%;"  required="true"  missingMessage="请填写手机号"/>
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>性别:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="sex"  style="width: 28%" id="a-sex" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                     required:true,
		                    data:[{   
    							'id':1,   
   							    'text':'男'  
							},{   
							    'id':2,   
							    'text':'女'  
							}] ">
		                </td>
		                <td>所属城市:&nbsp;</td>
			   			<td><input class="easyui-combobox" name="city" id="a-city"  style="width: 24%" data-options="  url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    required:true,
		                    panelHeight:'auto'">
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>工种:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="worker"  style="width: 28%" id="a-worker" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                    data:[{   
    							'id':1,   
   							    'text':'正式工'  
							},{   
							    'id':0,   
							    'text':'临时工'  
							}] ">
		                </td>
						<td>行政职位:&nbsp;</td>
						<td><input class="easyui-textbox" name="position" id="a-position"  style="width: 24%" required="true"  missingMessage="请填写行政职位"/>
						</td>
						<td>邮箱:&nbsp;</td>
						<td><input class="easyui-textbox" name="email" id="a-email"  style="width: 24%"  required="true"  missingMessage="请填写邮箱"/>
						</td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="a-specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
	       </form>
   		</div>
   	
   	      	<!-- 修改对话框 -->
		<div id="editView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="editViewForm" id="editViewForm">
	           <input type="hidden" name="id" id="e-id">
			   <div class="fitem">
			   		<tr>
			   			<td>姓名:&nbsp;&nbsp;</td>
			   			<td>
			   				<input class="easyui-textbox" name="cityManagerName" id="e-cityManagerName" style="width: 30%;"  required="true"  missingMessage="请填写行政职位"/>
		                </td>
		                <td>手机号:&nbsp;</td>
			   			<td><input class="easyui-textbox" name="phone" id="e-phone" style="width: 30%;"  required="true"  missingMessage="请填写邮箱"/>
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>性别:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="sex" style="width: 20%" id="e-sex" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                     required:true,
		                    data:[{   
    							'id':1,   
   							    'text':'男'  
							},{   
							    'id':2,   
							    'text':'女'  
							}] ">
		                </td>
		                <td>所属城市:</td>
			   			<td><input class="easyui-combobox" name="city" id="e-city" style="width: 25%" data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    required:true,
		                    panelHeight:'auto'">
		                </td>
						<td>行政职位:&nbsp;</td>
						<td><input class="easyui-textbox" name="position" id="e-position"  style="width: 25%" required="true"  missingMessage="请填写行政职位"/>
						</td>
		            </tr>
		            <br><br>
		             <tr>
		            	<td>类型：</td>
		            	<td>
		            		<input class="easyui-combobox" style="width: 20%" name="type" id ="e-type" data-options="
		            		valueField:'id',
		                    textField:'text',
		                    required:true,
		                    panelHeight:'auto',
		                    data:[{   
    							'id':1,   
   							    'text':'调度员'  
							},{   
							    'id':2,   
							    'text':'救援员'  
							},{   
							    'id':3,   
							    'text':'维保员'  
							},{   
							    'id':4,   
							    'text':'城市管理员'  
							}]">
		            	</td>
		            	<td>&nbsp;&nbsp;&nbsp;&nbsp;工种:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="worker" style="width: 25%" id="e-worker" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                    data:[{   
    							'id':1,   
   							    'text':'正式工'  
							},{   
							    'id':0,   
							    'text':'临时工'  
							}] ">
		                </td>
						 <td>&nbsp;&nbsp;&nbsp;邮箱:&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 <td><input class="easyui-textbox" name="email" id="e-email" style="width: 25%"   required="true"  missingMessage="请填写邮箱"/>
						 </td>
		            </tr>
		            <br><br>
		            <tr>
			   			
		            	<td>身份：</td>
			   			<td><input class="easyui-combobox" name="identy" style="width: 20%"  id="e-identy" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                    data:[{   
    							'id':1,   
   							    'text':'主管'  
							},{   
							    'id':0,   
							    'text':'员工'  
							},{   
							    'id':2,   
							    'text':'城市管理员'  
							}] ">
		                </td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="e-specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
	       </form>
   		</div>
	</body>
</html>