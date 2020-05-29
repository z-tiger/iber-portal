package com.iber.portal.controller.city;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.WZAssociatedCitys;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.car.WZAssociatedCitysService;

@Controller
public class WzCityController extends MainController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CityService cityService;

	@Autowired
	private WZAssociatedCitysService wzAssociatedCitysService;

	/**
	 * 多城市违章管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "多城市违章管理")
	@RequestMapping(value = "/carWzSelect", method = { RequestMethod.GET })
	public String city_wzcity_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "car/carWzSelect";
	}

	/**
	 * 多城市违章列表
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "多城市违章列表 ")
	@RequestMapping(value = "/car_wzcity_list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String carWzCityList(int page, int rows, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		List<WZAssociatedCitys> list=new ArrayList<WZAssociatedCitys>();
		List<WZAssociatedCitys> data = wzAssociatedCitysService.selectAll(map);
		for(WZAssociatedCitys wzassociatedCitys:data){
			String[] associated=wzassociatedCitys.getAssociatedCity().split("\\,");
			String associatedCity="";
			for(int i=0;i<associated.length;i++){
				String code=associated[i].trim();
				String cityName=cityService.queryNameByCode(code);
				associatedCity=associatedCity+cityName+",";
				wzassociatedCitys.setAssociatedCity(associatedCity);
			}
			list.add(wzassociatedCitys);
		}
		int totalRecords = wzAssociatedCitysService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		/*
		 * map.put("type", type); map.put("typeName", typeName);
		 * map.put("cityCode", cityCode); map.put("brance", brance);
		 */
		// List<CarType> data = carTypeService.selectAll(map);
		// int totalRecords = carTypeService.selectAllRecords(map);
		// String json = Data2Jsp.Json2Jsp(data, totalRecords);
		// response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description = "城市列表")
	@RequestMapping(value = "/all_city_list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String allCityList(int page, int rows, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		List<City> data = cityService.selectAllCityPagelist(map);
		int totalRecords = cityService.selectAllCityPagelistRecords(map);	
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				City city = data.get(i);
				tree.append("{");
				tree.append("\"code\":\"" + city.getCode() + "\",");
				tree.append("\"text\":\"" + city.getName() + "\"");
				if (i < data.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");
		String json = Data2Jsp.Json2Jsp(tree.toString(), totalRecords);
		response.getWriter().print(json);
		return null;
	}
	/**
	 * 可选所属中心城市
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "可选所属中心城市")
	@RequestMapping(value = "/sys_wzcityCombobox", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String sysWzcityCombobox(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HttpSession sess = request.getSession();
		List<WZAssociatedCitys> wzAssociatedCitysList = (List<WZAssociatedCitys>) sess
				.getAttribute("WZAssociatedCitys");
		if (null == wzAssociatedCitysList) {
			wzAssociatedCitysList = wzAssociatedCitysService
					.selectAllAssociatedCitys();
		}
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (wzAssociatedCitysList != null && wzAssociatedCitysList.size() > 0) {
			for (int i = 0; i < wzAssociatedCitysList.size(); i++) {
				WZAssociatedCitys city = wzAssociatedCitysList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + city.getCityCode() + "\",");
				tree.append("\"text\":\"" + city.getCityName() + "\"");
				if (i < wzAssociatedCitysList.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");

		response.getWriter().print(tree.toString());
		return null;
	}

	/**
	 * 添加修改多城市
	 * 
	 * @param request
	 * @param response
	 * @param rearviewFile
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "添加修改多城市")
	@RequestMapping(value = "/add_wzcity_update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addWzcityUpdate(HttpServletRequest request,
			HttpServletResponse response, MultipartFile rearviewFile)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String id = request.getParameter("id");
		String associatedCity= request.getParameter("ids");
		String cityCode = request.getParameter("cityCode");
		//String associated = request.getParameter("associatedCity");
		String cityName = cityService.queryNameByCode(cityCode);
		if (cityName != null && !cityName.equals("")) {
			if (id != null && !id.equals("")) {
				WZAssociatedCitys currObj = wzAssociatedCitysService
						.selectByPrimaryId(Integer.parseInt(id));
				if (currObj != null) {
					currObj.setCityCode(cityCode);
					currObj.setCityName(cityName);
					currObj.setAssociatedCity(associatedCity);
					wzAssociatedCitysService
							.updateByPrimaryKeySelective(currObj);
				}
			} else {
				int count=wzAssociatedCitysService.queryIfRecord(cityCode);
				if(count==0){
				WZAssociatedCitys obj = new WZAssociatedCitys();
				obj.setCityCode(cityCode);
				obj.setCityName(cityName);
				obj.setAssociatedCity(associatedCity);
				wzAssociatedCitysService.insert(obj);
				}else{
					response.getWriter().print("defalt");
					return null;
				}
			}
			response.getWriter().print("success");
			return null;
		} else {
			response.getWriter().print("false");
			return null;
		}

	}

	/**
	 * 删除多城市违章
	 * 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "删除多城市违章")
	@RequestMapping(value = "/del_wzCity_list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String delWzCityList(String id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		if (id != null && !id.equals("")) {
			wzAssociatedCitysService.delete(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}

	@SystemServiceLog(description = "会员违章记录查询")
	@RequestMapping(value = "/wzCitySelect", method = { RequestMethod.GET })
	public String wzRecordsPage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return "car/wzCitySelect";
	}

	/*@Autowired
	private WZCitysQueryMapper wzCitysQueryMapper;
	@Autowired
	private WZQueryMapper wzQueryMapper;
	@Autowired
	private CityMapper cityMapper;

	@SystemServiceLog(description = "违章记录数据列表")
	@RequestMapping(value = "/wz_records_city_member", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String wzRecordsCityMember(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String type = request.getParameter("type").trim();
		//中心城市
		String cityCode = request.getParameter("cityCode");
		//所属城市
		String code = request.getParameter("code");
		String lpn = request.getParameter("lpn");
		String custName = request.getParameter("custName");
		String custPhone = request.getParameter("custPhone");
		String status = request.getParameter("status");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(code)){
	         	if(code.equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", code);
	         	}
	         }
      }else{
   	   map.put("cityCode", user.getCityCode());
      }
		// 查找附近的城市
		List<WZQueryVo> list = new ArrayList<WZQueryVo>();
		int totalRecords =0;
		if (cityCode != null && !cityCode.equals("")) {
			WZAssociatedCitys associated = wzAssociatedCitysService
					.selectCityByCode(cityCode);
			if (associated != null) {
				//截取字符串
				String[] city = associated.getAssociatedCity().split("\\,");
				for (int i = 0; i < city.length; i++) {
					String wzCityCode = city[i];
					WZCitysQuery cityQuery = wzCitysQueryMapper
							.queryWZCitysQueryByCityCode(wzCityCode);
					if (null != cityQuery) {
						map.put("city", cityQuery.getQueryCityCode());
						map.put("status", status);
						map.put("lpn", lpn);
						map.put("custName", custName);
						map.put("custPhone", custPhone);
						map.put("orderId", request.getParameter("orderId"));
						if(type.equals("1")){
							//员工
							List<WZQueryVo> data = wzQueryMapper.selectWZQueryEmployee(map);
							int totalRecord=wzQueryMapper.selectWZQueryEmployeeRecords(map);
							totalRecords=totalRecord+totalRecords;
							for (WZQueryVo wzQuery : data) {
								if(!list.contains(wzQuery)){
									wzQuery.setType(type);
									list.add(wzQuery);
								}
							}
						}else if(type.equals("2")){
							//会员
							List<WZQueryVo> data = wzQueryMapper.selectWZQueryMember(map);
							int totalRecord=wzQueryMapper.selectWZQueryMemberRecords(map);
							totalRecords=totalRecord+totalRecords;
							for (WZQueryVo wzQuery : data) {
								if(!list.contains(wzQuery)){
									wzQuery.setType(type);
									list.add(wzQuery);
								}
							}
						}else{
							//无员工判断
							List<WZQueryVo> data = wzQueryMapper.selectWZQuery(map);
							int totalRecord=wzQueryMapper.selectWZQueryRecords(map);
							totalRecords=totalRecord+totalRecords;
							for (WZQueryVo wzQuery : data) {
								if(!list.contains(wzQuery)){
									wzQuery.setType(type);
									list.add(wzQuery);
								}
							}
						}
						
					}
				}
			}
			String json = Data2Jsp.Json2Jsp(list, totalRecords);
			response.getWriter().print(json);
			return null;
		} else {
			map.put("status", status);
			map.put("lpn", lpn);
			map.put("custName", custName);
			map.put("custPhone", custPhone);
			map.put("orderId", request.getParameter("orderId"));
			map.put("type", type);
			if(type.equals("1")){
				List<WZQueryVo> data = wzQueryMapper.selectWZQueryEmployee(map);
				totalRecords = wzQueryMapper.selectWZQueryEmployeeRecords(map);
				for (WZQueryVo wzQuery : data) {
					if(!list.contains(wzQuery)){
						wzQuery.setType(type);
						list.add(wzQuery);
					}
				}
				
			}else if(type.equals("2")){
				List<WZQueryVo> data = wzQueryMapper.selectWZQueryMember(map);
				totalRecords = wzQueryMapper.selectWZQueryMemberRecords(map);
				for (WZQueryVo wzQuery : data) {
					if(!list.contains(wzQuery)){
						wzQuery.setType(type);
						list.add(wzQuery);
					}
				}
			}else{
			//所有的数据
			List<WZQueryVo> data = wzQueryMapper.selectWZQuery(map);
			totalRecords = wzQueryMapper.selectWZQueryRecords(map);
			String json = Data2Jsp.Json2Jsp(list, totalRecords);
			response.getWriter().print(json);
			}
			String json = Data2Jsp.Json2Jsp(list, totalRecords);
			response.getWriter().print(json);
			return null;
		}

	}*/
}
