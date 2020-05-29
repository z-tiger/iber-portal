package com.iber.portal.controller.monitorCenter;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.dispatcher.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class EmployeeMonitorController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 网格管理列表
	 */
	@SystemServiceLog(description = "监控中心员工监控列表")
	@RequestMapping(value = "employee_working_page", method = {RequestMethod.GET, RequestMethod.POST })
	public String dispatcherManagementList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", pageInfo.get("first_page"));
		paramMap.put("to", pageInfo.get("page_size"));
		// paramMap.put("cityCode", cityCode);
		if (user.getCityCode().equals("00")) {
			if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
				if (request.getParameter("cityCode").equals("00")) {
					paramMap.put("cityCode", null);
				} else {
					paramMap.put("cityCode", request.getParameter("cityCode"));
				}
			}
		} else {
			paramMap.put("cityCode", user.getCityCode());
		}
		String dispatcherName = request.getParameter("dispatcherName");
		String griddingId = request.getParameter("griddingId");

		if (!StringUtils.isBlank(griddingId)) {
			paramMap.put("griddingId", Integer.parseInt(griddingId));
		}
		paramMap.put("dispatcherName", dispatcherName);
		String empType = request.getParameter("empType");
		if(StringUtils.isBlank(empType)){
			paramMap.put("empType", "");
		}else if("4".equals(empType)){
			paramMap.put("empType", null);
		}else {
			paramMap.put("empType", empType);
		}
		paramMap.put("wstatus", request.getParameter("wstatus"));
		Pager<Employee> pager = employeeService.queryEmployeePortInfos(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}

	@SystemServiceLog(description = "获取救援人员列表")
	@RequestMapping(value = "/getEmployeeInfo",method  = {RequestMethod.GET, RequestMethod.POST })
	public String getEmployeeInfo(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("from", pageInfo.get("first_page"));
		map.put("to", pageInfo.get("page_size"));
		map.put("name",name);
		map.put("phone",phone);
	//	List<Employee> employees = employeeService.getEmployeeInfo(map);
		Pager<Employee> pages = employeeService.getEmployeeInfo(map);
		response.getWriter().print(Data2Jsp.Json2Jsp(pages));
		return null;
	}

}
