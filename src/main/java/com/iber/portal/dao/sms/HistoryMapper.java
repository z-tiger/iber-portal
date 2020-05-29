package com.iber.portal.dao.sms;

import com.iber.portal.model.sms.History;

public interface HistoryMapper {
    int deleteByPrimaryKey(Integer smsHistorySeq);

    int insert(History record);

    int insertSelective(History record);

    History selectByPrimaryKey(Integer smsHistorySeq);

    int updateByPrimaryKeySelective(History record);

    int updateByPrimaryKey(History record);
}