/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.service.order;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.order.RentReportMapper;
import com.iber.portal.vo.order.RentReportVo;

/**
 * 租赁收入报表
 * @author ouxx
 * @since 2016-12-19 下午6:06:14
 *
 */
@Service
public class RentReportService {

	@Autowired
	private RentReportMapper rentReportMapper;
	
	public Pager<RentReportVo> queryRentReport(String cityCode,
			String name, String lpn, 
			Timestamp begin, Timestamp end,
			Integer from, Integer to,String invoiceStatus,String orderType){
		
		List<RentReportVo> list = this.rentReportMapper.queryRentReport(cityCode, name, lpn, begin, end, from, to, invoiceStatus,orderType);
		Pager<RentReportVo> pager = new Pager<RentReportVo>();
		pager.setDatas(list);
		Integer cnt = this.rentReportMapper.getRentReportRecords(cityCode, name, lpn, begin, end, invoiceStatus,orderType);
		pager.setTotalCount(cnt);
		return pager;
	}
	
	public List<RentReportVo> queryRentReportList(String cityCode,
			String name, String lpn, 
			Timestamp begin, Timestamp end,String invoiceStatus,String orderType){
		List<RentReportVo> list = this.rentReportMapper.queryRentReport(cityCode, name, lpn, begin, end, null, null,invoiceStatus,orderType);
		return list;
	}
	
	
	public Integer getRentReportRecords(String cityCode,
			String name, String lpn, 
			Timestamp begin, Timestamp end,String invoiceStatus,String orderType){
		return this.rentReportMapper.getRentReportRecords(cityCode, name, lpn, begin, end,invoiceStatus,orderType);
		
	}
	

	/********计算合计。本日、本月、本年的*************/
	public RentReportVo getTodayAmount(String cityCode, String name, String lpn,String orderType){
		return this.rentReportMapper.getTodayAmount(cityCode, name, lpn,orderType);
	}
	
	public RentReportVo getThisMonthAmount(String cityCode, String name, String lpn,String orderType){
		return this.rentReportMapper.getThisMonthAmount(cityCode, name, lpn,orderType);
	}
	
	public RentReportVo getThisYearAmount(String cityCode, String name, String lpn, String invoiceStatus,String orderType){
		return this.rentReportMapper.getThisYearAmount(cityCode, name, lpn, invoiceStatus, orderType);
	}

	public RentReportVo getRentTotal(String cityCode, String name, String lpn,
			Timestamp begin, Timestamp end,String invoiceStatus,String orderType) {
		return this.rentReportMapper.getRentTotal(cityCode, name, lpn,begin,end, invoiceStatus,orderType);
	}
	
}
