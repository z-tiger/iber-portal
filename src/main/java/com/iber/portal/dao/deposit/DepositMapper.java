package com.iber.portal.dao.deposit;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.deposit.Deposit;


public interface DepositMapper {

	List<Deposit> selectAll(HashMap<String, Object> map);

	int selectAllRecords(HashMap<String, Object> map);

	int updateDeposit(Deposit deposit);

	int addDeposit(Deposit deposit);

	int deleteByPrimaryKey(int id);
	
	List<Deposit> getAllInfo();

	Deposit selectByMemberLevelAndDriverAge(@Param("memberLevel")String memberLevel, @Param("driverAge")String driverAge);
	
	/**
	 * 根据会员等级以及芝麻信用查询会员应缴押金
	 * @param memberLevel
	 * @param zmxyIntegral
	 * @return
	 */
	Deposit selectByMemberLevelAndzmxyIntegral(@Param("memberLevel")String memberLevel,@Param("sesameCredit")String sesameCredit);

	Deposit selectDepositByMemberLevel(Integer levelCode);
   
}