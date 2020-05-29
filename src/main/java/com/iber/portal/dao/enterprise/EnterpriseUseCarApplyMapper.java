package com.iber.portal.dao.enterprise;

import com.iber.portal.model.enterprise.EnterpriseUseCarApply;
import com.iber.portal.vo.enterprise.EnterpriseUseCarApplyVo;

import java.util.List;
import java.util.Map;

public interface EnterpriseUseCarApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseUseCarApply record);

    int insertSelective(EnterpriseUseCarApply record);

    EnterpriseUseCarApply selectByPrimaryKey(Integer id);

    List<EnterpriseUseCarApplyVo> selectEnterpriseUseCarApplyList( Map<String, Object> paramMap);

    int selectEnterpriseCarApplyListNumber(Map<String, Object> map);

    int updateByPrimaryKeySelective(EnterpriseUseCarApply record);

    int updateByPrimaryKey(EnterpriseUseCarApply record);
    
    int selectApplyRecords(Map<String, Object> paramMap);
}