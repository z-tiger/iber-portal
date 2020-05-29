package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.member.MemberBehaviorTypeMapper;
import com.iber.portal.model.member.MemberBehaviorType;

@Service
public class MemberBehaviorTypeService {
    private final static Logger log= Logger.getLogger(MemberBehaviorTypeService.class);
    
	public static final String BEHAVIOR_TYPE_COMMON = "0";
	public static final String BEHAVIOR_TYPE_RESCUE = "1";
	public static final String BEHAVIOR_TYPE_BAD = "2";
	
	@Autowired
    private MemberBehaviorTypeMapper memberBehaviorTypeMapper;
	public List<MemberBehaviorType> getMemberBehaviorTypeList(
			Map<String, Object> paramMap) {
		return memberBehaviorTypeMapper.getMemberBehaviorTypeList(paramMap);
	}

	public int getMemberBehaviorTypeNum(Map<String, Object> paramMap) {
		return memberBehaviorTypeMapper.getMemberBehaviorTypeNum(paramMap);
	}

	public int updateByPrimaryKeySelective(MemberBehaviorType type) {
		return memberBehaviorTypeMapper.updateByPrimaryKeySelective(type);
		
	}

	public int insertSelective(MemberBehaviorType type) {
		return memberBehaviorTypeMapper.insertSelective(type);
	}

	public int deleteByPrimaryKey(int id) {
		return memberBehaviorTypeMapper.deleteByPrimaryKey(id);
	}

	public List<MemberBehaviorType> selectBehaviorTypeById() {
		return memberBehaviorTypeMapper.selectBehaviorTypeById();
	}

	public MemberBehaviorType selectMemberBehaviorTypeByKey(Integer id) {
		return memberBehaviorTypeMapper.selectByPrimaryKey(id);
	}

	public List<MemberBehaviorType> getAll() {
		return memberBehaviorTypeMapper.getAll();
	}

	public List<MemberBehaviorType> getCanAddBehaviorTypeList() {
		return memberBehaviorTypeMapper.getCanAddBehaviorTypeList();
	}

	public List<MemberBehaviorType> getValidBehaviorType(String isMemberComplain) {
		return memberBehaviorTypeMapper.getValidBehaviorType(isMemberComplain);
	}
}
