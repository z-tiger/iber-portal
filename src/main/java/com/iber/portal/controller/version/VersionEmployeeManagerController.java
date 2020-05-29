package com.iber.portal.controller.version;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.versions.VersionsEmployee;
import com.iber.portal.service.version.VersionsEmployeeManagerService;
@Controller
public class VersionEmployeeManagerController extends MainController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private VersionsEmployeeManagerService versionsEmployeeManagerService ;
	
	/**
	 * 员工版本管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="员工版本管理")
	@RequestMapping(value = "/employeeVersion", method = { RequestMethod.GET })
	public String employeeVersionPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "version/employeeVersion";		
	}
	/**
	 * 员工版本管理list
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="员工版本管理list")
	@RequestMapping(value = "/manager_employee_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String employeeList(int page, int rows,HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String categoryCode = request.getParameter("categoryCode");
		String versionNo = request.getParameter("versionNo");
		HashMap<String, Object> record = new  HashMap<String, Object>();
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		if(!StringUtils.isBlank(categoryCode)){
			record.put("categoryCode", categoryCode);
		}
		if(!StringUtils.isBlank(versionNo)){
			record.put("versionNo", versionNo);
		}
		record.put("page", pageInfo.get("first_page"));
		record.put("size", pageInfo.get("page_size"));
		List<VersionsEmployee> data = versionsEmployeeManagerService.selectAll(record);
		int total=versionsEmployeeManagerService.selectByPrimaryKeyRecords(record);
		JSONObject obj = new JSONObject();
		obj.put("total",total );
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		return null;
	}
	/**
	 * 员工版本添加和编辑
	 * @param request
	 * @param response
	 * @param rearviewFile
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="员工版本添加和编辑")
	@RequestMapping(value = "/add_versions_employee", method = { RequestMethod.GET , RequestMethod.POST })
	public String AddOReditEmployee(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
		String id = request.getParameter("id");
		String categoryCode = request.getParameter("categoryCode");
		String versionName = request.getParameter("versionName");
		String versionNo = request.getParameter("versionNo");
		int versionRecord = Integer.parseInt(request.getParameter("versionRecord"));
		if (id!=null && !id.equals("")) {
			VersionsEmployee currObj = versionsEmployeeManagerService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCategoryCode(categoryCode);
				currObj.setVersionName(versionName);
				currObj.setVersionNo(versionNo);
				currObj.setVersionRecord(versionRecord);
				currObj.setUpdateId(sysUser.getId());
				currObj.setUpdateTime(new Date());
				versionsEmployeeManagerService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			VersionsEmployee obj = new VersionsEmployee();
			obj.setCategoryCode(categoryCode);
			obj.setVersionName(versionName);
			obj.setVersionNo(versionNo);
			obj.setVersionRecord(versionRecord);
			obj.setCreateId(sysUser.getId());
			obj.setCreateTime(new Date());
			versionsEmployeeManagerService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}
	/**
	 * 删除员工帮本记录
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除员工帮本记记录")	
	@RequestMapping(value = "/versions_employ_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String versionsEmployDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		response.setContentType("text/html;charset=utf-8");

		if (id!=null && !id.equals("")) {
			versionsEmployeeManagerService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
}
