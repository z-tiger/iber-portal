<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
	<title>日租价格管理</title>                                       <!-- metro -->
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
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
						memberName : memberName ,
						cityCode :cityCode,
						lpn : lpn,
						phone : phone
					}) ; 
				}
			});
			 
			//查询链接
			$("#btnQuery").bind("click",function(){
				var memberName = $.trim($("#s-memberName").val()) ;
				var cityCode = $('#s-cityCode').textbox('getValue') ;
				var lpn = $('#s-lpn').textbox('getValue');
				var phone = $('#s-phone').textbox('getValue');
				$('#dataGrid').datagrid('load',{
					memberName : memberName ,
					cityCode :cityCode,
					lpn : lpn,
					phone : phone
				}) ;  
			});
			
			
			$('#dataGrid').datagrid( {
				title : '日租退款异常列表',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dayRent_order_refund_list.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [ {
					field : 'ck',
					checkbox:true
				},{
					field : 'cityName',
					title : '所属地区',
					width : $(this).width() * 0.08,
					align : 'center'
				},{
					field : 'orderId',
					title : '订单号',
					width : $(this).width() * 0.15,
					align : 'center'
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
				},  {
					field : 'orderStatus',
					title : '订单状态',
					width : $(this).width() * 0.08,
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
				},{
					field : 'refundStatus',
					title : '退款状态',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == 'finish'){
							return '完成' ;
						}else if(val == 'refunding'){
							return '退款中' ;
						}else if(val == 'cancel'){
							return '' ;
						}
					}
				},{
					field : 'payMoney',
					title : '支付金额',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				   		   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			           return n.replace(re, "$1,");	
					} 
				}, {
					field : 'refundMoney',
					title : '退款金额',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "null")
					       return "";
					    else
					       n = (val/100).toFixed(2);
				   		   var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			   		   return n.replace(re, "$1,");	
					} 
				},
				{
					field : 'refundType',
					title : '退款方式',
					width : $(this).width() * 0.08,
					align : 'center',
					formatter : function(val, rec) {
						if(val == "B")
					       return "余额";
					    else if(val == "WX")
					       return "微信";
					    else if(val == "A")
					       return "支付宝";
					} 
				},{
					field : 'createTime',
					title : '创建时间',
					width : $(this).width() * 0.1,
					align : 'center'
				},
				{
					field : 'xq',
					title : '详情',
					width : $(this).width() * 0.05,
					align : 'center',
					formatter : function(val,row,index) {
						return "<a href='#' onClick=refund('"+row.orderId+"')>退款</a>" ;
					}
				}] ],
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
		 
		function refund(orderId){
		alert(orderId);
			$.messager.confirm("提示","确定要退款吗?",function(r){
	      		if(r){
					$.post("dayRent_order_platForm_refund.do",{"orderId":orderId},function(data){
						if(data=="success"){
							//$.messager.alert("提示", "删除成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "申请失败", "info");
						}
					},"text");
				}
			});	 
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
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
        </form>
	</body>
</html>