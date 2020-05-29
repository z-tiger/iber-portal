<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<c:if test='${ null == sessionScope.user}'>
	<c:redirect url="./SessionTimeOut.html"></c:redirect>
</c:if>

<title>预警统计</title>
	    <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
</head>

<body>
	<em id="warnRemindTotal" style="display:none; float:left; font-size:14px; height:20px; padding:0 10px; background: #f00; color:#fff; margin:0 0 0; text-align: center; line-height: 20px; border-radius:20px; font-style:normal;"></em>
		 <script type="text/javascript" language="javascript"><%--
		    function warnRefresh() {  
		       	 var url = "warn_remind_total";
		       	 var data = {type:1};
		       	 $.ajax({
			       	  type : "get",
			       	  async : true, //同步请求
			       	  url : url,
			       	  data : data,
			       	  success:function(dates){
				       	  if(dates!=0){
				           	  $("#warnRemindTotal").html(dates);//要刷新的div
				          	  document.getElementById("warnRemindTotal").style.display = 'block';	  
				       	  }
				       	
				       	  if(dates==0){
				          	  $("#warnRemindTotal").html("");//要刷新的div
				            	document.getElementById("warnRemindTotal").style.display = 'none';		 
			       	     }
			       	 }
		       	 });      	               
		     }--%>
		     
		  function timeDown(limit, i) {
               limit--;
               if (limit < 0) {
                   limit = 60*2;
                   warnRefresh(i);
                   i++;
               }
               setTimeout(function() {
                   timeDown(limit, i);
               }, 1000)
           }
           
  		 $(document).ready(function() {
               timeDown(1, 0)
        });
	</script>
</body>

</html>
