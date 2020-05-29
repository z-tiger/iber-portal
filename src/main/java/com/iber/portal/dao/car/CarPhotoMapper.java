package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.car.CarPhoto;
import com.iber.portal.vo.car.CarPhotoVo;

public interface CarPhotoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarPhoto record);

    int insertSelective(CarPhoto record);

    CarPhoto selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarPhoto record);

    int updateByPrimaryKey(CarPhoto record);
    
    List<CarPhotoVo> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
}