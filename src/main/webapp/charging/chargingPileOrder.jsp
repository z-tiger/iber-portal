<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>充电订单</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		$(function() {
			//查询链接
			$(document).keydown(function(event){
				var city_code = $("input[name='_city_code']").val();
			    var member_name = $("input[name='_member_name']").val();
			    var park_name = $("input[name='_park_name']").val();
			    var pile_type = $("input[name='_pile_type']").val();
			    var pay_type = $("input[name='_pay_type']").val();
			    var charging_type = $("input[name='_charging_type']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           city_code : city_code,
			           member_name : member_name,
			           park_name : park_name,
			           pile_type : pile_type,
			           pay_type : pay_type,
			           charging_type : charging_type,
			           queryDateFrom : queryDateFrom,
			           queryDateTo : queryDateTo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var city_code = $("input[name='_city_code']").val();
			    var member_name = $("input[name='_member_name']").val();
			    var park_name = $("input[name='_park_name']").val();
			    var pile_type = $("input[name='_pile_type']").val();
			    var pay_type = $("input[name='_pay_type']").val();
			    var charging_type = $("input[name='_charging_type']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
			    $('#dataGrid').datagrid('load',{
		    	    city_code : city_code,
		            member_name : member_name,
		            park_name : park_name,
		            pile_type : pile_type,
		            pay_type : pay_type,
		            charging_type : charging_type,
		            queryDateFrom : queryDateFrom,
		            queryDateTo : queryDateTo
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '充电订单',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : false,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'charging_pile_order_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				}, {
					field : 'cityName',
					title : '所属区域',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'memberName',
					title : '会员名称',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'pileType',
					title : '桩型号',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'parkName',
					title : '网点名称',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'beginTime',
					title : '开始时间',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'endTime',
					title : '结束时间',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'orderMoney',
					title : '订单金额',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return (val/100).toFixed(2);
					}
				}, {
					field : 'couponNo',
					title : '优惠券编号',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'couponBalance',
					title : '优惠券面值',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return (val/100).toFixed(2);
					}
				}, {
					field : 'privilegeMoney',
					title : '优惠金额',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return (val/100).toFixed(2);
					}
				}, {
					field : 'payMoney',
					title : '支付金额',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return (val/100).toFixed(2);
					}
				}, {
					field : 'payType',
					title : '支付方式',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "B")
					       return "余额";
					    else if(val == "WX")
					       return "微信";
					    else if(val == "A")
					       return "支付宝";
					} 
				}, {
					field : 'dimensionInfo',
					title : '二维码',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'checkStatus',
					title : '订单状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else if(val == "ordered"){
							return "预约";
						}else if(val == "charging"){
							return "充电";
						}else if(val == "noPay"){
							return "待支付";
						}else if(val == "finish"){
							return "完成";
						}else{
							return val;
						}
					}
				}, {
					field : 'beforeElectric',
					title : '充电前电量',
					width : $(this).width() * 0.08,
					align : 'center',
				}, {
					field : 'endElectric',
					title : '充电后电量',
					width : $(this).width() * 0.08,
					align : 'center',
				}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
			
		});
	
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
        	<form id="queryForm" method="post" action="#" enctype="multipart/form-data" >
				所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
					data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
				会员名称:<input id="_member_name"  name="_member_name" class="easyui-textbox" style="width:100px"/>
				网点名称:<input id="_park_name"  name="_park_name" class="easyui-textbox" style="width:100px"/>
				桩型号:<input id="_pile_type"  name="_pile_type" class="easyui-textbox" style="width:100px"/>
				支付方式:<input class="easyui-combobox" name="_pay_type" id="_pay_type" style="width:100px"
						data-options=" url:'sys_dic?dicCode=PAY_TYPE',
		                    method:'get',
		                    valueField:'code',
		                    textField:'name',
		                    panelHeight:'auto',
		                    editable:false">
		                    充电类型:<select class="easyui-combobox" name="_charging_type" id="_charging_type" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
						 <option value=""></option>
			             <option value="fast">快充</option>
			             <option value="slow">慢充</option>
			          </select>
				开始充电时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px"/>
	          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
         	</form>
         </div>
	</body>
</html>