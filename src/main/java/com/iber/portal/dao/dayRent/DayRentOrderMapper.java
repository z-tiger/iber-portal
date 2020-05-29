package com.iber.portal.dao.dayRent;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.dayRent.DayRentOrder;
import com.iber.portal.vo.dayRent.DayRentOrderVo;

public interface DayRentOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentOrder record);

    int insertSelective(DayRentOrder record);

    DayRentOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentOrder record);

    int updateByPrimaryKey(DayRentOrder record);
    
	List<DayRentOrderVo> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
	
	DayRentOrder queryDayRentOrder(String orderId);

	List<DayRentOrder> getAllFinish(Map<String, Object> paramMap);

	int getAllFinishNum(Map<String, Object> paramMap);
	
	DayRentOrder queryFinishByOrderId(String orderId);
}