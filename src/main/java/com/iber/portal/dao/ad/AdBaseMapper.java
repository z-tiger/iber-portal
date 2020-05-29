package com.iber.portal.dao.ad;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.ad.AdBase;

public interface AdBaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdBase record);

    int insertSelective(AdBase record);

    AdBase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdBase record);

    int updateByPrimaryKey(AdBase record);

	List<AdBase> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	List<AdBase> getPreview(Map<String, Object> paramMap);
}