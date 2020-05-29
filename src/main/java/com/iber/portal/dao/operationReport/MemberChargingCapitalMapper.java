package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;


import com.iber.portal.model.operationReport.MemberConsumption;

public interface MemberChargingCapitalMapper {
	
	List<MemberConsumption> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	List<MemberConsumption> selectByPrimaryKeyExcel(Map<String, String> map); 
}