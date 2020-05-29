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
 * 地锁说明
 */
@Controller
public class parkLockController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 地锁说明页面
	 */
	
	@SystemServiceLog(description="地锁说明页面")
	@RequestMapping(value = "/parkLock", method = { RequestMethod.GET })
	public String pricesPage(HttpServletRequest request, HttpServletResponse response){
		log.info("地锁说明页面");
		return "app/jsp/parkLock";
	}
}
