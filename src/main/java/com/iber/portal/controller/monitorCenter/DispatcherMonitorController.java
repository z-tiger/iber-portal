package com.iber.portal.controller.monitorCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iber.portal.getui.PushClientEmployee;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.dispatcher.EmployeeVo;
import com.iber.portal.model.dispatcherMonitor.DispatcherLocation;
import com.iber.portal.model.employee.EmployeeOrder;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.FastJsonUtils;

/**
 * 调度员监控
 * @author Administrator
 *
 */
@Controller
public class DispatcherMonitorController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SysParamService sysParamService;

	
	
	
	@SystemServiceLog(description = "员工监控中心")
	@RequestMapping(value = "/dispatcher_monitor_page", method = { RequestMethod.GET })
	public String dispatcherMonitorPage(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
		log.info("当前订单页面跳转") ;
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		//获得登录人的经纬度
		String latitude = (String) session.getAttribute("latitude");
		String longitude = (String) session.getAttribute("longitude");
		Double latitudeDouble = null;
		Double longitudeDouble = null;
		if (!StringUtils.isBlank(latitude) && !StringUtils.isBlank(longitude)) {
			latitudeDouble = Double.parseDouble(latitude);
			longitudeDouble = Double.parseDouble(longitude);
		}
		List<Employee> dispatchers = employeeService.selectAllDispatcher();
		List<DispatcherLocation> locations = new ArrayList<DispatcherLocation>();
		for (Employee employee : dispatchers) {
			DispatcherLocation location = new DispatcherLocation();
			location.setLatitude(latitudeDouble);
			location.setLongitude(longitudeDouble);
			location.setDispatcherLatitude(employee.getLatitude());
			location.setDispatcherLongitude(employee.getLongitude());
			location.setName(employee.getName());
			location.setPhotoUrl(employee.getPhotoUrl());
			locations.add(location);
		}
		JSONArray array = JSONArray.fromObject(locations);
		model.addAttribute("locations", array);	
		return "/dispatcherMonitor/dispatcherMonitor";
	}
	/**
	 * 查询所有的调度员
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description = "查询所有的员工")
	@RequestMapping(value = "/query_dispatcher_list", method = { RequestMethod.GET })
	public String queryDispatcherList(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		List<EmployeeVo> employees = employeeService.queryDispatcherList();
		List<EmployeeVo> newEmployeeVos  = new ArrayList<EmployeeVo>();
		for (EmployeeVo employeeVo : employees) {
			if ("1".equals(employeeVo.getType())) {//调度员
				if("2".equals(employeeVo.getIdentifyLabel())){
					employeeVo.setType("城市管理员");
				}else if("1".equals(employeeVo.getIdentifyLabel())){
					employeeVo.setType("调度管理员");
				}else{
					employeeVo.setType("调度员");
				}
			}else if ("2".equals(employeeVo.getType())) {//救援员
				if("2".equals(employeeVo.getIdentifyLabel())){
					employeeVo.setType("城市管理员");
				}else if("1".equals(employeeVo.getIdentifyLabel())){
					employeeVo.setType("救援主管");
				}else{
					employeeVo.setType("救援员");
				}
			}else  {
				if("2".equals(employeeVo.getIdentifyLabel())){
					employeeVo.setType("城市管理员");
				}else if("1".equals(employeeVo.getIdentifyLabel())){
					employeeVo.setType("维保主管");
				}else{
					employeeVo.setType("维保员");
				}
			}
			if ("closed".equals(employeeVo.getStatus())) {
				employeeVo.setStatus("下班");
				employeeVo.setStatusImg("images/monitorCenter/staff_blue.png");
			}else {
				employeeVo.setStatus("上班");
				employeeVo.setStatusImg("images/monitorCenter/staff_green.png");
			}
			if (employeeVo.getPhotoUrl()==null) {
				employeeVo.setPhotoUrl("images/monitorCenter/map_default.png");
			}
			newEmployeeVos.add(employeeVo);
		}
		response.getWriter().print(JSONArray.fromObject(newEmployeeVos));
		return null;
	}
	
	/**
	 * 员工还车
	 */
	@SystemServiceLog(description = "员工还车")
	@RequestMapping(value = "/return_employee_car", method = { RequestMethod.GET })
	public String returnEmployeeCar(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String parkId = request.getParameter("parkId");
		String employeeId = request.getParameter("employeeId");
		String status = request.getParameter("status");
		String reason = request.getParameter("reason");
		//判断下线原因和下线状态
		if(StringUtils.isBlank(status) && StringUtils.isBlank(reason)){
			
			EmployeeOrder order = employeeService.queryOrderByEmployeeId(Integer.parseInt(employeeId));
		if (order == null) {//该员工没有用车订单，不能还车
			response.getWriter().print("returnFail");
			return null;
		}else{
			String endLocation = order.getEndLocation();
			Double latitude = order.getLatitude();
			Double longitude = order.getLongitude();
			String orderId = order.getOrderNo();
			Map<String, Object> methodParamMap = new HashMap<String, Object>();
			methodParamMap.put("endLocation", endLocation);
			methodParamMap.put("latitude", latitude);
			methodParamMap.put("longitude", longitude);
			methodParamMap.put("parkId", parkId);
			methodParamMap.put("orderId", orderId);
			methodParamMap.put("retrunCarByPortal", 1);
			String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
			Map<String, String> httpMap = new HashMap<String, String>();
			httpMap.put("cId", "platForm");
			httpMap.put("memberId", employeeId);
			httpMap.put("method", "employeeRequestReturnCar");
			httpMap.put("param", methodParams);
			httpMap.put("phone", null);
			httpMap.put("type", "platForm");
			httpMap.put("version", "1");
			String params = FastJsonUtils.toJson(httpMap);
			SysParam sysParam = sysParamService.selectByKey("http_url_e") ;
			String json = "" ;
			if(sysParam.getValue().indexOf("https") == 0){ //https接口
				json = HttpsClientUtil.get(sysParam.getValue(), params) ;
			}else{
				json = HttpUtils.commonSendUrl(sysParam.getValue(), params) ;
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			String code = jsonObject.getString("code") ;
			if(code.equals("00")){//成功
				response.getWriter().print("succ");
				//员工通过平台还车，向前端推送
				Employee employee = employeeService.selectByEmployeeId(Integer.parseInt(employeeId));
				PushCommonBean pushCommon = new PushCommonBean("server_push_employee_portal_return_car","1","您已经通过管理平台还车","") ;
				List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
				for (String employeeCid : cidList) {
					PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
				}
			}else{
				response.getWriter().print(jsonObject.getString("msg"));
			}
			return null;
		}
	}else{
			/** 员工强制还车 */
		
			EmployeeOrder order = employeeService.queryOrderByEmployeeId(Integer.parseInt(employeeId));
			if(order==null){
				response.getWriter().print("returnFail");
				return null;
			}else{
				
				if(reason.indexOf("/")!=-1 || reason.indexOf("\\")!=-1 || reason.indexOf("\"")!=-1 || reason.indexOf("'")!=-1){
					response.getWriter().print("noReason");
					return null;
				}else{
					reason = "强制还车:"+reason;
				}
				//获取登录体name
				HttpSession session = request.getSession();
				SysUser user = (SysUser)session.getAttribute("user");
				String name = user.getName();
				
				Map<String, Object> methodParamMap = new HashMap<String, Object>();
				//接口参数
				methodParamMap.put("orderId", order.getOrderNo());
				methodParamMap.put("parkId", parkId);
				methodParamMap.put("endLocation", "platForm");
				methodParamMap.put("status", status);//下线状态
				methodParamMap.put("reason", reason);//下线原因
				methodParamMap.put("name", name);
				String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
				Map<String, String> httpMap = new HashMap<String, String>();
				httpMap.put("cId", "platForm");
				httpMap.put("memberId", employeeId);
				httpMap.put("method", "employeeForceReturnCar");
				httpMap.put("param", methodParams);
				httpMap.put("phone", null);
				httpMap.put("type", "platForm");
				httpMap.put("version", "1");
				String params = FastJsonUtils.toJson(httpMap);
				SysParam sysParam = sysParamService.selectByKey("http_url_e") ;
				String json = "" ;
				if(sysParam.getValue().indexOf("https") == 0){ //https接口
					json = HttpsClientUtil.get(sysParam.getValue(), params) ;
				}else{
					//sysParam.getValue()
					json = HttpUtils.commonSendUrl(sysParam.getValue(), params) ;
				}
				JSONObject jsonObject = JSONObject.fromObject(json);
				String code = jsonObject.getString("code") ;
				if(code.equals("00")){//成功
					response.getWriter().print("succ");
					//员工通过平台还车，向前端推送
					Employee employee = employeeService.selectByEmployeeId(Integer.parseInt(employeeId));
					PushCommonBean pushCommon = new PushCommonBean("server_force_employee_portal_return_car","1","您已经通过管理平台强制还车","") ;
					List<String> cidList = PushClientEmployee.queryClientId(employee.getPhone());
					System.out.println(JSON.toJSON(pushCommon));
					for (String employeeCid : cidList) {
						PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
					}
				}else{
					response.getWriter().print(jsonObject.getString("msg"));
				}
			}
		}
		return null;
	}
		
}
