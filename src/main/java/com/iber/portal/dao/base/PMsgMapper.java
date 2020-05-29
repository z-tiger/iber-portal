package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.PMsg;

public interface PMsgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PMsg record);

    int insertSelective(PMsg record);

    PMsg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PMsg record);

    int updateByPrimaryKey(PMsg record);
    
    List<PMsg> selectAllPmsg(Map<String, Object>  map);
    
    int selectAllPmsgRecords(Map<String, Object> map);
    
    int updateRecords(Integer id);
    
    int updateEx(@Param("id") Integer id, @Param("msgStatus") String msgStatus,@Param("sysUserName") String sysUserName);
    
    List<PMsg> selectArticlePmsg(Map<String, Object>  map);
    
    int selectArticlePmsgRecords(Map<String, Object> map);
    
    
}