package com.iber.portal.service.coupon;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.coupon.CouponLogMapper;
import com.iber.portal.model.coupon.CouponLog;

/**
 * 
 * <br>
 * <b>功能：</b>XCouponLogService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("CouponLogService")
public class CouponLogService{

	private final static Logger log= Logger.getLogger(CouponLogService.class);
	
	@Autowired
    private CouponLogMapper  dao;

		
	public int insert(CouponLog record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(CouponLog record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(CouponLog record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public CouponLog selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<CouponLog> queryPageList(Map<String, Object> paramMap){
		List<CouponLog> listObj = dao.queryPageList(paramMap);
		Pager<CouponLog> pager = new Pager<CouponLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	public int queryByBatchNo(String batchNo){
		return dao.queryByBatchNo(batchNo);
	}
	
}
