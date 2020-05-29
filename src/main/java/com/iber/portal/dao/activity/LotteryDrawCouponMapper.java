package com.iber.portal.dao.activity;


import com.iber.portal.model.activity.LotteryDrawCoupon;

import java.util.List;
import java.util.Map;

public interface LotteryDrawCouponMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryDrawCoupon record);

    int insertSelective(LotteryDrawCoupon record);

    LotteryDrawCoupon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryDrawCoupon record);

    int updateByPrimaryKey(LotteryDrawCoupon record);

    List<LotteryDrawCoupon> queryCouponStrategy(Map<String,Object> map);

    int selectCount(Map<String,Object> map);
}