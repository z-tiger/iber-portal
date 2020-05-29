$(function() {
			//清空
			$("#clearQuery").bind("click",function(){
				clearToolbar();
			});
			//查询链接
			$(document).keydown(function(event){
			    var itemName = $("#q_itemname").val();
			    var status = $("#e-status").combobox("getValue");
                var city_code = $("#q_city_code").combobox("getValue");
				if(event.keyCode==13){
				  $('#dataGrid').datagrid('load',{
					  itemName:itemName,
					  status:status,
                      city_code:city_code
		          });
				}
			});
			//查询链接
			$("#btnQuery").bind("click",function(){
				 var itemName = $("#q_itemname").val();
				 var status = $("#e-status").combobox("getValue");
                 var city_code = $("#q_city_code").combobox("getValue");
			    $('#dataGrid').datagrid('load',{
			    	 itemName:itemName,
					 status:status,
                     city_code:city_code
		        });
			});
			
			
			$('#dataGrid').datagrid( {
				title : '优惠券配置',
				width : 'auto',
				height : 'auto',
				fit : true,
				fitColumns : true,
				nowrap : true,
				striped : true,
				collapsible : true,
				rownumbers : true,
				singleSelect : true,
				url : 'dataListCouponItem.do',
				pageSize : 100,
				pageList : [100,50,30,10],
				idField : 'id',
				columns : [ [{
					field : 'ck',
					checkbox:true
				},   
					{
						field:'itemname',
						title:'项名称',
						align:'center',
						width : $(this).width() * 0.12,
					},
					{
						field:'itemcode',
						title:'项编码',
						align:'center',
						width : $(this).width() * 0.15,
					},{
                        field:'cityName',
                        title:'所属城市',
                        align:'center',
                        width : $(this).width() * 0.15,
                    },
					{	
						field:'number',
						title:'数量',align:'center',
						width : $(this).width() * 0.08,
					},
					{
						field:'balance',
						title:'面值(元/折)',
						align:'center',
						width : $(this).width() * 0.08,
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
					{
						field:'deadline',
						title:'期限(天)',
						align:'center',
						width : $(this).width() * 0.08,
					},
					{
						field:'status',
						title:'状态',
					    align:'center',
					    width : $(this).width() * 0.08,
					    formatter:function(val,row){
					    	if (val == "1") {
							return "启用";
					    	} else {
							return "关闭";
					    	}
					    }
					},
					{
						field:'useType',
						title:'生成的优惠券类型',
					    align:'center',
					    width : $(this).width() * 0.1,
					    formatter:function(val,row){
					    	if (val == "1") {
					    		return "满减券";
					    	}else if(val == "2"){
					    		return "折扣券";
					    	} else {
					    		return "现金券 ";
					    	}
					    }
					},
					{
						field:'minUseValue',
						title:'最低使用值',
					    align:'center',
					    width : $(this).width() * 0.08,
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
					{
						field:'maxDeductionValue',
						title:'封顶额',
						align:'center',
						width : $(this).width() * 0.08,
						formatter : function(val, rec) {
							if(rec.useType == "2"){
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
					},
					{
						field:'startTime',
						title:'活动开始时间',
						align:'center',
						width : $(this).width() * 0.15,
					},
					{
						field:'endTime',
						title:'活动结束时间',
						align:'center',
						width : $(this).width() * 0.15,
					},
					{
						field:'createName',
						title:'创建人',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'createtime',
						title:'创建时间',
						align:'center',
						width : $(this).width() * 0.15,
						formatter:function(value,row,index){
								return row.createtime;
						}
					},
					{
						field:'updateName',
						title:'修改人',
						align:'center',
						width : $(this).width() * 0.1,
					},
					{
						field:'updatetime',
						title:'修改时间',
						align:'center',
						width : $(this).width() * 0.15,
					}
				] ],
				pagination : true,
				rownumbers : true
			}); 
			
	//构造对话框
	$("#view").dialog( {
		width : "400",
		height : "400",
		top : "80",
		modal:true,
		buttons : [ {
			text : "保存",
			iconCls : "icon-save",
			handler : function() {
				$("#viewForm").form("submit", {
					url : "saveOrUpdateCouponItem.do",
					onSubmit : function() {
						var itemcode = document.getElementById('itemcode').value.trim();
						var reg = reg=/^[a-zA-Z]+$/;
//						if(!reg.test(itemcode)){    
//							$.messager.alert("提示", "项编码格式不正确", "info");   
//							return false;
//				        }   
						$.messager.progress({
		                    text:"正在加载，请稍等！"
		                });
		                var flag = $(this).form("validate");
		                if(!flag){
		                	$.messager.progress('close'); 
		                }
		                var couponUseType = $("#couponUseType").combobox("getValue");
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
					success : function(result) {
						$.messager.progress('close'); 
						if (result == "success") {
							$.messager.alert("提示", "保存成功", "info");
						    $("#dataGrid").datagrid("reload");
							$("#view").dialog("close");
						}else if(result == "fail"){
							$.messager.alert("提示", "编码已经存在", "info");
						/*	$("#itemcode")*/
						}else if(result == "num-wrong"){
							$.messager.alert("提示", "填写的数值有误", "info");
							/*	$("#itemcode")*/
						}else if(result == "timeErr"){
							$.messager.alert("提示", "开始时间必须小于结束时间", "info");	
						}
						else{
							$.messager.alert("提示", "保存失败", "info");
						    $("#dataGrid").datagrid("reload");
							$("#view").dialog("close");
						} 
					}
				});
				}
			}, {
				text : "取消",
				iconCls : "icon-cancel",
				handler : function() {
					$("#view").dialog("close");
			}
		}]
	});
			
	//添加
	$("#btnAdd").bind("click",function(){
		$("#viewForm").form("clear");
		$("#view").dialog({title:"添加信息"});
		$("#itemcode").textbox('textbox').attr('readonly',false);  //添加时 项编码可以填写
		$("#view").dialog("open");
		$('#maxDeductionVal').hide(); 
      	$('#minUseVal').hide();
	});
			
	//修改信息
	$("#btnEdit").bind("click",function(){
		$("#viewForm").form("clear");
		var selectedRows = $("#dataGrid").datagrid("getSelections");
		if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择要修改的记录", "error");
		}else{
			
			$("#id").val(selectedRows[0].id);
			$("#itemname").textbox('setValue',selectedRows[0].itemname); 
			$("#itemname").textbox('textbox').attr('readonly',true);
			$("#itemcode").textbox('setValue',selectedRows[0].itemcode); 
			$("#itemcode").textbox('textbox').attr('readonly',true);   //修改时项编码不能修改
			$("#number").textbox('setValue',selectedRows[0].number); 
			$("#balance").textbox('setValue',selectedRows[0].balance/100); 
			$("#status").combobox('setValue',selectedRows[0].status);
			$("#deadline").textbox('setValue',selectedRows[0].deadline);
			$("#couponUseType").combobox('setValue',selectedRows[0].useType);
            $("#city_code").combobox('setText',selectedRows[0].cityName);
			$("#minUseValue").textbox('setValue',selectedRows[0].minUseValue/100);
			$("#maxDeductionValue").textbox('setValue',selectedRows[0].maxDeductionValue/100);
			var name = selectedRows[0].itemname;
			if(name.indexOf("四星")!=-1 || name.indexOf("五星")!=-1){
				$("#status").combobox('setValue',selectedRows[0].status);
				$("#status").combobox('readonly',false);
				$("#start").textbox('setValue',selectedRows[0].startTime);
				$("#start").textbox({required:false});
				$("#end").textbox('setValue',selectedRows[0].endTime);
				$("#end").textbox({required:false});
				$("#start").combobox('readonly',true);
				$("#end").combobox('readonly',true);
			}else{
				$("#start").textbox('setValue',selectedRows[0].startTime);
				$("#start").textbox({required:true});
				$("#end").textbox('setValue',selectedRows[0].endTime);
				$("#end").textbox({required:true});
				$("#status").combobox('readonly',true);
				$("#start").combobox('readonly',false);
				$("#end").combobox('readonly',false);
			}
			$("#view").dialog({title:"修改信息"});
			$("#view").dialog("open")
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
		}
	});
		
	//删除操作
	$("#btnRemove").bind("click",function(){
      	var selectedRow = $("#dataGrid").datagrid("getSelections");
   		if(selectedRow.length <= 0){
			$.messager.alert("提示", "请选择要删除的策略", "error");
   		}else{
			var JsonData = selectedRow[0];
			$.messager.confirm("提示","确定要删除吗?",function(r){
	      		if(r){
					$.post("deleteCouponItemById.do",{"id":JsonData.id},function(data){
						if(data=="success"){
							//$.messager.alert("提示", "删除成功", "info");
						    $("#dataGrid").datagrid("reload");
						}else{
							$.messager.alert("提示", "删除失败", "info");
						}
					},"text");
				}
			});
  		}	     
	});
			
});
	function clearToolbar(){
		$('#toolbar').form('clear');
	}
		