package com.iber.portal.service.base;

import com.iber.portal.dao.base.MemberCardMapper;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.vo.base.MemberCardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MemberCardService {

	@Autowired
	private MemberCardMapper memberCardMapper;

	public int deleteByPrimaryKey(Integer id) {
		return memberCardMapper.deleteByPrimaryKey(id);
	}

	public int insert(MemberCard record) {
		return memberCardMapper.insert(record);
	}

	public int insertSelective(MemberCard record) {
		return memberCardMapper.insertSelective(record);
	}

	public MemberCard selectByPrimaryKey(Integer id) {
		return memberCardMapper.selectByPrimaryKey(id);
	}
	
	public MemberCard selectByMemberId(Integer memberId) {
		return memberCardMapper.selectByMemberId(memberId);
	}

	public int updateByPrimaryKeySelective(MemberCard record) {
		return memberCardMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(MemberCard record) {
		return memberCardMapper.updateByPrimaryKey(record);
	}
	
	public List<MemberCardVo> selectAll(Map<String, Object> map){
		return memberCardMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return memberCardMapper.selectAllRecords(map);
	}
	
	public int memberCardFrozenThaw(int memberId,  String accoutStatus){
		return memberCardMapper.memberCardFrozenThaw(memberId, accoutStatus);
	}
	public int updateBlockingReason(Integer memberId,String blockingReason){
		return memberCardMapper.updateBlockingReason(memberId,blockingReason);
	}
	public int deleteBlockingReason(Integer memberId){
		return memberCardMapper.deleteBlockingReason(memberId);
	}
	
	
	/** 增减贡献值 **/
	public int increContributedVal(Integer memberId, Integer contributedValDelta){
		return memberCardMapper.increContributedVal(memberId, contributedValDelta);
	}
	public int decreContributedVal(Integer memberId, Integer contributedValDelta){
		return memberCardMapper.decreContributedVal(memberId, contributedValDelta);
	}

}
