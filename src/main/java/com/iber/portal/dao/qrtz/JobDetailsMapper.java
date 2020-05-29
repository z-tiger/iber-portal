package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.JobDetails;
import com.iber.portal.model.qrtz.JobDetailsKey;

public interface JobDetailsMapper {
    int deleteByPrimaryKey(JobDetailsKey key);

    int insert(JobDetails record);

    int insertSelective(JobDetails record);

    JobDetails selectByPrimaryKey(JobDetailsKey key);

    int updateByPrimaryKeySelective(JobDetails record);

    int updateByPrimaryKeyWithBLOBs(JobDetails record);

    int updateByPrimaryKey(JobDetails record);
}