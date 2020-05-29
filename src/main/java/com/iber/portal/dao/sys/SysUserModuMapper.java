package com.iber.portal.dao.sys;

import java.util.List;

import com.iber.portal.model.sys.SysUserModu;
import org.apache.ibatis.annotations.Param;


public interface SysUserModuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserModu record);

    int insertSelective(SysUserModu record);

    SysUserModu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserModu record);

    int updateByPrimaryKey(SysUserModu record);
    
    int deleteByUserId(Integer userId);
    
    List<SysUserModu> selectByEnabledUserId(Integer userId);
    /**
     * 根据用户id获得用户具有的所有功能
     * @param userId
     * @return
     */
	List<String> selectFunctionByUserId(Integer userId);

    /**
     * 根据用户id和角色id获得用户具有的所有功能
     * @param userId
     * @author zengfeiyue
     * @return
     */
    List<String> selectFunctionByUserId_roleIds(Integer userId,@Param("roleids") List<Integer> roleids);
	/**
	 * 根据用户id获得用户拥有的角色
	 * @param userId
	 * @return
	 */
	List<SysUserModu> selectRoleIdsByUserId(Integer userId);
}