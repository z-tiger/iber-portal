/**
 * 车辆运营统计，echarts折线图部分
 * */

	// 路径配置
    require.config({
        paths: {
            echarts: 'echarts/core/www/dist'
        }
    });
    
	 //选中“今天”或“本月”
	function setPeriodType(periodType){
		$("#periodType").val(periodType);
	}
	//统计维度：次数、人数、时长、里程、收入
	function setCountType(countType){
		$("#countType").val(countType);
	}
	
	
	//根据countType从dataList获取不同的数据
//	function selFromCountType(index, dataList, countType) {
//		var data = dataList[index].count;
//		if (0 == countType) {// 次数
//			data = dataList[index].count;
//		} else if (1 == countType) {// 人数
//			data = dataList[index].memberCount;
//		} else if (2 == countType) {// 时长
//			data = dataList[index].rentTime;
//		} else if (3 == countType) {// 里程
//			data = dataList[index].mileage;
//		} else if (4 == countType) {// 收入
//			data = dataList[index].income;
//		}
//		data = (data != null) ? data : 0;
//		return data;
//	}
	//根据countType获取维度名称
	function getCountTypeStr(countType) {
		var type = "次数";
		if (0 == countType) {// 次数
			type = "次数";
		} else if (1 == countType) {// 人数
			type = "人数";
		} else if (2 == countType) {// 时长
			type = "时长";
		} else if (3 == countType) {// 里程
			type = "里程";
		} else if (4 == countType) {// 收入
			type = "收入";
		} else if (5 == countType) {// 预约次数
			type = "预约次数";
		}
		return type;
	}
	
	function lineCallBack(periodType, data){
		 ////debugger;
		 var xAxisData = null;
		 //租赁次数
		 var seriesCntDataCurr = new Array();
		 var seriesCntDataLast = new Array();
		 //今天和昨天
		 if(0 == periodType){
			 var len = 24;
			 xAxisData = new Array("0时", "1时", "2时", "3时", "4时", "5时", "6时", "7时", "8时", "9时", "10时",
					  "11时", "12时", "13时", "14时", "15时", "16时", "17时", "18时", "19时", "20时",
					  "21时", "22时", "23时");
			 //初始化Y轴数据全为0
			 for(var i = 0; i < len; ++i){
				//租赁次数
				 seriesCntDataCurr.push(0);
				 seriesCntDataLast.push(0);
			 }
		 }else if(1 == periodType){//本月和上月
			 var now = new Date();
			 var thisMonthDays = getLastDay(now.getFullYear(), now.getMonth() + 1).getDate();//本月天数
			 var lastMonthDays = getLastDay(now.getFullYear(), now.getMonth()).getDate();//上月天数
			 var days = Math.max(thisMonthDays, lastMonthDays);//取月份天数最大值
			 xAxisData = new Array() ;
			 //构造X轴和初始化Y轴数据全为0
			 for(var i = 0; i < days; ++i){
				 xAxisData.push(i + 1);
				 //租赁次数
				 seriesCntDataCurr.push(0);
				 seriesCntDataLast.push(0);
			 }
		 }
		 
		 if(null != data && "" != data){
			 var seriesNameCurr; 
			 var seriesNameLast;
			 var detailVo = $.parseJSON( data ); 
			 if(null != detailVo){
				 //统计维度
				 var countType = $("#countType").val();
				 //默认统计租赁次数
				 if(null == countType){
					 countType = 0;
				 }
				 var type = getCountTypeStr(countType);
				 var currList = detailVo.currList;
				 var lastList = detailVo.lastList;
				 //选择“今日”，x轴是 [0, 24]时
				 if(0 == periodType){
					 if(null != currList){
						//splice()方法：
						//1.删除-用于删除元素，两个参数，第一个参数（要删除第一项的位置），第二个参数（要删除的项数） 
						//2.插入-向数组指定位置插入任意项元素。三个参数，第一个参数（其实位置），第二个参数（0），第三个参数（插入的项） 
						//3.替换-向数组指定位置插入任意项元素，同时删除任意数量的项，三个参数。第一个参数（起始位置），第二个参数（删除的项数），第三个参数（插入任意数量的项）
						 for(var i = 0; i < currList.length; ++i){
							 seriesCntDataCurr.splice(currList[i].orderHour, 1, currList[i].cnt);
						 }									 
					 }
					 if(null != lastList){
						 for(var i = 0; i < lastList.length; ++i){
							 seriesCntDataLast.splice(lastList[i].orderHour, 1, lastList[i].cnt);
						 }	
					 }
					 
					 seriesNameCurr = "今日车辆租赁" + type;
					 seriesNameLast = "昨日车辆租赁" + type;
				 } else if(1 == periodType){//选择“本月”
					 if(null != currList){
						 for(var i = 0; i < currList.length; ++i){
							 seriesCntDataCurr.splice(currList[i].orderDay - 1, 1, currList[i].cnt);
						 }			
					 }
					 if(null != lastList){
						 for(var i = 0; i < lastList.length; ++i){
							 seriesCntDataLast.splice(lastList[i].orderDay - 1, 1, lastList[i].cnt);
						 }	
					 }
					 
					 seriesNameCurr = "本月车辆租赁" + type;
					 seriesNameLast = "上月车辆租赁" + type;
				 } 
				 
				 lineOption("cntLine", "", seriesNameCurr, seriesNameLast, 
						 seriesCntDataCurr, seriesCntDataLast, xAxisData) ;
			 }
		 }
	}

	function setLineOptions(periodType){
		$("#periodType").val(periodType);
		if(0 === periodType){//选择"今日"
			$("#characterArea-left").css({"background" : "url(echarts/asset/img/newUi/backimg.jpg)"});
			$("#characterArea-middle").css({"background" : "url(echarts/asset/img/newUi/backimg4.jpg)"});
		} else if(1 === periodType){//选择“本月”
			$("#characterArea-left").css({"background" : "url(echarts/asset/img/newUi/backimg2.jpg)"});
			$("#characterArea-middle").css({"background" : "url(echarts/asset/img/newUi/backimg3.jpg)"});
		}
		
		var check = $("input[name='radio']:checked").val();
		var brandName = null;
		var orderType = null;
		var memberType = null;
		if(null != check){
			if(1 == check){//车品牌
				brandName = $("#hidBrandName").val();
			} else if(2 == check){
				memberType = $("#hidMemberType").val();
			} else if(3 == check){
				orderType = $("#hidOrderType").val();
			}
		}
		
		var countType = $("#countType").val();
		
		var statusJson = getStatusJson(countType);
		var status = statusJson.status;
		var statusList = statusJson.statusList;
		var excludeStatus = statusJson.excludeStatus;
		
		$.post("queryRentDetail.do", 
				{
				 "countType": countType, 
				 "periodType": periodType,
				 "brandName" : brandName,
				 "orderType" : orderType,
				 "memberType" : memberType,
				 "cityCode" : $("#hidCityCode").val(),
				 "layer" : $("#hidLayer").val(),
				 "status" : status,
				 "statusList" : statusList,
				 "excludeStatus" : excludeStatus
				 }, function(data){
					 lineCallBack(periodType, data);
				 });
	}
	
	//今天/本月、昨天/上月的数据的折线图
	function lineOption(id, text, seriesNameCurr, seriesNameLast, 
			seriesDataCurr, seriesDataLast, xAxisData){
		////debugger ;
		require(
			 	[
	                'echarts',
	                'echarts/chart/line' // 使用折线图就加载line模块，按需加载
	    		],
			 	function(ec){
			 	var myChart = ec.init(document.getElementById(id));
			 	//今日(本月)和昨日(上月)的比较
			 	var option = {
			 		    title : {
			 		        text: text
			 		    },
			 		    tooltip : {
			 		        trigger: 'axis'
			 		    },
			 		    legend: {
			 		        data: [seriesNameCurr, seriesNameLast],
			 		        x:'right',
			 		        y:'bottom'
			 		    },
			 		    toolbox: {
			 		        show : false,
			 		        feature : {
			 		            mark : {show: false},
			 		            dataView : {show: false, readOnly: false},
			 		            magicType : {show: false, type: ['line', 'bar']},
			 		            restore : {show: false},
			 		            saveAsImage : {show: false}
			 		        }
			 		    },
			 		    calculable : true,
			 		    xAxis : [
			 		        {
			 		            type : 'category',
			 		            boundaryGap : false,
			 		            data : xAxisData
			 		        }
			 		    ],
			 		    yAxis : [
			 		        {
			 		            type : 'value',
			 		            axisLabel : {
			 		            	formatter: '{value}'
			 		            }
			 		        }
			 		    ],
			 		    series : [
			 		        {
			 		            name:seriesNameCurr,
			 		            type:'line',
			 		            data: seriesDataCurr,
			 		            markLine : {
			 		                data : [
			 		                    {type : 'average', name: '平均值'}
			 		                ]
			 		            }
			 		        },
			 		        {
			 		            name:seriesNameLast,
			 		            type:'line',
			 		           data: seriesDataLast,
			 		            markLine : {
			 		                data : [
			 		                    {type : 'average', name : '平均值'}
			 		                ]
			 		            }
			 		        }
			 		    ]
			 		};
			 	
			 	 window.onresize = myChart.resize;
			 	 // 为echarts对象加载数据 
	       		 myChart.setOption(option, true);
			 	}
			 );
	}

	//选择不同统计维度:车品牌、会员类型、订单类型
	function selByBrandOrMemberTypeOrOrderType(check, value){
//		var check = $("input[name='radio']:checked").val();
		var json;
		if(1 == check){//车品牌
			$("#radioBrand").prop("checked", true);
			json = {"brandName" : value};
		} else if(2 == check){
			$("#radioMember").prop("checked", true);
			json = {"memberType" : value};
		} else if(3 == check){
			$("#radioOrderType").prop("checked", true);
			json = {"orderType" : value};
		}
		return json;
	}

	function queryRentDetail(check, value){
		var brandName = null;
		var orderType = null;
		var memberType = null;
		if(null != check && null != value){
			var json = selByBrandOrMemberTypeOrOrderType(check, value);
			brandName = null != json ? json.brandName : null;
			orderType = null != json ? json.orderType : null;
			memberType = null != json ? json.memberType : null;
		}
		var periodType = $("#periodType").val();
		var countType = $("#countType").val();
		
		var statusJson = getStatusJson(countType);
		var status = statusJson.status;
		var statusList = statusJson.statusList;
		var excludeStatus = statusJson.excludeStatus;
		
		$.post("queryRentDetail.do", 
				{
				 "countType": countType, 
				 "periodType": periodType,
				 "brandName" : brandName,
				 "memberType" : memberType,
				 "orderType" : orderType,
				 "cityCode" : $("#hidCityCode").val(),
				 "layer" : $("#hidLayer").val(),
				 "status" : status,
				 "statusList" : statusList,
				 "excludeStatus" : excludeStatus}, function(data){
					 lineCallBack(periodType, data);
				 });
	}
	
	//总体统计回调：今天及其与昨天的比率、本月及其与上月的比率、累计
	function totalRentDatasCallBack(data){
		if(null != data && "" != data){
			/*console.log(data);*/
			 var statisticsVo = $.parseJSON( data ); 
			 if(null != statisticsVo){
				 //今天
				 $("#todayCnt").html(cg(statisticsVo.todayCnt.toString()));
				 var ratioDayCnt = statisticsVo.ratioDayCnt;
				 //比率<0，则今天少于昨天的，比率数字要为绿色，且左边有向下箭头
				 if(0 > ratioDayCnt){
					 $("#ratioDayCnt").css({"color" : "green"});
					 $("#ratioDayCnt").html("↓ " + Math.abs(ratioDayCnt) + " %");
				 }else{//比率>0，则今天多于昨天的，比率数字要为红色，且左边有向上箭头
					 $("#ratioDayCnt").css({"color" : "red"});
					 $("#ratioDayCnt").html("↑ " + ratioDayCnt + " %");
				 }
				 //本月
				 $("#thisMonthCnt").html( cg(statisticsVo.thisMonthCnt.toString()));
				 var ratioMonthCnt = statisticsVo.ratioMonthCnt;
				 //比率<0，则今天少于昨天的，比率数字要为绿色，且左边有向下箭头
				 if(0 > ratioMonthCnt){
					 $("#ratioMonthCnt").css({"color" : "green"});
					 $("#ratioMonthCnt").html("↓ " + Math.abs(ratioMonthCnt) + " %");
				 }else{//比率>0，则今天多于昨天的，比率数字要为红色，且左边有向上箭头
					 $("#ratioMonthCnt").css({"color" : "red"});
					 $("#ratioMonthCnt").html("↑ " + ratioMonthCnt + " %");
				 }
				 //累计
				 $("#totalCnt").html(cg(statisticsVo.totalCnt.toString()));
			 }
		}
	}
	
	/**
	 * 显示或隐藏 预约次数、租赁次数相关的radio
	 * @param countType
	 * @returns
	 */
	function showRadio(countType){
		if(0 == countType){//租赁次数
			$("#id_radioCnt").show();
			$("#id_radioOrderCnt").hide();
		} else if(5 == countType){//预约次数
			$("#id_radioCnt").hide();
			$("#id_radioOrderCnt").show();
		} else{
			$("#id_radioCnt").hide();
			$("#id_radioOrderCnt").hide();
		}
		$("#radioOrderCar").val(1);
		$("#radioReturn").val(2);
		$("#radioAll").val(1);
		$("#radioCancel").val(2);
	}
	
	//订单状态常量
	var CONSTANT_CANCEL = "cancel";//取消
	var CONSTANT_FINISH = "finish";//完成
	var CONSTANT_ORDERED = "ordered";//预约
	var CONSTANT_RETURN = "return";//还车
	
	function getStatusJson(countType){
		var status = null;
		var statusList = null;
		var excludeStatus = null;
		
		var orderCnt = null;
		var cnt = null;
		var ordAll = false; //是否为预约次数中的全部
		if(0 == countType){//租赁次数中的预约或还车
			cnt = $("input[name='radioCnt']:checked").val();
			if(cnt){
				if(1 == cnt){//预约 = 不是取消的
					excludeStatus = CONSTANT_CANCEL;
				} else if(2 == cnt){//还车 = 完成的 + 正还车的
					statusList = "'" + CONSTANT_FINISH + "','" + CONSTANT_RETURN + "'";
				}
			}
		} else if(5 == countType){//预约次数中的全部或取消
			orderCnt = $("input[name='radioOrderCnt']:checked").val();
			if(1 == orderCnt){
				ordAll = true;
			} else if(2 == orderCnt){//取消
				status = CONSTANT_CANCEL;
			}
		}
		
		if(null == status && null == statusList && null == excludeStatus && false == ordAll){
			if(5 != countType)
				status = CONSTANT_FINISH;
		}
		
		var json = {"status" : status, "statusList" : statusList, "excludeStatus" : excludeStatus};
		return json;
	}
	
	//总体统计数据：今天及其与昨天的比率、本月及其与上月的比率、累计
	function queryRentSummarizationAndDetail(check, value){
		var brandName = null;
		var orderType = null;
		var memberType = null;
		if(null != check && null != value){
			var json = selByBrandOrMemberTypeOrOrderType(check, value);
			if(null != json){
				if(null != json.brandName){
					brandName = json.brandName;
				} else if(null != json.orderType){
					orderType = json.orderType;
				} else if(null != json.memberType){
					memberType = json.memberType;
				}
			}
		}
		var periodType = $("#periodType").val();
		var countType = $("#countType").val();
		
		var statusJson = getStatusJson(countType);
		var status = statusJson.status;
		var statusList = statusJson.statusList;
		var excludeStatus = statusJson.excludeStatus;
		
		//总体统计数据：今天及其与昨天的比率、本月及其与上月的比率、累计
		$.post("queryRentSummarizationAndDetail.do", 
				{
				 "countType": countType, 
				 "periodType": periodType,
				 "brandName" : brandName,
				 "memberType" : memberType,
				 "orderType" : orderType,
				 "cityCode" : $("#hidCityCode").val(),
				 "layer" : $("#hidLayer").val(),
				 "status" : status,
				 "statusList" : statusList,
				 "excludeStatus" : excludeStatus}, function(data){
					 totalRentDatasCallBack(data);
					 lineCallBack(periodType, data);
				 });
		
	}
	
	//下拉框的响应函数
	//统计维度：次数、人数、时长、里程、收入
	$("#countTypeList").combobox({
		onSelect: function(rec){
			var countType = rec.value;
			$("#countType").val(countType);
			var check = $("input[name='radio']:checked").val();
			var brandName = null;
			var orderType = null;
			var memberType = null;
			
			var status = null;
			var excludeStatus = null;
			
			if(null != check){
				if(1 == check){//车品牌
					brandName = $("#hidBrandName").val();
				} else if(2 == check){
					memberType = $("#hidMemberType").val();
				} else if(3 == check){
					orderType = $("#hidOrderType").val();
				}
			}
			//根据不同的统计维度定图片说明区的文字
			if(null != countType){
				if(0 == countType){
					$(".cntText").html("车辆租赁次数");
					$(".unit").html("(次)");
				} else if(1 == countType){
					$(".cntText").html("车辆租赁人数");
					$(".unit").html("(人)");
				} else if(2 == countType){
					$(".cntText").html("车辆租赁时长");
					$(".unit").html("(分)");
				} else if(3 == countType){
					$(".cntText").html("车辆租赁里程");
					$(".unit").html("(km)");
				} else if(4 == countType){
					$(".cntText").html("车辆租赁收入");
					$(".unit").html("(元)");
				} else if(5 == countType){//预约次数
					$(".cntText").html("车辆预约次数");
					$(".unit").html("(次)");
					//excludeStatus = "cancel";//排除取消的订单
				}
				
				if(5 != countType){
					status = CONSTANT_FINISH;
				}
				
				showRadio(countType);
			}
			var periodType = $("#periodType").val();
			$.post("queryRentSummarizationAndDetail.do", 
					{
					 "countType": countType, 
					 "periodType" : periodType,
					 "brandName" : brandName,
					 "memberType" : memberType,
					 "orderType" : orderType,
					 "cityCode" : $("#hidCityCode").val(),
					 "layer" : $("#hidLayer").val(),
					 "status" : status,
					 "excludeStatus" : excludeStatus
					 }, function(data){
						 totalRentDatasCallBack(data);
						 lineCallBack(periodType, data);
					 });
        }
	});
		
	$("#brandNameList").combobox({
		onSelect: function(rec){    
			$("#hidBrandName").val(rec.brandName);
			$("#memberTypeList").textbox("setValue","");
			$("#orderTypeList").textbox("setValue","");
			queryRentSummarizationAndDetail(1, rec.brandName);
        }
	});
	$("#memberTypeList").combobox({
		onSelect: function(rec){    
			$("#hidMemberType").val(rec.value);
			$("#brandNameList").textbox("setValue","");
			$("#orderTypeList").textbox("setValue","");
			queryRentSummarizationAndDetail(2, rec.value);  
		}
	});
	$("#orderTypeList").combobox({
		onSelect: function(rec){    
			$("#hidOrderType").val(rec.value);
			$("#brandNameList").textbox("setValue","");
			$("#memberTypeList").textbox("setValue","");
			queryRentSummarizationAndDetail(3, rec.value);  
		}
	});
	
	function onSelectCntRadio(){
		var check = $("input[name='radio']:checked").val();
		var value = null;
		if(null != check){
			if(1 == check){//车品牌
				value = $("#hidBrandName").val();
			} else if(2 == check){
				value = $("#hidMemberType").val();
			} else if(3 == check){
				value = $("#hidOrderType").val();
			}
		}
		queryRentSummarizationAndDetail(check, value);
	}
	
	//租赁次数中的预约或还车
	$("input[name='radioCnt']").click(function(){    
		onSelectCntRadio();
	});
	//预约次数中的全部或取消
	$("input[name='radioOrderCnt']").click(function(){    
		onSelectCntRadio();
	});
	
	//点击“清空”
	function clearSelRent(){
		$("input[type='radio']").prop("checked", false);
		$(".easyui-combobox").combobox("clear");
		
		$("input[type='hidden']").val(null);
		$("input[type='radio']").val(null);
		
		showRadio(null);
		
		queryRentSummarizationAndDetail(null, null);
		$(".cntText").html("车辆租赁次数");
		$(".unit").html("(次)");
	}
	
	function initLine(){
		setPeriodType(0);//默认统计今天和昨天的
    	setCountType(0);//默认统计租赁次数
    	
    	showRadio(null);
    	queryRentSummarizationAndDetail(null, null);
    	
//    	selectCombobox();
	}
	
	/**map加载*/
	function loadTopMap(){
	   	 var mapType;
	       // 顶部地图使用  
	       require(
	           [
	               'echarts',
	               'echarts/chart/map' // 使用地图就加载map模块，按需加载
	           ],
	           function (ec) {
	           	
	           var myChart = ec.init(document.getElementById('main')); 
	           var ecConfig = require('echarts/config');
				var curIndx = 0;
			    mapType = [
				   'china',
				    // 23个省
				    '广东省', '青海', '四川', '海南', '陕西', 
				    '甘肃', '云南', '湖南', '湖北', '黑龙江',
				    '贵州', '山东', '江西', '河南', '河北',
				    '山西', '安徽', '福建', '浙江', '江苏', 
				    '吉林', '辽宁', '台湾',
				    // 5个自治区
				    '新疆', '广西', '宁夏', '内蒙古', '西藏', 
				    // 4个直辖市
				    '北京', '天津', '上海', '重庆',
				    // 2个特别行政区
				    '香港', '澳门',
				];
			    var len = mapType.length;
				var mt = mapType[curIndx % len];
				myChart.on(ecConfig.EVENT.MAP_SELECTED, function (param){
				  if(permission=="all"){//地图的点击钻取 只在cityCode为00时	
					level = level + 1 ;
				   /* var mt = mapType[curIndx % len];*/
				    var selected;
				    if (level == 2) {
				        // 全国选择时指定到选中的省份
				    	levelIdentifier='province';
				        var selected = param.selected;
				        var provinceName ;
				        
				        for (var i in selected) {
				            if (selected[i]) {  
				                mt = i;
				                provinceName=i;
				                while (len--) {
				                    if (mapType[len] == mt) {
				                        curIndx = len;
				                    }
				                }
				               
				                break;
				            }
				        }
				        /**根据省份的名称查询省份的cityCode*/
				           $.ajax({  
					           type : "post",  
					            url : "queryCodeByName",
					            data : {name:provinceName,level:level},
					            async : false,   //采用同步
					            success : function(data){  
					           	 var cityVo = $.parseJSON( data ); 
					            	// for(var i = 0 ; i < 1 ; i ++ ) {
					            		 if(cityVo.length>0)
					            			 cityCode=cityVo[0].code;
					            	//}        	
					            }  
					      });
				           
				        var seriesData  = new Array() ;
			        	var array10 =new Array();
				       // option.tooltip.formatter = '车辆统计数据<br/>{b}:8888';
			           loadProvinceCarSumLocal(provinceName,array10);
			           value1=provinceName+"车辆统计";
			 	       value2="车辆总数:"+cg(provinceCarNums.toString())+"辆";
			 	       value3=provinceName+"车辆总数("+cg(provinceCarNums.toString())+")辆";
			 	       value4=provinceName+"车辆租赁次数";
			 	      $("#sp22").html(provinceName+"车辆运营分析");
			           loadTextData(value1,value2,value3,value4);
			           /**(车辆总数)车辆状态区分的饼状图随着省份联动*/
			           loadMiddleLeftProvince();
			           /**(车辆总数)车辆类型区分的饼状图随着省份联动*/
			           loadProvinceMiddleLeftByCarType();
			           /**(运营车辆数据)车辆类型*/
			           loadProvinceMiddleRight();
			           /**(运营车辆数据)订单类型*/
			           loadProvinceMiddleRightByOrderType();
			           /**(运营车辆数据)会员类型*/
			           loadProvinceMiddleRightByMemberType();
			           /**获取数据库中全国省下级城市信息*/
					     $.ajax({  
					           type : "post",  
					            url : "city_area_map_statistics",
					            data:{name : "全省"},
					            async : false,   //采用同步
					            success : function(data){  
					           	 var cityVo = $.parseJSON( data ); 
					            	 for(var i = 0 ; i < cityVo.length ; i ++ ) {
					            		var obj = {name:cityVo[i].name,value:cityVo[i].cityCarSum,cityCode:cityVo[i].code} ;
					            		seriesData.push(obj) ;
					            	}        	
					            }  
					     });
			           mapOption(seriesData) ;
				       option.series[0].mapType = mt;
					   myChart.setOption(option,true);
					 //加载车辆运营分析数据
//					   loadOrderCarNumber();
//					   loadOrderNumber();
//					   loadOrderTime();
//					   loadOrderIncome();
//					   var rentRate = ((rentCarNumber/provinceCarNums)*100).toFixed(0);
//				       $("#sp24").html(rentRate);
//				       var flowRate = ((orderTotal/provinceCarNums)*100).toFixed(0);
//				       $("#sp25").html(flowRate);
//				       var orderTimeAverage =(timeTotal/provinceCarNums).toFixed(0);
//				       $("#sp26").html(cg(orderTimeAverage));
//				       var orderIncomeAverage =(moneyTotal/provinceCarNums).toFixed(2);
//				       $("#sp27").html(cg(orderIncomeAverage));   
					  /// getCarRunStatistics("is_today","#is_today")
					    
				    }else {
				    	if(level == 3){
				    		levelIdentifier='city';			   
				    	// 全省选择时指定到选中的市
				    	var cityMaps = param.selected;
				    	for (var city in cityMaps) {
				            if (cityMaps[city]) {  //获取到当前选中城市
				                    
				            		curIndx = 0;
				            		mapType = [];
				            		var mapGeoData = require('echarts/util/mapData/params');
				            		    mapType.push(city);
				            		    // 自定义扩展图表类型
				            		    mapGeoData.params[city] = {
				            		        getGeoJson: (function (c) {
				            		            var geoJsonName = cityMap[c];
				            		            return function (callback) {
				            		            	 $.getJSON('echarts/core/data/' + geoJsonName + '.json', callback);
				            		            }
				            		        })(city)
				            		    }
				            		var ecConfig = require('echarts/config');

				            		    var mt = param.target;
				            		    var len = mapType.length;
				            		    var f= false;
				            		    var cityName;
				            		    for(var i=0;i<len;i++){
				            		        if(mt == mapType[i]){
				            		        	cityName=mt;   
				            		              f =true;
				            		              mt=mapType[i];
				            		        }
				            		     }
				            		  var seriesData2  = new Array() ;
				            		  /**根据城市的名称查询城市的cityCode*/
				   			           $.ajax({  
				   				           type : "post",  
				   				            url : "queryCodeByName",  
				   				            data : {name:cityName,level :level},
				   				            async : false,   //采用同步
				   				            success : function(data){  
				   				           	 var cityVo = $.parseJSON( data ); 
				   				            	 for(var i = 0 ; i < 1; i ++ ) {
				   				            		cityCode=cityVo[i].code;
				   				            	}        	
				   				            }  
				   				      });
				   			        /**获取数据库中全国省下级城市车辆总数信息*/
									     $.ajax({  
									           type : "post",  
									            url : "city_area_map_statistics", 
									            data : {name : cityName},
									            async : false,   //采用同步
									            success : function(data){  
									           	 var cityVo = $.parseJSON( data ); 
									            	 for(var i = 0 ; i < cityVo.length ; i ++ ) {
									            		cityCarNums=cityVo[i].cityCarSum
									            	}        	
									            }  
									     });
									     /**获取数据库中全国地区车辆信息*/
						       			    $.ajax({  
						       			           type : "post",  
						       					   url : "area_map_car_statistics",  
						       					   async : false,   //采用同步
						       				       success : function(data){  
						       				      	   var cityVo = $.parseJSON( data ); 
						       				       	   for(var i = 0 ; i < cityVo.length ; i ++ ) {
						       				       		var obj = {name:cityVo[i].name,value:cityVo[i].areaCarSum} ;
						       				       	       seriesData2.push(obj) ;
						       				       	   }      
						       			           }  
						       			    });
									   //填充页面统计数据信息
				            			  value1=cityName+"车辆统计";
				   			 	       	  value2="车辆总数:"+cg(cityCarNums.toString())+"辆";
				   			 	          value3=cityName+"车辆总数("+cg(cityCarNums.toString())+")辆";
				   			 	          value4=cityName+"车辆租赁次数";
				   			 	          $("#sp22").html(cityName+"车辆运营分析");
				   			              loadTextData(value1,value2,value3,value4);
					   			           
					   			           /**车辆状态区分的饼状图随着市联动*/
					   			           		loadMiddleLeft();
					   			           /**车辆类型区分的饼状图随着市联动*/	
					   			            	loadMiddleLeftByCarType();
					   			            	
					   			           /**(运营车辆数据)车辆类型*/
					   			            	loadMiddleRight();
					   			            	
					   			           /**(运营车辆数据)车辆类型*/
					   			            loadMiddleRightByOrderType(); 
					   			            
					   			          /**(运营车辆数据)会员类型*/
					   			         loadMiddleRightByMemberType();
					       			   		/**获取数据库中全国地区车辆信息*/
						       			    $.ajax({  
						       			           type : "post",  
						       					   url : "area_map_car_statistics",  
						       					   async : false,   //采用同步
						       				       success : function(data){  
						       				      	   var cityVo = $.parseJSON( data ); 
						       				       	   for(var i = 0 ; i < cityVo.length ; i ++ ) {
						       				       		var obj = {name:cityVo[i].name,value:cityVo[i].areaCarSum} ;
						       				       	       seriesData2.push(obj) ;
						       				       	   }      
						       			           }  
						       			    }); 
						       			  mapOption(seriesData2);
					            		  option.series[0].mapType = mt;
					            		  myChart.setOption(option, true);
					            		//加载车辆运营分析数据
//										   loadOrderCarNumber();
//										   loadOrderNumber();
//										   loadOrderTime();
//										   loadOrderIncome();
//										   var rentRate = ((rentCarNumber/cityCarNums)*100).toFixed(0);
//									       $("#sp24").html(rentRate);
//									       var flowRate = ((orderTotal/cityCarNums)*100).toFixed(0);
//									       $("#sp25").html(flowRate);
//									       var orderTimeAverage =(timeTotal/cityCarNums).toFixed(0);
//									       $("#sp26").html(cg(orderTimeAverage));
//									       var orderIncomeAverage =(moneyTotal/cityCarNums).toFixed(2);
//									       $("#sp27").html(cg(orderIncomeAverage)); 
					            		  //getCarRunStatistics("is_today","#is_today")
				            }
				    	}
				    	
				  }else if(level == 4){
					  levelIdentifier='area';
					   level = 1 ;
				       curIndx = 0;
				       mt = 'china';  
				       var seriesDatas8 = new Array();
			           value1="全国车辆统计";
			 	       value2="车辆总数:"+cg(carTotal.toString())+"辆";
			 	       value3="全国车辆总数("+cg(carTotal.toString())+")辆";
			 	       value4="全国车辆租赁次数";
			 	       $("#sp22").html("全国车辆运营分析");
			 	       loadTextData(value1,value2,value3,value4);
			        	/**获取数据库中全国省级城市车辆信息*/
			 	      loadProvinceCarSumLocal(names,seriesDatas8);
			        	cityCode='00';
			        	loadMiddleLeft();
			        	loadMiddleLeftByCarType();
			        	loadMiddleRight();
			        	loadMiddleRightByOrderType();
			        	loadMiddleRightByMemberType();
					    mapOption(seriesDatas8);
				        option.series[0].mapType = mt;
				        myChart.setOption(option, true);
				      //加载车辆运营分析数据
//					   loadOrderCarNumber();
//					   loadOrderNumber();
//					   loadOrderTime();
//					   loadOrderIncome();
//					   var rentRate = ((rentCarNumber/carTotal)*100).toFixed(0);
//				       $("#sp24").html(rentRate);
//				       var flowRate = ((orderTotal/carTotal)*100).toFixed(0);
//				       $("#sp25").html(flowRate);
//				       var orderTimeAverage =(timeTotal/carTotal).toFixed(0);
//				       $("#sp26").html(cg(orderTimeAverage));
//				       var orderIncomeAverage =(moneyTotal/carTotal).toFixed(2);
//				       $("#sp27").html(cg(orderIncomeAverage));
				      
				    }     
			  } 
				    $("#hidCityCode").val(cityCode);
					 $("#hidLayer").val(level);
					 initLine();
					  getCarRunStatistics("is_today","#is_today")
			}	
				  	
				}); 
					if(permission=="all"){//如果登陆用户的cityCode为oo显示全国的地图信息
				    var seriesDatas9 = new Array();	
				    /**获取数据库中全国省级城市车辆信息*/
				    loadProvinceCarSumLocal(names,seriesDatas9);
   					//加载车辆运营分析数据
//   				   loadOrderCarNumber();
//   				   loadOrderNumber();
//   				   loadOrderTime();
//   				   loadOrderIncome();
//   				   var rentRate = ((rentCarNumber/carTotal)*100).toFixed(0);
//   			       $("#sp24").html(rentRate);
//   			       var flowRate = ((orderTotal/carTotal)*100).toFixed(0);
//   			       $("#sp25").html(flowRate);
//   			       var orderTimeAverage =(timeTotal/carTotal).toFixed(0);
//   			       $("#sp26").html(cg(orderTimeAverage));
//   			       var orderIncomeAverage =(moneyTotal/carTotal).toFixed(2);
//   			       $("#sp27").html(cg(orderIncomeAverage));
   						getCarRunStatistics("is_today","#is_today")
					}else if(permission=="part"){}   
					mapOption(seriesDatas9);
				    option.series[0].mapType = mt;
				   // 为echarts对象加载数据 \
	                myChart.setOption(option,true);
	           }
	       );
	}
	$(function(){
		   $("#middle-right-map1").show();
		   $("#middle-right-map2").hide();
		   $("#middle-right-map3").hide();
		   $("#middle-left-map2").hide();
		   $("#middle-left-map1").show();
		   /**获取数据库中全国省下级城市车辆总数信息*/
		     $.ajax({  
		           type : "post",  
		            url : "china_car_statistics",  
		            async : false,   //采用同步
		            success : function(data){  
		           	 var cityVo = $.parseJSON( data ); 
		             	carTotal =cityVo[0].carTotal; 
		             	
		            }  
		     });
		     level = 1 ;
	       cityCode='00';
	       if(sessionCityCode!='00'){
	    	   cityCode=sessionCityCode;
	       } 
	       if(levelIdentifier=='province'){
	    	   levelIdentifier='china';
	       }
	       value1="全国车辆统计";
	       value2="车辆总数:"+cg(carTotal.toString())+"辆";
	       value3="全国车辆总数("+cg(carTotal.toString())+")辆";
	       value4="全国车辆租赁次数";
	       $("#sp22").html("全国车辆运营分析");
	       loadTextData(value1,value2,value3,value4);
	       loadTopMap();
	       loadMiddleLeft();
	       loadMiddleRight();
	       initLine();
	       
	       $("#middle-carStatus").bind("click",function(){
			   $("#middle-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-carStatus").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-left-map1").show();
			   $("#middle-left-map2").hide();
			   if(levelIdentifier=='province'){
				   loadMiddleLeftProvince();
			   }else{
				   loadMiddleLeft(); 
			   }
		   });
		   $("#middle-carType").bind("click",function(){
			   $("#middle-carType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-carStatus").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-left-map1").hide();
			   $("#middle-left-map2").show();
			   if(levelIdentifier=='province'){
				   loadProvinceMiddleLeftByCarType();
			   }else{
				   loadMiddleLeftByCarType();   
			   }
		   });
		   $("#middle-right-carType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-map1").show();
			   $("#middle-right-map2").hide();
			   $("#middle-right-map3").hide();
			   if(levelIdentifier=='province'){
				   loadProvinceMiddleRight();
			   }else{
				   loadMiddleRight();  
			   }
		   });
		   $("#middle-right-orderType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-orderType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-map1").hide();
			   $("#middle-right-map3").hide();
			   $("#middle-right-map2").show();
			   if(levelIdentifier=='province'){
				   loadProvinceMiddleRightByOrderType();
			   }else{
				   loadMiddleRightByOrderType();
			   }  
		   });
		   $("#middle-right-memberType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-memberType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-map1").hide();
			   $("#middle-right-map2").hide();
			   $("#middle-right-map3").show();
			   if(levelIdentifier=='province'){
				   loadProvinceMiddleRightByMemberType();
			   }else{
				   loadMiddleRightByMemberType();
			   } 
		      });
		 
		   $.ajax({
	 		   type: "post",
	 		   url: "query_run_car",
	 		   success: function(data) {
	 			   var _json = eval("("+data+")");
	 			   $("#car_max_run_1").html("全国运营车辆同时租赁总数达到");
	 			   $("#car_max_run_2").html(_json.maxNumber);
	 			   $("#car_max_run_3").html("辆," + "(" + _json.createTime + ")");
	 			   
	 		   }
	 	   })
		    $("#car_li").click(function(){
		    	$.ajax({
		 		   type: "post",
		 		   url: "query_run_car",
		 		   success: function(data) {
		 			   var _json = eval("("+data+")");
		 			   $("#car_max_run_1").html("全国运营车辆同时租赁总数达到");
		 			   $("#car_max_run_2").html(_json.maxNumber);
		 			   $("#car_max_run_3").html("辆," + "(" + _json.createTime + ")");
		 			   
		 		   }
		 	   })
			});
	});
	/**加载省级车辆总数信息*/
	 function loadProvinceCarSumLocal(names,array){
		 $.ajax({  
		           type : "post",  
				   url : "province_area_map_statistics", 
				   data: {names : names},
				   async : false,   //采用同步
			       success : function(data){  
			      	   var cityVo = $.parseJSON( data ); 
			       	   for(var i = 0 ; i < cityVo.length ; i ++ ) {
			       		var obj = {name:cityVo[i].name,value:cityVo[i].provinceCarSum,cityCode:cityVo[i].code} ;
			       		provinceCarNums =cityVo[i].provinceCarSum;
			       		array.push(obj) ;
			       	   }      
		           }  
		    }); 
	 }
	//中部 左侧饼状图(车辆状态 ：权限为省份)
     function loadMiddleLeftProvince(){        
         require(
     		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
         		],
     		 	function(ec){
     		 		$.post("carStatus_province_list_statistics_sum",{cityCode:cityCode}, function(data){
     		 			var carRunNumVo = $.parseJSON( data ); 
     		 			myChart= ec.init(document.getElementById('middle-left-map1'));
     		 			pieLegendData = ['空闲','运营','维修','维护'] ;
 		    			pieSeriesData = [{value:carRunNumVo[0].emptyTotal,name:'空闲'},
 		    			                 {value:carRunNumVo[0].runTotal,name:'运营'},
 		    			                 {value:carRunNumVo[0].repairTotal,name:'维修'},
 		    			                 {value:carRunNumVo[0].maintainTotal,name:'维护'}
 		    			                 ];	
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("车辆状态",pieLegendData,pieSeriesData),true);
     		 		});
     		 	}
     		 );
         }
   //中部 左侧饼状图(车辆类型 --按照省份划分)
     function loadProvinceMiddleLeftByCarType(){  
		   require(
	    		 	[
		                'echarts',
		                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
	        		],
	    		 	function(ec){
	    		 		$.post("provinceCarType_list_statistics_sum",{cityCode:cityCode},function(data){
	    		 			
	    		 			var carRunNumVo = $.parseJSON( data ); 
	    		 			var myChart= ec.init(document.getElementById('middle-left-map2'));
	    		 			var pieLegendDatas = new Array();
	    		 			var pieSeriesDatas = new Array();
	    		 			for(var i=0;i<carRunNumVo.length;i++){
	    		 				var a=carRunNumVo[i].brance+carRunNumVo[i].type;
	    		 				pieLegendDatas.push(a);
	    		 				var b={ value:carRunNumVo[i].total,name:a};
	    		 				pieSeriesDatas.push(b);
	    		 			}
	    		 				pieLegendData = pieLegendDatas ;
	    		    			pieSeriesData = pieSeriesDatas;	
			    		 	 // 为echarts对象加载数据 
			           		myChart.setOption(pieOption("车辆类型",pieLegendData,pieSeriesData),true);
	    		 		});
	    		 	}
	    		 );
		   }
   //中部 右侧饼状图(车辆类型--按照省份划分)
     function loadProvinceMiddleRight(){
         require(
     		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
         		],
     		 	function(ec){
     		 		$.post("provinceCarType_list_statistics_run?cityCode="+cityCode, function(data){
     		 			var carRunNumVo = $.parseJSON( data ); 
     		 			myChart= ec.init(document.getElementById('middle-right-map1'));
     		 			var pieLegendDatas = new Array();
     		 			var pieSeriesDatas = new Array();
     		 			for(var i=0;i<carRunNumVo.length;i++){
     		 				var a=carRunNumVo[i].brance+carRunNumVo[i].type;
     		 				pieLegendDatas.push(a);
     		 				var b={ value:carRunNumVo[i].total,name:a};
     		 				pieSeriesDatas.push(b);
     		 			}
     		 				pieLegendData = pieLegendDatas ;
     		    			pieSeriesData = pieSeriesDatas;
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("车辆类型",pieLegendData,pieSeriesData),true);
     		 		});
     		 	}
     		 );
       }
   //中部 右侧饼状图(订单类型--按照省份划分)
	   function loadProvinceMiddleRightByOrderType(){
	   require(
 		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
     		],
 		 	function(ec){
 		 		$.post("ProvinceOrderType_list_statistics_run?cityCode="+cityCode, function(data){
 		 			var carRunNumVo = $.parseJSON( data ); 
 		 			var myChart= ec.init(document.getElementById('middle-right-map2'));
 		 			pieLegendData = ['日租','时租'] ;
		    			pieSeriesData = [{value:carRunNumVo[0].dayRentTotal,name:'日租'},
		    			                 {value:carRunNumVo[0].timeShareTotal,name:'时租'}
		    			                 ];	
		    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("订单类型",pieLegendData,pieSeriesData),true);
 		 		});
 		 	}
 		 );
	   }
	   //中部 右侧饼状图(会员类型--按照省份划分)
	   function loadProvinceMiddleRightByMemberType(){
	   require(
    		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
        		],
    		 	function(ec){
    		 		
    		 		$.post("ProvinceMemberType_list_statistics_run?cityCode="+cityCode, function(data){
    		 			var carRunNumVo = $.parseJSON( data ); 
    		 			var myChart= ec.init(document.getElementById('middle-right-map3'));
    		 				pieLegendData = ['个人','政企'] ;
    		    			pieSeriesData = [{value:carRunNumVo[0].personTotal,name:'个人'},
    		    			                 {value:carRunNumVo[0].enterpriseTotal,name:'政企'}
    		    			                 ];	
		    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("会员类型",pieLegendData,pieSeriesData),true);
    		 		});    
    		 	}
    		 );
	   }
	 //中部 右侧饼状图(车辆类型--按照全国、地级市划分)
	      function loadMiddleRight(){
	        require(
	    		 	[
		                'echarts',
		                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
	        		],
	    		 	function(ec){
	    		 		$.post("carType_list_statistics_run",{cityCode:cityCode}, function(data){
	    		 			var carRunNumVo = $.parseJSON( data ); 
	    		 			myChart= ec.init(document.getElementById('middle-right-map1'));
	    		 			var pieLegendDatas = new Array();
	    		 			var pieSeriesDatas = new Array();
	    		 			for(var i=0;i<carRunNumVo.length;i++){
	    		 				var a=carRunNumVo[i].brance+carRunNumVo[i].type;
	    		 				pieLegendDatas.push(a);
	    		 				var b={ value:carRunNumVo[i].total,name:a};
	    		 				pieSeriesDatas.push(b);
	    		 			}
	    		 				pieLegendData = pieLegendDatas ;
	    		    			pieSeriesData = pieSeriesDatas;
			    		 	 // 为echarts对象加载数据 
			           		myChart.setOption(pieOption("车辆类型",pieLegendData,pieSeriesData),true);
	    		 		});
	    		 	}
	    		 );
	      }
	      
	    //中部 左侧饼状图(车辆状态 ：权限为全国 和地级市公用)
	        function loadMiddleLeft(){        
	        require(
	    		 	[
		                'echarts',
		                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
	        		],
	    		 	function(ec){
	    		 		$.post("carStatus_list_statistics_sum?cityCode="+cityCode, function(data){
	    		 			var carRunNumVo = $.parseJSON( data ); 
	    		 			myChart= ec.init(document.getElementById('middle-left-map1'));
	    		 				pieLegendData = ['空闲','运营','维修','维护'] ;
	    		    			pieSeriesData = [{value:carRunNumVo[0].emptyTotal,name:'空闲'},
	    		    			                 {value:carRunNumVo[0].runTotal,name:'运营'},
	    		    			                 {value:carRunNumVo[0].repairTotal,name:'维修'},
	    		    			                 {value:carRunNumVo[0].maintainTotal,name:'维护'}
	    		    			                 ];	
			    		 	 // 为echarts对象加载数据 
			           		myChart.setOption(pieOption("车辆状态",pieLegendData,pieSeriesData),true);
	    		 		});
	    		 	}
	    		 );
	        }
	        //中部 左侧饼状图(车辆类型--按照全国、地级市划分)
	        function loadMiddleLeftByCarType(){  
	 		   require(
	 	    		 	[
	 		                'echarts',
	 		                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
	 	        		],
	 	    		 	function(ec){
	 	    		 		$.post("carType_list_statistics_sum",{cityCode:cityCode}, function(data){
	 	    		 			
	 	    		 			var carRunNumVo = $.parseJSON( data ); 
	 	    		 			var myChart= ec.init(document.getElementById('middle-left-map2'));
	 	    		 			var pieLegendDatas = new Array();
	 	    		 			var pieSeriesDatas = new Array();
	 	    		 			for(var i=0;i<carRunNumVo.length;i++){
	 	    		 				var a=carRunNumVo[i].brance+carRunNumVo[i].type;
	 	    		 				pieLegendDatas.push(a);
	 	    		 				var b={ value:carRunNumVo[i].total,name:a};
	 	    		 				pieSeriesDatas.push(b);
	 	    		 			}
	 	    		 				pieLegendData = pieLegendDatas ;
	 	    		    			pieSeriesData = pieSeriesDatas;	
	 			    		 	 // 为echarts对象加载数据 
	 			           		myChart.setOption(pieOption("车辆类型",pieLegendData,pieSeriesData),true);
	 	    		 		});
	 	    		 	}
	 	    		 );
	 		   }
	        //中部 右侧饼状图(订单类型--按照全国、地级市划分)
	 	   function loadMiddleRightByOrderType(){
	 	   require(
	    		 	[
	 	                'echarts',
	 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
	        		],
	    		 	function(ec){
	    		 		$.post("orderType_list_statistics_run?cityCode="+cityCode, function(data){
	    		 			var carRunNumVo = $.parseJSON( data ); 
	    		 			var myChart= ec.init(document.getElementById('middle-right-map2'));
	    		 			pieLegendData = ['日租','时租'] ;
	 		    			pieSeriesData = [{value:carRunNumVo[0].dayRentTotal,name:'日租'},
	 		    			                 {value:carRunNumVo[0].timeShareTotal,name:'时租'}
	 		    			                 ];	
	 		    		 	 // 为echarts对象加载数据 
	 		           		myChart.setOption(pieOption("订单类型",pieLegendData,pieSeriesData),true);
	    		 		});
	    		 	}
	    		 );
	 	   }
	 	//中部 右侧饼状图(会员类型--按照全国、低级市划分)
		   function loadMiddleRightByMemberType(){
		   require(
	    		 	[
		                'echarts',
		                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
	        		],
	    		 	function(ec){
	    		 		
	    		 		$.post("memberType_list_statistics_run?cityCode="+cityCode, function(data){
	    		 			var carRunNumVo = $.parseJSON( data ); 
	    		 			var myChart= ec.init(document.getElementById('middle-right-map3'));
	    		 				pieLegendData = ['个人','政企'] ;
	    		    			pieSeriesData = [{value:carRunNumVo[0].personTotal,name:'个人'},
	    		    			                 {value:carRunNumVo[0].enterpriseTotal,name:'政企'}
	    		    			                 ];	
			    		 	 // 为echarts对象加载数据 
			           		myChart.setOption(pieOption("会员类型",pieLegendData,pieSeriesData),true);
	    		 		});
	    		 	}
	    		 );
		   }
		   /**根据cityCode获取订单车辆数*/
		    function loadOrderCarNumber(){
		    	$.ajax({  
			           type : "post",  
			            url : "getOrderCarNumber?cityCode="+cityCode+"&level="+level,  
			            async : false,   //采用同步
			            success : function(data){  
			           	 var memberTotalVo = $.parseJSON( data ); 
			           	rentCarNumber =memberTotalVo[0].rentCarNumber;    	
			            }  
			    });
		    }
		 /**根据cityCode获取订单总数*/
		    function loadOrderNumber(){
		    	$.ajax({  
			           type : "post",  
			            url : "getOrderNumber?cityCode="+cityCode+"&level="+level,  
			            async : false,   //采用同步
			            success : function(data){  
			           	 var memberTotalVo = $.parseJSON( data ); 
			           	orderTotal =memberTotalVo[0].orderTotal;    	
			            }  
			    });
		    }
		 /**根据cityCode获取订单时长*/
		    function loadOrderTime(){
		    	$.ajax({  
			           type : "post",  
			            url : "getOrderTime?cityCode="+cityCode+"&level="+level,  
			            async : false,   //采用同步
			            success : function(data){  
			           	 var memberTotalVo = $.parseJSON( data ); 
			           	 timeTotal =memberTotalVo[0].timeTotal;    	
			            }  
			    });
		    }
		 /**根据cityCode获取订单收入*/ 
		    function loadOrderIncome(){
		    	$.ajax({  
			           type : "post",  
			            url : "getOrderIncome?cityCode="+cityCode+"&level="+level,  
			            async : false,   //采用同步
			            success : function(data){  
			           	 var memberTotalVo = $.parseJSON( data ); 
			             moneyTotal =memberTotalVo[0].moneyTotal;    	
			            }  
			    });
		    }
		   
		    
		    function getCarRunStatistics(period,obj){
		    	$("#last_month").removeClass("bkblue");
		    	$("#last_week").removeClass("bkblue");
		    	$("#is_today").removeClass("bkblue");
		    	$("#sp24").html("");
		    	$("#sp25").html("");
		    	$("#sp26").html("");
		    	$("#sp27").html("");
		    	//var cityCody = $("#hidCityCode").val(); 
		    	var level = $("#hidLayer").val(); 
		    	var domObj = $(obj);
		    	domObj.removeClass("bkwhite").addClass("bkblue");
		    	$.ajax({  
			           type : "post",  
			            url : "getCarRunStatistics", 
			            data :{cityCode:$("#hidCityCode").val(),level:level,period:period},
			            async : false,   //采用同步
			            success : function(data){  
			            	var json = eval(data);
			            	if(json){
			            		if(json.length > 0){
			            			var periodNum =1;
			            			if("last_month" == period){
			            				periodNum = getLastDays();
			            			} else if("last_week" == period){
			            				periodNum = 7;
			            			}
			            			var rcn = json[0].rentCarNumber?json[0].rentCarNumber:0;
			            			var rentRate = ((rcn/(json[0].totalCarSum*periodNum))*100).toFixed(2);
			     			       $("#sp24").html(rentRate);
			     			       var orderT = json[0].orderTotal? json[0].orderTotal:0;
			     			       var flowRate = ((orderT/(json[0].totalCarSum*periodNum))*100).toFixed(2);
			     			       $("#sp25").html(flowRate);
			     			       var timeT = json[0].timeTotal?json[0].timeTotal:0;
			     			       var orderTimeAverage =(timeT/(json[0].totalCarSum*periodNum)).toFixed(0);
			     			       $("#sp26").html(cg(orderTimeAverage));
			     			       var mont = json[0].moneyTotal ? json[0].moneyTotal: 0;
			     			       var orderIncomeAverage =(mont/(json[0].totalCarSum*periodNum)).toFixed(2);
			     			       $("#sp27").html(cg(orderIncomeAverage));
			            		}
			            	}
			            }  
			    });
		    }
		    
		    function getLastDays(){
		        var now = new Date;
		        now.setMonth(now.getMonth() - 1);
		        now.setDate(1);
		        var next = new Date;
		        next.setDate(1);
		        var arr = [];
		        while(now < next){
		            arr.push(now.getDate());
		            now.setDate(now.getDate() + 1);
		        }
		        return arr[arr.length-1];
		    }