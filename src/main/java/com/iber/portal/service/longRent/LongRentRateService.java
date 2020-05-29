package com.iber.portal.service.longRent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iber.portal.common.CommonUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.longRent.LongRentRateCalendarMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.longRent.LongRentRateCalendar;
import com.iber.portal.vo.longRent.LongRentRateCalendarVo;
import com.iber.portal.vo.longRent.LongRentRateVo;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LongRentRateService {

    @Autowired
    private LongRentRateCalendarMapper rateCalendarMapper;

    public int insertSelective(LongRentRateCalendar model) throws ServiceException {
        int len;
        try {
            len = rateCalendarMapper.insertSelective(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
        return len;
    }

    public int deleteByPrimaryKey(int id) throws ServiceException {
        int len;
        try {
            len = rateCalendarMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
        return len;
    }

    public int updateByPrimaryKeySelective(LongRentRateCalendar model) throws ServiceException {
        try {
            return rateCalendarMapper.updateByPrimaryKeySelective(model);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException();
        }
    }

    public LongRentRateCalendar selectByPrimaryKey(int id) {
        return rateCalendarMapper.selectByPrimaryKey(id);
    }

    public Map<String, Object> getList(LongRentRateVo vo) {
        List<LongRentRateCalendar> list = rateCalendarMapper.getList(vo);
        Map<String, Object> result = Maps.newHashMap();
        result.put("rows", list);
        result.put("total", rateCalendarMapper.getCount(vo));
        return result;
    }

    /**
     * 添加日租计费
     * lf
     * 2018年1月4日
     *
     * @param vo
     */
    public Map<String, Object> addLongRentRate(LongRentRateCalendarVo vo) {
        final Integer carTypeId = vo.getCarTypeId();
        final Integer money = (int) (vo.getMoneyFen() * 100);
        Date from = vo.getFrom();
        Date to = vo.getTo();
        if (CommonUtil.checkInt(carTypeId) || CommonUtil.checkInt(money)
                || from == null || to == null) {
            return CommonUtil.paramFail();
        }
        // 查询是否存在要插入的记录
        LongRentRateVo rentRateVo = new LongRentRateVo();
        final String cityCode = vo.getCityCode();
        final Date createTime = vo.getCreateTime();
        final Integer createId = vo.getCreateId();
        if (from.compareTo(to) > 0) {
            Date temp = from;
            from = to;
            to = temp;
        }
        rentRateVo.setCarTypeId(carTypeId);
        rentRateVo.setCityCode(cityCode);
        rentRateVo.setFrom(from);
        rentRateVo.setTo(to);
        final int count = rateCalendarMapper.getCount(rentRateVo);
        if (count > 0) return CommonUtil.fail("指定的日期内已经存在记录！");
        // 开始批量插入
        List<LongRentRateCalendar> list = Lists.newArrayList();
        while (from.compareTo(to) <= 0) {
            LongRentRateCalendar rentRateCalendar = new LongRentRateCalendar();
            rentRateCalendar.setCreateTime(createTime);
            rentRateCalendar.setCreateId(createId);
            rentRateCalendar.setCarTypeId(carTypeId);
            rentRateCalendar.setCityCode(cityCode);
            rentRateCalendar.setMoney(money);
            rentRateCalendar.setRentDate(from);
            list.add(rentRateCalendar);
            from = DateUtils.addDays(from, 1);
        }
        rateCalendarMapper.batchInsert(list);
        return CommonUtil.success();
    }
}
