package com.iber.portal.dao.ad;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.ad.AdAttachment;

public interface AdAttachmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdAttachment record);

    int insertSelective(AdAttachment record);

    AdAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdAttachment record);

    int updateByPrimaryKey(AdAttachment record);

	List<AdAttachment> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);

	List<AdAttachment> getPreview(Map<String, Object> paramMap);
}