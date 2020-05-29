package com.iber.portal.service.enterprise;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.enterprise.EnterpriseExcessApplyMapper;
import com.iber.portal.model.enterprise.EnterpriseExcessApply;
import com.iber.portal.vo.enterprise.EnterpriseExcessApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liubiao
 */
@Service
public class EnterpriseExceesApplyService {

    @Autowired
    private EnterpriseExcessApplyMapper enterpriseExcessApplyMapper;
    public int deleteByPrimaryKey(Integer id){
        return enterpriseExcessApplyMapper.deleteByPrimaryKey(id);
    }

    public int insert(EnterpriseExcessApply record){
        return enterpriseExcessApplyMapper.insert(record);
    }

    public int insertSelective(EnterpriseExcessApply record){
        return enterpriseExcessApplyMapper.insertSelective(record);
    }

    public EnterpriseExcessApply selectByPrimaryKey(Integer id){
        return enterpriseExcessApplyMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EnterpriseExcessApply record){
        return enterpriseExcessApplyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EnterpriseExcessApply record){
        return enterpriseExcessApplyMapper.updateByPrimaryKey(record);
    }

    public Pager<EnterpriseExcessApplyVo> getEnterpriseExcessApplyPage(Map<String, Object> map) {
        List<EnterpriseExcessApplyVo> excessApplyVos = enterpriseExcessApplyMapper.selectEnterpriseExcessApplyList(map);
        Pager<EnterpriseExcessApplyVo> pager = new Pager<EnterpriseExcessApplyVo>();
        pager.setDatas(excessApplyVos);
        pager.setTotalCount(enterpriseExcessApplyMapper.selectEnterpriseExcessApplyTotalNumber(map));
        return pager;
    }



}
