<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>抽奖活动管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<script type="text/javascript" src="activity/lotteryManagePage.js"></script>
<script type="text/javascript">
	$(function(){
		//开始时间默认00:00:00
	    $(".s").datetimebox({  
  			 	onShowPanel:function(){  
       		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
   			}  
		});
		//结束时间默认23:59:59  
	   $(".e").datetimebox({  
  			 	onShowPanel:function(){  
       		$(this).datetimebox("spinner").timespinner("setValue","23:59:59");  
   			} 
		});  
	});
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
	<table id="grid" toolbar="#toolbar"></table>
	<div id="toolbar" style="height:auto">
			所属城市:
			<input class="easyui-combobox" name="cityCode" id="s-cityCode"
						data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'">
			标题:<input type="text" name="title" class="easyui-textbox" id="s-title">
			活动类型:<input name="activityType" id="s-activityType" class="easyui-combobox"
				data-options=" url:'sys_dic?dicCode=ACTIVITY_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'code',
	                    panelHeight:'auto'"/>
			状态:<input class="easyui-combobox" name="status" id="s-status"
					data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
							label: '全部',
							value: ''
	                    },{
							label: '关闭',
							value: '0'
	                    },{
							label: '开启',
							value: '1'
						}]">  
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear" onclick=";">清除</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
	  </div>
	  <div id="addViewLottery" class="easyui-dialog" closed="true" style="padding:10px 10px">
       <form name="addLotteryForm" id="addLotteryForm" method="post" >
       	   <input type="hidden" name="id" id="t-id" />
	       <div class="fitem">
	       		<lable>所属城市:</lable>
	       		<input class="easyui-combobox" name="cityCode" id="t-cityCode"
						data-options=" url:'sys_cityCombobox',
		                    method:'get',
		                    valueField:'id',
		                    textField:'text',
		                    panelHeight:'auto'" style="width: 75%;" required="true" >
	       </div>
	       <div class="fitem">
	       		<lable>标题:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</lable>
	       		<input name="shareText" id="t-shareText" class="easyui-textbox" style="width: 75%;" required="true"/>
	       </div>
	        <div class="fitem">
	       		<lable>活动类型:</lable>
	       		<input name="activityType" id="t-activityType" class="easyui-combobox"
				data-options=" url:'sys_dic?dicCode=ACTIVITY_TYPE',
	                    method:'get',
	                    valueField:'code',
	                    textField:'code',
	                    panelHeight:'auto'" style="width: 75%;" required="true"/>
	       </div>
	        <div class="fitem">
	       		<lable>开始时间:</lable>
	       		<input name="startTime" id="t-startTime" class="easyui-datetimebox s" style="width: 75%;" data-options="required:true,missingMessage:'开始时间不能为空'"/>
	       </div>
	        <div class="fitem">
	       		<lable>结束时间:</lable>
	       		<input name="endTime" id="t-endTime" class="easyui-datetimebox e" style="width: 75%;" data-options="required:true,missingMessage:'结束时间不能为空'"/>
	       </div>
	       <div class="fitem">
	       		<lable>分享url:&nbsp;&nbsp;&nbsp;</lable>
	       		<input name="shareUrl" id="t-shareUrl" class="easyui-textbox" style="width: 75%;" required="true"/>
	       </div>
       </form>
	</div>
	
	<div id="getprizeNameView" class="easyui-dialog" closed="true" style="padding: 18px">
		 <div id="toolbar1" style="height:auto">
		 	奖品名称:<input type="text" name="prizeName" class="easyui-textbox" id="s-prizeName">
		 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQueryLotteryItem" onclick=";">查询</a>
		 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSaveLotteryItem" onclick=";">添加</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEditLotteryItem" onclick=";">修改</a>
		 	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClearLotteryItem" onclick=";">清除</a>
			 <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="l-btn-icon icon-luck-cloud-sprocket_dark" plain="true" id="lottery_coupon" onclick=";">配置优惠券策略</a>
		 	<!-- <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemoveLotteryItem">删除</a> -->
		 </div>
		 <table id="getprizeNameGrid" toolbar="#toolbars1"></table>
	</div>
	<div id="addView" class="easyui-dialog" closed="true" style="padding:10px 10px">
       <form name="addViewForm" id="addViewForm" method="post" >
       	   <input type="hidden" name="id" id="id" />
	       <div class="fitem">
	       		<lable>项目名称:</lable>
	       		<input name="prizeName" id="e-prizeName" class="easyui-textbox" style="width: 75%;" required="true"/>
	       </div>
	       <div class="fitem">
	       		<lable>奖品数量:</lable>
	       		<input name="prizeAmount" id="e-prizeAmount" class="easyui-textbox" style="width: 75%;" required="true"/>
	       </div>
	       <div class="fitem">
	       		<lable>剩余数量:</lable>
	       		<input name="prizeRestAmount" id="e-prizeRestAmount" class="easyui-textbox" style="width: 75%;" required="true"/>
	       </div>
	       <div class="fitem">
	       		<lable>奖品概率:</lable>
	       		<input name="prizeWeight" id="e-prizeWeight" class="easyui-textbox" style="width: 75%;" required="true" missingMessage="注意，奖品概率总和为1!"/>
	       </div>
       </form>
	</div>

	<div id="lotteryCouponView" class="easyui-dialog" closed="true" style="padding: 18px">
		<div id="lotteryCoupon" style="height:auto">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSaveLotteryCoupon" onclick=";">添加</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEditLotteryCoupon" onclick=";">修改</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemoveLotteryCoupon">删除</a>
		</div>
		<table id="lotteryCouponGrid" toolbar="#toolbars2"></table>
	</div>

	<div id="addCoupnView" class="easyui-dialog" closed="true" style="padding:10px 10px">
		<form name="addViewForm" id="addCoupnViewForm" method="post" >
			<input type="hidden" name="id" />
			<input type="hidden" name="lotteryDrawItemId" id="lotteryDrawItemId">
			<div class="fitem">
				<lable>优惠券类型:</lable>
				<input class="easyui-combobox" name="useType" id="useType" required="true" style="width: 100px"
					   data-options="valueField:'value',
	                    textField:'label',
	                    panelHeight:'auto',
	                    data: [{
							label: '现金券',
							value: '1'
	                    },{
							label: '满减券',
							value: '2'
	                    },{
							label: '折扣券',
							value: '3'
						}]">
			</div>
			<div class="fitem">
				<lable>优惠券数量:</lable>
				<input name="couponAccount"  class="easyui-numberbox" style="width: 100px"  required="true"/>
			</div>
			<div class="fitem">
				<lable>优惠券最低面值:</lable>
				<input name="minBalance"  class="easyui-numberbox" style="width: 80px" precision="2" required="true"/>
			</div>
			<div class="fitem">
				<lable>优惠券最高面值:</lable>
				<input name="maxBalance"  class="easyui-numberbox" style="width: 80px" precision="2" required="true"/>
			</div>
			<div class="fitem">
				<lable>满减券最低限额（元）:&nbsp;&nbsp;&nbsp;</lable>
				<input name="minUseValue"  id ="minUseValue" class="easyui-numberbox" style="width: 70px" precision="2" required="true"/>
			</div>
			<div class="fitem">
				<lable>折扣券最大抵扣额（元）:</lable>
				<input name="maxDeductionValue" id ="maxDeductionValue" class="easyui-numberbox" style="width: 70px" precision="2" required="true"/>
			</div>
			<div class="fitem">
				<lable>优惠券有效天数:</lable>
				<input name="deadline"  class="easyui-numberbox" style="width: 90px" required="true"/>
			</div>
		</form>
	</div>
</body>
</html>