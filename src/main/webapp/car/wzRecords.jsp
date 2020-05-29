<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
<meta charset="UTF-8">
<title>车辆违章管理</title>
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="car/wzRecords.js"></script>
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

.fundSettlementWorkflowTable{
	font-size: 10pt;
	border-right: 1px solid #E0E0E0;
	border-bottom: 1px solid #E0E0E0;
	
	}
	
	.fundSettlementWorkflowTable td{
	  border-left: 1px solid #E0E0E0;
	  border-top: 1px solid #E0E0E0;
	}
</style>
</head>
<body>
   <table id="grid" toolbar="#toolbar"></table>
   
   
    <div id="toolbar" style="height:48px">
    <form id="queryWZRecords">
             所属城市:
		<input class="easyui-combobox" name="code" id="code" style="width:100px"
					data-options=" url:'sys_optional_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'">
                违章类型：<input type="text" name="type" class="easyui-combobox" id="type"style="width:100px"
                data-options="
                     valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '员工类型',
							value: '1'
						},{
							label: '会员类型',
							value: '2'
						}],
	                    panelHeight:'auto'"/>
	                    <input type="hidden" id="type" name="type"/>
                选择中心城市：<input type="text" name="cityCode" class="easyui-combobox" id="cityCode" style="width:120px"
		data-options="
		       method:'get',
	           valueField:'id',
	           textField:'text',
		       url:'sys_wzcityCombobox',
		       panelHeight:'auto'"/> 
		车牌号码:<input type="text" name="lpn" class="easyui-textbox" id="s-lpn" style="width:80px">
		违章姓名:<input type="text" name="custName" class="easyui-textbox" id="s-custName" style="width:80px"   >
		订单号:<input type="text" name="orderId" class="easyui-textbox" id="s-orderId">
		违章手机号码:<input type="text" name="custPhone" class="easyui-textbox" id="s-custPhone" style="width:100px">
		违章状态:&nbsp;<input type="text" name="status" class="easyui-combobox" id="s-status" style="width:100px"
		data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '未处理',
							value: '1'
						},{
							label: '已处理',
							value: '2'
						}],
	                    panelHeight:'auto'"
		>处理类型:&nbsp;<input type="text" name="handle_type" class="easyui-combobox" id="s-handle_type" style="width:100px"
                           data-options="
	                    valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '全部',
							value: ''
						},{
							label: '用户处理',
							value: '0'
						},{
							label: '公司处理',
							value: '1'
						}],
	                    panelHeight:'auto'"
    >
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery" onclick=";">查询</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnSave" onclick=";">添加</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit" onclick=";">修改</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="btnClear">清空</a>
		<r:FunctionRole functionRoleId="handler_car_wz">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-car-repair" plain="true" id="btnRepair" onclick=";">违章处理</a>
		</r:FunctionRole>
		<r:FunctionRole functionRoleId="export_WZ_Record_excel">
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-print" plain="true"  id="btnExport">导出excel</a>
		</r:FunctionRole>
		</form>
   </div>
   
   <div id="executeWZRerodsDialog" class="easyui-dialog" closed="true">
      <form id="wzRecordForm" name="wzRecordForm" method="post">
       <input type="hidden" name="id" id="ex_id">
       <table class='fundSettlementWorkflowTable' width="100%" cellpadding="2">
           <tr>
               <td align="center" nowrap="nowrap">处理类型</td>
               <td nowrap="nowrap">
                   <input type="radio" name='handleType' id='handleType_1' value="0" checked="checked">
                   <label for="status_1">用户处理</label>
                   <input type="radio" name='handleType' id='handleType_2' value="1" >
                   <label for="status_2">公司处理</label></td>
           </tr>
            <tr style="display: none">
              <td align="center" nowrap="nowrap">处理结果</td>
	           <td nowrap="nowrap">
		        <input type="radio" name='status' id='status_1'  value="1">
		        <label for="status_1">未处理</label>
		        <input type="radio" name='status' id='status_2'value="2" checked="checked">
		        <label for="status_2">已处理</label></td>
           </tr>
           <tr>
              <td align="center" nowrap="nowrap">备注</td>
               <td nowrap="nowrap">
                 <input class="easyui-textbox"  name="remark" id='ex_remark' data-options="multiline:'true'" style="height:80px; width:400px;">
               </td>
          </tr>
       </table>
      </form> 
   </div>

   <div id="editView" class="easyui-dialog" closed="true"
        style="padding:10px 20px">
       <form name="editViewForm" id="editViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="id" id="e-id">
           <input type="hidden" name="lpn_orderId" id="e-lpn_orderId">
           <div class="fitem">
               <label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="e-cityCode" style="width: 75%;"
                                           data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
                        missingMessage="请选择所属城市">
           </div>
           <div class="fitem">
               <label>违章类型:</label>
               <input  name="type" id="e-type" class="easyui-combobox"  style="width: 75%;" data-options="
                valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '会员',
							value: '2'
						},{
							label: '员工',
							value: '1'
						}],
	                    panelHeight:'auto'"/>
           </div>
           <div class="fitem">
               <label>车牌号:</label> <input id="e-lpn"  name="hphm" class="easyui-textbox lpn" style="width: 75%;"/>
           </div>
           <div class="fitem">
               <label>违章订单号:</label> <input class="easyui-combobox orderId" name="phone_orderId" id="e-orderId" style="width: 75%;" missingMessage="请填选择违章订单号">
           </div>
           <div class="fitem">
               <label>违章手机号:</label> <input name="phone" id="e-phone" class="easyui-textbox phone"  style="width: 75%;" required="true" editable="false"/>
           </div>
           <div class="fitem">
               <label>违章姓名:</label> <input  name="name" id="e-name"  class="easyui-textbox name"  style="width: 75%;"  editable="false" editable="false"/>
           </div>
           <%--<div class="fitem">--%>
               <%--<label>文书编号:</label> <input class="easyui-textbox" name="archiveno" id="e-archiveno" style="width: 75%;" missingMessage="请填写文书编号" required="true">--%>
           <%--</div>--%>
           <div class="fitem">
               <label>违章行为:</label> <input class="easyui-textbox" name="act" id="e-act" style="width: 75%;height: 60px;" missingMessage="请填写违章行为" data-options="multiline:true" required="true">
           </div>
           <div class="fitem">
               <label>违章时间:</label> <input id="e-date"  name="date" class="easyui-datetimebox" style="width: 75%;" data-options="required:true" />
           </div>
           <div class="fitem">
               <label>违章地点:</label> <input id="e-area"  name="area" class="easyui-textbox" style="width: 75%;" data-options="required:true" />
           </div>
           <div class="fitem">
               <label>违章罚款:</label> <input id="e-money"  name="money" class="easyui-textbox" style="width: 75%;" data-options="required:true" />
           </div>
           <div class="fitem">
               <label>违章扣分:</label> <input id="e-point"  name="fen" class="easyui-textbox" style="width: 75%;" data-options="required:true" />
           </div>
       </form>
   </div>

   <div id="addView" class="easyui-dialog" closed="true"
        style="padding:10px 20px">
       <form name="addViewForm" id="addViewForm" method="post" enctype="multipart/form-data">
           <input type="hidden" name="lpn_orderId" id="lpn_orderId">
           <div class="fitem">
               <label>所属城市:</label> <input class="easyui-combobox" name="cityCode" id="a-cityCode" style="width: 75%;"
                                           data-options=" url:'sys_cityCombobox',
	                    method:'get',
	                    valueField:'id',
	                    textField:'text',
	                    panelHeight:'auto'"
                        missingMessage="请选择所属城市">
           </div>
           <div class="fitem">
               <label>违章类型:</label>
               <input  name="type" id="a-type" class="easyui-combobox"  style="width: 75%;" data-options="
                valueField:'value',
	                    textField:'label',
	                    data: [{
							label: '会员',
							value: '2'
						},{
							label: '员工',
							value: '1'
						}],
	                    panelHeight:'auto'"/>
           </div>
           <div class="fitem">
               <label>车牌号:</label> <input id="lpn"  name="hphm" class="easyui-textbox lpn" style="width: 75%;"/>
           </div>
           <div class="fitem">
               <label>违章订单号:</label> <input class="easyui-combobox orderId" name="phone_orderId" id="orderId" style="width: 75%;" missingMessage="请选择违章订单号">
           </div>
           <div class="fitem">
               <label>违章手机号:</label> <input  name="phone" id="phone" class="easyui-textbox phone"  style="width: 75%;" required="true" editable="false"/>
           </div>
           <div class="fitem">
               <label>违章姓名:</label> <input  name="name" id="name"  class="easyui-textbox name"  style="width: 75%;"  editable="false" editable="false"/>
           </div>
           <%--<div class="fitem">--%>
               <%--<label>文书编号:</label> <input class="easyui-textbox" name="archiveno" id="archiveno" style="width: 75%;" missingMessage="请填写违章行为" required="true">--%>
           <%--</div>--%>
           <div class="fitem">
               <label>违章行为:</label> <input class="easyui-textbox" name="act" id="act" style="width: 75%;height: 60px;" missingMessage="请填写文书编号" data-options="multiline:true">
           </div>
           <div class="fitem">
               <label>违章时间:</label> <input id="date"  name="date" class="easyui-datetimebox" style="width: 75%;" data-options="required:true" />
           </div>
           <div class="fitem">
               <label>违章地点:</label> <input id="area"  name="area" class="easyui-textbox" style="width: 75%;" data-options="required:true" />
           </div>
           <div class="fitem">
               <label>违章罚款:</label> <input id="money"  name="money" class="easyui-textbox" style="width: 75%;" data-options="required:true" />
           </div>
           <div class="fitem">
               <label>违章扣分:</label> <input id="point"  name="fen" class="easyui-textbox" style="width: 75%;" data-options="required:true" />
           </div>
       </form>
   </div>
 </body>
 </html>