$(function(){
	$('#grid').datagrid({
		title : '优惠券策略配置',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : true,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'queryCouponList',
		queryParams:{						
			'itemName':$("#itemName").combobox("getValue"),
			'couponState':$("#couponState").textbox("getValue"),
			'queryDateFrom':$("#queryDateFrom").textbox("getValue"),
		},
		
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'itemName',
			title : '策略项类型',
			width : $(this).width() * 0.1,
			align : 'center'
		},  {
            field : 'cityName',
            title : '生效城市',
            width : $(this).width() * 0.1,
            align : 'center'
        }, {
            field : 'cityCode',
			hidden:'true',
            title : '生效城市',
            width : $(this).width() * 0.1,
            align : 'center'
        }, {
			field : 'min',
			title : '应用此策略的下限(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null"){
					return "";
				}else{
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");
				}
			}
			
		}, {
			field : 'max',
			title : '应用此策略的上限(元)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null"){
					return "";
				}else{
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");
				}
			}
		}, {
			field : 'balance',
			title : '优惠券面值(元/折)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null"){
					return "";
				}else{
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");
				}
			}
		},{
			field : 'number',
			title : '优惠券张数(张)',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'total',
			title : '合计优惠金额(元/折)',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "null"){
					return "";
				}else{
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");
				}
			}
		},
//		{
//			field : 'startTime',
//			title : '策略生效时间',
//			width : $(this).width() * 0.1,
//			align : 'center',
//		},
		{
			field : 'deadline',
			title : '策略有效期限(天)',
			width : $(this).width() * 0.1,
			align : 'center',

		},{
			field : 'useType',
			title : '优惠券类型',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(val == 1){
					return "满减券";
				}else if(val == 2){
					return "折扣券";
				}else{
       			    return "现金券";
				}
			}
		},{
			field : 'minUseValue',
			title : '最低使用值',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				//if(rec.useType == 1 || rec.useType == 2){
					n = (val/100).toFixed(2);
				    var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
       			    return n.replace(re, "$1,");
				//}else{
       			//    return "无";
				//}
			}
		},{
			field : 'maxDeductionValue',
			title : '封顶额',
			width : $(this).width() * 0.1,
			align : 'center',
			formatter : function(val, rec) {
				if(rec.useType == 2){
					if(val == null || val == 0||val == "null" || val == "0"){
						return "无";
					}else{
						n = (val/100).toFixed(2);
						var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
						return n.replace(re, "$1,");
					}
				}else{
					return "无";
				}
			}
		}]],
		pagination : true,
		rownumbers : true
	});
	
	 $('#AitemName').combobox({
		 
		    onSelect: function(rec){   

		    	var value = $("#AitemName").combobox('getValue');   
		    	$('#itemCode').val(value);
//		    	var mydate  = new Date();
//		    	alert(mydate.toLocaleString());
//		    	$('#createTime').val(mydate.toLocaleString());
		    	
	       }		 
 });
	 
	 
	 
	 
	 
	 
	 $('#e-itemName').combobox({
		 
		    onSelect: function(rec){   

		    	var date=$("#e-itemName").combobox('getValue');   
		    	$('#U_itemCode').val(date);
		    	
		    	//alert($('#U_itemCode').val());
	       }		 
});
	 
//清空Combogrid
$("#clearQuery").bind("click",function(){
    $('#toolbar').form('clear');
});


	//add view
	$("#addView").dialog( {
		width : "480",
		height : "450",
		top : "100",
		left:'400',
		modal:true,
		title:"添加优惠券信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#addViewForm").form("submit",{
					url:'saveOrUpdateCoupon',
					onSubmit:function(){					
						var flag = $(this).form("validate");
		                if(!flag){
		                	$.messager.progress('close'); 
		                }
		                var couponUseType = $("#couUseType").combobox("getValue");
		                var balance = $("#balance").textbox("getValue");
		                if(balance < 0 ){
		                	$.messager.alert("提示", "面值不能为负数", "error");
		                	$.messager.progress('close'); 
		                	flag = false;
		                }
		                if(couponUseType == '2'){
		                	if(balance > 10 ){
		                		$.messager.alert("提示", "折扣不能大于10", "error");
		                		$.messager.progress('close'); 
		                		flag = false;
		                	}
		                }
						return flag;							
					},
					success:function(result) {
						if (result== "success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#addView").dialog("close");
						}else if(result== "minGreaterThanMax"){
                            $.messager.alert("提示", "策略下限不能大于上限", "error");
						}else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#addView").dialog("close");
			}
		}]
	});
	
	

	$("#editView").dialog({
		width : "480",
		height : "450",
		top : "100",
		left:'400',
		modal:true,
		title:"修改优惠券信息",
		buttons : [{
			text : "确定",
			iconCls : "icon-save",
			handler : function() {
				$("#editViewForm").form("submit",{
					url:'saveOrUpdateCoupon',
					onSubmit:function(){
						var flag = $(this).form("validate");
		                if(!flag){
		                	$.messager.progress('close'); 
		                }
		                var couponUseType = $("#e-couUseType").combobox("getValue");
		                var balance = $("#e-balance").textbox("getValue");
		                if(balance < 0 ){
		                	$.messager.alert("提示", "面值不能为负数", "error");
		                	$.messager.progress('close'); 
		                	flag = false;
		                }
		                if(couponUseType == '2'){
		                	if(balance > 10 ){
		                		$.messager.alert("提示", "折扣不能大于10", "error");
		                		$.messager.progress('close'); 
		                		flag = false;
		                	}
		                }
						return flag;						
					},
					success:function(result) {
						if (result== "success") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#editView").dialog("close");
						}else if(result== "num-wrong"){
						    $.messager.alert("提示", "请输入格式正确的数字", "error");
						}else if(result== "minGreaterThanMax"){
                            $.messager.alert("提示", "策略下限不能大于上限", "error");
                        }else{
						    $.messager.alert("提示", "操作失败", "error");
						}
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#editView").dialog("close");
			}
		}]
	});
	
	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("load",{			
			'itemName':$("#itemName").combobox("getValue"),
			'couponState':$("#couponState").textbox("getValue"),
			'queryDateFrom':$("#queryDateFrom").textbox("getValue"),
			'city_code':$("#q_city_code").combobox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		$("#memberSearchForm").form("clear");
	});
	
	
	$("#btnSave").bind("click",function(){
		$("#addViewForm").form("clear");
	    $("#startTime").datetimebox({  
			required : true,  
			 	onShowPanel:function(){  
    		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
			}  
		});
		$("#addView").dialog("open");
		$('#a-maxDeductionVal').hide(); 
     	$('#a-minUseVal').hide();
	});
	
	$("#btnEdit").bind("click",function(){
		$("#editView").form("clear");
		var selectedRows = $("#grid").datagrid("getSelections");
		if(selectedRows.length <= 0){
			$.messager.alert("提示", "请选择需要修改的用户", "error");
		}else{
			$("#e-id").val(selectedRows[0].id);
            $("#city_code2").combobox("setValue",selectedRows[0].cityCode);
			$("#e-min").textbox("setValue",selectedRows[0].min/100);
			$("#e-max").textbox("setValue",selectedRows[0].max/100);
			$("#e-balance").textbox("setValue",selectedRows[0].balance/100);
			$("#e-number").textbox("setValue",selectedRows[0].number);
			$("#e-total").textbox("setValue",selectedRows[0].total);
			$("#e-startTime").textbox("setValue",selectedRows[0].startTime);
			$("#e-deadline").textbox("setValue",selectedRows[0].deadline);
			$("#e-couUseType").combobox("setValue",selectedRows[0].useType);
			$("#e-minUseValue").textbox("setValue",selectedRows[0].minUseValue/100);
			$("#e-maxDeductionValue").textbox("setValue",selectedRows[0].maxDeductionValue/100);
			$("#editView").dialog("open");
			var ut = selectedRows[0].useType;
			switch (ut) {
			case 0:
				$('#maxDeductionVal').hide(); 
				$('#minUseVal').hide(); 
				break;
			case 1:
				$('#maxDeductionVal').hide(); 
				$('#minUseVal').show(); 
				break;
			case 2:
				$('#maxDeductionVal').show(); 
				$('#minUseVal').hide(); 
				break;
			default:
				break;
			}
            itemCode =  selectedRows[0].itemCode;
        }
    });
	var itemCode;
	//删除信息
	$("#btnDelete").bind("click",function(){
		debugger;
		var selectedRows = $("#grid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要删除信息 ", "error");
		 }else{
			 var _id =  selectedRows[0].id;
			 
			// alert(_id);
				$.messager.confirm("提示","确定要删除吗?",function(r){
					if(r){
						$.post("deleteCouponById",{id:_id},function(data){
							if(data == "success"){
								$.messager.alert("提示", "操作成功", "info");
								$("#grid").datagrid("reload");
							}else{
								 $.messager.alert("提示", "操作失败", "error");
							}
						},"text");
					}
				});
		 }
	});

    $("#city_code").combobox({
        onChange:function(){
        	var cityCode = $(this).combobox("getValue");
            $.getJSON("couponnames?cityCode="+cityCode, function(json) {
                $('#AitemName').combobox({
                    data : json
                });
            });
        }});

    $("#city_code2").combobox({
        onChange:function(){
            var cityCode = $(this).combobox("getValue");
            $.getJSON("couponnames?cityCode="+cityCode, function(json) {
                $('#e-itemName').combobox({
                    data : json
                });
            });

        }, onSelect:function(){
            var cityCode = $(this).combobox("getValue");
            $.getJSON("couponnames?cityCode="+cityCode, function(json) {
                $('#e-itemName').combobox({
                    data : json
                });
            });

        }

    });

    $("#e-itemName").combobox({
        onLoadSuccess:function(){
            $("#e-itemName").combobox("setValue", itemCode);
        }
    });


});

