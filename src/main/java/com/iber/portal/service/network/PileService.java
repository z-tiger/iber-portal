package com.iber.portal.service.network;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.pile.PileMapper;
import com.iber.portal.model.pile.Pile;

@Service
public class PileService {

	@Autowired
	private PileMapper pileMapper;
	
	
	public int deleteByPrimaryKey(Integer id){
		return pileMapper.deleteByPrimaryKey(id);
	}

	public   int insert(Pile record){
		return pileMapper.insert(record);
	}

	public   int insertSelective(Pile record){
		return pileMapper.insertSelective(record);
	}

	public  Pile selectByPrimaryKey(Integer id){
		return pileMapper.selectByPrimaryKey(id);
	}

	public   int updateByPrimaryKeySelective(Pile record){
		return pileMapper.updateByPrimaryKeySelective(record);
	}

	public  int updateByPrimaryKey(Pile record){
		return pileMapper.updateByPrimaryKey(record);
	}
	
	public List<Pile> selectAll(Map<String, Object> map){
		return pileMapper.selectAll(map);
	}
	    
	public   int selectAllRecords(Map<String, Object> map){
		return pileMapper.selectAllRecords(map);
	}
}
