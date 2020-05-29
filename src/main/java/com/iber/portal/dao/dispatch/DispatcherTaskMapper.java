package com.iber.portal.dao.dispatch;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.dispatch.DispatcherTask;
import com.iber.portal.model.evaluate.OrderEvaluateLabel;

public interface DispatcherTaskMapper {

    int insert(DispatcherTask record);

    int insertSelective(DispatcherTask record);

    DispatcherTask selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DispatcherTask record);

    int updateByPrimaryKey(DispatcherTask record);

    List<DispatcherTask> selectByDispatcherTaskList(Map<String, Object> map) ;
    
    int selectByDispatcherTaskRecords(Map<String, Object> map) ;

    List<DispatcherTask> selectByPrimaryKeyExcel(Map<String, String> map);

    DispatcherTask selectByPrimaryId(Integer id);

    DispatcherTask selectBySrcAlertId(Integer id);
    

}