package com.iber.portal.dao.ad;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.ad.AdPosition;

public interface AdPositionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdPosition record);

    int insertSelective(AdPosition record);

    AdPosition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdPosition record);

    int updateByPrimaryKey(AdPosition record);

	List<AdPosition> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}