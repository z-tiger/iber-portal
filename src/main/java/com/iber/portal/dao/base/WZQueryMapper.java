package com.iber.portal.dao.base;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.Member;
import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.WZQuery;
import com.iber.portal.vo.base.WZQueryVo;

public interface WZQueryMapper {
	
	List<WZQueryVo> selectEmployeeWZRecord(Map<String, Object> map);
	List<WZQueryVo> selectMemberWZRecord(Map<String, Object> map);
    int insert(WZQuery record);

    int insertSelective(WZQuery record);
    
    /**查询违章记录信息*/
    List<WZQueryVo> selectWZQuery(Map<String, Object> map);
    
    int selectWZQueryRecords(Map<String, Object> map);
    
    int updateWZStatus(@Param("id") Integer id, @Param("status") String status,@Param("handleType") String handleType, @Param("remark") String remark, @Param("date") Date date, @Param("user") String user);
    
    /**根据车牌号  车型 违章时间查询是否有记录*/
    int queryByCondition(@Param("hphm") String hphm,@Param("hpzl") String hpzl,@Param("date") String date);
    /**
     * 根据订单id查询该订单违章记录
     * @param orderId
     * @return
     */
    List<WZQuery> selectByOrderId(String orderId);
    /**
     * 根据会员id查询会员违章记录
     * @param memberId
     * @return
     */
    List<WZQuery> queryWzRecord(Integer memberId);

	List<WZQueryVo> selectWZQueryEmployee(HashMap<String, Object> map);

	int selectWZQueryEmployeeRecords(HashMap<String, Object> map);

	List<WZQueryVo> selectWZQueryMember(HashMap<String, Object> map);

	int selectWZQueryMemberRecords(HashMap<String, Object> map);
	
	/**
	 * 查超过天数为days的未处理违章
	 * @param days
	 * @return
	 * @author ouxx
	 * @date 2017-4-19 上午11:16:44
	 */
	List<WZQuery> queryNoDealt(@Param("days") Integer days);

	/**
	 * 查会员最新一条违章记录
	 * @param memberId
	 * @return
	 * @author ouxx
	 * @date 2017-4-21 下午5:43:12
	 */
	WZQuery queryLatestWzRecord(@Param("memberId") Integer memberId);
	
	/**
	 * 查最后一次存储违章记录的时间
	 * @return
	 * @author ouxx
	 * @date 2017-4-22 下午12:03:59
	 */
	Date getLatestInsertTime();
	
	WZQuery selectRecordByOrderId(String orderId);
	
	
	/**
	 * 查询车辆违章信息
	 * @return
	 * @author zhaocj
	 * @date 2017-5-22 下午17:34:29
	 */
	
	
   List<WZQueryVo> queryWZEmployeeInfos(Map<String, Object> map);
   List<WZQueryVo> queryWZMemberInfos(Map<String, Object> map);
   List<WZQueryVo> queryWZLongRentInfos(Map<String, Object> map);
   int  queryWZMemberInfosRecords(Map<String, Object> map);
   int  queryWZEmployeeInfosRecords(Map<String, Object> map);
   int  queryWZLongRentInfosRecords(Map<String, Object> map);
   List<WZQuery> queryUndisposedWzRecord(Integer memberId);

	/**
	 * 查找长时间未处理的违章用户，并冻结账户
	 * @param timeValue
	 * @return
	 */
   List<String> lockLongTimeUndisposedMember(@Param("timeValue") String timeValue,@Param("timeSpace") String timeSpace);

	/**
	 * 查找长时间未处理的违章用户，并冻结账户
	 * @param timeValue
	 * @return
	 */
	List<Member> queryLongTimeMember(@Param("timeValue") String timeValue,@Param("timeSpace") String timeSpace);

	List<WZQueryVo> queryWzInfo(HashMap<String, Object> map);

	Integer queryWzInfoRecord(HashMap<String, Object> map);

	Integer selectRecordByLpnAndDateAndArchiveno(@Param("lpn") String lpn, @Param("date") String date, @Param("act") String act,@Param("id")Integer id);

	void update(WZQuery wzQuery);
}