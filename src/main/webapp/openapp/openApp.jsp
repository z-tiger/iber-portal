<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,height=device-height,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name='apple-itunes-app' content='app-id=1035190480'>
    <title>宜步出行</title>
    <style type="text/css">
        .main {
            margin:50% auto;
        }
        input{

        }
        .appect{
            width: 100%;
        }
        .dowm{
            width: 100%;
        }
    </style>
</head>

<script type="text/javascript" src="../ui_lib/js/jquery.min.js"></script>
<script type="text/javascript">

    submitFn()
    function submitFn(){
        //判断浏览器
        var u = navigator.userAgent;
        if(u.indexOf('Android') > -1 ){
            //Android
            openApp('iber://ibgoing',"http://eberimg.oss-cn-shenzhen.aliyuncs.com/upgrade/99183b29-ee04-4fdb-ab0b-3b67c5e1b647.apk")
        }else if(u.indexOf('iPhone') > -1){
            //IOS
            openApp('commandDemo.ProperWay://',"https://itunes.apple.com/us/app/xing-zhi-you-dao/id1035190480?l=zh&ls=1&mt=8")
        }
    }

    function openApp(src,downsrc) {
        window.location.href = src;
        setTimeout(function(){
            window.location.href = downsrc;//打开app下载地址
        },3000)
    }
</script>
<body style="padding: 0;margin: 0">
    <div class="main">

    </div>
</body>
</html>