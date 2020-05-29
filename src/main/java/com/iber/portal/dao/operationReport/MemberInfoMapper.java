package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;


import com.iber.portal.model.operationReport.MemberInfo;

public interface MemberInfoMapper {
	
	List<MemberInfo> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);
	
	List<MemberInfo> selectByPrimaryKeyExcel(Map<String, Object> record);

}