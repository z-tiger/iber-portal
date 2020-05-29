package com.iber.portal.dao.member;

import com.iber.portal.model.member.MemberFreezeLog;
import com.iber.portal.vo.member.MemberFreezeLogVo;

import java.util.List;
import java.util.Map;

public interface MemberFreezeLogMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MemberFreezeLog record);

    int insertSelective(MemberFreezeLog record);

    MemberFreezeLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberFreezeLog record);

    int updateByPrimaryKey(MemberFreezeLog record);

    List<MemberFreezeLogVo> selectAllFreezeLog(Map<String, Object> map);

    int selecAllRecords(Map<String, Object> map);
}