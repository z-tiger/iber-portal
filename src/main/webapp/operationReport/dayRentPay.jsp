<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>日租收入报表</title>                                     <!-- metro -->
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
			    var lpn = $("input[name='_lpn']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           city_code : city_code,
			           lpn : lpn,
			           queryDateFrom : queryDateFrom,
			           queryDateTo : queryDateTo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var city_code = $("input[name='_city_code']").val();
				var lpn = $("input[name='_lpn']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
			    $('#dataGrid').datagrid('load',{
		    	    city_code : city_code,
		    	    lpn : lpn,
	           		queryDateFrom : queryDateFrom,
	           		queryDateTo : queryDateTo
		        });
			});
			
			//查询导出excel链接
			$("#btnExport").bind("click",function(){
				var city_code = $("input[name='_city_code']").val();
				var lpn = $("input[name='_lpn']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
			    $("#queryForm").form("submit", {
	    	        url :"day_rent_pay_excel?city_code="+city_code+"&lpn="+lpn+"&queryDateFrom="+queryDateFrom+"&queryDateTo=" + queryDateTo,
					onSubmit : function() {
						return $("#queryForm").form("validate");
					},
					success : function(result) {
					}
				});
		    });	
			
			
			$('#dataGrid').datagrid( {
				title : '日租收入报表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'day_rent_pay_list.do',
				pageSize : 20,
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
					field : 'orderId',
					title : '订单号',
					width : $(this).width() * 0.2,
					align : 'center',
				}, {
					field : 'lpn',
					title : '车牌号',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'memberName',
					title : '会员名称',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'payTime',
					title : '支付时间',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'payMoney',
					title : '金额',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val ==null || val == "null"){
							return "";
						}else{
							n = (val/100).toFixed(2);
							var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
	       					return n.replace(re, "$1,");	
						}
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
					field : 'orderType',
					title : '订单类型',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "dayRent")
					       return "日租";
					    else if(val == "continueRent")
					       return "续租";
					    else if(val == "timeoutRent")
					       return "超时";
					} 
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
	<style type="text/css">
	 #queryForm{
	  margin:0 auto;
	 }
	</style>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">

        <div>
        	<form id="queryForm">
			所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			车牌号:<input id="_lpn"  name="_lpn" class="easyui-textbox" style="width:100px"/>
			支付时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px"/>
          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            
            <r:FunctionRole functionRoleId="dat_rent_income_form_export_excel">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
            </r:FunctionRole>
			
			</form>
	</body>
</html>