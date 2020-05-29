// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});

// Callbacks to run specific code for specific pages, for example for About page:
myApp.onPageInit('about', function (page) {
    // run createContentPage func after link was clicked
    $$('.create-page').on('click', function () {
        createContentPage();
    });
});

// Generate dynamic page
var dynamicPageIndex = 0;
function createContentPage(var rate) {
	mainView.router.loadContent(
        '<!-- Top Navbar-->' +
        '<div class="navbar">' +
        '  <div class="navbar-inner">' +
        '    <div class="left"><a href="#" class="back link"><i class="icon icon-back"></i><span>Back</span></a></div>' +
//        '    <div class="center sliding">Dynamic Page ' + (++dynamicPageIndex) + '</div>' +
        '    <div class="center sliding"> 车型 ' + '${rate.carTypeName}' + '</div>' +
        '  </div>' +
        '</div>' +
        '<div class="pages">' +
        '  <!-- Page, data-page contains page name-->' +
        '  <div data-page="dynamic-pages" class="page">' +
        '    <!-- Scrollable page content-->' +
        '    <div class="page-content">' +
        '      <div class="content-block">' +
        '        <div class="content-block-inner">' +
        '          <p>Here is a dynamic page created on ' + new Date() + ' !</p>' +
        '          <p>Go <a href="#" class="back">back</a> or go to <a href="services.html">Services</a>.</p>' +
        '        </div>' +
        '      </div>' +
        '    </div>' +
        '  </div>' +
        '</div>'
    );
	return;
}


function getDetailByCityCodeAndCarTypeId(var cityCode, var carTypeId){
	$$.ajax({
		type: "POST", //请求方式
		url: "get_by_city_code_and_car_type_id.do",
        cache: false,
        data: {"cityCode" = cityCode, "carTypeId" = carTypeId}, //传参
        dataType: 'json',//返回值类型
        success: function(rate){
        	createContentPage(rate);
//        	alert(json[1].username+" "+ json[1].password);//弹出返回过来的List对象
        }
	});
}

/**
 * 点击查看价格详情
 */
$("#priceDetail").bind("click",function(){
//	createContentPage('${carType}');
	//1
//	$.post("get_by_city_code.do",{"id":JsonData.id},function(data){
//		if(data=="success"){
//			//$.messager.alert("提示", "删除成功", "info");
//		    $("#dataGrid").datagrid("reload");
//		}else{
//			$.messager.alert("提示", "删除失败", "info");
//		}
//	},"text");
//	
//	//2
	$$.ajax({
		type: "POST", //请求方式
		url: "get_by_city_code.do",
//        url: "get_by_city_code_and_car_type_id.do",//请求路径
        cache: false,
        data: {"cityCode" = '440300'}, //传参
//        data: {"cityCode" = '441900', "carTypeId" = 2}, //传参
        dataType: 'json',//返回值类型
        success:function(json){
        	createContentPage('${carType}');
//        	alert(json[1].username+" "+ json[1].password);//弹出返回过来的List对象
        }
	});
});
