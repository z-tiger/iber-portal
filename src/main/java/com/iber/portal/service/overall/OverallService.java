package com.iber.portal.service.overall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.overall.OverallCarMapper;
import com.iber.portal.dao.overall.OverallAnnualMemberMapper;
import com.iber.portal.dao.overall.OverallMemberMapper;
import com.iber.portal.dao.overall.OverallPileMapper;
import com.iber.portal.model.overall.OverallCar;
import com.iber.portal.model.overall.OverallAnnualMember;
import com.iber.portal.model.overall.OverallMember;
import com.iber.portal.model.overall.OverallPile;

/**
 * 运营分析 总体情况分析service
 * 
 * @author zixb
 * @date 2017-07-03
 * 
 */
@Service
public class OverallService {

	private static  Logger logger = LoggerFactory.getLogger(OverallService.class);
	@Autowired
	private OverallCarMapper overallCarMapper;

	@Autowired
	private OverallAnnualMemberMapper overallAnnualMemberMapper;
	
	@Autowired
	private OverallPileMapper overallPileMapper;
	
	@Autowired
	private OverallMemberMapper overallMemberMapper;

	/**
	 * 添加车辆信息
	 * 
	 * @param record
	 * @return
	 */
	public int insertOverallCar(OverallCar record) {
		return overallCarMapper.insert(record);
	}

	/**
	 * 添加车辆信息
	 * 
	 * @param record
	 * @return
	 */
	public int insertOverallCarSelective(OverallCar record) {
		return overallCarMapper.insertSelective(record);
	}

	/**
	 * 添加年度会员信息
	 * 
	 * @param record
	 * @return
	 */
	public int insertOverallAnnualMember(OverallAnnualMember record) {
		return overallAnnualMemberMapper.insert(record);
	}

	/**
	 * 添加年度会员信息
	 * 
	 * @param record
	 * @return
	 */
	public int insertOverallAnnualMemberSelective(OverallAnnualMember record) {
		return overallAnnualMemberMapper.insertSelective(record);
	}


	/**
	 * 更新车辆分析
	 * 
	 * @param record
	 * @return
	 */
	public int updateOverallCar(OverallCar record) {
		return overallCarMapper.updateByPrimaryKey(record);
	}

	/**
	 * 更新车辆分析
	 * 
	 * @param record
	 * @return
	 */
	public int updateOverallCarSelective(OverallCar record) {
		return overallCarMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新年度会员分析
	 * 
	 * @param record
	 * @return
	 */
	public int updateOverallAnnualMember(OverallAnnualMember record) {
		return overallAnnualMemberMapper.updateByPrimaryKey(record);
	}

	/**
	 * 更新年度会员分析
	 * 
	 * @param record
	 * @return
	 */
	public int updateOverallAnnualMemberSelective(OverallAnnualMember record) {
		return overallAnnualMemberMapper.updateByPrimaryKeySelective(record);
	}

	
	/**
	 * 合并年度会员分析数据
	 * 
	 * @param distList
	 * @param arrayList
	 */
	private void mergeOverallAnnualMember(List<OverallAnnualMember> distList, List<OverallAnnualMember> ... arrayList ) {
		for ( List<OverallAnnualMember> resList : arrayList) {
		for (OverallAnnualMember resMember : resList) {
			boolean flag = true;
			for (OverallAnnualMember distMember : distList) {
				if (StringUtils.equals(resMember.getAnnual(), distMember.getAnnual())
						&& resMember.getTimeNum().intValue() == distMember.getTimeNum().intValue()
						&& StringUtils.equals(resMember.getTimeType(), distMember.getTimeType())
						&& StringUtils.equals(resMember.getCityCode(), distMember.getCityCode())
						) {
					if (resMember.getAddUser() != null && resMember.getAddUser() > 0) 
						distMember.setAddUser(resMember.getAddUser());
					if (resMember.getAuditUser() != null && resMember.getAuditUser() > 0) 
						distMember.setAuditUser(resMember.getAuditUser());
					if (resMember.getBalance() != null && resMember.getBalance() > 0) 
						distMember.setBalance(resMember.getBalance());
					if (resMember.getDeposit() != null && resMember.getDeposit() > 0) 
						distMember.setDeposit(resMember.getDeposit());
					if (resMember.getIncome() != null && resMember.getIncome() > 0) 
						distMember.setIncome(resMember.getIncome());
					if (resMember.getLoseUser() != null && resMember.getLoseUser() > 0) 
						distMember.setLoseUser(resMember.getLoseUser());
					if (resMember.getMemberAdd() != null && resMember.getMemberAdd() > 0) 
						distMember.setMemberAdd(resMember.getMemberAdd());
					if (resMember.getMemberAudit() != null && resMember.getMemberAudit() > 0) 
						distMember.setMemberAudit(resMember.getMemberAudit());
					if (resMember.getMemberLose() != null && resMember.getMemberLose() > 0) 
						distMember.setMemberLose(resMember.getMemberLose());
					if (resMember.getMemberTotal() != null && resMember.getMemberTotal() > 0) 
						distMember.setMemberTotal(resMember.getMemberTotal());
					if (resMember.getSilenceUser() != null && resMember.getSilenceUser() > 0) 
						distMember.setSilenceUser(resMember.getSilenceUser());
					if (resMember.getTotalBalance() != null && resMember.getTotalBalance() > 0) 
						distMember.setTotalBalance(resMember.getTotalBalance());
					if (resMember.getTotalDeposit() != null && resMember.getTotalDeposit() > 0) 
						distMember.setTotalDeposit(resMember.getTotalDeposit());
					if (resMember.getTotalIncome() != null && resMember.getTotalIncome() > 0) 
						distMember.setTotalIncome(resMember.getTotalIncome());
					if (resMember.getTotalLoseUser() != null && resMember.getTotalLoseUser() > 0) 
						distMember.setTotalLoseUser(resMember.getTotalLoseUser());
					if (resMember.getTotalMemberAudit() != null && resMember.getTotalMemberAudit() > 0) 
						distMember.setTotalMemberAudit(resMember.getTotalMemberAudit());
					if (resMember.getTotalMemberLose() != null && resMember.getTotalMemberLose() > 0) 
						distMember.setTotalMemberLose(resMember.getTotalMemberLose());
					if (resMember.getTotalMemberNew() != null && resMember.getTotalMemberNew() > 0) 
						distMember.setTotalMemberNew(resMember.getTotalMemberNew());
					if (resMember.getTotalSilenceUser() != null && resMember.getTotalSilenceUser() > 0) 
						distMember.setTotalSilenceUser(resMember.getTotalSilenceUser());
					if (resMember.getTotalUser() != null && resMember.getTotalUser() > 0) 
						distMember.setTotalUser(resMember.getTotalUser());
					flag = false;
					break;
				}
			}
			if (flag) {
				resMember.setCreateTime(new Date());
				distList.add(resMember);
			}
		}
		}
	}

	

	/**
	 * 合并车辆分析数据
	 * 
	 * @param arrayList
	 * @return
	 */
	private List<OverallCar> mergeOverallCar(List<OverallCar>... arrayList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<OverallCar> dataList = new ArrayList<OverallCar>();
		for (List<OverallCar> resList : arrayList) {
			for (OverallCar resCar : resList) {
				boolean flag = true;
				resCar.setCreateTime(new Date());
				if (dataList.isEmpty()) {
					dataList.add(resCar);
				} else {
					for (OverallCar distCar : dataList) {
						if (StringUtils.equals(distCar.getCityCode(), resCar.getCityCode())
								&& StringUtils.equals(distCar.getCarType(), resCar.getCarType())
								&& StringUtils.equals(sdf.format(distCar.getDateTime()), sdf.format(resCar.getDateTime()))
								) {
							if(resCar.getCarNum() != null && resCar.getCarNum() > 0)
								distCar.setCarNum(resCar.getCarNum());
							if(resCar.getCarRunNum() != null && resCar.getCarRunNum() > 0) 
								distCar.setCarRunNum(resCar.getCarRunNum());
							if(resCar.getMaintainNum() != null && resCar.getMaintainNum() > 0) 
								distCar.setMaintainNum(resCar.getMaintainNum());
							if(resCar.getOrderCarNum() != null && resCar.getOrderCarNum() > 0) 
								distCar.setOrderCarNum(resCar.getOrderCarNum());
							if(resCar.getOrderMemberNum() != null && resCar.getOrderMemberNum() > 0) 
								distCar.setOrderMemberNum(resCar.getOrderMemberNum());
							if(resCar.getOrderMoney() != null && resCar.getOrderMoney() > 0) 
								distCar.setOrderMoney(resCar.getOrderMoney());
							if(resCar.getOrderNum() != null && resCar.getOrderNum() > 0) 
								distCar.setOrderNum(resCar.getOrderNum());
							if(resCar.getPayMoney() != null && resCar.getPayMoney() > 0) 
								distCar.setPayMoney(resCar.getPayMoney());
							if(resCar.getRentCarNum() != null && resCar.getRentCarNum() > 0) 
								distCar.setRentCarNum(resCar.getRentCarNum());
							if(resCar.getRentMemberNum() != null && resCar.getRentMemberNum() > 0) 
								distCar.setRentMemberNum(resCar.getRentMemberNum());
							if(resCar.getRentMileage() != null && resCar.getRentMileage() > 0) 
								distCar.setRentMileage(resCar.getRentMileage());
							if(resCar.getRentNum() != null && resCar.getRentNum() > 0) 
								distCar.setRentNum(resCar.getRentNum());
							if(resCar.getRentTimelong() != null && resCar.getRentTimelong() > 0) 
								distCar.setRentTimelong(resCar.getRentTimelong());
							if(resCar.getRepairNum() != null && resCar.getRepairNum() > 0) 
								distCar.setRepairNum(resCar.getRepairNum());
							flag = false;
							break;
						} 
					}
					if(flag){
						dataList.add(resCar);
					}
				}
			}
		}
		return dataList;
	}

	
	/**
	 * 总体情况分析 电桩分析数据合并
	 * 
	 * @param arrayList
	 * @return
	 */
	private List<OverallPile> mergeOverallPile(List<OverallPile>  ... arrayList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<OverallPile> distList = new ArrayList<OverallPile>();
		for (List<OverallPile> resList : arrayList) {
			for (OverallPile resPile : resList) {
				boolean flag = true;
				resPile.setCreateTime(new Date());
				if(distList.isEmpty()){
					distList.add(resPile);
				}else{
					for (OverallPile distPile : distList) {
						if(StringUtils.equals(distPile.getCityCode(), resPile.getCityCode())
								&&StringUtils.equals(distPile.getEquipmentType(), resPile.getEquipmentType())
								&&StringUtils.equals(distPile.getConnectorType(), resPile.getConnectorType())
								&& StringUtils.equals(sdf.format(distPile.getDateTime()), sdf.format(resPile.getDateTime()))
								){
							if(resPile.getChargingAmount() != null && resPile.getChargingAmount() > 0) 
								distPile.setChargingAmount(resPile.getChargingAmount());
							if(resPile.getChargingCount() != null && resPile.getChargingCount() > 0) 
								distPile.setChargingCount(resPile.getChargingCount());
							if(resPile.getChargingMemberNum() != null && resPile.getChargingMemberNum() > 0) 
								distPile.setChargingMemberNum(resPile.getChargingMemberNum());
							if(resPile.getChargingTimelong() != null && resPile.getChargingTimelong() > 0) 
								distPile.setChargingTimelong(resPile.getChargingTimelong());
							if(resPile.getOnlineNum() != null && resPile.getOnlineNum() > 0) 
								distPile.setOnlineNum(resPile.getOnlineNum());
							if(resPile.getOrderMemberNum() != null && resPile.getOrderMemberNum()> 0) 
								distPile.setOrderMemberNum(resPile.getOrderMemberNum());
							if(resPile.getOrderMoney() != null && resPile.getOrderMoney() > 0) 
								distPile.setOrderMoney(resPile.getOrderMoney());
							if(resPile.getOrderNum() != null && resPile.getOrderNum() > 0) 
								distPile.setOrderNum(resPile.getOrderNum());
							if(resPile.getPayMoney() != null && resPile.getPayMoney() > 0) 
								distPile.setPayMoney(resPile.getPayMoney());
							if(resPile.getPileNum() != null && resPile.getPileNum() > 0) 
								distPile.setPileNum(resPile.getPileNum());
							if(resPile.getMalfunctionNum() != null && resPile.getMalfunctionNum() > 0) 
								distPile.setMalfunctionNum(resPile.getMalfunctionNum());
							flag = false;
							break;
						}
					}
					if(flag){
						distList.add(resPile);
					}
				}
			}
		}
		return distList;
	}

	/**
	 * 合并查询结果
	 * 
	 * @param arrayList
	 * @return
	 */
	private List<OverallMember> mergeOverallMember(List<OverallMember> ... arrayList) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<OverallMember> distList = new ArrayList<OverallMember>();
		for (List<OverallMember> resList : arrayList) {
			for (OverallMember resMember : resList) {
				boolean flag = true;
				resMember.setCreateTime(new Date());
				if(distList.isEmpty()){
					distList.add(resMember);
				}else{
					for (OverallMember distMember : distList) {
						if(StringUtils.equals(resMember.getCityCode(), distMember.getCityCode())
								&&StringUtils.equals(resMember.getChannel(), distMember.getChannel())
								&&StringUtils.equals(resMember.getMemberType(), distMember.getMemberType())
								&&StringUtils.equals(sdf.format(resMember.getDateTime()), sdf.format(distMember.getDateTime()))
								){
							if(resMember.getActualIncome() != null && resMember.getActualIncome() > 0) 
								distMember.setActualIncome(resMember.getActualIncome());
							if(resMember.getAuditNum() != null && resMember.getAuditNum() > 0) 
								distMember.setAuditNum(resMember.getAuditNum());
							if(resMember.getBalance() != null && resMember.getBalance() > 0) 
								distMember.setBalance(resMember.getBalance());
							if(resMember.getChargeMemberNum() != null && resMember.getChargeMemberNum() > 0) 
								distMember.setChargeMemberNum(resMember.getChargeMemberNum());
							if(resMember.getDeposit() != null && resMember.getDeposit()> 0)
								distMember.setDeposit(resMember.getDeposit());
							if(resMember.getIncome() != null && resMember.getIncome() > 0) 
								distMember.setIncome(resMember.getIncome());
							if(resMember.getOnlineMemberNum() != null && resMember.getOnlineMemberNum() > 0) 
								distMember.setOnlineMemberNum(resMember.getOnlineMemberNum());
							if(resMember.getOnlineMoney() != null && resMember.getOnlineMoney() > 0) 
								distMember.setOnlineMoney(resMember.getOnlineMoney());
							if(resMember.getOwing30MemberNum() != null && resMember.getOwing30MemberNum() > 0) 
								distMember.setOwing30MemberNum(resMember.getOwing30MemberNum());
							if(resMember.getOwing30Money() != null && resMember.getOwing30Money() > 0) 
								distMember.setOwing30Money(resMember.getOwing30Money());
							if(resMember.getOwingMemberNum() != null && resMember.getOwingMemberNum() > 0)
								distMember.setOwingMemberNum(resMember.getOwingMemberNum());
							if(resMember.getOwingMoney() != null && resMember.getOwingMoney() > 0)
								distMember.setOwingMoney(resMember.getOwingMoney());
							if(resMember.getRefundMemberNum() != null && resMember.getRefundMemberNum() > 0) 
								distMember.setRefundMemberNum(resMember.getRefundMemberNum());
							if(resMember.getRefundMoney() != null && resMember.getRefundMoney() > 0) 
								distMember.setRefundMoney(resMember.getRefundMoney());
							if(resMember.getRegisterNum() != null && resMember.getRegisterNum() > 0)
								distMember.setRegisterNum(resMember.getRegisterNum());
							if(resMember.getUserNum() != null && resMember.getUserNum() > 0) 
								distMember.setUserNum(resMember.getUserNum());
							flag = false;
							break;
						}
					}
					if(flag){
						distList.add(resMember);
					}
				}
			}
		}
		return distList;
	}

	/**
	 * 批量插入会员数据
	 * 
	 * @param pileList
	 * @return
	 */
	public int insertBatchOverallMember(List<OverallMember> memberList) {
		return overallMemberMapper.insertBatch(memberList);
	}

	/**
	 * 批量插入电桩数据
	 * 
	 * @param pileList
	 * @return
	 */
	public int insertBatchOverallPile(List<OverallPile> pileList) {
		return overallPileMapper.insertBatch(pileList);
	}

	/**
	 * 批量插入车辆数据
	 * 
	 * @param carList
	 * @return
	 */
	public int insertBatchOverallCar(List<OverallCar> carList) {
		return overallCarMapper.insertBatch(carList);
	}

	/**
	 * 批量添加年度总体会员分析数据
	 * 
	 * @param memberList
	 * @return
	 */
	public int insertBatchAnnualMember(List<OverallAnnualMember> memberList) {
		return overallAnnualMemberMapper.insertBatch(memberList);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate     较小的时间
	 * @param bdate      较大的时间
	 * @return  相差天数
	 * @throws ParseException
	 */
	public  int daysBetween(Date smdate, Date bdate) throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			logger.error("OvreallService daysBetween 时间计算异常："+e.getMessage());
		}
		return 0;
	}   

	/**
	 * 初始化总的分析 车辆信息
	 * 
	 * @param currentTime
	 */
	@SuppressWarnings("unchecked")
	public void initOverallCar(String currentTime){
		List<OverallCar> carNumList = overallCarMapper.selectInitCarNum(currentTime);
		List<OverallCar> carRunNumList = overallCarMapper.selectInitCarRunNum(currentTime);
		List<OverallCar> repairNumList = overallCarMapper.selectInitRepairNum(currentTime);
		List<OverallCar> rentCarNumList = overallCarMapper.selectInitRentCarNum(currentTime);
		List<OverallCar> rentMemberNumList = overallCarMapper.selectInitRentMemberNum(currentTime);
		List<OverallCar> rentNumList = overallCarMapper.selectInitRentNum(currentTime);
		List<OverallCar> orderNumList = overallCarMapper.selectInitOrderNum(currentTime);
		List<OverallCar> orderCarNumList = overallCarMapper.selectInitOrderCarNum(currentTime);
		List<OverallCar> orderMemberNumList = overallCarMapper.selectInitOrderMemberNum(currentTime);
		List<OverallCar> rentTimelongList = overallCarMapper.selectInitRentTimelong(currentTime);
		List<OverallCar> rentMileageList = overallCarMapper.selectInitRentMileage(currentTime);
		List<OverallCar> orderMoneyList = overallCarMapper.selectInitOrderMoney(currentTime);
		List<OverallCar> orderPayMoneyList = overallCarMapper.selectInitPayMoney(currentTime);
		
		List<OverallCar> carList = mergeOverallCar(carNumList,
				carRunNumList, repairNumList, rentCarNumList,
				rentMemberNumList, rentNumList, orderCarNumList,
				orderMemberNumList, orderNumList, rentTimelongList,
				rentMileageList, orderMoneyList, orderPayMoneyList);
		if(!carList.isEmpty())
			insertBatchOverallCar(carList);
	}
	
	/**
	 * 初始化总的分析 电桩信息
	 * 
	 * @param currentTime
	 */
	@SuppressWarnings("unchecked")
	public void initOverallPile(String currentTime){
		List<OverallPile> pileNumList = overallPileMapper.selectInitPileNum(currentTime);
		//List<OverallPile> useNumList = overallPileMapper.selectInitMalfunctionNum(currentTime);
		//List<OverallPile> onlineNumList = overallPileMapper.selectInitOnlineNum(currentTime);
		List<OverallPile> finishOrderList = overallPileMapper.selectInitFinishOrderInfo(currentTime);
		List<OverallPile> allOrderList = overallPileMapper.selectInitAllOrderInfo(currentTime);
		List<OverallPile> pileList = mergeOverallPile(
				pileNumList,/* useNumList,onlineNumList, */
				finishOrderList, allOrderList);
		if(!pileList.isEmpty())
			insertBatchOverallPile(pileList);
	}
	
	/**
	 * 初始化总的分析 会员信息
	 * 
	 * @param currentTime
	 */
	@SuppressWarnings("unchecked")
	public void initOverallMember(String currentTime){
		List<OverallMember> userNumList = overallMemberMapper.selectInitUserNum(currentTime);
		List<OverallMember> auditNumList = overallMemberMapper.selectInitAuditNum(currentTime);
		List<OverallMember> balanceList = overallMemberMapper.selectInitBalance(currentTime);
		List<OverallMember> chargeMemberNumList = overallMemberMapper.selectInitChargeMemberNum(currentTime);
		List<OverallMember> depositList = overallMemberMapper.selectInitDeposit(currentTime);
		List<OverallMember> incomeList = overallMemberMapper.selectInitIncome(currentTime);
		List<OverallMember> onlineList = overallMemberMapper.selectInitOnline(currentTime);
		//List<OverallMember> owingList = overallMemberMapper.selectInitOwing(currentTime);
		//List<OverallMember> owing30List = overallMemberMapper.selectInitOwing30(currentTime);
		List<OverallMember> refundList = overallMemberMapper.selectInitRefund(currentTime);
		List<OverallMember> registerNumList = overallMemberMapper.selectInitRegisterNum(currentTime);
		
		List<OverallMember> memberList = mergeOverallMember(userNumList,auditNumList,
				balanceList, chargeMemberNumList, depositList, incomeList,
				onlineList,/* owingList, owing30List,*/ refundList, registerNumList);
		if(!memberList.isEmpty())
			insertBatchOverallMember(memberList);
	}
	
	/**
	 * 初始化 年度按月分析 
	 * 
	 * @param annual
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OverallAnnualMember> initAnnualMemberByMonth(int annual, int month){
		List<OverallAnnualMember> addAnnualMemberList = overallAnnualMemberMapper.selectInitAnnualAddMember(annual,month);
		// 新增加的会员
		List<OverallAnnualMember> addMemberList = overallAnnualMemberMapper.selectInitDayAddMember(annual,month);
		// 年度流失会员数
		List<OverallAnnualMember> loseAnnualMemberList = overallAnnualMemberMapper.selectInitAnnualLoseMember(annual,month);
		// 流失会员数
		List<OverallAnnualMember> loseMemberList = overallAnnualMemberMapper.selectInitDayLoseMember(annual,month);
		//总会员数
		List<OverallAnnualMember> totalMemberList =  overallAnnualMemberMapper.selectInitDayTotalMember(annual,month);
		//验证会员数
		List<OverallAnnualMember> auditMemberList = overallAnnualMemberMapper.selectInitDayAuditMember(annual,month);
		//年度验证会员数
		List<OverallAnnualMember> auditAnnualMemberList = overallAnnualMemberMapper.selectInitAnnualAuditMember(annual,month);
		//年度总余额
		List<OverallAnnualMember> annualBalanceList = overallAnnualMemberMapper.selectInitAnnualBalanceOrDeposit(annual,month,"B");
		//年度总押金
		List<OverallAnnualMember> annualDepositList = overallAnnualMemberMapper.selectInitAnnualBalanceOrDeposit(annual,month,"D");
		//余额
		List<OverallAnnualMember> dayBalanceList = overallAnnualMemberMapper.selectInitDayBalanceOrDeposit(annual,month,"B");
		//押金
		List<OverallAnnualMember> dayDepositList = overallAnnualMemberMapper.selectInitDayBalanceOrDeposit(annual,month,"D");
		//总收入
		List<OverallAnnualMember> totalIncomeList = overallAnnualMemberMapper.selectInitAnnualIncome(annual,month);
		//收入
		List<OverallAnnualMember> dayIncomeList = overallAnnualMemberMapper.selectInitDayIncome(annual,month);
		//沉默用户
		//List<OverallAnnualMember> silenceUserList = overallAnnualMemberMapper.selectInitSilenceUser(annual,month);
		//总沉默用户
		//List<OverallAnnualMember> totalSilenceUserList = overallAnnualMemberMapper.selectInitTotalSilenceUser(annual,month);
		//总用户
		List<OverallAnnualMember> totalUserList = overallAnnualMemberMapper.selectInitOverallAnnualMember(annual,month);
		//流失用户
		//List<OverallAnnualMember> loseUserList = overallAnnualMemberMapper.selectInitLoseUserList(annual,month);
		//新增用户
		List<OverallAnnualMember> addUserList = overallAnnualMemberMapper.selectInitAddUser(annual,month);
		//新增认证用户
		List<OverallAnnualMember> auditUserList = overallAnnualMemberMapper.selectInitAuditUser(annual,month);
		//初始化年度分析按季
		List<OverallAnnualMember> dataList = overallAnnualMemberMapper.selectByAnnualAndTimeType(annual, "month", month);
		
		mergeOverallAnnualMember(dataList,totalMemberList, loseMemberList,
				loseAnnualMemberList, addMemberList, addAnnualMemberList,
				auditMemberList, auditAnnualMemberList, annualBalanceList,
				annualDepositList, dayBalanceList, dayDepositList, totalIncomeList,
				dayIncomeList,totalUserList,addUserList,auditUserList/*,silenceUserList*/);
		
		return dataList;
	}
	
	/**
	 * 初始化年度按季分析
	 * 
	 * @param annual
	 * @param quarter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<OverallAnnualMember> initAnnualMemberByQuarter(int annual, int quarter){
		List<OverallAnnualMember> addAnnualMemberList = overallAnnualMemberMapper.selectInitAnnualAddMemberByQuarter(annual,quarter);
		// 新增加的会员
		List<OverallAnnualMember> addMemberList = overallAnnualMemberMapper.selectInitDayAddMemberByQuarter(annual,quarter);
		// 年度流失会员数
		List<OverallAnnualMember> loseAnnualMemberList = overallAnnualMemberMapper.selectInitAnnualLoseMemberByQuarter(annual,quarter);
		// 流失会员数
		List<OverallAnnualMember> loseMemberList = overallAnnualMemberMapper.selectInitDayLoseMemberByQuarter(annual,quarter);
		//总会员数
		List<OverallAnnualMember> totalMemberList =  overallAnnualMemberMapper.selectInitDayTotalMemberByQuarter(annual,quarter);
		//验证会员数
		List<OverallAnnualMember> auditMemberList = overallAnnualMemberMapper.selectInitDayAuditMemberByQuarter(annual,quarter);
		//年度验证会员数
		List<OverallAnnualMember> auditAnnualMemberList = overallAnnualMemberMapper.selectInitAnnualAuditMemberByQuarter(annual,quarter);
		//年度总余额
		List<OverallAnnualMember> annualBalanceList = overallAnnualMemberMapper.selectInitAnnualBalanceOrDepositByQuarter(annual,quarter,"B");
		//年度总押金
		List<OverallAnnualMember> annualDepositList = overallAnnualMemberMapper.selectInitAnnualBalanceOrDepositByQuarter(annual,quarter,"D");
		//余额
		List<OverallAnnualMember> dayBalanceList = overallAnnualMemberMapper.selectInitDayBalanceOrDepositByQuarter(annual,quarter,"B");
		//押金
		List<OverallAnnualMember> dayDepositList = overallAnnualMemberMapper.selectInitDayBalanceOrDepositByQuarter(annual,quarter,"D");
		//总收入
		List<OverallAnnualMember> totalIncomeList = overallAnnualMemberMapper.selectInitAnnualIncomeByQuarter(annual,quarter);
		//收入
		List<OverallAnnualMember> dayIncomeList = overallAnnualMemberMapper.selectInitDayIncomeByQuarter(annual,quarter);
		//沉默用户
		//List<OverallAnnualMember> silenceUserList = overallAnnualMemberMapper.selectInitSilenceUserByQuarter(annual,quarter);
		//总沉默用户
		//List<OverallAnnualMember> totalSilenceUserList = overallAnnualMemberMapper.selectInitTotalSilenceUserByQuarter(annual,quarter);
		//总用户
		List<OverallAnnualMember> totalUserList = overallAnnualMemberMapper.selectInitOverallAnnualMemberByQuarter(annual,quarter);
		//流失用户
		//List<OverallAnnualMember> loseUserList = overallAnnualMemberMapper.selectInitLoseUserListByQuarter(annual,quarter);
		//新增用户
		List<OverallAnnualMember> addUserList = overallAnnualMemberMapper.selectInitAddUserByQuarter(annual,quarter);
		//新增认证用户
		List<OverallAnnualMember> auditUserList = overallAnnualMemberMapper.selectInitAuditUserByQuarter(annual,quarter);
		//初始化年度分析按季
		List<OverallAnnualMember> dataList = overallAnnualMemberMapper.selectByAnnualAndTimeType(annual, "quarter", quarter);
		mergeOverallAnnualMember(dataList, totalMemberList, loseMemberList,
				loseAnnualMemberList, addMemberList, addAnnualMemberList,
				auditMemberList, auditAnnualMemberList, annualBalanceList,
				annualDepositList, dayBalanceList, dayDepositList, totalIncomeList,
				dayIncomeList,totalUserList,addUserList,auditUserList/*,silenceUserList*/);
		return dataList;
	}
	
	
	/**
	 * 初始化总体分析数据
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public String initOverallData(String type) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int days = 0;
		Date startDate = sdf.parse("2016-11-01");
		days = daysBetween(startDate, new Date());
		String result = "success";
		
		if(StringUtils.equals("all", type)){
			int records = overallCarMapper.getInitRecords();
			if(records > 0){
				return "数据库中表已有数据，请删除相应表数据后再试";
			} 
		}
		
		if(StringUtils.equals(type, "car")){
			int records = overallCarMapper.getCarRecords();
			if(records > 0){
				return "数据库中bi_overall_car表已有数据，请删除相应表数据后再试";
			} 
		}
		
		if(StringUtils.equals(type, "pile")){
			int records = overallPileMapper.getPileRecords();
			if(records > 0){
				return "数据库中bi_overall_pile表已有数据，请删除相应表数据后再试";
			} 
		}
		
		if(StringUtils.equals(type, "member")){
			int records = overallMemberMapper.getMemberRecords();
			if(records > 0){
				return "数据库中bi_overall_member表已有数据，请删除相应表数据后再试";
			} 
		}
		
		for(int index = 0 ; index < days ; index++){
			Calendar   calendar   =   new   GregorianCalendar(); 
			calendar.setTime(startDate); 
			calendar.add(Calendar.DAY_OF_YEAR,index);//把日期往后增加一天.整数往后推,负数往前移动 
			String currentTime = sdf.format(calendar.getTime());
			System.out.println(currentTime);
			//初始化车辆
			if(StringUtils.equals(type, "car") || StringUtils.equals(type, "all"))
				initOverallCar(currentTime);
			//初始化电桩
			if(StringUtils.equals(type, "pile") || StringUtils.equals(type, "all"))
				initOverallPile(currentTime);
			//初始化会员
			if(StringUtils.equals(type, "member") || StringUtils.equals(type, "all"))
				initOverallMember(currentTime);
		}
		
		//初始化年度分析
		int annual = 2017 ;
		int currMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
		
		//初始化年度分析按月
		for(int month = 1 ; month <= currMonth ; month++){
			System.out.println(annual +"年"+month+"月");
			if(StringUtils.equals(type, "amm") || StringUtils.equals(type, "all")){
				List<OverallAnnualMember> dataList = initAnnualMemberByMonth(annual,month);
				if(!dataList.isEmpty())
					insertBatchAnnualMember(dataList);
			}
		}
		
		//初始化年度分析按季
		for(int quarter=1 ; quarter <= (currMonth/3+1) ; quarter++){
			System.out.println(annual +"年"+quarter+"季");
			if(StringUtils.equals(type, "aqm") || StringUtils.equals(type, "all")){
				List<OverallAnnualMember> dataList 	= initAnnualMemberByQuarter(annual,quarter);
				if(!dataList.isEmpty())
					insertBatchAnnualMember(dataList);
			}
		}
		
		annual = 2016;
		
		//初始化年度分析按月
		for(int month = 11 ; month <= 12 ; month++){
			System.out.println(annual +"年"+month+"月");
			if(StringUtils.equals(type, "amm") || StringUtils.equals(type, "all")){
				List<OverallAnnualMember> dataList = initAnnualMemberByMonth(annual,month);
				if(!dataList.isEmpty())
					insertBatchAnnualMember(dataList);
			}
		}
		
		//初始化年度分析按季
		for(int quarter=4 ; quarter <= 4 ; quarter++){
			System.out.println(annual +"年"+quarter+"季");
			if(StringUtils.equals(type, "aqm") || StringUtils.equals(type, "all")){
				List<OverallAnnualMember> dataList 	= initAnnualMemberByQuarter(annual,quarter);
				if(!dataList.isEmpty())
					insertBatchAnnualMember(dataList);
			}
		}
		return result;
	}
	
	/**
	 * 实例化某天数据
	 * 
	 * @param dayTime
	 * @param type
	 * @return
	 */
	public String setOneDayData(String dayTime,String type){
		System.out.println(dayTime +"*****"+type);
		if(StringUtils.isBlank(dayTime)){
			return "FAIL 初始化时间不能为空";
		}
		String[] arr = dayTime.split("-");
		int annual = Integer.parseInt(arr[0]);
		int month = Integer.parseInt(arr[1]);
		int quarter = month/3 +1;
		
		//初始化车辆
		if(StringUtils.equals(type, "car") || StringUtils.equals(type, "all"))
			try{
				initOverallCar(dayTime);
			}catch (Exception e) {
				return "FAIL 总体情况统计 初始化车辆信息异常 ："+e.getMessage();
			}
		
		//初始化电桩
		if(StringUtils.equals(type, "pile") || StringUtils.equals(type, "all"))
			try{
				initOverallPile(dayTime);
			}catch (Exception e) {
				return "FAIL 总体情况统计 初始化电桩信息异常 ："+e.getMessage();
			}
		
		//初始化会员
		if(StringUtils.equals(type, "member") || StringUtils.equals(type, "all"))
			try{
				initOverallMember(dayTime);
			}catch (Exception e) {
				return "FAIL 总体情况统计 初始化会员信息异常 ："+e.getMessage();
			}

		//初始化年度分析按月
		if(StringUtils.equals(type, "amm") || StringUtils.equals(type, "all")){
			try{
				List<OverallAnnualMember> dataList = initAnnualMemberByMonth(annual,month);
				for (OverallAnnualMember record : dataList) {
					if(record.getId() != null){
						updateOverallAnnualMemberSelective(record);
					}else{
						insertOverallAnnualMemberSelective(record);
					}
				}
			}catch (Exception e) {
				return "FAIL 总体情况统计 初始化年度会员信息异常 ："+e.getMessage();
			}
		}
		
		//初始化年度分析按季
		if(StringUtils.equals(type, "aqm") || StringUtils.equals(type, "all")){
			try{
				List<OverallAnnualMember> dataList = initAnnualMemberByQuarter(annual,quarter);
				for (OverallAnnualMember record : dataList) {
					if(record.getId() != null){
						updateOverallAnnualMemberSelective(record);
					}else{
						insertOverallAnnualMemberSelective(record);
					}
				}
			}catch (Exception e) {
				return "FAIL 总体情况统计 初始化年度会员信息异常 ："+e.getMessage();
			}
		}
		System.out.println("SUCCESS");
		return  "SUCCESS";
	}
}


