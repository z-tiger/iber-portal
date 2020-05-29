package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingPileOrder;

public interface ChargingPileOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingPileOrder record);

    int insertSelective(ChargingPileOrder record);

    ChargingPileOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingPileOrder record);

    int updateByPrimaryKey(ChargingPileOrder record);

	List<ChargingPileOrder> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}