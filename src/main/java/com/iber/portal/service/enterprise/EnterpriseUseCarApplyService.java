package com.iber.portal.service.enterprise;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.enterprise.EnterpriseUseCarApplyMapper;
import com.iber.portal.model.enterprise.EnterpriseUseCarApply;
import com.iber.portal.vo.enterprise.EnterpriseUseCarApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liubiao
 */
@Service
public class EnterpriseUseCarApplyService {
    @Autowired
    private EnterpriseUseCarApplyMapper enterpriseUseCarApplyMapper;

    public int deleteByPrimaryKey(Integer id){
        return enterpriseUseCarApplyMapper.deleteByPrimaryKey(id);
    }

    public int insert(EnterpriseUseCarApply record){
        return enterpriseUseCarApplyMapper.insert(record);
    }

    public int insertSelective(EnterpriseUseCarApply record){
        return enterpriseUseCarApplyMapper.insertSelective(record);
    }

    public EnterpriseUseCarApply selectByPrimaryKey(Integer id){
        return enterpriseUseCarApplyMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EnterpriseUseCarApply record){
        return enterpriseUseCarApplyMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EnterpriseUseCarApply record){
        return enterpriseUseCarApplyMapper.updateByPrimaryKey(record);
    }

    public Pager<EnterpriseUseCarApplyVo> getEnterpriseUseCarApplyPage(Map<String, Object> map) {
        List<EnterpriseUseCarApplyVo> carApplyVoList = enterpriseUseCarApplyMapper.selectEnterpriseUseCarApplyList(map);
        Pager<EnterpriseUseCarApplyVo>  pager = new Pager<EnterpriseUseCarApplyVo>();
        pager.setDatas(carApplyVoList);
        pager.setTotalCount(enterpriseUseCarApplyMapper.selectEnterpriseCarApplyListNumber(map));
        return pager;
    }


}
