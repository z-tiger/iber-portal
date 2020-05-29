package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.OperatorInfoMapper;
import com.iber.portal.model.charging.OperatorInfo;


@Service("operatorInfoService")
public class OperatorInfoService{

	private final static Logger log= Logger.getLogger(OperatorInfoService.class);
	
	@Autowired
    private OperatorInfoMapper  operatorInfoMapper;

		
	public int insert(OperatorInfo record){
		return operatorInfoMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return operatorInfoMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(OperatorInfo record){
		return operatorInfoMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(OperatorInfo record){
		return operatorInfoMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public OperatorInfo selectByPrimaryKey(Integer id){
		return operatorInfoMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return operatorInfoMapper.getAllNum(paramMap) ;
	}
	
	public Pager<OperatorInfo> queryPageList(Map<String, Object> paramMap){
		List<OperatorInfo> listObj = operatorInfoMapper.queryPageList(paramMap);
		Pager<OperatorInfo> pager = new Pager<OperatorInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(operatorInfoMapper.getAllNum(paramMap));
		return pager;
	}
	public List<OperatorInfo> getAllOperatorInfo(){
		
		return operatorInfoMapper.getAllOperatorInfo();
	}
	
	public List<OperatorInfo> getAllOperatorName(){
		return operatorInfoMapper.getAllOperatorName();
	}
	
	public int selectIdByName(String name){
		return operatorInfoMapper.selectIdByName(name);
	}
	
}
