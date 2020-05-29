package com.iber.portal.service.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberCardMapper;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.member.MemberBlacklistMapper;
import com.iber.portal.dao.member.MemberContributedDetailMapper;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.model.member.MemberBlacklist;
import com.iber.portal.model.member.MemberContributedDetail;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("memberBlacklistService")
public class MemberBlacklistService{

	@Autowired
    private MemberBlacklistMapper  dao;
	
	@Autowired
	private MemberCardMapper memberCardMapper;
	
	@Autowired
	private MemberContributedDetailMapper memberContributedDetailMapper;

	@Autowired
	private MemberMapper memberMapper;
	
	public int insert(MemberBlacklist record, String objId, String type) {
		int insert = dao.insert(record);
		if (insert > 0) {
			MemberCard memberCard = memberCardMapper.selectByMemberId(record.getMemberId());
			// 贡献值清零
			memberCardMapper.updateContributedVal(record.getMemberId(), 0);
			//等级设置为黑名单
			memberMapper.updateLevelCodeById(record.getMemberId(), 0); 
			//添加贡献值明细
			MemberContributedDetail detail = new MemberContributedDetail();
			detail.setCreateTime(new Date());
			detail.setMemberId(record.getMemberId());
			detail.setType(type);
			detail.setContributedValDelta(memberCard.getContributedVal());
			detail.setObjId(objId);
			memberContributedDetailMapper.insert(detail);
		}
		// 添加贡献值明细
		return insert;
	}

	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(MemberBlacklist record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberBlacklist record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberBlacklist selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<MemberBlacklist> queryPageList(Map<String, Object> paramMap){
		List<MemberBlacklist> listObj = dao.queryPageList(paramMap);
		Pager<MemberBlacklist> pager = new Pager<MemberBlacklist>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	public int getRecordsByMemberId(Integer memberId) {
		return dao.getRecordsByMemberId(memberId);
	}
}
