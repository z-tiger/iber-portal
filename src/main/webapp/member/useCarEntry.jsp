<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员违章结案</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="member/useCarEntry.js"></script>
<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>

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
	
	.fundSettlementWorkflowTable{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	
	}
	
	.fundSettlementWorkflowTable td{
	  border-left: 1px solid #E0E0E0;
	  border-top: 1px solid #E0E0E0;
	}
	</style>
</head>
<body>

  <table id="usrCarEntryListTable" toolbar="#toolbar"></table>
  
  <div id="toolbar" style="height:auto">
          <label for="areaCode">所属城市:</label>
	      <input class="easyui-combobox" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
      
	      <label for="custName">会员姓名 </label>
	      <input class="easyui-textbox" type="text" name="custName" id='s-custName' />
	      
	      <label for="custPhone">会员手机号 </label>
	      <input class="easyui-textbox" type="text" name="custPhone" id='s-custPhone'/>
	      
	      <label for="lpn">车牌号码</label>
	      <input class="easyui-textbox" type="text" name="lpn"  id='s-lpn'/>
	      <label for="lpn">订单号</label>
	      <input class="easyui-textbox" type="text" name="orderId"  id='s-orderId'/>
	       <br/>
	       <label for="begData">用车时间</label>
	       <input id="s-bt" name="begData" class="easyui-datetimebox s" size="18" />
		  <label class="endDate" style="text-align: center;">至</label>
		  <input id="s-et" name="endDate" class="easyui-datetimebox e" size="18"/>	
	      
          <label for="illegal">是否违章</label>
	      <input class="easyui-combobox" name="is_traffic_citation" id="s-isTrafficCitation"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
							label: '是',
							value: '1'
						},{
							label: '否',
							value: '0'
						}]">
      
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
        <r:FunctionRole functionRoleId="audit_member_wz">
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_edit" plain="true" id="btnWorder">审核</a>
        </r:FunctionRole>
  </div>
  
  
     <!-- 审核dialog -->
  <div id="useCarEntryWorderDialog" class="easyui-dialog" closed="true">
      
      <div class="easyui-panel" title="订单明细" style="width:100%;height: 150	;">
         <table class="fundSettlementWorkflowTable" width="100%" cellpadding="2">
            <tr>
                <td align="right">所属城市:</td>
                <td id="a_cityCodeName">&nbsp;</td>
                <td align="right">订单号:</td>
                <td id="a_orderId">&nbsp;</td>
            </tr>
            <tr>
                <td align="right">车牌号:</td>
                <td id='a_lpn'>&nbsp;</td>
                <td align="right">消费金额:</td>
                <td id='a_payMoney'>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">会员姓名:</td>
                <td id='a_custName'>&nbsp;</td>
                <td align="right">会员手机:</td>
                <td id='a_custPhone'>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">用车开始时间:</td> 
                <td id='a_beginTime'>&nbsp;</td>
                <td align="right">用车结束时间:</td>
                <td id='a_endTime'>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">用车时长(分钟):</td>
                <td id='a_totalMinute'>&nbsp;</td>
                <td align="right">行驶里程(公里):</td>
                <td id='a_totalMileage'>&nbsp;</td>
            </tr>
         </table>
     </div>
     	<table id="wzOrderRecord"></table>
	     <div class="easyui-panel" title="是否违章" style="width:100%;height: 60;">
	         <div align="center">
		        <label style="margin-right: 20px; ">是否违章:</label>
		        <input type="radio" name='isTrafficCitation' id='is_traffic_citation_1' value="1">
		        <label for="is_traffic_citation_1">是</label>
		        <input type="radio" name='isTrafficCitation' id='is_traffic_citation_2' value="0" checked="checked">
		        <label for="is_traffic_citation_2">否</label>
	         </div>
	     </div>
     	
	     <div id="useCarEntryAudit" style="display: none;" >
	      <div id="useCarEntryAuditPanel" class="easyui-dialog" title="录入违章事故" style="width:100%;height:330;" closed="true">
	      		<form id="useCarEntryAuditForm" name='useCarEntryAuditForm' method="post" enctype="multipart/form-data">
	      		 <input type="hidden" name="myOrderId" id="a_myOrderId">
	      		 <input type="hidden" name="hiddenIsTrafficCitation">
	      		 <input type="hidden" name="hiddenLpn">
	             <table class='fundSettlementWorkflowTable' id="wzFeeByCompany" width="100%" cellpadding="2">
	                	<tr>
	                		<label style="margin-right: 20px; ">请选择违章费用支付方:</label>
						    <input type="radio"   name='isFeeByCompany' id='wzFee_1' value="1">	
						    <label for="wzFee_1">违章费用由会员支付</label>
						    <input type="radio" name='isFeeByCompany' id='wzFee_2' value="2" checked="checked">
						    <label for="wzFee_2">违章费用由宜步出行支付</label>
						</tr>   
						
						<!-- 如果选择违章费用由宜步出行支付，则显示这个面板 -->
							<tr>   
								<td align="right">违章费用:</td>      
								<td>
									<input rows="1" cols="25" name="wzFee" id='u-wzFee'/>
								</td>
							</tr>
							<tr>
								<td align="right">违章分数:</td>      
								<td> 
									<input rows="1" cols="25" name='wzPoint' id='u-wzPoint'/>
								</td>
							</tr>
							<tr>
								<td align="right">买分总费用:</td> 
								<td>
									<input rows="1" cols="25" name='buyPointFee' id='u-buyPointFee'/>
								</td>
							</tr>
							<tr>
								<td align="right">处理违章总费用:</td> 
								<td>
									<input rows="1" cols="25" name='totalWzFee' id='u-totalWzFee'/>
								</td>
		                	</tr>
		                	
				</table>
					
				<table class="fundSettlementWorkflowTable" width="100%" cellpadding="2">
					<tr id="detial">
			              <td align="right" style="padding-left: 100px">详情:</td>
			              <td>
			                  <textarea rows="4" name="trafficContent" id='u-trafficContent' style="width: 75%;"></textarea>
			              </td>
		          	</tr>                
		            <tr id="attachment">
		                  <td align="right">附件:</td>
			              <td> 
			                  <input type="file" name='uploadFile' id='u-uploadFile'>
			              </td>
		            </tr>
				</table>
			   </form>
	      </div>
	     </div>
     
   </div>  
  
  
   <div id="useCarEntryWorderLookDialog" class="easyui-dialog" closed="true">
   
     <div class="easyui-panel" title="订单明细" style="width:100%;height: 250;">
         <table class="fundSettlementWorkflowTable" width="100%" cellpadding="2">
            <tr>
                <td align="right">所属城市:</td>
                <td id="l_cityCodeName">&nbsp;</td>
                <td align="right">订单号:</td>
                <td id="l_orderId">&nbsp;</td>
            </tr>
            <tr>
                <td align="right">车牌号:</td>
                <td id='l_lpn'>&nbsp;</td>
                <td align="right">消费金额:</td>
                <td id='l_payMoney'>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">会员姓名:</td>
                <td id='l_custName'>&nbsp;</td>
                <td align="right">会员手机:</td>
                <td id='l_custPhone'>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">用车开始时间:</td>
                <td id='l_beginTime'>&nbsp;</td>
                <td align="right">用车结束时间:</td>
                <td id='l_endTime'>&nbsp;</td>
            </tr>
            <tr>
                <td align="right">用车时长:</td>
                <td id='l_totalMinute'>&nbsp;</td>
                <td align="right">行驶里程:</td>
                <td id='l_totalMileage'>&nbsp;</td>
            </tr>
            
         </table>
     </div>
     <div class="easyui-panel" title="是否违章" style="width:100%;height: 60;">
         <table class="fundSettlementWorkflowTable" width="100%" cellpadding="2">
             <tr>
                <td align="center">是否违章:&nbsp;&nbsp;<span id='l_isTrafficCitation'></span></td>
             </tr>
         </table>
     </div> 
     
     <!-- <div class="easyui-panel" title="录入违章事故" style="width:100%;height: 150;">
          <table class="fundSettlementWorkflowTable"  width="100%" cellpadding="2">
                <tr>
                   	<label><input name="wzFee" type="radio" value="1" />违章费用由会员支付 </label> 
					<label><input name="wzFee" type="radio" value="2" />违章费用由宜步出行支付 </label> 
                </tr>
                <tr>
                  <td align="right" nowrap="nowrap">详情:</td>
                  <td id='l_tcContent'>&nbsp;</td>
                </tr>
                
                  <tr>
                   <td align="right" nowrap="nowrap">附件:</td>
                   <td id='l_auditorAccessoryFilename'>&nbsp;</td>
                </tr>
             </table>
     </div>   
    -->
   </div>
  
  
</body>
</html>