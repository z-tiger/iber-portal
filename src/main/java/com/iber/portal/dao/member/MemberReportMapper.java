package com.iber.portal.dao.member;

import com.iber.portal.model.member.MemberReport;
import com.iber.portal.vo.member.MemberLastOrderVo;
import com.iber.portal.vo.member.MemberReportRelationVo;
import com.iber.portal.vo.member.MemberReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberReportMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(MemberReport record);

	int insertSelective(MemberReport record);

	MemberReport selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(MemberReport record);

	int updateByPrimaryKey(MemberReport record);

	List<MemberReportVo> queryPageList(Map<String, Object> paramMap);

	int getRecordNum(Map<String, Object> paramMap);

	MemberLastOrderVo getLastCarOrderInfo(@Param("lpn") String lpn,
			@Param("memberName") String memberName,
			@Param("memberPhone") String memberPhone);

	MemberLastOrderVo getLastChargingOrderInfo(@Param("parkId") String parkId,
			@Param("parkNo") String parkNo,
			@Param("memberName") String memberName, @Param("phone") String phone);

	List<MemberReportRelationVo> getRelationVo(Map<String, Object> paramMap);

	MemberReportVo selectDetailById(@Param("id") Integer id);

	int getRelationVoNum(Map<String, Object> paramMap);

	int getMemberRecords(@Param("name") String name,@Param("phone")  String phone);
	
	int selectByOrderId(String orderId);

	int selectByOrderIds(List<String> orderIds);
}