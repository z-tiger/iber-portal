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
	<script type="text/javascript" src="dayLongRent/dayLongOrder.js"></script>
	<script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
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
	        <form id="queryForm" method="post" action="#" enctype="multipart/form-data" >
	        	 所属地区:<input class="easyui-combobox" name="cityCode" id="s-cityCode" style="width: 100px"
					data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
				  姓名:<input type="text" id="s-memberName"  class="easyui-textbox" style="width: 100px" >
				  手机号:<input type="text" id="s-phone"  class="easyui-textbox" style="width: 100px" >
				 车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn" style="width: 100px" >
		                    订单状态:<input class="easyui-combobox" name="orderStatus" id="s-orderStatus" style="width: 100px"
						data-options="valueField:'value',
		                    textField:'label',
		                    panelHeight:'auto',
		                    data: [{
		                    	label: '全部',
		                    	value: ''
		                    },{
								label: '取消',
								value: 'cancel'
							},{
								label: '预约',
								value: 'ordered'
							},{
								label: '用车',
								value: 'useCar',
								selected : true
							},{
								label: '完成',
								value: 'finish'
							}]">
		                    上车时间:<input id="s-bt" name="bt" class="easyui-datetimebox s" size="18" style="width: 150px"/>
			 	 到<input id="s-et" name="et" class="easyui-datetimebox e" size="18" style="width: 150px" />
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="changeLpn">更换车辆</a>
	        </form>
        </div>
		<div id="changeLpnView" class="easyui-dialog" closed="true" style="height: 500px">

		   <form name="changeLpnForm" id="changeLpnForm" method="post">
			   <input type="hidden" name="orderId" id="orderId">
			   <font color="red">当前订单车辆下线操作：</font>
			   <div class="fitem">
				   <label>车牌号码:</label><span id="r-lpn-2"></span>
			   </div>
			   <div class="fitem">
				   <label>负责人手机号:</label><input  name="responsiblePersonPhone" id="e-seats"  class="easyui-textbox"  required="true" style="width: 20%;"/>
					<lable>下线状态:</lable>
					<input class="easyui-combobox" name="status" id="status"  required="true"
					data-options="valueField:'value',
						textField:'label',
						panelHeight:'auto',
						data: [{
							label: '维修',
							value: '1'
						},{
							label: '维护',
							value: '2'
						}]">
			   </div>
			   <label>预计上线时间:&nbsp;&nbsp;</label><input id="queryDateFrom"  name="queryDateFrom" class="easyui-datetimebox" required="true" style="width: 20%;" />
			   <lable>下线&车辆更换原因:</lable>
			   <input name="reason" id="e-reason" class="easyui-textbox" style="width: 20%;" required="true"/>
			   <div class="fitem">
				   <font color="red">注意：下线后，原车系统将自动还到车辆所属运营城市的：</font> <font color="red"><strong >换车临时网点</strong></font>
			   </div>
		   </form>
			<div style="height: 100%;width: 100%;padding-bottom: 20px">
					所属城市:
					<input class="easyui-combobox" name="cityCode" id="scityCode" style="width: 100px"
						   data-options=" url:'sys_optional_cityCombobox',
						method:'get',
						valueField:'id',
						textField:'text',
						panelHeight:'auto'">
					车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="lpn" style="width: 100px">
					网点:<input type="text" name="parkName" class="easyui-textbox" id="s-parkName" style="width: 100px">
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="car_btnQuery">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-arrow_large_down" plain="true" id="upToDownQuery" onclick=";">剩余电量降序查询</a>
					<table id="grid" toolbar="#toolbar2"></table>
			</div>

		</div>
		<div id="changeLpnHistory" class="easyui-dialog" closed="true" style="height: 300px" >
			<table id="gridhistory" ></table>
		</div>
	<div id="expireDetailInfoView" class="easyui-dialog" closed="true" style="padding: 18px">
		 <div id="toolbar1" style="height:auto">
		 	<input type="hidden" name="orderId" id="s-orderId" />
		 	<input type="hidden" name="extendTsOrderId" id="s-extendTsOrderId" />
		 	<input type="hidden" name="orderStatus" id="s-orderStatus" />
<!-- 		 	<input type="hidden" name="extendTsOrderId" id="s-extendTsOrderId" /> -->
<!-- 		 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQueryExpireOrder" onclick=";">查询</a> -->
		 </div>
		 <table id="expireDetailInfoGrid" toolbar="#toolbars1"></table>
	</div>
	</body>
</html>
