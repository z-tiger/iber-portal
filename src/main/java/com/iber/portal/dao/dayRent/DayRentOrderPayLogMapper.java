package com.iber.portal.dao.dayRent;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.dayRent.DayRentOrderPayLog;

public interface DayRentOrderPayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DayRentOrderPayLog record);

    int insertSelective(DayRentOrderPayLog record);

    DayRentOrderPayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DayRentOrderPayLog record);

    int updateByPrimaryKey(DayRentOrderPayLog record);

	List<DayRentOrderPayLog> queryDayRentOrderPayLogByOrderId(
			Map<String, Object> payLogMap);

	List<DayRentOrderPayLog> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	List<DayRentOrderPayLog> getAllExcel(Map<String, Object> paramMap);
}