package com.iber.portal.dao.versions;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.versions.VersionsRearview;

public interface VersionsRearviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VersionsRearview record);

    int insertSelective(VersionsRearview record);

    VersionsRearview selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VersionsRearview record);

    int updateByPrimaryKey(VersionsRearview record);

	List<VersionsRearview> selectByPrimaryInfo(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);

	VersionsRearview selectByPrimaryId(Integer id);

}