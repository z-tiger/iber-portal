package com.iber.portal.service.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.UpdateUserModuParam;
import com.iber.portal.dao.sys.SysUserModuMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.sys.SysUserModu;

@Service
public class SysUserModuService {
	
	@Autowired
	private SysUserModuMapper sysUserModuMapper;

	public int insertSelective(SysUserModu model) throws ServiceException {
		int len;
		try {
			len = sysUserModuMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = sysUserModuMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	
	public int deleteByUserId(Integer userId) throws ServiceException {
		int len;
		try {
			len = sysUserModuMapper.deleteByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	

	public int updateByPrimaryKeySelective(SysUserModu model) throws ServiceException {
		int len;
		try {
			len = sysUserModuMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	public int updateByUserIdModuId(Integer userId, UpdateUserModuParam record) throws ServiceException {
		int len;
		try {
			len = sysUserModuMapper.deleteByUserId(userId);
			int[] moduIds = record.getModuIds();
			for (int i=0; i<moduIds.length; i++) {
				SysUserModu newUserModu = new SysUserModu();
				newUserModu.setUserId(userId);
				newUserModu.setModuId(moduIds[i]);
				newUserModu.setIsAuth("1");
				newUserModu.setCreateUser("admin");
				newUserModu.setCreateTime(new Date());
				insertSelective(newUserModu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public SysUserModu selectByPrimaryKey(int id) {
		return sysUserModuMapper.selectByPrimaryKey(id);
	}
	/**
	 * 
	 * @param userId 用户id
	 * @param moduIdList 用户拥有的除了功能菜单之外的所有菜单的id
	 * @param lists 用户拥有的功能菜单的id以及该功能菜单对应的功能的json格式字符串的map的list集合
	 * @throws ServiceException 
	 */
	public void updateSysUserModu(Integer userId, List<Integer> moduIdList,
			List<Map<Integer, String>> lists) throws ServiceException {
		try {
			sysUserModuMapper.deleteByUserId(userId);
			for (int i = 0; i < moduIdList.size(); i++) {
				SysUserModu newUserModu = new SysUserModu();
				newUserModu.setUserId(userId);
				newUserModu.setIsAuth("1");
				newUserModu.setCreateUser("admin");
				newUserModu.setCreateTime(new Date());
				newUserModu.setModuId(moduIdList.get(i));
				for (Map<Integer, String> map : lists) {
					if (map.get(moduIdList.get(i))!=null) {
						newUserModu.setFunction(map.get(moduIdList.get(i)));
						
					}
				}
				insertSelective(newUserModu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		
		
		
	}

	public List<String> selectFunctionByUserId(Integer userId) {
		return sysUserModuMapper.selectFunctionByUserId(userId);
	}

	/**
	 * 根据角色ids和用户id获取授权功能
	 * @param userId
	 * @param roleids
	 * @author zengfeiyue
	 * @return
	 */
	public List<String> selectFunctionByUserId(Integer userId,List<Integer> roleids) throws Exception{
		if(roleids == null||roleids.size()==0){
			return selectFunctionByUserId(userId);
		}
		return sysUserModuMapper.selectFunctionByUserId_roleIds(userId,roleids);
	}

	public List<SysUserModu> selectRoleIdsByUserId(Integer userId) {
		return sysUserModuMapper.selectRoleIdsByUserId(userId);
	}
}
