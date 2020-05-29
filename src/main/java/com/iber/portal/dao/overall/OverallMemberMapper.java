package com.iber.portal.dao.overall;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.overall.OverallMember;

public interface OverallMemberMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(OverallMember record);

    int insertSelective(OverallMember record);

    OverallMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverallMember record);

    int updateByPrimaryKey(OverallMember record);

	int insertBatch(List<OverallMember> pileList);

	List<OverallMember> selectInitAuditNum(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitRegisterNum(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitUserNum(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitRefund(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitBalance(@Param("currentTime")String currentTime);
	
	List<OverallMember> selectInitDeposit(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitChargeMemberNum(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitIncome(@Param("currentTime")String currentTime);

	List<OverallMember> selectInitOnline(@Param("currentTime")String currentTime);

	//List<OverallMember> selectInitOwing(@Param("currentTime")String currentTime);

	int getMemberRecords();

	//List<OverallMember> selectInitOwing30(@Param("currentTime")String currentTime);

}