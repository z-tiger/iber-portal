/**
 * 
 */
package com.iber.portal.dao.ad;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.ad.StartUpPage;

/**
 * @PackageName：com.iber.portal.dao.ad
 * @FileName:StartUpPageMapper.java
 * @Description:TODO
 * @Create:CUICHONG
 * @CreateDate:2017-6-27 下午1:33:58
 * @Update:CUICHONG
 * @UpdateDate:2017-6-27 下午1:33:58
 * @Version:1.0
 */
public interface StartUpPageMapper {
	
	int deleteByPrimaryKey(Integer id);

    int insert(StartUpPage record);

    int insertSelective(StartUpPage record);

    StartUpPage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StartUpPage record);

    int updateByPrimaryKey(StartUpPage record);
    
    //query list
    List<StartUpPage> getAll(Map<String, Object> paramMap);
    //query total
	int getAllNum(Map<String, Object> paramMap);
	
	int getStartTime(@Param("from") String from);
	
	int getEndTime(@Param("to") String to);

}
