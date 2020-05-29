package com.iber.portal.dao.dispatch;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.dispatch.Dispatcher;
import com.iber.portal.model.dispatch.DispatcherTask;
import com.iber.portal.model.sys.SysDic;

public interface DispatcherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dispatcher record);

    int insertSelective(Dispatcher record);

    Dispatcher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dispatcher record);

    int updateByPrimaryKey(Dispatcher record);

    List<Dispatcher> selectByDispatcherType(Map<String, Object> map) ;

    List<Dispatcher> selectByDispatcherPageList(Map<String, Object> map) ;
    
    int selectByDispatcherPageListRecords(Map<String, Object> map) ;

    Dispatcher selectByPrimaryId(Integer id);

}