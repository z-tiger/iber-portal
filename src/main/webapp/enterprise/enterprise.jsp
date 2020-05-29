<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib prefix="r" uri="/tags/platform-taglib.tld" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>企业管理</title>
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
	<script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="ui_lib/js/validator-extend.js" ></script>
	<script type="text/javascript" src="enterprise/enterprise.js"></script>
</head>

	<body>
		<table id="dataGrid" toolbar="#toolbar"></table>
      	<!--列表工具栏 -->
 	    <div id="toolbar" style="height:auto">
        	<!-- <form id="queryForm" method="post" action="#" enctype="multipart/form-data"  > -->
				所属地区:<input class="easyui-combobox" name="_city_code" id="_city_code" style="width: 80px;" 
					data-options=" url:'sys_optional_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto'">
				企业名称:<input id="_enterprise_name"  name="_enterprise_name" class="easyui-textbox"/>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="btnQuery">查询</a>
	            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearQuery">清空</a>
	           
	            <r:FunctionRole functionRoleId="add_enterprise">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnAdd">添加企业</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="update_enterprise">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="btnEdit">修改</a>
	            </r:FunctionRole>
	            <%--<r:FunctionRole functionRoleId="delete_enterprise">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemove">删除</a>
	            </r:FunctionRole>--%>
	          <%--  <r:FunctionRole functionRoleId="add_member">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-contact_blue_add" plain="true" id="memberAdd">添加会员</a>
	            </r:FunctionRole>--%>
	            <r:FunctionRole functionRoleId="account_recharge">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-box_add" plain="true" id="moneyAdd">余额充值</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="deposit_recharge">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-box_add" plain="true" id="depositAdd">押金充值</a>
	            </r:FunctionRole>
	            <%--<r:FunctionRole functionRoleId="user_import">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-box_locked" plain="true" id="membersAdd">用户导入</a>
	            </r:FunctionRole>--%>
	           <%-- <r:FunctionRole functionRoleId="sample_download">
	            	<a href="importMember.xls"  class="easyui-linkbutton" iconCls="icon-luck-cloud-document_letter_download" plain="true" id="membersAddUploud" >样表下载</a>
	            </r:FunctionRole>--%>
	           
         	<!-- </form> -->
         	</div>
         <!-- add -->
         <div id="addView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="addForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="id" id="id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 0px;">
					<tr>
		    			<td width="100px;" align="left">所属区域:</td>
		    			<td >
		    			<input class="easyui-combobox" name="city_code" id="city_code" style="width:100%;height:24px" 
							data-options=" url:'sys_cityCombobox',method:'get',valueField:'id',editable:false,textField:'text',panelHeight:'auto',required:true">
		    			</td>
		    			<td  width="80px;"></td>
		    		</tr>
					<%--<tr>
		    			<td>企业等级:</td>
		    			<td>
		    			<input class="easyui-combogrid" name="level" id="level" style="width:90%;height:24px" 
							data-options="
								editable:false,
								panelHeight: 'auto',
								panelWidth: 300,
								idField: 'id',
								textField: 'name',
								url : 'enterprise_level_list.do',
								pageSize:10,
								required:true,
								columns : [ [ 
								{
									field : 'name',
									title : '等级名称',
									width : '80%',
									align : 'center'
								}] ],
								pagination : true,
								rownumbers : true
							">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" plain="true" id="clearCombogrid" style="width:8%;" title="清空"></a>
		    			</td>
		    			<td></td>
		    		</tr>--%>
		    		<tr>
		    			<td>企业名称:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="enterprise_name" name="enterprise_name" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业名称不能为空'">
		    			</td>
		    			<td id="enterprise_name_msg"  ></td>
		    		</tr>
		    		<tr>
		    			<td>管理员姓名:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="admin" name="admin" style="width:100%;height:24px" data-options="required:true,missingMessage:'管理员姓名不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
		    		<tr>
		    			<td>管理员手机号:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="admin_mobile" id="admin_mobile" style="width:100%;height:24px" data-options="required:true,missingMessage:'管理员手机号不能为空',validType:'mobile'">
		    			</td>
		    			<td id="admin_mobile_msg"  ></td>
		    		</tr>
		    		<tr>
		    			<td>企业法人:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="legal_person" name="legal_person" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业法人不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
		    		<tr>
		    			<td>企业电话:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="enterprise_tel" id="enterprise_tel" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业电话不能为空',validType:'phone'">
		    			</td>
		    			<td id="enterprise_tel_msg"  ></td>
		    		</tr>
		    		<tr>
		    			<td>企业地址:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="address" id="address" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业地址不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
		    		<tr>
		    			<td>营业执照编号:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="business_license" id="business_license" style="width:100%;height:24px" data-options="required:true,missingMessage:'营业执照编号不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
                    <tr>
                        <td>企业应缴押金(元):</td>
                        <td>
                            <input class="easyui-textbox"  name="depositLimit" id="depositLimit" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业应缴押金不能为空',validType:'plusmoney'">
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>透支金额(元):</td>
                        <td>
                            <input class="easyui-textbox"  name="overdraftMoney" id="overdraftMoney" style="width:100%;height:24px" data-options="required:true,missingMessage:'透支金额不能为空',validType:'plusmoney'">
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td>透支次数(次):</td>
                        <td>
                            <input class="easyui-textbox"  name="overdraftNum" id="overdraftNum" style="width:100%;height:24px" data-options="required:true,missingMessage:'透支次数不能为空',validType:'zeroOrInt'">
                        </td>
                        <td></td>
                    </tr>
		    		<tr>
		    			<td>营业执照图片:</td>
		    			<td>
			           	<input id="license_file_url"  name="license_file_url" type="file" onchange="javascript:setImagePreview(this,localImag,enterprisePhoto);" style="width:100%;height:24px"/>
			           	<div id="localImag" >
			              	<img style="margin-left: 0px;"  id="enterprisePhoto" />
			            </div>
		    			</td>
		    			<td></td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 
		 <!-- memberAddView -->
         <div id="memberAddView" class="easyui-dialog" closed="true" style="width: 600px;height: 600px;">
			<form id="memberAddForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="m_id" id="m_id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 10px;">
					<tr>
		    			<td width="100px;">企业名称:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="m_enterpris_name" id="m_enterpris_name" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业名称不能为空'" readonly="readonly">
		    			</td>
		    			<td width="80px;"></td>
		    		</tr>
					<tr>
		    			<td>手机号码:</td>
		    			<td>
		    			<input  name="m_phone" id="m_phone" class="easyui-textbox"  style="width:100%;height:24px" data-options="required:true,missingMessage:'手机号码不能为空'"/>
		    			</td>
		    			<td id="m_phone_msg"  ></td>
		    		</tr>
		    		<tr>
		    			<td>姓名:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="m_name" name="m_name" style="width:100%;height:24px" data-options="required:true,missingMessage:'姓名不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
		    		<tr>
		    			<td>性别:</td>
		    			<td>
		    			<input class="easyui-combobox"  id="m_sex" name="m_sex" style="width:100%;height:24px" 
		    			data-options="url:'sys_dic?dicCode=DIC_SEX',
	                    method:'get',valueField:'code',textField:'name',
	                    panelHeight:'auto',required:true,missingMessage:'性别不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
		    		<tr>
		    			<td>Email:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="m_email" id="m_email" style="width:100%;height:24px" data-options="required:false,missingMessage:'Email不能为空'">
		    			</td>
		    			<td id="m_email_msg"  ></td>
		    		</tr>
		    		<tr>
		    			<td>身份证号码:</td>
		    			<td>
		    			<input class="easyui-textbox"  id="m_idcard" name="m_idcard" style="width:100%;height:24px" data-options="required:true,missingMessage:'身份证号码不能为空'">
		    			</td>
		    			<td id="m_idcard_msg"  ></td>
		    		</tr>
		    		<tr>
		    			<td>驾驶证号码:</td>
		    			<td>
		    			<input class="easyui-textbox"  name="m_driver_idcard" id="m_driver_idcard" style="width:100%;height:24px" data-options="required:true,missingMessage:'驾驶证号码不能为空'">
		    			</td>
		    			<td></td>
		    		</tr>
		    		<tr>
		    			<td>驾驶证照片:</td>
		    			<td>
			           	<input id="m_driver_idcard_url"  name="m_driver_idcard_url" type="file" onchange="javascript:setImagePreview(this,driverIdcardImag,driverIdcardPhoto);" style="width:100%;height:24px"/>
			           	<div id="driverIdcardImag" >
			              	<img style="margin-left: 0px;" width="250"  id="driverIdcardPhoto" />
			            </div>
		    			</td>
		    			<td></td>
		    		</tr>
		    	</table>
			</form>
		 </div>
		 
		 <div id="moneyAddView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
			<form id="moneyAddForm" method="post" action="" <%-- onsubmit="checkCustomer(this)"--%>>
				<input type="hidden" name="enterpriseId" id="enterpriseId" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
		    		<tr>
		    			<td>企业名称:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="enterpriseName" id="enterpriseName" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业名称不能为空'" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>充值金额(元):</td>
		    			<td>
		    				<input class="easyui-textbox"  name="chargeMoney" id="chargeMoney" style="width:100%;height:24px" data-options="required:true,missingMessage:'充值金额不能为空' ,validType:'money'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>备注:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="remark" id="remark" style="width:100%;height:24px" >
		    			</td>
		    		</tr>
		    		<tr>
		    			<td colspan="2"><font color="red">备注：充值金额为负数时表示退款</font></td>
		    		</tr>
		    	</table>
		    	
			</form>
		</div>
		
		<div id="depositAddView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
			<form id="depositAddForm" method="post" action=""<%-- onsubmit="checkCustomer(this)"--%>>
				<input type="hidden" name="deposit_add_id" id="deposit_add_id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
		    		<tr>
		    			<td>企业名称:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="enterpris_name_d" id="enterpris_name_d" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业名称不能为空'" readonly="readonly">
		    			</td>
		    		</tr>

		    		<tr>
		    			<td>充值押金(元):</td>
		    			<td>
		    				<input class="easyui-textbox"  name="money_d" id="money_d" style="width:100%;height:24px" data-options="required:true,missingMessage:'充值押金不能为空',validType:'money'">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>备注:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="trade_no_d" id="trade_no_d" style="width:100%;height:24px">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td colspan="2"><font color="red">备注：充值押金为负数时表示退押金</font></td>
		    		</tr>
		    	</table>
			</form>
		</div>
		<div id="importMemberView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
			<form id="importMemberForm" method="post" action="" enctype="multipart/form-data" <%--onsubmit="checkCustomer(this)"--%>>
				<input type="hidden" name="import_member_id" id="import_member_id" />
				<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
					<tr>
		    			<td>企业名称:</td>
		    			<td>
		    				<input class="easyui-textbox"  name="enterpris_name_imp" id="enterpris_name_imp" style="width:100%;height:24px" data-options="required:true,missingMessage:'企业名称不能为空'" readonly="readonly">
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>excel(xls):</td>
		    			<td>
		    				<input type="file"  name="improt_url" id="improt_url" style="width:100%;height:24px" >
		    			</td>
		    		</tr>
		    	</table>
			</form>
		</div>
		<div id="showMemberView" class="easyui-dialog" closed="true"  data-options="disabled:false" style="width:800px;height:30px;padding-bottom: 25px;">
			<%--<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-box_edit" plain="true" id="btnEditMoney">设置额度</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-group_blue_new" plain="true" id="btnEditSetManager">设置管理员</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-group_blue_remove" plain="true" id="btnEditCancelManager">取消管理员</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnRemoveMember">移除企业会员</a>--%>
			<table id="showMemberGrid"></table>
			<div id="editMoneyView" class="easyui-dialog" closed="true" style="width: 350px;height: 250px;">
				<form id="editMoneyForm" method="post" action="" enctype="multipart/form-data" onsubmit="checkCustomer(this)">
					<input type="hidden" name="member_id" id="member_id" />
					<table cellpadding="5" style="font-size: 12px;margin: auto;margin-top: 20px;">
			    		<tr>
			    			<td>姓名:</td>
			    			<td>
			    				<input class="easyui-textbox"  name="name" id="name" style="width:100%;height:24px" data-options="required:true,missingMessage:'姓名不能为空'" readonly="readonly">
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>额度:</td>
			    			<td>
			    				<input class="easyui-numberbox"  name="quota" id="quota" style="width:100%;height:24px" data-options="required:true,missingMessage:'额度不能为空'">
			    			</td>
			    		</tr>
			    		<%--<tr>
			    			<td>期限:</td>
			    			<td>
			    			<select class="easyui-combobox"  name="quota_month" id="quota_month"  style="width:100%;height:24px" data-options="required:true,missingMessage:'期限不能为空'">
			                   <option value="1">1个月</option>
			                   <option value="2">2个月</option>
			                   <option value="3">3个月</option>
			                   <option value="4">4个月</option>
			                   <option value="5">5个月</option>
			                   <option value="6">6个月</option>
			                   <option value="7">7个月</option>
			                   <option value="8">8个月</option>
			                   <option value="9">9个月</option>
			                   <option value="10">10个月</option>
			                   <option value="11">11个月</option>
			                   <option value="12">12个月</option>
			               </select>
			    			</td>
			    		</tr>--%>
			    	
			    	</table>
				</form>
			</div>
		</div>
		
		<!-- 企业所属网点view -->
		<div id="showParkView" class="easyui-dialog" closed="true" style="width:500px;height:400px;padding-bottom: 25px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="btnEditSetPark">设置网点</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="btnEditCancelPark">移除网点</a>
			<input type="hidden" name="p_enterprise_id" id="p_enterprise_id" />

            <table id="showParkGrid"></table>
			<div id="addParkView" class="easyui-dialog" closed="true" style="padding:10px 20px">
			<form id="addParkForm" method="post" action=""
				enctype="multipart/form-data" onsubmit="checkCustomer(this)">
				<input type="hidden" name="enterprise_id" id="enterprise_id" />
				
					<table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">	
		    		<tr>
		    			<td>网点名称:</td>
		    			<td>
		    			<input class="easyui-combogrid"  id="parkName" name="parkName" style="width:300px;height:24px">
		    			</td>
		    		</tr> 
		    		
		    	</table> 
		    	
			</form>
			 </div>	
		</div>

        <%--企业所属车辆view--%>
        <div id="showEnterpriseCarView" class="easyui-dialog" closed="true" style="width:500px;height:400px;padding-bottom: 25px;">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" id="addEnterpriseCar">添加企业所属车辆</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="removeEnterpriseCar">移除车辆</a>
            <input type="hidden" name="c_enterprise_id" id="c_enterprise_id" />
            <table id="showEnterpriseCarGrid"></table>

            <div id="addEnterpriseCarView" class="easyui-dialog" closed="true" style="padding:10px 20px;width: 450px">
                <form id="addEnterpriseCarForm" method="post" action=""
                      enctype="multipart/form-data" onsubmit="checkCustomer(this)">
                    <input type="hidden" name="enter_id" id="enter_id" />
                    <table cellpadding="5" style="font-size: 12px;margin-left:10px;margin-top: 0px;">
                        <tr>
                            <td>车辆列表:</td>
                            <td>
                                <input class="easyui-combogrid"  id="carList" name="carList" style="width:200px;height:24px">
                            </td>

                        </tr>
                    </table>
                </form>
            </div>
        </div>
        
        <div id="branchOfficeDetailView" class="easyui-dialog" closed="true"
			style="padding-left:20px;padding-right:20px;padding-top:3px;padding-bottom:30px; width: 400px; height: 400px;">
            <form action="" id = "branchCompanyForm">
                <input type="hidden" name="parentId" id="parentId">
                分公司名称:<input id="branchOfficeName"  name="branchOfficeName" class="easyui-textbox" style="width:150px"/>
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true" id="branchOfficeQuery">查询</a>
            </form>

	        <%--    <r:FunctionRole functionRoleId="account_recharge">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-box_add" plain="true" id="branchOfficeMoneyAdd">余额充值</a>
	            </r:FunctionRole>
	            <r:FunctionRole functionRoleId="deposit_recharge">
	            	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-luck-cloud-box_add" plain="true" id="branchOfficeDepositAdd">押金充值</a>
	            </r:FunctionRole>--%>
			<!-- 该位置有两个功能按钮被删 -->
			 <table id="branchOfficeDetailDataGrid" ></table>
			 <div id="toolbar" class="fitem" style="height:auto"></div>
	    </div> 
        
        
        
	</body>
</html>