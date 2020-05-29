<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>优惠券发放记录</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="coupon/CouponLog.js"></script>
	
	
</head>
	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
				<!-- 优惠券编号:<input id="_couponno"  name="_couponno" class="easyui-textbox" style="width:80px"/>
				会员ID:<input id="_memberid"  name="_memberid" class="easyui-textbox" style="width:80px"/> -->
				批次号:<input id="_batchno"  name="_batchno" class="easyui-textbox" style="width:120px"/>
				状态:<input class="easyui-combobox" name="status" id="e-status"
					data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '失败',
							value: '0'
						},{
							label: '成功',
							value: '1'
						}],
	                    panelHeight:'auto'">
				会员姓名:<input id="_memberName"  name="_memberName" class="easyui-textbox" style="width:120px"/>
				会员手机:<input id="_memberPhone"  name="_memberPhone" class="easyui-textbox" style="width:120px"/>
				<!-- <input id="_status"  name="_status" class="easyui-textbox" style="width:80px"/> -->
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	            <r:FunctionRole functionRoleId="resend_coupon">
            		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="sendEdit">重新发送</a>
            	</r:FunctionRole>
	            <!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
		        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a> -->
         </div>
         <!-- add -->
         <div id="view" class="easyui-dialog" closed="true">
			<form id="viewForm" name="viewForm" method="post">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
						<tr>
			    			<td>优惠券:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="couponno" name="couponno" style="width:100%;height:24px" data-options="required:true,missingMessage:'优惠券不能为空'">
			    			</td>
			    		</tr>
		    		
						<tr>
			    			<td>会员ID:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="memberid" name="memberid" style="width:100%;height:24px" data-options="required:true,missingMessage:'会员ID不能为空'">
			    			</td>
			    		</tr>
		    		
						<tr>
			    			<td>状态:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="status" name="status" style="width:100%;height:24px" data-options="required:true,missingMessage:'状态 0不启用 1启用不能为空'">
			    			</td>
			    		</tr>
		    		
						<tr>
			    			<td>批次号:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="batchno" name="batchno" style="width:100%;height:24px" data-options="required:true,missingMessage:'批次号不能为空'">
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>创建人:</td>
			    			<td>
			    			 	<input class="easyui-textbox"  id="createid" name="createid" style="width:100%;height:24px" data-options="required:true,missingMessage:'不能为空'">
			    			</td>
			    		</tr>	
					</table>
			</form>
		 </div>
	</body>
</html>