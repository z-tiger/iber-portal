package com.iber.portal.controller;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.car.CarMapper;
import com.iber.portal.dao.car.CarPreOfflineMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.dayRent.DayLongRentOrderMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.*;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.dayRent.LongRentOrder;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysDic;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.car.*;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.sys.SysDicService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.task.TaskService;
import com.iber.portal.service.tbox.TboxService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.PropertyUtil;
import com.iber.portal.util.SendSMS;
import com.iber.portal.util.SystemOperationUtil;
import com.iber.portal.vo.car.CarMrgVo;
import com.iber.portal.vo.car.CarWarnVo;
import com.iber.portal.vo.car.EmployeeInfoVo;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JsonConfig;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Controller
public class CarMrgController extends MainController{

	@Autowired
	private CarTypeService carTypeService;
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CarRepairService carRepairService;
	
	@Autowired
	private CarRunLogService carRunLogService;
	
	@Autowired
	private CarRunService carRunService;
	
	@Autowired
	private CarRescueService carRescueService;
	
	@Autowired
	private CarAccidentService carAccidentService;
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	@Autowired
	private TaskService taskService;

	@Autowired
	private CarOfflineApplyService applyService;
	
	@Autowired
	private CarPreOfflineMapper carPreOfflineMapper;
	
	@Autowired
	private CarRunMapper carRunMapper;
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private TimeShareOrderMapper timeShareOrderMapper;

	@Autowired
	private TboxService tboxService;
	@Autowired
	private SysDicService sysDicService;
	
	@Autowired
	private DayLongRentOrderMapper dayLongRentOrderMapper;

    private static final String E200Url;
    private static final String EV160Url;
    private static final String EQUrl;
    static {
        E200Url = PropertyUtil.getString("E200.url");
        EV160Url = PropertyUtil.getString("EV160.url");
        EQUrl = PropertyUtil.getString("EQ.url");
    }
	
	@SystemServiceLog(description="车辆类型")
	@RequestMapping(value = "/car_type", method = { RequestMethod.GET })
	public String carType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/carType";		
	}
	
	/**查询车辆类型列表 car_type_list*/
	@SystemServiceLog(description="查询车辆类型列表 ")
	@RequestMapping(value = "/car_type_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String type = request.getParameter("type");
		String typeName = request.getParameter("typeName");
		String cityCode = request.getParameter("cityCode");
		String brance = request.getParameter("brance");
		map.put("type", type);
		map.put("typeName", typeName);
		map.put("cityCode", cityCode);
		map.put("brance", brance);
		List<CarType> data = carTypeService.selectAll(map);
		int totalRecords = carTypeService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	/**车辆类型保存*/
	@SystemServiceLog(description="车辆类型保存 ")
	@RequestMapping(value = "/car_type_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeSave(CarType carType, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carType.setCreateTime(new Date());
		carType.setCreateUser(getUser(request).getAccount());
		carTypeService.insertSelective(carType);
		response.getWriter().print("succ");
		return null;
	}
	
	/**车辆类型编辑*/
	@SystemServiceLog(description="车辆类型编辑")
	@RequestMapping(value = "/car_type_edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeEdit(CarType carType, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carType.setUpdateTime(new Date());
		carType.setUpdateUser(getUser(request).getAccount());
		carTypeService.updateByPrimaryKeySelective(carType);
		response.getWriter().print("succ");
		return null;
	}
	
	/**车辆类型删除*/
	@SystemServiceLog(description="车辆类型删除")
	@RequestMapping(value = "/car_type_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String carTypeDel(int id, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carTypeService.deleteByPrimaryKey(id);
		response.getWriter().print("succ");
		return null;
	}
	
	/**查询全部的车辆类型combobox*/
	@SystemServiceLog(description="查询全部的车辆类型combobox")
	@RequestMapping(value = "/carTypeCombobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String carTypeCombobox(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");	
		List<CarType> data = carTypeService.selectAllNotPage();
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(data!=null && data.size()>0) {				
			for(int i=0;i<data.size();i++ ){
				CarType ctype=data.get(i);
				tree.append("{");
				tree.append("\"id\":\""+ctype.getId()+"\",");
				tree.append("\"text\":\""+ctype.getType()+"\"");
				if(i<data.size()-1){
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
	@SystemServiceLog(description="查询全部的车辆类型")
	@RequestMapping(value = "/car_mrg", method = { RequestMethod.GET })
	public String carMrg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/carMrg";		
	}
	
	/**车辆管理列表*/
	@SystemServiceLog(description="车辆管理列表")
	@RequestMapping(value = "/car_mrg_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String carMrgList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String lpn = request.getParameter("lpn");  //为了方便重用查询语句
		String cityCode = request.getParameter("cityCode"); 
		String parkName = request.getParameter("parkName"); 
		String shortcut = request.getParameter("shortcut");
		String status = request.getParameter("status");
//		if(StringUtils.equals(status, "0")){
//			map.put("status", "0");
//		}else if(StringUtils.equals(status, "1")){
//			map.put("status", "1");
//		}else{
//			map.put("status", status);
//		}
		map.put("status", status);
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
		map.put("lpn", lpn);
		map.put("parkName", parkName);
		map.put("shortcut",shortcut);
		List<CarMrgVo> data = carService.selectAllCarMrg(map);
		int totalRecords = carService.selectAllCarMrgRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description="车辆信息导出")
	@RequestMapping(value = "/export_carMrg_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportPileReportExecl(String lpn,String status,String cityCode,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarMrgReport" ;
		//列名充电桩编码	
		String columnNames [] = { "所属城市", "车牌号码", "车辆状态",  "发动机号", "车架号（VIN）", "车辆类型", "车辆品牌", 
				"车辆型号","车牌颜色","车辆年检时间","座厢数","最大续航（公里）","最高时速(公里/小时)","导航",
				"一键呼叫","行车记录","车辆所属","车载电话","蓝牙编号","盒子类型","tboxVersion","tboxImei","备注原因","操作人"};
		
		String keys[] = { "cityName", "lpn", "status","engineno", 
				"classno", "carTypeTypeName", "carTypeBrance", "carTypeType",
				"color","annualInspectionTime","seats","mileage","speed","isNav","isCall","isTripRecord",
				"owner","phone","bluetoothNo","boxType","tboxVersion","tboxImei","remark","userName"};
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
		map.put("lpn", lpn);
		map.put("status", status);
		map.put("offset", null);
		map.put("rows", null);
		List<CarMrgVo> datas = carService.selectAllCarMrg(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆信息报表");
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
			List<CarMrgVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "lpn", "status","engineno", 
				"classno", "carTypeTypeName", "carTypeBrance", "carTypeType",
				"color","annualInspectionTime","seats","mileage","speed","isNav","isCall","isTripRecord",
				"owner","phone","bluetoothNo","boxType","tboxVersion","tboxImei","remark","userName"};
		for (CarMrgVo car : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], car.getCityName());
			map.put(keys[1], car.getLpn().indexOf("•")<0 ?car.getLpn().substring(0, 2)+"•"+car.getLpn().substring(2):car.getLpn());
			map.put(keys[2], car.getStatus() ? "启用":"关闭");
			map.put(keys[3], car.getEngineno());
			map.put(keys[4], car.getClassno());
			if(car.getCarTypeTypeName().equals("ELECTRIC")){
				map.put(keys[5], "电动");
			}else if(car.getCarTypeTypeName().equals("FUEL")){
				map.put(keys[5], "燃油");
			}else if(car.getCarTypeTypeName().equals("MIXEDPOWER")) {
				map.put(keys[5], "混合动力");
			}
			map.put(keys[6], car.getCarTypeBrance());
			map.put(keys[7], car.getCarTypeType());
			map.put(keys[8], car.getColor());
			map.put(keys[9], car.getAnnualInspectionTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(car.getAnnualInspectionTime()):"");
			map.put(keys[10], car.getSeats());
			if(car.getMileage() != null && !"".equals(car.getMileage())){
				map.put(keys[11], car.getMileage());
			}else{
				map.put(keys[11], "0");
			}
			map.put(keys[12], car.getSpeed()==null ||car.getMileage().equals("") ? 0:car.getSpeed());
			if (car.getIsNav() != null && !"".equals(car.getIsNav())) {
				if(car.getIsNav().equals("1")){
					map.put(keys[13], "是");
				}else{
					map.put(keys[13], "否");
				}
			}else{
				map.put(keys[13], "否");
			}
			if (car.getIsCall() != null && !"".equals(car.getIsCall())) {
				if(car.getIsCall().equals("1")){
					map.put(keys[14], "是");
				}else{
					map.put(keys[14], "否");
				}
			}else{
				map.put(keys[14], "否");
			}
			if (car.getIsTripRecord() != null && !"".equals(car.getIsTripRecord())) {
				if(car.getIsTripRecord().equals("1")){
					map.put(keys[15], "是");
				}else{
					map.put(keys[15], "否");
				}
			}else{
				map.put(keys[15], "否");
			}
			if(car.getOwner().equals("OWNERER")){
				map.put(keys[16],"自有车辆");
			}else if(car.getOwner().equals("TESTER")){
				map.put(keys[16],"测试车辆");
			}
			map.put(keys[17], car.getPhone());
			map.put(keys[18], car.getBluetoothNo());
			if(car.getBoxType() != null){
				switch (car.getBoxType()) {
				case 0:
					map.put(keys[19], "北汽");
					break;
				case 1:
					map.put(keys[19], "奇瑞");
					break;
				case 2:
					map.put(keys[19], "众泰");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[19], "北汽");
			}
			map.put(keys[20], car.getTboxVersion());
			map.put(keys[21], car.getTboxImei());
			map.put(keys[22], car.getRemark());
			map.put(keys[23], car.getUserName());
			list.add(map);
		}
		return list;
	}
	/**
	 * 车辆运营管理
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆运营管理")
	@RequestMapping(value = "/car_mrg_run_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String carMrgRunList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		//此三条数据是为了重用sql
		String lpn = request.getParameter("lpn");
		String parkName = request.getParameter("parkName");
		String cityCode = request.getParameter("cityCode");
		String status  = request.getParameter("status");
		String preOfflineStatus  = request.getParameter("preStatus");
		String tfstatus  = request.getParameter("tfstatus");
		String isLook = request.getParameter("isLook");
		String brandName = request.getParameter("brandName");
		String areaName = request.getParameter("areaName");
		// orderRule 剩余电量排序规则
		map.put("orderRule",request.getParameter("orderRule"));
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if("00".equals(user.getCityCode())){
			if(StringUtils.isNotBlank(cityCode)&&!StringUtils.equals("00", cityCode)){
				map.put("cityCode", cityCode);
			}else{
				map.put("cityCode", null);
			}
        }else{
    	   map.put("cityCode", user.getCityCode());
        }
		map.put("preOffline", StringUtils.isBlank(preOfflineStatus)?"":preOfflineStatus);
		map.put("tfstatus", tfstatus);
		map.put("lpn", lpn.toUpperCase());
		map.put("parkName", parkName);
		map.put("isLook", isLook);
		map.put("brandName", brandName);
		map.put("areaName", areaName);
		map.put("carRunStatus",status);
		map.put("deviceUploadOrderRule", request.getParameter("deviceUploadOrderRule"));
		map.put("batteryVOrderRule", request.getParameter("batteryVOrderRule"));
		List<CarMrgVo> data = carService.selectAllCarMrg(map);
		int totalRecords = carService.selectAllCarMrgRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		
		response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description="车辆运营信息导出")
	@RequestMapping(value = "/export_carRun_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String lpn,String status,String cityCode,String brandName,String areaName,
			String parkName,String preStatus,String tfstatus,String isLook,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarRunReport" ;
		//列名充电桩编码	
		String columnNames [] = { "所属城市", "车牌号码", "品牌","型号","车辆状态","会员是否可见", "剩余电量", "车载CPU温度", "小电瓶电压", "所在网点","所属城/镇区",
				"所属片区","设备蓝牙地址","设备软件版本号","设备上次上报时间","tf卡状态","个推状态","ICCID"};
		
		String keys[] = { "cityName", "lpn","brandName","carTypeType", "carRunStatus","","restBattery",
				"cpuTemperature", "smallBatteryVoltage", "parkName", "areaName","giName",
				"bluetoothNo","versionName","updateTime","tfcard","getuiStatus","iccId"};
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if("00".equals(user.getCityCode())){
			if(StringUtils.isNotBlank(cityCode)&&!StringUtils.equals("00", cityCode)){
				map.put("cityCode", cityCode);
			}else{
				map.put("cityCode", null);
			}
        }else{
    	   map.put("cityCode", user.getCityCode());
        }
		map.put("lpn", lpn.toUpperCase());
		map.put("carRunStatus", status);
		map.put("parkName", parkName);
		map.put("tfstatus", tfstatus);
		map.put("preOffline", StringUtils.isBlank(preStatus)?"":preStatus);
		map.put("isLook", isLook);
		map.put("brandName", brandName);
		map.put("areaName", areaName);
		map.put("offset", null);
		map.put("rows", null);
		List<CarMrgVo> datas = carService.selectAllCarMrg(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆运营信息报表");
		list.add(sheetNameMap);
		list.addAll(createData2(datas));
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
	
	private List<Map<String, Object>> createData2(
			List<CarMrgVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "lpn","brandName","carTypeType", "carRunStatus","","restBattery",
				"cpuTemperature", "smallBatteryVoltage", "parkName", "areaName", "giName",
				"bluetoothNo","versionName","updateTime","tfcard","getuiStatus","iccId"};
		for (CarMrgVo car : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], car.getCityName());
			map.put(keys[1], car.getLpn().indexOf("•")<0 ?car.getLpn().substring(0, 2)+"•"+car.getLpn().substring(2):car.getLpn());
			map.put(keys[2], car.getBrandName());
			map.put(keys[3], car.getCarTypeType());
			String status = car.getCarRunStatus();
			if(status.equals("empty")||status.equals("")||status==null){
				map.put(keys[4], "闲置中");
			}else if(status.equals("repair")){
				map.put(keys[4], "维修中");
			}else if(status.equals("maintain")){
				map.put(keys[4], "维护中");
			}else if(status.equals("rescue")){
				map.put(keys[4], "救援中");
			}else if(status.equals("accident")){
				map.put(keys[4], "事故处理中");
			}else if(status.equals("charging")){
				map.put(keys[4], "补电中");
			}else{
				if(StringUtils.isNotBlank(car.getPreOffline())){
					if (car.getPreOffline().equals("1")) {
						map.put(keys[4], "运营中（预下线）");
					}else{
						map.put(keys[4], "运营中");
					}
				}
			}
			if(StringUtils.equals(car.getCarRunStatus(), "empty")||StringUtils.isBlank(car.getCarRunStatus())){
				if(car.getRestBattery()!=null&&car.getOfflineThreshold()!=null&&StringUtils.isNotBlank(car.getSmallBatteryVoltage())){
					if((car.getRestBattery()>=car.getOfflineThreshold())&&Integer.parseInt(car.getSmallBatteryVoltage())>=100){
						map.put(keys[5], "是");
					}else{
						map.put(keys[5], "否");
					}
				}
			}else{
				if(StringUtils.equals(car.getCarRunStatus(), "repair")) map.put(keys[5], "否");
				if(StringUtils.equals(car.getCarRunStatus(), "maintain")) map.put(keys[5], "否");
				if(StringUtils.equals(car.getCarRunStatus(), "rescue")) map.put(keys[5], "否");
				if(StringUtils.equals(car.getCarRunStatus(), "accident")) map.put(keys[5], "否");
				if (StringUtils.equals(car.getCarRunStatus(), "charging")) {
					map.put(keys[5], "否");
				} else {
					if(StringUtils.equals(car.getPreOffline(), "1")){
						map.put(keys[5], "是");
					}else{
						map.put(keys[5], "是");
					}
				}
			}
			Double restBattery = car.getRestBattery();
			map.put(keys[6], restBattery);
			
			String cpuTemperature = car.getCpuTemperature();
			if(StringUtils.isNotBlank(cpuTemperature)){
				map.put(keys[7], cpuTemperature+"°C");
			}else{
				map.put(keys[7],  "");
			}
			String voltage = car.getSmallBatteryVoltage();
			if(StringUtils.isNotBlank(voltage)){
				map.put(keys[8], Double.parseDouble(voltage)/10.0+"V");
			}else{
				map.put(keys[8], "0V");
			}
			map.put(keys[9], car.getParkName());
			map.put(keys[10], car.getAreaName());
			map.put(keys[11], car.getGiName());
			map.put(keys[12], car.getBluetoothNo());
			map.put(keys[13], car.getVersionName());
			map.put(keys[14], car.getUpdateTime());
			if(StringUtils.isNotBlank(car.getTfcard())){
				map.put(keys[15], car.getTfcard().equals("1")?"正常":"已拔出");
			}else{
				map.put(keys[15], "已拔出");
			}
			
			if(StringUtils.isNotBlank(car.getGetuiStatus())){
				if (car.getGetuiStatus().equals("Offline")) {
					map.put(keys[16], "离线");
				} else if(car.getGetuiStatus().equals("Online")){
					map.put(keys[16], "在线");
				}
			}else{
				map.put(keys[16], "");
			}
			if(StringUtils.isNotBlank(car.getIccId())){
				map.put(keys[17], car.getIccId());
			}else{
				map.put(keys[17], "");
			}
			list.add(map);
		}
		return list;
	}
	private String uploadImg(MultipartFile file, OSSClientAPI oss) throws Exception{
		String filename = file.getOriginalFilename();
		if(!filename.equals("")){
			CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的
			DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
			File file1 = fi.getStoreLocation();
			InputStream is =   Picture.resizePngNoCompress(file1, filename.substring(filename.lastIndexOf(".") +1)) ;
//			InputStream is =   Picture.resizePNG(file1, 200, 100, filename.substring(filename.lastIndexOf(".") +1)) ;
			String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
			
			return oss.putObject(newFileName, is, "car/");
		}
		return null;
	}
	
	/**保存车辆信息*/
	@SystemServiceLog(description="保存车辆信息")
	@RequestMapping(value = "/car_mrg_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveCarMrg(Car car,HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile insuranceFile, 
			@RequestParam(required = false) MultipartFile drivingLicenseFile,
			@RequestParam(required = false) MultipartFile insuranceStrongUriFile,
			@RequestParam(required = false) MultipartFile insuranceBusUriFile
			) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		
		//车辆信息和型号字段匹配
		carMatchField(car);
		
		if(carService.selectByLpn(car.getLpn()) == null){
			//保险单、行驶证文件上传到CDN
			OSSClientAPI oss = makeUploadFileOss();
			//保险单
			String insuranceFileUri = uploadImg(insuranceFile, oss);
			//行驶证
			String drivingLicenseFileUri = uploadImg(drivingLicenseFile, oss);
			//交强险
			String insuranceStrongUri = uploadImg(insuranceStrongUriFile, oss);
			//商业险
			String insuranceBusUri = uploadImg(insuranceBusUriFile, oss);
			car.setInsuranceFileUri(insuranceFileUri);
			car.setDrivingLicenseFileUri(drivingLicenseFileUri);
			car.setInsuranceStrongUri(insuranceStrongUri);
			car.setInsuranceBusUri(insuranceBusUri);
			/**从系统参数中获取车辆图片和车辆小图片*/
			String carFileUrl = sysParamService.selectByKey("car_img_url").getValue();
			car.setCarImgUri(carFileUrl) ;
			String smallCarImgUri = null;
			String carImgUrl = null;
			/**根据选择的车型找到对应的小图标*/
			if(car.getModelId().intValue() == Integer.valueOf(1).intValue()){
				smallCarImgUri = sysParamService.selectByKey("EV160_small_car_img_uri").getValue();
				carImgUrl = EV160Url;
			}else if(car.getModelId().intValue() == Integer.valueOf(2).intValue()){
				smallCarImgUri = sysParamService.selectByKey("EQ_small_car_img_uri").getValue();
				carImgUrl = EQUrl;
			}else if(car.getModelId().intValue() == Integer.valueOf(5).intValue()){
				smallCarImgUri = sysParamService.selectByKey("E200_small_car_img_uri").getValue();
				carImgUrl = E200Url;
			}
			car.setSmallCarImgUri(smallCarImgUri);
	        car.setCarImgUri(carImgUrl);
	        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{17}$";
	        boolean b = Pattern.matches(regex, car.getClassno());
	        if(!b){
	        	response.getWriter().print("classNoErr");
	        	return null ;
	        }
			int r = carService.saveCarInfo(car, null);
			if(r > 0){
				response.getWriter().print("succ");
//				if(StringUtils.equals(car.getOwner(), "OCEAN")){
//					synchCarsNotifyToOcean(car.getLpn(),car.getClassno(),car.getModelId() , "0");
//				}
			}else{
				response.getWriter().print("fail or exist");
			}
		}else{
			response.getWriter().print("existException");
		}
		return null;
	}
	
	public void carMatchField(Car car){
		
		CarType carType = carTypeService.selectSomeColumn(car.getModelId());
		car.setType(carType.getTypeName());//车辆类型 燃油车 电动车 代码表
		car.setBrandName(carType.getBrance());//品牌名称
		car.setSeats(carType.getSeatNumber().toString());//座位数
		car.setSpeed(carType.getMaxSpeed().toString());//最高速度
		car.setMileage(carType.getEndurance().toString());//续航里程
	}
	/**
	 * @return
	 */
	private OSSClientAPI makeUploadFileOss() {
		String endpoint = sysParamService.selectByKey("endpoint").getValue();
		String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
		String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
		String bucketName = sysParamService.selectByKey("bucketName").getValue();
		String dns = sysParamService.selectByKey("dns").getValue();
		OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		return oss;
	}
	
	private void synchCarsNotifyToOcean(String lpn, String classno,
			Integer modelId, String operType) {
		CarType carType = carTypeService.selectByPrimaryKey(modelId);
		String veModel = "";
		if(carType != null){
			veModel = carType.getBrance()+carType.getType();
		}
		String jsonParam = "{'lpn':'"+lpn+"','vin':'"+classno+"','vehModel':'"+veModel+"','operType':'"+operType+"'}";
		String url = sysParamService.selectByKey("http_ocean_url").getValue();
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",") ;
		sb.append("\"method\":\"carsNotify\",") ;
		sb.append("\"param\":\""+jsonParam+"\",") ;
		sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		if(url.indexOf("https") == 0){ //https接口
			 HttpsClientUtil.get(url, sb.toString()) ;
		}else{
			 HttpUtils.commonSendUrl(url, sb.toString()) ;
		}
	}

	/**删除车辆信息*/
	@SystemServiceLog(description="删除车辆信息")
	@RequestMapping(value = "/car_mrg_del", method = { RequestMethod.GET, RequestMethod.POST })
	public String delCarMrg(int id, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		//carService.deleteByPrimaryKey(id);
		Car car = carService.selectByPrimaryKey(id);
		int r = carService.deleteCarInfo(id);
		if(r > 0){
			response.getWriter().print("succ");
//			if(StringUtils.equals(car.getOwner(), "OCEAN")){
//				synchCarsNotifyToOcean(car.getLpn(),car.getClassno(),car.getModelId() , "2");
//			}
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	/**设置车辆预下线*/
	@SystemServiceLog(description="车辆预下线状态变更")
	@RequestMapping(value = "/changePreOnlineStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public String changeCarToPrepareOffline(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
        String lpn = request.getParameter("lpn");
        String opreationType = request.getParameter("opreationType");
        String prepareOnlineTime = request.getParameter("predictTime");
        String offlineType = request.getParameter("status");
        int count = 0;
        Date predictTime = null;
        if(StringUtils.isBlank(prepareOnlineTime)){
        	long time = System.currentTimeMillis()+60*60*24*1000;
        	predictTime = new Date(time);
        }else{
        	predictTime = DateTime.StringToDate(prepareOnlineTime);
        }
        SysUser sysUser = SystemOperationUtil.getSysUser(request);
        Map<String, Object> map = new HashMap<String, Object>();
    	map.put("lpn", lpn);
    	map.put("status", "2"); // 预下线中
        CarRun carRun =  carService.queryCarRunInfo(lpn);
        CarPreOffline offline = null;
        String carStatus = carRun.getStatus(); 
        if("ordered".equals(carStatus)||"useCar".equals(carStatus)||"return".equals(carStatus)){
            // PREOFFLINE预下线操作
            if("PREOFFLINE".equals(opreationType)){
                String reason = request.getParameter("reason").trim();
                if(StringUtils.isBlank(reason)){
                	response.getWriter().print("noWord");
                	return null;
                }
                if("1".equals(carRun.getPreOffline())){
                	response.getWriter().print("preOffline");
                	return null;
                }
            	carRun.setPreOffline("1");
            	offline = carPreOfflineMapper.selectPreOfflineRecordByLpn(map);
                if(null==offline){
                	offline = new CarPreOffline();
                }
                offline.setCityCode(sysUser.getCityCode());
                offline.setPrincipalId(sysUser.getId());
                offline.setCreateTime(new Date());
                offline.setLpn(lpn);
                offline.setOfflineType(Integer.valueOf(offlineType));
                offline.setPredictTime(predictTime);
                offline.setReason("预下线: ".concat(reason));
                offline.setCreateUser(sysUser.getName());
                offline.setPrincipalName(sysUser.getName());
                offline.setPrincipalPhone(sysUser.getPhone());
                offline.setStatus(2);
            }
            // CANCEL_PREOFFLINE 车辆取消预下线操作
            if("CANCEL_PREOFFLINE".equals(opreationType)){
            	offline = carPreOfflineMapper.selectPreOfflineRecordByLpn(map);
            	if(null!=offline){
            		offline.setStatus(3);
            		Date date = new Date();
                    offline.setUpdateTime(date);
                    offline.setEndTime(date);
                    offline.setUpdateUserId(sysUser.getId());
                    offline.setUpdateUser(sysUser.getName());
            	}
            	carRun.setPreOffline("0");
            }
           count = carService.updateCarPreOfflineStatus(carRun,offline);
            if(0<count){
            	response.getWriter().print("succ");
            }else {
    			response.getWriter().print("fail");
    		}
        }else {
        	response.getWriter().print("车辆处于非运营状态,请刷新页面...");
		}
		return null;
	}
	/**修改车辆信息*/
	@SystemServiceLog(description="修改车辆信息")
	@RequestMapping(value = "/car_mrg_edit", method = { RequestMethod.GET, RequestMethod.POST })
	public String editCarMrg(Car car,HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) MultipartFile insuranceFile, 
			@RequestParam(required = false) MultipartFile drivingLicenseFile,
			@RequestParam(required = false) MultipartFile insuranceStrongUriFile,
			@RequestParam(required = false) MultipartFile insuranceBusUriFile
			)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carMatchField(car);
		 /*MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);  
       Iterator<String> files = mRequest.getFileNames(); 
        while (files.hasNext()) { 
        	MultipartFile muFile = mRequest.getFile(files.next());
        	CommonsMultipartFile mFile = (CommonsMultipartFile) muFile;
        	String fileName = muFile.getOriginalFilename();
        	if(!"".equals(fileName)){
        		DiskFileItem dfi =  (DiskFileItem) mFile.getFileItem();
        		File storeFile = dfi.getStoreLocation();
        		InputStream stream = Picture.resizePNG(storeFile, 200, 100,fileName.substring(fileName.lastIndexOf(".") +1)) ;
        		String newFileName = UUID.randomUUID().toString() + "." + fileName.substring(fileName.lastIndexOf(".") +1);
        		//文件上传到CDN
    	        String endpoint = sysParamService.selectByKey("endpoint").getValue();
    	        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
    	        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
    	        String bucketName = sysParamService.selectByKey("bucketName").getValue();
    	        String dns = sysParamService.selectByKey("dns").getValue();
    		    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
    		    if("file".equals(dfi.getFieldName())){
    		    String carFileUrl = oss.putObject(newFileName, stream, "car/");
    			car.setCarImgUri(carFileUrl) ;
    		    }
    		    if("carSmallPic".equals(dfi.getFieldName())){
        		    String carSmallUrl = oss.putObject(newFileName, stream, "car/");
        			car.setSmallCarImgUri(carSmallUrl);
        		    }
        	}
        }*/
		/**从系统参数中获取车辆图片和车辆小图片*/
		String carFileUrl = sysParamService.selectByKey("car_img_url").getValue();
		car.setCarImgUri(carFileUrl) ;
		String smallCarImgUri = null;
		String carImgUrl = null;
		/**根据选择的车型找到对应的小图标*/
		if(car.getModelId().intValue() == Integer.valueOf(1).intValue()){
			smallCarImgUri = sysParamService.selectByKey("EV160_small_car_img_uri").getValue();
			carImgUrl = EV160Url;
		}else if(car.getModelId().intValue() == Integer.valueOf(2).intValue()){
			smallCarImgUri = sysParamService.selectByKey("EQ_small_car_img_uri").getValue();
			carImgUrl = EQUrl;
		}else if(car.getModelId().intValue() == Integer.valueOf(5).intValue()){
			smallCarImgUri = sysParamService.selectByKey("E200_small_car_img_uri").getValue();
			carImgUrl = E200Url;
		}
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{17}$";
        boolean b = Pattern.matches(regex, car.getClassno());
        if(!b){
        	response.getWriter().print("classNoErr");
        	return null ;
        }
		car.setSmallCarImgUri(smallCarImgUri);
		car.setCarImgUri(carImgUrl);
		if(null != insuranceFile || null != drivingLicenseFile || null != insuranceStrongUriFile || null != insuranceBusUriFile){
			//保险单、行驶证文件上传到CDN
			OSSClientAPI oss = makeUploadFileOss();
			//保险单
			if(null != insuranceFile){
				String insuranceFileUri = uploadImg(insuranceFile, oss);
				car.setInsuranceFileUri(insuranceFileUri);
			}
			//行驶证
			if(null != drivingLicenseFile){
				String drivingLicenseFileUri = uploadImg(drivingLicenseFile, oss);
				car.setDrivingLicenseFileUri(drivingLicenseFileUri);				
			}
			//交强险
			if(null != insuranceStrongUriFile){
				String insuranceStrongUri = uploadImg(insuranceStrongUriFile, oss);
				car.setInsuranceStrongUri(insuranceStrongUri);	
			}
			//商业险
			if(null != insuranceBusUriFile){
				String insuranceBusUri = uploadImg(insuranceBusUriFile, oss);
				car.setInsuranceBusUri(insuranceBusUri);	
			}
		}
		
		int r = carService.updateByPrimaryKeySelective(car);
		if(r > 0){
			response.getWriter().print("succ");
			Car car2 = carService.selectByPrimaryKey(car.getId());
			/*if(StringUtils.equals(car2.getOwner(), "OCEAN")){
				synchCarsNotifyToOcean(car2.getLpn(),car2.getClassno(),car2.getModelId() , "1");
			}*/
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	/**保存车辆维修信息*/
	@SystemServiceLog(description="保存车辆下线信息")
	@RequestMapping(value = "/car_repair_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveCarRepair(CarRepair carRepair, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		carRepair.setStartTime(new Date());
		carRepair.setStatusCache(carRepair.getStatus());
		//查询车辆状态
		SysUser sysUser = SystemOperationUtil.getSysUser(request);
		carRepair.setResponsiblePersonId(sysUser.getId());
		carRepair.setResponsiblePerson(sysUser.getName());
		CarRun vo = carRunService.getCarInfo(carRepair.getLpn().replace("•", ""));
		String carRunStatus = vo.getStatus();
		String downLineStatus = carRepair.getStatus();
		if(SysConstant.CAR_RUN_STATUS_USECAR.equals(carRunStatus)||SysConstant.CAR_RUN_STATUS_RETURN.equals(carRunStatus)||SysConstant.CAR_RUN_STATUS_ORDERED.equals(carRunStatus)){
			response.getWriter().print("running");
			return null;	
		}
		// 如果下线变更类型是相同的,即本来已处于维修状态的车再次变更为维修,则拒绝再次把该车进行下线变更
		if(SysConstant.CAR_RUN_STATUS_MAINTAIN.equals(carRunStatus)&&"2".equals(downLineStatus)){
			response.getWriter().print("sameStatus");
			return null;	
		}
		if(SysConstant.CAR_RUN_STATUS_REPAIR.equals(carRunStatus)&&"1".equals(downLineStatus)){
			response.getWriter().print("sameStatus");
			return null;
		}
		if(SysConstant.CAR_RUN_STATUS_CHARING.equals(carRunStatus)&&"3".equals(downLineStatus)){
			response.getWriter().print("sameStatus");
			return null;
		}
		// 空闲的车直接走下去,非空闲的车判断是否存在维修中或维护中的转补电中
		if(!SysConstant.CAR_RUN_STATUS_EMPTY.equals(carRunStatus)){
			// 1,2,3分别代表维修、维护和补电,如果由1或2切换到3,则拒绝切换
			if(("3".equals(downLineStatus))&&!SysConstant.CAR_RUN_STATUS_CHARING.equals(carRunStatus)){
				response.getWriter().print("modifyFailed");
				return null;
			}
		}
//		if(!"empty".equals(vo.getStatus())){
//			response.getWriter().print("noEmpty");
//			return null;
//		}
		carRepair.setCreateUser(sysUser.getAccount());
		carRepair.setCreateTime(new Date());
		String predictTime = request.getParameter("queryDateFrom");
		Date date = null;
		if(!StringUtils.isBlank(predictTime)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = sdf.parse(predictTime);
		}else{
			date = new Date(System.currentTimeMillis()+60*60*24*1000);
		}
		carRepair.setPredictTime(date);
		carRepair.setLpn(carRepair.getLpn().replace("•", ""));
		int r = carRepairService.saveCarRepairInfo(carRepair,carRunStatus,vo.getRestBattery());
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	@RequestMapping(value = "/car_repair", method = { RequestMethod.GET })
	public String carRepair(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/carRepair";		
	}
	
	/**维修列表*/
	@SystemServiceLog(description="维修列表")
	@RequestMapping(value = "/car_repair_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String carRepairList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String lpn = request.getParameter("lpn");
		String status = request.getParameter("repairStatus");
		String timeOut = request.getParameter("timeOut");
		String cityCode= request.getParameter("cityCode");
		String category = request.getParameter("category");
		map.put("category", category) ;
		map.put("lpn", lpn);
		if (status!=null&&status.split(",").length>1){
			map.put("statusCache",status.split(",")[1]);
			map.put("status",status.split(",")[0]);
		}else{
			map.put("status",status);
		}

		map.put("timeOut",timeOut);
		map.put("reason",request.getParameter("reason"));
		//城市过滤
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
//		List<CarRepair> data = carRepairService.selectAll(map);
		List<CarRepair> data = carRepairService.selectTotalRepairsInfos(map);
		int totalRecords = carRepairService.selectTotalRepairsInfoRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="车辆维修信息导出")
	@RequestMapping(value = "/export_carRepair_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl2(String lpn,String repairStatus,String cityCode,String reason,String category,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarRepairReport" ;
		//列名充电桩编码	
		String columnNames [] = { "所属城市", "车牌号码", "剩余电量",  "小电瓶电压", "维修网点", "维修状态", "责任人", 
				"手机号码","维修开始时间","预计上线时间","下线原因","网点类型","所属片区","维修结束时间",
				"设备上次上报时间","蓝牙地址","tf状态"};
		
		String keys[] = { "cityName", "lpn", "battery","voltage", "parkName", "status", "responsiblePerson", "responsiblePersonPhone",
				"startTime","predictTime","reason","category","giName","endTime","latestUpdateTime","bluetoothNo","tfCardStatus"};
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
		map.put("lpn", lpn);
		map.put("reason", reason);
		map.put("category", category);
		if (repairStatus!=null&&repairStatus.split(",").length>1){
			map.put("statusCache",repairStatus.split(",")[1]);
			map.put("status",repairStatus.split(",")[0]);
		}else{
			map.put("status",repairStatus);
		}
		map.put("offset", null);
		map.put("rows", null);
		List<CarRepair> datas = carRepairService.selectTotalRepairsInfos(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆维修信息报表");
		list.add(sheetNameMap);
		list.addAll(createData3(datas));
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
	
	private List<Map<String, Object>> createData3(
			List<CarRepair> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "lpn", "battery","voltage", "parkName", "status", "responsiblePerson", "responsiblePersonPhone",
				"startTime","predictTime","reason","category","giName","endTime","latestUpdateTime","bluetoothNo","tfCardStatus"};
		for (CarRepair repair : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], repair.getCityName());
			map.put(keys[1], repair.getLpn().indexOf("•")<0 ?repair.getLpn().substring(0, 2)+"•"+repair.getLpn().substring(2):repair.getLpn());
			Double restBattery = repair.getBattery();
			if(restBattery != null){
				map.put(keys[2], restBattery);
			}else{
				map.put(keys[2], 0.0);
			}
			String voltage = String.valueOf(repair.getVoltage());
			if(StringUtils.isNotBlank(voltage)){
				map.put(keys[3], Double.parseDouble(voltage)/10.0+"V");
			}else{
				map.put(keys[3], "0V");
			}
			map.put(keys[4], repair.getParkName());
			String status = repair.getStatus();
			String cache = repair.getStatusCache();
			if(StringUtils.isNotBlank(status)&&StringUtils.isNotBlank(cache)){
				if(status.equals("1")&&cache.equals("1")){
					map.put(keys[5], "维修中");
				}else if(status.equals("2")&&cache.equals("2")){
					map.put(keys[5], "维护中");
				}else if(status.equals("3")&&cache.equals("3")){
					map.put(keys[5], "补电中");
				}else if(status.equals("0")&&cache.equals("1")){
					map.put(keys[5], "已维修");
				}else if(status.equals("0")&&cache.equals("2")){
					map.put(keys[5], "已维护");
				}else if(status.equals("0")&&cache.equals("3")){
					map.put(keys[5], "已补电");
				}
			}
			map.put(keys[6], repair.getResponsiblePerson());
			map.put(keys[7], repair.getResponsiblePersonPhone());
			map.put(keys[8], repair.getStartTime()!= null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(repair.getStartTime()):"");
			map.put(keys[9], repair.getPredictTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(repair.getPredictTime()):"");
			map.put(keys[10], repair.getReason());
			if(StringUtils.isNotBlank(repair.getCategory())){
				switch (Integer.parseInt(repair.getCategory())) {
				case 1:
					map.put(keys[11], "1S网点");
					break;
				case 2:
					map.put(keys[11], "2S网点");
					break;
				case 3:
					map.put(keys[11], "4S网点");
					break;
				default:
					break;
				}
			}else{
				map.put(keys[11], "");
			}
			map.put(keys[12], repair.getGiName());
			map.put(keys[13], repair.getEndTime()!=null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(repair.getEndTime()):"");
			map.put(keys[14], repair.getLatestUpdateTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(repair.getLatestUpdateTime()):"");
			map.put(keys[15], repair.getBluetoothNo());
			Integer tfCardStatus = repair.getTfCardStatus();
			if(tfCardStatus != null){
				if(repair.getTfCardStatus()==1){
					map.put(keys[16], "正常");
				}else{
					map.put(keys[16], "已拔出");
				}
			}else{
				map.put(keys[16], "已拔出");
			}
			
			list.add(map);
		}
		return list;
	}
	
	/**车辆恢复运营*/
	@SystemServiceLog(description="车辆恢复运营")
	@RequestMapping(value = "/car_resume", method = { RequestMethod.GET, RequestMethod.POST})
	public String carResume(int id,String lpn, String recoverReason, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = getUser(request);
		/**查询是否有员工正在用车，如有用车不准恢复运营*/
		Car car = carService.selectByLpn(lpn);
		if(null != car.getStatus() && false == car.getStatus()){
			response.getWriter().print("closeCar");
			return null;
		}else{
			String orderNo = carRepairService.queryCarOrderId(lpn);
			if(orderNo!=null && orderNo.contains("employee") ){
				response.getWriter().print("employee");
			}else{
				int r = carRepairService.carResume(id, sysUser.getAccount(), sysUser.getName(), sysUser.getPhone(), recoverReason);
				if(r > 0){
					response.getWriter().print("succ");
				}else{
					response.getWriter().print("fail");
				}
			}
		}
		return null;		
	}
	
	/**删除信息*/
	@SystemServiceLog(description="删除车辆信息")
	@RequestMapping(value = "/car_repair_del", method = { RequestMethod.GET, RequestMethod.POST  })
	public String carRepairDel(int id, String lpn,  HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int r = carRepairService.delCarRepairInfo(id, lpn);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;		
	}
	
	@SystemServiceLog(description="车辆维修")
	@RequestMapping(value = "/car_repair_edit", method = { RequestMethod.GET, RequestMethod.POST  })
	public String carRepairEdit(CarRepair carRepair, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		if (carRepair == null){
			response.getWriter().print("fail");
			return null;
		}
		final String lpn = carRepair.getLpn().replace("•", "");
		carRepair.setLpn(lpn);
		final CarRun carRun = carRunService.queryCarRun(lpn);
		carRepair.setStartRestBattery(carRun.getRestBattery());
		String userName = getUser(request).getAccount();
		carRepair.setUpdateUser(userName);
		carRepair.setUpdateTime(new Date());
		carRepairService.updateByPrimaryKeySelective(carRepair);
		response.getWriter().print("succ");
		return null;
	}
	@SystemServiceLog(description="车辆信息批量导入样表下载")
	@RequestMapping(value = "/car_inport_template_file_download", method = { RequestMethod.GET, RequestMethod.POST  })
	public String carInportTemplateFileDownload(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String path = request.getRealPath("car/template.xls");
		try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
	}
	@SystemServiceLog(description="批量导入车辆信息")
	@RequestMapping(value = "/inport_car", method = { RequestMethod.GET, RequestMethod.POST  })
	public String inportCar(MultipartFile uploadFile , HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<Map<String, String>> data = readTemplateFileInfo(uploadFile);
		if(!data.isEmpty()){
			for(Map<String, String> map : data){
				Car car = new Car();
				car.setCityCode(map.get("0"));
				car.setLpn(map.get("1"));
				try {
					car.setModelId(Integer.parseInt(map.get("2")));
				} catch (Exception e) {
				}
				car.setBluetoothNo(map.get("3"));
				car.seteLpn(map.get("4"));
				car.setIosUuid(map.get("5"));
				car.setColor(map.get("6"));
				car.setSeats(map.get("7"));
				car.setSpeed(map.get("8"));
				car.setMileage(map.get("9"));
				car.setPhone(map.get("10"));
				car.setOwner(map.get("11"));
				car.setSmallCarImgUri(map.get("12"));
				car.setEngineno(map.get("13"));
				car.setClassno(map.get("14"));
				car.setCarImgUri(map.get("15"));
				carService.saveCarInfo(car, null);
			}
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	
	private List<Map<String, String>> readTemplateFileInfo(MultipartFile uploadFile){
		 List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		 Set<String> lpnSet = carService.selectAllCarLpns();
	        try{
	            jxl.Workbook rwb = Workbook.getWorkbook(uploadFile.getInputStream());   
                Sheet rs = rwb.getSheet(0);   
                for (int j = 1; j < rs.getRows(); j++) {   
                   Cell[] cells = rs.getRow(j);
                   if(!lpnSet.contains(cells[1].getContents())){
                	   lpnSet.add(cells[1].getContents());
                       Map<String, String> map = new HashMap<String, String>();
                       for(int k=0;k<cells.length;k++){
                	       map.put(String.valueOf(k), cells[k].getContents());
                       }   
                       list.add(map);
                   }
                }   
	            uploadFile.getInputStream().close();   
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return list;
	}

	@SystemServiceLog(description="车辆运行信息")
	@RequestMapping(value = "/car_run", method = { RequestMethod.GET })
	public String carRun(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/carRun";		
	}
	

	@SystemServiceLog(description="车辆报警信息")	
	@RequestMapping(value = "/car_warn", method = { RequestMethod.GET })
	public String carWarnList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/carWarn";		
	}
	
	/**车辆警告列表*/
	@SystemServiceLog(description="车辆警告列表")
	@RequestMapping(value = "/car_warn_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String carWarnList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String lpn = request.getParameter("lpn");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		map.put("lpn", lpn);
		String city_code = request.getParameter("cityCode") ;
		if(StringUtils.isNotBlank(city_code) && !StringUtils.equals("00", city_code)){
			map.put("cityCode", city_code);
		}else{
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			String cityCode = null;
			if(!StringUtils.equals("00", user.getCityCode())){
				cityCode = user.getCityCode();
			}
			map.put("cityCode", cityCode);
		}
		
		map.put("queryDateFrom", queryDateFrom);
		map.put("queryDateTo", queryDateTo);
		List<CarWarnVo> data = carRunLogService.selectAll(map);
		int totalRecords = carRunLogService.selectAllCarWarnVoRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description="网点")
	@RequestMapping(value = "/save_park_info", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveParkInfo(String carRunId, int parkId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		try {
			if(carRunId.indexOf(",") != -1){
				String[] arr = carRunId.split(",");
				for(String s : arr){
					carRunService.updateParkInfo(Integer.parseInt(s), parkId);
				}
			}else{
				carRunService.updateParkInfo(Integer.parseInt(carRunId), parkId);
			}
			response.getWriter().print("succ");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("fail");
		}
		return null;
	}
	
	/**保存车辆救援信息*/
	@SystemServiceLog(description="新增车辆救援记录")
	@RequestMapping(value ="/rescue_car_save",method = { RequestMethod.GET,RequestMethod.POST})
	public String saveCarRescue(String[] rescueType,HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		CarRescue carRescue = new CarRescue();
		String  carId = request.getParameter("rescue-id");
		String  lpn = request.getParameter("rescue-lpn-1");
		String employeeId = request.getParameter("employeeId");
		String rescueAddress = request.getParameter("rescue-address");
		String deadline = request.getParameter("deadline");
		String taskLevel = request.getParameter("taskLevel");//任务等级
		Employee employee = employeeService.selectByPrimaryKey(Integer.parseInt(employeeId));
		String reason = request.getParameter("rescue-reason");
		String eventCource = request.getParameter("eventCource");
		String responsibilityJudgeAdvice = request.getParameter("responsibilityJudgeAdvice");
		CarRun car = carRunService.queryCarRun(lpn.replace("•", ""));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskType", "5");
		paramMap.put("lpn", car.getLpn());
		Integer count = taskService.selectByTaskTypeAndLpn(paramMap);
		if (count > 0) {//如果存在救援中的任务
			response.getWriter().print("rescuring");
			return null;
		}
		//创建救援任务
		TaskInfo taskInfo = new TaskInfo();
		if(StringUtils.isBlank(car.getOrderId())){
			long tsBeginTime = 0L;
			long drBeginTime = 0L; 
		    TimeShareOrder order = timeShareOrderMapper.selectLastedOrderbyLpn(paramMap);
		    LongRentOrder longRentOrder = dayLongRentOrderMapper.selectLastedOrderbyLpn(paramMap);
		    if(null!=order && null!=order.getOrderTime()){
		    	tsBeginTime = order.getOrderTime().getTime();
		    }
		    if(null!=longRentOrder && null!=longRentOrder.getCreateTime()){
		    	drBeginTime = longRentOrder.getCreateTime().getTime();
		    }
		    long diff = tsBeginTime - drBeginTime;
		    if(diff == 0){
		    	taskInfo.setMemberId(null);
		    }else {
			    try {
			    	if(diff<0){
			    		taskInfo.setMemberId(Integer.valueOf(longRentOrder.getMemberId()));
			    		carRescue.setOrderId(longRentOrder.getOrderId());
			    		carRescue.setMemberId(Integer.valueOf(longRentOrder.getMemberId()));
			    	}else{
			    		taskInfo.setMemberId(Integer.valueOf(order.getMemberId()));
			    		carRescue.setOrderId(order.getOrderId());
			    		carRescue.setMemberId(Integer.valueOf(order.getMemberId()));
			    	}
				} catch (Exception e) {
					taskInfo.setMemberId(null);
				}
		    }
		}else {
			// 员工订单就不关联用户
			if(car.getOrderId().contains("employee")){
				taskInfo.setMemberId(null);
			}else{
				taskInfo.setMemberId(car.getMemberId());
				carRescue.setOrderId(car.getOrderId());
				carRescue.setMemberId(car.getMemberId());
			}
		}
		taskInfo.setCityCode(car.getCityCode());
		SysUser sysUser = getSysUser(request);
		taskInfo.setCreateId(sysUser.getId());
		taskInfo.setCreateTime(new Date());
		taskInfo.setLpn(lpn.replace("•", ""));
		taskInfo.setEmployeeId(employee.getId());
		taskInfo.setTaskName(lpn.replace("•", "") + "救援");
		taskInfo.setTaskLevel(taskLevel);
		taskInfo.setDeadline(StringUtils.isBlank(deadline)?null:DateTime.StringToDate(deadline));
		taskInfo.setStatus("1");//创建状态
		taskInfo.setTaskExplain(reason);
		taskInfo.setTaskType("5");//类型为救援
		taskInfo.setAddress(rescueAddress);
		taskInfo.setLatitude(StringUtils.isBlank(car.getLatitude())?null:Double.valueOf(car.getLatitude()));
		taskInfo.setLongitude(StringUtils.isBlank(car.getLongitude())?null:Double.valueOf(car.getLongitude()));
		taskService.insert(taskInfo);
		PushCommonBean pushCommon = new PushCommonBean("server_push_employee_task","1","您有新的任务，请尽快处理",taskInfo) ;
		/**根据员工的id查询员工的phone(客户端别名)*/
		List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
		System.out.println(JSON.toJSON(pushCommon));
		for (String employeeCid : cidList) {
			PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
		}
		//如果这个任务是救援任务，需要短信通知员工和会员，并推送消息给会员
		String ip = getRemortIP(request);
		//String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();
		//Map<String, String> params = new HashMap<String, String>();
		//获取加密token
		String encryptToken = SendSMS.encryptBySalt(employee.getPhone());
		//String param = "";
		//给救援人员发短信
		/*param ="{\"telephoneNo\":\""+employee.getPhone()+"\",\"ipAddress\":\""+ip+"\",\"templateId\":\"2678\",\"contentParam\":\""+new String(car.getLpn().getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
		params.put("msgContentJson", param); 
		HttpsClientUtil.post(sendUrl+"",params) ;*/
		SendSMS.send(employee.getPhone(),ip,2678,car.getLpn());

		//根据车牌查询用车会员
		CarChargingRemind remind = timeShareOrderService.selectUserByLpn(car.getLpn());
		//给会员发短信
		if (remind != null) {
			taskInfo.setMemberId(remind.getMemberId());
			taskService.updateTask(taskInfo);
			/*param ="{\"telephoneNo\":\""+remind.getPhone()+"\",\"ipAddress\":\""+ip+"\",\"templateId\":\"2679\",\"contentParam\":\""+new String((employee.getPhone()+"|"+employee.getName()).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
			params.put("msgContentJson", param);
			HttpsClientUtil.post(sendUrl+"",params) ;*/
			SendSMS.send(remind.getPhone(),ip,2679,employee.getPhone()+"|"+employee.getName());

			//给会员推送消息
			SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(18);
			if(null != systemMsg){
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType("member");
				systemMsgLog.setCreateTime(new Date());
				systemMsgLog.setMemberId(remind.getMemberId());
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				systemMsgLog.setMsgContent(systemMsg.getMsgContent());
				systemMsgLogMapper.insertSelective(systemMsgLog);
				PushCommonBean push = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
				if(push != null){
					List<String> alias = PushClient.queryClientId(remind.getPhone());
					if(!alias.isEmpty() && alias.size()> 0){
						for(String cid : alias){
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
							PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
						}
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", car.getLpn());
	    int num = carRescueService.selectUnfinishedRecordByLpn(map);
	    if(!(0<num)){
			carRescue.setStartTime(new Date());
			carRescue.setStatus("1");
			carRescue.setCreateUser(getUser(request).getAccount());
			carRescue.setCreateTime(new Date());
			carRescue.setCarId(Integer.valueOf(carId));
			carRescue.setLpn(lpn.replace("•", ""));
			carRescue.setResponsiblePerson(employee.getName());
			carRescue.setResponsiblePersonPhone(employee.getPhone());
			carRescue.setReason(eventCource);
			carRescue.setResponsibilityJudgeAdvice(responsibilityJudgeAdvice);
			carRescue.setMatterProcess(eventCource);
			carRescue.setTaskId(taskInfo.getId());
			carRescue.setRescueAddress(rescueAddress);
			carRescueService.saveCarRescueInfo(carRescue);
	    }
		response.getWriter().print("succ");
		return null;
	}
	
	/**保存车辆事故信息*/
	@SystemServiceLog(description="保存车辆事故信息")
	@RequestMapping(value ="/accident_car_save",method = { RequestMethod.GET,RequestMethod.POST})
	public String saveCarAccident(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		CarAccident carAccident= new CarAccident();
		String  carId = request.getParameter("accident-id");
		String  lpn = request.getParameter("accident-lpn-1");
		String responsiblePerson =request.getParameter("accident-responsiblePerson");
		String  responsiblePersonPhone  =request.getParameter("accident-responsiblePersonPhone");
		String  reason = request.getParameter("accident-reason");
		String relativeOrderId = request.getParameter("relativeOrderId");
		String money = request.getParameter("accident-money");
		String handleByCustomer = request.getParameter("memberCompensate");
		String is_insurance = request.getParameter("is_insurance");
		String insurance_code = request.getParameter("insurance_code");
		String predictTime = request.getParameter("predictTime");
		String assessmentTime = request.getParameter("assessment_time");
		String responsibility = request.getParameter("responsibility");

		carAccident.setStartTime(new Date());
		carAccident.setStatus("1");
		carAccident.setCreateUser(getUser(request).getAccount());
		carAccident.setCreateTime(new Date());
		carAccident.setCarId(Integer.valueOf(carId));													
		carAccident.setLpn(lpn.replace("•", ""));
		carAccident.setResponsiblePerson(responsiblePerson);
		carAccident.setResponsiblePersonPhone(responsiblePersonPhone);
		carAccident.setReason(reason);
		carAccident.setOrderId(relativeOrderId);
		carAccident.setPredictTime(predictTime);
		carAccident.setResponsibility(responsibility);
		carAccident.setInsuranceCode(insurance_code);
		carAccident.setIsInsurance(is_insurance);
		carAccident.setAssessmentTime(assessmentTime);
		if ("1".equals(handleByCustomer)) {
			if(money.indexOf(".")!=-1){
				Double mm = Double.parseDouble(money);
				Double mm2 =new Double( mm*100);
				carAccident.setMoney(mm2.intValue());
			}else{
				carAccident.setMoney(Integer.parseInt(money)*100);
			}
		}
		if (!StringUtils.isBlank(handleByCustomer)) {
			carAccident.setHandleByCustomer(Integer.parseInt(handleByCustomer));
		}
		int r = carAccidentService.insertSelective(carAccident);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
	private SysUser getSysUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return sysUser;
	}
	
	/**推送重启后视镜*/
	@SystemServiceLog(description="推送重启后视镜")
	@RequestMapping(value ="/server_push_reboot_system",method = { RequestMethod.GET,RequestMethod.POST})
	public String serverPushRebootSystem(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String lpn = request.getParameter("lpn");
		List<String> cIdList = PushClient.queryClientId(lpn);
		for(String str:cIdList){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lpn", lpn);
			PushCommonBean pushCommonBean = new PushCommonBean(SysConstant.SERVER_PUSH_REBOOT_SYSTEM, "1", "推送重启后视镜成功", map);
			PushClient.push(str,pushCommonBean);
		}
		response.getWriter().print("succ");
		return null;
	}
	
	/**推送重启后视镜*/
	@SystemServiceLog(description="服务器推送重启盒子")
	@RequestMapping(value ="/server_push_reboot_box",method = { RequestMethod.GET,RequestMethod.POST})
	public String serverPushRebootBox(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String lpn = request.getParameter("lpn");
		// 查询车辆
		final Integer tboxVersion = tboxService.tboxVersion(lpn);
		if (tboxVersion == null || tboxVersion == 0) {
			response.getWriter().print("fail");
			return null;
		}

		if (tboxVersion == 3){
			final Map<String, Object> map = tboxService.rebootSystem(lpn);
			if (CommonUtil.isSuccess(map)){
				response.getWriter().print("succ");
			}else {
				response.getWriter().print("fail");
			}
		}else {
			List<String> cIdList = PushClient.queryClientId(lpn);
			for(String str:cIdList){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("lpn", lpn);
				PushCommonBean pushCommonBean = new PushCommonBean(SysConstant.SERVER_PUSH_REBOOT_BOX, "1", "服务器推送重启盒子成功", map);
				PushClient.push(str,pushCommonBean);
			}
			response.getWriter().print("succ");
		}
		return null;
	}
	
	@SystemServiceLog(description="车辆一键启动")
	@RequestMapping(value ="/server_push_start_car",method = { RequestMethod.GET,RequestMethod.POST})
	public String serverPushStartCar(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String lpn = request.getParameter("lpn");
		// 查询车辆
		final Integer tboxVersion = tboxService.tboxVersion(lpn);
		if (tboxVersion == null || tboxVersion == 0) {
			response.getWriter().print("fail");
			return null;
		}

		if (tboxVersion == 3){
			final Map<String, Object> map = tboxService.startCar(lpn);
			if (CommonUtil.isSuccess(map)){
				response.getWriter().print("succ");
			}else {
				response.getWriter().print("fail");
			}
		}else {
			List<String> cIdList = PushClient.queryClientId(lpn);
			for(String str:cIdList){
				PushCommonBean pushCommonBean = new PushCommonBean(SysConstant.SERVER_PUSH_START_CAR, "1", "车辆一键启动成功", "");
				PushClient.push(str,pushCommonBean);
			}
			response.getWriter().print("succ");
		}
		return null;
	}
	
	/**服务器推送设置盒子类型*/
	@SystemServiceLog(description="服务器推送设置盒子类型")
	@RequestMapping(value ="/server_push_set_box_type",method = { RequestMethod.GET,RequestMethod.POST})
	public String serverPushSetBoxType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String lpn = request.getParameter("lpn");
		String carType = request.getParameter("carType");

		// 查询车辆
		final Integer tboxVersion = tboxService.tboxVersion(lpn);
		if (tboxVersion == null || tboxVersion == 0) {
			response.getWriter().print("fail");
			return null;
		}

		if (tboxVersion == 3){
			final Map<String, Object> map = tboxService.setCarType(lpn, carType);
			if (CommonUtil.isSuccess(map)){
				response.getWriter().print("succ");
			}
		}else {
			List<String> cIdList = PushClient.queryClientId(lpn);
			for(String str:cIdList){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("lpn", lpn);
				map.put("brandName", carType);
				PushCommonBean pushCommonBean = new PushCommonBean(SysConstant.SERVER_PUSH_SET_BOX_TYPE, "1", "服务器推送设置盒子类型成功", map);
				PushClient.push(str,pushCommonBean);
			}
			response.getWriter().print("succ");
		}

		return null;
	}

	/**
	 * @describe 员工列表
	 */
	@SystemServiceLog(description="员工列表")
	@RequestMapping(value = "/employee_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String chargingPileTypeList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		Pager<EmployeeInfoVo> pager = employeeService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	/**
	 * @describe 查看车辆救援详细信息
	 */
	@SystemServiceLog(description="查看车辆救援详细信息")
	@RequestMapping(value = "/selectRescueById", method = { RequestMethod.GET , RequestMethod.POST })
	public String selectRescueById(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		CarRescue carRescue = carRescueService.selectByPrimaryKey(Integer.parseInt(id));
		response.getWriter().print(JSON.toJSONString(carRescue));
		return null;
	}
	
	/**
	 * @describe 查看车辆救援详细信息carProblemsDetail
	 */
	@SystemServiceLog(description="查看车辆救援详细信息")
	@RequestMapping(value = "/carProblemsDetail", method = { RequestMethod.GET , RequestMethod.POST })
	public String carProblemsDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("recId");
		if(StringUtils.isBlank(id)){
			return null;
		}
		List<CarRescueProblem> problems = carRescueService.selectCarProblemsDetailByRecId(Integer.parseInt(id));
		response.getWriter().print(JSON.toJSONString(problems));
		return null;
	}
	
	/**
	 * @describe 根据会员电话号码以及车牌号查询订单信息
	 */
	@SystemServiceLog(description="根据会员电话号码以及车牌号查询订单信息")
	@RequestMapping(value = "/getOrderInfoByMemberPhone", method = { RequestMethod.GET , RequestMethod.POST })
	public String getOrderInfoByMemberPhone(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		
		String lpn = request.getParameter("lpn").replace("•", "");
		String phone = request.getParameter("memberPhone");
		String startTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		map.put("lpn", lpn);
		map.put("phone", phone);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		Pager<TimeShareOrder> pager = timeShareOrderService.getOrderInfoByMemberPhone(map);
		String json = Data2Jsp.Json2Jsp(pager);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="查询员工信息")
	@RequestMapping(value = "/getEmployeeInfos", method = { RequestMethod.GET, RequestMethod.POST })
	public String getEmployeeInfos(int page,int rows, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String employeeName = request.getParameter("employeeName");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		map.put("name", employeeName);
	    List<Employee> employees =	employeeService.getAllEmployeeInfo(map);
		int totalRecords = employeeService.selectAllEmployeesRecords(map);
		String json = Data2Jsp.Json2Jsp(employees, totalRecords);
		response.getWriter().print(json);
		return null;	
	}
	
	 	@SystemServiceLog(description="设置车辆关闭")
		@RequestMapping(value = "/set_car_status", method = { RequestMethod.GET, RequestMethod.POST })
	 	@ResponseBody
		public Map<String,Object> setCarStatus(String lpn,Boolean status,String remark,HttpServletRequest request,HttpServletResponse response)throws Exception{
			response.setCharacterEncoding("utf-8");
			String carStatus = carRunMapper.selectCarStatus(lpn);//车的状态
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if(status){
				if(StringUtils.equals(carStatus, "maintain")||StringUtils.equals(carStatus, "repair")){
					carMapper.updateStatus(lpn, false,remark,user.getId(), new Date());
					//更改车辆为已维修,已维护
					if(StringUtils.equals(carStatus, "maintain")){
						carStatus = "2" ;
						carRepairService.updateStatusCarBylpn(lpn, "0", carStatus);
					}else if(StringUtils.equals(carStatus, "repair")){
						carStatus = "1" ;
						carRepairService.updateStatusCarBylpn(lpn, "0", carStatus);
					}
					return ResultMsg.succResult("车辆关闭成功！");
				}else{
					return ResultMsg.fialResult("车辆正在运营中,不可关闭！");
				}
			}else {
				return ResultMsg.fialResult("关闭的车辆不可操作！");
			}
			
		}



	@SystemServiceLog(description="查询所有未绑定的tboxImei")
	@RequestMapping(value = "/getNotBindImeis", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> getNotBindImeis()throws Exception{
		return carService.selectNotBindImeis();
	}

	@SystemServiceLog(description="绑定TboxImei")
	@RequestMapping(value = "/bindTboxImei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> bindTboxImei(String lpn,String imei)throws Exception{
		if (StringUtils.isBlank(lpn) || StringUtils.isBlank(imei)){
			return fail("参数为空！");
		}
		return carService.bindTboxImei(lpn,imei);
	}

	@SystemServiceLog(description="解除绑定TboxImei")
	@RequestMapping(value = "/unBindTboxImei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> unBindTboxImei(String lpn,String imei)throws Exception{
		if (StringUtils.isBlank(lpn) || StringUtils.isBlank(imei)){
			return fail("参数为空！");
		}
		return carService.unBindTboxImei(lpn,imei);
	}

	@SystemServiceLog(description = "查询找所有的汽车品牌")
	@ResponseBody
	@RequestMapping(value = "/car/selectAllCarBrand", method = RequestMethod.GET)
	public List<CarType> selectAllCarBrand() {
		return  carTypeService.selectAllCarBrandList();

	}
}
