package com.iber.portal.dao.coupon;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.coupon.CouponLog;
/**
 * 
 * <br>
 * <b>功能：</b>XCouponLogDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface CouponLogMapper {
	
	int insert(CouponLog record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(CouponLog record);
	
	int updateByPrimaryKeySelective(CouponLog record);
	
	CouponLog selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<CouponLog> queryPageList(Map<String, Object> paramMap);
	int queryByBatchNo(String batchNo);
	
}
