package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.operationReport.MemberCapital;
import com.iber.portal.model.operationReport.MemberRecharge;

public interface MemberCapitalMapper {
 
	List<MemberCapital> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	List<MemberCapital> selectByPrimaryKeyExcel(Map<String, String> map); 
	
	List<MemberRecharge> selectMemberRechargeDetails(Map<String, Object> map);
	
	int selectMemberRechargeDetailsCount(int memberId);
    
}