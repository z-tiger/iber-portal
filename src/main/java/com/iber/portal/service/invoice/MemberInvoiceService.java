package com.iber.portal.service.invoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.invoice.MemberInvoiceMapper;
import com.iber.portal.model.invoice.MemberInvoice;
import com.iber.portal.vo.invoice.InvoiceOrderInfoVo;
import com.iber.portal.vo.invoice.MemberInvoiceVO;

/**
 * 发票管理service
 * 
 * @date 2017/11/10 create by zixiaobang
 */
@Service
public class MemberInvoiceService {

	@Autowired
	private MemberInvoiceMapper memberInvoiceMapper;

	/**
	 * 分页查询发票管理列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public Pager<MemberInvoiceVO> queryPageList(Map<String, Object> paramMap) {
		List<MemberInvoiceVO> listObj = memberInvoiceMapper.queryPageList(paramMap);
		for (MemberInvoiceVO memberInvoiceVO : listObj) {
			if (memberInvoiceVO.getServerType() == null) {
				continue;
			}
			memberInvoiceVO = setOrderMoney(memberInvoiceVO);
		}
		Pager<MemberInvoiceVO> pager = new Pager<MemberInvoiceVO>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	/**
	 * 分页查询发票管理列表记录数
	 * 
	 * @param paramMap
	 * @return
	 */
	private int getAllNum(Map<String, Object> paramMap) {
		return memberInvoiceMapper.getAllNum(paramMap);
	}

	public MemberInvoice selectById(Integer id) {
		return memberInvoiceMapper.selectByPrimaryKey(id);
	}

	public int updateStatusById(Integer id, int status) {
		return memberInvoiceMapper.updateStatusById(id, status);
	}

	public int updateByPrimaryKeySelective(MemberInvoice record) {
		return memberInvoiceMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public MemberInvoiceVO selectVOById(Integer id) {
		MemberInvoiceVO memberInvoiceVO = memberInvoiceMapper.selectVOById(id);
		if (memberInvoiceVO.getServerType() != null) {
			memberInvoiceVO = setOrderMoney(memberInvoiceVO);
		}
		return memberInvoiceVO;
	}

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public Pager<InvoiceOrderInfoVo> getOrderInfoList(Map<String, Object> paramMap) {
		List<InvoiceOrderInfoVo> listObj = memberInvoiceMapper.getOrderInfoList(paramMap);
		Pager<InvoiceOrderInfoVo> pager = new Pager<InvoiceOrderInfoVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllOrderInfoNum(paramMap));
		return pager;
	}

	private int getAllOrderInfoNum(Map<String, Object> paramMap) {
		return memberInvoiceMapper.getAllOrderInfoNum(paramMap);
	}

	/**
	 * 设置发票申请个订单的金额
	 * 
	 * @param memberInvoiceVO
	 * @param jsonObject
	 * @return
	 */
	private MemberInvoiceVO setOrderMoney(MemberInvoiceVO memberInvoiceVO) {
		switch (memberInvoiceVO.getServerType()) {
		case 1:
		case 4:
			memberInvoiceVO.setOrderMoney(memberInvoiceVO.getMoney().doubleValue() / 100);
			break;
		case 2:
			memberInvoiceVO.setChargingMoney(memberInvoiceVO.getMoney().doubleValue() / 100);
			break;
		case 3:
		case 5:
		case 6:
		case 7:
			JSONObject jsonObject = JSONObject.fromObject(memberInvoiceVO.getOrderIds());
			if (jsonObject.containsKey("tS")) {
				String tsOrderIds = jsonObject.getString("tS");
				if(!StringUtils.isBlank(tsOrderIds)) {
					String[] oids = tsOrderIds.split(",");
					List<String> olist = new ArrayList<String>();
					for (String oid : oids) {
						olist.add(oid);
					}
					Double omoney = memberInvoiceMapper.getOrderMoney(olist);
					memberInvoiceVO.setOrderMoney(omoney );
				}
			}
			if (jsonObject.containsKey("charging")) {
				String chOrderIds = jsonObject.getString("charging");
				if(!StringUtils.isBlank(chOrderIds)) {
					String[] cids = chOrderIds.split(",");
					List<String> clist = new ArrayList<String>();
					for (String oid : cids) {
						clist.add(oid);
					}
					Double cmoney = memberInvoiceMapper.getCharingMoney(clist);
					memberInvoiceVO.setChargingMoney(cmoney);
				}
			}
			if (jsonObject.containsKey("lr")) {
				String lrIds = jsonObject.getString("lr");
				if(!StringUtils.isBlank(lrIds)) {
					String[] lids = lrIds.split(",");
					List<String> lrlist = new ArrayList<String>();
					for (String oid : lids) {
						lrlist.add(oid);
					}
					Double lmoney = memberInvoiceMapper.getLrMoney(lrlist);
					if(memberInvoiceVO.getOrderMoney() != null) {
						lmoney = memberInvoiceVO.getOrderMoney()+lmoney;
					}
					memberInvoiceVO.setOrderMoney(lmoney);
				}
			}
			break;
		default:
			break;
		}
		return memberInvoiceVO;
	}
	
	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<MemberInvoiceVO> queryList(Map<String, Object> paramMap) {
		List<MemberInvoiceVO> listObj = memberInvoiceMapper.queryPageList(paramMap);
		for (MemberInvoiceVO memberInvoiceVO : listObj) {
			if (memberInvoiceVO.getServerType() == null) {
				continue;
			}
			memberInvoiceVO = setOrderMoney(memberInvoiceVO);
		}
		return listObj;
	}
}
