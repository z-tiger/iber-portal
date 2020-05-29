/**
 * ouxx 网点运营统计，echarts柱状图部分
 * 
 */
function sethistogramOptions(periodType) {

	$("#period_Type").val(periodType);
	if (0 === periodType) {// 选择"今日"
		$("#characterArea_left").css({
			"background" : "url(echarts/asset/img/newUi/backimg.jpg)"
		});
		$("#characterArea_middle").css({
			"background" : "url(echarts/asset/img/newUi/backimg4.jpg)"
		});
	} else if (1 === periodType) {// 选择“本月”
		$("#characterArea_left").css({
			"background" : "url(echarts/asset/img/newUi/backimg2.jpg)"
		});
		$("#characterArea_middle").css({
			"background" : "url(echarts/asset/img/newUi/backimg3.jpg)"
		});
	}
	var check = $("input[name='radio1']:checked").val();
	var countType = null;
	var orderType = null;
	var memberType = null;
	if (null != check) {
		if (1 == check) {// 订单类型
			countType = $("#hidCountType1").val();
		} else if (2 == check) {// 会员类型
			memberType = $("#hidMemberType1").val();
		} else if (3 == check) {// 统计类型
			orderType = $("#hidOrderType1").val();
		}
	}

	$.post("queryParkDetail.do", {
		"periodType" : periodType,
		"countType" : countType,
		"orderType" : orderType,
		"memberType" : memberType,
		"cityCode" : $("#hidCityCode").val(),
		"layer" : $("#hidLayer").val()
	}, function(data) {
		barCallBack(periodType, data);
	});

}


// 选择不同，车品牌、会员类型、订单类型
function selByBrandOrMemberTypeOrOrderType1(check, value) {
	// var check = $("input[name='radio']:checked").val();
	var json;
	if (1 == check) {
		// 统计类型
		$("#radioCount1").prop("checked", true);
		json = {
			"countType" : value
		};
	} else if (2 == check) {
		$("#radioMember1").prop("checked", true);
		json = {
			"memberType" : value
		};
	} else if (3 == check) {
		$("#radioOrder1").prop("checked", true);
		json = {
			"orderType" : value
		};
	}
	return json;
}

// 获取查询条件的值

$("#countTypeList1").combobox({
	onSelect : function(rec) {
		$("#hidCountType1").val(rec.value);
		// queryRentDatas1(1, rec.value);
		$("#memberTypeList1").textbox("setValue","");
		$("#orderTypeList1").textbox("setValue","");
		queryRentDetail1(1, rec.value);

	}
});
$("#memberTypeList1").combobox({
	onSelect : function(rec) {
		$("#hidMemberType1").val(rec.value);
		$("#countTypeList1").textbox("setValue","");
		$("#orderTypeList1").textbox("setValue","");
		// queryRentDatas1(2, rec.value);
		queryRentDetail1(2, rec.value);
	}
});
$("#orderTypeList1").combobox({
	onSelect : function(rec) {
		$("#hidOrderType1").val(rec.value);
		$("#memberTypeList1").textbox("setValue","");
		$("#countTypeList1").textbox("setValue","");
		// queryRentDatas1(3, rec.value);
		queryRentDetail1(3, rec.value);
	}
});

//点击“清空”
function clearSelPark(){
	$("input[type='radio']").prop("checked", false);
	$(".easyui-combobox").combobox("clear");
	$("input[type='hidden']").val(null);
	$("input[type='radio']").val(null);
	$("#hidCityCode").val(cityCode);
	$("#hidLayer").val(level);
	queryRentDetail1(null, null);
}
function clearSelPark1(){
	$(".easyui-combobox").combobox("clear");
	$(".easyui-datebox").combo('setText','');
	$("#hidNetworkCountByType").val(null);
	$("#hidNetworkCountTime").val(null);
	$("#hidNetworkCountTo").val(null);
	queryNetworkUseDetail();
}


// 设置查询条件
function queryRentDetail1(check, value) {

	var countType = null;
	var orderType = null;
	var memberType = null;
	if (null != check && null != value) {
		var json = selByBrandOrMemberTypeOrOrderType1(check, value);
		countType = null != json ? json.countType : null;
		orderType = null != json ? json.orderType : null;
		memberType = null != json ? json.memberType : null;
	}
	var periodType = $("#period_Type").val();
	$.post("queryParkDetail.do", {
		"periodType" : periodType,
		"countType" : countType,
		"memberType" : memberType,
		"orderType" : orderType,
		"cityCode" : $("#hidCityCode").val(),
		"layer" : $("#hidLayer").val()
	}, function(data) {
		barCallBack(periodType, data);
	});
}
// 总体统计数据：今天及其与昨天的比率、本月及其与上月的比率、累计
function queryRentDatas1(check, value) {
	var countType = null;
	var orderType = null;
	var memberType = null;
	if (null != check && null != value) {
		var json = selByBrandOrMemberTypeOrOrderType1(check, value);
		if (null != json) {
			if (null != json.countType) {
				countType = json.countType;
			} else if (null != json.orderType) {
				orderType = json.orderType;
			} else if (null != json.memberType) {
				memberType = json.memberType;
			}
		}
	}
}
// 选中“今天”或“本月”
function setPeriodType1(periodType) {
	$("#period_Type").val(periodType);
}

// 设置总体统计：今天及其与昨天的比率、本月及其与上月的比率、累计
function setTotalDatas(statisticsVo) {
	if (null != statisticsVo && "" != statisticsVo) {
		// 今天
		$("#todayCnt1").html(cg(statisticsVo.todayCnt.toString()));
		
		var ratioDayCnt1 = statisticsVo.ratioDayCnt;
		// 比率<0，则今天少于昨天的，比率数字要为绿色，且左边有向下箭头
		if (0 > ratioDayCnt1) {
			$("#ratioDayCnt1").css({
				"color" : "green"
			});
			$("#ratioDayCnt1").html("↓ " + Math.abs(ratioDayCnt1) + " %");
		} else {// 比率>0，则今天多于昨天的，比率数字要为红色，且左边有向上箭头
			$("#ratioDayCnt1").css({
				"color" : "red"
			});
			$("#ratioDayCnt1").html("↑ " + ratioDayCnt1 + " %");
		}
		// 本月
		$("#thisMonthCnt1").html(cg(statisticsVo.thisMonthCnt.toString()));
		var ratioMonthCnt1 = statisticsVo.ratioMonthCnt;
		// 比率<0，则今天少于昨天的，比率数字要为绿色，且左边有向下箭头
		if (0 > ratioMonthCnt1) {
			$("#ratioMonthCnt1").css({
				"color" : "green"
			});
			$("#ratioMonthCnt1").html("↓ " + Math.abs(ratioMonthCnt1) + " %");
		} else {// 比率>0，则今天多于昨天的，比率数字要为红色，且左边有向上箭头
			$("#ratioMonthCnt1").css({
				"color" : "red"
			});
			$("#ratioMonthCnt1").html("↑ " + ratioMonthCnt1 + " %");
		}
		$("#totalCnt1").html(cg(statisticsVo.totalCnt.toString()));
	}
}

function barCallBack(periodType, data) {
	var todayCnt = 0;
	var yesterdayCnt = 0;
	var thisMonthCnt = 0;
	var lastMonthCnt = 0;
	var totalCnt = 0;
	var ratioDayCnt = 0;// 今天次数 占 昨天次数 的比率， 单位：%
	var ratioMonthCnt = 0;// 本月次数 占 上月次数 的比率， 单位：%

	var xAxisData = new Array("    \t    ", "    \t    ", "    \t    ", "    \t    ", "    \t    ");

	var seriesCntDataCurr = new Array(0, 0, 0, 0, 0);
	var seriesCntDataLast = new Array(0, 0, 0, 0, 0);

	if (null != data && "" != data) {
		var seriesNameCurr;
		var seriesNameLast;

		var detailVo = $.parseJSON(data);
		// 显示总体数据
		setTotalDatas(detailVo);
		// 插入详情
		var currList = detailVo.currList;
		var lastList = detailVo.lastList;
		if(null != currList && currList.length > 0){
			for(var i = 0; i < currList.length; ++i){
				seriesCntDataCurr[i] = currList[i].cnt;
				xAxisData[i] = currList[i].name + xAxisData[i];
			}
		}
		if(null != lastList && lastList.length > 0){
			for(var i = 0; i < lastList.length; ++i){
				seriesCntDataLast[i] = lastList[i].cnt;
				xAxisData[i] = xAxisData[i] + lastList[i].name;
			}
		}

		// 选择“今日”，x轴是
		if (0 == periodType) {
			seriesNameCurr = "今日网点使用";
			seriesNameLast = "昨日网点使用";
		} else if (1 == periodType) {// 选择“本月”
			seriesNameCurr = "本月网点使用";
			seriesNameLast = "上月网点使用";
		}
		histogramOption("clshistogram", seriesNameCurr, seriesNameLast,
				seriesCntDataCurr, seriesCntDataLast, xAxisData);
	}
}
function  barCallBack1(data){
	var dataObj=eval("("+data+")");
	var yAxis = new Array();
	var series = new Array();
	for(var i=0;i<dataObj.length;i++){
		yAxis[i]=dataObj[i].name;
		series[i] =dataObj[i].parkUserTime;
	}
	histogramOption1(yAxis,series)
}
function histogramOption1(yAxis,series){
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		myChart = ec.init(document.getElementById('networkRunAnalysisChart'));
		// 今日(本月)和昨日(上月)的比较
		var optionw = {
				  
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['网点使用次数']
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'value',
			            boundaryGap : [0, 0.01]
			        }
			    ],
			    yAxis : [
			             {
			                 type : 'category',
			                 data : yAxis
			             }
			         ],
	           series : [
	                   {
	                       name:'网点使用次数',
	                       type:'bar',
	                       data:series,
	                       barWidth : 10,
	                   }
	               ],
			};
		window.onresize = myChart.resize;
		// 为echarts对象加载数据
		myChart.setOption(optionw, true);
	});
}
/**
 * 
 */

// 今天/本月、昨天/上月的数据的折线图
function histogramOption(id, seriesNameCurr, seriesNameLast,
		seriesCntDataCurr, seriesCntDataLast, xAxisData) {
	require([ 'echarts', 'echarts/chart/bar' ], function(ec) {
		myChart = ec.init(document.getElementById('clshistogram'));
		// 今日(本月)和昨日(上月)的比较
		var optionTest = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ seriesNameCurr, seriesNameLast ]
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : xAxisData
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : seriesNameCurr,
				type : 'bar',
				data : seriesCntDataCurr,
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : seriesNameLast,
				type : 'bar',
				data : seriesCntDataLast,
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};

		window.onresize = myChart.resize;
		// 为echarts对象加载数据
		myChart.setOption(optionTest, true);
	});
}

//设置网点运营分析的查询条件
$("#networkCountByType").combobox({
	onSelect : function(rec) {
		$("#hidNetworkCountByType").val(rec.value);
		queryNetworkUseDetail();
	}
});

$("#networkCountBeginTime").datebox({
	onChange:function(rec){
		$("#hidNetworkCountTime").val(rec);
		if($("#networkCountEndTime").datebox('getValue')!=""){
			queryNetworkUseDetail();
		}
	}
});

$("#networkCountEndTime").datebox({
	onChange:function(rec){
		$("#hidNetworkCountTo").val(rec);
		if($("#networkCountBeginTime").datebox('getValue')!=""){
			queryNetworkUseDetail();
		}
	}
});


function queryNetworkUseDetail(){
	$.post("queryParkUserDetail.do", {
		"countType" : $("#hidNetworkCountByType").val(),
		"startTime" : $("#hidNetworkCountTime").val(),
		"endTime" : $("#hidNetworkCountTo").val(),
		"cityCode" : $("#hidCityCode").val(),
		"layer" : $("#hidLayer").val()
	}, function(data) {
		barCallBack1(data);
	});
}

/**获取数据库cityCode权限对应的网点车位总数信息--按照全国。地级市划分*/
function loadParkCarNums(){

    $.ajax({  
          type : "post",  
           url : "queryAllParkCarport?cityCode="+cityCode,  
           async : false,   //采用同步
           success : function(data){  
          	 var parkVo = $.parseJSON( data ); 
           	parkNumTotal =parkVo[0].total;    	
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
		        // 全国选择时指定到选中的省份clearSelPark1
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
			            url : "queryCodeByName?",  
			            data:{name:provinceName,level:level},
			            async : false,   //采用同步
			            success : function(data){  
			           	 var cityVo = $.parseJSON( data ); 
			            	 for(var i = 0 ; i < 1 ; i ++ ) {
			            		cityCode=cityVo[i].code;
			            	}        	
			            }  
			      });
		          var seriesData  = new Array() ; 
		          loadProvinceParkCarNums();
	        	   $("#sp17").html("网点车位数("+parkNumTotal+")个");
	        	   loadProvinceParkInfo(seriesData);
	        	   value1=provinceName+"网点统计";
		 	       value2="网点总数:"+cg(provinceParkNums.toString())+"个";
		 	       value3=provinceName+"网点总数("+cg(provinceParkNums.toString())+")个";
		 	       value4="";
		           loadTextData(value1,value2,value3,value4);
		           $("#sp28").html(provinceName);
		           /**(网点总数)网点类型区分的饼状图随着省份联动*/
		           loadProvinceMiddleLeftPark();
		           loadProvinceMiddleLeftServicePark();
		           loadProvinceMiddleRightByConnectorType();
		           loadProvinceMiddleRightByCarType();
		           /**获取数据库中全国省下级城市网点信息*/
		        	$.ajax({  
				           type : "post",  
				            url : "city_parkNum_statistics",
				            data:{name:"全国"},
				            async : false,   //采用同步
				            success : function(data){  
				           	 var parkVo = $.parseJSON( data ); 
				            	 for(var i = 0 ; i < parkVo.length ; i ++ ) {
				            		var obj = {name:parkVo[i].name,value:parkVo[i].cityTotal,cityCode:parkVo[i].code} ;
				            		seriesData.push(obj) ;
				            	}        	
				            }  
				     });
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
		            		    /**获取数据库中全国省下级城市网点总数信息*/
					        	$.ajax({  
							           type : "post",  
							            url : "city_parkNum_statistics", 
							            data:{name:cityName},
							            async : false,   //采用同步
							            success : function(data){  
							           	 var parkVo = $.parseJSON( data ); 
							            	 for(var i = 0 ; i < parkVo.length ; i ++ ) {
							            		var obj = {name:parkVo[i].name,value:parkVo[i].cityTotal,cityCode:parkVo[i].code} ;
							            		cityParkNums=parkVo[i].cityTotal;
							            	}        	
							            }  
							     });
		            		  var seriesData2  = new Array() ;
		            		  /**根据城市的名称查询城市的cityCode*/
		   			           $.ajax({  
		   				           type : "post",  
		   				            url : "queryCodeByName", 
		   				            data:{name:cityName,level:level},
		   				            async : false,   //采用同步
		   				            success : function(data){  
		   				           	 var cityVo = $.parseJSON( data ); 
		   				            	 for(var i = 0 ; i < 1; i ++ ) {
		   				            		cityCode=cityVo[i].code;
		   				            	}        	
		   				            }  
		   				      });
		   			          //填充页面统计数据信息
		   			          loadParkCarNums();
	            			  $("#sp17").html("网点车位数("+parkNumTotal+")个");
	            			  value1=cityName+"网点统计";
	   			 	       	  value2="网点总数:"+cg(cityParkNums.toString())+"个";
	   			 	          value3=cityName+"网点总数("+cg(cityParkNums.toString())+")个";
	   			 	          value4="";
	   			              loadTextData(value1,value2,value3,value4);
	   			              $("#sp28").html(cityName);
	            			  /**随地图联动加载饼状图*/
	   			              loadMiddleLeftPark();
	   			              loadMiddleLeftServicePark();
	   			              loadMiddleRightByConnectorType();
	   			              loadMiddleRightByCarType();
	   			              /**获取数据库中全国地区网点信息*/
			       			    $.ajax({  
			       			           type : "post",  
			       					   url : "area__parkNum_statistics",  
			       					   async : false,   //采用同步
			       				       success : function(data){  
			       				      	   var parkVo = $.parseJSON( data ); 
			       				       	   for(var i = 0 ; i < parkVo.length ; i ++ ) {
			       				       		var obj = {name:parkVo[i].name,value:parkVo[i].areaTotal} ;
			       				       	       seriesData2.push(obj) ;
			       				       	   }      
			       			           }  
			       			    });
			       			  mapOption(seriesData2);
		            		  option.series[0].mapType = mt;
		            		  myChart.setOption(option, true);
		            }
		    	}
		    	
		  }else if(level == 4){
			    levelIdentifier='area';
			    	level = 1 ;
			    	curIndx = 0;
			        mt = 'china';  
			        var seriesDatas8 = new Array();
			        cityCode='00';
			        value1="全国网点统计";
			 	    value2="网点总数:"+cg(websiteTotal.toString())+"个";
			 	    value3="全国网点总数("+cg(websiteTotal.toString())+")个";
			 	    value4="";
			 	    loadTextData(value1,value2,value3,value4);
			 	    $("#sp28").html("全国");
			 	    
			 	    /**随地图加载饼状图*/
			 	    loadMiddleLeftPark();
			 	    loadMiddleLeftServicePark();
			 	    loadMiddleRightByConnectorType();
			 	    loadMiddleRightByCarType();
			 	    loadParkCarNums();
			 	   $("#sp17").html("网点车位数("+parkNumTotal+")个");
				   $.ajax({  
				           type : "post",  
						   url : "province_parkNum_statistics",  
						   async : false,   //采用同步
					       success : function(data){  
					      	   var parkVo = $.parseJSON( data ); 
					       	   for(var i = 0 ; i < parkVo.length ; i ++ ) {
					       		var obj = {name:parkVo[i].name,value:parkVo[i].parkNum,cityCode:parkVo[i].code} ;
					       		seriesDatas8.push(obj) ;
					       	   }      
				           }  
				    });
			        mapOption(seriesDatas8);
			        option.series[0].mapType = mt;
			        myChart.setOption(option, true); 
		    } 	
	  } 
		    $("#hidCityCode").val(cityCode);
			 $("#hidLayer").val(level);
			 clearSelPark();
			 sethistogramOptions(0);
			 queryNetworkUseDetail();
	}	       
		  	
		}); 
			if(permission=="all"){//如果登陆用户的cityCode为oo显示全国的地图信息
			 /**加载页面数量信息*/
			   loadParkNums();
			   value1="全国网点统计";
		       value2="网点总数:"+cg(websiteTotal.toString())+"个";
		       value3="全国网点总数("+cg(websiteTotal.toString())+")个";
		       value4="";
		       loadTextData(value1,value2,value3,value4);
		       loadParkCarNums();
		       $("#sp28").html("全国");
		       $("#sp17").html("网点车位数("+parkNumTotal+")个");
		       
		       /**获取数据库中全国省级网点总数信息*/
		       var seriesDatas9 = new Array();
			   loadProvinceParkInfo(seriesDatas9);
			   mapOption(seriesDatas9);
			   option.series[0].mapType = mt;
			   // 为echarts对象加载数据 \
               myChart.setOption(option,true);
               /**加载中部饼状图*/
               loadMiddleLeftPark();
               loadMiddleRightByConnectorType();
               /**底部折线图*/
               sethistogramOptions(0)
               /**网点运营分析*/
               queryNetworkUseDetail();
			}else if(permission=="part"){
				
			}   
          
       }
   );
}

	function loadParkNums (){
		/**根据cityCode获取网点个数的总计*/
	    
	    $.ajax({  
	          type : "post",  
	           url : "queryAllParkNums",  
	           async : false,   //采用同步
	           success : function(data){  
	          	 var parkVo = $.parseJSON( data ); 
	          	  websiteTotal =parkVo[0].websiteTotal;    	
	           }  
	    });
	}

	/**获取省份网点数*/
	 function loadProvinceParkInfo(array){
  	   $.ajax({  
		           type : "post",  
				   url : "province_parkNum_statistics", 
				   data:{cityCode:cityCode},
				   async : false,   //采用同步
			       success : function(data){  
			      	   var parkVo = $.parseJSON( data ); 
			       	   for(var i = 0 ; i < parkVo.length ; i ++ ) {
			       		var obj = {name:parkVo[i].name,value:parkVo[i].parkNum,cityCode:parkVo[i].code} ;
			       		provinceParkNums =parkVo[i].parkNum;
			       		array.push(obj) ;
			       	   }      
		           }  
		    });
     }   
	 /**获取数据库cityCode权限对应的网点车位总数信息--按照全国。地级市划分*/
     function loadParkCarNums(){
    
	     $.ajax({  
	           type : "post",  
	            url : "queryAllParkCarport",  
	            data:{cityCode:cityCode},
	            async : false,   //采用同步
	            success : function(data){  
	           	 var parkVo = $.parseJSON( data ); 
	            	parkNumTotal =parkVo[0].total;    	
	            }  
	     });
	 
     }
     
     /** 中部 左侧饼状图(网点类型)*/
     function loadMiddleLeftPark(){
         require(
     		 	[
 	                'echarts',
 	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
         		],
     		 	function(ec){
     		 		$.post("queryAllParkByType?cityCode="+cityCode, function(data){
     		 			
     		 			var parkTotalVo = $.parseJSON( data ); 
     		 			myChart= ec.init(document.getElementById('middle-left-map1'));
     		 				var arrays =new Array();
     		 				for(var i=0;i<parkTotalVo.length;i++){
         		 				var a=parkTotalVo[i].parkTotalByType;
         		 				arrays.push(a);
         		 			}
     		 				pieLegendData = ['自有','合作'] ;
     		    			pieSeriesData = [{value:arrays[0],name:'自有'},
     		    			                 {value:arrays[1],name:'合作'},
     		    			                 ];	
 		    		 	 // 为echarts对象加载数据 
 		           		myChart.setOption(pieOption("网点类型",pieLegendData,pieSeriesData),true);
     		 		});
     		 	}
     		 );
         }
     	/**中部 右侧饼状图(充电枪类型--按照全国、地级市区分)*/
	   function loadMiddleRightByConnectorType(){
		  
	   require(
 		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
     		],
 		 	function(ec){
 		 		
 		 		$.post("queryAllParkCarportByConnectorType?cityCode="+cityCode, function(data){
 		 			var park = $.parseJSON( data ); 
 		 			var arrays = new Array();
 		 			var myChart= ec.init(document.getElementById('middle-right-map2'));
 		 				for(var i=0;i<park.length;i++){
 		 					var a =park[i].connectorTotal;
 		 				     arrays.push(a);
 		 				}
 		 				var emptyCarPort =parkNumTotal-arrays[0]-arrays[1];
 		 				pieLegendData = ['快充','慢充'/*,'空闲'*/] ;
 		    			pieSeriesData = [{value:arrays[0],name:'慢充'},
 		    			                 {value:arrays[1],name:'快充'}
 		    			                /* {value:emptyCarPort,name:'空闲'}*/
 		    			                 ];	
		    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("充电枪类型",pieLegendData,pieSeriesData),true);
 		 		});
 		 	}
 		 );
	   }
	   /**中部 左侧饼状图(网点类型--按省份划分)*/
      function loadProvinceMiddleLeftPark(){
          
          require(
      		 	[
  	                'echarts',
  	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
          		],
      		 	function(ec){
      		 		$.post("queryAllProvinceParkByType?cityCode="+cityCode, function(data){
      		 			
      		 			var parkTotalVo = $.parseJSON( data ); 
      		 			myChart= ec.init(document.getElementById('middle-left-map1'));
      		 				var arrays =new Array();
      		 				for(var i=0;i<parkTotalVo.length;i++){
          		 				var a=parkTotalVo[i].parkTotalByType;
          		 				arrays.push(a);
          		 			}
      		 				pieLegendData = ['自有','合作'] ;
      		    			pieSeriesData = [{value:arrays[0],name:'自有'},
      		    			                 {value:arrays[1],name:'合作'},
      		    			                 ];	
  		    		 	 // 为echarts对象加载数据 
  		           		myChart.setOption(pieOption("网点类型",pieLegendData,pieSeriesData),true);
      		 		});
      		 	}
      		 );
          }
      /**中部 左侧饼状图(服务类型)--按照省份划分*/
      function loadProvinceMiddleLeftServicePark(){
          
          require(
      		 	[
  	                'echarts',
  	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
          		],
      		 	function(ec){
      		 		$.post("queryAllProvinceParkByServiceType?cityCode="+cityCode, function(data){
      		 			
      		 			var parkTotalVo = $.parseJSON( data ); 
      		 			myChart= ec.init(document.getElementById('middle-left-map2'));
      		 				var arrays =new Array();
      		 				for(var i=0;i<parkTotalVo.length;i++){
          		 				var a=parkTotalVo[i].parkTotalByService;
          		 				arrays.push(a);
          		 			}
      		 				pieLegendData = ['1s网点','2s网点','3s网点','6s网点'] ;
      		    			pieSeriesData = [{value:arrays[0],name:'1s网点'},
      		    			                 {value:arrays[1],name:'2s网点'},
      		    			                 {value:arrays[2],name:'3s网点'},
      		    			                 {value:arrays[3],name:'6s网点'},
      		    			                 ];	
  		    		 	 // 为echarts对象加载数据 
  		           		myChart.setOption(pieOption("服务类型",pieLegendData,pieSeriesData),true);
      		 		});
      		 	}
      		 );
          }
      /**中部 左侧饼状图(服务类型)*/
      function loadMiddleLeftServicePark(){
          
          require(
      		 	[
  	                'echarts',
  	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
          		],
      		 	function(ec){
      		 		$.post("queryAllParkByServiceType?cityCode="+cityCode, function(data){
      		 			
      		 			var parkTotalVo = $.parseJSON( data ); 
      		 			myChart= ec.init(document.getElementById('middle-left-map2'));
      		 				var arrays =new Array();
      		 				for(var i=0;i<parkTotalVo.length;i++){
          		 				var a=parkTotalVo[i].parkTotalByService;
          		 				arrays.push(a);
          		 			}
      		 				pieLegendData = ['1s网点','2s网点','3s网点','6s网点'] ;
      		    			pieSeriesData = [{value:arrays[0],name:'1s网点'},
      		    			                 {value:arrays[1],name:'2s网点'},
      		    			                 {value:arrays[2],name:'3s网点'},
      		    			                 {value:arrays[3],name:'6s网点'},
      		    			                 ];	
  		    		 	 // 为echarts对象加载数据 
  		           		myChart.setOption(pieOption("服务类型",pieLegendData,pieSeriesData),true);
      		 		});
      		 	}
      	);
     }
$(function(){
	 	//给页面上的饼状图的title设置样式
		cityCode='00';
		level = 1 ;
		if(sessionCityCode!='00'){
		   cityCode=sessionCityCode;
		} 
	   //调用顶部地图  
	    loadTopMap();
		$("#hidCityCode").val(cityCode);
		$("#hidLayer").val(level);
		/**隐藏默认不显示区域*/
	   $("#middle-right-map2").show();
	   $("#middle-right-map3").hide();
	   $("#middle-left-map2").hide();
	   $("#middle-left-map1").show();
	   $("#middle-carStatus").bind("click",function(){
		   $("#middle-carType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-carStatus").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-left-map2").hide();
		   $("#middle-left-map1").show();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleLeftPark();
		   }
		   loadMiddleLeftPark();
	   });
	   $("#middle-carType").bind("click",function(){
		   $("#middle-carType").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-carStatus").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-left-map1").hide();
		   $("#middle-left-map2").show();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleLeftServicePark();
		   }
		   loadMiddleLeftServicePark();
	   }); 
	   
	   $("#middle-right-orderType").bind("click",function(){
		   $("#middle-right-orderType").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-right-memberType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-map3").hide();
		   $("#middle-right-map2").show();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleRightByConnectorType();
		   }
		   loadMiddleRightByConnectorType();
	   });
	   $("#middle-right-memberType").bind("click",function(){
		   $("#middle-right-orderType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-memberType").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-right-map2").hide();
		   $("#middle-right-map3").show();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleRightByCarType();
		   }
		   loadMiddleRightByCarType();
	      });
});
