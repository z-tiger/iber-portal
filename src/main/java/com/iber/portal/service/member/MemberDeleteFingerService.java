package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.member.MemberDeleteFingerMapper;
import com.iber.portal.model.member.MemberDeleteFinger;


@Service("memberDeleteFingerService")
public class MemberDeleteFingerService {
	private final static Logger log= Logger.getLogger(MemberCountDetailService.class);
	
	@Autowired
	private MemberDeleteFingerMapper memberDeleteFingerMapper;
	
	public int insertSelective(MemberDeleteFinger record){
		return memberDeleteFingerMapper.insertSelective(record);
	}
	
	public List<MemberDeleteFinger> selectAll(Map<String,Object> map){
		return memberDeleteFingerMapper.selectAll(map);
	}
	
	public int selectAllRecords(Map<String,Object> map){
		return memberDeleteFingerMapper.selectAllRecords(map);
	}
}
