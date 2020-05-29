package com.iber.portal.dao.base;

import java.util.List;

import com.iber.portal.model.base.MemberRefundWorderDetail;

public interface MemberRefundWorderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberRefundWorderDetail record);

    int insertSelective(MemberRefundWorderDetail record);

    MemberRefundWorderDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberRefundWorderDetail record);

    int updateByPrimaryKey(MemberRefundWorderDetail record);
    
    List<MemberRefundWorderDetail> selectMemberRefundWorderDetailByRid(Integer rid);
}