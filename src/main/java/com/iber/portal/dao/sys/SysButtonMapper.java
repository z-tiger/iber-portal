package com.iber.portal.dao.sys;

import java.util.List;

import com.iber.portal.common.PageLimit;
import com.iber.portal.model.sys.SysButton;

public interface SysButtonMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(SysButton record);

	int insertSelective(SysButton record);

	SysButton selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SysButton record);

	int updateByPrimaryKey(SysButton record);

	List<SysButton> getAllButton();

	List<SysButton> getAll(PageLimit pLimit);

	int getAllNum();
}