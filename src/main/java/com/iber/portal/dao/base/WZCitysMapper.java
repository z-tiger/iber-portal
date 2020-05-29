package com.iber.portal.dao.base;

import java.util.List;

import com.iber.portal.model.base.WZCitys;

public interface WZCitysMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WZCitys record);

    int insertSelective(WZCitys record);

    WZCitys selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WZCitys record);

    int updateByPrimaryKey(WZCitys record);
    
    List<WZCitys> selectAll();
    
    int deleteAll();
    
    WZCitys selectByCityCode(String cityCode);
}