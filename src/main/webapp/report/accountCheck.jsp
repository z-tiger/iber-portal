<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8"> 
	<title>会员消费对账单</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	
	<script type="text/javascript" src="report/accountCheck.js"></script>	
	<script type="text/javascript" src="common/reportCommon.js"></script>
	<script type="text/javascript">
		$(function(){
			//开始时间默认:00:00:00
		    $("#begin_full").datetimebox({  
   			 	onShowPanel:function(){  
        		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");  
    			}  
			});
			//结束时间默认为23:59:59  
		   $("#end_full").datetimebox({  
   			 	onShowPanel:function(){  
        		$(this).datetimebox("spinner").timespinner("setValue","23:59:59");  
    			} 
			});  
  		}); 
	</script>	
	
	<style type="text/css" >
	#conversionForm {
		margin: 0;
		padding: 10px 30px;
	}
	
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
		margin-left:10px;  
 		margin-right:5px; 
 		display: inline-block;
	}
	
	.fitem input {
		width: 160px;
	}
	
	.fitem a {
		margin-right: 5px;
	}
	/* #queryTimeLease{
	  margin:0 auto;
	} */
	</style>
</head>

<body>
   
	<table id="dataGrid" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" class="fitem" style="height:auto;">
		<form id="accountCheckForm">
		<!-- 存放年月季度的值 -->
    	<input type="hidden" name='hidBegin' id="hidBegin" />
    	<input type="hidden" name='hidEnd' id="hidEnd" />
    	
    	<input type="hidden" name='hidYear' id="hidYear" />
    	<input type="hidden" name='hidMonth' id="hidMonth" />
    	<input type="hidden" name='hidQuarter' id="hidQuarter" />


        <%--存放支付的年月季度值--%>
        <input type="hidden" name='hidPayBeginTime' id="hidPayBeginTime" />
        <input type="hidden" name='hidPayEndTime' id="hidPayEndTime" />
        <input type="hidden" name='hidPayYear' id="hidPayYear" />
        <input type="hidden" name='hidPayMonth' id="hidPayMonth" />
        <input type="hidden" name='hidPayQuarter' id="hidPayQuarter" />

            所属城市:<input class="easyui-combobox" name="cityCode" id="s-cityCode"
					data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
		会员姓名:<input type="text" name="name" class="easyui-textbox" id="name">
		车辆品牌:<input class="easyui-combobox"  name="brandName" id="s-brandName" data-options=" url:'car/selectAllCarBrand',
                        method:'get',
                        valueField:'brance',
                        textField:'brance',
                        panelHeight:'auto'">
		周期:<input class="easyui-combobox" name="period" id="period" style="width:100px"
					data-options=" 
						valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '自选区间',
							value: '0'
						},{
							label: '月',
							value: '1'
						},{
							label: '季度',
							value: '2'
						},{
							label: '年',
							value: '3'
						}]">
            支付方式:<input class="easyui-combobox" name="payType" id="payType" style="width:100px"
                      data-options="
						valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '余额',
							value: 'B'
						},{
							label: '微信',
							value: 'WX'
						},{
							label: '支付宝',
							value: 'A'
						},{
							label: '企业余额',
							value: 'EB'
						}]">
            费用类型:<input class="easyui-combobox" name="consumptionType" id="consumptionType" style="width:100px"
                      data-options="
						valueField: 'value',
						textField: 'label',
						panelHeight:'auto',
						editable:false,
						data: [{
							label: '押金充值',
							value: '押金充值'
						},{
							label: '余额充值',
							value: '余额充值'
						},{
							label: '日租',
							value: '日租'
						},{
							label: '时租',
							value: '时租'
						},{
							label: '违章事故',
							value: '违章事故'
						},{
							label: '返余额',
							value: '返余额'
						},{
							label: '充电',
							value: '充电'
						},{
							label: '押金退款',
							value: '押金退款'
						}]">
			
			<!-- 时间区间 -->
			<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
			
			支付时间
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox"  name="begin_full" id="begin_full"
					   style="width:150px;height:24px" data-options="">
			</label>
			
			<label name="label_year">
				<input class="easyui-numberbox"  name="year" id="begin_year" 
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="begin_month" min="1" max="12" 
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				月
			</label>
			
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="begin_quarter" min="1" max="4" 
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				季度
			</label>
			
			<span>&nbsp;&nbsp;</span>
			到 
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox"  name="end_full" id="end_full"
					   style="width:150px;height:24px" data-options="">
			</label>
			
			<label name="label_year" >
				<input class="easyui-numberbox"  name="year" id="end_year" 
					<%--data-options="required:true, missingMessage:'数值要比开始的年份大'"--%>
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="end_month" min="1" max="12" 
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				月
			</label>
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="end_quarter" min="1" max="4" 
					<%--data-options="required:true"--%>
					style="width:100px;height:24px">
				季度
			</label>

		
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearBtn">清空</a>
			<r:FunctionRole functionRoleId="account_check_form_export_excel"></r:FunctionRole>
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
		</form>
	</div>
	
	
</body>
</html>