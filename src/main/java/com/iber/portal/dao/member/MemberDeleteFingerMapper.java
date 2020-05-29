package com.iber.portal.dao.member;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.member.MemberDeleteFinger;

public interface MemberDeleteFingerMapper {
	Integer insertSelective(MemberDeleteFinger record);
	List<MemberDeleteFinger> selectAll(Map<String,Object> map);
	Integer selectAllRecords(Map<String,Object> map);
}
