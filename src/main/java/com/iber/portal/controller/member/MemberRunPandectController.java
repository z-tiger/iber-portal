package com.iber.portal.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.member.MemberRightsService;
import com.iber.portal.service.sys.SysParamService;


@Controller
public class MemberRunPandectController extends MainController{
	
	private final static Logger log= Logger.getLogger(MemberRunPandectController.class);
	
	@Autowired
	private CityService cityService ;
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="会员权益管理页面")
	@RequestMapping("/memberRunPandect_page") 
	public String memberRights_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("会员统计页面");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = user.getCityCode();
		
		String cityName = cityService.queryNameByCode(cityCode);
	    HttpSession session = request.getSession();  
	    session.setAttribute("cityCode",cityCode);  
	    if(cityName !=null && !("".equals(cityName))){
	    	session.setAttribute("cityName",cityName);
	    }
		return "member/memberRunPandect" ;
	}
	
}
