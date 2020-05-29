<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>

<html>
  <head>
   <meta charset="UTF-8">
    <title>会员行为明细管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="version/common.js"></script>
<script type="text/javascript" src="member/behavior.js"></script>
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
 .radioSpan {
      position: relative;
      border: 1px solid #95B8E7;
      background-color: #fff;
      vertical-align: middle;
      display: inline-block;
      overflow: hidden;
      white-space: nowrap;
      margin: 0;
      padding: 0;
      -moz-border-radius: 5px 5px 5px 5px;
      -webkit-border-radius: 5px 5px 5px 5px;
      border-radius: 5px 5px 5px 5px;
      display:block;
    }
</style>
  </head>
  
  <body>
      <table id="grid" toolbar="#toolbar"></table>
       
       <div id="toolbar" style="height:auto">
       <form action="" id="behaviorForm">
      分类:
		<input class="easyui-combobox" name="behaviorTypeName" id="s-behaviorTypeName"
					data-options=" url:'getBehaviorType_list',
	                    method:'post',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">  
	     &nbsp; 子分类:<input type="text" name="name" class="easyui-textbox" id="s-name">
      &nbsp;  加/扣分:
		<input class="easyui-combobox" style="width:80px;" name="isIncrease" id="s-isIncrease"
					data-options=" valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '扣分',
							value: '0'
						},{
							label: '加分',
							value: '1'
						}],
	                    panelHeight:'auto'">
		 &nbsp;贡献值类型:<input class="easyui-combobox"  style="width:80px;"name="isRatio" id="s-isRatio"
					data-options=" valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '绝对值',
							value: '0'
						},{
							label: '比例',
							value: '1'
						}],
	                    panelHeight:'auto'">
	 &nbsp;贡献值:  &nbsp;<input type="text" name="minContriValue" class="easyui-numberbox" id="s-minContriValue" style="width:80px;">
					 &nbsp;~ &nbsp;<input type="text" name="maxContriValue" class="easyui-numberbox" id="s-maxContriValue" style="width:80px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<r:FunctionRole functionRoleId="add_member_behavior">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" >添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_member_behavior">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_member_behavior">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnDelete" onclick=";">删除</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="export_behavior">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
		</form>
      </div>
      
      
       <!--添加行为管理  --> 
       <div id="addBehaviorView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
        <form  name="addViewForm" id="addViewForm" method="post" style="width:300px;padding:10px 10px">
        <input type="hidden" id="id" name="behavior_id">
        <table cellpadding="5" style=" font-size: 12px;margin-left:10px;margin-top: 0px;">	
        <tr>
   			<td nowrap="nowrap">分类:</td>
   			<td colspan="3">
   			 <input class="easyui-combobox" name="behaviorType" id="behaviorType" data-options="required:true"  style="width:280px;height:24px" /></td>
   			</td>
		</tr>
		
      	<tr>
        	<td  nowrap="nowrap">子分类</td>	<td colspan="3"><input id="name" name="name" class="easyui-textbox" data-options="required:true"  style="width:280px;height:24px" /></td>
      	</tr> 
     	<tr>
	        <td  nowrap="nowrap">贡献值类型：</td> 
	       	<td> <input class="easyui-combobox" style="width:90px;height:24px"  name="isRatio" id="isRatio" data-options="
                        valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '比例',
							value: '1'
						},{
							label: '绝对值',
							value: '0'
						}],
	                    panelHeight:'auto',required:true"
	        /></td>
	       <td  nowrap="nowrap">加/扣分：</td> 
           <td><input class="easyui-combobox" style="width:90px;height:24px" name="isIncrease" id="isIncrease" data-options="
                        valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '扣分',
							value: '0'
						},{
							label: '加分',
							value: '1'
						}],
	                    panelHeight:'auto',required:true"
       		 /></td>
      	</tr> 
      	<tr>
           	<td  nowrap="nowrap">贡献值:</td>	<td><input id="contriValue" name="contriValue" class="easyui-textbox" style="width:90px;height:24px"  data-options="required:true" /></td>
         	<td  nowrap="nowrap">子分类排序:</td>	<td><input id="sort" name="sort" class="easyui-textbox" style="width:90px;height:24px"  /></td>
      	</tr> 
     	 <tr>
	      	<td  nowrap="nowrap">子分类条件值:</td>	<td><input id="condition" name="condition" class="easyui-textbox" style="width:90px;height:24px" /></td>
	        <td  nowrap="nowrap">子分类条件类型:</td>	<td><input id="conditionType" name="conditionType" class="easyui-combobox" style="width:90px;height:24px" data-options="
                        valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '',
							value: ''
						},{
							label: '小时',
							value: 'hour'
						},{
							label: '天',
							value: 'day'
						},{
							label: '月',
							value: 'month'
						},{
							label: '年',
							value: 'year'
						},{
							label: '次数',
							value: 'count'
						},{
							label: '名称',
							value: 'name'
						}],
	                    panelHeight:'auto'" /></td>
       
        
    	</tr> 
    
	    <tr>
	     	<td>客服新增信用项:</td><td><input class="easyui-combobox"  id="can_add" name="canAdd" style="width:90px;height:24px"   data-options="
                        valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '不是',
							value: '0'
						},{
							label: '是',
							value: '1'
						}],
	                    panelHeight:'auto'">
		    			</td>
	     	<td></td><td></td>
	    </tr>
	    <tr>
	     	<td></td><td nowrap="nowrap"><input type="checkbox"  name="memberComplain" id="memberComplain"  value="1" />会员举报类型</td>
	     	<td colspan="2"><input type="checkbox" name="employeeComplain"  id="employeeComplain" value="1" />员工举报类型</td>
	    </tr>
	    <tr>
	         <td  nowrap="nowrap">会员行为描述:</td>	<td colspan="3"><input id="contriDetail" name="contriDetail" class="easyui-textbox"  data-options="multiline:true"  style="width:280px;height:100px"/></td>
	    </tr> 
    </table>
 	</form>
 </div>
       
  <div id="editBehaviorView" class="easyui-dialog" closed="true" style="padding:10px 20px">
       <form  name="editViewForm" id="editViewForm" method="post" style="width:210px;padding:10px 10px">
       	<input type="hidden" id="mbid" name="behavior_id">
       	<input type="hidden" id="behavior_id" name="behaviorType">
       	 <table cellpadding="5" style=" font-size: 12px;margin-left:10px;margin-top: 0px;">
       	 	<tr><td>分类:</td><td><span id="member_behavior_name"></span></td></tr>
       	 	<tr><td>子分类:</td><td><span id="member_son_behavior_name"></span></td></tr>
       	 	<tr>
		        <td  nowrap="nowrap">贡献值类型：</td> 
		       	<td> <input class="easyui-combobox" style="width:90px;height:24px"  name="isRatio" id="is_ratio" data-options="
		                        valueField:'value',
			                    textField:'label',
			                    data: [{
									label: '比例',
									value: '1'
								},{
									label: '绝对值',
									value: '0'
								}],
			                    panelHeight:'auto',required:true"
		        /></td>
		     </tr>
		     <tr>
		       <td  nowrap="nowrap">加/扣分：</td> 
		        	<td><input class="easyui-combobox" style="width:90px;height:24px" name="isIncrease" id="is_increase" data-options="
		                        valueField:'value',
			                    textField:'label',
			                    data: [{
									label: '扣分',
									value: '0'
								},{
									label: '加分',
									value: '1'
								}],
			                    panelHeight:'auto',required:true"
		        /></td>
		    </tr> 
			<tr>
	           	<td  nowrap="nowrap">贡献值:</td><td ><input id="contri_value" name="contriValue" class="easyui-textbox" style="width:90px;height:24px"  data-options="required:true" /></td>
	        </tr> 
       	 	<tr>
		         <td  nowrap="nowrap">会员行为描述:</td>	<td><input id="contri_detail" name="contriDetail" class="easyui-textbox"  data-options="multiline:true"  style="width:200px;height:100px"/></td>
		    </tr>
       	 </table>
       	</form>
       </div>
  </body>
</html>
