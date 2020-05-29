
//获取当天开始时间字符串
function getTodayBeginTimeString() {
	// 设置默认值
	var today = new Date();
	var month = today.getMonth() + 1;
	var day = today.getDate();
	var hour = today.getHours();
	return today.getFullYear() + "-" + ((month < 10) ? "0" : "") + month + "-" 
	+ ((day < 10) ? "0" : "") + day + " 00:00:00";
}

//获取当天结束时间字符串
function getTodayEndTimeString() {
	// 设置默认值
	var today = new Date();
	var month = today.getMonth() + 1;
	var day = today.getDate();
	var hour = today.getHours();
	return today.getFullYear() + "-" + ((month < 10) ? "0" : "") + month + "-" 
	+ ((day < 10) ? "0" : "") + day + " 23:59:59";
	
}

// 转换格式化
function formatDate(val) {
	if(val == undefined || val == null || $.trim(val) == '') {
		return '';
	}
	var date = new Date();
	date.setTime(val.time);
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	return date.getFullYear() + "-" + ((month < 10) ? "0" : "") + month + "-" 
	+ ((day < 10) ? "0" : "") + day + " " + ((hour < 10) ? "0" : "") + hour + ":" 
	+ ((minute < 10) ? "0" : "") + minute + ":" + ((second < 10) ? "0" : "") + second;
}