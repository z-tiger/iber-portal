package com.iber.portal.dao.insurance;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.insurance.InsuranceAttachment;

public interface InsuranceAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(InsuranceAttachment record);

    int insertSelective(InsuranceAttachment record);

    InsuranceAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(InsuranceAttachment record);

    int updateByPrimaryKey(InsuranceAttachment record);

	List<InsuranceAttachment> getAllAttachment(Map<String, Object> paramMap);
}