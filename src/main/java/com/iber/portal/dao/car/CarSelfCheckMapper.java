package com.iber.portal.dao.car;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.car.CarSelfCheck;

public interface CarSelfCheckMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(CarSelfCheck record);

    int insertSelective(CarSelfCheck record);

    CarSelfCheck selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarSelfCheck record);

    int updateByPrimaryKey(CarSelfCheck record);
    
    List<CarSelfCheck> selectAll(Map<String, Object> map);
    
    int selectCount(Map<String, Object> map);
    
    String selectHandleStatus(int id);
    
    List<String> selectOrderList(List<Integer> idList);
    
    int batchUpdateHandleStatus(Map map);
    
    List<Boolean> selecthandleStatus(List<Integer> idList);
   
    
    //int updateByIdHandleUser(List<Integer> idList,@Param("name") String name);
}