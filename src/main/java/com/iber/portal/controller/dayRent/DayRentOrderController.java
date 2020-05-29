package com.iber.portal.controller.dayRent;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iber.portal.util.DateTime;
import com.iber.portal.util.SendSMS;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.Pager;
import com.iber.portal.common.SysConstant;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.dayRent.DayRentOrder;
import com.iber.portal.model.dayRent.DayRentOrderExtend;
import com.iber.portal.model.dayRent.DayRentOrderRefundLog;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.dayRent.DayRentOrderExtendService;
import com.iber.portal.service.dayRent.DayRentOrderRefundLogService;
import com.iber.portal.service.dayRent.DayRentOrderService;
import com.iber.portal.service.network.ParkService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.vo.dayRent.CarOrderBean;
import com.iber.portal.vo.dayRent.DayRentOrderDetailVo;
import com.iber.portal.vo.dayRent.DayRentOrderVo;
import com.iber.portal.vo.dayRent.DayRentRefundVo;


@Controller
public class DayRentOrderController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private DayRentOrderService dayRentOrderService;

	@Autowired
    private CarRunService carRunService;	
	
	@Autowired
    private MemberService memberService;	
	
	@Autowired
    private CarService carService;
	
	@Autowired
    private DayRentOrderExtendService dayRentOrderExtendService ;
	
	@Autowired
    private ParkService parkService ;
	
	@Autowired
    private DayRentOrderRefundLogService dayRentOrderRefundLogService;
	
	@Autowired
    private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private SystemMsgMapper systemMsgMapper;
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	@Autowired
    private SysParamService sysParamService;
	
	/**
	 * @describe 日租订单
	 * 
	 */
	@SystemServiceLog(description="日租订单")
	@RequestMapping(value = "/dayRent_order_page", method = { RequestMethod.GET })
	public String dayRentPrice(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单页面");
		return "dayRent/dayRentOrder";		
	}
	
	/**
	 * @describe 日租订单列表
	 */
	@SystemServiceLog(description="日租订单列表")
	@RequestMapping(value = "/dayRent_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRentPriceList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		String memberName = request.getParameter("memberName");
		//String cityCode = request.getParameter("cityCode");
		String phone = request.getParameter("phone");
		String lpn = request.getParameter("lpn");
		String orderStatus = request.getParameter("orderStatus");
		Map<String, Object> paramMap = new HashMap<String, Object>();

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
		
		paramMap.put("memberName", memberName);
		//paramMap.put("cityCode", cityCode);
		paramMap.put("phone", phone);
		paramMap.put("lpn", lpn);
		paramMap.put("orderStatus", orderStatus);
		
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		
		Pager<DayRentOrderVo> pager = dayRentOrderService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	} 
	
	/**
	 * @describe 获取车辆列表（区域 车型 未预约 ）
	 */
	@SystemServiceLog(description="获取车辆列表")
	@RequestMapping(value = "/car_city_code_model_id", method = { RequestMethod.GET , RequestMethod.POST })
	public String car_city_code_model_id(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单列表");
		response.setContentType("text/html;charset=utf-8");
		String orderId =  request.getParameter("orderId"); //订单号
		DayRentOrder dayRentOrder = dayRentOrderService.queryDayRentOrder(orderId) ;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("modelId",dayRentOrder.getModelId() ) ;
		paramMap.put("cityCode",dayRentOrder.getCityCode() ) ;
		paramMap.put("parkId",dayRentOrder.getAppointmenTakeParkId() ) ;
		
		List<CarRun> data = carRunService.queryCarLpnByStatus(paramMap) ;
		JSONArray json = JSONArray.fromObject(data);
		response.getWriter().print(json.toString());
		return null ;
	} 
	
	/**
	 * 时间差（小时）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDateHour(Date d1, Date d2) {
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		Integer l = (int)Math.ceil((double)(l2 - l1) / 1000/ 60/60);
		return l.intValue();
	}
	
	/**
	 * @describe 设置车牌号
	 */
	@SystemServiceLog(description="设置车牌号")
	@RequestMapping(value = "/save_lpn_order_info", method = { RequestMethod.GET , RequestMethod.POST })
	public String save_lpn_order_info(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单列表");
		response.setContentType("text/html;charset=utf-8");
		String orderId =  request.getParameter("orderId"); //订单号
		String lpn =  request.getParameter("lpn").replace("•", ""); //订单号
		DayRentOrder dayRentOrder = dayRentOrderService.queryDayRentOrder(orderId) ;
		CarRun carRun = carRunService.queryCarRun(lpn) ;
		if(carRun.getStatus().equals(SysConstant.CAR_RUN_STATUS_EMPTY)){
			//绑定车辆时间必须在预约取车时间一小时内
			Date currentDate = new Date() ;
			Date takeCarDate = dayRentOrder.getAppointmenTakeCarTimet() ;
			Integer timeDifference = getDateHour(currentDate,takeCarDate ) ; //超时小时
			System.err.println(timeDifference + "--- 超时小时---");
			if(timeDifference <= 1000) {
				//判断会员是否存在分时订单，如果存在，则不能绑定车辆
				List<TimeShareOrder> timeShareOrderList = timeShareOrderService.queryOrderByMemberId(Integer.parseInt(dayRentOrder.getMemberId())) ;
				if(timeShareOrderList.size() <= 0) {
					//如果为上车状态，不能绑定车辆
					if(dayRentOrder.getOrderStatus().equals(SysConstant.X_DAY_RENT_ORDER_STATUS_ORDERED) ||dayRentOrder.getOrderStatus().equals(SysConstant.X_DAY_RENT_ORDER_STATUS_BOUND)){
						if(dayRentOrder.getLpn() != null) { //将已经绑定车辆取消
							carRun.setStatus(SysConstant.CAR_RUN_STATUS_EMPTY) ;
							carRun.setMemberId(null) ;
							carRunService.updateByPrimaryKey(carRun) ;
						}
						dayRentOrder.setLpn(lpn) ;
						dayRentOrder.setCarBindTime(new Date()) ;
						int len = dayRentOrderService.updateByPrimaryKeySelective(dayRentOrder) ;
						if(len > 0) {
							//修改订单状态
							dayRentOrder.setOrderStatus(SysConstant.X_DAY_RENT_ORDER_STATUS_BOUND) ;
							dayRentOrderService.updateByPrimaryKeySelective(dayRentOrder) ;
							//修改车辆状态
							carRun.setMemberId(Integer.parseInt(dayRentOrder.getMemberId())) ;
							carRun.setStatus(SysConstant.CAR_RUN_STATUS_ORDERED) ;
							carRunService.updateByPrimaryKey(carRun) ;
							
							Member member = memberService.selectByPrimaryKey(Integer.parseInt(dayRentOrder.getMemberId())) ;
							Car car = carService.selectByLpn(lpn) ;
							//给车载推送绑定成功信息
							CarOrderBean carOrderBean = new CarOrderBean(
									orderId,
									member.getIdcard(), member.getFingerPrint(),
									car.geteLpn(),car.getBluetoothNo(),
									dayRentOrder.getCreateTime().getTime()+"", 
									member.getName(),member.getSex(),dayRentOrder.getAppointmenReturnCarTime().getTime()+"");
							PushCommonBean push = new PushCommonBean(SysConstant.SERVER_PUSH_DAY_ORDER,"1","",carOrderBean) ;
							List<String> cidList = PushClient.queryClientId(lpn);
							for (String lpnCid : cidList) {
								PushClient.push(lpnCid,JSONObject.fromObject(push));
							}
							
							//网点名称
							String parkName = parkService.selectByPrimaryKey(dayRentOrder.getAppointmenTakeParkId()).getName() ;
							//给会员推送绑定成功信息
							Map<String,Object> map = new HashMap<String, Object>() ;
							map.put("lpn", lpn) ;
							PushCommonBean memberPush = new PushCommonBean(SysConstant.SERVER_PUSH_DAY_ORDER_BOUND_LPN,"1","",map) ;
							List<String> memberCidList = PushClient.queryClientId(member.getPhone());
							
							SystemMsg systemMsg = systemMsgMapper.selectByPrimaryKey(9);
							SystemMsgLog systemMsgLog = new SystemMsgLog();
							systemMsgLog.setMsgType("dayRentCancel");
							systemMsgLog.setCreateTime(new Date());
							systemMsgLog.setMemberId( member.getId());
							systemMsgLog.setMsgTitle(systemMsg.getMsgTitle());
							systemMsgLog.setMsgContent(systemMsg.getMsgContent().replace("{1}",lpn).replace("{2}",parkName ));
							systemMsgLogMapper.insertSelective(systemMsgLog);
							PushCommonBean systemPush = new PushCommonBean("push_server_system_msg_log","1","系统通知消息",systemMsgLog);

							//生成token
							/*String encryptToken = SendSMS.encryptBySalt(member.getPhone());
							Map<String, String> params = new HashMap<String, String>();  
							String param ="{\"telephoneNo\":\""+member.getPhone()+"\",\"ipAddress\":\""+getRemortIP(request)+"\",\"templateId\":\"2655\",\"contentParam\":\""+new String((member.getName()+"|"+lpn+"|"+parkName).getBytes("utf-8"),"ISO-8859-1")+"\",\"token\":\""+encryptToken+"\"}";
							params.put("msgContentJson", param); 
							HttpsClientUtil.post(sysParamService.selectByKey("send_sms_url").getValue()+"",params) ;*/
							SendSMS.send(member.getPhone(),"",2655,member.getName()+"|"+lpn+"|"+parkName);

							for (String phoneCid : memberCidList) {
								PushClient.push(phoneCid,JSONObject.fromObject(memberPush));
								
								JsonConfig jsonConfig = new JsonConfig();
								jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
								PushClient.push(phoneCid, net.sf.json.JSONObject.fromObject(systemPush, jsonConfig));
							}
							
							response.getWriter().print("succ");
						}else{
							response.getWriter().print("fail");
						}
					}else{
						response.getWriter().print("error");
					} 
				}else{
					response.getWriter().print("timeShareOrderError");
				}
			}else{
				response.getWriter().print("timeError");
			}
		}else{
			response.getWriter().print("carError");
		}
		
		return null ;
	} 
	
	/**
	 * @describe 日租订单详情
	 */
	@SystemServiceLog(description="日租订单详情")
	@RequestMapping(value = "/day_rent_order_detail", method = { RequestMethod.GET , RequestMethod.POST })
	public String day_rent_order_detail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info(" 日租订单详情");
		response.setContentType("text/html;charset=utf-8");
		DayRentOrderDetailVo dayRentOrderDetailVo = new DayRentOrderDetailVo() ;
		Integer orderTotalPayMoney = 0 ;
		String orderId =  request.getParameter("orderId"); //订单号
		DayRentOrder dayRentOrder = dayRentOrderService.queryDayRentOrder(orderId) ; //日租基本信息
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		dayRentOrderDetailVo.setAppointmenTakeCarTime(dayRentOrder.getAppointmenTakeCarTimet()==null?"":sdf.format(dayRentOrder.getAppointmenTakeCarTimet())) ; //预约取车时间
		dayRentOrderDetailVo.setAppointmenReturnCarTime(dayRentOrder.getAppointmenReturnCarTime()==null?"":sdf.format(dayRentOrder.getAppointmenReturnCarTime())) ; //预约还车时间
		dayRentOrderDetailVo.setAppointmenTakeCarParkAddress(dayRentOrder.getAppointmenTakeParkId()==null?"":parkService.selectByPrimaryKey(dayRentOrder.getAppointmenTakeParkId()).getName()) ; // 预约取车网点
		dayRentOrderDetailVo.setAppointmenReturnCarParkAddress(dayRentOrder.getAppointmenReturnCarParkId()==null?"":parkService.selectByPrimaryKey(dayRentOrder.getAppointmenReturnCarParkId()).getName()) ; //预约还车网点
		
		dayRentOrderDetailVo.setActualTakeCarTime(dayRentOrder.getActualTakenCarTime()==null?"":sdf.format(dayRentOrder.getActualTakenCarTime())) ; //实际取车时间
		dayRentOrderDetailVo.setActualTakeCarParkAddress(dayRentOrder.getActualTakenCarParkId()==null?"":parkService.selectByPrimaryKey(dayRentOrder.getActualTakenCarParkId()).getName()) ; //时间取车网点
		
		dayRentOrderDetailVo.setActualReturnCarTime(dayRentOrder.getActualReturnCarTime()==null?"":sdf.format(dayRentOrder.getActualReturnCarTime()));//实际还车时间
		dayRentOrderDetailVo.setActualReturnCarParkAddress(dayRentOrder.getActualReturnCarParkId()==null?"":parkService.selectByPrimaryKey(dayRentOrder.getActualReturnCarParkId()).getName()) ; //时间还车网点
		
		dayRentOrderDetailVo.setDelayReturnCarTime(dayRentOrder.getDelayReturnCarTime()==null?"":sdf.format(dayRentOrder.getDelayReturnCarTime())) ; //延期预约还车时间 
		// 日租 订单扩展
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("extendType", SysConstant.DAY_RENT_TYPE_DAY_RENT);
		List<DayRentOrderExtend> dayRentOrderExtends = dayRentOrderExtendService.queryDayRentOrderExtendByOrderIdType(paramMap);
		if(dayRentOrderExtends.size() > 0){
			DayRentOrderExtend dayRentOrderExtend = dayRentOrderExtends.get(0) ;
			
			dayRentOrderDetailVo.setAppointmenOrderMoney(moneyFormat(dayRentOrderExtend.getOrderMoney())) ; // 订单金额
			dayRentOrderDetailVo.setAppointmenCarRentMoney(moneyFormat(dayRentOrderExtend.getRentCarMoney())) ; //车辆租赁费
			if(dayRentOrderExtend.getCoupon() != null){
				dayRentOrderDetailVo.setAppointmenCouponBalance(dayRentOrderExtend.getCoupon()+"（"+moneyFormat(dayRentOrderExtend.getCouponBalance())+"）") ; //优惠券编号（面值）
			}else{
				dayRentOrderDetailVo.setAppointmenCouponBalance("无") ; //优惠券编号（面值）
			}
			dayRentOrderDetailVo.setAppointmenInsuranceMoney(moneyFormat(dayRentOrderExtend.getInsuranceMoney())) ; //基本保险费
			dayRentOrderDetailVo.setAppointmenDayRentTime((dayRentOrderExtend.getRentTimeout()/24)+"") ; //预约租期
			dayRentOrderDetailVo.setAppointmenFreeMoney(moneyFormat(dayRentOrderExtend.getFreeMoney())) ;//优惠金额
			dayRentOrderDetailVo.setAppointmenProcedureMoney(moneyFormat(dayRentOrderExtend.getProcedureMoney()));//手续费
			dayRentOrderDetailVo.setAppointmenPayMoney(moneyFormat(dayRentOrderExtend.getPayMoney())) ; //支付金额
			dayRentOrderDetailVo.setAppointmenRemoteMoney(moneyFormat(dayRentOrderExtend.getRemoteMoney())) ;//异地还车费
			dayRentOrderDetailVo.setAppointmenPayType(payTypeName(dayRentOrderExtend.getPayType())) ; //支付方式
			dayRentOrderDetailVo.setAppointmenFreeCompensateMoney(moneyFormat(dayRentOrderExtend.getFreeCompensateMoney())) ; //不计免赔
			orderTotalPayMoney += dayRentOrderExtend.getPayMoney()==null?0:dayRentOrderExtend.getPayMoney() ;
		}
		//订单 续租扩展
		paramMap.put("extendType", SysConstant.DAY_RENT_TYPE_CONTINUE_RENT);
		List<DayRentOrderExtend> continueRentOrderExtends = dayRentOrderExtendService.queryDayRentOrderExtendByOrderIdType(paramMap);
		if(continueRentOrderExtends.size() > 0) {
			DayRentOrderExtend continueRentOrderExtend = continueRentOrderExtends.get(0) ;
			dayRentOrderDetailVo.setDelayReturnCarOrderTime(continueRentOrderExtend.getCreateTime()==null?"":sdf.format(continueRentOrderExtend.getCreateTime())) ; //延期还车下单时间
			dayRentOrderDetailVo.setDelayCarRentMoney(moneyFormat(continueRentOrderExtend.getRentCarMoney())) ; //车辆租赁费
			dayRentOrderDetailVo.setDelayDayRentTime((continueRentOrderExtend.getRentTimeout()/24)+"") ; //延期天数
			dayRentOrderDetailVo.setDelayInsuranceMoney(moneyFormat(continueRentOrderExtend.getInsuranceMoney())) ; //基本保险费
			dayRentOrderDetailVo.setDelayFreeCompensateMoney(moneyFormat(continueRentOrderExtend.getFreeCompensateMoney())) ; //不计免赔
			dayRentOrderDetailVo.setDelayPayMoney(moneyFormat(continueRentOrderExtend.getPayMoney())) ; //延期支付金额
			dayRentOrderDetailVo.setDelayPayType(payTypeName(continueRentOrderExtend.getPayType())) ; //支付方式
			
			dayRentOrderDetailVo.setDelayOrderMoney(moneyFormat(continueRentOrderExtend.getOrderMoney())) ; //延期订单金额
			
			orderTotalPayMoney += continueRentOrderExtend.getPayMoney() ;
		}
		
		//订单 超时扩展
		paramMap.put("extendType", SysConstant.DAY_RENT_TYPE_TIMEOUT_RENT);
		List<DayRentOrderExtend> timeoutRentOrderExtends = dayRentOrderExtendService.queryDayRentOrderExtendByOrderIdType(paramMap);
		if(timeoutRentOrderExtends.size() > 0) {
			DayRentOrderExtend timeoutRentOrderExtend = timeoutRentOrderExtends.get(0) ;
			dayRentOrderDetailVo.setTimeoutDayTime(timeoutRentOrderExtend.getRentTimeout()+"") ; //超时时间
			dayRentOrderDetailVo.setTimeoutPayMoney(moneyFormat(timeoutRentOrderExtend.getRentCarMoney())) ; //超时支付金额
			dayRentOrderDetailVo.setTimeoutRemoteMoney(moneyFormat(timeoutRentOrderExtend.getRemoteMoney())) ; //异地还车支付金额
			dayRentOrderDetailVo.setTimeoutPayType(payTypeName(timeoutRentOrderExtend.getPayType())) ; // 支付方式
			
			orderTotalPayMoney += timeoutRentOrderExtend.getPayMoney()==null?0:timeoutRentOrderExtend.getPayMoney() ;
		}
		
		dayRentOrderDetailVo.setOrderTotalPayMoney(moneyFormat(orderTotalPayMoney)) ; //支付总额
		
		response.getWriter().print(JSONObject.fromObject(dayRentOrderDetailVo));
		return null ;
	}
	
	public static String payTypeName(String payType){
		String payTypeName = "" ;
		if(payType!= null){
			if(payType.equals(SysConstant.BALANCE_PAY_TYPE)){
				payTypeName = "余额" ;
			}else if(payType.equals(SysConstant.ALI_PAY_TYPE)){
				payTypeName = "支付宝" ;
			}else if(payType.equals(SysConstant.CAIFU_PAY_TYPE)){
				payTypeName = "财付通" ;
			}else if(payType.equals(SysConstant.WEIXIN_PAY_TYPE)){
				payTypeName = "微信" ;
			}else if(payType.equals(SysConstant.YINLIAN_PAY_TYPE)){
				payTypeName = "银联" ;
			}else if(payType.equals(SysConstant.APPLE_PAY_TYPE)){
				payTypeName = "苹果支付" ;
			}
		}else{
			payTypeName = "未支付" ; //未支付
		}
		return payTypeName ;
	}
	
	public static String moneyFormat(Integer money){
		String result = "0.00" ;
		if(money != null && money ==0){
			result =  "" ;
		}else{
			result =  money ==null?"":new DecimalFormat("#.00").format((double) money/100) ;
		}
		if(result.indexOf('.') == 0) {
			result = "0"+result ;
		}
		return result ;
	}
	/**
	 * 日租订单退款页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="日租订单退款页面")
	@RequestMapping(value = "/dayRent_order_refund", method = { RequestMethod.GET })
	public String dayRent_order_refund(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单退款页面");
		return "dayRent/dayRentRefund";		
	}
	
	/**
	 * @describe 日租订单退款列表
	 */
	@SystemServiceLog(description="日租订单退款列表")
	@RequestMapping(value = "/dayRent_order_refund_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRent_order_refund_list(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单退款列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		
		String memberName = request.getParameter("memberName");
		//String cityCode = request.getParameter("cityCode");
		String phone = request.getParameter("phone");
		String lpn = request.getParameter("lpn");
		Map<String, Object> paramMap = new HashMap<String, Object>();

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
		
		paramMap.put("memberName", memberName);
		paramMap.put("phone", phone);
		paramMap.put("lpn", lpn);
		
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		
		Pager<DayRentRefundVo> pager = dayRentOrderRefundLogService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	} 
	
	/**
	 * @describe 日租订单退款
	 */
	@SystemServiceLog(description="日租订单退款")
	@RequestMapping(value = "/dayRent_order_platForm_refund", method = { RequestMethod.GET , RequestMethod.POST })
	public String dayRent_order_platForm_refund(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("日租订单退款");
		response.setContentType("text/html;charset=utf-8");
		String orderId = request.getParameter("orderId");
		DayRentOrderRefundLog dayRentOrderRefundLog = dayRentOrderRefundLogService.queryRefundLogByOrderId(orderId);
		String msg = dayRentOrderRefundLogService.RentOrderRefundLog(dayRentOrderRefundLog);
		response.getWriter().print(msg);
		return null;	
	} 
}
