<%--
  Created by IntelliJ IDEA.
  User: liubiao
  Date: 2018/4/20
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
    <%@include file="../common/common-header.jsp"%>
    <title>用车超额申请记录</title>
    <script src="enterprise/enterpriseOverApplyRecord.js"></script>
</head>
<body>
<table id="dataGrid" toolbar="#toolbar"></table>
<!--列表工具栏 -->
<div id="toolbar" style="height:auto">
    <form id="queryForm"  action=""  >
        <%--所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;"
                    data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">--%>
        企业名称:<input id="entepriseName"  name="entepriseName" class="easyui-textbox" style="width:100px"/>
        员工姓名:<input id="memberName"  name="memberName" class="easyui-textbox" style="width:100px"/>
        手机号码:<input id="phone"  name="phone" class="easyui-textbox" style="width:100px" data-options="validType:'mobile'"/>
       <%-- 订单类型:<input class="easyui-combobox" style="width:100px;" name="orderType" id="orderType"
                    data-options=" valueField:'value',
                textField:'label',
                data: [{
                    label: '',
                    value: ''
                },{
                    label: '企业约车',
                    value: '1'
                },{
                    label: '个人约车',
                    value: '0'
                }],
                panelHeight:'auto'">--%>

            审核结果:<select class="easyui-combobox" name="checkStatus" id="checkStatus" style="width:80px;" data-options="editable:false,panelHeight:'auto'">
                        <option value=""></option>
                        <option value="1">待审核</option>
                        <option value="2">通过</option>
                        <option value="3">不通过</option>
                        <option value="4">取消</option>
                    </select>
        <%--上车时间:<input id="queryDateFrom"  name="queryDateFrom" class="easyui-datebox" style="width:100px"/>
        到:<input id="queryDateTo"  name="queryDateTo" class="easyui-datebox" style="width:100px"/>--%>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>

        <%--<r:FunctionRole functionRoleId="pass_audit">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_okay" plain="true" id="btnEdit">审核通过</a>
        </r:FunctionRole>
        <r:FunctionRole functionRoleId="no_pass_audit">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_remove" plain="true" id="btnRemove">审核不通过</a>
        </r:FunctionRole>--%>
    </form>
</div>


    <%--<%@include file="../common/common-footer.jsp"%>--%>


</body>
</html>
