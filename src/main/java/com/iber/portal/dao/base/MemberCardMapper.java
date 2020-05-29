package com.iber.portal.dao.base;

import com.iber.portal.model.base.MemberCard;
import com.iber.portal.vo.base.MemberCardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemberCard record);

    int insertSelective(MemberCard record);

    MemberCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemberCard record);

    int updateByPrimaryKey(MemberCard record);
    
    List<MemberCardVo> selectAll(Map<String, Object> map);
    
    int selectAllRecords(Map<String, Object> map);
    
    int memberCardFrozenThaw(@Param("memberId") int memberId, @Param("accoutStatus") String accoutStatus);

	MemberCard selectByMemberId(Integer memberId);
	
	int updatMemberCardMoney(@Param("clear") Integer clear, @Param("money") Integer money,  @Param("menberId") Integer menberId);
	
	int updatMemberCardDeposit(@Param("clear") Integer clear,@Param("money") Integer money,  @Param("menberId") Integer menberId, @Param("totalRefundMoney")Integer totalRefundMoney);
	
	//更新会员积分
	int updateIntegral(@Param("memberId") Integer memberId,@Param("integral") Integer integral);
	//更新会员冻结资金原因
	int updateBlockingReason(@Param("memberId") Integer memberId,@Param("blockingReason")String blockingReason);
	
	int deleteBlockingReason(Integer memberId);
	
	//根据会员id更新会员卡
	int updateMemberByDeposit(MemberCard memberCard);
	
	/**
	 * 更新会员贡献值
	 * @param memberId
	 * @param contributedVal
	 * @return
	 * @author ouxx
	 * @date 2016-12-29 上午9:53:59
	 */
	int updateContributedVal(@Param("memberId") Integer memberId, @Param("contributedVal") Integer contributedVal);
	
	/** 增减贡献值 **/
	int increContributedVal(@Param("memberId") Integer memberId, @Param("contributedValDelta") Integer contributedValDelta);
	int decreContributedVal(@Param("memberId") Integer memberId, @Param("contributedValDelta") Integer contributedValDelta);


}