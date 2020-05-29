package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.BlobTriggers;
import com.iber.portal.model.qrtz.BlobTriggersKey;

public interface BlobTriggersMapper {
    int deleteByPrimaryKey(BlobTriggersKey key);

    int insert(BlobTriggers record);

    int insertSelective(BlobTriggers record);

    BlobTriggers selectByPrimaryKey(BlobTriggersKey key);

    int updateByPrimaryKeySelective(BlobTriggers record);

    int updateByPrimaryKeyWithBLOBs(BlobTriggers record);
}