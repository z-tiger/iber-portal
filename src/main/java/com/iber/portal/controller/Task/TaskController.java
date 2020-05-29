package com.iber.portal.controller.Task;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iber.portal.dao.task.TaskPoolMapper;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.model.task.*;
import com.iber.portal.util.SendSMS;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.ParkMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.CarChargingRemind;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.car.CarRescueService;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.dispatcher.GridService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.task.TaskService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.DateTime;

/**
 * 任务控制器
 * @author Administrator
 *
 */
@Controller
public class TaskController extends MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GridService gridService;
	
	@Autowired
	private TaskService taskService;	
	
	@Autowired
	private CarRunService carRunService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private SystemMsgMapper  systemMsgMapper;
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	private CarRescueService carRescueService;
	
	@Autowired
	private TimeShareOrderMapper timeShareOrderMapper;
	
	@Autowired
	private ParkMapper parkMapper;

	@Autowired
	private TaskPoolMapper taskPoolMapper;
	public static final String CREATE_STATU = "1";
	/**
	 * 任务管理页面
	 */
	@SystemServiceLog(description = "任务管理页面")
	@RequestMapping(value = "task_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/task/TaskManagement";
	}
	
	/**
	 * 任务管理列表
	 */
	@SystemServiceLog(description = "任务管理列表")
	@RequestMapping(value = "task_management_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
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
		String name = request.getParameter("name");
		String taskName = request.getParameter("taskName");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		paramMap.put("name", name);
		paramMap.put("taskName", taskName);
		paramMap.put("taskType", request.getParameter("taskType"));
		String status = request.getParameter("status");
		if(StringUtils.isBlank(status)||"0".equals(status)){
			paramMap.put("status", null);
		}else {
			paramMap.put("status", status);
		}
		Pager<TaskInfo> pager = taskService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_task_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String name,String taskName,String taskType,String status,String cityCode,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "TaskReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市", "所属网格",  "任务级别", "任务名称", "任务类型", "执行人", 
				"状态","创建时间","任务更新时间","任务开始时间","任务结束时间","任务完成期限","任务说明","任务反馈" ,"规划里程","积分"};
		
		String keys[] = { "cityName", "gridName","taskLevelName", 
				"taskName", "taskType", "employeeName",
				"status","createTime","updateTime","taskBeginTime","doneTime","deadline","taskExplain","doneRemark","planMileage","score"};
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
		if(StringUtils.isBlank(status)||"0".equals(status)){
			paramMap.put("status", null);
		}else {
			paramMap.put("status", status);
		}
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		paramMap.put("name", name);
		paramMap.put("taskName", taskName);
		paramMap.put("taskType",taskType );
		paramMap.put("from", null);
		paramMap.put("to", null);
		Pager<TaskInfo> pager = taskService.queryPageList(paramMap);
		List<TaskInfo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "任务管理数据报表");
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
			List<TaskInfo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cityName", "gridName","taskLevelName", 
				"taskName", "taskType", "employeeName", "employeeLeader",
				"status","createTime","updateTime","taskBeginTime","doneTime","deadline","taskExplain","doneRemark","planMileage","score"};
		for (TaskInfo task : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], task.getCityName());
			map.put(keys[1], task.getGridName());
			map.put(keys[2], task.getTaskLevelName());
			map.put(keys[3], task.getTaskName());
			if(task.getTaskType()!=null ){
				if(task.getTaskType().equals("1") || task.getTaskType().equals("3")||task.getTaskType().equals("6") ){
					map.put(keys[4], "调度任务");
				}else if(task.getTaskType().equals("5")){
					map.put(keys[4], "救援任务");
				}else{
					map.put(keys[4], "维保任务");
				} 
			}else{
				map.put(keys[4], "无");
			}
			map.put(keys[5], task.getEmployeeName());
			map.put(keys[6], task.getEmployeeLeader()!=null?task.getEmployeeLeader():"");
			if(task.getStatus()!=null){
				if(task.getStatus().equals("1")) map.put(keys[7], "创建"); 
				if(task.getStatus().equals("2")) map.put(keys[7], "正在执行"); 
				if(task.getStatus().equals("3")) map.put(keys[7], "完成");
				if(task.getStatus().equals("4")) map.put(keys[7], "取消");
			}else{
				map.put(keys[7], task.getStatus()); 
			}
			map.put(keys[8], task.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime()):"");
			map.put(keys[9], task.getUpdateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getUpdateTime()):"");
			map.put(keys[10], task.getTaskBeginTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getTaskBeginTime()):"");
			map.put(keys[11], task.getDoneTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getDoneTime()):"");
			map.put(keys[12], task.getDeadline()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getDeadline()):"" );
			map.put(keys[13], task.getTaskExplain());
			map.put(keys[14], task.getDoneRemark());
			map.put(keys[15], task.getPlanMileage());
			map.put(keys[16], task.getScore());
			list.add(map);
		}
		return list;
	}
	
	@SystemServiceLog(description = "调度员信息列表")
	@RequestMapping(value = "getDispatcherList" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getDispatcherList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = getSysUser(request);
		String cityCode = sysUser.getCityCode();
		List<EmployeeOnGrid> employeeOnGrids = gridService.selectEmployeeOnGrid(cityCode);
		List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
		for (int i = 0; i < employeeOnGrids.size(); i++) {   
	        EmployeeOnGrid employeeOnGrid = (EmployeeOnGrid) employeeOnGrids.get(i);  
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", employeeOnGrids.get(i).getId()); 
	        map.put("text",employeeOnGrids.get(i).getName()); 
	        map.put("children",createComboTreeChildren(employeeOnGrid.getEmployees())); 
	        if (map != null){
	        	comboTreeList.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(comboTreeList));
		return null;
	}
	
	/**
	 * 执行人员列表
	 */
	@SystemServiceLog(description = "执行人员列表")
	@RequestMapping(value = "get_employee_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getEmployeeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = getSysUser(request);
		String cityCode = sysUser.getCityCode();
		List<EmployeeOnGrid> employeeOnGrids = gridService.selectEmployeeOnGrid(cityCode);
		List<Map<String,Object>> comboTreeList  =new ArrayList<Map<String,Object>>();
		for (int i = 0; i < employeeOnGrids.size(); i++) {   
	        EmployeeOnGrid employeeOnGrid = (EmployeeOnGrid) employeeOnGrids.get(i);  
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", employeeOnGrids.get(i).getId()); 
	        map.put("text",employeeOnGrids.get(i).getName()); 
	        map.put("children",createComboTreeChildren(employeeOnGrid.getEmployees())); 
	        if (map != null){
	        	comboTreeList.add(map);
	        }       
	    }   
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		paramMap.put("employeeType", "2");
		
		List<Employee> rescueEmployees =employeeService.queryEmployeesByCityCodeAndTaskType(paramMap);
		Map<String, Object> reMap = new HashMap<String, Object>();
		reMap.put("id", -2);
		reMap.put("text", "救援员");
		reMap.put("children", createComboTreeChildren(rescueEmployees));
		comboTreeList.add(reMap);
		
		paramMap.put("employeeType", "3");
		List<Employee> maintenceEmployees =employeeService.queryEmployeesByCityCodeAndTaskType(paramMap);
		Map<String, Object> mainMap = new HashMap<String, Object>();
		mainMap.put("id", -3);
		mainMap.put("text", "维保员");
		mainMap.put("children", createComboTreeChildren(maintenceEmployees));
		comboTreeList.add(mainMap);
		
		paramMap.put("employeeType", null);
		List<Employee> cityEmployees =employeeService.queryEmployeesByCityCodeAndTaskType(paramMap);
		Map<String, Object> cityMap = new HashMap<String, Object>();
		cityMap.put("id", -4);
		cityMap.put("text", "城市管理员");
		cityMap.put("children", createComboTreeChildren(cityEmployees));
		comboTreeList.add(cityMap);
		
		response.getWriter().print(JSON.toJSONString(comboTreeList));
		return null;
	}
	
	private SysUser getSysUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser) session.getAttribute("user");
		return sysUser;
	}
	
	/**
	 * 加载子节点
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> createComboTreeChildren(List<Employee> list) {  
	    List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();  
	    for (int j = 0; j < list.size(); j++) {  
	        Map<String, Object> map = null;  
	      //  Employee treeChild = (Employee) list.get(j);  
	        map = new HashMap<String, Object>();  
	        //这里必须要将对象角色的id、name转换成ComboTree在页面的显示形式id、text  
	        //ComboTree,不是数据表格，没有在页面通过columns转换数据的属性  
	        map.put("id", list.get(j).getId()); 
	        map.put("text", list.get(j).getName());
	        if (map != null){
		    	childList.add(map);
		    }
	    }
        return childList;
	}
 
	/**
	 * 获得所有的任务类型
	 */
	@SystemServiceLog(description = "获得所有的任务类型")
	@RequestMapping(value = "get_task_type_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getTaskTypeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<TaskType> taskTypes =taskService.selectAllTaskType();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < taskTypes.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", taskTypes.get(i).getId()); 
	        map.put("text",taskTypes.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
	/**
	 * 获得所有的车牌
	 */
	@SystemServiceLog(description = "获得所有的车牌")
	@RequestMapping(value = "get_lpn_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getLpnList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<Lpn> lpns =taskService.selectAllLpn();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < lpns.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", lpns.get(i).getId()); 
	        map.put("text",lpns.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	/**
	 * 获得所有的车牌
	 */
	@SystemServiceLog(description = "获得所有的车牌")
	@RequestMapping(value = "query_total_lpn" , method = { RequestMethod.GET, RequestMethod.POST })
	public String queryTotalLpn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<Lpn> lpns =taskService.queryTotalLpns();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < lpns.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", lpns.get(i).getId()); 
	        map.put("text",lpns.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	/**
	 * 获得所有的网点
	 */
	@SystemServiceLog(description = "获得所有的网点")
	@RequestMapping(value = "get_park_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getParkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<ParkName> parkNames =taskService.selectAllPark();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < parkNames.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", parkNames.get(i).getId()); 
	        map.put("text",parkNames.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
	/**
	 * 获得所有的任务等级
	 */
	@SystemServiceLog(description = "获得所有的任务等级")
	@RequestMapping(value = "get_task_grade_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getTaskGradeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<TaskGrade> taskGrades =taskService.selectAllTaskGrade();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < taskGrades.size(); i++) {   
	        Map<String, Object> map = new HashMap<String, Object>();  
	        map.put("id", taskGrades.get(i).getId()); 
	        map.put("text",taskGrades.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	
	/**
	 * 更新任务
	 */
	@SystemServiceLog(description = "更新任务")
	@RequestMapping(value = "updateTask" , method = { RequestMethod.GET, RequestMethod.POST })
	public String updateTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		//根据id查询要修改的任务
		TaskInfo taskInfo = taskService.selectByPrimaryKey(Integer.parseInt(request.getParameter("id")));
		String taskTypeStr = request.getParameter("taskTypeNo");
		String lpn = request.getParameter("lpnId");
		//查询车牌
		CarRun car = carRunService.selectByPrimaryKey(Integer.parseInt(lpn));
		if (!StringUtils.isBlank(taskTypeStr)) {
			TaskType taskType = taskService.selectByTaskType(Integer.parseInt(taskTypeStr));
			taskInfo.setTaskTypeName(taskType.getName());
			taskInfo.setTaskName(car.getLpn() + taskType.getName()); 
		}
		taskInfo.setLpnId(Integer.valueOf(lpn));
		String taskLevelStr = request.getParameter("taskLevel");
		String employeeIdStr = request.getParameter("employeeId");
		String beginParkIdStr = request.getParameter("beginParkId");
		String endParkIdStr = request.getParameter("endParkId");
		String oldEmployeeIdStr = request.getParameter("oldEmployeeId");
		String deadline = request.getParameter("deadline");
		String timeLimit = request.getParameter("requestPayTime");
		String taskExplain = request.getParameter("specialComment");
		if (!StringUtils.isBlank(beginParkIdStr)) {
			taskInfo.setBeginParkId(Integer.parseInt(beginParkIdStr));
		}
		if (!StringUtils.isBlank(endParkIdStr)) {
			taskInfo.setEndParkId(Integer.parseInt(endParkIdStr));
		}
		if (!StringUtils.isBlank(employeeIdStr)) {
			taskInfo.setEmployeeId(Integer.parseInt(employeeIdStr));
			// 如果这是救援任务且该任务的执行有更改,则要把车辆救援管理对应的记录修改新的责任人
			if("5".equals(taskInfo.getTaskType())){
				SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
				carRescueService.updateRescueRecordCharge(taskInfo.getLpn(),Integer.valueOf(employeeIdStr),sysUser.getAccount());
			}
		}else {
			taskInfo.setEmployeeId(Integer.parseInt(oldEmployeeIdStr));
		}
		if(!StringUtils.isBlank(car.getLongitude())){
			taskInfo.setLongitude(Double.valueOf(car.getLongitude()));
		}
		if(!StringUtils.isBlank(car.getLatitude())){
			taskInfo.setLatitude(Double.valueOf(car.getLatitude()));
		}
		taskInfo.setTaskType(taskTypeStr);
		taskInfo.setLpn(car.getLpn());
		taskInfo.setCityCode(car.getCityCode());
		taskInfo.setTaskLevel(taskLevelStr);
		taskInfo.setTimeLimit(timeLimit);
		taskInfo.setTaskExplain(taskExplain);
		taskInfo.setDeadline(DateTime.StringToDate(deadline));
		taskInfo.setUpdateTime(new Date());
		SysUser sysUser = getSysUser(request);
		taskInfo.setUpdateId(sysUser.getId());
		taskService.updateTask(taskInfo);
		PushCommonBean pushCommon = new PushCommonBean("server_push_employee_task",taskTypeStr,"您有任务已经修改，请注意查看",taskInfo) ;
		/**根据员工的id查询员工的phone(客户端别名)*/
		Employee employee = new Employee();
		if (!StringUtils.isBlank(employeeIdStr)&& !employeeIdStr.equals(oldEmployeeIdStr)) {
			employee = employeeService.selectByPrimaryKey(Integer.parseInt(employeeIdStr));
			List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
		}
		employee = employeeService.selectByPrimaryKey(Integer.parseInt(oldEmployeeIdStr));
		taskInfo.setEmployeeId(employee.getId());
		List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
		for (String employeeCid : cidList) {
			PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 创建任务
	 */
	@Transactional(rollbackFor = Exception.class)
	@SystemServiceLog(description = "创建任务")
	@RequestMapping(value = "saveOrUpdateTask" , method = { RequestMethod.GET, RequestMethod.POST })
	public String saveOrUpdateTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		//requestType 为1的时候，表明这个是从任务池里面分配的任务
		String requestType = request.getParameter("requestType");
		String taskPoolId = request.getParameter("taskId");

		String employeeId = request.getParameter("id");
		String taskTypeStr = request.getParameter("taskType");
		String lpn = request.getParameter("lpn");
		String beginParkIdStr = request.getParameter("beginParkId");
		String deadline = request.getParameter("deadline");
		String endParkIdStr = request.getParameter("endParkId");
		String taskLevelStr = request.getParameter("taskLevel");
		String timeLimit = request.getParameter("requestPayTime");
		String taskExplain = request.getParameter("specialComment");
		//查询车牌
		CarRun car = carRunService.selectByPrimaryKey(Integer.parseInt(lpn));
		//判断该车辆是否存在救援中的任务
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("taskType", taskTypeStr);
			paramMap.put("lpn", car.getLpn());
			if("5".equals(taskTypeStr)){
				Integer count = taskService.selectByTaskTypeAndLpn(paramMap);
				if (count > 0) {//如果存在救援中的任务
					response.getWriter().print("existing");
					return null;
				}	
			}
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setEmployeeId(Integer.parseInt(employeeId));
		if (!StringUtils.isBlank(beginParkIdStr)) {
			taskInfo.setBeginParkId(Integer.parseInt(beginParkIdStr));
		}
		
		//获得任务类型名称
		if (!StringUtils.isBlank(taskTypeStr)) {
			TaskType taskType = taskService.selectByTaskType(Integer.parseInt(taskTypeStr));
			taskInfo.setTaskTypeName(taskType.getName());
			taskInfo.setTaskName(car.getLpn() + taskType.getName()); 
		}
		taskInfo.setCityCode(car.getCityCode());
		SysUser sysUser = getSysUser(request);
		taskInfo.setCreateId(sysUser.getId());
		taskInfo.setCreateTime(new Date());
		taskInfo.setTaskExplain(taskExplain);
		taskInfo.setTaskLevel(taskLevelStr);
		taskInfo.setEmployeeId(Integer.parseInt(employeeId));
		if (!StringUtils.isBlank(endParkIdStr)) {
			taskInfo.setEndParkId(Integer.parseInt(endParkIdStr));
		}
		taskInfo.setLpn(car.getLpn());
		taskInfo.setTimeLimit(timeLimit);
		taskInfo.setTaskType(taskTypeStr);
		taskInfo.setDeadline(DateTime.StringToDate(deadline));
		taskInfo.setStatus(CREATE_STATU);
		taskService.insert(taskInfo);
		if ("1".equals(requestType)){
			TaskPool taskPool = taskPoolMapper.selectByPrimaryKey(Integer.parseInt(taskPoolId));

			if (!taskPool.getStatus().equals("1")){
				response.getWriter().print("fail");
				throw new Exception("task is aleady accept");
			}
			taskPool.setStatus("3");
			taskPool.setTaskInfoId(taskInfo.getId());
			taskPoolMapper.updateByPrimaryKeySelective(	taskPool);
		}
		taskInfo.setCreateTime(null);
		PushCommonBean pushCommon = new PushCommonBean("server_push_employee_task","1","您有新的任务，请尽快处理",taskInfo) ;
		/**根据员工的id查询员工的phone(客户端别名)*/
		Employee employee = employeeService.selectByPrimaryKey(Integer.parseInt(employeeId));
		List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
		for (String employeeCid : cidList) {
			PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
		}
		//如果这个任务是救援任务，需要短信通知员工和会员，并推送消息给会员
		String ip = getRemortIP(request);
		//String sendUrl = sysParamService.selectByKey("send_sms_url").getValue();
		Map<String, Object> params = new HashMap<String, Object>();
        params.put("lpn", car.getLpn());
		if (taskTypeStr.equals("5")) {
			//如果是救援任务，需要将救援车辆的地址，经纬度保存到任务中
			TimeShareOrder order = timeShareOrderMapper.selectLastedOrderbyLpn(params);
			try {
				taskInfo.setMemberId(Integer.valueOf(order.getMemberId()));
			} catch (Exception e) {
				taskInfo.setMemberId(null);
			}
			taskInfo.setAddress(car.getAddress());
			taskInfo.setLatitude(Double.valueOf(car.getLatitude()));
			taskInfo.setLongitude(Double.valueOf(car.getLongitude()));
			taskService.updateTask(taskInfo);
			//将车辆插入到车辆救援中
			CarRescue carRescue = new CarRescue();
			carRescue.setStartTime(new Date());
			carRescue.setStatus("1");
			carRescue.setCreateUser(getUser(request).getAccount());
			carRescue.setCreateTime(new Date());
			carRescue.setCarId(car.getId());
			carRescue.setLpn(car.getLpn());
			carRescue.setResponsiblePerson(employee.getName());
			carRescue.setResponsiblePersonPhone(employee.getPhone());
			carRescue.setReason(taskExplain);
			carRescueService.saveCarRescueInfo(carRescue);
			//给救援人员发短信
			SendSMS.send(employee.getPhone(),"",2678,car.getLpn());
			//根据车牌查询用车会员
			CarChargingRemind remind = timeShareOrderService.selectUserByLpn(car.getLpn());
			//给会员发短信
			if (remind != null) {
				taskInfo.setMemberId(remind.getMemberId());
				taskService.updateTask(taskInfo);
				//获取加密token
				/*String encryptToken2 = SendSMS.encryptBySalt(employee.getPhone());
				param ="{\"telephoneNo\":\""+remind.getPhone()+"\",\"ipAddress\":\""+ip+"\",\"templateId\":\"2679\",\"contentParam\":\""+new String((employee.getPhone()+"|"+employee.getName()).getBytes("utf-8"),"ISO-8859-1")+"\",\"token:\":\""+encryptToken2+"\"}";
				params.put("msgContentJson", param); 
				HttpsClientUtil.post(sendUrl+"",params) ;*/

				SendSMS.send(remind.getPhone(),"",2679,employee.getPhone()+"|"+employee.getName());
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
						List<String> alias = PushClientEmployee.queryClientId(remind.getPhone());
						if(!alias.isEmpty() && alias.size()> 0){
							for(String cid : alias){
								JsonConfig jsonConfig = new JsonConfig();
								jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
								PushClientEmployee.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
							}
						}
					}
				}
			}
			
		}
		response.getWriter().print("success");   
		return null;
	}
	
	@SystemServiceLog(description = "获得车辆监控还车的网点")
	@RequestMapping(value = "get_return_park_list" , method = { RequestMethod.GET, RequestMethod.POST })
	public String getReturnParkList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String lpn = request.getParameter("lpn");
		TimeShareOrder order = timeShareOrderMapper.selectRunningOrderByLpn(lpn);
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		List<ParkName> parkNames = taskService.selectAllPark();
		//Park orderPark = null;
		Park orderPark = parkMapper.selectByPrimaryKey(order.getParkId());
		// 如果是企业员工订单
		if(null!=order&&Objects.equals("enterpriseOrder", order.getOrderType())){ 
			// 如果约车网点的enterpriseId是空，说明这个网点普通网点，返回网点列表数据时就要把所有普通网点数据返回
			if(null == orderPark.getEnterpriseId()){
				for (ParkName parkName : parkNames) {
					if(null==parkName.getEnterpriseId()){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", parkName.getId()); 
						map.put("text",parkName.getName());
						list.add(map);
					}
				}
			}else{
				for (ParkName parkName : parkNames) {
					if(null!=parkName.getEnterpriseId()&&
							Objects.equals(order.getEnterpriseId().toString(), parkName.getEnterpriseId().toString())){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", parkName.getId()); 
						map.put("text",parkName.getName());
						list.add(map);
					}
				}
			}
		}
		// 如果不是企业端员工订单，返回非专属网点
		else{
			for (ParkName parkName : parkNames) {  
		        if(null==parkName.getEnterpriseId()){
			        Map<String, Object> map = new HashMap<String, Object>();  
			        map.put("id", parkName.getId()); 
			        map.put("text",parkName.getName()); 
		        	list.add(map);
		        }
		    }   
		}
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
}
