package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.Triggers;
import com.iber.portal.model.qrtz.TriggersKey;

public interface TriggersMapper {
    int deleteByPrimaryKey(TriggersKey key);

    int insert(Triggers record);

    int insertSelective(Triggers record);

    Triggers selectByPrimaryKey(TriggersKey key);

    int updateByPrimaryKeySelective(Triggers record);

    int updateByPrimaryKeyWithBLOBs(Triggers record);

    int updateByPrimaryKey(Triggers record);
}