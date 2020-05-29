package com.iber.portal.dao.enterprise;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.enterprise.EnterpriseExtend;

public interface EnterpriseExtendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseExtend record);

    int insertSelective(EnterpriseExtend record);

    EnterpriseExtend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseExtend record);

    int updateByPrimaryKey(EnterpriseExtend record);

	List<EnterpriseExtend> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}