/**
 * xyq 会员运营统计，echarts折线图部分
 * 
 */
	require.config({
        paths: {
            echarts: 'echarts/core/www/dist'
        }
    });

	// 选中“今天”或“本月”
	function setPeriodType2(periodType) {
		$("#period_Type2").val(periodType);
	}
	//统计维度：次数、人数、时长、里程、收入
	function setCountType2(countType){
		$("#MemberCountType").val(countType);
	}
	//下拉框的响应函数
	function selectMemberCombobox(){
		//统计维度：注册数、正式会员数、充值押金额、充值余额、充值人数
		$("#MemberCountTypeList").combobox({
			onSelect: function(rec){
				var countType = rec.value;
				$("#MemberCountType").val(countType);
				var check = $("input[name='radio2']:checked").val();
				var　memberLevel = null;
				var memberType = null;    
				if (null != check) {
					if (1 == check) {// 会员类型
						memberType = $("#hidMemberType").val();
					} else if (2 == check) {// 会员等级
						memberLevel = $("#hidMemberLevel").val();
					} 
				}
				//根据不同的统计维度定图片说明区的文字
				if(null != countType){
					if(1 == countType){
						$(".cntText_member").html("注册会员数");
						$(".unit").html("(个)");
					} else if(2 == countType){
						$(".cntText_member").html("正式会员数");
						$(".unit").html("(个)");
					} else if(3 == countType){
						$(".cntText_member").html("充值押金值");
						$(".unit").html("(元)");
					} else if(4 == countType){
						$(".cntText_member").html("充值余额值");
						$(".unit").html("(元)");
					} else if(5 == countType){
						$(".cntText_member").html("充值人数");
						$(".unit").html("(个)");
					} else if(6 == countType){
						$(".cntText_member").html("消费金额");
						$(".unit").html("(元)");
					} else if(7 == countType){
						$(".cntText_member").html("未付款金额");
						$(".unit").html("(元)");
					} else if(8 == countType){
						$(".cntText_member").html("退款金额");
						$(".unit").html("(元)");
					}
				}
				$.post("queryMemberCountDatas.do", 
						{
						 "countType": countType, 
						 "memberLevel" : memberLevel,
						 "memberType" : memberType,
						 "cityCode" : $("#hidMemberCityCode").val(),
						 "layer" : $("#hidMemberLayer").val()}, 
						 function(data){
							 totalMemberRentDatasCallBack(data);
						 });
				var periodType = $("#period_Type2").val();
				$.post( "queryMemberCountDetail.do", 
						{"countType": countType, 
						 "periodType": periodType,
						 "memberLevel" : memberLevel,
						 "memberType" : memberType,
						 "cityCode" : $("#hidMemberCityCode").val(),
						 "layer" : $("#hidMemberLayer").val()}, 
						  function(data){
							 memberLineCallBack(periodType, data);
						 });
			}
		});
		$("#memberTypeList2").combobox({
			onSelect: function(rec){   
				$("#radioMemberType2").prop("checked", true);
				$("#hidMemberType").val(rec.value);
				$("#memberLevelList2").textbox("setValue","");
				queryRentDatas2(1, rec.value);
				queryRentDetail2(1,rec.value);
				
	        }
		});
		$("#memberLevelList2").combobox({
			onSelect: function(rec){ 
				$("#radioMemberLevel2").prop("checked", true);
				$("#hidMemberLevel").val(rec.value);
				$("#memberTypeList2").textbox("setValue","");
				queryRentDatas2(2, rec.value);  
				queryRentDetail2(2,rec.value);
				 
			}
		});
	}	
	function memberLineCallBack(periodType, data){
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
			 var days = getLastDay(now.getFullYear(), now.getMonth() + 1).getDate();
			 xAxisData = new Array()  ;
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
				 var countType = $("#MemberCountType").val();
				 //默认统计会员注册数
				 if(null == countType){
					 countType = 1;
				 }
				 var type = getMemberCountTypeStr(countType);
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
							 var a = currList[i].cTime;
							 seriesCntDataCurr.splice(currList[i].currTime, 1, currList[i].todayCnt);
						 }									 
					 }
					 if(null != lastList){
						 for(var i = 0; i < lastList.length; ++i){
							 var b = lastList[i].cTime;
							 seriesCntDataLast.splice(lastList[i].currTime, 1, lastList[i].yesterdayCnt);
						 }	
					 }
					 
					 seriesNameCurr = "今日" + type;
					 seriesNameLast = "昨日" + type;
				 } else if(1 == periodType){//选择“本月”
					 if(null != currList){
						 for(var i = 0; i < currList.length; ++i){
							 var c = currList[i].cDate;
							 seriesCntDataCurr.splice( currList[i].currDate-1,1, currList[i].thisMonthCnt);
						 }			
					 }
					 if(null != lastList){
						 for(var i = 0; i < lastList.length; ++i){
							 var d = lastList[i].cDate;
							 seriesCntDataLast.splice(lastList[i].currDate-1,1, lastList[i].lastMonthCnt);
						 }	
					 }
					 
					 seriesNameCurr = "本月" + type;
					 seriesNameLast = "上月" + type;
				 } 
				 
				 memberLineOption("line-member", "", seriesNameCurr, seriesNameLast, 
						 seriesCntDataCurr, seriesCntDataLast, xAxisData) ;
			 }
		 }
	}
	//根据countType获取维度名称
	function getMemberCountTypeStr(countType) {
		var type = "注册数";
		if (1 == countType) {// 次数
			type = "会员注册数";
		} else if (2 == countType) {// 人数
			type = "正式会员数";
		} else if (3 == countType) {// 时长
			type = "会员充值押金";
		} else if (4 == countType) {// 里程
			type = "会员充值余额";
		} else if (5 == countType) {// 收入
			type = "充值会员数";
		} else if (6 == countType) {// 收入
			type = "消费金额";
		} else if (7 == countType) {// 收入
			type = "未支付金额";
		} else if (8 == countType) {// 收入
			type = "退款金额";
		}
		return type;
	}
	//今天/本月、昨天/上月的数据的折线图
	function memberLineOption(id, text, seriesNameCurr, seriesNameLast, 
			seriesDataCurr, seriesDataLast, xAxisData){
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
	       		 myChart.setOption(option,true);
			 	}
			 );
	}
	function queryRentDatas2(check, value){
		var memberType = null;
		var memberLevel = null;
		if(check==1){
			memberType=value;
		}else{
			memberLevel=value;
		}
		
		//总体统计数据：今天及其与昨天的比率、本月及其与上月的比率、累计
		$.post("queryMemberCountDatas.do", 
				{
				 "countType": $("#MemberCountType").val(), 
				 "memberType" : memberType,
				 "memberLevel" : memberLevel,
				 "cityCode" : $("#hidMemberCityCode").val(),
				 "layer" : $("#hidMemberLayer").val()}, 
				 function(data){
					 totalMemberRentDatasCallBack(data);
				 });
		
	}
	function queryRentDetail2(check, value){
		var memberType = null;
		var memberLevel = null;
		if(check==1){
			memberType=value;
		}else{
			memberLevel=value;
		}
		var periodType = $("#period_Type2").val();
		$.post("queryMemberCountDetail.do", 
				{
			 	 "countType": $("#MemberCountType").val(), 
			 	 "memberType" : memberType,
				 "memberLevel" : memberLevel,
				 "periodType": periodType,
				 "cityCode" : $("#hidCityCode").val(),
				 "layer" : $("#hidLayer").val()}, function(data){
					 memberLineCallBack(periodType, data);
				 });
	}
	
	//总体统计回调：今天及其与昨天的比率、本月及其与上月的比率、累计
	function totalMemberRentDatasCallBack(data){
		if(null != data && "" != data){
			 var statisticsVo = $.parseJSON( data );  
			 if(null != statisticsVo && null != statisticsVo[0]){
				 //今天
				 $("#todayCnt_member").html(cg(statisticsVo[0].todayCnt.toString()));
				 var a1 =parseFloat(statisticsVo[0].todayCnt);
				 var a2 =parseFloat(statisticsVo[0].yesterdayCnt);
				 if(a1==a2){
					 $("#ratioDayCnt_member").css({"color" : "red"});
					 $("#ratioDayCnt_member").html( 0 + " %");
				 }else{
						var ratioDayCnt =0;
						if(a2 != 0)
					 	   ratioDayCnt = (((a1-a2)/a2)*100).toFixed(0);
						else
							if(a1 > 0){
								ratioDayCnt = 100;
							}
						//比率<0，则今天少于昨天的，比率数字要为绿色，且左边有向下箭头
						 if(0 > ratioDayCnt){
							 $("#ratioDayCnt_member").css({"color" : "green"});
							 $("#ratioDayCnt_member").html("↓ " + Math.abs(ratioDayCnt) + " %");
						 }else{//比率>0，则今天多于昨天的，比率数字要为红色，且左边有向上箭头
							 $("#ratioDayCnt_member").css({"color" : "red"});
							 $("#ratioDayCnt_member").html("↑ " + ratioDayCnt + " %");
						 }
						 
					 
				 }
				 //本月
				 $("#thisMonthCnt_member").html(cg(statisticsVo[0].thisMonthCnt.toString()));
				 var ratioMonthCntMember;
				 var a3 =parseFloat(statisticsVo[0].thisMonthCnt);
				 var a4 =parseFloat(statisticsVo[0].lastMonthCnt);
				 if(a3==a4){
					 $("#ratioMonthCnt_member").css({"color" : "red"});
					 $("#ratioMonthCnt_member").html(  0 + " %");
				 }else{
					 var ratioMonthCnt = 0;
					 if(a4 != 0)
						 ratioMonthCnt = (((a3-a4)/a4)*100).toFixed(0);
					 else
						 if(a3 > 0){
							 ratioMonthCnt = 100;
						 }
					 //比率<0，则今天少于昨天的，比率数字要为绿色，且左边有向下箭头
					 if(0 > ratioMonthCnt){
						 $("#ratioMonthCnt_member").css({"color" : "green"});
						 $("#ratioMonthCnt_member").html("↓ " + Math.abs(ratioMonthCnt) + " %");
					 }else{//比率>0，则今天多于昨天的，比率数字要为红色，且左边有向上箭头
						 $("#ratioMonthCnt_member").css({"color" : "red"});
						 $("#ratioMonthCnt_member").html("↑ " + ratioMonthCnt + " %");
					 }
				 }
				 //累计
				 $("#totalCnt_member").html(cg(statisticsVo[0].totalCnt.toString()));
			 }
		}
	}
	function setMemberLineOptions(periodType){
		$("#period_Type2").val(periodType);
		var countType = $("#MemberCountType").val();
		if(0 === periodType){//选择"今日"
			$("#member_characterArea_left").css({"background" : "url(echarts/asset/img/newUi/backimg.jpg)"});
			$("#member_characterArea_middle").css({"background" : "url(echarts/asset/img/newUi/backimg4.jpg)"});
		} else if(1 === periodType){//选择“本月”
			$("#member_characterArea_left").css({"background" : "url(echarts/asset/img/newUi/backimg2.jpg)"});
			$("#member_characterArea_middle").css({"background" : "url(echarts/asset/img/newUi/backimg3.jpg)"});
		}
		if(null != countType){
			if(1 == countType){
				$(".cntText_member").html("注册会员数");
				$(".unit").html("(个)");
			} else if(2 == countType){
				$(".cntText_member").html("正式会员数");
				$(".unit").html("(个)");
			} else if(3 == countType){
				$(".cntText_member").html("充值押金值");
				$(".unit").html("(元)");
			} else if(4 == countType){
				$(".cntText_member").html("充值余额值");
				$(".unit").html("(元)");
			} else if(5 == countType){
				$(".cntText_member").html("充值人数");
				$(".unit").html("(个)");
			} else if(6 == countType){
				$(".cntText_member").html("消费金额");
				$(".unit").html("(元)");
			} else if(7 == countType){
				$(".cntText_member").html("未支付金额");
				$(".unit").html("(元)");
			} else if(8 == countType){
				$(".cntText_member").html("退款金额");
				$(".unit").html("(元)");
			}
		}
		var check = $("input[name='radio2']:checked").val();
		var memberLevel = null;
		var memberType = null;
		if(null != check){
			if(1 == check){//会员类型
				memberType = $("#hidMemberType").val();
			} else if(2 == check){
				memberLevel = $("#hidMemberLevel").val();
			} 
		}
		$.post("queryMemberCountDatas.do", 
				{
			     "countType": countType,
				 "memberLevel" : memberLevel,
				 "memberType" : memberType,
				 "cityCode" : $("#hidMemberCityCode").val(),
				 "layer" : $("#hidMemberLayer").val()}, 
				 function(data){
					 totalMemberRentDatasCallBack(data);
				 });
		$.post("queryMemberCountDetail.do", 
				{
			     "countType": countType, 
				 "periodType": periodType,
				 "memberLevel":memberLevel,
				 "memberType" : memberType,
				 "periodType": periodType,
				 "cityCode" : $("#hidMemberCityCode").val(),
				 "layer" : $("#hidMemberLayer").val()
				 }, function(data){
					 memberLineCallBack(periodType, data);
				 });
	}

	//点击“清空”
	function clearSelMember(){
		$("input[name='radio2']").prop("checked", false);
		$(".easyui-combobox").combobox("clear");
		$("input[type='hidden']").val(null);
		$("input[name='radio2']").val(null);
		$("#MemberCountType").val(1);
		$("#hidMemberCityCode").val(cityCode);
		$("#hidMemberLayer").val(level);
		setMemberLineOptions(0);
	}
	 /**获取数据库中全国省下级城市会员总数信息*/
    function loadMemberTotal(){
    	$.ajax({  
	           type : "post",  
	            url : "china_member_statistics?cityCode="+cityCode,  
	            async : false,   //采用同步
	            success : function(data){  
	           	 var memberTotalVo = $.parseJSON( data ); 
	           	   memberTotals =memberTotalVo[0].memberTotal;    	
	            }  
	    });
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
			       // option.tooltip.formatter = '车辆统计数据<br/>{b}:8888';
			        /**根据省份的名称查询省份的cityCode*/
			           $.ajax({  
				           type : "post",  
				            url : "queryCodeByName", 
				            data: {name:provinceName , level:level },
				            async : false,   //采用同步
				            success : function(data){  
				           	 var cityVo = $.parseJSON( data ); 
				            	 for(var i = 0 ; i < 1 ; i ++ ) {
				            		cityCode=cityVo[i].code;
				            	}        	
				            }  
				      });
			           
			        var seriesData  = new Array() ;
			        	/**加载选中省份的城市会员数量信息*/
			        	$.ajax({  
					           type : "post",  
					            url : "city_memberTotal_statistics", 
					            data : {cityCode : cityCode},
					            async : false,   //采用同步
					            success : function(data){  
					           	 var memberTotalVo = $.parseJSON( data ); 
					            	 for(var i = 0 ; i < memberTotalVo.length ; i ++ ) {
					            		var obj = {name:memberTotalVo[i].name,value:memberTotalVo[i].memberTotal,cityCode:memberTotalVo[i].code} ;
					            		seriesData.push(obj) ;
					            	}        	
					            }  
					     });
			        	var array9 =new Array();
			        	loadProvinceMemberNums(array9);
			        	provinceMemberNums =array9[0].value;
			        	value1=provinceName+"会员统计";
			 	        value2="会员总数:"+cg(provinceMemberNums.toString())+"个";
			 	        value3=provinceName+"会员总数("+cg(provinceMemberNums.toString())+")个";
			 	        value4=provinceName+"会员统计趋势图";
			 	        loadTextData(value1,value2,value3,value4);
			 	        loadMemberLevelPie();
			 	        loadMemberTypePie();
			 	       loadMemberStatusPie();
			 	      loadExpenseMemberPie();
			 	     loadExpenseMemberByTypePie();
			 	    loadExpenseMemberByLevelPie();
			 	   loadExpenseMemberByStatusPie();
				     mapOption(seriesData) ;
			         option.series[0].mapType = mt;
				     myChart.setOption(option,true);
				     
				    
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
			   				            data : {name:cityName,level:level},
			   				            async : false,   //采用同步
			   				            success : function(data){  
			   				           	 var cityVo = $.parseJSON( data ); 
			   				            	 for(var i = 0 ; i < 1; i ++ ) {
			   				            		cityCode=cityVo[i].code;
			   				            	}        	
			   				            }  
			   				      });
			            		  
			            			  loadMemberTotal();
			            			   value1=cityName+"会员统计";
			            		       value2="会员总数:"+cg(memberTotals.toString())+"个";
			            		       value3=cityName+"会员总数("+cg(memberTotals.toString())+")个";
			            		       value4=cityName+"会员统计趋势图";
			            		       loadTextData(value1,value2,value3,value4);
			            		       loadMemberLevelPie();
			            		       loadMemberTypePie();
			            		       loadMemberStatusPie();
			            		       loadExpenseMemberPie();
			            		       loadExpenseMemberByTypePie();
			            		       loadExpenseMemberByLevelPie();
			            		       loadExpenseMemberByStatusPie();
			            		  
			            		 
			            }
			    	}
			    	
			  }else if(level == 4){
				    levelIdentifier='area';
				    var cityMaps = param.selected;
				    var name ;
				    for (var city in cityMaps) {
				    	if(cityMaps[city]){
				    		name = city;
				    	}
				    }
				    if(name){
				    	level = 3;
				    	 /**根据城市的名称查询城市的cityCode*/
	   			           $.ajax({  
	   				           type : "post",  
	   				            url : "queryCodeByName",  
	   				            data:{name:name,level:level},
	   				            async : false,   //采用同步
	   				            success : function(data){  
	   				           	 var cityVo = $.parseJSON( data ); 
	   				            	 for(var i = 0 ; i < 1; i ++ ) {
	   				            		cityCode=cityVo[i].code;
	   				            	}        	
	   				            }  
	   				      });
	            		  
	            			  loadMemberTotal();
	            			   value1=name+"会员统计";
	            		       value2="会员总数:"+cg(memberTotals.toString())+"个";
	            		       value3=name+"会员总数("+cg(memberTotals.toString())+")个";
	            		       value4=name+"会员统计趋势图";
	            		       loadTextData(value1,value2,value3,value4);
	            		       loadMemberLevelPie();
	            		       loadMemberTypePie();
	            		       loadMemberStatusPie();
	            		       loadExpenseMemberPie();
	            		       loadExpenseMemberByTypePie();
	            		       loadExpenseMemberByLevelPie();
	            		       loadExpenseMemberByStatusPie();
				    }else{
				    	level = 1 ;
				    	curIndx = 0;
				        mt = 'china';  
				        var seriesDatas8 = new Array();
				        
				        	cityCode='00';
				        	/**加载选中省份的城市会员数量信息*/
				        	loadProvinceMemberNums(seriesDatas8);
				        	loadMemberTotal();
				 		   value1="全国会员统计";
				 	       value2="会员总数:"+cg(memberTotals.toString())+"个";
				 	       value3="全国会员总数("+cg(memberTotals.toString())+")个";
				 	       value4="全国会员统计趋势图";
				 	       loadTextData(value1,value2,value3,value4);
				 	       loadMemberLevelPie();
				 	       loadMemberTypePie();
				 	       loadMemberStatusPie();
				 	       loadExpenseMemberPie();
				 	       loadExpenseMemberByTypePie();
				 	       loadExpenseMemberByLevelPie();
				 	       loadExpenseMemberByStatusPie();
						    mapOption(seriesDatas8);
					        option.series[0].mapType = mt;
					        myChart.setOption(option, true);
				    }
				    
			        
			      
			    }     
		  } 
			    $("#hidMemberCityCode").val(cityCode);
				 $("#hidMemberLayer").val(level);
				 setPeriodType2(0);// 默认统计今天和昨天的
				setCountType2(1);//默认统计注册会员数
				//selectMemberCombobox();
				setMemberLineOptions(0);
		}	
			  	
			}); 
				if(permission=="all"){//如果登陆用户的cityCode为oo显示全国的地图信息
					/**加载数据信息*/
				   loadMemberTotal();
				   value1="全国会员统计";
			       value2="会员总数:"+cg(memberTotals.toString())+"个";
			       value3="全国会员总数("+cg(memberTotals.toString())+")个";
			       value4="会员统计趋势图";
			       loadTextData(value1,value2,value3,value4);
				    /**加载地图信息*/
	   				var seriesDatas9 = new Array();
	   				loadProvinceMemberNums(seriesDatas9);
				    mapOption(seriesDatas9);
				    option.series[0].mapType = mt;
				   // 为echarts对象加载数据 \
	                myChart.setOption(option,true);
				    /**加载底部折线图区*/
				    //左侧饼状图区域
					loadMemberLevelPie();
					//右侧饼状图区域
					loadExpenseMemberPie();
					//底部区域
					setPeriodType2(0);// 默认统计今天和昨天的
					setCountType2(1);//默认统计注册会员数
					//selectMemberCombobox();
					setMemberLineOptions(0);
				}else if(permission=="part"){
					var seriesData2 = new Array();
		     		var city=cityName;
		     		var mapGeoData = require('echarts/util/mapData/params');
		     		    mapType.push(city);
		     		    // 自定义扩展图表类型
		     		    mapGeoData.params[city] = {
		     		        getGeoJson: (function (c) {
		     		            var geoJsonName = cityMap[c];
		     		            return function (callback) {
		     		            	 $.getJSON('echarts/core/data/' + cityCode + '.json', callback);
		     		            }
		     		        })(city)
		     		    }
		     		var ecConfig = require('echarts/config');
	
	     		    var mt = cityName;
	     		    var len = mapType.length;
	     		    var f= false;
	     		  
	     		    for(var i=0;i<len;i++){
	     		        if(mt == mapType[i]){
	     		        	cityName=mt;
	     		              f =true;
	     		              mt=mapType[i];
	     		        }
	     		     }
	   			      mapOption(seriesData2);
			   		  option.series[0].mapType = cityName;
			   		  myChart.setOption(option, true);
			   	   	/**生成饼状图以及底部折线图数据*/
           			  loadMemberTotal();
           			   value1=cityName+"会员统计";
           		       value2="会员总数:"+cg(memberTotals.toString())+"个";
           		       value3=cityName+"会员总数("+cg(memberTotals.toString())+")个";
           		       value4=cityName+"会员统计趋势图";
           		       loadTextData(value1,value2,value3,value4);
           		       loadMemberLevelPie();
           		       loadMemberTypePie();
           		       loadMemberStatusPie();
           		       loadExpenseMemberPie();
           		       loadExpenseMemberByTypePie();
           		       loadExpenseMemberByLevelPie();
           		       loadExpenseMemberByStatusPie();
		       		  //底部区域
						setPeriodType2(0);// 默认统计今天和昨天的
						setCountType2(1);//默认统计注册会员数
						//selectMemberCombobox();
						setMemberLineOptions(0);
				}   
              
           }
       );
    }
    /**加载省级会员总数信息*/
    function loadProvinceMemberNums(array){
   	 $.ajax({  
	           type : "post",  
			   url : "province_memberTotal_statistics", 
			   data:{cityCode:cityCode},
			   async : false,   //采用同步
		       success : function(data){  
		      	   var memberTotalVo = $.parseJSON( data ); 
		       	   for(var i = 0 ; i < memberTotalVo.length ; i ++ ) {
		       		var obj = {name:memberTotalVo[i].name,value:memberTotalVo[i].memberTotal,cityCode:memberTotalVo[i].code};
		       		array.push(obj) ;
		       	   }      
	           }  
	    }); 
    }
    
    /**左侧饼状图方法区*/
    //按照会员等级统计会员总数
    function loadMemberLevelPie(){
 	   require(
      		 	[
  	                'echarts',
  	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
          		],
      		 	function(ec){
      		 		$.post("getMemberTotalByLevel?cityCode="+cityCode+"&level="+level, function(data){
      		 			var MemberTotalVo = $.parseJSON( data ); 
      		 			myChart= ec.init(document.getElementById('middle-left-map3'));
      		 			var pieLegendDatas = new Array();
      		 			var pieSeriesDatas = new Array();
      		 			for(var i=0;i<MemberTotalVo.length;i++){
      		 				var a=MemberTotalVo[i].name;
      		 				pieLegendDatas.push(a);
      		 				var b={ value:MemberTotalVo[i].memberTotal,name:a};
      		 				pieSeriesDatas.push(b);
      		 			}
      		 				pieLegendData = pieLegendDatas ;
      		    			pieSeriesData = pieSeriesDatas;
  		    		 	 // 为echarts对象加载数据 
  		           		myChart.setOption(pieOption("会员等级",pieLegendData,pieSeriesData),true);
      		 		});
      		 	}
      		 );
    }
    //按照会员类型统计会员数
    function loadMemberTypePie(){
 	   require(
      		 	[
  	                'echarts',
  	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
          		],
      		 	function(ec){
      		 		$.post("getMemberTotalByType?cityCode="+cityCode+"&level="+level, function(data){
      		 			var MemberTotalVo = $.parseJSON( data ); 
      		 			myChart= ec.init(document.getElementById('middle-left-map1'));
      		 			pieLegendData = ['政企','个人'] ;
    		    			pieSeriesData = [{value:MemberTotalVo[0].memberTotal,name:'政企'},
    		    			                 {value:MemberTotalVo[1].memberTotal,name:'个人'}
    		    			                 ];	
  		    		 	 // 为echarts对象加载数据 
  		           		myChart.setOption(pieOption("会员类型",pieLegendData,pieSeriesData),true);
      		 		});
      		 	}
      		 );
    }
    //按照会员状态统计会员数
    function loadMemberStatusPie(){
 	   require(
      		 	[
  	                'echarts',
  	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
          		],
      		 	function(ec){
      		 		$.post("getMemberTotalByStatus?cityCode="+cityCode+"&level="+level, function(data){
      		 			var MemberTotalVo = $.parseJSON( data ); 
      		 			myChart= ec.init(document.getElementById('middle-left-map2'));
      		 			pieLegendData = ['就绪','体验'] ;
    		    			pieSeriesData = [{value:MemberTotalVo[0].memberTotal,name:'就绪'},
    		    			                 {value:MemberTotalVo[1].memberTotal,name:'体验'}
    		    			                 ];	
  		    		 	 // 为echarts对象加载数据 
  		           		myChart.setOption(pieOption("会员状态",pieLegendData,pieSeriesData),true);
      		 		});
      		 	}
      		 );
    } 
    
    
    /**右侧饼状图方法*/
    //按照消费类型统计消费会员数
    function loadExpenseMemberPie(){
 	   require(
     		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
         		],
     		 	function(ec){
     		 		$.post("getExpenseMember?cityCode="+cityCode+"&level="+level, function(data){
     		 			var MemberTotalVo = $.parseJSON( data ); 
     		 			myChart= ec.init(document.getElementById('middle-right-map4'));
     		 			pieLegendData = ['时租','日租','充电'] ;
   		    			pieSeriesData = [{value:MemberTotalVo[0].memberTotal,name:'时租'},
   		    			                 {value:MemberTotalVo[1].memberTotal,name:'日租'},
   		    			                 {value:MemberTotalVo[2].memberTotal,name:'充电'},
   		    			                 ];	
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("消费类型",pieLegendData,pieSeriesData),true);
     		 		});
     		 	}
     		 );
    }	
    
    //按照会员类型统计消费会员数
    function loadExpenseMemberByTypePie(){
 	   require(
     		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
         		],
     		 	function(ec){
     		 		$.post("getExpenseMemberByType?cityCode="+cityCode+"&level="+level, function(data){
     		 			var MemberTotalVo = $.parseJSON( data ); 
     		 			console.log(MemberTotalVo);
     		 			var enterpriseTotal =MemberTotalVo[0].memberTotal+MemberTotalVo[1].memberTotal+MemberTotalVo[2].memberTotal;
     		 			var elseTotal =MemberTotalVo[3].memberTotal+MemberTotalVo[4].memberTotal+MemberTotalVo[5].memberTotal;
     		 			myChart= ec.init(document.getElementById('middle-right-map1'));
     		 			pieLegendData = ['政企','个人'] ;
   		    			pieSeriesData = [{value:enterpriseTotal,name:'政企'},
   		    			                 {value:elseTotal,name:'个人'}
   		    			                 ];	
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("会员类型",pieLegendData,pieSeriesData),true);
     		 		});
     		 	}
     		 );
    }
    
    //按照会员等级统计消费会员数
    function loadExpenseMemberByLevelPie(){
 	   require(
    		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
        		],
    		 	function(ec){
    		 		$.post("getExpenseMemberByLevel?cityCode="+cityCode+"&level="+level, function(data){
    		 			var MemberTotalVo = $.parseJSON( data ); 
    		 			console.log(MemberTotalVo);
    		 			myChart= ec.init(document.getElementById('middle-right-map2'));
    		 			pieLegendData = ['黑名单','一星会员','二星会员','三星会员','四星会员','五星会员'] ;
  		    			pieSeriesData = [{value:MemberTotalVo[0].memberTotal,name:'黑名单'},
  		    			                 {value:MemberTotalVo[1].memberTotal,name:'一星会员'},
  		    			                 {value:MemberTotalVo[2].memberTotal,name:'二星会员'},
  		    			                 {value:MemberTotalVo[3].memberTotal,name:'三星会员'},
  		    			                 {value:MemberTotalVo[4].memberTotal,name:'四星会员'},
  		    			                 {value:MemberTotalVo[5].memberTotal,name:'五星会员'},
  		    			                 ];	
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("会员等级",pieLegendData,pieSeriesData),true);
    		 		});
    		 	}
    		 );
 	   
    }
    //根据会员状态统计消费会员数
    function loadExpenseMemberByStatusPie(){
 	   require(
    		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
        		],
    		 	function(ec){
    		 		$.post("getExpenseMemberByStatus?cityCode="+cityCode+"&level="+level, function(data){
    		 			var MemberTotalVo = $.parseJSON( data ); 
    		 			console.log(MemberTotalVo);
    		 			myChart= ec.init(document.getElementById('middle-right-map3'));
    		 			pieLegendData = ['就绪','体验'] ;
  		    			pieSeriesData = [{value:MemberTotalVo[0].memberTotal,name:'就绪'},
  		    			                 {value:MemberTotalVo[1].memberTotal,name:'体验'}
  		    			                 ];	
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("会员状态",pieLegendData,pieSeriesData),true);
    		 		});
    		 	}
    		 );
 	   
    }
    
	$(function(){
		   $("#statisticsTypeValue").val("member");
		   statisticsTypeValue = document.getElementById("statisticsTypeValue").value ;
		   //给页面上的饼状图的title设置样式
		   $("#middle-carType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-carStatus").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-memberLevel").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-expenseType").removeClass("bkwhite").addClass("bkblue");
	       $("#sp15").html("会员状态");
		   $("#sp16").html("会员类型");
	       $("#sp17").html("消费会员数据");
	       $("#sp18").html("会员类型");
	       $("#sp19").html("会员等级");
	       $("#sp20").html("会员状态");
	       cityCode='00';
	       level = 1 ;
	       if(sessionCityCode!='00'){
	    	   cityCode=sessionCityCode;
	       } 
	       
		   //调用顶部地图  
		   loadTopMap();
			$("#hidMemberCityCode").val(cityCode);
			$("#hidMemberLayer").val(level);
	
			
			/**隐藏默认不显示区域*/
		   $("#middle-right-map1").hide();
		   $("#middle-right-map2").hide();
		   $("#middle-right-map3").hide();
		   $("#middle-right-map4").show();
		   $("#middle-left-map2").hide();
		   $("#middle-left-map1").hide();
		   $("#middle-left-map3").show();
	
		   $("#middle-carStatus").bind("click",function(){
			   $("#middle-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-carStatus").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-memberLevel").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-left-map1").show();
			   $("#middle-left-map2").hide();
			   $("#middle-left-map3").hide();
			   loadMemberTypePie();
		   });
		   $("#middle-carType").bind("click",function(){
			   $("#middle-carType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-carStatus").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-memberLevel").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-left-map1").hide();
			   $("#middle-left-map3").hide();
			   $("#middle-left-map2").show();
			   loadMemberStatusPie();
		   }); 
		   $("#middle-memberLevel").bind("click",function(){
			   $("#middle-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-carStatus").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-memberLevel").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-left-map1").hide();
			   $("#middle-left-map2").hide();
			   $("#middle-left-map3").show();
			   loadMemberLevelPie();
		   });
		 
		   $("#middle-right-carType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-expenseType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-map1").show();
			   $("#middle-right-map2").hide();
			   $("#middle-right-map3").hide();
			   $("#middle-right-map4").hide();
			   loadExpenseMemberByTypePie();
		   });
		   $("#middle-right-orderType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-orderType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-expenseType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-map1").hide();
			   $("#middle-right-map3").hide();
			   $("#middle-right-map4").hide();
			   $("#middle-right-map2").show();
			   loadExpenseMemberByLevelPie();
		   });
		   $("#middle-right-memberType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-memberType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-expenseType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-map1").hide();
			   $("#middle-right-map2").hide();
			   $("#middle-right-map4").hide();
			   $("#middle-right-map3").show();
			   loadExpenseMemberByStatusPie();
		      });
		   $("#middle-right-expenseType").bind("click",function(){
			   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
			   $("#middle-right-expenseType").removeClass("bkwhite").addClass("bkblue");
			   $("#middle-right-map1").hide();
			   $("#middle-right-map2").hide();
			   $("#middle-right-map3").hide();
			   $("#middle-right-map4").show();
			   loadExpenseMemberPie();
		   });
		   selectMemberCombobox();
	});



