package com.iber.portal.service.enterprise;

import com.iber.portal.dao.enterprise.EnterpriseChargeLogMapper;
import com.iber.portal.model.enterprise.EnterpriseChargeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liubiao
 */
@Service
public class EnterpriseChargeLogService {
    @Autowired
    private EnterpriseChargeLogMapper enterpriseChargeLogMapper;

    public int deleteByPrimaryKey(Integer id) {
        return enterpriseChargeLogMapper.deleteByPrimaryKey(id);
    }

    public int insert(EnterpriseChargeLog record){
        return enterpriseChargeLogMapper.insert(record);
    }

    @Transactional(rollbackFor=Exception.class)
    public int insertSelective(EnterpriseChargeLog record){
        return enterpriseChargeLogMapper.insertSelective(record);
    }

    public EnterpriseChargeLog selectByPrimaryKey(Integer id){
        return enterpriseChargeLogMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EnterpriseChargeLog record){
        return enterpriseChargeLogMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EnterpriseChargeLog record){
        return enterpriseChargeLogMapper.updateByPrimaryKey(record);
    }
}
