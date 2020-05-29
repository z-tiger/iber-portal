package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.car.CarLpnCheck;

public interface CarLpnCheckMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarLpnCheck record);

    int insertSelective(CarLpnCheck record);

    CarLpnCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarLpnCheck record);

    int updateByPrimaryKey(CarLpnCheck record);

	List<CarLpnCheck> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}