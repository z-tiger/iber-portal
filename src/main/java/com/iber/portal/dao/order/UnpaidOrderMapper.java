/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.vo.order.UnpaidMemberVo;

/**
 * 统计未支付的订单，时租、日租、充电
 * @author ouxx
 * @since 2016-12-27 下午5:03:21
 *
 */
public interface UnpaidOrderMapper {

	/**
	 * 查时租、日租、充电 结束后7天还未支付的会员
	 * @return
	 * @author ouxx
	 * @date 2016-12-30 下午7:04:11
	 */
	List<UnpaidMemberVo> queryMemberWithUnpaidOrder(@Param("days") Integer days);
	
	List<UnpaidMemberVo> queryNoPayMemberByBetwentDay(@Param("startDay") Integer startDay, @Param("endDay") Integer endDay);
}
