package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.iber.portal.dao.operationReport.MemberRegisterMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.MemberRegister;



/**
 * @describe 会员注册表
 * 
 * @author yangguiwu
 * @date 2016年04月07日
 */
@Service
public class MemberRegisterService {

	@Autowired
	private MemberRegisterMapper memberRegisterMapper;


	public List<MemberRegister> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return memberRegisterMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return memberRegisterMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}


	public List<MemberRegister> selectByPrimaryKeyExcel(Map<String, Object> record)throws ServiceException{
	       try{
	    	   return memberRegisterMapper.selectByPrimaryKeyExcel(record);	
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

    public List<MemberRegister> selectRegisterInfo(Map<String, Object> record)throws ServiceException{
	       try{
				return memberRegisterMapper.selectRegisterInfo(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectRegisterInfoRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return memberRegisterMapper.selectRegisterInfoRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

}
