package com.iber.portal.controller.car;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;
import com.iber.portal.controller.member.MemberRunPandectController;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;

@Controller
public class carRunPandectController extends MainController{
private final static Logger log= Logger.getLogger(MemberRunPandectController.class);
	
	@Autowired
	private CityService cityService ;

	@SystemServiceLog(description="车辆运营总览")
	@RequestMapping(value = "/carRunPandect_page", method = { RequestMethod.GET })
	public String car_catch_record(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = user.getCityCode();
		
		String cityName = cityService.queryNameByCode(cityCode);
	    HttpSession session = request.getSession();  
	    session.setAttribute("cityCode",cityCode);  
	    if(cityName !=null && !("".equals(cityName))){
	    	session.setAttribute("cityName",cityName);
	    }
		return "car/carRunPandect";		
	}
	
}
