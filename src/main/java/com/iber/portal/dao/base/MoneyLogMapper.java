package com.iber.portal.dao.base;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.MoneyLog;


public interface MoneyLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MoneyLog record);

    int insertSelective(MoneyLog record);

    MoneyLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MoneyLog record);

    int updateByPrimaryKey(MoneyLog record);
    
    int insertMoneyLogBatch(@Param("logList") List<MoneyLog> logList);
    
}