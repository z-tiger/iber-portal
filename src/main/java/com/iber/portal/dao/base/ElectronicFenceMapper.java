package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.ElectronicFence;

public interface ElectronicFenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElectronicFence record);

    int insertSelective(ElectronicFence record);

    ElectronicFence selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElectronicFence record);

    int updateByPrimaryKey(ElectronicFence record);
    
    List<ElectronicFence> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
    
    List<ElectronicFence> selectFenceByCityCode(Map<String,String> map);
}