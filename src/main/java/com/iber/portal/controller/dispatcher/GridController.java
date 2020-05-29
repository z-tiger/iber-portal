package com.iber.portal.controller.dispatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.serializer.SerializerFeature;

import com.iber.portal.getui.PushClientEmployee;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.dispatcher.ElectronicGridGps;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.dispatcher.EmployeeGridRelation;
import com.iber.portal.model.dispatcher.Grid;
import com.iber.portal.model.dispatcher.GridParkRelation;
import com.iber.portal.model.dispatcherMonitor.ParkLocation;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.dispatcher.ElectronicGridGpsService;
import com.iber.portal.service.dispatcher.EmployeeGridRelationService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.dispatcher.GridParkRelationService;
import com.iber.portal.service.dispatcher.GridService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.util.PointUtil;
import com.iber.portal.vo.city.ExtendCity;

/**
 * 网格管理
 * @author Administrator
 *
 */
@Controller
public class GridController {
	
	@Autowired
	private GridService gridService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ParkService parkService;
	
	@Autowired
	private GridParkRelationService gridParkRelationService;
	
	@Autowired
	private EmployeeService employeeService;
	 
	@Autowired
	private EmployeeGridRelationService employeeGridRelationService;
	
	@Autowired
	private ElectronicGridGpsService electronicGridGpsService;
	
	public static final Integer IS_NOT_MANAGER = 2;
	
	/**
	 * 网格管理页面
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@SystemServiceLog(description = "网格管理页面")
	@RequestMapping(value = "grid_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/dispatcher/gridManagement";
	}
	
	/**
	 * 网格管理列表
	 */
	@SystemServiceLog(description = "网格管理列表")
	@RequestMapping(value = "grid_management_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		String gridName = request.getParameter("gridName");
		paramMap.put("gridName", gridName);
		Pager<Grid> pager = gridService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	/**
	 * 根据城市得到城市中心的经纬度
	 */
	@SystemServiceLog(description = "城市中心经纬度")
	@RequestMapping(value = "selectLocationByCityCode" , method = { RequestMethod.GET, RequestMethod.POST })
	public String selectLocationByCityCode(HttpServletRequest request, HttpServletResponse response, String cityCode) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<ExtendCity> extendCitys = cityService.selectLocationByCityCode(cityCode);
		getCity(response, extendCitys);
		return null;
	}
	
	/**
	 * 保存或者更新网格
	 */
	@SystemServiceLog(description = "保存或者更新网格")
	@RequestMapping(value = "saveOrUpdateGrid" , method = { RequestMethod.GET, RequestMethod.POST })
	public String saveOrUpdateGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8"); 
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		String fencePoint = request.getParameter("fencePoint");
		String gridName = request.getParameter("gridName");
		String cityCode = request.getParameter("cityCode");
		
		if (StringUtils.isBlank(cityCode)) {//如果新增的时候没有cityCode
			String latitude = (String) session.getAttribute("latitude");
			String longitude = (String) session.getAttribute("longitude");
			//根据经纬度获取城市
			if (!StringUtils.isBlank(latitude) && !StringUtils.isBlank(longitude)) {
				double latitudeDouble = Double.parseDouble(latitude);
				double longitudeDouble = Double.parseDouble(longitude);
				List<ExtendCity> extendCities = cityService.selectByLocation(latitudeDouble,longitudeDouble);
				cityCode = extendCities.get(0).getCode();
			}
		}
		//获得电子围栏所画区域的经纬度
		ArrayList<Double> latitudes = null;
		ArrayList<Double> longitudes = null;
		if (!StringUtils.isBlank(fencePoint)) {
			String[] points = fencePoint.split(",");
			latitudes = new ArrayList<Double>();
			longitudes = new ArrayList<Double>();
			for (String point : points) {
				String[] locations = point.split("#");
				latitudes.add(Double.parseDouble(locations[1]));
				longitudes.add(Double.parseDouble(locations[0]));
			}
		}
		//获得城市的所有网点
		List<Park> parks = parkService.selectParkByCityCode(cityCode);
		//获得在电子围栏区域中的所有网点，并将id保存到集合中
		ArrayList<Integer> parkIds = new ArrayList<Integer>();
		for (Park park : parks) {
			if (latitudes != null && longitudes != null) {
				boolean isInPolygon = PointUtil.isPointInPolygon(Double.parseDouble(park.getLongitude()), 
						Double.parseDouble(park.getLatitude()), longitudes, latitudes);
				if (isInPolygon) {//如果网点在网格内
					parkIds.add(park.getId());
				}
			}
		}
		//判断电子围栏区域中的网点是否已经存在于其他网格中，如果存在，直接返回
		if (parkIds.size() > 0) {
			List<GridParkRelation> relations = gridParkRelationService.selectByParkIds(parkIds);
			if (relations.size() > 0) {
				response.getWriter().print("contains");
				return null;
			}
		}
		Grid grid = new Grid();
		if (StringUtils.isBlank(id)) {//新增操作
			grid.setCityCode(cityCode);
			grid.setCreateTime(new Date());
			SysUser createUser = (SysUser) session.getAttribute("user");
			grid.setCreateUser(createUser.getName());
			grid.setName(gridName);
			grid.setCreateId(createUser.getId());
			gridService.save(grid);
			saveElevtronicGridAndBuildRelation(fencePoint, cityCode, grid, parks, longitudes, latitudes);
		}else {//编辑操作
			grid = gridService.selectByPrimaryKey(Integer.parseInt(id));
			cityCode = request.getParameter("cityCode");
			grid.setCityCode(cityCode);
			grid.setUpdateTime(new Date());
			grid.setName(gridName);
			SysUser updateUser = (SysUser) session.getAttribute("user");
			grid.setUpdateId(updateUser.getId());
			gridService.update(grid);
			saveElevtronicGridAndBuildRelation(fencePoint, cityCode, grid, parks, longitudes, latitudes);
		}
		
		response.getWriter().print("success");
		return null;
	}
	
	/**保存电子网格以及建立网格和网点的关系
	 * 
	 * @param fencePoint
	 * @param cityCode
	 * @param grid
	 * @param latitudes 
	 * @param longitudes 
	 * @param parks 
	 */
	private void saveElevtronicGridAndBuildRelation(String fencePoint,
			String cityCode, Grid grid, List<Park> parks, ArrayList<Double> longitudes, ArrayList<Double> latitudes) {
		//保存电子网格区域
		if (!StringUtils.isBlank(fencePoint)) {
			String[] points = fencePoint.split(",");
			for (String point : points) {
				ElectronicGridGps gps = new ElectronicGridGps();
				String[] locations = point.split("#");
				gps.setGridId(grid.getId());
				gps.setLatitude(Double.parseDouble(locations[1]));
				gps.setLongitude(Double.parseDouble(locations[0]));
				electronicGridGpsService.insert(gps);
			}
		}
		int parkNumber = 0;
		for (Park park : parks) {
			if (latitudes != null && longitudes != null) {
				boolean isInPolygon = PointUtil.isPointInPolygon(Double.parseDouble(park.getLongitude()), 
						Double.parseDouble(park.getLatitude()), longitudes, latitudes);
				if (isInPolygon) {//如果网点在网格内
					parkNumber = parkNumber + 1;
					//将网格和网点建立关系
					GridParkRelation relation = new GridParkRelation();
					relation.setGridId(grid.getId());
					relation.setParkId(park.getId());
					relation.setIsBuildByElecGrid(0);//通过电子网格创建关联关系
					gridParkRelationService.save(relation);
				}
			}
		}
	}
	
	/**
	 * 获取当前登陆人的位置信息
	 */
	@SystemServiceLog(description = "获取当前登陆人的位置信息")
	@RequestMapping(value = "get_login_location" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getLoginLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		//获得登录人的经纬度
		String latitude = (String) session.getAttribute("latitude");
		String longitude = (String) session.getAttribute("longitude");
		//根据经纬度获取城市
		if (!StringUtils.isBlank(latitude) && !StringUtils.isBlank(longitude)) {
			double latitudeDouble = Double.parseDouble(latitude);
			double longitudeDouble = Double.parseDouble(longitude);
			List<ExtendCity> extendCities = cityService.selectByLocation(latitudeDouble,longitudeDouble);
			getCity(response, extendCities);
		}
		return null;
	}
	/**
	 * 获得城市以及城市内所有网点
	 * @param response
	 * @param extendCitys
	 * @throws IOException
	 */
	private void getCity(HttpServletResponse response,
			List<ExtendCity> extendCitys) throws IOException {
		ArrayList<ParkLocation> locations = new ArrayList<ParkLocation>();
		for (ExtendCity extendCity : extendCitys) {
			if (!StringUtils.isBlank(extendCitys.get(0).getLatitude()) && !StringUtils.isBlank(extendCitys.get(0).getLongitude())) {
				ParkLocation location = new ParkLocation();
				location.setLatitude(Double.parseDouble(extendCitys.get(0).getLatitude()));
				location.setLongitude(Double.parseDouble(extendCitys.get(0).getLongitude()));
				if (!StringUtils.isBlank(extendCity.getParkLatitude()) && !StringUtils.isBlank(extendCity.getParkLongitude())) {
					location.setParkLatitude(Double.parseDouble(extendCity.getParkLatitude()));
					location.setParkLongitude(Double.parseDouble(extendCity.getParkLongitude()));
					location.setParkName(extendCity.getParkName());
					location.setCategory(extendCity.getCategory());
					location.setCooperationType(extendCity.getCooperationType());
				}
				locations.add(location);
			}
		}
		//禁用循环引用检测
		SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect;
		response.getWriter().print(JSON.toJSONString(locations));
	}
	
	/**
	 * 设置网点
	 */
	@SystemServiceLog(description = "设置网点")
	@RequestMapping(value = "managePark" , method = { RequestMethod.GET, RequestMethod.POST })
	public String managePark(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("设置网点");
		response.setContentType("text/html;charset=utf-8");
		String gridIdStr = request.getParameter("gridId");
		String parkId = request.getParameter("parkId");
		String specialComment = request.getParameter("specialComment");
		Integer gridId = Integer.parseInt(gridIdStr);
		List<GridParkRelation> gridParkRelations = gridParkRelationService.selectGridParkRelationByGridId(gridId);
		ArrayList<Integer> parkIdList = new ArrayList<Integer>();
		for (GridParkRelation gridParkRelation : gridParkRelations) {
			parkIdList.add(gridParkRelation.getParkId());
		}
		if (!StringUtils.isBlank(parkId)) {
			if (parkIdList.size() == 0 || !parkIdList.contains(Integer.parseInt(parkId))) {//如果该网格没有该网点
				GridParkRelation newRelation = new GridParkRelation();
				newRelation.setGridId(gridId);
				newRelation.setParkId(Integer.parseInt(parkId));
				newRelation.setIsBuildByElecGrid(1);//不是通过电子网格创建关联关系
				gridParkRelationService.save(newRelation);
			}
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 获得所有网点
	 */
	@SystemServiceLog(description = "获得所有网点")
	@RequestMapping(value = "get_all_park" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllPark(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String cityCode = request.getParameter("cityCode");
		//获得网格与网点关系表中不存在的网点
		List<Park> parks = parkService.selectExceptExistRelation(cityCode);
		response.getWriter().print(JSON.toJSONString(parks));
		return null;
	}
	
	/**
	 * 根据cityCode获得所有网点
	 */
	@SystemServiceLog(description = "根据cityCode获得所有网点")
	@RequestMapping(value = "getAllParkByCityCode" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllParkByCityCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String cityCode = request.getParameter("cityCode");
		//获得网格与网点关系表中不存在的网点
		List<Park> parks = parkService.selectExceptExistRelation(cityCode);
		//查询出超长订单临时网点
		List<Park> temporaryParks = parkService.selectTemporaryParks(cityCode);
		for (Park park : temporaryParks) {
			parks.add(park);
		}
		for (Park park : parks) {
			if(1==park.getIsCoexist()){
				park.setName(park.getName().concat("【多片区共存】"));
			}
		}
		response.getWriter().print(JSON.toJSONString(parks));
		return null;
	}
	
	/**
	 * 获得所有调度员
	 */
	@SystemServiceLog(description = "获得所有调度员")
	@RequestMapping(value = "get_all_dispatcher" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String cityCode = request.getParameter("cityCode");
		List<Employee> dispatchers = employeeService.selectExceptExistRelation(cityCode);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Employee employee : dispatchers) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", employee.getId());
			map.put("text", employee.getName());
			list.add(map);
		}
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
	/**
	 * 根据cityCode获得所有调度员
	 */
	@SystemServiceLog(description = "根据cityCode获得所有调度员")
	@RequestMapping(value = "getAllDispatcherByCityCode" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getAllDispatcherByCityCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String cityCode = request.getParameter("cityCode");
		List<Employee> dispatchers = employeeService.selectExceptExistRelation(cityCode);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (Employee employee : dispatchers) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", employee.getId());
			map.put("text", employee.getName());
			list.add(map);
		}
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
	/**
	 * 设置调度员
	 */
	@SystemServiceLog(description = "设置调度员")
	@RequestMapping(value = "manageDispatcher" , method = { RequestMethod.GET, RequestMethod.POST })
	public String manageDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String gridIdStr = request.getParameter("newGridId");
		String dispatcherIdStr = request.getParameter("dispatcherId");
		String specialComment = request.getParameter("specialComment");
		//获得该网格内所有调度员
		Integer gridId = Integer.parseInt(gridIdStr);
		List<EmployeeGridRelation> relations = employeeGridRelationService.selectEmployeeGridRelationByGridId(gridId);
		ArrayList<Integer> employeeIdList = new ArrayList<Integer>();
		for (EmployeeGridRelation employeeGridRelation : relations) {
			employeeIdList.add(employeeGridRelation.getEmployeeId());
		}
		if (!StringUtils.isBlank(dispatcherIdStr)) {
			if (employeeIdList.size() == 0 || !employeeIdList.contains(Integer.parseInt(dispatcherIdStr))) {
				EmployeeGridRelation newRelation = new EmployeeGridRelation();
				newRelation.setEmployeeId(Integer.parseInt(dispatcherIdStr));
				newRelation.setGridId(gridId);
				newRelation.setIsManager(IS_NOT_MANAGER);//默认不是网格管理员
				employeeGridRelationService.insert(newRelation);
				//设置调度员推送消息
				Employee employee = employeeService.selectByEmployeeId(Integer.parseInt(dispatcherIdStr));
				Grid grid = gridService.selectByPrimaryKey(gridId);
				PushCommonBean pushCommon = new PushCommonBean("server_push_employee_manage_dispatcher","1","您现在属于"+grid.getName(),employee) ;
				List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
				System.out.println(JSON.toJSON(pushCommon));
				for (String employeeCid : cidList) {
					PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
				}
			}
			
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 删除网格
	 */
	@SystemServiceLog(description = "删除网格")
	@RequestMapping(value = "deleteGrid" , method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		//删除网格
		gridService.deleteGrid(Integer.parseInt(id));
		//删除网格和网点的关联关系
		gridParkRelationService.batchDeleteAllParkByGridId(Integer.parseInt(id));
		//删除网格和调度员的关联关系
		employeeGridRelationService.batchDeleteEmployeeByGridId(Integer.parseInt(id));
		//删除网格对应的电子网格
		electronicGridGpsService.deleteByGridId(Integer.parseInt(id));
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 查询网格的所有描点，用于编辑界面的地图描点
	 */
	@SystemServiceLog(description = "查询网格的所有描点，用于编辑界面的地图描点")
	@RequestMapping(value = "selectElectronicGridGpsByGridId" , method = { RequestMethod.GET, RequestMethod.POST })
	public String selectElectronicGridGpsByGridId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String gridId = request.getParameter("gridId");
		List<ElectronicGridGps> gpsList = electronicGridGpsService.selectElectronicGridGpsByGridId(Integer.parseInt(gridId));
		response.getWriter().print(JSON.toJSONString(gpsList));
		return null;
	}
	
	/**
	 * 修改时点击地图，删除网格和网点的关联关系以及网格对应的电子网格
	 */
	@SystemServiceLog(description = "修改时点击地图，删除网格和网点的关联关系以及网格对应的电子网格")
	@RequestMapping(value = "deleteGridByGridId" , method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteGridByGridId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("gridId");
		//删除网格和网点的关联关系
		if (!StringUtils.isBlank(id)) {
			gridParkRelationService.batchDeleteParkByGridId(Integer.parseInt(id));
			//删除网格对应的电子网格
			electronicGridGpsService.deleteByGridId(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
		
	}
	
	/**
	 * 根据网格id查询所有网点
	 */
	@SystemServiceLog(description = "根据网格id查询所有网点")
	@RequestMapping(value = "parkDetail" , method = { RequestMethod.GET, RequestMethod.POST })
	public String parkDetail(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		String gridId = request.getParameter("gridId");
		String parkName = request.getParameter("parkName");
		Pager<Park> pager = new Pager<Park>();
		if (!StringUtils.isBlank(gridId)) {
			paramMap.put("gridId", Integer.parseInt(gridId));
			paramMap.put("parkName", parkName);
			pager = parkService.queryPageDetail(paramMap);
		}
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	/**
	 * 根据网格id查询该网格下的所有调度员
	 */
	@SystemServiceLog(description = "根据网格id查询该网格下的所有调度员")
	@RequestMapping(value = "dispatcherDetail" , method = { RequestMethod.GET, RequestMethod.POST })
	public String dispatcherDetail(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		String gridId = request.getParameter("gridId");
		String dispatcherName = request.getParameter("dispathcherName");
		Pager<Employee> pager = new Pager<Employee>();
		if (!StringUtils.isBlank(gridId)) {
			paramMap.put("gridId", Integer.parseInt(gridId));
			paramMap.put("dispatcherName", dispatcherName);
			pager = employeeService.queryPageDetail(paramMap);
		}
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	/**
	 * 删除网格下的网点
	 */
	@SystemServiceLog(description = "删除网格下的网点")
	@RequestMapping(value = "deleteParkOnGrid" , method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteParkOnGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String parkId = request.getParameter("id");
		String gridId = request.getParameter("gridId");
		gridService.deleteParkOnGrid(Integer.parseInt(parkId), Integer.parseInt(gridId));
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 删除网格下的调度员
	 */
	@SystemServiceLog(description = "删除网格下的调度员")
	@RequestMapping(value = "deleteDispatcherOnGrid" , method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteDispatcherOnGrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String dispatcherId = request.getParameter("id");
		String gridId = request.getParameter("gridId");
		gridService.deleteDispatcherOnGrid(Integer.parseInt(dispatcherId), Integer.parseInt(gridId));
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 设置网格管理员
	 */
	@SystemServiceLog(description = "设置网格管理员")
	@RequestMapping(value = "manageGridAdministration" , method = { RequestMethod.GET, RequestMethod.POST })
	public String manageGridAdministration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String dispatcherId = request.getParameter("id");
		//根据员工id查询该员工
		Employee employee = employeeService.selectByPrimaryKey(Integer.parseInt(dispatcherId));
		if ("1".equals(employee.getProfession())) {//如果是正式工
			gridService.manageGridAdministration(Integer.parseInt(dispatcherId));
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_manager_administrator","1","您已成为网格管理员",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "取消网格管理员")
	@RequestMapping(value = "cancelGridAdministration" , method = { RequestMethod.GET, RequestMethod.POST })
	public String cancelGridAdministration(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String dispatcherId = request.getParameter("id");
		//根据员工id查询该员工
		Employee employee = employeeService.selectByPrimaryKey(Integer.parseInt(dispatcherId));
		if ("1".equals(employee.getProfession())) {//如果是正式工
			gridService.cancelGridAdministration(Integer.parseInt(dispatcherId));
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_cancel_administrator","1","您已不是网格管理员",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
		return null;
	}
	
	@SystemServiceLog(description = "获取网格名称")
	@RequestMapping(value = "sys_griddingNameCombobox" , method = { RequestMethod.GET, RequestMethod.POST })
	public String sysGriddingNameCombobox(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		String cityCode = sysUser.getCityCode();
		if("00".equals(cityCode)){
			cityCode =null;
		}
		List<Grid> names = employeeGridRelationService.getAllGriddingInfo(cityCode);
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(names!=null && names.size()>0) {				
			for(int i=0;i<names.size();i++ ){
			    Grid grid=names.get(i);
				tree.append("{");
				tree.append("\"id\":\""+grid.getId()+"\",");
				tree.append("\"text\":\""+grid.getName()+"\"");
				if(i<names.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
		return null;
	}
	
}
