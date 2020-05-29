<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>车辆运营月报表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="operationReport/common.js"></script>	
	<script type="text/javascript" src="operationReport/carMonthlyOperation.js"></script>	
	
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
		display: inline-block;
		width: 80px;
		
	}
	
	.fitem input {
		width: 160px;
	}
	
	.fitem a {
		margin-right: 5px;
	}
	#data{
		margin:0 auto;
	}
	</style>
<!-- 	<script> -->
<!--     $(function () { -->
<!--         $('#queryMonth').datebox({ -->
<!--             onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层 -->
<!--                 span.trigger('click'); //触发click事件弹出月份层 -->
<!--                 if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔 -->
<!--                     tds = p.find('div.calendar-menu-month-inner td'); -->
<!--                     tds.click(function (e) { -->
<!--                         e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件 -->
<!--                         var year = /\d{4}/.exec(span.html())[0]//得到年份 -->
<!--                         , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1 -->
<!--                         $('#queryMonth').datebox('hidePanel')//隐藏日期对象 -->
<!--                         .datebox('setValue', year + '-' + month); //设置日期的值 -->
<!--                     }); -->
<!--                 }, 0) -->
<!--             }, -->
<!--             parser: function (s) { -->
<!--                 if (!s) return new Date(); -->
<!--                 var arr = s.split('-'); -->
<!--                 return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1); -->
<!--             }, -->
<!--             formatter: function (d) { return d.getFullYear() + '-' +(((d.getMonth()+1) < 10) ? "0" : "")+ (d.getMonth()+1);/*getMonth返回的是0开始的，忘记了。。已修正*/ } -->
<!--         }); -->
<!--         var p = $('#queryMonth').datebox('panel'), //日期选择对象 -->
<!--             tds = false, //日期选择对象中月份 -->
<!--             span = p.find('span.calendar-text'); //显示月份层的触发控件 -->
<!--     }); -->
<!--   </script> -->
</head>

<body>
	<table id="dataGrid" toolbar="#toolbar"></table>
     
	<!--列表工具栏 -->
	<div id="toolbar" style="height:auto">
					<form id="data">
					<!-- <label class="label">会员身份证：</label>
					<input class="easyui-textbox" " name="idcard" id="idcard" style="width: 150px;"> -->
					<label class="label">&nbsp;年份:</label>
					<select name="yearValue" id="yearValue">
					  <script>
					    var date = new Date();
					    var _y = date.getFullYear();
					    for(var i=_y; i> (_y-3); i--){
					       var tmpHtml = "<option value='"+i+"'>"+i+"年</option>";
					       if(i == date.getFullYear()){
					         tmpHtml = "<option selected='selected' value='"+i+"'>"+i+"年</option>";
					       }
					       document.write(tmpHtml);
					    }
					  </script>
					</select>
					<label class="label">月份:</label>
					<select name="monthValue" id="monthValue">
					    <script>
					      for(var k=1; k<=12; k++){
					         var tmpHmlk = "<option value='"+k+"'>"+k+"月</option>";
					         if(k == new Date().getMonth() + 1){
					           tmpHmlk = "<option selected='selected' value='"+k+"'>"+k+"月</option>";
					         }
					         document.write(tmpHmlk);
					      }
					    </script>
					</select>
<!-- 					<input class="easyui-textbox" name="queryMonth" id="queryMonth" data-options="required:true,missingMessage:'月份不能为空'"> -->
			<label class="label">&nbsp;车牌：</label>
			<input class="easyui-textbox"" name="lpn" id="lpn" style="width: 120px;">
			<label class="label">&nbsp;车辆型号：</label>
			<input class="easyui-combobox" " name="carType" id="carType" data-options=" url:'sys_carTypeCombobox',
                   method:'get',
                   valueField:'id',
                   textField:'text',
                   panelHeight:'auto'">
           <!--  <label class="label">&nbsp;租赁类型：</label>
			<input class="easyui-combobox"" name="rentType" id="rentType" style="width: 120px;" data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '个人',
							value: 'personOrder'
						},{
							label: '企业',
							value: 'enterpriseOrder'
						}],
	                    panelHeight:'auto'"
			> -->
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
			<r:FunctionRole functionRoleId="car_run_month_form_export_excel">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a> 
			</r:FunctionRole>
		    
			</form>
		</div>	
</body>
</html>