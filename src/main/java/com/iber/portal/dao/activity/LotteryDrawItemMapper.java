package com.iber.portal.dao.activity;

import com.iber.portal.model.activity.LotteryDraw;
import com.iber.portal.model.activity.LotteryDrawItem;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LotteryDrawItemMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(LotteryDrawItem record);

    int insertSelective(LotteryDrawItem record);

    LotteryDrawItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LotteryDrawItem record);

    int updateByPrimaryKey(LotteryDrawItem record);
    
    List<LotteryDrawItem> selectAllLotteryDrawItem (Map<String, Object> map) ;
    
    int selectCount(Map<String, Object> map);
    
    int selectMaxSort() ;
    
    String selectSumPrizeWeight(Integer id);
   
}