<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test='${ null == sessionScope.user}'>
	<c:redirect url="./SessionTimeOut.html"></c:redirect>
</c:if>

<script>
            $(function() {

                $('#menu').metisMenu();

                $('#menu2').metisMenu({
                    toggle: false
                });

                $('#menu3').metisMenu({
                    doubleTapToGo: true
                });
            });
</script>

<script>
    $(function(){
      /**给第一等级的li添加选中样式*/
   	$("#d2").on("click","#d1", function() {
	  if($(this).children("li").hasClass("active")){   	
      $(this).addClass("checked").siblings().removeClass("checked");
      $(this).children().children("a").removeClass("FontBlue").addClass("checkedFont"); 
      }else{
      $(this).removeClass("checked");
      $(this).children().children("a").removeClass("checkedFont").addClass("FontBlue"); 
      $(this).children().children().children().children("#l1").removeClass("checkedSecond");
      }
      /**删除展开的li的active class属性 */
      if(!($(this).siblings().hasClass("checked"))){
      	$(this).siblings().children("li").removeClass("active");
      }
      /**让失去active状态的Li 的ul元素合起来 */
      if(!($(this).siblings().children("li").hasClass("active"))){
     	$(this).siblings().children("li").children("#u1").removeClass("collapse in");
     	$(this).siblings().children("li").children("#u1").addClass("collapse");
     	$(this).siblings().children("li").children("ul").children().children("li").removeClass("active checkedSecond");
     	$(this).siblings().children("li").children("ul").children().children().children("ul").removeClass("collapse in");
     	$(this).siblings().children("li").children("ul").children().children().children("ul").addClass("collapse");
     	/**删除掉合起来的li选中元素*/
     	 $(this).siblings().children().children().children().children("#l1").removeClass("checkedSecond");
     	  $(this).siblings().children().children().children().children("#l4").removeClass("checkedSecond");
      }  
    });
      
      /**给第二级(不含第三等级)的li设置选中样式*/
      $("#d2").on("click","#l1", function(){
      $(this).addClass("checkedSecond").parent().parent().siblings().children().children("li").removeClass("checkedSecond");
      });
      
      /**给第二级(含第三等级)的li设置选中样式*/
      $("#d2").on("click","#l4", function(){
      if($(this).hasClass("active")){
       $(this).addClass("checkedSecond").parent().parent().siblings().children().children("li").removeClass("checkedSecond");
      }else{
        $(this).removeClass("checkedSecond"); 
        
      }
      if(!($(this).parent().parent().siblings().children().children("li").hasClass("active checkedSecond"))){
      	$(this).parent().parent().siblings().children().children("li").removeClass("active");
      }
      /**让失去active状态的Li 的ul元素合起来 */
      if(!($(this).parent().parent().siblings().children().children("li").hasClass("active"))){
     	$(this).parent().parent().siblings().children().children("li").children("ul").removeClass("collapse in");
     	$(this).parent().parent().siblings().children().children("li").children("ul").addClass("collapse");
     	/**删除掉合起来的li选中元素*/
     	$(this).parent().parent().siblings().children().children("li").children("ul").children("li").removeClass("checkedThree").addClass("cl6"); 
      }  
      
      });
      /**给第三级的Li设置选中样式*/
      $("#d2").on("click","#l6", function(){
      $(this).removeClass("cl6").addClass("checkedThree").parent().siblings().children("li").removeClass("checkedThree").addClass("cl6");
       
      });
 	});
   
</script>

<style>
body{    font-family: "微软雅黑", "Helvetica Neue", Helvetica, Arial, sans-serif;
   
}
.l-btn{
	border: 0px solid #AED0EA; 
	background:;
}
</style>
<body>
	<div class="sucaihuo-container"> 
		
			<div class="clearfix">
				<!-- <aside class="sidebar"> -->
					  <nav class="sidebar-nav"> 
						<ul id="menu"> 
						  <div id="d2">
							<c:forEach items="${moduData }" var="v" varStatus="vs">
							  
								  <%-- <c:if test="${ v.grade == 1 and  vs.index != 0}">content
					          			 </li>
								  </c:if>   --%>  
									
									 <c:if test="${ v.grade == 1}">
									   <div id="d1">
										 <li id="l2"> 
											<a  href="#" style="font-size:12px;font-weight: bold;" class="FontBlue">
											<span style="margin-left:0px;" class="l-btn-icon ${v.iconClass }"></span><span style="margin-left:30px;">${v.name }</span>
											</a>
											<c:forEach items="${moduData }" var="se">
											  
												<c:if test="${ se.grade == 2 and se.pid == v.id }">
												  	<c:if test = "${se.link != null and !(empty se.link) }">
														<ul id="u1">
														 <div id="d6">
					                                        <li id="l1">
					                                            <a class="secondLevelFont" href="javascript:void addTab('${se.name }','${se.link }')">${se.name }</a>
					                                            <c:forEach items="${moduData }" var="th">
						                                        	<c:if test="${ th.grade == 3 and th.pid == se.id }">
							                                        	<ul>
							                                                <li id="l3"><a href="javascript:void addTab('${th.name }','${th.link }')">${th.name }</a></li>
							                                            </ul>
						                                            </c:if>
					                                            </c:forEach>
					                                        </li>
					                                     </div>  
					                                    </ul>
				                                  	</c:if>
				                                  	<c:if test = "${se.link == null or  empty se.link }">
				                                  		<ul id="u1">
														 <div id="d6">
					                                        <li id="l4">
					                                            <a style="color:rgb(39, 121, 170);font-weight:bold;" href="javascript:void addTab('${se.name }','${se.link }')">${se.name }</a>
					                                            <c:forEach items="${moduData }" var="th">
						                                        	<c:if test="${ th.grade == 3 and th.pid == se.id and th.name != '车辆周边城市管理' }">
							                                        	<ul>
							                                                <li id="l6" class="cl6"><a href="javascript:void addTab('${th.name }','${th.link }')">${th.name }</a></li>
							                                            </ul>
						                                            </c:if>
					                                            </c:forEach>
					                                        </li>
					                                     </div>  
					                                    </ul>
				                                  	</c:if>
			                                    </c:if>
			                                  
			                                 </c:forEach>   
										
										</li> 
									  </div>	
	                                </c:if>
	                            
	                          </c:forEach>
	                        </div> 
                          </ul>     
				       </nav>
			<!-- 	</aside> -->
				
			</div>
	
	</div>
</body>  
