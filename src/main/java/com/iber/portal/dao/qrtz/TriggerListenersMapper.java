package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.TriggerListenersKey;

public interface TriggerListenersMapper {
    int deleteByPrimaryKey(TriggerListenersKey key);

    int insert(TriggerListenersKey record);

    int insertSelective(TriggerListenersKey record);
}