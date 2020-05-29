package com.iber.portal.controller.timeShare;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.timeShare.MoneyCalculateService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.vo.timeShare.TimeShareOrderVo;

@Controller
public class TimeShareOrderController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MoneyCalculateService moneyCalculateService;
	
	/**
	 * @describe 当前订单页面
	 * 
	 */
	@SystemServiceLog(description="当前订单页面")
	@RequestMapping(value = "/current_order", method = { RequestMethod.GET })
	public String cuttentOrder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("当前订单页面跳转") ;
		return "timeShare/orderlist";		
	}
	
	/**
	 * @describe 按钮列表
	 */
	@SystemServiceLog(description="按钮列表")
	@RequestMapping(value = "/timeShare_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String cuttentOrderList(String memberName,Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int p = (page == null) ? 1 : page;
		int r = (rows == null) ? 100 : rows;
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);	
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> record = new HashMap<String, Object>() ;
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		record.put("cityCode", null);
	         	}else{
	         		record.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
      	    record.put("cityCode", user.getCityCode());
       }
		record.put("memberName", memberName) ;
       if(!StringUtils.isBlank(request.getParameter("lpn"))){
       	record.put("lpn", request.getParameter("lpn"));
       }
        if (StringUtils.isNotBlank(request.getParameter("isEnterpriseUseCar"))) {
            record.put("isEnterpriseUseCar", request.getParameter("isEnterpriseUseCar"));
        }
       if(!StringUtils.isBlank(request.getParameter("phoneNumber"))){
          	record.put("phoneNumber", request.getParameter("phoneNumber"));
          }
       if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
       	String startTime = request.getParameter("queryDateFrom");
       	record.put("queryDateFrom", startTime);
       }
       if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
       	String endTime = request.getParameter("queryDateTo");
       	record.put("queryDateTo", endTime);
       }
       	record.put("isLongOrder", request.getParameter("isLongOrder"));
       	record.put("isLockCar", request.getParameter("isLockCar"));
        record.put("offset", pageInfo.get("first_page"));
		record.put("rows", pageInfo.get("page_size")); 
		Pager<TimeShareOrderVo> pager = timeShareOrderService.getPagerAll(record);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	} 

	@SystemServiceLog(description="当前订单数据导出")
	@RequestMapping(value = "/export_timeshareOrder_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String isEnterpriseUseCar,String lpn,String queryDateFrom,String queryDateTo,
			String memberName,String isLongOrder,String isLockCar,String phoneNumber,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "timeshareOrderReport" ;
		//列名充电桩编码	
		String columnNames [] = {"订单编号", "会员姓名",  "手机号","订单类型", "预约网点", "车牌号", "预约时间",
				"上车时间","订单状态","所属城市","是否购买商业保险","余额","押金","芝麻信用分","超长订单","是否锁车"};
		
		String keys[] = { "orderId", "memberName","memberPhone", "isEnterpriseUseCar",
				"parkName", "lpn", "orderTime", "beginTime",
				"status","cityName","isFreeCompensate","balance","deposit","score","isLongOrder",
				"isLockCar"};
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
      }else{
    	  map.put("cityCode", user.getCityCode());
      }

        if (StringUtils.isNotBlank(isEnterpriseUseCar)) {
		    map.put("isEnterpriseUseCar",isEnterpriseUseCar);
        }
        map.put("lpn", lpn);
		map.put("queryDateFrom", queryDateFrom);
		map.put("queryDateTo", queryDateTo);
		map.put("memberName", memberName);
		map.put("isLongOrder", isLongOrder);
		map.put("isLockCar", isLockCar);
		map.put("phoneNumber", phoneNumber);
		map.put("offset", null);
		map.put("rows", null);
		List<TimeShareOrderVo> datas = timeShareOrderService.getAll(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "当前订单数据报表");
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
			List<TimeShareOrderVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "orderId", "memberName","memberPhone", 
				"parkName", "lpn", "orderTime", "beginTime",
				"status","cityName","isFreeCompensate","balance","deposit","score","isLongOrder",
				"isLockCar","isEnterpriseUseCar"};
		for (TimeShareOrderVo order : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], order.getOrderId());
			map.put(keys[1], order.getMemberName());
			map.put(keys[2], order.getMemberPhone());
			map.put(keys[3], order.getParkName());
			String lpn = order.getLpn();
			if(lpn != null){
				map.put(keys[4], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[5], order.getOrderTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderTime()):"");
			map.put(keys[6], order.getBeginTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getBeginTime()):"");
			String status = order.getStatus();
			if(status != null){
				if(status.equals("ordered")) map.put(keys[7], "预约状态");
				if(status.equals("useCar")) map.put(keys[7], "用车状态");
				if(status.equals("return")) map.put(keys[7], "还车状态");
			}
			map.put(keys[8], order.getCityName());
			if(order.getIsFreeCompensate() != null){
				map.put(keys[9], order.getIsFreeCompensate().equals("0")?"否":"是");
			}
			map.put(keys[10], order.getBalance()!=null?new DecimalFormat("0.00").format(order.getBalance()/100.0):0.00);
			map.put(keys[11], order.getDeposit()!=null?new DecimalFormat("0.00").format(order.getDeposit()/100.0):0.00);
			map.put(keys[12], order.getScore());
			if(order.getIsLongOrder()!=null){
				map.put(keys[13], order.getIsLongOrder()==0?"否":"是" );
			}
			if(order.getIsLockCar()!=null){
				map.put(keys[14], order.getIsLockCar()==0?"否":"是" );
			}
            if (StringUtils.isNotBlank(order.getIsEnterpriseUseCar())) {
                map.put(keys[15], order.getIsEnterpriseUseCar().equalsIgnoreCase("false") ? "个人用户" : "企业用车");
            }

            list.add(map);
		}
		return list;
	}
	/**
	 * @describe 历史订单页面
	 * 
	 */
	@SystemServiceLog(description="历史订单页面")
	@RequestMapping(value = "/history_order", method = { RequestMethod.GET })
	public String historyOrder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("历史订单页面跳转") ;
		return "timeShare/historyOrderlist";		
	}
	
	/**
	 * @describe 历史订单列表
	 */
	@SystemServiceLog(description="历史订单列表")
	@RequestMapping(value = "/timeShare_history_order_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String historyOrderList(String memberName,int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);	
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> record = new HashMap<String, Object>() ;
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		record.put("cityCode", null);
	         	}else{
	         		record.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
      	    record.put("cityCode", user.getCityCode());
       }
	   if(!StringUtils.isBlank(request.getParameter("phoneNumber"))){
	       	record.put("phoneNumber", request.getParameter("phoneNumber"));
	   }
        if (StringUtils.isNotBlank(request.getParameter("isEnterpriseUseCar"))) {

            record.put("isEnterpriseUseCar", request.getParameter("isEnterpriseUseCar"));
        }
        if (!StringUtils.isBlank(request.getParameter("lpn"))) {
            record.put("lpn", request.getParameter("lpn"));
        }
       if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
       	String startTime = request.getParameter("queryDateFrom");
       	record.put("queryDateFrom", startTime+" 00:00:00");
       }
       if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
       	String endTime = request.getParameter("queryDateTo");
       	record.put("queryDateTo", endTime+" 23:59:59");
       }
	   if(!StringUtils.isBlank(request.getParameter("orderId"))){
    	   String orderId = request.getParameter("orderId");
    	   record.put("orderId", orderId);
       }
	   if(!StringUtils.isBlank(request.getParameter("status"))){ //状态查询
	       
	       	if(request.getParameter("status").equals("noPay")){//未支付
	       		record.put("payStatus", request.getParameter("status")); //查询nopay
	       	}else if(request.getParameter("status").equals("finish")){//已完成
	       		record.put("status", request.getParameter("status"));
	       		record.put("payStatus","finish");
	       	}
	    }
        record.put("memberName", memberName) ;
        record.put("offset", pageInfo.get("first_page"));
		record.put("rows", pageInfo.get("page_size"));
		if (!StringUtils.isBlank(request.getParameter("status"))) {
			if (request.getParameter("status").equals("cancel")) {//如果是已取消订单，则用另外一个方法查询
				Pager<TimeShareOrderVo> pager = timeShareOrderService.queryPageCancelOrder(record);
				response.getWriter().print(Data2Jsp.Json2Jsp(pager));
				return null;
			}
		}
		Pager<TimeShareOrderVo> pager = timeShareOrderService.getPagerHistoryAll(record);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}

	@SystemServiceLog(description="历史订单数据导出")
	@RequestMapping(value = "/export_historyOrder_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<TimeShareOrderVo> exportExecl2(String memberName,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String fileName = "HistoryOrderReport" ;
		//列名充电桩编码	
		String columnNames [] = {"订单编号", "会员姓名",  "手机号","订单类型","个人所付金额", "预约网点", "车牌号", "预约时间",
				"上车时间","还车时间","订单状态","所属城市","还车网点","使用时长（分）","时长花费（元）","使用时长（夜）","行驶里程（公里）","里程花费（元）",
				"是否购买不计免赔","订单消费（元）","优惠券编号","优惠券面值（元）","夜时长优惠（元）","日上限优惠（元）","总优惠金额（元）",
				"日封顶价格（元","支付金额（元）","折扣优惠金额（元）","会员等级折扣"};
		
		String keys[] = { "orderId", "memberName","memberPhone","isEnterpriseUseCar","lastMoney", "parkName", "lpn", "orderTime", "beginTime","endTime",
				"status","cityName","returnParkName","totalMinute","totalMinuteCost","nightTotalMinute","totalMileage","totalMileageCost",
				"isFreeCompensate","totalPayMoney","couponNo","couponBalance","nightMinuteReductionMoney",
				"dayRentReductionPayMoney","reductionPayMoney","consumpVal","payMoney","discountMoney","memberLevelDiscount"};
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> record = new HashMap<String, Object>() ;
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		record.put("cityCode", null);
	         	}else{
	         		record.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
      	    record.put("cityCode", user.getCityCode());
       }
	   if(!StringUtils.isBlank(request.getParameter("phoneNumber"))){
	       	record.put("phoneNumber", request.getParameter("phoneNumber"));
	   }
        if (StringUtils.isNotBlank(request.getParameter("isEnterpriseUseCar"))) {
            record.put("isEnterpriseUseCar", request.getParameter("isEnterpriseUseCar"));
        }
        if (!StringUtils.isBlank(request.getParameter("lpn"))) {
            record.put("lpn", request.getParameter("lpn"));
        }
       if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
       	String startTime = request.getParameter("queryDateFrom");
       	record.put("queryDateFrom", startTime+" 00:00:00");
       }
       if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
       	String endTime = request.getParameter("queryDateTo");
       	record.put("queryDateTo", endTime+" 23:59:59");
       }
	   if(!StringUtils.isBlank(request.getParameter("orderId"))){
    	   String orderId = request.getParameter("orderId");
    	   record.put("orderId", orderId);
       }
	   if(!StringUtils.isBlank(request.getParameter("status"))){ //状态查询
	       
	       	if(request.getParameter("status").equals("noPay")){//未支付
	       		record.put("payStatus", request.getParameter("status")); //查询nopay
	       	}else if(request.getParameter("status").equals("finish")){//已完成
	       		record.put("status", request.getParameter("status"));
	       		record.put("payStatus","finish");
	       	}
	    }
	   	record.put("memberName", memberName) ;
        record.put("offset", null);
		record.put("rows", null);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "历史订单数据报表");
		list.add(sheetNameMap);
		if (!StringUtils.isBlank(request.getParameter("status"))) {
			if (request.getParameter("status").equals("cancel")) {//如果是已取消订单，则用另外一个方法查询
				Pager<TimeShareOrderVo> pager = timeShareOrderService.queryPageCancelOrder(record);
				List<TimeShareOrderVo> datas = pager.getDatas();
				list.addAll(createData3(datas));
			}else{
				Pager<TimeShareOrderVo> pager = timeShareOrderService.getPagerHistoryAll(record);
				List<TimeShareOrderVo> datas = pager.getDatas();
				list.addAll(createData3(datas));
			}
		}else{
			Pager<TimeShareOrderVo> pager = timeShareOrderService.getPagerHistoryAll(record);
			List<TimeShareOrderVo> datas = pager.getDatas();
			list.addAll(createData3(datas));
		}
		
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
	
	private List<Map<String, Object>> createData3(
			List<TimeShareOrderVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "orderId", "memberName","memberPhone", "parkName", "lpn", "orderTime", "beginTime","endTime",
				"status","cityName","returnParkName","totalMinute","totalMinuteCost","nightTotalMinute","totalMileage","totalMileageCost",
				"isFreeCompensate","totalPayMoney","couponNo","couponBalance","nightMinuteReductionMoney",
				"dayRentReductionPayMoney","reductionPayMoney","consumpVal","payMoney","discountMoney","memberLevelDiscount","isEnterpriseUseCar","lastMoney"};
		for (TimeShareOrderVo order : data) {
				map = new HashMap<String, Object>();
				map.put(keys[0], order.getOrderId());
				map.put(keys[1], order.getMemberName());
				map.put(keys[2], order.getMemberPhone());
				map.put(keys[3], order.getParkName());
				String lpn = order.getLpn();
				if (lpn != null) {
					map.put(keys[4], lpn.indexOf("•") < 0 ? lpn.substring(0, 2) + "•" + lpn.substring(2) : lpn);
				}
				map.put(keys[5], order.getOrderTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getOrderTime()) : "");
				map.put(keys[6], order.getBeginTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getBeginTime()) : "");
				map.put(keys[7], order.getEndTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getEndTime()) : "");
				String status = order.getStatus();
				if (status != null) {
					if (status.equals("cancel")) map.put(keys[8], "已取消");
					if (status.equals("finish")) {
						if (order.getPayStatus() != null) {
							if (order.getPayStatus().equals("noPay")) {
								map.put(keys[8], "未支付");
							} else {
								map.put(keys[8], "已完成");
							}
						}
					}
				}
				map.put(keys[9], order.getCityName());
				map.put(keys[10], order.getReturnParkName());
				map.put(keys[11], order.getTotalMinute());
				map.put(keys[12], order.getTotalMinuteCost() != null ? new DecimalFormat("0.00").format(Double.parseDouble(order.getTotalMinuteCost()) / 100.0) : 0.00);
				map.put(keys[13], order.getNightTotalMinute());
				map.put(keys[14], order.getTotalMileage());
				map.put(keys[15], order.getTotalMileageCost() != null ? new DecimalFormat("0.00").format(order.getTotalMileageCost() / 100.0) : 0.00);
				map.put(keys[16], order.getFreeCompensationMoney() != null ? new DecimalFormat("0.00").format(order.getFreeCompensationMoney() / 100.0) : 0.00);
				map.put(keys[17], order.getTotalPayMoney() != null ? new DecimalFormat("0.00").format(order.getTotalPayMoney() / 100.0) : 0.00);
				map.put(keys[18], order.getCouponNo());
				map.put(keys[19], order.getCouponBalance() != null ? new DecimalFormat("0.00").format(order.getCouponBalance() / 100.0) : "0.00");
				map.put(keys[20], order.getNightMinuteReductionMoney() != null ? new DecimalFormat("0.00").format(order.getNightMinuteReductionMoney() / 100.0) : 0.00);
				Double consumpVal = order.getConsumpVal();
				Double totalPayMoney = order.getTotalPayMoney();
				if (consumpVal != null && totalPayMoney > consumpVal && consumpVal != 0.00) {
					Double d1 = order.getFreeCompensationMoney();
					Double payMoney = order.getPayMoney();
					Double couponBalance = order.getCouponBalance();
					if (null == couponBalance) {
						couponBalance = 0.00;
					}
					if (payMoney != null && couponBalance != null) {
						Double d2 = payMoney + couponBalance;
						Double d3 = d2 - d1;
						Double d5 = totalPayMoney - d3;
						if (d5 > 0.00) {
							map.put(keys[21], new DecimalFormat("0.00").format(d5 / 100.0));
						} else {
							map.put(keys[21], "0.00");
						}

					}
				} else {
					map.put(keys[21], "0.00");
				}
				map.put(keys[22], order.getReductionPayMoney() != null ? new DecimalFormat("0.00").format(order.getReductionPayMoney() / 100.0) : 0.00);
				map.put(keys[23], order.getConsumpVal() != null ? new DecimalFormat("0.00").format(order.getConsumpVal() / 100.0) : 0.00);
				map.put(keys[24], order.getPayMoney() != null ? new DecimalFormat("0.00").format(order.getPayMoney() / 100.0) : 0.00);
				map.put(keys[25], order.getDiscountMoney() != null ? new DecimalFormat("0.00").format(order.getDiscountMoney() / 100.0) : 0.00);
				map.put(keys[26], order.getMemberLevelDiscount());
				map.put(keys[27], order.getIsEnterpriseUseCar()== null ? "" : (order.getIsEnterpriseUseCar().equalsIgnoreCase("false") ? "个人约车" : "企业约车"));
				//lastMoney人就单位是
				Integer payMoney = order.getLastMoney();
				map.put(keys[28], (payMoney == null ? 0 : payMoney.doubleValue()) / 100);

				list.add(map);
		}
		return list;
	}
	/**
	 * 
	 * @param memberName
	 * @param lpn
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="历史完成订单")
	@RequestMapping(value = "/timeShare_history_order_list_finish", method = { RequestMethod.GET , RequestMethod.POST })
	public String timeShare_history_order_list_finish(String memberName,String lpn , int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);	
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null ;
		if(!user.getCityCode().equals("00")){
			cityCode = user.getCityCode() ;
		}
		Pager<TimeShareOrder> pager = timeShareOrderService.getHistoryAllFinish(lpn , cityCode,memberName,pageInfo.get("first_page"), pageInfo.get("page_size"));
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	} 
	
	/**
	 * 手动保留订单
	 */
	@SystemServiceLog(description="手动保留订单")
	@RequestMapping(value = "/saveOrder", method = { RequestMethod.GET , RequestMethod.POST })
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String memberId = request.getParameter("memberId");
		String orderId = request.getParameter("orderId");
		List<TimeShareOrder> timeShareOrders = timeShareOrderService.queryOrderByOrderId(orderId);
		if (!timeShareOrders.get(0).getStatus().equals("ordered")) {//如果订单状态不为预约状态
			response.getWriter().print("noOrder");
			return null;
		}else {
			//判读该会员是否是五星会员
			Member member = memberService.selectByPrimaryKey(Integer.parseInt(memberId));
			if (member != null && !member.getLevelCode().equals(5)) {//如果不是五星会员
				response.getWriter().print("notEnoughLevel");
				return null;
			}else{
				int rec = timeShareOrderService.updateIsManualSaveByOrderId(orderId);
				if (rec > 0) {
					response.getWriter().print("succ");
					return null;
				}else{
					response.getWriter().print("fail");
					return null;
				}
			}
		}
		
	}

	/**
	 * 计算当前订单金额
	 */
	@SystemServiceLog(description="计算当前订单金额")
	@RequestMapping(value = "/calculateCurrOrdMoney", method = { RequestMethod.GET , RequestMethod.POST })
	public String calculateCurrOrdMoney(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderId = request.getParameter("orderId");
		Map currOrdMoneyMap = moneyCalculateService.getMoneyByOrderId(orderId);
		Double money = 0.0;
		if(null != currOrdMoneyMap){
			money = ((Integer) currOrdMoneyMap.get("money")).doubleValue() / 100.0;
		}
		DecimalFormat formatter = new DecimalFormat("#0.00");
		response.getWriter().print(formatter.format(money));
		return null;
		
	}
	
}
