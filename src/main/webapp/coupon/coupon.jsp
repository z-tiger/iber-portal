<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>优惠券管理</title>
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
			$("#btnExcel").bind("click", function() {
				location.href="coupon_template.xls";
			});
			//查询链接
			$(document).keydown(function(event){
			    var title = $("input[name='_title']").val();
			    var city_code = $("input[name='_city_code']").val();
			    var batch_no = $("input[name='_batch_no']").val();
			    var coupon_no = $("input[name='_coupon_no']").val();
			    var status = $("input[name='_status']").val();
			    var use_status = $("input[name='_use_status']").val();
			    var memberName = $("input[name='memberName']").val();
			    var memberPhone = $("input[name='memberPhone']").val();
				var minAmount = $("input[name='minAmount']").val();
				var maxAmount = $("input[name='maxAmount']").val();
				var applyDep = $("input[name='_applyDep']").val();
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
			           title : title,
			           city_code : city_code,
			           batch_no : batch_no,
			           coupon_no : coupon_no,
			           status : status,
			           use_status : use_status,
			           memberName:memberName,
			           memberPhone:memberPhone,
					   minAmount:minAmount,
					   applyDep:applyDep,
				       maxAmount:maxAmount
		          });
				}
			});			
			//查询链接
			$("#btnQuery").bind("click",function(){
				var title = $("input[name='_title']").val();
			    var city_code = $("input[name='_city_code']").val();
			    var batch_no = $("input[name='_batch_no']").val();
			    var coupon_no = $("input[name='_coupon_no']").val();
			    var status = $("input[name='_status']").val();
			    var use_status = $("input[name='_use_status']").val();
			    var memberName = $("input[name='memberName']").val();
			    var memberPhone = $("input[name='memberPhone']").val();
				var minAmount = $("input[name='minAmount']").val();
				var maxAmount = $("input[name='maxAmount']").val();
				var applyDep = $("input[name='_applyDep']").val();
			    $('#dataGrid').datagrid('load',{
		    	   title : title,
		           city_code : city_code,
		           batch_no : batch_no,
		           coupon_no : coupon_no,
		           status : status,
		           use_status : use_status,
		           memberName:memberName,
			       memberPhone:memberPhone,
				   minAmount:minAmount,
				   applyDep:applyDep,
				   maxAmount:maxAmount
		        });
			});
			
			$('#dataGrid').datagrid( {
				title : '优惠券管理',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'coupon_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
				  {
					field : 'cityName',
					title : '地区',
					width : $(this).width() * 0.15,
					align : 'center'
					
				},{
					field : 'title',
					title : '标题',
					width : $(this).width() * 0.2,
					align : 'center'
				}, {
					field : 'batchNo',
					title : '批次号',
					width : $(this).width() * 0.2,
					align : 'center'
				} , {
					field : 'couponNo',
					title : '优惠券编号',
					width : $(this).width() * 0.2,
					align : 'center'
				}, 
				{
					field : 'balance',
					title : '面额/折扣',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter : function(val, rec) {
					    return (val/100).toFixed(2);
					}
				},
				{
					field : 'startTime',   
					title : '生效时间',
					width : $(this).width() * 0.3,
					align : 'center'
				},
				{  
					field : 'endTime',   
					title : '失效时间',
					width : $(this).width() * 0.3,
					align : 'center'
				},
				{  
					field : 'sysUserName',   
					title : '创建人',
					width : $(this).width() * 0.15,
					align : 'center'
				},
				{
					field : 'status',
					title : '是否领用',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter:function(val, rec) {
						if(val == "1"){
					       	return "是";
						}else if(val == "0"){
						   	return "否";
					    }else{
					    	return "";
					    }
					}
				},{
					field : 'useStatus',
					title : '是否使用',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "1"){
					       	return "<font color='red'>是</font>";
						}else if(val == "0"){
						   	return "<font color='green'>否</font>";
					    }else{
					    	return "";
					    }
					}
				},{
					field : 'status1',
					title : '是否作废',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter:function(val, rec) {
						var date=Date.parse(rec.endTime.replace(/-/g,"/"));
						var now=new Date();
						//if(date<now){ //失效时间小于当前时间，表示优惠券已经作废
						//	return "失效";
						//}else{
							if(val == "2"){
						       	return "<font color='red'>是</font>";
							}else if(date<now){
								return "失效";
							}else if(val == "0"){
							   	return "";
						    }else if(val == "1"){
						    	return "";
						    }
						}
					//}
				},{
					field : 'memberName',
					title : '会员姓名',
					width : $(this).width() * 0.15,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "0"){
					       	return "";
					    }else{
					    	return val;
					    }
					}
				},{
					field : 'memberPhone',
					title : '手机号码',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "0"){
					       	return "";
					    }else{
					    	return val;
					    }
					}
				},{
					field : 'useTime',
					title : '使用时间',
					width : $(this).width() * 0.3,
					align : 'center'
				},{
					field : 'useType',
					title : '优惠券类型',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter:function(val){
                        if(val == 1){
							return "满减券";
						}else if(val == 2){
							return "折扣券";
						}else{
							return "现金券";
						}
					}
				},{
					field : 'minUseValue',
					title : '最低使用值',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(rec.useType == "1" ){
					       	return (val/100).toFixed(2);
					    }else{
					    	return "无";
					    }
					}
				},{
					field : 'maxDeductionValue',
					title : '封顶额',
					width : $(this).width() * 0.2,
					align : 'center',
					formatter : function(val, rec) {
						if(rec.useType == "2"){
							if(val == null || val == 0|| val == "null"|| val == "0"){
								return "无";
							}else{
					       		return (val/100).toFixed(2);
							}
					    }else{
					    	return "无";
					    }
					}
				},{
					field : 'applyDep',
					title : '申请部门',
					width : $(this).width() * 0.3,
					align : 'center'
				},{
					field : 'applyUser',
					title : '申请人',
					width : $(this).width() * 0.3,
					align : 'center'
				}
				] ],
				pagination : true,
				rownumbers : true
			});
			
			
			//添加用户链接
			$("#btnAdd").bind("click",function(){
				clearForm();
			    $("#start_time").datetimebox({  
	    			required : true,  
	   			 	onShowPanel:function(){  
	        		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
	    			}  
				});
			    $("#end_time").datetimebox({  
	    			required : true,
	   			 	onShowPanel:function(){  
	        		$(this).datetimebox("spinner").timespinner("setValue","23:59:59");
	    			} 
				});
			 	$("#addView").dialog("open").dialog("setTitle", "添加优惠券");
                $('.maxDeductionVal').hide(); 
                $('.minUseVal').hide();
                $('.coupon_val_type').show(); 
                $('.coupon_discount_type').hide();
                $('#balance').textbox({    
                    required:true,    
                });  
			});
			
			
			
			//构造对话框
			$("#addView").dialog( {
				width : "600",
				height : "450",
				top : "80",
				left:"30%",
				modal:true,
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "coupon_saveOrUpdate.do",
							onSubmit : function() {
								$.messager.progress({
				                    text:"正在加载，请稍等！"
				                });
				                var flag = $(this).form("validate");
				                if(!flag){
				                	$.messager.progress('close'); 
				                }
				                var useType = $("#useType").combobox("getValue");
			                	var balance = $("#balance").textbox("getValue");
			                	if(balance < 0 ){
			                		$.messager.alert("提示", "面值不能为负数", "error");
			                		$.messager.progress('close'); 
			                		flag = false;
			                	}
				                if(useType == '2'){
			                		var discount = $("#discount").textbox("getValue");
			                		if(discount < 0 ){
				                		$.messager.alert("提示", "折扣不能为负数", "error");
				                		$.messager.progress('close'); 
				                		flag = false;
				                	}
									 if(discount > 10 ){
				                		$.messager.alert("提示", "折扣不能大于10", "error");
				                		$.messager.progress('close'); 
				                		flag = false;
				                	}
				                }
								return flag;
							},
							success : function(result) {
								$.messager.progress('close'); 
								if (result == "success") {
									$.messager.alert("提示", "保存成功", "info");
								    $("#dataGrid").datagrid("reload");
									$("#addView").dialog("close");
								}else if(result == "num-wrong"){
									$.messager.alert("提示", "请输入正确最低使用值", "info");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "保存失败", "info");
								    $("#dataGrid").datagrid("reload");
									$("#addView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#addView").dialog("close");
					}
				}]
			});
			
			
			//删除操作
			$("#btnRemove").bind("click",function(){
		      		var selectedRow = $("#dataGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要删除的策略", "error");
		   		}else{
					var JsonData = selectedRow[0];
					$.messager.confirm("提示","确定要删除吗?",function(r){
			      		if(r){
							$.post("coupon_del.do",{"id":JsonData.id},function(data){
								if(data=="success"){
									//$.messager.alert("提示", "删除成功", "info");
								    $("#dataGrid").datagrid("reload");
								}else{
									$.messager.alert("提示", "删除失败", "info");
								}
							},"text");
						}
					});
		  		}	     
			});
			
				//清空
			$("#clearQuery").bind("click",function(){
				clearQueryForm();
			});
				
			
			//发放团体优惠券
			$("#setGroupCoupon").bind("click",function(){
				$("#groupCouponViewForm").form("clear");
				$("#groupCouponView").dialog({title:"发放团体优惠券"});
				$("#groupCouponView").dialog("open");
				/* var selectedRows = $("#dataGrid").datagrid("getSelections");
				 if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要发放的优惠券的记录", "error");
				 }else{
				 	if(selectedRows[0].status == 1) {
				 		$.messager.alert("提示", "优惠券已经被领用", "error");
				 	}else{
				 		$("#c-couponNo").val(selectedRows[0].couponNo); 
				 		$("#c-id").val(selectedRows[0].id); 
						//团体优惠券
						$("#groupCouponView").dialog("open");
				 	}
			 	} */
			});
			
			//发放团体优惠券
			$("#groupCouponView").dialog( {
				width : "480",
				height : "340",
				top : "100",
				modal:true,
				left:'450',
				buttons : [ {
					text : "发送",
					iconCls : "icon-save",
					handler : function() {
						$.messager.progress({
							text:"正在发送中，请稍等！"
						});
						$("#groupCouponViewForm").form("submit", {
							url : "bindGroupCoupon",
							success : function(result) {
								$.messager.progress('close'); 
								if (result == "succ") {
									$.messager.alert("提示", "绑定团体优惠券成功", "info");
								    $("#dataGrid").datagrid("reload");
									$("#groupCouponView").dialog("close");
								}else if(result == "over"){
									$.messager.alert("提示", "绑定优惠券失败：优惠券已失效");
								    $("#dataGrid").datagrid("reload");
									$("#groupCouponView").dialog("close");
								}else if(result == "member_empty"){
									$.messager.alert("提示", "绑定优惠券失败:用户不能为空");
								    $("#dataGrid").datagrid("reload");
									$("#groupCouponView").dialog("close");
								}else {
									$.messager.alert("提示", "绑定团体优惠券失败：\r\n"+result, "info");
								    $("#dataGrid").datagrid("reload");
									$("#groupCouponView").dialog("close");
								}
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#groupCouponView").dialog("close");
					}
				}]
			});
			
				//设置优惠券
			$("#setCoupon").bind("click",function(){
				$("#couponViewForm").form("clear");
				$("#couponView").dialog({title:"绑定优惠券"});
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				 if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要绑定的优惠券的记录", "error");
				 }else{
				 	if(selectedRows[0].status == 1) {
				 		$.messager.alert("提示", "优惠券已经被领用", "error");
				 	}else if(selectedRows[0].status == 2){
				 		$.messager.alert("提示", "优惠券已经作废", "error");
				 	}else{
				 		$("#c-couponNo").val(selectedRows[0].couponNo); 
				 		$("#c-id").val(selectedRows[0].id); 
						//根据组区域获取电子围栏
						$("#couponView").dialog("open");
				 	}
			 	}
			});
			
			//构造对话框
			$("#couponView").dialog( {
				width : "480",
				height : "180",
				top : "100",
				modal:true,
				left:'400',
				buttons : [ {
					text : "发送",
					iconCls : "icon-save",
					handler : function() {
						$.messager.progress({
							text:"正在发送中，请稍等！"
						});
						$("#couponViewForm").form("submit", {
							url : "bindCoupon",
							success : function(result) {
								$.messager.progress('close'); 
								if (result == "succ") {
									$.messager.alert("提示", "绑定优惠券成功", "info");
								    $("#dataGrid").datagrid("reload");
									$("#couponView").dialog("close");
								}else if(result == "notMember"){
									$.messager.alert("提示", "绑定优惠券失败：会员不存在", "info");
								}else {
									$.messager.alert("提示", "绑定优惠券失败："+result, "info");
								    $("#dataGrid").datagrid("reload");
									$("#couponView").dialog("close");
								}
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#couponView").dialog("close");
					}
				}]
			});
			//修改优惠券链接
			$("#btnEdit").bind("click",function(){
				$("#updateForm").form("clear");
				var selectedRows = $("#dataGrid").datagrid("getSelections");
				if(selectedRows.length <= 0){
						$.messager.alert("提示", "请选择要修改的记录", "error");
				}else{
					if(selectedRows[0].status == 2) {
				 		$.messager.alert("提示", "优惠券已经作废", "error");
				 	}else{
					 	$("#uid").val(selectedRows[0].id);
						//$("#status").combobox('setValue',selectedRows[0].status); 
						$("#updateView").dialog({title:"设置作废"});
						$("#updateView").dialog("open")
				 	}
				}
			});
			//弹出优惠券保修改存对话框
			$("#updateView").dialog( {
				width : "300",
				height : "180",
				top : "80",
				modal:true,
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#updateForm").form("submit", {
							url : "coupon_Update.do",
							onSubmit : function() {
								var selectedRows = $("#dataGrid").datagrid("getSelections");
								var status = selectedRows[0].status;
								if(status=='2'){    
									$.messager.alert("提示", "优惠券已作废", "info");   
									return false;
						        }
								$.messager.progress({
				                    text:"正在加载，请稍等！"
				                });
				                var flag = $(this).form("validate");
				                if(!flag){
				                	$.messager.progress('close'); 
				                }
								return flag;
							},
							success : function(result) {
								$.messager.progress('close'); 
								if (result == "success") {
									$.messager.alert("提示", "保存成功", "info");
								    $("#dataGrid").datagrid("reload");
									$("#updateView").dialog("close");
								}else{
									$.messager.alert("提示", "保存失败", "info");
								    $("#dataGrid").datagrid("reload");
									$("#updateView").dialog("close");
								} 
							}
						});
						}
					}, {
						text : "取消",
						iconCls : "icon-cancel",
						handler : function() {
							$("#updateView").dialog("close");
					}
				}]
			});
			
		});
		function clearForm(){
			$('#addForm').form('clear');
		}
		function clearQueryForm(){
			$('#queryForm').form('clear');
		}
		$(function(){
			$("#pub").bind("click",function(){
				$("#pubView").dialog("open").dialog("setTitle", "用户列表");
				$('#pubView').window('open').window('center');
			})
		})
		$(function(){
			$('#sysUserGrid').datagrid(
			{
				width : '700',
				height : '500',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				singleSelect : true,
				url : 'sys_user_list',
				pageSize : 40,
				pageList : [40,30,20,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox : true

				}
				, {
					field : 'cityName',
					title : '所属区域',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'name',
					title : '姓名',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'account',
					title : '登陆账号',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
					field : 'phone',
					title : '手机号码',
					width : $(this).width() * 0.08,
					align : 'center'
				}, {
						field : 'email',
						title : '邮箱',
						width : $(this).width() * 0.08,
						align : 'center'
				}, {
						field : 'status',
						title : '是否启用',
						width : $(this).width() * 0.08,
						align : 'center',
						formatter : function(val, rec) {
							if (val== "1") {
								return "<font color='#FF792B'>启用</font>";
							} else if(val == "0"){
								return "<font color='#3BFF3B'>未启用</font>";	
							}else {
								return "<font color='#2414FF'>异常</font>";
							}
							}
				}
				] ],
				pagination : true,
				rownumbers : true
			});
			$("#pubView").dialog( {
				width : "700",
				height : "600",
				top : "80"
			});
		})
		$(function(){
			$("#btn").bind("click", function() {
				search();
			});
			
			$("#btnGive").bind("click",function(){
		      	var selectedRow = $("#sysUserGrid").datagrid("getSelections");
		   		if(selectedRow.length <= 0){
					$.messager.alert("提示", "请选择要设置的用户", "error");
		   		}else if(selectedRow[0].status == "0"){
		   			$.messager.alert("提示", "该用户未启用", "error");
		   		}else{
				   	var JsonData = selectedRow[0];
					$("#issueAuthority").val(JsonData.id);
					$.messager.alert("提示", "设置成功 ", "info");
					console.log($("#issueAuthority"));
					$("#pubView").dialog("close");
		  		}	     
			});
		})
		function search() {
		var LoginUAcc = $.trim($("#LoginUAcc").val());
		var LoginUName = $.trim($("#LoginUName").val());
		$('#sysUserGrid').datagrid('load', {
			account : LoginUAcc,
			name : LoginUName
		});
	}
	
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
			所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
				data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
			标题:<input id="_title"  name="_title" class="easyui-textbox" style="width:80px"/>
			批次号:<input id="_batch_no"  name="_batch_no" class="easyui-textbox" style="width:80px" />
			优惠券编号:<input id="_coupon_no"  name="_coupon_no" class="easyui-textbox" style="width:80px" />
			是否领用:<input class="easyui-combobox" name="_status" id="_status" style="width:80px"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto'
	                    ">
			是否使用:<input class="easyui-combobox" name="_use_status" id="_use_status" style="width:80px"
					data-options=" url:'sys_dic?dicCode=YES_NO',
	                    method:'get',
	                    valueField:'code',
	                    textField:'name',
	                    panelHeight:'auto',
	                    editable:false">
	                   会员姓名:<input class="easyui-textbox" style="width:80px" id="memberName" name="memberName"/>
	                   手机号码:<input class="easyui-textbox" style="width:100px" id="memberPhone" name="memberPhone"/> 
	                   申请部门:<input class="easyui-combobox" style="width:100px" id="_applyDep" name="_applyDep" 
			           			data-options="
			                    valueField:'value',
			                    textField:'label',
			                    data: [{
									label:'全部',
									value:''
								},{
									label:'企划中心',
									value:'企划中心'
								},{
									label:'网络发展中心',
									value:'网络发展中心'
								},{
									label:'用户管理中心',
									value:'用户管理中心'
								},{
									label:'运营中心',
									value:'运营中心'
								},{
									label:'研发中心',
									value:'研发中心'
								},{
									label:'人事行政中心',
									value:'人事行政中心'
								},{
									label:'总经办',
									value:'总经办'
								},{
									label:'财务中心',
									value:'财务中心'
								}],
			                    panelHeight:'auto'" >	 
	                   额度(元):&nbsp;<input class="easyui-textbox" style="width:50px" id="minAmount" name="minAmount"/> - 
                             <input class="easyui-textbox" style="width:50px" id="maxAmount" name="maxAmount"/>					   
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            
            <r:FunctionRole functionRoleId="add_coupon">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="coupon_expire">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">设置作废</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="delete_coupon">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="bind_coupon">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setCoupon" onclick=";">绑定优惠券</a>
            </r:FunctionRole>
            <r:FunctionRole functionRoleId="give_group_coupon">
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-sprocket_dark" plain="true" id="setGroupCoupon" onclick=";">发放团体优惠券</a>
            </r:FunctionRole>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExcel">excel模板下载</a>
            
         </div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" >
			<form id="addForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<input type="hidden" name="issueAuthority" id="issueAuthority">
				<table cellpadding="8"  style="font-size: 12px;margin: auto;margin-top: 10px;width:auto;">
					<tr>
						<td>
							<div style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left; width:30%">
									所属区域:
								</span>
								<span style="float:left;width:50%">
									<input class="easyui-combobox" name="city_code" id="city_code"  
										data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
								</span>
							</div>
							
							<div style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								
								<span style="float:left; width:30% ;">
									标题:
								</span>
								<span style="float:left; width:50%"">
									<input class="easyui-textbox"  id="title" name="title"  data-options="required:true,missingMessage:'标题不能为空'">
								</span>
							</div>
							
							<div style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								
								<span style="float:left; width:30% ;">
									优惠券类型:
								</span>
								<span style="float:left; width:50%"">
									<input class="easyui-combobox" id="useType" name="useType" 
			           			data-options="
			                    valueField:'value',
			                    textField:'label',
			                    data: [{
									label:'现金券',
									value:'0'
								},{
									label:'满减券',
									value:'1'
								},{
									label:'折扣券',
									value:'2'
								}],
								onSelect: function(rec){
								  if(rec.value=='1'){
								   	 $('.maxDeductionVal').hide();
								   	 $('.coupon_discount_type').hide(); 
								   	 $('.coupon_val_type').show(); 
                                     $('.minUseVal').show(); 
								     $('#minUseValue').textbox({    
                                       required:true,    
                                      }); 
								     $('#coupon_val_type').textbox({    
                                       required:true,    
                                      }); 
								     $('#discount').textbox({    
                                       required:false,    
                                      }); 
                                   }else if(rec.value == '2'){ 
                                     $('.maxDeductionVal').show(); 
                                     $('.minUseVal').hide();
                                     $('.coupon_discount_type').show(); 
								   	 $('.coupon_val_type').hide(); 
								     $('#minUseValue').textbox({    
                                       required:false,    
                                      }); 
								     $('#balance').textbox({    
                                       required:false,    
                                      }); 
								     $('#discount').textbox({    
                                       required:true,    
                                      }); 
								  } else{
								 	 $('.maxDeductionVal').hide(); 
                                     $('.minUseVal').hide();
                                     $('.coupon_val_type').show(); 
                                     $('.coupon_discount_type').hide();
								     $('#minUseValue').textbox({    
                                       required:false,    
                                      });  
								     $('#balance').textbox({    
                                       required:true,    
                                      });  
								     $('#discount').textbox({    
                                       required:false,    
                                      });  
								  }    
                                 },
			                    panelHeight:'auto'"  required="required">
		    					</span>						
							</div>
						
							
							<div class="coupon_val_type" style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								 面额(元): 
								</span>
								<span style="float:right;">
									<input class="easyui-textbox"  name="balance" id="balance"  data-options="required:true,missingMessage:'面额不能为空',groupSeparator:','">
								</span>
							</div>
							
							<div class="coupon_discount_type" style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								 折扣(满折10): 
								</span>
								<span style="float:right;">
									<input class="easyui-textbox"  name="discount" id="discount"  data-options="required:true,missingMessage:'面额不能为空',groupSeparator:','">								</span>
								</span>
							</div>
							
							<div  class="minUseVal" style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								最低使<br/>用值(元):
								</span>
								<span style="float:right;">
									<input class="easyui-textbox"  name="minUseValue" id="minUseValue"  data-options="missingMessage:'对满减券与折扣券有效',groupSeparator:','">
								</span>
							</div>
							
							<div class="maxDeductionVal" style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								封顶额(元):
								</span>
								<span style="float:right;">
									<input class="easyui-textbox"  name="maxDeductionValue" id="maxDeductionValue"   data-options="missingMessage:'对满减券与折扣券有效',groupSeparator:','">
								</span>
							</div>
							
							<div  style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								数量:
								</span>
								<span style="float:right;">
									<input class="easyui-numberbox"  name="number" id="number"  data-options="required:true,missingMessage:'张数不能为空',groupSeparator:','">
								</span>
							</div>
							
							<div style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								生效时间:
								</span>
								<span style="float:right;">
									<input class="easyui-datetimebox" style="width:172px;" name="start_time" id="start_time"  data-options="required:true,missingMessage:'生效时间不能为空',groupSeparator:','">
								</span>
							</div>
							
							<div  style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								失效时间:
								</span>
								<span style="float:right;">
									<input class="easyui-datetimebox"  style="width:172px;" name="end_time" id="end_time"  data-options="required:true,missingMessage:'失效时间不能为空',precision:0">
								</span>
							</div>
							
							<div  style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								描述:
								</span>
								<span style="float:right;">
									<input class="easyui-textbox"  id="description" name="description"  >
								</span>
							</div>
							
							<div  style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								发放权限:
								</span>
								<span >
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="radio"  value="" id ="priAttr" name="attribute" checked="checked" style="cursor:pointer;">公共
					    				&nbsp;&nbsp;&nbsp;
					    			<input type="radio"  value="" name="attribute" id="pub" style="cursor:pointer;">私有
								</span>
							</div>
							
							<div  style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								申请部门:
								</span>
								<span style="float:right;">
									<input class="easyui-combobox" id="applyDep" name="applyDep" 
			           			data-options="
			                    valueField:'value',
			                    textField:'label',
			                    data: [{
									label:'企划中心',
									value:'企划中心'
								},{
									label:'网络发展中心',
									value:'网络发展中心'
								},{
									label:'用户管理中心',
									value:'用户管理中心'
								},{
									label:'运营中心',
									value:'运营中心'
								},{
									label:'研发中心',
									value:'研发中心'
								},{
									label:'人事行政中心',
									value:'人事行政中心'
								},{
									label:'总经办',
									value:'总经办'
								},{
									label:'财务中心',
									value:'财务中心'
								}],
			                    panelHeight:'auto'"   required="required">	  
								</span>
							</div>
							
							<div  style='position: relative; width: 250px; height: 40px; float: left;margin: 5px;'>
								<span style="float:left;">
								申请人:
								</span>
								<span style="float:right;">
									<input class="easyui-textbox"  id="applyPerson" name="applyPerson"  data-options="required:true,missingMessage:'标题不能为空'">
								</span>
							</div>
							
						</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 
		 <div id="pubView" class="easyui-dialog" closed="true" style="padding:10px 0px 40px 0px">
		 	<form action="">
		 		登陆账号:<input type="text" id="LoginUAcc"  class="easyui-textbox">
				姓名:<input type="text" id="LoginUName"  class="easyui-textbox">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btn" onclick=";">查询</a>
            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnGive">设置权限</a>
			</form>
			  <table id="sysUserGrid" style="padding:50px 0px 50px 0px;"></table>
		 </div>
		 
	<!-- view -->
	  <div id="couponView" class="easyui-dialog" closed="true"
			style="padding:10px 20px">
	       <form name="couponViewForm" id="couponViewForm" method="post" >
	       		<input type="hidden" name='couponNo' id="c-couponNo">
	       		<input type="hidden" name='id' id="c-id">
			   <table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
			   		<tr>
		    			<td>绑定类型:</td>
		    			<td>
		    				<input class="easyui-combobox" name="category" id="c-category"
							data-options="
							valueField: 'value',
							textField: 'label',
							panelHeight:'auto',
							data: [{
								label: '个人',
								value: '1'
							},{
								label: '企业',
								value: '2'
							}]">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>会员手机号:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="c-phone" name="phone" style="width:100%;height:24px" >
		    			</td>
		    		</tr>
		    	</table>	
	       </form>
	   </div> 
	   
	   <!-- 发放团体优惠券 -->
	   <div id="groupCouponView" class="easyui-dialog" closed="true"
			style="padding:10px 20px">
	       <form name="groupCouponViewForm" id="groupCouponViewForm" enctype="multipart/form-data" method="post" >
			   <table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
			   		<tr>
		    			<td>优惠券批次号:</td>
		    			<td>
		    				<input class="easyui-textbox"  id="batchNo" name="batchNo" 
		    					data-options="required:true" style="width:100%;height:24px" >
		    			</td>
		    			
		    		</tr>
		    		<tr>
		    			<td>填写数量:</td>
		    			<td>
		    				<input class="easyui-textbox"  id="couponNum" name="couponNum" 
		    					data-options="required:true" style="width:100%;height:24px" >
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>会员手机号:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="phoneList" name="phoneList" style="width:100%;height:60px" data-options="multiline:true"  >
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>EXCEL模板</td>
		    			<td>
		    				<input type="file" name="file" id="file"/>
		    			</td>
		    		</tr>
		    	</table>	
	       </form>
	   </div> 
   		<!-- updateView -->
   		<div id="updateView" class="easyui-dialog" closed="true"
			style="padding:10px 20px">
	       <form name="updateForm" id="updateForm" method="post" >
	       		<input type="hidden" name="uid" id="uid" />
			   <table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
			   		<tr>
		    			<td>是否作废:</td>
			    			<td>
			    			 	<input class="easyui-combobox" name="status" id="status" style="width:100px;height:24px"
									data-options="
					                    valueField:'value',
					                    textField:'label',
					                    data: [{
											label: '作废',
											value: '2'
										}],
					                    panelHeight:'auto'" required="true"  missingMessage="请选择状态">
			    			</td>
			    	</tr>
		    	</table>	
	       </form>
	   </div>
	</body>
</html>