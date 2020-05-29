package com.iber.portal.dao.sms;

import com.iber.portal.model.sms.WhiteList;

public interface WhiteListMapper {
    int deleteByPrimaryKey(Integer smsWhiteListSeq);

    int insert(WhiteList record);

    int insertSelective(WhiteList record);

    WhiteList selectByPrimaryKey(Integer smsWhiteListSeq);

    int updateByPrimaryKeySelective(WhiteList record);

    int updateByPrimaryKey(WhiteList record);
}