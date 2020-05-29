package com.iber.portal.service.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.vo.systemMsg.SystemMsgLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.model.base.SystemMsgLog;

@Service
public class SystemMsgLogService {
	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;
	
	public int deleteByPrimaryKey(Integer id){
		return systemMsgLogMapper.deleteByPrimaryKey(id);
	}
	
	public int insert(SystemMsgLog record){
		return systemMsgLogMapper.insert(record);
	}

	public  int insertSelective(SystemMsgLog record){
		return systemMsgLogMapper.insertSelective(record);
	}

	public  SystemMsgLog selectByPrimaryKey(Integer id){
		return systemMsgLogMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(SystemMsgLog record){
		return systemMsgLogMapper.updateByPrimaryKeySelective(record);
	}
	    

	 public int updateByPrimaryKey(SystemMsgLog record){
		 return systemMsgLogMapper.updateByPrimaryKey(record);
	 }
	 
	 public int insertSystemMsgLogBatch(List<SystemMsgLog> logList){
		 return systemMsgLogMapper.insertSystemMsgLogBatch(logList);
	 }

	public List<Map<String,Object>> selectAllSystemMsgByHand(Map<String, Object> map) {
		return systemMsgLogMapper.selectAllSystemMsgByHand(map);
	}

	public Integer selecAllRecords(Map<String, Object> map) {
		return systemMsgLogMapper.selecAllRecords(map);
	}

    public void insertBatch(List<SystemMsgLogVo> list) {
	 	systemMsgLogMapper.insertBatch(list);
    }
}

