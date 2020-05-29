package com.iber.portal.controller.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;

/**
 * 押金规则说明
 */
@Controller
public class DepositRuleController extends MainController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
/*	@Autowired
	private DepositService depositService;*/
	
	@SystemServiceLog(description="押金规则说明页面")
	@RequestMapping(value = "/depositRule")
	public String depositRule(HttpServletRequest request, HttpServletResponse response){
		log.info("押金规则说明页面");
		/*List<Deposit> depositList = depositService.getAllInfo();*/
		/*HttpSession session = request.getSession();  
	    session.setAttribute("depositList",depositList); */
		return "app/jsp/depositRule";
	}
}
