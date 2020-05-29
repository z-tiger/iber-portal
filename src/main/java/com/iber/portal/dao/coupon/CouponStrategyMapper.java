package com.iber.portal.dao.coupon;
import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.CounponList;
import com.iber.portal.model.base.CounponName;
import com.iber.portal.model.coupon.CouponStrategy;
import com.iber.portal.model.order.RentDetailOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * <br>
 * <b>功能：</b>CouponItemDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface CouponStrategyMapper {
	List<CounponName> selectCouponName(@Param("cityCode") String cityCode);
 
	List<CounponList> queryPageList(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
	
	int insert(CouponStrategy record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(CouponStrategy record);

	int getMaxOfMin(@Param("itemCode") String itemCode,@Param("chargeMoney") Integer chargeMoney,@Param("cityCode") String cityCode);

	List<CouponStrategy> getStrategyBetweenMinMax(@Param("itemCode") String itemCode, 
			@Param("money") Integer money, @Param("hasMaxLimit") Boolean hasMaxLimit,@Param("cityCode") String cityCode);
}
