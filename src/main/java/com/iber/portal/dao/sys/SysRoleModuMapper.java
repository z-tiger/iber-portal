package com.iber.portal.dao.sys;

import java.util.List;

import com.iber.portal.model.sys.SysRoleModu;


public interface SysRoleModuMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByRoleId(Integer roleId);

    int insert(SysRoleModu record);

    int insertSelective(SysRoleModu record);

    SysRoleModu selectByPrimaryKey(Integer id);
    
    List<SysRoleModu> selectByRoleId(Integer roleId);

    int updateByPrimaryKeySelective(SysRoleModu record);

    int updateByPrimaryKey(SysRoleModu record);
    /**
     * 根据角色id获得角色的所有功能
     * @param roleIds
     * @return
     */
	List<String> selectFuntionByRoleIds(List<Integer> roleIds);
}