package com.iber.portal.dao.versions;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.versions.VersionsBox;

public interface VersionsBoxMapper {
    
	int deleteByPrimaryKey(Integer id);

    int insert(VersionsBox record);

    int insertSelective(VersionsBox record);

    VersionsBox selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VersionsBox record);

    int updateByPrimaryKey(VersionsBox record);

	List<VersionsBox> selectByPrimaryInfo(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	VersionsBox selectByPrimaryId(Integer id);
}