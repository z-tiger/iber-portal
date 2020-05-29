/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.dao.finance;

import com.iber.portal.vo.finance.AccountCheckVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 消费对账
 * @author ouxx
 * @since 2016-12-21 上午9:40:58
 *
 */
public interface AccountCheckMapper {

	/**
	 * 对账
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2016-12-21 下午5:12:51
	 */
	List<AccountCheckVo> queryAccountReport(Map<String, Object> paramMap);
	
	Integer getAccountReportRecords(Map<String, Object> paramMap);

    /**
     * 计算本年和总合计
     */
    AccountCheckVo countPayMoneyTotal(Map<String, Object> paraMap);

    /********计算合计。本日、本月、本年的*************/
	AccountCheckVo getTodayAmount();
	
	AccountCheckVo getThisMonthAmount();
	
	AccountCheckVo getThisYearAmount();
    
	// 押金充值统计
	List<AccountCheckVo> getThisYearChargeTotal(@Param("name") String name);

	// 年度消费部分统计
	AccountCheckVo getThisYearTotal(@Param("name") String name);
	
	// 余额统计
	AccountCheckVo getThisBanlanceTotal(@Param("name") String name);
	
	// 获取账户余额
	AccountCheckVo getAccountTotal(Map<String, Object> paramMap);
	
	// 获取账户充值合计
	List<AccountCheckVo> getAccountChargeTotal(Map<String, Object> paramMap);

	// 获取余额统计
	AccountCheckVo getAccountBanlanceTotal(Map<String, Object> map);
	
	// 获取会员余额消费总额
	Double getAccountBalanceConsumptionTotal(Map<String, Object> paramMap);
	
	// 获取会员今年余额消费总额
	Double getThisYearAccountBalanceConsumptionTotal(@Param("name") String name);
	
	List<AccountCheckVo> getZtoyeReturnedBalanceRecords(@Param("name") String name);
	
	List<AccountCheckVo> getTotalZtoyeReturnedBalanceByTime(Map<String, Object> paramMap);
}
