package com.iber.portal.dao.operationReport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.iber.portal.model.operationReport.MemberConsumption;

public interface MemberConsumptionMapper {
	
	List<MemberConsumption> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	List<MemberConsumption> selectByPrimaryKeyExcel(Map<String, Object> map); 
	
	
	/**会员充电明细*/
	
	List<MemberConsumption> selectChargingCapitalByPrimaryKey(Map<String, Object> record);

	int selectChargingCapitalByPrimaryKeyRecords(Map<String, Object> record);

	List<MemberConsumption> selectChargingCapitalByPrimaryKeyExcel(Map<String, Object> map);

	int selectChargingCapitalCountByPrimaryKey(HashMap<String, Object> record);


}