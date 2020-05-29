package com.iber.portal.dao.function;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.function.AppVersion;

public interface AppVersionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppVersion record);

    int insertSelective(AppVersion record);

    AppVersion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppVersion record);

    int updateByPrimaryKey(AppVersion record);
    
    List<AppVersion> selectByAPPVersionList(Map<String, Object> map) ;
    
    int selectByAPPVersionListRecords(Map<String, Object> map) ;
}