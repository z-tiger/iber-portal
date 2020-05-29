package com.iber.portal.dao.sys;

import com.iber.portal.model.sys.SysDicType;

import java.util.List;

public interface SysDicTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDicType record);

    int insertSelective(SysDicType record);

    SysDicType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDicType record);

    int updateByPrimaryKey(SysDicType record);
    
    List<SysDicType> selectDicTypeAll();

    List<SysDicType> querySysDicType();
}