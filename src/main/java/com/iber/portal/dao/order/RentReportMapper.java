/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.dao.order;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.vo.order.RentReportVo;

/**
 * 租赁收入报表
 * @author ouxx
 * @since 2016-12-19 下午3:15:43
 *
 */
public interface RentReportMapper {

	List<RentReportVo> queryRentReport(@Param("cityCode") String cityCode,
			@Param("name") String name, @Param("lpn") String lpn, 
			@Param("begin") Timestamp begin, @Param("end") Timestamp end,
			@Param("from") Integer from, @Param("to") Integer to, @Param("invoiceStatus")String invoiceStatus,@Param("orderType")String orderType);
	
	Integer getRentReportRecords(@Param("cityCode") String cityCode,
			@Param("name") String name, @Param("lpn") String lpn, 
			@Param("begin") Timestamp begin, @Param("end") Timestamp end, @Param("invoiceStatus")String invoiceStatus,@Param("orderType")String orderType);
	
	
	/********计算合计。本日、本月、本年的*************/
	RentReportVo getTodayAmount(@Param("cityCode") String cityCode,
			@Param("name") String name, @Param("lpn") String lpn,@Param("orderType")String orderType);
	
	RentReportVo getThisMonthAmount(@Param("cityCode") String cityCode,
			@Param("name") String name, @Param("lpn") String lpn,@Param("orderType")String orderType);
	
	RentReportVo getThisYearAmount(@Param("cityCode") String cityCode,
			@Param("name") String name, @Param("lpn") String lpn , @Param("invoiceStatus")String invoiceStatus,@Param("orderType")String orderType);
	
	// 合计
	RentReportVo getRentTotal(@Param("cityCode") String cityCode,
			@Param("name") String name, @Param("lpn") String lpn, 
			@Param("begin") Timestamp begin, @Param("end") Timestamp end, @Param("invoiceStatus")String invoiceStatus,@Param("orderType")String orderType);
}
