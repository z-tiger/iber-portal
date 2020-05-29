package com.iber.portal.service.deposit;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.iber.portal.dao.deposit.DepositMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.deposit.Deposit;

@Service
public class DepositService {
	@Autowired
	private DepositMapper depositMapper;
	
	public List<Deposit> selectAll(HashMap<String, Object> map) throws ServiceException{
		return depositMapper.selectAll(map);
	}

	public int selectAllRecords(HashMap<String, Object> map) throws ServiceException{
		return depositMapper.selectAllRecords(map);
	}

	public int updateDeposit(Deposit deposit) {
		return depositMapper.updateDeposit(deposit);
	}

	public int addDeposit(Deposit deposit) {
		return depositMapper.addDeposit(deposit);
	}

	public int deleteByPrimaryKey(int id) {
		return depositMapper.deleteByPrimaryKey(id);
	}
	public List<Deposit> getAllInfo(){
		return depositMapper.getAllInfo();
	}

	public Deposit selectByMemberLevelAndDriverAge(
			String memberLevel, String driverAge) {
		return depositMapper.selectByMemberLevelAndDriverAge(memberLevel, driverAge);
	}

	public Deposit selectByMemberLevelAndzmxyIntegral(String memberLevel,
			String sesameCredit) {
		return depositMapper.selectByMemberLevelAndzmxyIntegral(memberLevel, sesameCredit);
	}

	public Deposit selectDepositByMemberLevel(
			Integer levelCode) {
		return depositMapper.selectDepositByMemberLevel(levelCode);
	}
}
