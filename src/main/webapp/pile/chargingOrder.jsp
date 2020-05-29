<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	<script type="text/javascript">
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var name = $("input[name='_name']").val();
			    var city_code = $("input[name='_city_code']").val();
			    var order_id = $("input[name='_order_id']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           name : name,
			           city_code : city_code,
			           order_id : order_id
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var name = $("input[name='_name']").val();
			    var city_code = $("input[name='_city_code']").val();
			    var order_id = $("input[name='_order_id']").val();
			    $('#dataGrid').datagrid('load',{
		    	    name : name,
			           city_code : city_code,
			           order_id : order_id
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '充电订单',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'pile_charging_order_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				},{
					field : 'cityName',
					title : '区域',
					width : $(this).width() * 0.01,
					align : 'center'
				},  {
					field : 'name',
					title : '会员姓名',
					width : $(this).width() * 0.01,
					align : 'center',
				},{
					field : 'orderId',
					title : '订单编号',
					width : $(this).width() * 0.01,
					align : 'center'
				}, {
					field : 'stationId',
					title : '站ID',
					width : $(this).width() * 0.01,
					align : 'center'
				},{
					field : 'pileId',
					title : '桩ID',
					width : $(this).width() * 0.01,
					align : 'center'
				},{
					field : 'power',
					title : '用电量',
					width : $(this).width() * 0.01,
					align : 'center'
				},{
					field : 'cost',
					title : '花费金额',
					width : $(this).width() * 0.01,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       return val/100;
					}
				},{
					field : 'time',
					title : '充电时长',
					width : $(this).width() * 0.01,
					align : 'center'
				},{
					field : 'beginTime',
					title : '开始时间',
					width : $(this).width() * 0.01,
					align : 'center'
				},{
					field : 'endTime',
					title : '结束时间',
					width : $(this).width() * 0.01,
					align : 'center'
				},{
					field : 'status',
					title : '订单状态',
					width : $(this).width() * 0.01,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "finish"){
							return "已完成";
						}else if(val == "start"){
							return "进行中";
						}else{
							 return val;
						}
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
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
	        <form id="queryForm" method="post" action="#" enctype="multipart/form-data"  style="height: 15px;">
	        	所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
					data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
				会员名称:<input id="_name"  name="_name" class="easyui-textbox" style="width: 100px;"/>
				订单编号:<input id="_order_id"  name="_order_id" class="easyui-textbox" style="width: 100px;"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	         </form>
         </div>
	</body>
</html>