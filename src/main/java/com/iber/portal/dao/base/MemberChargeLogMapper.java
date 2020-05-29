package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.MemberChargeLog;

public interface MemberChargeLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberChargeLog record);

    int insertSelective(MemberChargeLog record);

    MemberChargeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberChargeLog record);

    int updateByPrimaryKey(MemberChargeLog record);
    
    /**
     * 根据charge_id查询充值订单
     * @param objId
     * @return
     */
	List<MemberChargeLog> selectByOrderId(String objId);

	
	/**
	 * 根据退款id查询充值记录
	 * @param refundId
	 * @return
	 */
	List<MemberChargeLog> selectByRefundId(Integer refundId);
	
	/**
	 * 将充值记录更新未退款
	 * @param paramMap
	 */
	void updateIsRefund(Map<String, Object> paramMap);
	
	/**
	 * 根据退款id查询会员充值记录
	 * @param rid
	 * @return
	 */
	List<MemberChargeLog> selectLogsByRefundId(int rid);
	
	//原路退款明细记录
	List<MemberChargeLog> getAllMemberOriginalRefund(Map<String, Object> map);
	
	int getAllMemberOriginalRefundTotal(Map<String, Object> map); 
	
	//手动退款明细记录
	List<MemberChargeLog> getAllMemberManualRefund(Map<String, Object> map);
	
	int getAllMemberManualRefundTotal(Map<String, Object> map);
	
}