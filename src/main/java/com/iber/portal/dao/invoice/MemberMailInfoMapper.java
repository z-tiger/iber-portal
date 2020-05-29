package com.iber.portal.dao.invoice;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.invoice.MemberMailInfo;
import com.iber.portal.vo.memberMail.MemberMailInfoVO;

public interface MemberMailInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberMailInfo record);

    int insertSelective(MemberMailInfo record);

    MemberMailInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberMailInfo record);

    int updateByPrimaryKey(MemberMailInfo record);

	List<MemberMailInfoVO> queryPageList(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
}