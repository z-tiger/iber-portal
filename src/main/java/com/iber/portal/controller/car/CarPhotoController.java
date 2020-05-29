package com.iber.portal.controller.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.car.CarPhotoService;
import com.iber.portal.vo.car.CarPhotoVo;

@Controller
public class CarPhotoController extends MainController{

	 /**
	  * 车辆抓拍记录
	  */
	@Autowired
	private CarPhotoService carPhotoService;
	@SystemServiceLog(description="车辆抓拍记录")
	@RequestMapping(value = "/car_catch_record", method = { RequestMethod.GET })
	public String car_catch_record(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/carPhoto";		
	}
	 /**
	  * 车辆抓拍记录列表
	  */
	@SystemServiceLog(description="车辆抓拍记录列表")
	@RequestMapping(value = "/car_photo_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String car_photo_list(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String cityCode = request.getParameter("cityCode");
		String lpn = request.getParameter("lpn");
		map.put("lpn", lpn);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(cityCode)){
	         	if(cityCode.equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", cityCode);
	         	}
	         }
        }else{
    	   map.put("cityCode", user.getCityCode());
        }
	    if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
	       	String startTime = request.getParameter("queryDateFrom");
	       	map.put("queryDateFrom", startTime+" 00:00:00");
        }
        if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
	       	String endTime = request.getParameter("queryDateTo");
	       	map.put("queryDateTo", endTime+" 23:59:59");
        }
		
		List<CarPhotoVo> data = carPhotoService.selectAll(map);
		int totalRecords = carPhotoService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
}
