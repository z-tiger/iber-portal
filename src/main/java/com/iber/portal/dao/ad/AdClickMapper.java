package com.iber.portal.dao.ad;

import com.iber.portal.model.ad.AdClick;

public interface AdClickMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdClick record);

    int insertSelective(AdClick record);

    AdClick selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdClick record);

    int updateByPrimaryKey(AdClick record);
}