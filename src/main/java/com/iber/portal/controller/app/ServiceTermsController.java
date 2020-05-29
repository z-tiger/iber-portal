package com.iber.portal.controller.app;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;


@Controller
public class ServiceTermsController {
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
	@SystemServiceLog(description="服务条款")
	@RequestMapping(value ="/serviceTerms")
	public String pricesPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("服务条款");
		return "app/jsp/serviceTerms";
	}
}