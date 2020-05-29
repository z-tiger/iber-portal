package com.iber.portal.service.sys;

import com.iber.portal.common.PageLimit;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.sys.SysRoleMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.sys.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysRoleService {

	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	public int insertSelective(SysRole model) throws ServiceException {
		int len;
		try {
			len = sysRoleMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = sysRoleMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(SysRole model) throws ServiceException {
		int len;
		try {
			len = sysRoleMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public SysRole selectByPrimaryKey(int id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}
	
	public List<SysRole> getAll(PageLimit pLimit) {
		return sysRoleMapper.getAll(pLimit);
	}
	
	public List<SysRole> getAllRole() {
		return sysRoleMapper.getAllRole();
	}

	public List<SysRole> getEnterpriseAllRole(){
	    return sysRoleMapper.getEnterpriseRole();
    }
	
	public int getAllNum(){
    	return sysRoleMapper.getAllNum();
    }
	
	public Pager<SysRole> getAll(int first_page, int page_size) {
		List<SysRole> listObj = getAll(new PageLimit(first_page, page_size));
		Pager<SysRole> pager = new Pager<SysRole>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum());
		return pager;
	}
}
