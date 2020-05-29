package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.car.CarSelfCheckItem;

public interface CarSelfCheckItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CarSelfCheckItem record);

    int insertSelective(CarSelfCheckItem record);

    CarSelfCheckItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarSelfCheckItem record);

    int updateByPrimaryKey(CarSelfCheckItem record);
    
    List<CarSelfCheckItem> selectAll(Map<String, Object> map);
    
    int selectCount(Map<String, Object> map);
}