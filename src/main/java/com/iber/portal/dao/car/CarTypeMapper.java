package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.car.CarType;

public interface CarTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarType record);

    int insertSelective(CarType record);

    CarType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarType record);

    int updateByPrimaryKey(CarType record);

    List<CarType> selectAll(Map<String, Object> map);

    int selectAllRecords(Map<String, Object> map);

    List<CarType> selectAllNotPage();

    CarType selectSomeColumn(Integer id);

    /**
     * 查找汽车品牌列表
     * @return
     */
    List<CarType> selectCarBrandList();


}