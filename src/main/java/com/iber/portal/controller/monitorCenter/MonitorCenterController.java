package com.iber.portal.controller.monitorCenter;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.*;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.car.CarMaxRunMapper;
import com.iber.portal.dao.car.CarPhotoMapper;
import com.iber.portal.dao.dayRent.DayLongRentOrderMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.*;
import com.iber.portal.model.dayRent.LongRentOrder;
import com.iber.portal.model.employee.EmployeeOrder;
import com.iber.portal.model.member.MemberTotalVo;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.mongo.HeatMapNosql;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.car.*;
import com.iber.portal.service.charging.ChargingPileService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.tbox.TboxService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.SendSMS;
import com.iber.portal.vo.car.*;
import com.iber.portal.vo.city.CityVo;
import com.iber.portal.vo.park.ParkTotalVo;
import com.iber.portal.vo.timeShare.TimeShareCarOrderVo;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.Map.Entry;

@Controller
public class MonitorCenterController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CarRunService carRunService;
	
	@Autowired
    private TimeShareOrderService timeShareOrderService ;
	
	@Autowired
    private SysParamService sysParamService ;
	
	@Autowired
    private MemberService memberService ;
	
	@Autowired
    private CarRunLogService carRunLogService ;
	
	@Autowired
    private ParkService parkService ;
	
	@Autowired
	private CityService cityService ;
	
	@Autowired
	private CarRepairService carRepairService ;
	
	@Autowired
	private CarNavigationService carNavigationService ;
	
	@Autowired
	private CarPhotoMapper carPhotoMapper ;
	
	@Autowired
    private ChargingPileService chargingPileService ;
	
	@Autowired
    private SystemMsgMapper systemMsgMapper ;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
	private CarMaxRunMapper carMaxRunMapper;
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private HeatMapNosql heatMapNosql;

	@Autowired
	private TboxService tboxService;

	@Autowired
	private CarService carService;

	@Autowired
	private DayLongRentOrderMapper dayLongRentOrderMapper;
	
	/**
	 * @describe 车辆监控
	 * 
	 */
	@SystemServiceLog(description="车辆监控")
	@RequestMapping(value = "/car_monitoring", method = { RequestMethod.GET })
	public String carMonitoring(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("当前订单页面跳转") ;
		return "monitorCenter/carMonitorCenter";		
	}


	/**
	 * @describe 车辆监控
	 *
	 */
	@SystemServiceLog(description="第三方车辆监控")
	@RequestMapping(value = "/third_party_monitoring", method = { RequestMethod.GET })
	public String thirdPartyMonitoring(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("当前订单页面跳转") ;
		return "monitorCenter/thirdPartyMonitorCenter";
	}

	
	/**
	 * @describe 获取车辆运行信息
	 */
	@SystemServiceLog(description="获取车辆运行信息")
	@RequestMapping(value = "/car_run_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String cuttentOrderList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null ;
		if(!user.getCityCode().equals("00")){
			cityCode = user.getCityCode() ;
		}
		List<CarRun> carRunList = carRunService.queryCarRunByCity(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(carRunList));
		return null;	
	} 
	
	/**
	 * @describe 获取车辆运行订单信息
	 */
	@SystemServiceLog(description="获取车辆运行订单信息")
	@RequestMapping(value = "/car_run_order_info", method = { RequestMethod.GET , RequestMethod.POST })
	public String cuttentOrderList(String lpn ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String,Object> map = new HashMap<String, Object>() ;
		lpn=java.net.URLDecoder.decode(lpn,"utf-8");
		map.put("lpn", lpn) ;
		List<TimeShareCarOrderVo> data = timeShareOrderService.queryOrderByLpn(map) ;
		if(data.size() > 0){
			TimeShareCarOrderVo timeShareOrder = data.get(0) ;
			response.getWriter().print(Data2Jsp.mapToJson(timeShareOrder));
		}else{
			response.getWriter().print("fail");
		}
		return null;	
	} 
	
	/**
	 * @describe 获取车辆运行订单信息
	 */
	@SystemServiceLog(description="获取所有车辆运行订单信息")
	@RequestMapping(value = "/allCarRunOrderInfo", method = { RequestMethod.GET , RequestMethod.POST })
	public String allCarRunOrderInfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		List<TimeShareCarOrderVo> lists = timeShareOrderService.queryAllCarRunOrderInfo();
		Map<String,String> map = new HashMap<String, String>() ;
		if(null!=lists&&0<lists.size()){
			for (TimeShareCarOrderVo info : lists) {
				String lpn = info.getLpn();
				if(!StringUtils.isBlank(lpn)){
						map.put(lpn, Data2Jsp.mapToJson(info));
					}
			    }
		}
	    StringBuffer sb = new StringBuffer("{");
		for(Entry<String, String> entry : map.entrySet()){
			sb.append("\""+entry.getKey()+"\""+":"+""+entry.getValue()+",");
		}
		int len = sb.toString().length();
		String str = "{";
		if(1<len){
			str = sb.toString().substring(0, len-1);
		}
		response.getWriter().print(str.concat("}"));
	    return null;
	}


	/**
	 * @describe 获取车辆维修信息
	 */
	@SystemServiceLog(description="获取车辆维修信息")
	@RequestMapping(value = "/car_repair_info", method = { RequestMethod.GET , RequestMethod.POST })
	public String car_repair_info(String lpn ,String status ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		CarRepair carRepait = carRepairService.queryCarRepairByLpnStatus(lpn,status)  ;
		if(carRepait != null){
			response.getWriter().print(Data2Jsp.mapToJson(carRepait));
		}else{
			response.getWriter().print("fail");
		}
		return null;	
	} 
	
	/**
	 * @describe 车辆预约
	 */
	@SystemServiceLog(description="车辆预约")
	@RequestMapping(value = "/order_car", method = { RequestMethod.GET , RequestMethod.POST })
	public String orderCar(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String lpn = request.getParameter("lpn") ;
		String phone = request.getParameter("phone") ;
		String isEnterpriseOrder = request.getParameter("isEnterpriseOrder") ;
		String isFreeCompensate = request.getParameter("isFreeCompensate");
		SysParam sysParam = sysParamService.selectByKey("http_url") ;
		CarRun carRun = carRunService.queryCarRun(lpn);
		/**1.通过身份证查询会员信息*/
		Member member = memberService.selectDetailByPhone(phone) ;
		
		Integer count = timeShareOrderService.queryCarOrderedStatusByLpn(lpn);
		if(null!=count && 0<count){
			response.getWriter().print("ordered");
		}else {	
		if(member != null){
			/**2.通过车牌获取车辆信息*/
			StringBuffer sb = new StringBuffer("{") ;
			sb.append("\"cId\":\"platForm\",\"memberId\":\""+member.getId()+"\",") ;
			sb.append("\"method\":\"memberGenerateOrder\",") ;
			sb.append("\"param\":\"{'isEnterpriseOrder':'"+isEnterpriseOrder+"','lpn':'"+lpn+"','parkId':'"+carRun.getParkId()+"','userCarType':'platForm','isFreeCompensate':'"+isFreeCompensate+"'}\",") ;
			sb.append("\"phone\":\""+phone+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
			sb.append("}") ;
			String json = "" ;
			if(sysParam.getValue().indexOf("https") == 0){ //https接口
				json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
			}else{
				json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
			}
			JSONObject jsonObject = JSONObject.fromObject(json) ;
			String code = jsonObject.getString("code") ;
			if(code.equals("00")){ // 预约成功，发短信提醒 + 消息提示
				
				SysParam cancelSysParam = sysParamService.selectByKey("cancel_order_minutes") ;
				
				String parkName = parkService.selectByPrimaryKey(carRun.getParkId()).getName() ;
				//推送消息
				SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(14);
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType("timeShare");
				systemMsgLog.setCreateTime(new Date());
				systemMsgLog.setMemberId( member.getId());
				systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
				systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{0}",parkName).replace("{1}",lpn ).replace("{2}",cancelSysParam.getValue() ));
				systemMsgLogMapper.insertSelective(systemMsgLog);
				PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);
				
				//短信
				SendSMS.sendSMS(phone,getRemortIP(request),2660,parkName+"|"+lpn+"|"+cancelSysParam.getValue());

				List<String> memberCidList = PushClient.queryClientId(member.getPhone());
				for (String phoneCid : memberCidList) {
					PushClient.push(phoneCid, net.sf.json.JSONObject.fromObject(systemPush));
				}
				
				response.getWriter().print("succ");
			}else{
				response.getWriter().print(jsonObject.getString("msg"));
			}
		}else{
			response.getWriter().print("noMember");
		}
	}
		return null;	
	} 
	
	/**
	 * @describe 还车
	 */
	@SystemServiceLog(description="还车")
	@RequestMapping(value = "/return_order_car", method = { RequestMethod.GET , RequestMethod.POST })
	public String returnOrderCar(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String lpn = request.getParameter("returnCarLpn") ;
		String parkId = request.getParameter("parkId") ;
		String status = request.getParameter("status");
		String reason = request.getParameter("reason");
		CarRun carRun = carRunService.queryCarRun(lpn.replace("•", ""));
		Member member = memberService.selectByPrimaryKey(carRun.getMemberId());
		//如果是日租订单，在日租还车界面还车
		if ("useCar".equals(carRun.getStatus()) || "return".equals(carRun.getStatus())){
			if (carRun.getOrderId().contains("DR")){
				response.getWriter().print("isLongRent");
				return null;
			}
		}
		/**将订单修改为还车状态*/
		List<TimeShareOrder> orderList = timeShareOrderService.queryOrderByOrderId(carRun.getOrderId()) ;


		//判断输入下线状态和下线原因
		if(StringUtils.isBlank(status) && StringUtils.isBlank(reason)){
			//查询该车辆是否是员工在使用
			EmployeeOrder employeeOrder = employeeService.queryOrderByLpn(lpn.replace("•", ""));
			if (employeeOrder != null) {
				response.getWriter().print("returnCarInEmployeeMonitorCenter");
				return null;
			}

		if(orderList.size() > 0){
			TimeShareOrder order = orderList.get(0) ;
			String execMethod="memberParkReturnCar";
			if(StringUtils.isNotEmpty(order.getIsEnterpriseUseCar().trim())&& order.getIsEnterpriseUseCar().trim().equalsIgnoreCase("true")){
				execMethod="enterpriseMemberFinishOrderAndReturnCar";
			}
			order.setStatus("return") ;
			order.setReturnParkId(Integer.valueOf(parkId)) ;
			timeShareOrderService.saveOrUpdateOrder(order) ;
			
			/** 更新会员信息 */
			member.setStatus(SysConstant.MEMBER_STATUS_RETURN);
			memberService.updateByPrimaryKey(member);

			/** 更新车辆运行表状态 */
			carRun.setStatus(SysConstant.CAR_RUN_STATUS_RETURN);
			carRunService.updateByPrimaryKey(carRun);
			

			StringBuffer sb = new StringBuffer("{") ;
			sb.append("\"cId\":\"platForm\",\"memberId\":\""+member.getId()+"\",\"method\":\""+execMethod+"\",") ;
			sb.append("\"param\":\"{'lpn':'"+lpn+"','orderNo':'"+carRun.getOrderId()+"','longitude':'"+carRun.getLongitude()+"','latitude':'"+carRun.getLatitude()+"','returnCarByPortal':'"+'1'+"','orderMileage':'0','endLocation':'platForm','parkId':'"+parkId+"'}\",") ;
			sb.append("\"phone\":\""+member.getPhone()+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
			sb.append("}") ; //2023
			SysParam sysParam = sysParamService.selectByKey("http_url") ;
			String json = "" ;
			if(sysParam.getValue().indexOf("https") == 0){ //https接口
				json = HttpsClientUtil.get(sysParam.getValue(), sb.toString());
			}else{
				json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
			}
			JSONObject jsonObject = JSONObject.fromObject(json) ;
			String code = jsonObject.getString("code") ;
			if(code.equals("00")){
				/** 给会员推送取消订单信息 */
				SystemMsgLog systemMsgLog = new SystemMsgLog();
				systemMsgLog.setMsgType("timeShareReturnCar");
				systemMsgLog.setCreateTime(new Date());
				systemMsgLog.setMemberId(member.getId());
				systemMsgLog.setMsgTitle("时租还车");
				systemMsgLog.setMsgContent("【宜步出行】尊敬的会员，您的订单"+carRun.getOrderId()+"于"+DateTime.getString() +",通过运营平台进行还车!");
				systemMsgLogMapper.insertSelective(systemMsgLog);
				
				PushCommonBean pushmemberCommonBean = new PushCommonBean(
						SysConstant.SERVER_PUSH_TIME_SHARE_RETURN_CAR, "1","" ,carRun.getOrderId());
				List<String> cidMemberList = PushClient.queryClientId(member.getPhone());
				for (String memberCid : cidMemberList) {
					PushClient.push(memberCid,
							JSONObject.fromObject(pushmemberCommonBean));
				}
				response.getWriter().print("succ");
			}else{
				response.getWriter().print(jsonObject.getString("msg"));
			}
		}else {
			response.getWriter().print("当前用户暂未预约该车或该车辆已还,请刷新");
		}
	}else{
			/** 会员强制还车 */
			
			if(reason.indexOf("/")!=-1 || reason.indexOf("\\")!=-1 || reason.indexOf("\"")!=-1 || reason.indexOf("'")!=-1){
				response.getWriter().print("noReason");
				return null;
			}else{
				reason = "强制还车:"+reason;
			}
			EmployeeOrder employeeOrder = employeeService.queryOrderByLpn(lpn.replace("•", ""));
			if (employeeOrder != null) {
				response.getWriter().print("returnCarInEmployeeMonitorCenter");
				return null;
			}

			if(orderList.size()>0){
				TimeShareOrder order = orderList.get(0);
				order.setStatus("return");
				order.setReturnParkId(Integer.valueOf(parkId)) ;
				timeShareOrderService.saveOrUpdateOrder(order) ;
				
				/** 更新会员信息 */
				member.setStatus(SysConstant.MEMBER_STATUS_RETURN);
				memberService.updateByPrimaryKey(member);
				
				//获取登陆体name
				HttpSession session = request.getSession();
				SysUser user = (SysUser)session.getAttribute("user");
				String name = user.getName();
				
				StringBuffer sb = new StringBuffer("{") ;
				sb.append("\"cId\":\"platForm\",\"memberId\":\""+member.getId()+"\",\"method\":\"forceReturnCar\",") ;
				sb.append("\"param\":\"{'endLocation':'platForm','longitude':'"+carRun.getLongitude()+"','latitude':'"+carRun.getLatitude()+"','orderMileage':'0','orderId':'"+carRun.getOrderId()+"','parkId':'"+parkId+"','status':'"+status+"','reason':'"+reason+"','name':'"+name+"'}\",");
				sb.append("\"phone\":\""+member.getPhone()+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
				sb.append("}") ;
				SysParam sysParam = sysParamService.selectByKey("http_url") ;
				String json = "" ;
				if(sysParam.getValue().indexOf("https") == 0){ //https接口
					//sysParam.getValue()
					//"http://192.168.1.84:8888/services/i/e/"
					json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
				}else{
					json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
				}
				JSONObject jsonObject = JSONObject.fromObject(json) ;
				String code = jsonObject.getString("code") ;
				if(code.equals("00")){
					/** 给会员推送取消订单信息 */
					SystemMsgLog systemMsgLog = new SystemMsgLog();
					systemMsgLog.setMsgType("timeShareReturnCar");
					systemMsgLog.setCreateTime(new Date());
					systemMsgLog.setMemberId(member.getId());
					systemMsgLog.setMsgTitle("时租还车");
					systemMsgLog.setMsgContent("【宜步出行】尊敬的会员，您的订单"+carRun.getOrderId()+"于"+DateTime.getString() +",通过运营平台进行强制还车!");
					systemMsgLogMapper.insertSelective(systemMsgLog);
					
					PushCommonBean pushmemberCommonBean = new PushCommonBean(
							SysConstant.SERVER_PUSH_TIME_SHARE_RETURN_CAR, "1","" ,carRun.getOrderId());//事件码，事件完成结果，返回信息，result
					List<String> cidMemberList = PushClient.queryClientId(member.getPhone());
					for (String memberCid : cidMemberList) {
						PushClient.push(memberCid,
								JSONObject.fromObject(pushmemberCommonBean));
					}
					response.getWriter().print("succ");
				}else{
					response.getWriter().print(jsonObject.getString("msg"));
				}
			}else{
				response.getWriter().print("当前用户暂未预约该车或该车辆已强制还,请刷新");
			}
		}
		return null;	
	}

	@SystemServiceLog(description = "日租还车")
	@RequestMapping(value = "/return_long_rent_car", method = { RequestMethod.GET , RequestMethod.POST })
	public String longRentReturnCar(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String lpn = request.getParameter("longRentReturnCarLpn");
		String parkId = request.getParameter("longRentParkId");
		String reason =  request.getParameter("offLineReason");
		String status = request.getParameter("carStatus");
		CarRun carRun = carRunService.queryCarRun(lpn.replace("•", ""));
		if (!"useCar".equals(carRun.getStatus())){
			response.getWriter().print("notUsing");
			return null;
		}
		Member member = memberService.selectByPrimaryKey(carRun.getMemberId());
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\""+member.getId()+"\",\"method\":\"longRentReturnCar\",") ;
		if (StringUtils.isNotBlank(reason) && StringUtils.isNotBlank(status)){
			String name = getUser(request).getName();
			sb.append("\"param\":\"{'lpn':'"+lpn.replace("•", "")+"','orderId':'"+carRun.getOrderId()+"','longitude':'"+carRun.getLongitude()+"','latitude':'"+carRun.getLatitude()+"','isDetection':'"+'1'+"','orderMileage':'0','endLocation':'platForm','returnParkId':'"+parkId+"','reason':'"+reason+"','status':'"+status+"','name':'"+name+"'}\",") ;
		}else {
			sb.append("\"param\":\"{'lpn':'"+lpn.replace("•", "")+"','orderId':'"+carRun.getOrderId()+"','longitude':'"+carRun.getLongitude()+"','latitude':'"+carRun.getLatitude()+"','isDetection':'"+'1'+"','orderMileage':'0','endLocation':'platForm','returnParkId':'"+parkId+"'}\",") ;

		}
		sb.append("\"phone\":\""+member.getPhone()+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		SysParam sysParam = sysParamService.selectByKey("http_url_d") ;
		String json = "" ;
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json) ;
		String code = jsonObject.getString("code") ;
		if ("00".equals(code)){
			SystemMsgLog systemMsgLog = new SystemMsgLog();
			systemMsgLog.setMsgType("longRentReturnCar");
			systemMsgLog.setCreateTime(new Date());
			systemMsgLog.setMemberId(member.getId());
			systemMsgLog.setMsgTitle("日租还车");
			systemMsgLog.setMsgContent("【宜步出行】尊敬的会员，您的订单"+carRun.getOrderId()+"于"+DateTime.getString() +",通过运营平台进行还车!");
			systemMsgLogMapper.insertSelective(systemMsgLog);

			PushCommonBean pushmemberCommonBean = new PushCommonBean(
					SysConstant.SERVER_PUSH_LONT_RENT_RETURN_CAR, "1","" ,carRun.getOrderId());//事件码，事件完成结果，返回信息，result
			List<String> cidMemberList = PushClient.queryClientId(member.getPhone());
			for (String memberCid : cidMemberList) {
				PushClient.push(memberCid,
						JSONObject.fromObject(pushmemberCommonBean));
			}
			response.getWriter().print("succ");
		}else{
			response.getWriter().print(jsonObject.getString("msg"));
		}
		return null;
	}
	
	/**
	 * @describe 打开车门
	 */
	@SystemServiceLog(description="打开车门")
	@RequestMapping(value = "/open_car_door", method = { RequestMethod.GET , RequestMethod.POST })
	public String openCarDoor(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		CarRun carRun = carRunService.queryCarRun(lpn) ;
		if(carRun.getBatStatus().equals("0")){ //充电状态 0 不在充电 1在充电
			if(("useCar").equals(carRun.getStatus())||"ordered".equals(carRun.getStatus())){
				Member member = memberService.selectByPrimaryKey(carRun.getMemberId()) ;
				String orderId = carRun.getOrderId() ;
				//根据车辆查询订单号
				Map<String,Object> map = new HashMap<String, Object>() ;
				map.put("lpn", lpn) ;
				List<TimeShareCarOrderVo> data = timeShareOrderService.queryTimeShareOrderByLpn(map) ;
				if(data.size() > 0 && orderId.contains("TS")) {
					orderId = data.get(0).getOrderId() ;
				}else {
					if(!("useCar").equals(carRun.getStatus())){
						response.getWriter().print("notUse");
						return null;
					}
					LongRentOrder longRentOrder =  dayLongRentOrderMapper.selectUsingOrderByOrderId(orderId);
					orderId = longRentOrder.getOrderId();
				}
				if(null!=member){
				StringBuffer sb = new StringBuffer("{") ;
				sb.append("\"cId\":\"platForm\",\"memberId\":\""+member.getId()+"\",\"method\":\"memberGetCarSuccess\",") ;
				sb.append("\"param\":\"{'lpn':'"+lpn+"','orderNo':'"+orderId+"'}\",") ;
				sb.append("\"phone\":\""+member.getPhone()+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
				sb.append("}") ;
				SysParam sysParam = sysParamService.selectByKey("http_url") ;
				String json = "" ;
				if(sysParam.getValue().indexOf("https") == 0){ //https接口
					json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
					List<String> cidList = PushClient.queryClientId(member.getPhone());
					PushCommonBean pushCommonBean = new PushCommonBean(
							SysConstant.SERVER_PUSH_OPEN_DOOR, "1","" ,null);//事件码，事件完成结果，返回信息，result
					for (String cid : cidList) {
						PushClient.push(cid, JSON.toJSON(pushCommonBean));
					}
				}else{
					json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
				}
				JSONObject jsonObject = JSONObject.fromObject(json) ;
				}
			}
			
			/** 推送打开车门指令给车载 */
			// 查询车辆tbox版本
			final Integer version = tboxService.tboxVersion(lpn);
			if (version == null || version == 0){
				response.getWriter().print("fail");
				return null;
			}

			if (version == 2){
				List<String> cidList = PushClient.queryClientId(lpn);
				for (String lpnCid : cidList) {
					String isLine = PushClient.getUserStatus(lpnCid) ;
					if(isLine.equals("Online")){
						PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_OPEN_DOOR,"1","","") ;
						String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
						if(result.equals("ok")){
							response.getWriter().print("succ");
						}else{
							response.getWriter().print("");
						}
					}
				}
			}else if (version == 3){
				final Map<String, Object> map = tboxService.openCarDoor(lpn);
				if (CommonUtil.isSuccess(map)){
					response.getWriter().print("succ");
				}else {
					response.getWriter().print("fail");
				}
			}else {
				response.getWriter().print("fail");
			}

		}else{
			response.getWriter().print("请先拔掉充电枪");
		}
		return null;	
	} 
	
	/**
	 * @describe 关闭车门
	 */
	@SystemServiceLog(description="关闭车门")
	@RequestMapping(value = "/close_car_door", method = { RequestMethod.GET , RequestMethod.POST })
	public String closeCarDoor(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8"); 
		// 查询车辆tbox版本
		final Integer version = tboxService.tboxVersion(lpn);
		if (version == null || version == 0){
			response.getWriter().print("fail");
			return null;
		}

		if (version == 2){
			List<String> cidList = PushClient.queryClientId(lpn);
			for (String lpnCid : cidList) {
				String isLine = PushClient.getUserStatus(lpnCid) ;
				if(isLine.equals("Online")){
					PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_CLOSE_DOOR,"1","","") ;
					String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
					if(result.equals("ok")){
						response.getWriter().print("succ");
					}else{
						response.getWriter().print("fail");
					}
				}else{
					response.getWriter().print("fail");
				}

			}
		}else if (version == 3){
			final Map<String, Object> map = tboxService.closeCarDoor(lpn);
			if (CommonUtil.isSuccess(map)){
				response.getWriter().print("succ");
			}else {
				response.getWriter().print("fail");
			}
		}else {
			response.getWriter().print("fail");
		}

		return null;	
	}
	
	/**
	 * @describe 启动车辆
	 */
	@SystemServiceLog(description="启动车辆")
	@RequestMapping(value = "/start_car_engine", method = { RequestMethod.GET , RequestMethod.POST })
	public String startCarEngine(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");

		// 查询车辆tbox版本
		final Integer version = tboxService.tboxVersion(lpn);
		if (version == null || version == 0){
			response.getWriter().print("fail");
			return null;
		}

		if (version == 2){
			List<String> cidList = PushClient.queryClientId(lpn);
			for (String lpnCid : cidList) {
				String isLine = PushClient.getUserStatus(lpnCid) ;
				if(isLine.equals("Online")){
					PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_START_CAR_ENGINE,"1","","") ;
					String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
					if(result.equals("ok")){
						response.getWriter().print("succ");
					}else{
						response.getWriter().print("fail");
					}
				}else{
					response.getWriter().print("fail");
				}

			}
		}else if (version == 3){
			final Map<String, Object> map = tboxService.startCarEngine(lpn);
			if (CommonUtil.isSuccess(map)){
				response.getWriter().print("succ");
			}else {
				response.getWriter().print("fail");
			}
		}else {
			response.getWriter().print("fail");
		}
		return null;
	}
	
	/**
	 * @describe 熄火
	 */
	@SystemServiceLog(description="熄火")
	@RequestMapping(value = "/car_fire_off", method = { RequestMethod.GET , RequestMethod.POST })
	public String carFireOff(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// 查询车辆tbox版本
		final Integer version = tboxService.tboxVersion(lpn);
		if (version == null || version == 0){
			response.getWriter().print("fail");
			return null;
		}

		if (version == 2){
			List<String> cidList = PushClient.queryClientId(lpn);
			for (String lpnCid : cidList) {
				String isLine = PushClient.getUserStatus(lpnCid) ;
				if(isLine.equals("Online")){
					PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_CAR_FIRE_OFF,"1","","") ;
					String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
					if(result.equals("ok")){
						response.getWriter().print("succ");
					}else{
						response.getWriter().print("fail");
					}
				}else{
					response.getWriter().print("fail");
				}

			}
		}else if (version == 3){
			final Map<String, Object> map = tboxService.carFireOff(lpn);
			if (CommonUtil.isSuccess(map)){
				response.getWriter().print("succ");
			}else {
				response.getWriter().print("fail");
			}
		}else {
			response.getWriter().print("fail");
		}

		return null;
	}
	
	/**
	 * @describe 开启导航
	 */
	@SystemServiceLog(description="开启导航")
	@RequestMapping(value = "/open_navigate", method = { RequestMethod.GET , RequestMethod.POST })
	public String openNavigate(String lpn , String position ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8"); 
		List<String> cidList = PushClient.queryClientId(lpn);
		CarPosition carPosition = new CarPosition(position) ;
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		CarRun carRun = carRunService.queryCarRun(lpn) ;
		//保存导航信息
		CarNavigation carNavigation = new CarNavigation() ;
		carNavigation.setCreateId(user.getId()) ;
		carNavigation.setCreateName(user.getName()) ;
		carNavigation.setBeginGps(carRun.getLongitude()+"|"+carRun.getLatitude()) ;
		carNavigation.setEndGps(position) ;
		carNavigation.setCreateTime(new Date()) ;
		carNavigation.setLpn(lpn) ;
		carNavigation.setStatus("0") ; //正在导航 0 取消导航
		if(carRun.getOrderId()!=null && !carRun.getOrderId().equals("")){
			Member member = memberService.selectByPrimaryKey(carRun.getMemberId()) ;
			carNavigation.setMemberName(member.getName()) ;
			carNavigation.setOrderId(carRun.getOrderId()) ;
		}
		carNavigation.setAddress(getAddress(sysParamService, position.split("\\|")[1], position.split("\\|")[0])) ;
		carNavigationService.insertSelective(carNavigation) ;
		for (String lpnCid : cidList) {
			String isLine = PushClient.getUserStatus(lpnCid) ;
			if(isLine.equals("Online")){
				PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_CAR_OPEN_NAVIGATE,"1","",carPosition) ;
				String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
				if(result.equals("ok")){
					response.getWriter().print("succ");
				}else{
					response.getWriter().print("fail");
				}
			}else{
				response.getWriter().print("fail");
			}
		}
		return null;	
	}
	
	public static String getAddress(SysParamService sysParamService,String lat, String lng){
		SysParam sysParam = sysParamService.selectByKey("map.geocode.url") ;
		String url = sysParam.getValue() ;
		String tmp = lng + "," + lat;
		url = url.replaceAll("LAT_LNG", tmp);
		String body = HttpUtils.commonSendUrl(url, "") ;
		com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(body);
		if(jsonObject.getString("info").equalsIgnoreCase("ok")){
			String regeocodes = jsonObject.getString("regeocode");
			com.alibaba.fastjson.JSONObject regeocodesjsonObject = com.alibaba.fastjson.JSONObject.parseObject(regeocodes);
			return regeocodesjsonObject.getString("formatted_address");
		}
		return "";
	}
	
	/**
	 * @describe 关闭导航
	 */
	@SystemServiceLog(description="关闭导航")
	@RequestMapping(value = "/cancel_navigation", method = { RequestMethod.GET , RequestMethod.POST })
	public String cancelNavigation(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8"); 
		List<String> cidList = PushClient.queryClientId(lpn);
		CarRun carRun = carRunService.queryCarRun(lpn) ;
		if(carRun.getOrderId()!=null && !carRun.getOrderId().equals("")){
			carNavigationService.updateStatusByPrimaryKey(carRun.getOrderId()) ;
		}
		
		for (String lpnCid : cidList) {
			String isLine = PushClient.getUserStatus(lpnCid) ;
			if(isLine.equals("Online")){
				PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_CAR_CANCEL_NAVIGATION,"1","","") ;
				String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
				if(result.equals("ok")){
					response.getWriter().print("succ");
				}else{
					response.getWriter().print("fail");
				}
			}else{
				response.getWriter().print("fail");
			}
		}
		return null;	
	}
	
	/**
	 * @describe 呼叫车主
	 */
	@SystemServiceLog(description="呼叫车主")
	@RequestMapping(value = "/call_car", method = { RequestMethod.GET , RequestMethod.POST })
	public String callCar(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<String> cidList = PushClient.queryClientId(lpn);
		for (String lpnCid : cidList) {
			PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_CALL_CAR,"1","","") ;
			String isLine = PushClient.getUserStatus(lpnCid) ;
			if(isLine.equals("Online")){
				String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
				if(result.equals("ok")){
					response.getWriter().print("succ");
				}else{
					response.getWriter().print("fail");
				}
			}else{
				response.getWriter().print("fail");
			}
		}
		return null;	
	}
	
	/**
	 * @describe 网点监控
	 * 
	 */
	@SystemServiceLog(description="网点监控")
	@RequestMapping(value = "/park_monitoring", method = { RequestMethod.GET })
	public String parkMonitoring(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("当前订单页面跳转") ;
		return "monitorCenter/parkMonitorCenter";		
	}
	
	/**
	 * @describe 网点监控
	 * 
	 */
	@SystemServiceLog(description="网点监控")
	@RequestMapping(value = "/query_carRun_park_id", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryCarRunParkId(Integer parkId ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		log.info("当前订单页面跳转");
		if(null==parkId){
			StringBuffer sb = new StringBuffer();
			Map<String, List<JSONObject>> map = new HashMap<String, List<JSONObject>>();
			List<CarRun> allCarRunInfo = carRunService.queryCarRunByparkId(null);
			for (CarRun carRun : allCarRunInfo) {
				Integer pId = carRun.getParkId();
				List<JSONObject> list = null;
				if(null!=pId){
					String pIdStr = pId.toString();
					list = map.get("parkId"+pIdStr);
					if(null==list){
						list = new ArrayList<JSONObject>();
						map.put("parkId"+pIdStr, list);
					}
					map.get("parkId"+pIdStr).add(JSONObject.fromObject(carRun));
				}
			}
			sb.append("{");
			for(Entry<String, List<JSONObject>> entry : map.entrySet()){
				sb.append("\""+entry.getKey()+"\""+":"+""+Data2Jsp.listToJson(entry.getValue())+",");
			}
			int len = sb.toString().length();
			String str = sb.toString().substring(0, len-1);
			response.getWriter().print(str.concat("}"));
		}else {
			List<CarRun> carRunList = carRunService.queryCarRunByparkId(parkId) ;
			response.getWriter().print(Data2Jsp.listToJson(carRunList));
		}
		return null;		
	}
	
	/**
	 * @describe 获取车辆运行信息
	 * 
	 */
	@SystemServiceLog(description="获取车辆运行信息")
	@RequestMapping(value = "/query_car_run_log_lpn", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryCarRunLogByLpn(String lpn ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		log.info("当前订单页面跳转") ;
//		CarRun carRun = carRunService.queryCarRun(lpn)
		request.setCharacterEncoding("UTF-8");
		CarRunLog carRunLog = carRunLogService.queryCarRunLogByLpn(lpn) ;
		if(carRunLog == null){
			response.getWriter().print("fail");
		}else{
			response.getWriter().print(Data2Jsp.mapToJson(carRunLog));
		}
		
		return null;		
	}
	/**
	 * 获取车辆运行轨迹
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 
	@SystemServiceLog(description="获取车辆运行轨迹")
	@RequestMapping(value = "/car_locus", method = { RequestMethod.GET , RequestMethod.POST})
	public String carLocus(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		log.info("当前订单页面跳转") ;
		String lpn = request.getParameter("lpn") ;
		request.setAttribute("lpn", lpn) ;
		return "monitorCenter/carLocus";		
	}
	/**
	 * @describe 获取车辆运行轨迹
	 * 
	 */
	@SystemServiceLog(description="获取车辆运行轨迹")
	@RequestMapping(value = "/query_car_run_log_locus", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryCarRunLogLocus(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		log.info("当前订单页面跳转") ;
		String lpn = request.getParameter("lpn") ;
		String queryDateFrom = request.getParameter("queryDateFrom") ;
		String queryDateTo = request.getParameter("queryDateTo") ;
		String orderId = request.getParameter("orderId") ;
		List<CarRunLog> carRunLog = carRunLogService.queryCarRunLogByLpnAndOrderId(lpn, queryDateFrom, queryDateTo, orderId) ;
		if(carRunLog == null){
			response.getWriter().print("empty");
		}else{
			response.getWriter().print(Data2Jsp.listToJson(carRunLog));
		}
		return null;		
	}
	
	/**
	 * 统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="统计")
	@RequestMapping(value = "/statistics_map", method = { RequestMethod.GET , RequestMethod.POST})
	public String statisticsMap(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		log.info("当前订单页面跳转") ;
		return "echarts/core/statistics_map";		
	}
	
	/**
	 * 统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车/桩场统计分析地图展示")
	@RequestMapping(value = "/mix5", method = { RequestMethod.GET , RequestMethod.POST})
	public String mix(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		log.info("当前订单页面跳转") ;
		return "echarts/core/mix";		
	}
	
	/**
	 * 根据统计类型获取饼型option
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="根据统计类型获取饼型option")
	@RequestMapping(value = "/car_list_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String car_list_statistics(String cityCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		
		List<CarStatusVo> carStatusList = null ; 
		if(cityCode.length() > 4){
			if(cityCode.substring(4).equals("00")){ //市级
				carStatusList = carRunService.queryCarRunStatusByCityCode(cityCode) ;
			}else{ //区级
				carStatusList = carRunService.queryCarRunStatusByAreaCode(cityCode) ;
			}
		}else{
			carStatusList = carRunService.queryCarRunStatusByCityCode(cityCode) ;
		}
		
		int emptyTotal = 0 ;  //空闲
		int repairTotal = 0 ; //维修
		int operateTotal = 0 ;//运营
		for(CarStatusVo vo : carStatusList){
			if(vo.getStatus().equals("empty")){
				emptyTotal = vo.getTotal() ; 
			}
			if(vo.getStatus().equals("repair")){
				repairTotal = vo.getTotal() ;
			}
			if(vo.getStatus().equals("maintain")){
				repairTotal = repairTotal + vo.getTotal() ;
			}
			if(vo.getStatus().equals("useCar")){
				operateTotal = operateTotal + vo.getTotal() ; 
			}
			if(vo.getStatus().equals("ordered")){
				operateTotal = operateTotal+ vo.getTotal() ;
			}
			if(vo.getStatus().equals("return")){
				operateTotal = operateTotal+ vo.getTotal() ;
			}
		}
		String cityName = "全国" ;
		if(!cityCode.equals("00")){
			cityName = cityService.queryCityByCode(cityCode).get(0).getName() ;
		}
		int total = emptyTotal + repairTotal + operateTotal ; //总数
		CarTotalVo carVo = new CarTotalVo(cityName,operateTotal ,repairTotal , emptyTotal , total) ;
		response.getWriter().print(Data2Jsp.mapToJson(carVo));
		return null;		
	}
	
	/**
	 * 获取城市区域 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="获取城市区域")
	@RequestMapping(value = "/park_list_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String park_list_statistics(String cityCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		Map<String,Object> map = new HashMap<String, Object>() ;
		String cityName = "全国" ;
		if(cityCode.equals("00")){
			cityCode = null ;
		}else{
			cityName = cityService.queryCityByCode(cityCode).get(0).getName() ;
		}
		map.put("cityCode",cityCode);
		
		List<Park> parkList = null ;
		if(cityCode != null && cityCode.length() > 4){
			if(cityCode.substring(4).equals("00")){ //市级
				parkList = parkService.queryParkByCode(map);
			}else{ //区级
				parkList = parkService.queryParkStatusByAreaCode(cityCode) ;
			}
		}else{
			parkList = parkService.queryParkByCode(map);
		}
		
		Integer parkEnabledTotal = 0 ; //可用
		Integer parkUsedTotal = 0 ;//已用
		Integer parkCarportTotal = 0 ;//全部车位
		for(Park park : parkList){
			parkCarportTotal = parkCarportTotal+park.getParkNums() ;
			List<CarRun> carRunList = carRunService.queryCarRunStatusByPackId(park.getId(), "empty") ;
			parkUsedTotal = parkUsedTotal + carRunList.size() ;
		}
		parkEnabledTotal = parkCarportTotal - parkUsedTotal ;
		ParkTotalVo parkVo = new ParkTotalVo(cityName,parkList.size(),parkEnabledTotal,parkUsedTotal,parkCarportTotal) ;
		response.getWriter().print(Data2Jsp.mapToJson(parkVo));
		return null;		
	}
	
	/**
	 * 地图车辆统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="地图车辆统计")
	@RequestMapping(value = "/map_car_list_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String map_car_list_statistics(String cityCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		//通过城市ID查询城市车辆
		List<CityVo> data = cityService.queryCityCarNum() ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**
	 * 地图网点统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="地图网点统计")
	@RequestMapping(value = "/map_park_list_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String map_park_list_statistics(String cityCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		//通过城市ID查询城市网点
		List<CityVo> data = cityService.queryCityParkNum() ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}

	/**
	 * @describe 拍照
	 */
	@SystemServiceLog(description="拍照")
	@RequestMapping(value = "/catched_photo", method = { RequestMethod.GET , RequestMethod.POST })
	public String catched_photo(String lpn , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		CarPhoto carPhoto = new CarPhoto() ;
		carPhoto.setCreateId(user.getId()) ;
		carPhoto.setCreateName(user.getName()) ;
		carPhoto.setCreateTime(new Date()) ;
		carPhoto.setLpn(lpn) ;
		CarRun carRun = carRunService.queryCarRun(lpn) ;
		carPhoto.setCityCode(carRun.getCityCode()) ;
		carPhotoMapper.insertSelective(carPhoto) ;
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("id", carPhoto.getId()) ;
		List<String> cidList = PushClient.queryClientId(lpn);
		for (String lpnCid : cidList) {
			PushCommonBean push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_CATCH_PHOTO,"1","",map) ;
			String result = PushClient.push(lpnCid,JSONObject.fromObject(push));
			if(result.equals("ok")){
				response.getWriter().print("succ");
			}else{
				response.getWriter().print("fail");
			}
		}
		return null;	
	}
	
	/**
	 * @describe 车辆导航地址
	 */
	@SystemServiceLog(description="车辆导航地址")
	@RequestMapping(value = "/car_navigate_info", method = { RequestMethod.GET , RequestMethod.POST })
	public String car_navigate_info(String orderId , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<CarNavigation> datas =carNavigationService.selectAddressByOrderId(orderId);
		Map<String,String> map = new HashMap<String, String>() ;
		for (CarNavigation info : datas) {
			String order = info.getOrderId();
			if(!StringUtils.isBlank(order)){
					map.put(order, Data2Jsp.mapToJson(info));
				}
			}
	    StringBuffer sb = new StringBuffer("{");
		for(Entry<String, String> entry : map.entrySet()){
			sb.append("\""+entry.getKey()+"\""+":"+""+entry.getValue()+",");
		}
		int len = sb.toString().length();
		String str = "";
		if(1!=len){
			str = sb.toString().substring(0, len-1);
		}else{
			str = str+"{";
		}
	//	response.getWriter().print(str.concat("}"));
//		List<CarNavigation> data = carNavigationService.selectAddressByOrderId(orderId) ;
		if(datas.size() > 0) {
			response.getWriter().print(str.concat("}"));
		}else{
			response.getWriter().print("fail");
		}
		return null ;
	}
	
	/**
	 * 地图区域统计
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="地图区域统计")
	@RequestMapping(value = "/area_map_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String area_map_statistics(String cityCode ,String typeCode ,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<CityVo> data = cityService.queryCityAreaByCode(cityCode) ;
		List<CityVo> cityList = new ArrayList<CityVo>() ;
		if(typeCode.equals("car")){
			for(CityVo vo : data){
				vo.setNum(carRunService.queryParkCarByAreaCode(vo.getCode())) ;
				cityList.add(vo) ;
			}
		}else if(typeCode.equals("park")){
			//通过区域获取网点数量
			for(CityVo vo : data){
				vo.setNum(parkService.queryParkByAreaCode(vo.getCode())) ;
				cityList.add(vo) ;
			}
		}else if(typeCode.equals("pile")) {
			for(CityVo vo : data){
				vo.setNum(chargingPileService.queryParkPileByAreaCode(vo.getCode())) ;
				cityList.add(vo) ;
			}
		}
		response.getWriter().print(Data2Jsp.listToJson(cityList));
		return null ;
	}
	
	/**
	 * @describe 获取车辆运行信息
	 */
	@SystemServiceLog(description="获取车辆运行信息")
	@RequestMapping(value = "/car_run_info", method = { RequestMethod.GET , RequestMethod.POST })
	public String car_run_info(String lpn,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		CarRun carRun = carRunService.queryCarRunByLpn(lpn) ;
		Car car = carService.selectByLpn(lpn);
		
		if(carRun != null){
			carRun.setBrandName(car.getBrandName());
			response.getWriter().print(Data2Jsp.mapToJson(carRun));
		}else{
			response.getWriter().print("fail");
		}
		return null;	
	}
	
	/**运营统计*/
	@RequestMapping(value = "/run_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String runStatistics(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		log.info("当前运营统计跳转") ;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = user.getCityCode();
		String cityName = cityService.queryNameByCode(cityCode);
	    HttpSession session = request.getSession();  
	    session.setAttribute("cityCode",cityCode);  
	    session.setAttribute("cityName",cityName);
		return "echarts/core/run_statistics";		
	}
	/**根据省份名称查询code*/
	@RequestMapping(value = "/queryCodeByName", method = { RequestMethod.GET , RequestMethod.POST})
	public String queryCodeByName(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String name =request.getParameter("name");
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("name", name);
		map.put("level", level);
		List<CarRunNumVo> data = carRunService.queryCodeByName(map) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	/**车辆总数饼状图(车辆状态)*/
	@RequestMapping(value = "/carStatus_list_statistics_sum", method = { RequestMethod.GET , RequestMethod.POST})
	public String carStatusListStatisticsSum(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode;
		/*SysUser user = (SysUser) request.getSession().getAttribute("user");*/
		cityCode =new String(request.getParameter("cityCode").getBytes("iso-8859-1"),"utf-8");
		 if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<CarRunNumVo> data = carRunService.queryCarStatusListStatisticsSum(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**车辆总数饼状图(车辆状态-按省级统计)*/
	@RequestMapping(value = "/carStatus_province_list_statistics_sum", method = { RequestMethod.GET , RequestMethod.POST})
	public String provinceCarStatusListStatisticsSum(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode;
//		cityCode =new String(request.getParameter("cityCode").getBytes("iso-8859-1"),"utf-8");
		cityCode = request.getParameter("cityCode");
		 if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<CarRunNumVo> data = carRunService.provinceCarStatusListStatisticsSum(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	/**车辆总数饼状图(车辆类型按照全国、地级市划分)*/
	@RequestMapping(value = "/carType_list_statistics_sum", method = { RequestMethod.GET , RequestMethod.POST})
	public String carTypeListStatisticsSum(HttpServletRequest request, HttpServletResponse response)
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
		List<CarRunNumVo> data = carRunService.queryCarTypeListStatisticsSum(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**车辆总数饼状图(车辆类型--按照省份划分)*/
	@RequestMapping(value = "/provinceCarType_list_statistics_sum", method = { RequestMethod.GET , RequestMethod.POST})
	public String provinceCarTypeListStatisticsSum(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode =request.getParameter("cityCode");
		List<CarRunNumVo> data = carRunService.queryProvinceCarTypeListStatisticsSum(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**运营车辆数据(车辆类型--按照全国、地级市划分)*/
	@RequestMapping(value = "/carType_list_statistics_run", method = { RequestMethod.GET , RequestMethod.POST})
	public String carTypeListStatisticsRun(HttpServletRequest request, HttpServletResponse response)
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
		List<CarRunNumVo> data = carRunService.queryRunCarNum(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**运营车辆数据(车辆类型--按照省份划分)*/
	@RequestMapping(value = "/provinceCarType_list_statistics_run", method = { RequestMethod.GET , RequestMethod.POST})
	public String ProvincecarTypeListStatisticsRun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode=request.getParameter("cityCode");
		 if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<CarRunNumVo> data = carRunService.queryProvinceRunCarNuma(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**运营车辆数据(订单类型--按照全国、低级市划分)*/
	@RequestMapping(value = "/orderType_list_statistics_run", method = { RequestMethod.GET , RequestMethod.POST})
	public String orderTypeListStatisticsRun(HttpServletRequest request, HttpServletResponse response)
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
		List<CarRunNumVo> data = carRunService.queryorderTypeListStatisticsRun(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	/**运营车辆数据(订单类型--按照省份划分)*/
	@RequestMapping(value = "/ProvinceOrderType_list_statistics_run", method = { RequestMethod.GET , RequestMethod.POST})
	public String ProvinceOrderTypeListStatisticsRun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		List<CarRunNumVo> data = carRunService.queryProvinceorderTypeListRun(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**运营车辆数据(会员类型)*/
	@RequestMapping(value = "/memberType_list_statistics_run", method = { RequestMethod.GET , RequestMethod.POST})
	public String memberTypeListStatisticsRun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String cityCode;
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		 if(user.getCityCode().equals("00")){
	        cityCode=null;
        }else{
       	    cityCode=user.getCityCode();
        }
		 //通过订单号查询车辆类型*/	
		String cityCode =request.getParameter("cityCode");
		if(cityCode.equals("00")){
	        cityCode=null;
        }
		List<CarRunNumVo> data = carRunService.queryMemberTypeListStatisticsRun(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**运营车辆数据(会员类型--按照省份划分)*/
	@RequestMapping(value = "/ProvinceMemberType_list_statistics_run", method = { RequestMethod.GET , RequestMethod.POST})
	public String ProvincememberTypeListStatisticsRun(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode =request.getParameter("cityCode");
		List<CarRunNumVo> data = carRunService.queryProvinceMemberTypeListStatisticsRun(cityCode) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	
	
	/**根据城市名称获取cityCode*/
	@RequestMapping(value = "/get_cityCode", method = { RequestMethod.GET , RequestMethod.POST})
	public String get_cityCode(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String name =new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");*/
		//通过城市ID查询城市网点
		List<CityVo> data = cityService.queryCode() ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**获取数据库中全国省下级城市信息*/
	@RequestMapping(value = "/city_area_map_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String city_area_map_statistics(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		//String name =new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
		String name = request.getParameter("name");
		if("全省".equals(name)){
			name=null;
		}
		List<CityVo> data = cityService.cityAreaMapStatistics(name) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**获取数据库中全国省级城市车辆信息*/
	@RequestMapping(value = "/province_area_map_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String province_area_map_statistics(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		//request.setCharacterEncoding("utf-8");
		/*String name =new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");*/
		//通过城市ID查询城市网点
		//String name =new String(request.getParameter("names").getBytes("iso-8859-1"),"utf-8");
		String name = request.getParameter("names");
		if("全国".equals(name)){
			name=null;
		}
		List<CityVo> data = cityService.provinceAreaMapStatistics(name) ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**获取数据库中全国区级信息*/
	@RequestMapping(value = "/area_map_car_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String area_map_statistics(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String name =new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");*/
		//通过城市ID查询城市网点
		List<CityVo> data = cityService.areaMapStatistics() ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**获取数据库中全国车辆总数信息*/
	@RequestMapping(value = "/china_car_statistics", method = { RequestMethod.GET , RequestMethod.POST})
	public String china_car_statistics(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		/*String name =new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");*/
		//通过城市ID查询城市网点
		List<CityVo> data = cityService.chinaCarStatistics() ;
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;		
	}
	
	/**获取全国省级单位的会员总数*/
	@SystemServiceLog(description="各省级会员总数信息")
	@RequestMapping(value = "/province_memberTotal_statistics", method = { RequestMethod.GET , RequestMethod.POST })
	public String queryAllMemberNums(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		List<MemberTotalVo> data = memberService.queryAllMemberNums(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="省下级城市会员总数信息")
	@RequestMapping(value = "/city_memberTotal_statistics", method = { RequestMethod.GET , RequestMethod.POST })
	public String queryCityAllMemberNums(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		List<MemberTotalVo> data = memberService.queryCityAllMemberNums(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	@SystemServiceLog(description="全国会员总数信息")
	@RequestMapping(value = "/china_member_statistics", method = { RequestMethod.GET , RequestMethod.POST })
	public String getMemberTotal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		List<MemberTotalVo> data = memberService.getMemberTotal(cityCode);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	@SystemServiceLog(description="按会员等级统计会员总数信息")
	@RequestMapping(value = "/getMemberTotalByLevel", method = { RequestMethod.GET , RequestMethod.POST })
	public String getMemberTotalByLevel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getMemberTotalByLevel(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="按会员类型统计会员总数信息")
	@RequestMapping(value = "/getMemberTotalByType", method = { RequestMethod.GET , RequestMethod.POST })
	public String getMemberTotalByType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getMemberTotalByType(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="按会员状态统计会员总数信息")
	@RequestMapping(value = "/getMemberTotalByStatus", method = { RequestMethod.GET , RequestMethod.POST })
	public String getMemberTotalByStatus(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getMemberTotalByStatus(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="按消费类型统计消费会员总数信息")
	@RequestMapping(value = "/getExpenseMember", method = { RequestMethod.GET , RequestMethod.POST })
	public String getExpenseMember(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getExpenseMember(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="按会员类型统计消费会员总数信息")
	@RequestMapping(value = "/getExpenseMemberByType", method = { RequestMethod.GET , RequestMethod.POST })
	public String getExpenseMemberByType(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getExpenseMemberByType(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	

	@SystemServiceLog(description="按会员等级统计消费会员总数信息")
	@RequestMapping(value = "/getExpenseMemberByLevel", method = { RequestMethod.GET , RequestMethod.POST })
	public String getExpenseMemberByLevel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getExpenseMemberByLevel(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	

	@SystemServiceLog(description="统计全国运营车辆租赁总数")
	@RequestMapping(value = "/query_run_car", method = { RequestMethod.GET , RequestMethod.POST })
	public String queryRunningCar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CarMaxRun carMaxRun = carMaxRunMapper.selectAllRecords();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONObject.fromObject(carMaxRun, jsonConfig));
		return null;
	}

	@SystemServiceLog(description="按会员状态统计消费会员总数信息")
	@RequestMapping(value = "/getExpenseMemberByStatus", method = { RequestMethod.GET , RequestMethod.POST })
	public String getExpenseMemberByStatus(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if(cityCode.equals("00")){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<MemberTotalVo> data = memberService.getExpenseMemberByStatus(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据cityCode获取订单车辆数")
	@RequestMapping(value = "/getOrderCarNumber", method = { RequestMethod.GET , RequestMethod.POST })
	public String getOrderCarNumber(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<carRunAnalysisVo> data = carRunService.getOrderCarNumber(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	@SystemServiceLog(description="根据cityCode获取订单总数")
	@RequestMapping(value = "/getOrderNumber", method = { RequestMethod.GET , RequestMethod.POST })
	public String getOrderNumber(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<carRunAnalysisVo> data = carRunService.getOrderNumber(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	@SystemServiceLog(description="根据cityCode获取订单时长")
	@RequestMapping(value = "/getOrderTime", method = { RequestMethod.GET , RequestMethod.POST })
	public String getOrderTime(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<carRunAnalysisVo> data = carRunService.getOrderTime(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	@SystemServiceLog(description="根据cityCode获取订单收入")
	@RequestMapping(value = "/getOrderIncome", method = { RequestMethod.GET , RequestMethod.POST })
	public String getOrderIncome(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8"); 
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode=null;
		}
		String level = request.getParameter("level");
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("cityCode", cityCode);
		map.put("level", level);
		List<carRunAnalysisVo> data = carRunService.getOrderIncome(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null ;
	}
	
	
	/**
	 * 车辆运营分析（日 上周 上月）
	 * 
	 * @author zixiaobang
	 * @date 20170327
	 * @throws Exception
	 */
	@SystemServiceLog(description = "车辆运营分析")
	@RequestMapping(value = "/getCarRunStatistics", method = { RequestMethod.GET , RequestMethod.POST })
	public String getCarRunStatistics(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		String cityCode = request.getParameter("cityCode");
		String level = request.getParameter("level");
		String period = request.getParameter("period");
		if ("00".equals(cityCode)) {
			cityCode = null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cityCode", cityCode);
		map.put("level", level);
		map.put("period", period);
		List<carRunAnalysisVo> data = carRunService.getCarRunStatistics(map);
		response.getWriter().print(Data2Jsp.listToJson(data));
		return null;
	}

	/**
	 * @describe 热力图
	 * lf
	 * 2017-10-18 17:19:35
	 */
	@SystemServiceLog(description="车辆运行热力图")
	@RequestMapping(value = "/heatmap_page", method = { RequestMethod.GET })
	public String heatmapPage() {
		return "monitorCenter/heatmap";
	}


    /**
     * 车辆运营热力图
     *
     * @author lf
     * @date 2017年10月19日 09:43:22
     */
    @SystemServiceLog(description = "车辆运行热力图数据")
    @RequestMapping(value = "/heatMapDate", method = RequestMethod.POST)
	@ResponseBody
    public List<Document> heatMapDate(String date){
		Date date1 = DateTime.getDate(date);
		return heatMapNosql.searchByDate(date1.getTime());
    }



}

