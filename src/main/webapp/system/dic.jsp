<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>字典列表</title>
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
    <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
	<script type="text/javascript" src="system/dic.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',split:true" title="字典"
		style="width:15%;" id="west">
		<ul id="tree" ></ul>
	</div>
	<div data-options="region:'center',iconCls:'icon-ok'" title="字典项">
	   <div id="toolbar" style="height:auto">
	   <div>
<!--            		字典项名称:<input type="text" id="queryname" />  -->
<!-- 				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>  -->
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEditDicType">编辑</a> 
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnDelDicType">删除</a>				
				<a href="javascript:void(0);" class="easyui-linkbutton"	iconCls="icon-add" plain="true" id="btnAddDicType">添加</a>
           </div>
           </div>
           <table  id="dicTypeGird" toolbar="#toolbar"></table>
	</div>
	
	<!-- 右键按钮出现菜单 -->
	<div id="mm" class="easyui-menu" style="width:120px;align:left">
		<div iconCls="icon-add" onclick="openAddDicView();">添加字典</div>
	</div>

	<div id="mm2" class="easyui-menu" style="width:120px;align:left">
		<r:FunctionRole functionRoleId="add_dic">
			<div iconCls="icon-add" onclick="openAddDicTypeView();">添加字典项</div>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="update_dic">
			<div iconCls="icon-edit" onclick="openUpdateView();">修改字典</div>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="delete_dic">
			<div iconCls="icon-remove" onclick="delDic();">删除字典</div>
		</r:FunctionRole>
	</div>
	<!-- 添加或者修改字典窗口 -->
<div id="dicView" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
			<form id="dicForm" method="post"  action="" enctype="multipart/form-data" onsubmit="return checkDicView(this)">
			       <div class="fitem">
		                <label>字典名称:</label>
		                <input type="hidden" name="id" id="dicId"/>
		                 <input name="name" id="dicname" class="easyui-textbox" required="true" missingMessage="请填写字典名称" id=""/>
		            </div>
					<div class="fitem">
		                <label>字典code:</label>
		                <input name="code" id="diccode" class="easyui-textbox" required="true" missingMessage="请填写字典code" id=""  data-options="validType:'dic'"/>
		            </div>
		            <div class="fitem">
		                <label>排序:</label>
		                <input name="sort" id="dicsort" type="text" class="easyui-numberbox" data-options="min:0" required="true" missingMessage="请填写排序,必须填入数字"  ></input>
		            </div>
		             
		             <div class="fitem">
		                <label>描述:</label>
		                <input name="description" id="dicdescription" class="easyui-textbox" />
		            </div>
			</form>
		</div>
	<!-- 添加字典项窗口 -->
	<div id="dictypeView" class="easyui-dialog" closed="true" style="width:400px;height:280px;padding:10px 20px">
			<form id="dictypeForm" method="post" action="" enctype="multipart/form-data" onsubmit="return checkDictypeView(this)">
			       <div class="fitem">
		                 <input type="hidden" id="dicId1" name="dicId"/>
		                 <input type="hidden" id="dictypeid" name="id"/>
		            </div>
			       <div class="fitem">
		                <label>字典项名称:</label>
		                 <input name="name" class="easyui-textbox" required="true" missingMessage="请填写字典名称" id="dictypename"/>
		            </div>
					<div class="fitem">
		                <label>字典项code:</label>
		                <input name="code" class="easyui-textbox" required="true" missingMessage="请填写字典code" id="dictypecode"/>
		            </div>
		            <div class="fitem">
		                <label>排序:</label>
		                <input name="sort"  type="text" class="easyui-numberbox" data-options="min:0" required="true" missingMessage="请填写排序,必须填入数字" id="dictypesort" ></input>
		            </div>
			</form>
		</div>
	<style type="text/css">
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
</style>
</body>
</html>