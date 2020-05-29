package com.iber.portal.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.dao.base.MemberChargeLogMapper;
import com.iber.portal.model.base.MemberChargeLog;

@Service
public class MemberChargeLogService {

	@Autowired
	private MemberChargeLogMapper memberChargeLogMapper;

	public int deleteByPrimaryKey(Integer id) {
		return memberChargeLogMapper.deleteByPrimaryKey(id);
	}

	public int insert(MemberChargeLog record) {
		return memberChargeLogMapper.insert(record);
	}

	public int insertSelective(MemberChargeLog record) {
		return memberChargeLogMapper.insertSelective(record);
	}

	public MemberChargeLog selectByPrimaryKey(Integer id) {
		return memberChargeLogMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(MemberChargeLog record) {
		return memberChargeLogMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(MemberChargeLog record) {
		return memberChargeLogMapper.updateByPrimaryKey(record);
	}

	public List<MemberChargeLog> selectByOrderId(String objId) {
		return memberChargeLogMapper.selectByOrderId(objId);
	}

	public List<MemberChargeLog> selectByRefundId(Integer refundId) {
		return memberChargeLogMapper.selectByRefundId(refundId);
	}

	public void updateIsRefund(Map<String, Object> paramMap) {
		memberChargeLogMapper.updateIsRefund(paramMap);
	}
	
	//原路退款明细表
	public List<MemberChargeLog> memberOriginalRefundList(Map<String, Object> map){
		return memberChargeLogMapper.getAllMemberOriginalRefund(map);
	}
	
	public int memberOriginalRefundTotal(Map<String, Object> map){
	
		return memberChargeLogMapper.getAllMemberOriginalRefundTotal(map);
	}
	//手动退款明细表
	
	public List<MemberChargeLog> memberManualRefundList(Map<String,Object> map){
		
		return memberChargeLogMapper.getAllMemberManualRefund(map);
	}
	
	public int memberManualRefundTotal(Map<String, Object> map){
		
		return memberChargeLogMapper.getAllMemberManualRefundTotal(map);
	}
	

}
