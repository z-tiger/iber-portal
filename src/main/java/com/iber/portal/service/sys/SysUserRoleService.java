package com.iber.portal.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.sys.SysUserRoleMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.sys.SysUserRole;

@Service
public class SysUserRoleService {

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	public int insertSelective(SysUserRole model) throws ServiceException {
		int len;
		try {
			len = sysUserRoleMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = sysUserRoleMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	//根据用户ID删除所有角色
	public int deleteByUserid(int userId) throws ServiceException {
		int len;
		try {
			len = sysUserRoleMapper.deleteByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(SysUserRole model) throws ServiceException {
		int len;
		try {
			len = sysUserRoleMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public SysUserRole selectByPrimaryKey(int id) {
		return sysUserRoleMapper.selectByPrimaryKey(id);
	}	
}
