package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.FiredTriggers;

public interface FiredTriggersMapper {
    int deleteByPrimaryKey(String entryId);

    int insert(FiredTriggers record);

    int insertSelective(FiredTriggers record);

    FiredTriggers selectByPrimaryKey(String entryId);

    int updateByPrimaryKeySelective(FiredTriggers record);

    int updateByPrimaryKey(FiredTriggers record);
}