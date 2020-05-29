package com.iber.portal.dao.dayRent;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.dayRent.DayRentOrderRefundLog;
import com.iber.portal.vo.dayRent.DayRentRefundVo;

public interface DayRentOrderRefundLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentOrderRefundLog record);

    int insertSelective(DayRentOrderRefundLog record);

    DayRentOrderRefundLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentOrderRefundLog record);

    int updateByPrimaryKey(DayRentOrderRefundLog record);
    
	List<DayRentRefundVo> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	DayRentOrderRefundLog queryRefundLogByOrderId(String orderId);
}