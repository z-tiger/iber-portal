package com.iber.portal.dao.base;

import com.iber.portal.model.base.WZCitysQuery;

public interface WZCitysQueryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WZCitysQuery record);

    int insertSelective(WZCitysQuery record);

    WZCitysQuery selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WZCitysQuery record);

    int updateByPrimaryKey(WZCitysQuery record);
    
    WZCitysQuery  queryWZCitysQueryByCityCode(String cityCode);
}