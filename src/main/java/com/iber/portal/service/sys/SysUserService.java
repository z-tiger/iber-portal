package com.iber.portal.service.sys;

import com.iber.portal.common.MD5;
import com.iber.portal.common.Pager;
import com.iber.portal.common.SysUserRoleQueryParam;
import com.iber.portal.dao.base.CityMapper;
import com.iber.portal.dao.base.MemberLevelMapper;
import com.iber.portal.dao.sys.SysUserMapper;
import com.iber.portal.dao.sys.SysUserModuMapper;
import com.iber.portal.dao.sys.SysUserRoleMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.sys.SysUserModu;
import com.iber.portal.model.sys.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private CityMapper cityMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysUserModuMapper sysUserModuMapper;
	
	@Autowired
	private MemberLevelMapper memberLevelMapper;
	

	public int deleteByPrimaryKey(Integer id) {
		return sysUserMapper.deleteByPrimaryKey(id);
	}

	public int insert(SysUser record) {
		return sysUserMapper.insert(record);
	}

	public int insertSelective(SysUser record) {
		return sysUserMapper.insertSelective(record);
	}

	public SysUser selectByPrimaryKey(Integer id) {
		return sysUserMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(SysUser record) {
		return sysUserMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(SysUser record) {
		return sysUserMapper.updateByPrimaryKey(record);
	}

	public SysUser selectByAccount(String account) {
		return sysUserMapper.selectByAccount(account);
	}
	
	/*public List<SysUser> getAll(PageLimitByUser pLimit) {
		return sysUserMapper.getAll(pLimit);
	}
	
	public int getAllNum(){
    	return sysUserMapper.getAllNum();
    }*/
	
	public List<City> selectAllCity(){
		return cityMapper.selectAllCity();
	}
	
	public List<SysUserRole> selectUserRoleByUserId(Integer userId){
		return sysUserRoleMapper.selectByUserId(userId);
	}

    public Pager<SysUser> getAll(Map<String, Object> map) {
        List<SysUser> sysUsers = sysUserMapper.getAll(map);
        Pager<SysUser> pager = new Pager<SysUser>();
        pager.setDatas(sysUsers);
        pager.setTotalCount(sysUserMapper.getAllNum(map));
        return pager;
    }

    public Pager<SysUser> getEnterpriseSysUserList(Map<String, Object> map) {
        List<SysUser> sysUsers = sysUserMapper.getEntepriseSysUserList(map);
        Pager<SysUser> pager = new Pager<SysUser>();
        pager.setDatas(sysUsers);
        pager.setTotalCount(sysUserMapper.getTotalEnterpriseSysUser(map));
        return pager;
    }

    public SysUser getSysUserByPhone(String phone) {
        return sysUserMapper.selectSysUserByphone(phone);
    }

	/*public Pager<SysUser> getAll(int first_page, int page_size, String name, String account) {
		List<SysUser> listObj = getAll(new PageLimitByUser(first_page, page_size, name, account));
		Pager<SysUser> pager = new Pager<SysUser>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum());
		return pager;
	}	*/
	
	public List<SysUserRole> selectByUserIdRoleId(SysUserRole record) {
		return sysUserRoleMapper.selectByUserIdRoleId(record);
	}
	
	public int deleteRoleByUserId(Integer userId) throws com.iber.portal.exception.ServiceException {
		int len;
		try {
			len = sysUserRoleMapper.deleteByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	
	public List<SysUserRole> selectByNotUserIdRoleId(Integer userId, String roleIds) {
		
		SysUserRoleQueryParam record = new SysUserRoleQueryParam();
		record.setUserId(userId);
		
		int arrayNum = 1;
		int roleIdss[] = null;
		if (roleIds.contains(",")) {
			String[] roleIdsArr = roleIds.split(",");
			arrayNum = roleIdsArr.length;
			roleIdss = new int[arrayNum];
			for (int i=0; i<arrayNum; i++) {
				roleIdss[i] = Integer.parseInt(roleIdsArr[i]);
			}
		}else{
			roleIdss = new int[arrayNum];
			roleIdss[0] = Integer.parseInt(roleIds);
		}
		
		record.setRoleIds(roleIdss);
		
		return sysUserRoleMapper.selectByNotUserIdRoleId(record);
	}	
	
	
	public List<SysUserModu> selectByEnabledUserId(Integer userId) {
		return sysUserModuMapper.selectByEnabledUserId(userId);
	}
	
	public List<MemberLevel>selectAllMemberLevel(){
		return memberLevelMapper.selectAllMemberLevel();
	}
	public List<SysUser> selectAllByName(String name){
		return sysUserMapper.selectAllByName(name);
	}

	public List<SysUser> selectAllOnTheJob(){
		return sysUserMapper.selectAllOnTheJob();
	}
    public SysUser getSysUserByIdAndPassword(int id, String password) {
        return sysUserMapper.getSysUserByIdAndPassword(id, MD5.toMD5(password));
    }

}	
