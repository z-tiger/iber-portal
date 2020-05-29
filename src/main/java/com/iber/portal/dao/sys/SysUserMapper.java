package com.iber.portal.dao.sys;

import com.iber.portal.model.sys.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);
    
    SysUser selectByAccount(String account);

    //    List<SysUser> getAll(PageLimitByUser pLimit);
    List<SysUser> getAll(Map<String, Object> map);
    
    int getAllNum(Map<String, Object> map);

    List<SysUser> getEntepriseSysUserList(Map<String, Object> map);

    int getTotalEnterpriseSysUser(Map<String, Object> map);
    
    int updatePwd(@Param("id") Integer id, @Param("np") String newPwd);

	List<SysUser> selectAllByName(@Param("name")String name);

	List<SysUser> selectAllOnTheJob();

    SysUser selectSysUserByphone(String phone);

    SysUser getSysUserByIdAndPassword(int id, String password);


}