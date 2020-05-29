package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.PausedTriggerGrps;

public interface PausedTriggerGrpsMapper {
    int deleteByPrimaryKey(String triggerGroup);

    int insert(PausedTriggerGrps record);

    int insertSelective(PausedTriggerGrps record);
}