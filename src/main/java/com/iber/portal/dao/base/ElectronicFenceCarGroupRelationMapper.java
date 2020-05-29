package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.ElectronicFenceCarGroupRelation;
import com.iber.portal.vo.fence.ElectronicFenceCarGroupRelationVo;

public interface ElectronicFenceCarGroupRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ElectronicFenceCarGroupRelation record);

    int insertSelective(ElectronicFenceCarGroupRelation record);

    ElectronicFenceCarGroupRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ElectronicFenceCarGroupRelation record);

    int updateByPrimaryKey(ElectronicFenceCarGroupRelation record);
    
    List<ElectronicFenceCarGroupRelationVo> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
    
    List<ElectronicFenceCarGroupRelation> selectAllByGroupIdAndFenceId(Map<String, Object> map);
    
    int deleteByGroupId(Map<String, String> map) ;
    
    List<ElectronicFenceCarGroupRelation> selectAllByGroupId(Map<String, Object> map);
}