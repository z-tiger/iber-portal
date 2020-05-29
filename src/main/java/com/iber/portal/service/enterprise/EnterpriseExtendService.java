package com.iber.portal.service.enterprise;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.MemberCardMapper;
import com.iber.portal.dao.base.MemberMapper;
import com.iber.portal.dao.base.MoneyLogMapper;
import com.iber.portal.dao.coupon.CouponMapper;
import com.iber.portal.dao.enterprise.EnterpriseExtendMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.MemberCard;
import com.iber.portal.model.base.MoneyLog;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.model.enterprise.EnterpriseExtend;

@Service
public class EnterpriseExtendService {

	@Autowired
	private EnterpriseExtendMapper enterpriseExtendMapper;
	
	@Autowired
	private MemberCardMapper memberCarMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CouponMapper couponMapper;
	
	@Autowired
	private MoneyLogMapper moneyLogMapper;
	
	public int insertSelective(EnterpriseExtend model) throws ServiceException {
		int len;
		try {
			len = enterpriseExtendMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = enterpriseExtendMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(EnterpriseExtend model) throws ServiceException {
		int len;
		try {
			len = enterpriseExtendMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public EnterpriseExtend selectByPrimaryKey(int id) {
		return enterpriseExtendMapper.selectByPrimaryKey(id);
	}
	
	public Pager<EnterpriseExtend> getAll(Map<String, Object> paramMap) {
		List<EnterpriseExtend> listObj = enterpriseExtendMapper.getAll(paramMap);
		Pager<EnterpriseExtend> pager = new Pager<EnterpriseExtend>();
		pager.setDatas(listObj);
		pager.setTotalCount(enterpriseExtendMapper.getAllNum(paramMap));
		return pager;
	}

	public void updateByMember(EnterpriseExtend currObj) {
		// TODO Auto-generated method stub
		int money = currObj.getAmount();
		
//		Coupon coupon = getCoupon(currObj.getMemberId());
//		if (coupon != null) {
//			int couponBalance = Integer.parseInt(coupon.getBalance()+"");//优惠券金额
//			if (couponBalance >= money) {
//				money = 0;
//			} else if (couponBalance < money) {
//				money = money - Integer.parseInt(couponBalance+ "");
//			}
//		}
		
		MemberCard mc = memberCarMapper.selectByMemberId(currObj.getMemberId());
		mc.setMoney(mc.getMoney()-money);
		mc.setTotalConsumeMoney(mc.getTotalConsumeMoney()+money);
		memberCarMapper.updateByPrimaryKeySelective(mc);
		
		//插入消费日志信息
		MoneyLog moneyLog = new MoneyLog();
		moneyLog.setCreateTime(new Date());
		moneyLog.setMemberId(mc.getMemberId());
		moneyLog.setMoney(money);
		moneyLog.setType("-");
		moneyLog.setCategory("timesharing");
		moneyLog.setObjId(String.valueOf(currObj.getOrderId()));
		moneyLogMapper.insertSelective(moneyLog);
		
	}

	private Coupon getCoupon(Integer memberId) {
		String userstatus = "0";// 可用状态 0可用  1不可使用
		Map<String, Object> couponParamMap = new HashMap<String, Object>();
		couponParamMap.put("currentTime", new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss").format(new Date()));
		couponParamMap.put("memberId", memberId);
		couponParamMap.put("userstatus", userstatus);

		List<Coupon> couponList = couponMapper.selectCanUsing(couponParamMap);
		Coupon coupon = null ;
		if (couponList.size() > 0) {
			coupon = couponList.get(0);
			/** 更新优惠券状态 */
			coupon.setUseStatus("1");
			coupon.setUseTime(new Date());
			couponMapper.updateByPrimaryKey(coupon);
		}
		return coupon;
	}

	public void updateByEnterprise(EnterpriseExtend currObj) {
		// TODO Auto-generated method stub
		if (currObj!=null) {
			System.out.println(currObj.getAmount());
			Integer money = currObj.getAmount();
			int enterpriseId = currObj.getEnterpriseId();
			Member m = memberMapper.selectEnterpriseAccount(enterpriseId);
			if (m!=null) {
				MemberCard mc = memberCarMapper.selectByMemberId(m.getId());
				if (mc != null) {
					mc.setMoney(mc.getMoney()-money);
					mc.setTotalConsumeMoney(mc.getTotalConsumeMoney()+money);
					memberCarMapper.updateByPrimaryKeySelective(mc);
				}
			}
			MemberCard memberCard = memberCarMapper.selectByMemberId(currObj.getMemberId());
			if (memberCard!=null) {
				if (money != null) {
					memberCard.setQuotaUseMoney(memberCard.getQuotaUseMoney()+money);
					memberCarMapper.updateByPrimaryKeySelective(memberCard);
				}	
			}
			
			//插入消费日志信息
			MoneyLog moneyLog = new MoneyLog();
			moneyLog.setCreateTime(new Date());
			moneyLog.setMemberId(memberCard.getMemberId());
			moneyLog.setMoney(money);
			moneyLog.setType("-");
			moneyLog.setCategory("timesharing");
			moneyLog.setObjId(String.valueOf(currObj.getOrderId()));
			moneyLogMapper.insertSelective(moneyLog);
		}
		
	}
}
