// 路径配置
        require.config({
            paths: {
                echarts: 'echarts/core/www/dist'
            }
        });
        /**地图数据公用option*/

 ////debugger; 
var cityCode ;
var pileNumTotal;//桩数量统计
var provincePileNums;//
var connectorNumTotal;
var provinceConnectorNumTotal;
var levelIdentifier ='china';
var level = 1 ;
var sessionCityCode=$("#sessionCityCode").val();//获取保存用户登陆cityCode信息
var permission = sessionCityCode=='00'?"all":"part";//如果cityCode为00 则表示拥有查看全国统计的权限（代号all），反之只有查看城市统计的权限(代号part)
//统计维度：次数、人数、时长、里程、收入
function setCountType2(countType){
	$("#MemberCountType").val(countType);
}

//选中“今天”或“本月”
function setPeriodType2(periodType) {
	$("#period_Type2").val(periodType);
}
/**获取数据库中全国、城市充电桩数量*/
function loadPileTotal(){
	$.ajax({  
           type : "post",  
            url : "pile_nums_statistics?cityCode="+cityCode,  
            async : false,   //采用同步
            success : function(data){  
            var memberTotalVo = $.parseJSON( data ); 
            pileNumTotal =memberTotalVo[0].pileNums;
            }  
    });
} 
/**统计省份充电桩个数*/
function loadProvincePileTotal(){
	$.ajax({  
           type : "post",  
            url : "province_pile_nums_statistics?cityCode="+cityCode,  
            async : false,   //采用同步
            success : function(data){  
            var memberTotalVo = $.parseJSON( data ); 
            provincePileNums =memberTotalVo[0].pileNums;
            }  
    });
} 
/**统计全国，城市充电枪个数*/
function loadConnectorTotal(){
	$.ajax({  
           type : "post",  
            url : "connector_nums_statistics?cityCode="+cityCode,  
            async : false,   //采用同步
            success : function(data){  
            var memberTotalVo = $.parseJSON( data ); 
            connectorNumTotal =memberTotalVo[0].pileNums;
            }  
    });
} 
/**统计省份充电枪个数*/
function loadProvinceConnectorTotal(){
	$.ajax({  
           type : "post",  
            url : "province_conncetor_nums_statistics?cityCode="+cityCode,  
            async : false,   //采用同步
            success : function(data){  
            var memberTotalVo = $.parseJSON( data ); 
            provinceConnectorNumTotal =memberTotalVo[0].pileNums;
            }  
    });
} 

/**加载省级充电桩数量信息*/
function loadProvincePileNums(array){
	 $.ajax({  
           type : "post",  
		   url : "province_pileTotal_statistics",  
		   async : false,   //采用同步
	       success : function(data){  
	      	   var pileTotalVo = $.parseJSON( data ); 
	       	   for(var i = 0 ; i < pileTotalVo.length ; i ++ ) {
	       		var obj = {name:pileTotalVo[i].name,value:pileTotalVo[i].pileNums,cityCode:pileTotalVo[i].code};
	       		array.push(obj) ;
	       	   }      
           }  
    }); 
}
/**加载左侧 全国和城市桩类型划分*/
function loadMiddleLeftByPileType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("city_pileType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(cityCode);
		 			var myChart= ec.init(document.getElementById('middle-left-map1'));
		 			pieLegendData = ['快充','慢充','快慢充一体'];
		 			var type1Nums;
		 			var type2Nums;
		 			var type3Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="1"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="2"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="3"){
		 					type3Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'快充'},
	    			                 {value:type2Nums,name:'慢充'},
	    			                 {value:type3Nums,name:'快慢充一体'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("充电桩类型",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }
/**加载左侧 省份桩类型划分*/
function loadProvinceMiddleLeftByPileType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("province_pileType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-left-map1'));
		 			pieLegendData = ['快充','慢充','快慢充一体'];
		 			var type1Nums;
		 			var type2Nums;
		 			var type3Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="1"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="2"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="3"){
		 					type3Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'快充'},
	    			                 {value:type2Nums,name:'慢充'},
	    			                 {value:type3Nums,name:'快慢充一体'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("充电桩类型",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }



/**加载左侧 全国和城市桩服务类型划分*/
function loadMiddleLeftByPileServiceType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("city_pileServiceType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-left-map2'));
		 			pieLegendData = ['自有','合作'];
		 			var type1Nums;
		 			var type2Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="0"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="1"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'自有'},
	    			                 {value:type2Nums,name:'合作'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("服务类型",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }
/**加载左侧 省桩服务类型划分*/
function loadProvinceMiddleLeftByPileServiceType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("province_pileServiceType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-left-map2'));
		 			pieLegendData = ['自有','合作'];
		 			var type1Nums;
		 			var type2Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="0"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="1"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'自有'},
	    			                 {value:type2Nums,name:'合作'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("服务类型",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }

/**加载右侧 全国和城市枪类型划分*/
function loadMiddleLeftByConnectorType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("city_conncetorType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-right-map1'));
		 			pieLegendData = ['慢充','快充'];
		 			var type1Nums;
		 			var type2Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="3"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="4"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'慢充'},
	    			                 {value:type2Nums,name:'快充'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("枪类型",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }
/**加载右侧 省枪类型划分*/
function loadProvinceMiddleLeftByConnectorType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("province_conncetorType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-right-map1'));
		 			pieLegendData = ['慢充','快充'];
		 			var type1Nums;
		 			var type2Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="3"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="4"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'慢充'},
	    			                 {value:type2Nums,name:'快充'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("枪类型",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }

/**加载右侧 全国和城市枪类型划分*/
function loadMiddleLeftByConnectorStatusType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("city_conncetorStatusType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-right-map2'));
		 			pieLegendData = ['离网','空闲','占用（未充电）','占用（充电中）','占用（预约锁定）','故障'];
		 			var type1Nums;
		 			var type2Nums;
		 			var type3Nums;
		 			var type4Nums;
		 			var type5Nums;
		 			var type6Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="0"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="1"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="2"){
		 					type3Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="3"){
		 					type4Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="4"){
		 					type5Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="255"){
		 					type6Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'离网'},
	    			                 {value:type2Nums,name:'空闲'},
	    			                 {value:type3Nums,name:'占用（未充电）'},
	    			                 {value:type4Nums,name:'占用（充电中）'},
	    			                 {value:type5Nums,name:'占用（预约锁定）'},
	    			                 {value:type6Nums,name:'故障'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("枪状态",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }




/**加载右侧 省枪类型划分*/
function loadProvinceMiddleLeftByConnectorStatusType(){  
	require(
		 	[
	                'echarts',
	                'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
    		],
		 	function(ec){
		 		$.post("province_conncetorStatusType_statistics?cityCode="+cityCode, function(data){
		 			var carRunNumVo = $.parseJSON( data ); 
		 			console.log(carRunNumVo);
		 			var myChart= ec.init(document.getElementById('middle-right-map2'));
		 			pieLegendData = ['离网','空闲','占用（未充电）','占用（充电中）','占用（预约锁定）','故障'];
		 			var type1Nums;
		 			var type2Nums;
		 			var type3Nums;
		 			var type4Nums;
		 			var type5Nums;
		 			var type6Nums;
		 			for (var i=0;i<carRunNumVo.length;i++)
		 			{
		 				if(carRunNumVo[i].type=="0"){
		 					type1Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="1"){
		 					type2Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="2"){
		 					type3Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="3"){
		 					type4Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="4"){
		 					type5Nums = carRunNumVo[i].pileNums;
		 				}else if(carRunNumVo[i].type=="255"){
		 					type6Nums = carRunNumVo[i].pileNums;
		 				}
		 			}
	    			pieSeriesData = [{value:type1Nums,name:'离网'},
	    			                 {value:type2Nums,name:'空闲'},
	    			                 {value:type3Nums,name:'占用（未充电）'},
	    			                 {value:type4Nums,name:'占用（充电中）'},
	    			                 {value:type5Nums,name:'占用（预约锁定）'},
	    			                 {value:type6Nums,name:'故障'}
	    			                 ];	
	    		 	 // 为echarts对象加载数据 
		           		myChart.setOption(pieOption("枪状态",pieLegendData,pieSeriesData),true);
		 		});
		 	}
		 );
	   }

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

 
var statisticsTypeValue;
var value1;
var value2;
var value3;
var carTotal;//车辆总数
var cityCarNums;//城市车辆总数
var cityParkNums;
var websiteTotal;//全国网点总数
var memberTotals;//全国会员总数
	 
function loadTextData(value1,value2,value3,value4,value5){
		 $("#sp12").html(value1);
		 $("#sp13").html(value2);
		 $("#sp14").html(value3);
		 $("#sp17").html(value4);
		 $("#sp21").html(value5);
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
				            data : {name:provinceName,level:level},
				            async : false,   //采用同步
				            success : function(data){  
				           	 var cityVo = $.parseJSON( data ); 
				            	 for(var i = 0 ; i < 1 ; i ++ ) {
				            		cityCode=cityVo[i].code;
				            	}        	
				            }  
				      });
			           
			        var seriesData  = new Array() ;
			        	/**加载选中省份的城市充电桩数量信息*/
			        	$.ajax({  
					           type : "post",  
					            url : "city_pileTotal_statistics?cityCode="+cityCode,  
					            async : false,   //采用同步
					            success : function(data){  
					           	 var pileTotalVo = $.parseJSON( data ); 
					            	 for(var i = 0 ; i < pileTotalVo.length ; i ++ ) {
					            		var obj = {name:pileTotalVo[i].name,value:pileTotalVo[i].pileNums,cityCode:pileTotalVo[i].code} ;
					            		seriesData.push(obj) ;
					            	}        	
					            }  
					     });
			        	
			        	loadProvincePileTotal();
			        	loadProvinceConnectorTotal();
			        	value1=provinceName+"充电桩统计";
			 	        value2="充电桩总数:"+cg(provincePileNums.toString())+"个";
			 	        value3=provinceName+"充电桩总数("+cg(provincePileNums.toString())+")个";
			 	        value4=provinceName+"充电枪总数("+cg(provinceConnectorNumTotal.toString()   )+")个";
			 	        value5=provinceName+"充电桩统计趋势图";
			 	        loadTextData(value1,value2,value3,value4,value5);
					     mapOption(seriesData) ;
				         option.series[0].mapType = mt;
					     myChart.setOption(option,true);
					     
					     loadProvinceMiddleLeftByPileType();

					     loadProvinceMiddleLeftByPileServiceType();

					     loadProvinceMiddleLeftByConnectorType();

					     loadProvinceMiddleLeftByConnectorStatusType();
					     
				    
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
			   				            data:{name:cityName,level:level},
			   				            async : false,   //采用同步
			   				            success : function(data){  
			   				           	 var cityVo = $.parseJSON( data ); 
			   				            	 for(var i = 0 ; i < 1; i ++ ) {
			   				            		cityCode=cityVo[i].code;
			   				            	}        	
			   				            }  
			   				      });
				   			        /**获取数据库中全国地区车辆信息*/
					       			    $.ajax({  
					       			           type : "post",  
					       					   url : "area_pileTotal_statistics?cityCode="+cityCode,  
					       					   async : false,   //采用同步
					       				       success : function(data){  
					       				      	   var cityVo = $.parseJSON( data ); 
					       				       	   for(var i = 0 ; i < cityVo.length ; i ++ ) {
					       				       		var obj = {name:cityVo[i].name,value:cityVo[i].pileNums} ;
					       				       	       seriesData2.push(obj) ;
					       				       	   }      
					       			           }  
					       			    });
			            		       mapOption(seriesData2);
					            	   option.series[0].mapType = mt;
					            	   myChart.setOption(option, true); 
					            	   loadMiddleLeftByPileType();
						               loadMiddleLeftByPileServiceType();
						               loadMiddleLeftByConnectorType();
						               loadMiddleLeftByConnectorStatusType();
						                loadPileTotal();
										loadConnectorTotal();
						                value1=cityName+"充电桩统计";
							 	        value2="充电桩总数:"+cg(pileNumTotal.toString())+"个";
							 	        value3=cityName+"充电桩总数("+cg(pileNumTotal.toString())+")个";
							 	        value4=cityName+"充电枪总数("+cg(connectorNumTotal.toString()   )+")个";
							 	        value5=cityName+"充电桩统计趋势图";
							 	        loadTextData(value1,value2,value3,value4,value5);
			            		 
			            }
			    	}
			    	
			  }else if(level == 4){
				    levelIdentifier='area';
				    var cityMaps = param.selected;
				    	level = 1 ;
				    	curIndx = 0;
				        mt = 'china';  
			        	cityCode='00';
			        	var seriesData8  = new Array() ;
			        	loadProvincePileNums(seriesData8);
					    mapOption(seriesData8);
				        option.series[0].mapType = mt;
				        myChart.setOption(option, true);
				        /**加载饼状图*/
		                loadMiddleLeftByPileType();
		                loadMiddleLeftByPileServiceType();
		                loadMiddleLeftByConnectorType();
		                loadMiddleLeftByConnectorStatusType();
		                /**加载数据信息*/
						loadPileTotal();
						loadConnectorTotal();
					    value1="全国充电桩统计";
				        value2="充电桩总数:"+cg(pileNumTotal.toString())+"个";
				        value3="全国充电桩总数("+cg(pileNumTotal.toString()   )+")个";
				        value4="全国充电枪总数("+cg(connectorNumTotal.toString()   )+")个";
				        value5="全国充电桩统计趋势图"
				        loadTextData(value1,value2,value3,value4,value5);

			    }     
		  } 
			    $("#hidMemberCityCode").val(cityCode);
				 $("#hidMemberLayer").val(level);
				 setPeriodType2(0);// 默认统计今天和昨天的
				 setCountType2(1);//默认统计注册会员数
				 selectPileCombobox();
				 setPileLineOptions(0);
		}	
			  	
			}); 
				if(permission=="all"){//如果登陆用户的cityCode为oo显示全国的地图信息
					/**加载数据信息*/
					loadPileTotal();
					loadConnectorTotal();
				   value1="全国充电桩统计";
			       value2="充电桩总数:"+cg(pileNumTotal.toString())+"个";
			       value3="全国充电桩总数("+cg(pileNumTotal.toString()   )+")个";
			       value4="全国充电枪总数("+cg(connectorNumTotal.toString()   )+")个";
			       value5="全国充电桩统计趋势图"
			       loadTextData(value1,value2,value3,value4,value5);
				    /**加载地图信息*/
	   				var seriesDatas9 = new Array();
	   				loadProvincePileNums(seriesDatas9);
				    mapOption(seriesDatas9);
				    option.series[0].mapType = mt;
				   // 为echarts对象加载数据 \
	                myChart.setOption(option,true);
	                /**加载饼状图*/
	                loadMiddleLeftByPileType();
	                loadMiddleLeftByPileServiceType();
	                loadMiddleLeftByConnectorType();
	                loadMiddleLeftByConnectorStatusType();
	                setCountType2(1);
	                setPileLineOptions(0);
	                selectPileCombobox();
				}else if(permission=="part"){
					
				}   
              
           }
       );
    }
    
    
    function setPileLineOptions(periodType){
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
				$(".cntText_member").html("充电次数");
				$(".unit").html("(次)");
			} else if(2 == countType){
				$(".cntText_member").html("充电人数");
				$(".unit").html("(个)");
			} else if(3 == countType){
				$(".cntText_member").html("充电电度数");
				$(".unit").html("(KWH)");
			} else if(4 == countType){
				$(".cntText_member").html("充电收入");
				$(".unit").html("(元)");
			}
		}
		var check = $("input[name='radio2']:checked").val();
		var memberLevel = null;
		var memberType = null;
		var returnCar = null;
		if(null != check){
			if(1 == check){//会员类型
				memberType = $("#hidMemberType").val();
			} else if(2 == check){
				memberLevel = $("#hidMemberLevel").val();
			}else if(3 == check){
				returnCar = $("#hidOrderType").val();
			} 
		}
		$.post("queryPileCountDatas.do", 
				{
			     "countType": countType,
				 "memberLevel" : memberLevel,
				 "memberType" : memberType,
				 "returnCar" : returnCar,
				 "cityCode" : $("#hidMemberCityCode").val(),
				 "layer" : $("#hidMemberLayer").val()}, 
				 function(data){
					 totalPileDatasCallBack(data);
				 });
		$.post("queryPileCountDetail.do", 
				{
			     "countType": countType, 
				 "periodType": periodType,
				 "memberLevel":memberLevel,
				 "memberType" : memberType,
				 "periodType": periodType,
				 "returnCar": returnCar,
				 "cityCode" : $("#hidMemberCityCode").val(),
				 "layer" : $("#hidMemberLayer").val()
				 }, function(data){
					 pileLineCallBack(periodType, data);
				 });
	}  
    
  //总体统计回调：今天及其与昨天的比率、本月及其与上月的比率、累计
	function totalPileDatasCallBack(data){
		if(null != data && "" != data && "[null]" != data){
			 var statisticsVo = $.parseJSON( data );  
			 if(null != statisticsVo){
				 //今天
				 var tcnt = statisticsVo[0].todayCnt == null ? "0":statisticsVo[0].todayCnt.toString();
				 var ycnt = statisticsVo[0].yesterdayCnt == null ? "0":statisticsVo[0].yesterdayCnt;
				 $("#todayCnt_member").html(cg(tcnt));
				 var a1 =parseInt(tcnt);
				 var a2 =parseInt(ycnt);
				 if(a1==a2){
					 $("#ratioDayCnt_member").css({"color" : "red"});
					 $("#ratioDayCnt_member").html("--" + 0 + " %");
				 }else{
					var  ratioDayCnt = 0 ;
					if(a2 != 0){
					 	ratioDayCnt = (((a1-a2)/a2)*100).toFixed(0);
					}else{
						if(a1>0)
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
				 var tcnt = statisticsVo[0].thisMonthCnt == null ? "0":statisticsVo[0].thisMonthCnt.toString();
				 var lcnt = statisticsVo[0].lastMonthCnt == null ? "0":statisticsVo[0].lastMonthCnt.toString();
				 //本月
				 $("#thisMonthCnt_member").html(cg(tcnt));
				 var ratioMonthCntMember;
				 var a3 =parseInt(tcnt);
				 var a4 =parseInt(lcnt);
				 if(a3==a4){
					 $("#ratioMonthCnt_member").css({"color" : "red"});
					 $("#ratioMonthCnt_member").html("--" + 0 + " %");
				 }else{
					 var ratioMonthCnt  = 0;
					 if(a4 != 0 ){
						  ratioMonthCnt = (((a3-a4)/a4)*100).toFixed(0);
					 }else{
						 if(a3 > 0)
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
				var totCnt = statisticsVo[0].totalCnt == null ?"0":statisticsVo[0].totalCnt.toString(); 
				 //累计
				 $("#totalCnt_member").html(cg(totCnt));
			 }
		}else{
			 $("#todayCnt_member").html(cg("0"));
			 $("#thisMonthCnt_member").html(cg("0"));
			 $("#totalCnt_member").html(cg("0"));
			 $("#ratioDayCnt_member").css({"color" : "red"});
			 $("#ratioDayCnt_member").html("--" + 0 + " %");
			 $("#ratioMonthCnt_member").css({"color" : "red"});
			 $("#ratioMonthCnt_member").html("--" + 0 + " %");
		}
	}
    
	//下拉框的响应函数
	function selectPileCombobox(){
		//统计维度：注册数、正式会员数、充值押金额、充值余额、充值人数
		$("#MemberCountTypeList").combobox({
			onSelect: function(rec){
				var countType = rec.value;
				$("#MemberCountType").val(countType);
				var check = $("input[name='radio2']:checked").val();
				var　memberLevel = null;
				var memberType = null;  
				var returnCar = null;
				if (null != check) {
					if (1 == check) {// 会员类型
						memberType = $("#hidMemberType").val();
					} else if (2 == check) {// 会员等级
						memberLevel = $("#hidMemberLevel").val();
					}else if(3 == check) {
						returnCar = $("#hidOrderType").val();
					}
				}
				//根据不同的统计维度定图片说明区的文字
				if(null != countType){
					if(1 == countType){
						$(".cntText_member").html("充电次数");
						$(".unit").html("(次)");
					} else if(2 == countType){
						$(".cntText_member").html("充电人数");
						$(".unit").html("(个)");
					} else if(3 == countType){
						$(".cntText_member").html("充电电度数");
						$(".unit").html("(KWH)");
					} else if(4 == countType){
						$(".cntText_member").html("充电收入");
						$(".unit").html("(元)");
					}
				}
				$.post("queryPileCountDatas.do", 
						{
						 "countType": countType, 
						 "memberLevel" : memberLevel,
						 "memberType" : memberType,
						 "cityCode" : $("#hidMemberCityCode").val(),
						 "layer" : $("#hidMemberLayer").val()}, 
						 function(data){
							 totalPileDatasCallBack(data);
						 });
				var periodType = $("#period_Type2").val();
				$.post( "queryPileCountDetail.do", 
						{"countType": countType, 
						 "periodType": periodType,
						 "memberLevel" : memberLevel,
						 "memberType" : memberType,
						 "cityCode" : $("#hidMemberCityCode").val(),
						 "layer" : $("#hidMemberLayer").val()}, 
						  function(data){
							 pileLineCallBack(periodType, data);
						 });
			}
		});
		$("#memberTypeList2").combobox({
			onSelect: function(rec){   
				$("#radioMemberType2").prop("checked", true);
				$("#hidMemberType").val(rec.value);
				$("#memberLevelList2").textbox("setValue","");
				$("#memberOrderList2").textbox("setValue","");
				queryRentDatas2(1, rec.value);
				queryRentDetail2(1,rec.value);
	        }
		});
		$("#memberLevelList2").combobox({
			onSelect: function(rec){ 
				$("#radioMemberLevel2").prop("checked", true);
				$("#hidMemberLevel").val(rec.value);
				$("#memberTypeList2").textbox("setValue","");
				$("#memberOrderList2").textbox("setValue","");
				queryRentDatas2(2, rec.value); 
				queryRentDetail2(2,rec.value);
			}
		});
		$("#memberOrderList2").combobox({
			onSelect: function(rec){ 
				$("#radioOrderType2").prop("checked", true);
				$("#hidOrderType").val(rec.value);
				$("#memberLevelList2").textbox("setValue","");
				$("#memberTypeList2").textbox("setValue","");
				queryRentDatas2(3, rec.value); 
				queryRentDetail2(3,rec.value);
			}
		});
	}
	
	function queryRentDatas2(check, value){
		var memberType = null;
		var memberLevel = null;
		var returnCar = null;
		if(check==1){
			memberType=value;
		}else if(check == 2){
			memberLevel=value;
		}else{
			returnCar = value;
		}
		
		//总体统计数据：今天及其与昨天的比率、本月及其与上月的比率、累计
		$.post("queryPileCountDatas.do", 
				{
				 "countType": $("#MemberCountType").val(), 
				 "memberType" : memberType,
				 "memberLevel" : memberLevel,
				 "returnCar" : returnCar,
				 "cityCode" : $("#hidMemberCityCode").val(),
				 "layer" : $("#hidMemberLayer").val()}, 
				 function(data){
					 totalPileDatasCallBack(data);
				 });
		
	}
    
	function queryRentDetail2(check, value){
		var memberType = null;
		var memberLevel = null;
		var returnCar = null;
		if(check==1){
			memberType=value;
		}else if(check == 2){
			memberLevel=value;
		}else{
			returnCar = value;
		}
		var periodType = $("#period_Type2").val();
		$.post("queryPileCountDetail.do", 
				{
			 	 "countType": $("#MemberCountType").val(), 
			 	 "memberType" : memberType,
				 "memberLevel" : memberLevel,
				 "returnCar" : returnCar,
				 "periodType": periodType,
				 "cityCode" : $("#hidCityCode").val(),
				 "layer" : $("#hidLayer").val()}, function(data){
					 pileLineCallBack(periodType, data);
				 });
	}
	function pileLineCallBack(periodType, data){
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
							// var c = currList[i].currDate;
							 seriesCntDataCurr.splice( currList[i].currDate-1,1, currList[i].thisMonthCnt);
						 }			
					 }
					 if(null != lastList){
						 for(var i = 0; i < lastList.length; ++i){
							// var d = currList[i].currDate;
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
		var type = "充电次数";
		if (1 == countType) {// 次数
			type = "充电次数";
		} else if (2 == countType) {// 人数
			type = "充电人数";
		} else if (3 == countType) {// 时长
			type = "充电电度数";
		} else if (4 == countType) {// 里程
			type = "充电收入";
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
	//点击“清空”
	function clearSelMember(){
		$("input[name='radio2']").prop("checked", false);
		$(".easyui-combobox").combobox("clear");
		$("input[type='hidden']").val(null);
		$("input[name='radio2']").val(null);
		$("#MemberCountType").val(1);
		$("#hidMemberCityCode").val(cityCode);
		$("#hidMemberLayer").val(level);
		setPileLineOptions(0);
	}
$(function(){
	$("#middle-right-map2").hide();
	$("#middle-left-map2").hide();
	 cityCode='00';
    if(sessionCityCode!='00'){
 	   cityCode=sessionCityCode;
    } 
	//调用顶部地图  
	loadTopMap();
	$("#middle-memberLevel").bind("click",function(){
		   $("#middle-memberLevel").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-carStatus").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-left-map1").show();
		   $("#middle-left-map2").hide();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleLeftByPileType();
		   }else{
			   loadMiddleLeftByPileType();
		   }
		   
		
	   });
	 $("#middle-carStatus").bind("click",function(){
		   $("#middle-carStatus").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-memberLevel").removeClass("bkblue").addClass("bkwhite");
   		   $("#middle-left-map1").hide();
   		   $("#middle-left-map2").show();
   		   if(levelIdentifier=='province'){
   			  loadProvinceMiddleLeftByPileServiceType();
   		   }else{
   			   loadMiddleLeftByPileServiceType();
   		   }
   
	   });
	 
	 $("#middle-right-expenseType").bind("click",function(){
		   $("#middle-right-carType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-expenseType").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-right-map1").show();
		   $("#middle-right-map2").hide();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleLeftByConnectorType();
		   }else{
			   loadMiddleLeftByConnectorType();
		   }
	      });
	   $("#middle-right-carType").bind("click",function(){
		   $("#middle-right-expenseType").removeClass("bkblue").addClass("bkwhite");
		   $("#middle-right-carType").removeClass("bkwhite").addClass("bkblue");
		   $("#middle-right-map2").show();
		   $("#middle-right-map1").hide();
		   if(levelIdentifier=='province'){
			   loadProvinceMiddleLeftByConnectorStatusType();
		   }else{
			   loadMiddleLeftByConnectorStatusType();
		   }
	   });
	   
});
   
   