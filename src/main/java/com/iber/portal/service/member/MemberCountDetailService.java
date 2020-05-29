package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.member.MemberCountDetailMapper;
import com.iber.portal.model.member.MemberCountDetail;


@Service("memberCountDetailService")
public class MemberCountDetailService{

	private final static Logger log= Logger.getLogger(MemberCountDetailService.class);
	
	@Autowired
    private MemberCountDetailMapper  memberCountDetailMapper;

		
	public int insert(MemberCountDetail record){
		return memberCountDetailMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return memberCountDetailMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(MemberCountDetail record){
		return memberCountDetailMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberCountDetail record){
		return memberCountDetailMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberCountDetail selectByPrimaryKey(Integer id){
		return memberCountDetailMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return memberCountDetailMapper.getAllNum(paramMap) ;
	}
	
	public Pager<MemberCountDetail> queryPageList(Map<String, Object> paramMap){
		List<MemberCountDetail> listObj = memberCountDetailMapper.queryPageList(paramMap);
		Pager<MemberCountDetail> pager = new Pager<MemberCountDetail>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	public List<MemberCountDetail> queryRegisterNumber(){
		return memberCountDetailMapper.queryRegisterNumber();
	}
	public List<MemberCountDetail> queryOfficialMemberNumber(){
		return memberCountDetailMapper.queryOfficialMemberNumber();
	}
	public List<MemberCountDetail> queryRechargeBalance(){
		return memberCountDetailMapper.queryRechargeBalance();
	}
	public List<MemberCountDetail> queryDeposit(){
		return memberCountDetailMapper.queryDeposit();
	}
	
	
	/**
	 * 定时收集会员统计数据，并插入数据表
	 * @return
	 * @author xyq
	 * @date 2017.1.6
	 */
	public int insertSelective(MemberCountDetail vo ){
		return this.memberCountDetailMapper.insertSelective(vo);
	}
	
	/**统计今日/昨日，本月/上月,累计的注册数*/
	public List<MemberCountDetail> getAllRegisterData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllRegisterData(paramMap);
	} 
	/**统计今日/昨日，本月/上月,累计的正式会员数*/
	public List<MemberCountDetail> getAllMemberData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllMemberData(paramMap);
	}
	/**统计今日/昨日，本月/上月,累计的充值押金额*/
	public List<MemberCountDetail> getAllDepositData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllDepositData(paramMap);
	}
	/**统计今日/昨日，本月/上月,累计的充值余额 */
	public List<MemberCountDetail> getAllBalanceData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllBalanceData(paramMap);
	}
	/**统计今日/昨日，本月/上月,累计的充值人数*/
	public List<MemberCountDetail> getAllChargeNumberData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllChargeNumberData(paramMap);
	}
	
	/**会员统计详细*/
	//注册数
	public List<MemberCountDetail> getAllTodayRegisterData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllTodayRegisterData(paramMap);
	}
	
	public List<MemberCountDetail> getAllYesterdayRegisterData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllYesterdayRegisterData(paramMap);
	}
	
	public List<MemberCountDetail> getAllThisMonthRegisterData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllThisMonthRegisterData(paramMap);
	}
	
	public List<MemberCountDetail> getAllLastMonthRegisterData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllLastMonthRegisterData(paramMap);
	}
	
	
	//正式会员数
	public List<MemberCountDetail> getAllTodayMemberData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllTodayMemberData(paramMap);
	}
	
	public List<MemberCountDetail> getAllYesterdayMemberData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllYesterdayMemberData(paramMap);
	}
	
	public List<MemberCountDetail> getAllThisMonthMemberData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllThisMonthMemberData(paramMap);
	}
	
	public List<MemberCountDetail> getAllLastMonthMemberData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllLastMonthMemberData(paramMap);
	}
	
	//充值押金数
	public List<MemberCountDetail> getAllTodayDepositData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllTodayDepositData(paramMap);
	}
	
	public List<MemberCountDetail> getAllYesterdayDepositData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllYesterdayDepositData(paramMap);
	}
	
	public List<MemberCountDetail> getAllThisMonthDepositData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllThisMonthDepositData(paramMap);
	}
	
	public List<MemberCountDetail> getAllLastMonthDepositData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllLastMonthDepositData(paramMap);
	}
	
	//充值余额数
	public List<MemberCountDetail> getAllTodayBalanceData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllTodayBalanceData(paramMap);
	}
	
	public List<MemberCountDetail> getAllYesterdayBalanceData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllYesterdayBalanceData(paramMap);
	}
	
	public List<MemberCountDetail> getAllThisMonthBalanceData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllThisMonthBalanceData(paramMap);
	}
	
	public List<MemberCountDetail> getAllLastMonthBalanceData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllLastMonthBalanceData(paramMap);
	}
	
	//会员充值数
	public List<MemberCountDetail> getAllTodayChargeData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllTodayChargeData(paramMap);
	}
	
	public List<MemberCountDetail> getAllYesterdayChargeData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllYesterdayChargeData(paramMap);
	}
	
	public List<MemberCountDetail> getAllThisMonthChargeData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllThisMonthChargeData(paramMap);
	}
	
	public List<MemberCountDetail> getAllLastMonthChargeData(Map<String, Object> paramMap ){
		return this.memberCountDetailMapper.getAllLastMonthChargeData(paramMap);
	}

	public List<MemberCountDetail> queryCostMoneyList() {
		return this.memberCountDetailMapper.queryCostMoneyList();
	}

	public List<MemberCountDetail> queryRefundMoneyList() {
		return this.memberCountDetailMapper.queryRefundMoneyList();
	}

	public int insertBatch(List<MemberCountDetail> allList) {
		return this.memberCountDetailMapper.insertBatch(allList);
	}

	public List<MemberCountDetail> getAllCostData(Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllCostData(paramMap);
	}

	public List<MemberCountDetail> getAllNoPayData(Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllNoPayData(paramMap);
	}

	public List<MemberCountDetail> getAllRefundData(Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllRefundData(paramMap);
	}

	public List<MemberCountDetail> getAllTodayCostData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllTodayCostData(paramMap);
	}

	public List<MemberCountDetail> getAllYesterdayCostData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllYesterdayCostData(paramMap);
	}

	public List<MemberCountDetail> getAllThisMonthCostData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllThisMonthCostData(paramMap);
	}

	public List<MemberCountDetail> getAllLastMonthCostData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllLastMonthCostData(paramMap);
	}

	public List<MemberCountDetail> getAllTodayNoPayData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllTodayNoPayData(paramMap);
	}

	public List<MemberCountDetail> getAllYesterdayNoPayData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllYesterdayNoPayData(paramMap);
	}

	public List<MemberCountDetail> getAllThisMonthNoPayData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllThisMonthNoPayData(paramMap);
	}

	public List<MemberCountDetail> getAllLastMonthNoPayData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllLastMonthNoPayData(paramMap);
	}

	public List<MemberCountDetail> getAllTodayRefundData(
			Map<String, Object> paramMap) {
		return this.memberCountDetailMapper.getAllTodayRefundData(paramMap);
	}

	public List<MemberCountDetail> getAllYesterdayRefundData(
			Map<String, Object> paramMap) {
		return this.memberCountDetailMapper.getAllYesterdayRefundData(paramMap);
	}

	public List<MemberCountDetail> getAllThisMonthRefundData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllThisMonthRefundData(paramMap);
	}

	public List<MemberCountDetail> getAllLastMonthRefundData(
			Map<String, Object> paramMap) {
		return memberCountDetailMapper.getAllLastMonthRefundData(paramMap);
	}
	
}
