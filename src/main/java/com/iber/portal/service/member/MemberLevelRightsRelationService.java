package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.member.MemberLevelRightsRelationMapper;
import com.iber.portal.model.member.MemberLevelRightsRelation;

@Service("memberLevelRightsRelationService")
public class MemberLevelRightsRelationService{

	private final static Logger log= Logger.getLogger(MemberLevelRightsRelationService.class);
	
	@Autowired
    private MemberLevelRightsRelationMapper  memberLevelRightsRelationMapper;

		
	public int insert(MemberLevelRightsRelation record){
		return memberLevelRightsRelationMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return memberLevelRightsRelationMapper.deleteByPrimaryKey(id) ;
	}
	
	public int deleteByLevelId(Integer levelId){
		return memberLevelRightsRelationMapper.deleteByLevelId(levelId) ;
	}
	
	public int updateByPrimaryKey(MemberLevelRightsRelation record){
		return memberLevelRightsRelationMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberLevelRightsRelation record){
		return memberLevelRightsRelationMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberLevelRightsRelation selectByPrimaryKey(Integer id){
		return memberLevelRightsRelationMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return memberLevelRightsRelationMapper.getAllNum(paramMap) ;
	}
	
	public Pager<MemberLevelRightsRelation> queryPageList(Map<String, Object> paramMap){
		List<MemberLevelRightsRelation> listObj = memberLevelRightsRelationMapper.queryPageList(paramMap);
		Pager<MemberLevelRightsRelation> pager = new Pager<MemberLevelRightsRelation>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
}
