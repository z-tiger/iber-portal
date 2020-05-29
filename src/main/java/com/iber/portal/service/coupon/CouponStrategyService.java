package com.iber.portal.service.coupon;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.coupon.CouponStrategyMapper;
import com.iber.portal.model.base.CounponList;
import com.iber.portal.model.base.CounponName;
import com.iber.portal.model.coupon.CouponStrategy;

/**
 * 
 * <br>
 * <b>功能：</b>CouponDeployService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("couponDeployService")
public class CouponStrategyService{

	private final static Logger log= Logger.getLogger(CouponStrategyService.class);
	
	@Autowired
    private CouponStrategyMapper  dao;
		
	public List<CounponName> selectCouponName(String cityCode){
		return dao.selectCouponName(cityCode);
	}
	public Pager<CounponList> queryPageList(Map<String, Object> paramMap){
		List<CounponList> listObj = dao.queryPageList(paramMap);
		Pager<CounponList> pager = new Pager<CounponList>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));		
		return pager;
	}
	public int getAllNum(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return dao.getAllNum(paramMap) ;
	}
	public int insert(CouponStrategy record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(CouponStrategy record){
		return dao.updateByPrimaryKey(record) ;
	}

	
}
