package com.iber.portal.controller.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.timeShare.TimeShareRate;
import com.iber.portal.service.timeShare.TimeShareRateService;

/**
 * 租车价格
 * @author ouxx
 * @since 2016-9-27 上午9:42:51
 *
 */
@Controller
@RequestMapping(value = "prices")
public class PricesController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 租赁价格服务
	 */
	@Autowired
	private TimeShareRateService timeShareRateService;
	@SystemServiceLog(description="租车价格页面")
	@RequestMapping(value = "/prices", method = { RequestMethod.GET })
	public String pricesPage(HttpServletRequest request, HttpServletResponse response){
		log.info("租车价格页面");
		return "app/jsp/prices";
	}
	
	
	/**
	 * 根据cityCode查车的租赁价格信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author ouxx
	 * @date 2016-9-27 上午11:23:17
	 */
	@SystemServiceLog(description="根据cityCode查车的租赁价格信息")	
	@RequestMapping(value = "/get_by_city_code", method = { RequestMethod.GET , RequestMethod.POST })
	public String getByCityCode(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		log.info("根据cityCode查车的租赁价格信息");
		response.setContentType("text/html;charset=utf-8");
		//cityCode若为空，则默认为东莞 441900
		String cityCode = StringUtils.isNotBlank(request.getParameter("cityCode")) ? 
				request.getParameter("cityCode") : "441900";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		Pager<TimeShareRate> ratePager = timeShareRateService.getAll(paramMap);
		List<TimeShareRate> rateList = ratePager.getDatas();
		request.setAttribute("timeShareRate", rateList);
//		response.getWriter().print(Data2Jsp.Json2Jsp(ratePager));
		
		return "app/jsp/prices";
	}
	
	/**
	 * 根据cityCode和carTypeId查车的租赁价格信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author ouxx
	 * @date 2016-9-27 上午11:23:22
	 */
	@SystemServiceLog(description="根据cityCode和carTypeId查车的租赁价格信息")	
	@RequestMapping(value = "/get_by_city_code_and_car_type_id", method = { RequestMethod.GET , RequestMethod.POST })
	public String getByCityCodeAndCarTypeId(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		log.info("根据cityCode和carTypeId查车的租赁价格信息");
		response.setContentType("text/html;charset=utf-8");
		//cityCode若为空，则默认为东莞 441900
		String cityCode = StringUtils.isNotBlank(request.getParameter("cityCode")) ? 
				request.getParameter("cityCode") : "441900";

		String carType = request.getParameter("carTypeId");
		if(StringUtils.isBlank(carType)){
			log.error("getByCityCodeAndCarTypeId carTypeId 为空");
			response.getWriter().print("fail");
			return null;
		}
		Integer carTypeId = Integer.parseInt(carType);
		
		TimeShareRate rate = timeShareRateService.getByCityCodeAndCarTypeId(cityCode, carTypeId);
		request.setAttribute("timeShareRate", rate);
		return "app/jsp/pricesDetail";
//		return JSONObject.toJSONString(rate);
	}
	
}
