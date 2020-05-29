package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.operationReport.CarApplyDetail;

public interface CarApplyDetailMapper {

    
	List<CarApplyDetail> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	List<CarApplyDetail> selectByPrimaryKeyExcel(Map<String, Object> map);
}