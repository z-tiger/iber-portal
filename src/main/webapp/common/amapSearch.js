/**
 * 高德地图的地址搜索
 */

// 构造地点查询类
function searchAddress() {
	var address = $("#s-address").val();
	mapObj.clearMap();
	mapObj.plugin([ "AMap.PlaceSearch" ], function() {
		MSearch = new AMap.PlaceSearch({ // 构造地点查询类
			pageSize : 10,
			pageIndex : 1
		});
		AMap.event.addListener(MSearch, "complete", Search_CallBack);// 返回地点查询结果
		MSearch.search(address); // 关键字查询
	});
}

// 地图查询回调函数
function Search_CallBack(data) {
	var poiArr = data.poiList.pois;
	var resultCount = poiArr.length;
	for ( var i = 0; i < resultCount; i++) {
		addmarker(i, poiArr[i]);
	}
	mapObj.setFitView();
}

function addmarker(i, d) {
	var lngX;
	var latY;
	var iName;
	var iAddress;
	if (d.location) {
		lngX = d.location.getLng();
		latY = d.location.getLat();
	} else {
		lngX = d._location.getLng();
		latY = d._location.getLat();
	}
	if (d.name) {
		iName = d.name;
	} else {
		iName = d._name;
	}
	if (d.name) {
		iAddress = d.address;
	} else {
		iAddress = d._address;
	}
	var markerOption = {
		map : mapObj,
		icon : "https://webapi.amap.com/images/" + (i + 1) + ".png",
		position : new AMap.LngLat(lngX, latY)
	};
	var mar = new AMap.Marker(markerOption);
	marker.push(new AMap.LngLat(lngX, latY));

	var infoWindow = new AMap.InfoWindow({
		content : "<h3><font color=\"#00a6ac\">" + (i + 1) + ". " + iName
				+ "</font></h3>",
		size : new AMap.Size(300, 0),
		autoMove : true,
		offset : new AMap.Pixel(0, -30)
	});
	windowsArr.push(infoWindow);
	var aa = function(e) {
		infoWindow.open(mapObj, mar.getPosition());
	};
	AMap.event.addListener(mar, "click", aa);
}