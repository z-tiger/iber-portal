package com.iber.portal.service.enterprise;

import com.iber.portal.common.MD5;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberCardMapper;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.base.MoneyLogMapper;
import com.iber.portal.dao.enterprise.EnterpriseMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.model.base.MoneyLog;
import com.iber.portal.model.enterprise.Enterprise;
import com.iber.portal.vo.enterprise.EnterpriseMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EnterpriseService {

	@Autowired
	private EnterpriseMapper enterpriseMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MemberCardMapper memberCarMapper;
	
	@Autowired
	private MoneyLogMapper moneyLogMapper;
	
	public int insertSelective(Enterprise model) throws ServiceException {
		int len;
		try {
			len = enterpriseMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = enterpriseMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
    @Transactional(rollbackFor=Exception.class)
	public int updateByPrimaryKeySelective(Enterprise model)  {
        return enterpriseMapper.updateByPrimaryKeySelective(model);

    }

	public Enterprise selectByPrimaryKey(int id) {
		return enterpriseMapper.selectByPrimaryKey(id);
	}

	public List<Map<String,Object>> enterpriseList(){
	    return enterpriseMapper.enterpriseList();
    }

    public Pager<Enterprise> getBranchCompanyPage(Map<String,Object> map) {
        List<Enterprise> listObj = enterpriseMapper.findBranchCompanyByParentId(map);
        Pager<Enterprise> pager = new Pager<Enterprise>();
        pager.setDatas(listObj);
        pager.setTotalCount(enterpriseMapper.findBranchCompanyTotal(map));
        return pager;
    }

    public Pager<Enterprise> getAll(Map<String, Object> paramMap) {
		List<Enterprise> listObj = enterpriseMapper.getAll(paramMap);
		Pager<Enterprise> pager = new Pager<Enterprise>();
		pager.setDatas(listObj);
		pager.setTotalCount(enterpriseMapper.getAllNum(paramMap));
		return pager;
	}

    @Transactional(rollbackFor=Exception.class)
    public int saveEnterprise(Enterprise enterprise) {
        return enterpriseMapper.insertSelective(enterprise);
    }

    @Transactional(rollbackFor=Exception.class)
	public int saveEnterpriseInfo(Enterprise obj) {
		
		int r1 = enterpriseMapper.insertSelective(obj);
		Member oldMember = memberMapper.selectDetailByPhone(obj.getAdminMobile());
		int memberId = -1;
		int r2 = 1;
		int r5 = 1;
		if(null != oldMember){
			oldMember.setMemberLevel("manager");
			oldMember.setEnterpriseId(obj.getId());
			memberId = oldMember.getId();
			r2 = memberMapper.updateByPrimaryKeySelective(oldMember);
		}else{
			Member member = new Member();
			member.setName(obj.getAdmin());
			member.setPhone(obj.getAdminMobile());
			member.setPassword(MD5.toMD5(obj.getAdminMobile().substring(5)));
			member.setStatus("experience");
			member.setMemberLevel("manager");
			member.setRegisterCategory("platform");
			member.setAccoutStatus("0");
			member.setIsDrive("0");
			member.setCreateTime(new Date());
			member.setEnterpriseId(obj.getId());
			member.setCityCode(obj.getCityCode());
			r2 = memberMapper.insertSelective(member);
			
			memberId = member.getId();
			MemberCard memberCard = new MemberCard();
			memberCard.setMemberId(memberId);
			memberCard.setMoney(0);
			memberCard.setTotalChargeMoney(0);
			memberCard.setTotalConsumeMoney(0);
			memberCard.setTotalRefundMoney(0);
			memberCard.setQuota(0);
			memberCard.setCreateTime(new Date());
			memberCard.setDeposit(0);
			memberCard.setIntegral(0);
			memberCard.setQuota(0);
			memberCard.setQuotaMonth(0);
			memberCard.setQuotaUseMoney(0);
			r5 = memberCarMapper.insertSelective(memberCard);
		}
		
		
		Member accountMember = new Member();
		accountMember.setMemberLevel("account");
		accountMember.setEnterpriseId(obj.getId());
		accountMember.setCreateTime(new Date());
		accountMember.setCityCode(obj.getCityCode());
		accountMember.setName(obj.getEnterpriseName());
		int r3 = memberMapper.insertSelective(accountMember);
		
		MemberCard accountMemberCard = new MemberCard();
		accountMemberCard.setMemberId(accountMember.getId());
		accountMemberCard.setMoney(0);
		accountMemberCard.setTotalChargeMoney(0);
		accountMemberCard.setTotalConsumeMoney(0);
		accountMemberCard.setTotalRefundMoney(0);
		accountMemberCard.setQuota(0);
		accountMemberCard.setCreateTime(new Date());
		accountMemberCard.setDeposit(0);
		accountMemberCard.setIntegral(0);
		accountMemberCard.setQuota(0);
		accountMemberCard.setQuotaMonth(0);
		accountMemberCard.setQuotaUseMoney(0);
		int r4 = memberCarMapper.insertSelective(accountMemberCard);
		
		
		if(r1 >0 && r2 >0 && r3 >0 && r4 >0 && r5 >0){
			return 1;
		}else{
			return -1;
		}
	}

	public Pager<EnterpriseMemberVo> getEnterpriseMemberListById(
			Map<String, Object> paramMap) {
		List<EnterpriseMemberVo> listObj = memberMapper.selectEnterpriseMemberAll(paramMap);
		Pager<EnterpriseMemberVo> pager = new Pager<EnterpriseMemberVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(memberMapper.selectEnterpriseMemberAllNum(paramMap));
		return pager;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int saveEnterpriseMember(Enterprise e, Member m) {
		int r1 = memberMapper.insertSelective(m);
		MemberCard memberCard = new MemberCard();
		memberCard.setMemberId(m.getId());
		memberCard.setMoney(0);
		memberCard.setTotalChargeMoney(0);
		memberCard.setTotalConsumeMoney(0);
		memberCard.setTotalRefundMoney(0);
		memberCard.setQuota(0);
		memberCard.setCreateTime(new Date());
		memberCard.setDeposit(0);
		memberCard.setIntegral(0);
		memberCard.setQuota(0);
		memberCard.setQuotaMonth(0);
		memberCard.setQuotaUseMoney(0);
		int r2 = memberCarMapper.insertSelective(memberCard);
		if(r1 >0 && r2 >0){
			return 1;
		}else{
			return -1;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int saveImpEnterpriseMember(List<Map<String, String>> list,
			Enterprise e) {
		int r = 0 ;
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			String name = map.get("name").trim();
			String sex = map.get("sex").trim();
			String phone = map.get("phone").trim();
			String idcard = map.get("idcard").trim();
			
			Member phoneMember = memberMapper.selectDetailByPhone(phone);
			Member idcardMember = memberMapper.selectDetailByIdcard(idcard);
			if(!"".equals(phone) && null!=phone && !"null".equals(phone)){
				if(isMobileNO(phone)){
					if(!"".equals(idcard) && null!=idcard && !"null".equals(idcard)){
						if(isIdcarNO(idcard)){
							if(null != phoneMember){
								if(!"manager".equals(phoneMember.getMemberLevel())){
									phoneMember.setEnterpriseId(e.getId());
									phoneMember.setMemberLevel("person");
									memberMapper.updateByPrimaryKeySelective(phoneMember);
									r++;
								}
							}else{
								Member member = new Member();
								member.setPhone(phone);
								if("".equals(name) || null==name || "null".equals(name)){
									member.setName(phone);
								}else{
									member.setName(name);
								}
								if(!"".equals(sex) && null!=sex && !"null".equals(sex)){
									member.setSex(sex);
								}
								if(!"".equals(idcard) && null!=idcard && !"null".equals(idcard)){
									if(isIdcarNO(idcard) && null == idcardMember){
										member.setIdcard(idcard);
									}
								}
								member.setPassword(MD5.toMD5(phone.substring(5)));
								member.setStatus("experience");
								member.setMemberLevel("person");
								member.setRegisterCategory("platform");
								member.setAccoutStatus("0");
								member.setIsDrive("0");
								member.setCreateTime(new Date());
								member.setEnterpriseId(e.getId());
								member.setCityCode(e.getCityCode());
								memberMapper.insertSelective(member);
								
								MemberCard memberCard = new MemberCard();
								memberCard.setMemberId(member.getId());
								memberCard.setMoney(0);
								memberCard.setTotalChargeMoney(0);
								memberCard.setTotalConsumeMoney(0);
								memberCard.setTotalRefundMoney(0);
								memberCard.setQuota(0);
								memberCard.setCreateTime(new Date());
								memberCard.setDeposit(0);
								memberCard.setIntegral(0);
								memberCard.setQuota(0);
								memberCard.setQuotaMonth(0);
								memberCard.setQuotaUseMoney(0);
								memberCarMapper.insertSelective(memberCard);
								r++;
							}
						}
					}
				}
			}
		}
		return r;
	}
	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
	public boolean isMobileNO(String mobiles){  
		  
		
		Matcher m = p.matcher(mobiles);  
		//System.out.println(m.matches()+"---");  
		return m.matches();  
	}
	Pattern p1 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$");  
	Pattern p2 = Pattern.compile("^(\\d{6})()?(\\d{2})(\\d{2})(\\d{2})(\\d{3})$");  
	public boolean isIdcarNO(String idcarNO){  
		Pattern p =null;  
		if(idcarNO.length()==15 || idcarNO.length()==18){
			if(idcarNO.length()==18){
				p = p1;
			}else{
				p = p2;
			}
			
			Matcher m = p.matcher(idcarNO);  
			System.out.println(m.matches()+"---");  
			return m.matches();  
		}else{
			return false;
		}
	}
	
	public static void main(String[] args) {
//		isIdcarNO("36233019880403825");
	}

	public int insertMoneyLog(MoneyLog moneyLog) {
		int r1 = moneyLogMapper.insertSelective(moneyLog);
		if(r1 >0){
			return 1;
		}else{
			return -1;
		}
	}

	public Enterprise selectByEnterpriseName(String enterpriseName) {
		return enterpriseMapper.selectByEnterpriseName(enterpriseName);
	}
	
}
