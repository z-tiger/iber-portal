package com.iber.portal.controller.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.ad.AdPoint;
import com.iber.portal.model.base.City;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.ad.AdPointService;
import com.iber.portal.service.base.CityService;


@Controller
public class AdPointController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AdPointService adPointService;
	
	@Autowired
	private CityService cityService ;
	
	/**
	 * @describe 广告投放点页面
	 * 
	 */
	@SystemServiceLog(description="广告投放点页面")
	@RequestMapping(value = "/ad_point_page", method = { RequestMethod.GET })
	public String adPoint(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告投放点页面");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		City city = cityService.queryCityByCode(user.getCityCode()).get(0);
		request.setAttribute("latitude", city.getLatitude());
		request.setAttribute("longitude", city.getLongitude());
		request.setAttribute("cityName", city.getName());
		return "ad/adPoint";		
	}
	
	/**
	 * @describe 广告投放点列表
	 */
	@SystemServiceLog(description="广告投放点列表")
	@RequestMapping(value = "/ad_point_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPointList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告投放点列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String adpointname = request.getParameter("adpointname");
		//String area_code = request.getParameter("area_code");
		String adpointadd = request.getParameter("adpointadd");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("area_code"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", request.getParameter("area_code"));
	         	}
	         }
       }else{
    	   paramMap.put("cityCode", user.getCityCode());
       }
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("adPointName", adpointname);
		//paramMap.put("cityCode", area_code);
		paramMap.put("adPointAddress", adpointadd);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<AdPoint> pager = adPointService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新广告投放点
	 * 
	 */
	@SystemServiceLog(description="保存更新广告投放点")
	@RequestMapping(value = "/ad_point_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPointSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新广告投放点");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String adPointName = request.getParameter("adpointname");
		String cityCode = request.getParameter("area_code");
		String adPointAddress = request.getParameter("adpointadd");
		String limits = request.getParameter("limits");
		String gps = request.getParameter("coordinate");
		if (id!=null && !id.equals("")) {
			AdPoint currObj = adPointService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setAdPointAddress(adPointAddress);
				currObj.setAdPointName(adPointName);
				currObj.setCityCode(cityCode);
				currObj.setCreateTime(new Date());
				currObj.setGps(gps);
				currObj.setLimits(Integer.parseInt(limits));
				adPointService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			AdPoint obj = new AdPoint();
			obj.setAdPointAddress(adPointAddress);
			obj.setAdPointName(adPointName);
			obj.setCityCode(cityCode);
			obj.setCreateTime(new Date());
			obj.setGps(gps);
			obj.setLimits(Integer.parseInt(limits));
			adPointService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除广告投放点
	 * 
	 */
	@SystemServiceLog(description="删除广告投放点")
	@RequestMapping(value = "/ad_point_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPointDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除广告投放点");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			adPointService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
}
