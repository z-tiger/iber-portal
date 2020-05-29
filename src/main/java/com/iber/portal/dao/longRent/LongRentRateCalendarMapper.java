package com.iber.portal.dao.longRent;

import com.iber.portal.model.longRent.LongRentRateCalendar;
import com.iber.portal.vo.longRent.LongRentRateVo;

import java.util.List;

public interface LongRentRateCalendarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LongRentRateCalendar record);

    int insertSelective(LongRentRateCalendar record);

    LongRentRateCalendar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LongRentRateCalendar record);

    int updateByPrimaryKey(LongRentRateCalendar record);

    List<LongRentRateCalendar> getList(LongRentRateVo vo);

    int getCount(LongRentRateVo vo);

    void batchInsert(List<LongRentRateCalendar> list);
}