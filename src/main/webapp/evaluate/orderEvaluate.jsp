<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>用车订单评价</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="evaluate/common.js"></script>	
	<script type="text/javascript" src="evaluate/orderEvaluate.js"></script>
	
	<style type="text/css" >
	#conversionForm {
		margin: 0;
		padding: 10px 30px;
	}
	
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
   
	<table id="orderEvaluateGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
		 	<c:choose>
				<c:otherwise>
				 <label class="label">类型：&nbsp;</label>
				  <input class="easyui-combobox" name="evaluateType" id="evaluateType"  data-options="valueField:'code',textField:'name',url:'sys_dic?dicCode=EVALUATE_TYPE',panelHeight:'80'"> 
				</c:otherwise>
			</c:choose>
		 	<c:choose>
			<c:otherwise>
					<label class="label">订单号:&nbsp;</label>
					<input class="easyui-textbox"" name="orderId" id="orderId" style="width: 120px;">
				</c:otherwise>
			</c:choose>
		 	<c:choose>
			<c:otherwise>
					<label class="label">会员:&nbsp;</label>
					<input class="easyui-textbox"" name="name" id="name" style="width: 120px;">
				</c:otherwise>
			</c:choose>
		 	<c:choose>
			<c:otherwise>
					<label class="label">手机号:&nbsp;</label>
					<input class="easyui-textbox"" name="phone" id="phone" style="width: 120px;">
				</c:otherwise>
			</c:choose>			
			<c:choose>
				<c:otherwise>
				  <label class="label" style="text-align: right;">星级:&nbsp;</label>
				  <input class="easyui-combobox" name="evaluateStar" id="evaluateStar"  data-options="valueField:'code',textField:'name',url:'sys_dic?dicCode=EVALUATE_SCORE',panelHeight:'100',panelWidth:'80'" style="width: 80px;" > 
				</c:otherwise>
			</c:choose>	
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="audit_user_order_assess">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_okay" plain="true" id="btnCheck">审核</a>
			</r:FunctionRole>
			
		</div>
 	
 	 <!-- view -->
  	<div id="checkView" class="easyui-dialog" closed="true"
		style="padding:10px 20px">
       <form name="viewForm" id="viewForm" method="post" >
       		<input type="hidden" name='id' id="e-id">
            <div class="fitem">
				<label>审核状态:</label><input class="easyui-combobox" name="checkStatus" id="e-checkStatus" 
					data-options=" url:'sys_dic?dicCode=EVALUATE_CHECK_STATUS',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto',editable:false,required:true,missingMessage:'请选择审核结果'"/>
		   </div>
           <div class="fitem">
				<label>审核描述:</label><input class="easyui-textbox" id="e-checkReason" name="checkReason" data-options="multiline:true" style="width:75%;height:60px" ></input>
		   </div>
       </form>
   </div> 
	
</body>
</html>