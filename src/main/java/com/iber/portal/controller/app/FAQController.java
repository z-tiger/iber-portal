package com.iber.portal.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;

@Controller
public class FAQController {
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
    @SystemServiceLog(description="网点介绍页面")
	@RequestMapping("/showFAQ")
	public String pricesPage(HttpServletRequest request, HttpServletResponse response){
		log.info(" 网点介绍页面");
		return "app/jsp/FAQ";
	}
}
