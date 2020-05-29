<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta charset="UTF-8">
    <title>ECharts</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="ECharts">
    <meta name="author" content="linzhifeng@baidu.com">

	<script src="echarts/asset/js/esl/esl.js"></script>
    <script src="echarts/asset/js/codemirror.js"></script>
    <script src="echarts/asset/js/javascript.js"></script>
    <link href="echarts/asset/css/bootstrap.css" rel="stylesheet">
    <link href="echarts/asset/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="echarts/asset/css/codemirror.css" rel="stylesheet">
    <link href="echarts/asset/css/monokai.css" rel="stylesheet">
    <link href="echarts/asset/css/echartsHome.css" rel="stylesheet">
    <link rel="shortcut icon" href="echarts/asset/ico/favicon.png">
    
    <style type="text/css">
        .CodeMirror {
            height: 550px;
        }
    </style>
</head>

<body>
    <!-- NAVBAR
    ================================================== -->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="../../index.html">ECharts</a>
          <div class="nav-collapse collapse">
              <a id="forkme_banner" href="https://github.com/ecomfe/echarts">View on GitHub</a>
              <ul class="nav">
                <li><a href="../../index.html"><i class="icon-home icon-white"></i> Home</a></li>
                <li class="active"><a href="../example.html" class="active">Example</a></li>
                <li><a href="../doc.html" >API &amp; Doc</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-download-alt icon-white"></i>Download <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a id="last-release-link" href=""> </a></li>
                    <li><a href="https://github.com/ecomfe/echarts/archive/master.zip">ZIP (Latest)</a></li>
                  </ul>
                </li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Link <b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li><a href="https://github.com/ecomfe" target="_blank">Ecom-FE</a></li>
                    <li><a href="http://fe.baidu.com/doc/ecom/tech/topic/dv/index.html" target="_blank">Data Visualization</a></li>
                    <li class="divider"></li>
                    <!--li class="nav-header">Library</li-->
                    <li><a href="http://ecomfe.github.io/zrender/index.html" target="_blank">ZRender</a></li>
                    
                  </ul>
                </li>
              </ul>
           </div><!--/.nav-collapse -->
        </div><!-- /.container -->
      </div><!-- /.navbar-inner -->
    </div><!-- /.navbar-wrapper -->

    <div class="container-fluid">
        <div class="row-fluid">
            <div id="sidebar-code" class="span4">
                <div class="well sidebar-nav">
                    <div class="nav-header"><a href="#" onclick="autoResize()" class="icon-resize-full" id ="icon-resize" ></a>option</div>
                    <textarea id="code" name="code">
option = {
    tooltip : {
        trigger: 'item'
    },
    toolbox: {
        show : true,
        orient: 'vertical',
        x:'right',
        y:'center',
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false}
        }
    },
    series : [
        {
            tooltip: {
                trigger: 'item',
                formatter: '{b}'
            },
            name: '选择器',
            type: 'map',
            mapType: 'china',
            mapLocation: {
                x: 'left',
                y: 'top',
                width: '30%'
            },
            roam: true,
            selectedMode : 'single',
            itemStyle:{
                //normal:{label:{show:true}},
                emphasis:{label:{show:true}}
            },
            data:[
                {name: '北京', selected:false},
                {name: '天津', selected:false},
                {name: '上海', selected:false},
                {name: '重庆', selected:false},
                {name: '河北', selected:false},
                {name: '河南', selected:false},
                {name: '云南', selected:false},
                {name: '辽宁', selected:false},
                {name: '黑龙江', selected:false},
                {name: '湖南', selected:false},
                {name: '安徽', selected:false},
                {name: '山东', selected:false},
                {name: '新疆', selected:false},
                {name: '江苏', selected:false},
                {name: '浙江', selected:false},
                {name: '江西', selected:false},
                {name: '湖北', selected:false},
                {name: '广西', selected:false},
                {name: '甘肃', selected:false},
                {name: '山西', selected:false},
                {name: '内蒙古', selected:false},
                {name: '陕西', selected:false},
                {name: '吉林', selected:false},
                {name: '福建', selected:false},
                {name: '贵州', selected:false},
                {name: '广东', selected:false},
                {name: '青海', selected:false},
                {name: '西藏', selected:false},
                {name: '四川', selected:false},
                {name: '宁夏', selected:false},
                {name: '海南', selected:false},
                {name: '台湾', selected:false},
                {name: '香港', selected:false},
                {name: '澳门', selected:false}
            ]
        }
    ],
    animation: false
};
var ecConfig = require('echarts/config');
myChart.on(ecConfig.EVENT.MAP_SELECTED, function(param){
    var selected = param.selected;
    var selectedProvince;
    var name;
    for (var i = 0, l = option.series[0].data.length; i < l; i++) {
        name = option.series[0].data[i].name;
        option.series[0].data[i].selected = selected[name];
        if (selected[name]) {
            selectedProvince = name;
        }
    }
    if (typeof selectedProvince == 'undefined') {
        option.series.splice(1);
        option.legend = null;
        option.dataRange = null;
        myChart.setOption(option, true);
        return;
    }
    option.series[1] = {
        name: '随机数据',
        type: 'map',
        mapType: selectedProvince,
        itemStyle:{
            normal:{label:{show:true}},
            emphasis:{label:{show:true}}
        },
        mapLocation: {
            x: '35%'
        },
        roam: true,
        data:[
        ]
    };
    option.legend = {
        x:'right',
        data:['随机数据']
    };
    option.dataRange = {
        orient: 'horizontal',
        x: 'right',
        min: 0,
        max: 1000,
        color:['orange','yellow'],
        text:['高','低'],           // 文本，默认为数值文本
        splitNumber:0
    };
    myChart.setOption(option, true);
})
                    </textarea>
              </div><!--/.well -->
            </div><!--/span-->
            <div id="graphic" class="span8">
                <div id="main" class="main" style="height: 530px;"></div>
                <div>
                    <button onclick="refresh(true)">Refresh ~</button>
                    <span id='wrong-message' style="color:red"></span>
                </div>
            </div><!--/span-->
        </div><!--/row-->
        <hr>
        <!-- FOOTER -->
        <footer>
          <p class="pull-right"><a href="#">Back to top</a></p>
          <p>&copy; 2014 <a href="http://efe.baidu.com" target="_blank">EFE</a> &middot; <a href="https://github.com/ecomfe/echarts/blob/master/LICENSE.txt" target="_blank">License</a> &middot; <a href="../changelog.html" target="_blank">Changelog</a></p>
        </footer>
    </div><!--/.fluid-container-->

    <script src="echarts/asset/js/jquery.js"></script>
    <script src="echarts/asset/js/bootstrap-transition.js"></script>
    <script src="echarts/asset/js/bootstrap-alert.js"></script>
    <script src="echarts/asset/js/bootstrap-modal.js"></script>
    <script src="echarts/asset/js/bootstrap-dropdown.js"></script>
    <script src="echarts/asset/js/bootstrap-scrollspy.js"></script>
    <script src="echarts/asset/js/bootstrap-tab.js"></script>
    <script src="echarts/asset/js/bootstrap-tooltip.js"></script>
    <script src="echarts/asset/js/bootstrap-popover.js"></script>
    <script src="echarts/asset/js/bootstrap-button.js"></script>
    <script src="echarts/asset/js/bootstrap-collapse.js"></script>
    <script src="echarts/asset/js/bootstrap-carousel.js"></script>
    <script src="echarts/asset/js/bootstrap-typeahead.js"></script>
    <script src="echarts/asset/js/echartsExample.js"></script>
    <script src="echarts/asset/js/config.js"></script>
</body>
</html>