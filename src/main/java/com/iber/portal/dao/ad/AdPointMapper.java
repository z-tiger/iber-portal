package com.iber.portal.dao.ad;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.ad.AdPoint;

public interface AdPointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdPoint record);

    int insertSelective(AdPoint record);

    AdPoint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdPoint record);

    int updateByPrimaryKey(AdPoint record);

	List<AdPoint> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}