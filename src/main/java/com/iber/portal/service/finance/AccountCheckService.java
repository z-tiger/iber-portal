/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.service.finance;

import com.iber.portal.common.Pager;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.finance.AccountCheckMapper;
import com.iber.portal.util.DateTime;
import com.iber.portal.vo.finance.AccountCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账
 * 
 * @author ouxx
 * @since 2017-1-4 下午6:20:05
 * 
 */
@Service
public class AccountCheckService {

	@Autowired
	private AccountCheckMapper accountCheckMapper;

	public Pager<AccountCheckVo> queryAccountReport(Map<String,Object> paramMap /*final String name,
			final Timestamp begin, final Timestamp end, final Integer from,
			final Integer to,final String cityName,final String brandName*/) {

		List<AccountCheckVo> list = accountCheckMapper
				.queryAccountReport(paramMap);
        /*for (AccountCheckVo accountCheckVo : list) {
             String consumptionType = accountCheckVo.getConsumptionType();
             if(!StringUtils.isBlank(consumptionType)&& "众泰尊享返现".equals(consumptionType)){
            	 accountCheckVo.setReturnedMoneyTotal(accountCheckVo.getChargeMoney());
            	 accountCheckVo.setChargeMoney(0.00);
             }
		}*/

		// 处理充值余额和押金
//		setChargeCategory(list);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

        //当年合计
        paramMap.put("searchType",1);
        paramMap.put("begin", yearStart);
        paramMap.put("end", yearEnd);
        AccountCheckVo totalYear = accountCheckMapper.countPayMoneyTotal(paramMap);

        //总合计
        paramMap.remove("begin");
        paramMap.remove("end");
        paramMap.put("searchType",2);
        AccountCheckVo total = accountCheckMapper.countPayMoneyTotal(paramMap);

        list.add(totalYear);
        list.add(total);

        Pager<AccountCheckVo> pager = new Pager<AccountCheckVo>();
		pager.setDatas(list);
		Integer cnt = accountCheckMapper.getAccountReportRecords(paramMap);
		pager.setTotalCount(cnt);
		return pager;
	}

	/**
	 * 处理充值余额和押金 lf 2017-2-20 17:59:10
	 * 
	 * @param list
	 */
	private void setChargeCategory(List<AccountCheckVo> list) {
		// 循环处理押金充值 lf 2017-2-16 17:08:30
		if (!CollectionUtils.isEmpty(list)) {
			for (AccountCheckVo accountCheckVo : list) {
				if (SysConstant.DEPOSIT_PAY_TYPE.equals(accountCheckVo
						.getChargeCategory())) {
					// 设置押金充值余额
					accountCheckVo.setDepositChargeMoney(accountCheckVo
							.getChargeMoney());
					// 清除余额充值数据
					accountCheckVo.setChargeMoney(0.0);
				}
				
				// 处理及时支付和第三方支付
				if (SysConstant.BALANCE_PAY_TYPE.equals(accountCheckVo
						.getPayType())) {
					// 设置押金充值余额
					accountCheckVo.setBalanceConsumptionMoney(accountCheckVo
							.getConsumptionMoney());
					// 清除余额充值数据
					accountCheckVo.setConsumptionMoney(0.0);
				}
			}
		}
	}

	public List<AccountCheckVo> queryAccountReportList(Map<String,Object> paramMap/*final String name,
			final Timestamp begin, final Timestamp end,final String cityName,final String brandName*/) {
		/*Map<String, Object> paramMap = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;

			{
				put("name", name);
				put("begin", begin);
				put("end", end);
				put("cityName", cityName);
				put("brandName", brandName);
			}
		};
*/
		List<AccountCheckVo> list = accountCheckMapper.queryAccountReport(paramMap);

      /*  for (AccountCheckVo accountCheckVo : list) {
            String consumptionType = accountCheckVo.getConsumptionType();
            if(!StringUtils.isBlank(consumptionType)&& "众泰尊享返现".equals(consumptionType)){
           	 accountCheckVo.setReturnedMoneyTotal(accountCheckVo.getChargeMoney());
           	 accountCheckVo.setChargeMoney(0.00);
            }
		}*/

		// 处理充值余额和押金
//		setChargeCategory(list);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

        //当年合计
        paramMap.put("searchType",1);
        paramMap.put("begin", yearStart);
        paramMap.put("end", yearEnd);
        AccountCheckVo totalYear = accountCheckMapper.countPayMoneyTotal(paramMap);

        //总合计
        paramMap.remove("begin");
        paramMap.remove("end");
        paramMap.put("searchType",2);
        AccountCheckVo total = accountCheckMapper.countPayMoneyTotal(paramMap);

        list.add(totalYear);
        list.add(total);

		return list;
	}

    /**
     * 计算当年合计
     *
     *
     * @return
     */

    public AccountCheckVo getCurrentYearTotal(Map<String, Object>paramMap) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //当前时间，根据当前时间获取当月的开始和结束时间，当年的开始和结束时间
        Date currtime = new Date();
        String yearStart = simpleDateFormat.format(DateTime.getStartTimeOfYear(currtime));
        String yearEnd = simpleDateFormat.format(DateTime.getEndTimeOfYear(currtime));

        //当年合计
        paramMap.put("searchType",1);
        paramMap.put("begin", yearStart);
        paramMap.put("end", yearEnd);
        AccountCheckVo totalYear = accountCheckMapper.countPayMoneyTotal(paramMap);
        return totalYear;

    }

    /**
     * 计算总合计
     *
     * @return
     */
    public AccountCheckVo getTotalAccountCheck(Map<String, Object> paramMap) {
        paramMap.put("searchType",2);
        AccountCheckVo total = accountCheckMapper.countPayMoneyTotal(paramMap);
        return total;

    }


	public Integer getAccountReportRecords(final String name,
			final Timestamp begin, final Timestamp end) {
		Map<String, Object> paramMap = new HashMap<String, Object>() {
			private static final long serialVersionUID = 1L;

			{
				put("name", name);
				put("begin", begin);
				put("end", end);
			}
		};
		return accountCheckMapper.getAccountReportRecords(paramMap);
	}

	/******** 计算合计。本日、本月、本年的 *************/
	public AccountCheckVo getTodayAmount() {
		return accountCheckMapper.getTodayAmount();
	}

	public AccountCheckVo getThisMonthAmount() {
		return accountCheckMapper.getThisMonthAmount();
	}

	public AccountCheckVo getThisYearAmount() {
		return accountCheckMapper.getThisYearAmount();
	}

	// 查询押金充值总额
	public List<AccountCheckVo> getThisYearDepositTotal(String name) {
		return accountCheckMapper.getThisYearChargeTotal(name);
	}

	// 查询押金充值总额 2017-2-17 10:00:38 lf
	public AccountCheckVo getThisYearTotal(String name) {
		return accountCheckMapper.getThisYearTotal(name);
	}

	// 查询现在的余额总和
	public AccountCheckVo getThisBanlanceTotal(String name) {
		return accountCheckMapper.getThisBanlanceTotal(name);
	}

	// 会员合计
	public AccountCheckVo getAccountTotal(String name, Timestamp begin,
			Timestamp end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("begin", begin);
		map.put("end", end);
		return accountCheckMapper.getAccountTotal(map);
	}

	// 获取会员充值合计
	public List<AccountCheckVo> getAccountChargeTotal(String name,
			Timestamp begin, Timestamp end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("begin", begin);
		map.put("end", end);
		return accountCheckMapper.getAccountChargeTotal(map);
	}

	// 获取余额统计
	public AccountCheckVo getAccountBanlanceTotal(String name, Timestamp begin,
			Timestamp end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("begin", begin);
		map.put("end", end);
		return accountCheckMapper.getAccountBanlanceTotal(map);
	}

	// 获取会员余额消费总额
	public Double getAccountBalanceConsumptionTotal(String name, Timestamp begin, Timestamp end) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("begin", begin);
		map.put("end", end);
		return accountCheckMapper.getAccountBalanceConsumptionTotal(map);
	}

	// 获取会员今年余额消费总额
	public Double getThisYearAccountBalanceConsumptionTotal(String name) {
		return accountCheckMapper.getThisYearAccountBalanceConsumptionTotal(name);
	}
    // 获取众泰年度返现的数据
	public List<AccountCheckVo> getZtoyeReturnedBalanceRecords(String name) {
		return accountCheckMapper.getZtoyeReturnedBalanceRecords(name);
	}
    // 获取众泰当前时间返现的数据
	public List<AccountCheckVo> getTotalZtoyeReturnedBalanceByTime(Map<String, Object> paramMap) {
		return accountCheckMapper.getTotalZtoyeReturnedBalanceByTime(paramMap);
	}
}
