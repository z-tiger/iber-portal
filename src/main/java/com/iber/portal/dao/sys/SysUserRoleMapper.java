package com.iber.portal.dao.sys;

import java.util.List;

import com.iber.portal.common.SysUserRoleQueryParam;
import com.iber.portal.model.sys.SysUserRole;


public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);
    
    int deleteByUserId(Integer userId);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    List<SysUserRole> selectByUserId(Integer userId);
    
    List<SysUserRole> selectByUserIdRoleId(SysUserRole record);
    
    List<SysUserRole> selectByNotUserIdRoleId(SysUserRoleQueryParam record);
}