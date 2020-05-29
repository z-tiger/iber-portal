package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.operationReport.MemberCapitalMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.MemberCapital;
import com.iber.portal.model.operationReport.MemberRecharge;



/**
 * @describe 车辆使用明细
 * 
 * @author yangguiwu
 * @date 2016年03月31日
 */
@Service
public class MemberCapitalService {

	@Autowired
	private MemberCapitalMapper memberCapitalMapper;

	public List<MemberCapital> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return memberCapitalMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return memberCapitalMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public List<MemberCapital> selectByPrimaryKeyExcel(Map<String, String> map) {

		return memberCapitalMapper.selectByPrimaryKeyExcel(map);	
	}

    public Pager<MemberRecharge> selectMemberRechargeDetails(Map<String, Object> map) throws ServiceException {
    	try {
    		Pager<MemberRecharge> pager = new Pager<MemberRecharge>();
    		 List<MemberRecharge> list = memberCapitalMapper.selectMemberRechargeDetails(map);
    		 pager.setTotalCount(memberCapitalMapper.selectMemberRechargeDetailsCount((Integer)map.get("memberId")));
    		 pager.setDatas(list);
    		 return pager;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
    }   
}
