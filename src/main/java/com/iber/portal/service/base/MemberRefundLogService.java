package com.iber.portal.service.base;

import com.iber.portal.dao.base.MemberRefundLogMapper;
import com.iber.portal.model.base.MemberRefundLog;
import com.iber.portal.vo.member.ChargeLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberRefundLogService {

	@Autowired
	private MemberRefundLogMapper refundLogMapper;
	
	public List<MemberRefundLog> selectAllRefundLog(Map<String, Object> map){
		return refundLogMapper.selectAllRefundLog(map);
	}
    
	public int selectAllRefundLogRecords(Map<String, Object> map){
		return refundLogMapper.selectAllRefundLogRecords(map);
	}
	
	public MemberRefundLog selectByPrimaryKey(Integer id){
		return refundLogMapper.selectByPrimaryKey(id);
	}
	
	public int updateByPrimaryKeySelective(MemberRefundLog record){
		return refundLogMapper.updateByPrimaryKeySelective(record);
	}

	public MemberRefundLog selectRefundLogByMemberId(Integer memberId) {
		return refundLogMapper.selectRefundLogByMemberId(memberId);
	}

    public List<ChargeLogVo> selectAllChargeLogByRefundId(Integer id) {
		return refundLogMapper.selectAllChargeLogByRefundId(id);
    }

	public List<Map<String,Object>> selectAllRejectLogByMemberId(Integer id) {
		return refundLogMapper.selectAllRejectLogByMemberId(id);
	}
}
