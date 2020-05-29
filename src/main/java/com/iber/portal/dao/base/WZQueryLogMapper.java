package com.iber.portal.dao.base;

import com.iber.portal.model.base.WZQueryLog;

public interface WZQueryLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WZQueryLog record);

    int insertSelective(WZQueryLog record);

    WZQueryLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WZQueryLog record);

    int updateByPrimaryKeyWithBLOBs(WZQueryLog record);

    int updateByPrimaryKey(WZQueryLog record);
}