package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.JobListenersKey;

public interface JobListenersMapper {
    int deleteByPrimaryKey(JobListenersKey key);

    int insert(JobListenersKey record);

    int insertSelective(JobListenersKey record);
}