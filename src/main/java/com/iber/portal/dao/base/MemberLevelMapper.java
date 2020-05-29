package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;
import com.iber.portal.model.base.MemberLevel;

public interface MemberLevelMapper {
	
	int insert(MemberLevel record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(MemberLevel record);
	
	int updateByPrimaryKeySelective(MemberLevel record);
	
	MemberLevel selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberLevel> queryPageList(Map<String, Object> paramMap);
	
	List<MemberLevel> selectAllMemberLevel();
	
	/**
	 * 根据等级编码查询会员等级
	 * @param levelCode
	 * @return
	 */
	MemberLevel selectByMemberLevel(Integer levelCode);
	
	Integer selectMemberLevelByContributeValue(Integer memberId);
}
