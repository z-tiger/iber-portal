package com.iber.portal.service.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.UpdateRoleModuParam;
import com.iber.portal.dao.sys.SysRoleModuMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.sys.SysRoleModu;
import com.iber.portal.model.sys.SysUserModu;

@Service
public class SysRoleModuService {
	
	@Autowired
	private SysRoleModuMapper sysRoleModuMapper;

	public int insertSelective(SysRoleModu model) throws ServiceException {
		int len;
		try {
			len = sysRoleModuMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = sysRoleModuMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	public int deleteByRoleId(Integer roleId) throws ServiceException {
		int len;
		try {
			len = sysRoleModuMapper.deleteByRoleId(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(SysRoleModu model) throws ServiceException {
		int len;
		try {
			len = sysRoleModuMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public SysRoleModu selectByPrimaryKey(int id) {
		return sysRoleModuMapper.selectByPrimaryKey(id);
	}
	
	public List<SysRoleModu> selectByRoleId(int roleId) {
		return sysRoleModuMapper.selectByRoleId(roleId);
	}
	
	public int updateByModuIds(UpdateRoleModuParam record) throws ServiceException {
		int len = 0;
		try {
			sysRoleModuMapper.deleteByRoleId(record.getRoleId());			
			int[] moduIds = record.getModuIds();			
			for (int i=0; i < moduIds.length; i++) {
				SysRoleModu newModel = new SysRoleModu();
				newModel.setRoleId(record.getRoleId());
				newModel.setModuId(moduIds[i]);
				newModel.setIsAuth("1");
				newModel.setCreateUser("admin");
				newModel.setCreateTime(new Date());
				insertSelective(newModel);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public void updateSysUserModu(int roleId, List<Integer> moduIdList,
			List<Map<Integer, String>> lists) throws ServiceException {
		try {
			sysRoleModuMapper.deleteByRoleId(roleId);
			for (int i = 0; i < moduIdList.size(); i++) {
				SysRoleModu newModel = new SysRoleModu();
				newModel.setRoleId(roleId);
				newModel.setModuId(moduIdList.get(i));
				newModel.setIsAuth("1");
				newModel.setCreateUser("admin");
				newModel.setCreateTime(new Date());
				for (Map<Integer, String> map : lists) {
					if (map.get(moduIdList.get(i))!=null) {
						newModel.setFunction(map.get(moduIdList.get(i)));
						
					}
				}
				insertSelective(newModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public List<String> selectFuntionByRoleIds(List<Integer> roleIds) {
		return sysRoleModuMapper.selectFuntionByRoleIds(roleIds);
	}
}
