package com.iber.portal.controller.monitorCenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iber.portal.util.SendSMS;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.charging.ChargingOrderInfo;
import com.iber.portal.model.charging.ConnectorInfo;
import com.iber.portal.model.charging.ConnectorStatusInfo;
import com.iber.portal.model.dispatcherMonitor.ParkLocation;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.charging.ChargingOrderInfoService;
import com.iber.portal.service.charging.ConnectorInfoService;
import com.iber.portal.service.charging.ConnectorStatusInfoService;
import com.iber.portal.service.charging.EquipmentInfoService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.vo.charging.ChargingMonitorVo;
import com.iber.portal.vo.charging.ChargingPileVo;

@Controller
public class ChargingMonitorController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EquipmentInfoService equipmentInfoService;

	@Autowired
	private ConnectorInfoService connectorInfoService;

	@Autowired
	private ConnectorStatusInfoService connectorStatusInfoService;

	@Autowired
	private ParkService parkService;

	@Autowired
	private CityService cityService;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private SystemMsgMapper systemMsgMapper;

	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ChargingOrderInfoService chargingOrderInfoService;

	/**
	 * @describe 充电监控
	 * 
	 */
	@SystemServiceLog(description = "充电监控")
	@RequestMapping(value = "/charging_pile_monitoring", method = { RequestMethod.GET })
	public String parkMonitoring(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		log.info("当前订单页面跳转");
		response.setContentType("text/html;charset=utf-8");
		HttpSession session = request.getSession();
		// 获得登录人的经纬度
		String latitude = (String) session.getAttribute("latitude");
		String longitude = (String) session.getAttribute("longitude");
		Double latitudeDouble = null;
		Double longitudeDouble = null;
		if (!StringUtils.isBlank(latitude) && !StringUtils.isBlank(longitude)) {
			latitudeDouble = Double.parseDouble(latitude);
			longitudeDouble = Double.parseDouble(longitude);
		}
		// 城市过滤
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null;
		if (!"00".equals(user.getCityCode())) {
			cityCode = user.getCityCode();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);

		List<Park> parks = parkService.selectAll(paramMap);
		List<ParkLocation> locations = new ArrayList<ParkLocation>();
		for (Park park : parks) {
			ParkLocation location = new ParkLocation();
			location.setId(park.getId());
			location.setLatitude(latitudeDouble);
			location.setLongitude(longitudeDouble);
			location.setParkLatitude(Double.parseDouble(park.getLatitude()));
			location.setParkLongitude(Double.parseDouble(park.getLongitude()));
			location.setParkName(park.getName());
			location.setCategory(park.getCategory());
			location.setCooperationType(String.valueOf(park.getCooperationType()));
			locations.add(location);
		}
		JSONArray array = JSONArray.fromObject(locations);
		model.addAttribute("locations", array);
		return "monitorCenter/chargingMonitorCenter";
	}

	/**
	 * @describe 获取只有充电桩的网点以及该网点下电桩,电枪的信息
	 * 
	 */
	@SystemServiceLog(description = "获取只有充电桩的网点及该网点下的充电桩状态等信息")
	@RequestMapping(value = "/park_equip_conn", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryParkAndEquipConn(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
	    request.setCharacterEncoding("UTF-8");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = request.getParameter("cityCode");
		String parkName = request.getParameter("parkName");
		String connectorStatusStr = request.getParameter("connectorStatus");
		Integer connectorStatus = null;
		if (StringUtils.isNotBlank(connectorStatusStr)) {
			connectorStatus = Integer.valueOf(connectorStatusStr);
		}
		if (user.getCityCode().equals("00")) {
			if (!StringUtils.isBlank(cityCode)) {
				if (cityCode.equals("00")) {
					paramMap.put("cityCode", null);
				} else {
					paramMap.put("cityCode", cityCode);
				}
			}
		} else {
			paramMap.put("cityCode", user.getCityCode());
		}
		paramMap.put("parkName", parkName);
		paramMap.put("connectorStatus", connectorStatus);
		List<ChargingMonitorVo> lists = new ArrayList<ChargingMonitorVo>();
		int ownRecords = 0;
		int partnerRecords = 0;
			ownRecords = connectorInfoService.queryParkAndEquipConnRecords(paramMap);
			partnerRecords = connectorInfoService.queryPartnerConnectorAndParkInfoRecords(paramMap);
			int index = ownRecords / rows;
			int mode = ownRecords % rows;
			int numeral = index+1-page;
			if(0<numeral){
				lists = connectorInfoService.queryConnectorStatusAndParkInfo(paramMap);
			}
			if(0==numeral){
				paramMap.put("offset", (page-1)*rows);
				paramMap.put("rows", mode);
				lists.addAll(connectorInfoService.queryConnectorStatusAndParkInfo(paramMap));
				paramMap.put("offset", 0);
				paramMap.put("rows", rows - mode);
				List<ChargingMonitorVo> results = connectorInfoService.queryPartnerConnectorAndParkInfo(paramMap);
				// 合作网点的枪状态均置空
				for (ChargingMonitorVo vo : results) {
					vo.setStatus(404);
				}
				lists.addAll(results);
			}
			if(0>numeral){
				paramMap.put("offset", (page-index-1)*rows-mode);
				paramMap.put("rows", rows);
				lists = connectorInfoService.queryPartnerConnectorAndParkInfo(paramMap);
				// 合作网点的枪状态均置空
				for (ChargingMonitorVo vo : lists) {
					vo.setStatus(404);
				}
			}
		int totalRecords = ownRecords + partnerRecords;
		String json = Data2Jsp.Json2Jsp(lists, totalRecords);
		response.getWriter().print(json);
     	return null;
	}

	private static String dealWith(Integer id) {
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"cId\":\"platForm\",\"memberId\":\"" + "\",");
		sb.append("\"method\":\"" + "queryPointDetails" + "\",");
		sb.append("\"param\":\"" + "{'stationId':'" + id + "'}" + "\",");
		sb.append("\"phone\":\"" + "" + "\",\"type\":\"\",\"version\":\"1\"");
		sb.append("}");
		return sb.toString();
	}

	@SystemServiceLog(description = "获取充电桩状态等信息")
	@RequestMapping(value = "/checkPartnerConnectStatus", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String queryCooperConnectStatus(int stationId, int connectorId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String json = "{}";
		SysParam sysParam = sysParamService.selectByKey("http_url_c");
		if (sysParam.getValue().indexOf("https") == 0) {
			json = HttpsClientUtil.get(sysParam.getValue(), dealWith(stationId));
		} else {
			json = HttpUtils.commonSendUrl(sysParam.getValue(), dealWith(stationId));
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject = JSONObject.fromObject(json);
		JSONArray arr = new JSONArray();
		if("00".equals(jsonObject.get("code").toString())){
			JSONObject result = jsonObject.getJSONObject("result");
	         arr = result.getJSONArray("connectorStatusList");
		}
		JSONObject results = new JSONObject();
		for (Object object : arr.toArray()) {
			JSONObject jsonObj = (JSONObject) object;
			Integer connId = (Integer) jsonObj.get("connectorId");
			Integer connStatus = (Integer) jsonObj.get("status");
			if (null != connId && connectorId == connId) {
				if (1 == connStatus) {
					// 空闲
					results.put("msg", "succ");
					results.put("status", connStatus);
					break;
				} else {
					results.put("msg", "fail");
					results.put("status", connStatus);
					break;
				}
			}
		}
		response.getWriter().print(results);
		return null;
	}

	/**
	 * @describe 获取充电桩状态等信息
	 */
	@SystemServiceLog(description = "获取充电桩状态等信息")
	@RequestMapping(value = "/charging_mrg_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String queryChargingMrgList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");

		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));

		SysUser user = (SysUser) request.getSession().getAttribute("user");

		String cityCode = request.getParameter("cityCode");
		String parkName = request.getParameter("parkName");
		String connectorStatusStr = request.getParameter("connectorStatus");
		Integer connectorStatus = null;
		if (StringUtils.isNotBlank(connectorStatusStr)) {
			connectorStatus = Integer.valueOf(connectorStatusStr);
		}

		if ("00".equals(user.getCityCode())) {
			if (!StringUtils.isBlank(cityCode)) {
				if (cityCode.equals("00")) {
					paramMap.put("cityCode", null);
				} else {
					paramMap.put("cityCode", cityCode);
				}
			}
		} else {
			paramMap.put("cityCode", user.getCityCode());
		}
		
		paramMap.put("parkName", parkName);
		paramMap.put("connectorStatus", connectorStatus);
		List<ChargingMonitorVo> list = connectorInfoService.queryConnectorStatusByCityParkAndStatus(paramMap);
		int totalRecords = connectorInfoService.queryConnectorRecordsByCityParkAndStatus(paramMap);

		String json = Data2Jsp.Json2Jsp(list, totalRecords);
		response.getWriter().print(json);
		return null;
	}

	/**
	 * @describe 根据parkName查询充电桩
	 */
	@SystemServiceLog(description = "根据parkName查询充电桩")
	@RequestMapping(value = "/getAllChargingPileByParkId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String getAllChargingPileByParkId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String parkName = request.getParameter("parkName");
		String parkId = request.getParameter("stationId");
		List<ChargingPileVo> vos = null;
		// 调用接口查询合作网点的桩枪信息
			String json = "";
		    Park park =	parkService.selectByPrimaryKey(Integer.valueOf(parkId)); 
			
			String operatorId = park.getOperatorId();
			if(operatorId.startsWith("EAST_")){
				SysParam sysParam = sysParamService.selectByKey("http_url_c");
				if (sysParam.getValue().indexOf("https") == 0) {
						json = HttpsClientUtil.get(sysParam.getValue(),dealWith(Integer.valueOf(parkId)));
					} else {
						json = HttpUtils.commonSendUrl(sysParam.getValue(),dealWith(Integer.valueOf(parkId)));
					}
			    JSONObject jsonObject =  JSONObject.fromObject(json);
				JSONArray arr = new JSONArray();
				if("00".equals(jsonObject.getString("code"))){
					arr = jsonObject.getJSONObject("result").getJSONArray("connectorStatusList");
				}
				vos = parkService.getCooperationParks(parkName);
				for (ChargingPileVo vo : vos) {
					if(!arr.isEmpty()){
					for (Object object : arr) {
						JSONObject jsonObj = (JSONObject) object;
						String connId = jsonObj.get("connectorId").toString();
						Integer status = (Integer) jsonObj.get("status");
						if (connId.equals(vo.getConnectorId().toString())) {
							if (0 == status) {
								vo.setStatusName("离网");
							} else if (1 == status) {
								vo.setStatusName("空闲");
							} else if (2 == status) {
								vo.setStatusName("占用（未充电）");
							} else if (3 == status) {
								vo.setStatusName("占用（充电中）");
							} else if (4 == status) {
								vo.setStatusName("占用（预约锁定）");
							} else if (255 == status){
								vo.setStatusName("故障");
							}else {
								vo.setStatusName("&nbsp;&nbsp;");
							}
							vo.setStatus(status);
						}
					  }
					}
			//		vo.setStatusName("&nbsp;&nbsp;");
					if (1 == vo.getCategory()) {
						vo.setCategoryType("1S网点");
					} else if (2 == vo.getCategory()) {
						vo.setCategoryType("2S网点");
					} else if (3 == vo.getCategory()) {
						vo.setCategoryType("3S网点");
					} else if (4 == vo.getCategory()) {
						vo.setCategoryType("6S网点");
					}
				}
			}
		    else {
			    // 查询自有网点的桩枪信息
			   vos = parkService.getAllChargingPileByParkId(parkName);
			   for (int i = 0; i < vos.size(); i++) {
			 	if (vos.get(i).getStatus() == 0) {
					vos.get(i).setStatusName("离网");// 0：离网 1：空闲 2：占用（未充电）
													// 3：占用（充电中）4：占用（预约锁定）255：故障
				} else if (vos.get(i).getStatus() == 1) {
					vos.get(i).setStatusName("空闲");
				} else if (vos.get(i).getStatus() == 2) {
					vos.get(i).setStatusName("占用（未充电）");
				} else if (vos.get(i).getStatus() == 3) {
					vos.get(i).setStatusName("占用（充电中）");
				} else if (vos.get(i).getStatus() == 4) {
					vos.get(i).setStatusName("占用（预约锁定）");
				} else if (vos.get(i).getStatus() == 255) {
					vos.get(i).setStatusName("故障");
				}
				if (vos.get(i).getCategory() == 1) {
					vos.get(i).setCategoryType("1S网点");
				} else if (vos.get(i).getCategory() == 2) {
					vos.get(i).setCategoryType("2S网点");
				} else if (vos.get(i).getCategory() == 3) {
					vos.get(i).setCategoryType("3S网点");
				} else if (vos.get(i).getCategory() == 4) {
					vos.get(i).setCategoryType("6S网点");
				}
			}
		}
		response.getWriter().print(Data2Jsp.listToJson(vos));
		return null;
	}

	/**
	 * @describe 预约充电桩
	 */
	@SystemServiceLog(description = "预约充电桩")
	@RequestMapping(value = "/ordered", method = { RequestMethod.GET, RequestMethod.POST })
	public String ordered(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String equipmentId = request.getParameter("equipmentId");// 桩id
		String parkNo = request.getParameter("parkNo");// 车位号
		String connectorId = request.getParameter("connectorId");// 枪id
		String stationId = request.getParameter("parkId");
		String phone = request.getParameter("phone");
		String lpn = request.getParameter("lpn");
		String parkName = request.getParameter("parkName");
		String operatorId = request.getParameter("operatorId");
		// 如果充电枪已经被预约，就不能预约
		if (!StringUtils.isBlank(connectorId)) {
			Integer  count = connectorInfoService.selectOrderedOrderByConnectorId(Integer.parseInt(connectorId));
			if (count > 0) {// 说明该充电枪已经被预约
				response.getWriter().print("ordered");
				return null;
			}
		}
		Member member = memberService.selectDetailByPhone(phone);
		if (member == null) {
			response.getWriter().print("noMember");
		}
		Map<String, String> httpMap = new HashMap<String, String>();
		httpMap.put("cId", "platForm");
		if (member != null)
			httpMap.put("memberId", member.getId().toString());
		httpMap.put("phone", phone);
		httpMap.put("type", "platForm");
		httpMap.put("version", "1");
		String jsons = "{}";
		Map<String, String> methodParamMap = new HashMap<String, String>();
		methodParamMap.put("equipmentId", equipmentId);
		methodParamMap.put("parkNo", parkNo);
		methodParamMap.put("connectorId", connectorId);
		methodParamMap.put("stationId", stationId);
		methodParamMap.put("lpn", lpn);
		SysParam sysParam = sysParamService.selectByKey("http_url_c");
		if (operatorId.startsWith("EAST_")) {
			methodParamMap.put("memberId", member.getId().toString());
			methodParamMap.put("operatorId", operatorId);
			methodParamMap.put("method", "generateOrdChargeOrder");
			httpMap.put("method", "generateOrdChargeOrder");
			String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
			httpMap.put("param", methodParams);
			String params = FastJsonUtils.toJson(httpMap);
			if (sysParam.getValue().indexOf("https") == 0) {
				jsons = HttpsClientUtil.get(sysParam.getValue(), params);
			} else {
				jsons = HttpUtils.commonSendUrl(sysParam.getValue(), params);
			}
		} else {
			methodParamMap.put("operatorId", "");
			String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
			httpMap.put("method", "generateOrdChargeOrder");
			httpMap.put("param", methodParams);
			String params = FastJsonUtils.toJson(httpMap);
			if (sysParam.getValue().indexOf("https") == 0) { // https接口
				jsons = HttpsClientUtil.get(sysParam.getValue(), params);
			} else {
				jsons = HttpUtils.commonSendUrl(sysParam.getValue(), params);
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(jsons);
		String code = jsonObject.getString("code");
		if("00".equals(code)){//成功
			// 下发通知短信给客户
			//SysParam sendUrl = sysParamService.selectByKey("send_sms_url");
			SysParam minutes = sysParamService.selectByKey("cancel_order_minutes");
			try {
				//生成token
				/*String encryptToken = SendSMS.encryptBySalt(phone);
				Map<String, String> paramss = new HashMap<String, String>();
				String param = "{\"telephoneNo\":\""+ phone+ "\",\"ipAddress\":\"\",\"templateId\":\"2674\",\"contentParam\":\""
						+ new String((parkName + "|" + parkNo + "号" + "|" + (minutes!=null ? minutes.getValue():"")).getBytes("utf-8"),"ISO-8859-1") + "\",\"token\":\""+encryptToken+"\"}";
				paramss.put("msgContentJson", param);
				if (sendUrl != null) {
					HttpsClientUtil.post(sendUrl.getValue(), paramss);
				} else {
					log.error("预约充电桩时发送短信有问题");
				}*/
				SendSMS.send(phone,"",2674, parkName + "|" + parkNo + "号" + "|" + (minutes!=null ? minutes.getValue():"") );

			} catch (Exception e) {
				log.error("预约充电桩时发送短信出错", e);
			}
			response.getWriter().print("succ");
		}else{
			response.getWriter().print(jsonObject.getString("msg"));
		}
		return null;
	}
	/**
	 * @describe 取消预约充电桩
	 */
	@SystemServiceLog(description = "取消预约充电桩")
	@RequestMapping(value = "/cancelOrdered", method = { RequestMethod.GET, RequestMethod.POST })
	public String cancelOrdered(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String connectorId = request.getParameter("connectorId");
		String parkName = request.getParameter("parkName");
		String parkNo = request.getParameter("parkNo");
		String operatorId = request.getParameter("operatorId");
		ChargingOrderInfo chargingOrderInfo = chargingOrderInfoService.selectByConnectorId(Integer.parseInt(connectorId));
		if (chargingOrderInfo == null) {
			response.getWriter().print("noOrder");
			return null;
		} else {
			String jsons = "{}";
			Member member = memberService.selectByPrimaryKey(chargingOrderInfo.getMemberId());
			Map<String, String> httpMap = new HashMap<String, String>();
			httpMap.put("cId", "platForm");
			httpMap.put("memberId", String.valueOf(chargingOrderInfo.getMemberId()));
			httpMap.put("method", "cancelOrdChargeOrder");
			httpMap.put("phone", member.getPhone());
			httpMap.put("type", "platForm");
			httpMap.put("version", "1");
			SysParam sysParam = sysParamService.selectByKey("http_url_c");
			// 易事特的桩
			if (operatorId.startsWith("EAST_")) {
				Map<String, String> methodParamMap = new HashMap<String, String>();
				methodParamMap.put("orderId", chargingOrderInfo.getChargeSeq());
				methodParamMap.put("method", "cancelOrdChargeOrder");
				String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
				httpMap.put("param", methodParams);
				String str = FastJsonUtils.toJson(httpMap);
				if (sysParam.getValue().indexOf("https") == 0) {
					jsons = HttpsClientUtil.get(sysParam.getValue(), str);
				} else {
					jsons = HttpUtils.commonSendUrl(sysParam.getValue(), str);
				}
			} else {// 自有网点的桩
				Map<String, String> methodParamMap = new HashMap<String, String>();
				methodParamMap.put("orderId", chargingOrderInfo.getChargeSeq());
				String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
				httpMap.put("param", methodParams);
				String params = FastJsonUtils.toJson(httpMap);
				if (sysParam.getValue().indexOf("https") == 0) { // https接口
					jsons = HttpsClientUtil.get(sysParam.getValue(), params);
				} else {
					jsons = HttpUtils.commonSendUrl(sysParam.getValue(), params);
				}
			}

			JSONObject jsonObject = JSONObject.fromObject(jsons);
			String code = jsonObject.getString("code");
			if("00".equals(code)){//成功
				//下发通知短信给客户
				//SysParam sendUrl = sysParamService.selectByKey("send_sms_url");
				try{

					//生成token
					/*String encryptToken = SendSMS.encryptBySalt(member.getPhone());

					Map<String, String> paramss = new HashMap<String, String>();  
					String param ="{\"telephoneNo\":\""+member.getPhone()+"\",\"ipAddress\":\"\",\"templateId\":\"2675\",\"contentParam\":\""
							+new String((parkName+"|"+parkNo+"号").getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
					paramss.put("msgContentJson", param); 
					if(sendUrl != null){
						HttpsClientUtil.post(sendUrl.getValue(), paramss) ;
					}else{
						log.error("取消预约充电桩时发送短信有问题");
					}*/
					SendSMS.send(member.getPhone(),"",2675,parkName+"|"+parkNo+"号");

				}catch(Exception e){
					log.error("取消预约充电桩时发送短信出错", e);
				}
				response.getWriter().print("succ");
				//推送消息给前端
				PushCommonBean push = new PushCommonBean("server_push_member_cancel_charging_order","1","取消预约充电桩","");
				if(push != null){
					List<String> alias = PushClient.queryClientId(member.getPhone());
					if(!alias.isEmpty() && alias.size()> 0){
						for(String cid : alias){
							JsonConfig jsonConfig = new JsonConfig();
							jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
							PushClient.push(cid, net.sf.json.JSONObject.fromObject(push, jsonConfig));
						}
					}
				}
			}else{
				response.getWriter().print(jsonObject.getString("msg"));
			}
			return null;
		}
	}

	/**
	 * @describe 控制地锁
	 */
	@SystemServiceLog(description = "控制地锁")
	@RequestMapping(value = "/lockUp", method = { RequestMethod.GET, RequestMethod.POST })
	public String lockUp(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String equipmentCode = request.getParameter("equipmentCode");
		String connectorNo = request.getParameter("connectorNo");
		String connectorId = request.getParameter("connectorId");
		String connectorNumber = request.getParameter("connectorNumber");
		String isUp = request.getParameter("isUp");
		Map<String, Object> methodParamMap = new HashMap<String, Object>();
		methodParamMap.put("equipmentCode", equipmentCode);
		methodParamMap.put("connectorNo", connectorNo);
		methodParamMap.put("connectorId", connectorId);
		methodParamMap.put("connectorCnt", connectorNumber);
		if ("0".equals(isUp)) {
			methodParamMap.put("isUp", true);
		}else {
			methodParamMap.put("isUp", false);
		}
		String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
		Map<String, String> httpMap = new HashMap<String, String>();
		httpMap.put("cId", "platForm");
		httpMap.put("memberId", "");
		httpMap.put("method", "sendOneLockCtrl");
		httpMap.put("param", methodParams);
		httpMap.put("phone", "");
		httpMap.put("type", "platForm");
		httpMap.put("version", "1");
		String params = FastJsonUtils.toJson(httpMap);
		SysParam sysParam = sysParamService.selectByKey("http_url_c") ;
		String json = "";
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), params) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), params) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		String code = jsonObject.getString("code") ;
		if(code.equals("00")){//成功
			response.getWriter().print("succ");
		}else{
			response.getWriter().print(jsonObject.getString("msg"));
		}
		return null;    
	}

	/**
	 * @describe 启动充电
	 */
	@SystemServiceLog(description = "启动充电")
	@RequestMapping(value = "/startCharging", method = { RequestMethod.GET, RequestMethod.POST })
	public String startCharging(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String connectorCode = request.getParameter("connectorCode");
		String connectorId = request.getParameter("connectorId");
		String phone = request.getParameter("phone");
		//查询充电抢的状态 若为空闲着调用接口开启充电 否则则提示不能充电的原因	
		
		ConnectorStatusInfo statusInfo = connectorStatusInfoService.queryByconnectorCodeAndId(connectorCode,connectorId);
		//ChargingOrderInfo chargingOrderInfo = chargingOrderInfoService.selectByMemberIdAndStatus(Integer.parseInt(connectorId));
		if(statusInfo == null){
			response.getWriter().print("没有找到充电抢,请稍后再试");
			return null;
		}
		
		if (!StringUtils.equals(statusInfo.getStatus().toString(), "1")) {
			//0：离网 1：空闲 2：占用（未充电） 3：占用（充电中）4：占用（预约锁定）255：故障
			switch (statusInfo.getStatus()) {
			case 0:
				response.getWriter().print("充电抢离网,请稍后再试");
				break;
			case 2:
			case 3:
			case 4:
				response.getWriter().print("充电抢被占用,请稍后再试");
				break;
			case 255:
				response.getWriter().print("充电抢故障,请稍后再试");
				break;
			default:
				break;
			}
			return null;
		}else {
			Member member = memberService.selectDetailByPhone(phone);
			if(member == null){
				response.getWriter().print("未找到对应的用户，请检查您输入的手机号码");
				return null;
			}
			Map<String, String> httpMap = new HashMap<String, String>();
			Map<String, String> methodParamMap = new HashMap<String, String>();
			methodParamMap.put("qrCode", connectorCode);
			//methodParamMap.put("memberId", member.getId().toString());
			String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
			httpMap.put("cId", "platForm");
			httpMap.put("memberId", member.getId().toString());
			httpMap.put("method", "getConnectorInfoListByEquipmentCode");
			httpMap.put("param", methodParams);
			httpMap.put("type", "platForm");
			httpMap.put("version", "1");
			httpMap.put("phone", phone);
			String params = FastJsonUtils.toJson(httpMap);
			String json = "";
			SysParam sysParam = sysParamService.selectByKey("http_url_c");
			if (sysParam.getValue().indexOf("https") == 0) {
				json = HttpsClientUtil.get(sysParam.getValue(), params);
			} else {
				json = HttpUtils.commonSendUrl(sysParam.getValue(), params);
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			String code = jsonObject.getString("code");
			if (code.equals("00")) {// 成功
				response.getWriter().print("succ");
			} else {
				response.getWriter().print(jsonObject.getString("msg"));
			}
			return null;

		}
	}

	/**
	 * @describe 结束充电
	 */
	@SystemServiceLog(description = "结束充电")
	@RequestMapping(value = "/endCharging", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String endCharging(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String connectorId = request.getParameter("connectorId");
		String operatorId = request.getParameter("operatorId");
		// 如果是员工结束充电，就不允许在管理平台结束充电
		List<ChargingOrderInfo> infos = chargingOrderInfoService.selectEmployeeChargingOrderByConnectorId(Integer.parseInt(connectorId));
		if (infos.size() > 0) {
			response.getWriter().print("employeeCharging");
			return null;
		}
		ChargingOrderInfo chargingOrderInfo = null;
		if(operatorId.startsWith("EAST_")){
			chargingOrderInfo = chargingOrderInfoService.queryCooperationChargingOrder(Integer.parseInt(connectorId));
		}
		else {
			chargingOrderInfo =	chargingOrderInfoService.selectByMemberIdAndChargingStatus(Integer.parseInt(connectorId));
		}
		if (chargingOrderInfo == null) {
			response.getWriter().print("noCharging");
			return null;
		} else {

			Map<String, String> methodParamMap = new HashMap<String, String>();
			methodParamMap.put("chargeSeq", chargingOrderInfo.getChargeSeq());
			String methodParams = FastJsonUtils.toJson(methodParamMap)
					.replaceAll("\"", "'");
			Map<String, String> httpMap = new HashMap<String, String>();
			httpMap.put("cId", "platForm");
			httpMap.put("memberId", chargingOrderInfo.getMemberId().toString());
			httpMap.put("method", "requestEndCharging");
			httpMap.put("param", methodParams);
			httpMap.put("phone", chargingOrderInfo.getPhone());
			httpMap.put("type", "platForm");
			httpMap.put("version", "1");
			String params = FastJsonUtils.toJson(httpMap);
			SysParam sysParam = sysParamService.selectByKey("http_url_c");
			String json = "";
			if (sysParam.getValue().indexOf("https") == 0) {
				json = HttpsClientUtil.get(sysParam.getValue(), params);
			} else {
				json = HttpUtils.commonSendUrl(sysParam.getValue(), params);
			}
			JSONObject jsonObject = JSONObject.fromObject(json);
			String code = jsonObject.getString("code");
			if (code.equals("00")) {// 成功
				response.getWriter().print("succ");
			} else {
				response.getWriter().print(jsonObject.getString("msg"));
			}
			return null;
		}
	}

	/**
	 * 查询充电桩使用人和使用时间
	 */
	@SystemServiceLog(description = "查询充电桩使用人和使用时间")
	@RequestMapping(value = "/selectUserByConnectorId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String selectUserByConnectorId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String connectorId = request.getParameter("connectorId");
		String status = request.getParameter("status");
		ChargingOrderInfo chargingOrderInfo = null;
		List<String> list = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (status.contains(",")) {// 如果是预约状态，可能是ordered或者preOrdered
			String[] orderStatuArr = status.split(",");
			for (String string : orderStatuArr) {
				list.add(string);
			}
			map.put("connectorId", Integer.parseInt(connectorId));
			map.put("statusList", list);
			chargingOrderInfo = chargingOrderInfoService
					.selectUserByConnectorId(map);
		} else {
			map.put("connectorId", Integer.parseInt(connectorId));
			list.add(status);
			map.put("statusList", list);
			chargingOrderInfo = chargingOrderInfoService
					.selectUserByConnectorId(map);
		}

		if (chargingOrderInfo == null) {// 如果chargingOrderInfo为空，说明是还车扫码充电，此时会员状态为return，充电枪状态为3
			Map<String, Object> chargingOrderMap = new HashMap<String, Object>();
			map.put("memberStatus", "return");
			map.put("status", 3);
			map.put("connectorId", connectorId);
			ChargingOrderInfo info = chargingOrderInfoService
					.selectReturnCarCharging(chargingOrderMap);
			response.getWriter().print(Data2Jsp.mapToJson(info));
			return null;
		}
		response.getWriter().print(Data2Jsp.mapToJson(chargingOrderInfo));
		return null;
	}
}
