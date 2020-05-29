package com.iber.portal.controller.city;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.City;
import com.iber.portal.service.base.CityService;

@Controller
public class CityManageController extends MainController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityService cityService;
	/**
	 * 城市管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="城市管理")
	@RequestMapping(value = "/city_manage_page", method = { RequestMethod.GET })
	public String city_manage_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "city/citylist";
	}
	/**
	 * 城市树形查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="城市树形查询")
	@RequestMapping(value = "/city_manage_page_buidTree", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String city_manage_page_buidTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<City> cityList = cityService.selectAllCityList();
		response.getWriter().print(createTreeJson(cityList));
		return null;
	}

	private String createTreeJson(List<City> cityList) {
		JSONArray rootArray = new JSONArray();
		for(City city : cityList){
			if(city.getpId()==null){
				JSONObject rootObj = createBranch(cityList, city);
				rootArray.add(rootObj);
			}
		} 
		return rootArray.toString();
	}
	
	private JSONObject createBranch(List<City> list, City currentNode) {
		/*
		 * 将javabean对象解析成为JSON对象
		 */
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject currentObj = JSONObject.fromObject(currentNode, jsonConfig);
		JSONArray childArray = new JSONArray();
		/*
		 * 循环遍历原始数据列表，判断列表中某对象的父id值是否等于当前节点的id值，
		 * 如果相等，进入递归创建新节点的子节点，直至无子节点时，返回节点，并将该
		 * 
		 * 节点放入当前节点的子节点列表中
		 */
		for (int i = 0; i < list.size(); i++) {
			City newNode = list.get(i);
			if (newNode.getpId() != null
					&& newNode.getpId().compareTo(currentNode.getId()) == 0) {
				JSONObject childObj = createBranch(list, newNode);
				childArray.add(childObj);
			}
		}
		
		

		/*
		 * 判断当前子节点数组是否为空，不为空将子节点数组加入children字段中
		 */
		if (!childArray.isEmpty()) {
			currentObj.put("children", childArray);
		}

		return currentObj;
	}
	
	/**
	 * @describe 保存城市信息
	 * 
	 */
	@SystemServiceLog(description="保存城市信息")
	@RequestMapping(value = "/city_update", method = { RequestMethod.GET , RequestMethod.POST })
	public String couponSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存城市信息");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		int intid = Integer.parseInt(id);
		//String cityCode = request.getParameter("city_code");
		String startTime = request.getParameter("start_time");
		String endTime = request.getParameter("end_time");
		String status = request.getParameter("city_status");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dstar = sdf.parse(startTime);
		Date dend = sdf.parse(endTime);
		City city = cityService.selectByPrimaryKey(intid);
		city.setStatus(status);
		city.setStartTime(dstar);
		city.setEndTime(dend);
		int index = cityService.updateByPrimaryKeySelective(city);
		if(index!=0){
			response.getWriter().print("success");
		}
		return null;	
	}
	

}
