package com.iber.portal.controller.timeShare;

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
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareRate;
import com.iber.portal.service.timeShare.TimeShareRateService;
import com.iber.portal.util.DateTime;


@Controller
public class TimeShareRateController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private TimeShareRateService rateService;
	
	/**
	 * @describe 计费策略页面
	 * 
	 */
	@SystemServiceLog(description="计费策略页面")
	@RequestMapping(value = "/timeshare_rate", method = { RequestMethod.GET })
	public String timeShareRate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("计费策略页面");
		return "timeShare/timeShareRate";		
	}
	
	/**
	 * @describe 计费策略列表
	 */
	@SystemServiceLog(description="计费策略列表")
	@RequestMapping(value = "/timeshare_rate_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String timeShareRateList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("计费策略列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String name = request.getParameter("name");
		//String city_code = request.getParameter("city_code");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("city_code"))){
	         	if(request.getParameter("city_code").equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", request.getParameter("city_code"));
	         	}
	         }
       }else{
    	   paramMap.put("cityCode", user.getCityCode());
       }
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("name", name);
		//paramMap.put("cityCode", city_code);
		Pager<TimeShareRate> pager = rateService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新计费策略
	 * 
	 */
	@SystemServiceLog(description="保存更新计费策略")
	@RequestMapping(value = "/timeshare_rate_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String timeShareRateSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新计费策略");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String cityCode = request.getParameter("city_code");
		String name = request.getParameter("name");
		String carTypeId = request.getParameter("car_type_id");
		String timeUnit = request.getParameter("time_unit");
		String timeRate = request.getParameter("time_rate");
		String milesRate = request.getParameter("miles_rate");
		String discount = request.getParameter("discount");
//		String timeDiscount = request.getParameter("time_discount");
		String milesDiscount = request.getParameter("miles_discount");
		String minConsump = request.getParameter("min_consump");
		String maxConsump = request.getParameter("max_consump");
		String status = request.getParameter("status");
		String description = request.getParameter("description");
		String freeCompensationPrice = request.getParameter("freeCompensationPrice");
		String disStartTime = request.getParameter("dis_start_time");
		String disEndTime = request.getParameter("dis_end_time");
		Date firstTime = DateTime.StringToDate(disStartTime);
		Date finishTime = DateTime.StringToDate(disEndTime);
		if(firstTime.after(finishTime)){
			response.getWriter().print("timeError");
			return null;	
		}
		String maxFreeCompensationPrice = request.getParameter("max_free_compensation_price");
		String nightTimeRate = request.getParameter("night_time_rate");
		String nightMilesRate = request.getParameter("night_miles_rate");
		String timeDiscountRate = request.getParameter("timeDiscountRate");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			TimeShareRate currObj = rateService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCarTypeId(Integer.parseInt(carTypeId));
				currObj.setCityCode(cityCode);
				currObj.setDescription(description);
				currObj.setMaxConsump(Integer.parseInt(maxConsump)*100);
				currObj.setMilesDiscount(Integer.parseInt(milesDiscount));
				currObj.setMilesRate((int)(Double.parseDouble(milesRate)*100));
				currObj.setMinConsump(Integer.parseInt(minConsump)*100);
				currObj.setName(name);
				currObj.setOtherCost(0);
				currObj.setStatus(status);
				
				currObj.setTimeRate((int)(Double.parseDouble(timeRate)*100));
				currObj.setTimeUnit(Integer.parseInt(timeUnit));
				currObj.setUpdatedTime(new Date());
				currObj.setUpdatedUser(user.getName());
				currObj.setFreeCompensationPrice(Double.parseDouble(freeCompensationPrice)*100) ;
				currObj.setMaxFreeCompensationPrice(Double.parseDouble(maxFreeCompensationPrice)*100) ;
				currObj.setNightMilesRate((int)(Double.parseDouble(nightMilesRate)*100)) ;
				currObj.setNightTimeRate((int)(Double.parseDouble(nightTimeRate)*100)) ;
				currObj.setDiscountStartTime(DateTime.StringToDate(disStartTime));
				currObj.setDiscountEndTime(DateTime.StringToDate(disEndTime));
				currObj.setTimeDiscountRate((int)(Double.parseDouble(timeDiscountRate)*100));
				currObj.setDiscount(Integer.parseInt(discount));
				double timeDisc = Double.parseDouble(timeDiscountRate)*100*100/(Double.parseDouble(timeRate)*100);
				currObj.setTimeDiscount((int)timeDisc);
				rateService.updateByPrimaryKeySelective(currObj);
			}
		}else{		
			TimeShareRate obj = new TimeShareRate();
			obj.setCarTypeId(Integer.parseInt(carTypeId));
			obj.setCityCode(cityCode);
			obj.setCreatedTime(new Date());
			obj.setCreatedUser(user.getName());
			obj.setDescription(description);
			obj.setDiscount(Integer.parseInt(discount));
			obj.setMaxConsump(Integer.parseInt(maxConsump)*100);
			obj.setMilesDiscount(Integer.parseInt(milesDiscount));
			obj.setMilesRate((int)(Double.parseDouble(milesRate)*100));
			obj.setMinConsump(Integer.parseInt(minConsump)*100);
			obj.setName(name);
			obj.setOtherCost(0);
			obj.setStatus(status);
			
			obj.setTimeRate((int)(Double.parseDouble(timeRate)*100));
			obj.setTimeDiscountRate((int)(Double.parseDouble(timeDiscountRate)*100));
			
			obj.setTimeUnit(Integer.parseInt(timeUnit));
			obj.setFreeCompensationPrice(Double.parseDouble(freeCompensationPrice)*100) ;
			obj.setMaxFreeCompensationPrice(Double.parseDouble(maxFreeCompensationPrice)*100) ;
			obj.setNightMilesRate((int)(Double.parseDouble(nightMilesRate)*100)) ;
			obj.setNightTimeRate((int)(Double.parseDouble(nightTimeRate)*100)) ;
			obj.setDiscountStartTime(DateTime.StringToDate(disStartTime));
			obj.setDiscountEndTime(DateTime.StringToDate(disEndTime));
			obj.setTimeDiscount((int)Math.ceil(Double.parseDouble(timeDiscountRate)*100*100/(Double.parseDouble(timeRate)*100)));
			rateService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除计费策略
	 * 
	 */
	@SystemServiceLog(description="删除计费策略")
	@RequestMapping(value = "/timeshare_rate_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String timeShareRateDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除计费策略");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			rateService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
}
