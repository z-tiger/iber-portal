package com.iber.portal.dao.sys;

import java.util.List;
import java.util.Map;


import com.iber.portal.model.sys.SysDic;

public interface SysDicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDic record);

    int insertSelective(SysDic record);

    SysDic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDic record);

    int updateByPrimaryKey(SysDic record);

    List<SysDic> selectListByDicCode(String dicCode);
    
    List<SysDic> getAll(Integer dicId);
    
    SysDic selectByCode(Map<String, Object> map);

	SysDic selectByName(Map<String, Object> map);
	/**
	 * 根据dicCode的部分字符串进行查询
	 * @param dicPartCode
	 * @return
	 */
	List<SysDic> selectListByDicPartCode(String dicPartCode);
	
	Integer selectRecordsByCode(Map<String, Object> map);
	
	Integer selectRecordsByName(Map<String, Object> map);

    List<SysDic> querySysDic(Integer id);
    
    List<SysDic> querySysDicByDicCodeList(List<String> lists);
    List<SysDic> querySysDicBySysDicIds(List<String> lists);
}