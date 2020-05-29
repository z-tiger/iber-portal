package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingPileCar;

public interface ChargingPileCarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingPileCar record);

    int insertSelective(ChargingPileCar record);

    ChargingPileCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingPileCar record);

    int updateByPrimaryKey(ChargingPileCar record);

	List<ChargingPileCar> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	int deleteByTypeIdAndBrandId(Map<String, Object> paramMap);
}