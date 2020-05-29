$(function(){
	//导出excel
	$("#btnExport").bind("click", function() {
		var name = $("#s-name").textbox("getValue");
		var maxContriValue = $("#s-maxContriValue").textbox("getValue");
		var minContriValue = $("#s-minContriValue").textbox("getValue");
		var behaviorId = $("#s-behaviorTypeName").combobox("getValue");
		var isIncrease = $("#s-isIncrease").combobox("getValue");
		var isRatio = $("#s-isRatio").combobox("getValue") ;
 		$("#behaviorForm").form("submit", {
			url : "export_behavior_report?name=" + name
					+ "&maxContriValue="
					+ maxContriValue
					+ "&minContriValue="
					+ minContriValue + "&behaviorId="
					+ behaviorId + "&isIncrease="
					+ isIncrease + "&isRatio="
					+ isRatio,
			onSubmit : function() {
			},
			success : function(result) {
			}
		});

	});
	$('#grid').datagrid({
		title : '行为管理',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : true,
		rownumbers : true,
		singleSelect : true,
		selectOnCheck: true,
		checkOnSelect: true,
		pagination : true,
		rownumbers : true,
		url : 'behavior_list',
		queryParams:{
			'name':$("#s-name").textbox("getValue"),
			'maxContriValue':$("#s-maxContriValue").textbox("getValue"),
			'minContriValue':$("#s-minContriValue").textbox("getValue"),
			'behaviorId':$("#s-behaviorTypeName").combobox("getValue"),
			'isIncrease':$("#s-isIncrease").combobox("getValue"),
			'isRatio': $("#s-isRatio").combobox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'behaviorType',
			title : '分类',
			width : $(this).width() * 0.08,
			align : 'center'
		},
		{
			field : 'name',
			title : '子分类',
			width : $(this).width() * 0.14,
			align : 'left'
		},{
			field : 'isRatio',
			title : '贡献值类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if(val=="0"){
					return "绝对值";
				}else if(val=="1"){
					return "比例";
				}
			}
		},{
			field : 'isIncrease',
			title : '加/扣分',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if(val=="0"){
					return "扣分";
				}else if(val=="1"){
					return "加分";
				}
			}
		}, {
			field : 'contriValue',
			title : '贡献值',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'conditionType',
			title : '关联数值类型',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val) {
				if("" != val){
					return getConditionTypeName(val);
				}
				return "";
			}
		},{
			field : 'conditionVal',
			title : '关联数值N',
			width : $(this).width() * 0.08,
			align : 'center'
			
		},{
			field : 'contriDetal',
			title : '说明',
			width : $(this).width() * 0.2,
			align : 'left'
		},{
			field : 'status',
			title : '开关',
			width : $(this).width() * 0.05,
			align : 'center',
			formatter : function(val,rec) {
				var result = '<input id ="switch_'+rec.id+'" style="height:25px;width:50px;" class="easyui-switchbutton"  data-options="onText:\'开\',offText:\'关\'">';
				if('1' == val){
					result = '<input id ="switch_'+rec.id+'" style="height:25px;width:50px;" class="easyui-switchbutton" data-options="onText:\'开\',offText:\'关\'">';
				}
				return result;
			}
		}
//		,{
//			field : 'createTime',
//			title : '创建时间',
//			width : $(this).width() * 0.12,
//			align : 'center',
//			formatter : function(val) {
//			return formatDate(val);
//		    }
//		},{
//			field : 'createName',
//			title : '创建人',
//			width : $(this).width() * 0.12,
//			align : 'center'
//		},{
//			field : 'updateName',
//			title : '更改人',
//			width : $(this).width() * 0.12,
//			align : 'center'
//		},{
//			field : 'updateTime',
//			title : '更改时间',
//			width : $(this).width() * 0.12,
//			align : 'center',
//			formatter : function(val) {
//				return formatDate(val);
//			}
//		}
//		,{
//			field : 'memberComplain',
//			title : '会员举报类型',
//			width : $(this).width() * 0.1,
//			align : 'center',
//			formatter : function(val) {
//				if("1" == val){
//					return "是";
//				}
//				return "";
//			}
//		},{
//			field : 'employeeComplain',
//			title : '员工举报类型',
//			width : $(this).width() * 0.1,
//			align : 'center',
//			formatter : function(val) {
//				if("1" == val){
//					return "是";
//				}
//				return "";
//			}
//		}
//		,{
//			field : 'sort',
//			title : '排序',
//			width : $(this).width() * 0.08,
//			align : 'center'
//		}
		]],
		 onLoadSuccess:function(data){
			 for(var index =0 ; index< data.rows.length;index++){
				 var beha = data.rows[index];
				 if(beha.status == 1){
				 $('#switch_'+beha.id).switchbutton({
					 	checked:true,
			            onChange: function(checked){
			            	modifyBehaviorStatus(this.id,checked)
			            }
			        });
				 }else{
					 $('#switch_'+beha.id).switchbutton({
				            onChange: function(checked){
				            	modifyBehaviorStatus(this.id,checked)
				            }
				        });
				 }
			 }
		 }  
	});

	var modifyBehaviorStatus = function(domid , checked){
		var id = domid.split('_')[1];
		var status = 0;
		if(checked){
			status = 1;
		}
		$.post("modifyBehaviorStatus",{"id":id,"status":status},function(data){
			var json = JSON.parse(data);
			if(json.status =="success"){
			    $.messager.alert("提示", "操作成功", "info");
			    $("#grid").datagrid("reload");
			}else{
				$.messager.alert("提示", json.message, "info");
				 $("#grid").datagrid("reload");
			}
		},"text");
	}
	var getConditionTypeName = function(val){
		var typename = "";
		switch (val) {
		case 'hour':
			typename = '小时';
			break;
		case 'day':
			typename = '天';
			break;
		case 'month':
			typename = '月';
			break;
		case 'year':
			typename = '年';
			break;
		case 'count':
			typename = '次数';
			break;
		case 'name':
			typename = '名称';
			break;
		default:
			break;
		}
		return typename;
	}
	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
		$("#addBehaviorView").dialog("open");
		
	});
	$('#behaviorType').combogrid({   //生成数据表单下拉框  使用Javascript通过已经定义的<select>或<input>标签来创建数据表格下拉框
	 	delay:500,// 1s延时查询
	 	editable : true,
		panelHeight: 'auto', 
		panelWidth: 300,
		idField: 'id',
		textField: 'behaviorName',
		url:'behaviorType_list',
		pageSize:10,
		required:true,
		columns : [ [ {
			field : 'behaviorName',
			title : '行为类型名称',
			width : '80%',
			align : 'center'
		}] ],
		pagination : true,//是否分页
		rownumbers : true,//序号
		keyHandler : {
			query : function(behaviorName) { // 动态搜索处理
				$('#behaviorType').combogrid("grid").datagrid('options').queryParams = 
					JSON.parse('{"behaviorName":"' + behaviorName + '"}');
				// 重新加载
				$('#behaviorType').combogrid("grid").datagrid("reload");
				$('#behaviorType').combogrid("setValue",behaviorName);
				 
			}
		}
	});
	var clearVal = function(){
		$("#id").val("");
		$('#behaviorType').combobox("setValue","");
		$('#behaviorType').combobox("setText","");
		$("#name").textbox("setValue","");
		$("#sort").textbox("setValue","");
		$("#isRatio").combobox("setValue","");
		$("#isIncrease").combobox("setValue","");
		$("#contriValue").textbox("setValue","");
		$("#contriDetail").textbox("setValue","");
		$('#conditionType').combobox("setValue","");
		$('#condition').textbox("setValue","");
		document.getElementById("memberComplain").checked = false;
		document.getElementById("employeeComplain").checked = false;
	};
	
	$("#btnMemberEdit").bind("click",function(){
		var selectedRow = $("#grid").datagrid("getSelections");
		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
   			$("#editViewForm").form('clear');
   			var JsonData = selectedRow[0];
   			$("#mbid").val(JsonData.id);
   			$("#member_behavior_name").html(JsonData.name);
   			$("#member_son_behavior_name").html(JsonData.behaviorType);
   			$("#is_ratio").combobox("setValue",JsonData.isRatio);
			$("#is_increase").combobox("setValue",JsonData.isIncrease);
			$("#contri_value").textbox("setValue",JsonData.contriValue);
			$("#contri_detail").textbox("setValue",JsonData.contriDetal);
   			$("#editBehaviorView").dialog("open").dialog("setTitle", "编辑记录");
   		}
	});
	
	$("#editBehaviorView").dialog({
		width : "420",
		height : 'auto',
		top : "100",
		left:'400',
		modal:true,
		title:"行为信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {

				$("#editViewForm").form("submit",{
					url:'modifyBehavior',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						var json = JSON.parse(result);
						if (json.status=="success") {
							$.messager.alert("提示", "修改成功", "info");
							$("#grid").datagrid("reload");
							$("#editBehaviorView").dialog("close");
						} else{
						    $.messager.alert("提示",json.message, "error");
						    $("#editBehaviorView").dialog("close");
						}
					}
				});
			
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#editBehaviorView").dialog("close");
			}
		}]
	});
	$("#btnEdit").bind("click",function(){
		//clearVal();
      	var selectedRow = $("#grid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要编辑的记录", "error");
   		}else{
   			$("#addViewForm").form('clear');
		   	var JsonData = selectedRow[0];
			$("#id").val(JsonData.id);
			$('#behaviorType').combobox("setValue",JsonData.behaviorId);
			$('#behaviorType').combobox("setText",JsonData.behaviorType);
			$("#name").textbox("setValue",JsonData.name);
			$("#sort").textbox("setValue",JsonData.sort);
			$("#isRatio").combobox("setValue",JsonData.isRatio);
			$("#isIncrease").combobox("setValue",JsonData.isIncrease);
			$("#contriValue").textbox("setValue",JsonData.contriValue);
			$("#contriDetail").textbox("setValue",JsonData.contriDetal);
			$('#conditionType').combobox("setValue",JsonData.conditionType);
			$('#can_add').combobox("setValue",JsonData.canAdd);
			$('#condition').textbox("setValue",JsonData.conditionVal);
			if("1" == JsonData.memberComplain){
				document.getElementById("memberComplain").checked = true;
			}
			if("1" == JsonData.employeeComplain){
				document.getElementById("employeeComplain").checked = true;
			}
			$("#addBehaviorView").dialog("open").dialog("setTitle", "编辑记录");
  		}	     
	});
	$("#addBehaviorView").dialog({
		width : "480",
		height : 'auto',
		top : "100",
		left:'400',
		modal:true,
		title:"行为信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit",{
					url:'addOrUpdateBehavior',
					onSubmit:function(){
						return $(this).form("validate");
					},
					success:function(result) {
						if (result=="success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#addBehaviorView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
							$("#addBehaviorView").dialog("close");
						}else{
						    $.messager.alert("提示", "操作失败", "error");
						    $("#addBehaviorView").dialog("close");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#addBehaviorView").dialog("close");
			}
		}]
	});
	$("#btnDelete").bind("click",function(){
  		var selectedRow = $("#grid").datagrid("getSelections");
		if(selectedRow.length <= 0){
		$.messager.alert("提示", "请选择要删除记录", "error");
		}else{
		var JsonData = selectedRow[0];
		$.messager.confirm("提示","确定要删除吗?",function(r){
      		if(r){
				$.post("delete_behavior",{"id":JsonData.id},function(data){
					if(data=="success"){
					    $("#grid").datagrid("reload");
					}else{
						$.messager.alert("提示", "删除失败", "info");
					}
				},"text");
			}
		});
		}	     
	});	

	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{
			'name':$("#s-name").textbox("getValue"),
			'maxContriValue':$("#s-maxContriValue").textbox("getValue"),
			'minContriValue':$("#s-minContriValue").textbox("getValue"),
			'behaviorId':$("#s-behaviorTypeName").combobox("getValue"),
			'isIncrease':$("#s-isIncrease").combobox("getValue"),
			'isRatio': $("#s-isRatio").combobox("getValue")
		});
	});
});
