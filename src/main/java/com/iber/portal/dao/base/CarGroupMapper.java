package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.CarGroup;
import com.iber.portal.model.base.Park;
import com.iber.portal.vo.car.CarGroupVo;

public interface CarGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarGroup record);

    int insertSelective(CarGroup record);

    CarGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarGroup record);

    int updateByPrimaryKey(CarGroup record);
    
    List<CarGroupVo> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
    
    List<CarGroup> selectAllNotPage();
    
    List<CarGroup> selectGroupByCityCode(@Param("cityCode") String cityCode) ;

    /**
     * 根据cityCode和车组名，查询车组
     * @param cityCode
     * @param groupName
     * @return
     * @author ouxx
     * @date 2016-9-26 下午2:53:30
     */
	List<CarGroup> selectGroupByCityCodeAndGroupName(@Param("cityCode") String cityCode, @Param("groupName") String groupName);
    
}