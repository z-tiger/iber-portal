package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.ElectronicFenceGps;

public interface ElectronicFenceGpsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElectronicFenceGps record);

    int insertSelective(ElectronicFenceGps record);

    ElectronicFenceGps selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElectronicFenceGps record);

    int updateByPrimaryKey(ElectronicFenceGps record);
    
    void deleteFenceGpsByFenceId(Integer fenceId) ;
    
//    List<ElectronicFenceGps> selectAll(Map<String, Object> map);
    
//    int selectAllRecords(Map<String, Object> map);
    
    List<ElectronicFenceGps> selectGpsByFenceId(Integer fenceId) ;
}