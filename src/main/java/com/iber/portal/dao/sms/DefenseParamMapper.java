package com.iber.portal.dao.sms;

import com.iber.portal.model.sms.DefenseParam;

public interface DefenseParamMapper {
    int deleteByPrimaryKey(Integer smsDefenseParamSeq);

    int insert(DefenseParam record);

    int insertSelective(DefenseParam record);

    DefenseParam selectByPrimaryKey(Integer smsDefenseParamSeq);

    int updateByPrimaryKeySelective(DefenseParam record);

    int updateByPrimaryKey(DefenseParam record);
}