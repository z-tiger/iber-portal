$(function () {

    $.ajaxSetup({
        method:"post",
        dataType:"json",
        beforeSend:function () {
            NProgress.start();
        },
        complete:function () {
            NProgress.done();
        }
    });

    // 获取日期选择框
    const $dateInput = $("#date_input");
    const nowDate = moment().format('YYYY-MM-DD');
    $dateInput.attr("max",nowDate);
    const yesterday = moment().add(-1,'days').format("YYYY-MM-DD");
    $dateInput.val(yesterday);

    var map = new AMap.Map("container", {
        resizeEnable: true,
        center: [113.7439,23.0063],
        zoom: 11
    });

    map.plugin(["AMap.ToolBar"], function() {
        map.addControl(new AMap.ToolBar());
    });

    if (!isSupportCanvas()) {
        alert('热力图仅对支持canvas的浏览器适用,您所使用的浏览器不能使用热力图功能,请换个浏览器试试~')
    }

    var heatmap;

    var setDate = function (obj) {
        map.plugin(["AMap.Heatmap"], function () {
            if (heatmap){
                //设置数据集
                heatmap.setDataSet({
                    data: obj,
                    max: 50
                });
            }{
                //初始化heatmap对象
                heatmap = new AMap.Heatmap(map, {
                    radius: 6, //给定半径
                    opacity: [0, 0.8]
                });

                //设置数据集
                heatmap.setDataSet({
                    data: obj,
                    max: 50
                });
            }
        });
    };

    getData(yesterday);
    // const $dayBtn = $("#day_btn");
    // $dayBtn.css("color","#f00");

    /**网点展示*/
    var parkArr= [];
    var markerArr = [];
    $.post("park_list_code", function(data){
        parkArr=eval(data);
        if(markerArr.length>0){
            for(var j=0;j<markerArr.length;j++){
                if(markerArr[j]){
                    markerArr[j].setMap(null);
                }
            }
        }
        for ( var i = 0; i < parkArr.length; i++) {
            markerArr[i]=addMarkerPark(parkArr[i]);//向地图中添加网点marker
        }
    });
    // 添加标注
    function addMarkerPark(json) {
        var p0 = json.longitude;
        var p1 = json.latitude;
        //点标记中的文本
        var markerContent = document.createElement("div");
        markerContent.className = "markerContentStyle";
        var markerImg= document.createElement("img");
        markerImg.className="markerlnglat";
        markerImg.src="images/monitorCenter/"+json.cooperationType+"-"+json.category+"s.png";
        markerContent.appendChild(markerImg);

        var markerSpan = document.createElement("span");
        markerSpan.innerHTML = "<div style='width:80px;'>"+json.name+"</div>";
        markerContent.appendChild(markerSpan);
        var marker = new AMap.Marker({ //创建自定义点标注
            position : new AMap.LngLat(p0, p1),
            offset : new AMap.Pixel(-10, -34),
            content:markerContent
        });
        marker.setMap(map);
        return marker;
    }


    //判断浏览区是否支持canvas
    function isSupportCanvas() {
        var elem = document.createElement('canvas');
        return !!(elem.getContext && elem.getContext('2d'));
    }

    // 绑定事件
    $("#search_btn").click(function () {
        // 搜索
        var val = $dateInput.val();
        getData(val);
    });

    function getData(date){
        $.ajax({
            url:"/heatMapDate",
            method:"post",
            data:{"date":date},
            dataType:"json",
            success:function (data) {
                if (data && data.length){
                    setDate(data)
                }
            },
            error:function () {
                alert("操作失败！");
            }
        });
    }

    // $dayBtn.click(function () {
    //     $(".button").removeAttr("disabled").css("color","#fff");
    //     $(this).attr("disabled",true);
    //     $(this).css("color","#f00");
    //     setDate(heatmapDataDay);
    // });
    //
    // $("#week_btn").click(function () {
    //     $(".button").removeAttr("disabled").css("color","#fff");
    //     $(this).attr("disabled",true);
    //     $(this).css("color","#f00");
    //     setDate(heatmapDataWeek);
    // });
    //
    // $("#hour_btn").click(function () {
    //     $(".button").removeAttr("disabled").css("color","#fff");
    //     $(this).attr("disabled",true);
    //     $(this).css("color","#f00");
    //     setDate(heatmapDataHour);
    // });
});
