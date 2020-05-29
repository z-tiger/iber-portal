package com.iber.portal.service.base;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberCarMapper;
import com.iber.portal.model.base.MemberCar;


@Service("memberCarService")
public class MemberCarService{

	private final static Logger log= Logger.getLogger(MemberCarService.class);
	
	@Autowired
    private MemberCarMapper  memberCarMapper;

		
	public int insert(MemberCar record){
		return memberCarMapper.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return memberCarMapper.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(MemberCar record){
		return memberCarMapper.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(MemberCar record){
		return memberCarMapper.updateByPrimaryKeySelective(record) ;
	}
	
	public MemberCar selectByPrimaryKey(Integer id){
		return memberCarMapper.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return memberCarMapper.getAllNum(paramMap) ;
	}
	
	public Pager<MemberCar> queryPageList(Map<String, Object> paramMap){
		List<MemberCar> listObj = memberCarMapper.queryPageList(paramMap);
		Pager<MemberCar> pager = new Pager<MemberCar>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
}
