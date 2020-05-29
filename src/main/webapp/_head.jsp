<%@ page language="java"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test='${ null == sessionScope.user}'>
    <c:redirect url="./SessionTimeOut.html"></c:redirect>
</c:if>

<link rel="stylesheet" type="text/css" href="ui_lib/css/newLoginStyle.css"/>
<script>
  function timeDown(limit, i) {
               limit--;
               if (limit < 0) {
                   limit = 60;
                   warnRefresh(i);
                   auditMemberRefresh(i);
                   carOfflineApplyRefresh(i);
                   i++;
               }
               setTimeout(function() {
                   timeDown(limit, i);
               }, 2000)
           }
           
           
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
				           	  $(".head-tip").html(dates);//要刷新的div
				          	  $(".head-tip").show();
				       	  }
				       	
				       	  if(dates==0){
                           $(".head-tip").hide();	 
			       	     }
			       	 }
		       	 });      	               
		     }
	 //待审核会员数量统计
	 function auditMemberRefresh() {  
		       	 var url = "audit_member_total";
		       	 $.ajax({
			       	  type : "get",
			       	  async : true, //同步请求
			       	  url : url,
			       	  success:function(dates){
			 
				      	  if(dates!=0){
				       	  	  console.log(dates);
				           	  $(".audit_tip").html(dates);//要刷新的div
				          	  $(".audit_tip").show();
				       	  }
				       	
				       	  if(dates==0){
                           $(".audit_tip").hide();	 
			       	    }
			       	}
		       	 });      	               
		    }
		    
		    //待审核下线车辆数量统计
	 function carOfflineApplyRefresh() {  
		       	 var url = "car_offline_apply_total";
		       	 $.ajax({
			       	  type : "get",
			       	  async : true, //同步请求
			       	  url : url,
			       	  success:function(dates){
			 
				      	  if(dates!=0){
				       	  	  console.log(dates);
				           	  $(".car_tip").html(dates);//要刷新的div
				          	  $(".car_tip").show();
				       	  }
				       	
				       	  if(dates==0){
                           $(".car_tip").hide();	 
			       	    }
			       	}
		       	 });      	               
		    }       		

	$(document).ready(function(){
	
	   timeDown(1, 0);
	
	  $("#headbut-user").mouseover(function(){
		$(".avatar").show();
	  });
	  $("#headbut-user").mouseout(function(){
		$(".avatar").hide();
	  });
	});
</script>
<div class="header">
    <div class="logo"></div>
    
    <div class="head-right">
        <ul class="topmenu">
         <c:forEach items="${moduData }" var="v" varStatus="vs">
              <c:if test='${ v.link == "car_monitoring"}'>
                 <li><a href="javascript:void addTab('${v.name }','${v.link }')"><span><img src="images/newLogin/webcam.png"></span>${v.name }</a></li>
              </c:if>
               <%--<c:if test='${ v.link == "car_mrg"}'>--%>
                 <%--<li><a  href="javascript:void addTab('${v.name }','${v.link }')"><span><img src="images/newLogin/car.png"></span>车辆管理</a></li>--%>
              <%--</c:if>--%>
              <c:if test='${ v.link == "car_offline_apply"}'>
                 <li>
                 	<a style="position:relative;"  href="javascript:void addTab('${v.name }','${v.link }')">
                 		<span class="car_tip" style="color:red; position:absolute; width:70px; height:70px; left:10px; top:2px; line-height:41px; text-align:center; "></span>
                 		<img src="images/newLogin/car.png">
                 		<span>状态审核</span>	
                 	</a>
                 </li>
              </c:if>
               <c:if test='${ v.link == "member_d_examine"}'>
                 <li>
                 	<a style="position:relative;"  href="javascript:void addTab('${v.name }','${v.link }')">
                 		<span class="audit_tip" style="color:red; position:absolute; width:70px; height:70px; left:3px; top:0px; line-height:41px; text-align:center; "></span>
                 		<img src="images/newLogin/status_away.png">
                 		<span>${v.name }</span>	
                 	</a>
                 </li>
              </c:if>
               <c:if test='${ v.link == "member_page"}'>
                 <li><a href="javascript:void addTab('${v.name }','${v.link }')"><span><img src="images/newLogin/folder_user.png"></span>${v.name }</a></li>
              </c:if>
               <c:if test='${ v.link == "current_order"}'>
                 <li><a href="javascript:void addTab('${v.name }','${v.link }')"><span><img src="images/newLogin/table_go.png"></span>${v.name }</a></li>
              </c:if>
               <c:if test='${ v.link == "history_order"}'>
                 <li><a href="javascript:void addTab('${v.name }','${v.link }')"><span><img src="images/newLogin/text_padding_top.png"></span>${v.name }</a></li>
              </c:if>
        </c:forEach>
        
        <li><a href="http://i.umeng.com" target="_blank"><span><img src="images/umeng.png"></span>友盟统计</a></li>
        </ul>
        <em class="head-right-line"></em>
        <ul class="headbut">
<!--         	<li id="headbut-user" ><a href="#"> -->
<!--             	<img src="images/newLogin/topicon-user.png"> -->
<!--                 <div class="avatar" > -->
<!--                 	<span><img src="images/newLogin/avatar.png"></span> -->
<!--                     <h1>${sessionScope.user.name }</h1> -->
<!--                     <h2>${sessionScope.roleName_1 }</h2> -->
<!--                 </div> -->
<!--             </a></li> -->
            <li><a href="javascript:void addTab('预警信息','warning_info')" title="预警信息"><span class="head-tip"></span><img src="images/newLogin/topicon-bell.png"></a></li>
            <li><a href="javascript:void addTab('修改密码','modify_pwd_page')" title="修改密码"><img src="images/newLogin/topicon-lock.png"></a></li>
            <li><a href="/logout" title="安全退出"><img src="images/newLogin/topicon-power.png"></a></li>
        </ul>
    </div>
</div>