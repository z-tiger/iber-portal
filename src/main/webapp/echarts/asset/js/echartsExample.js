var myChart;
var pieChart ;
var domCode = document.getElementById('sidebar-code');
var domGraphic = document.getElementById('graphic');
var domMain = document.getElementById('main');
var domMessage = document.getElementById('wrong-message');
var iconResize = document.getElementById('icon-resize');
var needRefresh = false;
//饼型option封装
var option ;
var option1 ;
var domMain1 = document.getElementById('main1');

var pieLegendData= new Array();
var pieSeriesData = new Array() ;

function autoResize() {
    if (iconResize.className == 'icon-resize-full') {
        focusCode();
        iconResize.className = 'icon-resize-small';
    }
    else {
        focusGraphic();
        iconResize.className = 'icon-resize-full';
    }
}

function focusCode() {
    domCode.className = 'span8 ani';
    domGraphic.className = 'span4 ani';
}

function focusGraphic() {
    domCode.className = 'span4 ani';
    domGraphic.className = 'span8 ani';
    if (needRefresh) {
        myChart.showLoading();
        setTimeout(refresh, 1000);
    }
}

function refresh(isBtnRefresh){
    if (isBtnRefresh) {
        needRefresh = true;
        focusGraphic();
        return;
    }
    needRefresh = false;
    if (myChart && myChart.dispose) {
        myChart.dispose();
    }
    myChart = echarts.init(domMain);
    //地图
    var ecConfig = require('echarts/config'); 
    window.onresize = myChart.resize;
    //根据统计类型获取地图option
    setMapOption("00") ;
    //domMessage.innerHTML = '';
    
    //饼型
    pieChart = echarts.init(domMain1);
    //根据统计类型获取饼型option
    setPieOption("00") ;
    
    myChart.on(ecConfig.EVENT.CLICK, function (param) { //点击事件  
    	var statisticsTypeValue = document.getElementById("statisticsTypeValue").value ;
    	var statisticsTypeName = "" ;
    	if(statisticsTypeValue == "car"){
    		statisticsTypeName = "车辆" ;
    		setPieOption(param.data.cityCode) ;
    	}else if(statisticsTypeValue == "park"){
    		statisticsTypeName = "网点" ;
    		setPieParkOption(param.data.cityCode) ;
    	}else if(statisticsTypeValue == "pile"){
    		statisticsTypeName = "充电桩" ;
    		setPiePileOption(param.data.cityCode) ;
    	}
    	//钻取层级到区，则无法在向下
    	if(param.data.layer != "end"){
    		
    		cityArea(statisticsTypeValue,statisticsTypeName,param.data) ;
    	}
    });
}

/**
 * 获取城市区域
 * @param typeName 类型名称
 * @param data  区域名称、区域编码
 */
function cityArea(typeCode,typeName,data){
	var cityNameJson = '{'+data.name+' : '+data.cityCode+'}' ;
	var cityMap = eval ("(" + cityNameJson + ")");   ;
	
	//根据城市获取区域信息
	var placeList = new Array();
	var geoCoordDataArray  = new Array()  ;
	var geoCoordData  ;
	var totalNum = 0 ;
	$.ajax({  
        type : "post",  
         url : "area_map_statistics",  
         data : "cityCode="+data.cityCode+"&typeCode="+typeCode,  
         async : false,   //采用同步
         success : function(data){  
        	 var cityVo = $.parseJSON( data ); 
         	 for(var i = 0 ; i < cityVo.length ; i ++ ) {
         		var geoGps = new Array(); //经纬度
         		geoGps.push(cityVo[i].longitude) ;
         		geoGps.push(cityVo[i].latitude) ;
         		var obj = {name:cityVo[i].name, geoCoord:geoGps,value:cityVo[i].num,cityCode:cityVo[i].code,layer:'end'} ;
         		placeList.push(obj) ;
         		totalNum += cityVo[i].num ;
         		geoCoordDataArray.push("\""+cityVo[i].name+"\":["+cityVo[i].longitude+","+cityVo[i].latitude+"]") ;
         		
         	}
         	geoCoordData =  $.parseJSON("{"+geoCoordDataArray.toString()+"}") ; 
         }  
    });

    option = {
    		title : {
		        text: data.name+typeName,
		        subtext: data.name+typeName+"总数："+totalNum+"个",
		        sublink: "www.baidu.com",
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item'
		    },
		    legend: {
		        orient: 'vertical',
		        x:'left',
		        data:['统计数据']
		    },
		    dataRange: {
		        min : 0,
		        max : 10,
		        calculable : true,
		        color: ['maroon','purple','red','orange','yellow','lightgreen']
		    },
		    toolbox: {
		        show : true,
		        orient : 'vertical',
		        x: 'right',
		        y: 'center',
		        feature : {
		           // mark : {show: true},
		           // dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    }, 
	    series : [
	        {
	            name: data.name+'地图',
	            type: 'map',
	            mapType: data.name,
	            hoverable: false,
	            roam:true,
	            data : [],
	            itemStyle: {
                    normal: {
                        borderColor: '#87cefa',
                        borderWidth: 1,            // 标注边线线宽，单位px，默认为1
                        color: '#FFCC99',
                        label: {
                            show: true
                        }
                    }
                },
				markPoint : { 
					 	symbolSize: 5,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
		                itemStyle: {
		                    normal: {
		                        borderColor: '#87cefa',
		                        borderWidth: 1,            // 标注边线线宽，单位px，默认为1
		                        label: {
		                            show: false
		                        }
		                    }
		                },
		                data :placeList
	            },
	            geoCoord:geoCoordData
	        }
	    ],
	    backgroundColor: '#fffff'
    }; 
        
    var curIndx = 0;
	var mapType = [];
	var mapGeoData = require('echarts/util/mapData/params');
	
	for (var city in cityMap) {
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
	}
	myChart.setOption(option, true); 
}

function setPieOption(cityCode){
	//根据统计类型获取饼型option
    $.post("car_list_statistics?cityCode="+cityCode, function(data){
    	var carVo = $.parseJSON( data ); 
    	pieLegendData = ['运营中','闲置中','维修中'] ;
    	pieSeriesData = [
			                {value:carVo.operateTotal, name:'运营中'},
			                {value:carVo.emptyTotal, name:'闲置中'},
			                {value:carVo.repairTotal, name:'维修中'}
			            ];	
    	pieOption(carVo.cityName+"车辆统计","车辆总数："+carVo.total,pieLegendData,pieSeriesData) ;
    	pieChart.setOption(option1, true);
    });
}

function setPieParkOption(cityCode){
	$.post("park_list_statistics?cityCode="+cityCode, function(data){
    	var parkVo = $.parseJSON( data ); 
		pieLegendData = ['可用','已用'] ;
		pieSeriesData = [
			                {value:parkVo.parkEnabledTotal, name:'可用'},
			                {value:parkVo.parkUsedTotal, name:'已用'}
			            ];	
		pieOption(parkVo.cityName+"网点统计","网点总数"+parkVo.parkTotal+"个，总车位数"+parkVo.parkCarportTotal+"个",pieLegendData,pieSeriesData) ;
		pieChart.setOption(option1, true);
	 });
}

function setPiePileOption(cityCode){
	$.post("park_list_pile_statistics?cityCode="+cityCode, function(data){
    	var parkVo = $.parseJSON( data ); 
		pieLegendData = ['快充','慢充'] ;
		pieSeriesData = [
			                {value:parkVo.pileFastTotal, name:'快充'},
			                {value:parkVo.pileSlowTotal, name:'慢充'}
			            ];	
		pieOption(parkVo.cityName+"充电桩统计","充电桩总数"+parkVo.pileTotal+"个",pieLegendData,pieSeriesData) ;
		pieChart.setOption(option1, true);
	 });
}

function setMapOption(cityCode){
	//根据统计类型获取饼型option
    $.post("map_car_list_statistics?cityCode="+cityCode, function(data){
    	var cityVo = $.parseJSON( data ); 
    	var seriesData = new Array() ;
    	var geoCoordData = new Array();
    	var geoData  ;
    	var topData = new Array() ;
    	var totalNum = 0 ;
    	for(var i = 0 ; i < cityVo.length ; i ++ ) {
    		var obj = {name: cityVo[i].name, value: cityVo[i].num,cityCode:cityVo[i].code} ;
    		seriesData.push(obj) ;
    		geoCoordData.push("\""+cityVo[i].name+"\":["+cityVo[i].longitude+","+cityVo[i].latitude+"]") ;
    		if(i < 1){
    			var topObj =  {name: cityVo[i].name, value: cityVo[i].num,cityCode:cityVo[i].code} ;
    			topData.push(topObj) ;
    		}
    		totalNum += cityVo[i].num ;
    	}
    	geoData =  $.parseJSON("{"+geoCoordData.toString()+"}") ;
    	mapOption("全国车辆统计","车辆总数："+totalNum+"个","#",seriesData,geoData,topData,"车辆") ;
    	myChart.setOption(option, true);
    });
}

function setMapParkOption(cityCode){
	//根据统计类型获取饼型option
    $.post("map_park_list_statistics?cityCode="+cityCode, function(data){
    	var cityVo = $.parseJSON( data ); 
    	var seriesData = new Array() ;
    	var geoCoordData = new Array();
    	var geoData  ;
    	var totalNum = 0 ;
    	var topData = new Array() ;
    	for(var i = 0 ; i < cityVo.length ; i ++ ) {
    		var obj = {name: cityVo[i].name, value: cityVo[i].num,cityCode:cityVo[i].code} ;
    		seriesData.push(obj) ;
    		geoCoordData.push("\""+cityVo[i].name+"\":["+cityVo[i].longitude+","+cityVo[i].latitude+"]") ;
    		if(i < 1){
    			var topObj =  {name: cityVo[i].name, value: cityVo[i].num,cityCode:cityVo[i].code} ;
    			topData.push(topObj) ;
    		}
    		totalNum += cityVo[i].num ;
    	}
    	geoData =  $.parseJSON("{"+geoCoordData.toString()+"}") ;
    	
    	mapOption("全国网点统计","网点总数："+totalNum+"个","#",seriesData,geoData,topData,"网点") ;
    	myChart.setOption(option, true);
    });
}

function setMapPileOption(cityCode){
	//根据统计类型获取饼型option
    $.post("map_pile_list_statistics?cityCode="+cityCode, function(data){
    	var cityVo = $.parseJSON( data ); 
    	var seriesData = new Array() ;
    	var geoCoordData = new Array();
    	var geoData  ;
    	var totalNum = 0 ;
    	var topData = new Array() ;
    	for(var i = 0 ; i < cityVo.length ; i ++ ) {
    		var obj = {name: cityVo[i].name, value: cityVo[i].num,cityCode:cityVo[i].code} ;
    		seriesData.push(obj) ;
    		geoCoordData.push("\""+cityVo[i].name+"\":["+cityVo[i].longitude+","+cityVo[i].latitude+"]") ;
    		if(i < 1){
    			var topObj =  {name: cityVo[i].name, value: cityVo[i].num,cityCode:cityVo[i].code} ;
    			topData.push(topObj) ;
    		}
    		totalNum += cityVo[i].num ;
    	}
    	geoData =  $.parseJSON("{"+geoCoordData.toString()+"}") ;
    	mapOption("全国充电桩统计","充电桩总数："+totalNum+"个","#",seriesData,geoData,topData,"充电桩") ;
    	myChart.setOption(option, true);
    });
}
function needMap() {
    var href = location.href;
    return href.indexOf('map') != -1
           || href.indexOf('mix3') != -1
           || href.indexOf('mix5') != -1;

}

var echarts;
var developMode = false;

if (developMode) {
    // for develop
    require.config({
        packages: [
            {
                name: 'echarts',
                location: '../../src',
                main: 'echarts'
            },
            {
                name: 'zrender',
                //location: 'http://ecomfe.github.io/zrender/src',
                location: '../../../zrender/src',
                main: 'zrender'
            }
        ]
    });
}
else {
    // for echarts online home page
    var fileLocation = needMap() ? './www/js/echarts-map' : './www/js/echarts';
    require.config({
        paths:{ 
            echarts: fileLocation,
            'echarts/chart/line': fileLocation,
            'echarts/chart/bar': fileLocation,
            'echarts/chart/scatter': fileLocation,
            'echarts/chart/k': fileLocation,
            'echarts/chart/pie': fileLocation,
            'echarts/chart/radar': fileLocation,
            'echarts/chart/map': fileLocation,
            'echarts/chart/chord': fileLocation,
            'echarts/chart/force': fileLocation
        }
    });
}

// 按需加载
require(
    [
        'echarts',
        'echarts/chart/line',
        'echarts/chart/bar',
        'echarts/chart/scatter',
        'echarts/chart/k',
        'echarts/chart/pie',
        'echarts/chart/radar',
        'echarts/chart/force',
        'echarts/chart/chord',
        'echarts/config',
        needMap() ? 'echarts/chart/map' : 'echarts'
    ],
    requireCallback
);

function requireCallback (ec) {
    echarts = ec;
    if (myChart && myChart.dispose) {
        myChart.dispose();
    }
    myChart = echarts.init(domMain);
   // pieChart = echarts.init(domMain1);
    refresh();
    window.onresize = myChart.resize;
} 

function statisticsType(type){
	if(type == 'car'){
		document.getElementById("statisticsTypeValue").value = "car" ;
		setPieOption("00") ;
		setMapOption("00") ;
		$("#car").addClass("active");
		$("#park").removeClass("active");
		$("#pile").removeClass("active");
	}else if(type == 'park'){
		document.getElementById("statisticsTypeValue").value = "park" ;
		setPieParkOption("00") ;
		setMapParkOption("00") ;
		$("#car").removeClass("active");
		$("#park").addClass("active");
		$("#pile").removeClass("active");
	}else if(type == 'pile'){
		document.getElementById("statisticsTypeValue").value = "pile" ;
		setPiePileOption("00") ;
		setMapPileOption("00") ;
		$("#car").removeClass("active");
		$("#park").removeClass("active");
		$("#pile").addClass("active");
	}
}  

function pieOption(text , subtext ,legendData,seriesData){
	option1 = {
		    title : {
		        text: text,
		        subtext: subtext ,
		        x:'center',
		        subtextStyle:{color:"#3baae3"}
		    },
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
}

function mapOption(text , subtext , sublink , seriesData,geoCoordData,topData,typeName){
	option = {
		    title : {
		        text: text,
		        subtext: subtext,
		        sublink: sublink,
		        x:'center',
		        subtextStyle:{color:"#3baae3"} // 副标题颜色  textStyle 主标题颜色
		    },
		    tooltip : {
		        trigger: 'item'
		    },
		    legend: {
		        orient: 'vertical',
		        x:'left',
		        data:[typeName+'统计数据']
		    },
		    dataRange: {
		        min : 0,
		        max : 10,
		        calculable : true,
		        /*color: ['maroon','purple','red','orange','yellow','lightgreen']*/
		    	color: ['maroon','purple','red','orange','yellow','lightgreen']
		    },
		    toolbox: {
		        show : false,
		        orient : 'vertical',
		        x: 'right',
		        y: 'center',
		        feature : {
		           // mark : {show: true},
		           // dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            name: typeName+'统计数据',
		            type: 'map',
		            mapType: 'china',
		            hoverable: false,
		            roam:true,
		            data : [],
		            itemStyle: {
	                    normal: {
	                        borderColor: '#ffb45b', //分割线颜色
	                        areaStyle:{color:'#fcf9f2'}, //分割省颜色
	                        borderWidth: 1,            // 分割边线线宽，单位px，默认为1
	                        label: {
	                            show: true,
	                        	textStyle:{color:"#000"},
	                        }
	                    }
	                },
		            markPoint : {
		                symbolSize: 5,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
		                itemStyle: {
		                    normal: {
		                        borderColor: '#0f89f5',
		                        borderWidth: 0.3,           // 标注边线线宽，单位px，默认为1
		                        label: {
		                            show: false
		                        }
		                    }
		                },
		                data : seriesData
		            },
		            geoCoord: geoCoordData
		        },
		        {
		            name: '排名第一的城市',
		            type: 'map',
		            mapType: 'china',
		            data:[],
		            markPoint : {
		                symbol:'emptyCircle',
		                symbolSize : function(v){
		                    return 10 + v/100
		                },
		                effect : {
		                    show: true,
		                    shadowBlur : 0
		                },
		                itemStyle:{
		                    normal:{
		                        label:{show:false}
		                    }
		                },
		                data : topData
		            }
		        }
		    ]
		};
}

function backType(){
	var statisticsTypeValue = document.getElementById("statisticsTypeValue").value ;
	statisticsType(statisticsTypeValue) ;
}