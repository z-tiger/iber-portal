package com.iber.portal.controller.app;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.model.dayRent.DayRentPriceDetail;
import com.iber.portal.service.dayRent.DayRentPriceDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class DailyRentalController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DayRentPriceDetailService dayRentPriceDetailService;
    @SystemServiceLog(description="日租价格说明")
	@RequestMapping("/dailyRentalPrices")
	public String dailyRentalpricesPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("日租价格说明");
		//cityCode若为空，则默认为东莞 441900
		String cityCode = StringUtils.isNotBlank(request.getParameter("cityCode")) ? 
		request.getParameter("cityCode") : "441900";
		String carTypeIdString = request.getParameter("carTypeId");
		Integer carTypeId = null;
		if (carTypeIdString != null) {
			carTypeId = Integer.parseInt(request.getParameter("carTypeId"));
		}
		
		if(null == carTypeId){
			log.error("getByCityCodeAndCarTypeId carTypeId 为空");
			response.getWriter().print("fail");
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(new Date().getTime());
		Date date = formatter.parse(dateString);
		List<DayRentPriceDetail> dayRentPriceDetail = dayRentPriceDetailService.getByCityCodeAndCarTypeId(cityCode, carTypeId,date.getTime()/1000);
		request.setAttribute("dayRentPriceDetail", dayRentPriceDetail);
		return "app/jsp/dailyRentalPrices";
	}
    @SystemServiceLog(description="日租须知")
   	@RequestMapping("/dailyRentalDetail")
   	public String pricesPage(HttpServletRequest request, HttpServletResponse response){
   		log.info("日租须知");
   		return "app/jsp/dailyRentalDetail";
   	}
    @SystemServiceLog(description="充值协议")
    @RequestMapping("/rechargeAgreement")
    public String rechargeAgreement(HttpServletRequest request, HttpServletResponse response){
        log.info("充值协议");
        return "app/jsp/rechargeAgreement";
    }
    
    @SystemServiceLog(description = "会员权益现金券")
    @RequestMapping("/memberRight")
    public String memberRight(HttpServletRequest request, HttpServletResponse response){
    	log.info("会员权益现金券");
    	return "app/jsp/memberRight";
    }
    @SystemServiceLog(description = "会员权益折扣券")
    @RequestMapping("/discount")
    public String discount(HttpServletRequest request, HttpServletResponse response){
    	log.info("会员权益现金券");
    	return "app/jsp/discount";
    }
}
