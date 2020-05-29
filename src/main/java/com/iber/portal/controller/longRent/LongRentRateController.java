package com.iber.portal.controller.longRent;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.CommonUtil;
import com.iber.portal.controller.MainController;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.longRent.LongRentRateCalendar;
import com.iber.portal.service.longRent.LongRentRateService;
import com.iber.portal.vo.longRent.LongRentRateCalendarVo;
import com.iber.portal.vo.longRent.LongRentRateVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;

/**
 * 日租计费管理
 * 2018年1月4日
 * lf
 */
@Controller
public class LongRentRateController extends MainController {

	@Autowired
    private LongRentRateService rateService;
	
	/**
	 * 日租计费管理页面
	 */
	@SystemServiceLog(description="日租计费管理页面")
	@RequestMapping(value = "/long_rent_rate_calender", method = RequestMethod.GET)
	public String longRentRateCalenderPage()
			throws Exception {
		LOGGER.info("日租计费管理页面");
		return "longRent/longRentRate";
	}
	
	/**
	 * 日租计费列表
	 */
	@SystemServiceLog(description="日租计费列表")
	@RequestMapping(value = "/long_rent_rate_list")
	@ResponseBody
	public Map<String,Object> longRentRateList(LongRentRateVo vo){
		LOGGER.info("日租计费列表");
		try {
			return rateService.getList(vo);
		} catch (Exception e) {
			LOGGER.error("日租计费列表失败！",e);
			return null;
		}
	}
	
	/**
	 *  添加日租计费
	 */
	@SystemServiceLog(description="添加日租计费")
	@RequestMapping(value = "/long_rent_rate_add")
	@ResponseBody
	public Map<String,Object> addLongRentRate(LongRentRateCalendarVo vo)
			throws Exception {
		LOGGER.info("添加日租计费");
		try {
			vo.setCreateId(getUserId());
			vo.setCreateTime(new Date());
			return rateService.addLongRentRate(vo);
		} catch (Exception e) {
			LOGGER.error("添加日租计费失败！",e);
			return error();
		}
	}

	/**
	 *  更新日租计费
	 */
	@SystemServiceLog(description="更新计费")
	@RequestMapping(value = "/long_rent_rate_update")
	@ResponseBody
	public Map<String,Object> updateLongRentRate(LongRentRateCalendarVo vo)
			throws Exception {
		LOGGER.info("更新日租计费");
		try {
			if (vo == null) return paramFail();
			vo.setUpdateTime(new Date());
			vo.setUpdateId(getUserId());
			final Integer money = (int) (vo.getMoneyFen() * 100);
			vo.setMoney(money);
			rateService.updateByPrimaryKeySelective(vo);
			return success();
		} catch (Exception e) {
			LOGGER.error("更新日租计费失败！",e);
			return error();
		}
	}

	/**
	 *  删除日租计费
	 * 
	 */
	@SystemServiceLog(description="删除日租计费")
	@RequestMapping(value = "/long_rent_rate_del")
	@ResponseBody
	public Map<String,Object> longRentRateDel(Integer id)
			throws Exception {
		LOGGER.info("删除日租计费");
		try {
			rateService.deleteByPrimaryKey(id);
			return success();
		} catch (ServiceException e) {
			LOGGER.error("删除日租计费失败！",e);
			return error();
		}
	}
}
