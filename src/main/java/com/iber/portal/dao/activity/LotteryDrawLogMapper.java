package com.iber.portal.dao.activity;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.activity.LotteryDrawLog;

public interface LotteryDrawLogMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryDrawLog record);

    int insertSelective(LotteryDrawLog record);

    LotteryDrawLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryDrawLog record);

    int updateByPrimaryKey(LotteryDrawLog record);
    
    List<LotteryDrawLog> selectLotteryLog(Map<String, Object> map) ;
    
    int selectCount(Map<String, Object> map);
}