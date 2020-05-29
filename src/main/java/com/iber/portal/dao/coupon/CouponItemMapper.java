package com.iber.portal.dao.coupon;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.coupon.CouponItem;
/**
 * 
 * <br>
 * <b>功能：</b>CouponItemDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface CouponItemMapper {
	
	int updateByCouponId(Map<String, Object> map);
	int insertItem(Map<String, Object> map);
	int insert(CouponItem record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(CouponItem record);
	
	int updateByPrimaryKeySelective(CouponItem record);
	
	CouponItem selectByPrimaryKey(Integer id);
	//根据code查询对象
	CouponItem selectByItemCode(@Param("itemCode") String itemCode,@Param("cityCode")String cityCode);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<CouponItem> queryPageList(Map<String, Object> paramMap);
	int queryByItemCode(String itemCode);
	List<CouponItem> queryItemByCode(@Param("itemCode")String activityName,@Param("cityCode") String cityCode);
	/**
	 * 根据itemCode以及活动创建时间查询优惠券
	 * @param activityName
	 * @param createTime
	 * @return
	 */
	CouponItem selectByItmeCodeAndCreateTime(@Param("activityName")String activityName ,@Param("createTime")Date createTime);
	
	List<CouponItem> selectAll();
	/**
	 * 查询开启对优惠券配置
	 * @param itemCode
	 * @param cityCode
	 * @return
	 */
	List<CouponItem> queryValidItemByCode(@Param("itemCode") String itemCode, @Param("cityCode")String cityCode);
}
