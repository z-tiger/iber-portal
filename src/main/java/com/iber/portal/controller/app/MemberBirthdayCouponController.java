package com.iber.portal.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;

/**
 * 生日券说明
 */
@Controller
public class MemberBirthdayCouponController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 生日券说明
	 */
	
	@SystemServiceLog(description="生日券说明页面")
	@RequestMapping(value = "/memberBirthdayCoupon", method = { RequestMethod.GET })
	public String pricesPage(HttpServletRequest request, HttpServletResponse response){
		log.info("生日券说明页面");
		return "app/jsp/memberBirthdayCoupon";
	}
}
