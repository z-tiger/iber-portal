package com.iber.portal.controller.maintenance;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.employee.MaintenanceEmployeeInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.maintenancer.MaintenancerService;
import com.iber.portal.service.task.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MaintenanceController {
    @Autowired
	private MaintenancerService maintenancerService;
    @Autowired
	private EmployeeService employeeService;
    
	
	@Autowired
	private TaskService taskService;
	/**
	 * 维保员管理页面
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@SystemServiceLog(description = "维保员管理页面")
	@RequestMapping(value = "maintenance_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String rescuerManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/maintenance/maintenance";
	}
	/**
	 * 维保员信息
	 */
	@SystemServiceLog(description = "维保员信息")
	@RequestMapping(value = "getMaintenancerInfos" , method = { RequestMethod.GET, RequestMethod.POST })
	public String rescuerInfos(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String cityCode = request.getParameter("cityCode");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		if("00".equals(sysUser.getCityCode())){
			if(StringUtils.isBlank(cityCode)||"00".equals(cityCode)){
				map.put("cityCode", null);
			}else{
				map.put("cityCode", cityCode);
			}
		}else {
			map.put("cityCode", sysUser.getCityCode());
		}
		map.put("page", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		map.put("maintenancerName", request.getParameter("maintenancerName"));
		map.put("status", request.getParameter("status"));
		Pager<MaintenanceEmployeeInfo> pager = maintenancerService.getMaintenancerInfos(map);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_maintenancer_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String maintenancerName,String status,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "MaintenancerReport" ;
		//列名充电桩编码	
		String columnNames [] = {"姓名", "所属城市", "身份", "状态", "手机号", "创建时间","修改时间",
				"当前任务数","今日完成数","正在执行","累计完成","今日积分","本月积分","总积分","备注" };
		
		String keys[] = { "name", "cityName", "identifyLabel",
				"status", "phone","createTime","updateTime", "currentTask", "todayCompleteTask",
				"processTask","totalCompleteTask","todayScore","monthScore","totalScore","remark"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(cityCode)){
	         	if(cityCode.equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", cityCode);
	         	}
	         }
		}else{
    	   paramMap.put("cityCode", user.getCityCode());
		}
		paramMap.put("status", status);
		paramMap.put("maintenancerName", maintenancerName);
		paramMap.put("page", null);
		paramMap.put("rows", null);
		
		Pager<MaintenanceEmployeeInfo> pager = maintenancerService.getMaintenancerInfos(paramMap);
		List<MaintenanceEmployeeInfo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "维保员数据报表");
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
			List<MaintenanceEmployeeInfo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "name", "cityName", "identifyLabel",
				"status", "phone","createTime","updateTime", "currentTask", "todayCompleteTask",
				"processTask","totalCompleteTask","todayScore","monthScore","totalScore","remark"};
		for (MaintenanceEmployeeInfo maintenance : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], maintenance.getName());
			map.put(keys[1], maintenance.getCityName());
			if(maintenance.getIdentifyLabel() != null){
				if(maintenance.getIdentifyLabel() == 1){
					map.put(keys[2], "维保主管");
				}else{
					map.put(keys[2], "维保员");
				}
			}else{
				map.put(keys[2], "维保员");
			}
			if(maintenance.getStatus() != null){
				if (maintenance.getStatus().equals("working")) {
					map.put(keys[3], "上班");
				} else if(maintenance.getStatus().equals("ordered")) {
					map.put(keys[3], "预约车辆中");
				} else if(maintenance.getStatus().equals("useCar")) {
					map.put(keys[3], "使用车辆中");
				} else if(maintenance.getStatus().equals("freeze")) {
					map.put(keys[3], "冻结");
				} else{
					map.put(keys[3], "下班");
				}
			}else{
				map.put(keys[3], "下班");
			}
			map.put(keys[4], maintenance.getPhone());
			map.put(keys[5], maintenance.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(maintenance.getCreateTime()):"");
			map.put(keys[6], maintenance.getUpdateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(maintenance.getUpdateTime()):"");
			map.put(keys[7], maintenance.getCurrentTask());
			map.put(keys[8], maintenance.getTodayCompleteTask()); 
			map.put(keys[9], maintenance.getProcessTask());
			map.put(keys[10], maintenance.getTotalCompleteTask());
			map.put(keys[11], maintenance.getTodayScore());
			map.put(keys[12], maintenance.getMonthScore());
			map.put(keys[13], maintenance.getTotalScore());
			map.put(keys[14], maintenance.getRemark());
			
			list.add(map);
		}
		return list;
	}
	/**
	 * 保存或者更新
	 */
	@SystemServiceLog(description = "添加或更改维保人员信息")
	@RequestMapping(value = "addOrUpdateMaintenancerInfo" , method = { RequestMethod.GET, RequestMethod.POST })
	public String addOrUpdateMaintenancerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String operateType = request.getParameter("operateType");
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String type = request.getParameter("type");
		//判断是否有任务
		if(!StringUtils.equals(type, "3")){
			int record = taskService.selectNonTaskRecords(id);
			if(record > 0){
				response.getWriter().print("hasTask");
				return null;
			}
		}
		String phone = request.getParameter("phone");
		if(!phone.matches("^[1][3,4,5,7,8][0-9]{9}$")){
			response.getWriter().print("p-wrong");
			return null;
		}
		String city = request.getParameter("city");
		if("00".equals(city)){
			response.getWriter().print("c-wrong");
			return null;
		}
		String email=request.getParameter("email");
		if(!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
			response.getWriter().print("email-fail");
			return null;
		}
		int count = 0;
		if(StringUtils.isBlank(operateType)){
			Employee employee = employeeService.selectByPhone(phone);
			
			if(employee != null){
				// 传过来的id与根据手机号查出的员工id不相等,则说明该手机号已被注册
				if(!employee.getId().toString().equals(id)){
					response.getWriter().print("exist");
					return null;
				}
			}
			employee = new Employee();
			employee.setIdentifyLabel(Integer.valueOf(request.getParameter("identy")));
			employee.setRemark(request.getParameter("specialComment"));
			employee.setProfession(request.getParameter("worker"));
			employee.setId(Integer.valueOf(id));
			employee.setSex(request.getParameter("sex"));
			employee.setName(request.getParameter("maintenancerName"));
			employee.setCityCode(city);
			employee.setUpdateId(sysUser.getId());
			employee.setPhone(phone);
			employee.setEmail(email);
			employee.setPosition(request.getParameter("position"));
			if(StringUtils.equals(type, "4")){//城市管理员
				employee.setIdentifyLabel(2);
				employee.setType("0");
			}else{
				employee.setType(type);
				if(employee.getIdentifyLabel() != null && employee.getIdentifyLabel() == 2){
					employee.setIdentifyLabel(0);
				}
			}
			count = maintenancerService.updateMaintenancerInfo(employee);
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_update_dispatcher","3","您的个人信息已经被修改，请注意查看",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(phone);
			System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
		}else {
			map.put("remark", request.getParameter("specialComment"));
			map.put("identy", request.getParameter("identy"));
			map.put("worker", request.getParameter("worker"));
			map.put("name", request.getParameter("maintenancerName"));
			map.put("sex", request.getParameter("sex"));
			map.put("cityCode",city);
			map.put("email",email);
			map.put("position",request.getParameter("position"));
			Employee employee = employeeService.selectByPhone(phone);
			map.put("phone", phone);
			if (employee != null) {
				response.getWriter().print("exist");
				return null;
			}
			map.put("createId", sysUser.getId());
			count = maintenancerService.addMaintenancerInfo(map);
		}
		if(0<count){
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
		return null;
	}
	@SystemServiceLog(description = "冻结或启用维保员账户")
	@RequestMapping(value = "freezeOrActiveMaintenceAccount" , method = { RequestMethod.GET, RequestMethod.POST })
	public String freezeOrActiveMaintenceAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String operationType = request.getParameter("operationType");
		Integer count = 0;
		if(StringUtils.isBlank(operationType)){
			Employee employee = employeeService.selectByPrimaryKey(Integer.valueOf(id));
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_delete_dispatcher","3","您的账户已被冻结",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
			count = maintenancerService.freezeMaintenceAccount(id);
		}else {
			count = maintenancerService.activeMaintenceAccount(id);
		}
		if(0<count){
			response.getWriter().print("success");
		}else{
			response.getWriter().print("failed");
		}
		return null;
	}

	@SystemServiceLog(description="维保员重置密码")
	@RequestMapping(value = "/maintence_reset_password", method = { RequestMethod.POST })
	public String resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		employeeService.resetPassword(Integer.parseInt(id));
		response.getWriter().print("success");
		return null;
	}

}
