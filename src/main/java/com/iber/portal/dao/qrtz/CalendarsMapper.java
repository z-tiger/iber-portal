package com.iber.portal.dao.qrtz;

import com.iber.portal.model.qrtz.Calendars;

public interface CalendarsMapper {
    int deleteByPrimaryKey(String calendarName);

    int insert(Calendars record);

    int insertSelective(Calendars record);

    Calendars selectByPrimaryKey(String calendarName);

    int updateByPrimaryKeySelective(Calendars record);

    int updateByPrimaryKeyWithBLOBs(Calendars record);
}