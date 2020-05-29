package com.iber.portal.dao.pile;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.pile.Pile;

public interface PileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pile record);

    int insertSelective(Pile record);

    Pile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pile record);

    int updateByPrimaryKey(Pile record);
    
    List<Pile> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
}