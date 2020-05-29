package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingPileType;

public interface ChargingPileTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingPileType record);

    int insertSelective(ChargingPileType record);

    ChargingPileType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingPileType record);

    int updateByPrimaryKey(ChargingPileType record);

	List<ChargingPileType> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}