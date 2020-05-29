<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电订单管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
	<script type="text/javascript">
		$(function() {
			//回车键查询
			$(document).keydown(function(event){
			   	var memberName = $("input[name='memberName']").val();
                var userType = $("#userType").combobox("getValue");
                var phone = $("input[name='phone']").val();
			   	var stationName = $("input[name='stationName']").val();
			   	var bt = $("#s-bt").datetimebox("getValue");
				var et = $("#s-et").datetimebox("getValue");
				 var chargingStatus = $("#s-chargingStatus").combobox("getValue");
				 var cityCode = $("#cityCode").combobox('getValue');
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
				  	cityCode:cityCode,
                      userType: userType,
                      phone:phone,
			        memberName:memberName,
		           	stationName:stationName,
		           	bt:bt,
		           	et:et,
		           	chargingStatus:chargingStatus 
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			   var memberName = $("input[name='memberName']").val();
			   var userType = $("#userType").combobox("getValue");
			   var phone = $("input[name='phone']").val();
			   var lpn = $("input[name='lpn']").val();
			   var stationName = $("input[name='stationName']").val();
			   var bt = $("#s-bt").datetimebox("getValue");
			   var et = $("#s-et").datetimebox("getValue");
			   var chargingStatus = $("#s-chargingStatus").combobox("getValue");
			   var cityCode = $("#cityCode").combobox('getValue');
			    $('#dataGrid').datagrid('load',{
			       cityCode:cityCode,
					userType: userType,
					phone:phone,
					lpn:lpn,
		           memberName:memberName,
		           stationName:stationName,
		           bt:bt,
		           et:et,
		           chargingStatus:chargingStatus   
		        });
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var memberName = $("input[name='memberName']").val();
			   var userType = $("#userType").combobox("getValue");
			   var phone = $("input[name='phone']").val();
			   var lpn = $("input[name='lpn']").val();
			   var stationName = $("input[name='stationName']").val();
			   var bt = $("#s-bt").datetimebox("getValue");
			   var et = $("#s-et").datetimebox("getValue");
			   var chargingStatus = $("#s-chargingStatus").combobox("getValue");
			   var cityCode = $("#cityCode").combobox('getValue');
				$("#chargingOrderForm").form("submit", {
					url : "export_chargingOrder_list?memberName="+memberName+"&userType="
													+userType+"&phone="+phone+"&stationName="
													+stationName+"&bt="+bt+"&et="+et
													+"&chargingStatus="+chargingStatus
													+"&cityCode="+cityCode+"&lpn="+lpn,
					onSubmit : function() {
					},
					success : function(result) {
					}
				});
		
			});
			$('#dataGrid').datagrid( {
				title : '充电订单管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'dataListChargingOrder.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'createTime',
				pagination : true,
				rownumbers : true,
				columns : [ [{
					field : 'ck',
					checkbox:true
				},{
					field : 'stationName',
					title : '网点名称',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				/* {
					field : 'equipmentCode',   
					title : '充电桩编码',
					width : $(this).width() * 0.08,
					align : 'center'
				},
				{
					field : 'connectorCode',
					title : '充电枪编码（名称+编码）',
					width : $(this).width() * 0.15,
					align : 'center'	
				},  */  
				  {
					field : 'memberName',
					title : '使用人',
					align : 'center',
					width : $(this).width() * 0.08,
					
				},   
				  {
					field : 'phone',
					title : '手机号码',
					align : 'center',
					width : $(this).width() * 0.08,
					
				},	
				{
					field : 'chargeSeq',
					title : '充电订单号',
					width : $(this).width() * 0.12,
					align : 'center'	  
				},
				{
					field : 'lpn',
					title : '充电车辆',
					width : $(this).width() * 0.08,
					align : 'center'	  
				},
				{
					field : 'equipmentType',
					title : '充电桩类型',
					width : $(this).width() * 0.08,
					align : 'center'	  
				},
				{
					field : 'carport',
					title : '充电车位号',
					width : $(this).width() * 0.08,
					align : 'center'	  
				},
				{
					field : 'chargingStatus',
					title : '充电状态',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
					   if(val=="charging"){
					    return "充电中";
					   }else if(val=="noPay"){
					    return "<font color=red >未支付</font>";
					   }else if(val=="finish"){
					    return "已完成";
					   }else if(val=="afterReturn"){
						   return "还车扫码充电中";
					   }

					}	
				},
				{
					field : 'memberStatus',
					title : '使用人状态',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
					   if(val=="ready"){
					    return "就绪";
					   }else if(val=="experience"){
					    return "体验";
					   }else if(val=="ordered"){
					    return "预约";
					   }else if(val=="useCar"){
					    return "用车";
					   }else if(val=="return"){
					    return "还车";
					   }
					}	
				},
				
				{
					field : 'chargingAmount',
					title : '充电量(KW/H)',
					width : $(this).width() * 0.06,
					align : 'center'	 
				},
				{
					field : 'chargingTime',
					title : '充电时长',
					width : $(this).width() * 0.06,
					align : 'center',
					formatter : function(val) {
						n = val.toFixed(0);
						var re = /(\d{1,3})(?=(\d{3}))/g;
						return n.replace(re, "$1,");
					}	
				},
				{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'startTime',
					title : '开始时间',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'endTime',
					title : '结束时间',
					width : $(this).width() * 0.08,
					align : 'center'	
				},
				{
					field : 'orderMoney',
					title : '订单金额(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
						n = (val/100).toFixed(2);
				   		var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			  	    return n.replace(re, "$1,");	
					}	
				},
				//{
					//field : 'couponCode',
					//title : '优惠券编码',
					//width : $(this).width() * 0.08,
					//align : 'center'	
				//},
				//{
					//field : 'couponValue',
					//title : '优惠券面值(元)',
					//width : $(this).width() * 0.08,
					//align : 'center',
					//formatter:function(val,rec){
						//n = (val/100).toFixed(2);
				   		//var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			   		//return n.replace(re, "$1,");	
					//}	
				//},
				{
					field : 'payMoney',
					title : '支付金额(元)',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
						n = (val/100).toFixed(2);
				   		var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			   		return n.replace(re, "$1,");	
					}	
				},
				{
					field : 'isFreeOrder',
					title : '是否免单',
					width : $(this).width() * 0.05,
					align : 'center',
					formatter:function(val,rec){
						if(val=="Y"){
						   return "是";
						}else{
						   return "否";
						}
					}	
				},
				{
					field : 'freeReason',
					title : '免单原因',
					width : $(this).width() * 0.1,
					align : 'center',
				},
				{
					field : 'payType',
					title : '支付方式',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter:function(val,rec){
					 	if(val=="B"){
					 		return "余额";
					 	}else if(val=="A"){
					 		return "支付宝";
					 	}else if(val=="T"){
					 		return "财付通";
					 	}else if(val=="WX"){
					 		return "微信";
					 	}else if(val=="U"){
					 		return "银联";
					 	}else if(val=="AP"){
					 		return "apple pay";
					 	}
					}	
				}
				] ]
			});
			
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			
		});
		function clearToolbar(){
			$('#toolbar').form('clear');
		}
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
	</script> 
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
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	    <form action="" id="chargingOrderForm">
			所属地区:<input class="easyui-combobox" name="cityCode" id="cityCode" style="width: 80px;"
				data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			订单类型:<select id="userType"  name="userType" class="easyui-combobox" style="width:120px"
						data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '会员充电',
							value: 'member'
						},{
							label: '员工充电',
							value: 'employee'
						}],
	                    panelHeight:'auto'"
		>
			<option value ='member' selected ="selected">会员充电</option >
		</select>
			网点名称:<input id="stationName"  name="stationName" class="easyui-textbox" style="width:120px"/>
			会员名称:<input id="memberName"  name="memberName" class="easyui-textbox" style="width:80px"/>
			手机号码:<input id="phone"  name="phone" class="easyui-textbox" style="width:80px"/>
			充电车牌:<input id="lpn"  name="lpn" class="easyui-textbox" style="width:80px"/>
			充电时间:
	       	<input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" />
		  	--
		  	<input id="s-et" name="et" class="easyui-datetimebox e" size="18"/>	
			订单状态:<input id="s-chargingStatus"  name="chargingStatus" class="easyui-combobox" style="width:120px"
			data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '充电中',
							value: 'charging'
						},{
							label: '已完成',
							value: 'finish'
						},{
							label: '未支付',
							value: 'noPay'
						},{
							label: '还车扫码充电中',
							value: 'afterReturn'
						}],
	                    panelHeight:'auto'"
			>
			<r:FunctionRole functionRoleId="select_charging_order">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            </r:FunctionRole>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
            <r:FunctionRole functionRoleId="export_charging_order">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
			</r:FunctionRole>
            <!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
	        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a> -->
	        </form>
         </div>
        	 
	</body>
</html>