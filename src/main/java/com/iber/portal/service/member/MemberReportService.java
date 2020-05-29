package com.iber.portal.service.member;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.member.MemberReportMapper;
import com.iber.portal.model.member.MemberReport;
import com.iber.portal.vo.member.MemberLastOrderVo;
import com.iber.portal.vo.member.MemberReportRelationVo;
import com.iber.portal.vo.member.MemberReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class MemberReportService {

    @Autowired
    private MemberReportMapper memberReportMapper;

    public Pager<MemberReportVo> pagerList(Map<String, Object> paramMap) {
        List<MemberReportVo> listObj = memberReportMapper.queryPageList(paramMap);
        Pager<MemberReportVo> pager = new Pager<MemberReportVo>();
        pager.setDatas(listObj);
        pager.setTotalCount(getRecordNum(paramMap));
        return pager;
    }

    public int getRecordNum(Map<String, Object> paramMap) {
        return memberReportMapper.getRecordNum(paramMap);
    }

    public MemberReport selectById(int id) {
        return memberReportMapper.selectByPrimaryKey(id);

    }

    public int updateMemberReport(MemberReport model) {
        return memberReportMapper.updateByPrimaryKeySelective(model);
    }

    public MemberLastOrderVo getLastCarOrderInfo(String lpn, String memberName, String memberPhone) {
        return memberReportMapper.getLastCarOrderInfo(lpn, memberName, memberPhone);
    }

    public MemberReport insertMemberReport(MemberReport report) {
        memberReportMapper.insert(report);
        return report;
    }

    public MemberLastOrderVo getLastChargingOrderInfo(String parkId,
                                                      String parkNo, String memberName, String phone) {
        return memberReportMapper.getLastChargingOrderInfo(parkId, parkNo, memberName, phone);
    }

    public Pager<MemberReportRelationVo> getRelationVo(Map<String, Object> paramMap) {
        List<MemberReportRelationVo> listObj = memberReportMapper.getRelationVo(paramMap);
        Pager<MemberReportRelationVo> pager = new Pager<MemberReportRelationVo>();
        pager.setDatas(listObj);
        pager.setTotalCount(getRelationVoNum(paramMap));
        return pager;
    }

    public int getRelationVoNum(Map<String, Object> paramMap) {
        return memberReportMapper.getRelationVoNum(paramMap);
    }

    public MemberReportVo selectDetailById(int id) {
        return memberReportMapper.selectDetailById(id);
    }

    public int getMemberRecords(String name, String phone) {
        return memberReportMapper.getMemberRecords(name, phone);
    }

    public int selectByOrderId(String orderId) {
        return memberReportMapper.selectByOrderId(orderId);

    }

    public int selectByOrderIds(List<String> orderIds) {
        if (CollectionUtils.isEmpty(orderIds)) return 0;
        return memberReportMapper.selectByOrderIds(orderIds);
    }
}
