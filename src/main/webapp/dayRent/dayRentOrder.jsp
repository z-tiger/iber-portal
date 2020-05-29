<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>日租价格管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/datagrid-detailview.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var memberName = $.trim($("#s-memberName").val()) ;
				var cityCode = $('#s-cityCode').textbox('getValue') ;
				var lpn = $('#s-lpn').textbox('getValue');
				var phone = $('#s-phone').textbox('getValue');
				var orderStatus = $('#s-orderStatus').textbox('getValue');
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
						memberName : memberName ,
						cityCode :cityCode,
						lpn : lpn,
						phone : phone,
						orderStatus : orderStatus
					}) ; 
				}
			});
			 
			//查询链接
			$("#btnQuery").bind("click",function(){
				var memberName = $.trim($("#s-memberName").val()) ;
				var cityCode = $('#s-cityCode').textbox('getValue') ;
				var lpn = $('#s-lpn').textbox('getValue');
				var phone = $('#s-phone').textbox('getValue');
				var orderStatus = $('#s-orderStatus').textbox('getValue');
				$('#dataGrid').datagrid('load',{
					memberName : memberName ,
					cityCode :cityCode,
					lpn : lpn,
					phone : phone,
					orderStatus : orderStatus
				}) ;  
			});
			
			
			$('#dataGrid').datagrid( {
				title : '日租订单列表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dayRent_order_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox:true
				},{
					field : 'cityName',
					title : '所属地区',
					width : $(this).width() * 0.05,
					align : 'center'
				},{
					field : 'orderId',
					title : '订单号',
					width : $(this).width() * 0.2,
					align : 'center',
				}, {
					field : 'memberName',
					title : '会员姓名',
					width : $(this).width() * 0.1,
					align : 'center'
				}, {
					field : 'phone',
					title : '手机号',
					width : $(this).width() * 0.1,
					align : 'center'
				},
				{
					field : 'branceName',
					title : '车型',
					width : $(this).width() * 0.1,
					align : 'center'
				},
				{
					field : 'lpn',
					title : '车牌号',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val) {
						if (val != "" && val.indexOf("•") < 0) {
							return val.substring(0,2) + "•" + val.substring(2);
						}else {
							return val;
						}
				}
				}, {
					field : 'appointmenTakeCarPark',
					title : '预约取车地点',
					width : $(this).width() * 0.2,
					align : 'center'
				}, {
					field : 'appointmenTakeCarTime',
					title : '预约取车时间',
					width : $(this).width() * 0.2,
					align : 'center'
				}, {
					field : 'payStatus',
					title : '支付状态',
					width : $(this).width() * 0.05,
					align : 'center',
					formatter : function(val, rec) {
						if(val == 'noPay'){
							return '未支付' ;
						}else if(val == 'finish'){
							return '已支付' ;
						}
					}
				}, {
					field : 'orderStatus',
					title : '订单状态',
					width : $(this).width() * 0.05,
					align : 'center',
					formatter : function(val, rec) {
						if(val == 'ordered'){
							return '预约' ;
						}else if(val == 'bound'){
							return '绑定' ;
						}else if(val == 'useCar'){
							return '用车' ;
						}else if(val == 'finish'){
							return '完成' ;
						}else if(val == 'cancel'){
							return '取消' ;
						}
					}
				},
				{
					field : 'xq',
					title : '详情',
					width : $(this).width() * 0.05,
					align : 'center',
					formatter : function(val, rec) {
						return "<a href='#' onClick=detail('"+rec.orderId+"')>详情</a>" ;
					}
				}] ],
				pagination : true,
				rownumbers : true
			}); 
				//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
			
			//构造对话框
			$("#detailView").dialog( {
				width : "700",
				height : "700",
				top : "80",
				buttons : [{
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#detailView").dialog("close");
					}
				}]
			});
			
			$("#carView").dialog( {
				width : "400",
				height : "320",
				top : "40",
				modal:true,
				title:"绑定车辆",
				buttons : [{
					text : "确定",
					iconCls : "icon-save",
					handler : function() {
						$("#carViewForm").form("submit",{
							url:'save_lpn_order_info',
							onSubmit:function(){
								return $(this).form("validate");
							},
							success:function(result) {
								if (result== "succ") {
									$.messager.alert("提示", "操作成功", "info");
									$("#dataGrid").datagrid("reload");
									$("#carView").dialog("close");
								} else if(result == "fail") {
									$.messager.alert("提示", "操作失败", "error");
								} else if(result == "error") {
									$.messager.alert("提示", "订单为不可绑定车辆状态，请确认订单信息", "error");
								}else if(result == "timeError") {
									$.messager.alert("提示", "只能在预约取车时间前一个小时内绑定车辆", "error");
								}else if(result == "timeShareOrderError") {
									$.messager.alert("提示", "该会员存在分时租赁订单，无法绑定车辆", "error");
								}else if(result == "carError") {
									$.messager.alert("提示", "车辆已经被使用，请绑定其他车辆", "error");
								}else{
								    $.messager.alert("提示", "操作失败", "error");
								}
							}
						});
					}
				},{
					text : "取消",
					iconCls : "icon-cancel",
					handler : function() {
						$("#carView").dialog("close");
					}
				}]
			});
			
			$("#setLpn").bind("click",function(){
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				 if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择记录", "error");
				 }else{
					 var tmpArr = new Array();
					 for(var i=0; i<selectedRows.length; i++){
						 tmpArr[i] = selectedRows[i].carRunId;
					 }
					 if(selectedRows[0].orderStatus == 'useCar' || selectedRows[0].orderStatus == 'finish' || selectedRows[0].orderStatus == 'cancel') {
					 	$.messager.alert("提示", "当前订单状态不可绑定车辆", "error");
					 }else if(selectedRows[0].orderStatus == 'noPay'){
					 	$.messager.alert("提示", "当前订单未支付，不能绑定车辆", "error");
					 }else{
					 	var _url = 'car_city_code_model_id?orderId='+selectedRows[0].orderId; 
						 $('#o-lpn').combobox('reload', _url);
						 $("#o-orderId").val(selectedRows[0].orderId);
						 $("#carView").dialog("open");
					 }
				 }
			});
	
		});
		
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		 
		function detail(orderId){
			$.post("day_rent_order_detail?orderId="+orderId, function(data){
				var dayRentDetail = $.parseJSON( data ); 
	            $("#appointmenTakeCarTime").html(dayRentDetail.appointmenTakeCarTime); //预约取车时间
				$("#appointmenOrderMoney").html(dayRentDetail.appointmenOrderMoney) ;//订单金额
				$("#appointmenCarRentMoney").html(dayRentDetail.appointmenCarRentMoney);//车辆租赁费
				$("#appointmenReturnCarTime").html(dayRentDetail.appointmenReturnCarTime) ;//预约还车时间
				$("#appointmenCouponBalance").html(dayRentDetail.appointmenCouponBalance) ;//优惠券编号（面值）
				$("#appointmenInsuranceMoney").html(dayRentDetail.appointmenInsuranceMoney) ;//基本保险费
				$("#appointmenDayRentTime").html(dayRentDetail.appointmenDayRentTime) ;//预约租期
				$("#appointmenFreeMoney").html(dayRentDetail.appointmenFreeMoney) ;//优惠金额
				$("#appointmenProcedureMoney").html(dayRentDetail.appointmenProcedureMoney) ;//手续费
				$("#appointmenTakeCarParkAddress").html(dayRentDetail.appointmenTakeCarParkAddress) ;//预约取车网点
				$("#appointmenPayMoney").html(dayRentDetail.appointmenPayMoney) ;//支付金额
				$("#appointmenRemoteMoney").html(dayRentDetail.appointmenRemoteMoney) ;//异地还车费
				$("#appointmenReturnCarParkAddress").html(dayRentDetail.appointmenReturnCarParkAddress) ;//预约还车网点
				$("#appointmenPayType").html(dayRentDetail.appointmenPayType) ;//支付方式
				$("#appointmenFreeCompensateMoney").html(dayRentDetail.appointmenFreeCompensateMoney) ;//商业保险
				$("#actualTakeCarTime").html(dayRentDetail.actualTakeCarTime) ;//实际取车时间
				$("#actualTakeCarParkAddress").html(dayRentDetail.actualTakeCarParkAddress) ;//实际还车时间
				$("#delayReturnCarOrderTime").html(dayRentDetail.delayReturnCarOrderTime) ;//延期还车下单时间
				$("#delayCarRentMoney").html(dayRentDetail.delayCarRentMoney) ;//车辆租赁费
				$("#delayDayRentTime").html(dayRentDetail.delayDayRentTime) ;//延期天数
				$("#delayInsuranceMoney").html(dayRentDetail.delayInsuranceMoney) ;//基本保险费
				$("#delayReturnCarTime").html(dayRentDetail.delayReturnCarTime) ;//延期预约还车时间
				$("#delayFreeCompensateMoney").html(dayRentDetail.delayFreeCompensateMoney) ;//商业保险
				$("#delayPayMoney").html(dayRentDetail.delayPayMoney) ;//延期支付金额
				$("#delayOrderMoney").html(dayRentDetail.delayOrderMoney) ;//延期订单金额
				$("#delayPayType").html(dayRentDetail.delayPayType) ;//支付方式
				$("#actualReturnCarTime").html(dayRentDetail.actualReturnCarTime) ;//时间还车时间
				$("#actualReturnCarParkAddress").html(dayRentDetail.actualReturnCarParkAddress) ;//时间还车网点
				$("#timeoutDayTime").html(dayRentDetail.timeoutDayTime) ;//超时时间
				$("#timeoutPayMoney").html(dayRentDetail.timeoutPayMoney) ;//超时支付金额
				$("#timeoutRemoteMoney").html(dayRentDetail.timeoutRemoteMoney) ;//异地还车支付金额
				$("#timeoutPayType").html(dayRentDetail.timeoutPayType) ;//支付方式
				$("#orderTotalPayMoney").html(dayRentDetail.orderTotalPayMoney) ;//总支付金额
			});
			$("#detailView").dialog("open").dialog("setTitle", "订单详情");
		}
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:26px">
        <div>
        <form id="queryForm" method="post" action="#" enctype="multipart/form-data" >
        	 所属地区:<input class="easyui-combobox" name="cityCode" id="s-cityCode" style="width: 80px;" 
				data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			  姓名:<input type="text" id="s-memberName"  class="easyui-textbox">
			  手机号:<input type="text" id="s-phone"  class="easyui-textbox">
			 车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn">
			 订单状态<input class="easyui-combobox" name="orderStatus" id="s-orderStatus"
					data-options=" url:'sys_dic?dicCode=DAY_ORDER_STATUS',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            <r:FunctionRole functionRoleId="bind_car">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="setLpn">绑定车辆</a>
            </r:FunctionRole>
             
        </form>
          <!-- add -->
         <div id="detailView" class="easyui-dialog" closed="true" style="width: 700px;height: 700px;">
				<table cellpadding="5" style="font-size: 12px;margin-top: 0px; width: 650px;">
		    		<tr>
		    			<td colspan="3" style="font-weight: bold;">预约信息</td>
		    		</tr>
		    		<tr id="appointmenTr1">
		    			<td width="40%">预约取车时间：<span id="appointmenTakeCarTime"></span></td>
		    			<td width="30%">订单金额（元）：<span id="appointmenOrderMoney"></span></td>
		    			<td width="30%">车辆租赁费（元）：<span id="appointmenCarRentMoney"></span></td>
		    		</tr>
		    		<tr id="appointmenTr2">
		    			<td>预约还车时间：<span id="appointmenReturnCarTime"></span></td>
		    			<td>预约租期（天）：<span id="appointmenDayRentTime"></span></td>
		    			<td>基本保险费（元）：<span id="appointmenInsuranceMoney"></span></td>
		    		</tr>
		    		<tr id="appointmenTr3">
		    			<td>优惠券编号（面值）：<span id="appointmenCouponBalance"></span></td>
		    			<td>优惠金额（元）：<span id="appointmenFreeMoney"></span></td>
		    			<td>手续费（元）：<span id="appointmenProcedureMoney"></span></td>
		    		</tr>
		    		<tr id="appointmenTr4">
		    			<td>预约取车网点：<span id="appointmenTakeCarParkAddress"></span></td>
		    			<td>支付金额（元）：<span id="appointmenPayMoney"></span></td>
		    			<td>异地还车费（元）：<span id="appointmenRemoteMoney"></span></td>
		    		</tr>
		    		<tr id="appointmenTr5">
		    			<td>预约还车网点：<span id="appointmenReturnCarParkAddress"></span></td>
		    			<td>支付方式：<span id="appointmenPayType"></span></td>
		    			<td>商业保险（元）：<span id="appointmenFreeCompensateMoney"></span></td>
		    		</tr>
		    	</table>
		    	<hr style="height:1px;border:none;border-top:1px dashed #A2A2A2;" />
		    	<table cellpadding="5" style="font-size: 12px;margin-top: 0px; width: 630px;">
		    		<tr>
		    			<td style="font-weight: bold;">实际取车信息</td>
		    		</tr>
		    		<tr>
		    			<td>实际取车时间：<span id="actualTakeCarTime"></span></td>
		    		</tr>
		    		<tr>
		    			<td>实际取车网点：<span id="actualTakeCarParkAddress"></span></td>
		    		</tr>
		    	</table>
		    	<hr style="height:1px;border:none;border-top:1px dashed #A2A2A2;" />
		    	<table cellpadding="5" style="font-size: 12px;margin-top: 0px; width: 630px;">
		    		<tr>
		    			<td colspan="2" style="font-weight: bold;">延期信息</td>
		    		</tr>
		    		<tr id="delayTr1">
		    			<td>延期还车下单时间：<span id="delayReturnCarOrderTime"></span></td>
		    			<td>车辆租赁费（元）：<span id="delayCarRentMoney"></span></td>
		    		</tr>
		    		<tr id="delayTr2">
		    			<td>延期天数（天）：<span id="delayDayRentTime"></span></td>
		    			<td>基本保险费（元）：<span id="delayInsuranceMoney"></span></td>
		    		</tr>
		    		<tr id="delayTr3">
		    			<td>延期预约还车时间：<span id="delayReturnCarTime"></span></td>
		    			<td>商业保险（元）：<span id="delayFreeCompensateMoney"></span></td>
		    		</tr>
		    		<tr id="delayTr4">
		    			<td>延期支付金额（元）：<span id="delayPayMoney"></span></td>
		    			<td>支付方式：<span id="delayPayType"></span></td>
		    		</tr>
		    		<tr id="delayTr5">
		    			<td>延期订单金额（元）：<span id="delayOrderMoney"></span></td>
		    		</tr>
		    	</table>
		    	<hr style="height:1px;border:none;border-top:1px dashed #A2A2A2;" />
		    	<table cellpadding="5" style="font-size: 12px;margin-top: 0px; width: 630px;">
		    		<tr>
		    			<td colspan="2" style="font-weight: bold;">实际还车信息</td>
		    		</tr>
		    		<tr>
		    			<td>实际还车时间：<span id="actualReturnCarTime"></span></td>
		    			<td>实际还车网点：<span id="actualReturnCarParkAddress"></span></td>
		    		</tr>
		    		<tr>
		    			<td>超时时间：<span id="timeoutDayTime"></span></td> 
		    		
		    			<td>超时支付金额（元）：<span id="timeoutPayMoney"></span></td>
		    		</tr>
		    		<tr>
		    			<td>异地还车支付金额（元）：<span id="timeoutRemoteMoney"></span></td>
		    		
		    			<td>支付方式：<span id="timeoutPayType"></span></td>
		    		</tr>
		    		<tr>
		    			<td colspan="2">总支付金额：<span id="orderTotalPayMoney"></span></td>
		    		</tr>
		    	</table>
		 </div>
		 
		  <!--  order car -->
	     <div id="carView" class="easyui-dialog" closed="true"
			style="padding:10px 20px">
	       <form name="carViewForm" id="carViewForm" method="post" >
	          <input type="hidden" name="orderId" id="o-orderId">
	         	<div class="fitem">
					<label>请选择车辆:</label> <input class="easyui-combobox" name='lpn' id="o-lpn"
						data-options="
		                    method:'get', 
		                    valueField:'lpn', 
		                    textField:'lpn', 
		                    panelHeight:'auto'" 
						required="true" missingMessage="请选选择车辆">
			   </div>
	       </form>
	     </div>
     
	</body>
</html>