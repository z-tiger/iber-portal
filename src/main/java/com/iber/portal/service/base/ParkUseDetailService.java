package com.iber.portal.service.base;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.ParkUseDetailMapper;
import com.iber.portal.model.base.ParkUseDetail;
import com.iber.portal.vo.park.ParkDetailStatisticsVo;
import com.iber.portal.vo.park.ParkUseAnalysisVo;

/**
 * 
 * <br>
 * <b>功能：</b>ParkUseDetailService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("parkUseDetailService")
public class ParkUseDetailService{

	private final static Logger log= Logger.getLogger(ParkUseDetailService.class);
	
	@Autowired
    private ParkUseDetailMapper  parkUseDetailMapper;

		
	public int insert(ParkUseDetail record){
		return parkUseDetailMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return parkUseDetailMapper.deleteByPrimaryKey(id) ;
	}
	
	
	public ParkUseDetail selectByPrimaryKey(Integer id){
		return parkUseDetailMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return parkUseDetailMapper.getAllNum(paramMap) ;
	}
	
	public Pager<ParkUseDetail> queryPageList(Map<String, Object> paramMap){
		List<ParkUseDetail> listObj = parkUseDetailMapper.queryPageList(paramMap);
		Pager<ParkUseDetail> pager = new Pager<ParkUseDetail>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	/**
	 * 定时收集网点使用明细数据，并插入数据表
	 * @return
	 * @author ouxx
	 * @date 2016-12-14 下午7:30:49
	 */
	public int insertQuatz(){
		return this.parkUseDetailMapper.insertQuatz();
	}
	
	
	/** 详情  */
	public List<ParkDetailStatisticsVo> queryTodayDetail(Map<String, Object> paramMap){
		return parkUseDetailMapper.queryTodayDetail(paramMap);
	}
	public List<ParkDetailStatisticsVo> queryYesterdayDetail(Map<String, Object> paramMap){
		return parkUseDetailMapper.queryYesterdayDetail(paramMap);
	}
	public List<ParkDetailStatisticsVo> queryThisMonthDetail(Map<String, Object> paramMap){
		return parkUseDetailMapper.queryThisMonthDetail(paramMap);
	}
	public List<ParkDetailStatisticsVo> queryLastMonthDetail(Map<String, Object> paramMap){
		return parkUseDetailMapper.queryLastMonthDetail(paramMap);
	}
	
	private Long getCntFromList(List<ParkDetailStatisticsVo> list){
		Long cnt = 0L;
		if(null != list && !list.isEmpty()){
			ParkDetailStatisticsVo vo = list.get(0);
			if(null != vo){
				cnt = vo.getCnt();
			}
		}
		return cnt;
	}
	
	/** 总体数据 */
	public Long queryTodayCnt(Map<String, Object> paramMap){
		List<ParkDetailStatisticsVo> list = parkUseDetailMapper.queryTodayCnt(paramMap);
		return getCntFromList(list);
	}
	public Long queryYesterdayCnt(Map<String, Object> paramMap){
		List<ParkDetailStatisticsVo> list = parkUseDetailMapper.queryYesterdayCnt(paramMap);
		return getCntFromList(list);
	}
	public Long queryThisMonthCnt(Map<String, Object> paramMap){
		List<ParkDetailStatisticsVo> list = parkUseDetailMapper.queryThisMonthCnt(paramMap);
		return getCntFromList(list);
	}
	public Long queryLastMonthCnt(Map<String, Object> paramMap){
		List<ParkDetailStatisticsVo> list = parkUseDetailMapper.queryLastMonthCnt(paramMap);
		return getCntFromList(list);
	}
	public Long queryTotalCnt(Map<String, Object> paramMap){
		List<ParkDetailStatisticsVo> list = parkUseDetailMapper.queryTotalCnt(paramMap);
		return getCntFromList(list);
	}
	
	/**网点运营分析*/
	public List<ParkUseAnalysisVo>  provinceParkUse(Map<String, Object> paramMap){
		return  parkUseDetailMapper.provinceParkUse(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  cityParkUse(Map<String, Object> paramMap){
		return  parkUseDetailMapper.cityParkUse(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  ParkUse(Map<String, Object> paramMap){
		return  parkUseDetailMapper.ParkUse(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  provinceParkUseByOrder(Map<String, Object> paramMap){
		return  parkUseDetailMapper.provinceParkUseByOrder(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  provinceParkUseByReturn(Map<String, Object> paramMap){
		return  parkUseDetailMapper.provinceParkUseByReturn(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  provinceParkUseByCharging(Map<String, Object> paramMap){
		return  parkUseDetailMapper.provinceParkUseByCharging(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  cityParkUseByOrder(Map<String, Object> paramMap){
		return  parkUseDetailMapper.cityParkUseByOrder(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  cityParkUseByReturn(Map<String, Object> paramMap){
		return  parkUseDetailMapper.cityParkUseByReturn(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  cityParkUseByCharging(Map<String, Object> paramMap){
		return  parkUseDetailMapper.cityParkUseByCharging(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  ParkUseByOrder(Map<String, Object> paramMap){
		return  parkUseDetailMapper.ParkUseByOrder(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  ParkUseByReturn(Map<String, Object> paramMap){
		return  parkUseDetailMapper.ParkUseByReturn(paramMap);
	}
	
	public List<ParkUseAnalysisVo>  ParkUseByCharging(Map<String, Object> paramMap){
		return  parkUseDetailMapper.ParkUseByCharging(paramMap);
	}
}
