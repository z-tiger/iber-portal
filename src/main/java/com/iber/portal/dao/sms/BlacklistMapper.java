package com.iber.portal.dao.sms;

import com.iber.portal.model.sms.Blacklist;

public interface BlacklistMapper {
    int deleteByPrimaryKey(Integer smsBlacklistSeq);

    int insert(Blacklist record);

    int insertSelective(Blacklist record);

    Blacklist selectByPrimaryKey(Integer smsBlacklistSeq);

    int updateByPrimaryKeySelective(Blacklist record);

    int updateByPrimaryKey(Blacklist record);
}