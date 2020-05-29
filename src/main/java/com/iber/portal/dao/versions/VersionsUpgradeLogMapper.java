package com.iber.portal.dao.versions;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.versions.VersionsUpgradeLog;
import com.iber.portal.vo.car.CarVersionVo;

public interface VersionsUpgradeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VersionsUpgradeLog record);

    int insertSelective(VersionsUpgradeLog record);

    VersionsUpgradeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VersionsUpgradeLog record);

    int updateByPrimaryKey(VersionsUpgradeLog record);
    
    List<VersionsUpgradeLog> queryCarVersionsUpgradeLog(Map<String, Object> map) ;
    
    int queryVersionsUpgradeLogCount(Map<String, Object> map) ;
}