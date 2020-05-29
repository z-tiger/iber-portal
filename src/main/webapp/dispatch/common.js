
//获取当前时间前一个小时字符串
function getNowBeforeFormatDate() {
	var now = new Date();
	var date = new Date(now.getTime() - 24*3600 * 1000);
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}



//获取当前时间字符串
function getNowFormatDate() {
	var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}

//获取当天开始时间字符串
function getTodayBeginTimeString() {
	// 设置默认值
	var today = new Date();
	var month = today.getMonth() + 1;
	var day = today.getDate();
	var hour = today.getHours();
	return today.getFullYear() + "-" + ((month < 10) ? "0" : "") + month + "-" 
	+ ((day < 10) ? "0" : "") + day + hour;
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

//获得当月的第一天日期
function getMonthStartDate(){  
    var dt = new Date();  
    dt.setDate(1);  
    dt.setMonth(dt.getMonth()+1);  
    cdt = new Date(dt.getTime()-1000*60*60*24);  
    return cdt.getFullYear()+"-"+(Number(cdt.getMonth())+1)+"-"+"01";   
}

//获得当月的最后一天日期
function getMonthEndDate(){  
    var dt = new Date();  
    dt.setDate(1);  
    dt.setMonth(dt.getMonth()+1);  
    cdt = new Date(dt.getTime()-1000*60*60*24);  
    return cdt.getFullYear()+"-"+(Number(cdt.getMonth())+1)+"-"+cdt.getDate();   
}


//计算本周开始日期，并以 YYYY-MM-DD 形式返回
function getWeekFirstDay()
{
  var now = new Date();      
  var Year = now.getFullYear();
  var Month = now.getMonth() + 1;
  var Day = now.getDate()- now.getDay();
  var beginTime = Year + "-" + Month +"-" + Day;        //并以 YYYY-MM-DD 形式返回
  return beginTime;
}


//计算本周结束日期，并以 YYYY-MM-DD 形式返回
function getWeekLastDay()
{
  var now = new Date();      
  var Year = now.getFullYear();
  var Month = now.getMonth() + 1;
  var Day = now.getDate()- now.getDay()+6;
  var endTime = Year + "-" + Month +"-" + Day; 
  return endTime;
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


