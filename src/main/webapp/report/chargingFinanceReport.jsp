<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>充电财务报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	
	<script type="text/javascript" src="report/chargingFinanceReport.js"></script>	
	<script type="text/javascript" src="common/checkCommon.js"></script>
    <script type="text/javascript" src="ui_lib/js/datetimeboxCommon.js"></script>
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

	</style>
</head>

<body>
   
	<table id="dataGrid" class="fitem" toolbar="#toolbar"></table>

	<!--列表工具栏 -->
	<div id="toolbar" class="fitem" style="height:auto">
		<form id="chargingReportForm">
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


            充电车辆所属公司:
		<input class="easyui-combobox" name="cityCode" id="cityCode"
					data-options=" url:'all_car_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
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
            支付状态:<select class="easyui-combobox" id="payStatus" name="payStatus"  style="width:80px;" data-options="editable:false,panelHeight:'auto'">
                        <option value="" selected></option>
                        <option value="finish">已支付</option>
                        <option value="noPay">未支付</option>
                    </select>
			
			<!-- 时间区间 -->
			<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
			
			开始充电时间：从 
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox"  name="begin_full" id="begin_full"
					   style="width:150px;height:24px" data-options="">
			</label>
			
			<label name="label_year">
				<input class="easyui-numberbox"  name="year" id="begin_year" 
					data-options="">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="begin_month" min="1" max="12" 
					data-options=""
					style="width:100px;height:24px">
				月
			</label>
			
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="begin_quarter" min="1" max="4" 
					data-options=""
					style="width:100px;height:24px">
				季度
			</label>
			
			<span>&nbsp;&nbsp;</span>
			到 
			<!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
			<label class="cls_label_datetime">
				<input class="easyui-datetimebox"  name="end_full" id="end_full"
					   style="width:150px;height:24px"  data-options="">
			</label>
			
			<label name="label_year" >
				<input class="easyui-numberbox"  name="year" id="end_year" 
					data-options=""
					style="width:100px;height:24px">
				年
			</label>
			<label name="label_month">
				<input class="easyui-numberbox"  name="month" id="end_month" min="1" max="12" 
					data-options=""
					style="width:100px;height:24px">
				月
			</label>
			<label name="label_quarter">第
				<input class="easyui-numberbox" name="quarter" id="end_quarter" min="1" max="4" 
					data-options=""
					style="width:100px;height:24px">
				季度
			</label>

            支付时间:
            <label class="cls_label_datetime">
                <input class="easyui-datetimebox s"  name="pay_begin_full" id="pay_begin_full" style="width:150px;height:24px">
            </label>

            <label name="label_year">
                <input class="easyui-numberbox"  name="pay_year" id="pay_begin_year"
                <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                年
            </label>
            <label name="label_month">
                <input class="easyui-numberbox"  name="pay_month" id="pay_begin_month" min="1" max="12"
                <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                月
            </label>

            <label name="label_quarter">第
                <input class="easyui-numberbox" name="pay_quarter" id="pay_begin_quarter" min="1" max="4"
                <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                季度
            </label>

            <span>&nbsp;&nbsp;</span>
            到:

            <!-- 自选区间，就用datetimebox，完整的yyyy-MM-dd hh:mm:ss -->
            <label class="cls_label_datetime">
                <input class="easyui-datetimebox e"  name="pay_end_full" id="pay_end_full" style="width:150px;height:24px">
            </label>

            <label name="label_year" >
                <input class="easyui-numberbox"  name="pay_year" id="pay_end_year"
                <%--data-options="required:true, missingMessage:'数值要比开始的年份大'"--%>
                       style="width:100px;height:24px">
                年
            </label>
            <label name="label_month">
                <input class="easyui-numberbox"  name="pay_month" id="pay_end_month" min="1" max="12"
                <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                月
            </label>
            <label name="label_quarter">第
                <input class="easyui-numberbox" name="pay_quarter" id="pay_end_quarter" min="1" max="4"
                <%--data-options="required:true"--%>
                       style="width:100px;height:24px">
                季度
            </label>
			  是否开票:
				<input class="easyui-combobox" name="invoiceStatus" id="invoiceStatus"
						data-options="valueField:'value',
		                    textField:'label',
		                    panelHeight:'auto',
		                    data: [{
		                    	label: '全部',
		                    	value: ''
		                    },{
								label: '已开票',
								value: '3'
							},{
								label: '未开票',
								value: '1'
							}]">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="l-btn-icon icon-clear" plain="true" id="clearBtn">清空</a>
			<r:FunctionRole functionRoleId="rent_income_form_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
			</r:FunctionRole>
		     
		</form>
	</div>
	
	
</body>
</html>