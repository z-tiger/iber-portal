package com.iber.portal.service.coupon;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.coupon.CouponItemMapper;
import com.iber.portal.model.coupon.CouponItem;

/**
 * 
 * <br>
 * <b>功能：</b>CouponItemService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("couponItemService")
public class CouponItemService{

	private final static Logger log= Logger.getLogger(CouponItemService.class);
	
	@Autowired
    private CouponItemMapper  dao;

	public int updateByCouponId(Map<String, Object> map){
		return dao.updateByCouponId(map);
	}
	public int insertItem(Map<String, Object> map){
		return dao.insertItem(map) ;
	}
	public int insert(CouponItem record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(CouponItem record){
		return dao.updateByPrimaryKey(record) ;
	}
	//根据code 查询对象
	public CouponItem selectByItemCode(String code,String cityCode){
		return dao.selectByItemCode(code,cityCode);
	}
	
	public int updateByPrimaryKeySelective(CouponItem record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public CouponItem selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<CouponItem> queryPageList(Map<String, Object> paramMap){
		List<CouponItem> listObj = dao.queryPageList(paramMap);
		Pager<CouponItem> pager = new Pager<CouponItem>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	public int  queryByItemCode(String itemCode){
		return dao.queryByItemCode(itemCode);
	}

	public CouponItem selectByItmeCodeAndCreateTime(String activityName,
			Date createTime) {
		
		return dao.selectByItmeCodeAndCreateTime(activityName, createTime);
	}

	public List<CouponItem> selectAll(){
		return dao.selectAll();
	}
	
}
