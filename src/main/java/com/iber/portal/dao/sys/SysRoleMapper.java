package com.iber.portal.dao.sys;

import com.iber.portal.common.PageLimit;
import com.iber.portal.model.sys.SysRole;

import java.util.List;


public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
    
    List<SysRole> getAll(PageLimit pLimit);
    
    List<SysRole> getAllRole();

    List<SysRole> getEnterpriseRole();
    
    int getAllNum(); 
    
    List<SysRole> selectByUserId(Integer userId);
}