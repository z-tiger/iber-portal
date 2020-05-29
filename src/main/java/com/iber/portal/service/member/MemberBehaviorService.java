package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.member.MemberBehaviorMapper;
import com.iber.portal.model.member.MemberBehavior;

@Service
public class MemberBehaviorService {
	private final static Logger log= Logger.getLogger(MemberBehaviorService.class);
	
	@Autowired
    private MemberBehaviorMapper memberBehaviorMapper;
	public List<MemberBehavior> getMemberBehaviorList(Map<String, Object> paramMap) {
	    return memberBehaviorMapper.getMemberBehaviorList(paramMap);
	}
	public int getMemberBehaviorNum(Map<String, Object> paramMap) {
		return memberBehaviorMapper.getMemberBehaviorNum(paramMap);
	}
	public int updateByPrimaryKeySelective(MemberBehavior memberBehavior) {
		return memberBehaviorMapper.updateByPrimaryKeySelective(memberBehavior);
	}
	public int insertSelective(MemberBehavior memberBehavior) {
		return memberBehaviorMapper.insertSelective(memberBehavior);
	}
	public int deleteByPrimaryKey(int id) {
		return memberBehaviorMapper.deleteByPrimaryKey(id);
	}
	public List<MemberBehavior> getByBehaviorId(Integer id) {
		return memberBehaviorMapper.selectByBehaviorId(id);
	}
	public int getCountByBehaviorId(Integer id) {
		return memberBehaviorMapper.getCountByBehaviorId(id);
	}
	
	public MemberBehavior selectByPrimaryKey(Integer id){
		return memberBehaviorMapper.selectByPrimaryKey(id);
	}
	public List<MemberBehavior> getByBehaviorIdAndCanAdd(Integer behaviorId, Integer canadd) {
		return memberBehaviorMapper.getByBehaviorIdAndCanAdd(behaviorId,canadd);
	}
	public List<MemberBehavior> getVaildBehaviorByTypeId(String parentId,
			String isMemberComplain) {
		return memberBehaviorMapper.getVaildBehaviorByTypeId(parentId,isMemberComplain);
	}
	public List<MemberBehavior> getRescueBehaviorTypeList(Map<String, Object> paramMap) {
	    return memberBehaviorMapper.getRescueBehaviorTypeList(paramMap);
	}
}
