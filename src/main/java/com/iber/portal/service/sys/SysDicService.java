package com.iber.portal.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.sys.SysDicMapper;
import com.iber.portal.dao.sys.SysDicTypeMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.sys.SysDic;
import com.iber.portal.model.sys.SysDicType;




@Service
public class SysDicService {

	@Autowired
	private SysDicMapper sysDicMapper;
	
	@Autowired
	private SysDicTypeMapper dicTypeMapper;
	

	public int insertSelective(SysDic model) throws ServiceException {
		int len;
		try {
			len = sysDicMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = sysDicMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(SysDic model) throws ServiceException {
		int len;
		try {
			len = sysDicMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public SysDic selectByPrimaryKey(int id) {
		return sysDicMapper.selectByPrimaryKey(id);
	}
	
	public List<SysDic> selectListByDicCode(String dicCode) {
		return sysDicMapper.selectListByDicCode(dicCode);
	}
	
	
	public List<SysDicType> selectDicTypeAll() {
		return dicTypeMapper.selectDicTypeAll();
	}
	
	public List<SysDic> getAll(Integer dicId){
		return sysDicMapper.getAll(dicId);
	}
	
	
	public SysDic selectByCode(Map<String, Object> map){
		return sysDicMapper.selectByCode(map);
	}

	public SysDic selectByName(Map<String, Object> map) {
		return sysDicMapper.selectByName(map);
	}

	public List<SysDic> selectListByDicPartCode(String dicPartCode) {
		return sysDicMapper.selectListByDicPartCode(dicPartCode);
	}
	public Integer selectRecordsByCode(Map<String, Object> map){
		return sysDicMapper.selectRecordsByCode(map);
	}
	public Integer selectRecordsByName(Map<String, Object> map){
		return sysDicMapper.selectRecordsByName(map);
	}

    public List<SysDic> querySysDic(Integer id) {
		return sysDicMapper.querySysDic(id);
    }
    
    public List<SysDic> querySysDicByDicCodeList(List<String> param) {
		return sysDicMapper.querySysDicByDicCodeList(param);
    }
    public List<SysDic> querySysDicBySysDicIds(List<String> param){
    	return sysDicMapper.querySysDicBySysDicIds(param);
    }
}
