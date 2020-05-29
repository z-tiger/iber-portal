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
 * 添加车牌说明
 */
@Controller
public class AddLpnController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@SystemServiceLog(description="添加车牌说明")
	@RequestMapping(value = "/addLpn", method = { RequestMethod.GET })
	public String pricesPage(HttpServletRequest request, HttpServletResponse response){
		log.info("添加车牌说明");
		return "app/jsp/addLpn";
	}
}
