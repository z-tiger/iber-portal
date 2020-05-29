package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.Locks;

public interface LocksMapper {
    int deleteByPrimaryKey(String lockName);

    int insert(Locks record);

    int insertSelective(Locks record);
}