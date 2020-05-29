package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.vo.systemMsg.SystemMsgLogVo;
import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.SystemMsgLog;

public interface SystemMsgLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemMsgLog record);

    int insertSelective(SystemMsgLog record);

    SystemMsgLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemMsgLog record);

    int updateByPrimaryKey(SystemMsgLog record);
    
    int insertSystemMsgLogBatch(@Param("logList") List<SystemMsgLog> logList);

    List<Map<String,Object>> selectAllSystemMsgByHand(Map<String, Object> map);

    Integer selecAllRecords(Map<String, Object> map);

    void insertBatch(List<SystemMsgLogVo> list);
}