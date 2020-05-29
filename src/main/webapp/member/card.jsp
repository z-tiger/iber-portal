<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>会员账户管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="member/card.js"></script>
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



.fitem a {
	margin-right: 5px;
}
</style>
</head>
<body>

    <table id="grid" toolbar="#toolbar"></table>
     
      <!-- 工具栏  start-->
   <div id="toolbar" style="height:auto">
   		<form action="" id="cardForm">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
		姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
		手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		
		<r:FunctionRole functionRoleId="freeze_amount">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjdj" plain="true" id="btnFrozen" onclick=";">冻结资金</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="un_freeze_amount">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjjd" plain="true" id="btnThaw" onclick=";">解冻资金</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="debt_member">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-contact_blue_remove" plain="true" id="btnDebit" onclick=";">欠款会员</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="export_card">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
		</r:FunctionRole>
 		</form>
   </div>
     <!-- 工具栏  end-->
	<!-- Frozen -->
         <div id="frozenView" class="easyui-dialog" closed="true" style="padding:10px 20px;overflow:hidden;">
			<form id="frozenForm" method="post" action="" onsubmit="checkCustomer(this)">
				<input type="hidden" name="memberId" id="memberId" />
				<input type="hidden" name="accoutStatus" id="accoutStatus"/>
				  <div class="fitem">
		    		<tr>
		    			<td>冻结原因:</td>
		    			<td>
		    			 <input class="easyui-textbox" required="true" name='blockingReason' id="blockingReason" style="width:300px;height:60px"  data-options="multiline:true" />
		    			</td>
		    		</tr>	
		    	 </div>
			</form>
		 </div>
		<!--解冻资金-->
		<div id="unFrozenView" class="easyui-dialog" closed="true" style="padding:10px 20px;overflow:hidden;">
		<form id="unFrozenForm" method="post" action="" onsubmit="checkCustomer(this)">
			<input type="hidden" name="memberId" id="u_memberId" />
			<input type="hidden" name="accoutStatus" id="u_accoutStatus"/>
			<div class="fitem">
				<tr>
					<td>解冻原因:</td>
					<td>
						<input class="easyui-textbox" required="true" name='blockingReason' id="unBlockingReason" style="width:300px;height:60px"  data-options="multiline:true" />
					</td>
				</tr>
			</div>
		</form>
		</div>
</body>
</html>