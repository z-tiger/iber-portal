package com.iber.portal.dao.versions;

import java.util.HashMap;
import java.util.List;

import com.iber.portal.model.versions.VersionsPile;


public interface VersionsPileMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(VersionsPile record);

    int insertSelective(VersionsPile record);

    VersionsPile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VersionsPile record);

    int updateByPrimaryKey(VersionsPile record);

	List<VersionsPile> selectByPrimaryInfo(HashMap<String, Object> record);

	int selectByPrimaryKeyRecords(HashMap<String, Object> record);

	List<VersionsPile> selectNewestVersion();
}