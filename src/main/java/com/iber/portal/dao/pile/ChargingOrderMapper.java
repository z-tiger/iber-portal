package com.iber.portal.dao.pile;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.pile.ChargingOrder;

public interface ChargingOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingOrder record);

    int insertSelective(ChargingOrder record);

    ChargingOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingOrder record);

    int updateByPrimaryKey(ChargingOrder record);

	List<ChargingOrder> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

}