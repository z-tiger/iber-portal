package com.iber.portal.dao.base;

import java.util.List;

import com.iber.portal.model.base.SystemMsg;

public interface SystemMsgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemMsg record);

    int insertSelective(SystemMsg record);

    SystemMsg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemMsg record);

    int updateByPrimaryKey(SystemMsg record);
    
    List<SystemMsg> selectAllSystemMsg();
}