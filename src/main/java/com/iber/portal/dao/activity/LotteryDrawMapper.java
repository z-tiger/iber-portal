package com.iber.portal.dao.activity;

import com.iber.portal.model.activity.LotteryDraw;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LotteryDrawMapper {
  
    int deleteByPrimaryKey(Integer id);

    int insert(LotteryDraw record);

    int insertSelective(LotteryDraw record);

    LotteryDraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryDraw record);

    int updateByPrimaryKey(LotteryDraw record);
    
    List<LotteryDraw> selectAllLotteryDraw(Map<String, Object> map);
    
    int selectCount(Map<String, Object> map);
    
    List<LotteryDraw> queryLotteryDraw();
    
    int updateLotteryDrawStatus(byte status);
}