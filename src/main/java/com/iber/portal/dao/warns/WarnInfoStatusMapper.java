package com.iber.portal.dao.warns;



import com.iber.portal.model.warns.WarnInfoStatus;

public interface WarnInfoStatusMapper {
    
    int insert(WarnInfoStatus record);

    int insertSelective(WarnInfoStatus record);

    WarnInfoStatus selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarnInfoStatus record);

    int updateByPrimaryKey(WarnInfoStatus record);

    WarnInfoStatus selectByPrimaryId(Integer id);


}