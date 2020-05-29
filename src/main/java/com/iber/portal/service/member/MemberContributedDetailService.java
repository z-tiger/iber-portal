package com.iber.portal.service.member;

import com.alibaba.fastjson.JSONObject;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberCardMapper;
import com.iber.portal.dao.member.MemberContributedDetailMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.util.DateTime;
import com.iber.portal.vo.member.MemberContributedDetailVo;
import com.iber.portal.vo.member.MemberVo;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>MemberContributedDetailService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("memberContributedDetailService")
public class MemberContributedDetailService {

    private final static Logger log = Logger.getLogger(MemberContributedDetailService.class);

    @Autowired
    private MemberContributedDetailMapper memberContributedDetailMapper;

    @Autowired
    private SysParamMapper sysParamMapper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    public int insert(MemberContributedDetail record) {
        return memberContributedDetailMapper.insert(record);
    }

    public int deleteByPrimaryKey(Integer id) {
        return memberContributedDetailMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKey(MemberContributedDetail record) {
        return memberContributedDetailMapper.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(MemberContributedDetail record) {
        return memberContributedDetailMapper.updateByPrimaryKeySelective(record);
    }

    public MemberContributedDetail selectByPrimaryKey(Integer id) {
        return memberContributedDetailMapper.selectByPrimaryKey(id);
    }

    public int getAllNum(Map<String, Object> paramMap) {
        return memberContributedDetailMapper.getAllNum(paramMap);
    }

    public Pager<MemberVo> queryPageList(Map<String, Object> paramMap) {
        List<MemberVo> listObj = memberContributedDetailMapper.queryPageList(paramMap);
        Pager<MemberVo> pager = new Pager<MemberVo>();
        pager.setDatas(listObj);
        pager.setTotalCount(getAllNum(paramMap));
        return pager;
    }

    public int insertSelective(MemberContributedDetail record) {
        return this.memberContributedDetailMapper.insertSelective(record);
    }

    public int insertBatch(List<MemberContributedDetail> list) {
        return this.memberContributedDetailMapper.insertBatch(list);
    }

    public int getCntByTypeAndObjId(String type, String objId) {
        return this.memberContributedDetailMapper.getCntByTypeAndObjId(type, objId);
    }

    public void insertMemberContributedDetail(final Integer memberId,
                                              final String objId, final String typeEnum, final Double value) {

        //调用接口的参数
        final Map<String, String> methodParamMap = new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
                put("objId", objId);
                put("typeEnum", typeEnum);
                put("value", (null != value ? value.toString() : null));
            }
        };
        final String methodParams = JSONObject.toJSONString(methodParamMap).replaceAll("\"", "'");
        //http请求的参数
        Map<String, String> httpMap = new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
                put("cId", "platForm");
                put("memberId", memberId.toString());
                put("method", "insertMemberContributedDetailByBehavior");
                put("param", methodParams);
                put("phone", "");
                put("type", "platForm");
                put("version", "1");
            }
        };
        String params = JSONObject.toJSONString(httpMap);

        SysParam sysParam = sysParamMapper.selectByKey("http_url");
        String json = "";
        if (sysParam.getValue().indexOf("https") == 0) { //https接口
            json = HttpsClientUtil.get(sysParam.getValue(), params);
        } else {
            json = HttpUtils.commonSendUrl(sysParam.getValue(), params);
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        log.info("增减会员贡献值任务结束" + jsonObject);
    }

    /**
     * @param createTime
     * @param memberId
     * @param objId
     * @param typeEnum
     * @param value
     * @author ouxx
     * @date 2017-5-9 下午5:35:10
     */
    public void insertMemberContributedDetail2(final Date createTime, final Integer memberId,
                                               final String objId, final String typeEnum, final Double value) {

        //调用接口的参数
        final Map<String, Object> methodParamMap = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put("objId", objId);
                put("typeEnum", typeEnum);
                put("createTime", createTime);
                put("value", (null != value ? value.toString() : null));
            }
        };
        final String methodParams = JSONObject.toJSONString(methodParamMap).replaceAll("\"", "'");
        //http请求的参数
        Map<String, String> httpMap = new HashMap<String, String>() {
            private static final long serialVersionUID = 1L;

            {
                put("cId", "platForm");
                put("memberId", memberId.toString());
                put("method", "insertMemberContributedDetailByBehavior2");
                put("param", methodParams);
                put("phone", "");
                put("type", "platForm");
                put("version", "1");
            }
        };
        String params = JSONObject.toJSONString(httpMap);

        SysParam sysParam = sysParamMapper.selectByKey("http_url");
        String json = "";
        if (sysParam.getValue().indexOf("https") == 0) { //https接口
            json = HttpsClientUtil.get(sysParam.getValue(), params);
        } else {
            json = HttpUtils.commonSendUrl(sysParam.getValue(), params);
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        log.info("增减会员贡献值任务结束" + jsonObject);
    }

	public Pager<MemberContributedDetailVo> queryContributedDetail(
			Map<String, Object> paramMap) {
		List<MemberContributedDetailVo> listObj = memberContributedDetailMapper.queryContributedDetail(paramMap);
		Pager<MemberContributedDetailVo> pager = new Pager<MemberContributedDetailVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllContributedDetailNum(paramMap));
		return pager;
	}

	private int getAllContributedDetailNum(Map<String, Object> paramMap) {
		return memberContributedDetailMapper.getAllContributedDetailNum(paramMap);
	}

    public Pager<MemberContributedDetailVo> queryMemberContributedDetailByMember(
            Map<String, Object> paramMap) {
        List<MemberContributedDetailVo> listObj = memberContributedDetailMapper.queryMemberContributedDetailByMember(paramMap);
        Pager<MemberContributedDetailVo> pager = new Pager<MemberContributedDetailVo>();
        pager.setDatas(listObj);
        pager.setTotalCount(getAllMemberContributedDetailByMember(paramMap));
        return pager;
    }

    private int getAllMemberContributedDetailByMember(Map<String, Object> paramMap) {
        return memberContributedDetailMapper.getAllMemberContributedDetailByMember(paramMap);
    }

    public Pager<MemberContributedDetailVo> queryDecreaseContributedDetail(
            Map<String, Object> paramMap) {
        List<MemberContributedDetailVo> listObj = memberContributedDetailMapper.queryDecreaseContributedDetail(paramMap);
        Pager<MemberContributedDetailVo> pager = new Pager<MemberContributedDetailVo>();
        pager.setDatas(listObj);
        pager.setTotalCount(getAllDecreaseContributedDetailNum(paramMap));
        return pager;
    }

    private int getAllDecreaseContributedDetailNum(Map<String, Object> paramMap) {
        return memberContributedDetailMapper.getAllDecreaseContributedDetailNum(paramMap);
    }

    public void initWeakContributedByConsume() {
        // 获取第一条贡献值时间
        Date start = memberContributedDetailMapper.queryMinCreateTime();
        // 获取周一
        final Date monday = DateTime.getfirstWeek(start);
        start = monday.compareTo(start) > 0 ? start : monday;
        // 增加一周的时间
        Date end = DateUtils.addDays(monday, 7);
        // 获取今天的时间
        final Date now = new Date();
        Map<String, Date> param = new HashMap<String, Date>();
        final String startStr = "start";
        final String endStr = "end";
        param.put(startStr, start);
        param.put(endStr, end);
        // 循环查询
        while (now.compareTo(start) >= 0) {
            // 查询这一周的消费贡献值
            final List<MemberContributedDetailVo> detailVos = memberContributedDetailMapper.queryOneWeakConsumeContributedSum(param);
            if (!CollectionUtils.isEmpty(detailVos)) {
                for (MemberContributedDetailVo detailVo : detailVos) {
                    if (detailVo != null && detailVo.getContributeVal() != null && detailVo.getContributeVal() > 0) {
                        // 删除统计过个数据
                        Integer contributeVal = detailVo.getContributeVal();
                        contributeVal = contributeVal > 300 ? 300 : contributeVal;
                        MemberContributedDetail detail = new MemberContributedDetail();
                        detail.setCreateTime(now.compareTo(end) >= 0 ? end : now);
                        detail.setMemberId(detailVo.getId());
                        detail.setType("temp");
                        detail.setContributedValDelta(contributeVal);
                        detail.setObjId("1");
                        memberContributedDetailMapper.insert(detail);
                        memberCardMapper.increContributedVal(detailVo.getId(), contributeVal);
                    }
                }
            }
            // 增加日期
            start = end;
            end = DateUtils.addDays(start, 7);
            param.put(startStr, start);
            param.put(endStr, end);
        }
        // 删除所有消费数据
        memberContributedDetailMapper.deleteConsumeContribute();
        System.out.println("init success");
    }
    
    public int selectRecordsByTypeAndMemberId(String type, String memberId){
    	return memberContributedDetailMapper.selectRecordsByTypeAndMemberId(type,memberId);
    }
}
