package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.member.MemberBehavior;
import com.iber.portal.vo.member.MemberBehaviorVo;

public interface MemberBehaviorMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(MemberBehavior record);

    MemberBehavior selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberBehavior record);

    int updateByPrimaryKey(MemberBehavior record);

	List<MemberBehavior> getMemberBehaviorList(Map<String, Object> paramMap);

	int getMemberBehaviorNum(Map<String, Object> paramMap);
	
	List<MemberBehavior> selectByBehaviorId(@Param("behaviorId") Integer behaviorId);

	int getCountByBehaviorId(@Param("behaviorId") Integer behaviorId);

	List<MemberBehavior> getByBehaviorIdAndCanAdd(@Param("behaviorId")Integer behaviorId,
			@Param("canAdd")Integer canadd);

	List<MemberBehavior> getVaildBehaviorByTypeId(@Param("behaviorId")String parentId,
			@Param("isMemberComplain")String isMemberComplain);

	/**
	 * 根据type查询行为子分类
	 * @param type
	 * @return
	 */
	MemberBehavior getByType(@Param("type") String type); 
	
	List<MemberBehavior> getRescueBehaviorTypeList(Map<String, Object> paramMap);
	
}