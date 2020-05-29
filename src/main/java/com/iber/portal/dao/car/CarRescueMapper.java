package com.iber.portal.dao.car;

import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.car.CarRescueProblem;
import com.iber.portal.model.member.EvidenceRelation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CarRescueMapper {
	
	int insert(CarRescue record);
	
	int insertSelective(CarRescue record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(CarRescue record);
	
	int updateByPrimaryKeySelective(CarRescue record);
	
	CarRescue selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<CarRescue> queryPageList(Map<String, Object> paramMap);

	int carResumeRepair(CarRescue carRescue);
	
	/**
	 * 查给定时间段，lpn的救援记录数 
	 * @param lpn
	 * @param ordStartTime
	 * @param ordEndTime
	 * @return
	 * @author ouxx
	 * @date 2017-4-21 下午8:27:59
	 */
	int  queryByLpnBetweenTime(@Param("lpn") String lpn, 
			@Param("ordStartTime") Date ordStartTime, @Param("ordEndTime") Date ordEndTime);
	
	int selectByLpn(Map<String, Object> paramMap);
	
	List<String> getRescueEviPicsByReportId(Integer reportId);
	
	int insertRescuePicUrls(List<EvidenceRelation> list);
	
	Integer selectUnfinishedRecordByLpn(Map<String, Object> paramMap);

	Integer insertCarRescueProblems(List<CarRescueProblem> lists);
	
	List<CarRescueProblem> selectCarProblemsDetailByRecId(@Param("recId") Integer recId);
}
