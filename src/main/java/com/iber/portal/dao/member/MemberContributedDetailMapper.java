package com.iber.portal.dao.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.common.Pager;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.vo.member.MemberContributedDetailVo;
import com.iber.portal.vo.member.MemberVo;

/**
 * <br>
 * <b>功能：</b>MemberContributedDetailDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface MemberContributedDetailMapper {

    int insert(MemberContributedDetail record);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(MemberContributedDetail record);

    int updateByPrimaryKeySelective(MemberContributedDetail record);

    MemberContributedDetail selectByPrimaryKey(Integer id);

    int getAllNum(Map<String, Object> paramMap);

    List<MemberVo> queryPageList(Map<String, Object> paramMap);

    int insertSelective(MemberContributedDetail record);

    int insertBatch(List<MemberContributedDetail> list);

    /**
     * 查会员ID为memberId的会员，在本月因分享好友而获得贡献值提升的次数
     *
     * @param memberId
     * @return
     * @author ouxx
     * @date 2017-1-3 下午7:18:35
     */
    Integer getShareCntByMemberIdInThisMonth(@Param("memberId") Integer memberId);

    /*
     * 获取关联ID == objId 的记录数
     */
    int getCntByTypeAndObjId(@Param("type") String type, @Param("objId") String objId);

    /**
     * 根据memberId查询贡献值明细
     *
     * @param paramMap
     * @return
     */
    List<MemberContributedDetailVo> queryMemberContributedDetailByMember(
            Map<String, Object> paramMap);

    /**
     * 根据memberId查询贡献值明细总数
     *
     * @param paramMap
     * @return
     */
    int getAllMemberContributedDetailByMember(Map<String, Object> paramMap);

    /**
     * 查询会员减少的贡献值明细
     *
     * @param paramMap
     * @return
     */
    List<MemberContributedDetailVo> queryDecreaseContributedDetail(
            Map<String, Object> paramMap);

    int getAllDecreaseContributedDetailNum(Map<String, Object> paramMap);

    /**
     * 查询会员每周的消费贡献累计
     * 2017-5-17 17:42:41
     *
     * @param paramMap
     * @return
     */
    List<MemberContributedDetailVo> queryOneWeakConsumeContributedSum(Map<String, Date> paramMap);

    /**
     * 删除已经统计过的数据
     * 2017-5-25 17:28:19
     * @param paramMap
     * @return
     */
    void deleteOneWeakConsumeContribute(Map<String, Date> paramMap);

    /**
     * 删除所有消费数据
     * 2017-5-25 17:28:19
     * @return
     */
    void deleteConsumeContribute();

    /**
     * 查询第一天记录的时间
     * @return
     */
    Date queryMinCreateTime();

	/**
	 * 查询会员贡献值明细
	 * @param paramMap
	 * @return
	 */
	List<MemberContributedDetailVo> queryContributedDetail(
			Map<String, Object> paramMap);
	
	/**
	 * 查询会员减少的贡献值明细条数
	 * @param paramMap
	 * @return
	 */
	int getAllContributedDetailNum(Map<String, Object> paramMap);
	
	/**查询时间段内是否有积分记录*/
	List<MemberContributedDetail> queryIsHave();
	
	int selectRecordsByTypeAndMemberId(@Param("type")String type, @Param("memberId")String memberId);
}
