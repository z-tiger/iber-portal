package com.iber.portal.controller.rescuer;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.employee.RescuerEmployeeInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.rescuer.RescuerService;
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
public class RescuerController {
    @Autowired
	private RescuerService rescuerService;
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private TaskService taskService;
	/**
	 * 救援员管理页面
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@SystemServiceLog(description = "救援员管理页面")
	@RequestMapping(value = "rescuer_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String rescuerManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/rescuer/rescuer";
	}
	
	
	/**
	 * 救援员信息
	 */
	@SystemServiceLog(description = "救援员信息")
	@RequestMapping(value = "getRescuerInfos" , method = { RequestMethod.GET, RequestMethod.POST })
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
		map.put("rescuerName", request.getParameter("rescuerName"));
		map.put("status", request.getParameter("status"));
		Pager<RescuerEmployeeInfo> pager = rescuerService.getRescuerInfos(map);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_rescuer_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String rescuerName,String status,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "RescuerReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市","姓名", "身份", "状态", "手机号", "创建时间","最新修改时间",
				"当前任务数","今日完成数","正在执行","累计完成","今日积分","本月积分","总积分","备注" };
		
		String keys[] = {"cityName", "name", "identifyLabel",
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
		paramMap.put("rescuerName", rescuerName);
		paramMap.put("page", null);
		paramMap.put("rows", null);
		
		Pager<RescuerEmployeeInfo> pager = rescuerService.getRescuerInfos(paramMap);
		
		List<RescuerEmployeeInfo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "救援员数据报表");
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
			List<RescuerEmployeeInfo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = {"cityName", "name", "identifyLabel",
				"status", "phone","createTime","updateTime", "currentTask", "todayCompleteTask",
				"processTask","totalCompleteTask","todayScore","monthScore","totalScore","remark"};
		for (RescuerEmployeeInfo rescuer : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], rescuer.getCityName());
			map.put(keys[1], rescuer.getName());
			if(rescuer.getIdentifyLabel() != null){
				if(rescuer.getIdentifyLabel() == 1){
					map.put(keys[2], "救援主管");
				}else{
					map.put(keys[2], "救援员");
				}
			}else{
				map.put(keys[2], "救援员");
			}
			if(rescuer.getStatus() != null){
				if (rescuer.getStatus().equals("working")) {
					map.put(keys[3], "上班");
				} else if(rescuer.getStatus().equals("ordered")) {
					map.put(keys[3], "预约车辆中");
				} else if(rescuer.getStatus().equals("useCar")) {
					map.put(keys[3], "使用车辆中");
				} else if(rescuer.getStatus().equals("freeze")) {
					map.put(keys[3], "冻结");
				} else{
					map.put(keys[3], "下班");
				}
			}else{
				map.put(keys[3], "下班");
			}
			map.put(keys[4], rescuer.getPhone());
			map.put(keys[5], rescuer.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rescuer.getCreateTime()):"");
			map.put(keys[6], rescuer.getUpdateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rescuer.getUpdateTime()):"");
			map.put(keys[7], rescuer.getCurrentTask());
			map.put(keys[8], rescuer.getTodayCompleteTask()); 
			map.put(keys[9], rescuer.getProcessTask());
			map.put(keys[10], rescuer.getTotalCompleteTask());
			map.put(keys[11], rescuer.getTodayScore());
			map.put(keys[12], rescuer.getMonthScore());
			map.put(keys[13], rescuer.getTotalScore());
			map.put(keys[14], rescuer.getRemark());
			
			list.add(map);
		}
		return list;
	}
	/**
	 * 保存或者更新
	 */
	@SystemServiceLog(description = "添加或修改救援人员")
	@RequestMapping(value = "addOrUpdateRescuerInfo" , method = { RequestMethod.GET, RequestMethod.POST })
	public String addOrUpdateRescuerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String operateType = request.getParameter("operateType");
		String id = request.getParameter("ed-id");
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		String phone = request.getParameter("phone");
		
		String type = request.getParameter("ed-type");
		//判断是否有任务
		if(!StringUtils.equals(type, "2")){
			int record = taskService.selectNonTaskRecords(id);
			if(record > 0){
				response.getWriter().print("hasTask");
				return null;
			}
		}
		if(!phone.matches("^[1][3,4,5,7,8][0-9]{9}$")){
			response.getWriter().print("p-wrong");
			return null;
		}
		String email=request.getParameter("email");
		if(!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
			response.getWriter().print("email-fail");
			return null;
		}
		String city = request.getParameter("city");
		if("00".equals(city)){
			response.getWriter().print("c-wrong");
			return null;
		}
		int count = 0;
		if(StringUtils.isBlank(operateType)){
			Employee edEmployee = employeeService.selectByPhone(phone);
			if(edEmployee != null){
				// 传过来的id与根据手机号查出的员工id不相等,则说明该手机号已被注册
				if(!edEmployee.getId().toString().equals(id)){
					response.getWriter().print("exist");
					return null;
				}
			}
			edEmployee = new Employee();
			edEmployee.setPhone(phone);
			edEmployee.setRemark(request.getParameter("ed-specialComment"));
			edEmployee.setIdentifyLabel(Integer.valueOf(request.getParameter("ed-identy")));
			edEmployee.setName(request.getParameter("ed-rescuerName"));
			edEmployee.setSex(request.getParameter("ed-sex"));
			edEmployee.setCityCode(city);
			edEmployee.setId(Integer.valueOf(id));
			edEmployee.setUpdateId(sysUser.getId());
			edEmployee.setEmail(email);
			edEmployee.setPosition(request.getParameter("position"));
			edEmployee.setProfession(request.getParameter("ed-worker"));
			if(StringUtils.equals(type, "4")){//城市管理员
				edEmployee.setIdentifyLabel(2);
				edEmployee.setType("0");
			}else{
				edEmployee.setType(type);
				if(edEmployee.getIdentifyLabel() != null && edEmployee.getIdentifyLabel() == 2){
					edEmployee.setIdentifyLabel(0);
				}
			}
			
			count = rescuerService.updateRescuerInfo(edEmployee);
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_update_dispatcher","3","您的个人信息已经被修改，请注意查看",edEmployee) ;
			List<String> cidList = PushClientEmployee.queryClientId(phone);
			System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
		}else {
			map.put("remark", request.getParameter("specialComment"));
			map.put("identy", request.getParameter("identy"));
			map.put("worker", request.getParameter("worker"));
			map.put("name", request.getParameter("rescuerName"));
			map.put("sex", request.getParameter("sex"));
			map.put("city_code", city);
			Employee employee = employeeService.selectByPhone(phone);
			if (employee != null) {
				response.getWriter().print("exist");
				return null;
			}
			map.put("createId", sysUser.getId());
			map.put("phone", phone);
			map.put("position",request.getParameter("position"));
			map.put("email",email);
			count = rescuerService.addRescuerInfo(map);
		}
		if(0<count){
			response.getWriter().print("success");
		}else {
			response.getWriter().print("fail");
		}
		return null;
	}
	@SystemServiceLog(description = "冻结或启用救援员账户")
	@RequestMapping(value = "freezeOrActiveRescuerAccount" , method = { RequestMethod.GET, RequestMethod.POST })
	public String freezeOrActiveRescuerAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		Employee employee = employeeService.selectByPrimaryKey(Integer.valueOf(id));
		String operationType = request.getParameter("operationType");
		Integer count = 0;
		if(StringUtils.isBlank(operationType)){
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_delete_dispatcher","2","您的账户已被冻结",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
			count = rescuerService.freezeRescuerAccount(id);
		}else {
			count = rescuerService.activeRescuerAccount(id);
		}
		if(0<count){
			response.getWriter().print("success");
		}else{
			response.getWriter().print("failed");
		}
		return null;
	}

	@SystemServiceLog(description="救援员重置密码")
	@RequestMapping(value = "/rescuer_reset_password", method = { RequestMethod.POST })
	public String resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		employeeService.resetPassword(Integer.parseInt(id));
		response.getWriter().print("success");
		return null;
	}


}
