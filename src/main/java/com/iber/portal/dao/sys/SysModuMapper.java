package com.iber.portal.dao.sys;

import com.iber.portal.model.sys.SysModu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysModuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysModu record);

    int insertSelective(SysModu record);

    SysModu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysModu record);

    int updateByPrimaryKey(SysModu record);
    
    List<SysModu> getAll();
    
    List<SysModu> getAllNot();
    
    List<SysModu> showModuList();

    List<SysModu> showEnterpriseModuList();
    
    List<SysModu> selectUserModuByUserId(Integer userId);
    
    List<SysModu> selectByPid(Integer pid);
    
    int deleteByPid(Integer pid);

	SysModu selectByNameAndPid(@Param("pid")Integer pid, @Param("name")String name);
	/**
	 * 根据link查找菜单
	 * @param values
	 * @return
	 */
	List<SysModu> selectModuByLinks(List<String> values);
}