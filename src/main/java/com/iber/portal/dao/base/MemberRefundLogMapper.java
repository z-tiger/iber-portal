package com.iber.portal.dao.base;

import com.iber.portal.model.base.MemberRefundLog;
import com.iber.portal.vo.member.ChargeLogVo;

import java.util.List;
import java.util.Map;

public interface MemberRefundLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberRefundLog record);

    int insertSelective(MemberRefundLog record);

    MemberRefundLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberRefundLog record);

    int updateByPrimaryKey(MemberRefundLog record);
    
    
    List<MemberRefundLog> selectAllRefundLog(Map<String, Object> map);
    
    int selectAllRefundLogRecords(Map<String, Object> map);
    
    int selectCustUsecarIsTrafficCitation(Integer memberId);
    
    int selectCustUsecartrafficCitationCharge(Integer memberId);

	MemberRefundLog selectRefundLogByMemberId(Integer memberId);

    List<ChargeLogVo> selectAllChargeLogByRefundId(Integer id);

    List<Map<String,Object>> selectAllRejectLogByMemberId(Integer id);
    
    int updateRecordByMap(Map<String, Object> map);
}