package com.iber.portal.dao.enterprise;

import java.util.List;
import java.util.Map;

import com.iber.portal.common.PageLimit;
import com.iber.portal.model.enterprise.EnterpriseLevel;

public interface EnterpriseLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseLevel record);

    int insertSelective(EnterpriseLevel record);

    EnterpriseLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseLevel record);

    int updateByPrimaryKey(EnterpriseLevel record);
    
    List<EnterpriseLevel> getAllEnterpriseLevel();

	List<EnterpriseLevel> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}