// 路径配置
        require.config({
            paths: {
                echarts: 'echarts/core/www/dist'
            }
        });
        /**地图数据公用option*/

 ////debugger;    
var names = "全国";
var provinceCarNums;//省份车辆数       
var provinceParkNums;
/*var cityCode ='00';*/
var levelIdentifier ='china';
var level = 1 ;
var cityName=$("#sessionCityName").val();
var sessionCityCode=$("#sessionCityCode").val();//获取保存用户登陆cityCode信息
var permission = sessionCityCode=='00'?"all":"part";//如果cityCode为00 则表示拥有查看全国统计的权限（代号all），反之只有查看城市统计的权限(代号part)
var cityMap = {
	    "北京市": "110100",
	    "天津市": "120100",
	    "上海市": "310100",
	    "重庆市": "500100",
	    
	    "崇明县": "310200",            
	    "湖北省直辖县市": "429000",       
	    "铜仁市": "522200",            
	    "毕节市": "522400",            
	    
	    "石家庄市": "130100",
	    "唐山市": "130200",
	    "秦皇岛市": "130300",
	    "邯郸市": "130400",
	    "邢台市": "130500",
	    "保定市": "130600",
	    "张家口市": "130700",
	    "承德市": "130800",
	    "沧州市": "130900",
	    "廊坊市": "131000",
	    "衡水市": "131100",
	    "太原市": "140100",
	    "大同市": "140200",
	    "阳泉市": "140300",
	    "长治市": "140400",
	    "晋城市": "140500",
	    "朔州市": "140600",
	    "晋中市": "140700",
	    "运城市": "140800",
	    "忻州市": "140900",
	    "临汾市": "141000",
	    "吕梁市": "141100",
	    "呼和浩特市": "150100",
	    "包头市": "150200",
	    "乌海市": "150300",
	    "赤峰市": "150400",
	    "通辽市": "150500",
	    "鄂尔多斯市": "150600",
	    "呼伦贝尔市": "150700",
	    "巴彦淖尔市": "150800",
	    "乌兰察布市": "150900",
	    "兴安盟": "152200",
	    "锡林郭勒盟": "152500",
	    "阿拉善盟": "152900",
	    "沈阳市": "210100",
	    "大连市": "210200",
	    "鞍山市": "210300",
	    "抚顺市": "210400",
	    "本溪市": "210500",
	    "丹东市": "210600",
	    "锦州市": "210700",
	    "营口市": "210800",
	    "阜新市": "210900",
	    "辽阳市": "211000",
	    "盘锦市": "211100",
	    "铁岭市": "211200",
	    "朝阳市": "211300",
	    "葫芦岛市": "211400",
	    "长春市": "220100",
	    "吉林市": "220200",
	    "四平市": "220300",
	    "辽源市": "220400",
	    "通化市": "220500",
	    "白山市": "220600",
	    "松原市": "220700",
	    "白城市": "220800",
	    "延边朝鲜族自治州": "222400",
	    "哈尔滨市": "230100",
	    "齐齐哈尔市": "230200",
	    "鸡西市": "230300",
	    "鹤岗市": "230400",
	    "双鸭山市": "230500",
	    "大庆市": "230600",
	    "伊春市": "230700",
	    "佳木斯市": "230800",
	    "七台河市": "230900",
	    "牡丹江市": "231000",
	    "黑河市": "231100",
	    "绥化市": "231200",
	    "大兴安岭地区": "232700",
	    "南京市": "320100",
	    "无锡市": "320200",
	    "徐州市": "320300",
	    "常州市": "320400",
	    "苏州市": "320500",
	    "南通市": "320600",
	    "连云港市": "320700",
	    "淮安市": "320800",
	    "盐城市": "320900",
	    "扬州市": "321000",
	    "镇江市": "321100",
	    "泰州市": "321200",
	    "宿迁市": "321300",
	    "杭州市": "330100",
	    "宁波市": "330200",
	    "温州市": "330300",
	    "嘉兴市": "330400",
	    "湖州市": "330500",
	    "绍兴市": "330600",
	    "金华市": "330700",
	    "衢州市": "330800",
	    "舟山市": "330900",
	    "台州市": "331000",
	    "丽水市": "331100",
	    "合肥市": "340100",
	    "芜湖市": "340200",
	    "蚌埠市": "340300",
	    "淮南市": "340400",
	    "马鞍山市": "340500",
	    "淮北市": "340600",
	    "铜陵市": "340700",
	    "安庆市": "340800",
	    "黄山市": "341000",
	    "滁州市": "341100",
	    "阜阳市": "341200",
	    "宿州市": "341300",
	    "六安市": "341500",
	    "亳州市": "341600",
	    "池州市": "341700",
	    "宣城市": "341800",
	    "福州市": "350100",
	    "厦门市": "350200",
	    "莆田市": "350300",
	    "三明市": "350400",
	    "泉州市": "350500",
	    "漳州市": "350600",
	    "南平市": "350700",
	    "龙岩市": "350800",
	    "宁德市": "350900",
	    "南昌市": "360100",
	    "景德镇市": "360200",
	    "萍乡市": "360300",
	    "九江市": "360400",
	    "新余市": "360500",
	    "鹰潭市": "360600",
	    "赣州市": "360700",
	    "吉安市": "360800",
	    "宜春市": "360900",
	    "抚州市": "361000",
	    "上饶市": "361100",
	    "济南市": "370100",
	    "青岛市": "370200",
	    "淄博市": "370300",
	    "枣庄市": "370400",
	    "东营市": "370500",
	    "烟台市": "370600",
	    "潍坊市": "370700",
	    "济宁市": "370800",
	    "泰安市": "370900",
	    "威海市": "371000",
	    "日照市": "371100",
	    "莱芜市": "371200",
	    "临沂市": "371300",
	    "德州市": "371400",
	    "聊城市": "371500",
	    "滨州市": "371600",
	    "菏泽市": "371700",
	    "郑州市": "410100",
	    "开封市": "410200",
	    "洛阳市": "410300",
	    "平顶山市": "410400",
	    "安阳市": "410500",
	    "鹤壁市": "410600",
	    "新乡市": "410700",
	    "焦作市": "410800",
	    "濮阳市": "410900",
	    "许昌市": "411000",
	    "漯河市": "411100",
	    "三门峡市": "411200",
	    "南阳市": "411300",
	    "商丘市": "411400",
	    "信阳市": "411500",
	    "周口市": "411600",
	    "驻马店市": "411700",
	    "省直辖县级行政区划": "469000",
	    "武汉市": "420100",
	    "黄石市": "420200",
	    "十堰市": "420300",
	    "宜昌市": "420500",
	    "襄阳市": "420600",
	    "鄂州市": "420700",
	    "荆门市": "420800",
	    "孝感市": "420900",
	    "荆州市": "421000",
	    "黄冈市": "421100",
	    "咸宁市": "421200",
	    "随州市": "421300",
	    "恩施土家族苗族自治州": "422800",
	    "长沙市": "430100",
	    "株洲市": "430200",
	    "湘潭市": "430300",
	    "衡阳市": "430400",
	    "邵阳市": "430500",
	    "岳阳市": "430600",
	    "常德市": "430700",
	    "张家界市": "430800",
	    "益阳市": "430900",
	    "郴州市": "431000",
	    "永州市": "431100",
	    "怀化市": "431200",
	    "娄底市": "431300",
	    "湘西土家族苗族自治州": "433100",
	    "广州市": "440100",
	    "韶关市": "440200",
	    "深圳市": "440300",
	    "珠海市": "440400",
	    "汕头市": "440500",
	    "佛山市": "440600",
	    "江门市": "440700",
	    "湛江市": "440800",
	    "茂名市": "440900",
	    "肇庆市": "441200",
	    "惠州市": "441300",
	    "梅州市": "441400",
	    "汕尾市": "441500",
	    "河源市": "441600",
	    "阳江市": "441700",
	    "清远市": "441800",
	    "东莞市": "441900",
	    "中山市": "442000",
	    "潮州市": "445100",
	    "揭阳市": "445200",
	    "云浮市": "445300",
	    "南宁市": "450100",
	    "柳州市": "450200",
	    "桂林市": "450300",
	    "梧州市": "450400",
	    "北海市": "450500",
	    "防城港市": "450600",
	    "钦州市": "450700",
	    "贵港市": "450800",
	    "玉林市": "450900",
	    "百色市": "451000",
	    "贺州市": "451100",
	    "河池市": "451200",
	    "来宾市": "451300",
	    "崇左市": "451400",
	    "海口市": "460100",
	    "三亚市": "460200",
	    "三沙市": "460300",
	    "成都市": "510100",
	    "自贡市": "510300",
	    "攀枝花市": "510400",
	    "泸州市": "510500",
	    "德阳市": "510600",
	    "绵阳市": "510700",
	    "广元市": "510800",
	    "遂宁市": "510900",
	    "内江市": "511000",
	    "乐山市": "511100",
	    "南充市": "511300",
	    "眉山市": "511400",
	    "宜宾市": "511500",
	    "广安市": "511600",
	    "达州市": "511700",
	    "雅安市": "511800",
	    "巴中市": "511900",
	    "资阳市": "512000",
	    "阿坝藏族羌族自治州": "513200",
	    "甘孜藏族自治州": "513300",
	    "凉山彝族自治州": "513400",
	    "贵阳市": "520100",
	    "六盘水市": "520200",
	    "遵义市": "520300",
	    "安顺市": "520400",
	    "黔西南布依族苗族自治州": "522300",
	    "黔东南苗族侗族自治州": "522600",
	    "黔南布依族苗族自治州": "522700",
	    "昆明市": "530100",
	    "曲靖市": "530300",
	    "玉溪市": "530400",
	    "保山市": "530500",
	    "昭通市": "530600",
	    "丽江市": "530700",
	    "普洱市": "530800",
	    "临沧市": "530900",
	    "楚雄彝族自治州": "532300",
	    "红河哈尼族彝族自治州": "532500",
	    "文山壮族苗族自治州": "532600",
	    "西双版纳傣族自治州": "532800",
	    "大理白族自治州": "532900",
	    "德宏傣族景颇族自治州": "533100",
	    "怒江傈僳族自治州": "533300",
	    "迪庆藏族自治州": "533400",
	    "拉萨市": "540100",
	    "昌都地区": "542100",
	    "山南地区": "542200",
	    "日喀则地区": "542300",
	    "那曲地区": "542400",
	    "阿里地区": "542500",
	    "林芝地区": "542600",
	    "西安市": "610100",
	    "铜川市": "610200",
	    "宝鸡市": "610300",
	    "咸阳市": "610400",
	    "渭南市": "610500",
	    "延安市": "610600",
	    "汉中市": "610700",
	    "榆林市": "610800",
	    "安康市": "610900",
	    "商洛市": "611000",
	    "兰州市": "620100",
	    "嘉峪关市": "620200",
	    "金昌市": "620300",
	    "白银市": "620400",
	    "天水市": "620500",
	    "武威市": "620600",
	    "张掖市": "620700",
	    "平凉市": "620800",
	    "酒泉市": "620900",
	    "庆阳市": "621000",
	    "定西市": "621100",
	    "陇南市": "621200",
	    "临夏回族自治州": "622900",
	    "甘南藏族自治州": "623000",
	    "西宁市": "630100",
	    "海东地区": "632100",
	    "海北藏族自治州": "632200",
	    "黄南藏族自治州": "632300",
	    "海南藏族自治州": "632500",
	    "果洛藏族自治州": "632600",
	    "玉树藏族自治州": "632700",
	    "海西蒙古族藏族自治州": "632800",
	    "银川市": "640100",
	    "石嘴山市": "640200",
	    "吴忠市": "640300",
	    "固原市": "640400",
	    "中卫市": "640500",
	    "乌鲁木齐市": "650100",
	    "克拉玛依市": "650200",
	    "吐鲁番地区": "652100",
	    "哈密地区": "652200",
	    "昌吉回族自治州": "652300",
	    "博尔塔拉蒙古自治州": "652700",
	    "巴音郭楞蒙古自治州": "652800",
	    "阿克苏地区": "652900",
	    "克孜勒苏柯尔克孜自治州": "653000",
	    "喀什地区": "653100",
	    "和田地区": "653200",
	    "伊犁哈萨克自治州": "654000",
	    "塔城地区": "654200",
	    "阿勒泰地区": "654300",
	    "自治区直辖县级行政区划": "659000",
	    "台湾省": "710000",
	    "香港特别行政区": "810100",
	    "澳门特别行政区": "820000"
	};
function mapOption(seriesData){
               	option = { 
               			
               		    tooltip : {
               		         trigger: 'item' ,
               	        	 formatter: function(params) {
               	        		 
               	                 var res = params.name+':'+ params.value;
               	                 return res;
               	                
               	           }
               		    },
                 		backgroundColor:{},
               		    legend: {
               		        orient: 'vertical',  
               		        x:'right',
               		        data:[/* '随机数据' */]
               		    },
               		    dataRange: {
               		        min: 0,
               		        max: 1000,
               		        color:['aquamarine','aquamarine'],//forestgreen
               		        text:['高','低'],           // 文本，默认为数值文本
               		        calculable : true
               		    },
               		    series : [
               		        {
               		            name: '车辆统计',
               		            type: 'map',  
               		            mapType: 'china',
               		            selectedMode : 'single',
               		            itemStyle:{
               		                normal:{label:{show:true}, 
               		                borderColor: '#247ea2',
               	                    borderWidth: 1,
               	                   
               	                    areaStyle: {
               	                    	label:{show:false},
               	                        color: '#b2e2f5'
               	                     }
               	                    },
               		                emphasis:{
               		                	color:'forestgreen',//aquamarine
               		                	borderWidth:1,
               		                	borderColor:'#fff',
               		                	label: {
               		                        show: true,
               		                        textStyle: {
               		                            color: 'black'
               		                        }
               		                    }
               		                },
               		            },
               		            data:seriesData
               		        }
               		    ]
               		};	   	
}   
//饼型option封装(公用)
function pieOption(text,legendData,seriesData){
	var option6 = {
		    title : {
		        x:'center',
		        subtextStyle:{color:"#3baae3"}
		    },
		    backgroundColor:{},
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:legendData
		    },
		    toolbox: {
		        show : false,
		        orient : 'vertical',
		        x: 'right',
		        y: 'center',
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:text,
		            type:'pie',
		            radius : '55%',
		            center: ['50%', 225],
		            data:seriesData
		        }
		    ]
		};
	return option6;
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
      //中部 左侧饼状图(车辆状态 ：权限为省份)
        function loadMiddleLeftProvince(){        
            require(
        		 	[
    	                'echarts',
    	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
            		],
        		 	function(ec){
        		 		$.post("carStatus_province_list_statistics_sum?cityCode="+cityCode, function(data){
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
 	    		 		$.post("carType_list_statistics_sum?cityCode="+cityCode, function(data){
 	    		 			
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
        
      //中部 左侧饼状图(车辆类型 --按照省份划分)
        function loadProvinceMiddleLeftByCarType(){  
 		   require(
 	    		 	[
 		                'echarts',
 		                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
 	        		],
 	    		 	function(ec){
 	    		 		$.post("provinceCarType_list_statistics_sum?cityCode="+cityCode, function(data){
 	    		 			
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
        
       
      //中部 右侧饼状图(车辆类型--按照全国、地级市划分)
      function loadMiddleRight(){
        require(
    		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
        		],
    		 	function(ec){
    		 		$.post("carType_list_statistics_run?cityCode="+cityCode, function(data){
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

      /**网点页面的function()*/
	
      
	  
      
      
	   
	 //中部 右侧饼状图(充电枪类型--按照省份划分)
	   function loadProvinceMiddleRightByConnectorType(){
		  
	   require(
   		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
       		],
   		 	function(ec){
   		 		
   		 		$.post("queryAllProvinceParkCarportByConnectorType?cityCode="+cityCode, function(data){
   		 			////debugger;
   		 			var park = $.parseJSON( data ); 
   		 			var arrays = new Array();
   		 			var myChart= ec.init(document.getElementById('middle-right-map2'));
   		 				for(var i=0;i<park.length;i++){
   		 					var a =park[i].connectorTotal;
   		 				     arrays.push(a);
   		 				}
   		 				var emptyCarPort =parkNumTotal-arrays[0]-arrays[1];
   		 				pieLegendData = ['快充','慢充','空闲'] ;
   		    			pieSeriesData = [{value:arrays[0],name:'慢充'},
   		    			                 {value:arrays[1],name:'快充'},
   		    			                 {value:emptyCarPort,name:'空闲'}
   		    			                 ];	
		    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("充电枪类型",pieLegendData,pieSeriesData),true);
   		 		});
   		 	}
   		 );
	   }
	 //中部 右侧饼状图(车辆类型)
	   function loadMiddleRightByCarType(){
		  
	   require(
   		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
       		],
   		 	function(ec){
   		 		
   		 		$.post("queryAllParkCarportByCarType?cityCode="+cityCode, function(data){
   		 			////debugger;
   		 			var park = $.parseJSON( data ); 
   		 			var totalarrays = new Array();
   		 			var myChart= ec.init(document.getElementById('middle-right-map3'));
   		 				for(var i=0;i<park.length;i++){
   		 					var a =park[i].brandTotal;
   		 				      totalarrays.push(a);
   		 				}
   		 				if(totalarrays.length==0){
  		 				 totalarrays.push(0);
  		 			     totalarrays.push(0);
  		 				}
  		 				if(totalarrays.length==1){
 		 				 totalarrays.push(0);
 		 				}
   		 				var emptyCarPort =parkNumTotal-totalarrays[0]-totalarrays[1]-totalarrays[2];
   		 				pieLegendData = ['众泰','北汽','奇瑞'/*,'空闲'*/] ;
   		    			pieSeriesData = [{value:totalarrays[0],name:'众泰'},
   		    			                 {value:totalarrays[1],name:'北汽'},
   		    			                 {value:totalarrays[2],name:'奇瑞'}
   		    			                /* {value:emptyCarPort,name:'空闲'}*/
   		    			                 ];	
		    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("车辆类型",pieLegendData,pieSeriesData),true);
   		 		});
   		 	}
   		 );
	   }
	   
	 //中部 右侧饼状图(车辆类型:按照省份划分)
	   function loadProvinceMiddleRightByCarType(){
		  
	   require(
   		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
       		],
   		 	function(ec){
   		 		
   		 		$.post("queryAllProvinceParkCarportByCarType?cityCode="+cityCode, function(data){
   		 			////debugger;
   		 			var park = $.parseJSON( data ); 
   		 			var totalarrays = new Array();
   		 			var myChart= ec.init(document.getElementById('middle-right-map3'));
   		 				for(var i=0;i<park.length;i++){
   		 					var a =park[i].brandTotal;
   		 				      totalarrays.push(a);
   		 				}
   		 				if(totalarrays.length==0){
   		 				 totalarrays.push(0);
   		 			     totalarrays.push(0);
   		 				}
   		 				if(totalarrays.length==1){
  		 				 totalarrays.push(0);
  		 				}
   		 				var emptyCarPort =parkNumTotal-totalarrays[0]-totalarrays[1];
   		 				pieLegendData = ['北汽','奇瑞','空闲'] ;
   		    			pieSeriesData = [{value:totalarrays[0],name:'北汽'},
   		    			                 {value:totalarrays[1],name:'奇瑞'},
   		    			                 {value:emptyCarPort,name:'空闲'}
   		    			                 ];	
		    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("车辆类型",pieLegendData,pieSeriesData),true);
   		 		});
   		 	}
   		 );
	   }
 
  
  
  
   
   
   
   
	 var parkNumTotal; 
	 var statisticsTypeValue;
	 var value1;
	 var value2;
	 var value3;
	 var carTotal;//车辆总数
	 var cityCarNums;//城市车辆总数
	 var cityParkNums;
	 var websiteTotal;//全国网点总数
	 var memberTotals;//全国会员总数
	 var rentCarNumber;
	 var orderTotal;
	 var timeTotal;
	 var moneyTotal;
	 function loadTextData(value1,value2,value3,value4){
		 $("#sp12").html(value1);
		 $("#sp13").html(value2);
		 $("#sp14").html(value3);
		 $("#sp21").html(value4);
	 }
	 /**加载省级车辆总数信息*/
	 function loadProvinceCarSum(names,array){
		 $.ajax({  
		           type : "post",  
				   url : "province_area_map_statistics?names="+names,  
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
	 
     
     /**获取数据库cityCode权限对应的网点车位总数信息--按照省份划分*/
     function loadProvinceParkCarNums(){
    
	     $.ajax({  
	           type : "post",  
	            url : "queryAllProvinceParkCarport?cityCode="+cityCode,  
	            async : false,   //采用同步
	            success : function(data){  
	           	 var parkVo = $.parseJSON( data ); 
	            	parkNumTotal =parkVo[0].total;    	
	            }  
	     });
	 
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
  
   
   /**此function用来将数据按国标的方法展示*/
   function cg(num){
	   var result= 0;
	   var dec="";
	   if (isNaN(num)){
	       result="0";
	   }
	   else{
		   if(num.length<4){
			   result=num;
		   }else{
			   pos=num.indexOf(".",1);
			   if (pos>0){
				   dec=num.substr(pos);   //小数部分的字符串，包括小数点
				   res=num.substr(0,pos);
			   }else{
				   res=num;
			   }
			   var tempResult="";
			   for(i=res.length;i>0;i-=3){
				   if(i-3>0){
					   tempResult=","+res.substr(i-3,3)+tempResult;
				   }else{
					   tempResult=res.substr(0,i)+tempResult;
				   }
			   }
			   result=tempResult+dec;
		   }
	   }
	   return result;
   }
   
   /**
	 * 获取year-month对应的月份的最后一天的最后时间
	 * @param year
	 * @param month
	 * @returns
	 */
	function getLastDay(year, month) {
		var newYear = year; // 取当前的年份
		var nextMonth = month++;// 取下一个月的第一天，方便计算（最后一天不固定）
		if (month > 12){ // 如果当前大于12月，则年份转到下一年
			nextMonth -= 12; // 月份减
			newYear++; // 年份增
		}
		var newDate = new Date(newYear, nextMonth, 1); // 取当年当月的下一个月的第一天
//		var dateCount = (new Date(newDate.getTime() - 1000 * 60 * 60 * 24))
//				.getDate();// 获取当月的天数
		var lastDate = new Date(newDate.getTime() - 1000);//减去一秒， 获得当月最后一天的日期
		return lastDate;
	}   
	
   
   