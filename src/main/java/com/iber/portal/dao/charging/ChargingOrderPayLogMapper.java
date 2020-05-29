package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingOrderPayLog;



public interface ChargingOrderPayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingOrderPayLog record);

    int insertSelective(ChargingOrderPayLog record);

    ChargingOrderPayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingOrderPayLog record);

    int updateByPrimaryKey(ChargingOrderPayLog record);

	List<ChargingOrderPayLog> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}