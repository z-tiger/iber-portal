package com.iber.portal.service.member;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.iber.portal.common.Pager;
import com.iber.portal.dao.member.MemberBlacklistLogMapper;
import com.iber.portal.model.member.MemberBlacklistLog;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistLogService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("memberBlacklistLogService")
public class MemberBlacklistLogService{

	private final static Logger log= Logger.getLogger(MemberBlacklistLogService.class);
	
	@Autowired
    private MemberBlacklistLogMapper  dao;

		
	public int insert(MemberBlacklistLog record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(MemberBlacklistLog record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberBlacklistLog record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberBlacklistLog selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<MemberBlacklistLog> queryPageList(Map<String, Object> paramMap){
		List<MemberBlacklistLog> listObj = dao.queryPageList(paramMap);
		Pager<MemberBlacklistLog> pager = new Pager<MemberBlacklistLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
}
