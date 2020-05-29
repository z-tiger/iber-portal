  <%@ page language="java" pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <html>
	<head>
	   <meta charset="UTF-8">
		<title>每日运营数据报表</title>
		<link rel="stylesheet" type="text/css" href="ui_lib/css/themes/ui-cupertino/easyui.css">
        <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/icon.css">
        <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/color.css">
        <link rel="stylesheet" type="text/css" href="ui_lib/css/themes/table.css">
        <script type="text/javascript" src="ui_lib/js/jquery.min.js"></script>
        <script type="text/javascript" src="ui_lib/js/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="ui_lib/js/easyui-lang-zh_CN.js"></script>
	    <script type="text/javascript" src="ui_lib/js/validator-extend.js" charset="gb2312"></script>
<!-- 	    <script type="text/javascript" src="operationReport/common.js"></script> -->
<!-- 	    <script type="text/javascript" src="operationReport/runingDayReport.js"></script>	      -->
<script type="text/javascript">
$(function(){
   $("#btnExport").bind("click", function(){
      var _queryTime = $("#queryTime").datebox("getValue");
      window.location.href = "runingDayReport?queryTime="+_queryTime;
   });
});
</script>
  </head>
    
   <body>
       <div align="center">
        <table class="gridtable" style="width: 100%; ">
            <form id="data" method="post" action="#" enctype="multipart/form-data" >
            <tr>
              <td colspan="3"><b>每日运营数据报表</b>
                                           选择时间:<input id="queryTime"  name="queryTime"  value="${queryTime }" class="easyui-datebox"/>
               <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" plain="true"  id="btnExport">查询</a>
               </td>
           </tr>
            </form>
            
             <tr>
              <td colspan="3">数据统计时间: <br/>
              本月： <span id="monthStartDate" style="font-size: 10pt;">${queryMonthTime }</span><br/>
                                    今日：<span id="currentBeginTime" style="font-size: 10pt;">${queryDaySTime }</span> 到  <span id="currentEndTime" style="font-size: 10pt;">${queryDayETime }</span>
                </td>               
           </tr>  
           
           <!-- 会员 -->
         
           <tr>
               <td rowspan="8">会员</td>
               <td>本月注册会员</td>
               <td>${month_regitster_counts } 
                </td>
           </tr> 
            <tr>
               <td>今日注册会员</td>
               <td>${day_regitster_counts }
                </td>
           </tr> 

           <tr>
               <td>本月APP登录次数</td>
               <td>--</td>
           </tr>
            <tr>
               <td>今日APP登录次数</td>
               <td>--</td>
           </tr>
           <tr>
               <td>本月微信登录次数</td>
               <td>--</td>
           </tr>
            <tr>
               <td>今日微信登录次数</td>
               <td>--</td>
           </tr>     
            <tr>
               <td>本月充值会员</td>
               <td>${countsMonthChargeMember }</td>
           </tr>
            <tr>
               <td>今日充值会员</td>
               <td>${countsDayChargeMember }</td>
           </tr>
           
           <!-- 用车 -->
           <tr>
               <td rowspan="6">用车</td>
               <td>车辆总数</td>
               <td>${countsCar }</td>
           </tr>
             
            <tr>
               <td>营运车辆总数</td>
               <td>${countsRunCar }</td>   
           </tr>
         
               
           <tr>
               <td>本月用车次数</td>
               <td>${countsMonthUseCarRecords }</td> 
           </tr>
    
           <tr>
               <td>今日用车次数</td>
                <td>${countsDayUseCarRecords }</td> 
           </tr>

           <tr>
               <td>本月用车时长(分钟)</td>
               <td>${countsMonthUseCarTimes }</td>
           </tr>
           <tr>
               <td>今日用车时长(分钟)</td>
                <td>${countsDayUseCarTimes }</td>
           </tr>
    </table>
   </div>
   </body>
 </html>