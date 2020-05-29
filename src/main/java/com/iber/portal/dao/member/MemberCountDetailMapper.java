package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;
import com.iber.portal.model.member.MemberCountDetail;



public interface MemberCountDetailMapper {
	
	int insert(MemberCountDetail record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(MemberCountDetail record);
	
	int updateByPrimaryKeySelective(MemberCountDetail record);
	
	MemberCountDetail selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberCountDetail> queryPageList(Map<String, Object> paramMap);
	
	List<MemberCountDetail>  queryRegisterNumber();
	
    List<MemberCountDetail> queryOfficialMemberNumber();
    
    List<MemberCountDetail> queryRechargeBalance();
    
    List<MemberCountDetail> queryDeposit();
    
	/**
	 * 定时收集会员统计明细数据，并插入数据表
	 * @return
	 * @author xyq
	 * @date 2017.1.6
	 */
	int insertSelective(MemberCountDetail vo);
    /**统计今日/昨日，本月/上月,累计的注册数*/
	List<MemberCountDetail> getAllRegisterData (Map<String, Object> paramMap);
	/**统计今日/昨日，本月/上月,累计的正式会员数*/
	List<MemberCountDetail> getAllMemberData (Map<String, Object> paramMap);
	/**统计今日/昨日，本月/上月,累计的充值押金额*/
	List<MemberCountDetail> getAllDepositData (Map<String, Object> paramMap);
	/**统计今日/昨日，本月/上月,累计的充值余额 */
	List<MemberCountDetail> getAllBalanceData (Map<String, Object> paramMap);
	/**统计今日/昨日，本月/上月,累计的充值人数*/
	List<MemberCountDetail> getAllChargeNumberData (Map<String, Object> paramMap);
	
	/**会员统计折线图详情*/
	//注册数
	List<MemberCountDetail> getAllTodayRegisterData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayRegisterData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthRegisterData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthRegisterData (Map<String, Object> paramMap);
	//正式会员数
	List<MemberCountDetail> getAllTodayMemberData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayMemberData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthMemberData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthMemberData (Map<String, Object> paramMap);
	//押金值
	List<MemberCountDetail> getAllTodayDepositData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayDepositData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthDepositData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthDepositData (Map<String, Object> paramMap);
	//余额值
	List<MemberCountDetail> getAllTodayBalanceData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayBalanceData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthBalanceData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthBalanceData (Map<String, Object> paramMap);
	//充值数
	List<MemberCountDetail> getAllTodayChargeData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayChargeData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthChargeData (Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthChargeData (Map<String, Object> paramMap);

	//消费金额
	List<MemberCountDetail> getAllTodayCostData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayCostData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthCostData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthCostData(Map<String, Object> paramMap);
	
	//未支付金额
	List<MemberCountDetail> getAllTodayNoPayData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayNoPayData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthNoPayData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthNoPayData(Map<String, Object> paramMap);
	
	//退款金额
	List<MemberCountDetail> getAllTodayRefundData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllYesterdayRefundData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllThisMonthRefundData(Map<String, Object> paramMap);
	List<MemberCountDetail> getAllLastMonthRefundData(Map<String, Object> paramMap);
	
	
	//会员消费/未付款信息 定时器定时收集
	List<MemberCountDetail> queryCostMoneyList();
	//会员退款信息定时器定时收集
	List<MemberCountDetail> queryRefundMoneyList();
	//批量插入会员统计数据
	int insertBatch(List<MemberCountDetail> allList);
	/**
	 * 会员花费
	 */
	List<MemberCountDetail> getAllCostData(Map<String, Object> paramMap);
	/**
	 * 会员未支付
	 * @param paramMap
	 * @return
	 */
	List<MemberCountDetail> getAllNoPayData(Map<String, Object> paramMap);

	/**
	 * 会员退款
	 * @param paramMap
	 * @return
	 */
	List<MemberCountDetail> getAllRefundData(Map<String, Object> paramMap);

}
