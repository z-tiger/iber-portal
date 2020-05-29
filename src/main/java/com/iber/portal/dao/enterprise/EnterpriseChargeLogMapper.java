package com.iber.portal.dao.enterprise;

import com.iber.portal.model.enterprise.EnterpriseChargeLog;

public interface EnterpriseChargeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseChargeLog record);

    int insertSelective(EnterpriseChargeLog record);

    EnterpriseChargeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseChargeLog record);

    int updateByPrimaryKey(EnterpriseChargeLog record);
}