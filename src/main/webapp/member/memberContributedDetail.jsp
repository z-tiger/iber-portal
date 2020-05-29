<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>会员贡献值明细管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="member/memberContributedDetail.js"></script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
 	    <form action="" id="contributedForm">
 	    		所属城市:<input class="easyui-combobox" name="cityCode" id="scityCode"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
	                        会员等级:<input class="easyui-combobox" name="memberLevel" id="smemberLevel"
					data-options="
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto',
	                    data:[{   
    							'id':'',   
   							    'text':'全部'  
							},{   
							    'id':'0',   
							    'text':'黑名单'  
							},{   
							    'id':'1',   
							    'text':'一星会员',   
							},{   
							    'id':'2',   
							    'text':'二星会员'  
							},{   
							    'id':'3',   
							    'text':'三星会员'  
							},{   
							    'id':'4',   
							    'text':'四星会员'  
							},{   
							    'id':'5',   
							    'text':'五星会员'  
							}]">
				会员姓名:<input type="text" name="memberName" class="easyui-textbox" id="s-memberName">
				手机号码:<input type="text" name="phone" class="easyui-textbox" id="s-phone">
				<r:FunctionRole functionRoleId="select_contributed">
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	    		</r:FunctionRole>
	    		<r:FunctionRole functionRoleId="export_contributed">
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true" id="btnExport">导出execl</a>
				</r:FunctionRole>
	    	</form>
	    </div>
        <div id="contributedDetailView" class="easyui-dialog" closed="true"
			style="padding:10px 20px; width: 1000px; height: 400px;">
			 <table id="contributedDetailDataGrid" 
			 	cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px; left:80px;width:600px;height:200px""></table>
	    </div> 
	</body>
</html>