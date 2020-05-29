package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingPileOrderPayLog;

public interface ChargingPileOrderPayLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingPileOrderPayLog record);

    int insertSelective(ChargingPileOrderPayLog record);

    ChargingPileOrderPayLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingPileOrderPayLog record);

    int updateByPrimaryKey(ChargingPileOrderPayLog record);

	List<ChargingPileOrderPayLog> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}