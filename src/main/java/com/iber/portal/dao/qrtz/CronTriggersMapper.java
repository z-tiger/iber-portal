package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.CronTriggers;
import com.iber.portal.model.qrtz.CronTriggersKey;

public interface CronTriggersMapper {
    int deleteByPrimaryKey(CronTriggersKey key);

    int insert(CronTriggers record);

    int insertSelective(CronTriggers record);

    CronTriggers selectByPrimaryKey(CronTriggersKey key);

    int updateByPrimaryKeySelective(CronTriggers record);

    int updateByPrimaryKey(CronTriggers record);
}