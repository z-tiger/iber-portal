package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.CarGroupRelation;

public interface CarGroupRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarGroupRelation record);

    int insertSelective(CarGroupRelation record);

    CarGroupRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarGroupRelation record);

    int updateByPrimaryKey(CarGroupRelation record);
    
    List<CarGroupRelation> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
    
    List<CarGroupRelation> selectLpnByGroupIdAndLpn(Map<String,Object> map);
    
    /**
     * 查询表中lpn和groupId为传参值的记录数
     * @param groupId
     * @param lpn
     * @return
     * @author ouxx
     * @date 2016-9-24 上午11:40:06
     */
    int getCountByGroupIdAndLpn(@Param("groupId") Integer groupId, @Param("lpn") String lpn);
}