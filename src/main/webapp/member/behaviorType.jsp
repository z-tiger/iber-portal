<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>

<html>
  <head>
   <meta charset="UTF-8">
    <title>会员行为类型管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="version/common.js"></script>
<script type="text/javascript" src="member/behaviorType.js"></script>
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
  	<body>
      <table id="grid" toolbar="#toolbar"></table>
       
       <div id="toolbar" style="height:auto">
       <r:FunctionRole functionRoleId="add_member_behaviorType">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addBehaviorType" >添加</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_member_behaviorType">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="updateBehaviorType" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_member_behaviorType">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="deleteBehaviorType" onclick=";">删除</a>
		</r:FunctionRole>
      </div>
      <div id="addBehaviorTypeView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addBehaviorTypeForm" name="addBehaviorTypeForm" method="post" action=""enctype="multipart/form-data" >
				 <input type="hidden" id="id" name="behaviorType_id">
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td>行为分类名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="behaviorTypeName" name="behaviorTypeName" style="width:200px;height:24px" data-options="required:true">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>举报项:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="complain" name="complain" style="width:110px;height:24px" data-options="
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
		    		</tr>
		    		<tr>
		    			<td>客服新增信用项:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="can_add" name="canAdd" style="width:110px;height:24px" data-options="
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
		    		</tr>
		    		<tr id="show_complain_type" >
		    			<td>举报类型:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="complain_type" name="complainType" style="width:110px;height:24px" data-options="
                        valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '会员和员工',
							value: '0'
						},{
							label: '会员',
							value: '1'
						},{
							label: '员工',
							value: '2'
						}],
	                    panelHeight:'auto'">
		    			
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>行为类型:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="behaviorType" name="behaviorType" style="width:110px;height:24px" data-options="
                        valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '普通',
							value: '0'
						},{
							label: '道路救援',
							value: '1'
						},{
							label: '严重行为',
							value: '2'
						}],
	                    panelHeight:'auto'">
		    			
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>行为分类描述:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="behaviorDetail" name="behaviorDetail" data-options="multiline:true" style="width:300px;height:100px" >
		    			</td>
		    		</tr>  
		    	</table> 
			</form>
			 </div>
  </body>
  </html>