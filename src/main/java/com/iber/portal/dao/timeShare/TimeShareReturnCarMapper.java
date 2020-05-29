package com.iber.portal.dao.timeShare;

import com.iber.portal.model.timeShare.TimeShareReturnCar;

public interface TimeShareReturnCarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeShareReturnCar record);

    int insertSelective(TimeShareReturnCar record);

    TimeShareReturnCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeShareReturnCar record);

    int updateByPrimaryKey(TimeShareReturnCar record);
}