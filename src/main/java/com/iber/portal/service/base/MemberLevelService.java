package com.iber.portal.service.base;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberLevelMapper;
import com.iber.portal.model.base.MemberLevel;


@Service("memberLevelService")
public class MemberLevelService{

	private final static Logger log= Logger.getLogger(MemberLevelService.class);
	
	@Autowired
    private MemberLevelMapper  dao;

		
	public int insert(MemberLevel record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(MemberLevel record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberLevel record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberLevel selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<MemberLevel> queryPageList(Map<String, Object> paramMap){
		List<MemberLevel> listObj = dao.queryPageList(paramMap);
		Pager<MemberLevel> pager = new Pager<MemberLevel>();
		pager.setDatas(listObj);
		pager.setTotalCount(dao.getAllNum(paramMap));
		return pager;
	}
	
	public List<MemberLevel> selectAllMemberLevel(){
		return dao.selectAllMemberLevel();
	}
	
	public MemberLevel selectByMemberLevel(Integer levelCode) {
		return dao.selectByMemberLevel(levelCode);
	}
	
	public Integer selectMemberLevelByContributeValue(Integer memberId) {
		return dao.selectMemberLevelByContributeValue(memberId);
	}
}
