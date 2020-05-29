package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.ParkUseDetail;
import com.iber.portal.vo.park.ParkDetailStatisticsVo;
import com.iber.portal.vo.park.ParkUseAnalysisVo;

/**
 * 
 * <br>
 * <b>功能：</b>ParkUseDetailDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface ParkUseDetailMapper {
	
	int insert(ParkUseDetail record);

	int insertSelective(ParkUseDetail record);
	
	int deleteByPrimaryKey(Integer id);
	
	ParkUseDetail selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<ParkUseDetail> queryPageList(Map<String, Object> paramMap);
	
	/**
	 * 定时收集网点使用明细数据，并插入数据表
	 * @return
	 * @author ouxx
	 * @date 2016-12-14 下午7:30:07
	 */
	int insertQuatz();
	
	/** 详情数据 */
	List<ParkDetailStatisticsVo> queryTodayDetail(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryYesterdayDetail(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryThisMonthDetail(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryLastMonthDetail(Map<String, Object> paramMap);
	
	/** 总体数据 */
	List<ParkDetailStatisticsVo> queryTodayCnt(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryYesterdayCnt(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryThisMonthCnt(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryLastMonthCnt(Map<String, Object> paramMap);
	List<ParkDetailStatisticsVo> queryTotalCnt(Map<String, Object> paramMap);
	
	/**网点运营分析*/
	List<ParkUseAnalysisVo> provinceParkUse(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo>cityParkUse(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo>ParkUse(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo>provinceParkUseByOrder(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo>provinceParkUseByReturn(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> provinceParkUseByCharging(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> cityParkUseByOrder(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> cityParkUseByReturn(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> cityParkUseByCharging(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> ParkUseByOrder(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> ParkUseByReturn(Map<String, Object> paramMap);
	
	List<ParkUseAnalysisVo> ParkUseByCharging(Map<String, Object> paramMap);
}
