package com.iber.portal.dao.enterprise;

import com.iber.portal.model.enterprise.EnterpriseExcessApply;
import com.iber.portal.vo.enterprise.EnterpriseExcessApplyVo;

import java.util.List;
import java.util.Map;

public interface EnterpriseExcessApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseExcessApply record);

    int insertSelective(EnterpriseExcessApply record);

    EnterpriseExcessApply selectByPrimaryKey(Integer id);

    List<EnterpriseExcessApplyVo> selectEnterpriseExcessApplyList(Map<String, Object> map);

    int selectEnterpriseExcessApplyTotalNumber(Map<String, Object> map);

    int updateByPrimaryKeySelective(EnterpriseExcessApply record);

    int updateByPrimaryKey(EnterpriseExcessApply record);
}