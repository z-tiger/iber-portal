<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>企业用车订单</title>
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
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			//查询链接
			$(document).keydown(function(event){
				var city_code = $("input[name='_city_code']").val();
			    var enterprise_name = $("input[name='_enterprise_name']").val();
			    var member_name = $("input[name='_member_name']").val();
			    var pay_type = $("input[name='_pay_type']").val();
			    var check_status = $("input[name='_check_status']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           city_code : city_code,
			           enterprise_name : enterprise_name,
			           member_name : member_name,
			           pay_type : pay_type,
			           check_status : check_status,
			           queryDateFrom : queryDateFrom,
			           queryDateTo : queryDateTo
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var city_code = $("input[name='_city_code']").val();
			    var enterprise_name = $("input[name='_enterprise_name']").val();
			    var member_name = $("input[name='_member_name']").val();
			    var pay_type = $("input[name='_pay_type']").val();
			    var check_status = $("input[name='_check_status']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
	    		var queryDateTo = $("input[name='queryDateTo']").val();
			    $('#dataGrid').datagrid('load',{
		    	    city_code : city_code,
	           		enterprise_name : enterprise_name,
	           		member_name : member_name,
	           		pay_type : pay_type,
	           		check_status : check_status,
	           		queryDateFrom : queryDateFrom,
	           		queryDateTo : queryDateTo
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '企业用车审核',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : false,
				nowrap : false,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'enterprise_extend_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				}, {
					field : 'cityName',
					title : '所属区域',
					width : $(this).width() * 0.05,
					align : 'center',
				}, {
					field : 'orderId',
					title : '订单号',
					width : $(this).width() * 0.15,
					align : 'center',
				}, {
					field : 'enterpriseName',
					title : '公司名称',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'memberName',
					title : '会员名称',
					width : $(this).width() * 0.05,
					align : 'center',
				}, {
					field : 'startTime',
					title : '上车时间',
					width : $(this).width() * 0.1,
					align : 'center',
				}, {
					field : 'upCarAddress',
					title : '出发地',
					width : $(this).width() * 0.2,
					align : 'center',
				}, {
					field : 'downCarAddress',
					title : '目的地',
					width : $(this).width() * 0.2,
					align : 'center',
				}, {
					field : 'amount',
					title : '金额',
					width : $(this).width() * 0.05,
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
					title : '扣款方式',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else if(val == "enterprise_pay"){
							return "企业扣款";
						}else if(val == "myself_pay"){
							return "个人扣款";
						}else{
							return val;
						}
					}
				}, {
					field : 'lpn',
					title : '车牌号',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val) {
						return val.substring(0,2) + "•" + val.substring(2);
					}
				}, {
					field : 'checkStatus',
					title : '订单状态',
					width : $(this).width() * 0.1,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null"){
							return "";
						}else if(val == "checkWait"){
							return "待审核";
						}else if(val == "checkSucc"){
							return "审核通过";
						}else if(val == "checkFail"){
							return "审核不通过";
						}else if(val == "checkWithout"){
							return "无需审核";
						}else if(val == "cancel"){
							return "已取消";
						}else{
							return val;
						}
					}
				}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			//审核通过
			$("#btnEdit").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要审核用车订单", "error");
		   		}else{
		   			var JsonData = selectedRow[0];
			   		if(JsonData.checkStatus != "checkWait"){
			   			$.messager.alert("提示", "请选择需要审核的订单", "error");
			   		}else{
						$.messager.confirm("提示","确定审核通过吗?",function(r){
				      		if(r){
								$.post("enterprise_extend_check_success.do",{"id":JsonData.id},function(data){
									if(data=="success"){
									    $("#dataGrid").datagrid("reload");
									}else{
										$.messager.alert("提示", "操作失败", "info");
									}
								},"text");
							}
						});
					}
		  		}	     
			});
			
			//审核不通过
			$("#btnRemove").bind("click",function(){
		      	var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要审核用车订单", "error");
		   		}else{
		   			var JsonData = selectedRow[0];
			   		if(JsonData.checkStatus != "checkWait"){
			   			$.messager.alert("提示", "请选择需要审核的订单", "error");
			   		}else{
						$.messager.confirm("提示","确定审核不通过吗?",function(r){
				      		if(r){
								$.post("enterprise_extend_check_fail.do",{"id":JsonData.id},function(data){
									if(data=="success"){
									    $("#dataGrid").datagrid("reload");
									}else{
										$.messager.alert("提示", "操作失败", "info");
									}
								},"text");
							}
						});
					}
		  		}		     
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
        	<!-- <form id="queryForm" method="post" action="#" enctype="multipart/form-data" > -->
				所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
					data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
				企业名称:<input id="_enterprise_name"  name="_enterprise_name" class="easyui-textbox" style="width:100px"/>
				会员名称:<input id="_member_name"  name="_member_name" class="easyui-textbox" style="width:100px"/>
				扣款方式:<input class="easyui-combobox" name="_pay_type" id="_pay_type" style="width:100px"
						data-options=" url:'sys_dic?dicCode=PAY_WAY',
		                    method:'get',
		                    valueField:'code',
		                    textField:'name',
		                    panelHeight:'auto',
		                    editable:false">
		                    订单状态:<select class="easyui-combobox" name="_check_status" id="_check_status" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
						 <option value=""></option>
			             <option value="checkWait">待审核</option>
			             <option value="checkSucc">审核通过</option>
			             <option value="checkFail">审核不通过</option>
			             <option value="checkWithout">无需审核</option>
			             <option value="cancel">已取消</option>
			          </select>
				上车时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px"/>
	          	 到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	     
	            <r:FunctionRole functionRoleId="pass_audit">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_okay" plain="true" id="btnEdit">审核通过</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="no_pass_audit">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_remove" plain="true" id="btnRemove">审核不通过</a>
	            </r:FunctionRole>
		  
         	<!-- </form> -->
         </div>
	</body>
</html>