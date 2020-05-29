package com.iber.portal.controller.longRent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.SysConstant;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.CityMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.getui.PushCarCommon;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.car.CarRepair;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.longRent.LongRentExchangeCarLog;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.CityService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.car.CarRepairService;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.longRent.LongRentExChangeCarLogService;
import com.iber.portal.service.longRent.LongRentOrderService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.tbox.TboxService;
import com.iber.portal.util.SystemOperationUtil;
import com.iber.portal.vo.car.CarOrderVo;
import com.iber.portal.vo.dayRent.CarOrderBean;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.dao.dayRent.DayLongRentOrderMapper;
import com.iber.portal.model.dayRent.LongRentOrder;

/**
 * @Author:cuichongc
 * @Version:1.0
 */
@Controller
public class LongRentController extends MainController {

	private static final Logger logger = LoggerFactory.getLogger(LongRentController.class);
	@Autowired
	private DayLongRentOrderMapper dayLongRentOrderMapper;

	@Autowired
	private CarRepairService carRepairService;

	@Autowired
	private CarRunService carRunService;

	@Autowired
	private ParkService parkService;


	@Autowired
	private CarService carService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CityMapper cityMapper;

	@Autowired
	private LongRentExChangeCarLogService longRentExChangeCarLogService;

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private TboxService tboxService;

	@Autowired
	private CarRunMapper carRunMapper;

	@SystemServiceLog(description="日租订单")
	@RequestMapping(value = "/day_rent_long_manager", method = { RequestMethod.GET })
	public String dayRentPrice(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("日租订单管理页面");
		return "dayLongRent/dayLongOrder";		
	}
	
	@SystemServiceLog(description="日租订单管理列表")
	@RequestMapping(value={"/dayLongRent_order_list"},method = { RequestMethod.GET , RequestMethod.POST })
	public String dayLongOrderList(Integer rows,Integer page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String cityCode = request.getParameter("cityCode");
		if(StringUtils.equals("00", cityCode)){
			map.put("cityCode", null) ;
		}else{
			map.put("cityCode", cityCode);
		}
		String memberName = request.getParameter("memberName");
		String phone = request.getParameter("phone");
		String lpn = request.getParameter("lpn");
		String orderStatus = request.getParameter("orderStatus");
		String bt = request.getParameter("bt");
		String et = request.getParameter("et");
		map.put("memberName", memberName);
		map.put("lpn", lpn);
		map.put("phone", phone);
		map.put("orderStatus", orderStatus);
		map.put("bt", bt);
		map.put("et", et);
		map.put("from", from);
		map.put("to", to);
		List<LongRentOrder> list = dayLongRentOrderMapper.selectAllDayLongOrder(map);
		int total = dayLongRentOrderMapper.selectCountDayOrder(map);
		String json = Data2Jsp.Json2Jsp(list, total);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="续租订单详情列表")
	@RequestMapping(value={"/reletDetailList"},method = { RequestMethod.GET , RequestMethod.POST })
	public String reletDetail(Integer rows,Integer page,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		int p = (page == null) ? 1 : page ;
		int r = (rows == null) ? 100 : rows ;
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String orderId = request.getParameter("orderId");
		String extendTsOrderId = request.getParameter("extendTsOrderId");
		String orderStatus = request.getParameter("orderStatus");
		map.put("orderStatus", orderStatus);
		map.put("orderId", orderId);
		map.put("extendTsOrderId", extendTsOrderId);
		map.put("from", from);
		map.put("to", to);
		
		List<LongRentOrder> list = dayLongRentOrderMapper.selectReletNoDetail(map);
		int total = dayLongRentOrderMapper.selectDetailCount(map);
		String json = Data2Jsp.Json2Jsp(list, total);
		response.getWriter().print(json);
		return null;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@SystemServiceLog(description="日租更换车辆")
	@RequestMapping(value = "long_rent_request_change_car", method = { RequestMethod.POST })
	public String longRentRequestChangeCar(CarRepair carRepair,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		logger.info("日租更换车辆");
		Boolean isOnline = true;
		String afterLpn = request.getParameter("afterLpn");

		//version
		final Integer version = tboxService.tboxVersion(afterLpn);
		if (version == null || version == 0){
			response.getWriter().print("fail");
			return null;
		}
		//如果是车载
		if (version == 2){
			List<String> cidList = PushClient.queryClientId(afterLpn);
			for (String lpnCid : cidList) {
				if (PushClient.getUserStatus(lpnCid).equalsIgnoreCase("offline")){
					isOnline = false;
				}else{
					isOnline = true;
				}
			}
		}
		if (version == 3){
			// TODO: 2018/1/22 tboxs是否在线判断 
		}
		//判断换车后的车辆是否在线
		if (!isOnline){
			response.getWriter().print("getui_offline");
			return null;
		}
		//日租订单
		LongRentOrder longRentOrder = dayLongRentOrderMapper.selectLatestByOrderId(carRepair.getOrderId());
		if (longRentOrder==null){
			response.getWriter().print("ordernotUse");
			return null;
		}
		//防止前端传过来的车牌错误的问题
		carRepair.setLpn(longRentOrder.getLpn());
		CarRun vo = carRunService.getCarInfo(longRentOrder.getLpn());
		String carRunStatus = vo.getStatus();
		//1.这个车不是用车状态
		if(!SysConstant.CAR_RUN_STATUS_USECAR.equals(carRunStatus)){
			response.getWriter().print("notUse");
			return null;
		}
		//1.订单的orderid 和carrun orderid对应不上
		if(vo.getOrderId()==null||!vo.getOrderId().equals(carRepair.getOrderId())){
			response.getWriter().print("orderid_not_true");
			return null;
		}
		//2.还车到临时换车网点
		//网点id
		List<City> cityList = cityMapper.queryCityByCode(vo.getCityCode());
		if(null == cityList || cityList.isEmpty()){
			response.getWriter().print("noCity");
			return  null;
		}
		final City city = cityList.get(0);
		int parkId ;
		Park park = parkService.queryParkByCityCodeAndParkName(vo.getCityCode(),city.getName()+SysConstant.LONG_RENT_CHANGE_PARKNAME);
		if (park == null){
			Park newPark = new Park();
			newPark.setFullNoParking(false);
			newPark.setStatus(1);
			newPark.setIsRun(1);
			newPark.setParkNums(0);
			newPark.setName(city.getName()+SysConstant.LONG_RENT_CHANGE_PARKNAME);
			newPark.setCityCode(vo.getCityCode());
			newPark.setParkType("1");
			newPark.setRemark(SysConstant.LONG_RENT_CHANGE_PARKNAME);
			//避免和超长订单重复
			newPark.setLatitude((Double.parseDouble(city.getLatitude())+0.003)+"");
			newPark.setLongitude(city.getLongitude());
			newPark.setAddress(city.getName() + SysConstant.LONG_RENT_CHANGE_PARKNAME);
			//是临时网点
			newPark.setIsTemporary(0);
			//超长订单的临时网点
			newPark.setCategory(0);
			//自有
			newPark.setCooperationType(0);
			parkService.insertSelective(newPark);
			parkId = newPark.getId();
		}else{
			parkId = park.getId();
		}
		//更新车辆parkid
		carRunService.updateCarRunParkId(vo.getId(),parkId);
		//3.下线车辆
		carRepair.setStartTime(new Date());
		carRepair.setStatusCache(carRepair.getStatus());
		//查询车辆状态
		SysUser sysUser = SystemOperationUtil.getSysUser(request);
		carRepair.setResponsiblePersonId(sysUser.getId());
		carRepair.setResponsiblePerson(sysUser.getName());
		Car car = carService.selectByLpn(carRepair.getLpn().replace("•", ""));
		carRepair.setCarId(car.getId());
		carRepair.setCityCode(car.getCityCode());
		carRepair.setCreateUser(sysUser.getAccount());
		carRepair.setCreateTime(new Date());
		carRepair.setParkId(parkId);
		String predictTime = request.getParameter("queryDateFrom");
		Date date = null;
		if(!org.apache.commons.lang3.StringUtils.isBlank(predictTime)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			date = sdf.parse(predictTime);
		}else{
			date = new Date(System.currentTimeMillis()+60*60*24*1000);
		}
		carRepair.setPredictTime(date);
		int r = carRepairService.saveCarRepairInfo(carRepair,carRunStatus,vo.getRestBattery());
		//4.保存换车记录
		try {
			longRentExChangeCarLogService.saveLongRentExchangeCarLog(carRepair.getOrderId(),vo,afterLpn,sysUser,carRepair.getReason(),longRentOrder);
		}catch (Exception e){
			if (e.getMessage().equalsIgnoreCase("car_run status is not empty")){
				response.getWriter().print("car_is_use");
				return null;
			}
		}

		//5.下发新的订单信息给新车辆
		SysParam sysParam = sysParamService.selectByKey("http_url_d");
		Map map = new HashMap();
		map.put("cId","");
		map.put("memberId","");
		map.put("method","pushOrderMsgTodev");
		map.put("param","{\'orderId\':\'"+carRepair.getOrderId()+"\'}");
		map.put("phone","");
		map.put("type","platForm");
		map.put("version","1");
		String result="";
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			result = HttpsClientUtil.get(sysParam.getValue(), JSON.toJSONString(map)) ;
		}else{
			result = HttpUtils.commonSendUrl(sysParam.getValue(), JSON.toJSONString(map)) ;
		}
		Map res = JSON.parseObject(result);
		if ("00".equals(res.get("code"))){
			//成功后清除换车前的车载订单
			CarOrderVo carOrderVo = new CarOrderVo(carRepair.getOrderId()) ;
			PushCommonBean pushCommonBean = new PushCommonBean(
					"server_push_car_delete_order", "1","" ,carOrderVo);
			List<String> cidListBefore = PushClient.queryClientId(carRepair.getLpn());
			for (String lpnCid : cidListBefore) {
				PushClient.push(lpnCid, com.alibaba.fastjson.JSONObject.toJSONString(pushCommonBean));
			}
			response.getWriter().print("ok");
		}else {
			response.getWriter().print("error");
		}
		return null;
	}

	@SystemServiceLog(description="根据订单号查找换车记录")
	@RequestMapping(value = "/get_change_history", method = { RequestMethod.GET , RequestMethod.POST })
	public String getChangeHistory(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		logger.info("根据订单号查找换车记录");
		String orderId = request.getParameter("orderId");
		List <LongRentExchangeCarLog> list = longRentExChangeCarLogService.queryAllHistory(orderId);
		String json = Data2Jsp.Json2Jsp(list, 0);
		response.getWriter().print(json);
		return null;
	}

}
