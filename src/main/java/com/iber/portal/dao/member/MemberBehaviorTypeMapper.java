package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.member.MemberBehaviorType;


public interface MemberBehaviorTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberBehaviorType record);

    int insertSelective(MemberBehaviorType record);

    MemberBehaviorType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberBehaviorType record);

	List<MemberBehaviorType> getMemberBehaviorTypeList(Map<String, Object> paramMap);

	int getMemberBehaviorTypeNum(Map<String, Object> paramMap);

	List<MemberBehaviorType> selectBehaviorTypeById();

	List<MemberBehaviorType> getAll();

	List<MemberBehaviorType> getCanAddBehaviorTypeList();

	List<MemberBehaviorType> getValidBehaviorType(@Param("isMemberComplain")String isMemberComplain);
}