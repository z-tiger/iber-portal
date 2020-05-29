package com.iber.portal.controller.dispatcher;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.base.BaseController;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.dispatcher.EmployeeGridRelationService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.task.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class DispatcherController extends BaseController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeGridRelationService employeeGridRelationService;
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 调度员管理页面
	 */
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@SystemServiceLog(description = "调度员管理页面")
	@RequestMapping(value = "dispatcher_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String dispatcherManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/dispatcher/dispatcherManagement";
	}
	
	/**
	 * 网格管理列表
	 */
	@SystemServiceLog(description = "调度员管理列表")
	@RequestMapping(value = "dispatcher_management_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String dispatcherManagementList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
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
		String dispatcherName = request.getParameter("dispatcherName");
		String griddingId = request.getParameter("griddingId");
		String status = request.getParameter("status");
		if(!StringUtils.isBlank(griddingId)){
			paramMap.put("griddingId", Integer.parseInt(griddingId));
		}
		paramMap.put("dispatcherName", dispatcherName);
		paramMap.put("status", status);
		Pager<Employee> pager = employeeService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	@SystemServiceLog(description="调度员重置密码")
	@RequestMapping(value = "/dispatcher_reset_password", method = { RequestMethod.POST })
	public String resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        employeeService.resetPassword(Integer.parseInt(id));
        response.getWriter().print("success");
		return null;
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_dispatcher_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String dispatcherName,String griddingId,String status,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "DispatcherReport" ;
		//列名充电桩编码	
		String columnNames [] = {"姓名", "所属城市",  "所属网格", "身份", "状态", "手机号", 
				"当前任务数","今日完成数","正在执行","累计完成","今日积分","本月积分","总积分","备注" };
		
		String keys[] = { "name", "cityName","gridName", "identifyLabel",
				"status", "phone", "currentTask", "todayCompleteTask",
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
		paramMap.put("dispatcherName", dispatcherName);
		if(!StringUtils.isBlank(griddingId)){
			paramMap.put("griddingId", Integer.parseInt(griddingId));
		}
		paramMap.put("from", null);
		paramMap.put("to", null);
		
		Pager<Employee> pager = employeeService.queryPageList(paramMap);
		List<Employee> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "调度员理数据报表");
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
			List<Employee> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "name", "cityName","gridName", "identifyLabel",
				"status", "phone", "currentTask", "todayCompleteTask",
				"processTask","totalCompleteTask","todayScore","monthScore","totalScore","remark"};
		for (Employee emp : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], emp.getName());
			map.put(keys[1], emp.getCityName());
			if(emp.getGridName() != null){
				map.put(keys[2], emp.getGridName());
			}else{
				map.put(keys[2], "");
			}
			if(emp.getIdentifyLabel() != null){
				if(emp.getIdentifyLabel() == 1){
					map.put(keys[3], "调度管理员");
				}else{
					map.put(keys[3], "调度员");
				}
			}else{
				map.put(keys[3], "调度员");
			}
			if(emp.getStatus() != null){
				if (emp.getStatus().equals("working")) {
					map.put(keys[4], "上班");
				} else if(emp.getStatus().equals("ordered")) {
					map.put(keys[4], "预约车辆中");
				} else if(emp.getStatus().equals("useCar")) {
					map.put(keys[4], "使用车辆中");
				} else if(emp.getStatus().equals("freeze")) {
					map.put(keys[4], "冻结");
				} else{
					map.put(keys[4], "下班");
				}
			}else{
				map.put(keys[4], "下班");
			}
			map.put(keys[5], emp.getPhone());
			map.put(keys[6], emp.getCurrentTask());
			map.put(keys[7], emp.getTodayCompleteTask()); 
			map.put(keys[8], emp.getProcessTask());
			map.put(keys[9], emp.getTotalCompleteTask());
			map.put(keys[10], emp.getTodayScore());
			map.put(keys[11], emp.getMonthScore());
			map.put(keys[12], emp.getTotalScore());
			map.put(keys[13], emp.getRemark());
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获得员工手机号
	 */
	@SystemServiceLog(description = "获得员工手机号")
	@RequestMapping(value = "get_phone_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getPhoneList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<Map<String, Object>> phones =employeeService.selectAllPhone();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < phones.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", phones.get(i).get("id")); 
	        map.put("text",phones.get(i).get("phone")); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
	/**
	 * 保存或者更新
	 */
	@Transactional(rollbackFor=Exception.class)
	@SystemServiceLog(description = "保存或者更新调度员")
	@RequestMapping(value = "saveOrUpdateDispatcher" , method = { RequestMethod.GET, RequestMethod.POST })
	public String saveOrUpdateDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String phone = request.getParameter("phone");
		String name = request.getParameter("dispatherName");
		String identifyLabel = request.getParameter("ident");
		String sex = request.getParameter("sex");
		String city = request.getParameter("city");
		String worker = request.getParameter("worker");
		String remark = request.getParameter("specialComment");
		String type = request.getParameter("type");
		String position=request.getParameter("position");
		String email=request.getParameter("email");
		//判断是否有任务
		if(!StringUtils.equals(type, "1")){
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
		if("00".equals(city)){
			response.getWriter().print("c-wrong");
			return null;
		}
		if(!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")){
			response.getWriter().print("email-fail");
			return null;
		}
		Employee employee = employeeService.selectByPhone(phone);
		if (StringUtils.isBlank(id)) {
			if(null!=employee){
				response.getWriter().print("exist");
				return null;
			}
			employee = new Employee();
			employee.setCityCode(city);
			employee.setCreateTime(new Date());
			SysUser sysUser = getSysUser(request);
			employee.setPhone(phone);
			employee.setCreateId(sysUser.getId());
			employee.setName(name);
			employee.setProfession(worker);
			employee.setSex(sex);
			employee.setType("1");
			employee.setIdentifyLabel(0);
			employee.setRemark(remark);
			employee.setPosition(position);
			employee.setEmail(email);
			employeeService.insert(employee);
		}else {//修改
			if(null!=employee && !employee.getId().toString().equals(id)){
				response.getWriter().print("exist");
				return null;
			}
			employee = new Employee();
			employee.setId(Integer.valueOf(id));
			// 普通调度员升级为主管类型或城市管理员时,检查该调度员有没有所属的片区,如果没有就提示为该调度员加入片区
			int count = employeeService.queryEmployeeGrid(id);
            if(0==count && StringUtils.equals(type, "1")){
            	if("1".equals(identifyLabel)){
            		response.getWriter().print("no-grid");
            		return null;	
            	}
            	else {
            		// 主要是修改e_employee_gridding_relation表的is_manager字段
                	employeeGridRelationService.updateEmpGridRealtion(id,"1");
    			}
            }else {
            	if("0".equals(identifyLabel)){
            		employeeGridRelationService.updateEmpGridRealtion(id,"2");
            	}
            	else {
            		employeeGridRelationService.updateEmpGridRealtion(id,"1");
				}
			}
			employee.setName(name);
			employee.setPhone(phone);
			employee.setIdentifyLabel(Integer.valueOf(identifyLabel));
			employee.setSex(sex);
			employee.setCityCode(city);
			//employee.setType("1");
			if(StringUtils.equals(type, "4")){//城市管理员
				employee.setIdentifyLabel(2);
				employee.setType("0");
			}else{
				employee.setType(type);
				if(employee.getIdentifyLabel() != null && employee.getIdentifyLabel() == 2){
					employee.setIdentifyLabel(0);
				}
			}
			employee.setProfession(worker);
			employee.setUpdateTime(new Date());
			SysUser sysUser = getSysUser(request);
			employee.setUpdateId(sysUser.getId());
			employee.setRemark(remark);
			employee.setPosition(position);
			employee.setEmail(email);
			int re = employeeService.update(employee);
			//当员工角色转换成非调度员时 删除员工与网格之间的关系
			if(re > 0 && !StringUtils.equals(type, "1")){
				employeeGridRelationService.deleteEmployeeByEmployeeId(employee.getId());
			}
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_update_dispatcher","1","您的个人信息已经被修改，请注意查看",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			//System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
		}
		response.getWriter().print("success");
		return null;
	}
	
	private SysUser getSysUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return sysUser;
	}
	
	/**
	 * 删除调度员
	 */
	@Transactional(rollbackFor=Exception.class)
	@SystemServiceLog(description = "冻结或启用调度员账户")
	@RequestMapping(value = "freezeOrActiveDispatcherAccount" , method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteDispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		Employee employee = employeeService.selectByPrimaryKey(Integer.parseInt(id));
		String operationType = request.getParameter("operationType");
		Integer count = 0;
		if(StringUtils.isBlank(operationType)){
			PushCommonBean pushCommon = new PushCommonBean("server_push_employee_delete_dispatcher","1","您的账户已被冻结",employee) ;
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			System.out.println(JSON.toJSON(pushCommon));
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
			// 删除已改为冻结
			count = employeeService.freezeDispatcherAccount(Integer.parseInt(id));
			//将调度员从网格中删除
			employeeGridRelationService.deleteEmployeeByEmployeeId(Integer.parseInt(id));
		}else {
			count = employeeService.activeDispatcherAccount(Integer.parseInt(id));
		}
		if(0<count){
			response.getWriter().print("success");
		}else{
			response.getWriter().print("failed");
		}
		return null;
	}
	/**
	 * 根据id查询调度员
	 */
	@SystemServiceLog(description = "根据id查询调度员")
	@RequestMapping(value = "selectByEmployeeId" , method = { RequestMethod.GET, RequestMethod.POST })
	public String selectByEmployeeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("employeeId");
		Employee employee = employeeService.selectByEmployeeId(Integer.parseInt(id));
		response.getWriter().print(JSON.toJSONString(employee));
		return null;
	}
	
	/**删除调度员指纹*/
	@SystemServiceLog(description = "删除调度员指纹")
	@RequestMapping(value = "employee_del_finger" , method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteEmployeeFinger(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
	   int nums =  employeeService.deleteEmployeeFinger(Integer.parseInt(id));
	   if(nums>0){
		   response.getWriter().print("succ"); 
	   }else{
		   response.getWriter().print("fail");
	   }
		return null;
	}

	/**删除调度员指纹*/
	@SystemServiceLog(description = "删除员工指纹")
	@RequestMapping(value = "employeeDelTboxFinger" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteEmployeeTboxFinger(Integer id) throws Exception {
	   int num =  employeeService.deleteEmployeeTboxFinger(id);
	   if(num>0){
		   return success();
	   }else{
		   return fail("删除失败！");
	   }
	}
}
