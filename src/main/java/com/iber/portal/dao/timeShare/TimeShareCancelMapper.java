package com.iber.portal.dao.timeShare;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.timeShare.TimeShareCancel;
import com.iber.portal.vo.timeShare.TimeShareCancelVo;

public interface TimeShareCancelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeShareCancel record);

    int insertSelective(TimeShareCancel record);

    TimeShareCancel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeShareCancel record);

    int updateByPrimaryKey(TimeShareCancel record);
    
    List<TimeShareCancelVo> getAll(Map<String,Object> map);

	int getAllNum(Map<String,Object> map);

	int resetMemberCancelCarOrderCount(Integer id);
	
	int resetMemberCancelCharingOrderCount(Integer id);
	
	/**
     * 增加连续无违章的用车次数
     * @param memberId
     * @return
     * @author ouxx
     * @date 2017-4-19 下午3:00:30
     */
    int increContinNoWzNum(@Param("memberId") Integer memberId);
    
    /**
     * 增加连续无救援的用车次数
     * @param memberId
     * @return
     * @author ouxx
     * @date 2017-4-19 下午3:00:36
     */
    int increContinNoRescueNum(@Param("memberId") Integer memberId);
    
    /**
     * 重置某会员的连续无违章或无救援的用车次数（只要发生违章或救援的其中一种情况，都把该会员的contin_no_wz_num、contin_no_rescue_num两个字段置为0）
     * @param memberId
     * @return
     * @author ouxx
     * @date 2017-4-19 下午3:09:28
     */
    int resetContinNoWzOrRescueNumByMemberId(@Param("memberId") Integer memberId);
    
    /**
     * 增加连续无违章与救援的用车次数
     * @param memberId
     * @return
     * @author ouxx
     * @date 2017-5-5 下午9:55:52
     */
    int increContinNoWzAndRescueNum(@Param("memberId") Integer memberId);
    
    TimeShareCancel queryTimeShareCancelByMemberId(@Param("memberId") Integer memberId);
}