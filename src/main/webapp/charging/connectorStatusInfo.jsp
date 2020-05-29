<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>充电设备接口状态管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		function clearToolbar(){
			$('#toolbar').form('clear');
		}
		$(function() {
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			//回车键查询
			$(document).keydown(function(event){
				var connectorId = $("input[name='connectorId']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			        connectorId : connectorId
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
			var connectorId = $("input[name='connectorId']").val();
			    $('#dataGrid').datagrid('load',{
		           connectorId : connectorId
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '充电设备接口状态管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dataListConnectorStatusInfo.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},{
					field : 'connectorId',
					title : '接口编码',
					width : $(this).width() * 0.1,
					align : 'center'
				},   
				  {
					field : 'status',
					title : '接口状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						if(val=="0"){
							return "离网";
						}else if(val=="1" || val=="2"){
							return "空闲";
						}/*else if(val=="2"){
							return "占用（未充电）";
						}*/else if(val=="3"){
							return "占用（充电中）";
						}else if(val=="4"){
							return "占用（预约锁定）";
						}else if(val=="255"){
						   return "故障";
						}
					}
					
				},   
				  {
					field : 'parkStatus',
					title : '车位状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						if(val=="10"){
							return "空闲";
						}else if(val=="50"){
							return "占用";
						}else {
							return "未知";
						}
					}
					
				},   
				  {
					field : 'lockStatus',
					title : '地锁状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						if(val=="10"){
							return "已解锁";
						}else if(val=="50"){
							return "已上锁";
						}else {
							return "未知";
						}
					}
					
				},{
					field : 'currentA',
					title : 'A相电流',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						return val+"A";
					}
				}, {
					field : 'currentB',
					title : 'B相电流',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						return val+"A";
					}
				} , {
					field : 'currentC',
					title : 'C相电流',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						return val+"A";
					}
				},{
					field : 'voltageA',
					title : 'A相电压',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						return val+"V";
					}
				}, {
					field : 'voltageB',
					title : 'B相电压',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						return val+"V";
					}
				} , {
					field : 'voltageC',
					title : 'C相电压',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter:function(val,rec){
						return val+"V";
					}
				}, 
				{
					field : 'soc',
					title : '剩余电量',
					width : $(this).width() * 0.1,
					align : 'center',
					
				},
				{  
					field : 'createTime',   
					title : '创建时间',
					width : $(this).width() * 0.18,
					align : 'center'
				},
				{
					field : 'createName',
					title : '创建人',
					width : $(this).width() * 0.15,
					align : 'center',
				},{
					field : 'updateTime',
					title : '更新时间',
					width : $(this).width() * 0.18,
					align : 'center',
				},
				{
					field : 'updateName',
					title : '更新人',
					width : $(this).width() * 0.15,
					align : 'center'	
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
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		
	</script> 
	
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			接口编码:<input id="connector_id"  name="connectorId" class="easyui-textbox" style="width:80px"/>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
         </div>
       <!--   <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
					
		    		<tr>
		    			<td>运营商id:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="operatorId" name="operatorId" style="width:100%;height:24px" data-options="required:true,missingMessage:'运营商id不能为空'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>运营商名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="name" name="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'运营商名称不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>联系电话1:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="phone1" name="phone1" style="width:100%;height:24px" data-options="required:true,missingMessage:'联系电话1不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>联系电话2:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="phone2" name="phone2" style="width:100%;height:24px" data-options="required:true,missingMessage:'联系电话2不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>注册地址:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="registerAddress" name="registerAddress" style="width:100%;height:24px" data-options="required:true,missingMessage:'注册地址不能为空'">
		    			</td>
		    		</tr><tr>
		    			<td>备注:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="remark" name="remark" style="width:100%;height:60px"  data-options="multiline:true">
		    			</td>
		    		</tr>
		    	</table> 	
			</form>
		 </div>
		     -->
   		
	</body>
</html>