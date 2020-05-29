package com.iber.portal.dao.base;



import java.util.List;
import java.util.Map;
import com.iber.portal.model.base.MemberCar;

public interface MemberCarMapper {
	
	int insert(MemberCar record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(MemberCar record);
	
	int updateByPrimaryKeySelective(MemberCar record);
	
	MemberCar selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<MemberCar> queryPageList(Map<String, Object> paramMap);
}
