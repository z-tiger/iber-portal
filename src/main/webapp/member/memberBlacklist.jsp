<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>黑名单管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="member/memberBlacklist.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	    	<form action="" id="blacklistForm">
 	    		所属城市:<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
				姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
				手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
	      		操作时间:
	       			<input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" />
		  			--
		  			<input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <r:FunctionRole functionRoleId="cancel_blacklist">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-zjjd" plain="true" id="btnCancelBlacklist">撤销黑名单</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="export_blacklist">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
				</r:FunctionRole>
	     		</form>
	    </div>
		 
		 <!-- 撤销黑名单 -->
		 <div id="editView" class="easyui-dialog" closed="true" style="padding:10px 20px">
       	  <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
       	   <div id="toolbar" style="height:auto">
				行为名称:<input type="text" name="behaviourName" class="easyui-textbox" id="s-behaviourName">
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnContributeValQuery">查询</a>
	       </div>
       	   <table id="cancelBlecklistDatagrid" style="width: 80%;height:80%;"></table>
           <input type="hidden" name="memberId" id="e-memberId">
           <input type="hidden" name="id" id="e-id">
           <input type="hidden" name="contributeDetailId" id="e-contributeDetailId">
           <input type="hidden" name="contributeVal" id="e-contributeVal">
		   <div class="fitem">
				<label>请输入撤销黑名单的原因:</label><br>
				<input  name="reason" id="e-reason" class="easyui-textbox" style="width: 80%;height:80%;" data-options="multiline:true" required="true"  missingMessage="请填写撤销黑名单的原因"/>
		   </div>
		   <div class="fitem">
				<tr>
        			<td style="text-align:right;">是否误判：</td>
        			<td style="text-align:left">
            			<span>
               				 <input type="radio" name="isError" value="0" id="error_redio">否</input>
               				 <input type="radio" name="isError" value="1">是</input>
            			</span>
        			</td>
   				 </tr>
		   </div>

       </form>
   </div>
	</body>
</html>