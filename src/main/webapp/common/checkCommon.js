/**
 * 报表公用JS
 * */

	var showHideSpeed = 0;//显示、隐藏的速度

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
	
	/**
	 * 设置起止时间
     *
     * liubiao 添加支付起始时间
	 */
	function setBeginAndEnd(period){
		switch(period){
			case "1"://月，要显示年月
                var begin_year = $("#begin_year").val();
                var end_year =$("#end_year").val();
                var pay_begin_year = $('#pay_begin_year').val();
                var pay_end_year = $('#pay_end_year').val();

                var begin_month = $('#begin_month').val();
                var end_month = $('#end_month').val();
                var pay_begin_month = $('#pay_begin_month').val();
                var pay_end_month = $('#pay_end_month').val();

                if(isNotEmpty(begin_year)&&isNotEmpty(end_year)&&isNotEmpty(begin_month)
                &&isNotEmpty(end_month)) {
                    var b = $("#begin_year").val() + "-" + $("#begin_month").val()
                        + "-01 00:00:00";
                    //获取year-month对应的月份的最后一天的最后时间
                   var  e = getLastDay($("#end_year").val(), $("#end_month").val());

                    $("#hidBegin").val(b);
                    $("#hidEnd").val(e.getFullYear() + "-" + (e.getMonth() + 1) + "-" + e.getDate()
                        + " " + e.getHours() + ":" + e.getMinutes() + ":" + e.getSeconds());
                }

                if(isNotEmpty(pay_begin_year) &&isNotEmpty(pay_end_year)&&isNotEmpty(pay_begin_month)&&isNotEmpty(pay_end_month)) {
                   var  pb = $("#pay_begin_year").val() + "-" + $("#pay_begin_month").val()
                        + "-01 00:00:00";
                    var pe = getLastDay($("#pay_end_year").val(), $("#pay_end_month").val());

                    $("#hidPayBeginTime").val(pb);
                    $("#hidPayEndTime").val(pe.getFullYear() + "-" + (pe.getMonth() + 1) + "-" + pe.getDate()
                        + " " + pe.getHours() + ":" + pe.getMinutes() + ":" + pe.getSeconds())

                }

				break;
			case "2"://季度，要显示年、季度
                var begin_year = $("#begin_year").val();
                var end_year =$("#end_year").val();
                var pay_begin_year = $('#pay_begin_year').val();
                var pay_end_year = $('#pay_end_year').val();

                var begin_quarter = $('#begin_quarter').val();
                var end_quarter = $('#end_quarter').val();
                var pay_begin_quarter = $('#pay_begin_quarter').val();
                var pay_end_quarter = $('#pay_end_quarter').val();

                if(isNotEmpty(begin_year)&&isNotEmpty(begin_quarter&&isNotEmpty(end_year)&&isNotEmpty(end_quarter))){
                    var beginLastMonth = $("#begin_quarter").val() * 3;//开始的季度的最后一个月
                    var beginFirstMonth = beginLastMonth - 2;//开始的季度的第一个月
                    var endLastMonth = $("#end_quarter").val() * 3;//结束的季度的最后一个月

                    var  b = $("#begin_year").val() + "-" + beginFirstMonth
                        + "-01 00:00:00";
                    //获取year-month对应的月份的最后一天的最后时间
                    var  e = getLastDay($("#end_year").val(), endLastMonth);
                    $("#hidBegin").val(b);
                    $("#hidEnd").val(e.getFullYear() + "-" + (e.getMonth() + 1) + "-" + e.getDate()
                        + " " + e.getHours() + ":" + e.getMinutes() + ":" + e.getSeconds());
                }

                if(isNotEmpty(pay_begin_year)&& isNotEmpty(pay_end_year)&& isNotEmpty(pay_begin_quarter) && isNotEmpty(pay_end_quarter)){
                    var payBeginLastMonth = $('#pay_begin_quarter').val()*3;
                    var payBeginFirstMonth = payBeginLastMonth-2;
                    var payEndLastMonth = $('#pay_end_quarter').val()*3;

                    var pb = $('#pay_begin_year').val() + "-" + payBeginFirstMonth + "-01 00:00:00";
                    var pe = getLastDay($('#pay_end_year').val(), payEndLastMonth);
                    $("#hidPayBeginTime").val(pb);
                    $("#hidPayEndTime").val(pe.getFullYear() + "-" + (pe.getMonth() + 1) + "-" + pe.getDate()
                        + " " + pe.getHours() + ":" + pe.getMinutes() + ":" + pe.getSeconds());
                }
				break;
			case "3"://年
                var begin_year = $("#begin_year").val();
                var end_year =$("#end_year").val();
                var pay_begin_year = $('#pay_begin_year').val();
                var pay_end_year = $('#pay_end_year').val();

                if(begin_year!=null&&begin_year!=''&& end_year!=null && end_year!='') {
                   var b = $("#begin_year").val() + "-01-01 00:00:00";
                   var e = $("#end_year").val() + "-12-31 23:59:59";

                    $("#hidBegin").val(b);
                    $("#hidEnd").val(e);
                }

                if(pay_begin_year!=null && pay_begin_year!='' && pay_end_year!=null && pay_end_year!='') {

                   var pb = $("#pay_begin_year").val() + "-01-01 00:00:00";
                   var pe = $("#pay_end_year").val() + "-12-31 23:59:59";

                    $("#hidPayBeginTime").val(pb);
                    $("#hidPayEndTime").val(pe);
                }

				break;
			default://默认为自选区间
				$("#hidBegin").val($("#begin_full").datetimebox("getValue"));
				$("#hidEnd").val($("#end_full").datetimebox("getValue"));

				//支付时间
                $("#hidPayBeginTime").val($("#pay_begin_full").datetimebox("getValue"));
                $("#hidPayEndTime").val($("#pay_end_full").datetimebox("getValue"));

				break;
		}
		
	}
	
	function setBeginAndEnd2(){
//		if((null == $("#hidBegin").val() || "" == $("#hidBegin").val()) 
//				|| (null == $("#hidEnd").val() || "" == $("#hidEnd").val())){
//			var period = $("input[name='period']").val();
			var period = $("#period").combobox('getValue');
			//如果没选周期，默认为自选区间
			if(null == period || "" == period){
				period = 0;
			}
			setBeginAndEnd(period);
//		}
	}
	
	//根据不同周期（年、月、季度、自选）来显示相关label
	function onSelPeriod(){
		var per = $("input[name='period']").val();
		$("label").hide();//先隐藏全部
		switch(per){
		case "0"://自选区间
			$(".cls_label_datetime").show(showHideSpeed);
			break;
		case "1"://月，要显示年月
			$("label[name='label_year']").show(showHideSpeed);
			$("label[name='label_month']").show(showHideSpeed);
			break;
		case "2"://季度，要显示年、季度
			$("label[name='label_year']").show(showHideSpeed);
			$("label[name='label_quarter']").show(showHideSpeed);
			break;
		case "3"://年
			$("label[name='label_year']").show(showHideSpeed);
			break;
		default:
			break;
		}
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
	
	
	/**
	 * 转换为tree
	 */
	function convert2Tree(rows){
		function exists(rows, parentId){
			for(var i = 0; i < rows.length; i++){
				if (rows[i].id == parentId) return true;
			}
			return false;
		}
		
		var nodes = [];
		//获取顶层节点
		for(var i = 0; i < rows.length; i++){
			var row = rows[i];
			//如果不存在，则插入
			if (!exists(rows, row.parentId)){
				nodes.push({
					id: row.id,
					text: row.text,
					attributes: row.attributes
				});
			}
		}
		
		var toDo = [];
		for(var i = 0; i < nodes.length; i++){
			toDo.push(nodes[i]);
		}
		while(toDo.length){
			//获取父节点，并弹出此节点
			var node = toDo.shift();
			//构建子节点
			for(var i = 0; i < rows.length; i++){
				var row = rows[i];
				if (row.parentId == node.id){
					var child = {id: row.id, text: row.text, attributes: row.attributes};
					if (node.children){
						node.children.push(child);
					} else {
						node.children = [child];
					}
					toDo.push(child);
				}
			}
		}
		return nodes;
	}
	
	/**
	 * 选择树节点时的处理逻辑
	 * @param node
	 */
	function onSelectCityParkTree(node) {  
		$("#cityParkTree").tree(node.state === 'closed' ? 'expand' : 'collapse', node.target);  
		var curAttr = node.attributes;
		if(null != curAttr){
			
    		var cityCode = curAttr.cityCode;
    		if(null != cityCode){
    			$("#hidCityCode").val(cityCode);
			}

    		var type = curAttr.type;
    		if(null != type){
    			$("#hidType").val(type);
    		}
    		
    		var stationId = curAttr.stationId;
    		if(null != stationId){
    			$("#hidParkId").val(stationId);
    		}
    		
    		//dataGrid加载数据
//    		$('#dataGrid').datagrid('load',{
//    	    	'cityCode' : cityCode,
//    	    	'type' : type,
//    	    	'parkId' : stationId,
//    			'begin' : $("#hidBegin").val(),
//    			'end' : $("#hidEnd").val()
//            });
    		
    		console.log("text = " + node.text + " , id = " + node.id 
    				+ " , type = " + type + " , cityCode = " + cityCode + " , stationId = " + stationId );
    		console.log("text = " + node.text + " , id = " + node.id 
    				+ " , type = " + $("#hidType").val() + " , cityCode = " + $("#hidCityCode").val() + " , stationId = " + $("#hidParkId").val() );
    	}
	}
	
	
	/**
	 * 小计，对指定datagrid 的指定列求和
	 * isMoney指定是否计算金额，如果是，则最后要除以100，因为数据库中的金额数据以分为单位
	 */
    function computeSubtotal(datagridId, colName) {
    	
        var rows = $(datagridId).datagrid('getRows');
        var total = 0;
        for (var i = 0; i < rows.length; i++) {
        	if(rows[i][colName] == null
        			|| rows[i][colName] == ""){
        		total += 0 ;
        	}else{
        		total += parseFloat(rows[i][colName]);
        	}
        }
        return total;
    }

    function isEmpty( obj) {
        if(obj==null||obj =='') {
            return true
        }else {
            return false;
        }

    }

    function isNotEmpty(obj) {
        return !isEmpty(obj);
    }
