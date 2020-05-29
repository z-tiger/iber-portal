package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.MemberRefundWorderUsecar;

public interface MemberRefundWorderUsecarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberRefundWorderUsecar record);

    int insertSelective(MemberRefundWorderUsecar record);

    MemberRefundWorderUsecar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberRefundWorderUsecar record);
    int updateByOrderIdSelective(MemberRefundWorderUsecar record);
    int updateByPrimaryKey(MemberRefundWorderUsecar record);
    
    List<MemberRefundWorderUsecar> selectOrderList(Map<String, Object> map);
    
    int selectOrderListRecords(Map<String, Object> map);
    
    
    MemberRefundWorderUsecar selectUserDetailByOrderId(String orderId);
}