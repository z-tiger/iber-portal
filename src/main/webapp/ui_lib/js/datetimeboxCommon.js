$(function(){
			//开始时间默认00:00:00
		    $(".s").datetimebox({  
    			required : false,  
   			 	onShowPanel:function(){  
        		$(this).datetimebox("spinner").timespinner("setValue","00:00:00");
    			}  
			});
			//结束时间默认23:59:59  
		   $(".e").datetimebox({  
    			required : false,  
   			 	onShowPanel:function(){  
        		$(this).datetimebox("spinner").timespinner("setValue","23:59:59");  
    			} 
			});  
}); 