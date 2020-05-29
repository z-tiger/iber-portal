package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.SimpleTriggers;
import com.iber.portal.model.qrtz.SimpleTriggersKey;

public interface SimpleTriggersMapper {
    int deleteByPrimaryKey(SimpleTriggersKey key);

    int insert(SimpleTriggers record);

    int insertSelective(SimpleTriggers record);

    SimpleTriggers selectByPrimaryKey(SimpleTriggersKey key);

    int updateByPrimaryKeySelective(SimpleTriggers record);

    int updateByPrimaryKey(SimpleTriggers record);
}