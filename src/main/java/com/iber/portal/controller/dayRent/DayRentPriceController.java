package com.iber.portal.controller.dayRent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.alibaba.fastjson.JSONObject;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.dayRent.DayRentPrice;
import com.iber.portal.model.dayRent.DayRentPriceDetail;
import com.iber.portal.model.dayRent.DayRentPriceFestival;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.dayRent.DayRentPriceDetailService;
import com.iber.portal.service.dayRent.DayRentPriceFestivalService;
import com.iber.portal.service.dayRent.DayRentPriceService;


@Controller
public class DayRentPriceController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private DayRentPriceService dayRentPriceService;
	
	@Autowired
	private DayRentPriceDetailService dayRentPriceDetailService;
	
	@Autowired
	private DayRentPriceFestivalService dayRentPriceFestivalService;
	
	/**
	 * @describe 日租价格页面
	 * 
	 */
	@SystemServiceLog(description="日租价格页面")
	@RequestMapping(value = "/dayRent_price_page", method = { RequestMethod.GET })
	public String dayRentPrice(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租价格页面");
		return "dayRent/dayRentPrice";		
	}
	
	/**
	 * @describe 日租价格列表
	 */
	@SystemServiceLog(description="日租价格列表")
	@RequestMapping(value = "/dayRent_price_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租价格列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String modelId = request.getParameter("car_type_id");
		//String cityCode = request.getParameter("city_code");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		if(!"".equals(modelId) && null!=modelId){
			paramMap.put("modelId", Integer.parseInt(modelId));
		}else{
			paramMap.put("modelId", "");
		}
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
    	   paramMap.put("cityCode", user.getCityCode());
       }
		//paramMap.put("cityCode", cityCode);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<DayRentPrice> pager = dayRentPriceService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新日租价格
	 * 
	 */
	@SystemServiceLog(description="保存更新日租价格")
	@RequestMapping(value = "/dayRent_price_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新日租价格");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String cityCode = request.getParameter("city_code");
		String modelId = request.getParameter("model_id");
		String ordinaryCoefficient = request.getParameter("ordinary_coefficient");
		String weekedCoefficient = request.getParameter("weeked_coefficient");
		String festivalCoefficient = request.getParameter("festival_coefficient");
		String basePrice = request.getParameter("base_price");
		String insurancePrice = request.getParameter("insurance_price");
		String procedurePrice = request.getParameter("procedure_price");
		String freeCompensationPrice = request.getParameter("free_compensation_price");
		String timeoutPrice = request.getParameter("timeout_price");
		String remotePrice = request.getParameter("remote_price");
		SysUser user = getUser(request);
		if (id!=null && !id.equals("")) {
			DayRentPrice currObj = dayRentPriceService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setBasePrice(Integer.parseInt(basePrice) * 100);
				currObj.setCityCode(cityCode);
				currObj.setUpdateId(user.getId());
				currObj.setUpdateCreate(new Date());
				currObj.setFestivalCoefficient(Double.parseDouble(festivalCoefficient));
				currObj.setFreeCompensationPrice(Integer.parseInt(freeCompensationPrice) * 100);
				currObj.setInsurancePrice(Integer.parseInt(insurancePrice) * 100);
				currObj.setModelId(Integer.parseInt(modelId));
				currObj.setOrdinaryCoefficient(Double.parseDouble(ordinaryCoefficient));
				currObj.setProcedurePrice(Integer.parseInt(procedurePrice) * 100);
				currObj.setTimeoutPrice(Integer.parseInt(timeoutPrice) * 100);
				currObj.setRemotePrice(Integer.parseInt(remotePrice) * 100);
				currObj.setWeekedCoefficient(Double.parseDouble(weekedCoefficient));
				dayRentPriceService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			DayRentPrice obj = new DayRentPrice();
			obj.setBasePrice(Integer.parseInt(basePrice) * 100);
			obj.setCityCode(cityCode);
			obj.setCreateId(user.getId());
			obj.setCreateTime(new Date());
			obj.setFestivalCoefficient(Double.parseDouble(festivalCoefficient));
			obj.setFreeCompensationPrice(Integer.parseInt(freeCompensationPrice) * 100);
			obj.setInsurancePrice(Integer.parseInt(insurancePrice) * 100);
			obj.setModelId(Integer.parseInt(modelId));
			obj.setOrdinaryCoefficient(Double.parseDouble(ordinaryCoefficient));
			obj.setProcedurePrice(Integer.parseInt(procedurePrice) * 100);
			obj.setTimeoutPrice(Integer.parseInt(timeoutPrice) * 100);
			obj.setRemotePrice(Integer.parseInt(remotePrice) * 100);
			obj.setWeekedCoefficient(Double.parseDouble(weekedCoefficient));
			
			dayRentPriceService.insertSelective(obj);
	
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除日租价格
	 * 
	 */
	@SystemServiceLog(description="删除日租价格")
	@RequestMapping(value = "/dayRent_price_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除日租价格");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			dayRentPriceService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 生成价格
	 * 
	 */
	@SystemServiceLog(description="生成价格")
	@RequestMapping(value = "/dayRent_price_add", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceAdd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("生成价格");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("add_id");
		String addYear = request.getParameter("add_year");
		if (id!=null && !id.equals("") && addYear!=null && !addYear.equals("")) {
			DayRentPrice currObj = dayRentPriceService.selectByPrimaryKey(Integer.parseInt(id));
			String cityCode = currObj.getCityCode();
			int modelId = currObj.getModelId();
			double ordinaryCoefficient = currObj.getOrdinaryCoefficient();
			double weekedCoefficient = currObj.getWeekedCoefficient();
			double festivalCoefficient = currObj.getFestivalCoefficient();
			int basePrice = currObj.getBasePrice();
			int insurancePrice = currObj.getInsurancePrice();
			int procedurePrice = currObj.getProcedurePrice();
			int freeCompensationPrice = currObj.getFreeCompensationPrice();
			int timeoutPrice = currObj.getTimeoutPrice();
			int remotePrice = currObj.getRemotePrice();
			SysUser user = getUser(request);
			int year = Integer.parseInt(addYear);//定义一个字段，接收输入的年份
			Calendar calendar = new GregorianCalendar();//定义一个日历，变量作为年初
			Calendar calendarEnd = new GregorianCalendar();//定义一个日历，变量作为年末
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);//设置年初的日期为1月1日
			calendarEnd.set(Calendar.YEAR, year);
			calendarEnd.set(Calendar.MONTH, 11);
			calendarEnd.set(Calendar.DAY_OF_MONTH, 31);//设置年末的日期为12月31日
			
			//获取节假日
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("queryDateFrom", sf.format(calendar.getTime()));
			paramMap.put("queryDateTo", sf.format(calendarEnd.getTime()));
			List<DayRentPriceFestival> dayRentPriceFestivals = dayRentPriceFestivalService.queryFestivalDays(paramMap);
			
			while(calendar.getTime().getTime()<=calendarEnd.getTime().getTime()){//用一整年的日期循环
				int actualPrice = (int) (basePrice * ordinaryCoefficient);
				String currDate = sf.format(calendar.getTime());
			    if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){//判断周末
			    	actualPrice = (int) (basePrice * weekedCoefficient);
			    }
			    if(dayRentPriceFestivals.size()>0){
			    	for (int i = 0; i < dayRentPriceFestivals.size(); i++) {
			    		DayRentPriceFestival dayRentPriceFestival = dayRentPriceFestivals.get(i);
			    		String festivalDate = sf.format(dayRentPriceFestival.getFestivalDate().getTime());
			    		if(festivalDate.equals(currDate)){
			    			actualPrice = (int) (basePrice * festivalCoefficient);
			    		}
					}
			    }
			    Map<String, Object> currMap = new HashMap<String, Object>();
			    currMap.put("cityCode", cityCode);
			    currMap.put("modeId", modelId);
			    currMap.put("currDate", currDate);
			    DayRentPriceDetail obj = dayRentPriceDetailService.selectBycurrDate(currMap);
			    if(null!= obj){
			    	obj.setActualPrice(actualPrice);
			    	obj.setBasePrice(basePrice);
			    	obj.setCityCode(cityCode);
			    	obj.setCurrDate(calendar.getTime());
			    	obj.setFreeCompensationPrice(freeCompensationPrice);
			    	obj.setInsurancePrice(insurancePrice);
			    	obj.setModelId(modelId);
			    	obj.setProcedurePrice(procedurePrice);
			    	obj.setRemotePrice(remotePrice);
			    	obj.setTimeoutPrice(timeoutPrice);
			    	obj.setUpdateTime(new Date());
			    	obj.setUpdateUser(user.getName());
			    	
			    	dayRentPriceDetailService.updateByPrimaryKeySelective(obj);
			    }else{
			    	DayRentPriceDetail dayRentPriceDetail = new DayRentPriceDetail();
			    	dayRentPriceDetail.setActualPrice(actualPrice);
			    	dayRentPriceDetail.setBasePrice(basePrice);
			    	dayRentPriceDetail.setCityCode(cityCode);
			    	dayRentPriceDetail.setCurrDate(calendar.getTime());
			    	dayRentPriceDetail.setFreeCompensationPrice(freeCompensationPrice);
			    	dayRentPriceDetail.setInsurancePrice(insurancePrice);
			    	dayRentPriceDetail.setModelId(modelId);
			    	dayRentPriceDetail.setProcedurePrice(procedurePrice);
			    	dayRentPriceDetail.setRemotePrice(remotePrice);
			    	dayRentPriceDetail.setTimeoutPrice(timeoutPrice);
			    	dayRentPriceDetail.setUpdateTime(new Date());
			    	dayRentPriceDetail.setUpdateUser(user.getName());
			    	
			    	dayRentPriceDetailService.insertSelective(dayRentPriceDetail);
			    }
			    calendar.add(Calendar.DAY_OF_MONTH, 1);//日期+1
			}
		}
		response.getWriter().print("success");
		return null;
	}
	/**
	 * @describe 查看日租价格
	 * 
	 */
	@SystemServiceLog(description="查看日租价格")
	@RequestMapping(value = "/dayRent_price_detail", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceDetail(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("查看日租价格");
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		if (id!=null && !id.equals("")) {
			DayRentPrice currObj = dayRentPriceService.selectByPrimaryKey(Integer.parseInt(id));
			String cityCode = currObj.getCityCode();
			int modelId = currObj.getModelId();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cityCode", cityCode);
			paramMap.put("modeId", modelId);
			List<DayRentPriceDetail> dayRentPriceDetails = dayRentPriceDetailService.queryDayRentPriceDetails(paramMap);
			json.put("msg", "success");
			json.put("data", dayRentPriceDetails);
		}else{
			json.put("msg", "fail");
			json.put("data", "");
		}
		response.getWriter().print(json);
		return null;
	}
	/**
	 * @describe 日租价格修改
	 * 
	 */
	@SystemServiceLog(description="日租价格修改")
	@RequestMapping(value = "/dayRent_price_update", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("查看日租价格");
		response.setContentType("text/html;charset=utf-8");
		String dateUpdate = request.getParameter("dateUpdate");
		String actualPrice = request.getParameter("actualPriceUpdate");
		String cityCode = request.getParameter("cityCodeUpdate");
		String modelId = request.getParameter("modelIdUpdate");
		SysUser user = getUser(request);
		Map<String, Object> currMap = new HashMap<String, Object>();
	    currMap.put("cityCode", cityCode);
	    currMap.put("modeId", Integer.parseInt(modelId));
	    currMap.put("currDate", dateUpdate);
	    DayRentPriceDetail obj = dayRentPriceDetailService.selectBycurrDate(currMap);
		JSONObject json = new JSONObject();
	    if(null!= obj){
	    	obj.setActualPrice(Integer.parseInt(actualPrice)*100);
	    	obj.setUpdateTime(new Date());
	    	obj.setUpdateUser(user.getName());
	    	dayRentPriceDetailService.updateByPrimaryKeySelective(obj);
	    	json.put("msg", "success");
			json.put("data", obj);
	    }else{
	    	json.put("msg", "fail");
			json.put("data", "");
	    }
		response.getWriter().print(json);
		return null;
	}
	
	
}
