package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.member.MemberBlacklist;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface MemberBlacklistMapper {
	
	int insert(MemberBlacklist record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(MemberBlacklist record);
	
	int updateByPrimaryKeySelective(MemberBlacklist record);
	
	MemberBlacklist selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberBlacklist> queryPageList(Map<String, Object> paramMap);

	int getRecordsByMemberId(@Param("memberId")Integer memberId);
}
