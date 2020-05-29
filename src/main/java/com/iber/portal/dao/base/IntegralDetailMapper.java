package com.iber.portal.dao.base;

import java.util.List;

import com.iber.portal.model.base.IntegralDetail;
import com.iber.portal.vo.member.MemberBehaviorVo;

public interface IntegralDetailMapper {

	int insert(IntegralDetail intergralDetail);
	
    List<MemberBehaviorVo> getAllAddBehaviorInfo();

}
