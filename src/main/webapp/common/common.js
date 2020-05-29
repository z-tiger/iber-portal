
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

function formatDay(val) {
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
	+ ((day < 10) ? "0" : "") + day;
}

function formatMonth(val) {
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
	return date.getFullYear() + "-" + ((month < 10) ? "0" : "") + month;
}
function secondToDate(value) {
    var secondTime = parseInt(value);// 秒
    var minuteTime = 0;// 分
    var hourTime = 0;// 小时
    if(secondTime > 60) {//如果秒数大于60，将秒数转换成整数
        //获取分钟，除以60取整数，得到整数分钟
        minuteTime = parseInt(secondTime / 60);
        //获取秒数，秒数取佘，得到整数秒数
        secondTime = parseInt(secondTime % 60);
        //如果分钟大于60，将分钟转换成小时
        if(minuteTime > 60) {
            //获取小时，获取分钟除以60，得到整数小时
            hourTime = parseInt(minuteTime / 60);
            //获取小时后取佘的分，获取分钟除以60取佘的分
            minuteTime = parseInt(minuteTime % 60);
        }
    }
    var result = "" + parseInt(secondTime) + "秒";

    if(minuteTime > 0) {
        result = "" + parseInt(minuteTime) + "分" + result;
    }
    if(hourTime > 0) {
        result = "" + parseInt(hourTime) + "小时" + result;
    }
    return result;
}




