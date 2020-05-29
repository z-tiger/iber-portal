<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>调度员管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script language="javascript" src="https://webapi.amap.com/maps?v=1.3&key=a22fc1682b40fc7774e90ee30fa36466"></script>
	<script type="text/javascript" src="dispatcher/dispatcherManagement.js"></script>
	<style type="text/css">
		/*.ftitle {
			font-size: 14px;
			font-weight: bold;
			padding: 5px 0;
			margin-bottom: 10px;
			border-bottom: 1px solid #ccc;
		}

		.fitem {
			width: 100%;
		}
		*/
		.fitem label {
			display: inline-block;
			width: 80px;
		}
		
		.fitem input {
			width: 142px;
		}
		/*
		.fitem a {
			margin-right: 5px;
		}*/

	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" class="fitem" style="height:auto">
 	    <form action="" id="dispatcherForm">
 	        所属地区:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;" 
				data-options="url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			调度员名称:<input id="_dispatcherName"  name="dispatcherName" class="easyui-textbox" style="width:150px"/> 
			所属网格:
			<input name="griddingId" id="griddingName" class="easyui-combobox"
				data-options=" url:'sys_griddingNameCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"/>
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
			<r:FunctionRole functionRoleId="append_dispatcher">
        		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
        	</r:FunctionRole>
        	<r:FunctionRole functionRoleId="edit_dispatcher">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="cut_dispatcher">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjdj" plain="true" id="btnFrozen">冻结</a>
            </r:FunctionRole>
                        <r:FunctionRole functionRoleId="active_dispatcher">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjjd" plain="true" id="btnActive">启用</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="cut_dispatcherFinger">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-finger" plain="true" id="btnFinger">删除二代指纹</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="cut_dispatcherFinger">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-finger" plain="true" id="btnTboxFinger">删除三代指纹</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="export_dispatcher">
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
	           <input type="hidden" name="id" id="e-id">
	           <input type="hidden" name="operationType" id="o-type">
			   <div class="fitem">
			   		<tr>
			   			<td>姓名:&nbsp;&nbsp;</td>
			   			<td>
			   				<input class="easyui-textbox" name="dispatherName" id="e-dispatherName" style="width: 30%;"  required="true"  missingMessage="请填写姓名"/>
		                </td>
		                <td>手机号:&nbsp;</td>
			   			<td><input class="easyui-textbox" name="phone" id="e-phone" style="width: 30%;"  required="true"  missingMessage="请填写手机号"/>
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>性别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="sex" id="e-sex" data-options=" 
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
			   			<td>&nbsp;身份:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="ident" id="e-ident" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                     required:true,
		                    data:[{
    							'id':1,
   							    'text':'调度员'
							}] ">
		                </td>
						<td>行政职位:&nbsp;</td>
						<td><input class="easyui-textbox" name="position" id="e-position"   required="true"  missingMessage="请填写行政职位"/>
						</td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td>所属城市:</td>
			   			<td><input class="easyui-combobox" name="city" id="e-city" data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    required:true,
		                    panelHeight:'auto'">
		                </td>
			   			<td>&nbsp;工种:&nbsp;&nbsp;</td>
			   			<td><input class="easyui-combobox" name="worker" id="e-worker" data-options=" 
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
						<td>邮箱:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input class="easyui-textbox" name="email" id="e-email"   required="true"  missingMessage="请填写邮箱"/>
						</td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td>特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
	       </form>
   		</div>
   	    <!-- 修改对话框 -->
   		<div id="updateView" class="easyui-dialog" closed="true" style="padding:10px 50px">
	       	 <form name="updateViewForm" id="updateViewForm">
	           <input type="hidden" name="id" id="u-id">
			   <div class="fitem">
			   		<tr>
			   			<td width="15%">姓名:&nbsp;&nbsp;</td>
			   			<td width="35%">
			   				<input class="easyui-textbox" name="dispatherName" id="u-dispatherName" style="width: 30%;"  required="true"  missingMessage="请填写姓名"/>
		                </td>
		                <td width="15%">手机号:&nbsp;</td>
			   			<td width="35%"><input class="easyui-textbox" name="phone" id="u-phone" style="width: 30%;"  required="true"  missingMessage="请填写手机号"/>
		                </td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td >性别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			   			<td ><input class="easyui-combobox" style="width: 23%;" name="sex" id="u-sex" data-options="
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
			   			<td >身份:&nbsp;&nbsp;</td>
			   			<td ><input class="easyui-combobox" name="ident" style="width: 23%" id="u-ident" data-options="
			   				valueField:'id',
		                    textField:'text',
		                     panelHeight:'auto',
		                     required:true,
		                    data:[{   
    							'id':0,   
   							    'text':'员工'  
							},{   
    							'id':1,   
   							    'text':'主管'  
							}] ">
		                </td>
						<td>行政职位:&nbsp;</td>
						<td><input class="easyui-textbox" name="position" id="u-position"   required="true"  missingMessage="请填写行政职位"/>
						</td>
		            </tr>
		            <br><br>
			   		<tr>
			   			<td> 所属城市:</td>
			   			<td ><input class="easyui-combobox" name="city" id="u-city" style="width:23%;" data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    required:true,
		                    panelHeight:'auto'">
		                </td>
			   			<td>工种:&nbsp;</td>
			   			<td ><input class="easyui-combobox" name="worker" style="width: 23%"  id="u-worker" data-options="
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
						<td>&nbsp;&nbsp;&nbsp;&nbsp;邮箱:&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><input class="easyui-textbox" name="email" id="u-email"   required="true"  missingMessage="请填写邮箱"/>
						</td>
		            </tr>
		            <br><br>
		             <tr>
		            	<td width="15%">类型：</td>
		            	<td width="35%">
		            		<input class="easyui-combobox" name="type" id ="u-type" data-options="
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
		            	<td></td>
		            	<td></td>
		            </tr>
		            <br><br>
		            <tr>
			   			<td width="15%">特别说明:</td>
			   			<td>
			   				<input class="easyui-textbox" data-options="multiline:true" name="specialComment" id="u-specialComment" style="width: 75%;height: 50%;"></textarea>
			   			</td>
		            </tr>
			   </div>
	       </form>
   		</div>
	</body>
</html>