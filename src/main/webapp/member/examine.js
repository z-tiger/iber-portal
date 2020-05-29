$(function(){
    var idCardNoUtil = {
        provinceAndCitys: {11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",
            31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",
            45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",
            65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"},

        powers: ["7","9","10","5","8","4","2","1","6","3","7","9","10","5","8","4","2"],

        parityBit: ["1","0","X","9","8","7","6","5","4","3","2"],

        genders: {male:"男",female:"女"},

        checkAddressCode: function(addressCode){
            var check = /^[1-9]\d{5}$/.test(addressCode);
            if(!check) return false;
            if(idCardNoUtil.provinceAndCitys[parseInt(addressCode.substring(0,2))]){
                return true;
            }else{
                return false;
            }
        },

        checkBirthDayCode: function(birDayCode){
            var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
            if(!check) return false;
            var yyyy = parseInt(birDayCode.substring(0,4),10);
            var mm = parseInt(birDayCode.substring(4,6),10);
            var dd = parseInt(birDayCode.substring(6),10);
            var xdata = new Date(yyyy,mm-1,dd);
            if(xdata > new Date()){
                return false;//生日不能大于当前日期
            }else if ( ( xdata.getFullYear() == yyyy ) && ( xdata.getMonth () == mm - 1 ) && ( xdata.getDate() == dd ) ){
                return true;
            }else{
                return false;
            }
        },

        getParityBit: function(idCardNo){
            var id17 = idCardNo.substring(0,17);
            var power = 0;
            for(var i=0;i<17;i++){
                power += parseInt(id17.charAt(i),10) * parseInt(idCardNoUtil.powers[i]);
            }
            var mod = power % 11;
            return idCardNoUtil.parityBit[mod];
        },

        checkParityBit: function(idCardNo){
            var parityBit = idCardNo.charAt(17).toUpperCase();
            if(idCardNoUtil.getParityBit(idCardNo) == parityBit){
                return true;
            }else{
                return false;
            }
        },

        checkIdCardNo: function(idCardNo){
//15位和18位身份证号码的基本校验
            var check = /^\d{15}|(\d{17}(\d|x|X))$/.test(idCardNo);
            if(!check) return false;
//判断长度为15位或18位
            if(idCardNo.length==15){
                return idCardNoUtil.check15IdCardNo(idCardNo);
            }else if(idCardNo.length==18){
                return idCardNoUtil.check18IdCardNo(idCardNo);
            }else{
                return false;
            }
        },

//校验15位的身份证号码
        check15IdCardNo: function(idCardNo){
//15位身份证号码的基本校验
            var check = /^[1-9]\d{7}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}$/.test(idCardNo);
            if(!check) return false;
//校验地址码
            var addressCode = idCardNo.substring(0,6);
            check = idCardNoUtil.checkAddressCode(addressCode);
            if(!check) return false;
            var birDayCode = '19' + idCardNo.substring(6,12);
//校验日期码
            return idCardNoUtil.checkBirthDayCode(birDayCode);
        },

//校验18位的身份证号码
        check18IdCardNo: function(idCardNo){
//18位身份证号码的基本格式校验
            var check = /^[1-9]\d{5}[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))\d{3}(\d|x|X)$/.test(idCardNo);
            if(!check) return false;
//校验地址码
            var addressCode = idCardNo.substring(0,6);
            check = idCardNoUtil.checkAddressCode(addressCode);
            if(!check) return false;
//校验日期码
            var birDayCode = idCardNo.substring(6,14);
            check = idCardNoUtil.checkBirthDayCode(birDayCode);
            if(!check) return false;
//验证校检码
            return idCardNoUtil.checkParityBit(idCardNo);
        },

        formateDateCN: function(day){
            var yyyy =day.substring(0,4);
            var mm = day.substring(4,6);
            var dd = day.substring(6);
            return yyyy + '-' + mm +'-' + dd;
        },

//获取信息
        getIdCardInfo: function(idCardNo){
            var idCardInfo = {
                gender:"", //性别
                birthday:"" // 出生日期(yyyy-mm-dd)
            };
            if(idCardNo.length==15){
                var aday = '19' + idCardNo.substring(6,12);
                idCardInfo.birthday=idCardNoUtil.formateDateCN(aday);
                if(parseInt(idCardNo.charAt(14))%2==0){
                    idCardInfo.gender=idCardNoUtil.genders.female;
                }else{
                    idCardInfo.gender=idCardNoUtil.genders.male;
                }
            }else if(idCardNo.length==18){
                var aday = idCardNo.substring(6,14);
                idCardInfo.birthday=idCardNoUtil.formateDateCN(aday);
                if(parseInt(idCardNo.charAt(16))%2==0){
                    idCardInfo.gender=idCardNoUtil.genders.female;
                }else{
                    idCardInfo.gender=idCardNoUtil.genders.male;
                }

            }
            return idCardInfo;
        },

        getId15:function(idCardNo){
            if(idCardNo.length==15){
                return idCardNo;
            }else if(idCardNo.length==18){
                return idCardNo.substring(0,6) + idCardNo.substring(8,17);
            }else{
                return null;
            }
        },

        getId18: function(idCardNo){
            if(idCardNo.length==15){
                var id17 = idCardNo.substring(0,6) + '19' + idCardNo.substring(6);
                var parityBit = idCardNoUtil.getParityBit(id17);
                return id17 + parityBit;
            }else if(idCardNo.length==18){
                return idCardNo;
            }else{
                return null;
            }
        }
    };


//验证护照是否正确
    function checknumber(number){
        var str=number;
//在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
        var Expression=/(P\d{7})|(G\d{8})/;
        var objExp=new RegExp(Expression);
        if(objExp.test(str)==true){
            return true;
        }else{
            return false;
        }
    }

    // 对于外国人身份证扩展
    function identityCodeValidExternal(idCard) {
    	const idCardStr = idCard.toString();
        // var len = idCardStr.length;
        // if(len < 6 || len > 18){
        // 	return false;
        // }else if (len < 15){
        //     return true;
        // }else if(idCardNoUtil.checkIdCardNo(idCardStr)){
        //     return true;
        // }
        // return false;
		return idCardNoUtil.checkIdCardNo(idCardStr);
    }
      
      
    $.extend($.fn.validatebox.defaults.rules, {     
        idcared: {     
            validator: function(value,param){    
                return identityCodeValidExternal(value);
            },
            message: '不是有效的身份证号码'    
        }     
    });  
	$('#grid').datagrid({
		title : '会员审核',
		width : 'auto',
		height : 'auto',
		fit : true,
		fitColumns : false,
		nowrap : true,
		striped : true,
		collapsible : true,
		singleSelect : true,
		url : 'member_examine_d_list',
		queryParams:{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue")
		},
		pageSize : 100,
		pageList : [100,50,30,10],
		idField : 'id',
		columns : [ [ {
			field : 'ck',
			checkbox : true

		}, {
			field : 'cityName',
			title : '所属城市',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'name',
			title : '姓名',
			width : $(this).width() * 0.06,
			align : 'center'
		}, {
			field : 'sex',
			title : '性别',
			width : $(this).width() * 0.04,
			align : 'center'
		}, {
			field : 'phone',
			title : '手机号码',
			width : $(this).width() * 0.08,
			align : 'center'
		},{
			field : 'idcard',
			title : '身份证号码',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'driverIdcard',
			title : '驾驶证号码',
			width : $(this).width() * 0.1,
			align : 'center'
		},{
			field : 'driverIdCardTime',
			title : '初次领证日期',
			align : 'center'
		},{
			field : 'driverIdcardValidityTime',
			title : '驾驶证有效期',
			align : 'center'
		},/*{
			field : 'accoutStatus',
			title : '资金状态',
			width : $(this).width() * 0.04,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "0"){
					return "正常";
				}else{
					return "冻结";
				}
			}
		},{
			field : 'memberLevel',
			title : '会员等级',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "account"){
					return "企业账号";
				}else if(val == "manager"){
					return "企业管理员";
				}else if(val == "person"){
					return "企业普通会员";
				}else if(val == "platinum"){
					return "白金企业用户";
				}else if(val == "general"){
					return "个人普通";
				}else if(val == "vip"){
					return "vip会员";
				}else if(val == "gold"){
					return "黄金会员";
				}else{
					return val;
				}
			}
		},{
			field : 'remark',
			title : '审核结果',
			width : $(this).width() * 0.1,
			align : 'center'
		},*/{
			field : 'status',
			title : '状态',
			width : $(this).width() * 0.12,
			align : 'center',
			formatter : function(val, rec) {
				console.log(rec)
				if(val == "ready")
					return "就绪";
				else if(val == "experience")
					if(rec.refuseReason !=null && rec.refuseReason !=""){
						return "体验  ("+rec.refuseReason+")";
					}else{
						return "体验";
					}
				else if(val=="ordered")
					return "预约";
				else if(val=="useCar")
					return "用车";
				else if(val=="planReturn")
					return "计划还车";
				else if(val=="waitQueue")
					return "排队等待";
				else if(val=="wait")
					return "等待用车";
				else if(val=="return")
					return "还车";
				else return val;   
			}
		},{
			field : 'driverIdcardPhotoUrl',
			title : '驾驶证照片',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(value, row, index) {
				if(null == value || value == ""){
					return "未上传";
				}else{
					return "已上传";
				}
			}
		},/*{
			field : 'fingerPrint',
			title : '指纹',
			width : $(this).width() * 0.06,
			align : 'center',
			formatter : function(val, rec) {
				if(null == val || val == ""){
					return "未录入";
				}else{
					return "已录入";
				}
			}
		},*/{
			field : 'registerIp',
			title : '注册IP',
			align : 'center'
		},{
			field : 'registerCategory',
			title : '注册终端',
			width : $(this).width() * 0.08,
			align : 'center',
			formatter : function(val, rec) {
				if(val == "platform"){
					return "平台注册";
				}else if(val == "member"){
					return "Android终端注册";
				}else if(val == "qq"){
					return "QQ注册";
				}else if(val == "weixin"){
					return "微信注册";
				}else if(val == "weibo"){
					return "微博注册";
				}else if(val == "ios"){
					return "IOS终端注册";
				}else{
					return "其它";
				}
			}
		},{
			field : 'createTime',
			title : '注册时间',
			align : 'center'
		},{
			field : 'uploadTime',
			title : '资料上传时间',
			align : 'center'
		},{
            field : 'channel',
            title : '渠道',
            align : 'center',
            formatter:function (val) {
                if(val==2) {
                    return "资料变更审核";
                }else {
                    return "注册审核";
                }

            }
        }/*,{
			field : 'email',
			title : 'Email',
			width : $(this).width() * 0.08,
			align : 'center'
		}*/] ],
		pagination : true,
		rownumbers : true
	});
	
	

	
	//query
	$("#btnQuery").bind("click",function(){
		$("#grid").datagrid("clearSelections");
		$("#grid").datagrid("load",{
			'cityCode':$("#scityCode").combobox("getValue"),
			'name':$("#s-name").textbox("getValue"),
			'phone':$("#s-phone").textbox("getValue"),
			'bt':$("#s-bt").datetimebox("getValue"),
			'et':$("#s-et").datetimebox("getValue")
		});
	});
	
	//clear
	$("#btnClear").bind("click",function(){
		$("#memberSearchForm").form("clear");
	});
	
	
	
	$("#examineView").dialog( {
		width : "490",
		height : "520",
		top : "100",
		left:"400",
		modal:true,
		title:"会员实名认证及驾照审核",
		buttons : [{
			text : "实名认证",
			iconCls : "icon-ok",
			id:'examineBtn',
			handler : function() {
				var _name = $("#examine_name").val();
				var _idcard = $("#examine_idcard").val();
				var driverIdcard = $("#examine_driverIdcard").val();
				var phone = $("#examine_phone").text();
				$.post("member_examine",
					{name:_name,
						idcard:_idcard,
                        driverIdcard:driverIdcard,
                        phone:phone},function(data){
					if(data == "1"){
						$("#examine_result_1").html("实名认证通过");
						$("#examine_result_2").html("");
					}else{
						$("#examine_result_1").html("");
						$("#examine_result_2").html("实名认证不通过");
					}
				},"text");
				$("#examineBtn").linkbutton({ disabled: false });
			}
		},{
			text : "审核",
			iconCls : "icon-save",
            id:'checkBtn',
			handler : function() {
			    var channecl= $('#channel').val();
			    var url ='member_examine_save';
			    if(channecl==2) {
                    url = "lost_phone_member_examine";
                }
				$("#examineForm").form("submit",{
					url:url,
					onSubmit:function(){
						var  remark= $("#remark").combobox("getValue");
						if(remark != ""){
							return true;
						}
						if ($("input[name='remark']").val()==''){
							if ($("#examine_idcard").val() == '') {
								$("#idcard_result").html("身份证号必填");
								return false;
							}
							if ($("#examine_driverIdcard").val() == '') {
								$("#driver_card_result").html("驾驶证号必填");
								return false;
							}

						}
						var tmp = $("#examine_idcard").val();
						var flag = identityCodeValidExternal(tmp);
						if(flag==true){
							$("#idcard_result").html("");
						}else{
							$("#idcard_result").html("不是有效的身份证号码");
							return false;
						}
						if ($("#examine_driverIdcard").val() !=$("#examine_idcard").val() ) {
                            $("#driver_card_result").html("与身份证号不一致");
                            return false;
                        }
						var firstTime = $("#examine_driverIdcardTime").datebox("getValue");
						var validTime = $("#examine_driverIdcardValidityTime").datebox("getValue");
						if(firstTime != "" && validTime != ""){
							var start = new Date(firstTime);
							var end =new  Date(validTime);
							if(start.getTime() >= end.getTime()){
								$.messager.alert("提示", "初次领证时间必须小于驾驶证有效时间", "error");
								return false;
							}
						}else{
							$.messager.alert("提示", "初次领证时间或驾驶证有效时间不能为空", "error");
							return false;
						}
						
					},
					success:function(result) {
					    console.log(result);
						if (result== "succ") {
							$.messager.alert("提示", "操作成功", "info");
							$("#grid").datagrid("reload");
							$("#grid").datagrid("clearSelections");
							$("#examineView").dialog("close");
						} else if(result == "fail") {
							$.messager.alert("提示", "操作失败", "error");
						}else if(result =="res"){
						    $.messager.alert("提示", "已有会员信息", "error");
						    $("#idcard_result").html("身份证已存在");
						}else if(result == 'already'){
							  $.messager.alert("提示", "会员已审核，不可重复审核", "error");
						}else if(result=='remark_to_long'){
                            $.messager.alert("提示", "审核结果不可以超过十五字！", "error");
						}else if(result=="age_fail"){
						    $.messager.alert("提示", "不满18周岁不可通过审核！", "error");
						}else if(result=="atypism"){
                            $.messager.alert("提示", "与身份证号不一致！", "error");
                        }else if(result=="no_change"){
                            $.messager.alert("提示", "操作成功", "info");
                            $("#grid").datagrid("reload");
                            $("#grid").datagrid("clearSelections");
                            $("#examineView").dialog("close");
                        }
					}
				});
			}
		},{
			text : "取消",
			iconCls : "icon-cancel",
			handler : function() {
				$("#examineView").dialog("close");
			}
		}]
	});
	
	
	$("#examineViewImg").dialog( {
		left:"10",
		top:"10",
		title:"驾照预览",
	});	
	
	//审核
	$("#btnExamine").bind("click",function(){
		$("#examineForm").form("clear");
		$("#examineBtn").linkbutton({ disabled: false });
		$("#examine_result_1").html("");
		$("#examine_result_2").html("");
		$("#idcard_result").html("");
        $("#driver_card_result").html("");
		var selectedRows = $("#grid").datagrid("getSelections");
		 if(selectedRows.length <= 0){
				$.messager.alert("提示", "请选择实名认证及驾照审核的用户", "error");
		 }
		 /*
		  * 
			 $.messager.alert("提示", "该会员资料不完善，请先完善资料", "error");
		 }*/
		 else if(selectedRows[0].status != "experience"){
			 $.messager.alert("提示", "该会员已经审核通过", "error");
		 }else{
			    $("#examine_id").val(selectedRows[0].id);
			    //$("#examine_name").html(selectedRows[0].name);
			    $("#examine_phone").html(selectedRows[0].phone);
			    $('#phone').val(selectedRows[0].phone);
			    //$("#examine_idcard").html(selectedRows[0].idcard);
			    //$("#examine_driverIdcard").html(selectedRows[0].driverIdcard);
			    $("#examine_name").textbox("setValue",selectedRows[0].name);
				//$("#examine_phone").textbox("setValue",selectedRows[0].phone);
				$("#examine_idcard").textbox("setValue",selectedRows[0].idcard);
                $('#channel').val( selectedRows[0].channel);
				/*var tmp = $("#examine_idcard").val();
			  	  var flag = identityCodeValidExternal(tmp);
			      if (flag) {
			      $("#idcard_result").html("");
			      }else {
			         $("#idcard_result").html("不是有效的身份证号码");
			         return ;
			      }*/
			    $("#examine_driverIdcard").textbox("setValue",selectedRows[0].driverIdcard);
				// 驾驶证有效期时间
				$("#examine_driverIdcardValidityTime").datebox("setValue",selectedRows[0].driverIdcardValidityTime);
				$("#examine_driverIdcardPhotoUrl").attr("src", selectedRows[0].driverIdcardPhotoUrl);
				$("#examine_driverIdcardPhotoUrlAHERF").attr("href", "drierCarImgOpen?img="+selectedRows[0].driverIdcardPhotoUrl);
				$("#examine_idcardPhotoUrl").attr("src", selectedRows[0].idcardPhotoUrl);
				$("#examine_idcardPhotoUrlAHERF").attr("href", "idcardPhotoOpen?img="+selectedRows[0].idcardPhotoUrl);
				$("#examineView").dialog("open");
				//在身份证输入框失去焦点时补全驾驶证号
				var value = $("#examine_idcard").val();
                var ex_driverIdcard=$("#examine_driverIdcard").val(); //获取驾驶证号码
                if(value!=ex_driverIdcard){
                     $("#driver_card_result").html("与身份证号不一致");
                }
				if ($("#examineView").parent().is(":hidden")==false){
					$("input",$("#examine_idcard").next("span")).blur(function(){
                            value = $("#examine_idcard").val();
                            ex_driverIdcard=$("#examine_driverIdcard").val(); //获取驾驶证号码
						var booleanValue = identityCodeValidExternal(value);
						if (booleanValue==true){
							$("#idcard_result").html("");
					//		$("#examine_driverIdcard").textbox("setValue",$("#examine_idcard").val());
                            if(value!=ex_driverIdcard){
                            	 $("#driver_card_result").html("与身份证号不一致");
                            }else{
                                 $("#driver_card_result").html("");
                            }
						}else {
						    $("#driver_card_result").html("");
							$("#idcard_result").html("不是有效的身份证号码");
					//		$("#examine_driverIdcard").textbox("setValue","");
						}

					})
					$("input",$("#examine_driverIdcard").next("span")).blur(function(){
                    		 value = $("#examine_idcard").val();
                    		 ex_driverIdcard=$("#examine_driverIdcard").val(); //获取驾驶证号码
                             if(value!=ex_driverIdcard){
                                    $("#driver_card_result").html("与身份证号不一致");
                              }else{
                                    $("#driver_card_result").html("");
                              }
                    })
				}
				$('#examine_driverIdcardTime').datebox({
				    onSelect: function(date){
			             var nowDate = new Date().getTime();
			        	 var chooseData = new Date(date).getTime();					      
			             if (nowDate<chooseData) {
							$("#driver_idcard_time_result").html("请选择比当前日期小的日期");
						 }else{
							$("#driver_idcard_time_result").html("");
						 }
				    }
				});
			    // 初次领取驾驶证日期
				$('#examine_driverIdcardTime').datebox("setValue",selectedRows[0].driverIdCardTime);
		 }   
	});
	
	
});