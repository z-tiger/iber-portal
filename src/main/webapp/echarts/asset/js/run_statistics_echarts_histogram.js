/**
 * 网点运营统计，echarts柱状图部分
 * 	
 * */	
	function sethistogramOptions(periodType){
		
		$("#period_Type").val(periodType);
		if(0 === periodType){//选择"今日"
			$("#characterArea_left").css({"background" : "url(echarts/asset/img/newUi/backimg.jpg)"});
			$("#characterArea_middle").css({"background" : "url(echarts/asset/img/newUi/backimg4.jpg)"});
		} else if(1 === periodType){//选择“本月”
			$("#characterArea_left").css({"background" : "url(echarts/asset/img/newUi/backimg2.jpg)"});
			$("#characterArea_middle").css({"background" : "url(echarts/asset/img/newUi/backimg3.jpg)"});
		}	
		var check = $("input[name='radio1']:checked").val();
		var countType = null;
		var orderType = null;
		var memberType = null;		
		if(null != check){
			if(1 == check){//订单类型
				countType = $("#hidCountType1").val();
			} else if(2 == check){//会员类型
				memberType = $("#hidMemberType1").val();
			} else if(3 == check){//统计类型
				orderType = $("#hidOrderType1").val();
			}
		}		

		$.post("queryDateDetail.do", 
				{
				 "periodType": periodType,
				 "countType" : countType,
				 "orderType" : orderType,
				 "memberType" : memberType
				 }, function(data){
					 lineCallBack1(periodType, data);
				 });
		
	}  	
		
		//下拉框的响应函数
		function selectCombobox1(){
				
			
			$("#countTypeList1").combobox({
				onSelect: function(rec){
					var countType = rec.value;
					$("#countType1").val(countType);
					var check = $("input[name='radio']:checked").val();
					var countType = null;
					var orderType = null;
					var memberType = null;
					if(null != check){
						if(1 == check){//车品牌
							orderType = $("#hidOrderType1").val();
						} else if(2 == check){
							memberType = $("#hidMemberType1").val();
						} else if(3 == check){
							countType = $("#hidCountType1").val();
						}
					}
					

					$.post("queryDateDetail.do", 
							{
							 "countType": countType, 
							 "countType" : countType,
							 "memberType" : memberType,
							 "orderType" : orderType}, function(data){
								 lineCallBack1(data);
							 });					
					var periodType1 = $("#period_Type").val();

		        }
			});			
			$("#orderTypeList1").combobox({
				onSelect: function(rec){    
					$("#hidOrderType1").val(rec.value);
					//queryRentDatas1(1, rec.value);
					queryRentDetail1(1, rec.value);
		        }
			});
			$("#memberTypeList1").combobox({
				onSelect: function(rec){    
					$("#hidMemberType1").val(rec.value);
					//queryRentDatas1(2, rec.value);  
					queryRentDetail1(2, rec.value);  
				}
			});
			$("#countTypeList1").combobox({
				onSelect: function(rec){    
					$("#hidCountType1").val(rec.value);
					//queryRentDatas1(3, rec.value);  
					queryRentDetail1(3, rec.value);  
				}
			});
		}

		//选择不同，车品牌、会员类型、订单类型
		function selByBrandOrMemberTypeOrOrderType1(check, value){
//			var check = $("input[name='radio']:checked").val();
			var json;
			if(3 == check){
				//订单类型				
				$("#radioCount1").prop("checked", true);
				json = {"countType" : value};
			} else if(2 == check){
				$("#radioMember1").prop("checked", true);
				json = {"memberType" : value};
			} else if(1 == check){
				$("#radioOrder1").prop("checked", true);
				json = {"orderType" : value};
			}
			return json;
		}

//获取查询条件的值	
		
		$("#countTypeList1").combobox({
			onSelect: function(rec){    
				$("#hidCountType1").val(rec.value);
				//queryRentDatas1(1, rec.value);
				queryRentDetail1(1, rec.value);
				
	        }
		});
		$("#memberTypeList1").combobox({
			onSelect: function(rec){ 
				$("#hidMemberType1").val(rec.value);
				//queryRentDatas1(2, rec.value);  
				queryRentDetail1(2, rec.value);  
			}
		});
		$("#orderTypeList1").combobox({
			onSelect: function(rec){    
				$("#hidOrderType1").val(rec.value);
				//queryRentDatas1(3, rec.value);  
				queryRentDetail1(3, rec.value);  
			}
		});
		//设置查询条件
		function queryRentDetail1(check, value){
			
			var countType = null;
			var orderType = null;
			var memberType = null;
			if(null != check && null != value){
				var json = selByBrandOrMemberTypeOrOrderType1(check, value);
				countType = null != json ? json.countType : null;
				orderType = null != json ? json.orderType : null;
				memberType = null != json ? json.memberType : null;
			}
			var periodType = $("#period_Type").val();
			$.post("queryDateDetail.do", 
					{
					 "periodType": periodType,
					 "countType" : countType,
					 "memberType" : memberType,
					 "orderType" : orderType}, 
					 function(data){
						 lineCallBack1(periodType, data);
					 });
		}
//总体统计数据：今天及其与昨天的比率、本月及其与上月的比率、累计
		function queryRentDatas1(check, value){
			var countType = null;
			var orderType = null;
			var memberType = null;
			if(null != check && null != value){
				var json = selByBrandOrMemberTypeOrOrderType1(check, value);
				if(null != json){
					if(null != json.countType){
						countType = json.countType;
					} else if(null != json.orderType){
						orderType = json.orderType;
					} else if(null != json.memberType){
						memberType = json.memberType;
					}
				}
			}		
		
		}
		 //选中“今天”或“本月”
		function setPeriodType1(periodType){
			$("#period_Type").val(periodType);
			
			
		}													
		function lineCallBack1(periodType, data){											
			var	todayCnt = new Array();
			var	yesterdayCnt = new Array();
			var	thisMonthCnt = new Array();
			var	lastMonthCnt = new Array();
			var	totalCnt = new Array();
			var	daytotalCnt = new Array();
			var	monthtotalCnt = new Array();
			var	todayName = new Array();
			var	yesterdayName = new Array();
			var	thisMonthName = new Array();
			var	lastMonthName = new Array();
			var	totalName = new Array();
			var xAxisData = new Array();
			 //租赁次数
			 var seriesCntDataCurr = new Array();
			 var seriesCntDataLast = new Array();
			 //今天和昨天
			 
				 //初始化数据
				
					 	todayCnt.length = 0; 
						yesterdayCnt.length = 0; 
						thisMonthCnt.length = 0; 
						lastMonthCnt.length = 0; 
						totalCnt.length = 0; 
						todayName.length = 0; 
						yesterdayName.length = 0; 
						thisMonthName.length = 0; 
						lastMonthName.length = 0; 
						totalName.length = 0;
						xAxisData.length = 0;
						daytotalCnt.length = 0; 
						monthtotalCnt.length = 0; 	
						debugger ;		
			 if(null != data && "" != data){
				 var seriesNameCurr; 
				 var seriesNameLast;
				 var detailVo = $.parseJSON( data ); 
				 /*console.log(detailVo);*/
				 for ( var i = 0; i < detailVo.length; i++) {
					 //第一个集合是今天的
					 if(0==i){
					 for ( var j = 0; j < detailVo[i].length; j++) {
						 
						 
						var date = detailVo[i][j]
						
						if(typeof(date.cnt)=="undefined"){ 

							date.cnt = 0; 

							} 
						if(typeof(date.name)=="undefined"){ 

							date.name = null; 

							} 
						
						
						todayCnt.push(date.cnt);
						todayName.push(date.name);
					}}					 					 					 
					 //昨天的
					 else if(1==i){
					 for ( var j = 0; j < detailVo[i].length; j++) {						 
						var date = detailVo[i][j]
						if(typeof(date.cnt)=="undefined"){ 

							date.cnt = 0; 

							} 
						if(typeof(date.name)=="undefined"){ 

							date.name = null; 

							} 
						yesterdayCnt.push(date.cnt);
						yesterdayName.push(date.name);						
					}} 
					 //本月的
					 else if(2==i){
						 for ( var j = 0; j < detailVo[i].length; j++) {						 
							var date = detailVo[i][j]
							if(typeof(date.cnt)=="undefined"){ 

								date.cnt = 0; 

								} 
							if(typeof(date.name)=="undefined"){ 

								date.name = null; 

								} 
							thisMonthCnt.push(date.cnt);
							thisMonthName.push(date.name);						
						}} 
					 //上月的
					 else if(3==i){
						 for ( var j = 0; j < detailVo[i].length; j++) {						 
							var date = detailVo[i][j]
							if(typeof(date.cnt)=="undefined"){ 

								date.cnt = 0; 

								} 
							if(typeof(date.name)=="undefined"){ 

								date.name = null; 

								}  
							lastMonthCnt.push(date.cnt);
							lastMonthName.push(date.name);						
						}} 	
					 //日总量
					 else if(4==i){
												 
							var date = detailVo[i]
							if(typeof(date)=="undefined"){ 

								date = 0; 

								} 
							 //今天
							 $("#todayCnt1").html(date);																																																																						
						}
					 //月总量
					 else if(5==i){												 
							var date = detailVo[i]
							if(typeof(date)=="undefined"){ 

								date = 0; 

								} 
							 //本月
							 $("#thisMonthCnt1").html(date);	
					 		} 
					 //总量
					 else if(6==i){						 
							var date = detailVo[i]
							if(typeof(date)=="undefined"){ 

								date = 0; 

								} 
							 //累计
							 $("#totalCnt1").html(date);				
						} 	
				 }		
				
				 if(null != detailVo){						 					 
					 //选择“今日”，x轴是 
					 if(0 == periodType){

						 dateName = todayName;
						 dateCnt = todayCnt;						 
						 ydateName = yesterdayName;
						 ydateCnt = yesterdayCnt;
						 seriesNameCurr = "今日网点使用"
						 seriesNameLast = "昨日网点使用"
					if(todayName.length>yesterdayName.length)								 														 
						{for ( var i = 0; i < todayName.length; i++) {
							
							if(typeof(dateName[i])=="undefined"){ 							
								var name = ydateName[i];} 
							else if(typeof(ydateName[i])=="undefined"){ 							
								var name = dateName[i];}
							else{var name = dateName[i] +  "   \t"  +ydateName[i] ;}
							xAxisData.push(name);												
						}
						} 
					else{
						for ( var i = 0; i < ydateName.length; i++) {
							if(typeof(dateName[i])=="undefined"){ 							
								var name = ydateName[i];} 
							else if(typeof(ydateName[i])=="undefined"){ 							
								var name = dateName[i];}
							else{var name = dateName[i] +  "   \t"  +ydateName[i] ;}
							xAxisData.push(name);											
						}						
					}						 												 						 						 						 						 						 
					 } else if(1 == periodType){//选择“本月”						 
						 seriesNameCurr = "本月网点使用";
						 seriesNameLast = "上月网点使用";
						 dateName = thisMonthName;
						 dateCnt = thisMonthCnt;
						 ydateName = lastMonthName;
						 ydateCnt = lastMonthCnt;
							if(dateName.length>ydateName.length)								 														 
							{for ( var i = 0; i < dateName.length; i++) {
							if(typeof(dateName[i])=="undefined"){ 							
								var name = ydateName[i];} 
							else if(typeof(ydateName[i])=="undefined"){ 							
								var name = dateName[i];}
							else {var name = dateName[i] +  "   \t"  +ydateName[i] ;}
							xAxisData.push(name);					
							}
							} 
						else{
							for ( var i = 0; i < ydateName.length; i++) {
								if(typeof(dateName[i])=="undefined"){ 							
									var name = ydateName[i];} 
								else if(typeof(ydateName[i])=="undefined"){ 							
									var name = dateName[i];}
								else{var name = dateName[i] +  "   \t"  +ydateName[i] ;}
								xAxisData.push(name);											
							}							
						}		 						 						 											 						 
					 } 					 					 
				 }				 
				 histogramOption("clshistogram", "", seriesNameCurr, seriesNameLast, 
						 seriesCntDataCurr, seriesCntDataLast, xAxisData) ;
			 }
		}
		function initHistogram(){
			setPeriodType1(0);//默认统计今天和昨天的
	    	//queryRentDatas1(null, null);
	    	sethistogramOptions(0);	    	
	    	selectCombobox1();
		}
		
/**
 * 
 */		
		
//今天/本月、昨天/上月的数据的折线图
			function histogramOption(id, text, seriesNameCurr, seriesNameLast, 
					seriesDataCurr, seriesDataLast, xAxisData){
				////debugger ;
				require(
						[
						'echarts',
						'echarts/chart/bar'
						],
						function(ec){
							myChart=ec.init(document.getElementById('clshistogram'));
					 	//今日(本月)和昨日(上月)的比较
						    var optionTest = {
						    	    tooltip : {
						    	        trigger: 'axis'
						    	    },
						    	    legend: {
						    	        data:[seriesNameCurr,seriesNameLast]
						    	    },
						    	    calculable : true,
						    	    xAxis : [
						    	        {
						    	            type : 'category',
						    	            data : xAxisData 
						    	        }
						    	    ],
						    	    yAxis : [
						    	        {
						    	            type : 'value'
						    	            	 
						    	        }
						    	    ],
						    	    series : [
						    	        {
						    	            name:seriesNameCurr,
						    	            type:'bar',
						    	            data:dateCnt,
						    	            markPoint : {
						    	                data : [
						    	                    {type : 'max', name: '最大值'},
						    	                    {type : 'min', name: '最小值'}
						    	                ]
						    	            },
						    	            markLine : {
						    	                data : [
						    	                    {type : 'average', name: '平均值'}
						    	                ]
						    	            }
						    	        },
						    	        {
						    	            name:seriesNameLast,
						    	            type:'bar',
						    	            data:ydateCnt,
						    	            markPoint : {
						    	                data : [
						    	                    {type : 'max', name: '最大值'},
						    	                    {type : 'min', name: '最小值'}
						    	                ]
						    	            },
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
			       		 myChart.setOption(optionTest, true);
					 	}
					 );
			}
		    	                    