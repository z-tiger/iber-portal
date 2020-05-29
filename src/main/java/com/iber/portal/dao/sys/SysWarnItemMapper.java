package com.iber.portal.dao.sys;

import com.iber.portal.model.sys.SysWarnItem;


public interface SysWarnItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysWarnItem record);

    int insertSelective(SysWarnItem record);

    SysWarnItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysWarnItem record);

    int updateByPrimaryKey(SysWarnItem record);
    
    SysWarnItem selectByCode(String code);
}