package com.iber.portal.dao.operationReport;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.operationReport.MemberRegister;

public interface MemberRegisterMapper {
   
	List<MemberRegister> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);
	
	List<MemberRegister> selectByPrimaryKeyExcel(Map<String, Object> record);
	
	List<MemberRegister> selectRegisterInfo(Map<String, Object> record);

	int selectRegisterInfoRecords(Map<String, Object> record);

}