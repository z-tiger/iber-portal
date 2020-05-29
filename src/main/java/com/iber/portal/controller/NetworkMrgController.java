package com.iber.portal.controller;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.JsonResult;
import com.iber.portal.common.Pager;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.enterprise.Enterprise;
import com.iber.portal.model.pile.Pile;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.dispatcher.GridService;
import com.iber.portal.service.enterprise.EnterpriseService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.network.PileService;
import com.iber.portal.vo.car.CarMrgVo;
import com.iber.portal.vo.park.ParkTotalVo;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class NetworkMrgController extends MainController{
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PileService pileService;
	
	@Autowired
	private ParkService parkService;
	
	@Autowired
	private GridService gridService;

	@Autowired
	private CarService carService;

	@Autowired
    private EnterpriseService enterpriseService;
	/**
	 * 充电桩PAGE
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="充电桩PAGE")
	@RequestMapping(value = "/pile_page", method = { RequestMethod.GET })
	public String pile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "network/pile";		
	}
	@SystemServiceLog(description="充电桩PAGE列表")
	@RequestMapping(value = "/pile_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String pileList(int page, int rows, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String name = request.getParameter("name");
		String cityCode = request.getParameter("cityCode");
		String status = request.getParameter("status");
		String type = request.getParameter("type");
		map.put("name", name);
		map.put("cityCode", cityCode);
		map.put("status", status);
		map.put("type", type);
		List<Pile> data = pileService.selectAll(map);
		int totalRecords = pileService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	/**保存充电桩信息*/
	@SystemServiceLog(description="保存充电桩信息")
	@RequestMapping(value = "/save_pile", method = { RequestMethod.GET, RequestMethod.POST })
	public String savePile(Pile pile, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		pileService.insertSelective(pile);
		response.getWriter().print("succ");
		return null;
	}
	
	/**修改充电桩信息*/
	@SystemServiceLog(description="修改充电桩信息")
	@RequestMapping(value = "/edit_pile", method = { RequestMethod.GET, RequestMethod.POST })
	public String editPile(Pile pile, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		pileService.updateByPrimaryKeySelective(pile);
		response.getWriter().print("succ");
		return null;
	}
	
	/**删除充电桩信息*/
	@SystemServiceLog(description="删除充电桩信息")
	@RequestMapping(value = "/del_pile", method = { RequestMethod.GET, RequestMethod.POST })
	public String delPile(int id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		pileService.deleteByPrimaryKey(id);
		response.getWriter().print("succ");
		return null;
	}
	
	/**查询全部的网点信息，但是不分页查询*/
	@SystemServiceLog(description="查询全部的网点信息，不分页")
	@RequestMapping(value = "/park_all", method = { RequestMethod.GET })
	public String parkAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<Park> data = parkService.selectAllNotPage();
		JSONArray json = JSONArray.fromObject(data);
		response.getWriter().print(json.toString());
		return null;		
	}
	
	
	/**根据城市编码查询网点信息*/
	@SystemServiceLog(description="根据城市编码查询网点信息")
	@RequestMapping(value = "/park_city_code", method = { RequestMethod.GET , RequestMethod.POST})
	public String parkByCityCode(String cityCode, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<Park> data = null;
		if(cityCode.equals("00")){
			data = parkService.selectAllNotPage();
		}else{
			data = parkService.selectParkByCityCode(cityCode);
		}
		JSONArray json = JSONArray.fromObject(data);
		response.getWriter().print(json.toString());
		return null;
	}
	
	
	/**
	 * 网点PAGE
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="网点PAGE")
	@RequestMapping(value = "/park", method = { RequestMethod.GET })
	public String park(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String cityCode = getUser(request).getCityCode();
		City city = getCity(request, cityCode);
		request.setAttribute("lat", city.getLatitude());
		request.setAttribute("lng", city.getLongitude());
		return "network/park";		
	}
	
	/**
	 * 网点列表
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="网点列表")
	@RequestMapping(value = "/park_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String parkList(int page, int rows, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
		}else{
			map.put("cityCode", user.getCityCode());
      	}
		
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String name = request.getParameter("name");
		String parkType = request.getParameter("parkType");
		String isTemporary = request.getParameter("isTemporary");
		String status = request.getParameter("status");
		String fullNoParking = request.getParameter("fullNoParking");
		String category = request.getParameter("category");
		String gareaCode = request.getParameter("gareaCode");
		map.put("category", category);
		map.put("fullNoParking", fullNoParking) ;
		map.put("name", name);
		map.put("parkType", parkType);
		map.put("isTemporary", isTemporary);
		map.put("status", status);
		map.put("gareaCode", gareaCode);
		List<Park> data = parkService.selectAllParks(map);
		int totalRecords = parkService.selectAllParksRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="网点报表导出")
	@RequestMapping(value = "/export_park_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportPileReportExecl(String name, String parkType, String isTemporary, String status,String gareaCode,
			String fullNoParking, String category, String cityCode,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "ParkReport" ;
		//列名充电桩编码	
		String columnNames [] = { "所属城市","所属城/镇区", "网点名称", "状态",  "网点类型", "网点合作类型", "运营商组织机构代码", "所属物业公司",
				"网点地址","电子围栏名称","停车位数量","停车费(元/小时)","停车费描述","责任人","是否为临时网点",
				"运营开始时间","运营结束时间","所属企业名称","描述","车位满时不可还车","多片区共存","网点全景链接"};
		
		String keys[] = { "cityName","areaName", "name", "status",
				"categoryName", "cooperationType", "operatorId", "propertyManagementCompany", "address",
				"fenceName","parkNums","parkFee","parkFeeDesc","liablePerson","isTemporary","runStartTime","runEndTime",
				"enterpriseName","remark","fullNoParking","isCoexist","parkPanoramaLink"};
		Map<String, Object> map = new HashMap<String, Object>();
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
		map.put("category", category);
		map.put("fullNoParking", fullNoParking) ;
		map.put("name", name);
		map.put("parkType", parkType);
		map.put("isTemporary", isTemporary);
		map.put("status", status);
		map.put("gareaCode", gareaCode);
		map.put("offset", null);
		map.put("rows", null);
		List<Park> datas = parkService.selectAllParks(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "网点报表");
		list.add(sheetNameMap);
		list.addAll(createData(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}	
		
		return null;
		
	}
	
	private List<Map<String, Object>> createData(
			List<Park> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.00");
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName","areaName", "name", "status",
				"categoryName", "cooperationType", "operatorId", "propertyManagementCompany", "address" ,
				"fenceName","parkNums","parkFee","parkFeeDesc","liablePerson","isTemporary","runStartTime","runEndTime" ,
				"enterpriseName","remark","fullNoParking","isCoexist","parkPanoramaLink"};
		for (Park p : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], p.getCityName());
			map.put(keys[1], p.getAreaName());
			map.put(keys[2], p.getName());
			map.put(keys[3], p.getStatus()==1 ? "开启":"关闭");
			map.put(keys[4], p.getCategoryName());
			map.put(keys[5], p.getCooperationType()==0 ? "自有网点" : "合作网点");
			map.put(keys[6], p.getOperatorId());
			map.put(keys[7], p.getPropertyManagementCompany());
			map.put(keys[8], p.getAddress());
			map.put(keys[9], p.getFenceName());
			map.put(keys[10], p.getParkNums());
			map.put(keys[11], p.getParkFee() != null ? df.format(p.getParkFee()/100.0):0.00 );
			map.put(keys[12], p.getParkFeeDesc());
			map.put(keys[13], p.getLiablePerson());
			map.put(keys[14], p.getIsTemporary()==0 ? "是":"否");
			map.put(keys[15], p.getRunStartTime() != null ? p.getRunStartTime():"");
			map.put(keys[16], p.getRunEndTime() != null ? p.getRunEndTime():"");
			map.put(keys[17], p.getEnterpriseName());
			map.put(keys[18], p.getRemark());
			if(p.getFullNoParking() != null){
				map.put(keys[19], p.getFullNoParking()==true ? "是" : "否");
			}else {
				map.put(keys[19], "否");
			}
			map.put(keys[20], p.getIsCoexist()==1 ? "是" : "否");
			map.put(keys[21], StringUtils.isBlank(p.getParkPanoramaLink()) ? "" : p.getParkPanoramaLink());
			list.add(map);
		}
		return list;
	}
	
	// 不要删
	@SystemServiceLog(description="网点信息")
	@RequestMapping(value = "/getTotalParks", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTotalParks(int page,int rows, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String parkName = request.getParameter("parkName");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		map.put("name", parkName);
	    List<Park> parks =	parkService.getTotalParks(map);
		int totalRecords = parkService.selectAllParksRecords(map);
		String json = Data2Jsp.Json2Jsp(parks, totalRecords);
		response.getWriter().print(json);
		return null;	
	}
	
	/**保存网点信息
	 * */
	@SystemServiceLog(description="保存网点信息")
	@RequestMapping(value = "/save_park", method = { RequestMethod.GET, RequestMethod.POST })
	public String savePark(Park park, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		park.setParkFee(park.getParkFee()*100);
		parkService.insertSelective(park);
		response.getWriter().print("succ");
		return null;
	}
	
	
	/**修改网点信息
	 * */
	@SystemServiceLog(description="修改网点信息")
	@RequestMapping(value = "/edit_park", method = { RequestMethod.GET, RequestMethod.POST })
	public String editPark(Park park, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		park.setParkFee(park.getParkFee()*100);
		if (park.getFullNoParking()==null){
			park.setFullNoParking(false);
		}
		if (null==park.getIsCoexist()){
			park.setIsCoexist(0);
		}else{
			park.setIsCoexist(1);
		}
		parkService.updateByPrimaryKeySelective(park);
		response.getWriter().print("succ");
		return null;
	}
	
	/**删除网点信息*/
	@SystemServiceLog(description="删除网点信息")
	@RequestMapping(value = "/del_park", method = { RequestMethod.GET, RequestMethod.POST })
	public String delPark(int id,  HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		parkService.deleteByPrimaryKey(id);
		// 删除网点同时也把该网点与网格的关联关系删除,这里只删除两者的关系,网格的信息不能删
		gridService.deleteGridParkRelationByParkId(id);
		response.getWriter().print("succ");
		return null;
	}
	
	/**
	 * 设置网点的开启和关闭
	 */
	@SystemServiceLog(description="设置网点状态")
	@RequestMapping(value = "/set_park_status", method = { RequestMethod.GET, RequestMethod.POST })
	public String setPark(int id,HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		Park p = parkService.selectByPrimaryKey(id);
		int count = parkService.queryByPidLpnCount(id);
		
		if(p.getStatus()==1){
			if(count > 0){
				response.getWriter().print("existsCar");
				return null;
			}else{
				p.setStatus(0);
				p.setIsRun(1);//设置关闭
				parkService.updateByPrimaryKeySelective(p);
				response.getWriter().print("closeSucc");
				return null;
			}
		}else{
			if(p.getStatus()==0){
				//获取网点运营的开始时间和结束时间
				String runStartTime = p.getRunStartTime();
				String runEndTime = p.getRunEndTime();
				//获取当前时间
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
				String now = sdf.format(new Date());
				if(!StringUtils.isBlank(runStartTime) && !StringUtils.isBlank(runEndTime)){
					Integer startMinute = getMinute(runStartTime);
					Integer endMinute = getMinute(runEndTime);
					Integer nowMinute = getMinute(now);
					if(nowMinute < startMinute || nowMinute > endMinute){
						p.setStatus(1);
						parkService.updateByPrimaryKeySelective(p);
						response.getWriter().print("openSucc");//会员不可见,员工端可见
						return null;
					}else{
						p.setStatus(1);
						p.setIsRun(0);
						parkService.updateByPrimaryKeySelective(p);
						response.getWriter().print("openSucc");//会员员工都可见
						return null;
					}
				}
			}
		}
		
		return null;
	}
	//开启是判断是否在这个时间里
	private static Integer getMinute(String runStartTime) {
		int sum = 0;
		if (!StringUtils.isBlank(runStartTime)) {
			String[] times = runStartTime.split(":");
			if ("0".equals(times[0].substring(0, 1))) {
				String hour = times[0].substring(1);
				sum += Integer.parseInt(hour)*60;
			}else{
				String hour = times[0];
				sum += Integer.parseInt(hour)*60;
			}
			if ("0".equals(times[1].subSequence(0, 1))) {
				String minute = times[1].substring(1);
				sum += Integer.parseInt(minute);
			}else{
				String minute = times[1];
				sum += Integer.parseInt(minute);
			}
		}
		return sum;
	}

	/**获取网点列表*/
	@SystemServiceLog(description="获取网点列表")
	@RequestMapping(value = "/park_list_code", method = { RequestMethod.GET, RequestMethod.POST })
	public String parkListCode(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String cityCode = null ;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(!user.getCityCode().equals("00")){
			cityCode = user.getCityCode() ;
		}
		map.put("cityCode",cityCode);
		List<Park> parkList = parkService.queryParkByCode(map);
		JSONArray json = JSONArray.fromObject(parkList);
		response.getWriter().print(json.toString());
		return null;
	}
//	/**查询所有网点信息 分页查询*/
	@SystemServiceLog(description="查询所有网点信息 分页查询")
	@RequestMapping(value = "/park_allByPage", method = { RequestMethod.GET,RequestMethod.POST })
	public String park_list(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info(" 网点列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String cityCode = request.getParameter("cityCode");
		String parkName = request.getParameter("parkName");
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("cityCode", cityCode);
		paramMap.put("parkName", parkName);
		Pager<Park> pager = parkService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
	
	/**查询所有网点信息(非路径传参) 分页查询*/
	@SystemServiceLog(description="查询所有网点信息(非路径传参) 分页查询")
	@RequestMapping(value = "/parkName_allByPage", method = { RequestMethod.GET,RequestMethod.POST })
	public String parkNamelist(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info(" 网点列表");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
 		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String cityCode = user.getCityCode();
		String parkName = request.getParameter("parkName");
	    if(cityCode.equals("00")){
	    	paramMap.put("cityCode", null);
        }else{
        	paramMap.put("cityCode", cityCode);
        }
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("parkName", parkName);
		Pager<Park> pager = parkService.getAllParkName(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
	
	/**根据企业Id查询 企业网点列表*/
	@SystemServiceLog(description="根据企业Id查询 企业网点列表")
	@RequestMapping(value = "/dataListPark", method = { RequestMethod.GET,RequestMethod.POST })
	public String getDataListPark(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info(" 网点列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
 		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("id", id);
		Pager<Park> pager = parkService.getDataListPark(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
    /**根据企业Id查询 企业所属车辆*/
    @SystemServiceLog(description="根据企业Id查询 企业所属车辆")
    @RequestMapping(value = "/getEnterpriseCarList", method = { RequestMethod.GET,RequestMethod.POST })
    public void getEnterpriseCarList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Integer pageNum = pageInfo.get("first_page");
        Integer pageSize = pageInfo.get("page_size");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String id = request.getParameter("id");
        paramMap.put("pageNum", pageNum);
        paramMap.put("pageSize", pageSize);
        paramMap.put("enterpriseID", id);
        Pager<CarMrgVo> pager = carService.getCarListByEnterpriseID(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));

    }

    /**
     * 添加企业所属车辆
     * @RequestParam(value = "ids[]")
     */
    @SystemServiceLog(description = "添加企业所属车辆")
    @RequestMapping(value = "/addEnterpriseCar", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public JsonResult<String> addEnterpriseCar(String carid,String enterpriseID) throws Exception{
//        System.out.println("ids>>>>" + carid);
//        System.out.println("enterpriseID>>>"+enterpriseID);
        if (carid.isEmpty() || enterpriseID.isEmpty()) {
            return new JsonResult<String>(2,"参数错误");
        }
        String[] ids = carid.split(",");
        int returnNum = 0;
        int repetion =0;
        for (String id : ids) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("carID", Integer.valueOf(id));
            map.put("enterpriseID", Integer.valueOf(enterpriseID));
            Map<String, Object> enterpriseCarMap = carService.enterpriseCarRelationMap(enterpriseID ,id);
            if (enterpriseCarMap != null) {
                repetion++;
                continue;

            }
            returnNum = carService.addEnterpriseCar(map);
            Car car = carService.selectByPrimaryKey(Integer.parseInt(id));
            car.setIsEnterpriseBind(true);
            carService.updateByPrimaryKeySelective(car);
//            System.out.println("returnNum>>>:"+returnNum);
        }
        Enterprise enterprise = enterpriseService.selectByPrimaryKey(Integer.parseInt(enterpriseID));
        enterprise.setBindCars(true);
        enterpriseService.updateByPrimaryKeySelective(enterprise);
        if (repetion > 0) {
            return new JsonResult<String>(2, "请注意添加后的车辆，请不要重复添加");
        }

        if (returnNum > 0) {
            return new JsonResult<String>(1, "操作成功");

        }else {
            return new JsonResult<String>(0,"操作失败");
        }
    }
    /**
     * 查询可用企业所属车辆
     */
    @SystemServiceLog(description = "查询可用企业所属车辆")
    @RequestMapping("/findEnterpriseCarList")
    public void findEnterpriseCarList(int page,int rows ,HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String lpn= request.getParameter("lpn");
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Integer pageNum = pageInfo.get("first_page");
        Integer pageSize = pageInfo.get("page_size");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pageNum", pageNum);
        paramMap.put("pageSize", pageSize);
        paramMap.put("lpn",lpn);
        Pager<Car> pager = carService.getUnusedEnterpriseCarsList(paramMap);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));

    }

    /**
     * 删除企业所属车辆
     */
    @SystemServiceLog(description = "删除企业所属车辆")
    @RequestMapping(value = "/removeEnterpriseCar", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> removeEnterpriseCar( String id , String enterpriseID,String carID) {
        String[] ids = id.split(",");
        String [] carIDs = carID.split(",");

        if (carService.batchRemoveEnterpriseCar(ids,enterpriseID) == 0) {
           return fail("操作失败");
        }
        for (String carid : carIDs) {
            List<Map<String, Object>> map = carService.getcarRelaitonList(Integer.parseInt(carid));
            if (org.apache.commons.collections.CollectionUtils.isEmpty(map)) {
                Car car = carService.selectByPrimaryKey(Integer.parseInt(carid));
                car.setIsEnterpriseBind(false);
                carService.updateByPrimaryKeySelective(car);
            }

        }
        return success();
    }

	/**根据企业id查询  企业区域范围类  不属于本企业的网点列表*/
	@SystemServiceLog(description="根据企业id查询  企业区域范围类  不属于本企业的网点列表")
	@RequestMapping(value = "/allNotBelongPark", method = { RequestMethod.GET,RequestMethod.POST })
	public String getallPark(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info(" 不属于本企业的网点列表");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
 		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String cityCode = user.getCityCode();
		String id = request.getParameter("id");
		String parkName = request.getParameter("parkName");
		paramMap.put("from", from);
		paramMap.put("to", to);
		if(cityCode.equals("00")){
			paramMap.put("cityCode", null);
		}else{
			paramMap.put("cityCode",cityCode);
	    }
		paramMap.put("id", id);
		paramMap.put("parkName",parkName);
		Pager<Park> pager = parkService.getAllPark(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
	
	/**给停车网点更新所属企业*/
	@SystemServiceLog(description="给停车网点更新所属企业")
	@RequestMapping(value = "/enterprise_park_add", method = { RequestMethod.GET,RequestMethod.POST })
	public String updateBelongEnterprise(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info("更新停车网点所属企业");
		response.setContentType("text/html;charset=utf-8");
		String enterpriseId = request.getParameter("enterprise_id");
		String ids= request.getParameter("ids");
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id",Integer.valueOf(id[i]));
			paramMap.put("enterpriseId",  Integer.valueOf(enterpriseId));
			parkService.updateEnterpriseId(paramMap);
		}
		response.getWriter().print("success");
		return null;		
	}
	/**移除停车网点所属企业*/
	@SystemServiceLog(description="移除停车网点所属企业")
	@RequestMapping(value = "/deleteEnterpriseById", method = { RequestMethod.GET,RequestMethod.POST })
	public String deleteEnterprise(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info("移除停车网点所属企业");
		response.setContentType("text/html;charset=utf-8");
		String ids= request.getParameter("id");
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id",Integer.valueOf(id[i]));
			parkService.deleteEnterprise(paramMap);
		}
		response.getWriter().print("success");
		return null;		
	}
	
	@SystemServiceLog(description="按网点类型（自有、合作）查询网点总数")
	@RequestMapping(value = "/queryAllParkByType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllParkByType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String cityCode;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	        cityCode=null;
        }else{
       	    cityCode=user.getCityCode();
        }*/
		String cityCode =request.getParameter("cityCode");
		if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<ParkTotalVo> data = parkService.queryAllParkByType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="按网点类型（自有、合作）查询网点总数--按省份划分")
	@RequestMapping(value = "/queryAllProvinceParkByType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllProvinceParkByType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode =request.getParameter("cityCode");
		List<ParkTotalVo> data = parkService.queryAllProvinceParkByType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	
	@SystemServiceLog(description="按网点服务类型（1s、2s...）查询网点总数")
	@RequestMapping(value = "/queryAllParkByServiceType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllParkByServiceType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String cityCode;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	        cityCode=null;
        }else{
       	    cityCode=user.getCityCode();
        }*/
		 String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
		        cityCode=null;
	    }
		List<ParkTotalVo> data = parkService.queryAllParkByServiceType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="按网点服务类型（1s、2s...）按照省份划分查询网点总数")
	@RequestMapping(value = "/queryAllProvinceParkByServiceType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllProvinceParkByServiceType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode= request.getParameter("cityCode");
		List<ParkTotalVo> data = parkService.queryAllProvinceParkByServiceType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode获取网点车位总数 --按照全国、地级市划分")
	@RequestMapping(value = "/queryAllParkCarport", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllParkCarport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode= request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode =null;
		}
		List<ParkTotalVo> data = parkService.queryAllParkCarport(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode获取网点车位总数 --按照省份划分")
	@RequestMapping(value = "/queryAllProvinceParkCarport", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllProvinceParkCarport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode= request.getParameter("cityCode");
		List<ParkTotalVo> data = parkService.queryAllProvinceParkCarport(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode分别统计快慢充的网点车位数")
	@RequestMapping(value = "/queryAllParkCarportByConnectorType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllParkCarportByConnectorType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String cityCode;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	        cityCode=null;
        }else{
       	    cityCode=user.getCityCode();
        }*/
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<ParkTotalVo> data = parkService.queryAllParkCarportByConnectorType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode分别统计快慢充的网点车位数(按照省份划分)")
	@RequestMapping(value = "/queryAllProvinceParkCarportByConnectorType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllProvinceParkCarportByConnectorType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		List<ParkTotalVo> data = parkService.queryAllProvinceParkCarportByConnectorType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode和车辆类型统计网点车位数")
	@RequestMapping(value = "/queryAllParkCarportByCarType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllParkCarportByCarType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String cityCode;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	        cityCode=null;
        }else{
       	    cityCode=user.getCityCode();
        }*/
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<ParkTotalVo> data = parkService.queryAllParkCarportByCarType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode和车辆类型统计网点车位数--按照省份划分")
	@RequestMapping(value = "/queryAllProvinceParkCarportByCarType", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllProvinceParkCarportByCarType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
	    String cityCode = request.getParameter("cityCode");
		List<ParkTotalVo> data = parkService.queryAllProvinceParkCarportByCarType(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode获取网点个数的总计")
	@RequestMapping(value = "/queryAllParkNums", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllParkNums(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	        cityCode=null;
        }else{
       	    cityCode=user.getCityCode();
        }
		 
		List<ParkTotalVo> data = parkService.queryAllParkNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode查询各省的网点个数的总计")
	@RequestMapping(value = "/province_parkNum_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllProvinceParkNums(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode =request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode=null;
		} 	
		List<ParkTotalVo> data = parkService.queryAllProvinceParkNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="根据cityCode查询省下级城市的网点个数的总计")
	@RequestMapping(value = "/city_parkNum_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllCityParkNums(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		//String name =new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
		String name = request.getParameter("name");
		if("全国".equals(name)){
			name=null;
		}
		List<ParkTotalVo> data = parkService.queryAllCityParkNums(name) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	@SystemServiceLog(description="查询城市下级的区县的网点个数的总计")
	@RequestMapping(value = "/area__parkNum_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryAllareaParkNums(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode =request.getParameter("cityCode");
		List<ParkTotalVo> data = parkService.queryAllareaParkNums(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
}
