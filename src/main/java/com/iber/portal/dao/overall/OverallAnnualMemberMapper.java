package com.iber.portal.dao.overall;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.overall.OverallAnnualMember;


public interface OverallAnnualMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OverallAnnualMember record);

    int insertSelective(OverallAnnualMember record);

    OverallAnnualMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverallAnnualMember record);

    int updateByPrimaryKey(OverallAnnualMember record);

	List<OverallAnnualMember> selectByAnnualAndTimeType(@Param("annual")int annual, @Param("timeType")String timeType, @Param("timeNum")int timeNum);

	int insertBatch(List<OverallAnnualMember> memberList);

	List<OverallAnnualMember> selectInitAnnualAddMember(@Param("annual")int annual, @Param("month")int month);

	List<OverallAnnualMember> selectInitAnnualAddMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitDayAddMember(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitDayAddMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitAnnualLoseMember(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitAnnualLoseMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitDayLoseMember(@Param("annual")int annual, @Param("month")int month);

	List<OverallAnnualMember> selectInitDayLoseMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitDayAuditMember(@Param("annual")int annual, @Param("month")int month);

	List<OverallAnnualMember> selectInitDayAuditMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitAnnualAuditMember(@Param("annual")int annual, @Param("month")int month);

	List<OverallAnnualMember> selectInitAnnualAuditMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitAnnualBalanceOrDeposit(@Param("annual")int annual, @Param("month")int month, @Param("category")String category);
	
	List<OverallAnnualMember> selectInitAnnualBalanceOrDepositByQuarter(@Param("annual")int annual, @Param("quarter")int quarter, @Param("category")String category);

	List<OverallAnnualMember> selectInitDayBalanceOrDeposit(@Param("annual")int annual, @Param("month")int month, @Param("category")String category);
	
	List<OverallAnnualMember> selectInitDayBalanceOrDepositByQuarter(@Param("annual")int annual, @Param("quarter")int quarter,@Param("category")String category);

	List<OverallAnnualMember> selectInitAnnualIncome(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitAnnualIncomeByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitDayIncome(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitDayIncomeByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitAddUser(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitAddUserByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitAuditUser(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitAuditUserByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitDayTotalMember(@Param("annual")int annual, @Param("month")int month);

	List<OverallAnnualMember> selectInitDayTotalMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

	List<OverallAnnualMember> selectInitOverallAnnualMember(@Param("annual")int annual, @Param("month")int month);
	
	List<OverallAnnualMember> selectInitOverallAnnualMemberByQuarter(@Param("annual")int annual, @Param("quarter")int quarter);

}