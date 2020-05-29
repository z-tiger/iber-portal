package com.iber.portal.service.memberMail;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.invoice.MemberMailInfoMapper;
import com.iber.portal.model.invoice.MemberMailInfo;
import com.iber.portal.vo.memberMail.MemberMailInfoVO;

/**
 * 邮寄地址管理 service
 * 
 * @date create 2017/11/11 by zixb
 * 
 */
@Service
public class MemberMailService {

	@Autowired
	private MemberMailInfoMapper memberMailInfoMapper;

	public Pager<MemberMailInfoVO> queryPageList(Map<String, Object> paramMap) {
		List<MemberMailInfoVO> listObj = memberMailInfoMapper.queryPageList(paramMap);
		Pager<MemberMailInfoVO> pager = new Pager<MemberMailInfoVO>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	private int getAllNum(Map<String, Object> paramMap) {
		return memberMailInfoMapper.getAllNum(paramMap);
	}

	public MemberMailInfo selectMemberMailById(Integer id) {
		return memberMailInfoMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(MemberMailInfo record){
		return memberMailInfoMapper.updateByPrimaryKeySelective(record);
	}
}
