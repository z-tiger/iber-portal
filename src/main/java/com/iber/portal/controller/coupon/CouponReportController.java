/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.controller.coupon;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.service.coupon.CouponService;
import com.iber.portal.util.DateTime;
import com.iber.portal.vo.report.CouponReport;

/**
 * 优惠券报表
 * @author ouxx
 * @since 2016-9-29 下午9:56:19
 *
 */
@Controller
//@SystemServiceLog(description="优惠券报表")
//@RequestMapping(value = "/coupon_report")
public class CouponReportController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CouponService couponService;

	private static final String FULL_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final FastDateFormat FULL_DATE_FORMAT = FastDateFormat.getInstance(FULL_DATE_FORMAT_PATTERN);

	/**
	 * 获取time所在月份的最后时间
	 * @param time
	 * @return
	 * @author ouxx
	 * @date 2016-10-8 下午9:32:40
	 */
	private static String getEndOfMonth(String time){
		Calendar now = Calendar.getInstance();
		try {
			String start = time.substring(0, 8) + "01 00:00:00";//截取成 yyyy-MM-01 00:00:00
			Date d = DateUtils.parseDate(start, FULL_DATE_FORMAT_PATTERN);
			now.setTime(d);
			now.add(Calendar.MONTH, 1);//time的下一个月的开始
			now.add(Calendar.SECOND, -1);//减去1秒，则为time的所在月份的最后时间
			
			return  FULL_DATE_FORMAT.format(now);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args){
		String time = FULL_DATE_FORMAT.format(Calendar.getInstance());
		System.out.println(getEndOfMonth(time));
	}
	
	/**
	 * @describe 优惠券报表页面
	 * 
	 */
	@SystemServiceLog(description="优惠券报表页面")
	@RequestMapping(value = "/coupon_report", method = { RequestMethod.GET })
	public String couponReport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("优惠券报表页面");
		return "coupon/couponReport";		
	}
	
	//统计类型
//	private static final String TOTAL = "total";
	private static final String COLLECTED = "collected";
	private static final String INVALID = "invalid";
	private static final String USED = "used";
	
	/**
	 * 根据title分组
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-9-30 下午1:59:48
	 */
	@SystemServiceLog(description="优惠券根据title分组")
	@RequestMapping(value = "/coupon_report_getGroupByTitle", method = { RequestMethod.GET , RequestMethod.POST })
	public String getGroupByTitle(int page, int rows, 
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("优惠券报表--根据title分组");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("title", request.getParameter("title"));
		
		String begin = request.getParameter("begin");
		if (StringUtils.isNotBlank(begin)) {
			paramMap.put("begin", DateTime.getDateTime(begin));
		}
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			paramMap.put("end", DateTime.getDateTime(end));
		}
		
		List<CouponReport> list = couponService.getGroupByTitle(paramMap);
		int totalRecords = couponService.getReportCount(paramMap);
		String json = Data2Jsp.Json2Jsp(list, totalRecords);
		response.getWriter().print(json);
		
		return null;		
	}
	
	/**
	 * 查明细
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author ouxx
	 * @date 2016-10-10 下午5:06:52
	 */
	@SystemServiceLog(description="优惠券总额明细")
	@RequestMapping(value = "/couponDetail", method = { RequestMethod.GET , RequestMethod.POST })
	public String getCouponDetail(int page, int rows, String batchNo,String memberName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("优惠券总额明细");
		response.setContentType("text/html;charset=utf-8");
		
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("title", request.getParameter("title"));
		paramMap.put("batchNo",batchNo);
		paramMap.put("memberName", memberName);
		Integer status = null;
		Integer useStatus = null;
		String type = request.getParameter("type");
		if (StringUtils.isNotBlank(type)) {
			if(type.equals(COLLECTED)){//已领取
				status = 1;
			}else if (type.equals(INVALID)) {//已作废
				status = 2;
			}else if (type.equals(USED)) {//已消费
				useStatus = 1;
			}
		}
		
		if (null != status) {
			paramMap.put("status", status.toString());
		}
		if (null != useStatus) {
			paramMap.put("useStatus", useStatus.toString());
		}
		
		String begin = request.getParameter("begin");
		if (StringUtils.isNotBlank(begin)) {
			paramMap.put("begin", DateTime.getDateTime(begin));
		}
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end)) {
			paramMap.put("end", DateTime.getDateTime(end));
		}
		
		Pager<Coupon> pager = couponService.getAllInTime(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		
		return null;
	}
	
}
