package com.iber.portal.dao.warns;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.operationReport.CarApplyDetail;
import com.iber.portal.model.warns.WarnInfo;

public interface WarnInfoMapper {
    
    int insert(WarnInfo record);

    int insertSelective(WarnInfo record);

    WarnInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarnInfo record);

    int updateByPrimaryKey(WarnInfo record);
    
    List<WarnInfo> selectByWarnInfoList(Map<String, Object> map) ;
    
    List<WarnInfo> selectByWarnAllList(Map<String, Object> map) ;
    
    int selectByWarnInfoListRecords(Map<String, Object> map) ;

    List<WarnInfo> selectByPrimaryKeyExcel(Map<String, String> map);

    int selectByWarnRemindTotal(Map<String, Object> record);

}