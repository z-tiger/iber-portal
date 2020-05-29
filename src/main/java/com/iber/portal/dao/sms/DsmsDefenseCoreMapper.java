package com.iber.portal.dao.sms;

import com.iber.portal.model.sms.DsmsDefenseCore;

public interface DsmsDefenseCoreMapper {
    int deleteByPrimaryKey(Integer smsDefenseCoreSeq);

    int insert(DsmsDefenseCore record);

    int insertSelective(DsmsDefenseCore record);

    DsmsDefenseCore selectByPrimaryKey(Integer smsDefenseCoreSeq);

    int updateByPrimaryKeySelective(DsmsDefenseCore record);

    int updateByPrimaryKey(DsmsDefenseCore record);
}