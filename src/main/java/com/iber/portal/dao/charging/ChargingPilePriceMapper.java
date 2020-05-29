package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingPilePrice;

public interface ChargingPilePriceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingPilePrice record);

    int insertSelective(ChargingPilePrice record);

    ChargingPilePrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingPilePrice record);

    int updateByPrimaryKey(ChargingPilePrice record);

	List<ChargingPilePrice> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}