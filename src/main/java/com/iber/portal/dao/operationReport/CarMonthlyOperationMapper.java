package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.operationReport.CarMonthlyOperation;


public interface CarMonthlyOperationMapper {
	
	List<CarMonthlyOperation> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	List<CarMonthlyOperation> selectByPrimaryKeyExcel(Map<String, String> map); 

}