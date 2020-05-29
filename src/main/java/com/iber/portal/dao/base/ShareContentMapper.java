package com.iber.portal.dao.base;

import java.util.List;

import com.iber.portal.model.base.ShareContent;

public interface ShareContentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareContent record);

    int insertSelective(ShareContent record);

    ShareContent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareContent record);

    int updateByPrimaryKey(ShareContent record);
    
    List<ShareContent> selectAllShareContent();
}