package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.operationReport.RuningDayReport;

public interface RuningDayReportMapper {

	List<RuningDayReport> selectByPrimaryKey(Map<String, Object> record);
	
	int selectByPrimaryKeyRecords(Map<String, Object> record);
	
	int countsMonthRegiterMember(String queryMonthTime);
	
	int countsDayRegiterMeber(@Param("queryDaySTime") String queryDaySTime, @Param("queryDayETime") String queryDayETime);
	
	int countsMonthChargeMember(String queryMonthTime);
	
	int countsDayChargeMember(@Param("queryDaySTime") String queryDaySTime, @Param("queryDayETime") String queryDayETime);
	
	int countsCar();
	
	int countsRunCar();
	
	int countsMonthUseCarRecords(String queryMonthTime);
	
	int countsDayUseCarRecords(@Param("queryDaySTime") String queryDaySTime, @Param("queryDayETime") String queryDayETime);
	
    double countsMonthUseCarTimes(String queryMonthTime);
	
    double countsDayUseCarTimes(@Param("queryDaySTime") String queryDaySTime, @Param("queryDayETime") String queryDayETime);
	
}