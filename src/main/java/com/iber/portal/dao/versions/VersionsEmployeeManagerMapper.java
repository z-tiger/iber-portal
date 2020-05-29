package com.iber.portal.dao.versions;

import java.util.HashMap;
import java.util.List;

import com.iber.portal.model.versions.VersionsEmployee;

public interface VersionsEmployeeManagerMapper {

	VersionsEmployee selectByPrimaryId(int id);

	int updateByPrimaryKeySelective(VersionsEmployee currObj);

	int insertSelective(VersionsEmployee obj);

	int deleteByPrimaryKey(int id);

	List<VersionsEmployee> selectAll(HashMap<String, Object> record);

	int selectByPrimaryKeyRecords(HashMap<String, Object> record);
     
}
