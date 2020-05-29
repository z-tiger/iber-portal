package com.iber.portal.service.sys;

import com.iber.portal.dao.sys.SysModuMapper;
import com.iber.portal.model.sys.SysModu;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysModuService {

	@Autowired
	private SysModuMapper sysModuMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public int deleteByPrimaryKey(Integer id){
		//删除的时候 必须删除所以的节点，包括叶子节点和模块节点
		sysModuMapper.deleteByPid(id);
		int r2 = sysModuMapper.deleteByPrimaryKey(id);
		return r2;
	}

	public int insert(SysModu record){
		return sysModuMapper.insert(record);
	}

	public int insertSelective(SysModu record){
		return sysModuMapper.insertSelective(record);
	}

	public SysModu selectByPrimaryKey(Integer id){
		return sysModuMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(SysModu record){
		return sysModuMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(SysModu record){
		return sysModuMapper.updateByPrimaryKey(record);
	}
    
	public List<SysModu> getAll(){
		return sysModuMapper.getAll();
	}
    
	public List<SysModu> getAllNot(){
		return sysModuMapper.getAllNot();
	}
    
	public List<SysModu> showModuList(){
		return sysModuMapper.showModuList();
	}

	public List<SysModu> showEnterpriseModuList(){
	    return sysModuMapper.showEnterpriseModuList();
    }
    
	public List<SysModu> selectUserModuByUserId(Integer userId){
		return sysModuMapper.selectUserModuByUserId(userId);
	}

	public SysModu selectByNameAndPid(Integer pid, String name) {
		// TODO Auto-generated method stub
		return sysModuMapper.selectByNameAndPid(pid, name);
	}

	public List<SysModu> selectModuByLinks(List<String> values) {
		// TODO Auto-generated method stub
        if (CollectionUtils.isEmpty(values)) {
            values.add("");
        }
        return sysModuMapper.selectModuByLinks(values);
	}
}
