package com.iber.portal.dao.versions;

import java.util.List;
import java.util.Map;


import com.iber.portal.model.versions.VersionsCategory;

public interface VersionsCategoryMapper {
	
	List<VersionsCategory> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);
	
	int deleteByPrimaryKey(Integer id);

    int insert(VersionsCategory record);

    int insertSelective(VersionsCategory record);

    int updateByPrimaryKeySelective(VersionsCategory record);

    int updateByPrimaryKey(VersionsCategory record);

    VersionsCategory selectByPrimaryId(Integer id);
    
    List<VersionsCategory> selectByPrimaryCategory(Map<String,String> map);
    
    
}