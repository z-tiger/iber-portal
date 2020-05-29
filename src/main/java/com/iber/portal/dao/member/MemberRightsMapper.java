package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.member.MemberRights;
import com.iber.portal.vo.member.MemberCouponVo;


public interface MemberRightsMapper {
	
	int insert(MemberRights record);
	int insertSelective(MemberRights record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(MemberRights record);
	
	int updateByPrimaryKeySelective(MemberRights record);
	
	MemberRights selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberRights> queryPageList(Map<String, Object> paramMap);
	
	List<MemberRights> queryAllMemberRights() ;

	
	List<MemberCouponVo> getMemberCoupons();
	
	List<MemberRights> queryMemberRigthtsByMemberLevel(Integer levelCode);
}
