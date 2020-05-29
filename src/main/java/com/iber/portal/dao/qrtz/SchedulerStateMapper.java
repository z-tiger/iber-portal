package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.SchedulerState;

public interface SchedulerStateMapper {
    int deleteByPrimaryKey(String instanceName);

    int insert(SchedulerState record);

    int insertSelective(SchedulerState record);

    SchedulerState selectByPrimaryKey(String instanceName);

    int updateByPrimaryKeySelective(SchedulerState record);

    int updateByPrimaryKey(SchedulerState record);
}