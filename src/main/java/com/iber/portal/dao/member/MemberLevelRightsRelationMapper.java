package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.member.MemberLevelRightsRelation;
public interface MemberLevelRightsRelationMapper {
	
	int insert(MemberLevelRightsRelation record);
	
	int deleteByPrimaryKey(Integer id);
	
	int deleteByLevelId(Integer levelId);
	
	int updateByPrimaryKey(MemberLevelRightsRelation record);
	
	int updateByPrimaryKeySelective(MemberLevelRightsRelation record);
	
	MemberLevelRightsRelation selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberLevelRightsRelation> queryPageList(Map<String, Object> paramMap);
}
