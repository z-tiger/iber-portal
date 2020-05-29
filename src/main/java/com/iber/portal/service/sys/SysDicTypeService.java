package com.iber.portal.service.sys;

import com.iber.portal.dao.sys.SysDicTypeMapper;
import com.iber.portal.model.sys.SysDicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDicTypeService {

	@Autowired
	private SysDicTypeMapper dicTypeMapper;
	
	
	public int deleteByPrimaryKey(Integer id){
		return dicTypeMapper.deleteByPrimaryKey(id);
	}

	public   int insert(SysDicType record){
		return dicTypeMapper.insert(record);
	}

	public   int insertSelective(SysDicType record){
		return dicTypeMapper.insertSelective(record);
	}
	public   SysDicType selectByPrimaryKey(Integer id){
		return dicTypeMapper.selectByPrimaryKey(id);
	}

	public   int updateByPrimaryKeySelective(SysDicType record){
		return dicTypeMapper.updateByPrimaryKeySelective(record);
	}

	public   int updateByPrimaryKey(SysDicType record){
		return dicTypeMapper.updateByPrimaryKey(record);
	}
	    
	public   List<SysDicType> selectDicTypeAll(){
		return dicTypeMapper.selectDicTypeAll();
	}

	public List<SysDicType> querySysDicType() {
		return dicTypeMapper.querySysDicType();
	}
}
