<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>会员管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="invoice/invoiceManage.js"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>

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
   <form id="queryTimeLease">
		所属城市:
		<input class="easyui-combobox" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
			姓名:<input type="text" name="name" class="easyui-textbox" id="s-name">
			发票抬头:<input type="text" name="invocieHead" class="easyui-textbox" id="s-invocieHead">	
			发票类型:<input class="easyui-combobox" name="invoiceType" id="s-invoiceType"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                    	label: '全部',
	                    	value: ''
	                    },{
							label: '普通电子发票',
							value: '1'
						},{
							label: '普通纸质发票',
							value: '2'
						},{
							label: '专用纸质发票',
							value: '3'
						}]">
			开票状态:<input class="easyui-combobox" name="invoiceStatus" id="s-invoiceStatus"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
							label: '全部',
							value: ''
	                    },{
							label: '待开票',
							value: '1'
	                    },{
							label: '开票中',
							value: '2'
						},{
							label: '已开票',
							value: '3'
						},{
							label: '已驳回',
							value: '5'
						}]">  
			    申请时间:
	       <input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" />
		  到
		  <input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>	
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
		<r:FunctionRole functionRoleId="invoice_edit">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="export_invoice_excel">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
		</r:FunctionRole>
	  </form>		
   </div>

	<div id="getOrderInfoView" class="easyui-dialog" closed="true" style="padding: 18px">
		
		 <div id="toolbar1" style="height:auto">
		 	<input id="s-invoiceId" type="hidden">
		 	订单号:<input type="text" name="orderId" class="easyui-textbox" id="s-orderId">
		 	订单类型:<input class="easyui-combobox" name="orderType" id="s-orderType"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
	                   		label: '全部',
							value: ''
	                	 },{
							label: '时租',
							value: 'TS'
	                    },{
							label: '充电',
							value: 'charging'
	                    },{
							label: '长租',
							value: 'lr'
						}] ">  
		 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQueryOrder" onclick=";">查询</a>
		 </div>
		 <table id="getOrderInfoGrid" toolbar="#toolbars1"></table>
	</div> 

	<div id="editView" class="easyui-dialog" closed="true" style="width: 650px;height: 600px;">
			<form id="editForm" method="post" action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
			<input type="hidden" id='e-id' name="id" >
			<input type="hidden" id='e-mailId' name="mailId" >
				<table cellpadding="5"   style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
						<tr height="32px;">
			    			<td nowrap="nowrap">会员名称:</td>
			    			<td nowrap="nowrap">
			    				 <span name='memberName' id="e-memberName"></span>
			    			</td>
							<td nowrap="nowrap">会员手机:</td>
			    			<td nowrap="nowrap">
			    				<span name='memberPhone' id="e-memberPhone"></span>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td colspan="4"></td>
			    		</tr>
			    		<tr>
			    			<td colspan="4"><b>发票信息:</b></td>
			    		</tr>
			    		
						<tr height="32px;">
			    			<td nowrap="nowrap">发票类型:</td>
			    			<td nowrap="nowrap" colspan="3">
			    				 	<input class="easyui-combobox" name="personType" id="e-personType" style="width:80px;height:24px"
										data-options="valueField:'value',
						                    textField:'label',
						                    panelHeight:'auto',
						                    data: [{
												label: '个人',
												value: '1'
											},{
												label: '企业',
												value: '2'
											}]">
			    				 <input class="easyui-combobox" name="invoiceType" id="e-invoiceType" style="width:100px;height:24px"
										data-options="valueField:'value',
						                    textField:'label',
						                    panelHeight:'auto',
						                    data: [{
												label: '普通电子发票',
												value: '1'
											},{
												label: '普通纸质发票',
												value: '2'
											},{
												label: '专用纸质发票',
												value: '3'
											}]">
			    			</td>
			    		</tr>
			    		
						<tr height="32px;">
							<td nowrap="nowrap">发票抬头:</td>
			    			<td nowrap="nowrap">
			    				<input class="easyui-textbox"  id="e-invocieHead" name="invocieHead" style="width:180px;height:24px" data-options="required:true,missingMessage:'发票抬头不能为空'">
			    			</td>
							<td nowrap="nowrap"></td>
			    			<td nowrap="nowrap"> </td>
			    		</tr>
			    		<tr  height="32px;">
							<td nowrap="nowrap">发票内容:</td>
			    			<td nowrap="nowrap">
				    			 <input class="easyui-combobox" name="serverType" id="e-serverType" style="width:180px;height:24px"
											data-options="valueField:'value',
							                    textField:'label',
							                    panelHeight:'auto',
							                    data: [{
													label: '汽车租赁费',
													value: '1'
												},{
													label: '充电服务费',
													value: '2'
												},{
													label: '汽车租赁+充电服务费',
													value: '3'
												}]">
			    			</td>
			    			<td nowrap="nowrap"></td>
			    			<td nowrap="nowrap">
			    			</td>
			    		</tr>
			    		<tr  height="32px;">
			    			<td nowrap="nowrap">发票号:</td>
			    			<td nowrap="nowrap">
			    			<div  class="car_rent_invoice">
			    				<input class="easyui-textbox"  id="e-invoiceNo" name="invoiceNo" style="width:180px;height:24px" >
			    			</div>
			    			</td>
			    			<td nowrap="nowrap"><!-- 充电服务发票号: --></td>
			    			<td nowrap="nowrap"  >
			    			<!-- <div class="charging_invoice">
			    				<input class="easyui-textbox"  id="e-invoiceNo2" name="invoiceNo2" style="width:180px;height:24px" >
			    			</div> -->
			    			</td>
			    		</tr>
			    		
						<tr height="32px;">
			    			<td nowrap="nowrap">发票金额:</td>
			    			<td nowrap="nowrap">
			    				<input class="easyui-textbox"  id="e-money" name="money" style="width:180px;height:24px" disabled="disabled">
			    			</td>
							<td nowrap="nowrap">邮费:</td>
			    			<td nowrap="nowrap">
			    					<span name='postage' id="e-postage"></span>
			    			</td>
			    		</tr>
						
		    		<tr>
		    			<td colspan="4"></td>
		    		</tr>
		    		<tr class="ele_invoice_info">
		    			<td colspan="4"><b>收件信息:</b></td>
		    		</tr>
		    		<tr height="32px;"  class="ele_invoice_info  car_ele_invoice">
			    		<td nowrap="nowrap">电子发票:</td>
		    			<td nowrap="nowrap">
		    				<input  name="boxfile" type="file"	 style="width:180px;height:24px" />
		    			</td>
		    		</tr>
		    		<tr height="32px;"  class="ele_invoice_info">
		    			<td nowrap="nowrap" colspan="4">
		    				<a id = "e-elecInvoiceUrl" href="" target="_blank" ></a>
		    			</td>
		    		</tr>
		    		
				<!-- 	<tr height="32px;" class="ele_invoice_info charging_ele_invoice">
		    			<td nowrap="nowrap">充电服务电子发票:</td>
		    			<td nowrap="nowrap">
		    				<input  name="boxfile2" type="file"	style="width:180px;height:24px" />
		    			</td>
		    		</tr>
		    		<tr height="32px;"  class="ele_invoice_info">
		    			<td nowrap="nowrap" colspan="4">
		    				<a id = "e-elecInvoiceUrl2" href="" target="_blank" ></a>
		    			</td>
		    		</tr> -->
		    		
		    		<tr class="paper_invoice_info">
		    			<td colspan="4"><b>收件信息:</b></td>
		    		</tr>
						
			    	<tr height="32px;" class="paper_invoice_info">
		    			<td nowrap="nowrap">所在地区：</td>
		    			<td nowrap="nowrap" colspan="2">
						     <input class="easyui-textbox"  id="e-province" name="province" style="width:80px;height:24px">
						     <input class="easyui-textbox"  id="e-city" name="city" style="width:80px;height:24px">
						     <input class="easyui-textbox"  id="e-area" name="area" style="width:80px;height:24px">
		    			</td>
		    		</tr>
					<tr height="32px;" class="paper_invoice_info">
			    			<td nowrap="nowrap">收件地址:</td>
			    			<td nowrap="nowrap" colspan="3">
			    				<input class="easyui-textbox"  id="e-address" name="address" style="width:380px;height:24px" >
			    			</td>
			    	</tr>
			    		
		    		<tr height="32px;" class="paper_invoice_info">
		    			<td nowrap="nowrap">收件人:</td>
		    			<td nowrap="nowrap">
		    				<input class="easyui-textbox"  id="e-receiver" name="receiver" style="width:180px;height:24px" >
		    			</td>
						<td nowrap="nowrap">收件电话:</td>
		    			<td nowrap="nowrap">
							<input class="easyui-textbox"  id="e-receiverPhone" name="receiverPhone" style="width:180px;height:24px" >
		    			</td>
		    		</tr>
		    		<tr class="paper_invoice_info">
		    			<td colspan="4"></td>
		    		</tr>
		    		<tr class="paper_invoice_info">
		    			<td colspan="4"><b>快递信息:</b></td>
		    		</tr class="paper_invoice_info">
		    		<tr height="32px;" class="paper_invoice_info">
		    			<td nowrap="nowrap">快递号:</td>
		    			<td nowrap="nowrap">
		    				<input class="easyui-textbox"  id="e-fastMailNo" name="fastMailNo" style="width:180px;height:24px" >
		    			</td>
						<td nowrap="nowrap">快递公司:</td>
		    			<td nowrap="nowrap">
							<input class="easyui-textbox"  id="e-fastMailCompany" name="fastMailCompany" style="width:180px;height:24px" >
		    			</td>
		    		</tr>
			    </table>
			 </form>
	</div>
	<div id="eleEditView" class="easyui-dialog" closed="true" style="width: 600px;height: 300px;">
			<form id="eleEditForm" method="post" action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
			<input type="hidden" id='ee-id' name="id" >
			<input type="hidden" id='ee-serverType'>
				<table cellpadding="5"   style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
						<tr height="32px;">
							<td nowrap="nowrap">所属城市:</td>
			    			<td nowrap="nowrap">
			    				<span name='cityName' id="ee-cityName"></span>
			    			</td>
						</tr>
						<tr height="32px;">
							<td nowrap="nowrap">会员姓名:</td>
			    			<td nowrap="nowrap">
			    				<span name='memberName' id="ee-memberName"></span>
			    			</td>
						</tr>
						<tr height="32px;">
							<td nowrap="nowrap">会员手机:</td>
			    			<td nowrap="nowrap">
			    				<span name='memberPhone' id="ee-memberPhone"></span>
			    			</td>
						</tr>
						<tr height="32px;" class="car_rent_invoice">
			    			<td nowrap="nowrap">发票号:</td>
			    			<td nowrap="nowrap">
			    			 	<input class="easyui-textbox"  id="ee-invoiceNo" name="invoiceNo" style="width:220px;height:24px">
			    			</td>
			    		</tr>
						<!-- <tr height="32px;" class="charging_invoice">
			    			<td nowrap="nowrap">充电服务发票号:</td>
			    			<td nowrap="nowrap">
			    			 	<input class="easyui-textbox"  id="ee-invoiceNo2" name="invoiceNo2" style="width:220px;height:24px">
			    			</td>
			    		</tr> -->
						<tr height="32px;"  class="car_rent_invoice">
			    			<td nowrap="nowrap">电子发票:</td>
			    			<td nowrap="nowrap">
			    				<input  name="boxfile" type="file"	 />
			    			</td>
			    		</tr>
			    </table>
			 </form>
	</div>
	<div id="paperEditView" class="easyui-dialog" closed="true" style="width: 460px;height: 300px;">
			<form id="paperEditForm" method="post" action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
			<input type="hidden" id='p-id' name="id" >
			<input type="hidden" id='p-serverType' >
				<table cellpadding="5"   style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
						<tr height="32px;">
							<td nowrap="nowrap">所属城市:</td>
			    			<td nowrap="nowrap">
			    				<span name='cityName' id="p-cityName"></span>
			    			</td>
						</tr>
						<tr height="32px;">
							<td nowrap="nowrap">会员姓名:</td>
			    			<td nowrap="nowrap">
			    				<span name='memberName' id="p-memberName"></span>
			    			</td>
						</tr>
						<tr height="32px;">
							<td nowrap="nowrap">会员手机:</td>
			    			<td nowrap="nowrap">
			    				<span name='memberPhone' id="p-memberPhone"></span>
			    			</td>
						</tr>
						<tr height="32px;" class="car_rent_invoice">
			    			<td nowrap="nowrap">发票号:</td>
			    			<td nowrap="nowrap">
			    				 <input class="easyui-textbox"  id="p-invoiceNo" name="invoiceNo" style="width:280px;height:24px">
			    			</td>
			    		</tr>
						<!-- <tr height="32px;" class="charging_invoice">
			    			<td nowrap="nowrap">充电服务发票号:</td>
			    			<td nowrap="nowrap">
			    				 <input class="easyui-textbox"  id="p-invoiceNo2" name="invoiceNo2" style="width:280px;height:24px">
			    			</td>
			    		</tr> -->
						<tr height="32px;">
			    			<td nowrap="nowrap">快递单号:</td>
			    			<td nowrap="nowrap">
			    			 	<input class="easyui-textbox"  id="p-fastMailNo" name="fastMailNo" style="width:280px;height:24px" data-options="required:true,missingMessage:'快递单号不能为空'">
			    			</td>
			    		</tr>
						<tr height="32px;">
			    			<td nowrap="nowrap">快递公司:</td>
			    			<td nowrap="nowrap">
			    			 	<input class="easyui-textbox"  id="p-fastMailCompany" name="fastMailCompany" style="width:280px;height:24px" data-options="required:true,missingMessage:'快递公司不能为空'">
			    			</td>
			    		</tr>
			    </table>
			 </form>
	</div>

	<div id="refuseEditView" class="easyui-dialog" closed="true" >
		<form id="refuseEditForm" method="post"  action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
		<input type="hidden" id="refuse-id" name="id">
		<table cellpadding="5"  style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
			<tr height="82px;">
				<td nowrap="nowrap" width="15%">驳回原因：</td>
				<td nowrap="nowrap" width="85%"><input class="easyui-textbox" id="refuse-remark" name="remark" data-options="multiline:true,required:true,missingMessage:'驳回原因不能为空'" style="height:60px;width:300px" /> </td>
			</tr>
		</table>
		</form>
	</div>
	<div id="refuseDetailsView" class="easyui-dialog" closed="true" >
		<form id="refuseDetailsForm" method="post"  action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
		<input type="hidden" id="refuse-id" name="id">
		<table cellpadding="5"  style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
			<tr height="82px;">
				<td nowrap="nowrap" width="15%">驳回原因：</td>
				<td nowrap="nowrap" width="85%"><input class="easyui-textbox" id="d-refuse-remark" name="remark" data-options="multiline:true,required:true,missingMessage:'驳回原因不能为空'" style="height:60px;width:300px" /> </td>
			</tr>
		</table>
		</form>
	</div>

<div id="lookEditView" class="easyui-dialog" closed="true" style="width: 650px;height: 600px;">
			<form id="lookEditForm" method="post" action=""	enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<table cellpadding="5"   style="font-size: 12px;margin-left:50px;margin-top: 10px;" >
					<tr height="32px;">
		    			<td nowrap="nowrap" width="15%">会员名称:</td>
		    			<td nowrap="nowrap" width="35%"> <span  id="l-memberName"></span> </td>
						<td nowrap="nowrap" width="15%">会员手机:</td>
		    			<td nowrap="nowrap" width="35%"> <span  id="l-memberPhone"></span></td>
		    		</tr>
		    		<tr>
		    			<td colspan="4"></td>
		    		</tr>
		    		<tr>
		    			<td colspan="4"><b>发票信息:</b></td>
		    		</tr>
		    		
					<tr height="32px;">
		    			<td nowrap="nowrap">发票类型:</td>
		    			<td nowrap="nowrap" colspan="3"> <span id="l-personType" ></span>&nbsp;&nbsp;&nbsp;&nbsp;<span id="l-invoiceType" ></span>	</td>
		    		</tr>
		    		
					<tr height="32px;">
						<td nowrap="nowrap">发票抬头:</td>
		    			<td nowrap="nowrap"> <span  id="l-invocieHead"></span> </td>
						<td nowrap="nowrap">发票内容:</td>
		    			<td nowrap="nowrap"> <span  id="l-serverType"></span> </td>
		    		</tr>
		    		
					<tr height="32px;">
		    			<td nowrap="nowrap">纳锐人识别号:</td>
		    			<td nowrap="nowrap"> <span  id="l-taxpayerCode"></span> </td>
						<td nowrap="nowrap">发票电话地址:</td>
		    			<td nowrap="nowrap"> <span  id="l-invoiceAddress"></span> </td>
		    		</tr>
					<tr height="32px;">
		    			<td nowrap="nowrap">开户行及账号:</td>
		    			<td nowrap="nowrap"> <span  id="l-bankDetail"></span> </td>
		    			<td nowrap="nowrap">发票号:</td>
		    			<td nowrap="nowrap"> <span  id="l-invoiceNo"></span> </td>
		    		</tr>
					<tr height="32px;">
		    			<td nowrap="nowrap">发票金额:</td>
		    			<td nowrap="nowrap"> <span  id="l-money"></span> </td>
						<td nowrap="nowrap">邮费:</td>
		    			<td nowrap="nowrap"> <span  id="l-postage"></span> </td>
		    		</tr>
					<tr height="32px;">
						<td nowrap="nowrap">汽车租赁费:</td>
		    			<td nowrap="nowrap"> <span  id="l-orderMoney"></span> </td>
		    			<td nowrap="nowrap">充电服务费:</td>
		    			<td nowrap="nowrap"> <span  id="l-chargingMoney"></span> </td>
		    		</tr>
		    		<tr>
		    			<td colspan="4"></td>
		    		</tr>
		    		<tr class="ele_invoice_info">
		    			<td colspan="4"  class="ele_invoice_info"><b>电子发票：</b></td>
		    		</tr>
		    		<tr height="32px;"  class="ele_invoice_info">
		    			<td nowrap="nowrap" colspan="4"> &nbsp;&nbsp;&nbsp;&nbsp;<a id = "l-elecInvoiceUrl" href="" target="_blank" ></a> </td>
		    		</tr>
		    		<!-- <tr height="32px;"  class="ele_invoice_info">
		    			<td nowrap="nowrap" colspan="4"> &nbsp;&nbsp;&nbsp;&nbsp;<a id = "l-elecInvoiceUrl2" href="" target="_blank" ></a> </td>
		    		</tr> -->
					
		    		<tr class="paper_invoice_info">
		    			<td colspan="4"><b>收件信息:</b></td>
		    		</tr>
		    		
			    	<tr height="32px;" class="paper_invoice_info">
		    			<td nowrap="nowrap">所在地区：</td>
		    			<td nowrap="nowrap" colspan="2"> <span id="l-province"></span> <span id="l-city"></span> <span id="l-area"></span> </td>
		    		</tr>
					<tr height="32px;" class="paper_invoice_info">
			    			<td nowrap="nowrap">收件地址:</td>
			    			<td nowrap="nowrap" colspan="3"> <span id="l-address"></span> </td>
			    	</tr>
			    		
		    		<tr height="32px;" class="paper_invoice_info">
		    			<td nowrap="nowrap">收件人:</td>
		    			<td nowrap="nowrap"> <span  id="l-receiver"></span> </td>
						<td nowrap="nowrap">收件电话:</td>
		    			<td nowrap="nowrap"> <span id="l-receiverPhone"></span> </td>
		    		</tr>
		    		<tr class="paper_invoice_info">
		    			<td colspan="4"></td>
		    		</tr>
		    		<tr class="paper_invoice_info">
		    			<td colspan="4"><b>快递信息:</b></td>
		    		</tr class="paper_invoice_info">
		    		<tr height="32px;" class="paper_invoice_info">
		    			<td nowrap="nowrap">快递号:</td>
		    			<td nowrap="nowrap"> <span id="l-fastMailNo"></span> </td>
						<td nowrap="nowrap">快递公司:</td>
		    			<td nowrap="nowrap"> <span id="l-fastMailCompany" ></span> </td>
		    		</tr>
			    </table>
			 </form>
	</div>
</body>
</html>