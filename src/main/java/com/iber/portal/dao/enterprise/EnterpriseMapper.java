package com.iber.portal.dao.enterprise;

import com.iber.portal.model.enterprise.Enterprise;

import java.util.List;
import java.util.Map;

public interface EnterpriseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Enterprise record);

    int insertSelective(Enterprise record);

    Enterprise selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Enterprise record);

    int updateByPrimaryKey(Enterprise record);

	List<Enterprise> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	Enterprise selectByEnterpriseName(String enterpriseName);

	List<Map<String,Object>> enterpriseList();

    List<Enterprise> findBranchCompanyByParentId(Map<String,Object> map);

    int findBranchCompanyTotal(Map<String,Object> map);


}