package com.iber.portal.dao.enterprise;

import com.iber.portal.model.enterprise.EnterpriseDepartment;

public interface EnterpriseDepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseDepartment record);

    int insertSelective(EnterpriseDepartment record);

    EnterpriseDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseDepartment record);

    int updateByPrimaryKey(EnterpriseDepartment record);
}