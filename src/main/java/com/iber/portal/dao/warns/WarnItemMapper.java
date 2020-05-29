package com.iber.portal.dao.warns;

import java.util.List;

import com.iber.portal.model.warns.WarnItem;

public interface WarnItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarnItem record);

    int insertSelective(WarnItem record);

    WarnItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarnItem record);

    int updateByPrimaryKey(WarnItem record);
    
    List<WarnItem> selectAll();
}