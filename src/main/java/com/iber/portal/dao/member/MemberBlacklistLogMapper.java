package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.member.MemberBlacklistLog;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistLogDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface MemberBlacklistLogMapper {
	
	int insert(MemberBlacklistLog record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(MemberBlacklistLog record);
	
	int updateByPrimaryKeySelective(MemberBlacklistLog record);
	
	MemberBlacklistLog selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberBlacklistLog> queryPageList(Map<String, Object> paramMap);
}
