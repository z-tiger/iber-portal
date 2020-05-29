package com.iber.portal.dao.sms;

import com.iber.portal.model.sms.DefenseCore;

public interface DefenseCoreMapper {
    int deleteByPrimaryKey(Integer smsDefenseCoreSeq);

    int insert(DefenseCore record);

    int insertSelective(DefenseCore record);

    DefenseCore selectByPrimaryKey(Integer smsDefenseCoreSeq);

    int updateByPrimaryKeySelective(DefenseCore record);

    int updateByPrimaryKey(DefenseCore record);
}