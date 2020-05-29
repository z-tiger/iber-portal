package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.member.MemberRightsMapper;
import com.iber.portal.model.member.MemberRights;
import com.iber.portal.vo.member.MemberCouponVo;

@Service("memberRightsService")
public class MemberRightsService{

	private final static Logger log= Logger.getLogger(MemberRightsService.class);
	
	@Autowired
    private MemberRightsMapper  memberRightsMapper;

		
	public int insert(MemberRights record){
		return memberRightsMapper.insert(record) ;
	}
	
	public int  insertSelective(MemberRights record){
		return memberRightsMapper.insertSelective(record);
	}
	public int deleteByPrimaryKey(Integer id){
		return memberRightsMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(MemberRights record){
		return memberRightsMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberRights record){
		return memberRightsMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberRights selectByPrimaryKey(Integer id){
		return memberRightsMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return memberRightsMapper.getAllNum(paramMap) ;
	}
	
	public List<MemberRights> queryAllMemberRights(){
		return memberRightsMapper.queryAllMemberRights() ;
	}
	
	public Pager<MemberRights> queryPageList(Map<String, Object> paramMap){
		List<MemberRights> listObj = memberRightsMapper.queryPageList(paramMap);
		Pager<MemberRights> pager = new Pager<MemberRights>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	public List<MemberCouponVo> getMemberCoupons(){
		return memberRightsMapper.getMemberCoupons();
	}

	public List<MemberRights> queryMemberRigthtsByMemberLevel(Integer levelCode) {
		// TODO Auto-generated method stub
		return memberRightsMapper.queryMemberRigthtsByMemberLevel(levelCode);
	}
	
}
