<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>出险信息管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript">
		var arr = new Array();
		var arrStr = "";
		
		/**
		 * 创建上传窗口 公共方法
		 * @param chunk 是否分割大文件
		 * @param callBack 上传成功之后的回调
		 */
		function Uploader(chunk,callBack){
			var addWin = $('<div style="overflow: hidden;"/>');
			var upladoer = $('<iframe/>');
			upladoer.attr({'src':'insurance_uploader_page.do?chunk='+chunk,width:'100%',height:'100%',frameborder:'0',scrolling:'no'});
			addWin.window({
				title:"上传图片",
				height:350,
				width:500,
				minimizable:false,
				modal:true,
				collapsible:false,
				maximizable:false,
				resizable:false,
				content:upladoer,
				onClose:function(){
					var fw = GetFrameWindow(upladoer[0]);
					var files = fw.files;
					$(this).window('destroy');
					callBack.call(this,files);
				},
				onOpen:function(){
					var target = $(this);
					setTimeout(function(){
						var fw = GetFrameWindow(upladoer[0]);
						fw.target = target;
					},100);
				}
			});
		}
		
		/**
		 * 根据iframe对象获取iframe的window对象
		 * @param frame
		 * @returns {Boolean}
		 */
		function GetFrameWindow(frame){
			return frame && typeof(frame)=='object' && frame.tagName == 'IFRAME' && frame.contentWindow;
		}
		 
		function makerUpload(chunk){
			Uploader(chunk,function(files){
	// 			alert(files.length);
				 if(files && files.length>0){
				 	var str = "";
				 	var arrlen = arr.length;
	// 			 	alert(arr.length);
				 	for(var i=0;i<files.length;i++){
				 		arr[arrlen+i] = files[i];
				 		var oldName = files[i].split("::")[0];
				 		str = str+oldName+" <img onclick=\"sctp('"+parseInt(arrlen+i)+"');\" src=\"<%=request.getContextPath()%>/images/close2.gif\" width=\"10px;\" height=\"10px;\" style=\"cursor: pointer;margin-top: 10px;margin-right: 30px;\" title=\"删除\"></img><br/>";
				 	}
				 	arrStr = arrStr + str;
				 	//alert(arr.length);
				 	$("#res").html(arrStr);
				 }
				 document.getElementById("uploud_url_arr").value = arr.join("@#@#@#@#@");
// 				 alert(document.getElementById("uploud_url_arr").value);
			});
		}
		
		function sctp(val){
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					//alert(val);
					//alert("elements: "+arr+"\nLength: "+arr.length); 
					arr.remove(val); //删除下标为1的元素 
					//alert("elements: "+arr+"\nLength: "+arr.length); 
					setVal(arr);
					document.getElementById("uploud_url_arr").value = arr.join("@#@#@#@#@");
					//alert(document.getElementById("uploud_url_arr").value);
				}
			});
		}
		
		function setVal(files){
			if(files && files.length>0){
			 	var str = "";
			 	for(var i=0;i<files.length;i++){
			 		arr[i] = files[i];
			 		var oldName = files[i].split("::")[0];
			 		str = str+oldName+" <img onclick=\"sctp('"+i+"');\" src=\"<%=request.getContextPath()%>/images/close2.gif\" width=\"10px;\" height=\"10px;\" style=\"cursor: pointer;margin-top: 10px;margin-right: 30px;\" title=\"删除\"></img><br/>";
			 	}
			 	arrStr = str;
			 	$("#res").html(str);
			}else{
				arrStr = "";
				$("#res").html("");
			}
		}
		
		/* 
		*  方法:Array.remove(dx) 通过遍历,重构数组 
		*  功能:删除数组元素. 
		*  参数:dx删除元素的下标. 
		*/ 
		Array.prototype.remove=function(dx) { 
		    if(isNaN(dx)||dx>this.length){return false;} 
		    for(var i=0,n=0;i<this.length;i++){ 
		        if(this[i]!=this[dx]) { 
		            this[n++]=this[i] ;
		        } 
		    } 
		    this.length-=1; 
		}
		
		
		$(function() {
			//查询链接
			$(document).keydown(function(event){
			    var lpn = $("input[name='lpn']").val();
			    var address = $("input[name='_address']").val();
			    var insurance_company = $("input[name='_insurance_company']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
				if(event.keyCode==13){
					$('#dataGrid').datagrid('load',{
				       lpn : lpn,
				       address : address,
				       insurance_company : insurance_company,
			           queryDateFrom:queryDateFrom,
			           queryDateTo:queryDateTo
		        	});
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				var lpn = $("input[name='lpn']").val();
			    var address = $("input[name='_address']").val();
			    var insurance_company = $("input[name='_insurance_company']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
			    $('#dataGrid').datagrid('load',{
		    	   lpn : lpn,
			       address : address,
			       insurance_company : insurance_company,
		           queryDateFrom:queryDateFrom,
		           queryDateTo:queryDateTo
	        	});
			});
			//导出excel
			$("#btnExport").bind("click", function() {
				var lpn = $("input[name='lpn']").val();
			    var address = $("input[name='_address']").val();
			    var insurance_company = $("input[name='_insurance_company']").val();
			    var queryDateFrom =$("input[name='queryDateFrom']").val();
			    var queryDateTo = $("input[name='queryDateTo']").val();
				$("#insuranceRecordForm").form("submit", {
					url : "export_insuranceRecord_list?address="
											+ address + "&lpn=" + lpn
											+ "&insurance_company="
											+ insurance_company
											+ "&queryDateFrom=" + queryDateFrom
											+ "&queryDateTo=" + queryDateTo,
									onSubmit : function() {
									},
									success : function(result) {
									}
								});

					});

			$('#dataGrid')
					.datagrid(
							{
								title : '出险信息管理',
								width : 'auto',
								height : 'auto',
								fit : true,
								fitColumns : false,
								nowrap : false,
								striped : true,
								collapsible : true,
								rownumbers : true,
								singleSelect : true,
								url : 'insuranceRecord_list.do',
								pageSize : 100,
								pageList : [ 100, 50, 30, 10 ],
								idField : 'id',
								pagination : true,
								rownumbers : true,
								columns : [ [
										{
											field : 'false',
											checkbox : true
										},
										{
											field : 'lpn',
											title : '车牌号',
											width : $(this).width() * 0.08,
											align : 'center',
											formatter : function(val) {
												if (val.indexOf("•") < 0) {
													return val.substring(0, 2)
															+ "•"
															+ val.substring(2);
												} else {
													return val;
												}
											}
										},
										{
											field : 'occurTime',
											title : '出险时间',
											width : $(this).width() * 0.12,
											align : 'center'
										},
										{
											field : 'address',
											title : '出险地址',
											width : $(this).width() * 0.15,
											align : 'center'

										},
										{
											field : 'describe',
											title : '描述',
											width : $(this).width() * 0.25,
											align : 'center'
										},
										{
											field : 'insuranceNo',
											title : '保单号',
											width : $(this).width() * 0.15,
											align : 'center'
										},
										{
											field : 'insuranceCompany',
											title : '承保公司',
											width : $(this).width() * 0.15,
											align : 'center'
										},
										{
											field : 'money',
											title : '赔偿金额(元)',
											width : $(this).width() * 0.06,
											align : 'center',
											formatter : function(val, row,
													index) {
												if (val == "null")
													return "";
												else
													n = (val / 100).toFixed(2);
												var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
												return n.replace(re, "$1,");
											}
										},
										{
											field : 'orderNo',
											title : '订单号',
											width : $(this).width() * 0.2,
											align : 'center'
										},
										{
											field : 'memberName',
											title : '会员姓名',
											width : $(this).width() * 0.08,
											align : 'center',

										},
										{
											field : 'memberPhone',
											title : '会员手机',
											width : $(this).width() * 0.08,
											align : 'center'

										},
										// 				{
										// 					field : 'createTime',
										// 					title : '创建时间',
										// 					width : $(this).width() * 0.12,
										// 					align : 'center'
										// 				},
										{
											field : 'isAttachment',
											title : '附件',
											width : $(this).width() * 0.05,
											align : 'center',
											formatter : function(val, row,
													index) {
												if (val == 0)
													return "";
												else
													return "<a href=\"javascript:showAttachment("
															+ row.id
															+ ")\" >"
															+ val + "</a>";
											}
										} ] ]
							});

			//添加用户链接
			$("#btnAdd").bind(
					"click",
					function() {
						clearForm();
						arr = [];
						arrStr = "";
						setVal(arr);
						document.getElementById("uploud_url_arr").value = arr
								.join("@#@#@#@#@");
						$("#addView").dialog("open").dialog("setTitle",
								"添加出险信息");
					});

			//编辑用户链接
			$("#btnEdit")
					.bind(
							"click",
							function() {
								clearForm();
								arr = [];
								arrStr = "";
								setVal(arr);
								document.getElementById("uploud_url_arr").value = arr
										.join("@#@#@#@#@");
								var selectedRow = $("#dataGrid").datagrid(
										"getSelections");
								if (selectedRow.length <= 0) {
									$.messager.alert("提示", "请选择要编辑的出险信息 ",
											"error");
								} else {
									var JsonData = selectedRow[0];
									$("#id").val(JsonData.id);
									// 					$("#lpn").combogrid("setValue",JsonData.lpn);
									if (JsonData.lpn.indexOf("•") < 0) {
										$("#lpn").textbox(
												"setValue",
												JsonData.lpn.substring(0, 2)
														+ "•"
														+ JsonData.lpn
																.substring(2));
									} else {
										$("#lpn").textbox("setValue",
												JsonData.lpn);
									}
									$("#address").textbox("setValue",
											JsonData.address);
									$("#occur_time").datetimebox("setValue",
											JsonData.occurTime);
									$("#order_no").textbox("setValue",
											JsonData.orderNo);
									$("#describe").textbox("setValue",
											JsonData.describe);
									// 					$("#insurance_company").combobox("setValue",JsonData.insuranceCompany);
									// 					$("#insurance_no").textbox("setValue",JsonData.insuranceNo);
									$("#money").numberspinner("setValue",
											(JsonData.money / 100).toFixed(2));
									$("#addView").dialog("open").dialog(
											"setTitle", "编辑出险信息");
								}
							});

			//构造对话框
			$("#addView").dialog({
				width : "500",
				height : "550",
				top : "0",
				buttons : [ {
					text : "保存",
					iconCls : "icon-save",
					handler : function() {
						$("#addForm").form("submit", {
							url : "insuranceRecord_saveOrUpdate.do",
							onSubmit : function() {
								$.messager.progress({
									text : "正在加载，请稍等！"
								});
								var flag = $(this).form("validate");
								if (!flag) {
									$.messager.progress('close');
								}
								return flag;
							},
							success : function(result) {
								$.messager.progress('close');
								if (result == "success") {
									$.messager.alert("提示", "保存成功", "info");
									$("#dataGrid").datagrid("reload");
									$("#addView").dialog("close");
								} else {
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
				} ]
			});

			//删除操作
			$("#btnRemove").bind("click", function() {
				var selectedRow = $("#dataGrid").datagrid("getSelections");
				if (selectedRow.length <= 0) {
					$.messager.alert("提示", "请选择要删除的出险信息", "error");
				} else {
					var JsonData = selectedRow[0];
					$.messager.confirm("提示", "确定要删除吗?", function(r) {
						if (r) {
							$.post("insuranceRecord_del.do", {
								"id" : JsonData.id
							}, function(data) {
								if (data == "success") {
									//$.messager.alert("提示", "删除成功", "info");
									$("#dataGrid").datagrid("reload");
								} else {
									$.messager.alert("提示", "删除失败", "info");
								}
							}, "text");
						}
					});
				}
			});

			//清空
			$("#clearQuery").bind("click", function() {
				//clearQueryForm();
			});

			//构造对话框
			$("#showAttachmentView").dialog({
				width : "500",
				height : "500",
				top : "30"
			});

			$("input", $("#lpn").next("span"))
					.blur(
							function() {
								var val = $("#lpn").textbox("getValue");
								if (IsLpn(val)) {
									$
											.post(
													"insuranceRecord_lpn_check.do",
													{
														"lpn" : val
													},
													function(data) {
														if (data == "success") {
															document
																	.getElementById('lpn_msg').innerHTML = "<font color='green'>验证通过！</font>";
														} else {
															document
																	.getElementById('lpn_msg').innerHTML = "<font color='red'>车牌号不存在！</font>";
															$('#lpn').textbox(
																	"setValue",
																	"");
														}
													}, "text");
								} else {
									document.getElementById('lpn_msg').innerHTML = "<font color='red'>验证失败！</font>";
									$("#lpn").textbox("setValue", "");
								}
							});

		});

		function IsLpn(lpn) {
			return (new RegExp(/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/)
					.test(lpn));
		}

		function clearForm() {
			document.getElementById('lpn_msg').innerHTML = "";
			$('#addForm').form('clear');
		}
		function clearQueryForm() {
			//$('#queryForm').form('clear');
		}

		//检查保险的格式是否正确,同时实现预览
		function setImagePreview(obj, localImagId, imgObjPreview) {
			var array = new Array('gif', 'jpeg', 'png', 'jpg', 'bmp', 'GIF',
					'JPEG', 'PNG', 'JPG', 'BMP'); // 可以上传的文件类型
			if (obj.value == '') {
				$.messager.alert("让选择要上传的出险信息!");
				return false;
			} else {
				var fileContentType = obj.value.match(/^(.*)(\.)(.{1,8})$/)[3]; // 这个文件类型正则很有用
				// //布尔型变量
				var isExists = false;
				// 循环判断保险的格式是否正确
				for ( var i in array) {
					if (fileContentType.toLowerCase() == array[i].toLowerCase()) {
						// 保险格式正确之后，根据浏览器的不同设置保险的大小
						if (obj.files && obj.files[0]) {
							// 火狐下，直接设img属性
							imgObjPreview.style.display = 'block';
							imgObjPreview.style.width = '250px';
							imgObjPreview.style.height = '150px';
							// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
							imgObjPreview.src = window.URL
									.createObjectURL(obj.files[0]);
						} else {
							// IE下，使用滤镜
							obj.select();
							var imgSrc = document.selection.createRange().text;
							// 必须设置初始大小
							localImagId.style.display = "";
							localImagId.style.width = "230px";
							// 保险异常的捕捉，防止用户修改后缀来伪造保险
							try {
								localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
								localImagId.filters
										.item("DXImageTransform.Microsoft.AlphaImageLoader=").src = imgSrc;
							} catch (e) {
								$.messager.alert("您上传的保险格式不正确，请重新选择!");
								return false;
							}
							imgObjPreview.style.display = 'none';
							document.selection.empty();
						}
						isExists = true;
						return true;
					}
				}
				if (isExists == false) {
					$.messager.alert("上传保险类型不正确!");
					return false;
				}
				return false;
			}
		}

		function display(id) {
			document.getElementById("box" + id).style.display = "block";
		}
		function disappear(id) {
			document.getElementById("box" + id).style.display = "none";
		}

		//图片预览
		function showAttachment(id) {
			$
					.post(
							"insuranceRecord_show_attachment.do",
							{
								"id" : id
							},
							function(data) {
								$("#showAttachmentView").dialog("open").dialog(
										"setTitle", "附件预览");
								$("#showAttachmentView").html("");
								data = $.parseJSON(data);
								var rs = data[0];
								var str = "";
								for ( var i = 0; i < rs.length; i++) {
									// 					str += "<img alt='"+rs[i].attachName+"' src='"+rs[i].attachUrl+"' width='400'>"
									str += " <a href='"+rs[i].attachUrl+"' target ='_blank'><img alt='"+rs[i].attachName+"' src='"+rs[i].attachUrl+"' width='400'></a>"
								}
								$("#showAttachmentView").html(str);
							}, "text");
		}
	</script> 
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	    <form action="" id="insuranceRecordForm">
	        	车牌号:<input id="_lpn"  name="lpn" class="easyui-textbox" style="width:100px;"/>
	        	出险地址:<input id="_address"  name="_address" class="easyui-textbox" style="width:150px;"/>
	        	承保公司:<input name="_insurance_company" id="_insurance_company" style="width:150px;" 
			    class="easyui-combobox"  data-options=" url:'sys_dic?dicCode=INSURANCE_COMPANY',method:'get',valueField:'code',textField:'name',panelHeight:'auto',editable:false">
	       		出险时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px;"/>
	          	到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px;"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a> -->
	            
	            <r:FunctionRole functionRoleId="add_car_happen_danger">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="update_car_happen_danger">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="delete__car_happen_danger">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="export_insuranceRecord">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
				</r:FunctionRole>
	          </form>
        </div> 
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 500px;height: 500px;">
			<form id="addForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<input type="hidden" name="uploud_url_arr" id="uploud_url_arr" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
					<tr>
		    			<td>车牌号:</td>
		    			<td width="250px;">
<!-- 		    			<input class="easyui-combogrid" name="lpn" id="lpn" style="width:300px;height:28px"  data-options=" -->
<!-- 								editable:false, -->
<!-- 								required:true, -->
<!-- 								panelHeight: 250, -->
<!-- 								idField: 'lpn', -->
<!-- 								textField: 'lpn', -->
<!-- 								url : 'car_mrg_list.do', -->
<!-- 								pageSize:10, -->
<!-- 								columns : [ [  -->
<!-- 								{ -->
<!-- 									field : 'lpn', -->
<!-- 									title : '车牌号', -->
<!-- 									width : '80%', -->
<!-- 									align : 'center' -->
<!-- 								}] ], -->
<!-- 								pagination : true, -->
<!-- 								rownumbers : true -->
<!-- 							" /> -->
							<input class="easyui-textbox" type="text" name="lpn" id="lpn" data-options="required:true,missingMessage:'车牌号不能为空'" style="width:100%;height:28px"></input>
							<span id="lpn_msg"></span>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>出险地址:</td>
		    			<td width="250px;"><input class="easyui-textbox" type="text" name="address" id="address" data-options="required:true,missingMessage:'出险地址不能为空'" style="width:100%;height:28px"></input></td>
		    		</tr>
		    		<tr>
		    			<td>出险时间:</td>
		    			<td width="250px;">
		    			<input class="easyui-datetimebox" type="text" name="occur_time" id="occur_time" data-options="required:true,missingMessage:'出险时间不能为空',editable:false" style="width:100%;height:28px">
		    			</input></td>
		    		</tr>
<!-- 		    		<tr> -->
<!-- 		    			<td>保单号:</td> -->
<!-- 		    			<td width="250px;"><input class="easyui-textbox" type="text" name="insurance_no" id="insurance_no" data-options="required:true,missingMessage:'保单号不能为空'" style="width:100%;height:28px"></input></td> -->
<!-- 		    		</tr> -->
<!-- 		    		<tr> -->
<!-- 		    			<td>承保公司:</td> -->
<!-- 		    			<td> -->
<!-- 			    			<input name="insurance_company" id="insurance_company" style="width:100%;height:28px" -->
<!-- 			    			class="easyui-combobox"  data-options=" url:'sys_dic?dicCode=INSURANCE_COMPANY',method:'get',valueField:'code',textField:'name',panelHeight:'auto',required:true,editable:false"> -->
<!-- 		    			</td> -->
<!-- 		    		</tr> -->
		    		<tr>
		    			<td>赔偿金额(元):</td>
		    			<td width="250px;"><input class="easyui-numberspinner" precision="2"  id="money" name="money" style="width:100%;height:28px" data-options="required:true,min:1,max:10000000,missingMessage:'赔偿金额不能为空'"></td>
		    		</tr>
		    		<tr>
		    			<td>用车订单号:</td>
		    			<td width="250px;">
		    			<input class="easyui-textbox" type="text" name="order_no" id="order_no" data-options="" style="width:100%;height:28px"></input>
<!-- 		    			<input class="easyui-combogrid" name="order_no" id="order_no" style="width:300px;height:28px"  data-options=" -->
<!-- 								editable:false, -->
<!-- 								panelHeight: 250, -->
<!-- 								idField: 'orderId', -->
<!-- 								textField: 'orderId', -->
<!-- 								url : 'timeShare_history_order_list.do', -->
<!-- 								pageSize:10, -->
<!-- 								columns : [ [  -->
<!-- 								{ -->
<!-- 									field : 'orderId', -->
<!-- 									title : '订单号', -->
<!-- 									width : '60%', -->
<!-- 									align : 'center' -->
<!-- 								},{ -->
<!-- 									field : 'memberName', -->
<!-- 									title : '会员姓名', -->
<!-- 									width : '20%', -->
<!-- 									align : 'center' -->
<!-- 								},{ -->
<!-- 									field : 'lpn', -->
<!-- 									title : '车牌号', -->
<!-- 									width : '20%', -->
<!-- 									align : 'center' -->
<!-- 								}] ], -->
<!-- 								pagination : true, -->
<!-- 								rownumbers : true -->
<!-- 							" /> -->
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>事故描述:</td>
		    			<td><input class="easyui-textbox" data-options="multiline:true" name="describe" id="describe" data-options="required:true,missingMessage:'事故描述不能为空'" style="width:100%;height:80px"></input></td>
		    		</tr>
		    		<tr>
		    			<td colspan="2">
		    			<div><a class="easyui-linkbutton" href="javascript:makerUpload(false)" style="width:100%;height:28px">图片附件上传</a> </div>
						<div id="res"></div>
						</td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 <div id="showAttachmentView" class="easyui-dialog" closed="true" style="width: 500px;height: 500px;" align="center">
		 </div>
	</body>
</html>