package com.iber.portal.dao.member;

import com.iber.portal.model.member.LostPhonenoApplication;

public interface LostPhonenoApplicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LostPhonenoApplication record);

    int insertSelective(LostPhonenoApplication record);

    LostPhonenoApplication selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LostPhonenoApplication record);

    int updateByPrimaryKey(LostPhonenoApplication record);
}