package com.iber.portal.dao.base;

import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.member.MemberTotalVo;
import com.iber.portal.vo.enterprise.EnterpriseMemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Member record);

	int insertSelective(Member record);

	Member selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Member record);

	int updateByPrimaryKey(Member record);

	Member selectDetailByPhone(String phone);

	List<Member> selectAll(Map<String, Object> map);

	int selectAllRecords(Map<String, Object> map);

	List<Member> selectEnterpriseAll(Map<String, Object> map);

	int selectEnterpriseAllRecords(Map<String, Object> map);

	int restPassword(@Param("id") int id, @Param("password") String pwd);

	/**
	 * 把已领取过只能参与一次的优惠券的会员的状态清0
	 * @return
	 */
	int resetDrewOnceCouponStatus();

	int deleteFingerprint(@Param("id") int id);

    /**
     * 删除人脸
     * @param id
     * @return
     */
    int deleteFace(@Param("id") int id);


    int memberExamineSave(@Param("refuseReason") String refuseReason, @Param("id") int id, @Param("status") String status, @Param("remark") String remark,
			@Param("name") String name, @Param("idcard") String idcard, @Param("driverIdcard") String driver_idcard,
			@Param("driverIdcardValidityTime") Date driverIdcardValidityTime,
			@Param("driverIdcardUpdate") int driverIdcardUpdate, @Param("sex") String sex,@Param("dirverIdCardTime") Date dirverIdCardTime,
			@Param("examineTime") Date examineTime,@Param("examineId")Integer examineId);

	Member selectEnterpriseAccount(int enterpriseId);

	List<EnterpriseMemberVo> selectEnterpriseMemberAll(Map<String, Object> paramMap);

	int selectEnterpriseMemberAllNum(Map<String, Object> paramMap);

	Member selectDetailByIdcard(String idcard);

	List<Member> selectDetailByIdcard4List(String idcard);

	Member selectDetailByEmail(String email);

	int updateMemberAccountStatus(@Param("accoutStatus") String accoutStatus, @Param("id") int id);

	List<Member> selectExamineAll(Map<String, Object> map);

	int selectExamineAllRecords(Map<String, Object> map);

    List<Map<String, Object>> selectLostNoExamineList(Map<String, Object> map);

    int selectLostNoExamineTotal(Map<String, Object> map);


    /**
	 * 根据id查会员手机号
	 * 
	 * @param id
	 * @return
	 * @author ouxx
	 * @date 2016-9-29 下午2:38:58
	 */
	String getPhoneById(int id);

	int removeEnterpriseId(Integer id);

	List<Member> selectMemberDriverIdcardValidity();

	/** 查询各省的会员数 */
	List<MemberTotalVo> queryAllMemberNums(@Param("cityCode") String cityCode);

	/** 查询省下级城市的会员数 */
	List<MemberTotalVo> queryCityAllMemberNums(@Param("cityCode") String cityCode);

	List<MemberTotalVo> getMemberTotal(@Param("cityCode") String cityCode);

	List<MemberTotalVo> getMemberTotalByLevel(Map<String, Object> paramMap);

	List<MemberTotalVo> getMemberTotalByType(Map<String, Object> paramMap);

	List<MemberTotalVo> getMemberTotalByStatus(Map<String, Object> paramMap);

	List<MemberTotalVo> getExpenseMember(Map<String, Object> paramMap);

	List<MemberTotalVo> getExpenseMemberByType(Map<String, Object> paramMap);
	
    List<MemberTotalVo> getExpenseMemberByLevel(Map<String, Object> paramMap); 
	 
	 List<MemberTotalVo> getExpenseMemberByStatus(Map<String, Object> paramMap); 

	
	/**
	 * 更新会员的贡献值
	 * 
	 * @return
	 * @author ouxx
	 * @date 2016-12-27 下午3:12:05
	 */
	int updateContributedVal();

	/**
	 * 更新会员等级编码，x_member_card中的contributedVal在x_member_level的区间中： [min, max]
	 * 
	 * @param levelList
	 * @return
	 * @author ouxx
	 * @date 2017-1-7 下午5:38:58
	 */
	int updateLevelCode(List<MemberLevel> levelList);
	
	/**
	  * 根据贡献值来源查询贡献值明细
	  * @param type
	  * @return
	  */
	 List<MemberContributedDetail> selectMemberContributeItemByType(Map<String, Object> paramMap);

	 int getAllNum(Map<String, Object> paramMap);

	int examineIdcardRecord(String idcard);

	List<Member> selectByName(@Param("name") String name);

	List<Member> queryMemberDriving2Year();

	int updateLevelCodeById(@Param("memberId")Integer memberId, @Param("levelCode")int levelCode); 
	
	int queryExists(@Param("id") Integer id,
			@Param("type") Integer type, @Param("value") String value);

	/**
	 * 根据订单编号查询会员
	 * @param orderId
	 * @return
	 */
    List<Member> queryMemberByOrderId(String orderId);

    //四星、五星
  	List<Member> selectFourFiveStar();

  	int deleteTboxFingerPrint(Integer id);

	List<Map<String, Object>> selectByCityCode(String cityCode);

	List<Map<String,Object>> selectByPhoneList(List<String> phones);


    List<Member> selectAllReady();
}